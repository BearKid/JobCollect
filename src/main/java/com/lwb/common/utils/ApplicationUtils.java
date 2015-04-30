package com.lwb.common.utils;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @autor: Lu Weibiao
 * Date: 2015/4/2 9:35
 */
public class ApplicationUtils {
    private static final ApplicationContext ac = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
    private static final String classPath = ApplicationUtils.class.getResource("/").getPath();

    public static ApplicationContext getApplicationContext(){
        return ac;
    }

    public static Object getBean(String beanName) {
        return ac.getBean(beanName);
    }

    /**
     * 获取本应用的classPath
     * @return
     */
    public static String getClassPath() {
        return classPath;
    }
}
