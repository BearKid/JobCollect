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
 * <p></p>
 * Date: 2015/4/14 16:28
 *
 * @version 1.0
 * @autor: Lu Weibiao
 */
@Service
@Lazy
public class LingHangAreaCollector {
    private final HttpClientUtils hcu = HttpClientUtils.getInstance();
    private static final String outputFilePath = ApplicationUtils.getClassPath() + "/data/"
            + LingHangAreaCollector.class.getSimpleName()  + "/areaMap.txt";

    public static void main(String[] args) throws IOException {
        LingHangAreaCollector collector = (LingHangAreaCollector) ApplicationUtils.getBean("lingHangAreaCollector");
        collector.run();
    }

    /**
     * 采集领航的地区类别编码，格式化成LingHangContants里的代码
     * 结果输出到文件
     * @throws IOException
     */
    private void run() throws IOException{
        String html = hcu.getResponseContent(LingHangConstants.DOMAIN + "/xml/city.ashx", null);
        html = HtmlParseUtils.parseToString(html,"<!\\[CDATA\\[中山市\\]\\]></c>","<c id=\"101200\"");

        List<String> codes = HtmlParseUtils.parseValues(html,"<c id=\"","\" pid=\"101000\"");
        List<String> names = HtmlParseUtils.parseValues(html, "<c id=\"\\d+?\" pid=\"101000\"[\\s\\S]+?><!\\[CDATA\\[", "\\]\\]></c>");

        if (names.size() != codes.size()) {
            throw new RuntimeException("解析的内容有问题，地区类别编码个数和地区类别名称个数不相同");
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
                    line = "areaMap.put(\"" + code + "\",);//" + name;
                    bw.append(line);
                    bw.newLine();
                }
                bw.close();
            } finally {
                bw.close();
            }
        }
        HttpClientUtils.close();
    }
}
