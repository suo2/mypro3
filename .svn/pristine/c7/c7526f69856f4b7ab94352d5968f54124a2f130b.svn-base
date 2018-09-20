package com.huawei.solarsafe.bean.personmanagement;

import com.google.gson.Gson;
import com.huawei.solarsafe.bean.BaseEntity;
import com.huawei.solarsafe.bean.ServerRet;

import org.json.JSONObject;

/**
 * <pre>
 *     author: Tzy
 *     time  : $date$
 *     desc  : 服务器版本实体类
 * </pre>
 */
public class SystemVersionBean extends BaseEntity {

    private SystemVersionBean systemVersionBean;

    private String mainVersion;

    @Override
    public boolean parseJson(JSONObject jsonObject) throws Exception {
        if (jsonObject==null){
            return false;
        }

        systemVersionBean=new Gson().fromJson(jsonObject.toString(),SystemVersionBean.class);
        return true;
    }

    @Override
    public void setServerRet(ServerRet serverRet) {

    }

    public SystemVersionBean getSystemVersionBean() {
        return systemVersionBean;
    }

    public String getMainVersion() {
        return mainVersion;
    }


}
