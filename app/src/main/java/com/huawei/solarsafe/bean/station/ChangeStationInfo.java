package com.huawei.solarsafe.bean.station;

import java.io.Serializable;

/**
 * Created by p00229 on 2017/6/6.
 */

public class ChangeStationInfo implements Serializable {
    private static final long serialVersionUID = -3922304633369367787L;
    private long createDate;
    private String createUser;
    private int updateDate;
    private String updateUser;
    private String stationCode;
    private String id;
    private String stationName;
    private double capacity;
    private long devoteDate;//并网时间
    private String combineType;
    private double longitude = Double.MIN_VALUE;
    private double latitude = Double.MIN_VALUE;
    private long safeBeginDate;
    private String stationPic;
    private String stationAddr;
    private int aidType;
    private int domainId;
    private String buildState;
    private int timeZone;
    private String stationBriefing;
    private String stationLinkman;
    private String linkmanPho;
    private String dbShardingId;
    private String tableShardingId;
    private String secDomainId;
    private int useDefaultPrice;
    private String dataFrom;//为3时表示710电站
    /**
     * 下层网元地址(只有710电站才有)
     */
    private String serviceLocation;

    public long getCreateDate() {
        return createDate;
    }

    public void setCreateDate(int createDate) {
        this.createDate = createDate;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public int getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(int updateDate) {
        this.updateDate = updateDate;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    public String getStationCode() {
        return stationCode;
    }

    public void setStationCode(String stationCode) {
        this.stationCode = stationCode;
    }

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getCapacity() {
        return capacity;
    }

    public void setCapacity(double capacity) {
        this.capacity = capacity;
    }

    public long getDevoteDate() {
        return devoteDate;
    }

    public String getCombineType() {
        return combineType;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public long getSafeBeginDate() {
        return safeBeginDate;
    }

    public String getStationPic() {
        return stationPic;
    }

    public String getStationAddr() {
        return stationAddr;
    }


    public int getAidType() {
        return aidType;
    }

    public void setAidType(int aidType) {
        this.aidType = aidType;
    }

    public int getDomainId() {
        return domainId;
    }

    public void setDomainId(int domainId) {
        this.domainId = domainId;
    }

    public String getBuildState() {
        return buildState;
    }

    public int getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(int timeZone) {
        this.timeZone = timeZone;
    }

    public String getStationBriefing() {
        return stationBriefing;
    }

    public String getStationLinkman() {
        return stationLinkman;
    }

    public void setStationLinkman(String stationLinkman) {
        this.stationLinkman = stationLinkman;
    }

    public String getLinkmanPho() {
        return linkmanPho;
    }

    public void setLinkmanPho(String linkmanPho) {
        this.linkmanPho = linkmanPho;
    }

    public String getDbShardingId() {
        return dbShardingId;
    }

    public void setDbShardingId(String dbShardingId) {
        this.dbShardingId = dbShardingId;
    }

    public String getTableShardingId() {
        return tableShardingId;
    }

    public void setTableShardingId(String tableShardingId) {
        this.tableShardingId = tableShardingId;
    }

    public String getSecDomainId() {
        return secDomainId;
    }

    public int getUseDefaultPrice() {
        return useDefaultPrice;
    }

    public void setUseDefaultPrice(int useDefaultPrice) {
        this.useDefaultPrice = useDefaultPrice;
    }

    public String getDataFrom() {
        return dataFrom;
    }

    public String getServiceLocation() {
        return serviceLocation;
    }

}


