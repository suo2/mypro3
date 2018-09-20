package com.huawei.solarsafe.bean.patrol;

import android.util.LongSparseArray;

import com.amap.api.maps.model.LatLng;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.huawei.solarsafe.bean.BaseEntity;
import com.huawei.solarsafe.bean.ServerRet;
import com.huawei.solarsafe.bean.defect.WorkerBean;
import com.huawei.solarsafe.net.JSONReader;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by p00319 on 2017/3/4.
 */

public class InspectTaskDetailList extends BaseEntity {

    private List<InspectTaskDetail> taskDetailList = new ArrayList<>();

    private LongSparseArray<List<LatLng>> userList = new LongSparseArray<>();
    private String currentTaskId;
    private Gson gson = new Gson();

    @Override
    public boolean parseJson(JSONObject jsonObject) throws Exception {

        JSONReader dataReader = new JSONReader(jsonObject);

        String temp;
        Type type;
        currentTaskId = dataReader.getString("currentTaskId");
        JSONArray array = dataReader.getJSONArray("taskDetailList");
        for (int i = 0; i < array.length(); i++) {
            InspectTaskDetail detail = new InspectTaskDetail();
            JSONReader reader = new JSONReader(array.getJSONObject(i));
            temp = reader.getString("detail");
            type = new TypeToken<InspectTaskDetail.DetailInfo>() {
            }.getType();
            InspectTaskDetail.DetailInfo info = gson.fromJson(temp, type);
            temp = reader.getString("station");
            type = new TypeToken<PatrolStationBean>() {
            }.getType();
            PatrolStationBean stationBean = gson.fromJson(temp, type);
            detail.setStationInfo(stationBean);
            detail.setDetailInfo(info);
            taskDetailList.add(detail);
        }

        JSONReader tempReader = new JSONReader(dataReader.getJSONObject("stationHealthState"));
        for (InspectTaskDetail detail : taskDetailList) {
            if(detail.getStationInfo() != null){
                detail.getStationInfo().setStationHealthState(tempReader.getInt(detail.getStationInfo().getStationCode()));
            }
        }

        temp = dataReader.getString("userList");
        type = new TypeToken<List<WorkerBean>>() {
        }.getType();
        if (!"".equals(temp)) {
            List<WorkerBean> workerList = gson.fromJson(temp, type);
            JSONObject obj = dataReader.getJSONObject("locationMap");
            type = new TypeToken<List<UserLocation>>() {
            }.getType();
            if (obj.length() > 0) {
                for (WorkerBean worker : workerList) {
                    temp = obj.getString(String.valueOf(worker.getUserid()));
                    List<UserLocation> posList = gson.fromJson(temp, type);
                    List<LatLng> latLngList = new ArrayList<>();
                    for (UserLocation bean : posList) {
                        LatLng latLng = new LatLng(bean.getLatitude(), bean.getLongitude());
                        latLngList.add(latLng);
                    }
                    userList.put(worker.getUserid(), latLngList);
                }
            }
        }

        return true;
    }
    // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0001_F03_Android_S01  删除Android的demo账号    【修改人】：zhaoyufeng

    @Override
    public void setServerRet(ServerRet serverRet) {

    }

    public List<InspectTaskDetail> getTaskDetailList() {
        return taskDetailList;
    }


    public LongSparseArray<List<LatLng>> getUserList() {
        return userList;
    }


    public class UserLocation {
        private double longitude;
        private double latitude;

        public void setLatitude(double latitude) {
            this.latitude = latitude;
        }

        public double getLatitude() {
            return latitude;
        }

        public void setLongitude(double longitude) {
            this.longitude = longitude;
        }

        public double getLongitude() {
            return longitude;
        }
    }

    public String getCurrentTaskId() {
        return currentTaskId;
    }

    public void setCurrentTaskId(String currentTaskId) {
        this.currentTaskId = currentTaskId;
    }
}
