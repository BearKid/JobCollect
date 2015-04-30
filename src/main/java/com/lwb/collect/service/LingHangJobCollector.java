package com.lwb.collect.service;

import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.lwb.common.constants.BaseConstants;
import com.lwb.common.constants.LingHangConstants;
import com.lwb.common.model.ComContact;
import com.lwb.common.model.ComContactBase;
import com.lwb.common.model.ComInfo;
import com.lwb.common.model.ComPosition;
import com.lwb.common.option.JobTypeOption;
import com.lwb.common.utils.*;
import com.lwb.common.utils.DateUtils;
import com.lwb.common.vo.AddressVo;
import com.lwb.collect.component.FilterConfigManager;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.joda.time.DateTime;
import org.springframework.context.annotation.Lazy;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.hibernate4.HibernateCallback;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.*;
import java.util.*;

/**
 * Date: 2015/4/2 9:21
 * @autor: Lu Weibiao
 */
@Service
@Lazy
@Transactional(value = "downJobTransactionManager")
public class LingHangJobCollector {
    private static final Logger logger = Logger.getLogger(LingHangJobCollector.class);
    private static final long LONG_WAIT_TIME = 121000;//2分钟左右

    private final HttpClientUtils hcu = HttpClientUtils.getInstance();
    private final Gson gsonWithNull = GsonUtils.getGsonWithNull();
    private int jobCount = 0;
    private DateTime lastestLongSleepTime = new DateTime();

    @Resource
    private HibernateTemplate downJobHibernateTemplate;
    @Resource
    private JdbcTemplate downJobJdbcTemplate;
    @Resource
    private FilterConfigManager filterConfigManager;

    public static void main(String[] args) throws IOException{
        String saveProgressFile = null;//采集进度存档文件
        if(args.length == 1){
            saveProgressFile = args[0];
        }
        LingHangJobCollector collector = (LingHangJobCollector) ApplicationUtils.getBean("lingHangJobCollector");
        collector.run(saveProgressFile);
//        collector.downloadJob("http://www.0750rc.com/jw/showjob_3071796.aspx",new HashMap<String, String>());
//        collector.downloadJob("http://www.0750rc.com/jw/showjobvip_3025700.aspx?keyword=",new HashMap<String, String>());
    }

    public void run(String progressFile) throws IOException{
        logger.info("----开始领航人才网职位抓取！----");
        SavePoint savePoint = SavePoint.create(progressFile);
        long startTime = System.currentTimeMillis();

        Map<String, Integer> workLocationMap = LingHangConstants.getWorkLocationMap();
        Map<String, Integer> jobTypeMap = LingHangConstants.getJobTypeMap();

        Set<String> workLocations = workLocationMap.keySet();
        Set<String> jobTypes = jobTypeMap.keySet();
        for (String workLocation : workLocations) {
            for (String jobType : jobTypes) {
                try {//每对【地区+职位类别】的采集作为一个进度保存点
                    if (!savePoint.isSavePoint(workLocation, jobType)) {//判断是否为已采集的【地区+职位类别】
                        Map<String, String> params = new HashMap<String, String>();
                        params.put(LingHangConstants.WorkLoactionParamName, workLocation);
                        params.put(LingHangConstants.JobTypeParamName, jobType);
                        downLoadJobs(params);//采集一个【地区+职位类别】
                        try {
                            savePoint.setSavePoint(workLocation, jobType);//对一个【地区+职位类别】采集完成后保存进度
                        } catch (IOException e) {
                            logger.error("保存进度[" + workLocation + "," + jobType + "]失败", e);
                        }
                    } else {
                        logger.info("上次中断采集任务前已采集[地区：" + workLocation + " 职位类别：" + jobType + "]");
                    }
                }catch (Exception e){//允许一对【地区+职位类别】采集失败后，可以继续下一对
                    logger.error("采集[地区：" + workLocation + ",职位类别：" + jobType + "]失败",e);
                }
            }
        }
        try {
            savePoint.close();
        }catch (IOException e){
            logger.error("关闭进度存档文件失败",e);
        }
        HttpClientUtils.close();

        long endTime = System.currentTimeMillis();
        logger.info("----领航人才网职位抓取完成！共" + jobCount + "个职位，耗时：" + (endTime - startTime) / 1000.0 / 3600.0 + "小时-----");
    }

    /**
     * 按指定的工作地区和职位类别收集职位
     *
     * @param params
     */
    public void downLoadJobs(final Map<String, String> params) throws IOException{
        //获取职位搜索结果分页总数
        int totalPagesCount = getJobSearchTotalPagesCount(params);
        //遍历下载分页里的所有职位
        Map<String, String> tempParams = new HashMap<String, String>(params);
        for (int i = 1; i <= totalPagesCount; i++) {
            tempParams.put("page", String.valueOf(i));
            downloadJobsByPagingUrl(tempParams);
        }
    }

