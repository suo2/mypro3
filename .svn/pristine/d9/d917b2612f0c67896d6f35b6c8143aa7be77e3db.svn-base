package com.huawei.solarsafe.bean.stationmagagement;

import com.huawei.solarsafe.bean.BaseEntity;
import com.huawei.solarsafe.bean.ServerRet;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * <pre>
 *     author: Tzy
 *     time  : $date$
 *     desc  : 查询设备返回信息集合实体类
 * </pre>
 */
public class DevInfoListBean extends BaseEntity{

    private ArrayList<DevInfo> devInfoArrayList;

    @Override
    public boolean parseJson(JSONObject jsonObject) throws Exception {
        if (jsonObject==null){
            return false;
        }

        //修复解析设备下挂设备数据为空bug
        devInfoArrayList=new ArrayList<>();
        JSONArray jsonArray=jsonObject.getJSONArray("data");
        for (int i=0;i<jsonArray.length();i++){
            DevInfo devInfo=new DevInfo();
            devInfo.parseJson(jsonArray.getJSONObject(i));
            devInfoArrayList.add(devInfo);
        }
        return true;
    }


    @Override
    public void setServerRet(ServerRet serverRet) {

    }

    public ArrayList<DevInfo> getData() {
        return devInfoArrayList;
    }

    public void setData(ArrayList<DevInfo> data) {
        this.devInfoArrayList = data;
    }
}
