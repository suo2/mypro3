package com.huawei.solarsafe.bean.device;

import com.google.gson.Gson;
import com.huawei.solarsafe.bean.BaseEntity;
import com.huawei.solarsafe.bean.ServerRet;
import com.google.gson.reflect.TypeToken;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.List;


/**
 * Created by P00708 on 2018/3/5.
 */

public class BoosterDevListInfo extends BaseEntity {

    private List<BoosterDevBean> list;
    private int pageCount;
    private int pageNo;
    private int pageSize;
    private int total;
    @Override
    public boolean parseJson(JSONObject jsonObject) throws Exception {

        String temp = jsonObject.getString("list");
        Gson gson = new Gson();
        total = jsonObject.getInt("total");
        Type type = new TypeToken<List<BoosterDevBean>>() {
        }.getType();
        list = gson.fromJson(temp, type);
        return true;
    }

    @Override
    public void setServerRet(ServerRet serverRet) { }

    public List<BoosterDevBean> getList() {
        return list;
    }

    public void setList(List<BoosterDevBean> list) {
        this.list = list;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}
