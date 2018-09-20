package com.huawei.solarsafe.presenter.maintaince.patrol;

import android.util.Log;

import com.huawei.solarsafe.MyApplication;
import com.huawei.solarsafe.R;
import com.huawei.solarsafe.bean.BaseEntity;
import com.huawei.solarsafe.bean.patrol.PatrolInspectObjList;
import com.huawei.solarsafe.bean.patrol.PatrolInspectTaskList;
import com.huawei.solarsafe.model.maintain.patrol.PatrolMgrModel;
import com.huawei.solarsafe.net.CommonCallback;
import com.huawei.solarsafe.presenter.BasePresenter;
import com.huawei.solarsafe.utils.LocalData;
import com.huawei.solarsafe.utils.ToastUtil;
import com.huawei.solarsafe.utils.Utils;
import com.huawei.solarsafe.view.maintaince.patrol.IPatrolMrgView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import okhttp3.Call;

/**
 * Create Date: 2017/3/2
 * Create Author: p00213
 * Description : IPatrolMgrPresenter的实现类
 */
public class PatrolMgrPresenter extends BasePresenter<IPatrolMrgView, PatrolMgrModel> implements IPatrolMgrPresenter {

    private List<String> rightsList = new ArrayList<>();

    public void setModel() {
        super.setModel(new PatrolMgrModel());
        String strRights = LocalData.getInstance().getRightString();
        rightsList = Utils.stringToList(strRights);
    }

    @Override
    public void requestInspectObj(Map<String, String> params) {

        if (rightsList.contains("app_mobileInspect") || rightsList.contains("app_defectManagement")){
            // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0001_F03_Android_S01  删除Android的demo账号    【修改人】：zhaoyufeng
            model.requestInspectTaskObj(params, new CommonCallback(PatrolInspectObjList.class) {
                @Override
                public void onError(Call call, Exception e, int id) {
                    super.onError(call,e,id);
                    // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0004_F01_Android_S02  敏感信息检查-日志中不能包含敏感信息    【修改人】：zhaoyufeng
                    Log.e(TAG, "request InspectObjList failed " + e);
                    if (e.toString().contains("java.net.ConnectException")) {
                        ToastUtil.showMessage(MyApplication.getContext().getString(R.string.net_error));
                    }
                }

                @Override
                public void onResponse(BaseEntity response, int id) {
                    if (response == null) {
                        return;
                    }
                    if (view != null)
                        view.getInspectObj(response);
                }
            });
        }

    }

    @Override
    public void requestInspectTask(Map<String, String> params) {

        if (rightsList.contains("app_mobileInspect")){
            // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0001_F03_Android_S01  删除Android的demo账号    【修改人】：zhaoyufeng
            model.requestInspectTaskList(params, new CommonCallback(PatrolInspectTaskList.class) {
                @Override
                public void onError(Call call, Exception e, int id) {
                    super.onError(call,e,id);
                    // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0004_F01_Android_S02  敏感信息检查-日志中不能包含敏感信息    【修改人】：zhaoyufeng
                    Log.e(TAG, "request InspectTaskList failed " + e);
                    if (e.toString().contains("java.net.ConnectException")) {
                        ToastUtil.showMessage(MyApplication.getContext().getString(R.string.net_error));
                    }
                }

                @Override
                public void onResponse(BaseEntity response, int id) {
                    if (response == null) {
                        return;
                    }
                    if (view != null)
                        view.getInspectTask(response);
                }
            });
        }
    }
}
