package com.qmr.wanandroid.network.tixi;

import com.qmr.wanandroid.model.entity.NaviBean;
import com.qmr.wanandroid.model.entity.TixiArticleBean;
import com.qmr.wanandroid.model.entity.TixiBean;
import com.qmr.wanandroid.network.base.WanAndroidResponse;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface Tixi {

    @GET("tree/json")
    Observable<WanAndroidResponse<List<TixiBean>>> tixi();

    @GET("navi/json")
    Observable<WanAndroidResponse<List<NaviBean>>> navigation();

    @GET("article/list/{page}/json")
    Observable<WanAndroidResponse<TixiArticleBean>> tixiArticle(@Path("page") int page, @Query("cid") int cid);

}
