package com.huawei.solarsafe.bean.defect;

import com.huawei.solarsafe.utils.customview.treeview.TreeNodeId;
import com.huawei.solarsafe.utils.customview.treeview.TreeNodeLabel;
import com.huawei.solarsafe.utils.customview.treeview.TreeNodePid;

import java.io.Serializable;

/**
 * Created by P00319 on 2017/2/20.
 */

public class StationBean implements Serializable {


    private static final long serialVersionUID = 3677841014356910541L;
    @TreeNodeId
    private String id = "-1";
    @TreeNodePid
    private String pid = "0";
    @TreeNodeLabel
    private String name;

    /**
     * STATION OR DOMAIN
     */
    private String model;
    /**
     * check : null
     * text : null
     * level : null
     * sort : null
     * childs : null
     * url : null
     */

    private String check;
    private String text;
    private String level;
    private String sort;
    private String url;
    private String supportPoor;

    public String getSupportPoor() {
        return supportPoor;
    }

    public void setSupportPoor(String supportPoor) {
        this.supportPoor = supportPoor;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid == null ? "0" : pid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getCheck() {
        return check;
    }

    public void setCheck(String check) {
        this.check = check;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }


    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "StationBean{" +
                "text='" + text + '\'' +
                ", check='" + check + '\'' +
                ", model='" + model + '\'' +
                ", name='" + name + '\'' +
                ", pid='" + pid + '\'' +
                ", id='" + id + '\'' +
                '}';
    }
}
