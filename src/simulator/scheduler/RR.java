package simulator.scheduler;

class RR extends Scheduler {
    private float quantum;

    RR (float quantum, PCB[] pcb) {
        super(pcb);
        this.quantum = quantum;
        System.out.println("Policy: Round Robin");
        System.out.println("Quantum: " + this.quantum);
    }

    private void setParameters () {
        for (int i = 0; i < nProcess; i++) {
            processControlBlock[i].setRemainingTime(processControlBlock[i].getBurstTime());
        }
    }

    private float timeLineCalc () {
        float cont = processControlBlock[0].getArrivalTime();
        for (int i = 0; i < this.nProcess; i++) {
            cont += processControlBlock[i].getBurstTime();
        }
        return cont;
    }

    void run () {
        sortPcb();
        setParameters();
        float actualTime = this.processControlBlock[0].getArrivalTime();
        this.timeLine = timeLineCalc();
        int before = 0;
        while (actualTime < this.timeLine) {
            for (int i = 0; i < this.nProcess; i++) {
                if (this.processControlBlock[i].getRemainingTime() > 0) {
                    if (i != before) {
                        preemptionTable(before, i, actualTime);
                    }
                    before = i;
                    for (int j = 0; j < this.nProcess; j++) {
                        if (i != j && this.processControlBlock[j].getRemainingTime() > 0) {
                            float temp = 0;
                            if (this.processControlBlock[i].getRemainingTime() >= this.quantum) {
                                temp = this.processControlBlock[j].getWaitTime() + this.quantum;
                            } else {
                                temp = this.processControlBlock[j].getWaitTime() + this.processControlBlock[i].getRemainingTime();
                            }
                            this.processControlBlock[j].setWaitTime(temp);
                        }
                    }
                    if (this.processControlBlock[i].getRemainingTime() < this.quantum) {
                        actualTime += this.processControlBlock[i].getRemainingTime();
                        float temp = this.processControlBlock[i].getRemainingTime();
                        temp += this.processControlBlock[i].getExecuted();
                        this.processControlBlock[i].setExecuted(temp);
                        this.processControlBlock[i].setRemainingTime(0);
                    } else {
                        float temp = this.quantum;
                        temp += this.processControlBlock[i].getExecuted();
                        this.processControlBlock[i].setExecuted(temp);
                        float temp2 = this.processControlBlock[i].getRemainingTime();
                        temp2 -= this.quantum;
                        this.processControlBlock[i].setRemainingTime(temp2);
                        actualTime += this.quantum;
                    }
                }
            }
        }
        for (int i = 1; i < nProcess; i++) {
            this.processControlBlock[i].changeWaitTime(normalization);
        }
        averageTime();
        resultTable();
    }
}