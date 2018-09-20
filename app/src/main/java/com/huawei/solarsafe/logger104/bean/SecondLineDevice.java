package com.huawei.solarsafe.logger104.bean;


import java.io.Serializable;

/**
 * Create Date: 2016/8/5
 * Create Author: P00029
 * Description :下联设备信息
 */
public class SecondLineDevice implements Serializable {

    private static final long serialVersionUID = 6570910073922376248L;
    //下联设备Modbus地址
    private int modbusAddr;
    //设备ESN
    private String deviceESN;
    //设备点表标识
    private long signPointFlag;
    //连接端口
    private byte connPort;
    //协议类型
    private byte protocolType;
    //数采名称，用于传至服务器，数采不感知
    private String deviceName;
    // 大小端
    int endian;
    // 波特率
    int baudRate;

    public SecondLineDevice(int modbusAddr, String deviceESN, long signPointFlag, byte connPort, byte protocolType, String deviceName) {
        this.modbusAddr = modbusAddr;
        this.deviceESN = deviceESN;
        this.signPointFlag = signPointFlag;
        this.connPort = connPort;
        this.protocolType = protocolType;
        this.deviceName = deviceName;
    }

    public SecondLineDevice() {

    }


    public int getModbusAddr() {
        return modbusAddr;
    }

    public void setModbusAddr(int modbusAddr) {
        this.modbusAddr = modbusAddr;
    }

    public String getDeviceESN() {
        return deviceESN;
    }

    public void setDeviceESN(String deviceESN) {
        this.deviceESN = deviceESN;
    }

    public long getSignPointFlag() {
        return signPointFlag;
    }

    public void setSignPointFlag(long signPointFlag) {
        this.signPointFlag = signPointFlag;
    }

    public byte getConnPort() {
        return connPort;
    }

    public void setConnPort(byte connPort) {
        this.connPort = connPort;
    }

    public byte getProtocolType() {
        return protocolType;
    }

    public void setProtocolType(byte protocolType) {
        this.protocolType = protocolType;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public int getEndian() {
        return endian;
    }

    public void setEndian(int endian) {
        this.endian = endian;
    }

    public int getBaudRate() {
        return baudRate;
    }

    public void setBaudRate(int baudRate) {
        this.baudRate = baudRate;
    }


    @Override
    public String toString() {
        return "SecondLineDevice{" +
                "modbusAddr=" + modbusAddr +
                ", deviceESN='" + deviceESN + '\'' +
                ", signPointFlag=" + signPointFlag +
                ", connPort=" + connPort +
                ", protocolType=" + protocolType +
                ", deviceName='" + deviceName + '\'' +
                '}';
    }
}
