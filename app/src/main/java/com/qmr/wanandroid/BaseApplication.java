package com.qmr.wanandroid;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;

import com.qmr.base.image.ImageLoader;
import com.qmr.wanandroid.model.cache.CacheManager;
import com.qmr.wanandroid.model.database.DatabaseManager;
import com.qmr.wanandroid.util.LoginUtil;
import com.qmr.wanandroid.util.PreferencesUtil;
import com.tencent.bugly.crashreport.CrashReport;

import java.util.Stack;

public class BaseApplication extends Application implements Application.ActivityLifecycleCallbacks {

    public static BaseApplication application;
    private Stack<Activity> activities = new Stack<>();

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
        CrashReport.initCrashReport(getApplicationContext(), "93af2bf3c9", BuildConfig.DEBUG);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);//跟随系统 夜间模式
        CacheManager.init(this);
        ImageLoader.init(this);
        DatabaseManager.init(this);
        PreferencesUtil.init(this);
        LoginUtil.init();
    }

    //登出app
    public void logout() {
        for (Activity activity : activities)
            activity.finish();
    }

    @Override
    public void onActivityCreated(@NonNull Activity activity, @Nullable Bundle savedInstanceState) {
        activities.push(activity);
    }

    @Override
    public void onActivityStarted(@NonNull Activity activity) {

    }

    @Override
    public void onActivityResumed(@NonNull Activity activity) {

    }

    @Override
    public void onActivityPaused(@NonNull Activity activity) {

    }

    @Override
    public void onActivityStopped(@NonNull Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(@NonNull Activity activity) {
        activities.pop();
    }
}
