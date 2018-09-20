package com.huawei.solarsafe.logger104.database;


/**
 * 数采信息
 */
public class PntDeviceInfoItem {
    //设备名称
    private String deviceName;
    //设备ESN
    private String deviceESN;
    //设备类型
    private String deviceType;
    //设备录入的状态 1：成功录入，2：没有录入，3：录入失败

    /**
     * 名称
     */
    public static final String KEY_DEV_NAME = "deviceName";
    /**
     * ESN
     */
    public static final String KEY_DEV_ESN = "deviceESN";
    /**
     * 型号
     */
    public static final String KEY_DEV_MODEL = "deviceModel";
    /**
     * 类型
     */
    public static final String KEY_DEV_TYPE = "deviceType";

    /**
     * 录入状态
     */
    public static final String KEY_JOIN_STATUS = "joinStatus";

    public PntDeviceInfoItem(String deviceName, String deviceESN, String deviceModel, String deviceType) {
        this.deviceName = deviceName;
        this.deviceESN = deviceESN;
        this.deviceType = deviceType;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getDeviceType() {
        return deviceType;
    }

    @Override
    public String toString() {
        return "PntDeviceInfoItem{" +
                "deviceName='" + deviceName + '\'' +
                ", deviceESN='" + deviceESN + '\'' +
                ", deviceType='" + deviceType + '\'' +
                '}';
    }
}
