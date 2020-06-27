package com.qmr.wanandroid.model.cache;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.qmr.wanandroid.model.entity.ArticleListBean;
import com.qmr.wanandroid.model.entity.BannerBean;
import com.qmr.wanandroid.model.entity.NaviBean;
import com.qmr.wanandroid.model.entity.TixiBean;
import com.qmr.wanandroid.model.entity.TopArticleBean;
import com.qmr.wanandroid.model.entity.WendaBean;
import com.qmr.wanandroid.util.Const;

import java.lang.reflect.Type;
import java.util.List;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableEmitter;
import io.reactivex.rxjava3.core.ObservableOnSubscribe;

/**
 * 不含erreocode的缓存类，应该用在checkObservable之后
 */
public class CacheObserver {

    private static final String TAG = "CacheObserver";

    /**
     * 要用concatdelayerror避免onError
     */
    private static <T> Observable<T> get(String key) {

        Observable<T> ob = Observable.create(new ObservableOnSubscribe<T>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<T> emitter) throws Throwable {
                Log.i(TAG, "subscribe: " + Thread.currentThread().getName());
                String c = CacheManager.getInstance().get(key);
                Type type = new TypeToken<T>() {
                }.getType();
                emitter.onNext(new Gson().fromJson(c, type));
                emitter.onComplete();
            }
        });

        return ob;
    }

    public static Observable<WendaBean> getWendaCache() {
        Observable<WendaBean> ob = Observable.create(new ObservableOnSubscribe<WendaBean>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<WendaBean> emitter) throws Throwable {
                Log.i(TAG, "subscribe: " + Thread.currentThread().getName());
                String c = CacheManager.getInstance().get(Const.KEY_WENDA);
                WendaBean bean = new Gson().fromJson(c, WendaBean.class);
                emitter.onNext(bean);
            }
        });

        return ob;
    }

    //首页三兄弟
    public static Observable<List<BannerBean>> getBannerCache() {
        Observable<List<BannerBean>> ob = Observable.create(new ObservableOnSubscribe<List<BannerBean>>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<List<BannerBean>> emitter) {
                String c = CacheManager.getInstance().get(Const.KEY_CACHE_BANNER);
                Type type = new TypeToken<List<BannerBean>>() {
                }.getType();
                List<BannerBean> bean = new Gson().fromJson(c, type);
                emitter.onNext(bean);
            }
        });
        return ob;
    }

    public static Observable<List<TopArticleBean>> getTopCache() {
        Observable<List<TopArticleBean>> ob = Observable.create(new ObservableOnSubscribe<List<TopArticleBean>>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<List<TopArticleBean>> emitter) throws Throwable {
                String c = CacheManager.getInstance().get(Const.KEY_CACHE_TOP);
                Type type = new TypeToken<List<TopArticleBean>>() {
                }.getType();
                List<TopArticleBean> bean = new Gson().fromJson(c, type);
                emitter.onNext(bean);
            }
        });

        return ob;
    }

    public static Observable<ArticleListBean> getContentCache() {
        //return get(Const.KEY_CACHE_CONTENT);
        Observable<ArticleListBean> ob = Observable.create(new ObservableOnSubscribe<ArticleListBean>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<ArticleListBean> emitter) throws Throwable {
                Log.i(TAG, "subscribe: " + Thread.currentThread().getName());
                String c = CacheManager.getInstance().get(Const.KEY_CACHE_CONTENT);
                Type type = new TypeToken<ArticleListBean>() {
                }.getType();
                ArticleListBean bean = new Gson().fromJson(c, type);
                emitter.onNext(bean);
            }
        });
        return ob;
    }

    public static Observable<List<NaviBean>> getNavigationCache() {

        Observable<List<NaviBean>> ob = Observable.create(new ObservableOnSubscribe<List<NaviBean>>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<List<NaviBean>> emitter) throws Throwable {
                Log.i(TAG, "subscribe: " + Thread.currentThread().getName());
                String c = CacheManager.getInstance().get(Const.KEY_CACHE_NAVIGATION);
                Type type = new TypeToken<List<NaviBean>>() {
                }.getType();
                List<NaviBean> bean = new Gson().fromJson(c, type);
                emitter.onNext(bean);
            }
        });
        return ob;
    }

    public static Observable<List<TixiBean>> getTixiCache() {

        Observable<List<TixiBean>> ob = Observable.create(new ObservableOnSubscribe<List<TixiBean>>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<List<TixiBean>> emitter) throws Throwable {
                Log.i(TAG, "subscribe: " + Thread.currentThread().getName());
                String c = CacheManager.getInstance().get(Const.KEY_CACHE_TIXI);
                Type type = new TypeToken<List<TixiBean>>() {
                }.getType();
                List<TixiBean> bean = new Gson().fromJson(c, type);
                emitter.onNext(bean);
            }
        });
        return ob;
    }

    public static Observable<List<TixiBean>> tixiCache() {
        Observable<List<TixiBean>> ob = Observable.create(new ObservableOnSubscribe<List<TixiBean>>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<List<TixiBean>> emitter) throws Throwable {
                String c = CacheManager.getInstance().get(Const.KEY_CACHE_TIXI);
                Type type = new TypeToken<List<TixiBean>>() {
                }.getType();
                List<TixiBean> bean = new Gson().fromJson(c, type);
                emitter.onNext(bean);
            }
        });
        return ob;
    }

    public static class NoCacheError extends RuntimeException {
    }

}
