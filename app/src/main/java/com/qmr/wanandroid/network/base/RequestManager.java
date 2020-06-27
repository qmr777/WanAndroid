package com.qmr.wanandroid.network.base;

import android.util.Log;

import androidx.collection.ArrayMap;

import com.qmr.wanandroid.network.account.LoginRegisterApi;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RequestManager {

    public static final String TAG = "RequestManager";

    private static final String BASE_URL = "https://www.wanandroid.com/";

    private static RequestManager instance = null;
    private ArrayMap<Class<?>, Object> arrayMap;
    private Retrofit mRetrofit;
    private OkHttpClient client;

    private RequestManager() {
        arrayMap = new ArrayMap<>();
        client = new OkHttpClient.Builder()
                .addInterceptor(new AddCookieInterceptor())
                .readTimeout(5, TimeUnit.SECONDS)
                .writeTimeout(5, TimeUnit.SECONDS)
                .build();
        mRetrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
    }

    public static RequestManager getInstance() {
        if (instance == null) {
            instance = new RequestManager();
        }
        return instance;
    }


    public <T> T createService(Class<T> clazz) {
        T service = mRetrofit.create(clazz);
        arrayMap.put(clazz, service);
        return service;
    }

    public <T> T getService(Class<T> clazz) {
        T service = (T) arrayMap.get(clazz);
        if (service == null) {
            service = createService(clazz);
            arrayMap.put(clazz, service);
        }
        return service;
    }

    public LoginRegisterApi loginOrRegister() {

        Log.d(TAG, "loginOrRegister");

        OkHttpClient c = new OkHttpClient.Builder()
                .addInterceptor(new SaveCookieInterceptor())
                .readTimeout(5, TimeUnit.SECONDS)
                .writeTimeout(5, TimeUnit.SECONDS)
                .build();

        LoginRegisterApi service = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(c)
                .build()
                .create(LoginRegisterApi.class);

        return service;
    }

}
