package com.huawei.solarsafe.logger104.database;

import java.io.Serializable;

/**
 * Created by P00517 on 2017/3/21.
 */
public class PntConnectInfoItem implements Serializable {
    private static final long serialVersionUID = -9065007287551956507L;
    //设备名称
    private String deviceName;
    //设备ESN
    private String deviceESN;
    //设备类型
    private String deviceType;
    //数采连接状态 0：未连接；1：已连接
    private int connectStatus = 0;
    //设备保存时间
    private long deviceSaveTime;
    //数采地址
    private String pntLocation;
    //modbus地址
    private int modbusLocation;
    //上级数采ESN号
    private String pntESN;
    //组串信息
    private String pvInfo;
    //预留字段
    private String reservePlace;

    /**
     * 名称
     */
    public static final String KEY_DEV_NAME = "deviceName";
    /**
     * ESN
     */
    public static final String KEY_DEV_ESN = "deviceESN";
    /**
     * 类型
     */
    public static final String KEY_DEV_TYPE = "deviceType";
    /**
     * 录入状态
     */
    public static final String KEY_CONNECT_STATUS = "connectStatus";
    /**
     * 保存时间
     */
    public static final String KEY_DEVICE_SAVE_TIME = "deviceSaveTime";
    /**
     * 数采地址
     */
    public static final String KEY_PNT_LOCATION = "pntLocation";
    /**
     * modbus地址
     */
    public static final String KEY_MODBUS_LOCATION = "modbusLocation";
    /**
     * 上级数采的ESN号
     */
    public static final String KEY_PNT_ESN = "pntESN";
    /**
     * 组串信息
     */
    public static final String KEY_PV_INFO = "pntInfo";
    /**
     * 预留字段
     */
    public static final String KEY_RESERVE_PLACE = "reservePlace";

    public PntConnectInfoItem(String deviceName, String deviceESN, String deviceType, int connectStatus,
                              long deviceSaveTime, String pntLocation, int modbusLocation,
                              String pntESN, String pvInfo, String reservePlace) {
        this.deviceName = deviceName;
        this.deviceESN = deviceESN;
        this.deviceType = deviceType;
        this.connectStatus = connectStatus;
        this.deviceSaveTime = deviceSaveTime;
        this.pntLocation = pntLocation;
        this.modbusLocation = modbusLocation;
        this.pntESN = pntESN;
        this.pvInfo = pvInfo;
        this.reservePlace = reservePlace;
    }

    /**
     * 下联设备信息
     *
     * @param deviceName     设备名称
     * @param deviceESN      设备esn号
     * @param deviceSaveTime 设备保存时间
     * @param pntESN         上级数采esn号
     * @param pvInfo         组串信息
     * @param reservePlace   预留字段，用于存储登录IP和用户
     */
    public PntConnectInfoItem(String deviceName, String deviceESN, long deviceSaveTime,
                              String pntESN, String pvInfo, String reservePlace) {
        this.deviceName = deviceName;
        this.deviceESN = deviceESN;
        this.deviceSaveTime = deviceSaveTime;
        this.pntESN = pntESN;
        this.pvInfo = pvInfo;
        this.reservePlace = reservePlace;
    }

    /**
     * 数采信息
     *
     * @param deviceName     设备名称
     * @param deviceESN      设备esn号
     * @param connectStatus  连接状态
     * @param deviceSaveTime 保存时间
     * @param pntLocation    数采地址
     * @param reservePlace   预留字段，用于存储登录IP和用户
     */
    public PntConnectInfoItem(String deviceName, String deviceESN, int connectStatus,
                              long deviceSaveTime, String pntLocation, String reservePlace) {
        this.deviceName = deviceName;
        this.deviceESN = deviceESN;
        this.deviceSaveTime = deviceSaveTime;
        this.connectStatus = connectStatus;
        this.pntLocation = pntLocation;
        this.reservePlace = reservePlace;
    }

    public PntConnectInfoItem(String deviceName, String deviceESN, long deviceSaveTime, int modbusLocation,
                              String deviceType, String pntESN, String reservePlace) {
        this.deviceName = deviceName;
        this.deviceESN = deviceESN;
        this.deviceSaveTime = deviceSaveTime;
        this.modbusLocation = modbusLocation;
        this.deviceType = deviceType;
        this.pntESN = pntESN;
        this.reservePlace = reservePlace;
    }

    public PntConnectInfoItem(String deviceName, String deviceESN, long deviceSaveTime,
                              int modbusLocation, String deviceType, String pntESN, String pvInfo, String reservePlace) {
        this.deviceName = deviceName;
        this.deviceESN = deviceESN;
        this.deviceSaveTime = deviceSaveTime;
        this.modbusLocation = modbusLocation;
        this.deviceType = deviceType;
        this.pntESN = pntESN;
        this.pvInfo = pvInfo;
        this.reservePlace = reservePlace;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getDeviceESN() {
        return deviceESN;
    }


    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public int getConnectStatus() {
        return connectStatus;
    }


    public long getDeviceSaveTime() {
        return deviceSaveTime;
    }


    public String getPntLocation() {
        return pntLocation;
    }


    public int getModbusLocation() {
        return modbusLocation;
    }


    public String getPntESN() {
        return pntESN;
    }


    public String getPvInfo() {
        return pvInfo;
    }


    public String getReservePlace() {
        return reservePlace;
    }

}
