package simulator.scheduler;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        int TE, TC;
        Scheduler a = new Scheduler();
        Scanner sc = new Scanner(System.in);
        System.out.println("Entre com a quantidade de processos");
        int q = sc.nextInt();
        int n_processos = sc.nextInt();
        int resta = 0, executando = 0, TTE = 0, TTESP = 0, begin_time = 0, end_time = 0;

        for (int i = 0; i < n_processos; i++) {
            TE = sc.nextInt();
            TC = sc.nextInt();
            a.setPcb(q, TC, TE, resta, executando, TTE, TTESP, begin_time, end_time);
        }
        a.firstComeFirstServed();
    }
}
/*
3
3
3
2
2
1
10
0
*/