package simulator.scheduler;

public class Scheduler {

    private PCB[] processControlBlock;
    private int nProcess;
    float timeLine = 0;

    Scheduler(PCB[] pcb){
        processControlBlock = pcb;
    }

    /*void setPcb(int q, float arrivalTime, float waitTime, float remainingTime, float executed, double averageExecutionTime, double averageWaitTime, float beginTime, float endTime){
        PCB[] temp  = processControlBlock;
        nProcess++;
        processControlBlock = new PCB[nProcess];
        System.arraycopy(temp, 0, processControlBlock,0,temp.length);
        processControlBlock[nProcess -1] = new PCB(q, arrivalTime, waitTime, remainingTime, executed, averageExecutionTime, averageWaitTime, beginTime, endTime);
    }*/

    private void sortPcb(){
        for (int i = 0; i < nProcess; i ++){
            for(int j = 1; j < nProcess; j++ ){
                if (processControlBlock[j].getArrivalTime() < processControlBlock[j-1].getArrivalTime()){
                    PCB temptemp = processControlBlock[j-1];
                    processControlBlock[j-1] = processControlBlock[j];
                    processControlBlock[j] = temptemp;
                }
            }
        }
    }

    private void calcAll() {
        for (int i = 0; i < nProcess; i++) {
            processControlBlock[i].setBeginTime(timeLine);
            timeLine += processControlBlock[i].getExecutionTime();
            processControlBlock[i].setEndTime(timeLine);
        }
    }

    void firstComeFirstServed(){
        sortPcb();
        calcAll();
        for (int i = 0; i < nProcess; i++) {
            processControlBlock[i].setExecuted(processControlBlock[i].getExecutionTime());
            processControlBlock[i].setRemainingTime(0);
            processControlBlock[i].setAverageExecutionTime(processControlBlock[i].getExecutionTime());
            System.out.println("Executado: " + processControlBlock[i].getExecuted() + " Restante: " + processControlBlock[i].getRemainingTime() + " Tempo total:" + timeLine + " Begin: " + processControlBlock[i].getBeginTime() + " End: " + processControlBlock[i].getEndTime());
        }
    }

    public void roundRobin(){

    }
}

