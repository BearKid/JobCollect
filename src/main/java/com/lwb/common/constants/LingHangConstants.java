package com.lwb.common.constants;

import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

/**
 * @autor: Lu Weibiao
 * Date: 2015/4/2 14:08
 */
public class LingHangConstants {
    public static final Logger logger = Logger.getLogger(LingHangConstants.class);

    public static final String DOMAIN = "http://www.0750rc.com";
    public static final String JOB_SEARCH_BASIC_URL = "http://www.0750rc.com/search/offer_search_result.aspx";

    private static final Map<String, Integer> areaMap = new HashMap<String, Integer>();
    private static final Map<String, Integer> jobTypeMap = new HashMap<String, Integer>();
    private static final Map<String, Integer> comPropertyMap = new HashMap<String, Integer>();
    private static final Map<String, Integer> comRegisterFundMap = new HashMap<String, Integer>();
    private static final Map<String, Integer> comEmployeeNumberMap = new HashMap<String, Integer>();
    private static final Map<String, Integer> comIndustryMap = new HashMap<String, Integer>();
    private static final Map<String, Integer> jobSalaryMap = new HashMap<String, Integer>();
    private static final Map<String, Integer> jobPropertyMap = new HashMap<String, Integer>();
    private static final Map<String, Integer> jobReqDegreeMap = new HashMap<String, Integer>();
    private static final Map<String, Integer> jobReqAgeMap = new HashMap<String, Integer>();
    private static final Map<String, Integer> jobReqGenderMap = new HashMap<String, Integer>();
    private static final Map<String, Integer> jobReqWorkYearMap = new HashMap<String, Integer>();
    private static final Map<String, Integer> jobRecruitmentNumber = new HashMap<String, Integer>();

