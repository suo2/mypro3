package com.huawei.solarsafe.bean.ivcurve;

import com.google.gson.Gson;
import com.huawei.solarsafe.bean.BaseEntity;
import com.huawei.solarsafe.bean.ServerRet;

import org.json.JSONObject;

/**
 * Created by p00507
 * on 2017/7/24.
 */
public class IvcurveInterverInfo extends BaseEntity {
    //统一定义的返回码
    ServerRet mRetCode;
    private String invertVersion;
    private String invertEsnCode;
    private String invertType;
    private String totalPower;
    private String checkTime;
    private String invertName;
    private String invertPower;
    private String stringIm;
    private String stringff;
    private String stringVoc;
    private String stringVm;
    private String stringVmVoc;
    private String stringIsc;
    private String stringPm;
    private String stringDecRate;
    private String stringImIoc;
    private IvcurveInterverInfo ivcurveInterverInfo;
    @Override
    public boolean parseJson(JSONObject jsonObject) throws Exception {
        if(jsonObject == null){
            return false;
        }
        String s = jsonObject.toString();
        Gson gson = new Gson();
        ivcurveInterverInfo = gson.fromJson(s, IvcurveInterverInfo.class);
        return true;
    }
    // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0001_F03_Android_S01  删除Android的demo账号    【修改人】：zhaoyufeng人】：zhaoyufeng

    @Override
    public void setServerRet(ServerRet serverRet) {
        this.mRetCode = serverRet;
    }

    public String getInvertVersion() {
        return invertVersion;
    }

    public String getInvertEsnCode() {
        return invertEsnCode;
    }

    public String getInvertType() {
        return invertType;
    }

    public String getTotalPower() {
        return totalPower;
    }

    public String getCheckTime() {
        return checkTime;
    }

    public String getInvertPower() {
        return invertPower;
    }

    public String getInvertName() {
        return invertName;
    }

    public String getStringIm() {
        return stringIm;
    }

    public String getStringff() {
        return stringff;
    }

    public String getStringVoc() {
        return stringVoc;
    }

    public String getStringVm() {
        return stringVm;
    }

    public String getStringVmVoc() {
        return stringVmVoc;
    }

    public String getStringIsc() {
        return stringIsc;
    }

    public String getStringPm() {
        return stringPm;
    }

    public String getStringDecRate() {
        return stringDecRate;
    }

    public String getStringImIoc() {
        return stringImIoc;
    }

    public IvcurveInterverInfo getIvcurveInterverInfo() {
        return ivcurveInterverInfo;
    }

}
