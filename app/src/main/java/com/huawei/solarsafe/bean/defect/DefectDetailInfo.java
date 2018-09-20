package com.huawei.solarsafe.bean.defect;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.huawei.solarsafe.bean.BaseEntity;
import com.huawei.solarsafe.bean.ServerRet;

import org.json.JSONObject;

import java.lang.reflect.Type;

/**
 * Created by p00322 on 2017/3/6.
 */
public class DefectDetailInfo extends BaseEntity {
    /**
     * 缺陷详情
     */
    private DefectDetail detail;

    private Gson gson = new Gson();

    private ServerRet mServerRet;

    @Override
    public boolean parseJson(JSONObject jsonObject) throws Exception {
        String temp = jsonObject.toString();
        Type type = new TypeToken<DefectDetail>() {
        }.getType();
        detail = gson.fromJson(temp, type);
        return true;
    }
    // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0001_F03_Android_S01  删除Android的demo账号    【修改人】：zhaoyufeng

    @Override
    public void setServerRet(ServerRet serverRet) {
        mServerRet = serverRet;
    }

    public ServerRet getServerRet() {
        return mServerRet;
    }

    public DefectDetail getDetail() {
        return detail;
    }
}
