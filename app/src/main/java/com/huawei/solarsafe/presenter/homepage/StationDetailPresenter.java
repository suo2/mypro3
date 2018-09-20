package com.huawei.solarsafe.presenter.homepage;

import android.util.Log;

import com.google.gson.Gson;
import com.huawei.solarsafe.MyApplication;
import com.huawei.solarsafe.R;
import com.huawei.solarsafe.bean.BaseEntity;
import com.huawei.solarsafe.bean.common.LogCallBack;
import com.huawei.solarsafe.bean.common.ResultBean;
import com.huawei.solarsafe.bean.device.DevDetailBean;
import com.huawei.solarsafe.bean.station.kpi.ConfigDevsDataBean;
import com.huawei.solarsafe.bean.station.kpi.StationInfo;
import com.huawei.solarsafe.bean.station.kpi.StationPowerCountInfo;
import com.huawei.solarsafe.bean.station.kpi.StationRealKpiInfo;
import com.huawei.solarsafe.bean.station.kpi.StationStateInfo;
import com.huawei.solarsafe.bean.station.kpi.StationViewConfiguraBean;
import com.huawei.solarsafe.bean.station.kpi.StationWeatherInfo;
import com.huawei.solarsafe.model.homepage.StationSingleModel;
import com.huawei.solarsafe.net.CommonCallback;
import com.huawei.solarsafe.presenter.BasePresenter;
import com.huawei.solarsafe.utils.ToastUtil;
import com.huawei.solarsafe.view.homepage.station.IStationView;

import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;

/**
 * Created by p00322 on 2017/1/4.
 */
public class StationDetailPresenter extends BasePresenter<IStationView, StationSingleModel> {
    public static final String TAG = StationDetailPresenter.class.getSimpleName();

    public StationDetailPresenter() {
        setModel(new StationSingleModel());
    }

