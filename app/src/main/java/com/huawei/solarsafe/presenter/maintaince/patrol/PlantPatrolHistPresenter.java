package com.huawei.solarsafe.presenter.maintaince.patrol;

import android.util.Log;

import com.huawei.solarsafe.MyApplication;
import com.huawei.solarsafe.R;
import com.huawei.solarsafe.bean.BaseEntity;
import com.huawei.solarsafe.bean.patrol.PatrolInspectHistList;
import com.huawei.solarsafe.model.maintain.patrol.PlantPatrolHistModel;
import com.huawei.solarsafe.net.CommonCallback;
import com.huawei.solarsafe.presenter.BasePresenter;
import com.huawei.solarsafe.utils.ToastUtil;
import com.huawei.solarsafe.view.maintaince.patrol.IPlantPatrolHistView;

import java.util.Map;

import okhttp3.Call;

/**
 * Create Date: 2017/3/2
 * Create Author: p00213
 * Description :
 */
public class PlantPatrolHistPresenter extends BasePresenter<IPlantPatrolHistView, PlantPatrolHistModel> implements IPlantPatrolHistPresenter {

    public void setModel() {
        super.setModel(new PlantPatrolHistModel());
    }

    @Override
    public void doRequestPlantPatrolHist(Map<String, String> param) {
        // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0001_F03_Android_S01  删除Android的demo账号    【修改人】：zhaoyufeng
        model.requestPlantPatrolHistroy(param, new CommonCallback(PatrolInspectHistList.class) {
            @Override
            public void onError(Call call, Exception e, int id) {
                super.onError(call,e,id);
                // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0004_F01_Android_S02  敏感信息检查-日志中不能包含敏感信息    【修改人】：zhaoyufeng
                Log.e(TAG, "request InspectTaskList failed " + e);
                if (e.toString().contains("java.net.ConnectException")) {
                    ToastUtil.showMessage(MyApplication.getContext().getString(R.string.net_error));
                }
                if (view != null) {
                    view.getData(null);
                }
            }

            @Override
            public void onResponse(BaseEntity response, int id) {
                if (view != null)
                    view.getData(response);
            }
        });
    }
}