    static {
        /*工作地区代码映射，key是领航，value是job5156*/
        //江门市 areaMap.put(102200,14080000);
//        areaMap.put("102210", 14080100);//江门蓬江区
//        areaMap.put("102211", 14080200);//江门江海区
//        areaMap.put("102212", 14080300);//江门新会区
//        areaMap.put("102213", 14080400);//江门台山市
//        areaMap.put("102214", 14080500);//江门开平市
//        areaMap.put("102215", 14080600);//江门鹤山市
//        areaMap.put("102216", 14080700);//江门恩平市
        //佛山
//        areaMap.put("101410",14090300);//佛山顺德区
//        areaMap.put("101411",14090200);//佛山南海区
//        areaMap.put("101412",14090400);//佛山三水区
//        areaMap.put("101413",14090100);//佛山禅城区
//        areaMap.put("101414",14090500);//佛山高明区
        //中山
        areaMap.put("101010",14040200);//中山南区
        areaMap.put("101011",14040100);//中山东区
        areaMap.put("101012",14040400);//中山西区
        areaMap.put("101013",14040000);//中山市区
        areaMap.put("101014",14040300);//中山石岐区
        areaMap.put("101015",14042400);//中山小榄镇
        areaMap.put("101016",14042100);//中山沙溪镇
        areaMap.put("101017",14042300);//中山坦洲镇
        areaMap.put("101018",14041000);//中山东升镇
        areaMap.put("101019",14041700);//中山南朗镇
        areaMap.put("101020",14042000);//中山三乡镇
        areaMap.put("101021",14041900);//中山三角镇
        areaMap.put("101022",14040500);//中山五桂山
        areaMap.put("101023",14040900);//中山东凤镇
        areaMap.put("101024",14041100);//中山阜沙镇
        areaMap.put("101025",14041500);//中山黄圃镇
        areaMap.put("101026",14041600);//中山民众镇
        areaMap.put("101027",14040600);//中山火炬区
        areaMap.put("101028",14041400);//中山横栏镇
        areaMap.put("101029",14040700);//中山板芙镇
        areaMap.put("101030",14040800);//中山大涌镇
        areaMap.put("101031",14041300);//中山古镇
        areaMap.put("101032",14042200);//中山神湾镇
        areaMap.put("101033",14041200);//中山港口镇
        areaMap.put("101034",14041800);//中山南头镇
        //TODO 其他地区

        /*职位类别代码映射，key是领航，value是job5156*/
        //计算机/互联网/电子商务类 (11000,1000)
        jobTypeMap.put("11001", 1003);//数据库开发与管理(DBA)
        jobTypeMap.put("11002", 1005);//系统分析员
        jobTypeMap.put("11003", 1015);//软件工程师
        jobTypeMap.put("11022", 1009);//软件实施
        jobTypeMap.put("11004", 1019);//网页设计师
        jobTypeMap.put("11026", 1024);//SEO专员
        jobTypeMap.put("11005", 1022);//电脑美工
        jobTypeMap.put("11027", 1013);//UI/UE设计师
        jobTypeMap.put("11023", 1024);//网店美工
        jobTypeMap.put("11024", 1020);//网站/网店运营
        jobTypeMap.put("11006", 1020);//网站/网店策划
        jobTypeMap.put("11007", 1020);//网站编辑
        jobTypeMap.put("11019", 1010);//网站程序开发
        jobTypeMap.put("11028", 1014);//架构师
        jobTypeMap.put("11008", 1023);//栏目主持人
        jobTypeMap.put("11009", 1012);//INTERNET/WEB/电子商务
        jobTypeMap.put("11010", 1016);//测试工程师
        jobTypeMap.put("11011", 1009);//MRP/ERP/SAP实施工程师
        jobTypeMap.put("11012", 1100);//系统集成/技术支持
        jobTypeMap.put("11013", 1018);//硬件工程师
        jobTypeMap.put("11014", 1006);//系统维护员
        jobTypeMap.put("11015", 1008);//网络管理员
        jobTypeMap.put("11025", 1002);//网络与信息安全工程师
        jobTypeMap.put("11016", 1013);//CAM、MI工程师与技术员
        jobTypeMap.put("11018", 1011);//多媒体/游戏开发工程师
        jobTypeMap.put("11020", 1099);//打印/复印/外设维修
        jobTypeMap.put("11021", 1103);//计算机技术员
        jobTypeMap.put("11017", 1099);//其它相关职位
//销售类 jobTypeMap.put("12000",null);
        jobTypeMap.put("12001",1403);//销售部经理
        jobTypeMap.put("12002",1404);//销售主管
        jobTypeMap.put("12003",1501);//销售代表
        jobTypeMap.put("12004",1405);//销售助理
        jobTypeMap.put("12005",1504);//推（营）销员
        jobTypeMap.put("12006",1402);//销售工程师
        jobTypeMap.put("12007",1410);//商务经理/商务专员
        jobTypeMap.put("12008",1406);//渠道经理
        jobTypeMap.put("12009",1407);//渠道主管
        jobTypeMap.put("12010",1409);//区域销售经理
        jobTypeMap.put("12011",1408);//分销经理
        jobTypeMap.put("12012",1503);//业务员
        jobTypeMap.put("12014",1504);//电话营销
        jobTypeMap.put("12013",1599);//其它相关职位
//市场营销/公关类 jobTypeMap.put("13000",null);
        jobTypeMap.put("13001",1715);//市场/营销经理
        jobTypeMap.put("13002",1705);//市场助理/专员
        jobTypeMap.put("13003",1706);//市场部主管
        jobTypeMap.put("13004",1707);//市场调研/业务分析
        jobTypeMap.put("13005",1708);//市场/行销企划
        jobTypeMap.put("13006",1710);//产品/品牌企划
        jobTypeMap.put("13007",1711);//品牌经理
        jobTypeMap.put("13008",1712);//价格企划
        jobTypeMap.put("13009",1713);//广告企划
        jobTypeMap.put("13010",1701);//新闻媒介企划
        jobTypeMap.put("13011",1709);//市场推广/拓展/合作
        jobTypeMap.put("13012",1702);//促销/礼仪专员
        jobTypeMap.put("13013",1703);//公关经理
        jobTypeMap.put("13014",1704);//公关专员
        jobTypeMap.put("13016",1703);//会务经理/主管
        jobTypeMap.put("13017",1704);//会务专员/助理
        jobTypeMap.put("13018",1799);//司仪
        jobTypeMap.put("13019",1802);//招商专员
        jobTypeMap.put("13015",1799);//其它相关职位
//客户服务类 jobTypeMap.put("14000",1600);
        jobTypeMap.put("14001",1606);//客户服务经理
        jobTypeMap.put("14002",1699);//客户数据库管理
        jobTypeMap.put("14003",1604);//客户关系管理
        jobTypeMap.put("14004",1699);//客户培训
        jobTypeMap.put("14005",1605);//客户咨询
        jobTypeMap.put("14006",1605);//热线咨询
        jobTypeMap.put("14007",1601);//客户服务
        jobTypeMap.put("14008",1602);//售前/售后支持
        jobTypeMap.put("14009",1603);//投诉处理
        jobTypeMap.put("14010",1603);//投诉监控
        jobTypeMap.put("14011",1606);//客户主任
        jobTypeMap.put("14013",1699);//网店客服
        jobTypeMap.put("14012",1699);//其它相关职位
//经营/管理类 jobTypeMap.put("15000",null);
        jobTypeMap.put("15001",1801);//(正/副)总裁/总经理/CEO
        jobTypeMap.put("15002",1810);//厂长/副厂长
        jobTypeMap.put("15003",1111);//技术总监CTO
        jobTypeMap.put("15004",1714);//市场总监
        jobTypeMap.put("15005",2203);//人力资源总监
        jobTypeMap.put("15006",2208);//行政总监
        jobTypeMap.put("15007",1112);//信息主管/CIO
        jobTypeMap.put("15008",1807);//部门经理
        jobTypeMap.put("15009",1807);//部门主管
        jobTypeMap.put("15010",1808);//总经理助理
        jobTypeMap.put("15011",1809);//经理助理
        jobTypeMap.put("15012",1811);//项目经理
        jobTypeMap.put("15013",2018);//财务总监
        jobTypeMap.put("15015",2418);//储备干部
        jobTypeMap.put("15016",1899);//合伙人
        jobTypeMap.put("15017",1806);//分公司/分支机构/办事处经理
        jobTypeMap.put("15014",1899);//其它相关职位
//财务/审(统)计类 jobTypeMap.put("16000",null);
        jobTypeMap.put("16001",2015);//财务主管/经理
        jobTypeMap.put("16002",2005);//会计
        jobTypeMap.put("16003",2005);//会计助理
        jobTypeMap.put("16004",2002);//出纳
        jobTypeMap.put("16005",2004);//注册会计师
        jobTypeMap.put("16006",2014);//注册审计师
        jobTypeMap.put("16007",2013);//审计
        jobTypeMap.put("16008",2415);//统计
        jobTypeMap.put("16009",2017);//财务分析
        jobTypeMap.put("16010",2008);//成本分析/核算
        jobTypeMap.put("16011",2006);//帐目(进出口)管理
        jobTypeMap.put("16013",2011);//税务经理/主管
        jobTypeMap.put("16014",2001);//税务专员/助理
        jobTypeMap.put("16012",2099);//其它相关职位
//公司文职类 jobTypeMap.put("17000",null);
        jobTypeMap.put("17001",2307);//图书情报/档案管理
        jobTypeMap.put("17002",2301);//文案策划/资料编写
        jobTypeMap.put("17003",2305);//高级文员
        jobTypeMap.put("17004",2302);//高级秘书
        jobTypeMap.put("17005",2306);//电脑操作员/打字员
        jobTypeMap.put("17006",2304);//前台文员接待
        jobTypeMap.put("17007",2303);//话务员
        jobTypeMap.put("17008",2205);//文员/文秘
        jobTypeMap.put("17009",2399);//其它相关职位
//行政/人事类 jobTypeMap.put("18000",null);
        jobTypeMap.put("18001",2204);//人事经理
        jobTypeMap.put("18002",2209);//行政经理
        jobTypeMap.put("18003",2205);//人事助理
        jobTypeMap.put("18004",2210);//行政助理
        jobTypeMap.put("18005",2205);//人事文员
        jobTypeMap.put("18006",2210);//行政文员
        jobTypeMap.put("18007",2214);//总务
        jobTypeMap.put("18008",2202);//薪资福利经理/主管/专员
        jobTypeMap.put("18009",2206);//绩效考核经理/主管/专员
        jobTypeMap.put("18010",2201);//员工培训经理/主管
        jobTypeMap.put("18011",2207);//招聘经理/主任
        jobTypeMap.put("18012",2207);//招聘专员
        jobTypeMap.put("18013",2299);//其它相关职位
//工业/工厂类 jobTypeMap.put("19000",2400);
        jobTypeMap.put("19001",2426);//产品开发
        jobTypeMap.put("19002",2424);//生产经理/主管
        jobTypeMap.put("19003",2407);//生管主管/督导
        jobTypeMap.put("19004",2408);//生管员
        jobTypeMap.put("19005",2409);//PE工程师
        jobTypeMap.put("19006",2410);//IE工程师
        jobTypeMap.put("19007",2406);//工艺工程师
        jobTypeMap.put("19008",2413);//组长/拉长
        jobTypeMap.put("19009",2425);//工程经理/主管
        jobTypeMap.put("19010",2412);//工程设备工程师
        jobTypeMap.put("19011",2201);//品管经理/主管
        jobTypeMap.put("19012",2706);//品管/质检工程师
        jobTypeMap.put("19013",2507);//品管员/质检员
        jobTypeMap.put("19014",2505);//ISO专员
        jobTypeMap.put("19015",2422);//物控经理/主管
        jobTypeMap.put("19016",2414);//物控员
        jobTypeMap.put("19017",4212);//仓库管理员
        jobTypeMap.put("19018",2403);//计划员/调度员
        jobTypeMap.put("19019",2423);//设备经理/主管
        jobTypeMap.put("19020",2506);//化验/检验员
        jobTypeMap.put("19021",2516);//安全主任
        jobTypeMap.put("19022",2404);//跟单员
        jobTypeMap.put("19023",2415);//统计员
        jobTypeMap.put("19024",2427);//采购管理
        jobTypeMap.put("19025",2405);//SMT技术员
        jobTypeMap.put("19026",2411);//ME工程师
        jobTypeMap.put("19027",2416);//制造课长
        jobTypeMap.put("19028",2417);//RD经理
        jobTypeMap.put("19030",2423);//设备技术员
        jobTypeMap.put("19031",2499);//资料管理及事物员
        jobTypeMap.put("19033",2419);//车间管理
        jobTypeMap.put("19034",2405);//PMC工程师
        jobTypeMap.put("19035",2499);//收发员
        jobTypeMap.put("19032",2499);//其它相关职位
//电子通讯/电气(器)类 jobTypeMap.put("20000",null);
        jobTypeMap.put("20001",1301);//电子工程师/技术员
        jobTypeMap.put("20002",1302);//电气工程师/技术员
        jobTypeMap.put("20003",1303);//电器工程师/技术员
        jobTypeMap.put("20004",1304);//电力工程师/技术员
        jobTypeMap.put("20005",1207);//电信工程师/通讯工程师
        jobTypeMap.put("20006",1311);//变压器/磁电工程师
        jobTypeMap.put("20007",1312);//电声/音响工程师
        jobTypeMap.put("20008",1323);//数码产品开发工程师
        jobTypeMap.put("20009",1317);//单片机/DSP/底层软件开发
        jobTypeMap.put("20010",1306);//电路(布线)设计
        jobTypeMap.put("20011",1307);//智能大厦/综合布线/弱电
        jobTypeMap.put("20012",1318);//光源与照明工程
        jobTypeMap.put("20013",1319);//灯饰研发工程师
        jobTypeMap.put("20014",1320);//电气维修
        jobTypeMap.put("20015",1308);//自动控制
        jobTypeMap.put("20016",1309);//无线电技术
        jobTypeMap.put("20017",1310);//半导体技术
        jobTypeMap.put("20018",1321);//家用电器
        jobTypeMap.put("20019",1322);//小家电
        jobTypeMap.put("20020",1213);//测试工程师
        jobTypeMap.put("20022",1313);//集成电路(IC)芯片开发
        jobTypeMap.put("20023",1316);//电池/电源开发工程师
        jobTypeMap.put("20021",1399);//其它相关职位
//机械(电)/仪表类 jobTypeMap.put("21000",2600);
        jobTypeMap.put("21001",2605);//机械设计与制造
        jobTypeMap.put("21002",2606);//机械工程师
        jobTypeMap.put("21003",2604);//机械工艺师
        jobTypeMap.put("21005",2716);//刀具工程师
        jobTypeMap.put("21007",2602);//结构设计师
        jobTypeMap.put("21008",2616);//机电一体化
        jobTypeMap.put("21009",2617);//铸造/锻造
        jobTypeMap.put("21011",2615);//气动/液压
        jobTypeMap.put("21012",2601);//机械制图
        jobTypeMap.put("21013",2611);//精密机械/仪器仪表
        jobTypeMap.put("21014",2620);//设备修理
        jobTypeMap.put("21015",2613);//汽车/摩托车工程师
        jobTypeMap.put("21016",2622);//五金矿产/金属制品
        jobTypeMap.put("21017",3505);//锅炉/压力容器
        jobTypeMap.put("21018",2612);//纺织机械
        jobTypeMap.put("21019",2607);//船舶机械
        jobTypeMap.put("21020",2609);//食品机械
        jobTypeMap.put("21021",2610);//焊接机械
        jobTypeMap.put("21023",2708);//冲压工程师
        jobTypeMap.put("21024",2716);//夹具工程师
        jobTypeMap.put("21025",2607);//船舶工程师
        jobTypeMap.put("21026",2618);//数控技术员
        jobTypeMap.put("21027",2603);//飞行器设计与制造
        jobTypeMap.put("21022",2699);//其它相关职位
//金融/保险/证券/投资类 jobTypeMap.put("22000",2100);
        jobTypeMap.put("22001",2106);//证券期货
        jobTypeMap.put("22002",2131);//银行信贷
        jobTypeMap.put("22003",2110);//金融投资分析
        jobTypeMap.put("22004",2114);//融资经理/主管
        jobTypeMap.put("22005",2125);//出纳员/银行专员
        jobTypeMap.put("22006",2126);//预结算专员
        jobTypeMap.put("22007",2001);//税务专员
        jobTypeMap.put("22008",2101);//评估师
        jobTypeMap.put("22009",2106);//股票/期货操盘手
        jobTypeMap.put("22010",2118);//保险业务/保险经纪人/保险代理人
        jobTypeMap.put("22012",2104);//拍卖师
        jobTypeMap.put("22013",2115);//融资专员/助理
        jobTypeMap.put("22014",2102);//精算师
        jobTypeMap.put("22015",2113);//证券/投资/基金/期货分析
        jobTypeMap.put("22017",2103);//风险管理/控制/稽查
        jobTypeMap.put("22016",2130);//行长/副行长/高级管理
        jobTypeMap.put("22019",2127);//大堂经理
        jobTypeMap.put("22023",2113);//基金经理
        jobTypeMap.put("22020",2128);//银行卡/电子银行/新业务开拓
        jobTypeMap.put("22018",2121);//理财顾问/财务规划师
        jobTypeMap.put("22021",2122);//保险理赔员
        jobTypeMap.put("22022",2124);//国际结算/外汇交易
        jobTypeMap.put("22024",2105);//证券/期货/外汇经纪人
        jobTypeMap.put("22025",2104);//担保/拍卖/典当业务
        jobTypeMap.put("22011",2299);//其它相关职位
//房地产/建筑施工类 jobTypeMap.put("23000",2800);
        jobTypeMap.put("23001",2806);//房地产开发/策划
        jobTypeMap.put("23002",2808);//房地产评估
        jobTypeMap.put("23003",2801);//房地产中介/销售
        jobTypeMap.put("23004",2804);//物业管理
        jobTypeMap.put("23026",2805);//物业招商/租赁/租售
        jobTypeMap.put("23005",2900);//建筑(结构)工程师
        jobTypeMap.put("23006",2905);//注册建筑师
        jobTypeMap.put("23007",2904);//建筑制图/建筑设计
        jobTypeMap.put("23008",2914);//工程监理
        jobTypeMap.put("23009",2916);//管道(水、电)
        jobTypeMap.put("23010",2918);//制冷暖通
        jobTypeMap.put("23011",2917);//给排水/供水(电)工程
        jobTypeMap.put("23012",2907);//工民建
        jobTypeMap.put("23013",2900);//工程预决算/施工
        jobTypeMap.put("23014",2912);//路桥技术/隧道工程
        jobTypeMap.put("23015",2913);//基建/岩土工程
        jobTypeMap.put("23016",2919);//港口与航道工程
        jobTypeMap.put("23017",2922);//园艺工程/园林技术
        jobTypeMap.put("23019",2910);//工程造价/预结算工程师
        jobTypeMap.put("23020",2902);//报批/报建专员
        jobTypeMap.put("23021",2906);//土木/土建工程师
        jobTypeMap.put("23022",2901);//投标主管
        jobTypeMap.put("23023",2999);//建筑工程管理
        jobTypeMap.put("23024",9911);//市政建设/城市规划
        jobTypeMap.put("23025",2911);//施工员
        jobTypeMap.put("23027",2908);//建筑工程验收
        jobTypeMap.put("23029",2928);//现场施工管理
        jobTypeMap.put("23030",9905);//测绘/测量
        jobTypeMap.put("23018",2999);//其它相关职位
//广告(装潢、包装)设计类 jobTypeMap.put("24000",3000);
        jobTypeMap.put("24001",3017);//平面设计
        jobTypeMap.put("24002",3007);//三维动画设计
        jobTypeMap.put("24003",3008);//多媒体设计与制作
        jobTypeMap.put("24004",3010);//产品包装设计
        jobTypeMap.put("24005",3004);//室内(外)装修/装潢设计
        jobTypeMap.put("24006",3011);//工艺品设计
        jobTypeMap.put("24007",3012);//工业产品设计
        jobTypeMap.put("24008",3014);//纺织/服饰(装)设计
        jobTypeMap.put("24009",3017);//形象设计
        jobTypeMap.put("24010",3018);//玩具设计
        jobTypeMap.put("24011",3019);//珠宝设计
        jobTypeMap.put("24018",4701);//家具设计
        jobTypeMap.put("24012",3003);//文案/媒体策划
        jobTypeMap.put("24013",3002);//广告设计/创意策划
        jobTypeMap.put("24015",1013);//计算机辅助设计CAD
        jobTypeMap.put("24016",3099);//广告喷绘/招牌制作
        jobTypeMap.put("24017",3099);//3D建模
        jobTypeMap.put("24019",3006);//陈列/展览设计
        jobTypeMap.put("24020",3099);//灯饰设计
        jobTypeMap.put("24022",3015);//灯光设计
        jobTypeMap.put("24021",3013);//雕塑设计
        jobTypeMap.put("24014",3099);//其它相关职位
//文体/教育/培训/科研类 jobTypeMap.put("25000",3212);
        jobTypeMap.put("25001",4409);//新闻出版
        jobTypeMap.put("25002",3111);//记者
        jobTypeMap.put("25003",3100);//广播电视(影)
        jobTypeMap.put("25004",3201);//文化艺术
        jobTypeMap.put("25005",3205);//高等教育
        jobTypeMap.put("25006",3206);//中级教育
        jobTypeMap.put("25013",3208);//小学教育
        jobTypeMap.put("25017",3208);//幼教
        jobTypeMap.put("25016",3208);//早期启蒙教育
        jobTypeMap.put("25010",3210);//家教
        jobTypeMap.put("25008",3209);//竞技/体育
        jobTypeMap.put("25009",3212);//职业教育/培训
        jobTypeMap.put("25014",3299);//招生专员
        jobTypeMap.put("25015",3217);//教学/教务管理人员
        jobTypeMap.put("25018",3213);//计算机培训
        jobTypeMap.put("25019",3214);//英语培训
        jobTypeMap.put("25020",3299);//音乐/美术教育
        jobTypeMap.put("25021",3299);//科研管理人员
        jobTypeMap.put("25022",3299);//科研人员
        jobTypeMap.put("25012",3299);//其它相关职位
//卫生医疗/美容保健类 jobTypeMap.put("26000",3300);
        jobTypeMap.put("26001",3301);//全科医生/医师
        jobTypeMap.put("26024",3302);//内科医生
        jobTypeMap.put("26004",3302);//外科医生
        jobTypeMap.put("26002",3302);//牙科医生
        jobTypeMap.put("26003",3302);//心理医生
        jobTypeMap.put("26005",3302);//妇产科医生
        jobTypeMap.put("26029",3302);//儿科医生
        jobTypeMap.put("26025",3302);//皮肤科医生
        jobTypeMap.put("26006",3302);//预防医生
        jobTypeMap.put("26026",3302);//放射医生
        jobTypeMap.put("26027",3302);//超声医生
        jobTypeMap.put("26028",3317);//麻醉医生
        jobTypeMap.put("26007",3303);//护士/护理
        jobTypeMap.put("26010",3306);//药剂/中药/西药/药检
        jobTypeMap.put("26030",3399);//中医
        jobTypeMap.put("26031",3399);//中西医结合
        jobTypeMap.put("26016",3399);//医学检验
        jobTypeMap.put("26012",3305);//临床医学
        jobTypeMap.put("26015",3420);//医药代表
        jobTypeMap.put("26013",3304);//妇幼保健
        jobTypeMap.put("26014",3304);//卫生防疫
        jobTypeMap.put("26017",3308);//兽医
        jobTypeMap.put("26011",3304);//针灸推拿
        jobTypeMap.put("26009",3316);//美容/美发
        jobTypeMap.put("26008",3312);//保健/健美
        jobTypeMap.put("26019",3103);//化妆/美容/整形师
        jobTypeMap.put("26020",3310);//足浴技师/洗头技师
        jobTypeMap.put("26021",3017);//发型师/形象设计
        jobTypeMap.put("26022",3311);//美甲师
        jobTypeMap.put("26023",3312);//保健/营养师
        jobTypeMap.put("26032",3313);//健身顾问/教练
        jobTypeMap.put("26033",3310);//发型助理/学徒
        jobTypeMap.put("26018",3399);//其它相关职位
//化工/制药类 jobTypeMap.put("27000",3400);
        jobTypeMap.put("27001",3403);//日用化工
        jobTypeMap.put("27002",3405);//生物化工/生物制药
        jobTypeMap.put("27003",3406);//造纸/废品处理
        jobTypeMap.put("27004",3413);//化学药剂/药品
        jobTypeMap.put("27005",3408);//玻璃/硅酸盐工业
        jobTypeMap.put("27006",3409);//农药、化肥
        jobTypeMap.put("27008",3410);//有机化工
        jobTypeMap.put("27007",3410);//无机化工
        jobTypeMap.put("27009",3412);//精细化工
        jobTypeMap.put("27010",3412);//分析化工
        jobTypeMap.put("27011",3411);//高分子化工/化纤/新材料
        jobTypeMap.put("27012",3412);//电镀化工
        jobTypeMap.put("27015",3417);//医药研发/化学制剂研发
        jobTypeMap.put("27014",3415);//药品注册
        jobTypeMap.put("27016",3499);//食品/饮料研发
        jobTypeMap.put("27017",3499);//化妆品研发
        jobTypeMap.put("27013",3499);//其它相关职位
//能源动力类 jobTypeMap.put("28000",3500);
        jobTypeMap.put("28001",3501);//水利、水电
        jobTypeMap.put("28002",3502);//核电、火电
        jobTypeMap.put("28003",3503);//电厂、电力
        jobTypeMap.put("28004",3504);//制冷、暖通
        jobTypeMap.put("28005",3505);//空调、锅炉
        jobTypeMap.put("28006",3506);//石油/天燃气/储运
        jobTypeMap.put("28007",3508);//城市燃气
        jobTypeMap.put("28008",3599);//其它相关职位
//宾馆饭店/餐饮旅游类 jobTypeMap.put("29000",3600);
        jobTypeMap.put("29001",2127);//大堂经理/副理
        jobTypeMap.put("29002",3604);//酒店管理
        jobTypeMap.put("29016",3612);//娱乐/餐饮管理
        jobTypeMap.put("29003",3603);//楼面经理/主任
        jobTypeMap.put("29004",3619);//服务员/迎宾/行李员
        jobTypeMap.put("29007",3606);//前台接待/礼仪/接线生
        jobTypeMap.put("29006",3613);//导游
        jobTypeMap.put("29014",3605);//订票/订房服务
        jobTypeMap.put("29005",3608);//高级厨师/调酒师
        jobTypeMap.put("29009",3699);//酱卤肉师傅
        jobTypeMap.put("29010",3610);//面包/面点师傅
        jobTypeMap.put("29011",3610);//中/西厨师
        jobTypeMap.put("29012",3699);//点心厨师
        jobTypeMap.put("29013",3699);//打荷/水台/粘板
        jobTypeMap.put("29015",3611);//茶艺师
        jobTypeMap.put("29008",3699);//其它相关职位
//商店/零售服务类 jobTypeMap.put("30000",null);
        jobTypeMap.put("30001",3904);//店长
        jobTypeMap.put("30002",3906);//营销主管
        jobTypeMap.put("30003",3901);//营业员/服务员/店员
        jobTypeMap.put("30004",4207);//理货员
        jobTypeMap.put("30005",3902);//导购员
        jobTypeMap.put("30006",3903);//收银员
        jobTypeMap.put("30008",3908);//陈列员
        jobTypeMap.put("30009",3913);//送货员
        jobTypeMap.put("30010",2415);//统计员
        jobTypeMap.put("30007",3999);//其它相关职位
//技工/普工类 jobTypeMap.put("31000",4000);
        jobTypeMap.put("31001",4005);//车、铣、刨、磨
        jobTypeMap.put("31002",4006);//钳、钣、铆、冲、焊、铸
        jobTypeMap.put("31003",4007);//裁、剪、车、缝、熨、烫
        jobTypeMap.put("31004",4012);//水工/木工/油漆工
        jobTypeMap.put("31005",4002);//电工
        jobTypeMap.put("31017",4010);//锅炉工
        jobTypeMap.put("31006",4014);//普通工人
        jobTypeMap.put("31008",4011);//摩托车/叉车/汽车维修工
        jobTypeMap.put("31009",4606);//装配工/包装工
        jobTypeMap.put("31010",2711);//数控车床/CNC
        jobTypeMap.put("31011",3505);//空调/电梯维修工
        jobTypeMap.put("31012",3700);//印刷机长/副手
        jobTypeMap.put("31013",4199);//玩具技工
        jobTypeMap.put("31014",4199);//陶瓷技工
        jobTypeMap.put("31015",3412);//电镀技工
        jobTypeMap.put("31016",2712);//电火花/线切割
        jobTypeMap.put("31018",2799);//绣花工
        jobTypeMap.put("31019",2707);//吹塑/注塑/吸塑
        jobTypeMap.put("31020",2799);//学徒工
        jobTypeMap.put("31021",2701);//模具工程师
        jobTypeMap.put("31007",2799);//其它相关职位
//轻工类 jobTypeMap.put("32000",null);
        jobTypeMap.put("32001",3800);//服装纺织
        jobTypeMap.put("32002",4105);//印刷/染整技术
        jobTypeMap.put("32003",4107);//纸浆造纸工艺
        jobTypeMap.put("32004",4108);//制鞋/制衣/制革/手袋
        jobTypeMap.put("32005",4114);//食品工程/糖酒饮料/粮油副食
        jobTypeMap.put("32006",4100);//陶瓷技术
        jobTypeMap.put("32007",4115);//金银首饰加工
        jobTypeMap.put("32010",3808);//配色师傅/调色/定型员
        jobTypeMap.put("32011",3806);//纸样/车版/样品工
        jobTypeMap.put("32013",4110);//样房/楦头/底格出格师
        jobTypeMap.put("32014",4109);//面料/辅料开发/采购专员
        jobTypeMap.put("32008",4199);//其它相关职位
//后勤保障类 jobTypeMap.put("33000",4300);
        jobTypeMap.put("33010",4206);//快递员
        jobTypeMap.put("33002",4310);//寻呼/声讯服务
        jobTypeMap.put("33003",4305);//社区/家政服务
        jobTypeMap.put("33004",4306);//清洁工/保洁员
        jobTypeMap.put("33005",4308);//食堂厨师
        jobTypeMap.put("33008",4309);//搬运工
        jobTypeMap.put("33001",4302);//保安/门卫
        jobTypeMap.put("33011",4308);//厨工
        jobTypeMap.put("33009",3913);//送货员
        jobTypeMap.put("33012",4311);//保姆
        jobTypeMap.put("33014",4311);//钟点工/临时工
        jobTypeMap.put("33013",4399);//宿舍管理员
        jobTypeMap.put("33006",4399);//其它相关职位
//翻译类 jobTypeMap.put("34000",null);
        jobTypeMap.put("34001",4501);//英语翻译
        jobTypeMap.put("34002",4502);//日语翻译
        jobTypeMap.put("34003",4503);//法语翻译
        jobTypeMap.put("34004",4504);//德语翻译
        jobTypeMap.put("34005",4505);//俄语翻译
        jobTypeMap.put("34006",4506);//朝鲜语翻译
        jobTypeMap.put("34007",4508);//西班牙语翻译
        jobTypeMap.put("34009",4599);//韩语翻译
        jobTypeMap.put("34010",4599);//葡萄牙语翻译
        jobTypeMap.put("34011",4510);//阿拉伯语翻译
        jobTypeMap.put("34012",4509);//意大利语翻译
        jobTypeMap.put("34013",4599);//泰语翻译
        jobTypeMap.put("34014",4511);//中国方言翻译
        jobTypeMap.put("34008",4599);//其它语种翻译
//物流/运输/贸易类 jobTypeMap.put("35000",null);
        jobTypeMap.put("35001",4204);//物流经理
        jobTypeMap.put("35002",4204);//物流主管
        jobTypeMap.put("35003",4211);//仓库经理/主管
        jobTypeMap.put("35008",4201);//物流操作员
        jobTypeMap.put("35005",4208);//运输经理/主管
        jobTypeMap.put("35006",4205);//单证员
        jobTypeMap.put("35007",4206);//快递员/速递员/配送员
        jobTypeMap.put("35009",4227);//船务人员
        jobTypeMap.put("35015",4399);//安检员
        jobTypeMap.put("35012",4226);//报关员
        jobTypeMap.put("35021",4209);//货运代理
        jobTypeMap.put("35022",4104);//物料管理
        jobTypeMap.put("35016",4303);//司机/驾驶员
        jobTypeMap.put("35017",4399);//乘务员
        jobTypeMap.put("35014",4222);//外贸业务员
        jobTypeMap.put("35020",4224);//外贸跟单员
        jobTypeMap.put("35018",2201);//国内贸易经理/主管
        jobTypeMap.put("35019",2201);//国外贸易经理/主管
        jobTypeMap.put("35023",4399);//海关事务管理
        jobTypeMap.put("35024",4210);//集装箱业务
        jobTypeMap.put("35025",4399);//空运/陆运操作
        jobTypeMap.put("35013",4399);//其它相关职位
//咨询/顾问类 jobTypeMap.put("36000",null);
        jobTypeMap.put("36001",1913);//企业策划/顾问
        jobTypeMap.put("36002",1908);//企业管理/企管顾问
        jobTypeMap.put("36003",1909);//涉外咨询师
        jobTypeMap.put("36004",1910);//高级猎头顾问
        jobTypeMap.put("36005",1911);//咨询总监
        jobTypeMap.put("36006",1911);//咨询经理
        jobTypeMap.put("36007",1912);//咨询员
        jobTypeMap.put("36008",1912);//信息中介
        jobTypeMap.put("36009",1912);//专业顾问
        jobTypeMap.put("36011",1999);//调研员
        jobTypeMap.put("36012",1999);//培训师
        jobTypeMap.put("36010",1999);//其它相关职位
//法律专业人员类 jobTypeMap.put("37000",null);
        jobTypeMap.put("37001",1901);//律师
        jobTypeMap.put("37002",1902);//法律顾问
        jobTypeMap.put("37003",1903);//法务人员
        jobTypeMap.put("37004",1904);//律师助理
        jobTypeMap.put("37005",1905);//书记员
        jobTypeMap.put("37007",1907);//知识产权/专利顾问
        jobTypeMap.put("37006",1999);//其它相关职位
//影视/摄影专业类 jobTypeMap.put("38000",3100);
        jobTypeMap.put("38001",3101);//影视策划/制作人员
        jobTypeMap.put("38002",3102);//影音器材管理
        jobTypeMap.put("38011",3113);//导演/编导
        jobTypeMap.put("38003",3112);//演员
        jobTypeMap.put("38004",3105);//模特儿
        jobTypeMap.put("38005",3106);//摄影师
        jobTypeMap.put("38006",3107);//音效师
        jobTypeMap.put("38007",3108);//节目主持人
        jobTypeMap.put("38009",3110);//配音员
        jobTypeMap.put("38012",3114);//经纪人/星探
        jobTypeMap.put("38010",3103);//化妆师/造型师
        jobTypeMap.put("38013",3199);//后期制作
        jobTypeMap.put("38008",3199);//其它相关职位
//编辑/发行类 jobTypeMap.put("39000",4411);
        jobTypeMap.put("39001",4401);//总编
        jobTypeMap.put("39002",4401);//副总编
        jobTypeMap.put("39003",4403);//编辑主任
        jobTypeMap.put("39004",4411);//编辑/作家/撰稿人
        jobTypeMap.put("39005",4406);//美术编辑
        jobTypeMap.put("39006",4402);//发行主管
        jobTypeMap.put("39007",4402);//发行助理
        jobTypeMap.put("39009",4405);//采访主任
        jobTypeMap.put("39010",4407);//排版设计
        jobTypeMap.put("39011",4408);//校对/录入
        jobTypeMap.put("39008",4499);//其它相关职位
//水产类 jobTypeMap.put("41000",null);
        jobTypeMap.put("41001",9999);//种苗培育
        jobTypeMap.put("41002",9999);//名特优养殖
        jobTypeMap.put("41003",9999);//淡水养殖
        jobTypeMap.put("41004",9999);//海水养殖
        jobTypeMap.put("41005",9999);//水产加工
        jobTypeMap.put("41006",9999);//水产经贸
        jobTypeMap.put("41007",9999);//渔经管理
        jobTypeMap.put("41008",9999);//水产科研
        jobTypeMap.put("41009",9999);//水产教学
        jobTypeMap.put("41010",9999);//其它相关职位
//幼师类 jobTypeMap.put("42000",null);
        jobTypeMap.put("42001",3299);//园长
        jobTypeMap.put("42002",3299);//业务园长
        jobTypeMap.put("42003",3299);//后勤园长
        jobTypeMap.put("42004",3299);//教研主任
        jobTypeMap.put("42005",3299);//园长助理
        jobTypeMap.put("42007",3299);//主班教师
        jobTypeMap.put("42008",3299);//中文教师
        jobTypeMap.put("42009",3299);//英文教师
        jobTypeMap.put("42010",3299);//蒙氏老师
        jobTypeMap.put("42011",3299);//音乐老师
        jobTypeMap.put("42012",3314);//舞蹈老师
        jobTypeMap.put("42013",3299);//体育老师
        jobTypeMap.put("42014",3299);//美术老师
        jobTypeMap.put("42015",3299);//电教教师
        jobTypeMap.put("42016",3218);//外籍教师
        jobTypeMap.put("42017",3299);//夜班教师
        jobTypeMap.put("42018",3299);//珠心算教师
        jobTypeMap.put("42019",3299);//其他才艺教师
        jobTypeMap.put("42020",3299);//保健医生
        jobTypeMap.put("42021",3299);//生活老师（保育员）
        jobTypeMap.put("42022",3299);//育婴师
        jobTypeMap.put("42023",2002);//出纳
        jobTypeMap.put("42024",2005);//会计
        jobTypeMap.put("42025",4308);//厨师
        jobTypeMap.put("42026",4302);//保安
        jobTypeMap.put("42027",3299);//特色教师
        jobTypeMap.put("42028",1505);//渠道销售专员
        jobTypeMap.put("42029",1501);//销售代表
        jobTypeMap.put("42030",3299);//钢琴教师
        jobTypeMap.put("42031",3299);//早教指导师
        jobTypeMap.put("42032",3299);//课程顾问
        jobTypeMap.put("42033",3299);//早教中心主任
//其他类 jobTypeMap.put("40000",null);
        jobTypeMap.put("40001",9902);//交通运输
        jobTypeMap.put("40002",9903);//声光技术
        jobTypeMap.put("40003",9904);//生物技术
        jobTypeMap.put("40004",9905);//测绘技术
        jobTypeMap.put("40005",9906);//激光技术
        jobTypeMap.put("40006",9907);//地质勘探
        jobTypeMap.put("40007",2622);//矿产冶金
        jobTypeMap.put("40008",9909);//环境工程
        jobTypeMap.put("40009",9911);//市政建设/城市规划
        jobTypeMap.put("40010",9910);//农、林、牧、渔、其他
        jobTypeMap.put("40012",9999);//实习生
        jobTypeMap.put("40013",9999);//培训生
        jobTypeMap.put("40011",9999);//其它相关职位

        /*职位月薪*/
        jobSalaryMap.put("1000以下", 1);
        jobSalaryMap.put("1000-1500", 1);
        jobSalaryMap.put("2500-3000", 3);
        jobSalaryMap.put("3000-4000", 4);
        jobSalaryMap.put("4000-5000", 4);
        jobSalaryMap.put("5000-7000", 6);
        jobSalaryMap.put("7000-10000", 7);
        jobSalaryMap.put("10000-15000", 8);
        jobSalaryMap.put("15000以上", 0);
        jobSalaryMap.put("面议", 0);

        /*职位类型*/
        jobPropertyMap.put("兼职", 2);
        jobPropertyMap.put("SOHO", 2);
        jobPropertyMap.put("实习", 3);
        jobPropertyMap.put("暑假工", 2);
        jobPropertyMap.put("全职", 1);

        /*职位工作经验*/
        jobReqWorkYearMap.put("无", 0);
        jobReqWorkYearMap.put("1年", 1);
        jobReqWorkYearMap.put("2年", 2);
        jobReqWorkYearMap.put("3年", 3);
        jobReqWorkYearMap.put("4年", 4);
        jobReqWorkYearMap.put("5年", 5);
        jobReqWorkYearMap.put("6年", 6);
        jobReqWorkYearMap.put("10年", 10);
        jobReqWorkYearMap.put("15年", 11);

        /*职位学历要求*/
        jobReqDegreeMap.put("小学以上", 0);
        jobReqDegreeMap.put("初中以上", 1);
        jobReqDegreeMap.put("高中以上", 2);
        jobReqDegreeMap.put("中专以上", 3);
        jobReqDegreeMap.put("大专以上", 4);
        jobReqDegreeMap.put("本科以上", 5);
        jobReqDegreeMap.put("硕士以上", 6);
        jobReqDegreeMap.put("博士以上", 8);

        /*职位性别要求*/
        jobReqGenderMap.put("男", 1);
        jobReqGenderMap.put("女", 2);
        jobReqGenderMap.put("不限", 3);


        /*公司注册资金*/
        comRegisterFundMap.put("100万以下", 1);
        comRegisterFundMap.put("100万－500万", 2);
        comRegisterFundMap.put("500万－1000万", 3);
        comRegisterFundMap.put("1000万－5000万", 4);
        comRegisterFundMap.put("5000万以上", 5);

        /*公司员工人数 注意：领航这里的"―"是不是键盘上的"-",坑*/
        comEmployeeNumberMap.put("50―100人", 1);
        comEmployeeNumberMap.put("100―200人", 2);
        comEmployeeNumberMap.put("200―500人", 2);
        comEmployeeNumberMap.put("500―1000人", 4);
        comEmployeeNumberMap.put("1000人以上", 5);

           /*公司性质*/
        comPropertyMap.put("民营企业", 5);
        comPropertyMap.put("私营企业", 5);
        comPropertyMap.put("三资企业", 2);
        comPropertyMap.put("股份制企业", 6);
        comPropertyMap.put("行政机关", 11);
        comPropertyMap.put("社会团体", 10);
        comPropertyMap.put("事业单位", 9);
        comPropertyMap.put("国有企业", 8);
        comPropertyMap.put("集体企业", 20);
        comPropertyMap.put("集体事业", 20);
        comPropertyMap.put("乡镇企业", 20);
        comPropertyMap.put("跨国集团", 7);
        comPropertyMap.put("外资企业", 1);
        comPropertyMap.put("其他", 20);

        /*公司所在行业*/
        comIndustryMap.put("综合性工商/实业公司",37);//10001
        comIndustryMap.put("电子技术/通讯/半导体/集成电路",4);//10002
        comIndustryMap.put("快速消费品（食品/粮油/烟草/日用品/化妆品)",6);//10003
        comIndustryMap.put("公关/市场/营销/广告/会展",15);//10004
        comIndustryMap.put("生物工程",19);//10005
        comIndustryMap.put("广告/企业策划/管理咨询",31);//10006
        comIndustryMap.put("教育/科研/学术/培训",30);//10007
        comIndustryMap.put("法律/社会服务",26);//10008
        comIndustryMap.put("传播/新闻/媒体/出版/影视/文化/艺术",28);//10009
        comIndustryMap.put("金融/保险/投资/银行/基金/证券",8);//10010
        comIndustryMap.put("旅遊/餐饮/健身/酒店/娱乐/体育/休闲/度假",20);//10011
        comIndustryMap.put("商业/贸易/进出口",10);//10012
        comIndustryMap.put("医疗卫生/药品/保健品",32);//10013
        comIndustryMap.put("计算机/网络/电子商务/互联网/计算机硬件及网络设备",1);//10014
        comIndustryMap.put("工程/制造/机械",13);//10015
        comIndustryMap.put("建筑/房地产/物业管理/安装工程/商业中心",12);//10016
        comIndustryMap.put("石油/化工/原材料/石化",24);//10017
        comIndustryMap.put("服装/纺织/制帽/皮具/羽绒",7);//10018
        comIndustryMap.put("农业/渔业/林业/牧业",35);//10019
        comIndustryMap.put("国家机关/公共事业",34);//10020
        comIndustryMap.put("城市规划/园林工程/环保",37);//10021
        comIndustryMap.put("协会/学会",36);//10022
        comIndustryMap.put("交通运输/物流/仓储",14);//10023
        comIndustryMap.put("造纸/印刷/包装/文体用品",22);//10024
        comIndustryMap.put("生产/制造业",11);//10025
        comIndustryMap.put("矿山/冶金/地质/能源/矿产/采掘/冶炼",23);//10026
        comIndustryMap.put("五金交电/仪器仪表/工业自动化",18);//10027
        comIndustryMap.put("耐用消费品（家具/家电/工艺品/玩具/服装服饰/纺织/皮革)",9);//10028
        comIndustryMap.put("批发零售（百货/超市/购物中心/专卖店）",16);//10029
        comIndustryMap.put("中介服务",33);//10030
        comIndustryMap.put("检验/检测/认证",37);//10031
        comIndustryMap.put("咨询/管理产业/财会",31);//10032
        comIndustryMap.put("家居/室内设计/装饰装潢",12);//10033
        comIndustryMap.put("IT服务（系统/数据/维护/多领域经营)",2);//10034
        comIndustryMap.put("办公用品及设备",25);//10035
        comIndustryMap.put("礼品/工艺美术/收藏品",37);//10036
        comIndustryMap.put("大型设备/机电设备/重工业",13);//10037
        comIndustryMap.put("加工制造（原料加工/模具）",11);//10038
        comIndustryMap.put("汽车/摩托车（制造/维护/配件/销售/服务）",37);//10039
        comIndustryMap.put("医疗/护理/美容/美发",32);//10040
        comIndustryMap.put("医疗设备/器械",37);//10041
        comIndustryMap.put("电气/电力/水利",23);//10042
        comIndustryMap.put("航空/航天",37);//10043
        comIndustryMap.put("政府/公共事业/非盈利机构",34);//10044
        comIndustryMap.put("跨领域经营",37);//10045
        comIndustryMap.put("玩具/塑胶",21);//10047
        comIndustryMap.put("其他",37);//10046
        comIndustryMap.put("通信/电信运营、增值服务",5);//10048
    }

