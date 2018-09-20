package com.huawei.solarsafe.bean.notice;

import com.huawei.solarsafe.bean.BaseEntity;
import com.huawei.solarsafe.bean.ServerRet;
import com.huawei.solarsafe.utils.JSONReader;

import org.json.JSONObject;

/**
 * Created by p00322 on 2017/2/15.
 * description: 系统公告读取信息获取接口解析类
 */
public class SystemQueryNoteInfo extends BaseEntity {
    //公告Id
    private static final String KEY_ID = "id";
    //公告主题
    private static final String KEY_TOPIC = "topic";
    //公告内容
    private static final String KEY_CONTENT = "content";
    //公告状态(true已读  false未读)
    private static final String KEY_STATUS = "status";
    //发布时间

    //公告Id
    long id;
    //公告主题
    String topic;
    //公告内容
    String content;
    //公告状态(true已读  false未读)
    boolean status;
    //统一定义的返回码
    ServerRet mRetCode;

    @Override
    public boolean parseJson(JSONObject jsonObject) throws Exception {
        if (jsonObject == null) {
            return false;
        }
        JSONReader jsonReader = new JSONReader(jsonObject);
        id = jsonReader.getLong(KEY_ID);
        topic = jsonReader.getString(KEY_TOPIC);
        content = jsonReader.getString(KEY_CONTENT);
        status = jsonReader.getBoolean(KEY_STATUS);
        return true;
    }
    // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0001_F03_Android_S01  删除Android的demo账号    【修改人】：zhaoyufeng

    @Override
    public void setServerRet(ServerRet serverRet) {
        mRetCode = serverRet;
    }

    public long getId() {
        return id;
    }

    public String getTopic() {
        return topic;
    }

    public String getContent() {
        return content;
    }



    public boolean isStatus() {
        return status;
    }


    public ServerRet getRetCode() {
        return mRetCode;
    }

    @Override
    public String toString() {
        return "SystemQueryNoteInfo{" +
                "id=" + id +
                ", topic='" + topic + '\'' +
                ", content='" + content + '\'' +
                ", status=" + status +
                ", mRetCode=" + mRetCode +
                '}';
    }
}
