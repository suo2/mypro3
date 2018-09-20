package com.huawei.solarsafe.presenter.pnlogger;

import android.util.Log;

import com.huawei.solarsafe.R;
import com.huawei.solarsafe.bean.common.LogCallBack;
import com.huawei.solarsafe.logger104.database.PntConnectInfoItem;
import com.huawei.solarsafe.model.pnlogger.SelectPntMode;
import com.huawei.solarsafe.presenter.BasePresenter;
import com.huawei.solarsafe.utils.ToastUtil;
import com.huawei.solarsafe.view.pnlogger.ISelectPntView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Create Date: 2017/3/7
 * Create Author: P00171
 * Description :
 */
public class SelectPntPresenter extends BasePresenter<ISelectPntView, SelectPntMode> {
    private static final String TAG = "SelectPntPresenter";

    public SelectPntPresenter() {
        setModel(new SelectPntMode());
    }

    public void getPntList() {
        List<PntConnectInfoItem> list = model.getLocalPntList();
        if(view!=null){
            view.showPntList(list);
        }

    }

    public void getScStatus(final PntConnectInfoItem deviceInfo, String esn) {
        if(view!=null){
            view.showSecondDev(deviceInfo);
        }
    }

    /**
     * 查询电站状态
     * @param esn 设备esn号
     */
    public void getDevBindStatus(final String esn) {
        view.showDialog();
        model.getDeviceStatus(esn, new LogCallBack() {

            @Override
            protected void onFailed(Exception e) {

                Log.e(TAG, "onError: ", e);
                if (view != null) {
                    view.dismissDialog();
                    ToastUtil.showMessage(R.string.net_error);
                }
            }

            @Override
            protected void onSuccess(String data) {
                // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0004_F01_Android_S02  敏感信息检查-日志中不能包含敏感信息    【修改人】：zhaoyufeng
                try {
                    JSONObject json = new JSONObject(data);
                    boolean isOk = json.getBoolean("success");
                    if (isOk) {
                        int status = json.getInt("data");
                        view.getDevBindStatus(status);
                    } else {
                        view.dismissDialog();
                        ToastUtil.showMessage(R.string.get_device_status_error);
                    }
                } catch (JSONException e) {
                    view.dismissDialog();
                    ToastUtil.showMessage(R.string.get_device_status_error);
                    Log.e(TAG, "onResponse: getDevBindStatus", e);
                }
            }
        });
    }
}
