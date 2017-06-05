# CPU Scheduling Simulator #

## Projeto ##

### Especificações técnicas ###

Linguagem: **Java JDK 8**

Bibliotecas adicionais: **https://github.com/stleary/JSON-java**

### Uso Esperado ###

O programa pode ser iniciado da seguinte maneira:

`cpu-simulate [arquivo][opções]`

Onde as opções disponíveis são:

1. `--generate` (Em vez de um arquivo específico, gerará processos utilizando uma distribuição normal)

2. `fcfs` (First-come, first-served) | `rr` (Round-robin)

2.1 `-q=2` (quantum, para o caso de Round-robin)

3. `--time` (Onde o simulador durará realmente o tempo esperado pelo arquivo, em segundos)

4. `--quiet` (Onde o simulador imprimirá apenas o resultado final)

Já o arquivo será um **JSON** da seguinte estrutura:

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

Já o retorno esperado, dependerá das opções escolhidas.

Em todos os casos, o programa inicia com:

```
Starting scheduling with 3 process.
Policy: Round Robin
Quantum: 2
```

Para o caso `--quiet`, será apenas a seguinte tabela:

```
-----------------------------------------------
| Process        | Wait Time | Execution Time |
-----------------------------------------------
| My Browser     | 2.2       | 2.8            |
| My Text Editor | 1.0       | 1.8            |
-----------------------------------------------
```

Do contrário, além disso, a cada preempção, imprimirá:

```
-- Preemption -- Current Time = 2.2 ---
| Exits            | Enters           |
---------------------------------------
| My Browser       | My Text Editor   |
---------------------------------------
```

Para o caso `--time`, o programa durará exatamente o tempo previsto, convertido para segundos. A resposta não alterará, porém a interação com o usuário sim.
