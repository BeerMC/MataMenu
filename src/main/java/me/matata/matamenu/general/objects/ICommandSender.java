package me.matata.matamenu.general.objects;

/**
 * @author matata
 * @date 2020/3/15 13:11
 */
public interface ICommandSender {


    void sendMessage(String message);


    boolean hasPermission(String permission);


    String getName();

}
