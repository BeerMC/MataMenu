package me.matata.matamenu.general.player;

import me.matata.matamenu.general.objects.ICommandSender;

import java.util.UUID;

/**
 * @author matata
 * @date 2020/3/15 13:00
 */
public abstract class MenuPlayer <T> implements ICommandSender, ContainerUser, FormUser {

    public final T p;


    public MenuPlayer(T player){
        this.p = player;
    }


    public T getRealPlayer(){
        return p;
    }


    public abstract UUID getUniqueId();

}
