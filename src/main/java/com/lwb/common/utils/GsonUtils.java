package com.lwb.common.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * @autor: Lu Weibiao
 * Date: 2015/4/7 16:01
 */
public class GsonUtils {
    private static final Gson gson = new Gson();
    private static final Gson gsonWithNull = new GsonBuilder().serializeNulls().create();
    public static Gson getInstance(){
        return gson;
    }
    public static Gson getGsonWithNull(){
        return gsonWithNull;
    }
}
