package com.huawei.solarsafe.presenter.maintaince.defect;

import android.support.annotation.Nullable;
import android.util.Log;

import com.huawei.solarsafe.bean.BaseEntity;
import com.huawei.solarsafe.bean.GlobalConstants;
import com.huawei.solarsafe.bean.defect.DefectList;
import com.huawei.solarsafe.bean.defect.DefectListReq;
import com.huawei.solarsafe.model.maintain.defect.DefectModel;
import com.huawei.solarsafe.net.CommonCallback;
import com.huawei.solarsafe.net.NetRequest;
import com.huawei.solarsafe.presenter.BasePresenter;
import com.huawei.solarsafe.view.maintaince.defects.IDefectListView;

import okhttp3.Call;

/**
 * Created by p00319 on 2017/2/16.
 */

public class DefectListPresenter extends BasePresenter<IDefectListView, DefectModel> implements IDefectListPresenter {

    public DefectListPresenter() {
        setModel(DefectModel.getInstance());
    }

    @Override
    public void requestList(int page, int pageSize, @Nullable String procState, @Nullable String dealResult,
                            @Nullable String sName, long startTimeS, long startTimeE) {
        DefectListReq req = new DefectListReq();
        req.setPage(String.valueOf(page));
        req.setPageSize(String.valueOf(pageSize));
        req.setProcState(procState);
        req.setDealResult(dealResult);
        req.setsName(sName);
        req.setStartTimeS(startTimeS == 0 ? "" : String.valueOf(startTimeS));
        req.setStartTimeE(startTimeE == 0 ? "" : String.valueOf(startTimeE));
        if (procState != null) {
            //【安全特性编号】I1 OR_SmartPVMS60_PVMS830_0004_F01 部分敏感信息APP不做长期存储 【修改人】zhaoyufeng
            req.setOwnerId(String.valueOf(GlobalConstants.userId));
        }

        if (model != null) {
            model.requestDefectList(req, new CommonCallback(DefectList.class) {
                @Override
                public void onError(Call call, Exception e, int id) {
                    super.onError(call,e,id);
                    // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0004_F01_Android_S02  敏感信息检查-日志中不能包含敏感信息    【修改人】：zhaoyufeng
                    Log.e(NetRequest.TAG, "Request Defect List Failed", e);
                    if (view != null) {
                        view.requestListFailed(NetRequest.NETERROR);
                    }
                }

                @Override
                public void onResponse(BaseEntity response, int id) {
                    if (response instanceof DefectList) {
                        if (((DefectList) response).getDfList() != null) {
                            if (view != null) {
                                view.requestListSuccess(((DefectList) response).getDfList());
                            }
                        }
                    }
                }
            });
        }
    }

}