    public static String JobTypeParamName = "jtype1Hidden";
    public static String WorkLoactionParamName = "jcity1Hidden";

    public static Map<String, Integer> getWorkLocationMap() {
        return areaMap;
    }

    public static Map<String, Integer> getJobTypeMap() {
        return jobTypeMap;
    }

    public static Integer getComPropertyCode(String property) {
        return comPropertyMap.get(property);
    }

    public static Integer getComRegisterFundCode(String registerFund) {
        return comRegisterFundMap.get(registerFund);
    }

    public static Integer getComEmployeeNumberCode(String employeeNumber) {
        return comEmployeeNumberMap.get(employeeNumber);
    }


    public static Integer getComIndustryCode(String industry) {
        return comIndustryMap.get(industry);
    }

    /**
     * 尝试从industry数组中找出一个能正常解析的，并返回解析结果
     *
     * @param industries
     * @return
     */
    public static Integer getComIndustryCode(String[] industries) {
        Integer rs = null;
        {
            for (String industry : industries) {
                rs = comIndustryMap.get(industry);
                if (rs != null) break;
            }
        }
        return rs;
    }

    public static Integer getJobSalaryCode(String salary) {
        Integer value = jobSalaryMap.get(salary);
        return (value == null) ? 0 : value;
    }

    public static Integer getJobProperty(String property) {
        return jobPropertyMap.get(property);
    }

