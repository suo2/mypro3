package com.huawei.solarsafe.bean.patrol;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.huawei.solarsafe.bean.BaseEntity;
import com.huawei.solarsafe.bean.ServerRet;
import com.huawei.solarsafe.bean.defect.WorkerBean;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by p00319 on 2017/3/2.
 */

public class PatrolMapInfo extends BaseEntity {

    private Gson gson = new Gson();


    private List<WorkerBean> workerList = new ArrayList<>();

    private List<PatrolStationBean> stationList = new ArrayList<>();

    @Override
    public boolean parseJson(JSONObject jsonObject) throws Exception {
        String temp = jsonObject.getJSONArray("stationList").toString();
        Type type = new TypeToken<List<PatrolStationBean>>() {
        }.getType();
        stationList = gson.fromJson(temp, type);
        if (stationList==null || stationList.size()==0){
            return false;
        }

        JSONObject state = jsonObject.getJSONObject("stationHealthState");
        for (PatrolStationBean sBean : stationList) {
            sBean.setStationHealthState(Integer.valueOf(state.getString(sBean.getStationCode())));
        }
        temp = jsonObject.getJSONArray("userList").toString();
        type = new TypeToken<List<WorkerBean>>() {
        }.getType();
        workerList = gson.fromJson(temp, type);
        return true;
    }
    // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0001_F03_Android_S01  删除Android的demo账号    【修改人】：zhaoyufeng

    @Override
    public void setServerRet(ServerRet serverRet) {

    }

    public List<PatrolStationBean> getStationList() {
        return stationList;
    }

    public void setStationList(List<PatrolStationBean> stationList) {
        this.stationList = stationList;
    }

    public List<WorkerBean> getWorkerList() {
        return workerList;
    }

}
