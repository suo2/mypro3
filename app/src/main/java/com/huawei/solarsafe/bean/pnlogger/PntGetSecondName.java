package com.huawei.solarsafe.bean.pnlogger;

import java.util.List;

/**
 * Create Date: 2017/6/19
 * Create Author: P00171
 * Description :
 */
public class PntGetSecondName {
    //设备ESN
    private String devEsn;
    //设备名称
    private String devName;
    //设备id
    private String devId;
    //配置新组串容量添加
    //电站id（可能为null）
    private String stationCode;
    //budiCode
    private String busiCode;
    //设备类型
    private String devTypeId;
    //域ID（可能为null）
    private String domainId;
    //经度
    private String longitude;
    //纬度
    private String latitude;
    //机型编号（可能为null）
    private String invType;
    //二级域ID（可能为null）
    private String twoLevelDomain;
    //子设备list
    private List<SecondName> secondDeviceList;
    private int deviceMaxConnectNum  = -1;  //B/C版下联设备最大连接数，默认为10   A版默认40

    public String getDevEsn() {
        return devEsn;
    }

    public void setDevEsn(String devEsn) {
        this.devEsn = devEsn;
    }

    public String getDevName() {
        return devName;
    }

    public void setDevName(String devName) {
        this.devName = devName;
    }

    public String getDevId() {
        return devId;
    }

    public void setDevId(String devId) {
        this.devId = devId;
    }

    public String getStationCode() {
        return stationCode;
    }

    public void setStationCode(String stationCode) {
        this.stationCode = stationCode;
    }

    public String getBusiCode() {
        return busiCode;
    }

    public void setBusiCode(String busiCode) {
        this.busiCode = busiCode;
    }

    public String getDevTypeId() {
        return devTypeId;
    }

    public void setDevTypeId(String devTypeId) {
        this.devTypeId = devTypeId;
    }

    public String getDomainId() {
        return domainId;
    }

    public void setDomainId(String domainId) {
        this.domainId = domainId;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getInvType() {
        return invType;
    }

    public void setInvType(String invType) {
        this.invType = invType;
    }

    public String getTwoLevelDomain() {
        return twoLevelDomain;
    }

    public void setTwoLevelDomain(String twoLevelDomain) {
        this.twoLevelDomain = twoLevelDomain;
    }

    public List<SecondName> getSecondDeviceList() {
        return secondDeviceList;
    }


    @Override
    public String toString() {
        return "PntGetSecondName{" +
                "devEsn='" + devEsn + '\'' +
                ", devName='" + devName + '\'' +
                ", devId='" + devId + '\'' +
                ", stationCode='" + stationCode + '\'' +
                ", busiCode='" + busiCode + '\'' +
                ", devTypeId='" + devTypeId + '\'' +
                ", domainId='" + domainId + '\'' +
                ", longitude='" + longitude + '\'' +
                ", latitude='" + latitude + '\'' +
                ", invType='" + invType + '\'' +
                ", twoLevelDomain='" + twoLevelDomain + '\'' +
                ", secondDeviceList=" + secondDeviceList +
                '}';
    }

    public int getDeviceMaxConnectNum() {
        return deviceMaxConnectNum;
    }

}
