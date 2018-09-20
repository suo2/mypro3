package com.huawei.solarsafe.bean.alarm;

import com.huawei.solarsafe.bean.BaseEntity;
import com.huawei.solarsafe.bean.ServerRet;
import com.huawei.solarsafe.net.JSONReader;

import org.json.JSONObject;

/**
 * Created by P00229 on 2017/2/13.
 */
public class CausesAndRepairSuggestions extends BaseEntity {
    public static final String KEY_ALARMAUSE = "alarmCause";
    public static final String KEY_REPAIRSUGGRSTION = "repairSuggestion";
    private String alarmCauses;
    private String repairSuggestions;
    private ServerRet serverRet;

    @Override
    public boolean parseJson(JSONObject jsonObject) throws Exception {
        if (jsonObject == null) {
            return false;
        }
        JSONReader jsonReader = new JSONReader(jsonObject);
        alarmCauses = jsonReader.getString(KEY_ALARMAUSE);
        repairSuggestions = jsonReader.getString(KEY_REPAIRSUGGRSTION);
        return true;
    }
    // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0001_F03_Android_S01  删除Android的demo账号    【修改人】：zhaoyufeng

    @Override
    public void setServerRet(ServerRet serverRet) {
        this.serverRet = serverRet;
    }

    public ServerRet getServerRet() {
        return serverRet;
    }

    public String getAlarmCauses() {
        return alarmCauses;
    }


    public String getRepairSuggestions() {
        return repairSuggestions;
    }

    @Override
    public String toString() {
        return "CausesAndRepairSuggestions{" +
                "alarmCauses='" + alarmCauses + '\'' +
                ", repairSuggestions='" + repairSuggestions + '\'' +
                ", serverRet=" + serverRet +
                '}';
    }
}
