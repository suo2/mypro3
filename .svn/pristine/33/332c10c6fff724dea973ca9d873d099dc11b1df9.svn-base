package com.huawei.solarsafe.bean.ivcurve;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.huawei.solarsafe.bean.BaseEntity;
import com.huawei.solarsafe.bean.ServerRet;
import com.huawei.solarsafe.net.JSONReader;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by P00507
 * on 2017/7/25.
 */
public class AllIVData extends BaseEntity {
    //统一定义的返回码
    ServerRet mRetCode;
    private List<StationIvData> ivDataList;
    @Override
    public boolean parseJson(JSONObject jsonObject) throws Exception {
        if (jsonObject == null) {
            return false;
        }
        JSONReader jsonReader = new JSONReader(jsonObject);
        String temp = jsonReader.getString("data");
        Gson gson = new Gson();
        Type type = new TypeToken<List<StationIvData>>() {
        }.getType();
        ivDataList = gson.fromJson(temp, type);
        return true;
    }
    // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0001_F03_Android_S01  删除Android的demo账号    【修改人】：zhaoyufengoyufeng

    @Override
    public void setServerRet(ServerRet serverRet) {
        this.mRetCode = serverRet;
    }

    public List<StationIvData> getIvDataList() {
        return ivDataList;
    }

}
