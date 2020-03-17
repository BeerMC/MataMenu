package me.matata.matamenu.general.objects;

/**
 * @author matata
 * @date 2020/3/13 15:37
 */
public abstract class ITaskManager {

    public static ITaskManager impl;

    public abstract int taskRepeat(Runnable runnable, int interval);

    public abstract int taskRepeatAsync(Runnable runnable, int interval);

    public abstract void taskAsync(Runnable runnable);

    public abstract void task(Runnable runnable);

    public abstract void taskDelay(Runnable runnable, int delay);

    public abstract void taskDelayAsync(Runnable runnable, int delay);

    public abstract void cancelTask(int task);
}
