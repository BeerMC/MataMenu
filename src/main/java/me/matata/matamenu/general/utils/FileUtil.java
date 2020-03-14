package me.matata.matamenu.general.utils;

import java.io.File;
import java.nio.file.Paths;

/**
 * @author matata
 * @date 2020/3/14 12:04
 */
public class FileUtil {


    public static File getFile(File base, String path) {
        if (Paths.get(path).isAbsolute()) {
            return new File(path);
        }
        return new File(base, path);
    }
}
