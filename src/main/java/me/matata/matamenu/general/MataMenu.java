package me.matata.matamenu.general;

import lombok.Getter;
import me.matata.matamenu.general.config.Locale;
import me.matata.matamenu.general.config.Settings;
import me.matata.matamenu.general.config.configuration.file.YamlConfiguration;
import me.matata.matamenu.general.objects.IPlugin;
import me.matata.matamenu.general.objects.ITaskManager;
import me.matata.matamenu.general.utils.FileUtil;
import me.matata.matamenu.general.utils.Strings;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * @author matata
 * @date 2020/3/13 15:34
 */
public class MataMenu{

    private static MataMenu instance;

    public final IPlugin<?> impl;

    public File translationFile;

    @Getter
    private File jarFile;

    public static final String version = "1.0";


    public static MataMenu getInstance(){
        return instance;
    }


    public static void log(Object message) {
        MataMenu.getInstance().impl.log(Strings.getString(message));
    }


    public static void debug(Object message) {
        if(Settings.DEBUG){
            MataMenu.getInstance().impl.log(Strings.getString(message));
        }
    }


    public MataMenu(IPlugin<?> plugin){
        MataMenu.instance = this;
        this.impl = plugin;
        try {
            URL url = MataMenu.class.getProtectionDomain().getCodeSource().getLocation();
            this.jarFile = new File(
                    new URL(url.toURI().toString().split("!")[0].replaceAll("jar:file", "file"))
                            .toURI().getPath());
        } catch (MalformedURLException | URISyntaxException | SecurityException e) {
            e.printStackTrace();
            this.jarFile = new File(this.impl.getFolder().getParentFile(), "MataMenu.jar");
            if (!this.jarFile.exists()) {
                this.jarFile = new File(this.impl.getFolder().getParentFile(),
                        "MataMenu_v" + version + ".jar");
            }
        }
        load();
    }


    public void load(){
        loadTaskManager();
        loadSettings();
        loadLocale();
        loadMenus();
    }


    public void loadTaskManager(){
        ITaskManager.impl = impl.getTaskManager();
    }


    private void loadSettings() {
        File folder = new File(this.impl.getFolder(), "config");
        if (!folder.exists() && !folder.mkdirs()) {
            MataMenu.log("&cFailed to create config folder. Please create it manually.");
        }
        try {
            File configFile = new File(folder, "settings.yml");
            if (!configFile.exists() && !configFile.createNewFile()) {
                MataMenu.log("&cFailed to create the settings.yml, please create it manually.");
            }
            YamlConfiguration settings_config = YamlConfiguration.loadConfiguration(configFile);
            String lastVersionString = settings_config.getString("VERSION");
            if(lastVersionString == null || !lastVersionString.equals(version)){
                Settings.convertOldSettings(configFile);
            }
            Settings.load(configFile);
            Settings.save(configFile);
        } catch (IOException ignored) {
            MataMenu.log("&cFailed to save settings.yml");
        }
    }


    private void loadLocale(){
        FileUtil.pullFileFromJar(this.jarFile, "en-US.yml", Settings.Paths.LOCALES);
        FileUtil.pullFileFromJar(this.jarFile, "zh-CN.yml", Settings.Paths.LOCALES);
        this.translationFile = FileUtil.getFile(this.impl.getFolder(),
                Settings.Paths.LOCALES + File.separator + Settings.LOCALE
                        + ".yml");
        Locale.load(this.translationFile);
    }


    public void loadMenus(){

    }


    public void unload(){

    }

}
