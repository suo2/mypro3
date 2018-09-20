package com.huawei.solarsafe.bean.pnlogger;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Create Date: 2016/8/21
 * Create Author: P00029
 * Description :
 */
public class SecondDeviceInfo implements Parcelable{
    private Long id;
    private Long parentId;
    // 电站编码
    private String stationCode;
    // 设备类型
    private Integer devTypeId;
    // 业务名称
    private String busiName;
    // 业务编码
    private String busiCode;
    // 一级地址
    private String hostAddress;
    // esnCode
    private String esnCode;
    // 父设备EsnCode
    private String parentEsnCode;
    // 版本编码：版本号
    private String modelVersionCode;
    // 额定功率编码
    private String powerCode;
    // 经度
    private Double longitude;
    // 纬度
    private Double latitude;
    // PN码
    private String pn;
    // 逆变器组件类型
    private String assemblyType;
    // 间隔
    private String interval;

    private String kksCode;

    protected SecondDeviceInfo(Parcel in) {
        stationCode = in.readString();
        busiName = in.readString();
        busiCode = in.readString();
        hostAddress = in.readString();
        esnCode = in.readString();
        parentEsnCode = in.readString();
        modelVersionCode = in.readString();
        powerCode = in.readString();
        pn = in.readString();
        assemblyType = in.readString();
        interval = in.readString();
        kksCode = in.readString();
    }

    public static final Creator<SecondDeviceInfo> CREATOR = new Creator<SecondDeviceInfo>() {
        @Override
        public SecondDeviceInfo createFromParcel(Parcel in) {
            return new SecondDeviceInfo(in);
        }

        @Override
        public SecondDeviceInfo[] newArray(int size) {
            return new SecondDeviceInfo[size];
        }
    };

    @Override
    public String toString() {
        return "SecondDeviceInfo{" +
                "id=" + id +
                ", parentId=" + parentId +
                ", stationCode='" + stationCode + '\'' +
                ", devTypeId=" + devTypeId +
                ", busiName='" + busiName + '\'' +
                ", busiCode='" + busiCode + '\'' +
                ", hostAddress='" + hostAddress + '\'' +
                ", esnCode='" + esnCode + '\'' +
                ", parentEsnCode='" + parentEsnCode + '\'' +
                ", modelVersionCode='" + modelVersionCode + '\'' +
                ", powerCode='" + powerCode + '\'' +
                ", longitude=" + longitude +
                ", latitude=" + latitude +
                ", pn='" + pn + '\'' +
                ", assemblyType='" + assemblyType + '\'' +
                ", interval='" + interval + '\'' +
                ", kksCode='" + kksCode + '\'' +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getStationCode() {
        return stationCode;
    }

    public void setStationCode(String stationCode) {
        this.stationCode = stationCode;
    }

    public Integer getDevTypeId() {
        return devTypeId;
    }

    public void setDevTypeId(Integer devTypeId) {
        this.devTypeId = devTypeId;
    }

    public String getBusiName() {
        return busiName;
    }

    public void setBusiName(String busiName) {
        this.busiName = busiName;
    }

    public String getBusiCode() {
        return busiCode;
    }

    public void setBusiCode(String busiCode) {
        this.busiCode = busiCode;
    }


    public String getEsnCode() {
        return esnCode;
    }

    public void setEsnCode(String esnCode) {
        this.esnCode = esnCode;
    }



    public String getModelVersionCode() {
        return modelVersionCode;
    }

    public void setModelVersionCode(String modelVersionCode) {
        this.modelVersionCode = modelVersionCode;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public String getInterval() {
        return interval;
    }

    public void setInterval(String interval) {
        this.interval = interval;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(stationCode);
        dest.writeString(busiName);
        dest.writeString(busiCode);
        dest.writeString(hostAddress);
        dest.writeString(esnCode);
        dest.writeString(parentEsnCode);
        dest.writeString(modelVersionCode);
        dest.writeString(powerCode);
        dest.writeString(pn);
        dest.writeString(assemblyType);
        dest.writeString(interval);
        dest.writeString(kksCode);
    }
}
