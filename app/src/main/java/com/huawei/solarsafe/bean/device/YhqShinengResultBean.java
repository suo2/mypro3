package com.huawei.solarsafe.bean.device;

/**
 * Created by P00229 on 2017/8/16.
 */

public class YhqShinengResultBean {


    private DataBean data;
    private int failCode;
    private int message;
    private int params;
    private boolean success;

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

    public int getMessage() {
        return message;
    }

    public void setMessage(int message) {
        this.message = message;
    }

    public int getParams() {
        return params;
    }

    public void setParams(int params) {
        this.params = params;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public static class DataBean {

        private String enableResult;

        public String getEnableResult() {
            return enableResult;
        }

    }
}
