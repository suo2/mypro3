package com.huawei.solarsafe.presenter.homepage;

import android.util.Log;

import com.huawei.solarsafe.MyApplication;
import com.huawei.solarsafe.R;
import com.huawei.solarsafe.bean.BaseEntity;
import com.huawei.solarsafe.bean.alarm.AlarmInfoLevel;
import com.huawei.solarsafe.bean.station.EquivalentHourList;
import com.huawei.solarsafe.bean.station.PoorCompleteInfo;
import com.huawei.solarsafe.bean.station.StationBuildCountInfo;
import com.huawei.solarsafe.bean.station.kpi.SocialContributionInfo;
import com.huawei.solarsafe.bean.station.kpi.StationRealKpiInfo;
import com.huawei.solarsafe.bean.station.kpi.StationStatusAllInfo;
import com.huawei.solarsafe.model.homepage.StationReq;
import com.huawei.solarsafe.net.CommonCallback;
import com.huawei.solarsafe.presenter.BasePresenter;
import com.huawei.solarsafe.utils.ToastUtil;
import com.huawei.solarsafe.view.homepage.station.IStationView;

import java.util.Map;

import okhttp3.Call;


/**
 * Created by p00229 on 2017/1/4.
 */
public class StationHomePresenter extends BasePresenter<IStationView, StationReq> implements IStationHomePresenter {

    public StationHomePresenter() {
        setModel(new StationReq());
    }

    @Override
    public void doRequestRealKpi(Map<String, String> param) {
        // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0001_F03_Android_S01  删除Android的demo账号    【修改人】：zhaoyufeng
        model.requestRealKpi(param, new CommonCallback(StationRealKpiInfo.class) {
            @Override
            public void onError(Call call, Exception e, int id) {
                super.onError(call, e, id);
                Log.e(TAG, "request StationRealKpiInfo failed " + e);
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

    @Override
    public void doRequestBuildCount(Map<String, String> param) {
        // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0001_F03_Android_S01  删除Android的demo账号    【修改人】：zhaoyufeng
        model.requestBulidCount(param, new CommonCallback(StationBuildCountInfo.class) {
            @Override
            public void onError(Call call, Exception e, int id) {
                super.onError(call, e, id);
                // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0004_F01_Android_S02  敏感信息检查-日志中不能包含敏感信息    【修改人】：zhaoyufeng
                Log.e(TAG, "request StationBuildCountInfo failed " + e);
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

    @Override
    public void doRequestEquivalentHour(Map<String, String> param) {
        // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0001_F03_Android_S01  删除Android的demo账号    【修改人】：zhaoyufeng
        model.requestEquivalentHour(param, new CommonCallback(EquivalentHourList.class) {
            @Override
            public void onError(Call call, Exception e, int id) {
                super.onError(call, e, id);
                // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0004_F01_Android_S02  敏感信息检查-日志中不能包含敏感信息    【修改人】：zhaoyufeng
                Log.e(TAG, "request EquivalentHour failed " + e);
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

    @Override
    public void doRequestPoorComplete(Map<String, String> param) {
        // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0001_F03_Android_S01  删除Android的demo账号    【修改人】：zhaoyufeng
        model.requestPoorComplete(param, new CommonCallback(PoorCompleteInfo.class) {
            @Override
            public void onError(Call call, Exception e, int id) {
                super.onError(call, e, id);
                Log.e(TAG, "request PoorCompleteInfo failed " + e);
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

    @Override
    public void doRequestContrDuceKpi(Map<String, String> param) {
        // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0001_F03_Android_S01  删除Android的demo账号    【修改人】：zhaoyufeng
        model.requestSocialContribution(param, new CommonCallback(SocialContributionInfo.class) {
            @Override
            public void onError(Call call, Exception e, int id) {
                super.onError(call, e, id);
                Log.e(TAG, "request SocialContributionInfo failed " + e);
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

    @Override
    public void doRequestRealTimeAlarmKpi(Map<String, String> param) {
        // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0001_F03_Android_S01  删除Android的demo账号    【修改人】：zhaoyufeng
        model.requestRealTimeAlarmKpi(param, new CommonCallback(AlarmInfoLevel.class) {
            @Override
            public void onError(Call call, Exception e, int id) {
                super.onError(call, e, id);
                Log.e(TAG, "request AlarmInfoLevel failed " + e);
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

    @Override
    public void doRequestStationStatusAll(Map<String, String> param) {
        // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0001_F03_Android_S01  删除Android的demo账号    【修改人】：zhaoyufeng
        model.requestStationStatusAll(param, new CommonCallback(StationStatusAllInfo.class) {
            @Override
            public void onError(Call call, Exception e, int id) {
                super.onError(call, e, id);
                Log.e(TAG, "request StationStatusAllInfo failed " + e);
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
}
