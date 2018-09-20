package com.huawei.solarsafe.bean.pnlogger;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.huawei.solarsafe.bean.BaseEntity;
import com.huawei.solarsafe.bean.ServerRet;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Created date: 2016-7-22<br>
 * Created Author: p00322<br>
 * description :运维多电站数据信息类
 */
public class PntStationList extends BaseEntity{
    private List<PntStation> data;

    public List<PntStation> getData() {
        return data;
    }

    public void setData(List<PntStation> data) {
        this.data = data;
    }

    @Override
    public void preParse(JSONObject jsonObject) throws Exception {
        if (jsonObject!=null){
            boolean isSuccess = jsonObject.getBoolean(SUCCESS);
            setSuccess(isSuccess);
            if (isSuccess){
                Type type = new TypeToken<List<PntStation>>(){}.getType();
                Gson gson = new Gson();
                String jsonStr = jsonObject.getJSONArray(DATA).toString();
                data = gson.fromJson(jsonStr,type);

            }
        }
    }

    @Override
    public boolean parseJson(JSONObject jsonObject) throws Exception {
        return false;
    }


    @Override
    public void setServerRet(ServerRet serverRet) {
        //Do nothing
    }

}
