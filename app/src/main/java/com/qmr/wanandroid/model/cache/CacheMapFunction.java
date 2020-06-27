package com.qmr.wanandroid.model.cache;

import androidx.annotation.StringDef;

import com.google.gson.Gson;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.List;

import io.reactivex.rxjava3.functions.Function;

public class CacheMapFunction<T> implements Function<List<T>, List<T>> {

    public static final String KEY_BANNER = "banner";
    public static final String KEY_TOP_ARTICLE = "topArticle";
    public static final String KEY_ARTICLE = "article";
    public static final String KEY_TIXI = "tixi";//体系
    public static final String KEY_NAVIGATION = "navi";//导航
    public static final String KEY_SQUARE = "square";//广场
    private String k;

    public CacheMapFunction(@KEY String key) {
        this.k = key;
    }

    @Override
    public List<T> apply(List<T> ts) throws Throwable {
        CacheManager.getInstance().put(k, new Gson().toJson(ts));
        return ts;
    }

    @StringDef({KEY_ARTICLE, KEY_BANNER, KEY_NAVIGATION, KEY_SQUARE, KEY_TIXI, KEY_TOP_ARTICLE})
    @Retention(RetentionPolicy.SOURCE)
    public @interface KEY {
    }
}
