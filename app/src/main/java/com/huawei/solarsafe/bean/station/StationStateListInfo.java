package com.huawei.solarsafe.bean.station;

import com.huawei.solarsafe.bean.BaseEntity;
import com.huawei.solarsafe.bean.ServerRet;
import com.huawei.solarsafe.utils.JSONReader;

import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

/**
 * 4.14电站状态统计
 * Created by P00229 on 2017/2/21.
 */
public class StationStateListInfo extends BaseEntity {
    public static final String TAG = "StationStateListInfo";
    public static final String KEY_STATIONAME = "stationName";
    public static final String KEY_STATIONCODE = "stationCode";
    public static final String KEY_CAPACITY = "capacity";
    public static final String KEY_CURGENERATION = "curGeneration";
    public static final String KEY_EQUHOURS = "equHours";
    public static final String KEY_DEVALARM = "devAlarm";
    public static final String KEY_INTERLLIGENTALARM = "intelligentAlarm";
    public static final String KEY_PERMWPOWER = "perMWPower";
    public static final String KEY_REALTIMEPOWER = "realTimePower";
    public static final String KEY_STATIONSTATE = "stationStatus";
    public static final String KEY_TOTAL = "total";
    public static final String KEY_LIST = "list";
    private List<StationStateInfo> stationStateInfos;
    private ServerRet serverRet;
    private int total;

    @Override
    public boolean parseJson(JSONObject jsonObject) throws Exception {
        if (jsonObject == null) {
            return false;
        }
        JSONReader jsonReader = new JSONReader(jsonObject);
        total = jsonReader.getInt(KEY_TOTAL);
        JSONArray jsonArray = jsonReader.getJSONArray(KEY_LIST);
        stationStateInfos = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            StationStateInfo staionStateInfo = new StationStateInfo();
            JSONObject jsonObject1 = jsonArray.getJSONObject(i);
            JSONReader jsonReader1 = new JSONReader(jsonObject1);
            staionStateInfo.setStationCode(jsonReader1.getString(KEY_STATIONCODE));
            staionStateInfo.setStationName(jsonReader1.getString(KEY_STATIONAME));
            staionStateInfo.setStationStatus(jsonReader1.getString(KEY_STATIONSTATE));
            staionStateInfo.setRealTimePower(jsonReader1.getDouble(KEY_REALTIMEPOWER));
            staionStateInfo.setPerMWPower(jsonReader1.getDouble(KEY_PERMWPOWER));
            staionStateInfo.setCapacity(jsonReader1.getDouble(KEY_CAPACITY));
            staionStateInfo.setCurGeneration(jsonReader1.getDouble(KEY_CURGENERATION));
            staionStateInfo.setDevAlarm(jsonReader1.getInt(KEY_DEVALARM));
            staionStateInfo.setIntelligentAlarm(jsonReader1.getInt(KEY_INTERLLIGENTALARM));
            staionStateInfo.setEquHours(jsonReader1.getDouble(KEY_EQUHOURS));
            stationStateInfos.add(staionStateInfo);
        }
        return true;
    }
    // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0001_F03_Android_S01  删除Android的demo账号    【修改人】：zhaoyufeng

    @Override
    public void setServerRet(ServerRet serverRet) {
        this.serverRet = serverRet;
    }

    public List<StationStateInfo> getStationStateInfos() {
        return stationStateInfos;
    }

    public ServerRet getServerRet() {
        return serverRet;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
