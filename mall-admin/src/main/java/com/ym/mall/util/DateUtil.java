package com.ym.mall.util;

import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 时间的工具类
 * @author matao
 * @create 2019-08-03 15:40
 */
@Component
public class DateUtil {

    /**
     * 时间格式为yyyy-MM-dd
     */
    private static final String  DATE_PATTERN = "yyyy-MM-dd";
    /**
     * 时间格式为yyyy-MM-dd
     */
    private static final String  DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";

    public static String  formatDate(Date date){
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_TIME_PATTERN);
        String dateTime = dateFormat.format(date);
        return dateTime;
    }
}