    /**
     * 从按指定查询条件得到的职位搜索结果中采集指定分页的所有职位
     *
     * @param params
     */
    public void downloadJobsByPagingUrl(final Map<String, String> params) throws IOException{
        String htmlContent = hcu.getResponseContent(LingHangConstants.JOB_SEARCH_BASIC_URL, params);
        String startPattern = "a01\" href=\"";
        String endPattern = "\"";
        List<String> jobDetailUrlList = HtmlParseUtils.parseValues(htmlContent, startPattern, endPattern);//分页里的所有职位详情页url
        List<String> comNameList = HtmlParseUtils.parseValues(htmlContent, "_EntUrl\" title=\"","\"");
        List<String> comDetailUrlList = HtmlParseUtils.parseValues(htmlContent,"_EntUrl\" title=\".+?\" href=\"","\"");

        int i = 0;
        for (String jobDetailUrl : jobDetailUrlList) {
            try {
                jobDetailUrl = LingHangConstants.DOMAIN + jobDetailUrl;
                logger.debug(jobDetailUrl);
                if(comDetailUrlList.get(i).startsWith("http:")){//有一些公司的url是它的官方网站
                    continue;
                }
                downloadJob(jobDetailUrl, params, comNameList.get(i),LingHangConstants.DOMAIN + comDetailUrlList.get(i));

                try {//休眠
                    collectSleep();
                }catch (InterruptedException e) {
                    logger.error("采集休眠出错", e);
                }
            } catch (Exception e) {
                logger.error("职位收集失败" + jobDetailUrl, e);
                try {
                    collectSleep(true);
                }catch (InterruptedException ie) {
                    logger.error("采集休眠出错", ie);
                }
            }
            ++i;
        }
    }

    /**
     * 采集休眠
     * @param forceLongSleep 是否为长休眠
     * @throws InterruptedException
     */
    private void collectSleep(boolean forceLongSleep) throws InterruptedException{
        if(BaseConstants.WAIT_TIME == 0) return;
        if(forceLongSleep || lastestLongSleepTime.plusMinutes(45).isBeforeNow()){
            logger.info("进行长休眠！");
            Thread.sleep(LONG_WAIT_TIME);
            lastestLongSleepTime = new DateTime();
            logger.info("长休眠结束，恢复采集！");
        } else{
            Thread.sleep(BaseConstants.WAIT_TIME);
        }
    }
    private void collectSleep() throws InterruptedException{
        collectSleep(false);
    }

    /**
     * 下载指定职位详情页的职位
     *
     * @param jobDetailUrl
     * @param params
     */
    public void downloadJob(final String jobDetailUrl, final Map<String, String> params, final String comName, final String comDetailUrl) throws IOException{
        Integer workLocation = LingHangConstants.getAreaCode(params.get(LingHangConstants.WorkLoactionParamName));
        Integer jobType = LingHangConstants.getJobTypeCode(params.get(LingHangConstants.JobTypeParamName));
        boolean isVip = isVipJobUrl(jobDetailUrl);

        ComInfo comInfo = null;
        ComContact comContact = null;
        ComPosition comPosition = null;
        boolean isExistedComInfo = false;

        comInfo = getExistedComInfo(comName);
        if(comInfo == null) {
//             comDetailUrl = isVip ? getVipComDetailUrl(jobDetailUrl) : getComDetailUrl(jobDetailUrl);
             comInfo = isVip ? getVipComInfo(comDetailUrl) : getComInfo(comDetailUrl);
             isExistedComInfo = false;
        } else{
            isExistedComInfo = true;
        }

        //持久化公司
        if (!isExistedComInfo && isDownloadableComInfo(comInfo)) {
            comInfo.setLocation(workLocation);
            downJobHibernateTemplate.save(comInfo);
            isExistedComInfo = true;
            if (!isCompanyHasContact(comInfo.getId())) {
                //下载公司联系信息
                comContact = isVip ? getVipComContact(comDetailUrl) : getComContact(comDetailUrl, jobDetailUrl);
                ComContactBase comContactBase = comContact.getComContactBase();
                if (comContactBase.getWebsite() != null && comContactBase.getWebsite().length() > 100) {
                    comContactBase.setWebsite(null);
                }
                comContact.setComId(comInfo.getId());
                downJobHibernateTemplate.save(comContact);
            }
        }
        if(isExistedComInfo){
            comPosition = isVip ? getVipComPosition(jobDetailUrl) : getComPosition(jobDetailUrl);
            comPosition.setComId(comInfo.getId());
            if (isDownloadableComPosition(comPosition)) {
                comPosition.setWorkLocation("[" + workLocation + "]");
                comPosition.setPosType("[" + jobType + "]");
                comPosition.setPosKeyword(JobTypeOption.getName(jobType));
                downJobHibernateTemplate.save(comPosition);
                jobCount++;
                logger.info("[收集成功-" + (isVip ? "vip" : "普通") + "]" + comPosition.getPosName() + "-" + comInfo.getComName());
            } else {
                logger.info("[放弃职位]#" + comPosition.getPosName() + "#(" + jobDetailUrl + ")");
            }
        } else {
            logger.info("[丢弃公司]不抓取#" + comInfo.getComName() + "#(" + comDetailUrl + ")的职位");
        }
    }

