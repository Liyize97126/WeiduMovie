package com.bw.movie.activity;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bawei.xtoastlibrary.XToast;
import com.bawei.xtoastlibrary.utils.AnimationUtils;
import com.bw.movie.R;
import com.bw.movie.base.BaseActivity;
import com.bw.movie.base.BasePresenter;
import com.bw.movie.bean.DataBean;
import com.bw.movie.bean.UserInfo;
import com.bw.movie.presenter.PresenterImpl;
import com.bw.movie.url.MyUrl;
import com.bw.movie.util.NetUtil;
import com.bw.movie.util.TimesFormatUtil;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

import static com.bw.movie.api.ApiService.GET;

/**
 * 用户信息页面
 * 李易泽
 * 20200619
 */
public class UserInfoActivity extends BaseActivity {
    //定义
    private ImageView back;
    private SimpleDraweeView headPic;
    private TextView nickName,sex,email;
    private Type type;
    //方法实现
    @Override
    protected boolean isFullScreen() {
        return false;
    }
    @Override
    protected int getLayoutId() {
        return R.layout.activity_user_info;
    }
    @Override
    protected BasePresenter initPresenter() {
        return new PresenterImpl();
    }
    @Override
    protected void initView() {
        //获取ID
        back = findViewById(R.id.back);
        headPic = findViewById(R.id.head_pic);
        nickName = findViewById(R.id.nick_name);
        sex = findViewById(R.id.sex);
        email = findViewById(R.id.email);
        //泛型类处理
        type = new TypeToken<DataBean<UserInfo>>() {
        }.getType();
        //返回界面操作
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    @Override
    protected void startCoding() {
        //判断网络
        if(NetUtil.getInstance().isConnected()){
            //发起请求
            mPresenter.startRequest(GET, MyUrl.GET_USER_INFO_BY_USER_ID,type,null);
        } else {
            //提示
            Toast.makeText(UserInfoActivity.this,"设备未联网，请联网后重试！",Toast.LENGTH_LONG).show();
        }
    }
    @Override
    protected void initResume() {
    }
    @Override
    protected void initPause() {
    }
    @Override
    protected void initDestroy() {
    }
    @Override
    public void onSuccess(Object o) {
        //instanceof判断
        if(o instanceof DataBean){
            //判断是否登录
            if(((DataBean) o).getStatus().equals("9999")){
                //跳转到登录页面
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                return;
            }
            Object result = ((DataBean) o).getResult();
            //判断是否非空
            if(result != null){
                //instanceof判断
                if(result instanceof UserInfo){
                    //设置数据
                    nickName.setText(((UserInfo) result).getNickName());
                    switch (((UserInfo) result).getSex()){
                        case 1:{sex.setText("男");}break;
                        case 2:{sex.setText("女");}break;
                    }
                    email.setText(((UserInfo) result).getEmail());
                    headPic.setImageURI(Uri.parse(((UserInfo) result).getHeadPic()));
                    XToast.create(this,"用户昵称：" + ((UserInfo) result).getNickName() + "\n" +
                            "上次登陆日期：" + TimesFormatUtil.timeFormatFourth(((UserInfo) result).getLastLoginTime()))
                            .setBackgroundColor(Color.parseColor("#AE15D3"))
                            .setTextSize(16)
                            .setTextColor(Color.parseColor("#FFFFFF"))
                            .setDuration(XToast.XTOAST_DURATION_LONG)
                            .setAnimation(AnimationUtils.ANIMATION_DRAWER)
                            .show();
                }
            }
        }
    }
    @Override
    public void onFail(String err) {
    }
}
