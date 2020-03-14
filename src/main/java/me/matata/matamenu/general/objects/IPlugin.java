package me.matata.matamenu.general.objects;

import java.io.File;

/**
 * @author matata
 * @date 2020/3/13 17:37
 */
public interface IPlugin {

    void log(String message);

    void error(String message);


    File getFolder();


    ITaskManager getTaskManager();

}
