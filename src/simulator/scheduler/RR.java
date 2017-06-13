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
        for (int i = 0; i < this.nProcess; i++) {
            this.processControlBlock[i].setRemainingTime(this.processControlBlock[i].getBurstTime());
        }
    }

    void run () {
        timeLineCalc();
        setParameters();
        float actualTime = 0;
        int before = 0;
        boolean verify = true;
        while (remainTime()) {
            for (int i = 0; i < this.nProcess; i++) {
                if (this.processControlBlock[i].getArrivalTime() <= actualTime) {
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
                } else {
                    for (int j = 0; j < i; j++) {
                        if (this.processControlBlock[j].getRemainingTime() > 0) {
                            break;
                        } else if (verify) {
                            actualTime = this.processControlBlock[i].getArrivalTime();
                            this.processControlBlock[i].setWaitTime(0);
                            verify = false;
                            break;
                        } else {
                            actualTime = this.processControlBlock[i].getArrivalTime();
                            this.processControlBlock[i].setWaitTime((this.processControlBlock[i].getArrivalTime() - this.processControlBlock[j].getArrivalTime()));
                        }
                    }
                }
            }
        }
        averageTime();
        resultTable();
    }
}
