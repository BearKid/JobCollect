package com.lwb.common.utils;

import org.apache.log4j.Logger;

/**
 * @autor: Lu Weibiao
 * Date: 2015/4/3 16:23
 */
public class IntegerUtils {
    private final static Logger logger = Logger.getLogger(IntegerUtils.class);

    public static Integer parse(String source, Integer defaultValue){
        Integer value = defaultValue;
        try{
            value = Integer.valueOf(source);
        }catch (Exception e){
            logger.debug(e);
        }
        return value;
    }
}
