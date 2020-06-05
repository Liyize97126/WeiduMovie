package com.bw.movie.activity;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bw.movie.R;
import com.bw.movie.base.BaseActivity;
import com.bw.movie.base.BasePresenter;

/**
 * 写影评
 * 李易泽
 * 20200605
 */
public class WriteEvaluationActivity extends BaseActivity {
    //定义
    private ImageView back;
    private TextView movieName;
    @Override
    protected boolean isFullScreen() {
        return false;
    }
    @Override
    protected int getLayoutId() {
        return R.layout.activity_write_evaluation;
    }
    @Override
    protected BasePresenter initPresenter() {
        return null;
    }
    @Override
    protected void initView() {
        //初始化
        back = findViewById(R.id.back);
        movieName = findViewById(R.id.movie_name);
        //点击返回
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //设置值
        movieName.setText(getIntent().getStringExtra("movieName"));
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
