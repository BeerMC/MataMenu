package me.matata.matamenu.general.utils;

import me.matata.matamenu.general.MataMenu;

import java.io.*;
import java.nio.file.Paths;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * @author intellectualsites
 */
public class FileUtil {


    public static File getFile(File base, String path) {
        if (Paths.get(path).isAbsolute()) {
            return new File(path);
        }
        return new File(base, path);
    }

    public static void pullFileFromJar(File jar, String file, String folder) {
        try {
            File output = MataMenu.getInstance().impl.getFolder();
            if (!output.exists()) {
                output.mkdirs();
            }
            File newFile = FileUtil.getFile(output, folder + File.separator + file);
            if (newFile.exists()) {
                return;
            }
            try (InputStream stream = MataMenu.getInstance().impl.getClass().getResourceAsStream(file)) {
                byte[] buffer = new byte[2048];
                if (stream == null) {
                    try (ZipInputStream zis = new ZipInputStream(
                            new FileInputStream(jar))) {
                        ZipEntry ze = zis.getNextEntry();
                        while (ze != null) {
                            String name = ze.getName();
                            if (name.equals(file)) {
                                new File(newFile.getParent()).mkdirs();
                                try (FileOutputStream fos = new FileOutputStream(newFile)) {
                                    int len;
                                    while ((len = zis.read(buffer)) > 0) {
                                        fos.write(buffer, 0, len);
                                    }
                                }
                                ze = null;
                            } else {
                                ze = zis.getNextEntry();
                            }
                        }
                        zis.closeEntry();
                    }
                    return;
                }
                newFile.createNewFile();
                try (FileOutputStream fos = new FileOutputStream(newFile)) {
                    int len;
                    while ((len = stream.read(buffer)) > 0) {
                        fos.write(buffer, 0, len);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
