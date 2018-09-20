package com.huawei.solarsafe.bean.station.map;

import com.huawei.solarsafe.bean.BaseEntity;
import com.huawei.solarsafe.bean.ServerRet;
import com.huawei.solarsafe.utils.JSONReader;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by P00229 on 2017/2/20.
 */
public class StationMapList extends BaseEntity {
    private List<StationForMapInfo> stationMapLists;
    private ServerRet serverRet;
    public static final String KEY_LIST = "list";
    public static final String KEY_STATIONNAME = "stationName";
    public static final String KEY_STATIONCODE = "stationCode";
    public static final String KEY_HEALTHSTATE = "healthState";
    public static final String KEY_LATITUDE = "latitude";
    public static final String KEY_LONGITUDE = "longitude";

    @Override
    public boolean parseJson(JSONObject jsonObject) throws Exception {
        if (jsonObject == null) {
            return false;
        }
        JSONReader jsonReader = new JSONReader(jsonObject);
        JSONArray jsonArray = jsonReader.getJSONArray(KEY_LIST);
        stationMapLists = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONReader jr = new JSONReader(jsonArray.getJSONObject(i));
            StationForMapInfo stationForMapInfo = new StationForMapInfo();
            stationForMapInfo.setStationName(jr.getString(KEY_STATIONNAME));
            stationForMapInfo.setStationCode(jr.getString(KEY_STATIONCODE));
            stationForMapInfo.setStationState(jr.getString(KEY_HEALTHSTATE));
            stationForMapInfo.setLatitude(jr.getDouble(KEY_LATITUDE));
            stationForMapInfo.setLongitude(jr.getDouble(KEY_LONGITUDE));
            stationMapLists.add(stationForMapInfo);
        }
        return true;
    }
    // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0001_F03_Android_S01  删除Android的demo账号    【修改人】：zhaoyufengg

    @Override
    public void setServerRet(ServerRet serverRet) {
        this.serverRet = serverRet;
    }

    public List<StationForMapInfo> getStationMapLists() {
        return stationMapLists;
    }

    public void setStationMapLists(List<StationForMapInfo> stationMapLists) {
        this.stationMapLists = stationMapLists;
    }

    public ServerRet getServerRet() {
        return serverRet;
    }

    @Override
    public String toString() {
        return "StationMapList{" +
                "stationMapLists=" + stationMapLists +
                ", serverRet=" + serverRet +
                '}';
    }
}
