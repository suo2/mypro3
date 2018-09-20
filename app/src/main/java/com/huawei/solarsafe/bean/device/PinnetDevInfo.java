package com.huawei.solarsafe.bean.device;

import java.io.Serializable;

/**
 * Created by P00604 on 2017/11/17.
 */

public class PinnetDevInfo implements Serializable {
    private static final long serialVersionUID = -2951370468898874411L;
    private String id;
    private String parentId;
    private String stationCode;
    private String devTypeId;
    private String busiName;
    private String busiCode;
    private String esnCode;
    private String modelVersionCode;
    private String longitude;
    private String latitude;
    private String interval;
    private String createDate;
    private String updateDate;
    private String createUser;
    private String updateUser;
    private String dbShardingId;
    private String tableShardingId;
    private String beginDate;
    private String endDate;
    private String domainId;
    private String twoLevelDomain;
    private String baudrate;
    private String endian;
    private boolean isOperation;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getStationCode() {
        return stationCode;
    }

    public void setStationCode(String stationCode) {
        this.stationCode = stationCode;
    }


    public String getDevTypeId() {
        return devTypeId;
    }

    public void setDevTypeId(String devTypeId) {
        this.devTypeId = devTypeId;
    }

    public String getBusiName() {
        return busiName;
    }

    public void setBusiName(String busiName) {
        this.busiName = busiName;
    }

    public String getBusiCode() {
        return busiCode;
    }

    public void setBusiCode(String busiCode) {
        this.busiCode = busiCode;
    }


    public String getEsnCode() {
        return esnCode;
    }

    public void setEsnCode(String esnCode) {
        this.esnCode = esnCode;
    }



    public String getModelVersionCode() {
        return modelVersionCode;
    }

    public void setModelVersionCode(String modelVersionCode) {
        this.modelVersionCode = modelVersionCode;
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



    public String getInterval() {
        return interval;
    }

    public void setInterval(String interval) {
        this.interval = interval;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
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



    public void setBeginDate(String beginDate) {
        this.beginDate = beginDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getDomainId() {
        return domainId;
    }

    public void setDomainId(String domainId) {
        this.domainId = domainId;
    }

    public String getTwoLevelDomain() {
        return twoLevelDomain;
    }

    public void setTwoLevelDomain(String twoLevelDomain) {
        this.twoLevelDomain = twoLevelDomain;
    }

    public String getBaudrate() {
        return baudrate;
    }

    public void setBaudrate(String baudrate) {
        this.baudrate = baudrate;
    }

    public String getEndian() {
        return endian;
    }

    public void setEndian(String endian) {
        this.endian = endian;
    }

    public boolean isOperation() {
        return isOperation;
    }

    public void setOperation(boolean operation) {
        isOperation = operation;
    }

}