    /**
     * 判断公司是否已有联系信息
     * @param comInfoId
     * @return
     */
    private boolean isCompanyHasContact(final Integer comInfoId) {
        if(comInfoId == null){
            throw new NullPointerException("comInfoId is null");
        }
        final String hql = "select id from ComContact where comId = ?";
        return downJobHibernateTemplate.execute(new HibernateCallback<Boolean>() {
            @Override
            public Boolean doInHibernate(Session session) throws HibernateException {
                Query query = session.createQuery(hql);
                query.setParameter(0,comInfoId);
                query.setMaxResults(1);
                return query.uniqueResult() != null;
            }
        });
    }

    /**
     * 根据传入的ComInfo尝试查出对应的已采集的公司信息
     * 目前已公司名作为查询条件
     * @param comName
     * @return
     */
    private ComInfo getExistedComInfo(final String comName) {
        if(StringUtils.isBlank(comName)){
            throw new RuntimeException("ComInfo.comName is empty");
        }
        final String hql = "select c from ComInfo c where c.comName = ?";
        return downJobHibernateTemplate.execute(new HibernateCallback<ComInfo>() {
            @Override
            public ComInfo doInHibernate(Session session) throws HibernateException {
                Query query = session.createQuery(hql);
                query.setParameter(0,comName);
                return (ComInfo)query.uniqueResult();
            }
        });
    }

    private String getVipComDetailUrl(String jobDetailUrl) throws IOException{
        String html = hcu.getResponseContent(jobDetailUrl, null);
        if(StringUtils.isBlank(html)){
            throw new RuntimeException("请求失败" + jobDetailUrl);
        }
        return LingHangConstants.DOMAIN + HtmlParseUtils.parseToString(html, "\"EntNameL\\\" class=\"entname\" href=\"", "\">");
    }

    /**
     * 判断职位是否符合采集要求
     * 1)是否不包含过滤关键词
     * 2)是否已存在
     *
     * @param comPosition
     * @return
     */
    private boolean isDownloadableComPosition(final ComPosition comPosition) {
        boolean isDownloadable = true;
        //基本的职位过滤
        boolean isLegal = (
                !filterConfigManager.containsJobNameBlackKeyword(comPosition.getPosName(),"lingHangBlackKeywords")
                        && !filterConfigManager.containsJobNameBlackKeyword(comPosition.getDescription(),"lingHangBlackKeywords")
        );
        if(!isLegal){
            logger.debug("职位不合法");
        }
        boolean isExisted = isExistedComPosition(comPosition);
        if(isExisted){
            logger.debug("职位已存在");
        }
        isDownloadable = isLegal && !isExisted;
        return isDownloadable;
    }

    /**
     * 判断职位是否已收集
     *
     * @param comPosition
     * @return
     */
    private boolean isExistedComPosition(final ComPosition comPosition) {
        boolean isExisted = true;
        //职位是否已收集过
        String sql = "SELECT id FROM com_position WHERE pos_name = ? AND com_id = ?";
        Object[] params = {
                comPosition.getPosName(),
                comPosition.getComId()
        };
        try {
            Integer id = downJobJdbcTemplate.queryForObject(sql, params, Integer.class);
            comPosition.setId(id);
            isExisted &= (id != null);//不为null，则存在
        } catch (EmptyResultDataAccessException e) {
            isExisted &= false;
            logger.debug(e);
        }
        return isExisted;
    }

    /**
     * 判断公司基本信息是否已收集
     *
     * @param comInfo
     * @return
     */
    private boolean isExistedComInfo(final ComInfo comInfo) {
        if(StringUtils.isBlank(comInfo.getComName())){
            throw new RuntimeException("ComInfo.comName is empty");
        }
        String sql = "SELECT id FROM com_info WHERE com_name = ?";
        Object[] params = {
                comInfo.getComName()
        };
        Integer id = null;
        try {
            id = downJobJdbcTemplate.queryForObject(sql, params, Integer.class);
            comInfo.setId(id);
        } catch (EmptyResultDataAccessException e) {
            id = null;
            logger.debug("isExistedComInfo", e);
        }
        return id != null;
    }

