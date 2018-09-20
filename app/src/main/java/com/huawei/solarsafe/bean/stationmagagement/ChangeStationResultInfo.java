package com.huawei.solarsafe.bean.stationmagagement;

import com.huawei.solarsafe.bean.BaseEntity;
import com.huawei.solarsafe.bean.ServerRet;

import org.json.JSONObject;

/**
 * Created by p00229 on 2017/6/6.
 */

public class ChangeStationResultInfo extends BaseEntity {
    private ChangeStationResultBean changeStationResultBean;

    public ChangeStationResultBean getChangeStationResultBean() {
        return changeStationResultBean;
    }

    public void setChangeStationResultBean(ChangeStationResultBean changeStationResultBean) {
        this.changeStationResultBean = changeStationResultBean;
    }

    @Override
    public boolean parseJson(JSONObject jsonObject) throws Exception {
        return false;
    }
    // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0001_F03_Android_S01  删除Android的demo账号    【修改人】：zhaoyufeng

    @Override
    public void setServerRet(ServerRet serverRet) {

    }
}
