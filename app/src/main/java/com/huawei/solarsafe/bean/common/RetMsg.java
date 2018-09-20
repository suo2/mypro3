package com.huawei.solarsafe.bean.common;

import java.util.List;

/**
 * Created by p00319 on 2017/2/14.
 */
//网络请求返回数据实体类
public class RetMsg<T> {
    private boolean success;
    private List<String> params;
    private int failCode;
    private String message;
    private T data;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public List<String> getParams() {
        return params;
    }

    public void setParams(List<String> params) {
        this.params = params;
    }

    public int getFailCode() {
        return failCode;
    }

    public void setFailCode(int failCode) {
        this.failCode = failCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "RetMsg{" +
                "success=" + success +
                ", params=" + params +
                ", failCode=" + failCode +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
