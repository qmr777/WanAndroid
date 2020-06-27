package com.qmr.wanandroid.network.base;

import com.google.gson.Gson;
import com.qmr.wanandroid.model.cache.CacheManager;

import io.reactivex.rxjava3.functions.Function;

/**
 * 带检查和缓存的map
 *
 * @param <T>
 */

public class CheckAndSaveMapFunc<T> implements Function<WanAndroidResponse<T>, T> {

    private static final String TAG = "CheckMapFunction";

    private String k;

    public CheckAndSaveMapFunc(String key) {
        k = key;
    }

    @Override
    public T apply(WanAndroidResponse<T> tWanAndroidResponse) throws Throwable {
        if (tWanAndroidResponse.getCode() != 0)
            throw new WanNetworkException(tWanAndroidResponse.getMsg());
        CacheManager.getInstance().put(k, new Gson().toJson(tWanAndroidResponse.getData()));
        return tWanAndroidResponse.getData();
    }
}
