package com.huawei.solarsafe.bean.station.kpi;

import java.util.List;

/**
 * Created by P00229 on 2017/2/21.
 */
public class WeatherInfo {

    private String status;

    private List<WeatherBean> weather;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<WeatherBean> getWeather() {
        return weather;
    }


    public static class WeatherBean {

        private NowBean now;
        private String city_name;
        private List<FutureBean> future;

        public NowBean getNow() {
            return now;
        }

        public void setNow(NowBean now) {
            this.now = now;
        }

        public List<FutureBean> getFuture() {
            return future;
        }

        public String getCity_name() {
            return city_name;
        }

        public void setCity_name(String city_name) {
            this.city_name = city_name;
        }


        public static class NowBean {
            private String text; //天气描述   --- 多云，小雨等
            private String code;
            private String temperature;//温度
            private String visibility;

            public String getText() {
                return text;
            }

            public void setText(String text) {
                this.text = text;
            }

            public String getCode() {
                return code;
            }

            public void setCode(String code) {
                this.code = code;
            }

            public String getTemperature() {
                return temperature;
            }

            public void setTemperature(String temperature) {
                this.temperature = temperature;
            }

            public String getVisibility() {
                return visibility;
            }

            public void setVisibility(String visibility) {
                this.visibility = visibility;
            }

        }

        public static class FutureBean {
            private String date;
            private String day;
            private String text;
            private String high;
            private String low;
            private String wind;

            public String getDate() {
                return date;
            }

            public void setDate(String date) {
                this.date = date;
            }

            public String getDay() {
                return day;
            }

            public void setDay(String day) {
                this.day = day;
            }

            public String getText() {
                return text;
            }

            public void setText(String text) {
                this.text = text;
            }

            public String getHigh() {
                return high;
            }

            public String getLow() {
                return low;
            }

            public String getWind() {
                return wind;
            }

        }
    }
}
