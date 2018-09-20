package com.huawei.solarsafe.bean.device;

import com.huawei.solarsafe.bean.BaseEntity;
import com.huawei.solarsafe.bean.ServerRet;

import org.json.JSONObject;

/**
 * Created by P00229 on 2017/4/13.
 */
public class HouseholdRequestResult extends BaseEntity {
    private HouseholdSetResult householdSetResult;
    private String resultString;

    public String getResultString() {
        return resultString;
    }

    public void setResultString(String resultString) {
        this.resultString = resultString;
    }

    public HouseholdSetResult getHouseholdSetResult() {
        return householdSetResult;
    }

    public void setHouseholdSetResult(HouseholdSetResult householdSetResult) {
        this.householdSetResult = householdSetResult;
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
