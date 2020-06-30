package com.bw.movie.api;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;

/**
 * 请求接口
 * 李易泽
 * 20200523
 */
public interface ApiService {
    //请求码
    int REQUEST_CODE = 100;
    //请求的方式
    int GET = 0;
    int POST = 1;
    //Get请求（无参）
    @GET
    Observable<ResponseBody> getRequestNoParams(@Url String url);
    //Get请求（有参）
    @GET
    Observable<ResponseBody> getRequestHaveParams(@Url String url, @QueryMap Map<String,Object> map);
    //Post请求
    @POST
    @FormUrlEncoded
    Observable<ResponseBody> postRequest(@Url String url, @FieldMap Map<String,Object> map);
    //上传图片
    @Multipart
    @POST
    Observable<ResponseBody> postUpLoadImage(@Url String url, @Part MultipartBody.Part part);
}
