package com.huawei.solarsafe.bean.device;

import com.huawei.solarsafe.bean.BaseEntity;
import com.huawei.solarsafe.bean.ServerRet;
import com.huawei.solarsafe.net.JSONReader;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by p00229 on 2017/5/26.
 */

public class InitModuleOptionInfo extends BaseEntity {
    private List<PvFactory> pvFactoryList;
    private List<CurrentModule> currentModuleList;
    private ServerRet mServerRet;

    public List<PvFactory> getPvFactoryList() {
        return pvFactoryList;
    }


    public List<CurrentModule> getCurrentModuleList() {
        return currentModuleList;
    }


    @Override
    public boolean parseJson(JSONObject jsonObject) throws Exception {
        if (jsonObject == null) {
            return false;
        }
        JSONReader reader = new JSONReader(jsonObject);
        JSONObject options = reader.getJSONObject("options");
        JSONArray array = reader.getJSONArray("currentModule");
        currentModuleList = new ArrayList<>();
        if (array != null && array.length() > 0) {
            for (int i = 0; i < array.length(); i++) {
                CurrentModule currentModule = new CurrentModule();
                JSONReader jsonReader = new JSONReader(array.getJSONObject(i));
                currentModule.setEsnCode(jsonReader.getString("esnCode"));
                currentModule.setPvIndex(jsonReader.getInt("pvIndex"));
                currentModule.setManufacturer(jsonReader.getString("manufacturer"));
                currentModule.setAbbreviation(jsonReader.getString("abbreviation"));
                currentModule.setModuleVersion(jsonReader.getString("moduleVersion"));
                currentModule.setModuleType(jsonReader.getString("moduleType"));
                currentModule.setStandardPower(jsonReader.getDouble("standardPower"));
                currentModule.setModuleRatio(jsonReader.getDouble("moduleRatio"));
                currentModule.setComponentsNominalVoltage(jsonReader.getDouble("componentsNominalVoltage"));
                currentModule.setNominalCurrentComponent(jsonReader.getDouble("nominalCurrentComponent"));
                currentModule.setMaxPowerPointVoltage(jsonReader.getDouble("maxPowerPointVoltage"));
                currentModule.setMaxPowerPointCurrent(jsonReader.getDouble("maxPowerPointCurrent"));
                currentModule.setFillFactor(jsonReader.getDouble("fillFactor"));
                currentModule.setMaxPowerTempCoef(jsonReader.getDouble("maxPowerTempCoef"));
                currentModule.setVoltageTempCoef(jsonReader.getDouble("voltageTempCoef"));
                currentModule.setCurrentTempCoef(jsonReader.getDouble("currentTempCoef"));
                currentModule.setFirstDegradationDrate(jsonReader.getDouble("firstDegradationDrate"));
                currentModule.setSecondDegradationDrate(jsonReader.getDouble("secondDegradationDrate"));
                currentModule.setCellsNumPerModule(jsonReader.getDouble("cellsNumPerModule"));
                currentModule.setMinWorkTemp(jsonReader.getDouble("minWorkTemp"));
                currentModule.setMaxWorkTemp(jsonReader.getDouble("maxWorkTemp"));
                currentModule.setModulesNumPerString(jsonReader.getInt("modulesNumPerString"));
                currentModule.setModuleProductionDate(jsonReader.getLong("moduleProductionDate"));
                currentModule.setCreateTime(jsonReader.getLong("createTime"));
                currentModule.setUpdateTime(jsonReader.getLong("updateTime"));
                currentModule.setIsDefault(jsonReader.getString("isDefault"));
                currentModuleList.add(currentModule);
            }
        }
        Iterator<String> keys = options.keys();
        pvFactoryList = new ArrayList<>();
        while (keys.hasNext()) {
            PvFactory pvFactory = new PvFactory();
            pvFactoryList.add(pvFactory);
            String factoryName = keys.next() + "";
            pvFactory.setFactotyName(factoryName);
            JSONObject jsonObject1 = options.getJSONObject(factoryName);
            Iterator<String> keys1 = jsonObject1.keys();
            List<PvFactory.PvModel> pvModelList = new ArrayList<>();
            pvFactory.setPvModelList(pvModelList);
            while (keys1.hasNext()) {
                PvFactory.PvModel pvModel = new PvFactory().new PvModel();
                pvModelList.add(pvModel);
                String modelName = keys1.next() + "";
                pvModel.setModelName(modelName);
                JSONObject jsonObject2 = jsonObject1.getJSONObject(modelName);
                Iterator<String> keys2 = jsonObject2.keys();
                List<PvFactory.PvPower> pvPowerList = new ArrayList<>();
                pvModel.setPvPowerList(pvPowerList);
                while (keys2.hasNext()) {
                    PvFactory.PvPower pvPower = new PvFactory().new PvPower();
                    pvPowerList.add(pvPower);
                    String power = keys2.next() + "";
                    String powerCode = jsonObject2.getString(power);
                    pvPower.setPower(power);
                    pvPower.setPowerCode(powerCode);
                }
            }
        }
        return true;
    }
    // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0001_F03_Android_S01  删除Android的demo账号    【修改人】：zhaoyufeng

    @Override
    public void setServerRet(ServerRet serverRet) {
        mServerRet = serverRet;
    }

