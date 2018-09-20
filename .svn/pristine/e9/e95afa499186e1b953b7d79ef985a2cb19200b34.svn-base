package com.huawei.solarsafe.bean.station.kpi;

import com.huawei.solarsafe.bean.BaseEntity;
import com.huawei.solarsafe.bean.ServerRet;
import com.huawei.solarsafe.bean.station.map.StationStateEnum;
import com.huawei.solarsafe.net.JSONReader;
import com.huawei.solarsafe.view.homepage.station.IClusterStationInfo;

import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by p00322 on 2017/1/4.
 * description: 单个电站基本信息获取接口解析类
 */
public class StationInfo extends BaseEntity implements Serializable, IClusterStationInfo {
    private static final String TAG = "StationInfo";
    private static final long serialVersionUID = -6702037950830929052L;
    private static final String KEY_STATIONNAME = "stationName";
    private static final String KEY_INSTALLCAPACITY = "installCap";
    private static final String KEY_GRIDCONNTIME = "gridConnTime";
    private static final String KEY_LATITUDE = "latitude";
    private static final String KEY_LONGITUDE = "longitude";
    private static final String KEY_STATIONPIC = "pic";
    private static final String KEY_PLANTADDR = "plantAddr";
    private static final String KEY_INTRODUCTION = "introduction";
    private static final String KEY_CURDAY = "curDay";
    private static final String KEY_CURYEAR = "curYear";
    private static final String KEY_CURMONTH = "curMonth";
    private static final String KEY_DATA = "data";
    private static final String KEY_DATANUM = "dataNum";
    private static final String KEY_RUNDAYS = "safeRunningDays";
    private static final String KEY_LINKMAN = "linkMan";
    private static final String KEY_LMPHO = "lmPho";
    /**
     * 电站名称
     */
    private String stationName;
    /**
     * 电站Id
     */
    private String sId;
    /**
     * 装机容量
     */
    private double installCapacity;
    /**
     * 并网时间
     */
    private long gridConnTime;
    /**
     * 电站纬度
     */
    private double latitude;
    /**
     * 电站经度
     */
    private double longitude;
    /**
     * 电站图片url
     */
    private String stationPic;
    /**
     * 电站图片Demo
     */
    private int stationPicDemo;
    /**
     * 电站地址
     */
    private String plantAddr;

    /**
     * 电站简介
     */
    private String introduction;
    /**
     * 当日发电量
     */
    private double curDay;
    /**
     * 当年发电量
     */
    private double curYear;
    /**
     * 当月发电量
     */
    private double curMonth;
    /**
     * 电站联系人
     */
    private String stationLinkMan;
    /**
     * 联系电话
     */
    private String contactPho;
    /**
     * 当前功率
     */
    private double currentPower;
    /**
     * 当日收益
     */
    private double dayProfit;
    /**
     * 统一返回码
     */
    private ServerRet retCode;
    /**
     * 电站状态
     */
    private StationStateEnum stationStateEnum = StationStateEnum.HEALTH;
    /**
     * 电站运行天数
     */
    private String safeRunningDays;
    //电站图片的更新时间
    private String picterUpdataTime;

    public String getSafeRunningDays() {
        return safeRunningDays;
    }

    public void setStationPicDemo(int stationPicDemo) {
        this.stationPicDemo = stationPicDemo;
    }

    @Override
    public StationStateEnum getStationState() {
        return stationStateEnum;
    }

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    public String getsId() {
        return sId;
    }

    public void setsId(String sId) {
        this.sId = sId;
    }

    public double getInstallCapacity() {
        return installCapacity;
    }

    public void setInstallCapacity(double installCapacity) {
        this.installCapacity = installCapacity;
    }

    public long getGridConnTime() {
        return gridConnTime;
    }


    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getStationPic() {
        return stationPic;
    }

    public void setStationPic(String stationPic) {
        this.stationPic = stationPic;
    }

    public String getPlantAddr() {
        return plantAddr;
    }

    public void setPlantAddr(String plantAddr) {
        this.plantAddr = plantAddr;
    }

