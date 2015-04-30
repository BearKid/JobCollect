package com.lwb.collect.service;

import com.lwb.common.constants.LingHangConstants;
import com.lwb.common.utils.ApplicationUtils;
import com.lwb.common.utils.HtmlParseUtils;
import com.lwb.common.utils.HttpClientUtils;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * @autor: Lu Weibiao
 * Date: 2015/4/7 10:48
 */
@Service
@Lazy
public class LingHangIndustryCollector {

    private final HttpClientUtils hcu = HttpClientUtils.getInstance();
    private static final String outputFilePath = ApplicationUtils.getClassPath() + "/data/"
            + LingHangIndustryCollector.class.getSimpleName()  + "/industryMap.txt";

    public static void main(String[] args) throws IOException{
        LingHangIndustryCollector collector = (LingHangIndustryCollector) ApplicationUtils.getBean("lingHangIndustryCollector");
        collector.run();
    }

    /**
     * 采集领航的行业类别编码，格式化成LingHangContants里的代码
     * 结果输出到文件
     * @throws IOException
     */
    private void run() throws IOException{
        String html = hcu.getResponseContent(LingHangConstants.DOMAIN + "/search/offer_searcher.aspx", null);
        html = HtmlParseUtils.parseToString(html, "id=\"ctl00_ContentPlaceHolder1_Industry\".+?>", "</select>");

        List<String> codes = HtmlParseUtils.parseValues(html, "value=\"", "\"");
        List<String> names = HtmlParseUtils.parseValues(html, "\">", "</");

        if (names.size() != codes.size()) {
            throw new RuntimeException("解析的内容有问题，行业类别编码个数和行业类别名称个数不相同");
        } else {
            BufferedWriter bw = null;
            try {

                File file = new File(outputFilePath);
                if (!file.exists()) {
                    file.getParentFile().mkdirs();
                    file.createNewFile();
                }

                bw = new BufferedWriter(new FileWriter(file, false));
                int size = codes.size();
                for (int i = 0; i < size; i++) {
                    String line = null;
                    String code = codes.get(i);
                    String name = names.get(i);
                    line = "comIndustryMap.put(\"" + name + "\",0);//" + code;
                    bw.append(line);
                    bw.newLine();
                }
                bw.close();
            } finally {
                 bw.close();
            }
        }
    }
}
