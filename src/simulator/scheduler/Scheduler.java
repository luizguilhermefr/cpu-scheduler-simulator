package simulator.scheduler;

class Scheduler {
    PCB[] processControlBlock;
    int nProcess;
    double timeLine = 0;
    private float avgExecutionTime, avgWaitTime;

    Scheduler (PCB[] pcb) {
        this.processControlBlock = pcb;
        this.nProcess = pcb.length;
        System.out.println("Initialized scheduler with " + this.nProcess + " processes.");

    }

    private float totalBurstTime () {
        float totalexecution = 0;
        for (int i = 0; i < this.nProcess; i++) {
            totalexecution += this.processControlBlock[i].getBurstTime();
        }
        return totalexecution;
    }


    void timeLineCalc () {
        this.processControlBlock[0].setArrivalTime(0);
        for (int i = 1; i < this.nProcess; i++) {
            float cont = this.processControlBlock[i - 1].getArrivalTime();
            cont += this.processControlBlock[i].getArrivalTime();
            this.processControlBlock[i].setArrivalTime(cont);
        }
    }

    boolean remainTime () {

        for (int i = 0; i < this.nProcess; i++) {
            if (this.processControlBlock[i].getRemainingTime() > 0) {
                return true;
            }
        }
        return false;
    }

    void averageTime () {
        for (int i = 0; i < this.nProcess; i++) {
            this.avgExecutionTime += this.processControlBlock[i].getBurstTime() + this.processControlBlock[i].getWaitTime();
            this.avgWaitTime += this.processControlBlock[i].getWaitTime();
        }
        this.avgWaitTime /= this.nProcess;
        this.avgExecutionTime /= this.nProcess;
    }

    private String indentation (String text, int limit) {
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
        for (int i = 0; i < this.nProcess; i++) {
            float temp = this.processControlBlock[i].getWaitTime();
            if (temp >= this.processControlBlock[i].getArrivalTime()) {
                temp -= this.processControlBlock[i].getArrivalTime();
            }

            this.processControlBlock[i].setWaitTime(temp);
        }

        System.out.print("---------------------------------------------------------------------------\n" +
                "| Process        | Wait Time | Execution Time | Arrival Time | Burst Time |\n" +
                "---------------------------------------------------------------------------\n");
        for (int i = 0; i < nProcess; i++) {
            System.out.print("|" + indentation(this.processControlBlock[i].getName(), 16) + "|");
            System.out.print(indentation(String.valueOf(String.format("%.3f", this.processControlBlock[i].getWaitTime())), 11) + "|");
            System.out.print(indentation(String.valueOf(String.format("%.3f", this.processControlBlock[i].getWaitTime() + this.processControlBlock[i].getExecuted())), 16) + "|");
            System.out.print(indentation(String.valueOf(String.format("%.3f", this.processControlBlock[i].getArrivalTime())), 14) + "|");
            System.out.print(indentation(String.valueOf(String.format("%.3f", this.processControlBlock[i].getBurstTime())), 12) + "|\n");
        }
        System.out.print("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++\n");
        System.out.print("|" + indentation("Average", 16) + "|");
        System.out.print(indentation(String.valueOf(String.format("%.3f", this.avgWaitTime)), 11) + "|");
        System.out.print(indentation(String.valueOf(String.format("%.3f", this.avgExecutionTime)), 16) + "|");
        System.out.println(indentation("Total Burst", 14) + "|" + indentation(String.valueOf(String.format("%.3f", totalBurstTime())), 12) + "|");
        System.out.print("---------------------------------------------------------------------------\n\n");
    }

    void preemptionTable (int i, int j, float time) {
        System.out.println("\n-> Context Change at time = " + String.format("%.3f", time));
        System.out.println("---------------------------------------");
        System.out.println("| Exits            | Enters           |");
        System.out.println("---------------------------------------");
        System.out.println("| " + indentation(this.processControlBlock[i].getName(), 17) + "| " + indentation(this.processControlBlock[j].getName(), 17) + "|");
        System.out.println("---------------------------------------\n");
    }
}
