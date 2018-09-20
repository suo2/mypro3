package com.huawei.solarsafe.bean.stationmagagement;

import java.util.List;

/**
 * Created by p00229 on 2017/6/8.
 */

public class UpdataPriceReq {


    private String stationCode;

    private List<PriceTotalBean> priceTotal;

    public String getStationCode() {
        return stationCode;
    }

    public void setStationCode(String stationCode) {
        this.stationCode = stationCode;
    }

    public void setPriceTotal(List<PriceTotalBean> priceTotal) {
        this.priceTotal = priceTotal;
    }

    public static class PriceTotalBean {

        private PriceBean price;

        private List<ItemsBean> items;

        public PriceBean getPrice() {
            return price;
        }

        public void setPrice(PriceBean price) {
            this.price = price;
        }

        public List<ItemsBean> getItems() {
            return items;
        }

        public void setItems(List<ItemsBean> items) {
            this.items = items;
        }

        public static class PriceBean {
            private long beginDate;
            private long endDate;
            private String domainId;
            private String stationCode;

            public void setBeginDate(long beginDate) {
                this.beginDate = beginDate;
            }

            public long getEndDate() {
                return endDate;
            }

            public void setEndDate(long endDate) {
                this.endDate = endDate;
            }

            public String getDomainId() {
                return domainId;
            }

            public void setDomainId(String domainId) {
                this.domainId = domainId;
            }

            public String getStationCode() {
                return stationCode;
            }

            public void setStationCode(String stationCode) {
                this.stationCode = stationCode;
            }
        }

        public static class ItemsBean {
            private String beginHour;
            private String endHour;
            private String price;


            public void setBeginHour(String beginHour) {
                this.beginHour = beginHour;
            }

            public void setEndHour(String endHour) {
                this.endHour = endHour;
            }

            public String getPrice() {
                return price;
            }

            public void setPrice(String price) {
                this.price = price;
            }
        }
    }
}