    /**
     * 判断公司信息是否符合采集要求
     * 1）公司名称和描述不包含过滤关键词
     * 2）公司不在黑名单中
     *
     * @param comInfo
     * @return
     */
    private boolean isDownloadableComInfo(final ComInfo comInfo) {
        boolean isLegal = !filterConfigManager.containsComNameBlackKeyword(comInfo.getComName(),"lingHangBlackKeywords")
                && !filterConfigManager.containsComNameBlackKeyword(comInfo.getCompanyIntroduction(),"lingHangBlackKeywords")
                && !filterConfigManager.isRefused(comInfo);
        if (!isLegal){
            logger.debug("公司非法");
        }
        return isLegal;
    }

    /**
     * 通过职位详情页获取公司详情页url
     *
     * @param jobDetailUrl
     * @return
     */
    private String getComDetailUrl(final String jobDetailUrl) throws IOException{
        String html = hcu.getResponseContent(jobDetailUrl, null);
        if(StringUtils.isBlank(html)){
            throw new RuntimeException("请求失败" + jobDetailUrl);
        }
        return LingHangConstants.DOMAIN + HtmlParseUtils.parseToString(html, "class=\"title\" .+?>\\s+<a href=['\"]", "['\"]><span class=\"home_icon");
    }

    /**
     * 获取vip公司联系信息
     *
     * @param comDetailUrl
     * @return
     */
    private ComContact getVipComContact(final String comDetailUrl) throws IOException{

        String comDetailHtml = hcu.getResponseContent(comDetailUrl, null);
        if(StringUtils.isBlank(comDetailHtml)){
            throw new RuntimeException("请求失败：" + comDetailHtml);
        }
        ComContact comContact = new ComContact();
        ComContactBase comContactBase = new ComContactBase();

//        String contactPerson = HtmlParseUtils.parseToString(jobDetailHtml,"联系</span>人：</span>","\\s*<");
        String addressTemp = HtmlParseUtils.parseToString(comDetailHtml, "ContactAddress\">", "<");
        AddressVo addressVo = new AddressVo();
        addressVo.setAddr(addressTemp);
        String busLine = HtmlParseUtils.parseToString(comDetailHtml, "公交路线：", "<");
        String zipCode = HtmlParseUtils.parseToString(comDetailHtml, "ContactZipcode\">", "<");
        String website = HtmlParseUtils.parseToString(comDetailHtml, "Homepage1\" title=\".*\" href=\"", "\" target");

        comContactBase.setAddress(gsonWithNull.toJson(addressVo));
        comContactBase.setBusLine(busLine);
        comContactBase.setZipCode(zipCode);
        comContactBase.setWebsite(website);
        //默认设置
        Date now = new Date();
        comContactBase.setContactPerson("人力资源部");
        comContactBase.setHideEmail(1);
        comContactBase.setHideFax(1);
        comContactBase.setHidePhone(1);
        comContact.setComContactBase(comContactBase);
        comContact.setCreateDate(now);
        comContact.setUpdateDate(now);
        comContact.setDefaultFlag(1);
        return comContact;
    }

    /**
     * 访问url解析出公司联系信息
     *
     * @param comDetailUrl 公司详情页url
     * @return
     */
    private ComContact getComContact(final String comDetailUrl, final String jobDetailUrl) throws IOException{

        String comDetailHtml = hcu.getResponseContent(comDetailUrl, null);
        if(StringUtils.isBlank(comDetailHtml)){
            throw new RuntimeException("请求失败：" + comDetailUrl);
        }
        String jobDetailHtml = hcu.getResponseContent(jobDetailUrl, null);
        if(StringUtils.isBlank(comDetailHtml)){
            throw new RuntimeException("请求失败：" + jobDetailUrl);
        }
        ComContact comContact = new ComContact();
        ComContactBase comContactBase = new ComContactBase();

        String contactPerson = HtmlParseUtils.parseToString(jobDetailHtml, "联系</span>人：</span>\\s*", "\\s*<");
        String addressTemp = HtmlParseUtils.parseToString(comDetailHtml, "公司地址：</span>\\s*", "\\s*<");
        AddressVo addressVo = new AddressVo();
        addressVo.setAddr(addressTemp);
        String busLine = HtmlParseUtils.parseToString(comDetailHtml, "ctl00_ContentPlaceHolder1_BUSWAY\" title=\".*\">\\s*", "\\s*<");
        String zipCode = HtmlParseUtils.parseToString(comDetailHtml, "邮政编码：</span>\\s*", "\\s*<");
        String website = HtmlParseUtils.parseToString(comDetailHtml, "ctl00_ContentPlaceHolder1_Homepage1\" title=\".*\" href=\"", "\" target");

        comContactBase.setAddress(gsonWithNull.toJson(addressVo));
        comContactBase.setContactPerson(StringUtils.isBlank(contactPerson) ? "人力资源部" : contactPerson);
        comContactBase.setBusLine(busLine);
        comContactBase.setZipCode(zipCode);
        comContactBase.setWebsite(website);
        //默认设置
//        comContactBase.setContactPerson("人力资源部");
        Date now = new Date();
        comContactBase.setHideEmail(1);
        comContactBase.setHideFax(1);
        comContactBase.setHidePhone(1);
        comContact.setComContactBase(comContactBase);
        comContact.setCreateDate(now);
        comContact.setUpdateDate(now);
        comContact.setDefaultFlag(1);

        return comContact;
    }

