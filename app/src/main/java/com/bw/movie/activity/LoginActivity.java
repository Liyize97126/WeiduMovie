package com.bw.movie.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.bw.movie.R;
import com.bw.movie.base.BaseActivity;
import com.bw.movie.base.BasePresenter;
import com.bw.movie.bean.DataBean;
import com.bw.movie.bean.LoginInfo;
import com.bw.movie.presenter.PresenterImpl;
import com.bw.movie.url.MyUrl;
import com.bw.movie.util.EncryptUtil;
import com.bw.movie.util.MyApplication;
import com.bw.movie.util.NetUtil;
import com.bw.movie.util.RegularCheckUtil;
import com.google.gson.reflect.TypeToken;

import org.greenrobot.eventbus.EventBus;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import static com.bw.movie.api.ApiService.POST;
import static com.bw.movie.api.ApiService.REQUEST_CODE;

/**
 * 登录页面
 * 李易泽
 * 20200602
 */
public class LoginActivity extends BaseActivity {
    //定义
    public static final int RESULT_CODE = 200;
    private ImageView back;
    private EditText email, pwd;
    private Button forgetPassword, loginDo;
    private TextView jumpRegisterPage;
    private ImageButton wechatLoginDo;
    private Type loginInfoType;
    private Map<String, Object> loginInfoMap;
    //方法实现
    @Override
    protected boolean isFullScreen() {
        return false;
    }
    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }
    @Override
    protected BasePresenter initPresenter() {
        return new PresenterImpl();
    }
    @Override
    protected void initView() {
        //初始化
        back = findViewById(R.id.back);
        email = findViewById(R.id.email);
        pwd = findViewById(R.id.pwd);
        forgetPassword = findViewById(R.id.forget_password);
        loginDo = findViewById(R.id.login_do);
        jumpRegisterPage = findViewById(R.id.jump_register_page);
        wechatLoginDo = findViewById(R.id.wechat_login_do);
        loginInfoMap = new HashMap<>();
        //泛型类处理
        loginInfoType = new TypeToken<DataBean<LoginInfo>>() {
        }.getType();
    }
    @Override
    protected void startCoding() {
        //点击事件
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        loginDo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //网络判断
                if(NetUtil.getInstance().isConnected()){
                    //获取信息
                    String z01 = email.getText().toString();
                    String z02 = pwd.getText().toString();
                    //判断
                    if(TextUtils.isEmpty(z01)){
                        //提示
                        Toast.makeText(LoginActivity.this,"请输入合法的邮箱！",Toast.LENGTH_LONG).show();
                        return;
                    }
                    if(!RegularCheckUtil.checkEmail(z01)){
                        //提示
                        Toast.makeText(LoginActivity.this,"输入的邮箱不合法！",Toast.LENGTH_LONG).show();
                        return;
                    }
                    if(TextUtils.isEmpty(z02)){
                        //提示
                        Toast.makeText(LoginActivity.this,"请输入密码！",Toast.LENGTH_LONG).show();
                        return;
                    }
                    //对密码加密
                    String encrypt = EncryptUtil.encrypt(z02);
                    //设置参数
                    loginInfoMap.put("email", z01);
                    loginInfoMap.put("pwd", encrypt);
                    //发起请求
                    mPresenter.startRequest(POST, MyUrl.LOGIN,loginInfoType,loginInfoMap);
                } else {
                    //提示
                    Toast.makeText(LoginActivity.this,"网络似乎开小差了，请检查下网络吧！",Toast.LENGTH_LONG).show();
                }
            }
        });
        jumpRegisterPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //跳转至注册
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivityForResult(intent,REQUEST_CODE);
            }
        });
        wechatLoginDo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //提示
                Toast.makeText(LoginActivity.this,"正在开发中，敬请期待！",Toast.LENGTH_LONG).show();
            }
        });
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
            //提示
            Toast.makeText(this,((DataBean) o).getMessage(),Toast.LENGTH_LONG).show();
            //判断是否登录成功
            if(((DataBean) o).getStatus().equals("0000")){
                //获取用户登录信息
                Object result = ((DataBean) o).getResult();
                //instanceof判断
                if(result instanceof LoginInfo){
                    //写入登录数据
                    SharedPreferences.Editor edit = MyApplication.getSharedPreferences().edit();
                    edit.putInt("userId",((LoginInfo) result).getUserId());
                    edit.putString("sessionId",((LoginInfo) result).getSessionId());
                    edit.putString("headPic",((LoginInfo) result).getUserInfo().getHeadPic());
                    edit.putString("nickName",((LoginInfo) result).getUserInfo().getNickName());
                    edit.putBoolean("loginValidity",true);
                    edit.commit();
                    //发送粘性事件
                    EventBus.getDefault().postSticky(((LoginInfo) result).getUserInfo());
                    //结束，返回我的页面
                    finish();
                }
            }
        }
    }
    @Override
    public void onFail(String err) {
    }
    //回传事件
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //判断
        if(requestCode == REQUEST_CODE && resultCode == RESULT_CODE && data != null){
            //设置值
            email.setText(data.getStringExtra("email"));
            pwd.setText(data.getStringExtra("pwd"));
        }
    }
}
