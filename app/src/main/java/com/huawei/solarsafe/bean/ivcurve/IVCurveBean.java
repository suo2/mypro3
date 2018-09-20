package com.huawei.solarsafe.bean.ivcurve;

import java.util.List;

/**
 * Created by P00517 on 2017/7/19.
 */

public class IVCurveBean {
    private boolean success;
    private List<IVCurveInfo> list;
    private int total;
    private int failCode;
    private int pageNo;
    private int pageCount;
    private int pageSize;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public List<IVCurveInfo> getList() {
        return list;
    }

    public void setList(List<IVCurveInfo> list) {
        this.list = list;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getFailCode() {
        return failCode;
    }

    public void setFailCode(int failCode) {
        this.failCode = failCode;
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}
