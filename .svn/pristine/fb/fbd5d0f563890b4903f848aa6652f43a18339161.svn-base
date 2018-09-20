package com.huawei.solarsafe.presenter.pnlogger;


import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.huawei.solarsafe.MyApplication;
import com.huawei.solarsafe.R;
import com.huawei.solarsafe.bean.pnlogger.PntGetSecondName;
import com.huawei.solarsafe.logger104.database.PntConnectInfoItem;
import com.huawei.solarsafe.logger104.utils.PntCommonCallback;
import com.huawei.solarsafe.model.pnlogger.ShowSecondMode;
import com.huawei.solarsafe.presenter.BasePresenter;
import com.huawei.solarsafe.utils.LocalData;
import com.huawei.solarsafe.utils.ToastUtil;
import com.huawei.solarsafe.view.pnlogger.IShowSecondView;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Create Date: 2017/3/7
 * Create Author: P00171
 * Description :
 */
public class ShowSecondPresenter extends BasePresenter<IShowSecondView, ShowSecondMode> {
    private static final String TAG = "ShowSecondPresenter";

    public ShowSecondPresenter() {
        setModel(new ShowSecondMode());
    }

    public void getDevBindStatus(final String esn) {
        view.showDialog();
        model.getDeviceStatus(esn, new PntCommonCallback() {
            @Override
            protected void onError(Exception e) {
                if (view != null) {
                    view.dismissDialog();
                }
                ToastUtil.showMessage(R.string.net_error);
            }

            @Override
            protected void onSuccess(String respone) {
                if (view != null) {
                    view.dismissDialog();
                }
                try {
                    JSONObject json = new JSONObject(respone);
                    int status = json.getInt("data");
                    view.getDevBindStatus(status, esn);
                } catch (JSONException e) {
                    Log.e(TAG, "onSuccess: " + e.getMessage());
                }
            }

            @Override
            protected void onFail(int failCode, String msg) {
                if (view != null) {
                    view.dismissDialog();
                }
                if (failCode == 0) {
                    msg = getString(R.string.get_device_status_error);
                }
                Toast.makeText(MyApplication.getContext(), msg, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void getSecondName(String esn) {
        view.showDialog();
        model.getSecondName(esn, new PntCommonCallback() {
            @Override
            protected void onError(Exception e) {
                if (view != null) {
                    view.dismissDialog();
                    ToastUtil.showMessage(R.string.net_error);
                }
            }

            @Override
            protected void onSuccess(String data) {
                // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0004_F01_Android_S02  敏感信息检查-日志中不能包含敏感信息    【修改人】：zhaoyufeng
                JSONObject json = null;
                try {
                    json = new JSONObject(data);
                    view.dismissDialog();
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
                    view.dismissDialog();
                    if (failCode == 0) {
                        msg = MyApplication.getContext().getString(R.string.get_pnt_secend_name) + msg;
                    }
                    ToastUtil.showMessage(msg);
                }
            }
        });
    }

    public void refreshData() {
        String esn = LocalData.getInstance().getEsn();
        //【解DTS单】 DTS2018032000774 修改人：杨彬洁
        if(!TextUtils.isEmpty(esn)) {
            ArrayList<PntConnectInfoItem> items = model.queryDeviceByPntESN(esn);
            view.freshData(items);
        }
    }
}