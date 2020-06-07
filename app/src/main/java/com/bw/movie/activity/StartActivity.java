package com.bw.movie.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.bawei.xtoastlibrary.XToast;
import com.bawei.xtoastlibrary.utils.AnimationUtils;
import com.bw.movie.R;
import com.bw.movie.base.BaseActivity;
import com.bw.movie.base.BasePresenter;
import com.bw.movie.bean.DataBean;
import com.bw.movie.bean.UserInfo;
import com.bw.movie.presenter.PresenterImpl;
import com.bw.movie.url.MyUrl;
import com.bw.movie.util.MyApplication;
import com.bw.movie.util.NetUtil;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import static com.bw.movie.api.ApiService.GET;

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
    private Type type;
    private Map<String,Object> map;
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
        return new PresenterImpl();
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
        map = new HashMap<>();
        //添加数据
        pictureList.add(R.drawable.ydy_0);
        pictureList.add(R.drawable.ydy_1);
        pictureList.add(R.drawable.ydy_2);
        pictureList.add(R.drawable.ydy_3);
        pictureList.add(R.drawable.ydy_4);
        //泛型类处理
        type = new TypeToken<DataBean<UserInfo>>() {
        }.getType();
    }
    //其它代码
    @Override
    protected void startCoding() {
        //用户合法性校验
        int userId = sharedPreferences.getInt("userId", 00000);
        if(userId != 00000){
            //获取用户信息
            map.put("userId",userId);
            //判断网络
            if(NetUtil.getInstance().isConnected()){
                //请求
                mPresenter.startRequest(GET, MyUrl.GET_USER_INFO_BY_USER_ID,type,map);
            }
        }
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
        //instanceof判断
        if(o instanceof DataBean){
            //判断
            if(((DataBean) o).getStatus().equals("9999")){
                //提示
                XToast.create(this,"用户信息已失效，请重新登录")
                        .setBackgroundColor(Color.parseColor("#AE15D3"))
                        .setTextSize(18)
                        .setTextColor(Color.parseColor("#FFFFFF"))
                        .setDuration(XToast.XTOAST_DURATION_SHORT)
                        .setAnimation(AnimationUtils.ANIMATION_SCALE)
                        .show();
                edit.remove("userId");
                edit.remove("sessionId");
                edit.remove("headPic");
                edit.remove("nickName");
                edit.remove("loginValidity");
                edit.commit();
            } else if (((DataBean) o).getStatus().equals("0000")) {
                //提示
                Object result = ((DataBean) o).getResult();
                if(result instanceof UserInfo){
                    XToast.create(this,"欢迎 " + ((UserInfo) result).getNickName() + " 回来")
                            .setBackgroundColor(Color.parseColor("#AE15D3"))
                            .setTextSize(18)
                            .setTextColor(Color.parseColor("#FFFFFF"))
                            .setDuration(XToast.XTOAST_DURATION_SHORT)
                            .setAnimation(AnimationUtils.ANIMATION_SCALE)
                            .show();
                }
            } else {
                //提示
                Toast.makeText(this,((DataBean) o).getMessage(),Toast.LENGTH_LONG).show();
            }
        }
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
