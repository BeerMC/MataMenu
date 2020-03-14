package me.matata.matamenu.general.config;

import me.matata.matamenu.general.MataMenu;

import java.io.File;

/**
 * @author matata
 * @date 2020/3/14 11:27
 */
public class Settings extends IConfig{

    @Final public static String VERSION = MataMenu.version;
    @Final public static String WIKI = "https://github.com/BeerMC/MataMenu/wiki";
    public static boolean DEBUG = false;
    public static String LOCALE = "zh-CN";

    public static void save(File file) {
        save(file, Settings.class);
    }

    public static void load(File file) {
        load(file, Settings.class);
    }

}
