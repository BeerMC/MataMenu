package me.matata.matamenu.nukkit.objects;

import cn.nukkit.scheduler.TaskHandler;
import me.matata.matamenu.general.objects.ITaskManager;
import me.matata.matamenu.nukkit.NukkitPlugin;

/**
 * @author Sauilitired, dordsor21
 */
@SuppressWarnings("deprecation")
public class NukkitTaskManager extends ITaskManager {

    private final NukkitPlugin plugin;

    public NukkitTaskManager(NukkitPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public int taskRepeat(Runnable r, int interval) {
        TaskHandler task = this.plugin.getServer().getScheduler().scheduleRepeatingTask(r, interval, false);
        return task.getTaskId();
    }

    @Override
    public int taskRepeatAsync(Runnable r, int interval) {
        TaskHandler task = this.plugin.getServer().getScheduler().scheduleRepeatingTask(r, interval, true);
        return task.getTaskId();
    }

    @Override
    public void taskAsync(Runnable r) {
        if (r == null) {
            return;
        }
        this.plugin.getServer().getScheduler().scheduleTask(r, true);
    }

    @Override
    public void task(Runnable r) {
        if (r == null) {
            return;
        }
        this.plugin.getServer().getScheduler().scheduleTask(r, false);
    }

    @Override
    public void taskLater(Runnable r, int delay) {
        if (r == null) {
            return;
        }
        this.plugin.getServer().getScheduler().scheduleDelayedTask(r, delay);
    }

    @Override
    public void taskLaterAsync(Runnable r, int delay) {
        this.plugin.getServer().getScheduler().scheduleDelayedTask(r, delay, true);
    }

    @Override
    public void cancelTask(int task) {
        if (task != -1) {
            this.plugin.getServer().getScheduler().cancelTask(task);
        }
    }
}
