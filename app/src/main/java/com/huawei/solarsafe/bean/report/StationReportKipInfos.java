package com.huawei.solarsafe.bean.report;

/**
 * Created by p00507
 * on 2017/4/20.
 */

public class StationReportKipInfos {
    String id;
    StationReportKpiInfo station;
    StationReportModel kpiModel;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public StationReportKpiInfo getStation() {
        return station;
    }

    public void setStation(StationReportKpiInfo station) {
        this.station = station;
    }

    public StationReportModel getKpiModel() {
        return kpiModel;
    }

    public void setKpiModel(StationReportModel kpiModel) {
        this.kpiModel = kpiModel;
    }
}
