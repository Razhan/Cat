package com.ran.delta.utils;

import android.app.Activity;
import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.Resources;
import android.os.Environment;
import android.support.annotation.MenuRes;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.PopupMenu;

import com.ran.delta.widget.bottomBar.BottomBarTab;

import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public final class MiscUtils {

    public static int dpToPixel(Context context, float dp) {
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();

        try {
            return (int) (dp * (metrics.densityDpi / 160f));
        } catch (NoSuchFieldError ignored) {
            return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, metrics);
        }
    }

    public static int getScreenWidth(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return (int) (displayMetrics.widthPixels / displayMetrics.density);
    }

    public static BottomBarTab[] inflateMenuFromResource(Activity activity, @MenuRes int menuRes) {
        PopupMenu popupMenu = new PopupMenu(activity, null);
        Menu menu = popupMenu.getMenu();
        activity.getMenuInflater().inflate(menuRes, menu);

        int menuSize = menu.size();
        BottomBarTab[] tabs = new BottomBarTab[menuSize];

        for (int i = 0; i < menuSize; i++) {
            MenuItem item = menu.getItem(i);
            BottomBarTab tab = new BottomBarTab(item.getIcon(),
                    String.valueOf(item.getTitle()));
            tab.id = item.getItemId();
            tabs[i] = tab;
        }

        return tabs;
    }

    public static void unzip(String targetDirectory, String zipFile) throws IOException {
        targetDirectory = getFolderPath(targetDirectory);
        zipFile = targetDirectory + zipFile;

        ZipInputStream zis = new ZipInputStream(new BufferedInputStream(new FileInputStream(zipFile)));
        try {
            ZipEntry ze;
            int count;
            byte[] buffer = new byte[8192];

            while ((ze = zis.getNextEntry()) != null) {
                File file = new File(targetDirectory, ze.getName());
                File dir = ze.isDirectory() ? file : file.getParentFile();

                if (!dir.isDirectory() && !dir.mkdirs()) {
                    throw new FileNotFoundException("Failed to ensure directory: " + dir.getAbsolutePath());
                }

                if (ze.isDirectory()) {
                    continue;
                }

                FileOutputStream fout = new FileOutputStream(file);
                try {
                    while ((count = zis.read(buffer)) != -1) {
                        fout.write(buffer, 0, count);
                    }
                } finally {
                    fout.close();
                }
            }
        } finally {
            zis.close();
        }
    }

    public static String getFolderPath(String folderName) {
            File mFolder = new File(Environment.getExternalStorageDirectory() + File.separator + folderName);
            if(!mFolder.exists()) {
                mFolder.mkdirs();
            }

        return mFolder.getAbsolutePath() + File.separator;
    }

    public static boolean createDir(String path) {
        boolean ret = true;
        File file = new File(Environment.getExternalStorageDirectory(), path);
        if (!file.exists()) {
            if (!file.mkdirs()) {
                ret = false;
            }
        }
        return ret;
    }

    public static boolean deleteFile(String path) {
        if (path == null || path.isEmpty()) {
            return true;
        }

        File file = new File(path);
        if (!file.exists()) {
            return true;
        }
        if (file.isFile()) {
            return file.delete();
        }
        if (!file.isDirectory()) {
            return false;
        }
        for (File f : file.listFiles()) {
            if (f.isFile()) {
                f.delete();
            } else if (f.isDirectory()) {
                deleteFile(f.getAbsolutePath());
            }
        }
        return file.delete();
    }

    public static String getSystemLanguage(Context context) {
        String language = context.getResources().getConfiguration().locale
                .getLanguage().toLowerCase();

        String country = context.getResources().getConfiguration().locale
                .getCountry().toLowerCase();

        if (language.equals(country) || language.equals("en")) {
            return language;
        } else {
            return language + "-" + country;
        }
    }

    public static JSONObject readJsonFile(Context context, String dir, String path) {
        FileInputStream stream = null;

        File file = new File(getFolderPath(dir) + path);

        try {
            if (!file.exists()) {
                AssetFileDescriptor fileDescriptor = context.getAssets().openFd(path);
                stream = fileDescriptor.createInputStream();
            } else {
                stream = new FileInputStream(file);
            }
        } catch (Exception e) {
        }

        try {
            FileChannel fc = stream.getChannel();
            MappedByteBuffer bb = fc.map(FileChannel.MapMode.READ_ONLY, 0, fc.size());

            String jsonStr = Charset.defaultCharset().decode(bb).toString();
            return new JSONObject(jsonStr);
        } catch (Exception e) {
        } finally {
            try {
                if (stream != null) {
                    stream.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return null;
    }

}
