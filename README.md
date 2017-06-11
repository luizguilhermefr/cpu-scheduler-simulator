# CPU Scheduling Simulator #

## Project ##

### Specifications ###

Language: **Java JDK 8**

Additional Libraries: **https://github.com/stleary/JSON-java**

### Use ###

The program may be call using:

`java -jar program.jar [file|generator][policy|all][generator options]`

Where:

1. `java -jar` Invokes the JRE to run the program.

2. `program.jar` The built program location.

3. `file|generator` The input file or `--generate` to make the program to generate random processes using normal distribution (see generator options below).

4. `policy|all` The scheduling policy or all of them. Currently `fcfs` to First Come, First Served or `rr` to Round Robin. Call `--all` to omit the policy and call both. In the two last cases, you must also specify:

4.1 `quantum` The quantum for Round Robin policy. Inform using `-q FLOAT`.

5. `generator options` When calling the generator, you may specify the number of processes, the desired mean and standard deviation. This can be done using: `-n INTEGER -m FLOAT -sd FLOAT`.

Some examples would be:

1. `java -jar cpu-scheduler.jar proc.json rr 2`

2. `java -jar cpu-scheduler.jar --generate fcfs -n 10 -m 5 -sd 2`

3. `java -jar cpu-scheduler.jar /home/username/proc.json --all -q 2`

The input file is a **JSON** structured like:

```
{
    [
        {
            "name": "My Browser",
            "burst_time": 12.0,
            "arrival_time": 0.2
        },
        ...
        {
            "name": "My Text Editor",
            "burst_time": 1.55,
            "arrival_time": 1.2
        }
    ]
}
```

And the expected return:


```
Starting scheduling with 3 process.
Policy: Round Robin
Quantum: 2
```

```
-----------------------------------------------
| Process        | Wait Time | Execution Time |
-----------------------------------------------
| My Browser     | 2.2       | 2.8            |
| My Text Editor | 1.0       | 1.8            |
-----------------------------------------------
```


```
-- Preemption -- Current Time = 2.2 ---
| Exits            | Enters           |
---------------------------------------
| My Browser       | My Text Editor   |
---------------------------------------
```

