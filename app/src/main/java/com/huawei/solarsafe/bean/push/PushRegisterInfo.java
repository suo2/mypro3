package com.huawei.solarsafe.bean.push;

import com.huawei.solarsafe.bean.BaseEntity;
import com.huawei.solarsafe.bean.ServerRet;
import com.huawei.solarsafe.utils.JSONReader;

import org.json.JSONObject;

/**
 * Created by p00322 on 2017/2/17.
 */
public class PushRegisterInfo extends BaseEntity {
    //用户名
    private static final String KEY_USER_NAME = "username";
    //密码
    private static final String KEY_CODE = "password";
    //topicId
    private static final String KEY_TOPIC_ID = "topic";
    //ip地址
    private static final String KEY_ADDRESS = "address";

    //用户名
    String userName;
    //密码
    String password;
    //topicId
    String topic;
    //ip地址
    String address;

    //统一定义的返回码
    ServerRet mRetCode;

    @Override
    public boolean parseJson(JSONObject jsonObject) throws Exception {
        if (jsonObject == null) {
            return false;
        }
        JSONReader jsonReader = new JSONReader(jsonObject);
        userName = jsonReader.getString(KEY_USER_NAME);
        password = jsonReader.getString(KEY_CODE);
        topic = jsonReader.getString(KEY_TOPIC_ID);
        address = jsonReader.getString(KEY_ADDRESS);
        return true;
    }
    // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0001_F03_Android_S01  删除Android的demo账号    【修改人】：zhaoyufeng

    @Override
    public void setServerRet(ServerRet serverRet) {
        mRetCode = serverRet;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public String getTopic() {
        return topic;
    }

    public ServerRet getRetCode() {
        return mRetCode;
    }

    public String getDizhi(){
        return  address;
    }
}