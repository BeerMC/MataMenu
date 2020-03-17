package me.matata.matamenu.nukkit.objects;

import cn.nukkit.Player;
import me.matata.matamenu.general.player.MenuPlayer;

import java.util.UUID;

/**
 * @author matata
 * @date 2020/3/15 13:09
 */
public class NukkitPlayer extends MenuPlayer<Player> {

    public NukkitPlayer(Player player){
        super(player);
    }


    public void sendMessage(String msg){
        this.p.sendMessage(msg);
    }


    public boolean hasPermission(String permission){
        return this.p.hasPermission(permission);
    }


    public String getName() {
        return p.getName();
    }


    public UUID getUniqueId() {
        return p.getUniqueId();
    }

}
