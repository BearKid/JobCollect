package com.lwb.common.utils;

import org.apache.log4j.Logger;

import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @autor: Lu Weibiao
 * Date: 2015/4/2 16:42
 */
public class HtmlParseUtils {
    private static final Logger logger = Logger.getLogger(HtmlParseUtils.class);
    public static final String htmlTagPattern = "(?i)(</?(?!br/|BR/).+?>)|(<BR/>\\s*?<BR/>)|(&nbsp;&nbsp;)";

    public static List<String> parseValues(String htmlContent, String startPattern, String endPattern) {
        List<String> values = new LinkedList<String>();
        String pattern = startPattern + "([\\s\\S]*?)" + endPattern;
        Matcher matcher = Pattern.compile(pattern).matcher(htmlContent);
        String value = null;
        while (matcher.find()) {
            value = matcher.group(1);
            values.add(value);
        }
        return values;
    }

    public static String parseToString(String htmlContent, String startPattern, String endPattern) {
        String pattern = startPattern + "([\\s\\S]*?)" + endPattern;
        Matcher matcher = Pattern.compile(pattern).matcher(htmlContent);
        String value = null;
        if (matcher.find()) {
            value = matcher.group(1);
            value = value.replaceAll("联系我时，请说是在江门领航人才网上看到的，谢谢！","").replaceAll("领航人才网","智通人才网").replaceAll("领航","智通");
        }
        return value;
    }

    public static boolean containsText(String html, String text) {
        return html.contains(text);
    }

    public static Integer parseToInt(String html, String startPattern, String endPattern) {
        String pattern = startPattern + "(\\d+?)" + endPattern;
        Matcher matcher = Pattern.compile(pattern).matcher(html);
        Integer value = null;
        String valueStr = null;
        if (matcher.find()) {
            try {
                valueStr = matcher.group(1);
                value = Integer.valueOf(valueStr);
            } catch (Exception e) {
                logger.debug("无法解析" + valueStr, e);
            }
        }
        return value;
    }
}
