/*
 * Copyright (C) TD Tech<br>
 * All Rights Reserved.<br>
 * 
 */
package com.huawei.solarsafe.bean.stationmagagement;

import android.text.TextUtils;

import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.huawei.solarsafe.bean.BaseEntity;
import com.huawei.solarsafe.bean.ServerRet;
import com.huawei.solarsafe.utils.JSONReader;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.HashMap;

/**
 * Create Date: 2016-8-02<br>
 * Create Author: P00171<br>
 * Description : 查询设备返回信息实体类
 */
public class DevInfo extends BaseEntity implements Serializable {

    public static final String KEY_ASSETS_LIST = "subDevList";
    private static final long serialVersionUID = -5557450030434862809L;
    private boolean isExits=false;//设备是否存在
    private SubDev dev;//接入设备实体类
    private SubDev[] subDevList;//下挂设备列表
    private boolean isBoundStation=false;//是否已绑定电站

    @Override
    public boolean parseJson(JSONObject jsonObject) throws Exception {
        if (jsonObject == null) {
            return false;
        }

        String devType = jsonObject.optString("subDevFloor");

        if (TextUtils.isEmpty(devType)){
            if ("has station".equals(jsonObject.getString("data"))){
                isExits=true;
                isBoundStation=true;
            }
        }else{
            isExits=true;
        }

        JSONReader jsonReader = new JSONReader(jsonObject);
        dev = parseSubDev(new JSONReader(jsonReader.getJSONObject("dev")));

        String pvCapacityStr=jsonObject.optString("pvCapacity");
        if (!TextUtils.isEmpty(pvCapacityStr)){
            Type pvCapacityType=new TypeToken<HashMap<String,SubDev.PvCapacity>>(){}.getType();
            HashMap<String,SubDev.PvCapacity> pvCapacity=new GsonBuilder().enableComplexMapKeySerialization().create().fromJson(pvCapacityStr,pvCapacityType);
            dev.setPvCapacity(pvCapacity);
        }

        JSONArray jsonArray = jsonReader.getJSONArray(KEY_ASSETS_LIST);
        int len = jsonArray.length();
        subDevList = new SubDev[len];
        for (int i = 0; i < len; i++) {
            if ("2".equals(devType)) {
                subDevList[i] = parseSubDev(new JSONReader(new JSONReader(jsonArray.getJSONObject(i)).getJSONObject("dev")));
            } else {
                subDevList[i] = parseSubDev(new JSONReader(jsonArray.getJSONObject(i)));
            }
        }
        return true;
    }

    private SubDev parseSubDev(JSONReader jsonReader) {
        SubDev subDev = new SubDev();
        subDev.setId(jsonReader.getLong("id"));
        subDev.setParentId(jsonReader.getLong("parentId"));
        subDev.setStationCode(jsonReader.getString("stationCode"));
        subDev.setAreaId(jsonReader.getString("areaId"));
        subDev.setMatrixId(jsonReader.getLong("matrixId"));
        subDev.setDevTypeId(jsonReader.getInt("devTypeId"));
        subDev.setBusiName(jsonReader.getString("busiName"));
        subDev.setBusiCode(jsonReader.getString("busiCode"));
        subDev.setHostAddress(jsonReader.getString("hostAddress"));
        subDev.setEsnCode(jsonReader.getString("esnCode"));
        subDev.setPortNumber(jsonReader.getInt("portNumber"));
        subDev.setParentEsnCode(jsonReader.getString("parentEsnCode"));
        subDev.setModelVersionCode(jsonReader.getString("modelVersionCode"));
        subDev.setPowerCode(jsonReader.getString("powerCode"));
        subDev.setProtocolAddr(jsonReader.getInt("protocolAddr"));
        subDev.setLongitude(jsonReader.getDouble("longitude"));
        subDev.setLatitude(jsonReader.getDouble("latitude"));
        subDev.setPn(jsonReader.getString("pn"));
        subDev.setAssemblyType(jsonReader.getString("assemblyType"));
        subDev.setInterval(jsonReader.getString("interval"));
        subDev.setTwoLevelDomain(jsonReader.getInt("twoLevelDomain") + "");
        subDev.setCapacity(jsonReader.getDouble("capacity") + "");
        return subDev;
    }
    // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0001_F03_Android_S01  删除Android的demo账号    【修改人】：zhaoyufeng

    @Override
    public void setServerRet(ServerRet serverRet) {

    }

    public boolean isExits() {
        return isExits;
    }

    public void setExits(boolean exits) {
        isExits = exits;
    }

    public boolean isBoundStation() {
        return isBoundStation;
    }

    public SubDev getDev() {
        return dev;
    }

    public void setDev(SubDev dev) {
        this.dev = dev;
    }

    public SubDev[] getSubDevs() {
        return subDevList;
    }


    @Override
    public String toString() {
        return "DevInfo{" +
                "isExits=" + isExits +
                ", dev=" + dev +
                ", subDevs=" + Arrays.toString(subDevList) +
                '}';
    }

}
