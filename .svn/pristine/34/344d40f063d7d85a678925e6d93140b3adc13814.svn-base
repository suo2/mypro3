package com.huawei.solarsafe.bean.station.kpi;

import android.text.TextUtils;

import com.huawei.solarsafe.bean.BaseEntity;
import com.huawei.solarsafe.bean.ServerRet;
import com.huawei.solarsafe.net.JSONReader;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by P00784 on 2018/7/19.
 */

public class StationListNew extends BaseEntity implements Serializable {
    //电站总数
    private static final String KEY_TOTAL = "total";
    //电站列表
    private static final String KEY_LIST = "list";
    private static final long serialVersionUID = 7398966183546069254L;

    //电站总数
    private int total;
    //电站列表
    private List<StationInfoNew> stationInfoList;

    //统一返回码
    private ServerRet retCode;

    @Override
    public boolean parseJson(JSONObject jsonObject) throws Exception {
        if (jsonObject == null) {
            return false;
        }
        JSONReader jsonReader = new JSONReader(jsonObject);
        total = jsonReader.getInt(KEY_TOTAL);
        JSONArray jsonArray = jsonReader.getJSONArray(KEY_LIST);
        stationInfoList = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            StationInfoNew stationInfo = new StationInfoNew();
            JSONObject jsonObject1 = (JSONObject) jsonArray.get(i);
            stationInfo.setStationCode(jsonObject1.getString("stationCode"));
            stationInfo.setEnergyStorage(jsonObject1.getString("energyStorage"));
            stationInfo.setStationPic(jsonObject1.getString("stationPic"));
            stationInfo.setStationDevoteDate(jsonObject1.getString("stationDevoteDate"));
            stationInfo.setStationName(jsonObject1.getString("stationName"));
            stationInfo.setStationAddr(jsonObject1.getString("stationAddr"));
            stationInfo.setState(jsonObject1.getString("state"));

            String daycapacity = jsonObject1.getString("daycapacity");
            if(TextUtils.isEmpty(daycapacity) || "null".equals(daycapacity) || "NULL".equals(daycapacity)){
                stationInfo.setDaycapacity(0);
            }else{
                stationInfo.setDaycapacity(jsonObject1.getDouble("daycapacity"));
            }
            String numberOfOptimizer = jsonObject1.getString("numberOfOptimizer");
            if(TextUtils.isEmpty(numberOfOptimizer) || "null".equals(numberOfOptimizer) || "NULL".equals(numberOfOptimizer)){
                stationInfo.setNumberOfOptimizer(0);
            }else{
                stationInfo.setNumberOfOptimizer(jsonObject1.getLong("numberOfOptimizer"));
            }
            String realcapacity = jsonObject1.getString("realcapacity");
            if(TextUtils.isEmpty(realcapacity) || "null".equals(realcapacity) || "NULL".equals(realcapacity)){
                stationInfo.setRealcapacity(0);
            }else{
                stationInfo.setRealcapacity(jsonObject1.getDouble("realcapacity"));
            }
            String daySelfCap = jsonObject1.getString("daySelfCap");
            if(TextUtils.isEmpty(daySelfCap) || "null".equals(daySelfCap) || "NULL".equals(daySelfCap)){
                stationInfo.setDaySelfCap(0);
            }else{
                stationInfo.setDaySelfCap(jsonObject1.getDouble("realcapacity"));
            }
            String latitude = jsonObject1.getString("latitude");
            if(TextUtils.isEmpty(latitude) || "null".equals(latitude) || "NULL".equals(latitude)){
                stationInfo.setLatitude(0);
            }else{
                stationInfo.setLatitude(jsonObject1.getDouble("latitude"));
            }
            String eqNumOfHours = jsonObject1.getString("eqNumOfHours");
            if(TextUtils.isEmpty(eqNumOfHours) || "null".equals(eqNumOfHours) || "NULL".equals(eqNumOfHours)){
                stationInfo.setEqNumOfHours(0);
            }else{
                stationInfo.setEqNumOfHours(jsonObject1.getDouble("eqNumOfHours"));
            }
            String totalcapacity = jsonObject1.getString("allCapacity");
            if(TextUtils.isEmpty(totalcapacity) || "null".equals(totalcapacity) || "NULL".equals(totalcapacity)){
                stationInfo.setTotalcapacity(0);
            }else{
                stationInfo.setTotalcapacity(jsonObject1.getDouble("allCapacity"));
            }
            String totalincome = jsonObject1.getString("totalincome");
            if(TextUtils.isEmpty(totalincome) || "null".equals(totalincome) || "NULL".equals(totalincome)){
                stationInfo.setTotalincome(0);
            }else{
                stationInfo.setTotalincome(jsonObject1.getDouble("totalincome"));
            }
            String power = jsonObject1.getString("power");
            if(TextUtils.isEmpty(power) || "null".equals(power) || "NULL".equals(power)){
                stationInfo.setPower(0);
            }else{
                stationInfo.setPower(jsonObject1.getDouble("power"));
            }
            String picLastModify = jsonObject1.getString("picLastModify");
            if(TextUtils.isEmpty(picLastModify) || "null".equals(picLastModify) || "NULL".equals(picLastModify)){
                stationInfo.setPicLastModify(0);
            }else{
                stationInfo.setPicLastModify(jsonObject1.getLong("picLastModify"));
            }
            String longitude = jsonObject1.getString("longitude");
            if(TextUtils.isEmpty(longitude) || "null".equals(longitude) || "NULL".equals(longitude)){
                stationInfo.setLongitude(0);
            }else{
                stationInfo.setLongitude(jsonObject1.getDouble("longitude"));
            }
            stationInfoList.add(stationInfo);
        }
        return true;
    }
    // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0001_F03_Android_S01  删除Android的demo账号    【修改人】：zhaoyufengg

    @Override
    public void setServerRet(ServerRet serverRet) {
        retCode = serverRet;
    }

    public int getTotal() {
        return total;
    }

    public List<StationInfoNew> getStationInfoList() {
        return stationInfoList;
    }

    public ServerRet getRetCode() {
        return retCode;
    }

    @Override
    public String toString() {
        return "StationList{" +
                "total=" + total +
                ", stationInfoList=" + stationInfoList.toString() +
                ", retCode=" + retCode +
                '}';
    }
}

