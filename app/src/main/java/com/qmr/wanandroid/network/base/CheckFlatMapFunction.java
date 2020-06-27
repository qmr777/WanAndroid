package com.qmr.wanandroid.network.base;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableEmitter;
import io.reactivex.rxjava3.core.ObservableOnSubscribe;
import io.reactivex.rxjava3.functions.Function;

public class CheckFlatMapFunction<T> implements Function<WanAndroidResponse<T>, Observable<T>> {
    @Override
    public Observable<T> apply(WanAndroidResponse<T> tWanAndroidResponse) throws Throwable {
        if (tWanAndroidResponse.getCode() != 0)
            throw new WanNetworkException(tWanAndroidResponse.getMsg());
        Observable<T> ob = Observable.create(new ObservableOnSubscribe<T>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<T> emitter) throws Throwable {
                emitter.onNext(tWanAndroidResponse.getData());
            }
        });
        return ob;
    }
}
