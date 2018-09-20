package com.huawei.solarsafe.bean.report;

/**
 * Created by p00507
 * on 2017/5/26.
 */
public class InverterReportKpiList {
    String id ;
    boolean hasMeter;
    InverterReportKpiInfo inverterReportKpiInfo;
    InverterModel inverterModel;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setHasMeter(boolean hasMeter) {
        this.hasMeter = hasMeter;
    }

    public InverterReportKpiInfo getInverterReportKpiInfo() {
        return inverterReportKpiInfo;
    }

    public void setInverterReportKpiInfo(InverterReportKpiInfo inverterReportKpiInfo) {
        this.inverterReportKpiInfo = inverterReportKpiInfo;
    }

    public InverterModel getInverterModel() {
        return inverterModel;
    }

    public void setInverterModel(InverterModel inverterModel) {
        this.inverterModel = inverterModel;
    }
}
