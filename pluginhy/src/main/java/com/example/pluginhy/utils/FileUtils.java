package com.example.pluginhy.utils;


import android.content.Context;
import android.content.res.AssetManager;
import android.os.Environment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * 文件工具类 用来将assets目录下面的jar拷贝到指定目录下面
 */
public class FileUtils {

    public static void copyFiles(Context context, String fileName, File desFile) {

        InputStream inputStream = null;
        OutputStream fos = null;
        try {
            inputStream = context.getAssets().open(fileName);

            fos = new FileOutputStream(desFile.getAbsolutePath());

            byte[] bytes = new byte[1024];
            int i;

            while ((i = inputStream.read(bytes)) != -1) {
                fos.write(bytes, 0, i);
            }

            fos.flush();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }

                if (fos != null) {
                    fos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }

    public static boolean hasExternalStorage() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }

    /**
     * 获取缓存目录
     * @param context
     * @return
     */
    public static File getCacheDir(Context context) {
        File cache;

        if (hasExternalStorage()) {
            cache = context.getExternalCacheDir();
        } else {
            cache = context.getCacheDir();
        }

        if (cache != null && !cache.exists()) {
            cache.mkdirs();

        }

        return cache;
    }

}
