package com.huawei.solarsafe.bean.defect;

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
 * Created by p00322 on 2017/2/22.
 * description: 获取用户待办流程列表接口解析
 */
public class TodoTaskList extends BaseEntity {


    private int total;
    private int pageNo;
    private int pageSize;
    private int pageCount;
    //统一定义的返回码
    ServerRet mRetCode;
    private List<TodoTaskBean> list;

    @Override
    public boolean parseJson(JSONObject jsonObject) throws Exception {
        list = new ArrayList<>();
        JSONReader jsonReader = new JSONReader(jsonObject);
        String temp = jsonReader.getString("list");
        Type type = new TypeToken<List<TodoTaskBean>>() {
        }.getType();
        Gson gson = new Gson();
        list = gson.fromJson(temp, type);
        return true;
    }
    // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0001_F03_Android_S01  删除Android的demo账号    【修改人】：zhaoyufeng

    @Override
    public void setServerRet(ServerRet serverRet) {
        this.mRetCode = serverRet;
    }

    public ServerRet getRetCode() {
        return mRetCode;
    }

    public List<TodoTaskBean> getList() {
        return list;
    }

    public void setList(List<TodoTaskBean> list) {
        this.list = list;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
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

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    @Override
    public String toString() {
        return "TodoTaskList{" +
                "total=" + total +
                ", pageNo=" + pageNo +
                ", pageSize=" + pageSize +
                ", pageCount=" + pageCount +
                ", mRetCode=" + mRetCode +
                ", list=" + list.toString() +
                '}';
    }
}
