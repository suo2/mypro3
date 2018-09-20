package com.huawei.solarsafe.bean.stationmagagement;

/**
 * Created by p00229 on 2017/6/6.
 */

public class ChangeStationBaseReq {



    private StationBean station;

    private String type;

    public StationBean getStation() {
        return station;
    }

    public void setStation(StationBean station) {
        this.station = station;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public static class StationBean {
        private String id;
        private String stationName;
        private String capacity;
        private String contact;
        private String phone;
        private String combinedType;
        private String gridTime;
        private String stationStatus;
        private String domainId;
        private String aidType;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getStationName() {
            return stationName;
        }

        public void setStationName(String stationName) {
            this.stationName = stationName;
        }

        public String getCapacity() {
            return capacity;
        }

        public void setCapacity(String capacity) {
            this.capacity = capacity;
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
}
