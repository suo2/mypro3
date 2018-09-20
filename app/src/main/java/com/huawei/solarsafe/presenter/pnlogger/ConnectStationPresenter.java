package com.huawei.solarsafe.presenter.pnlogger;

import android.text.TextUtils;
import android.util.Log;

import com.huawei.solarsafe.MyApplication;
import com.huawei.solarsafe.R;
import com.huawei.solarsafe.logger104.utils.PntCommonCallback;
import com.huawei.solarsafe.model.pnlogger.StationOperator;
import com.huawei.solarsafe.presenter.BasePresenter;
import com.huawei.solarsafe.utils.JSONReader;
import com.huawei.solarsafe.utils.ToastUtil;
import com.huawei.solarsafe.view.pnlogger.IConnectStationView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Create Date: 2017/3/15
 * Create Author: P00171
 * Description :
 */
public class ConnectStationPresenter extends BasePresenter<IConnectStationView, StationOperator> {
    private static final String TAG = "ConnectStationPresenter";
    private String esn;

    public ConnectStationPresenter() {
        setModel(new StationOperator());
    }

    public void connectStation(List<String> esns, String stationCode) {
        esn = esns.get(0);
        if (view != null) {
            view.showDialog();
        }
        model.connectStation(esns, stationCode, new PntCommonCallback() {
            @Override
            protected void onError(Exception e) {
                if (view != null) {
                    view.dismissDialog();
                }
                onFailed(e);

            }

            @Override
            protected void onSuccess(String respone) {
                if (view != null) {
                    view.dismissDialog();
                }
                view.connectSuccess(esn, MyApplication.getContext().getString(R.string.connect_success));
            }

            @Override
            protected void onFail(int failCode, String msg) {
                if (view != null) {
                    view.dismissDialog();
                }
                if (failCode == 0) {
                    msg = getString(R.string.connect_station) + msg;
                }
                ToastUtil.showMessage(msg);
            }
        });
    }

    protected void onFailed(Exception e) {
        // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0004_F01_Android_S02  敏感信息检查-日志中不能包含敏感信息    【修改人】：zhaoyufeng
        Log.e(TAG, "connectStation: onError", e);
        if (view != null) {
            view.connectFail(esn, MyApplication.getContext().getString(R.string.connect_failed));
        }
    }

    protected void onSuccess(String data) {
        if (TextUtils.isEmpty(data)) {
            view.connectFail(esn, MyApplication.getContext().getString(R.string.connect_failed));
            return;
        }
        boolean result ;
        try {
            JSONReader reader = new JSONReader(new JSONObject(data));
            result = reader.getBoolean("success");
            if (view != null) {
                if (result) {
                    view.connectSuccess(esn, MyApplication.getContext().getString(R.string.connect_success));
                } else {
                    view.connectFail(esn, MyApplication.getContext().getString(R.string.connect_failed));
                }
            }

        } catch (JSONException e) {
            if (view != null) {
                view.connectFail(esn, MyApplication.getContext().getString(R.string.connect_failed));
            }
            // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0004_F01_Android_S02  敏感信息检查-日志中不能包含敏感信息    【修改人】：zhaoyufeng
            Log.e(TAG, "onResponse JSONException", e);
        }
    }

}
