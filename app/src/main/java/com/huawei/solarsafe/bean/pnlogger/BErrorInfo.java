package com.huawei.solarsafe.bean.pnlogger;

/**
 * Created by P00517 on 2017/6/8.
 */

public class BErrorInfo {
    private boolean success;
    private int failCode;
    private String tag;
    public final String TAG_SET_PNLOGGER_INFO = "setPnloggerInfo";

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public int getFailCode() {
        return failCode;
    }

    public void setFailCode(int failCode) {
        this.failCode = failCode;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    @Override
    public String toString() {
        return "BErrorInfo{" +
                "success=" + success +
                ", failCode=" + failCode +
                ", tag='" + tag + '\'' +
                '}';
    }
}
