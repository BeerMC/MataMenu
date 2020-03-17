package me.matata.matamenu.general.objects;

import me.matata.matamenu.general.player.MenuPlayer;

import java.io.File;

/**
 * @author matata
 * @date 2020/3/13 17:37
 */
public interface IPlugin<T> {

    void log(String message);


    void error(String message);


    File getFolder();


    ITaskManager getTaskManager();


    T getServer();


    MenuPlayer<?> wrapPlayer(Object p);


}
