package com.lwb.common.vo;

/**
 * @autor: Lu Weibiao
 * Date: 2015/4/7 15:37
 */

public class AddressVo {

    private String prov;// 省
    private String city;// 市
    private String dist;// 区
    private String addr;// 地址


    public String getProv() {
        return prov;
    }

    public void setProv(String prov) {
        this.prov = prov;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDist() {
        return dist;
    }

    public void setDist(String dist) {
        this.dist = dist;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    @Override
    public String toString() {
        return (prov == null ? "" : prov) + (city == null ? "" : city) +
                (dist == null ? "" : dist) + (addr == null ? "" : addr);
    }
}

