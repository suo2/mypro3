package com.huawei.solarsafe.bean.device;

import android.util.Log;

import com.huawei.solarsafe.bean.BaseEntity;
import com.huawei.solarsafe.bean.ServerRet;
import com.huawei.solarsafe.net.JSONReader;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Create Date: 2017/4/19
 * Create Author: P00171
 * Description :
 */
public class DevChandeDetailEntity extends BaseEntity {
    private boolean isDevNotFind;
    private boolean devTypeError;
    private boolean isTarDevPinDc;
    private boolean tarNeedFE;
    private boolean differentParent;
    //
    private String stringType;
    private String esn;
    private String stationCode;
    private String inverterVersion;
    private String inverterType;

    private String deviceType;

    @Override
    public boolean parseJson(JSONObject jsonObject) throws Exception {
        try {
            esn = jsonObject.getString("esn");
        } catch (JSONException e) {
            Log.e(TAG, "parseJson: "+e.getMessage() );
        }
        if (esn == null) {
            //目标设备非组串式逆变器设备或户用逆变器设备
            devTypeError = false;
            try {
                devTypeError = jsonObject.getBoolean("devTypeError");
            } catch (JSONException e) {
                Log.e(TAG, "parseJson: "+e.getMessage() );
            }
            //品联数采下挂设备不支持此功能
            isTarDevPinDc = false;
            try {
                isTarDevPinDc = jsonObject.getBoolean("isTarDevPinDc");
            } catch (JSONException e) {
                Log.e(TAG, "parseJson: "+e.getMessage() );
            }
            //目标设备只能是FE直连逆变器
            tarNeedFE = false;
            try {
                tarNeedFE = jsonObject.getBoolean("tarNeedFE");
            } catch (JSONException e) {
                Log.e(TAG, "parseJson: "+e.getMessage() );
            }
            //只能替换相同数采下的设备
            differentParent = false;
            try {
                differentParent = jsonObject.getBoolean("differentParent");
            } catch (JSONException e) {
                Log.e(TAG, "parseJson: "+e.getMessage() );
            }
            if (!(devTypeError | isTarDevPinDc | tarNeedFE | differentParent)) {
                isDevNotFind = true;
            }
        } else {
            JSONReader jsonReader = new JSONReader(jsonObject);
            stringType = jsonReader.getString("stringType");
            esn = jsonReader.getString("esn");
            stationCode = jsonReader.getString("stationCode");
            inverterVersion = jsonReader.getString("inverterVersion");
            inverterType = jsonReader.getString("inverterType");
            deviceType = jsonReader.getString("tarDevTypeId");
        }
        return true;
    }
    // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0001_F03_Android_S01  删除Android的demo账号    【修改人】：zhaoyufeng

    @Override
    public void setServerRet(ServerRet serverRet) {

    }

    public boolean isDevNotFind() {
        return isDevNotFind;
    }

    public boolean isDevTypeError() {
        return devTypeError;
    }

    public boolean isTarDevPinDc() {
        return isTarDevPinDc;
    }

    public boolean isTarNeedFE() {
        return tarNeedFE;
    }

    public boolean isDifferentParent() {
        return differentParent;
    }

    public String getStringType() {
        return stringType;
    }

    public String getEsn() {
        return esn;
    }

    public void setEsn(String esn) {
        this.esn = esn;
    }

    public String getStationCode() {
        return stationCode;
    }

    public void setStationCode(String stationCode) {
        this.stationCode = stationCode;
    }

    public String getInverterVersion() {
        return inverterVersion;
    }

    public String getInverterType() {
        return inverterType;
    }


    @Override
    public String toString() {
        return "DevChandeDetailEntity{" +
                "isDevNotFind=" + isDevNotFind +
                ", devTypeError=" + devTypeError +
                ", isTarDevPinDc=" + isTarDevPinDc +
                ", tarNeedFE=" + tarNeedFE +
                ", differentParent=" + differentParent +
                ", stringType='" + stringType + '\'' +
                ", esn='" + esn + '\'' +
                ", stationCode='" + stationCode + '\'' +
                ", inverterVersion='" + inverterVersion + '\'' +
                ", inverterType='" + inverterType + '\'' +
                '}';
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }
}
