package com.huawei.solarsafe.bean;

import org.json.JSONObject;

/**
 * Created by P00229 on 2017/3/2.
 */
public class ResultInfo extends BaseEntity {
    private String data;
    private int userId;



    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public boolean parseJson(JSONObject jsonObject) throws Exception {
        return true;
    }
    // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0001_F03_Android_S01  删除Android的demo账号    【修改人】：zhaoyufeng

    @Override
    public void preParse(JSONObject jsonObject) throws Exception {

        super.preParse(jsonObject);
    }


    @Override
    public void setServerRet(ServerRet serverRet) {

    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
