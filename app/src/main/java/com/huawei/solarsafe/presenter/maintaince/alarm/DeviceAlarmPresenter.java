package com.huawei.solarsafe.presenter.maintaince.alarm;

import android.util.Log;

import com.huawei.solarsafe.MyApplication;
import com.huawei.solarsafe.R;
import com.huawei.solarsafe.bean.BaseEntity;
import com.huawei.solarsafe.bean.ResultInfo;
import com.huawei.solarsafe.bean.alarm.CausesAndRepairSuggestions;
import com.huawei.solarsafe.bean.alarm.DeviceAlarmQueryList;
import com.huawei.solarsafe.bean.alarm.StationSourceBean;
import com.huawei.solarsafe.model.maintain.alarm.DeviceAlarmModel;
import com.huawei.solarsafe.net.CommonCallback;
import com.huawei.solarsafe.net.StringCallback;
import com.huawei.solarsafe.presenter.BasePresenter;
import com.huawei.solarsafe.utils.ToastUtil;
import com.huawei.solarsafe.view.maintaince.main.IDeviceAlarmView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

import okhttp3.Call;

/**
 * Created by P00229 on 2017/2/21.
 */
public class DeviceAlarmPresenter extends BasePresenter<IDeviceAlarmView, DeviceAlarmModel> {
    public static final String TAG = DeviceAlarmPresenter.class.getSimpleName();

    public DeviceAlarmPresenter() {
        setModel(new DeviceAlarmModel());
    }

    public void doRequestDevAlarmDetail(Map<String, String> params) {
        // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0001_F03_Android_S01  删除Android的demo账号    【修改人】：zhaoyufeng
        model.requestDevAlarmDetail(params, new CommonCallback(CausesAndRepairSuggestions.class) {
            @Override
            public void onError(Call call, Exception e, int id) {
                super.onError(call,e,id);
                if (view != null) {
                    view.getData(null);
                }
                if (e.toString().contains("java.net.ConnectException")) {
                    ToastUtil.showMessage(MyApplication.getContext().getString(R.string.net_error));
                }
                Log.e(TAG, "request CausesAndRepairSuggestions failed " + e);
            }

            @Override
            public void onResponse(BaseEntity response, int id) {
                if (view != null) {
                    view.getData(response);
                }
            }
        });
    }

    public void doRequestDevAlarmQuery(Map<String, String> params) {
        // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0001_F03_Android_S01  删除Android的demo账号    【修改人】：zhaoyufeng
        model.requestDevAlarmQuery(params, new CommonCallback(DeviceAlarmQueryList.class) {
            @Override
            public void onError(Call call, Exception e, int id) {
                super.onError(call,e,id);
                if (view != null) {
                    view.getData(null);
                }
                if (e.toString().contains("java.net.ConnectException")) {
                    ToastUtil.showMessage(MyApplication.getContext().getString(R.string.net_error));
                }
                Log.e(TAG, "request DeviceAlarmQueryList failed " + e);
            }

            @Override
            public void onResponse(BaseEntity response, int id) {
                if (view != null) {
                    view.getData(response);
                }
            }
        });
    }

    public void doRequesetDevAlarmConfir(String params) {
        // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0001_F03_Android_S01  删除Android的demo账号    【修改人】：zhaoyufeng
        model.requestDevAlarmComfirm(params, new CommonCallback(ResultInfo.class) {
            @Override
            public void onError(Call call, Exception e, int id) {
                super.onError(call,e,id);
                if (view != null) {
                    view.getData(null);
                }
                if (e.toString().contains("java.net.ConnectException")) {
                    ToastUtil.showMessage(MyApplication.getContext().getString(R.string.net_error));
                }
                Log.e(TAG, "request ResultInfo failed " + e);
            }

            @Override
            public void onResponse(BaseEntity response, int id) {
                if (view != null) {
                    view.getData(response);
                }
            }
        });
    }

    public void doRequesetDevAlarmClear(String params) {
        // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0001_F03_Android_S01  删除Android的demo账号    【修改人】：zhaoyufeng
        model.requestDevAlarmClear(params, new CommonCallback(ResultInfo.class) {
            @Override
            public void onError(Call call, Exception e, int id) {
                super.onError(call,e,id);
                if (view != null) {
                    view.getData(null);
                }
                if (e.toString().contains("java.net.ConnectException")) {
                    ToastUtil.showMessage(MyApplication.getContext().getString(R.string.net_error));
                }
                Log.e(TAG, "request ResultInfo failed " + e);
            }

            @Override
            public void onResponse(BaseEntity response, int id) {
                if (view != null) {
                    view.getData(response);
                }
            }
        });
    }

    public void doRequesetStationSource(Map<String, String> params, final String op) {
        // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0001_F03_Android_S01  删除Android的demo账号    【修改人】：zhaoyufeng
        model.toRequestStationSource(params, new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                super.onError(call, e, id);
                if (e.toString().contains("java.net.ConnectException")) {
                    ToastUtil.showMessage(MyApplication.getContext().getString(R.string.net_error));
                }
                if (view != null) {
                    view.getData(null);
                }
            }

            @Override
            public void onResponse(Object response, int id) {
               
                StationSourceBean stationBean = new StationSourceBean();
                try {
                    JSONObject js = new JSONObject(response.toString());
                    boolean success = js.getBoolean("success");
                    stationBean.setUserStation(success);
                    stationBean.setOprtion(op);
                    if (view != null) {
                        view.getData(stationBean);
                    }
                } catch (JSONException e) {
                    if (view != null) {
                        view.getData(null);
                    }
                    Log.e(TAG, "onResponse: "+e.getMessage() );
                }
            }
        });
    }
}
