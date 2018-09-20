package com.huawei.solarsafe.bean.report;

import com.huawei.solarsafe.bean.BaseEntity;
import com.huawei.solarsafe.bean.ServerRet;

import org.json.JSONObject;

import java.io.Serializable;
import java.util.List;

/**
 * Created by p00507
 * on 2017/5/27.
 */
public class RmListUserDevice  extends BaseEntity implements Serializable {
    private static final long serialVersionUID = -6772553146884819998L;
    private List<RmUserDevice> rmUserDevices;
    //统一定义的返回码
    ServerRet mRetCode;
    @Override
    public boolean parseJson(JSONObject jsonObject) throws Exception {
        return true;
    }
    // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0001_F03_Android_S01  删除Android的demo账号    【修改人】：zhaoyufeng

    @Override
    public void setServerRet(ServerRet serverRet) {
            mRetCode = serverRet;
    }

    public List<RmUserDevice> getRmUserDevices() {
        return rmUserDevices;
    }

    public void setRmUserDevices(List<RmUserDevice> rmUserDevices) {
        this.rmUserDevices = rmUserDevices;
    }
}
