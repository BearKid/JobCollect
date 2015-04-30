package com.lwb.common.model;

import javax.persistence.Embeddable;

/**
 * 
 * @ClassName: ContactBase
 * @Description: 联系人基类
 * @author xh
 * @date 2013-7-24 上午8:20:25
 * 
 */
@Embeddable
public class ComContactBase {
    private String  contactPerson;   // 联系人
    private Integer  gender;          // 性别0=未选择，1=先生，2=女士
    private String  contactPhone;    // 联系电话
    private Integer hidePhone;       // 是否公开电话1为不公开，其他为公开
    private String  mobile;          // 手机号
    private Integer mobileAlert;     // 收到简历提醒，1提醒,0=不提醒
    private Integer hideMobile;      // 1为不公开，其他为公开
    private String  fax;             // 传真号
    private Integer hideFax;         // 是否公开传真1为不公开传真，0为公开
    private String  email;           // 电子邮件用json格式存储
    private Integer hideEmail;       // 1为不公开，其他为公开
//    private Integer rejectEmail;       // 1为拒收邮件，其他为收邮件
    private Integer emailFormat;     // 邮件格式0简体 1繁体
    private String  interviewAddress;                        // 面试地址,格式为{"prov":"省","city":"市","dist":"区","addr":"地址"}
    private String  address;         // 通讯地址,格式为{"prov":"省","city":"市","dist":"区","addr":"地址"}
    private String  busLine;         // 乘车路线
    private String  nearStation;     // 附近站点
    private String  zipCode;         // 编邮
    private String  website;         // 企业网址
    // 经纬度
    private Double  lat;             // 纬度
    private Double  lng;             // 经度

    public String getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public Integer getHidePhone() {
        return hidePhone;
    }

    public void setHidePhone(Integer hidePhone) {
        this.hidePhone = hidePhone;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Integer getMobileAlert() {
        return mobileAlert;
    }

    public void setMobileAlert(Integer mobileAlert) {
        this.mobileAlert = mobileAlert;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public Integer getHideFax() {
        return hideFax;
    }

    public void setHideFax(Integer hideFax) {
        this.hideFax = hideFax;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getHideEmail() {
        return hideEmail;
    }

    public void setHideEmail(Integer hideEmail) {
        this.hideEmail = hideEmail;
    }

//    public Integer getRejectEmail() {
//        return rejectEmail;
//    }
//
//    public void setRejectEmail(Integer rejectEmail) {
//        this.rejectEmail = rejectEmail;
//    }

    public Integer getEmailFormat() {
        return emailFormat;
    }

    public void setEmailFormat(Integer emailFormat) {
        this.emailFormat = emailFormat;
    }

    public String getInterviewAddress() {
        return interviewAddress;
    }

    public void setInterviewAddress(String interviewAddress) {
        this.interviewAddress = interviewAddress;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBusLine() {
        return busLine;
    }

    public void setBusLine(String busLine) {
        this.busLine = busLine;
    }

    public String getNearStation() {
        return nearStation;
    }

    public void setNearStation(String nearStation) {
        this.nearStation = nearStation;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

    public Integer getHideMobile() {
        return hideMobile;
    }

    public void setHideMobile(Integer hideMobile) {
        this.hideMobile = hideMobile;
    }
}
