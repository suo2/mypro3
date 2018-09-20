package com.huawei.solarsafe.bean.update;

/**
 * Created by P00517 on 2017/4/10.
 */

public class UpdateDetailVersionInfo {
    //升级包版本id
    String devUpVersionID;
    //设备类型id
    int devTypeId;
    //升级包版本号
    String versionNum;
    //升级包适用版本号
    String suitVersion;
    //升级包文件ID
    String fileId;
    //升级包状态ID
    int statusId;
    //上传日期
    long uploadDate;
    //上传人ID  userId
    long uploadUserId;
    //上传人姓名
    String uploadUserName;
    //升级包详情描述
    String devPackDesciption;
    //所在域ID
    long domainId;

    String sId;
    String dbShardingId;
    String tableShardingId;

    public String getsId() {
        return sId;
    }

    public void setsId(String sId) {
        this.sId = sId;
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

    public void setDevUpVersionID(String devUpVersionID) {
        this.devUpVersionID = devUpVersionID;
    }

    public int getDevTypeId() {
        return devTypeId;
    }

    public void setDevTypeId(int devTypeId) {
        this.devTypeId = devTypeId;
    }

    public String getVersionNum() {
        return versionNum;
    }

    public void setVersionNum(String versionNum) {
        this.versionNum = versionNum;
    }

    public String getSuitVersion() {
        return suitVersion;
    }

    public void setSuitVersion(String suitVersion) {
        this.suitVersion = suitVersion;
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public int getStatusId() {
        return statusId;
    }

    public void setStatusId(int statusId) {
        this.statusId = statusId;
    }


    public void setUploadDate(long uploadDate) {
        this.uploadDate = uploadDate;
    }


    public void setUploadUserId(long uploadUserId) {
        this.uploadUserId = uploadUserId;
    }

    public void setUploadUserName(String uploadUserName) {
        this.uploadUserName = uploadUserName;
    }

    public String getDevPackDesciption() {
        return devPackDesciption;
    }

    public void setDevPackDesciption(String devPackDesciption) {
        this.devPackDesciption = devPackDesciption;
    }

    public long getDomainId() {
        return domainId;
    }

    public void setDomainId(long domainId) {
        this.domainId = domainId;
    }
}
