package com.bw.movie.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;

import com.bw.movie.R;
import com.bw.movie.base.BaseActivity;
import com.bw.movie.base.BasePresenter;
import com.bw.movie.util.MyApplication;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 引导页
 * 李易泽
 * 20200523
 */
public class StartActivity extends BaseActivity {
    //定义
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor edit;
    private LinearLayout ldyBackground;
    private List<Integer> pictureList;
    private int time = 3;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            //判断
            if(msg.what == 0) {
                //判断时间
                if(time > 0) {
                    //减少时间
                    time--;
                    //重新发送
                    handler.sendEmptyMessageDelayed(0,1000);
                } else {
                    //跳转页面
                    jumpPage();
                }
            }
        }
    };
    //方法实现
    //是否全屏
    @Override
    protected boolean isFullScreen() {
        return true;
    }
    //布局ID
    @Override
    protected int getLayoutId() {
        return R.layout.activity_start;
    }
    //初始化Presenter
    @Override
    protected BasePresenter initPresenter() {
        return null;
    }
    //初始化视图控件
    @Override
    protected void initView() {
        //获取id
        ldyBackground = findViewById(R.id.ldy_background);
        //获取SharedPreferences
        sharedPreferences = MyApplication.getSharedPreferences();
        //初始化Editor对象
        edit = sharedPreferences.edit();
        //初始化集合容器
        pictureList = new ArrayList<>();
        //添加数据
        pictureList.add(R.drawable.ydy_0);
        pictureList.add(R.drawable.ydy_1);
        pictureList.add(R.drawable.ydy_2);
        pictureList.add(R.drawable.ydy_3);
        pictureList.add(R.drawable.ydy_4);
    }
    //其它代码
    @Override
    protected void startCoding() {
        //判断
        if(!sharedPreferences.getBoolean("isFirstOpen",true)){
            //获取要展示的图片
            Integer integer = pictureList.get(new Random().nextInt(pictureList.size()));
            //设置要展示的背景
            ldyBackground.setBackgroundResource(integer);
        } else {
            //写入状态
            edit.putBoolean("isFirstOpen",false);
            edit.commit();
        }
        //发送消息
        handler.sendEmptyMessageDelayed(0,1000);
    }
    //Resume
    @Override
    protected void initResume() {
    }
    //Pause
    @Override
    protected void initPause() {
    }
    //释放资源
    @Override
    protected void initDestroy() {
        //清空消息队列
        handler.removeCallbacksAndMessages(null);
    }
    //请求成功方法
    @Override
    public void onSuccess(Object o) {
    }
    //请求失败方法
    @Override
    public void onFail(String err) {
    }
    //跳转
    public void startGo(View view) {
        //跳转页面
        jumpPage();
    }
    //封装跳转页面方法
    private void jumpPage(){
        //清空消息队列
        handler.removeCallbacksAndMessages(null);
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
        finish();
    }
}
