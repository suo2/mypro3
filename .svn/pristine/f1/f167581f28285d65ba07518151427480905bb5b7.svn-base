package com.huawei.solarsafe.presenter.maintaince.onlinediagnosis;

import android.util.Log;

import com.huawei.solarsafe.MyApplication;
import com.huawei.solarsafe.R;
import com.huawei.solarsafe.bean.BaseEntity;
import com.huawei.solarsafe.bean.device.DispersionListInfo;
import com.huawei.solarsafe.bean.device.DispersionStatisticsInfo;
import com.huawei.solarsafe.model.maintain.onlinediagnosis.OnlineDiagnosisModel;
import com.huawei.solarsafe.net.CommonCallback;
import com.huawei.solarsafe.presenter.BasePresenter;
import com.huawei.solarsafe.utils.ToastUtil;
import com.huawei.solarsafe.view.maintaince.main.IOnlineDiagnosisView;

import java.util.Map;

import okhttp3.Call;

/**
 * Created by P00229 on 2017/2/21.
 */
public class OnlineDiagnosisPresenter extends BasePresenter<IOnlineDiagnosisView, OnlineDiagnosisModel> {
    public static final String TAG = OnlineDiagnosisPresenter.class.getSimpleName();

    public OnlineDiagnosisPresenter() {
        setModel(new OnlineDiagnosisModel());
    }

    public void doRequestDispersion(Map<String, String> params) {
        // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0001_F03_Android_S01  删除Android的demo账号    【修改人】：zhaoyufeng
        model.requestDispersion(params, new CommonCallback(DispersionListInfo.class) {
            @Override
            public void onError(Call call, Exception e, int id) {
                super.onError(call,e,id);
                Log.e(TAG, "request DispersionListInfo failed " + e);
                view.getData(null);
                getErrorInfo(e);
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

    public void doRequestStatDispersion(Map<String, String> params) {
        // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0001_F03_Android_S01  删除Android的demo账号    【修改人】：zhaoyufeng
        model.requestStatDispersion(params, new CommonCallback(DispersionStatisticsInfo.class) {
            @Override
            public void onError(Call call, Exception e, int id) {
                super.onError(call,e,id);
                Log.e(TAG, "request DispersionStatisticsInfo failed " + e);
                if (view != null) {
                    view.getData(null);
                }
                getErrorInfo(e);
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

    public void doRequestDCDispersion(Map<String, String> params) {
        // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0001_F03_Android_S01  删除Android的demo账号    【修改人】：zhaoyufeng
        view.showDialog();
        model.requestDCDispersion(params, new CommonCallback(DispersionListInfo.class) {
            @Override
            public void onError(Call call, Exception e, int id) {
                super.onError(call,e,id);
                Log.e(TAG, "request DispersionListInfo failed " + e);
                view.getData(null);
                getErrorInfo(e);
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

    public void doRequestDCStatDispersion(Map<String, String> params) {
        // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0001_F03_Android_S01  删除Android的demo账号    【修改人】：zhaoyufeng
        view.showDialog();
        model.requestDCStatDispersion(params, new CommonCallback(DispersionStatisticsInfo.class) {
            @Override
            public void onError(Call call, Exception e, int id) {
                super.onError(call,e,id);
                Log.e(TAG, "request DispersionStatisticsInfo failed " + e);
                view.dismissDialog();
                getErrorInfo(e);
            }

            @Override
            public void onResponse(BaseEntity response, int id) {
                if (response == null) {
                    view.dismissDialog();
                    return;
                }
                view.getData(response);
                view.dismissDialog();
            }
        });
    }

    /**
     * 处理异常信息
     *
     * @param e
     */
    private void getErrorInfo(Exception e) {
        if(e.toString().contains("java.net.ConnectException")){
            ToastUtil.showMessage(MyApplication.getContext().getString(R.string.net_error));
        }
    }
}

