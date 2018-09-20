package com.huawei.solarsafe.bean.device;

import com.huawei.solarsafe.bean.BaseEntity;
import com.huawei.solarsafe.bean.ServerRet;

import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by P00229 on 2017/2/21.
 */
public class EquipmentDispersionInfo extends BaseEntity implements Serializable {
    public static final String TAG = "EquipmentDispersionInfo";
    private static final long serialVersionUID = -1979252294735347429L;
    //电站编号
    private String stationCode;
    //电站名称
    private String stationName;
    //设备名称
    private String deviceName;
    //离散率
    private String dispersion;
    //平均光伏电压
    private String avgPhotcU;
    private String pv1, pv2, pv3, pv4, pv5, pv6, pv7, pv8, pv9, pv10, pv11, pv12, pv13, pv14,pv15, pv16, pv17, pv18, pv19, pv20;

    public String getStationCode() {
        return stationCode;
    }

    public void setStationCode(String stationCode) {
        this.stationCode = stationCode;
    }

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getDispersion() {
        return dispersion;
    }

    public void setDispersion(String dispersion) {
        this.dispersion = dispersion;
    }

    public String getPv1() {
        return pv1;
    }

    public void setPv1(String pv1) {
        this.pv1 = pv1;
    }

    public String getPv2() {
        return pv2;
    }

    public void setPv2(String pv2) {
        this.pv2 = pv2;
    }

    public String getPv3() {
        return pv3;
    }

    public void setPv3(String pv3) {
        this.pv3 = pv3;
    }

    public String getPv4() {
        return pv4;
    }

    public void setPv4(String pv4) {
        this.pv4 = pv4;
    }

    public String getPv5() {
        return pv5;
    }

    public void setPv5(String pv5) {
        this.pv5 = pv5;
    }

    public String getPv6() {
        return pv6;
    }

    public void setPv6(String pv6) {
        this.pv6 = pv6;
    }

    public String getPv7() {
        return pv7;
    }

    public void setPv7(String pv7) {
        this.pv7 = pv7;
    }

    public String getPv8() {
        return pv8;
    }

    public void setPv8(String pv8) {
        this.pv8 = pv8;
    }

    public String getPv9() {
        return pv9;
    }

    public void setPv9(String pv9) {
        this.pv9 = pv9;
    }

    public String getPv10() {
        return pv10;
    }

    public void setPv10(String pv10) {
        this.pv10 = pv10;
    }

    public String getPv11() {
        return pv11;
    }

    public void setPv11(String pv11) {
        this.pv11 = pv11;
    }

    public String getPv12() {
        return pv12;
    }

    public void setPv12(String pv12) {
        this.pv12 = pv12;
    }

    public String getPv13() {
        return pv13;
    }

    public void setPv13(String pv13) {
        this.pv13 = pv13;
    }

    public String getPv14() {
        return pv14;
    }

    public void setPv14(String pv14) {
        this.pv14 = pv14;
    }

    public String getPv15() {
        return pv15;
    }

    public void setPv15(String pv15) {
        this.pv15 = pv15;
    }

    public String getPv16() {
        return pv16;
    }

    public void setPv16(String pv16) {
        this.pv16 = pv16;
    }

    public String getPv17() {
        return pv17;
    }

    public void setPv17(String pv17) {
        this.pv17 = pv17;
    }

    public String getPv18() {
        return pv18;
    }

    public void setPv18(String pv18) {
        this.pv18 = pv18;
    }

    public String getPv19() {
        return pv19;
    }

    public void setPv19(String pv19) {
        this.pv19 = pv19;
    }

    public String getPv20() {
        return pv20;
    }

    public void setPv20(String pv20) {
        this.pv20 = pv20;
    }

    @Override
    public boolean parseJson(JSONObject jsonObject) throws Exception {
        return false;
    }
    // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0001_F03_Android_S01  删除Android的demo账号    【修改人】：zhaoyufeng

    @Override
    public void setServerRet(ServerRet serverRet) {

    }

    public String getAvgPhotcU() {
        return avgPhotcU;
    }

    public void setAvgPhotcU(String avgPhotcU) {
        this.avgPhotcU = avgPhotcU;
    }
}
