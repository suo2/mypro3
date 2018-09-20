package com.huawei.solarsafe.bean.station;

import com.huawei.solarsafe.bean.BaseEntity;
import com.huawei.solarsafe.bean.ServerRet;
import com.huawei.solarsafe.utils.JSONReader;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by p00322 on 2017/2/14.
 * description: 电站等效利用小时数排名列表获取接口解析
 */
public class EquivalentHourList extends BaseEntity {
    //电站总数
    private static final String KEY_PLANT_TOTAL = "plantTotalh";
    //第二层MAP
    private static final String KEY_PLANT_MSG = "plantMsg";
    private static final String KEY_PLANT_STATION = "plantStation";
    //电站总数
    int plantTotalh;
    //等效小时利用数电站排名列表
    List<EquivalentHourInfo> equivalentHourInfoList;
    //等效消失利用数电站ID（乱序）
    List<EquivalentStationNameInfo> stationNameInfoList;
    //统一定义的返回码
    ServerRet mRetCode;

    @Override
    public boolean parseJson(JSONObject jsonObject) throws Exception {
        if (jsonObject == null) {
            return false;
        }
        JSONReader jsonReader = new JSONReader(jsonObject);
        plantTotalh = jsonReader.getInt(KEY_PLANT_TOTAL);
        JSONArray jsonArray = jsonReader.getJSONArray(KEY_PLANT_MSG);
        JSONObject object = jsonReader.getJSONObject(KEY_PLANT_STATION);

        equivalentHourInfoList = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            EquivalentHourInfo equivalentHourInfo = new EquivalentHourInfo();
            JSONObject jsonObject1 = jsonArray.getJSONObject(i);
            String key = jsonObject1.keys().next();
            equivalentHourInfo.setStationName(key);
            equivalentHourInfo.setEquivalentHour(jsonObject1.getDouble(key));
            equivalentHourInfoList.add(equivalentHourInfo);
        }

        stationNameInfoList = new ArrayList<EquivalentStationNameInfo>();
        Iterator<String> keys = object.keys();

        while (keys.hasNext()) {
            EquivalentStationNameInfo equivalentStationNameInfo = new EquivalentStationNameInfo();
            String next = keys.next();
            equivalentStationNameInfo.setStationName(next);
            equivalentStationNameInfo.setStationId(object.optString(next));
            for (EquivalentHourInfo info : equivalentHourInfoList) {
                if (info.getStationName().equals(next)) {
                    stationNameInfoList.add(equivalentStationNameInfo);
                }
            }

        }

        return true;
    }
    // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0001_F03_Android_S01  删除Android的demo账号    【修改人】：zhaoyufengaoyufeng

    @Override
    public void setServerRet(ServerRet serverRet) {
        mRetCode = serverRet;
    }

    public int getPlantTotalh() {
        return plantTotalh;
    }

    public List<EquivalentHourInfo> getEquivalentHourInfoList() {
        return equivalentHourInfoList;
    }

    public List<EquivalentStationNameInfo> getStationNameInfoList() {
        return stationNameInfoList;
    }

    public ServerRet getRetCode() {
        return mRetCode;
    }

    @Override
    public String toString() {
        return "EquivalentHourList{" +
                "plantTotalh=" + plantTotalh +
                ", equivalentHourInfoList=" + equivalentHourInfoList +
                ", stationNameInfoList=" + stationNameInfoList +
                ", mRetCode=" + mRetCode +
                '}';
    }
}
