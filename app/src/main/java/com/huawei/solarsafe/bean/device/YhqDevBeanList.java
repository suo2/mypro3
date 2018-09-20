package com.huawei.solarsafe.bean.device;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by P00229 on 2017/8/23.
 */

public class YhqDevBeanList implements Serializable {
    private static final long serialVersionUID = 986684920518902393L;
    private ArrayList<DevBean> yhqDevList;

    public ArrayList<DevBean> getYhqDevList() {
        return yhqDevList;
    }

    public void setYhqDevList(ArrayList<DevBean> yhqDevList) {
        this.yhqDevList = yhqDevList;
    }
}
