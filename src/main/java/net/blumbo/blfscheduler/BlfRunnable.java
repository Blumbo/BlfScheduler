package net.blumbo.blfscheduler;

public abstract class BlfRunnable implements BlfTask {

    protected boolean isCancelled;
    protected boolean isRepeating;
    protected long period;

    /**
     * Stops a repeating task.
     */
    public void cancel() {
        this.isCancelled = true;
    }

}
