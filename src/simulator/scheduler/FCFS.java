package simulator.scheduler;

class FCFS extends Scheduler {
    FCFS (PCB[] pcb) {
        super(pcb);
        System.out.println("Policy: First Come, First Served");
    }

    private void calcAllFcfs () {
        for (int i = 0; i < nProcess; i++) {
            if (i != 0) {
                if(processControlBlock[i].getArrivalTime() <= processControlBlock[i-1].getEndTime()) {
                    processControlBlock[i].setBeginTime(processControlBlock[i - 1].getEndTime());
                }else{
                    processControlBlock[i].setBeginTime(processControlBlock[i].getArrivalTime());
                }
            } else {
                processControlBlock[i].setBeginTime(0);
            }
            processControlBlock[i].setEndTime(processControlBlock[i].getBurstTime() + processControlBlock[i].getBeginTime());
        }
        timeLine = processControlBlock[nProcess - 1].getEndTime();
    }

    void run () {
        timeLineCalc();
        calcAllFcfs();
        for (int i = 0; i < nProcess; i++) {
            float temp = 0;
            processControlBlock[i].setExecuted(processControlBlock[i].getBurstTime());
            processControlBlock[i].setRemainingTime(0);
            processControlBlock[i].setWaitTime(processControlBlock[i].getBeginTime() - processControlBlock[i].getArrivalTime());
            if (i + 1 != nProcess) {
                preemptionTable(i, i + 1, processControlBlock[i].getEndTime());
            }
        }
        averageTime();
        resultTable();
    }
}
