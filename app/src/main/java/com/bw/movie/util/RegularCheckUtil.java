package com.bw.movie.util;

/**
 * 正则校验工具类
 * 李易泽
 * 20200604
 */
public class RegularCheckUtil {
    //校验邮箱地址
    public static boolean checkEmail(String email) {
        return email.matches("^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$");
    }
}
