package com.huawei.solarsafe.bean.device;

import com.google.gson.Gson;
import com.huawei.solarsafe.bean.BaseEntity;
import com.huawei.solarsafe.bean.ServerRet;
import com.huawei.solarsafe.utils.JSONReader;

import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by p00507
 * on 2017/5/23.
 *  品联数采信息
 */
public class SmartLoggerInfo extends BaseEntity implements Serializable{
    private static final long serialVersionUID = -635377722018759503L;
    private ServerRet mServerRet;
    public ParamsInfo paramsInfo;
    public PinnetDevInfo devInfo;
    public static final String KEY_PARAMS_INFO = "paramsInfo";
    public static final String KEY_DEV_INFO = "devInfo";
    @Override
    public boolean parseJson(JSONObject jsonObject) throws Exception {
        if (jsonObject == null) {
            return false;
        }
        JSONReader jsonReader = new JSONReader(jsonObject);
        JSONObject jsonObject1 = jsonReader.getJSONObject(KEY_PARAMS_INFO);
        JSONObject jsonObject2 = jsonReader.getJSONObject(KEY_DEV_INFO);
        Gson gson = new Gson();
        if(!"null".equals(jsonObject1+"")){
            paramsInfo = gson.fromJson(jsonObject1.toString(),ParamsInfo.class);
        }
        if(!"null".equals(jsonObject2+"")){
            devInfo = gson.fromJson(jsonObject2.toString(),PinnetDevInfo.class);
        }
        return true;
    }
    // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0001_F03_Android_S01  删除Android的demo账号    【修改人】：zhaoyufeng

    @Override
    public void setServerRet(ServerRet serverRet) {
        this.mServerRet = serverRet;
    }


    public ParamsInfo getParamsInfo() {
        return paramsInfo;
    }


    public PinnetDevInfo getDevInfo() {
        return devInfo;
    }

    public void setDevInfo(PinnetDevInfo devInfo) {
        this.devInfo = devInfo;
    }
}
