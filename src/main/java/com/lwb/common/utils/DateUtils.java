package com.lwb.common.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 日期工具(线程安全)
 * @autor:Lu Weibiao Date: 2015/3/19 9:44
 */
public class DateUtils {
    private final static Map<String, SimpleDateFormat> formatterMap = new HashMap<String,SimpleDateFormat>();

    /**
     * 按yy-MM-dd格式从日期字符串解析出日期对象
     * @param source
     * @return
     */
    public static Date parseDate(String source){
        return parse(source,"yy-MM-dd");
    }
    /**
     * 从字符串解析出日期对象
     * @param source      日期字符串
     * @param pattern     日期模式
     * @param defaultDate 如果无法解析，则返回该值
     * @return
     */
    public static Date parse(String source, String pattern, Date defaultDate) {
        Date date;
        try{
            date = baseParse(source,pattern);
        }catch (Exception e){
            date = defaultDate;
        }
        return date;
    }

    /**
     * 从字符串解析出日期对象
     * 如果无法解析，抛出RuntimeException
     * @param source 日期字符串
     * @param pattern 日期模式
     * @return
     */
    public static Date parse(String source, String pattern) {
        Date date;
        try{
            date = baseParse(source, pattern);
        } catch (ParseException e){
            throw new RuntimeException(e);
        }
        return date;
    }

    /**
     * 从字符串解析出日期对象
     * 如果无法解析，抛出checked异常
     * @param source
     * @param pattern
     * @return
     * @throws java.text.ParseException
     */
    private static Date baseParse(String source, String pattern) throws ParseException{
        SimpleDateFormat formatter = formatterMap.get(pattern);
        if (formatter == null) {
            synchronized (formatterMap){
                formatter = formatterMap.get(pattern);
                if(formatter == null){
                    formatter = new SimpleDateFormat(pattern);
                    formatterMap.put(pattern, formatter);
                }
            }
        }
        Date date = formatter.parse(source);
        return date;
    }
}
