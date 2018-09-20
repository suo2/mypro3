package com.huawei.solarsafe.bean.device;

import java.io.Serializable;

/**
 * Created by p00229 on 2017/5/31.
 */

public class PvBean2 implements Serializable{


    private static final long serialVersionUID = -4030163312703862653L;
    private String manufacturer;
    private String moduleVersion;
    private String standardPower;
    private String moduleType;
    private String voltageTempCoef;
    private String currentTempCoef;
    private String maxPowerTempCoef;
    private String maxPowerPointVoltage;
    private String maxPowerPointCurrent;
    private String firstDegradationDrate;
    private String secondDegradationDrate;
    private String cellsNumPerModule;
    private String componentsNominalVoltage;
    private String nominalCurrentComponent;
    private String moduleRatio;
    private String fillFactor;
    private long moduleProductionDate;
    private boolean isDefault;
    private String modulesNumPerString;

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getModuleVersion() {
        return moduleVersion;
    }

    public void setModuleVersion(String moduleVersion) {
        this.moduleVersion = moduleVersion;
    }

    public String getStandardPower() {
        return standardPower;
    }

    public void setStandardPower(String standardPower) {
        this.standardPower = standardPower;
    }

    public String getModuleType() {
        return moduleType;
    }

    public void setModuleType(String moduleType) {
        this.moduleType = moduleType;
    }

    public String getVoltageTempCoef() {
        return voltageTempCoef;
    }

    public void setVoltageTempCoef(String voltageTempCoef) {
        this.voltageTempCoef = voltageTempCoef;
    }

    public String getCurrentTempCoef() {
        return currentTempCoef;
    }

    public void setCurrentTempCoef(String currentTempCoef) {
        this.currentTempCoef = currentTempCoef;
    }

    public String getMaxPowerTempCoef() {
        return maxPowerTempCoef;
    }

    public void setMaxPowerTempCoef(String maxPowerTempCoef) {
        this.maxPowerTempCoef = maxPowerTempCoef;
    }

    public String getMaxPowerPointVoltage() {
        return maxPowerPointVoltage;
    }

    public void setMaxPowerPointVoltage(String maxPowerPointVoltage) {
        this.maxPowerPointVoltage = maxPowerPointVoltage;
    }

    public String getMaxPowerPointCurrent() {
        return maxPowerPointCurrent;
    }

    public void setMaxPowerPointCurrent(String maxPowerPointCurrent) {
        this.maxPowerPointCurrent = maxPowerPointCurrent;
    }

    public String getFirstDegradationDrate() {
        return firstDegradationDrate;
    }

    public void setFirstDegradationDrate(String firstDegradationDrate) {
        this.firstDegradationDrate = firstDegradationDrate;
    }

    public String getSecondDegradationDrate() {
        return secondDegradationDrate;
    }

    public void setSecondDegradationDrate(String secondDegradationDrate) {
        this.secondDegradationDrate = secondDegradationDrate;
    }

    public String getCellsNumPerModule() {
        return cellsNumPerModule;
    }

    public void setCellsNumPerModule(String cellsNumPerModule) {
        this.cellsNumPerModule = cellsNumPerModule;
    }

    public String getComponentsNominalVoltage() {
        return componentsNominalVoltage;
    }

    public void setComponentsNominalVoltage(String componentsNominalVoltage) {
        this.componentsNominalVoltage = componentsNominalVoltage;
    }

    public String getNominalCurrentComponent() {
        return nominalCurrentComponent;
    }

    public void setNominalCurrentComponent(String nominalCurrentComponent) {
        this.nominalCurrentComponent = nominalCurrentComponent;
    }

    public String getModuleRatio() {
        return moduleRatio;
    }

    public void setModuleRatio(String moduleRatio) {
        this.moduleRatio = moduleRatio;
    }

    public String getFillFactor() {
        return fillFactor;
    }

    public void setFillFactor(String fillFactor) {
        this.fillFactor = fillFactor;
    }

    public long getModuleProductionDate() {
        return moduleProductionDate;
    }

    public void setModuleProductionDate(long moduleProductionDate) {
        this.moduleProductionDate = moduleProductionDate;
    }


    public void setIsDefault(boolean isDefault) {
        this.isDefault = isDefault;
    }

    public String getModulesNumPerString() {
        return modulesNumPerString;
    }

    public void setModulesNumPerString(String modulesNumPerString) {
        this.modulesNumPerString = modulesNumPerString;
    }
}
