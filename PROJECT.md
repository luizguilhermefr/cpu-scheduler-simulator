# CPU Scheduling Simulator #

## Projeto ##

### Especificações técnicas ###

Linguagem: **Java JDK 8**

Bibliotecas adicionais: **-**

### Uso Esperado ###

O programa pode ser iniciado da seguinte maneira:

`cpu-simulate [arquivo][opções]`

Onde as opções disponíveis são:

1. `fcfs` (First-come, first-served) | `rr` (Round-robin)

1.1 `-q=2` (quantum, para o caso de Round-robin)

2. `--time` (Onde o simulador durará realmente o tempo esperado pelo arquivo, em segundos)

3. `--quiet` (Onde o simulador imprimirá apenas o resultado final)

Já o arquivo será um **JSON** da seguinte estrutura:

```
{
    [
        {
            "burst_time": 12.0,
            "arrival_time": 0.2
        },
        ...
        {
            "burst_time": 1.55,
            "arrival_time": 1.2
        }
    ]
}
```
