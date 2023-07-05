package net.blumbo.blfscheduler;

import com.mojang.logging.LogUtils;
import net.fabricmc.api.ModInitializer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class BlfScheduler implements ModInitializer {

    private static long ticks = 0L;
    private static final HashMap<Long, List<BlfRunnable>> runnableMap = new HashMap<>();
    private static final Logger LOGGER = LogUtils.getLogger();

    @Override
    public void onInitialize() {}

    /**
     * Runs a task after specified amount of ticks.
     * @param delay Number of ticks before the task is run.
     * @param runnable Task being run.
     * @return Runnable specified as parameter.
     */
    public static BlfRunnable delay(long delay, @NotNull BlfRunnable runnable) {
        delay = properDelayCheck(delay);

        runnable.isCancelled = false;
        runnable.isRepeating = false;
        runnable.period = Integer.MAX_VALUE;

        long time = ticks + delay;
        addTask(time, runnable);

        return runnable;
    }

    /**
     * Repeats a task every specified amount of ticks.
     * @param delay Number of ticks before the first task is run.
     * @param period Number of ticks between following runs.
     * @param runnable Task being repeated.
     * @return Runnable specified as parameter.
     */
    public static BlfRunnable repeat(long delay, long period, @NotNull BlfRunnable runnable) {
        delay = properDelayCheck(delay);

        runnable.isCancelled = false;
        runnable.isRepeating = true;
        runnable.period = period;

        long time = ticks + delay;
        addTask(time, runnable);

        return runnable;
    }

    protected static void tick() {
        List<BlfRunnable> currentTasks = runnableMap.remove(ticks);
        if (currentTasks != null) {
            for (BlfRunnable runnable : currentTasks) {
                runCurrentTimeTask(runnable);
            }
        }
        ticks++;
    }

    private static void runCurrentTimeTask(BlfRunnable runnable) {
        if (runnable.isCancelled) return;
        runnable.run();
        if (runnable.isCancelled || !runnable.isRepeating) return;

        Long period = properPeriodCheck(runnable.period);
        if (period == null) return;
        runnable.period = period;

        long time = ticks + runnable.period;
        addTask(time, runnable);
    }

    private static void addTask(long time, BlfRunnable runnable) {
        List<BlfRunnable> runnables = runnableMap.get(time);

        if (runnables == null) {
            runnables = new ArrayList<>(1);
            runnables.add(runnable);
            runnableMap.put(time, runnables);
        } else {
            runnables.add(runnable);
        }
    }

    private static long properDelayCheck(long delay) {
        if (delay < 0) {
            delay = 0;
            LOGGER.warn("Tried to schedule task with negative delay. Task delay set to 0.");
        }
        return delay;
    }

    @Nullable
    private static Long properPeriodCheck(@NotNull Long period) {
        if (period < 0) {
            period = null;
            LOGGER.warn("Tried to repeat task with negative period. Cancelled task.");
        } else if (period == 0) {
            period = 1L;
            LOGGER.warn("Tried to repeat task with period 0. Task period set to 1.");
        }
        return period;
    }

}
