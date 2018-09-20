package com.huawei.solarsafe.bean.device;

import android.util.Log;

import com.huawei.solarsafe.bean.BaseEntity;
import com.huawei.solarsafe.bean.ServerRet;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Create Date: 2017/4/19
 * Create Author: P00171
 * Description :
 */
public class DevChangeEntity extends BaseEntity {
    private boolean result;

    @Override
    public boolean parseJson(JSONObject jsonObject) throws Exception {
        try {
            result = jsonObject.getBoolean("result");
        } catch (JSONException e) {
            Log.e(TAG, "parseJson: "+e.getMessage() );
            result = false;
        }
        return true;
    }
    // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0001_F03_Android_S01  删除Android的demo账号    【修改人】：zhaoyufeng

    @Override
    public void setServerRet(ServerRet serverRet) {

    }

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }
}
