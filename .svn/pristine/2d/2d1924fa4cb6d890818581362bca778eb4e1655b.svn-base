package com.huawei.solarsafe.bean.station.kpi;

import com.google.gson.Gson;
import com.huawei.solarsafe.bean.BaseEntity;
import com.huawei.solarsafe.bean.ServerRet;

import org.json.JSONObject;

/**
 * Created by P00229 on 2017/2/17.
 */
public class StationWeatherInfo extends BaseEntity {
    public static final String TAG = StationWeatherInfo.class.getSimpleName();

    private ServerRet serverRet;
    private WeatherInfo weatherInfo;

    public ServerRet getServerRet() {
        return serverRet;
    }

    public WeatherInfo getWeatherInfo() {
        return weatherInfo;
    }


    @Override
    public boolean parseJson(JSONObject jsonObject) throws Exception {
        if (jsonObject == null) {
            return false;
        }
        String string = jsonObject.toString();
        Gson gson = new Gson();
        weatherInfo = gson.fromJson(string, WeatherInfo.class);
        return true;
    }
    // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0001_F03_Android_S01  删除Android的demo账号    【修改人】：zhaoyufeng

    @Override
    public void setServerRet(ServerRet serverRet) {
        this.serverRet = serverRet;
    }


}
