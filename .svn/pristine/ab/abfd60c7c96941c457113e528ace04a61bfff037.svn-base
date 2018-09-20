package com.huawei.solarsafe.presenter.maintaince.defect;

import android.util.Log;

import com.huawei.solarsafe.MyApplication;
import com.huawei.solarsafe.R;
import com.huawei.solarsafe.bean.BaseEntity;
import com.huawei.solarsafe.bean.device.DevList;
import com.huawei.solarsafe.bean.device.DevTypeListInfo;
import com.huawei.solarsafe.model.devicemanagement.IDevManagementModel;
import com.huawei.solarsafe.model.maintain.defect.DefectModel;
import com.huawei.solarsafe.net.CommonCallback;
import com.huawei.solarsafe.net.NetRequest;
import com.huawei.solarsafe.presenter.BasePresenter;
import com.huawei.solarsafe.utils.ToastUtil;
import com.huawei.solarsafe.view.maintaince.defects.picker.device.IDevicePickerView;

import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;

/**
 * Created by P00319 on 2017/2/21.
 */

public class DevicePickerPresenter extends BasePresenter<IDevicePickerView, DefectModel> {


    public void getDevList(String sId, int page, int pageSize, String devTypeId, String devName) {
        Map<String, String> param = new HashMap<>();
        if (devTypeId != null) {
            param.put("devTypeId", devTypeId);
        }
        param.put("pageSize", String.valueOf(pageSize));
        param.put("page", String.valueOf(page));
        param.put("stationIds", sId == null ? "" : sId);
        param.put("devName", devName == null ? "" : devName);
        NetRequest.getInstance().asynPostJson(NetRequest.IP + "/devManager/listDev", param, new CommonCallback(DevList.class) {
            @Override
            public void onError(Call call, Exception e, int id) {
                super.onError(call,e,id);
                // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0004_F01_Android_S02  敏感信息检查-日志中不能包含敏感信息    【修改人】：zhaoyufeng
                Log.e(NetRequest.TAG, "Request /devManager/listDev error", e);
                if (view != null) {
                    view.loadList(null);
                }
                if(e.toString().contains("java.net.ConnectException")){
                    ToastUtil.showMessage(MyApplication.getContext().getString(R.string.net_error));
                }
            }

            @Override
            public void onResponse(BaseEntity response, int id) {
                if (response instanceof DevList) {
                    if (view != null) {
                        view.loadList(((DevList) response).getList());
                    }
                }
            }
        });
    }

    public void getDevTypeInfo() {
        NetRequest.getInstance().asynPostJson(NetRequest.IP + IDevManagementModel.URL_DEVTYPE, new HashMap<String, String>(), new CommonCallback(DevTypeListInfo.class) {
            @Override
            public void onError(Call call, Exception e, int id) {
                super.onError(call,e,id);
                // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0004_F01_Android_S02  敏感信息检查-日志中不能包含敏感信息    【修改人】：zhaoyufeng
                if(e.toString().contains("java.net.ConnectException")){
                    ToastUtil.showMessage(MyApplication.getContext().getString(R.string.net_error));
                }
            }

            @Override
            public void onResponse(BaseEntity response, int id) {
                if (response instanceof DevTypeListInfo) {
                    if (view != null) {
                        view.loadDevInfo((DevTypeListInfo) response);
                    }
                }
            }
        });
    }

}
