package com.huawei.solarsafe.bean.device;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by p00507
 * on 2017/9/19.
 */
public class DevLocationBean implements Serializable{
    private static final long serialVersionUID = -8291182283355950295L;
    private String id;//自己id
    private String plocId;//父id
    private String locId;//子id
    private String name;
    private int level;
    private int dataFrom;
    private String stationCode;

    public DevLocationBean p;
    public ArrayList<DevLocationBean> children;
    public boolean isChecked;
    public boolean isExpanded;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPlocId() {
        return plocId;
    }

    public void setPlocId(String plocId) {
        this.plocId = plocId;
    }

    public String getLocId() {
        return locId;
    }

    public void setLocId(String locId) {
        this.locId = locId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void setDataFrom(int dataFrom) {
        this.dataFrom = dataFrom;
    }

    public String getStationCode() {
        return stationCode;
    }

    public void setStationCode(String stationCode) {
        this.stationCode = stationCode;
    }

    public DevLocationBean getP() {
        return p;
    }

    public void setP(DevLocationBean p) {
        this.p = p;
    }

    public ArrayList<DevLocationBean> getChildren() {
        return children;
    }

    public void setChildren(ArrayList<DevLocationBean> children) {
        this.children = children;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public boolean isExpanded() {
        return isExpanded;
    }

}
