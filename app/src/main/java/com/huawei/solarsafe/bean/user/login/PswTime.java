package com.huawei.solarsafe.bean.user.login;

import com.huawei.solarsafe.bean.BaseEntity;
import com.huawei.solarsafe.bean.ServerRet;

import org.json.JSONObject;

/**
 * Created by jWX531163 on 2018/2/27.
 */

public class PswTime extends BaseEntity {

    private String needReset;

    private String reason;

    //统一定义的返回码
    ServerRet mRetCode;

    @Override
    public boolean parseJson(JSONObject jsonObject) throws Exception {
        needReset =  jsonObject.optString("needReset");
        if ("now".equals(needReset)){
            reason =jsonObject.optString("reason");
        }
        return true;
    }

    @Override
    public void setServerRet(ServerRet serverRet) {
        mRetCode = serverRet;
    }


    public String getNeedReset() {
        return needReset;
    }

    public String getReason() {
        return reason;
    }

    @Override
    public String toString() {
        return "PswTime{" +
                "reason='" + reason + '\'' +
                ", needReset='" + needReset + '\'' +
                '}';
    }
}