    public String getIntroduction() {
        return introduction;
    }


    public double getCurDay() {
        return curDay;
    }

    public void setCurDay(double curDay) {
        this.curDay = curDay;
    }

    public double getCurYear() {
        return curYear;
    }

    public void setCurYear(double curYear) {
        this.curYear = curYear;
    }

    public double getCurMonth() {
        return curMonth;
    }

    public void setCurMonth(double curMonth) {
        this.curMonth = curMonth;
    }

    public void setStationStateEnum(StationStateEnum stationStateEnum) {
        this.stationStateEnum = stationStateEnum;
    }

    public static String getTAG() {
        return TAG;
    }

    public String getStationLinkMan() {
        return stationLinkMan;
    }

    public void setStationLinkMan(String stationLinkMan) {
        this.stationLinkMan = stationLinkMan;
    }

    public String getContactPho() {
        return contactPho;
    }

    public void setContactPho(String contactPho) {
        this.contactPho = contactPho;
    }

    public double getCurrentPower() {
        return currentPower;
    }

    public void setCurrentPower(double currentPower) {
        this.currentPower = currentPower;
    }

    public void setDayProfit(double dayProfit) {
        this.dayProfit = dayProfit;
    }

    public ServerRet getRetCode() {
        return retCode;
    }

    public String getPictureUpdataTime() {
        return picterUpdataTime;
    }

    public void setPictureUpdataTime(String picterUpdataTime) {
        this.picterUpdataTime = picterUpdataTime;
    }

    @Override
    public boolean parseJson(JSONObject jsonObject) throws Exception {
        if (jsonObject == null) {
            return false;
        }
        JSONReader jsonReader = new JSONReader(jsonObject);
        JSONReader jsonReader1 = new JSONReader(jsonReader.getJSONObject(KEY_DATA));
        JSONReader jsonReader2 = new JSONReader(jsonReader.getJSONObject(KEY_DATANUM));
        stationName = jsonReader1.getString(KEY_STATIONNAME);
        plantAddr = jsonReader1.getString(KEY_PLANTADDR);
        latitude = jsonReader1.getDouble(KEY_LATITUDE);
        longitude = jsonReader1.getDouble(KEY_LONGITUDE);
        installCapacity = jsonReader1.getDouble(KEY_INSTALLCAPACITY);
        gridConnTime = jsonReader1.getLong(KEY_GRIDCONNTIME);
        introduction = jsonReader1.getString(KEY_INTRODUCTION);
        stationPic = jsonReader1.getString(KEY_STATIONPIC);
        safeRunningDays = jsonReader1.getString(KEY_RUNDAYS);
        curDay = jsonReader2.getDouble(KEY_CURDAY);
        curMonth = jsonReader2.getDouble(KEY_CURMONTH);
        curYear = jsonReader2.getDouble(KEY_CURYEAR);
        stationLinkMan = jsonReader1.getString(KEY_LINKMAN);
        contactPho = jsonReader1.getString(KEY_LMPHO);
        return true;
    }
    // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0001_F03_Android_S01  删除Android的demo账号    【修改人】：zhaoyufeng

    @Override
    public void setServerRet(ServerRet serverRet) {
        retCode = serverRet;
    }

    @Override
    public String toString() {
        return "StationInfo{" +
                "stationName='" + stationName + '\'' +
                ", sId='" + sId + '\'' +
                ", installCapacity=" + installCapacity +
                ", gridConnTime=" + gridConnTime +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", stationPic='" + stationPic + '\'' +
                ", stationPicDemo=" + stationPicDemo +
                ", plantAddr='" + plantAddr + '\'' +
                ", introduction='" + introduction + '\'' +
                ", curDay=" + curDay +
                ", curYear=" + curYear +
                ", curMonth=" + curMonth +
                ", stationLinkMan='" + stationLinkMan + '\'' +
                ", contactPho='" + contactPho + '\'' +
                ", currentPower=" + currentPower +
                ", dayProfit=" + dayProfit +
                ", retCode=" + retCode +
                ", stationStateEnum=" + stationStateEnum +
                '}';
    }
}
