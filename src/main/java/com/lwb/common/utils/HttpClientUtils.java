package com.lwb.common.utils;

import org.apache.commons.httpclient.*;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpConnectionParams;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.httpclient.params.HttpMethodParams;
import org.joda.time.DateTime;

/**
 * @autor: Lu Weibiao
 * Date: 2015/4/2 15:49
 */
// TODO 改进整个Http请求工具的api设计
public class HttpClientUtils {
    /* static fields */
    private static final Logger logger = Logger.getLogger(HttpClientUtils.class);

    private static final int CONNECT_TIME_OUT = 5000;
    private static final int SO_TIME_OUT = 5000;
    private static final int CACHE_EXPIRE_PERIOD = 1000 * 60 * 30;//毫秒数，20分钟
    private static final int CACHE_SIZE = 1000;

    private static HttpClientUtils httpClientUtils;

    /* non-static fields */
    private HttpClient httpClient;

    private boolean startRequestCount = false;

    private int requestCount = 0;

    private boolean useCache = true;

    private boolean useProxy = false;

    private CacheCleanTask cacheCleanTask;

    private Thread cacheCleanThread;

    private Map<String, ResponseContent> responseContentCache = new ConcurrentHashMap<>();

    /* constructors */
    private HttpClientUtils() {
        //创建http client
        httpClient = new HttpClient();
        HttpConnectionParams params = httpClient.getHttpConnectionManager().getParams();
        params.setSoTimeout(SO_TIME_OUT);
        params.setConnectionTimeout(CONNECT_TIME_OUT);
        if(useCache) {
            //创建缓存清理线程
            cacheCleanTask = new CacheCleanTask();
            cacheCleanThread = new Thread(cacheCleanTask);
            cacheCleanThread.setName("HttpClientUtils-responseCacheCleaner");
            cacheCleanThread.start();
        }
    }

    /**
     * 获取HttpClientUtils单例
     *
     * @return
     */
    public static HttpClientUtils getInstance() {
        if (httpClientUtils == null) {
            httpClientUtils = new HttpClientUtils();
        }

        return httpClientUtils;
    }

    /**
     * 在程序关闭之前执行以确保关闭一些资源或线程
     */
    public static void close() {
        if(httpClientUtils.cacheCleanTask != null){
            httpClientUtils.cacheCleanTask.setIsExit(true);
        }
        if(httpClientUtils.cacheCleanThread != null) {
            httpClientUtils.cacheCleanThread.interrupt();
        }
        httpClientUtils = null;
    }

    /**
     * 获取http响应内容
     *
     * @param url
     * @param nameValuePairMap
     * @return
     * @throws IOException
     */
    public String getResponseContent(String url, Map<String, String> nameValuePairMap) throws IOException {

        GetMethod getMethod = createHttpMethod(url);
        //将表单的值放入postMethod中
        getMethod.setQueryString(mapToNameValuePairs(nameValuePairMap));
        String content = null;
        if(useCache) {
            String urlWithQueryString = url + "?" + getMethod.getQueryString();
            content = getContentByCache(urlWithQueryString);
            if (content == null) {
                content = executeMethod(getMethod);
                if (startRequestCount) {
                    ++requestCount;
                }
                if(StringUtils.isNotBlank(content)) {
                    setContentCache(urlWithQueryString, content);
                }
            }
        } else {
            content = executeMethod(getMethod);
        }
        return content;
    }

    /**
     * @param @param  method
     * @param @return 设定文件
     * @return String    返回请求结果
     * @throws
     * @Title: executeMethod
     * @Description: 执行指定的http方法
     */
    public String executeMethod(HttpMethod method) throws IOException {
        String content = "";

        //使用系统提供的默认的恢复策略,在发生异常时候将自动重试3次
        method.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler(3, false));

