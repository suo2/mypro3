package com.huawei.solarsafe.bean.user.info;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.huawei.solarsafe.bean.BaseEntity;
import com.huawei.solarsafe.bean.ServerRet;
import com.huawei.solarsafe.net.JSONReader;

import org.json.JSONObject;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.List;

public class LatestLoginInfo extends BaseEntity implements Serializable{

    private static final long serialVersionUID = 8170519347183887836L;
    private int pageCount;
    private int pageNo;
    private int pageSize;
    private int total;
    private ServerRet serverRet;
    private List<LatestLoginInfoBean> list;

    @Override
    public boolean parseJson(JSONObject jsonObject) throws Exception {
        JSONReader jsonReader = new JSONReader(jsonObject);
        String temp = jsonReader.getString("list");
        Gson gson = new Gson();
        total = jsonReader.getInt("total");
        Type type = new TypeToken<List<LatestLoginInfoBean>>() {
        }.getType();
        list = gson.fromJson(temp, type);
        return true;
    }

    @Override
    public void setServerRet(ServerRet serverRet) {
        this.serverRet = serverRet;
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

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<LatestLoginInfoBean> getList() {
        return list;
    }

    public void setList(List<LatestLoginInfoBean> list) {
        this.list = list;
    }

    public ServerRet getServerRet() {
        return serverRet;
    }

}
