package com.qmr.wanandroid.network.base;

import android.util.Log;

import com.qmr.wanandroid.util.PreferencesUtil;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import okhttp3.Interceptor;
import okhttp3.Response;

public class SaveCookieInterceptor implements Interceptor {

    public static final String TAG = "SaveCookieInterceptor";

    @Override
    public Response intercept(Chain chain) throws IOException {
        Response originalResponse = chain.proceed(chain.request());

        if (!originalResponse.headers("Set-Cookie").isEmpty()) {
            List<String> cookie = chain.request().headers("Set-Cookie");
            Log.i(TAG, "intercept: " + cookie.size());
            for (String s : cookie)
                Log.i(TAG, "intercept: " + s);
            Set<String> cookies = new HashSet<>(originalResponse.headers("Set-Cookie"));
            PreferencesUtil.getInstance().putCookies(cookies);
        }
        return originalResponse;
    }
}
