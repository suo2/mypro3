package com.huawei.solarsafe.bean.personmanagement;

import android.util.Log;

import com.google.gson.Gson;
import com.huawei.solarsafe.bean.BaseEntity;
import com.huawei.solarsafe.bean.ServerRet;

import org.json.JSONObject;

/**
 * Created by P00229 on 2017/4/10.
 */
public class UserListInfo extends BaseEntity {
    private UserListBean userListBean;

    @Override
    public boolean parseJson(JSONObject jsonObject) throws Exception {
        if (jsonObject == null) {
            return false;
        }
        String s = jsonObject.toString();
        Gson gson = new Gson();
        try {
            userListBean = gson.fromJson(s, UserListBean.class);
        } catch (Exception e) {
            // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0004_F01_Android_S02  敏感信息检查-日志中不能包含敏感信息    【修改人】：zhaoyufeng
            Log.e("UserListBean", "服务器返回数据结构错误");
        }

        return true;
    }
    // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0001_F03_Android_S01  删除Android的demo账号    【修改人】：zhaoyufeng

    @Override
    public void setServerRet(ServerRet serverRet) {

    }

    public UserListBean getUserListBean() {
        return userListBean;
    }

}
