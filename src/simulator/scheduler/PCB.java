package simulator.scheduler;

public class PCB {
    private String name;
    private float burstTime;
    private float arrivalTime;
    private float remainingTime;
    private float executed;
    private float beginTime;
    private float endTime;
    private float waitTime;

    float getWaitTime () {
        return this.waitTime;
    }

    void setWaitTime (float waitTime) {
        this.waitTime = waitTime;
    }

    PCB (String name, float burstTime, float arrivalTime) {
        this.name = name;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
    }

    String getName () {
        return this.name;
    }

    float getBurstTime () {
        return this.burstTime;
    }

    protected void setBurstTime (float burstTime) {
        this.burstTime = burstTime;
    }

    float getBeginTime () {
        return this.beginTime;
    }

    void setBeginTime (float beginTime) {
        this.beginTime = beginTime;
    }

    float getEndTime () {
        return this.endTime;
    }

    void setEndTime (float endTime) {
        this.endTime = endTime;
    }

    float getRemainingTime () {
        return this.remainingTime;
    }

    void setRemainingTime (float remainingTime) {
        this.remainingTime = remainingTime;
    }

    float getExecuted () {
        return this.executed;
    }

    void setExecuted (float executed) {
        this.executed = executed;
    }

    float getArrivalTime () {
        return this.arrivalTime;
    }

    void setArrivalTime (float arrivalTime) {
        this.arrivalTime = arrivalTime;
    }
}
