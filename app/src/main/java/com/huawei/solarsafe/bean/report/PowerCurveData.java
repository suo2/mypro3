package com.huawei.solarsafe.bean.report;

import java.util.List;

/**
 * Created by P00708 on 2018/7/11.
 */

public class PowerCurveData {

    private boolean hasInverter;
    private boolean hasMeter;
    private boolean hasEnergyStore;
    private boolean hasUserPower;
    private String userPowers;
    private List<String> xData;
    //用于画图的数据
    private List<String> inverterPowers;
    private List<String> meterInputPowers;
    private List<String> meterOutputPowers;
    private List<String> energyStoreInputPowers;
    private List<String> energyStoreOutputPowers;
    //用于展示的数据
    private List<String> inverterCaps;
    private List<String> meterInputCaps;
    private List<String> meterOutputCaps;
    private List<String> energyStoreInputCaps;
    private List<String> energyStoreOutputCaps;

    private StationProuductAndUserPower stationProuductAndUserPower;
    private List<String> productPower;
    private List<String> onGridPower;
    private List<String> charging;
    private List<String> discharge;
    private List<String> buyPower;

    public boolean isHasInverter() {
        return hasInverter;
    }

    public void setHasInverter(boolean hasInverter) {
        this.hasInverter = hasInverter;
    }

    public boolean isHasMeter() {
        return hasMeter;
    }

    public void setHasMeter(boolean hasMeter) {
        this.hasMeter = hasMeter;
    }

    public boolean isHasEnergyStore() {
        return hasEnergyStore;
    }

    public void setHasEnergyStore(boolean hasEnergyStore) {
        this.hasEnergyStore = hasEnergyStore;
    }

    public boolean isHasUserPower() {
        return hasUserPower;
    }

    public void setHasUserPower(boolean hasUserPower) {
        this.hasUserPower = hasUserPower;
    }

    public String getUserPowers() {
        return userPowers;
    }

    public void setUserPowers(String userPowers) {
        this.userPowers = userPowers;
    }

    public List<String> getxData() {
        return xData;
    }

    public void setxData(List<String> xData) {
        this.xData = xData;
    }

    public List<String> getInverterPowers() {
        return inverterPowers;
    }

    public void setInverterPowers(List<String> inverterPowers) {
        this.inverterPowers = inverterPowers;
    }

    public List<String> getMeterInputPowers() {
        return meterInputPowers;
    }

    public void setMeterInputPowers(List<String> meterInputPowers) {
        this.meterInputPowers = meterInputPowers;
    }

    public List<String> getMeterOutputPowers() {
        return meterOutputPowers;
    }

    public void setMeterOutputPowers(List<String> meterOutputPowers) {
        this.meterOutputPowers = meterOutputPowers;
    }

    public List<String> getEnergyStoreInputPowers() {
        return energyStoreInputPowers;
    }

    public void setEnergyStoreInputPowers(List<String> energyStoreInputPowers) {
        this.energyStoreInputPowers = energyStoreInputPowers;
    }

    public List<String> getEnergyStoreOutputPowers() {
        return energyStoreOutputPowers;
    }

    public void setEnergyStoreOutputPowers(List<String> energyStoreOutputPowers) {
        this.energyStoreOutputPowers = energyStoreOutputPowers;
    }

    public StationProuductAndUserPower getStationProuductAndUserPower() {
        return stationProuductAndUserPower;
    }

    public void setStationProuductAndUserPower(StationProuductAndUserPower stationProuductAndUserPower) {
        this.stationProuductAndUserPower = stationProuductAndUserPower;
    }

    public List<String> getProductPower() {
        return productPower;
    }

    public void setProductPower(List<String> productPower) {
        this.productPower = productPower;
    }

    public List<String> getOnGridPower() {
        return onGridPower;
    }

    public void setOnGridPower(List<String> onGridPower) {
        this.onGridPower = onGridPower;
    }

    public List<String> getCharging() {
        return charging;
    }

    public void setCharging(List<String> charging) {
        this.charging = charging;
    }

    public List<String> getDischarge() {
        return discharge;
    }

    public void setDischarge(List<String> discharge) {
        this.discharge = discharge;
    }

    public List<String> getBuyPower() {
        return buyPower;
    }

    public void setBuyPower(List<String> buyPower) {
        this.buyPower = buyPower;
    }

    public List<String> getInverterCaps() {
        return inverterCaps;
    }

    public void setInverterCaps(List<String> inverterCaps) {
        this.inverterCaps = inverterCaps;
    }

    public List<String> getMeterInputCaps() {
        return meterInputCaps;
    }

    public void setMeterInputCaps(List<String> meterInputCaps) {
        this.meterInputCaps = meterInputCaps;
    }

    public List<String> getMeterOutputCaps() {
        return meterOutputCaps;
    }

    public void setMeterOutputCaps(List<String> meterOutputCaps) {
        this.meterOutputCaps = meterOutputCaps;
    }

    public List<String> getEnergyStoreInputCaps() {
        return energyStoreInputCaps;
    }

    public void setEnergyStoreInputCaps(List<String> energyStoreInputCaps) {
        this.energyStoreInputCaps = energyStoreInputCaps;
    }

    public List<String> getEnergyStoreOutputCaps() {
        return energyStoreOutputCaps;
    }

    public void setEnergyStoreOutputCaps(List<String> energyStoreOutputCaps) {
        this.energyStoreOutputCaps = energyStoreOutputCaps;
    }
}
