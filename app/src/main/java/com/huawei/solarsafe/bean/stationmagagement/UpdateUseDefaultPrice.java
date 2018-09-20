package com.huawei.solarsafe.bean.stationmagagement;

/**
 * Created by p00229 on 2017/6/14.
 */

public class UpdateUseDefaultPrice {


    private String type;

    private StationBean station;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public StationBean getStation() {
        return station;
    }

    public void setStation(StationBean station) {
        this.station = station;
    }

    public static class StationBean {
        private String id;
        private String useDefaultPrice;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }


        public void setUseDefaultPrice(String useDefaultPrice) {
            this.useDefaultPrice = useDefaultPrice;
        }
    }
}