        try {
            // 执行postMethod
            int statusCode = httpClient.executeMethod(method);
            if (statusCode == HttpStatus.SC_OK) {//请求正常返回结果
                content = method.getResponseBodyAsString();
            } else if (statusCode == HttpStatus.SC_MOVED_TEMPORARILY || statusCode == HttpStatus.SC_MOVED_PERMANENTLY ||
                    statusCode == HttpStatus.SC_SEE_OTHER || statusCode == HttpStatus.SC_TEMPORARY_REDIRECT) {//请求被重定向
                //读取新的URL地址
                Header header = method.getResponseHeader("location");
                if (header != null) {
                    String newuri = header.getValue();
                    if (StringUtils.isNotBlank(newuri)) {
                        GetMethod redirect = createHttpMethod(newuri);
                        httpClient.executeMethod(redirect);
                        content = redirect.getResponseBodyAsString();
                    }
                }
            }
        } finally {
            // 释放连接
            method.releaseConnection();
        }

        return content;
    }

    /**
     * @param @param  nameValuePairMap
     * @param @return 设定文件
     * @return NameValuePair[]    返回类型
     * @throws
     * @Title: mapToNameValuePairs
     * @Description: 将map转换成http表单域值
     */
    private NameValuePair[] mapToNameValuePairs(Map<String, String> nameValuePairMap) {
        if (nameValuePairMap == null || nameValuePairMap.isEmpty()) {
            return new NameValuePair[0];
        }
        NameValuePair[] nameValuePairs = new NameValuePair[nameValuePairMap.keySet().size()];

        int i = 0;
        Iterator<String> keys = nameValuePairMap.keySet().iterator();
        while (keys.hasNext()) {
            String key = keys.next();
            nameValuePairs[i] = new NameValuePair(key, nameValuePairMap.get(key));
            i++;
        }

        return nameValuePairs;
    }

    private static final String COOKIE_VALUE;

    private static final List<String> ipList;

    private static List<ProxyHost> proxyList;

    static {
        COOKIE_VALUE = "ASP.NET_SessionId=i5ltiaivgf1k0s5snknh1olv; _citys_=101000; entsearchlog=%5B%7B%22searchtitle%22%3A%22%E5%85%B3%E9%94%AE%E5%AD%97%3A%E4%BD%9B%E5%B1%B1%E5%B8%82%E6%9F%9A%E5%AD%90%E7%94%B5%E5%AD%90%E5%95%86%E5%8A%A1%E6%9C%89%E9%99%90%E5%85%AC%E5%8F%B8%2B%E4%B8%AD%E5%B1%B1%E5%B8%82%22%2C%22searchurl%22%3A%22%2Fsearch%2Foffer_search_result.aspx%3Fkeyword%3D%25u4F5B%25u5C71%25u5E02%25u67DA%25u5B50%25u7535%25u5B50%25u5546%25u52A1%25u6709%25u9650%25u516C%25u53F8%26jtype1Hidden%3D%26jcity1Hidden%3D101000%22%7D%2C%7B%22searchtitle%22%3A%22%E5%85%B3%E9%94%AE%E5%AD%97%3A%E6%9F%9A%E5%AD%90%E7%94%B5%E5%AD%90%E5%95%86%E5%8A%A1%E6%9C%89%E9%99%90%E5%85%AC%E5%8F%B8%2B%E4%B8%AD%E5%B1%B1%E5%B8%82%22%2C%22searchurl%22%3A%22%2Fsearch%2Foffer_search_result.aspx%3Fkeyword%3D%25u67DA%25u5B50%25u7535%25u5B50%25u5546%25u52A1%25u6709%25u9650%25u516C%25u53F8%26jcity1Hidden%3D101000%22%7D%2C%7B%22searchtitle%22%3A%22%E5%85%B3%E9%94%AE%E5%AD%97%3A%E6%9F%9A%E5%AD%90%2B%E4%B8%AD%E5%B1%B1%E5%B8%82%22%2C%22searchurl%22%3A%22%2Fsearch%2Foffer_search_result.aspx%3Fkeyword%3D%25u67DA%25u5B50%26jcity1Hidden%3D101000%22%7D%2C%7B%22searchtitle%22%3A%22%E5%85%B3%E9%94%AE%E5%AD%97%3A%E4%BD%9B%E5%B1%B1%E5%B8%82%E6%9F%9A%E5%AD%90%2B%E4%B8%AD%E5%B1%B1%E5%B8%82%22%2C%22searchurl%22%3A%22%2Fsearch%2Foffer_search_result.aspx%3Fkeyword%3D%25u4F5B%25u5C71%25u5E02%25u67DA%25u5B50%26jcity1Hidden%3D101000%22%7D%2C%7B%22searchtitle%22%3A%22%E5%85%B3%E9%94%AE%E5%AD%97%3A%E4%BD%9B%E5%B1%B1%E5%B8%82%E6%9F%9A%E5%AD%90%E7%94%B5%E5%AD%90%E5%95%86%E5%8A%A1%2B%E4%B8%AD%E5%B1%B1%E5%B8%82%22%2C%22searchurl%22%3A%22%2Fsearch%2Foffer_search_result.aspx%3Fkeyword%3D%25u4F5B%25u5C71%25u5E02%25u67DA%25u5B50%25u7535%25u5B50%25u5546%25u52A1%26jcity1Hidden%3D101000%22%7D%2C%7B%22searchtitle%22%3A%22%E5%85%B3%E9%94%AE%E5%AD%97%3A%E6%9F%9A%E5%AD%90%E7%94%B5%E5%AD%90%E5%95%86%E5%8A%A1%2B%E4%B8%AD%E5%B1%B1%E5%B8%82%22%2C%22searchurl%22%3A%22%2Fsearch%2Foffer_search_result.aspx%3Fkeyword%3D%25u67DA%25u5B50%25u7535%25u5B50%25u5546%25u52A1%26jcity1Hidden%3D101000%22%7D%5D; EntSearchCookies=%e8%d6%d7%d3%b5%e7%d7%d3%c9%cc%ce%f1%7c%7c%b7%f0%c9%bd%ca%d0%e8%d6%d7%d3%b5%e7%d7%d3%c9%cc%ce%f1%7c%7c%b7%f0%c9%bd%ca%d0%e8%d6%d7%d3%7c%7c%e8%d6%d7%d3%7c%7c%e8%d6%d7%d3%b5%e7%d7%d3%c9%cc%ce%f1%d3%d0%cf%de%b9%ab%cb%be; CNZZDATA264172=cnzz_eid%3D658397646-1429145989-null%26ntime%3D1429173041";
        HashSet<String> ipSet = new HashSet<String>();
        //http header ip
        while (ipSet.size() < 1000) {
            //193.1.1.1 - 223.254.254.254
            String p1 = (int)(Math.random() * 30 + 193) + "";
            String p2 = (int)(Math.random() * 254 + 1) + "";
            String p3 = (int)(Math.random() * 254 + 1) + "";
            String p4 = (int)(Math.random() * 254 + 1) + "";
            String ip =  p1+"." + p2 + "." + p3 + "." + p4;
            ipSet.add(ip);
        }
        ipList = new ArrayList<String>(ipSet);
        //http proxy
        proxyList = new ArrayList<ProxyHost>();
        proxyList.add(new ProxyHost("121.14.4.111",80));
        proxyList.add(new ProxyHost("112.84.130.5",10000));
        proxyList.add(new ProxyHost("211.144.72.153",8080));
        proxyList.add(new ProxyHost("113.105.224.87",80));
        proxyList.add(new ProxyHost("218.92.227.166",19305));
        proxyList.add(new ProxyHost("218.92.227.165",15275));
    }

    private static ProxyHost getDynamicProxyHost() {
        return proxyList.get((int)(Math.random() * proxyList.size()));
    }

    /**
     * @param @param  url
     * @param @return 设定文件
     * @return HttpMethod    返回类型
     * @throws
     * @Title: createHttpMethod
     * @Description: 通过url创建http方法
     */
    public GetMethod createHttpMethod(String url) {
        if(useProxy) {
            httpClient.getHostConfiguration().setProxyHost(getDynamicProxyHost());
        }

        GetMethod getMethod = new GetMethod(url);
        // 浏览器伪造
        getMethod.addRequestHeader("User-Agent", "Mozilla/5.0 (Windows NT 5.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/35.0.1916.114 Safari/537.36");
        getMethod.addRequestHeader("Protocol", "HTTP/1.1");
        getMethod.addRequestHeader("Scheme", "http");
        getMethod.addRequestHeader("Connection", "keep-alive");
//        getMethod.addRequestHeader("Referer","https://www.baidu.com/link?url=gEm5TW0Sxqs9nnEd8OkEb5dkj-e_g6GmKYczdKsAq0u&wd=%E9%A2%86%E8%88%AA%E4%BA%BA%E6%89%8D%E7%BD%91&issp=1&f=8&ie=utf-8&tn=baiduhome_pg&inputT=2559");
//        getMethod.addRequestHeader("Cookie", COOKIE_VALUE);
//        String ip = getDynamicIp();
//        getMethod.addRequestHeader("X-Forwarded-For",ip);

        return getMethod;
    }
    private static String getDynamicIp(){
        return ipList.get((int)(ipList.size() * Math.random()));
    }
    /*响应缓存相关*/
    /**
     * 缓存响应内容
     *
     * @param urlWithQueryString
     * @param content
     */
    private void setContentCache(String urlWithQueryString, String content) {
        if (responseContentCache.size() < CACHE_SIZE) {
            ResponseContent responseContent = new ResponseContent();
            responseContent.setContent(content);
            responseContent.setRequestTime(new Date());
            responseContent.setRequestCount(1);
            responseContentCache.put(urlWithQueryString, responseContent);
        }
    }

    /**
     * 获取缓存内容
     *
     * @param urlWithQueryString
     * @return
     */
    private String getContentByCache(String urlWithQueryString) {
        String content = null;
        ResponseContent responseContent = responseContentCache.get(urlWithQueryString);
        if (responseContent != null) {//更新缓存内容访问状态
            content = responseContent.getContent();
            responseContent.setRequestTime(new Date());
            responseContent.setRequestCount(responseContent.getRequestCount() + 1);
        }
        return content;
    }

    private void startRequestCount(){
        startRequestCount = true;
        requestCount = 0;
    }

    private void endRequestCount(){
        startRequestCount = false;
        logger.info("本轮请求数：" + requestCount);
    }

    public static void setUseCache(boolean useCache){
        httpClientUtils.useCache = useCache;
    }
    public static void setUseProxy(boolean useProxy){
        httpClientUtils.useProxy = useProxy;
    }

    /*内部类*/

    /**
     * http响应内容缓存清理任务
     */
    private final class CacheCleanTask implements Runnable {
        private final Logger logger = Logger.getLogger(this.getClass());
        private final Map<String, ResponseContent> responseContentCache = HttpClientUtils.this.responseContentCache;
        private boolean isExit = false;

        @Override
        public void run() {
            while (!isExit) {
                logger.info("开始清理过期响应缓存");
                clearExpiredCache();
                logger.info("清理过期响应缓存完成");
                try {
                    Thread.sleep(CACHE_EXPIRE_PERIOD / 2);
                } catch (InterruptedException e) {
                    if (isExit) {
                        break;
                    }
                    logger.error(e);
                }
            }
            logger.info("结束 Http Client 缓存清理线程");
        }

        /**
         * 清理过期的缓存
         */
        private void clearExpiredCache() {
            if (responseContentCache.size() >= CACHE_SIZE) {
                int expiredNum = 0;
                Set<Map.Entry<String, ResponseContent>> cacheSet = responseContentCache.entrySet();
                for (Map.Entry<String, ResponseContent> entry : cacheSet) {
                    ResponseContent rc = entry.getValue();
                    if (rc == null ||
                            new DateTime(rc.getRequestTime()).plusMillis(CACHE_EXPIRE_PERIOD).isBeforeNow()) {//清理20分钟前的
                        responseContentCache.remove(entry.getKey());
                        ++expiredNum;
                        Thread.yield();
                    }
                }
                logger.info("共清理" + expiredNum + "条！");
            }
        }

        /**
         * 设置任务是否退出
         *
         * @param isExit
         */
        public void setIsExit(boolean isExit) {
            this.isExit = isExit;
        }
    }

    /**
     * 缓存响应内容
     */
    private final class ResponseContent {
        private String content;
        private Date requestTime;//最近请求时间
        private int requestCount = 0;//请求计数

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public Date getRequestTime() {
            return requestTime;
        }

        public void setRequestTime(Date requestTime) {
            this.requestTime = requestTime;
        }

        public int getRequestCount() {
            return requestCount;
        }

        public void setRequestCount(int requestCount) {
            this.requestCount = requestCount;
        }
    }
}
