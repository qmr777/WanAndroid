package com.qmr.wanandroid.network.account;

import com.qmr.wanandroid.model.entity.LoginBean;
import com.qmr.wanandroid.network.base.WanAndroidResponse;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface LoginRegisterApi {

    @FormUrlEncoded
    @POST("user/login")
    Observable<WanAndroidResponse<LoginBean>> login(@Field("username") String username,
                                                    @Field("password") String password);

    @FormUrlEncoded
    @POST("user/register")
    Observable<WanAndroidResponse<LoginBean>> register(@Field("username") String username,
                                                       @Field("password") String password,
                                                       @Field("repassword") String repassword);

    @FormUrlEncoded
    @GET("user/logout/json")
    void logout();
}
