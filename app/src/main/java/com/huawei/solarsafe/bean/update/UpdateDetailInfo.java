package com.huawei.solarsafe.bean.update;

/**
 * Created by P00517 on 2017/4/12.
 */

public class UpdateDetailInfo {
    //主键id
    long keyId;
    //任务id
    long task_id;
    //任务的执行用户id
    long executorId;
    //执行用户姓名
    String executorName;
    //设备id
    long devId;
    //设备名称
    String devName;
    //设备类型id
    int devTypeId;
    //电站id
    String sId;
    //电站名称
    String sName;
    //升级时间
    long upgradeTime;
    //设备升级包id
    String devUpVersionID;
    //当前版本
    String sourceVersion;
    //升级后目标版本
    String targetVersion;
    //进度
    double process;
    //升级结果
    int upgradeResult;

    public int getUpgradeResult() {
        return upgradeResult;
    }

    public void setUpgradeResult(int upgradeResult) {
        this.upgradeResult = upgradeResult;
    }

    public String getsName() {
        return sName;
    }

    public void setsName(String sName) {
        this.sName = sName;
    }

    public long getUpgradeTime() {
        return upgradeTime;
    }

    public void setUpgradeTime(long upgradeTime) {
        this.upgradeTime = upgradeTime;
    }

    public String getDevUpVersionID() {
        return devUpVersionID;
    }

    public void setDevUpVersionID(String devUpVersionID) {
        this.devUpVersionID = devUpVersionID;
    }

    public void setSourceVersion(String sourceVersion) {
        this.sourceVersion = sourceVersion;
    }

    public void setTargetVersion(String targetVersion) {
        this.targetVersion = targetVersion;
    }

    public double getProcess() {
        return process;
    }

    public void setProcess(double process) {
        this.process = process;
    }

    public long getKeyId() {
        return keyId;
    }

    public void setKeyId(long keyId) {
        this.keyId = keyId;
    }

    public void setTask_id(long task_id) {
        this.task_id = task_id;
    }

    public void setExecutorId(long executorId) {
        this.executorId = executorId;
    }

    public String getExecutorName() {
        return executorName;
    }

    public void setExecutorName(String executorName) {
        this.executorName = executorName;
    }

    public long getDevId() {
        return devId;
    }

    public void setDevId(long devId) {
        this.devId = devId;
    }

    public String getDevName() {
        return devName;
    }

    public void setDevName(String devName) {
        this.devName = devName;
    }

    public int getDevTypeId() {
        return devTypeId;
    }

    public void setDevTypeId(int devTypeId) {
        this.devTypeId = devTypeId;
    }

    public String getsId() {
        return sId;
    }

    public void setsId(String sId) {
        this.sId = sId;
    }
}
