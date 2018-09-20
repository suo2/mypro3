package com.huawei.solarsafe.bean.device;

import com.google.gson.Gson;
import com.huawei.solarsafe.bean.BaseEntity;
import com.huawei.solarsafe.bean.ServerRet;

import org.json.JSONObject;

/**
 * Created by p00322 on 2017/4/13.
 */

public class DevAlarmBean extends BaseEntity {

    private ServerRet mServerRet;
    private DevAlarmInfo devAlarmInfo;

    @Override
    public boolean parseJson(JSONObject jsonObject) throws Exception {
        if (jsonObject == null) {
            return false;
        }
        Gson gson = new Gson();
        devAlarmInfo = gson.fromJson(jsonObject.toString(), DevAlarmInfo.class);
        return true;
    }
    // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0001_F03_Android_S01  删除Android的demo账号    【修改人】：zhaoyufeng

    @Override
    public void setServerRet(ServerRet serverRet) {
        mServerRet = serverRet;
    }

    public ServerRet getServerRet() {
        return mServerRet;
    }

    public DevAlarmInfo getDevAlarmInfo() {
        return devAlarmInfo;
    }

    public void setDevAlarmInfo(DevAlarmInfo devAlarmInfo) {
        this.devAlarmInfo = devAlarmInfo;
    }
}
