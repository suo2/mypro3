package com.huawei.solarsafe.bean.patrol;

import com.huawei.solarsafe.bean.station.map.StationStateEnum;
import com.huawei.solarsafe.view.homepage.station.IClusterStationInfo;

import java.io.Serializable;

/**
 * Created by p00213 on 2017/1/9.
 */
public class PatrolObj implements Serializable, IClusterStationInfo
{

    private static final long serialVersionUID = 1L;
    // 电站编号
    private String sId;
    // 电站名称
    private String sName;
    // 巡检对象名称
    private String annexObjName;
    // 最后巡检时间
    private long lastAnnexTime;
    // 状态码
    private int annexStatus;
    //经度
    private double longitude;
    //纬度
    private double latitude;
    //是否为户用电站
    private boolean mIsHouseHold = false;
    //巡检次数
    private int inspectCount;
    //上次巡检人员
    private String lastInspectPerson;
    //距离上次巡检的天数
    private int lastInspectDay;
    //上次巡检结果
    private int lastInspectResult;

    private String remark;
    private String taskId;

    public PatrolObj() {
        super();
    }

    public String getsId()
    {
        return sId;
    }

    public void setsId(String sId)
    {
        this.sId = sId;
    }

    public String getsName()
    {
        return sName;
    }

    public void setsName(String sName)
    {
        this.sName = sName;
    }


    public String getAnnexObjName()
    {
        return annexObjName;
    }

    public void setAnnexObjName(String annexObjName)
    {
        this.annexObjName = annexObjName;
    }

    public long getLastAnnexTime()
    {
        return lastAnnexTime;
    }

    public void setLastAnnexTime(long lastAnnexTime)
    {
        this.lastAnnexTime = lastAnnexTime;
    }


    public void setAnnexStatus(int annexStatus)
    {
        this.annexStatus = annexStatus;
    }

    public double getLongitude()
    {
        return longitude;
    }

    public void setLongitude(double longitude)
    {
        this.longitude = longitude;
    }

    public double getLatitude()
    {
        return latitude;
    }

    public void setLatitude(double latitude)
    {
        this.latitude = latitude;
    }


    public boolean isHouseHold(){
        return mIsHouseHold;
    }


    public String getRemark()
    {
        return remark;
    }


    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }


    public int getInspectCount() {
        return inspectCount;
    }

    public void setInspectCount(int inspectCount) {
        this.inspectCount = inspectCount;
    }

    public String getLastInspectPerson() {
        return lastInspectPerson;
    }

    public void setLastInspectPerson(String lastInspectPerson) {
        this.lastInspectPerson = lastInspectPerson;
    }

    public int getLastInspectDay() {
        return lastInspectDay;
    }

    public void setLastInspectDay(int lastInspectDay) {
        this.lastInspectDay = lastInspectDay;
    }

    public int getLastInspectResult() {
        return lastInspectResult;
    }

    public void setLastInspectResult(int lastInspectResult) {
        this.lastInspectResult = lastInspectResult;
    }

    @Override
    public String toString() {
        return "PatrolObj{" +
                "sId='" + sId + '\'' +
                ", sName='" + sName + '\'' +
                ", annexObjName='" + annexObjName + '\'' +
                ", lastAnnexTime=" + lastAnnexTime +
                ", annexStatus=" + annexStatus +
                ", longitude=" + longitude +
                ", latitude=" + latitude +
                ", mIsHouseHold=" + mIsHouseHold +
                ", remark='" + remark + '\'' +
                ", taskId='" + taskId + '\'' +
                '}';
    }

    @Override
    public StationStateEnum getStationState() {
        return StationStateEnum.HEALTH;
    }

    @Override
    public String getStationName() {
        return sName;
    }

}
