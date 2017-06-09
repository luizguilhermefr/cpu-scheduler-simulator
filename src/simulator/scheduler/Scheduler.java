package simulator.scheduler;

class Scheduler {

    PCB[] processControlBlock;
    int nProcess;
    float timeLine = 0;
    private float avgExecutionTime, avgWaitTime;
    float normalization;


    Scheduler(PCB[] pcb) {
        this.processControlBlock = pcb;
        this.nProcess = pcb.length;
        normalization();
        System.out.println("Initialized scheduler with "+ this.nProcess+" processes.");

    }

    private void normalization() { // This function serve to set begin just only for the first process
        normalization = processControlBlock[0].getArrivalTime();
    }

    void sortPcb() {
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

    void averageTime() {
        for (int i = 0; i < nProcess; i++) {
            avgExecutionTime += processControlBlock[i].getBurstTime() + processControlBlock[i].getWaitTime();
            avgWaitTime += processControlBlock[i].getWaitTime();
        }
        avgWaitTime /= nProcess;
        avgExecutionTime /= nProcess;
    }

    private String identation(String text, int limit) {
        int qtdSpace = 0;
        if (limit >= text.length()) {
            qtdSpace = limit - text.length();
            for (int i = 0; i < qtdSpace; i++)
                text += " ";
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

    void resultTable() {
        System.out.print("---------------------------------------------------------------------------\n" +
                "| Process        | Wait Time | Execution Time | Arrival Time | Burst Time |\n" +
                "---------------------------------------------------------------------------\n");
        for (int i = 0; i < nProcess; i++) {
            System.out.print("|" + identation(processControlBlock[i].getName(), 16) + "|");
            System.out.print(identation(String.valueOf(processControlBlock[i].getWaitTime()), 11) + "|");
            System.out.print(identation(String.valueOf(processControlBlock[i].getWaitTime() + processControlBlock[i].getExecuted()), 16) + "|");
            System.out.print(identation(String.valueOf(processControlBlock[i].getArrivalTime()), 14) + "|");
            System.out.print(identation(String.valueOf(processControlBlock[i].getBurstTime()), 12) + "|\n");
        }
        System.out.print("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++\n");
        System.out.print("|" + identation("Average", 16) + "|");
        System.out.print(identation(String.valueOf(avgWaitTime), 11) + "|");
        System.out.print(identation(String.valueOf(avgExecutionTime), 16) + "|");
        System.out.println(identation(" -", 27) + "|");
        System.out.print("---------------------------------------------------------------------------\n\n");
    }

    void preemptionTable(int i, int j, float time) {
        System.out.print("-- Preemption -- Current Time = " + time + " --\n");
        System.out.print("| Exits            | Enters           |\n");
        System.out.print("---------------------------------------\n");
        System.out.print("| " + identation(processControlBlock[i].getName(), 17) + "| " + identation(processControlBlock[j].getName(), 17) + "|\n");
        System.out.print("---------------------------------------\n\n\n");
    }
}