    /**
     * 解析出vip版职位信息
     *
     * @param jobDetailUrl
     * @return
     */
    private ComPosition getVipComPosition(final String jobDetailUrl) throws IOException{
        String html = hcu.getResponseContent(jobDetailUrl, null);
        if(StringUtils.isBlank(html)){
            throw new RuntimeException("请求失败：" + jobDetailUrl);
        }
        ComPosition comPosition = new ComPosition();

//        String jobId = HtmlParseUtils.parseToString(jobDetailUrl, "showjobvip_", ".aspx");
        String jobName = HtmlParseUtils.parseToString(html, "class=\"title\" style=\"font-weight: bold\">", "<");
        boolean urgent = HtmlParseUtils.containsText(html, "id=\"hot\"");
//        String updateDate = HtmlParseUtils.parseToString(html, "publishdate\">", "<");
//        String endDate = HtmlParseUtils.parseToString(html, "expiredate\">", "<");
        String recruitmentNumber = HtmlParseUtils.parseToString(html, "peoplecount\">", "\\s*<").replaceAll("人", "");
        String salary = HtmlParseUtils.parseToString(html, "showmoney\">", "<");
        String property = HtmlParseUtils.parseToString(html, "jobkind\">", "<");
        String reqDegree = HtmlParseUtils.parseToString(html, "learnlimited\">", "<");
        String reqAgeMin = HtmlParseUtils.parseToString(html, "agelowest\">", "岁以上");
        String reqAgeMax = HtmlParseUtils.parseToString(html, "年龄要求：</span>\\s*(?:(?:\\d+岁以上-)|(?:不限-))?", "岁以下");
//        String reqGender = HtmlParseUtils.parseToString(html, "性别要求：</span>", "<");
        String reqWorkYear = HtmlParseUtils.parseToString(html, "expr\">", "<");
        String description = HtmlParseUtils.parseToString(html, "requirement\">", "</span>\\s*</td>");
        description.replaceAll(HtmlParseUtils.htmlTagPattern,"");

//        comPosition.setId(Integer.valueOf(jobId));
        comPosition.setPosName(jobName);
        comPosition.setUrgent(urgent ? 1 : 0);
//        comPosition.setEndDate(DateUtils.parse(endDate, "yyyy年MM月dd日", null));

        comPosition.setRecruitmentNumber(LingHangConstants.getJobRecruitmentNumber(recruitmentNumber));
        comPosition.setSalary(LingHangConstants.getJobSalaryCode(salary));
        comPosition.setProperty(LingHangConstants.getJobProperty(property));
        comPosition.setReqDegree(LingHangConstants.getJobReqDegreeCode(reqDegree));
        comPosition.setReqAgeMin(LingHangConstants.getJobReqAge(reqAgeMin));
        comPosition.setReqAgeMax(LingHangConstants.getJobReqAge(reqAgeMax));
        comPosition.setReqWorkYear(LingHangConstants.getJobReqWorkYearCode(reqWorkYear));
        comPosition.setReqGender(LingHangConstants.getJobReqGenderCode(""));
        comPosition.setDescription((description == null) ? "" : description);
        //设置默认值
        Date now = new Date();
        comPosition.setPosStatus(9);
        comPosition.setCreateDate(now);
        comPosition.setUpdateDate(now);
        comPosition.setRefreshDate(now);
        comPosition.setEndDate(org.apache.commons.lang.time.DateUtils.addMonths(now,3));
        comPosition.setDepartmentId(0);
        comPosition.setComUserId(0);
        comPosition.setDelStatus(0);
        comPosition.setInterviewNote("");
        comPosition.setScore(0.0);
        return comPosition;
    }

