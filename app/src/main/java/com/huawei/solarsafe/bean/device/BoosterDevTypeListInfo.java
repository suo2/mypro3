package com.huawei.solarsafe.bean.device;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.huawei.solarsafe.bean.BaseEntity;
import com.huawei.solarsafe.bean.ServerRet;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by P00708 on 2018/3/6.
 */

public class BoosterDevTypeListInfo extends BaseEntity {


    private List<BoosterDevTypeBean> list;

    @Override
    public boolean parseJson(JSONObject jsonObject) throws Exception {

        String temp = jsonObject.getString("data");
        Gson gson = new Gson();
        Type type = new TypeToken<List<BoosterDevTypeBean>>() {
        }.getType();
        if(TextUtils.isEmpty(temp)){
            list = new ArrayList<>();
        }else{
            list = gson.fromJson(temp, type);
        }
        return true;
    }

    @Override
    public void setServerRet(ServerRet serverRet) { }

    public List<BoosterDevTypeBean> getList() {
        return list;
    }

    public void setList(List<BoosterDevTypeBean> list) {
        this.list = list;
    }
}
