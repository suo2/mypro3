package com.huawei.solarsafe.net;

import android.util.Log;

import com.huawei.solarsafe.utils.Utils;
import com.zhy.http.okhttp.callback.Callback;

import org.json.JSONObject;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by zWX527779 on 2018/2/8.
 */

public abstract class StringCallback extends Callback {
    private static final String TAG = "StringCallback";

    @Override
    public void onError(Call call, Exception e, int id) {
        Log.e(TAG, "onError: " + e.getMessage());
    }

    @Override
    public Object parseNetworkResponse(Response response, int id) throws Exception {
        String body = response.body().string();
        JSONObject jsonObject = new JSONObject(body);
        int failCode = jsonObject.getInt("failCode");
        Utils.logout(failCode,jsonObject);
        //二次认证时更新token
        if ((NetRequest.IP + "/user/secondAuth").equals(response.request().tag())){
            if(jsonObject.getBoolean("success")){
                NetRequest.handleToken(response);
            }
        }
        return body;
    }
}
