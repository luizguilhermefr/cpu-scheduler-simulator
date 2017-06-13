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

    void run () {
        timeLineCalc();
        setParameters();
        float actualTime = 0;
        int before = 0;
        boolean verify = true;
        while (remainTime()) {
            for (int i = 0; i < nProcess; i++) {
                if(processControlBlock[i].getArrivalTime() <= actualTime) {
                    if (processControlBlock[i].getRemainingTime() > 0) {
                        if (i != before) {
                            preemptionTable(before, i, actualTime);
                        }
                        before = i;
                        for (int j = 0; j < nProcess; j++) {
                            if (i != j && processControlBlock[j].getRemainingTime() > 0) {
                                float temp = 0;
                                if (processControlBlock[i].getRemainingTime() >= this.quantum) {
                                    temp = processControlBlock[j].getWaitTime() + this.quantum;
                                } else {
                                    temp = processControlBlock[j].getWaitTime() + processControlBlock[i].getRemainingTime();
                                }
                                processControlBlock[j].setWaitTime(temp);
                            }
                        }
                        if (processControlBlock[i].getRemainingTime() < this.quantum) {
                            actualTime += processControlBlock[i].getRemainingTime();
                            float temp = processControlBlock[i].getRemainingTime();
                            temp += processControlBlock[i].getExecuted();
                            processControlBlock[i].setExecuted(temp);
                            processControlBlock[i].setRemainingTime(0);
                        } else {
                            float temp = this.quantum;
                            temp += processControlBlock[i].getExecuted();
                            processControlBlock[i].setExecuted(temp);
                            float temp2 = processControlBlock[i].getRemainingTime();
                            temp2 -= this.quantum;
                            processControlBlock[i].setRemainingTime(temp2);
                            actualTime += this.quantum;
                        }
                    }
                }else{
                    for (int j = 0; j < i; j++){
                        if(processControlBlock[j].getRemainingTime() > 0) {
                            break;
                        }else if(verify){
                            actualTime = processControlBlock[i].getArrivalTime();
                            processControlBlock[i].setWaitTime(0);
                            verify = false;
                            break;
                        }else{
                            actualTime = processControlBlock[i].getArrivalTime();
                            processControlBlock[i].setWaitTime((processControlBlock[i].getArrivalTime() - processControlBlock[j].getArrivalTime() ));
                            System.out.println("AQUI: "+ processControlBlock[i].getWaitTime());
                        }
                    }
                }
            }
        }
        averageTime();
        resultTable();
    }
}