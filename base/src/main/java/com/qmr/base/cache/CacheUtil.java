package com.qmr.base.cache;

import android.text.TextUtils;
import android.util.Log;

import com.jakewharton.disklrucache.DiskLruCache;

import java.io.File;
import java.io.IOException;

public class CacheUtil {

    public static final int APP_VERSION = 1;
    private static final String TAG = "CacheUtil";
    private static CacheUtil instance;
    private File cacheDir;

    private CacheUtil(File cacheDir) {
        this.cacheDir = cacheDir;
    }

    public static CacheUtil getInstance() {
        return instance;
    }

    public static void init(File cacheDir) {
        instance = new CacheUtil(cacheDir);
    }

    public void addDiskCache(String key, String value) {
        try {
            DiskLruCache diskLruCache = DiskLruCache.open(cacheDir, APP_VERSION, 1, 1024 * 1024 * 10);
            DiskLruCache.Editor editor = diskLruCache.edit(key);
            editor.newOutputStream(0).write(value.getBytes());
            editor.commit();
            diskLruCache.close();
        } catch (IOException e) {
            e.printStackTrace();
            Log.d(TAG, "addDiskCache: " + e.getMessage());
        }
    }

    public String getDiskCache(String key) {
        String value = null;
        try {
            DiskLruCache diskLruCache = DiskLruCache.open(cacheDir, APP_VERSION, 1, 1024 * 1024 * 10);
            value = diskLruCache.get(key).getString(0);
            diskLruCache.close();
            Log.i(TAG, key + " getDiskCache: value.length()" + value.length());

        } catch (Exception e) {
            Log.i(TAG, "getDiskCache: " + e.getMessage());
            e.printStackTrace();
        }
        return TextUtils.isEmpty(value) ? "" : value;
    }
}
