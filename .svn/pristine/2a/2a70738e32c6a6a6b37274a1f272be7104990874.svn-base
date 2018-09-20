package com.huawei.solarsafe.bean.poverty;

import com.huawei.solarsafe.bean.BaseEntity;
import com.huawei.solarsafe.bean.ServerRet;
import com.huawei.solarsafe.utils.JSONReader;

import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by p00322 on 2017/2/14.
 * description: 扶贫对象列表信息获取接口解析
 */
public class PoorList extends BaseEntity {
    //总条数
    private static final String KEY_TOTAL = "total";
    //第几页
    private static final String KEY_PAGE_NO = "pageNo";
    //每页显示多少条
    private static final String KEY_PAGE_SIZE = "pageSize";
    //扶贫对象数组
    private static final String KEY_LIST = "list";
    //扶贫对象列号
    private static final String KEY_ID = "id";
    //扶贫对象姓名
    private static final String KEY_NAME = "name";
    //扶贫对象性别
    private static final String KEY_SEX = "sex";
    //所在省
    private static final String KEY_PROVINCE = "province";
    //所在市
    private static final String KEY_CITY = "city";
    //所在县
    private static final String KEY_COUNTY = "county";
    //所在镇
    private static final String KEY_TOWN = "town";
    //所在村
    private static final String KEY_VILLAGE = "village";
    //住址
    private static final String KEY_LIVE_ADD = "liveAdd";
    //联系方式
    private static final String KEY_CONTACT_WAY = "contactWay";
    //对应电站
    private static final String KEY_STATION = "station";
    //对应电站Id
    private static final String KEY_SID = "sid";
    //是否完成扶贫
    private static final String KEY_IS_COMPLETE = "isComplete";
    //完成扶贫日期
    private static final String KEY_COMPLETE_DATE = "completeDate";

    //总条数
    int total;
    //第几页
    int pageNo;
    //每页显示多少条
    int pageSize;
    //扶贫对象列表
    List<PoorInfo> poorInfoList;
    //统一定义的返回码
    ServerRet mRetCode;

    @Override
    public boolean parseJson(JSONObject jsonObject) throws Exception {
        if (jsonObject == null) {
            return false;
        }
        JSONReader jsonReader = new JSONReader(jsonObject);
        total = jsonReader.getInt(KEY_TOTAL);
        pageNo = jsonReader.getInt(KEY_PAGE_NO);
        pageSize = jsonReader.getInt(KEY_PAGE_SIZE);
        JSONArray jsonArray = jsonReader.getJSONArray(KEY_LIST);
        poorInfoList = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            PoorInfo poorInfo = new PoorInfo();
            JSONReader jsonReader1 = new JSONReader(jsonArray.getJSONObject(i));
            poorInfo.setId(jsonReader1.getLong(KEY_ID));
            poorInfo.setName(jsonReader1.getString(KEY_NAME));
            poorInfo.setSex(jsonReader1.getString(KEY_SEX));
            poorInfo.setProvince(jsonReader1.getString(KEY_PROVINCE));
            poorInfo.setCity(jsonReader1.getString(KEY_CITY));
            poorInfo.setCounty(jsonReader1.getString(KEY_COUNTY));
            poorInfo.setTown(jsonReader1.getString(KEY_TOWN));
            poorInfo.setVillage(jsonReader1.getString(KEY_VILLAGE));
            poorInfo.setLiveAdd(jsonReader1.getString(KEY_LIVE_ADD));
            poorInfo.setContactWay(jsonReader1.getString(KEY_CONTACT_WAY));
            poorInfo.setStation(jsonReader1.getString(KEY_STATION));
            poorInfo.setSid(jsonReader1.getString(KEY_SID));
            poorInfo.setIsComplete(jsonReader1.getString(KEY_IS_COMPLETE));
            poorInfo.setCompleteDate(jsonReader1.getString(KEY_COMPLETE_DATE));
            poorInfoList.add(poorInfo);
        }
        return true;
    }
    // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0001_F03_Android_S01  删除Android的demo账号    【修改人】：zhaoyufeng

    @Override
    public void setServerRet(ServerRet serverRet) {
        mRetCode = serverRet;
    }

    public int getTotal() {
        return total;
    }

    public int getPageNo() {
        return pageNo;
    }

    public int getPageSize() {
        return pageSize;
    }

    public List<PoorInfo> getPoorInfoList() {
        return poorInfoList;
    }

    public ServerRet getRetCode() {
        return mRetCode;
    }

    @Override
    public String toString() {
        return "PoorList{" +
                "total=" + total +
                ", pageNo=" + pageNo +
                ", pageSize=" + pageSize +
                ", poorInfoList=" + poorInfoList.toString() +
                ", mRetCode=" + mRetCode +
                '}';
    }
}
