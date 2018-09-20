package com.huawei.solarsafe.presenter.maintaince.stationstate;

import android.util.Log;

import com.huawei.solarsafe.MyApplication;
import com.huawei.solarsafe.R;
import com.huawei.solarsafe.bean.BaseEntity;
import com.huawei.solarsafe.bean.station.PerMwPowerChartCompareInfo;
import com.huawei.solarsafe.bean.station.PerPowerRatioChartCompareInfo;
import com.huawei.solarsafe.bean.station.StationStateListInfo;
import com.huawei.solarsafe.model.maintain.stationstate.StationStateModel;
import com.huawei.solarsafe.net.CommonCallback;
import com.huawei.solarsafe.presenter.BasePresenter;
import com.huawei.solarsafe.utils.ToastUtil;
import com.huawei.solarsafe.view.maintaince.main.IStationStateView;

import java.util.Map;

import okhttp3.Call;

/**
 * 电站状态Persenter
 * Created by P00229 on 2017/2/16.
 */
public class StationStatePresenter extends BasePresenter<IStationStateView, StationStateModel> implements IStationStatePresenter {

    public static final String TAG = StationStatePresenter.class.getSimpleName();

    public StationStatePresenter() {
        setModel(new StationStateModel());
    }

    @Override
    public void doRequestStationStateList(Map<String, String> params) {
        // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0001_F03_Android_S01  删除Android的demo账号    【修改人】：zhaoyufeng
        model.requestStationStateList(params, new CommonCallback(StationStateListInfo.class) {
            @Override
            public void onError(Call call, Exception e, int id) {
                super.onError(call,e,id);
                if (e.toString().contains("java.net.ConnectException")) {
                    ToastUtil.showMessage(MyApplication.getContext().getString(R.string.net_error));
                }
                if (view != null) {
                    view.getData(null);
                }
                Log.e(TAG, "request StationStateListInfo failed " + e);
            }

            @Override
            public void onResponse(BaseEntity response, int id) {
                if (response == null) {
                    if (view != null) {
                        view.getData(null);
                    }
                }
                view.getData(response);
            }
        });
    }


    @Override
    public void doRequestPerMwPowerChart(Map<String, String> params) {
        // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0001_F03_Android_S01  删除Android的demo账号    【修改人】：zhaoyufeng
        model.requestPerMwPowerChart(params, new CommonCallback(PerMwPowerChartCompareInfo.class) {
            @Override
            public void onError(Call call, Exception e, int id) {
                super.onError(call,e,id);
                Log.e(TAG, "request PerMwPowerChartCompareInfo failed " + e);
                if (e.toString().contains("java.net.ConnectException")) {
                    ToastUtil.showMessage(MyApplication.getContext().getString(R.string.net_error));
                }
                if (view != null) {
                    view.getData(null);
                }
            }

            @Override
            public void onResponse(BaseEntity response, int id) {
                if (response == null) {
                    if (view != null) {
                        view.getData(null);
                    }
                }
                view.getData(response);
            }
        });
    }

    @Override
    public void doRequestPerPowerRatioChart(Map<String, String> params) {
        // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0001_F03_Android_S01  删除Android的demo账号    【修改人】：zhaoyufeng
        model.requestPerPowerRatioChart(params, new CommonCallback(PerPowerRatioChartCompareInfo.class) {
            @Override
            public void onError(Call call, Exception e, int id) {
                super.onError(call,e,id);
                Log.e(TAG, "request StationCompareCurPowerInfo failed " + e);
                if (e.toString().contains("java.net.ConnectException")) {
                    ToastUtil.showMessage(MyApplication.getContext().getString(R.string.net_error));
                }
                if (view != null) {
                    view.getData(null);
                }
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

