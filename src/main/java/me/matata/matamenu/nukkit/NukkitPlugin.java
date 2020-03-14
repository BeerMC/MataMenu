package me.matata.matamenu.nukkit;

import cn.nukkit.Server;
import cn.nukkit.plugin.PluginBase;
import cn.nukkit.utils.TextFormat;
import me.matata.matamenu.general.MataMenu;
import me.matata.matamenu.general.objects.IPlugin;
import me.matata.matamenu.general.objects.ITaskManager;
import me.matata.matamenu.general.utils.StringMan;
import me.matata.matamenu.nukkit.objects.NukkitTaskManager;

import java.io.File;

/**
 * @author matata
 * @date 2020/3/13 15:22
 */
public class NukkitPlugin extends PluginBase implements IPlugin {

    @Override
    public void onEnable() {
        new MataMenu(this);
    }

    @Override
    public void onDisable(){

    }

    public void log(String message){
        Server.getInstance().getLogger().info(StringMan.replaceAll(message, "&", TextFormat.ESCAPE));
    }

    public void error(String message){
        Server.getInstance().getLogger().error(message);
    }

    public File getFolder(){
        return getDataFolder();
    }

    public ITaskManager getTaskManager(){
        return new NukkitTaskManager(this);
    }
}
