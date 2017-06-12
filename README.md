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

### Return

The expected return will be the scheduling result of the processes within the given policy. It is printed like:

```
Starting scheduling with 3 processes.
Policy: Round Robin
Quantum: 2

-- Preemption -- Current Time = 2.2 ---
| Exits            | Enters           |
---------------------------------------
| My Browser       | My Text Editor   |
---------------------------------------

-----------------------------------------------
| Process        | Wait Time | Execution Time |
-----------------------------------------------
| My Browser     | 0.0       | 2.2            |
| My Text Editor | 2.2       | 3.8            |
-----------------------------------------------
| Average        | 2.2       | 3.0            |
-----------------------------------------------
```

### Implementation

- **Main** Class: Entry of command line args, and input preparation.

- **PCB** Class: Stands for "Process Control Block". Represents an unitary process. An array of PCB represents the processes set.

- **Scheduler** Class: The generalized class for scheduler. It is extended by the next two classes.

- **RR** Class: The Round Robin scheduler class. Extends the Scheduler class adding the specific methods and attributes (e.g. the *quantum*).

- **FCFS** Class: The First Come, First Served scheduler class. Extends the Scheduler class.

![Class Diagram](./project%20details/class%20diagram.png | width=400)

### Contributors

[Elixandre Michael Baldi](https://github.com/ElixandreBaldi)

[João Victor Canabarro](https://github.com/jcanabarro)

[Luiz Guilherme F. Rosa](https://github.com/luizguilhermefr)

And **Professor Dr. Marcio Seiji Oyamada**