package com.huawei.solarsafe.bean.station.kpi;

import com.huawei.solarsafe.bean.BaseEntity;
import com.huawei.solarsafe.bean.ServerRet;
import com.huawei.solarsafe.utils.JSONReader;

import org.json.JSONObject;

/**
 * Created by P00229 on 2017/2/17.
 */
public class StationStateInfo extends BaseEntity {
    public static final String TAG = StationStateInfo.class.getSimpleName();
    public static final String KEY_STATUS = "status";
    /**
     * 电站状态（
     * 1：连接中断，2：故障，
     * 3：健康）
     */
    private int status;
    private ServerRet serverRet;

    public ServerRet getServerRet() {
        return serverRet;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public boolean parseJson(JSONObject jsonObject) throws Exception {
        if (jsonObject == null) {
            return false;
        }
        JSONReader jsonReader = new JSONReader(jsonObject);
        status = jsonReader.getInt(KEY_STATUS);
        return true;
    }
    // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0001_F03_Android_S01  删除Android的demo账号    【修改人】：zhaoyufeng

    @Override
    public void setServerRet(ServerRet serverRet) {
        this.serverRet = serverRet;
    }
}
