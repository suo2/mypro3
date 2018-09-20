package com.huawei.solarsafe.presenter.pnlogger;

import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.huawei.solarsafe.MyApplication;
import com.huawei.solarsafe.R;
import com.huawei.solarsafe.bean.pnlogger.PntGetSecondName;
import com.huawei.solarsafe.logger104.utils.PntCommonCallback;
import com.huawei.solarsafe.model.pnlogger.PvOperator;
import com.huawei.solarsafe.presenter.BasePresenter;
import com.huawei.solarsafe.utils.LocalData;
import com.huawei.solarsafe.utils.ToastUtil;
import com.huawei.solarsafe.view.pnlogger.IPvDataView;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.Map;

/**
 * Create Date: 2017/3/8
 * Create Author: P00028
 * Description :
 */
public class PVPresenter extends BasePresenter<IPvDataView, PvOperator> {
    private static final String TAG = "PVPresenter";

    public PVPresenter() {
        setModel(new PvOperator());
    }



    public void getSecondName(String esn) {
        model.getSecondName(esn, new PntCommonCallback() {
            @Override
            protected void onError(Exception e) {
                if (view != null) {
                    ToastUtil.showMessage(R.string.net_error);
                }
            }

            @Override
            protected void onSuccess(String data) {
                // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0004_F01_Android_S02  敏感信息检查-日志中不能包含敏感信息    【修改人】：zhaoyufeng
                JSONObject json ;
                try {
                    json = new JSONObject(data);
                    Type type = new TypeToken<PntGetSecondName>() {
                    }.getType();
                    Gson gson = new Gson();
                    PntGetSecondName pntGetSecondName = gson.fromJson(json.getString("data"), type);
                    view.getSecondName(pntGetSecondName);
                } catch (JSONException e) {
                    Log.e(TAG, "onSuccess: "+e.getMessage() );
                }
            }

            @Override
            protected void onFail(int failCode, String msg) {
                if (view != null) {
                    if (failCode == 0) {
                        msg = MyApplication.getContext().getString(R.string.get_pnt_secend_name) + msg;
                    }
                    ToastUtil.showMessage(msg);
                }
            }
        });
    }

    public void reqSetDevNames(Map<String, String> names) {
        for (Map.Entry<String, String> var : names.entrySet()) {
            final String esn = var.getKey();
            String value = var.getValue();
            String longitudeStr = LocalData.getInstance().getLon();
            String latitudeStr = LocalData.getInstance().getLat();
            model.upLoadData(esn, value, latitudeStr, longitudeStr, new PntCommonCallback() {
                @Override
                protected void onError(Exception e) {
                    if (view != null) {
                        ToastUtil.showMessage(R.string.net_error);
                    }
                }

                @Override
                protected void onSuccess(String data) {
                }

                @Override
                protected void onFail(int failCode, String msg) {
                    if (failCode == 0) {
                        msg = "配置设备名称" + msg;
                    }
                    Toast.makeText(MyApplication.getContext(), msg, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}