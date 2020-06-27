package com.qmr.wanandroid.network.main;

import com.qmr.wanandroid.model.entity.ArticleListBean;
import com.qmr.wanandroid.model.entity.BannerBean;
import com.qmr.wanandroid.model.entity.BingImageEntity;
import com.qmr.wanandroid.model.entity.HotKeyBean;
import com.qmr.wanandroid.model.entity.SearchResultBean;
import com.qmr.wanandroid.model.entity.TopArticleBean;
import com.qmr.wanandroid.model.entity.WendaBean;
import com.qmr.wanandroid.network.base.WanAndroidResponse;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Base URL: 在最后加下 /
 * Url（@GET,@POST）: 不要在开始位置加 /
 */
public interface MainApi {
        /**
         * 首页banner数据
         */
        @GET("banner/json")
        Observable<WanAndroidResponse<List<BannerBean>>> banner();

        @GET("article/list/{page}/json")
        Observable<WanAndroidResponse<ArticleListBean>> articleList(@Path("page") int page);

        @GET("article/top/json")
        Observable<WanAndroidResponse<List<TopArticleBean>>> topArticleList();

        @GET("hotkey/json")
        Observable<WanAndroidResponse<List<HotKeyBean>>> hotKey();

        @FormUrlEncoded
        @POST("article/query/{page}/json")
        Observable<WanAndroidResponse<SearchResultBean>> search(@Path("page") int page, @Field("k") String key);

        @GET("article/list/{page}/json")
        Observable<WanAndroidResponse<ArticleListBean>> searchAuthor(@Path("page") int page, @Query("author") String author);

        @GET("wenda/list/{page}/json")
        Observable<WanAndroidResponse<WendaBean>> wenda(@Path("page") int page);

        @GET("http://bing.getlove.cn/latelyBingImageStory")
//开屏图片
        Observable<List<BingImageEntity>> splashImage();


}
