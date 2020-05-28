package com.bw.movie.util;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.facebook.cache.disk.DiskCacheConfig;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
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
        //Fresco
        //设置磁盘缓存
        DiskCacheConfig diskCacheConfig = DiskCacheConfig.newBuilder(this)
                //说明缓存大小不能超过10M
                .setMaxCacheSize(1024 * 1024 * 10)
                //设置缓存路径
                .setBaseDirectoryPath(getExternalCacheDir().getAbsoluteFile())
                //新建一个文件夹
                .setBaseDirectoryName("FrescoLoadingData")
                .build();
        //定义ImagePipelineConfig
        ImagePipelineConfig build = ImagePipelineConfig.newBuilder(this)
                .setMainDiskCacheConfig(diskCacheConfig)
                .build();
        //初始化Fresco
        Fresco.initialize(this,build);
    }
}
