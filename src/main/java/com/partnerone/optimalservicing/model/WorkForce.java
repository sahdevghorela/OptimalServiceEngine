package com.partnerone.optimalservicing.model;

public class WorkForce {
    private int seniors;
    private int juniors;

    public int getSeniors() {
        return seniors;
    }

    public void setSeniors(int seniors) {
        this.seniors = seniors;
    }

    public int getJuniors() {
        return juniors;
    }

    public void setJuniors(int juniors) {
        this.juniors = juniors;
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
