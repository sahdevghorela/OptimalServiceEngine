package com.partnerone.optimalservicing.model;

public class OptimalWorkForce implements Comparable<OptimalWorkForce> {
    private WorkForce workForce = new WorkForce();
    private int overCapacity;

    public OptimalWorkForce(int seniors, int juniors, int overCapacity){
        workForce.setSeniors(seniors);
        workForce.setJuniors(juniors);
        this.overCapacity = overCapacity;
    }

    public WorkForce getWorkForce() {
        return workForce;
    }

    public void setWorkForce(WorkForce workForce) {
        this.workForce = workForce;
    }

    public int getOverCapacity() {
        return overCapacity;
    }

    public void setOverCapacity(int overCapacity) {
        this.overCapacity = overCapacity;
    }

    public void addSeniors(int seniors) {
        workForce.setSeniors(workForce.getSeniors()+seniors);
    }

    @Override
    public int compareTo(OptimalWorkForce o) {
        return Integer.compare(this.getOverCapacity(),o.getOverCapacity());
    }
}
