package com.huawei.solarsafe.bean.defect;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by p00319 on 2017/2/16.
 */
//缺陷信息实体类
public class DefectDetail implements Serializable {
    private static final long serialVersionUID = -2061154515104399975L;
    /**
     * 缺陷id
     */
    private long defectId;
    /**
     * 缺陷编码
     */
    private String defectCode;
    /**
     * 关联设备id
     */
    private String deviceId;
    /**
     * 关联设备名称
     */
    private String deviceName;
    /**
     * 缺陷级别
     */
    private String defectGrade;
    /**
     * 缺陷描述
     */
    private String defectDesc;
    /**
     * 流程实例id
     */
    private String procId;
    /**
     * 流程状态
     */
    private String procState;
    /**
     * 处理结果
     */
    private String dealResult;
    /**
     * 设备类型
     */
    private String deviceType;
    /**
     * 设备版本型号
     */
    private String deviceVersion;
    /**
     * 关联告警条数
     */
    private int alarmNum;
    /**
     * 关联告警类型
     */
    private String alarmType;
    /**
     * 电站id
     */
    private String sId;
    /**
     * 电站名称
     */
    private String sName;
    /**
     * 域id
     */
    private int domainId;
    /**
     * 附件id
     */
    private String fileId;
    /**
     * 所属人用户登录名
     */
    private String ownerId;//当前处理人(负责人)id
    /**
     * 所属人姓名
     */
    private String ownerName;
    /**
     * 创建时间
     */
    private long createTime;
    /**
     * 更新时间
     */
    private long updateTime;
    /**
     * 流程开始时间
     */
    private long startTime;
    /**
     * 流程结束时间
     */
    private long endTime;
    /**
     * 关联告警ID列表
     */
    private List<String> alarmIds;
    /**
     * 当前任务id
     */
    private String currentTaskId;
    private int timeZone;

    private boolean isOper;//更新消缺单时是否为操作行为

    public boolean isOper() {
        return isOper;
    }

    public void setOper(boolean oper) {
        isOper = oper;
    }

    private ArrayList<WorkFlowBean.DefectDisposeCituationBean> wfhts;

    public ArrayList<WorkFlowBean.DefectDisposeCituationBean> getWfhts() {
        return wfhts;
    }

    private String preTaskOpDesc;//处理意见/处理结果

    public String getPreTaskOpDesc() {
        return preTaskOpDesc;
    }

    public int getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(int timeZone) {
        this.timeZone = timeZone;
    }

    public String getCurrentTaskId() {
        return currentTaskId;
    }

    public void setCurrentTaskId(String currentTaskId) {
        this.currentTaskId = currentTaskId;
    }

    public long getDefectId() {
        return defectId;
    }

    public String getDefectCode() {
        return defectCode;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getDefectGrade() {
        return defectGrade;
    }

    public String getDefectDesc() {
        return defectDesc;
    }

    public void setDefectDesc(String defectDesc) {
        this.defectDesc = defectDesc;
    }

    public String getProcId() {
        return procId;
    }

    public void setProcId(String procId) {
        this.procId = procId;
    }

    public String getProcState() {
        return procState;
    }

    public void setProcState(String procState) {
        this.procState = procState;
    }

    public String getDealResult() {
        return dealResult;
    }

    public void setDealResult(String dealResult) {
        this.dealResult = dealResult;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public String getDeviceVersion() {
        return deviceVersion;
    }

    public void setDeviceVersion(String deviceVersion) {
        this.deviceVersion = deviceVersion;
    }

    public int getAlarmNum() {
        return alarmNum;
    }

    public void setAlarmNum(int alarmNum) {
        this.alarmNum = alarmNum;
    }

    public String getAlarmType() {
        return alarmType;
    }

    public void setAlarmType(String alarmType) {
        this.alarmType = alarmType;
    }

    public String getSId() {
        return sId;
    }

    public void setSId(String sId) {
        this.sId = sId;
    }

    public String getSName() {
        return sName;
    }

    public void setSName(String sName) {
        this.sName = sName;
    }

    public int getDomainId() {
        return domainId;
    }

    public void setDomainId(int domainId) {
        this.domainId = domainId;
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(long updateTime) {
        this.updateTime = updateTime;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public List<String> getAlarmIds() {
        return alarmIds;
    }

    public void setAlarmIds(List<String> alarmIds) {
        this.alarmIds = alarmIds;
    }
}
