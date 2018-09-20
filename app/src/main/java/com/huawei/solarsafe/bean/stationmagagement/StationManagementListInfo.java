package com.huawei.solarsafe.bean.stationmagagement;

import com.huawei.solarsafe.bean.BaseEntity;
import com.huawei.solarsafe.bean.ServerRet;

import org.json.JSONObject;

/**
 * Created by p00229 on 2017/5/18.
 */

public class StationManagementListInfo extends BaseEntity {
    private StationManegementList stationManegementList;

    public StationManegementList getStationManegementList() {
        return stationManegementList;
    }

    public void setStationManegementList(StationManegementList stationManegementList) {
        this.stationManegementList = stationManegementList;
    }

    @Override
    public boolean parseJson(JSONObject jsonObject) throws Exception {
        return false;
    }

    @Override
    public void setServerRet(ServerRet serverRet) {

    }
}
