package com.huawei.solarsafe.presenter.report;

import android.util.Log;

import com.huawei.solarsafe.MyApplication;
import com.huawei.solarsafe.R;
import com.huawei.solarsafe.bean.BaseEntity;
import com.huawei.solarsafe.bean.report.InverterReportKpi;
import com.huawei.solarsafe.bean.report.PowerCurveKpi;
import com.huawei.solarsafe.bean.report.StationKpiChartList;
import com.huawei.solarsafe.bean.report.StationReportKpiList;
import com.huawei.solarsafe.model.report.ReportModel;
import com.huawei.solarsafe.net.CommonCallback;
import com.huawei.solarsafe.presenter.BasePresenter;
import com.huawei.solarsafe.utils.ToastUtil;
import com.huawei.solarsafe.view.report.IReportView;

import java.util.Map;

import okhttp3.Call;

/**
 * Created by p00322 on 2017/2/20.
 */
public class ReportPresenter extends BasePresenter<IReportView, ReportModel> implements IReportPresenter {

    public ReportPresenter() {
        setModel(new ReportModel());
    }

    @Override
    public void doRequestKpiList(Map<String, String> param) {
        // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0001_F03_Android_S01  删除Android的demo账号    【修改人】：zhaoyufeng
        model.requestKpiList(param, new CommonCallback(StationReportKpiList.class) {
            @Override
            public void onError(Call call, Exception e, int id) {
                super.onError(call,e,id);
                // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0004_F01_Android_S02  敏感信息检查-日志中不能包含敏感信息    【修改人】：zhaoyufeng
                if (e.toString().contains("java.net.ConnectException")) {
                    ToastUtil.showMessage(MyApplication.getContext().getString(R.string.net_error));
                } else {
                    ToastUtil.showMessage(e.getMessage());
                }
                if (view != null) {
                    view.resetData();
                }
            }

            @Override
            public void onResponse(BaseEntity response, int id) {
                if (response == null) {
                    view.resetData();
                    return;
                }
                if (view != null) {
                    view.getReportData(response);
                }
            }
        });
    }

    @Override
    public void doRequestKpiChart(Map<String, String> param) {
        // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0001_F03_Android_S01  删除Android的demo账号    【修改人】：zhaoyufeng
        model.requestKpiChart(param, new CommonCallback(StationKpiChartList.class) {
            @Override
            public void onError(Call call, Exception e, int id) {
                super.onError(call,e,id);
                // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0004_F01_Android_S02  敏感信息检查-日志中不能包含敏感信息    【修改人】：zhaoyufeng
                Log.e(TAG, "request StationKpiChartList failed 1" + e);
                if (e.toString().contains("java.net.ConnectException")) {
                    ToastUtil.showMessage(MyApplication.getContext().getString(R.string.net_error));
                } else {
                    ToastUtil.showMessage(e.getMessage());
                }
                if (view != null) {
                    view.resetData();
                }
            }

            @Override
            public void onResponse(BaseEntity response, int id) {
                if (response == null) {
                    if (view!=null)
                        view.resetData();
                    return;
                }
                if (view != null) {
                    view.getReportData(response);
                }
            }
        });
    }


    @Override
    public void doRequestInverterRporterData(Map<String, String> param) {
        // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0001_F03_Android_S01  删除Android的demo账号    【修改人】：zhaoyufeng
        model.requestInverterReporterData(param, new CommonCallback(InverterReportKpi.class) {
            @Override
            public void onError(Call call, Exception e, int id) {
                super.onError(call,e,id);
                // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0004_F01_Android_S02  敏感信息检查-日志中不能包含敏感信息    【修改人】：zhaoyufeng
                Log.e(TAG, "requestInverterReporterData failed " + e);
                if (e.toString().contains("java.net.ConnectException")) {
                    ToastUtil.showMessage(MyApplication.getContext().getString(R.string.net_error));
                } else {
                    ToastUtil.showMessage(e.toString());
                }
                if (view != null) {
                    view.resetData();
                }
            }

            @Override
            public void onResponse(BaseEntity response, int id) {
                if (view != null) {
                    view.getReportData(response);
                }
            }
        });
    }

    public void requestStationPowerCurve(Map<String, String> param) {

        model.requestStationPowerCurve(param,new CommonCallback(PowerCurveKpi.class){

            @Override
            public void onError(Call call, Exception e, int id) {
                super.onError(call,e,id);
                if (e.toString().contains("java.net.ConnectException")) {
                    ToastUtil.showMessage(MyApplication.getContext().getString(R.string.net_error));
                } else {
                    //该判断只是为了解单(禅道单58440)，最好是服务器端检查为啥返回该异常
                    if(!e.toString().contains("403")){
                        ToastUtil.showMessage(e.toString());
                    }
                }
                if (view != null) {
                    view.resetData();
                }
            }
            @Override
            public void onResponse(BaseEntity baseEntity, int i) {
                if (view != null) {
                    view.getReportData(baseEntity);
                }
            }
        });

    }

}
