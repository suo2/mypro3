package com.huawei.solarsafe.bean.defect;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.huawei.solarsafe.bean.BaseEntity;
import com.huawei.solarsafe.bean.ServerRet;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by p00319 on 2017/3/17.
 */

public class MapTodoInfo extends BaseEntity {
    private int total;
    private List<MapTodoBean> todoList = new ArrayList<>();
    private WorkerBean data;


    @Override
    public boolean parseJson(JSONObject jsonObject) throws Exception {
        total = jsonObject.getInt("total");
        Gson gson = new Gson();
        Type type = new TypeToken<List<MapTodoBean>>() {
        }.getType();
        todoList = gson.fromJson(jsonObject.getString("list"), type);
        return true;
    }
    // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0001_F03_Android_S01  删除Android的demo账号    【修改人】：zhaoyufeng

    @Override
    public void setServerRet(ServerRet serverRet) {

    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<MapTodoBean> getTodoList() {
        return todoList;
    }

    public WorkerBean getData() {
        return data;
    }

    public void setData(WorkerBean data) {
        this.data = data;
    }
}
