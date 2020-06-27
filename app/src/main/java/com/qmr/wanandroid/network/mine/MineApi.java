package com.qmr.wanandroid.network.mine;

import com.qmr.wanandroid.model.entity.CollectionBean;
import com.qmr.wanandroid.network.base.WanAndroidResponse;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface MineApi {

    @GET("lg/collect/list/{page}/json")
    Observable<WanAndroidResponse<CollectionBean>> getCollections(@Path("page") int page);
    //List<CollectionItemBean> getCollections(@Path("page")int page);

    @POST("lg/collect/{id}/json")
    Observable<WanAndroidResponse> addCollection(@Path("id") int id);


    @POST("lg/uncollect_originId/{id}/json")
    Observable<WanAndroidResponse> removeCollections(@Path("id") int id);

    @FormUrlEncoded
    @POST("lg/uncollect/{id}/json")
    Observable<WanAndroidResponse> removeCollections(@Path("id") int id, @Field("originalID") int originalID);
}
