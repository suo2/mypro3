package com.huawei.solarsafe.net;

import android.util.Log;

import com.huawei.solarsafe.MyApplication;
import com.huawei.solarsafe.R;
import com.huawei.solarsafe.bean.BaseEntity;
import com.zhy.http.okhttp.callback.Callback;

import org.json.JSONObject;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by P00028 on 2016/11/28.
 */
//处理网络请求返回数据的通用callback
public abstract class CommonCallback extends Callback<BaseEntity> {
    public String TAG = "CommonCallback";
    private BaseEntity entity;

    public CommonCallback(Class<? extends BaseEntity> clazz) {
        if (clazz == null) {
            throw new IllegalArgumentException("error args");
        }
        try {
            entity = clazz.newInstance();
            entity.setTag(clazz.getSimpleName());
        } catch (Exception e) {
            Log.e(TAG, "error", e);
        }
        // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0001_F03_Android_S01  删除Android的demo账号    【修改人】：zhaoyufeng
    }

    public CommonCallback(Class<? extends BaseEntity> clazz, String tag) {
        if (clazz == null) {
            throw new IllegalArgumentException("error args");
        }
        try {
            entity = clazz.newInstance();
            entity.setTag(tag);
        } catch (Exception e) {
            Log.e(TAG, "error", e);
        }
        // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0001_F03_Android_S01  删除Android的demo账号    【修改人】：zhaoyufeng
    }

    @Override
    public void onError(Call call, Exception e, int id) {
        Log.e(TAG, "onError: " + "\n" + e.getMessage());
    }

    //处理数据
    @Override
    public BaseEntity parseNetworkResponse(Response response, int id) throws Exception {
        if (response.request().url().toString().contains("/cas/login?service=")){
            MyApplication.reLogin(MyApplication.getContext().getResources().getString(R.string.other_device_login));
            return null;
        }
        String body = response.body().string();
        entity.preParse(new JSONObject(body));
//        Log.e(TAG, "parseNetworkResponse: " + response.request().tag() + "\n" + response.request().headers());
        return entity;
    }

}
