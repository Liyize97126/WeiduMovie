package com.bw.movie.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bw.movie.contract.IContract;

/**
 * Fragment基类
 * 李易泽
 * 20200524
 */
public abstract class BaseFragment<P extends BasePresenter> extends Fragment implements IContract.IView {
    //给定以下变量
    //封装一个P层变量
    protected P mPresenter;
    //判断当前Fragment是否可见
    private boolean isUserHint;
    //判断当前fragment里面初始化任务是否执行过
    private boolean isViewLoad;
    //加载的大布局抽成成员变量
    private View mContentView;
    //用来优化网络操作，执行过的耗时操作再次进来不用重新请求了
    private boolean isDataLoad;
    //布局的加载
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //判断
        if(mContentView == null){
            mContentView = createContentView();
        }
        return mContentView;
    }
    //加载视图方法
    private View createContentView() {
        View view;
        if(getFragmentLayoutId() != 0){
            view = getLayoutInflater().inflate(getFragmentLayoutId(),null);
            return view;
        } else {
            //异常
            throw new NumberFormatException("布局数据类型不匹配！");
        }
    }
    //创建视图
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //判断
        if(!isViewLoad){
            initViews(mContentView);
            //初始化Presenter
            if(mPresenter == null){
                mPresenter = initPresenter();
                mPresenter.onAttach(this);
            }
        }
        isViewLoad = true;
        loadData();
    }
    //销毁视图
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        initDestroyView();
    }
    //释放资源
    @Override
    public void onDetach() {
        super.onDetach();
        //判断
        if(mPresenter != null){
            mPresenter.onDeAttach();
            mPresenter = null;
        }
    }
    //判断当前Fragment是否处于可见状态
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        isUserHint = isVisibleToUser;
        loadData();
    }
    //加载数据的方法（懒加载）
    private void loadData() {
        //判断
        if(isUserHint && isViewLoad && (!isDataLoad)){
            //加载数据
            lazyLoad();
            isDataLoad = true;
        }
    }
    //方法实现
    protected abstract int getFragmentLayoutId();
    protected abstract void initViews(View mContentView);
    protected abstract P initPresenter();
    protected abstract void lazyLoad();
    protected abstract void initDestroyView();
}
