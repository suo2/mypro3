package com.huawei.solarsafe.bean.stationmagagement;

import com.google.gson.Gson;
import com.huawei.solarsafe.bean.BaseEntity;
import com.huawei.solarsafe.bean.ServerRet;

import org.json.JSONObject;

/**
 * Created by p00507
 * on 2017/10/16.
 */
public class DevCapByIdData extends BaseEntity {
    private ServerRet mServerRet;
    private DevCapByIdDataBean dataBean;
    @Override
    public boolean parseJson(JSONObject jsonObject) throws Exception {
        if (jsonObject == null) {
            return false;
        }
        Gson gson = new Gson();
        dataBean = gson.fromJson(jsonObject.toString(), DevCapByIdDataBean.class);
        return true;
    }
    // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0001_F03_Android_S01  删除Android的demo账号    【修改人】：zhaoyufeng

    @Override
    public void setServerRet(ServerRet serverRet) {
        mServerRet = serverRet;
    }


    public DevCapByIdDataBean getDataBean() {
        return dataBean;
    }

    public void setDataBean(DevCapByIdDataBean dataBean) {
        this.dataBean = dataBean;
    }
}
