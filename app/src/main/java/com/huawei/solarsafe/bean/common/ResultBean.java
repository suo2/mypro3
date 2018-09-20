package com.huawei.solarsafe.bean.common;

import com.huawei.solarsafe.bean.BaseEntity;
import com.huawei.solarsafe.bean.ServerRet;

import org.json.JSONObject;

/**
 * <pre>
 *     author: Tzy
 *     time  : $date$
 *     desc  :
 * </pre>
 */
public class ResultBean extends BaseEntity {


    /**
     * success : true
     * data : null
     * params : null
     * message : null
     * buildCode : 2
     */

    private boolean success;
    private Object data;
    private Object params;
    private Object message;
    private int buildCode;

    @Override
    public boolean parseJson(JSONObject jsonObject) throws Exception {
        return true;
    }

    @Override
    public void setServerRet(ServerRet serverRet) {

    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
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

    public int getBuildCode() {
        return buildCode;
    }

    public void setBuildCode(int buildCode) {
        this.buildCode = buildCode;
    }
}