    /**
     * 解析出职位信息
     *
     * @param jobDetailUrl
     * @return
     */
    private ComPosition getComPosition(final String jobDetailUrl) throws IOException{
        String html = hcu.getResponseContent(jobDetailUrl, null);
        if(StringUtils.isBlank(html)){
            throw new RuntimeException("请求失败：" + jobDetailUrl);
        }
        ComPosition comPosition = new ComPosition();

//        String jobId = HtmlParseUtils.parseToString(jobDetailUrl, "showjob_", ".aspx");
        String jobName = HtmlParseUtils.parseToString(html, "ItemTitle JobTitleItem\">\\s*<span class=\"ch l\">", "<");
        boolean urgent = HtmlParseUtils.containsText(html, "ctl00_ContentPlaceHolder1_hot");
//        String updateDate = HtmlParseUtils.parseToString(html, "更新日期：</span>\\s*", "\\s*<");
//        String endDate = HtmlParseUtils.parseToString(html, "有效日期：</span>\\s*", "\\s*<");
        String recruitmentNumber = HtmlParseUtils.parseToString(html, "招聘人数：</span>\\s*", "\\s*<");
        if(recruitmentNumber != null){
            recruitmentNumber = recruitmentNumber.replace("人", "");
        }
        String salary = HtmlParseUtils.parseToString(html, "提供月薪：</span>\\s*", "\\s*<");
        String property = HtmlParseUtils.parseToString(html, "职位类型：</span>\\s*", "\\s*<");
        String reqDegree = HtmlParseUtils.parseToString(html, "学历要求：</span>\\s*", "\\s*<");
        String reqAgeMin = HtmlParseUtils.parseToString(html, "年龄要求：</span>\\s*", "岁以上");
        String reqAgeMax = HtmlParseUtils.parseToString(html, "年龄要求：</span>\\s*(?:(?:\\d+岁以上-)|(?:不限-))?", "岁以下");
        String reqGender = HtmlParseUtils.parseToString(html, "性别要求：</span>\\s*", "\\s*<");
        String reqWorkYear = HtmlParseUtils.parseToString(html, "工作经验：</span>\\s*", "\\s*<");
        String description = HtmlParseUtils.parseToString(html, "ctl00_ContentPlaceHolder1_requirement\" class=\"ItemContent\" style=\"font-size: 14px;\">", "</div>\\s*<div id=\"divApplyJob");
        description.replaceAll(HtmlParseUtils.htmlTagPattern,"");

//        comPosition.setId(Integer.valueOf(jobId));
        comPosition.setPosName(jobName);
        comPosition.setUrgent(urgent ? 1 : 0);
//        comPosition.setUpdateDate(DateUtils.parse(updateDate, "yyyy年MM月dd日", new Date()));
//        comPosition.setEndDate(DateUtils.parse(endDate, "yyyy年MM月dd日", null));
        comPosition.setRecruitmentNumber(LingHangConstants.getJobRecruitmentNumber(recruitmentNumber));
        comPosition.setSalary(LingHangConstants.getJobSalaryCode(salary));
        comPosition.setProperty(LingHangConstants.getJobProperty(property));
        comPosition.setReqDegree(LingHangConstants.getJobReqDegreeCode(reqDegree));
        comPosition.setReqAgeMin(LingHangConstants.getJobReqAge(reqAgeMin));
        comPosition.setReqAgeMax(LingHangConstants.getJobReqAge(reqAgeMax));
        comPosition.setReqWorkYear(LingHangConstants.getJobReqWorkYearCode(reqWorkYear));
        comPosition.setReqGender(LingHangConstants.getJobReqGenderCode(reqGender));
        comPosition.setDescription((description == null) ? "" : description);
        //设置默认值
        Date now = new Date();
        comPosition.setPosStatus(9);
        comPosition.setCreateDate(now);
        comPosition.setUpdateDate(now);
        comPosition.setRefreshDate(now);
        comPosition.setEndDate(org.apache.commons.lang.time.DateUtils.addMonths(now,3));
        comPosition.setDepartmentId(0);
        comPosition.setComUserId(0);
        comPosition.setDelStatus(0);
        comPosition.setInterviewNote("");
        comPosition.setScore(0.0);
        return comPosition;
    }

