package com.bw.movie.base;

import com.bw.movie.contract.IContract;

import java.lang.ref.WeakReference;

/**
 * Presenter基类
 * 李易泽
 * 20200523
 */
public abstract class BasePresenter<V extends IContract.IView> implements IContract.IPresenter {
    //定义弱引用
    private WeakReference<V> weakReference;
    //P层绑定View
    protected void onAttach(V v) {
        weakReference = new WeakReference<>(v);
    }
    //解绑
    protected void onDeAttach() {
        //判断
        if(weakReference != null && weakReference.get() != null) {
            weakReference.clear();
            weakReference = null;
        }
    }
    //获取View对象
    protected V getView() {
        return weakReference.get();
    }
    //初始化Model
    public BasePresenter() {
        initModel();
    }
    protected abstract void initModel();
}
