package com.bw.movie.activity;

import android.view.View;
import android.widget.TextView;

import com.bw.movie.R;
import com.bw.movie.base.BaseActivity;
import com.bw.movie.base.BasePresenter;

/**
 * 注册页面
 * 李易泽
 * 20200602
 */
public class RegisterActivity extends BaseActivity {
    //定义
    private TextView jumpLoginPage;
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
        return null;
    }
    @Override
    protected void initView() {
        //获取ID
        jumpLoginPage = findViewById(R.id.jump_login_page);
        //点击事件
        jumpLoginPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //结束页面
                finish();
            }
        });
    }
    @Override
    protected void startCoding() {
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
    }
    @Override
    public void onFail(String err) {
    }
}
