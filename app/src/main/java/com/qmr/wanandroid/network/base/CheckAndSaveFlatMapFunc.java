package com.qmr.wanandroid.network.base;

import android.util.Log;

import com.google.gson.Gson;
import com.qmr.wanandroid.model.cache.CacheManager;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableEmitter;
import io.reactivex.rxjava3.core.ObservableOnSubscribe;
import io.reactivex.rxjava3.functions.Function;


public class CheckAndSaveFlatMapFunc<T> implements Function<WanAndroidResponse<T>, Observable<T>> {

    private static final String TAG = "CheckAndSaveFlatMapFunc";

    private String k;

    public CheckAndSaveFlatMapFunc(String key) {
        k = key;
    }

    @Override
    public Observable<T> apply(WanAndroidResponse<T> tWanAndroidResponse) throws Throwable {

        if (tWanAndroidResponse.getCode() != 0) {
            throw new WanNetworkException(tWanAndroidResponse.getMsg());
        }
        CacheManager.getInstance().put(k, new Gson().toJson(tWanAndroidResponse.getData()));
        Log.i(TAG, "apply: " + Thread.currentThread().getName());
        Observable<T> ob = Observable.create(new ObservableOnSubscribe<T>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<T> emitter) throws Throwable {
                emitter.onNext(tWanAndroidResponse.getData());
            }
        });
        return ob;
    }
}
