package me.matata.matamenu.general.config;

import me.matata.matamenu.general.MataMenu;
import me.matata.matamenu.general.config.configuration.file.YamlConfiguration;
import me.matata.matamenu.general.objects.ICommandSender;
import me.matata.matamenu.general.utils.Strings;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * @author matata, intellectualsites
 * @date 2020/3/14 11:32
 */
public enum Locale {

    TEST_MESSAGE("1", "1");


    public static final HashMap<String, String> replacements = new HashMap<>();

    private final String defaultString;
    private final String category;
    private final boolean prefix;
    private String translatedString;


    Locale(String defaultString, boolean prefix, String category) {
        this.defaultString = defaultString;
        this.translatedString = defaultString;
        this.prefix = prefix;
        this.category = category.toLowerCase();
    }

    Locale(String defaultString, String category) {
        this(defaultString, true, category.toLowerCase());
    }

    public static String format(String message, Object... args) {
        if (args.length == 0) {
            return message;
        }
        Map<String, String> map = new LinkedHashMap<>();
        for (int i = args.length - 1; i >= 0; i--) {
            String arg = "" + args[i];
            if (arg.isEmpty()) {
                map.put("%s" + i, "");
            } else {
                arg = Locale.color(arg);
                map.put("%s" + i, arg);
            }
            if (i == 0) {
                map.put("%s", arg);
            }
        }
        message = Strings.replaceFromMap(message, map);
        return message;
    }

    public static String format(Locale item, Object... args) {
        if (item.usePrefix() && item.translatedString.length() > 0) {
            return Settings.PREFIX + format(item.translatedString, args);
        } else {
            return format(item.translatedString, args);
        }
    }

    public static void load(File file) {
        try {
            if (!file.exists()) {
                file.getParentFile().mkdirs();
                file.createNewFile();
            }
            YamlConfiguration yml = YamlConfiguration.loadConfiguration(file);
            Set<String> keys = yml.getKeys(true);
            EnumSet<Locale> allEnums = EnumSet.allOf(Locale.class);
            HashSet<String> allNames = new HashSet<>();
            HashSet<String> categories = new HashSet<>();
            for (Locale item : allEnums) {
                allNames.add(item.name());
                categories.add(item.category.toLowerCase());
            }
            HashSet<Locale> captions = new HashSet<>();
            boolean changed = false;
            HashSet<String> toRemove = new HashSet<>();
            for (String key : keys) {
                if (!yml.isString(key)) {
                    if (!categories.contains(key)) {
                        toRemove.add(key);
                    }
                    continue;
                }
                String[] split = key.split("\\.");
                String node = split[split.length - 1].toUpperCase();
                Locale item;
                if (allNames.contains(node)) {
                    item = valueOf(node);
                } else {
                    item = null;
                }
                if (item != null) {
                    if (item.category.startsWith("static")) {
                        continue;
                    }
                    String value = yml.getString(key);
                    if (!split[0].equalsIgnoreCase(item.category)) {
                        changed = true;
                        yml.set(key, null);
                        yml.set(item.category + '.' + item.name().toLowerCase(), value);
                    }
                    captions.add(item);
                    item.translatedString = value;
                } else {
                    toRemove.add(key);
                }
            }
            for (String remove : toRemove) {
                changed = true;
                yml.set(remove, null);
            }
            for (char letter : "1234567890abcdefklmnor".toCharArray()) {
                replacements.put("&" + letter, "" + Settings.COLOR_ESCAPE + letter);
            }
            replacements.put("\\\\n", "\n");
            replacements.put("\\n", "\n");
            replacements.put("&-", "\n");
            for (Locale item : allEnums) {
                if (!captions.contains(item)) {
                    if (item.getCategory().startsWith("static")) {
                        continue;
                    }
                    changed = true;
                    yml.set(item.category + '.' + item.name().toLowerCase(),
                            item.defaultString);
                }
                item.translatedString =
                        Strings.replaceFromMap(item.translatedString, replacements);
            }
            if (changed) {
                yml.save(file);
            }
        } catch (IOException | IllegalArgumentException e) {
            e.printStackTrace();
        }
    }

    public static String color(String string) {
        return Strings.replaceFromMap(string, replacements);
    }

    @Override public String toString() {
        return this.translatedString;
    }

    public String getTranslated() {
        return this.translatedString;
    }

    public boolean usePrefix() {
        return this.prefix;
    }

    public String formatted() {
        return Strings.replaceFromMap(getTranslated(), replacements);
    }

    public String getCategory() {
        return this.category;
    }

    public void send(ICommandSender receiver, String... args) {
        send(receiver, (Object[]) args);
    }

    public void send(ICommandSender receiver, Object... args) {
        String msg = format(this, args);
        if (receiver == null) {
            MataMenu.log(msg);
        } else {
            receiver.sendMessage(msg);
        }
    }

}
