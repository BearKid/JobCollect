/*
 * Created on 2007-1-23
 */
package com.lwb.collect.component;

import com.lwb.common.model.ComInfo;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.io.File;

/**
 * @author Lu Weibiao
 */
@Component
@Lazy
public class FilterConfigManager {
    public static final Logger logger = Logger.getLogger(FilterConfigManager.class);

    private static final String defaultConfigFilePath = "/dataFilterConfig.xml";
    private static final char DELIMITER = ',';

    private final Document configXml;
    private final Element rootElement;

    public FilterConfigManager() {
        this(null);
    }

    private FilterConfigManager(String configFilePath){

        if (StringUtils.isBlank(configFilePath)) {
            configFilePath = defaultConfigFilePath;
        }

        //获取类路径
        String classPath = null;
        try {
            classPath = FilterConfigManager.class.getResource("/").getPath();
        } catch (Exception e) {
            logger.error("getConfig", e);
        }
        //加载配置文件
        try {
            configFilePath = classPath + configFilePath;
            File f = new File(configFilePath);  //服务器运行目录
            SAXReader reader = new SAXReader();
            configXml = reader.read(f);
            rootElement = configXml.getRootElement();
        } catch (Exception e) {
            throw new RuntimeException("无法加载文件：" + configFilePath,e);
        }
    }

    /**
     * 从配置文件获取指定类型的关键词黑名单
     * @param module
     * @param type 如jobName、comName
     * @return
     */
    private String[] getBlackKeywords(String module, String type) throws RuntimeException{
        Element element = rootElement.element(module);
        if(element == null){
            throw new RuntimeException("找不到指定关键词过滤模块，请检查配置文件");
        }
        String keywordsStr = element.elementText(type);
        return splitToArray(keywordsStr);
    }

    /**
     * 将配置文件得到的元素的文本分割成数组
     *
     * @param elementText
     * @return
     */
    private String[] splitToArray(String elementText) {
        if (StringUtils.isNotBlank(elementText)) {//去空白字符
            elementText = elementText.trim().replaceAll(",\\s+", ",");
        }
        String[] keyList = StringUtils.split(elementText, DELIMITER);
        return keyList;
    }

    /**
     * 判断文本是否包含黑色关键词
     * @param text 被检查的文本
     * @param module 指定模块。<generalBlackKeywords>总是被纳入检查
     * @param type 如"jobName"、"companyName"
     * @return
     */
    private boolean containsBlackKeyword(String text, String module, String type) {
        boolean isInKeywordBlackList = false;
        String[] keywords = getBlackKeywords(module,type);
        if (text != null && keywords != null && keywords.length > 0) {
            for (String key : keywords) {
                if (text.contains(key)) {
                    isInKeywordBlackList = true;
                    break;
                }
            }
        }
        return isInKeywordBlackList;
    }

    /**
     * 判断公司名称是否包含黑色关键词
     * @param module
     * @param name
     * @return
     */
    public boolean containsComNameBlackKeyword(String name,String module){
        return containsBlackKeyword(name,"generalBlackKeywords","companyName")
                ||containsBlackKeyword(name,"generalBlackKeywords","all")
                ||containsBlackKeyword(name,module,"companyName")
                || containsBlackKeyword(name,module,"all");
    }

    /**
     * 获取公司黑名单
     * @return
     */
    private String[] getRefusedComName() {
        Element element = rootElement.element("refuseCompany");
        String folderStr = element.elementText("companyName");
        return splitToArray(folderStr);
    }

    /**
     * 判断企业是否在黑名单
     * @param comInfo
     * @return
     */
    public boolean isRefused(ComInfo comInfo) {
        boolean isRefusedCompany = false;
        String[] refusedComNameList = getRefusedComName();
        if (comInfo != null && StringUtils.isNotBlank(comInfo.getComName())
                && refusedComNameList != null && refusedComNameList.length > 0) {
            for (String refusedComName : refusedComNameList) {
                if (comInfo.getComName().equals(refusedComName)) {
                    isRefusedCompany = true;
                    break;
                }
            }
        }
        return isRefusedCompany;
    }

    /**
     * 判断职位名称是否包含黑色关键词
     * @param jobName 被检查的职位名称
     * @param module 指定检查哪个模块的关键词。<generalBlackKeywords>模块总是会被纳入检查.
     * @return
     */
    public boolean containsJobNameBlackKeyword(String jobName,String module){
        return containsBlackKeyword(jobName,"generalBlackKeywords","jobName")
                || containsBlackKeyword(jobName,"generalBlackKeywords","all")
                || containsBlackKeyword(jobName,module,"jobName")
                || containsBlackKeyword(jobName,module,"all") ;
    }
 }
