package com.partnerone.optimalservicing.model;

public class WorkForce{
    private int overCapacity;
    private int seniors;
    private int juniors;

    public WorkForce(int seniors, int juniors, int overCapacity){
        this.seniors = seniors;
        this.juniors = juniors;
        this.overCapacity = overCapacity;
    }

    public int getOverCapacity() {
        return overCapacity;
    }

    public void addOneSenior() {
        this.seniors = this.seniors + 1;
    }

    public int getSeniors() {
        return seniors;
    }

    public int getJuniors() {
        return juniors;
    }

    @Override
    public String
    toString() {
        return "{" +
                "seniors=" + seniors +
                ", juniors=" + juniors +
                '}';
    }
}
