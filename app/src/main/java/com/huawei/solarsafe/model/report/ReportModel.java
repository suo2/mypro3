package com.huawei.solarsafe.model.report;

import com.huawei.solarsafe.net.CommonCallback;
import com.huawei.solarsafe.net.NetRequest;

import java.util.Map;

/**
 * Created by p00322 on 2017/2/20.
 */
public class ReportModel implements IReportModel {
    NetRequest request = NetRequest.getInstance();

    @Override
    public void requestKpiList(Map<String, String> param, CommonCallback callback) {
        String url = NetRequest.IP + IReportModel.URL_RM_KPI_LIST;
        request.asynPostJson(url, param, callback);
    }

    @Override
    public void requestKpiChart(Map<String, String> param, CommonCallback callback) {
        String url = NetRequest.IP + IReportModel.URL_RM_KPI_CHART;
        request.asynPostJson(url, param, callback);
    }

    @Override
    public void requestInverterReporterData(Map<String, String> param, CommonCallback callback) {
        String url = NetRequest.IP + IReportModel.URL_INVERTER_REPORTER;
        request.asynPostJson(url, param, callback);
    }

    @Override
    public void requestStationPowerCurve(Map<String, String> param, CommonCallback callback) {
        String url = NetRequest.IP + IReportModel.URL_STATION_POWER_CURVE;
        request.asynPostJson(url, param, callback);
    }
}
