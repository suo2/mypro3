package com.huawei.solarsafe.bean;

import android.util.Log;

import com.huawei.solarsafe.net.JSONReader;
import com.huawei.solarsafe.utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by P00028 on 2017/1/3.
 */
//网络请求返回数据实体类基类,着重于对返回的数据做预处理
public abstract class BaseEntity implements IUserDatabuilder {

    public static final String SUCCESS = "success";
    public static final String DATA = "data";
    private String retMsg;

    private int failCode;

    private String tag2;
    private String tag1;

    public static final String TAG = "BaseEntity";
    public boolean success1;

    public String getRetMsg() {
        return retMsg;
    }

    public void setRetMsg(String retMsg) {
        this.retMsg = retMsg;
    }

    /**
     * 处理网络请求返回的数据,判断数据的合法性
     * @param jsonObject
     * @throws Exception
     */
    @Override
    public void preParse(JSONObject jsonObject) throws Exception {
        //返回json数据不存在
        if (jsonObject == null) {
            setServerRet(ServerRet.ILLEGAL_STATE_EXCEPTION);
            return;
        }

        //返回错误
        failCode = jsonObject.getInt(KEY_ERROR_CODE);
        Utils.logout(failCode, jsonObject);

        //返回无效数据
        if (!parseSuccess(jsonObject)) {
            return;
        }

        //返回数据格式是否正确
        JSONReader jsonReader = new JSONReader(jsonObject);
        try {
            parseJson(jsonReader.getJSONObject(KEY_DATA));
        } catch (Exception e) {
            Log.e(TAG, "mapping doesn't exist or is not a JSONObject, pass original json to subclass.", e);
            parseJson(jsonObject);
        }
    }

    /**
     * 解析网络请求响应成功返回的数据,判断是否是有效的数据
     * @param jsonObject
     * @return
     * @throws JSONException
     */
    private boolean parseSuccess(JSONObject jsonObject) throws JSONException {
        success1 = jsonObject.getBoolean(KEY_SUCCESS);
        // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0004_F01_Android_S02  敏感信息检查-日志中不能打印d和v级别的日志    【修改人】：zhaoyufeng
        Log.e(TAG, "Request Status :" + success1);
        if (success1) {
            setServerRet(ServerRet.OK);
            return true;
        }
        JSONReader jsonReader = new JSONReader(jsonObject);
        long errorCode = jsonReader.getLong(KEY_ERROR_CODE);
        retMsg = jsonReader.getString(KEY_MESSAGE);
        ServerRet serverRet = ServerRet.parseString(errorCode);
        if (serverRet == ServerRet.INVALID_RET) {
            Log.e(TAG, "errorCode:" + errorCode);
        }
        setServerRet(serverRet);
        return false;
    }

    public void setTag(String tag) {
        this.tag2 = tag;
    }

    public String getTag() {
        return tag2;
    }

    public String getTag1() {
        return tag1;
    }

    public void setTag1(String tag1) {
        this.tag1 = tag1;
    }

    public boolean isSuccess() {
        return success1;
    }

    public void setSuccess(boolean success) {
        this.success1 = success;
    }

    public int getFailCode() {
        return failCode;
    }

    public void setFailCode(int failCode) {
        this.failCode = failCode;
    }
}
