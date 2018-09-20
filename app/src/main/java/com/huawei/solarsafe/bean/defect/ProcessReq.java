package com.huawei.solarsafe.bean.defect;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by P00319 on 2017/2/20.
 */
//操作缺陷单 网络请求参数 实体类
public class ProcessReq {

    //缺陷单信息 参数 实体类
    public static class Info implements Serializable {

        private static final long serialVersionUID = -8588321135616295329L;
        private String defectId;
        private String alarmType;
        private String deviceType;
        private String deviceId;
        private String sId;
        private String defectGrade;
        private String sName;
        private String deviceName;
        private String deviceTypeName;
        private String deviceVersion;
        private String defectCode;
        private String procState;//消缺状态,新建的消缺单为""
        private String startTime;
        private String endTime;
        private String ownerId;//当前负责人(处理人)id
        private String dealResult;//消缺处理中状态处理结果状态码
        private String fileId;
        private String defectDesc;
        private String ownerName;
        private List<String> alarmIds;
        private boolean isOper;//是否是操作人行为
        private String preTaskOpDesc;//取消描述

        public String getPreTaskOpDesc() {
            return preTaskOpDesc;
        }

        public void setPreTaskOpDesc(String preTaskOpDesc) {
            this.preTaskOpDesc = preTaskOpDesc;
        }

        public void setOper(boolean oper) {
            isOper = oper;
        }

        public void setDefectId(String defectId) {
            this.defectId = defectId;
        }

        public String getAlarmType() {
            return alarmType;
        }

        public void setAlarmType(String alarmType) {
            this.alarmType = alarmType;
        }

        public String getDeviceType() {
            return deviceType;
        }

        public void setDeviceType(String deviceType) {
            this.deviceType = deviceType;
        }

        public String getDeviceId() {
            return deviceId;
        }

        public void setDeviceId(String deviceId) {
            this.deviceId = deviceId;
        }

        public String getSId() {
            return sId;
        }

        public void setSId(String sId) {
            this.sId = sId;
        }

        public void setDefectGrade(String defectGrade) {
            this.defectGrade = defectGrade;
        }

        public String getSName() {
            return sName;
        }

        public void setSName(String sName) {
            this.sName = sName;
        }

        public String getDeviceName() {
            return deviceName;
        }

        public void setDeviceName(String deviceName) {
            this.deviceName = deviceName;
        }

        public void setDeviceTypeName(String deviceTypeName) {
            this.deviceTypeName = deviceTypeName;
        }

        public void setDeviceVersion(String deviceVersion) {
            this.deviceVersion = deviceVersion;
        }

        public void setDefectCode(String defectCode) {
            this.defectCode = defectCode;
        }

        public String getProcState() {
            return procState;
        }

        public void setProcState(String procState) {
            this.procState = procState;
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

        public void setOwnerId(String ownerId) {
            this.ownerId = ownerId;
        }

        public void setDealResult(String dealResult) {
            this.dealResult = dealResult;
        }

        public String getFileId() {
            return fileId;
        }

        public void setFileId(String fileId) {
            this.fileId = fileId;
        }

        public void setDefectDesc(String defectDesc) {
            this.defectDesc = defectDesc;
        }

        public void setAlarmIds(List<String> alarmIds) {
            this.alarmIds = alarmIds;
        }

        public void setOwnerName(String ownerName) {
            this.ownerName = ownerName;
        }

    }

    //操作信息 参数 实体类
    public static class Process implements Serializable {


        private static final long serialVersionUID = 2408198656040683335L;
        private String procId;
        private String operation;//操作类型
        private String recipient;//接收负责人id
        private String operationDesc;//流转意见
        private String isPass;
        private String currentTaskId;
        private ArrayList<String> operators;//选择的操作人id集合

        public void setOperators(ArrayList<String> operators) {
            this.operators = operators;
        }

        public String getCurrentTaskId() {
            return currentTaskId;
        }

        public void setCurrentTaskId(String currentTaskId) {
            this.currentTaskId = currentTaskId;
        }

        public String getProcId() {
            return procId;
        }

        public void setProcId(String procId) {
            this.procId = procId;
        }

        public String getOperation() {
            return operation;
        }

        public void setOperation(String operation) {
            this.operation = operation;
        }

        public void setRecipient(String recipient) {
            this.recipient = recipient;
        }

        public void setOperationDesc(String operationDesc) {
            this.operationDesc = operationDesc;
        }

        public void setIsPass(String isPass) {
            this.isPass = isPass;
        }
    }

}
