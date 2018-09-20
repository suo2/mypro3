package com.huawei.solarsafe.bean.device;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.huawei.solarsafe.bean.BaseEntity;
import com.huawei.solarsafe.bean.ServerRet;

import org.json.JSONObject;
import java.lang.reflect.Type;

/**
 * Created by P00708 on 2018/3/9.
 */

public class BoosterDevRealTimeBean extends BaseEntity {


    private BoosterDevRealTimeData boosterDevRealTimeData;


    @Override
    public boolean parseJson(JSONObject jsonObject) throws Exception {

        String temp = jsonObject.toString();
        Gson gson = new Gson();
        Type type = new TypeToken<BoosterDevRealTimeData>() {
        }.getType();
        if(!TextUtils.isEmpty(temp)){
            boosterDevRealTimeData = gson.fromJson(temp, type);
        }
        return true;

    }

    @Override
    public void setServerRet(ServerRet serverRet) {}

    public BoosterDevRealTimeData getBoosterDevRealTimeData() {

        return boosterDevRealTimeData;
    }
}
