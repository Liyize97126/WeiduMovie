package com.bw.movie.contract;

import java.util.Map;

/**
 * 契约类接口
 * 李易泽
 * 20200523
 */
public interface IContract {
    //Model
    interface IModel {
        //Get
        void getRequest(String url, Class cls, Map<String, Object> map, ModelCallBack modelCallBack);
        //Post
        void postRequest(String url, Class cls, Map<String, Object> map, ModelCallBack modelCallBack);
    }
    //Presenter
    interface IPresenter {
        void startRequest(int method, String url, Class cls, Map<String, Object> map);
    }
    //View
    interface IView<T> {
        void onSuccess(T t);
        void onFail(String err);
    }
    //Model层回调
    interface ModelCallBack<T> {
        void onSuccess(T t);
        void onFail(String err);
    }
}
