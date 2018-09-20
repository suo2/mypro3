package com.huawei.solarsafe.bean.defect;

/**
 * Created by p00319 on 2017/2/22.
 */

public class WorkerReq {

    private int page;
    private int pageSize;
    private String stationCode;
    private boolean isCurrent;
    private String isPass;
    private String procKey;
    private String taskKey;
    private String sortname = "orderNo";//按什么进行排序
    private String sortorder = "asc";//排序方式

    public String getStationCode() {
        return stationCode;
    }

    public void setStationCode(String stationCode) {
        this.stationCode = stationCode;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public boolean isCurrent() {
        return isCurrent;
    }

    public void setCurrent(boolean current) {
        isCurrent = current;
    }

    public String isPass() {
        return isPass;
    }

    public void setPass(String pass) {
        isPass = pass;
    }

    public String getProcKey() {
        return procKey;
    }

    public void setProcKey(String procKey) {
        this.procKey = procKey;
    }

    public String getTaskKey() {
        return taskKey;
    }

    public void setTaskKey(String taskKey) {
        this.taskKey = taskKey;
    }

    public String getSortname() {
        return sortname;
    }

    public void setSortname(String sortname) {
        this.sortname = sortname;
    }

    public String getSortorder() {
        return sortorder;
    }

    public void setSortorder(String sortorder) {
        this.sortorder = sortorder;
    }

}
