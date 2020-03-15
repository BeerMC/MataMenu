package me.matata.matamenu.nukkit;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.plugin.PluginBase;
import me.matata.matamenu.general.MataMenu;
import me.matata.matamenu.general.objects.IPlugin;
import me.matata.matamenu.general.objects.ITaskManager;
import me.matata.matamenu.general.objects.MenuPlayer;
import me.matata.matamenu.nukkit.objects.NukkitPlayer;
import me.matata.matamenu.nukkit.objects.NukkitTaskManager;
import me.matata.matamenu.nukkit.utils.MetricsLite;

import java.io.File;
import java.util.HashMap;

/**
 * @author matata
 * @date 2020/3/13 15:22
 */
public class NukkitPlugin extends PluginBase implements IPlugin {

    private HashMap<String, MenuPlayer> cachedPlayers = new HashMap<>();

    @Override
    public void onEnable() {
        new MataMenu(this);
        new MetricsLite(this, 6761);
    }

    @Override
    public void onDisable(){
        MataMenu.getInstance().unload();
    }

    public void log(String message){
        Server.getInstance().getLogger().info(message);
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

    @Override
    public MenuPlayer wrapPlayer(Object obj) {
        if(obj instanceof Player){
            Player player = (Player) obj;
            String name = player.getName();
            if(cachedPlayers.containsKey(name)){
                return cachedPlayers.get(name);
            }else{
                cachedPlayers.put(name, new NukkitPlayer(player));
            }
        }
        return null;
    }

}
