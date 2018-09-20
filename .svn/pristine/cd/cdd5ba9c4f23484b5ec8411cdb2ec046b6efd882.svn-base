package com.huawei.solarsafe.bean.user.login;

import com.huawei.solarsafe.bean.BaseEntity;
import com.huawei.solarsafe.bean.ServerRet;
import com.huawei.solarsafe.utils.JSONReader;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Create Date: 2017/3/16
 * Create Author: P00438
 * Description :
 */
public class AuthInfo extends BaseEntity {
    private List<AuthBean> list;
    private static final String RIGHTS_ID = "id";
    private static final String JSON_KEY_DATAS = "datas";


    @Override
    public boolean parseJson(JSONObject jsonObject) throws Exception {
        if (jsonObject == null) {
            return false;
        }

        list = new ArrayList<>();
        JSONReader jsonReader = new JSONReader(jsonObject);
        JSONArray jsonArray = jsonReader.getJSONArray(JSON_KEY_DATAS);
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject object = jsonArray.getJSONObject(i);
            AuthBean authBean = new AuthBean();
            authBean.setId(object.getString(RIGHTS_ID));
            list.add(authBean);
        }

        return true;
    }
    // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0001_F03_Android_S01  删除Android的demo账号    【修改人】：zhaoyufeng

    @Override
    public void setServerRet(ServerRet serverRet) {

    }

    public List<AuthBean> getAuthInfoList() {
        return list;
    }

}
