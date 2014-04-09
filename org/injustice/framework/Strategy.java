package org.injustice.framework;

/**
 * Created with IntelliJ IDEA.
 * User: Injustice
 * Date: 06/05/13
 * Time: 09:47
 * To change this template use File | Settings | File Templates.
 */
public abstract class Strategy implements Condition {
    private Condition condition;
    private Job job;
    private boolean running;

    public Strategy() {
        this.condition = this;
        if (this instanceof Job) {
            this.job = (Job) this;
        } else {
            throw new IllegalArgumentException("Doesn't implement Job");
        }
    }

    public Strategy(final Condition cond, final Job job) {
        this.condition = cond;
        this.job = job;
    }

    public Condition getCondition() {
        return this.condition;
    }

    public Job getJob() {
        return this.job;
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
