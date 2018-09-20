package com.huawei.solarsafe.presenter.poverty;

import android.util.Log;

import com.huawei.solarsafe.MyApplication;
import com.huawei.solarsafe.R;
import com.huawei.solarsafe.bean.BaseEntity;
import com.huawei.solarsafe.bean.poverty.PoorList;
import com.huawei.solarsafe.model.poverty.PovertyModel;
import com.huawei.solarsafe.net.CommonCallback;
import com.huawei.solarsafe.presenter.BasePresenter;
import com.huawei.solarsafe.utils.ToastUtil;
import com.huawei.solarsafe.view.extra.IPovertyView;

import java.util.Map;

import okhttp3.Call;

/**
 * Created by P00229 on 2017/2/16.
 */
public class PovertyPresenter extends BasePresenter<IPovertyView, PovertyModel> implements IPovertyPresenter {
    public static final String TAG = PovertyPresenter.class.getSimpleName();

    public PovertyPresenter() {
        setModel(new PovertyModel());
    }

    @Override
    public void doRequestPovertyList(Map<String, String> param) {
        // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0001_F03_Android_S01  删除Android的demo账号    【修改人】：zhaoyufeng
        model.requestPovertyList(param, new CommonCallback(PoorList.class) {
            @Override
            public void onError(Call call, Exception e, int id) {
                super.onError(call,e,id);
                Log.e(TAG, "request PoorList failed");
                if (e.toString().contains("java.net.ConnectException")) {
                    ToastUtil.showMessage(MyApplication.getContext().getString(R.string.net_error));
                } else {
                    ToastUtil.showMessage(e.toString());
                }
                if (view != null) {
                    view.loadPovertyData(null);
                }
            }

            @Override
            public void onResponse(BaseEntity response, int id) {
                if (response == null) {
                    if (view != null) {
                        view.loadPovertyData(null);
                    }
                    return;
                }
                if (view != null) {
                    view.loadPovertyData((PoorList) response);
                }
            }
        });
    }
}
