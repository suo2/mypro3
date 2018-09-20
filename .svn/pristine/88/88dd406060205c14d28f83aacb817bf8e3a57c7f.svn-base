package com.huawei.solarsafe.bean.pnlogger;

import com.google.gson.Gson;

import java.util.List;

/**
 * Create Date: 2017/3/7
 * Create Author: P00028
 * Description :
 */
public class CreateStationArgs {

    private StationBean station;


    private List<String> esnlist;


    private List<PricetotalBean> pricetotal;


    private List<CameraInfoListBean> cameraInfoList;

    public StationBean getStation() {
        return station;
    }

    public void setStation(StationBean station) {
        this.station = station;
    }

    public List<String> getEsnlist() {
        return esnlist;
    }

    public void setEsnlist(List<String> esnlist) {
        this.esnlist = esnlist;
    }


    public void setPricetotal(List<PricetotalBean> pricetotal) {
        this.pricetotal = pricetotal;
    }


    public void setCameraInfoList(List<CameraInfoListBean> cameraInfoList) {
        this.cameraInfoList = cameraInfoList;
    }

    public class StationBean {
        private String stationName = "";
        //装机容量
        private String capacity = "";
        private String introduction = "";
        //户主
        private String contact = "";
        private String phone = "";
        //并网类型
        private String combinedType = "";
        //时区
        private String timeZone = "";
        private String longtitude = "";
        private String latitude = "";
        //并网时间
        private String gridTime = "";
        //电站状态
        private String stationStatus = "";
        //域
        private String domainId = "";
        //扶贫类型
        private String aidType = "";
        //图片
        private String image = "";

        public String getStationName() {
            return stationName;
        }

        public void setStationName(String stationName) {
            this.stationName = stationName;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }


        public String getCapacity() {
            return capacity;
        }

        public void setCapacity(String capacity) {
            this.capacity = capacity;
        }


        public void setIntroduction(String introduction) {
            this.introduction = introduction;
        }


        public void setContact(String contact) {
            this.contact = contact;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }


        public void setCombinedType(String combinedType) {
            this.combinedType = combinedType;
        }

        public String getTimeZone() {
            return timeZone;
        }

        public void setTimeZone(String timeZone) {
            this.timeZone = timeZone;
        }


        public void setLongtitude(String longtitude) {
            this.longtitude = longtitude;
        }

        public String getLatitude() {
            return latitude;
        }

        public void setLatitude(String latitude) {
            this.latitude = latitude;
        }


        public void setGridTime(String gridTime) {
            this.gridTime = gridTime;
        }


        public void setStationStatus(String stationStatus) {
            this.stationStatus = stationStatus;
        }

        public String getDomainId() {
            return domainId;
        }

        public void setDomainId(String domainId) {
            this.domainId = domainId;
        }

        public void setAidType(String aidType) {
            this.aidType = aidType;
        }
    }

    public class CameraInfoListBean {
        private String name = "";
        private String ip = "";
        private String username = "";
        private String password;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getIp() {
            return ip;
        }

        public void setIp(String ip) {
            this.ip = ip;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }

    public String getJsonArgs(){
        Gson gson = new Gson();
        String value = gson.toJson(this);
        return value;
    }
}