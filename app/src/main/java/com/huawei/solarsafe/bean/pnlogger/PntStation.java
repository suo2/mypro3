package com.huawei.solarsafe.bean.pnlogger;

import java.io.Serializable;

/**
 * Create Date: 2017/3/7
 * Create Author: P00028
 * Description :
 */
public class PntStation implements Serializable {
    private static final long serialVersionUID = 6872820334333599334L;
    //创建时间
    private long createDate;
    //创建人
    private String createUser;
    /**
     * 更新时间
     */
    private long updateDate;
    /**
     * 更新人
     */
    private String updateUser;
    /**
     * 电站编号
     */
    private String stationCode;
    /**
     * id
     */
    private long id;
    /**
     * 电站名称
     */
    private String stationName;
    /**
     * 装机容量
     */
    private double capacity;
    /**
     * 经度
     */
    private double longitude;
    /**
     * 纬度
     */
    private double latitude;
    /**
     * 地址
     */
    private String stationAddr;
    private int aidType;
    /**
     * 域id
     */
    private long domainId;
    private int timeZone;
    private int dbShardingId;
    private String tableShardingId;

    public long getCreateDate() {
        return createDate;
    }

    public void setCreateDate(long createDate) {
        this.createDate = createDate;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public long getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(long updateDate) {
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

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    public double getCapacity() {
        return capacity;
    }

    public void setCapacity(double capacity) {
        this.capacity = capacity;
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



    public String getStationAddr() {
        return stationAddr;
    }

    public void setStationAddr(String stationAddr) {
        this.stationAddr = stationAddr;
    }

    public int getAidType() {
        return aidType;
    }

    public void setAidType(int aidType) {
        this.aidType = aidType;
    }


    public long getDomainId() {
        return domainId;
    }

    public void setDomainId(long domainId) {
        this.domainId = domainId;
    }



    public int getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(int timeZone) {
        this.timeZone = timeZone;
    }

    public int getDbShardingId() {
        return dbShardingId;
    }

    public void setDbShardingId(int dbShardingId) {
        this.dbShardingId = dbShardingId;
    }

    public String getTableShardingId() {
        return tableShardingId;
    }

    public void setTableShardingId(String tableShardingId) {
        this.tableShardingId = tableShardingId;
    }

    @Override
    public String toString() {
        return "PntStation{" +
                "createDate=" + createDate +
                ", createUser='" + createUser + '\'' +
                ", updateDate=" + updateDate +
                ", updateUser='" + updateUser + '\'' +
                ", stationCode='" + stationCode + '\'' +
                ", id=" + id +
                ", stationName='" + stationName + '\'' +
                ", capacity=" + capacity +
                ", longitude=" + longitude +
                ", latitude=" + latitude +
                ", stationAddr='" + stationAddr + '\'' +
                ", aidType=" + aidType +
                ", domainId=" + domainId +
                ", timeZone=" + timeZone +
                ", dbShardingId=" + dbShardingId +
                ", tableShardingId='" + tableShardingId + '\'' +
                '}';
    }
}