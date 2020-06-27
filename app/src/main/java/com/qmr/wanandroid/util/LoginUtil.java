package com.qmr.wanandroid.util;

import android.text.TextUtils;
import android.util.Log;

import java.util.Collections;
import java.util.Set;

/**
 * 登录状态
 */
public class LoginUtil {

    public static String username;
    private static boolean isLogin = false;
    private static Set<String> mCookieSet;
    private static LoginUtil util;

    private LoginUtil() {
        mCookieSet = PreferencesUtil.getInstance().getCookies();
        username = PreferencesUtil.getInstance().getUsername();
        isLogin = (mCookieSet != null && mCookieSet.size() != 0) && (!TextUtils.isEmpty(username));

        Log.i("LoginUtil", "LoginUtil: " + username);

    }

    public static void success(String name) {
        isLogin = true;
        PreferencesUtil.getInstance().setUsername(name);
        mCookieSet = PreferencesUtil.getInstance().getCookies();
        username = name;
    }

    public static void init() {
        util = new LoginUtil();
    }

    public static boolean getLoginStatue() {
        return isLogin;
    }

    public static void logout() {
        //mCookieSet.clear();
        PreferencesUtil.getInstance().putCookies(Collections.emptySet());
        PreferencesUtil.getInstance().setUsername(null);
        isLogin = false;
    }

}
