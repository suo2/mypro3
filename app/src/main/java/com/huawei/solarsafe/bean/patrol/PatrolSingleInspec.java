package com.huawei.solarsafe.bean.patrol;

import com.huawei.solarsafe.bean.BaseEntity;
import com.huawei.solarsafe.bean.ServerRet;

import org.json.JSONObject;

/**
 * Create Date: 2017/3/6
 * Create Author: p00213
 * Description :
 */
public class PatrolSingleInspec extends BaseEntity {

    @Override
    public boolean parseJson(JSONObject jsonObject) throws Exception {
        return true;
    }
    // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0001_F03_Android_S01  删除Android的demo账号    【修改人】：zhaoyufeng

    @Override
    public void setServerRet(ServerRet serverRet) {
    }

}
