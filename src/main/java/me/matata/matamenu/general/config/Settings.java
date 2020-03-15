package me.matata.matamenu.general.config;

import me.matata.matamenu.general.MataMenu;

import java.io.File;

/**
 * @author matata
 * @date 2020/3/14 11:27
 */
public class Settings extends IConfig{


    @Comment("Unconfigurable")
    @Final public static String VERSION = MataMenu.version;
    @Final public static String WIKI = "https://github.com/BeerMC/MataMenu/wiki";
    @Final public static String AUTHOR = "马踏踏";

    @Comment("Configurable")
    public static boolean DEBUG = false;
    public static String LOCALE = "zh-CN";
    public static String PREFIX = "&7[&bMata&3Menu&7]&f";
    public static char COLOR_ESCAPE = '\u00a7';

    public static void save(File file) {
        save(file, Settings.class);
    }

    public static void load(File file) {
        load(file, Settings.class);
    }

    public static void convertOldSettings(File file){

    }


    @Comment("Where plugin will load configurations")
    public static final class Paths {
        public static String MENUS = "menus";
        public static String LOCALES = "locales";
    }


}
