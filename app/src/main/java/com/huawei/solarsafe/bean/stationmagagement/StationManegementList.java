package com.huawei.solarsafe.bean.stationmagagement;

import com.huawei.solarsafe.bean.station.ChangeStationInfo;

import java.util.List;

/**
 * Created by p00229 on 2017/5/18.
 */

public class StationManegementList {

    private boolean success;
    private DataBean data;
    private int failCode;
    private Object params;
    private Object message;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public int getFailCode() {
        return failCode;
    }

    public void setFailCode(int failCode) {
        this.failCode = failCode;
    }

    public Object getParams() {
        return params;
    }

    public void setParams(Object params) {
        this.params = params;
    }

    public Object getMessage() {
        return message;
    }

    public void setMessage(Object message) {
        this.message = message;
    }

    public static class DataBean {
        private int total;
        private int pageNo;
        private int pageSize;
        private int pageCount;

        private List<ChangeStationInfo> list;

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public int getPageNo() {
            return pageNo;
        }

        public void setPageNo(int pageNo) {
            this.pageNo = pageNo;
        }

        public int getPageSize() {
            return pageSize;
        }

        public void setPageSize(int pageSize) {
            this.pageSize = pageSize;
        }

        public int getPageCount() {
            return pageCount;
        }

        public void setPageCount(int pageCount) {
            this.pageCount = pageCount;
        }

        public List<ChangeStationInfo> getList() {
            return list;
        }

        public void setList(List<ChangeStationInfo> list) {
            this.list = list;
        }
    }
}
