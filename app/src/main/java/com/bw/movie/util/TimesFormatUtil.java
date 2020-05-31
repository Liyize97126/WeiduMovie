package com.bw.movie.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 时间格式转换工具类
 * 李易泽
 * 20200529
 */
public class TimesFormatUtil {
    //定义
    private static final SimpleDateFormat SIMPLE_DATE_FORMAT_FIRST = new SimpleDateFormat("yyyy-MM-dd");
    private static final SimpleDateFormat SIMPLE_DATE_FORMAT_SECOND = new SimpleDateFormat("MM月dd日上映");
    //格式转换
    public static String timeFormatFirst(long date) {
        return SIMPLE_DATE_FORMAT_FIRST.format(new Date(date));
    }
    public static String timeFormatSecond(long date) {
        return SIMPLE_DATE_FORMAT_SECOND.format(new Date(date));
    }
}
