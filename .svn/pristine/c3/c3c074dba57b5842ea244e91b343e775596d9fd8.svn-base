package com.huawei.solarsafe.view.stationmanagement;

import com.huawei.solarsafe.bean.common.RetMsg;
import com.huawei.solarsafe.bean.station.ChangeStationInfo;
import com.huawei.solarsafe.bean.stationmagagement.PowerStationListBean;
import com.huawei.solarsafe.bean.stationmagagement.SubDev;

import java.util.ArrayList;

/**
 * <pre>
 *     author: Tzy
 *     time  : $date$
 *     desc  : 单选电站视图接口
 * </pre>
 */
public interface ISingerSelectPowerStationView {

    /**
     * 初始化数据方法
     */
    void requestData();

    /**
     * 获取电站(带层级)列表回调
     */
    void getPowerStations(PowerStationListBean powerStationListBean);

    /**
     * 绑定设备到电站回调
     */
    void updateBindDev(boolean b);

    /**
     * 获取绑定到电站的设备列表回调
     */
    void getBindInvs(ArrayList<SubDev> subDevs);

    /**
     * 设置设备PV容量请求回调
     */
    void  savePvCapacity(RetMsg retMsg);

    /**
     * 获取指定电站编号的电站信息请求回调
     */
    void  getPowerStationInfo(ChangeStationInfo changeStationInfo);

}
