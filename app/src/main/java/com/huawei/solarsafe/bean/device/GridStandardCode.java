package com.huawei.solarsafe.bean.device;

import java.io.Serializable;

/**
 * Created by p00229 on 2017/7/4.
 */

public class GridStandardCode implements Serializable {

    private static final long serialVersionUID = 7303670802626888690L;
    private String description;
    private double fn;
    private boolean haveNLine;
    private String invCodeRela;
    private boolean isShow;
    private String location;
    private String name;
    private String proRelaOF;//过频
    private String proRelaOV;//过压
    private String proRelaUF;//欠频
    private String proRelaUV;//欠压
    private boolean tenMinuOVProV1;//十分钟过压
    private int value;
    private double vn;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getFn() {
        return fn;
    }

    public void setFn(double fn) {
        this.fn = fn;
    }

    public void setHaveNLine(boolean haveNLine) {
        this.haveNLine = haveNLine;
    }


    public void setInvCodeRela(String invCodeRela) {
        this.invCodeRela = invCodeRela;
    }

    public boolean isIsShow() {
        return isShow;
    }

    public void setIsShow(boolean isShow) {
        this.isShow = isShow;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProRelaOF() {
        return proRelaOF;
    }

    public void setProRelaOF(String proRelaOF) {
        this.proRelaOF = proRelaOF;
    }

    public String getProRelaOV() {
        return proRelaOV;
    }

    public void setProRelaOV(String proRelaOV) {
        this.proRelaOV = proRelaOV;
    }

    public String getProRelaUF() {
        return proRelaUF;
    }

    public void setProRelaUF(String proRelaUF) {
        this.proRelaUF = proRelaUF;
    }

    public String getProRelaUV() {
        return proRelaUV;
    }

    public void setProRelaUV(String proRelaUV) {
        this.proRelaUV = proRelaUV;
    }


    public void setTenMinuOVProV1(boolean tenMinuOVProV1) {
        this.tenMinuOVProV1 = tenMinuOVProV1;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public double getVn() {
        return vn;
    }

    public void setVn(double vn) {
        this.vn = vn;
    }
}
