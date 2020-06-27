package com.qmr.wanandroid.util;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import java.util.Set;

public class PreferencesUtil {

    public static final String KEY_USER_INFO = "user_info";
    private static Application context;
    private static PreferencesUtil instance;
    private SharedPreferences sp;


    private PreferencesUtil(Application application) {
        context = application;
        sp = context.getSharedPreferences("sp", Context.MODE_PRIVATE);
    }

    public static PreferencesUtil getInstance() {
        return instance;
    }

    public static void init(Application application) {
        instance = new PreferencesUtil(application);
    }

    public void putCookies(Set<String> cookies) {
        sp.edit().putStringSet("cookies", cookies).apply();
    }

    public String getUsername() {
        return sp.getString("username", "");
    }

    public void setUsername(String username) {
        sp.edit().putString("username", username).apply();
    }

    public Set<String> getCookies() {
        return sp.getStringSet("cookies", null);
    }

    public String getUserInfo() {
        return sp.getString(KEY_USER_INFO, null);
    }

}