    /**
     * 获取vip公司基本信息
     *
     * @param comDetailUrl
     * @return 总是返回一个实例，但属性字段有可能为空
     */
    private ComInfo getVipComInfo(final String comDetailUrl) throws IOException{
        String html = hcu.getResponseContent(comDetailUrl, null);
        if(StringUtils.isBlank(html)){
            logger.error("请求失败："+ comDetailUrl);
            return null;
        }
        ComInfo comInfo = new ComInfo();
//        String comInfoId = HtmlParseUtils.parseToString(comDetailUrl, "showentvip_", ".aspx");
        String comName = HtmlParseUtils.parseToString(html, "EntNameL\" class=\"entname\" href=\"/jw/showentvip_\\d+.aspx\">", "<");
        String bussinessLicence = HtmlParseUtils.parseToString(html, "EntRegNum\">", "<");
        String property = HtmlParseUtils.parseToString(html, "单位性质：<span id=\"EntProperty\">", "<");

        String industryText = HtmlParseUtils.parseToString(html, "Industry\">", "<");
        String[] industryArray = StringUtils.split(industryText, ",");

        String foundDate = HtmlParseUtils.parseToString(html, "成立日期：", "<");
        String registerFund = HtmlParseUtils.parseToString(html, "\"Fund\">注册资金：", "<");
        String employeeNumber = HtmlParseUtils.parseToString(html, "EmpNum\">员工人数：", "<");
//            String comIntroduction = HtmlParseUtils.parseToString(html, "Intro\">(?:<script[\\s\\S]+<div class=\"clear sliderImage\"></div>\\s*</ul>\\s*</div>)?", "<");
        String comIntroduction = HtmlParseUtils.parseToString(html, "Intro\">(?:(?:<script .+></script>\\s*)?<div [\\s\\S]+<img src=.+>[\\s\\S]*</div>)?", "</span>\\s*</div>[\\s\\S]+基本状况");
        comIntroduction.replaceAll(HtmlParseUtils.htmlTagPattern,"");

//        comInfo.setId(Integer.valueOf(comInfoId));
        comInfo.setComName(StringUtils.isBlank(comName) ? null : comName);
        comInfo.setBusinessLicence(bussinessLicence);
        comInfo.setProperty(LingHangConstants.getComPropertyCode(property));
        comInfo.setIndustry(LingHangConstants.getComIndustryCode(industryArray));
        comInfo.setFoundDate(DateUtils.parse(foundDate, "yyyy年MM月dd日", null));
        comInfo.setRegisterFund(LingHangConstants.getComRegisterFundCode(registerFund));
        comInfo.setEmployeeNumber(LingHangConstants.getComEmployeeNumberCode(employeeNumber));
        comInfo.setCompanyIntroduction(comIntroduction);
        //默认设置
        comInfo.setSalerId(109);
        comInfo.setSalerName("导入资料");
        comInfo.setSalerRead(0);
        comInfo.setCreateDate(new Date());
        comInfo.setUpdateDate(new Date());
        comInfo.setStatus(1);
        comInfo.setComFlag(2);
        comInfo.setLastEditor("领航人才网");
        comInfo.setNeedHunter(0);
        return comInfo;
    }

    /**
     * 从html中解析出公司基本信息
     *
     * @param comDetailUrl
     * @return 总是返回一个实例，但属性字段有可能为空
     */
    private ComInfo getComInfo(final String comDetailUrl) throws IOException{
        String html = hcu.getResponseContent(comDetailUrl, null);
        if(StringUtils.isBlank(html)){
            throw new RuntimeException("请求失败：" + comDetailUrl);
        }
        ComInfo comInfo = new ComInfo();
//        String comInfoId = HtmlParseUtils.parseToString(comDetailUrl, "showent_", ".aspx");
        String comName = HtmlParseUtils.parseToString(html, "ch l\".*?>", "<");
        String bussinessLicence = HtmlParseUtils.parseToString(html, "营业执照：</span>\\s*", "\\s*<");
        String property = HtmlParseUtils.parseToString(html, "单位性质：</span>\\s*", "\\s*<");
        String industryText = HtmlParseUtils.parseToString(html, "所属行业：</span>\\s*", "\\s*<");
        String[] industryArray = StringUtils.split(industryText, ",");
        String foundDate = HtmlParseUtils.parseToString(html, "成立日期：</span>\\s*", "\\s*<");
        String registerFund = HtmlParseUtils.parseToString(html, "注册资金：</span>\\s*", "\\s*<");
        String employeeNumber = HtmlParseUtils.parseToString(html, "员工人数：</span>\\s*", "\\s*<");
        String comIntroduction = HtmlParseUtils.parseToString(html, "EntIntro\" class=\"ItemContent\">\\s*", "\\s*</div>\\s*<div class=\"ItemTitle");
        comIntroduction.replaceAll(HtmlParseUtils.htmlTagPattern,"");

//        comInfo.setId(Integer.valueOf(comInfoId));
        comInfo.setComName(StringUtils.isBlank(comName) ? null : comName);
        comInfo.setBusinessLicence(bussinessLicence);
        comInfo.setProperty(LingHangConstants.getComPropertyCode(property));
        comInfo.setIndustry(LingHangConstants.getComIndustryCode(industryArray));
        comInfo.setFoundDate(DateUtils.parse(foundDate, "yyyy年MM月dd日", null));
        comInfo.setRegisterFund(LingHangConstants.getComRegisterFundCode(registerFund));
        comInfo.setEmployeeNumber(LingHangConstants.getComEmployeeNumberCode(employeeNumber));
        comInfo.setCompanyIntroduction(comIntroduction);
        //默认设置
        comInfo.setSalerId(109);
        comInfo.setSalerName("导入资料");
        comInfo.setSalerRead(0);
        comInfo.setCreateDate(new Date());
        comInfo.setUpdateDate(new Date());
        comInfo.setStatus(1);
        comInfo.setComFlag(2);
        comInfo.setLastEditor("领航人才网");
        comInfo.setNeedHunter(0);
        return comInfo;
    }

