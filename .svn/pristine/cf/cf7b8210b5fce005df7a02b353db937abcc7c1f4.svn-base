package com.huawei.solarsafe.bean.update;

import com.huawei.solarsafe.bean.BaseEntity;
import com.huawei.solarsafe.bean.ServerRet;

import org.json.JSONObject;

import java.util.Map;

/**
 * Created by P00517 on 2017/4/10.
 */

public class UpDateDetailBean extends BaseEntity {
    //数据实体
    Map<UpdateDetailInfo, UpdateDetailVersionInfo> data;
    //消息体
    String message;
    //统一定义的返回码
    ServerRet mRetCode;
    String params;


    @Override
    public boolean parseJson(JSONObject jsonObject) throws Exception {
        return  false;
    }
    // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0001_F03_Android_S01  删除Android的demo账号    【修改人】：zhaoyufeng

    @Override
    public void setServerRet(ServerRet serverRet) {
        this.mRetCode = serverRet;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ServerRet getmRetCode() {
        return mRetCode;
    }

    public void setmRetCode(ServerRet mRetCode) {
        this.mRetCode = mRetCode;
    }

    public Map<UpdateDetailInfo, UpdateDetailVersionInfo> getData() {
        return data;
    }

    public void setData(Map<UpdateDetailInfo, UpdateDetailVersionInfo> data) {
        this.data = data;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }
}
