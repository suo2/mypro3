package com.huawei.solarsafe.bean.pnlogger;

import android.os.Parcel;
import android.os.Parcelable;

import com.huawei.solarsafe.bean.BaseEntity;
import com.huawei.solarsafe.bean.ServerRet;
import com.huawei.solarsafe.net.JSONReader;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Create Date: 2017/3/9
 * Create Author: P00028
 * Description :
 */
public class PvItemInfos extends BaseEntity implements Parcelable {
    private String deviceId;
    private String stationCode;
    private List<String> pvData;

    public PvItemInfos(Parcel in) {
        deviceId = in.readString();
        stationCode = in.readString();
        pvData = in.createStringArrayList();
    }

    public static final Creator<PvItemInfos> CREATOR = new Creator<PvItemInfos>() {
        @Override
        public PvItemInfos createFromParcel(Parcel in) {
            return new PvItemInfos(in);
        }

        @Override
        public PvItemInfos[] newArray(int size) {
            return new PvItemInfos[size];
        }
    };

    @Override
    public boolean parseJson(JSONObject jsonObject) throws Exception {
        JSONReader jsonReader = new JSONReader(jsonObject);
        String jsonStr = jsonObject.toString();
        deviceId = jsonReader.getString("devId");
        stationCode = jsonReader.getString("stationCode");
        pvData = new ArrayList<>();
        for (int i = 1; i <= 20; i++) {
            String temp = "pv" + i;
            if (jsonStr.contains(temp)) {
                pvData.add(jsonReader.getString(temp));
            }
        }
        return true;
    }
    // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0001_F03_Android_S01  删除Android的demo账号    【修改人】：zhaoyufengufeng

    @Override
    public void setServerRet(ServerRet serverRet) {
        //Do nothing
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getStationCode() {
        return stationCode;
    }

    public void setStationCode(String stationCode) {
        this.stationCode = stationCode;
    }

    public List<String> getPvData() {
        return pvData;
    }

    public void setPvData(List<String> pvData) {
        this.pvData = pvData;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(deviceId);
        dest.writeString(stationCode);
        dest.writeStringList(pvData);
    }

    @Override
    public String toString() {
        return "PvItemInfos{" +
                "deviceId='" + deviceId + '\'' +
                ", stationCode='" + stationCode + '\'' +
                ", pvData=" + pvData +
                '}';
    }
}