package com.huawei.solarsafe.bean.report;

import com.google.gson.Gson;
import com.huawei.solarsafe.bean.BaseEntity;
import com.huawei.solarsafe.bean.ServerRet;

import org.json.JSONObject;

/**
 * Created by P00708 on 2018/7/11.
 */

public class PowerCurveKpi extends BaseEntity {

    private PowerCurveData powerCurveData;
    public PowerCurveData powerCurveMothData;
    @Override
    public boolean parseJson(JSONObject jsonObject) throws Exception {
        Gson gson = new Gson();
        if (jsonObject == null) {
            return false;
        }
        powerCurveData = gson.fromJson(jsonObject.toString(),PowerCurveData.class);
//        powerCurveData = gson.fromJson(DayJson.json,PowerCurveData.class);
//        powerCurveMothData = gson.fromJson(DayJson.jsonMoth,PowerCurveData.class);
        return true;
    }

    @Override
    public void setServerRet(ServerRet serverRet) {

    }

    public PowerCurveData getPowerCurveData() {
        return powerCurveData;
    }

    public void setPowerCurveData(PowerCurveData powerCurveData) {
        this.powerCurveData = powerCurveData;
    }
}
