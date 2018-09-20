package com.huawei.solarsafe.bean.station;

import com.huawei.solarsafe.bean.defect.StationBean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by p00229 on 2017/4/21.
 */
public class StationListBeanForPerson implements Serializable {
    private static final long serialVersionUID = -2834472403439138540L;
    private List<StationBean> stationBeans;

    public List<StationBean> getStationBeans() {
        return stationBeans;
    }

    public void setStationBeans(List<StationBean> stationBeans) {
        this.stationBeans = stationBeans;
    }
}
