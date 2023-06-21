package net.blumbo.blfscheduler;

public abstract class BlfRunnable implements BlfTask {

    protected boolean cancelled;
    protected long period;

    public void cancel() {
        this.cancelled = true;
    }

}
