package com.bw.movie.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.TextUtils;

import com.bw.movie.api.ApiService;
import com.bw.movie.url.MyUrl;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 网络工具类
 * 李易泽
 * 20200523
 */
public class NetUtil {
    //定义
    private ApiService mApiService;
    //单例模式
    private NetUtil(){
        //日志拦截器
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        //请求头拦截器
        Interceptor interceptor = new Interceptor() {
            @NotNull
            @Override
            public Response intercept(@NotNull Chain chain) throws IOException {
                //登陆的时候就可以把userId和sessionId保存在本地
                String userId = MyApplication.getSharedPreferences().getString("userId",null);
                String sessionId = MyApplication.getSharedPreferences().getString("sessionId", null);
                //判断是否为空
                if(TextUtils.isEmpty(userId) || TextUtils.isEmpty(sessionId)){
                    //返回不带请求头的请求
                    return chain.proceed(chain.request());
                } else {
                    Request request = chain.request().newBuilder()
                            .addHeader("userId", userId)
                            .addHeader("sessionId", sessionId)
                            .build();
                    return chain.proceed(request);
                }
            }
        };
        //OkHttp
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .readTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .connectTimeout(10, TimeUnit.SECONDS)
                .addInterceptor(httpLoggingInterceptor)
                .addInterceptor(interceptor)
                .build();
        //Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(MyUrl.BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();
        //初始化请求
        mApiService = retrofit.create(ApiService.class);
    }
    private static class Holder{
        private static final NetUtil NET_UTIL = new NetUtil();
    }
    public static NetUtil getInstance() {
        return Holder.NET_UTIL;
    }
    //GET请求方法
    public void getRequest(String url, final Type type, Map<String, Object> map, final NetCallBack netCallBack){
        Observable<ResponseBody> request;
        //判断
        if(map != null){
            request = mApiService.getRequestHaveParams(url, map);
        } else {
            request = mApiService.getRequestNoParams(url);
        }
        //请求
        request.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResponseBody>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }
                    //响应结果
                    @Override
                    public void onNext(ResponseBody responseBody) {
                        try {
                            String string = responseBody.string();
                            Object o = MyApplication.getGson().fromJson(string, type);
                            if(netCallBack != null){
                                netCallBack.onSuccess(o);
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    //响应错误
                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        if(netCallBack !=null){
                            netCallBack.onFail(e.getMessage());
                        }
                    }
                    //响应完成
                    @Override
                    public void onComplete() {
                    }
                });
    }
    //POST请求方法
    public void postRequest(String url, final Type type, Map<String, Object> map, final NetCallBack netCallBack){
        Observable<ResponseBody> request = mApiService.postRequest(url,map);
        //判断
        /*if(map != null){
            request = mApiService.getRequestHaveParams(url, map);
        } else {
            request = mApiService.getRequestNoParams(url);
        }*/
        //请求
        request.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResponseBody>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }
                    //响应结果
                    @Override
                    public void onNext(ResponseBody responseBody) {
                        try {
                            String string = responseBody.string();
                            Object o = MyApplication.getGson().fromJson(string, type);
                            if(netCallBack != null){
                                netCallBack.onSuccess(o);
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    //响应错误
                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        if(netCallBack !=null){
                            netCallBack.onFail(e.getMessage());
                        }
                    }
                    //响应完成
                    @Override
                    public void onComplete() {
                    }
                });
    }
    //网络判断
    public boolean isConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager) MyApplication.getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        if(activeNetworkInfo != null && activeNetworkInfo.isAvailable()){
            return true;
        }
        return false;
    }
    //接口回调（把当前网络工具类的数据回传给Model层）
    public interface NetCallBack<T> {
        void onSuccess(T t);
        void onFail(String err);
    }
}
