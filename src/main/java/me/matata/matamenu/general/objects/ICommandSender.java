package me.matata.matamenu.general.objects;

/**
 * @author matata
 * @date 2020/3/15 13:11
 */
public interface ICommandSender {

    /**
     * Send the player a message.
     *
     * @param message the message to send
     */
    void sendMessage(String message);

    /**
     * Check the player's permissions.
     *
     * @param permission the name of the permission
     */
    boolean hasPermission(String permission);

}
