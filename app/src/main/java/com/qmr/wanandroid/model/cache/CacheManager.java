package com.qmr.wanandroid.model.cache;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.qmr.base.cache.CacheUtil;
import com.qmr.wanandroid.model.entity.BannerBean;
import com.qmr.wanandroid.model.entity.TopArticleBean;
import com.qmr.wanandroid.network.base.WanAndroidResponse;

import java.io.File;
import java.lang.reflect.Type;
import java.util.List;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableEmitter;
import io.reactivex.rxjava3.core.ObservableOnSubscribe;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class CacheManager {

    public static final String KEY_BANNER = "banner";
    public static final String KEY_TOP_ARTICLE = "topArticle";
    public static final String KEY_ARTICLE = "article";


    public static final String KEY_TIXI = "tixi";//体系
    public static final String KEY_NAVIGATION = "navi";//导航
    public static final String KEY_SQUARE = "square";//广场


    public static final int APP_VERSION = 1;

    private static CacheManager instance;
    private File cacheDir;
    private CacheUtil cacheUtil;

    private CacheManager(Context context) {
        cacheDir = context.getCacheDir();
        CacheUtil.init(cacheDir);
        cacheUtil = CacheUtil.getInstance();
    }

    public static CacheManager getInstance() {
        return instance;
    }

    public static void init(Context context) {
        instance = new CacheManager(context);
    }

    public void put(String key, String value) {
        cacheUtil.addDiskCache(key, value);
    }

    public String get(String key) {
        return cacheUtil.getDiskCache(key);
    }

    public Observable<List<TopArticleBean>> getTopArticleOb() {
        return Observable.create(new ObservableOnSubscribe<List<TopArticleBean>>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<List<TopArticleBean>> emitter) throws Throwable {
                List<TopArticleBean> ps = new Gson().fromJson(getTopArticleCache(), new TypeToken<List<TopArticleBean>>() {
                }.getType());
                emitter.onNext(ps);
            }
        }).subscribeOn(Schedulers.io());
    }

    public String getBannerCache() {
        return cacheUtil.getDiskCache(KEY_BANNER);
    }

    public void setBannerCache(String json) {
        cacheUtil.addDiskCache(KEY_BANNER, json);
    }

    public Observable<WanAndroidResponse<List<BannerBean>>> getBannerCacheOb() {
        return Observable.create(new ObservableOnSubscribe<WanAndroidResponse<List<BannerBean>>>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<WanAndroidResponse<List<BannerBean>>> emitter) throws Throwable {
                Type type = new TypeToken<WanAndroidResponse<List<BannerBean>>>() {
                }.getType();
                emitter.onNext(new Gson().fromJson(getBannerCache(), type));
            }
        }).subscribeOn(Schedulers.io());
    }

    public String getTopArticleCache() {
        return cacheUtil.getDiskCache(KEY_TOP_ARTICLE);
    }

    public void setTopArticleCache(String json) {
        cacheUtil.addDiskCache(KEY_TOP_ARTICLE, json);
    }

    public void setTopArticleCache(List<TopArticleBean> data) {
        cacheUtil.addDiskCache(KEY_TOP_ARTICLE, new Gson().toJson(data));
    }

    public String getArticleCache() {
        return cacheUtil.getDiskCache(KEY_ARTICLE);
    }

    public void setArticleCache(String json) {
        cacheUtil.addDiskCache(KEY_ARTICLE, json);
    }

    public String getKnowledgeCache() {
        return cacheUtil.getDiskCache(KEY_TIXI);
    }

    public void setKnowledgeCache(String json) {
        cacheUtil.addDiskCache(KEY_TIXI, json);
    }

    public String getNavigationCache() {
        return cacheUtil.getDiskCache(KEY_NAVIGATION);
    }

    public void setNavigationCache(String json) {
        cacheUtil.addDiskCache(KEY_NAVIGATION, json);
    }

}
