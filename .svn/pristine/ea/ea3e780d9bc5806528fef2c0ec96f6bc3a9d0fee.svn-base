package com.huawei.solarsafe.bean.notice;

import android.util.Log;

import com.huawei.solarsafe.bean.BaseEntity;
import com.huawei.solarsafe.bean.ServerRet;
import com.huawei.solarsafe.net.JSONReader;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by p00322 on 2017/3/2.
 */
public class InforMationList extends BaseEntity {
    public static final int HAS_READ = 1;
    public static final int WAIT_READ = 0;

    //主键id
    private static final String KEY_ID = "id";
    //用户id
    private static final String KEY_USER_ID = "userId";
    //消息keyid
    private static final String KEY_KEY_ID = "keyId";
    //消息内容
    private static final String KEY_MESSAGE = "message";
    //消息类型（缺陷1，巡检2，公告3）
    private static final String KEY_MSG_TYPE = "msgType";
    //推送时间(jsonobject)
    private static final String KEY_SEND_TIME_OBJECT = "sendTime";
    //推送时间
    private static final String KEY_SEND_TIME = "time";
    //推送时间
    private static final String KEY_ISREAD = "isRead";
    private int total;
    //统一定义的返回码
    ServerRet mRetCode;
    private List<InforMationInfo> infoMationlist = new ArrayList<>();

    public List<InforMationInfo> getinfoMationlist() {
        return infoMationlist;
    }


    @Override
    public boolean parseJson(JSONObject jsonObject) throws Exception {
        if (jsonObject == null) {
            return false;
        }
        // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0004_F01_Android_S02  敏感信息检查-日志中不能包含敏感信息    【修改人】：zhaoyufeng
        JSONReader jsonReader = new JSONReader(jsonObject);
        JSONObject pagedResult = jsonReader.getJSONObject("pagedResult");
        JSONArray jsonArray ;
        int len ;
        /**
         * CODEX 162435  修改人：江东
         */
        try{
            total = pagedResult.getInt("total");
            jsonArray = pagedResult.getJSONArray("list");
            if (jsonArray!=null){
                len = jsonArray.length();
                for (int i = 0; i < len; i++) {
                    InforMationInfo messageInfo = new InforMationInfo();
                    JSONObject object = jsonArray.getJSONObject(i);
                    JSONReader jsonReader2 = new JSONReader(object);
                    messageInfo.setId(jsonReader2.getString(KEY_ID));
                    messageInfo.setKeyId(jsonReader2.getString(KEY_KEY_ID));
                    messageInfo.setUserId(jsonReader2.getLong(KEY_USER_ID));
                    messageInfo.setMsgType(jsonReader2.getString(KEY_MSG_TYPE));
                    messageInfo.setMessage(jsonReader2.getString(KEY_MESSAGE));
                    messageInfo.setReadflag(jsonReader2.getInt(KEY_ISREAD));
                    JSONReader jsonReader3 = new JSONReader(jsonReader2.getJSONObject(KEY_SEND_TIME_OBJECT));
                    messageInfo.setSendTime(jsonReader3.getLong(KEY_SEND_TIME));
                    infoMationlist.add(messageInfo);
                }
            }
        }catch (Exception e){
            Log.e("InforMationList",e.getMessage());
            return  false;
        }
        return true;
    }
    // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0001_F03_Android_S01  删除Android的demo账号    【修改人】：zhaoyufeng

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    @Override
    public void setServerRet(ServerRet serverRet) {
        this.mRetCode = serverRet;
    }

    public ServerRet getRetCode() {
        return mRetCode;
    }

    @Override
    public String toString() {
        return "infoMationList{" +
                "serverRet=" + mRetCode +
                ", list=" + infoMationlist.toString() +
                '}';
    }
}
