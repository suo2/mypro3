package com.huawei.solarsafe.bean.patrol;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.huawei.solarsafe.bean.BaseEntity;
import com.huawei.solarsafe.bean.ServerRet;
import com.huawei.solarsafe.utils.JSONReader;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Create Date: 2017/3/6
 * Create Author: P00171
 * Description :
 */
public class PatrolObjListForCreateTask extends BaseEntity {
    private List<PatrolObjForCreateTask> list;

    public List<PatrolObjForCreateTask> getList() {
        return list;
    }

    public void setList(List<PatrolObjForCreateTask> list) {
        this.list = list;
    }

    @Override
    public boolean parseJson(JSONObject jsonObject) throws Exception {
        list = new ArrayList<>();
        JSONReader jsonReader = new JSONReader(jsonObject);
        Gson gson = new Gson();
        Type type = new TypeToken<List<PatrolObjForCreateTask>>() {
        }.getType();
        list = gson.fromJson(jsonReader.getString("list"),type);
        return true;
    }

    @Override
    public void setServerRet(ServerRet serverRet) {

    }
}
