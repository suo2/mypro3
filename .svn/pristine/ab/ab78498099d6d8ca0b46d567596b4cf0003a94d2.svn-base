package com.huawei.solarsafe.bean.device;

import android.util.Log;

import com.google.gson.Gson;
import com.huawei.solarsafe.bean.BaseEntity;
import com.huawei.solarsafe.bean.ServerRet;
import com.huawei.solarsafe.utils.JSONReader;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;
import java.util.List;

/**
 * Created by p00507
 * on 2017/9/26.
 */
public class CurrencySignalDataInfoList extends BaseEntity {
    private List<CurrencySignalDataInfo> list;

    @Override
    public boolean parseJson(JSONObject jsonObject) throws Exception {
        try {
            JSONReader jsonReader = new JSONReader(jsonObject);
            JSONObject jsonObject0 = jsonReader.getJSONObject("data");
            Iterator<String> keys = jsonObject0.keys();
            while (keys.hasNext()) {
                String key = keys.next();
                if ("devRuningStatus".equals(key)) {
                    continue;
                }
                JSONObject jsonObject1 = jsonObject0.getJSONObject(key);
                Gson gson = new Gson();
                CurrencySignalDataInfo currencySignalDataInfo = gson.fromJson(jsonObject1.toString(), CurrencySignalDataInfo.class);
                list.add(currencySignalDataInfo);
            }
            CurrencySignalDataInfoList currencySignalDataInfoList = new CurrencySignalDataInfoList();
            currencySignalDataInfoList.setList(list);
        } catch (JSONException e) {
            Log.e(TAG, "parseJson: " + e.getMessage());
        }
        return true;
    }
    // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0001_F03_Android_S01  删除Android的demo账号    【修改人】：zhaoyufeng

    @Override
    public void setServerRet(ServerRet serverRet) {

    }

    public List<CurrencySignalDataInfo> getList() {
        return list;
    }

    public void setList(List<CurrencySignalDataInfo> list) {
        this.list = list;
    }
}
