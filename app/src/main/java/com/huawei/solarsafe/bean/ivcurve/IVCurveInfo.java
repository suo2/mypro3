package com.huawei.solarsafe.bean.ivcurve;

/**
 * Created by P00517 on 2017/7/19.
 */

public class IVCurveInfo {
    //任务id
    private String taskId;
    //任务名称
    private String taskName;
    //开始时间
    private String startTime;
    //结束时间
    private String endTime;
    //故障单元数
    private String faultCount;
    //单元总数
    private String unitCount;
    //设备数量
    private String devCount;
    //任务进度
    private String process;
    //清洗状态
    private String cleanStatus;
    //任务状态
    private String taskStatus;
    //域id
    private String domainId;
    //创建人ID
    private String createUser;
    //修改人ID
    private String updateUser;
    //当前系统时间
    private String currentTime;

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getFaultCount() {
        return faultCount;
    }

    public void setFaultCount(String faultCount) {
        this.faultCount = faultCount;
    }

    public String getUnitCount() {
        return unitCount;
    }

    public void setUnitCount(String unitCount) {
        this.unitCount = unitCount;
    }

    public void setDevCount(String devCount) {
        this.devCount = devCount;
    }

    public String getProcess() {
        return process;
    }

    public void setProcess(String process) {
        this.process = process;
    }

    public void setCleanStatus(String cleanStatus) {
        this.cleanStatus = cleanStatus;
    }

    public String getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(String taskStatus) {
        this.taskStatus = taskStatus;
    }

    public String getDomainId() {
        return domainId;
    }

    public void setDomainId(String domainId) {
        this.domainId = domainId;
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

    public void setCurrentTime(String currentTime) {
        this.currentTime = currentTime;
    }
}
