package me.matata.matamenu.general.objects;

/**
 * @author Sauilitired, dordsor21
 */
public abstract class ITaskManager {

    public static ITaskManager impl;

    public static int runTaskRepeat(Runnable runnable, int interval) {
        if (runnable != null) {
            if (impl == null) {
                throw new IllegalArgumentException("disabled");
            }
            return impl.taskRepeat(runnable, interval);
        }
        return -1;
    }

    public static int runTaskRepeatAsync(Runnable runnable, int interval) {
        if (runnable != null) {
            if (impl == null) {
                throw new IllegalArgumentException("disabled");
            }
            return impl.taskRepeatAsync(runnable, interval);
        }
        return -1;
    }

    public static void runTaskAsync(Runnable runnable) {
        if (runnable != null) {
            if (impl == null) {
                runnable.run();
                return;
            }
            impl.taskAsync(runnable);
        }
    }

    public static void runTask(Runnable runnable) {
        if (runnable != null) {
            if (impl == null) {
                runnable.run();
                return;
            }
            impl.task(runnable);
        }
    }

    public static void runTaskLater(Runnable runnable, int delay) {
        if (runnable != null) {
            if (impl == null) {
                runnable.run();
                return;
            }
            impl.taskLater(runnable, delay);
        }
    }

    public static void runTaskLaterAsync(Runnable runnable, int delay) {
        if (runnable != null) {
            if (impl == null) {
                runnable.run();
                return;
            }
            impl.taskLaterAsync(runnable, delay);
        }
    }

    public abstract int taskRepeat(Runnable runnable, int interval);

    public abstract int taskRepeatAsync(Runnable runnable, int interval);

    public abstract void taskAsync(Runnable runnable);

    public abstract void task(Runnable runnable);

    public abstract void taskLater(Runnable runnable, int delay);

    public abstract void taskLaterAsync(Runnable runnable, int delay);

    public abstract void cancelTask(int task);
}
