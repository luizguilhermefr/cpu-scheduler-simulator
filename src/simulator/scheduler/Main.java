package simulator.scheduler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Objects;
import java.util.Random;

public class Main {
    private static PCB pcb[];
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
                pcb = new PCB[nProcess];
                for (int n = 0; n < nProcess; n++) {
                    JSONObject object = array.getJSONObject(n);
                    pcb[n] = new PCB(object.getString("name"), (float) object.getDouble("burst_time"), (float) object.getDouble("arrival_time"));
                }
            } catch (JSONException j) {
                invalidFile();
            }
            fr.close();
            br.close();
        } catch (IOException e) {
            invalidFile();
        }

    }

//    public static void formatVariableSize(String[] args, int i) { falar depois com o UJS sobre a necessidade do metodo
//        if(Objects.equals(args[i], "-i")){
//
//        }else if(Objects.equals(args[i],"-f")) {
//
//
//        }else
//            invalidArguments();
//    }

    public static void normalDistribution( PCB[] pcb, int nProcess, float mean, float standardDeviation, float minRange, float maxRange){
        pcb = new PCB[nProcess];
        for (int n = 0; n < nProcess; n++) {
            Random rand = new Random();
            double burstTime = ( (rand.nextGaussian() * standardDeviation) + mean);
            double arrivalTime = ( (rand.nextGaussian() * standardDeviation) + mean);
            pcb[n] = new PCB("Process" + n, (float) burstTime , (float) arrivalTime);
        }
    }

    public static void main(String[] args) {
        boolean quiet, generate = false;
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
        if(generate){
            int nProcess;
            float mean, standardDeviation, minRange,maxRange;
            if(Objects.equals(args[1],"fcfs")) {
                if ((Objects.equals(args[2],"-n")) && (Objects.equals(args[4],"-m")) && (Objects.equals(args[6],"-sd"))) {
                    nProcess = Integer.parseInt(args[3]);
                    mean = Float.parseFloat(args[5]);
                    standardDeviation = Float.parseFloat(args[7]);
                    if(Objects.equals(args[8],"-range")) {
                        minRange = Float.parseFloat(args[9]);
                        maxRange = Float.parseFloat(args[10]);
                    }
                    else{
                        minRange = 0;
                        maxRange = 500;
                    }
                    normalDistribution( pcb, nProcess, mean, standardDeviation, minRange,maxRange);
                } else {
                    invalidArguments();
                }
            }
        }

        if (Objects.equals(args[1], "fcfs")) {
            FCFS firstComeFirstServed = new FCFS(pcb);
        } else if (Objects.equals(args[1], "rr")) {
            if (Objects.equals(args[2], "-q")) {
                quantum = Float.parseFloat(args[3]);
                RR roundRobin = new RR(quantum, pcb);
            } else {
                invalidArguments();
            }
        } else {
            invalidArguments();
        }

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