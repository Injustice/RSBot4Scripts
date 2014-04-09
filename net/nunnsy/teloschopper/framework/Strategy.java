package net.nunnsy.teloschopper.framework;

public abstract class Strategy implements Condition {
	
    private Condition condition;
    private Job task;
    private boolean running;

    public Strategy() {
        this.condition = this;
        if(this instanceof Job) {
            this.task = (Job) this;
        } else {
            throw new IllegalArgumentException("Does not implement task");
        }
    }

    public Strategy(final Condition condition, final Job task) {
        this.condition = condition;
        this.task = task;
    }

    public Condition getCondition() {
        return this.condition;
    }

    public Job getTask() {
        return this.task;
    }
    
    public boolean isRunning() {
    	return running;
    }
    
    public void started() {
    	running = true;
    }
    
    public void finished() {
    	running = false;
    }
}