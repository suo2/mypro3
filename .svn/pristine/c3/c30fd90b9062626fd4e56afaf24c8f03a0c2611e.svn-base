package com.huawei.solarsafe.bean.common;

import android.util.Log;

import com.huawei.solarsafe.utils.Utils;
import com.zhy.http.okhttp.callback.Callback;

import org.json.JSONObject;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Gson公共解析返回类
 * Created by P00319 on 2017/2/23.
 */

public abstract class LogCallBack extends Callback<String> {
    private static final String TAG = "LogCallBack";

    @Override
    public String parseNetworkResponse(Response response, int id) throws Exception {
        // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0004_F01_Android_S02  敏感信息检查-日志中不能包含敏感信息    【修改人】：zhaoyufeng
        String body = response.body().string();
        JSONObject jsonObject = new JSONObject(body);
        Utils.logout(jsonObject.getInt("failCode"), jsonObject);

        return body;
    }

    @Override
    public void onError(Call call, Exception e, int id) {
        Log.e(TAG, "onError: " + "\n"  + e.getMessage());
        onFailed(e);
    }

    @Override
    public void onResponse(String response, int id) {
        // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0004_F01_Android_S02  敏感信息检查-日志中不能包含敏感信息    【修改人】：zhaoyufeng
        onSuccess(response);
    }

    protected abstract void onFailed(Exception e);

    protected abstract void onSuccess(String data);

}
