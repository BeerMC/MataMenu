package me.matata.matamenu.general;

import me.matata.matamenu.general.config.Locale;
import me.matata.matamenu.general.config.Settings;
import me.matata.matamenu.general.configuration.file.YamlConfiguration;
import me.matata.matamenu.general.objects.IPlugin;
import me.matata.matamenu.general.objects.ITaskManager;
import me.matata.matamenu.general.utils.FileUtil;
import me.matata.matamenu.general.utils.StringMan;

import java.io.*;

/**
 * @author matata
 * @date 2020/3/13 15:34
 */
public class MataMenu {

    private static MataMenu instance;

    public final IPlugin impl;

    public File translationFile;

    public static String version;

    public static MataMenu getInstance(){
        return instance;
    }

    public static void log(Object message) {
        MataMenu.getInstance().impl.log(StringMan.getString(message));
    }

    public static void debug(Object message) {
        if(Settings.DEBUG){
            MataMenu.getInstance().impl.log(StringMan.getString(message));
        }
    }

    public MataMenu(IPlugin plugin){
        MataMenu.instance = this;
        this.impl = plugin;
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
            try (InputStream stream = getClass().getResourceAsStream("/plugin.yml")) {
                try (BufferedReader br = new BufferedReader(new InputStreamReader(stream))) {
                    version = br.readLine();
                    System.out.println("&3Plugin version is " + version);
                }
            } catch (IOException throwable) {
                throwable.printStackTrace();
            }
            String lastVersionString = settings_config.getString("VERSION");
            if(lastVersionString == null || !lastVersionString.equals(version)){
                Settings.save(configFile);  //Overwrite
                Settings.load(configFile);
            }else{
                Settings.load(configFile);
                Settings.save(configFile);
            }
        } catch (IOException ignored) {
            MataMenu.log("&cFailed to save settings.yml");
        }
    }

    private void loadLocale(){
        this.translationFile = FileUtil.getFile(this.impl.getFolder(),
                "locales" + File.separator + Settings.LOCALE
                        + ".yml");
        //Locale.load(this.translationFile);
    }

    public void loadMenus(){

    }

}
