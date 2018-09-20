package com.huawei.solarsafe.bean.defect;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by p00319 on 2017/2/22.
 */

//缺陷处理流水信息实体类
public class WorkFlowBean {


    private long id;
    private String taskId;
    private String taskKey;//执行流程状态(工单填写/消缺处理/消缺确认//已放弃/已完成)
    private String procId;
    private long taskEndTime;
    private String assignee;//处理负责人id
    private String assigneeName;
    private String recipient;//接收负责人id
    private String recipientName;
    private String operation;//操作类型(退回/提交)
    private String operationDesc;
    private String sId;
    private long domainId;
    private ArrayList<DefectDisposeCituationBean> wfhts;
    private String dealMark;

    public String getDealMark() {
        return dealMark;
    }

    public ArrayList<DefectDisposeCituationBean> getWfhts() {
        return wfhts;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getTaskKey() {
        return taskKey;
    }

    public String getProcId() {
        return procId;
    }

    public void setProcId(String procId) {
        this.procId = procId;
    }


    public long getTaskEndTime() {
        return taskEndTime;
    }

    public String getAssignee() {
        return assignee;
    }

    public String getAssigneeName() {
        return assigneeName;
    }

    public void setAssigneeName(String assigneeName) {
        this.assigneeName = assigneeName;
    }

    public String getRecipient() {
        return recipient;
    }

    public String getRecipientName() {
        return recipientName;
    }

    public void setRecipientName(String recipientName) {
        this.recipientName = recipientName;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public String getOperationDesc() {
        return operationDesc;
    }

    public String getSId() {
        return sId;
    }

    public void setSId(String sId) {
        this.sId = sId;
    }

    public long getDomainId() {
        return domainId;
    }

    public void setDomainId(long domainId) {
        this.domainId = domainId;
    }

    /**
     * <pre>
     *     author: Tzy
     *     time  : $date$
     *     desc  : 缺陷处理流水中每个人处理情况实体类
     * </pre>
     */
    public static class DefectDisposeCituationBean implements Serializable{

        private static final long serialVersionUID = -8570890006740901341L;
        private String id;
        private String taskId;
        private String procId;
        private String operator;
        private String operationMark;
        private String dealMark;
        private String operationDesc;
        private String sId;
        private String domainId;
        private boolean isDeal;
        private long updateTime;
        private String operatorName;

        public String getOperatorName() {
            return operatorName;
        }

        public void setOperatorName(String operatorName) {
            this.operatorName = operatorName;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTaskId() {
            return taskId;
        }

        public void setTaskId(String taskId) {
            this.taskId = taskId;
        }

        public String getProcId() {
            return procId;
        }

        public void setProcId(String procId) {
            this.procId = procId;
        }

        public String getOperator() {
            return operator;
        }

        public String getOperationMark() {
            return operationMark;
        }

        public String getDealMark() {
            return dealMark;
        }

        public String getOperationDesc() {
            return operationDesc;
        }

        public String getSId() {
            return sId;
        }

        public void setSId(String sId) {
            this.sId = sId;
        }

        public String getDomainId() {
            return domainId;
        }

        public void setDomainId(String domainId) {
            this.domainId = domainId;
        }

        public boolean isIsDeal() {
            return isDeal;
        }

        public long getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(long updateTime) {
            this.updateTime = updateTime;
        }
    }
}
