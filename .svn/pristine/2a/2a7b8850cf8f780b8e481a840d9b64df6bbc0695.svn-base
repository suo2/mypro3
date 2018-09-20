package com.huawei.solarsafe.bean.push;

import com.huawei.solarsafe.bean.BaseEntity;
import com.huawei.solarsafe.bean.ServerRet;
import com.huawei.solarsafe.utils.JSONReader;

import org.json.JSONObject;

/**
 * Created by p00322 on 2017/2/21.
 */
public class PushLogOutInfo extends BaseEntity {
    //topicId
    private static final String KEY_TOPIC_ID = "topicid";

    //topicId
    String topic;

    //统一定义的返回码
    ServerRet mRetCode;

    @Override
    public boolean parseJson(JSONObject jsonObject) throws Exception {
        if (jsonObject == null) {
            return false;
        }
        JSONReader jsonReader = new JSONReader(jsonObject);
        topic = jsonReader.getString(KEY_TOPIC_ID);
        return true;
    }
    // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0001_F03_Android_S01  删除Android的demo账号    【修改人】：zhaoyufeng

    @Override
    public void setServerRet(ServerRet serverRet) {
        mRetCode = serverRet;
    }


    public ServerRet getRetCode() {
        return mRetCode;
    }
}