    /**
     * 判断链接是否为vip职位详情页
     *
     * @param jobDetailUrl
     * @return
     */
    private boolean isVipJobUrl(final String jobDetailUrl) {
        return jobDetailUrl.contains("jobvip");
    }

    /**
     * 获取职位搜索结果分页总数
     *
     * @param params
     * @return
     */
    private int getJobSearchTotalPagesCount(final Map<String, String> params) throws IOException{
        String html = hcu.getResponseContent(LingHangConstants.JOB_SEARCH_BASIC_URL, params);
        if(StringUtils.isBlank(html)){//空返回则抛错
            StringBuilder urlWithQueryString = new StringBuilder(LingHangConstants.JOB_SEARCH_BASIC_URL);
            urlWithQueryString.append("?");
            for(Map.Entry<String,String> param : params.entrySet()){
                urlWithQueryString.append(param.getKey());
                urlWithQueryString.append("=");
                urlWithQueryString.append(param.getValue());
                urlWithQueryString.append("&");
            }
            throw new RuntimeException("职位搜索结果页请求失败：" + urlWithQueryString.toString());
        }
        String startPattern = "href=\".+page=";
        String endPattern = "\" style=\"margin-right:5px;\">尾页";
        Integer value = HtmlParseUtils.parseToInt(html, startPattern, endPattern);
        return value == null ? 1 : value;//如果为空，意味着最多只有一页
    }
}

class SavePoint {
    private static final Logger logger = Logger.getLogger(SavePoint.class);
    private static final String LINE_DELIMITER = "#";

    private final File saveProgress;
    private final RandomAccessFile saveProgressFileAccess;
    private final List<String> savePointList;

    private SavePoint(String saveFileName) throws IOException{
        //saveProgress
        if(saveFileName == null){
            saveFileName = System.currentTimeMillis() + ".sav";
        }
        String dirPath = ApplicationUtils.getClassPath() + "data/" + LingHangJobCollector.class.getSimpleName();
        saveProgress = new File(dirPath + "/" + saveFileName);
        if(!saveProgress.exists()){
            saveProgress.getParentFile().mkdirs();
            saveProgress.createNewFile();
        }
        saveProgressFileAccess = new RandomAccessFile(saveProgress, "rws");
        //读入文件字节码
        char[] chars = new char[(int) (saveProgressFileAccess.length() >>> 1)];
        int i = 0;
        try {
            while (true) {
                chars[i] = saveProgressFileAccess.readChar();
                i++;
            }
        } catch (EOFException e) {
            logger.info("载入进度存档文件完成！");
        }
        String text = new String(chars);//转成字符串
        savePointList = Lists.newArrayList(text.split(LINE_DELIMITER));//分割
    }

    /**
     * 返回存档对象
     * @param saveFile 如果null,创建新的存档文件
     * @return
     * @throws IOException
     */
    public static final SavePoint create(String saveFile) throws IOException{
        return new SavePoint(saveFile);
    }
    /**
     * 对采集进度做存档
     * @param workLocation
     * @param jobType
     */
    public void setSavePoint(String workLocation, String jobType) throws IOException{
        String savePoint = workLocation + "," + jobType + LINE_DELIMITER;
        saveProgressFileAccess.writeChars(savePoint);
        savePointList.add(savePoint);
    }

    /**
     * 判断是否有保存
     * @param workLocation
     * @param jobType
     * @return
     * @throws IOException
     */
    public boolean isSavePoint(String workLocation, String jobType) throws IOException{
        return savePointList.contains(workLocation+","+jobType);
    }

    /**
     * 关闭存档对象
     * @throws IOException
     */
    public void close() throws IOException{
        saveProgressFileAccess.close();
    }
}