    public static Integer getJobReqAge(String age) {
        Integer realAge = 0;
        try {
            realAge = Integer.valueOf(age);
        } catch (Exception e) {
            logger.debug("#" + age + "#");
        }
        return realAge;
    }

    public static Integer getJobRecruitmentNumber(String jobRecruitmentNumber) {
        Integer value = 0;
        try {
            value = Integer.valueOf(jobRecruitmentNumber);
        } catch (Exception e) {
            logger.debug("#" + jobRecruitmentNumber + "#");
        }
        return value;
    }

    public static Integer getAreaCode(String workLocation) {
        Integer rs = areaMap.get(workLocation);
        return (rs == null) ? 0 : rs;
    }

    public static Integer getJobTypeCode(String jobType) {
        Integer rs = jobTypeMap.get(jobType);
        return (rs == null) ? 9999 : rs;
    }

    public static Integer getJobReqDegreeCode(String reqDegree) {
        return jobReqDegreeMap.get(reqDegree);
    }

    public static Integer getJobReqGenderCode(String reqGender) {
        Integer rs = jobReqGenderMap.get(reqGender);
        return (rs == null) ? 0 : rs;
    }

    public static Integer getJobReqWorkYearCode(String reqWorkYear) {
        Integer rs = jobReqWorkYearMap.get(reqWorkYear);
        return (rs == null) ? 0 : rs;
    }
}