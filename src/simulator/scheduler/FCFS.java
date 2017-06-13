package simulator.scheduler;

class FCFS extends Scheduler {
    FCFS (PCB[] pcb) {
        super(pcb);
        System.out.println("Policy: First Come, First Served");
    }

    private void calcAllFcfs () {
        for (int i = 0; i < nProcess; i++) {
            if (i != 0) {
                if (this.processControlBlock[i].getArrivalTime() <= this.processControlBlock[i - 1].getEndTime()) {
                    this.processControlBlock[i].setBeginTime(this.processControlBlock[i - 1].getEndTime());
                } else {
                    this.processControlBlock[i].setBeginTime(this.processControlBlock[i].getArrivalTime());
                }
            } else {
                this.processControlBlock[i].setBeginTime(0);
            }
            this.processControlBlock[i].setEndTime(this.processControlBlock[i].getBurstTime() + this.processControlBlock[i].getBeginTime());
        }
        this.timeLine = this.processControlBlock[this.nProcess - 1].getEndTime();
    }

    void run () {
        timeLineCalc();
        calcAllFcfs();
        for (int i = 0; i < this.nProcess; i++) {
            float temp = 0;
            this.processControlBlock[i].setExecuted(this.processControlBlock[i].getBurstTime());
            this.processControlBlock[i].setRemainingTime(0);
            this.processControlBlock[i].setWaitTime(this.processControlBlock[i].getBeginTime() - this.processControlBlock[i].getArrivalTime());
            if (i + 1 != nProcess) {
                preemptionTable(i, i + 1, this.processControlBlock[i].getEndTime());
            }
        }
        averageTime();
        resultTable();
    }
}
