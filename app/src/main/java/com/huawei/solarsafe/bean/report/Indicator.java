package com.huawei.solarsafe.bean.report;

import java.io.Serializable;

/**
 * Created by p00507
 * on 2018/1/8.
 */

public class Indicator implements Serializable {
    private static final long serialVersionUID = 5861748599166427055L;
    private int index;
    private String item;
    private boolean isChecked;
    private boolean isDefaultChecked;

    public Indicator(int i, String s) {
        index = i;
        item = s;
    }

    public Indicator(int i, String s, boolean l) {
        index = i;
        item = s;
        isChecked = l;
    }

    public boolean isDefaultChecked() {
        return isDefaultChecked;
    }

    public void setDefaultChecked(boolean defaultChecked) {
        isDefaultChecked = defaultChecked;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    @Override
    public String toString() {
        return "Indicator{" +
                "index=" + index +
                ", item='" + item + '\'' +
                ", isChecked=" + isChecked +
                ", isDefaultChecked=" + isDefaultChecked +
                '}';
    }
}
