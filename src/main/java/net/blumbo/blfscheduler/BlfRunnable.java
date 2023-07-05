package net.blumbo.blfscheduler;

public abstract class BlfRunnable implements BlfTask {

    boolean isCancelled;
    boolean isRepeating;
    long period;

    /**
     * Stops a repeating task.
     */
    public void cancel() {
        this.isCancelled = true;
    }

}
