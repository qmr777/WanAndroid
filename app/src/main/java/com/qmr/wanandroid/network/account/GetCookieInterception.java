package com.qmr.wanandroid.network.account;

import android.util.Log;

import java.io.IOException;
import java.util.List;

import okhttp3.Interceptor;
import okhttp3.Response;

public class GetCookieInterception implements Interceptor {

    public static final String TAG = "COOKie";

    @Override
    public Response intercept(Chain chain) throws IOException {
        List<String> cookie = chain.request().headers("Set-Cookie");
        Log.i(TAG, "intercept: " + cookie.size());
        return chain.proceed(chain.request());
    }
}
