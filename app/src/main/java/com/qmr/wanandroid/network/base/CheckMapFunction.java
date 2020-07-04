package com.qmr.wanandroid.network.base;

import io.reactivex.rxjava3.functions.Function;

public class CheckMapFunction<T> implements Function<WanAndroidResponse<T>, T> {

    private static final String TAG = "CheckMapFunction";

    @Override
    public T apply(WanAndroidResponse<T> tWanAndroidResponse) throws Throwable {
        if (tWanAndroidResponse.getCode() != 0)
            throw new WanNetworkException(tWanAndroidResponse.getMsg());

        return tWanAndroidResponse.getData();
    }
}
