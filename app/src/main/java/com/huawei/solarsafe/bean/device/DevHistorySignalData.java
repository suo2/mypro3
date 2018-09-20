package com.huawei.solarsafe.bean.device;

/**
 * Created by p00507
 * on 2017/4/11.
 */

public class DevHistorySignalData  {
    private String id;//信号点id   signalCodes
    private String  name;//信号点名称
    private String  pid;
    private String  check;
    private String text;
    private String  level;
    private String sort;
    private String  model;
    private String unit;//信号点单位


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }


    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getCheck() {
        return check;
    }

    public void setCheck(String check) {
        this.check = check;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "DevHistorySignalData{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", pid='" + pid + '\'' +
                ", check='" + check + '\'' +
                ", text='" + text + '\'' +
                ", level='" + level + '\'' +
                ", sort='" + sort + '\'' +
                ", model='" + model + '\'' +
                ", unit='" + unit + '\'' +
                '}';
    }
}
