package com.bw.movie.presenter;

import com.bw.movie.base.BasePresenter;
import com.bw.movie.contract.IContract;
import com.bw.movie.model.ModelImpl;

import java.io.File;
import java.lang.reflect.Type;
import java.util.Map;

import static com.bw.movie.api.ApiService.GET;
import static com.bw.movie.api.ApiService.POST;

/**
 * Presenter实现类
 * 李易泽
 * 20200523
 */
public class PresenterImpl extends BasePresenter {
    //初始化Model
    private ModelImpl mModelImpl;
    @Override
    protected void initModel() {
        mModelImpl = new ModelImpl();
    }
    //请求
    @Override
    public void startRequest(int method, String url, Type type, Map<String, Object> map) {
        switch (method){
            case GET: {
                //GET请求
                mModelImpl.getRequest(url, type, map, new IContract.ModelCallBack() {
                    @Override
                    public void onSuccess(Object o) {
                        getView().onSuccess(o);
                    }
                    @Override
                    public void onFail(String err) {
                        getView().onFail(err);
                    }
                });
            }break;
            case POST: {
                //POST请求
                mModelImpl.postRequest(url, type, map, new IContract.ModelCallBack() {
                    @Override
                    public void onSuccess(Object o) {
                        getView().onSuccess(o);
                    }
                    @Override
                    public void onFail(String err) {
                        getView().onFail(err);
                    }
                });
            }break;
        }
    }
    //图片上传请求
    @Override
    public void startRequest(String url, Type type, File file) {
        //上传图片请求
        mModelImpl.uploadImageRequest(url, type, file, new IContract.ModelCallBack() {
            @Override
            public void onSuccess(Object o) {
                getView().onSuccess(o);
            }
            @Override
            public void onFail(String err) {
                getView().onFail(err);
            }
        });
    }
}
