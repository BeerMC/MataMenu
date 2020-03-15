package me.matata.matamenu.general.objects;

import java.io.File;

/**
 * @author matata
 * @date 2020/3/13 17:37
 */
public interface IPlugin {

    /**
     * Print log.
     */
    void log(String message);

    /**
     * Print error.
     */
    void error(String message);

    /**
     * Get directory of plugin data.
     *
     * @return File
     */
    File getFolder();

    /**
     * Get TaskManager.
     *
     * @return TaskManager
     */
    ITaskManager getTaskManager();

    /**
     * Wraps a player into a MenuPlayer object.
     *
     * @param player The player to convert to a MenuPlayer
     * @return A MenuPlayer
     */
    MenuPlayer wrapPlayer(Object player);


}
