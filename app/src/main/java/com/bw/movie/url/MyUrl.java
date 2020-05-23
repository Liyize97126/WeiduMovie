package com.bw.movie.url;

/**
 * 接口地址
 * 李易泽
 * 20200523
 */
public interface MyUrl {
    //统一入口地址
    //String BASE_URL = "http://172.17.8.100/";
    String BASE_URL = "http://mobile.bwstudent.com/";
    //发送邮箱验证码
    String SEND_OUT_EMAIL_CODE = "movieApi/user/v2/sendOutEmailCode";
    //注册
    String REGISTER = "movieApi/user/v2/register";
    //登录
    String LOGIN = "movieApi/user/v2/login";
}
