package com.huawei.solarsafe.bean.station;

import com.huawei.solarsafe.bean.BaseEntity;
import com.huawei.solarsafe.bean.ServerRet;
import com.huawei.solarsafe.utils.JSONReader;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * 4.15电站对比实时功率
 * Created by P00229 on 2017/2/21.
 */
public class StationCompareCurPowerInfo extends BaseEntity {
    public static final String TAG = "StationCompareCurPowerInfo";
    public static final String KEY_LIST = "list";
    public static final String KEY_STATIONCODE = "stationCode";
    public static final String KEY_CURPOWER = "curPower";
    private ServerRet serverRet;

    public ServerRet getServerRet() {
        return serverRet;
    }

    @Override
    public boolean parseJson(JSONObject jsonObject) throws Exception {
        if (jsonObject == null) {
            return false;
        }
        JSONReader jsonReader = new JSONReader(jsonObject);
        JSONArray jsonArray = jsonReader.getJSONArray(KEY_LIST);
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject1 = jsonArray.getJSONObject(i);
            JSONReader jsonReader1 = new JSONReader(jsonObject1);
            CurPowerInfo curPowerInfo = new CurPowerInfo();
            curPowerInfo.setStationCode(jsonReader1.getString(KEY_STATIONCODE));
            curPowerInfo.setCurPower(jsonReader1.getDouble(KEY_CURPOWER));
        }
        return true;
    }
    // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0001_F03_Android_S01  删除Android的demo账号    【修改人】：zhaoyufeng

    @Override
    public void setServerRet(ServerRet serverRet) {
        this.serverRet = serverRet;
    }

    class CurPowerInfo {
        String stationCode;
        double curPower;

        public String getStationCode() {
            return stationCode;
        }

        public void setStationCode(String stationCode) {
            this.stationCode = stationCode;
        }

        public void setCurPower(double curPower) {
            this.curPower = curPower;
        }
    }
}
