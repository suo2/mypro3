package com.huawei.solarsafe.bean.patrol;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.ClusterItem;
import com.huawei.solarsafe.bean.station.map.StationStateEnum;

/**
 * Created by p00319 on 2017/3/2.
 */

public class PatrolStationBean implements ClusterItem {


    private long createDate;
    private String createUser;
    private long updateDate;
    private String updateUser;
    private String stationCode;
    private long id;
    private String stationName;
    private double capacity;
    private double longitude;
    private double latitude;
    private String stationAddr;
    private int aidType;
    private long domainId;
    private int timeZone;
    private String dbShardingId;
    private String tableShardingId;
    private int stationHealthState;

    public StationStateEnum getStationState() {
        StationStateEnum stationStateEnum;
        if (stationHealthState == 1) {
            stationStateEnum = StationStateEnum.FAULTCHAIN;
        } else if (stationHealthState == 2) {
            stationStateEnum = StationStateEnum.EXCEPTION;
        } else {
            stationStateEnum = StationStateEnum.HEALTH;
        }
        return stationStateEnum;
    }

    public long getCreateDate() {
        return createDate;
    }

    public void setCreateDate(long createDate) {
        this.createDate = createDate;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public long getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(long updateDate) {
        this.updateDate = updateDate;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    public String getStationCode() {
        return stationCode;
    }

    public void setStationCode(String stationCode) {
        this.stationCode = stationCode;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    public double getCapacity() {
        return capacity;
    }

    public void setCapacity(double capacity) {
        this.capacity = capacity;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public String getStationAddr() {
        return stationAddr;
    }

    public void setAidType(int aidType) {
        this.aidType = aidType;
    }


    public long getDomainId() {
        return domainId;
    }

    public void setDomainId(long domainId) {
        this.domainId = domainId;
    }


    public int getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(int timeZone) {
        this.timeZone = timeZone;
    }

    public String getDbShardingId() {
        return dbShardingId;
    }

    public void setDbShardingId(String dbShardingId) {
        this.dbShardingId = dbShardingId;
    }

    public String getTableShardingId() {
        return tableShardingId;
    }

    public void setTableShardingId(String tableShardingId) {
        this.tableShardingId = tableShardingId;
    }

    public void setStationHealthState(int stationHealthState) {
        if (Integer.MIN_VALUE == stationHealthState) {
            this.setStationHealthState(1);
        } else {
            this.stationHealthState = stationHealthState;
        }
    }

    public int getStationHealthState() {
        return stationHealthState;
    }

    @Override
    public LatLng getPosition() {
        return new LatLng(latitude,longitude);
    }


    public String getTitle() {
        return "";
    }


    public String getSnippet() {
        return "";
    }
}
