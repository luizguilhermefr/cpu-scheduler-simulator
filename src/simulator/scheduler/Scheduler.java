package simulator.scheduler;

import javafx.animation.Timeline;

class Scheduler {
    PCB[] processControlBlock;
    int nProcess;
    float timeLine = 0;
    private float avgExecutionTime, avgWaitTime;
    float normalization;

    Scheduler (PCB[] pcb) {
        this.processControlBlock = pcb;
        this.nProcess = pcb.length;
        normalization();
        System.out.println("Initialized scheduler with " + this.nProcess + " processes.");

    }

    private void normalization () {
        this.normalization = this.processControlBlock[0].getArrivalTime();
    }

    void sortPcb () {
        for (int i = 0; i < nProcess; i++) {
            for (int j = 1; j < nProcess; j++) {
                if (processControlBlock[j].getArrivalTime() < processControlBlock[j - 1].getArrivalTime()) {
                    PCB temptemp = processControlBlock[j - 1];
                    processControlBlock[j - 1] = processControlBlock[j];
                    processControlBlock[j] = temptemp;
                }
            }
        }
    }

    void averageTime () {
        for (int i = 0; i < nProcess; i++) {
            avgExecutionTime += processControlBlock[i].getBurstTime() + processControlBlock[i].getWaitTime();
            avgWaitTime += processControlBlock[i].getWaitTime();
        }
        avgWaitTime /= nProcess;
        avgExecutionTime /= nProcess;
    }

    private String indentation(String text, int limit) {
        int qtdSpace = 0;
        if (limit >= text.length()) {
            qtdSpace = limit - text.length();
            for (int i = 0; i < qtdSpace; i++) {
                text += " ";
            }
            return text;
        } else {
            String temp = "";
            for (int i = 0; i < limit - 3; i++) {
                temp += text.charAt(i);
            }
            temp += "...";
            return temp;
        }
    }

    void resultTable () {
        System.out.print("---------------------------------------------------------------------------\n" +
                "| Process        | Wait Time | Execution Time | Arrival Time | Burst Time |\n" +
                "---------------------------------------------------------------------------\n");
        for (int i = 0; i < nProcess; i++) {
            System.out.print("|" + indentation(processControlBlock[i].getName(), 16) + "|");
            System.out.print(indentation(String.valueOf(String.format("%.3f",processControlBlock[i].getWaitTime())), 11) + "|");
            System.out.print(indentation(String.valueOf(String.format("%.3f",processControlBlock[i].getWaitTime() + processControlBlock[i].getExecuted())), 16) + "|");
            System.out.print(indentation(String.valueOf(String.format("%.3f",processControlBlock[i].getArrivalTime())), 14) + "|");
            System.out.print(indentation(String.valueOf(String.format("%.3f",processControlBlock[i].getBurstTime())), 12) + "|\n");
        }
        System.out.print("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++\n");
        System.out.print("|" + indentation("Average", 16) + "|");
        System.out.print(indentation(String.valueOf(String.format("%.3f",avgWaitTime)), 11) + "|");
        System.out.print(indentation(String.valueOf(String.format("%.3f",avgExecutionTime)), 16) + "|");
        System.out.println(indentation("Total Burst", 14) + "|" + indentation(String.valueOf(String.format("%.3f",this.timeLine)), 12) +"|");
        System.out.print("---------------------------------------------------------------------------\n\n");
    }

    void preemptionTable (int i, int j, float time) {
        System.out.println("\n-> Context Change at time = " + String.format("%.3f",time));
        System.out.println("---------------------------------------");
        System.out.println("| Exits            | Enters           |");
        System.out.println("---------------------------------------");
        System.out.println("| " + indentation(processControlBlock[i].getName(), 17) + "| " + indentation(processControlBlock[j].getName(), 17) + "|");
        System.out.println("---------------------------------------\n");
    }
}
