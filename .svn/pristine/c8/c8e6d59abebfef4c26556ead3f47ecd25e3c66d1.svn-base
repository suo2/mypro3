package com.huawei.solarsafe.model.report;

import com.huawei.solarsafe.model.BaseModel;
import com.huawei.solarsafe.net.CommonCallback;

import java.util.Map;

/**
 * Created by p00322 on 2017/2/20.
 */
public interface IReportModel extends BaseModel {
    //电站报表数据接口url后缀
    String URL_RM_KPI_LIST = "/rm/listKpiList";
    //电站报表数据汇总接口url后缀
    String URL_RM_KPI_CHART = "/rm/listKpiChart";
    //逆变器报表
    String URL_INVERTER_REPORTER = "/inverterRm/listInverterKpiList";
    //设备选择树
    String URL_DEV_CHOICE_REPORT = "/rm/listUserDevice";
    //单电站功率曲线图
    String URL_STATION_POWER_CURVE = "/station/getStationEnergyManage";
    /**
     * 电站报表数据获取请求(表)
     *
     * @param param
     * @param callback
     */
    void requestKpiList(Map<String, String> param, CommonCallback callback);

    /**
     * 电站报表数据汇总获取请求（图）
     *
     * @param param
     * @param callback
     */
    void requestKpiChart(Map<String, String> param, CommonCallback callback);
    /**
     * 逆变器报表
     *
     * @param param
     * @param callback
     */
    void requestInverterReporterData(Map<String, String> param, CommonCallback callback);

    void requestStationPowerCurve(Map<String, String> param, CommonCallback callback);

}
