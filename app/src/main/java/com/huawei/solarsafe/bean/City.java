package com.huawei.solarsafe.bean;

/**
 * Created by p00507
 * on 2017/9/21.
 */

public class City {
    private String cityName;
    private String firstLetter;
    private boolean isHead;

    public boolean isHead() {
        return isHead;
    }

    public void setIsHead(boolean isHead) {
        this.isHead = isHead;
    }

    public String getCityName() {
        return cityName;
    }

    public String getFirstLetter() {
        return firstLetter;
    }
}
