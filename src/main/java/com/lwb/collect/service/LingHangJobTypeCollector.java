package com.lwb.collect.service;

import com.lwb.common.option.JobTypeOption;
import com.lwb.common.utils.ApplicationUtils;
import com.lwb.common.utils.HtmlParseUtils;
import com.lwb.common.utils.HttpClientUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @autor: Lu Weibiao
 * Date: 2015/4/2 11:02
 */
@Service
@Lazy
public class LingHangJobTypeCollector {
    private final HttpClientUtils hcu = HttpClientUtils.getInstance();

    private static final String outputFilePath = ApplicationUtils.getClassPath() + "/data/"
            + LingHangJobTypeCollector.class.getSimpleName() + "/jobTypeMap.txt";

    public static void main(String[] args) throws IOException {
        LingHangJobTypeCollector collector = (LingHangJobTypeCollector) ApplicationUtils.getBean("lingHangJobTypeCollector");
        collector.run();
    }

    /**
     * 采集领航的职位类别编码，格式化成LingHangContants里的代码，结果输出到文件
     * 形如jobTypeMap.put("10001",1000);//注解
     * 减轻手动复制的工作量
     */
    private void run() throws IOException {
        String html = hcu.getResponseContent("http://www.0750rc.com/xml/job.ashx", null);
        List<String> JobTypeCodes = HtmlParseUtils.parseValues(html, "<c id=\"", "\"");
        List<String> jobTypeNames = HtmlParseUtils.parseValues(html, "CDATA\\[", "\\]\\]");
        if (jobTypeNames.size() != JobTypeCodes.size()) {
            throw new RuntimeException("解析的内容有问题，职位类别编码个数和职位类别名称个数不相同");
        } else {
            BufferedWriter bw = null;
            try {
                //确保被写入的文件已创建
                File file = new File(outputFilePath);
                if (!file.exists()) {
                    file.getParentFile().mkdirs();
                    file.createNewFile();
                }

                bw = new BufferedWriter(new FileWriter(file, false));

                int size = JobTypeCodes.size();
                String line = null;
                for (int i = 0; i < size; i++) {
                    String jobTypeCode = JobTypeCodes.get(i);//领航的职位类别编码
                    String jobTypeName = jobTypeNames.get(i);//领航的职位类别名称
                    Integer myJobTypeCode = getMyJobTypeCode(jobTypeName);


                    if (jobTypeCode.endsWith("000")) {//暂时注释掉大类
                        line = "//" + jobTypeName + " jobTypeMap.put(\"" + jobTypeCode + "\"," + myJobTypeCode +");";
                    } else {
                        line = "jobTypeMap.put(\"" + jobTypeCode + "\"," + myJobTypeCode +");//" + jobTypeName;
                    }
                    bw.append(line);
                    bw.newLine();
                }
            } finally {
                if (bw != null) {
                    bw.close();
                }
            }
        }
    }

    /**
     * 尝试用领航的职位类别名称去匹配出job5156的对应职位类别编码
     * @param jobTypeName
     * @return
     */
    private Integer getMyJobTypeCode(String jobTypeName) {
        Integer myJobTypeCode = null;
        Set<Map.Entry<Integer,String>> simpleOptions =  JobTypeOption.getSimpleOptions().entrySet();
        String splitPattern = "\\(|\\)|/";
        String[] jobTypeNameSplit = jobTypeName.split(splitPattern);
        for(String lingHangName : jobTypeNameSplit) {//领航职位类别名称
            for(Map.Entry<Integer,String> option : simpleOptions){
                String[] myJobTypeNameSplit = StringUtils.split(option.getValue(), splitPattern);//TODO 缓存中间结果
                for(String myName : myJobTypeNameSplit){
                    if(myName.equals(lingHangName)){
                        myJobTypeCode = option.getKey();
                        return myJobTypeCode;
                    }
                }
            }
        }
        return null;
    }
}
