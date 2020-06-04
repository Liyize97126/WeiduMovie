package com.bw.movie.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.bw.movie.R;
import com.bw.movie.base.BaseActivity;
import com.bw.movie.base.BasePresenter;
import com.bw.movie.bean.DataBean;
import com.bw.movie.presenter.PresenterImpl;
import com.bw.movie.url.MyUrl;
import com.bw.movie.util.EncryptUtil;
import com.bw.movie.util.NetUtil;
import com.bw.movie.util.RegularCheckUtil;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import static com.bw.movie.activity.LoginActivity.RESULT_CODE;
import static com.bw.movie.api.ApiService.POST;

/**
 * 注册页面
 * 李易泽
 * 20200602
 */
public class RegisterActivity extends BaseActivity {
    //定义
    private ImageView back;
    private EditText nickName,email,pwd,code;
    private Button sendOutCodeDo,registerDo;
    private TextView jumpLoginPage;
    private String emailText,pwdText;
    private Type type;
    private Map<String, Object> map;
    private int flag = -1;
    private int time = 120;
    private boolean isRequestCode = true;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            //判断
            if(msg.what == 0){
                if(time > 0){
                    time--;
                    sendOutCodeDo.setText(time + " S");
                    handler.sendEmptyMessageDelayed(0,1000);
                } else {
                    handler.removeCallbacksAndMessages(null);
                    sendOutCodeDo.setText("获取验证码");
                    time = 120;
                    isRequestCode = true;
                }
            }
        }
    };
    @Override
    protected boolean isFullScreen() {
        return false;
    }
    @Override
    protected int getLayoutId() {
        return R.layout.activity_register;
    }
    @Override
    protected BasePresenter initPresenter() {
        return new PresenterImpl();
    }
    @Override
    protected void initView() {
        //初始化
        back = findViewById(R.id.back);
        nickName = findViewById(R.id.nick_name);
        email = findViewById(R.id.email);
        pwd = findViewById(R.id.pwd);
        code = findViewById(R.id.code);
        sendOutCodeDo = findViewById(R.id.send_out_code_do);
        registerDo = findViewById(R.id.register_do);
        jumpLoginPage = findViewById(R.id.jump_login_page);
        //泛型类处理
        type = new TypeToken<DataBean>() {
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
        sendOutCodeDo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //网络判断
                if(isRequestCode){
                    //判断是否符合发送条件
                    if(NetUtil.getInstance().isConnected()){
                        //获取邮箱内容
                        String emailRequest = email.getText().toString();
                        //判断
                        if(TextUtils.isEmpty(nickName.getText())){
                            //提示
                            Toast.makeText(RegisterActivity.this,"请输入一个昵称！",Toast.LENGTH_LONG).show();
                            return;
                        }
                        if(TextUtils.isEmpty(emailRequest)){
                            //提示
                            Toast.makeText(RegisterActivity.this,"请输入合法的邮箱！",Toast.LENGTH_LONG).show();
                            return;
                        }
                        if(!RegularCheckUtil.checkEmail(emailRequest)){
                            //提示
                            Toast.makeText(RegisterActivity.this,"输入的邮箱不合法！",Toast.LENGTH_LONG).show();
                            return;
                        }
                        if(TextUtils.isEmpty(pwd.getText())){
                            //提示
                            Toast.makeText(RegisterActivity.this,"请设置您的密码！",Toast.LENGTH_LONG).show();
                            return;
                        }
                        //封装数据
                        map = new HashMap<>();
                        map.put("email",emailRequest);
                        //请求
                        flag = 0;
                        mPresenter.startRequest(POST, MyUrl.SEND_OUT_EMAIL_CODE,type,map);
                    } else {
                        //提示
                        Toast.makeText(RegisterActivity.this,"网络似乎开小差了，请检查下网络吧！",Toast.LENGTH_LONG).show();
                    }
                } else {
                    //提示
                    Toast.makeText(RegisterActivity.this,"验证码已发送，请不要重复操作！",Toast.LENGTH_LONG).show();
                }
            }
        });
        registerDo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //网络判断
                if(NetUtil.getInstance().isConnected()){
                    //获取信息
                    String z01 = nickName.getText().toString();
                    String z02 = email.getText().toString();
                    String z03 = pwd.getText().toString();
                    String z04 = code.getText().toString();
                    //判断
                    if(TextUtils.isEmpty(z01)){
                        //提示
                        Toast.makeText(RegisterActivity.this,"请输入一个昵称！",Toast.LENGTH_LONG).show();
                        return;
                    }
                    if(TextUtils.isEmpty(z02)){
                        //提示
                        Toast.makeText(RegisterActivity.this,"请输入合法的邮箱！",Toast.LENGTH_LONG).show();
                        return;
                    }
                    if(!RegularCheckUtil.checkEmail(z02)){
                        //提示
                        Toast.makeText(RegisterActivity.this,"输入的邮箱不合法！",Toast.LENGTH_LONG).show();
                        return;
                    }
                    if(TextUtils.isEmpty(z03)){
                        //提示
                        Toast.makeText(RegisterActivity.this,"请设置您的密码！",Toast.LENGTH_LONG).show();
                        return;
                    }
                    if(TextUtils.isEmpty(z04)){
                        //提示
                        Toast.makeText(RegisterActivity.this,"请输入验证码！",Toast.LENGTH_LONG).show();
                        return;
                    }
                    //对密码加密
                    String encrypt = EncryptUtil.encrypt(z03);
                    //设置参数
                    map = new HashMap<>();
                    map.put("nickName",z01);
                    map.put("email",z02);
                    map.put("pwd",encrypt);
                    map.put("code",z04);
                    emailText = z02;
                    pwdText = z03;
                    //发起请求
                    flag = 1;
                    mPresenter.startRequest(POST, MyUrl.REGISTER,type,map);
                } else {
                    //提示
                    Toast.makeText(RegisterActivity.this,"网络似乎开小差了，请检查下网络吧！",Toast.LENGTH_LONG).show();
                }
            }
        });
        jumpLoginPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //判断状态值
                if(flag == 1){
                    //调用回传值方法
                    getUserInfo();
                } else {
                    //结束页面
                    finish();
                }
            }
        });
    }
    //封装统一回传值方法
    private void getUserInfo() {
        Intent intent = new Intent();
        intent.putExtra("email",emailText);
        intent.putExtra("pwd",pwdText);
        setResult(RESULT_CODE,intent);
        finish();
    }
    @Override
    protected void initResume() {
    }
    @Override
    protected void initPause() {
    }
    @Override
    protected void initDestroy() {
        //停止计时
        handler.removeCallbacksAndMessages(null);
    }
    @Override
    public void onSuccess(Object o) {
        //instanceof判断
        if(o instanceof DataBean){
            switch (flag){
                case 0 :{
                    //判断验证码是否发送成功
                    if(((DataBean) o).getStatus().equals("0000")){
                        //提示
                        Toast.makeText(this,"验证码发送成功，请留意您的邮箱！",Toast.LENGTH_LONG).show();
                        isRequestCode = false;
                        sendOutCodeDo.setText(time + " S");
                        handler.sendEmptyMessageDelayed(0,1000);
                    } else {
                        //提示
                        Toast.makeText(this,((DataBean) o).getMessage(),Toast.LENGTH_LONG).show();
                        flag = -1;
                    }
                }break;
                case 1 :{
                    //判断是否注册成功
                    if(((DataBean) o).getStatus().equals("0000")){
                        //提示
                        Toast.makeText(this,((DataBean) o).getMessage(),Toast.LENGTH_LONG).show();
                        //调用回传值方法
                        getUserInfo();
                    } else if(((DataBean) o).getStatus().equals("1005")){
                        //提示
                        Toast.makeText(this,"该邮箱已注册，您可以直接去登录！",Toast.LENGTH_LONG).show();
                    } else if(((DataBean) o).getStatus().equals("1001")){
                        //提示
                        Toast.makeText(this,"验证码已过期，请重新获取！",Toast.LENGTH_LONG).show();
                        flag = -1;
                    } else {
                        //提示
                        Toast.makeText(this,((DataBean) o).getMessage(),Toast.LENGTH_LONG).show();
                        flag = -1;
                    }
                }break;
            }
        }
    }
    @Override
    public void onFail(String err) {
    }
}
