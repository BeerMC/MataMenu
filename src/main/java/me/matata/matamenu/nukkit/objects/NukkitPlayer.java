package me.matata.matamenu.nukkit.objects;

import cn.nukkit.Player;
import me.matata.matamenu.general.objects.MenuPlayer;

/**
 * @author matata
 * @date 2020/3/15 13:09
 */
public class NukkitPlayer extends MenuPlayer {

    private final Player p;

    public NukkitPlayer(Player player){
        this.p = player;
    }

    public void sendMessage(String msg){
        p.sendMessage(msg);
    }

    public boolean hasPermission(String permission){
        return p.hasPermission(permission);
    }

}
