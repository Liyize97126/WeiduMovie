package com.bw.movie.model;

import com.bw.movie.contract.IContract;
import com.bw.movie.util.NetUtil;

import java.lang.reflect.Type;
import java.util.Map;

/**
 * Model层实现类
 * 李易泽
 * 20200523
 */
public class ModelImpl implements IContract.IModel {
    @Override
    public void getRequest(String url, Type type, Map<String, Object> map, final IContract.ModelCallBack modelCallBack) {
        //发起请求
        NetUtil.getInstance().getRequest(url, type, map, new NetUtil.NetCallBack() {
            @Override
            public void onSuccess(Object o) {
                if(modelCallBack != null){
                    modelCallBack.onSuccess(o);
                }
            }
            @Override
            public void onFail(String err) {
                if(modelCallBack != null){
                    modelCallBack.onFail(err);
                }
            }
        });
    }
    @Override
    public void postRequest(String url, Type type, Map<String, Object> map, final IContract.ModelCallBack modelCallBack) {
        //发起请求
        NetUtil.getInstance().postRequest(url, type, map, new NetUtil.NetCallBack() {
            @Override
            public void onSuccess(Object o) {
                if(modelCallBack != null){
                    modelCallBack.onSuccess(o);
                }
            }
            @Override
            public void onFail(String err) {
                if(modelCallBack != null){
                    modelCallBack.onFail(err);
                }
            }
        });
    }
}
