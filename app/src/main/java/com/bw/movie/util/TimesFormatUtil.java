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
    private static final SimpleDateFormat SIMPLE_DATE_FORMAT_THIRD = new SimpleDateFormat("E MM-dd");
    private static final SimpleDateFormat SIMPLE_DATE_FORMAT_THIRD_STYLE_TODAY = new SimpleDateFormat("今天 MM-dd");
    private static final SimpleDateFormat SIMPLE_DATE_FORMAT_THIRD_STYLE_TOMORROW = new SimpleDateFormat("明天 MM-dd");
    private static final SimpleDateFormat SIMPLE_DATE_FORMAT_THIRD_STYLE_AFTER_TOMORROW = new SimpleDateFormat("后天 MM-dd");
    //格式转换
    public static String timeFormatFirst(long date) {
        return SIMPLE_DATE_FORMAT_FIRST.format(new Date(date));
    }
    public static String timeFormatSecond(long date) {
        return SIMPLE_DATE_FORMAT_SECOND.format(new Date(date));
    }
    public static String timeFormatThird(long date) {
        return SIMPLE_DATE_FORMAT_THIRD.format(new Date(date));
    }
    public static String timeFormatThirdStyleToday(long date) {
        return SIMPLE_DATE_FORMAT_THIRD_STYLE_TODAY.format(new Date(date));
    }
    public static String timeFormatThirdStyleTomorrow(long date) {
        return SIMPLE_DATE_FORMAT_THIRD_STYLE_TOMORROW.format(new Date(date));
    }
    public static String timeFormatThirdStyleAfterTomorrow(long date) {
        return SIMPLE_DATE_FORMAT_THIRD_STYLE_AFTER_TOMORROW.format(new Date(date));
    }
}
