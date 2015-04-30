package com.lwb.common.option;

import com.google.common.collect.ImmutableMap;

import java.util.Map;

/**
 * @autor: Lu Weibiao
 * Date: 2015/4/4 10:22
 */
public class OptionMap {
    public static final String NAME = "name";
    public static final String EN = "en";
    public static final String ST = "st";

    public static Map<String, String> setMap(final String name, final String en,final String st) {
        return ImmutableMap.of(OptionMap.NAME, name, OptionMap.EN, en, OptionMap.ST, st);
    }
}
