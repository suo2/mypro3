package com.huawei.solarsafe.bean.personmanagement;

import com.google.gson.Gson;
import com.huawei.solarsafe.bean.BaseEntity;
import com.huawei.solarsafe.bean.ServerRet;

import org.json.JSONObject;

/**
 * Created by P00229 on 2017/4/13.
 */
public class RoleListInfo extends BaseEntity {
    public static final String LIST = "list";
    public static final String NO_LIST = "no_list";
    private RoleListBean roleListBean;
    /**
     * 判断是否列表
     */
    private String tag;

    @Override
    public String getTag() {
        return tag;
    }

    @Override
    public void setTag(String tag) {
        this.tag = tag;
    }

    public RoleListBean getRoleListBean() {
        return roleListBean;
    }

    public void setRoleListBean(RoleListBean roleListBean) {
        this.roleListBean = roleListBean;
    }

    @Override
    public boolean parseJson(JSONObject jsonObject) throws Exception {
        if (jsonObject == null) {
            return false;
        }
        Gson gson = new Gson();
        String json = jsonObject.toString();
        roleListBean = gson.fromJson(json, RoleListBean.class);
        return true;
    }
    // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0001_F03_Android_S01  删除Android的demo账号    【修改人】：zhaoyufengfeng

    @Override
    public void setServerRet(ServerRet serverRet) {

    }
}
