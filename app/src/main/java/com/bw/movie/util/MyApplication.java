package com.bw.movie.util;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

/**
 * 全局设置类
 * 李易泽
 * 20200523
 */
public class MyApplication extends Application {
    //定义
    private static Context context;
    private static final Gson GSON = new Gson();
    private static SharedPreferences sharedPreferences;
    public static Context getContext() {
        return context;
    }
    public static Gson getGson() {
        return GSON;
    }
    public static SharedPreferences getSharedPreferences() {
        return sharedPreferences;
    }
    //初始化
    @Override
    public void onCreate() {
        super.onCreate();
        //Context
        context = this;
        //SharedPreferences
        sharedPreferences = getSharedPreferences("info", MODE_PRIVATE);
    }
}
