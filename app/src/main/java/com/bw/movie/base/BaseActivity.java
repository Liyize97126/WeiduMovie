package com.bw.movie.base;

import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bw.movie.contract.IContract;

/**
 * 页面基类
 * 李易泽
 * 20200523
 */
public abstract class BaseActivity<P extends BasePresenter> extends AppCompatActivity implements IContract.IView {
    //在BaseActivity里面封装一个P层变量
    protected P mPresenter;
    //创建视图
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(isFullScreen()){
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
        //判断布局是否合法
        if(getLayoutId() != 0){
            //布局
            setContentView(getLayoutId());
            //界面优化
            getSupportActionBar().hide();
            //初始化控件
            initView();
            //数据请求
            initData();
            //初始化Presenter
            if(mPresenter != null){
                mPresenter = initPresenter();
                mPresenter.onAttach(this);
            }
            //其它代码
            startCoding();
        } else {
            //异常
            throw new NumberFormatException("布局数据类型不匹配！");
        }
    }
    //Resume
    @Override
    protected void onResume() {
        super.onResume();
        initResume();
    }
    //Pause
    @Override
    protected void onPause() {
        super.onPause();
        initPause();
    }
    //释放资源
    @Override
    protected void onDestroy() {
        super.onDestroy();
        initDestroy();
        //判断
        if(mPresenter != null){
            mPresenter.onDeAttach();
            mPresenter = null;
        }
    }
    //方法实现
    protected abstract boolean isFullScreen();
    protected abstract int getLayoutId();
    protected abstract P initPresenter();
    protected abstract void initView();
    protected void initData(){
    }
    protected abstract void startCoding();
    protected abstract void initResume();
    protected abstract void initPause();
    protected abstract void initDestroy();
}
