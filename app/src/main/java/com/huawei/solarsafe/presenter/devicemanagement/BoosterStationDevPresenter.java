package com.huawei.solarsafe.presenter.devicemanagement;

import android.util.Log;

import com.google.gson.Gson;
import com.huawei.solarsafe.MyApplication;
import com.huawei.solarsafe.R;
import com.huawei.solarsafe.bean.BaseEntity;
import com.huawei.solarsafe.bean.device.BoosterDevListInfo;
import com.huawei.solarsafe.bean.device.BoosterDevRealTimeBean;
import com.huawei.solarsafe.bean.device.BoosterDevTypeListInfo;
import com.huawei.solarsafe.bean.device.DevAlarmBean;
import com.huawei.solarsafe.bean.device.DevDetailBean;
import com.huawei.solarsafe.bean.stationmagagement.StationManagementListInfo;
import com.huawei.solarsafe.bean.stationmagagement.StationManegementList;
import com.huawei.solarsafe.model.devicemanagement.BoosterStationDevModel;
import com.huawei.solarsafe.net.CommonCallback;
import com.huawei.solarsafe.net.StringCallback;
import com.huawei.solarsafe.presenter.BasePresenter;
import com.huawei.solarsafe.utils.ToastUtil;
import com.huawei.solarsafe.view.devicemanagement.IBoosterStationDevView;

import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;

/**
 * Created by P00708 on 2018/3/1.
 */

/**
 * 升压设备相关界面控制器
 */
public class BoosterStationDevPresenter extends BasePresenter<IBoosterStationDevView, BoosterStationDevModel> {


    public BoosterStationDevPresenter() {
        setModel(new BoosterStationDevModel());
    }

    public void reauestBoosterList(Map<String, String> params) {

        model.requestBoosterList(params, new CommonCallback(BoosterDevListInfo.class) {
            @Override
            public void onError(Call call, Exception e, int id) {
                super.onError(call, e, id);
                if (view != null) {
                    view.getData(null);
                }
                if (!e.getMessage().contains("403")){
                    ToastUtil.showMessage(MyApplication.getContext().getString(R.string.net_error));
                }
            }
            @Override
            public void onResponse(BaseEntity response, int id) {
                if (view != null) {
                    view.getData(response);
                }
            }
        });
    }

    public void requestStationList(String param, int page, int pageSize) {
        Map<String, String> params = new HashMap<>();
        params.put("stationName", param);
        params.put("page", "" + page);
        params.put("pageSize", "" + pageSize);
        model.requestStationList(params, new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                super.onError(call, e, id);
                ToastUtil.showMessage(MyApplication.getContext().getString(R.string.net_error));
                if (view != null) {
                    view.getData(null);
                }
            }

            @Override
            public void onResponse(Object response, int id) {
                try {
                    Gson gson = new Gson();
                    StationManegementList stationManegementList = gson.fromJson(response.toString(), StationManegementList.class);
                    StationManagementListInfo stationManagementListInfo = new StationManagementListInfo();
                    if (view != null && stationManegementList.isSuccess()) {
                        stationManagementListInfo.setStationManegementList(stationManegementList);
                        view.getData(stationManagementListInfo);
                    } else {
                        if (view != null) {
                            view.getData(stationManagementListInfo);
                        }
                    }
                } catch (Exception e) {
                    if (view != null) {
                        StationManagementListInfo stationManagementListInfo = new StationManagementListInfo();
                        view.getData(stationManagementListInfo);
                    }
                    Log.e("JsonSyntaxException", e.toString());
                }
            }
        });
    }

    public void requestBoosterDeviceTypeList() {
        HashMap<String, String> params = new HashMap<>();
        params.put(" ", " ");
        model.requestBoosterDeviceTypeList(params, new CommonCallback(BoosterDevTypeListInfo.class) {
            @Override
            public void onError(Call call, Exception e, int id) {
                super.onError(call, e, id);
                Log.e(TAG, "request ListDevInfo failed " + e);
                if (view != null) {
                    view.getData(null);
                }
            }

            @Override
            public void onResponse(BaseEntity response, int id) {
                if (view != null) {
                    view.getData(response);
                }
            }
        });
    }

    /**
     * 请求设备告警信息
     *
     * @param params
     */
    public void requestDevAlarm(Map<String, String> params) {
        model.requestDevAlarmData(params, new CommonCallback(DevAlarmBean.class) {
            @Override
            public void onError(Call call, Exception e, int id) {
                super.onError(call, e, id);
                Log.e(TAG, "request DevAlarm failed " + e);
                if (view != null) {
                    view.getData(null);
                }
                ToastUtil.showMessage(MyApplication.getContext().getString(R.string.net_error));
            }

            @Override
            public void onResponse(BaseEntity response, int id) {
                if (response == null) {
                    view.getData(null);
                    return;
                }
                view.getData(response);
            }
        });
    }

    /**
     * 发送查询设备信息请求
     *
     * @param devId 设备id
     */
    public void doRequestDevDetail(String devId) {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("devId", devId);

        model.requestDevDetail(hashMap, new CommonCallback(DevDetailBean.class) {
            @Override
            public void onResponse(BaseEntity response, int id) {
                if (view!=null){
                    if (response instanceof DevDetailBean) {
                        DevDetailBean devDetailBean = (DevDetailBean) response;
                        if (devDetailBean.getDevDetailInfo() != null) {
                            view.getData(devDetailBean);
                            return;
                        }
                    }
                    view.getData(null);
                }
            }

            @Override
            public void onError(Call call, Exception e, int id) {
                super.onError(call, e, id);
                ToastUtil.showMessage(MyApplication.getContext().getString(R.string.net_error));
                if (view!=null)
                view.getData(null);
            }
        });
    }

    public void requestDevRealTimeData(String devId) {

        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("devId", devId);
        model.requestDevRealTimeData(hashMap, new CommonCallback(BoosterDevRealTimeBean.class) {

            @Override
            public void onError(Call call, Exception e, int id) {
                super.onError(call, e, id);
                ToastUtil.showMessage(MyApplication.getContext().getString(R.string.net_error));
                if (view!=null)
                view.getData(null);
            }


            @Override
            public void onResponse(BaseEntity response, int id) {
                if (view!=null){
                    view.getData(response);
                }
            }
        });


    }

}