    public class CurrentModule {
        String esnCode;
        int pvIndex;
        String manufacturer;
        String abbreviation;
        String moduleVersion;
        String moduleType;
        double standardPower;
        //标称组件转换率
        double moduleRatio;
        double componentsNominalVoltage;
        double nominalCurrentComponent;
        double maxPowerPointVoltage;
        double maxPowerPointCurrent;
        //填充因子
        double fillFactor;
        double maxPowerTempCoef;
        double voltageTempCoef;
        double currentTempCoef;
        double firstDegradationDrate;
        double secondDegradationDrate;
        double cellsNumPerModule;
        double minWorkTemp;
        double maxWorkTemp;
        int modulesNumPerString;
        long moduleProductionDate;
        long createTime;
        long updateTime;
        String isDefault;

        public String getEsnCode() {
            return esnCode;
        }

        public void setEsnCode(String esnCode) {
            this.esnCode = esnCode;
        }

        public int getPvIndex() {
            return pvIndex;
        }

        public void setPvIndex(int pvIndex) {
            this.pvIndex = pvIndex;
        }

        public String getManufacturer() {
            return manufacturer;
        }

        public void setManufacturer(String manufacturer) {
            this.manufacturer = manufacturer;
        }


        public void setAbbreviation(String abbreviation) {
            this.abbreviation = abbreviation;
        }

        public String getModuleVersion() {
            return moduleVersion;
        }

        public void setModuleVersion(String moduleVersion) {
            this.moduleVersion = moduleVersion;
        }

        public String getModuleType() {
            return moduleType;
        }

        public void setModuleType(String moduleType) {
            this.moduleType = moduleType;
        }

        public double getStandardPower() {
            return standardPower;
        }

        public void setStandardPower(double standardPower) {
            this.standardPower = standardPower;
        }

        public double getModuleRatio() {
            return moduleRatio;
        }

        public void setModuleRatio(double moduleRatio) {
            this.moduleRatio = moduleRatio;
        }

        public double getComponentsNominalVoltage() {
            return componentsNominalVoltage;
        }

        public void setComponentsNominalVoltage(double componentsNominalVoltage) {
            this.componentsNominalVoltage = componentsNominalVoltage;
        }

        public double getNominalCurrentComponent() {
            return nominalCurrentComponent;
        }

        public void setNominalCurrentComponent(double nominalCurrentComponent) {
            this.nominalCurrentComponent = nominalCurrentComponent;
        }

        public double getMaxPowerPointVoltage() {
            return maxPowerPointVoltage;
        }

        public void setMaxPowerPointVoltage(double maxPowerPointVoltage) {
            this.maxPowerPointVoltage = maxPowerPointVoltage;
        }

        public double getMaxPowerPointCurrent() {
            return maxPowerPointCurrent;
        }

        public void setMaxPowerPointCurrent(double maxPowerPointCurrent) {
            this.maxPowerPointCurrent = maxPowerPointCurrent;
        }

        public double getFillFactor() {
            return fillFactor;
        }

        public void setFillFactor(double fillFactor) {
            this.fillFactor = fillFactor;
        }

        public double getMaxPowerTempCoef() {
            return maxPowerTempCoef;
        }

        public void setMaxPowerTempCoef(double maxPowerTempCoef) {
            this.maxPowerTempCoef = maxPowerTempCoef;
        }

        public double getVoltageTempCoef() {
            return voltageTempCoef;
        }

        public void setVoltageTempCoef(double voltageTempCoef) {
            this.voltageTempCoef = voltageTempCoef;
        }

        public double getCurrentTempCoef() {
            return currentTempCoef;
        }

        public void setCurrentTempCoef(double currentTempCoef) {
            this.currentTempCoef = currentTempCoef;
        }

        public double getFirstDegradationDrate() {
            return firstDegradationDrate;
        }

        public void setFirstDegradationDrate(double firstDegradationDrate) {
            this.firstDegradationDrate = firstDegradationDrate;
        }

        public double getSecondDegradationDrate() {
            return secondDegradationDrate;
        }

        public void setSecondDegradationDrate(double secondDegradationDrate) {
            this.secondDegradationDrate = secondDegradationDrate;
        }

        public double getCellsNumPerModule() {
            return cellsNumPerModule;
        }

        public void setCellsNumPerModule(double cellsNumPerModule) {
            this.cellsNumPerModule = cellsNumPerModule;
        }

        public void setMinWorkTemp(double minWorkTemp) {
            this.minWorkTemp = minWorkTemp;
        }


        public void setMaxWorkTemp(double maxWorkTemp) {
            this.maxWorkTemp = maxWorkTemp;
        }

        public int getModulesNumPerString() {
            return modulesNumPerString;
        }

        public void setModulesNumPerString(int modulesNumPerString) {
            this.modulesNumPerString = modulesNumPerString;
        }

        public long getModuleProductionDate() {
            return moduleProductionDate;
        }

        public void setModuleProductionDate(long moduleProductionDate) {
            this.moduleProductionDate = moduleProductionDate;
        }

        public long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(long createTime) {
            this.createTime = createTime;
        }

        public long getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(long updateTime) {
            this.updateTime = updateTime;
        }


        public void setIsDefault(String isDefault) {
            this.isDefault = isDefault;
        }

    }

    public ServerRet getServerRet() {
        return mServerRet;
    }
}
