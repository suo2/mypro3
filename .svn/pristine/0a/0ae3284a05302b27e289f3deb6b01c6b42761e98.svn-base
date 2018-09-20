package com.huawei.solarsafe.logger104.utils;

import android.util.Log;

import com.huawei.solarsafe.MyApplication;
import com.huawei.solarsafe.R;
import com.huawei.solarsafe.net.NetRequest;
import com.huawei.solarsafe.utils.Utils;
import com.zhy.http.okhttp.callback.Callback;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.Call;
import okhttp3.Response;


/**
 * Create Date: 2017/6/20
 * Create Author: P00171
 * Description :
 */
public abstract class PntCommonCallback extends Callback<String> {
    private static final String TAG = "PntCommonCallback";

    @Override
    public void onError(Call call, Exception e, int id) {
        Log.e(TAG, "onError: " + "\n"  + e.getMessage());
        onError(e);
    }

    @Override
    public String parseNetworkResponse(Response response, int id) throws Exception {
//        NetRequest.handleToken(response);
        String body = response.body().string();
        return body;
    }

    @Override
    public void onResponse(String response, int id) {
        // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0004_F01_Android_S02  敏感信息检查-日志中不能包含敏感信息    【修改人】：zhaoyufeng
        try {
            JSONObject jsonObject = new JSONObject(response);
            String msg = null;
            int failCode = jsonObject.getInt("failCode");
            Utils.logout(failCode,jsonObject);
            switch (failCode) {
                case 444:
                    msg = jsonObject.getString("data");
                    break;
                //3.1管理系统相应错误
                case -1:
                    msg = getString(R.string.dev_alone_bd_str);
                    break;
                case -2:
                    msg = getString(R.string.dev_jieru_failed);
                    break;
                case -3:
                    msg = getString(R.string.dev_number_yuexian);
                    break;
                case -4:
                    msg = getString(R.string.household_lisence_notice_str);
                    break;
                case -5:
                    msg = getString(R.string.capity_yuexian_str);
                    break;
                case -6:
                    msg = getString(R.string.station_name_repeat_str);
                    break;
                //B版数采相应错误
                case 701://数采ESN不正确
                    msg = getString(R.string.be_sure_esn_is_right);
                    break;
                case 702://数采与管理系统未连接
                    msg = getString(R.string.pnl_system_net_is_normal);
                    break;
                case 703://命令执行超时
                    msg = getString(R.string.out_time);
                    break;
                case 704://数采设备已经关联电站
                    onSuccess(response);
                    break;
                case 0:
                    boolean success = jsonObject.getBoolean("success");
                    if (success) {
                        onSuccess(response);
                        return;
                    } else {
                        msg = getString(R.string.request_failed);
                        break;
                    }
                default:
                    msg = getString(R.string.request_failed);
                    break;
            }
            onFail(failCode, msg);
        } catch (JSONException e) {
            // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0004_F01_Android_S02  敏感信息检查-日志中不能包含敏感信息    【修改人】：zhaoyufeng
            Log.e(TAG," Convert to json error ", e);
        }
    }

    protected abstract void onError(Exception e);

    protected abstract void onSuccess(String respone);

    protected abstract void onFail(int failCode, String msg);

    public String getString(int resId) {
        return MyApplication.getContext().getString(resId);
    }
}
