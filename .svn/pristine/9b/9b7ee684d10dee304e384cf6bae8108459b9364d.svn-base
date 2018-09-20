package com.huawei.solarsafe.bean.device;

import com.huawei.solarsafe.bean.BaseEntity;
import com.huawei.solarsafe.bean.ServerRet;
import com.huawei.solarsafe.utils.JSONReader;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by P00229 on 2017/2/21.
 */
public class DispersionStatisticsInfo extends BaseEntity {
    public static final String TAG = "DispersionStatisticsInfo";
    public static final String KEY_DISPERSION = "dispersion";
    private int[] dispersions;
    private ServerRet serverRet;

    @Override
    public boolean parseJson(JSONObject jsonObject) throws Exception {
        if (jsonObject == null) {
            return false;
        }
        JSONReader jsonReader = new JSONReader(jsonObject);
        JSONArray jsonArray = jsonReader.getJSONArray(KEY_DISPERSION);
        dispersions = new int[jsonArray.length()];
        for (int i = 0; i < jsonArray.length(); i++) {
            dispersions[i] = jsonArray.getInt(i);
        }
        return true;
    }
    // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0001_F03_Android_S01  删除Android的demo账号    【修改人】：zhaoyufeng

    @Override
    public void setServerRet(ServerRet serverRet) {
        this.serverRet = serverRet;
    }

    public int[] getDispersions() {
        return dispersions;
    }

    public ServerRet getServerRet() {
        return serverRet;
    }
}
