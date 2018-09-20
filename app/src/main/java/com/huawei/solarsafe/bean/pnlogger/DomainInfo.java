package com.huawei.solarsafe.bean.pnlogger;

import java.io.Serializable;
import java.util.List;

/**
 * Create Date: 2017/3/9
 * Create Author: P00028
 * Description :
 */
public class DomainInfo implements Serializable {
    private static final long serialVersionUID = -3813440430956442639L;
    private String check;
    private String id;
    private int isPoor;
    private String level;
    private String model;
    private String name;
    private String pid;
    private String sort;
    private String text;

    private List<DomainInfo> childDomains;

    public String getCheck() {
        return check;
    }

    public void setCheck(String check) {
        this.check = check;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<DomainInfo> getChildDomains() {
        return childDomains;
    }

    public void setChildDomains(List<DomainInfo> childDomains) {
        this.childDomains = childDomains;
    }

    @Override
    public String toString() {
        return "DomainInfo{" +
                "check='" + check + '\'' +
                ", id='" + id + '\'' +
                ", isPoor=" + isPoor +
                ", level='" + level + '\'' +
                ", model='" + model + '\'' +
                ", name='" + name + '\'' +
                ", pid='" + pid + '\'' +
                ", sort='" + sort + '\'' +
                ", text='" + text + '\'' +
                ", childDomains=" + childDomains +
                '}';
    }
}