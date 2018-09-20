package com.huawei.solarsafe.bean.ivcurve;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.huawei.solarsafe.bean.BaseEntity;
import com.huawei.solarsafe.bean.ServerRet;
import com.huawei.solarsafe.utils.JSONReader;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by P00507
 * on 2017/7/25.
 */
public class StringIVLists extends BaseEntity {
    //统一定义的返回码
    ServerRet mRetCode;
    private List<String> voltage;
    private List<String> current;
    private List<String> power;

    @Override
    public boolean parseJson(JSONObject jsonObject) throws Exception {
        if(jsonObject == null){
            return false;
        }
        JSONReader jsonReader = new JSONReader(jsonObject);
        String voltageTemp = jsonReader.getString("voltage");
        String currentTemp = jsonReader.getString("current");
        String powerTemp = jsonReader.getString("power");
        Gson gson = new Gson();
        Type type = new TypeToken<List<String>>() {
        }.getType();
        voltage = gson.fromJson(voltageTemp, type);
        current = gson.fromJson(currentTemp, type);
        power = gson.fromJson(powerTemp, type);
        return true;
    }
    // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0001_F03_Android_S01  删除Android的demo账号    【修改人】：zhaoyufengfeng

    @Override
    public void setServerRet(ServerRet serverRet) {
        this.mRetCode = serverRet;
    }

    public List<String> getVoltage() {
        return voltage;
    }


    public List<String> getCurrent() {
        return current;
    }

    public void setCurrent(List<String> current) {
        this.current = current;
    }

    public List<String> getPower() {
        return power;
    }

    public void setPower(List<String> power) {
        this.power = power;
    }
}
