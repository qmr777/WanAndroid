package com.qmr.base.log;


import android.util.Log;

import com.qmr.base.BuildConfig;

public class LogUtil {

    private static final boolean isDebug = BuildConfig.DEBUG;

    public static void v(String tag, String msg) {
        if (isDebug) Log.v(tag, msg);
    }

    public static void d(String tag, String msg) {
        if (isDebug) Log.d(tag, msg);
    }

    public static void i(String tag, String msg) {
        if (isDebug) Log.i(tag, msg);
    }

    public static void e(String tag, String msg) {
        if (isDebug) Log.e(tag, msg);
    }

    public static void w(String tag, String msg) {
        if (isDebug) Log.w(tag, msg);
    }

}
