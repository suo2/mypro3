package com.huawei.solarsafe.presenter.pnlogger;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.huawei.solarsafe.bean.common.LogCallBack;
import com.huawei.solarsafe.bean.common.RetMsg;
import com.huawei.solarsafe.bean.pnlogger.SignPointInfo;
import com.huawei.solarsafe.logger104.database.PntConnectDao;
import com.huawei.solarsafe.logger104.database.PntDatabase;
import com.huawei.solarsafe.model.pnlogger.BuildStationMode;
import com.huawei.solarsafe.model.pnlogger.StationOperator;
import com.huawei.solarsafe.presenter.BasePresenter;
import com.huawei.solarsafe.utils.LocalData;
import com.huawei.solarsafe.view.pnlogger.IBuildStationView;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Create Date: 2017/2/28
 * Create Author: P00171
 * Description :
 */
public class BuildStationPresenter extends BasePresenter<IBuildStationView, BuildStationMode> implements IBuildStationPresenter {

    public BuildStationPresenter() {
        setModel(new BuildStationMode());
    }


    @Override
    public void doShowUnconnectNum() {
        // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0001_F03_Android_S01  删除Android的demo账号    【修改人】：zhaoyufeng
        PntConnectDao dao = PntConnectDao.getInstance();
        int num = dao.queryByStatus(StationOperator.NO_JOIN, LocalData.getInstance().getIp()+"::" + LocalData.getInstance().getLoginName());
        if (view != null) {
            view.showUnconnetedUnm(num);
        }
    }

    @Override
    public void doGetSecondDeviceType() {
        model.getSecondDeviceType(new LogCallBack() {

            @Override
            protected void onFailed(Exception e) {
            }

            @Override
            protected void onSuccess(String data) {
                try {
                    Gson gson = new Gson();
                    // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0004_F01_Android_S02  敏感信息检查-日志中不能包含敏感信息    【修改人】：zhaoyufeng
                    Type type = new TypeToken<RetMsg<List<SignPointInfo>>>() {
                    }.getType();
                    RetMsg<List<SignPointInfo>> retMsg = gson.fromJson(data, type);
                    if (retMsg.isSuccess() && retMsg.getData() != null && retMsg.getFailCode() == 0) {
                        List<SignPointInfo> signPointInfos = retMsg.getData();
                        //清除
                        PntDatabase.getInstance().clearSignPointInfo();
                        PntDatabase.getInstance().addSignPointInfo(signPointInfos);
                        //回调操作成功
                        if (view != null) {
                            view.getSecondDevSuccess();
                        }
                    } else if (!retMsg.isSuccess() || retMsg.getData() == null) {
                        if (view != null) {
                            view.getSecondDevFailed(retMsg.getFailCode());
                        }
                    }
                }catch (Exception e){
                    Log.e("error",e.toString());
                }
            }
        });
    }
}
