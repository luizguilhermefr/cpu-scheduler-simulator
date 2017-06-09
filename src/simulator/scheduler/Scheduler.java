package simulator.scheduler;

public class Scheduler {

    protected PCB[] processControlBlock;
    protected int nProcess;
    protected float timeLine = 0;
    private float avgExecutionTime, avgWaitTime;
    protected float normalization;


    public Scheduler(PCB[] pcb){
        this.processControlBlock = pcb;
        this.nProcess = pcb.length;
        normalization();
    }

    public void normalization(){ // This function serve to set begin just only for the first process
        normalization = processControlBlock[0].getArrivalTime();
    }

    public void sortPcb(){
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

    public void averageTime(){
        for(int i = 0; i < nProcess; i++) {
            avgExecutionTime += processControlBlock[i].getBurstTime() + processControlBlock[i].getWaitTime();
            avgWaitTime += processControlBlock[i].getWaitTime();
        }
        avgWaitTime /= nProcess;
        avgExecutionTime /= nProcess;
    }

    public String identation(String text, int limit){
        int qtdSpace = 0;
        if( limit >= text.length()) {
            qtdSpace = limit - text.length();
            for (int i = 0; i < qtdSpace; i++)
                text +=" ";
            return text;
        }else{
            String temp = "";
            for ( int i = 0; i < limit - 3; i++){
                temp += text.charAt(i);
            }
            temp += "...";
            return temp;
        }
    }

    public void resultTable(){
        System.out.print("-----------------------------------------------\n" +
                         "| Process        | Wait Time | Execution Time |\n"+
                         "-----------------------------------------------\n");
        for (int i = 0; i < nProcess; i++) {
            System.out.print("|" + identation(processControlBlock[i].getName(), 16) + "|" );
            System.out.print(identation(String.valueOf(processControlBlock[i].getWaitTime()), 11) + "|" );
            System.out.print(identation(String.valueOf(processControlBlock[i].getWaitTime() + processControlBlock[i].getExecuted()), 16) + "|\n" );
        }
        System.out.print("+++++++++++++++++++++++++++++++++++++++++++++++\n");
        System.out.print("|"+ identation("Average", 16) + "|");
        System.out.print(identation(String.valueOf(avgWaitTime), 11) + "|" );
        System.out.print(identation(String.valueOf(avgExecutionTime), 16) + "|\n" );
        System.out.print("-----------------------------------------------\n");
    }
    public void preemptionTable( int i, int j, float time){
        System.out.print("-- Preemption -- Current Time = "+ time+" --\n");
        System.out.print("| Exits            | Enters           |\n");
        System.out.print("---------------------------------------\n");
        System.out.print("| "+identation(processControlBlock[i].getName(), 17)+"| "+identation(processControlBlock[j].getName(), 17)+"|\n");
        System.out.print("---------------------------------------\n\n\n");
    }

//    -- Preemption -- Current Time = 2.2 ---
//    | Exits            | Enters           |
//    ---------------------------------------
//    | My Browser       | My Text Editor   |
//    ---------------------------------------
}
