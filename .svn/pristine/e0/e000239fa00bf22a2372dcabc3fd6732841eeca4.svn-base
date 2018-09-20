package com.huawei.solarsafe.bean.alarm;

import com.google.gson.Gson;
import com.huawei.solarsafe.bean.BaseEntity;
import com.huawei.solarsafe.bean.ServerRet;

import org.json.JSONObject;

import java.util.List;

/**
 * Created by P00322 on 2017/2/15.
 * description: 设备告警查询列表信息获取接口解析类
 */
public class DeviceAlarmQueryList extends BaseEntity {

    private static final String TAG = "DeviceAlarmQueryList";
    //告警命中总数
    int total;
    //页码
    int pageNo;
    //页大小
    int pageSize;
    //总页数
    int pageCount;
    //设备告警查询列表
    List<DeviceAlarmInfo> list;
    //统一定义的返回码
    ServerRet mRetCode;

    @Override
    public boolean parseJson(JSONObject jsonObject) throws Exception {
        if (jsonObject == null) {
            return false;
        }
        Gson gson = new Gson();
        DeviceAlarmQueryList deviceAlarmQueryList = gson.fromJson(jsonObject.toString(), DeviceAlarmQueryList.class);
        list = deviceAlarmQueryList.getList();
        total = deviceAlarmQueryList.getTotal();
        pageNo = deviceAlarmQueryList.getPageNo();
        pageSize = deviceAlarmQueryList.getPageSize();
        pageCount = deviceAlarmQueryList.getPageCount();
        return true;
    }

    public List<DeviceAlarmInfo> getList() {
        return list;
    }

    public void setList(List<DeviceAlarmInfo> list) {
        this.list = list;
    }

    @Override
    public void setServerRet(ServerRet serverRet) {
        mRetCode = serverRet;
    }

    public static String getTAG() {
        return TAG;
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
        return "DeviceAlarmQueryList{" +
                "total=" + total +
                ", pageNo=" + pageNo +
                ", pageSize=" + pageSize +
                ", pageCount=" + pageCount +
                ", deviceAlarmInfoList=" + list.toString() +
                ", mRetCode=" + mRetCode +
                '}';
    }
}
