package com.huawei.solarsafe.bean.stationmagagement;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.HashMap;

/**
 * Create Date: 2016/8/21
 * Create Author: P00029
 * Description : 接入设备实体类/接入设备下挂设备实体类(2个数据格式一样)
 */
public class SubDev implements Parcelable,Serializable{
    private static final long serialVersionUID = -9198114691215073699L;
    private Long id;
    private Long parentId;
    // 电站编码
    private String stationCode;
    // 分区编码
    private String areaId;
    // 子阵编码
    private Long matrixId;
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
    // 端口号
    private Integer portNumber;
    // 父设备EsnCode
    private String parentEsnCode;
    // 版本编码：设备版本号
    private String modelVersionCode;
    // 版本编码：软件版本号
    private String softwareVersion;
    // 额定功率编码
    private String powerCode;
    // 设备二级地址
    private Integer protocolAddr;
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


    private boolean isCheck;//组串详情配置碎片 列表 是否被选中
    //判断是否配置过组串信息
    private boolean isSet;
    private String twoLevelDomain;
    private String capacity;//组串容量
    private String pvNum;

    private HashMap<String,PvCapacity> pvCapacity;

    public HashMap<String, PvCapacity> getPvCapacity() {
        return pvCapacity;
    }

    public void setPvCapacity(HashMap<String, PvCapacity> pvCapacity) {
        this.pvCapacity = pvCapacity;
    }

    public String getSoftwareVersion() {
        return softwareVersion;
    }

    public void setSoftwareVersion(String softwareVersion) {
        this.softwareVersion = softwareVersion;
    }

    public String getPvNum() {
        return pvNum;
    }

    public void setPvNum(String pvNum) {
        this.pvNum = pvNum;
    }

    public boolean isSet() {
        return isSet;
    }

    public void setSet(boolean set) {
        isSet = set;
    }

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }

    protected SubDev(Parcel in) {
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
        id = in.readLong();
        devTypeId = in.readInt();
        areaId = in.readString();
        twoLevelDomain = in.readString();
        capacity = in.readString();
    }

    public SubDev() {
    }

    public static final Creator<SubDev> CREATOR = new Creator<SubDev>() {
        @Override
        public SubDev createFromParcel(Parcel in) {
            return new SubDev(in);
        }

        @Override
        public SubDev[] newArray(int size) {
            return new SubDev[size];
        }
    };

    @Override
    public String toString() {
        return "SubDev{" +
                "id=" + id +
                ", parentId=" + parentId +
                ", stationCode='" + stationCode + '\'' +
                ", areaId=" + areaId +
                ", matrixId=" + matrixId +
                ", devTypeId=" + devTypeId +
                ", busiName='" + busiName + '\'' +
                ", busiCode='" + busiCode + '\'' +
                ", hostAddress='" + hostAddress + '\'' +
                ", esnCode='" + esnCode + '\'' +
                ", portNumber=" + portNumber +
                ", parentEsnCode='" + parentEsnCode + '\'' +
                ", modelVersionCode='" + modelVersionCode + '\'' +
                ", powerCode='" + powerCode + '\'' +
                ", protocolAddr=" + protocolAddr +
                ", longitude=" + longitude +
                ", latitude=" + latitude +
                ", pn='" + pn + '\'' +
                ", assemblyType='" + assemblyType + '\'' +
                ", interval='" + interval + '\'' +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }

    public void setMatrixId(Long matrixId) {
        this.matrixId = matrixId;
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


    public void setHostAddress(String hostAddress) {
        this.hostAddress = hostAddress;
    }

    public String getEsnCode() {
        return esnCode;
    }

    public void setEsnCode(String esnCode) {
        this.esnCode = esnCode;
    }

    public void setPortNumber(Integer portNumber) {
        this.portNumber = portNumber;
    }


    public void setParentEsnCode(String parentEsnCode) {
        this.parentEsnCode = parentEsnCode;
    }

    public String getModelVersionCode() {
        return modelVersionCode;
    }

    public void setModelVersionCode(String modelVersionCode) {
        this.modelVersionCode = modelVersionCode;
    }


    public void setPowerCode(String powerCode) {
        this.powerCode = powerCode;
    }


    public void setProtocolAddr(Integer protocolAddr) {
        this.protocolAddr = protocolAddr;
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


    public void setPn(String pn) {
        this.pn = pn;
    }


    public void setAssemblyType(String assemblyType) {
        this.assemblyType = assemblyType;
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

    public String getTwoLevelDomain() {
        return twoLevelDomain;
    }

    public void setTwoLevelDomain(String twoLevelDomain) {
        this.twoLevelDomain = twoLevelDomain;
    }

    public String getCapacity() {
        return capacity;
    }

    public void setCapacity(String capacity) {
        this.capacity = capacity;
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
        dest.writeLong(id);
        dest.writeInt(devTypeId);
        dest.writeString(areaId);
        dest.writeString(twoLevelDomain);
        dest.writeString(capacity);
    }

    public class PvCapacity implements Serializable{

        /**
         * ratedCapacity : 65.537
         * capNum : 2
         */

        private double ratedCapacity;
        private int capNum;

        public double getRatedCapacity() {
            return ratedCapacity;
        }

        public void setRatedCapacity(double ratedCapacity) {
            this.ratedCapacity = ratedCapacity;
        }

        public int getCapNum() {
            return capNum;
        }

        public void setCapNum(int capNum) {
            this.capNum = capNum;
        }
    }
}
