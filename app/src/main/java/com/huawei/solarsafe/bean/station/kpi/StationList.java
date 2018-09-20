package com.huawei.solarsafe.bean.station.kpi;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.huawei.solarsafe.R;
import com.huawei.solarsafe.bean.BaseEntity;
import com.huawei.solarsafe.bean.ServerRet;
import com.huawei.solarsafe.bean.station.map.StationStateEnum;

import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by p00322 on 2017/1/4.
 * description: 电站列表信息获取接口解析类
 */
public class StationList extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 7398966183546069254L;
    private StationListDataBean dataBean;

    //电站总数
    private int total;
    //电站列表
    private List<StationInfo> stationInfoList;

    //统一返回码
    private ServerRet retCode;

    @Override
    public boolean parseJson(JSONObject jsonObject) throws Exception {
        if (jsonObject == null) {
            return false;
        }
        Gson gson = new Gson();
        dataBean = gson.fromJson(jsonObject.toString(),StationListDataBean.class);
        total = dataBean.getTotal();
        stationInfoList = new ArrayList<>();
        for (int i = 0; i < dataBean.getList().size(); i++) {
            StationInfo stationInfo = new StationInfo();
            StationListItemDataBean stationListItemDataBean = dataBean.getList().get(i);
            if ("1".equals(stationListItemDataBean.getState())) {
                stationInfo.setStationStateEnum(StationStateEnum.FAULTCHAIN);
            } else if ("2".equals(stationListItemDataBean.getState())) {
                stationInfo.setStationStateEnum(StationStateEnum.EXCEPTION);
            } else if ("3".equals(stationListItemDataBean.getState())) {
                stationInfo.setStationStateEnum(StationStateEnum.HEALTH);
            }
            stationInfo.setStationPic(stationListItemDataBean.getStationPic());
            stationInfo.setStationName(stationListItemDataBean.getStationName());
            stationInfo.setPlantAddr(stationListItemDataBean.getStationAddr());
            stationInfo.setStationLinkMan(stationListItemDataBean.getStationLinkman());
            stationInfo.setContactPho(stationListItemDataBean.getLinkmanPho());
            if(TextUtils.isEmpty(stationListItemDataBean.getRealcapacity()) || "null".equals(stationListItemDataBean.getRealcapacity())
                    || "NULL".equals(stationListItemDataBean.getRealcapacity())){
                stationInfo.setInstallCapacity(0);
            }else{
                stationInfo.setInstallCapacity(Double.valueOf(stationListItemDataBean.getRealcapacity()));
            }
            if(TextUtils.isEmpty(stationListItemDataBean.getPower()) || "null".equals(stationListItemDataBean.getPower())
                    || "NULL".equals(stationListItemDataBean.getPower())){
                stationInfo.setCurrentPower(0);
            }else{
                stationInfo.setCurrentPower(Double.valueOf(stationListItemDataBean.getPower()));
            }
            if(TextUtils.isEmpty(stationListItemDataBean.getDaycapacity()) || "null".equals(stationListItemDataBean.getDaycapacity())
                    || "NULL".equals(stationListItemDataBean.getDaycapacity())){
                stationInfo.setCurDay(0);
            }else{
                stationInfo.setCurDay(Double.valueOf(stationListItemDataBean.getDaycapacity()));
            }
            if(TextUtils.isEmpty(stationListItemDataBean.getTotalMonthCapacity()) || "null".equals(stationListItemDataBean.getTotalMonthCapacity())
                    || "NULL".equals(stationListItemDataBean.getTotalMonthCapacity())){
                stationInfo.setCurMonth(0);
            }else{
                stationInfo.setCurMonth(Double.valueOf(stationListItemDataBean.getTotalMonthCapacity()));
            }
            if(TextUtils.isEmpty(stationListItemDataBean.getDayincome()) || "null".equals(stationListItemDataBean.getDayincome())
                    || "NULL".equals(stationListItemDataBean.getDayincome())){
                stationInfo.setDayProfit(0);
            }else{
                stationInfo.setDayProfit(Double.valueOf(stationListItemDataBean.getDayincome()));
            }
            stationInfo.setsId(stationListItemDataBean.getStationCode());
            stationInfo.setPictureUpdataTime(stationListItemDataBean.getPicLastModify());
            stationInfo.setStationPicDemo(R.drawable.demo2);
            stationInfoList.add(stationInfo);
        }
        return true;
    }
    // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0001_F03_Android_S01  删除Android的demo账号    【修改人】：zhaoyufengg

    @Override
    public void setServerRet(ServerRet serverRet) {
        retCode = serverRet;
    }

    public int getTotal() {
        return total;
    }

    public List<StationInfo> getStationInfoList() {
        return stationInfoList;
    }

    public StationListDataBean getDataBean() {
        return dataBean;
    }

    public ServerRet getRetCode() {
        return retCode;
    }

    @Override
    public String toString() {
        return "StationList{" +
                "total=" + total +
                ", stationInfoList=" + stationInfoList.toString() +
                ", retCode=" + retCode +
                '}';
    }
}
