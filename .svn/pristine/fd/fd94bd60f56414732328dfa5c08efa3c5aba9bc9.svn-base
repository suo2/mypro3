package com.huawei.solarsafe.bean.device;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.huawei.solarsafe.bean.BaseEntity;
import com.huawei.solarsafe.bean.ServerRet;
import com.huawei.solarsafe.net.JSONReader;

import org.json.JSONObject;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by P00319 on 2017/2/20.
 */

public class DevList extends BaseEntity implements Serializable{


    private static final long serialVersionUID = 9179909804163419659L;
    private int pageCount;
    private int pageNo;
    private int pageSize;
    private int total;
    private ServerRet serverRet;

    @Override
    public boolean parseJson(JSONObject jsonObject) throws Exception {
        JSONReader jsonReader = new JSONReader(jsonObject);
        String temp = jsonReader.getString("list");
        Gson gson = new Gson();
        total = jsonReader.getInt("total");
        Type type = new TypeToken<List<DevBean>>() {
        }.getType();
        list = gson.fromJson(temp, type);
        return true;
    }
    // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0001_F03_Android_S01  删除Android的demo账号    【修改人】：zhaoyufeng

    @Override
    public void setServerRet(ServerRet serverRet) {
        this.serverRet = serverRet;
    }

    private List<DevBean> list;

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

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<DevBean> getList() {
        return list;
    }

    public void setList(List<DevBean> list) {
        this.list = list;
    }

    public ServerRet getServerRet() {
        return serverRet;
    }
}