    public void doRequestSingleStation(Map<String, String> params) {
        // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0001_F03_Android_S01  删除Android的demo账号    【修改人】：zhaoyufeng
        model.requestSingleStation(params, new CommonCallback(StationInfo.class) {
            @Override
            public void onError(Call call, Exception e, int id) {
                super.onError(call,e,id);
                // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0004_F01_Android_S02  敏感信息检查-日志中不能包含敏感信息    【修改人】：zhaoyufeng
                Log.e(TAG, "request StationList failed! " + e);
                if (view != null) {
                    view.getData(null);
                }
                if (e.toString().contains("java.net.ConnectException")) {
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

    public void doRequestSingleRealKpi(Map<String, String> params) {
        // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0001_F03_Android_S01  删除Android的demo账号    【修改人】：zhaoyufeng
        model.requestSingleRealKpi(params, new CommonCallback(StationRealKpiInfo.class) {
            @Override
            public void onError(Call call, Exception e, int id) {
                super.onError(call,e,id);
                // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0004_F01_Android_S02  敏感信息检查-日志中不能包含敏感信息    【修改人】：zhaoyufeng
                Log.e(TAG, "request StationRealKpiInfo failed! " + e);
                if (view != null) {
                    view.getData(null);
                }
                if (e.toString().contains("java.net.ConnectException")) {
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

    public void doRequestStationWeather(Map<String, String> params) {
        // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0001_F03_Android_S01  删除Android的demo账号    【修改人】：zhaoyufeng
        model.requestStationWeather(params, new CommonCallback(StationWeatherInfo.class) {
            @Override
            public void onError(Call call, Exception e, int id) {
                super.onError(call,e,id);
                // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0004_F01_Android_S02  敏感信息检查-日志中不能包含敏感信息    【修改人】：zhaoyufeng
                Log.e(TAG, "request StationWeatherInfo failed! " + e);
                if (view != null) {
                    view.getData(null);
                }
                if (e.toString().contains("java.net.ConnectException")) {
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

    public void doRequestHeathyState(Map<String, String> params) {
        // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0001_F03_Android_S01  删除Android的demo账号    【修改人】：zhaoyufeng
        model.requestHeathyState(params, new CommonCallback(StationStateInfo.class) {
            @Override
            public void onError(Call call, Exception e, int id) {
				super.onError(call,e,id);
                // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0004_F01_Android_S02  敏感信息检查-日志中不能包含敏感信息    【修改人】：zhaoyufeng
                Log.e(TAG, "request StationStateInfo failed! " + e);
                if (view != null) {
                    view.getData(null);
                }
                if (e.toString().contains("java.net.ConnectException")) {
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

    public void doRequestPowerCount(Map<String, String> params) {
        // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0001_F03_Android_S01  删除Android的demo账号    【修改人】：zhaoyufeng
        model.requestPowerCount(params, new CommonCallback(StationPowerCountInfo.class) {
            @Override
            public void onError(Call call, Exception e, int id) {
				super.onError(call,e,id);
                // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0004_F01_Android_S02  敏感信息检查-日志中不能包含敏感信息    【修改人】：zhaoyufeng
                Log.e(TAG, "request StationPowerCountInfo failed! " + e);
                if (view != null) {
                    view.getData(null);
                }
                if (e.toString().contains("java.net.ConnectException")) {
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

    /**
     * 发送获取电站视图请求
     * @param stationCode 电站id
     */
    public void doRequestConfigura(String stationCode,String type){
        Map<String,String> params=new HashMap<>();
        params.put("stationCode",stationCode);
        params.put("type",type);
        model.requestConfigura(params,new CommonCallback(StationViewConfiguraBean.class){

            @Override
            public void onResponse(BaseEntity baseEntity, int i) {
                if (view!=null){
                    if (baseEntity instanceof StationViewConfiguraBean){
                        StationViewConfiguraBean stationViewConfiguraBean=((StationViewConfiguraBean) baseEntity).getStationViewConfiguraBean();
                        view.getData(stationViewConfiguraBean);
                    }else{
                        view.getData(null);
                    }
                }
            }

            @Override
            public void onError(Call call, Exception e, int id) {
                super.onError(call, e, id);
                if (view!=null){
                    view.getData(null);
                    if(e.getMessage() != null && e.getMessage().contains("403")){
                        return;
                    }
                    ToastUtil.showMessage(MyApplication.getContext().getString(R.string.net_error));
                }
            }
        });

    }

    /**
     * 发送获取电站视图设备实时状态请求
     * @param devEsns
     * @param dateType
     */
    public void doRequestConfigDevsData(String devEsns,String dateType,String stationCode){
        HashMap<String,String> hashMap=new HashMap<>();
        hashMap.put("devEsns",devEsns);
        hashMap.put("dateType",dateType);
        hashMap.put("stationCode",stationCode);

        model.requestConfigDevsData(hashMap, new CommonCallback(ConfigDevsDataBean.class) {
            @Override
            public void onResponse(BaseEntity baseEntity, int i) {
                if (view!=null){
                    if (baseEntity instanceof ConfigDevsDataBean){
                        view.getData(baseEntity);
                    }else{
                        view.getData(null);
                    }
                }
            }

            @Override
            public void onError(Call call, Exception e, int id) {
                super.onError(call, e, id);
                ToastUtil.showMessage(MyApplication.getContext().getString(R.string.net_error));
                if (view!=null){
                    view.getData(null);
                }
            }
        });
    }

    /**
     * 发送查询设备信息请求
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

    /**
     * 发送检查设备Model版本请求
     * @param devId 设备ID
     * @param stationCode 电站ID
     */
    public void doRequestCheckModelVersion(String devId,String stationCode){
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("devId", devId);
        hashMap.put("sId",stationCode);

        model.requestCheckModelVersion(hashMap, new LogCallBack() {
            @Override
            protected void onFailed(Exception e) {
                ToastUtil.showMessage(MyApplication.getContext().getString(R.string.net_error));
                if (view!=null)
                    view.getData(null);
            }

            @Override
            protected void onSuccess(String data) {
                if (view!=null){
                    ResultBean resultBean=new Gson().fromJson(data,ResultBean.class);
                    if (resultBean!=null){
                        view.getData(resultBean);
                    }else{
                        view.getData(null);
                    }
                }
            }
        });
    }
}
