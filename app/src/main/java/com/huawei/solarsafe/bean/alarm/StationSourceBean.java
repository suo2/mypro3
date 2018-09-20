package com.huawei.solarsafe.bean.alarm;

import com.huawei.solarsafe.bean.BaseEntity;
import com.huawei.solarsafe.bean.ServerRet;

import org.json.JSONObject;

/**
 * Created by p00507
 * on 2017/10/31.
 */

public class StationSourceBean extends BaseEntity {
    private String oprtion;
    private boolean isUserStation = true;

    public String getOprtion() {
        return oprtion;
    }

    public void setOprtion(String oprtion) {
        this.oprtion = oprtion;
    }

    public boolean isUserStation() {
        return isUserStation;
    }

    public void setUserStation(boolean userStation) {
        isUserStation = userStation;
    }

    @Override
    public boolean parseJson(JSONObject jsonObject) throws Exception {
        return false;
    }
    // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0001_F03_Android_S01  删除Android的demo账号    【修改人】：zhaoyufeng

    @Override
    public void setServerRet(ServerRet serverRet) {

    }
}
