package simulator.scheduler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Objects;

public class Main {

    private static void invalidArguments() {
        System.out.println("Invalid arguments.");
        System.exit(1);
    }

    private static void invalidFile() {
        System.out.println("Cannot open file.");
        System.exit(1);
    }

    private static void openAndPrepareProcess(String filename) {
        try {
            FileReader fr = new FileReader(filename);
            BufferedReader br = new BufferedReader(fr);
            String contents = "";
            String tmpLine;
            while ((tmpLine = br.readLine()) != null) {
                contents += tmpLine;
            }
            try {
                JSONArray array = new JSONArray(contents);
                int nProcess = array.length();
                PCB pcb[] = new PCB[nProcess];
                for (int n = 0; n < nProcess; n++) {
                    JSONObject object = array.getJSONObject(n);
                    pcb[n] = new PCB(object.getString("name"), (float) object.getDouble("burst_time"), (float) object.getDouble("arrival_time"));
                }
            } catch (JSONException j) {
                invalidFile();
            }
        } catch (IOException e) {
            invalidFile();
        }

    }

    public static void main(String[] args) {
        boolean quiet, generate;
        float quantum;
        String filename;

        if (args.length < 2) {
            invalidArguments();
        }

        if (Objects.equals(args[0], "--generate")) {
            generate = true;
        } else {
            filename = args[0];
            openAndPrepareProcess(filename);
        }

        if (Objects.equals(args[1], "fcfs")) {
            FCFS firstComeFirstServed = new FCFS();
        } else if (Objects.equals(args[1], "rr")) {
            if (Objects.equals(args[2], "q")) {
                quantum = Float.parseFloat(args[3]);
                RR roundRobin = new RR(quantum);
            } else {
                invalidArguments();
            }
        } else {
            invalidArguments();
        }

        /*float executionTime, arrivalTime;
        Scheduler a = new Scheduler();
        int q = sc.nextInt();
        int n_processos = sc.nextInt();
        int resta = 0, executando = 0, TTE = 0, TTESP = 0, begin_time = 0, end_time = 0;

        for (int i = 0; i < n_processos; i++) {
            executionTime = sc.nextInt();
            arrivalTime = sc.nextInt();
            a.setPcb(q, arrivalTime, executionTime, resta, executando, TTE, TTESP, begin_time, end_time);
        }
        a.firstComeFirstServed();*/
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