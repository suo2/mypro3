/*
 * Copyright (C) PINNET Tech<br>
 * All Rights Reserved.<br>
 * 
 */
package com.huawei.solarsafe.bean;


import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Locale;

/**
 * Create Date: 2017-4-25<br>
 * Create Author: P00322<br>
 * Description:
 */
public class UnitType {
    private static UnitType unitType;
    private JSONObject jsonObject;
    private static final String TAG = "UnitType";

    public static UnitType getInstance() {
        if (unitType == null) {
            unitType = new UnitType();
        }
        return unitType;
    }

    //中文环境
    private String jsonStringszh = "{\n" +
            "    \"topPowerUnit\": \"kW·h\",\n" +
            "    \"powerUnit\": \"万kW·h\",\n" +
            "    \"KWh\": \"kWh\",\n" +
            "    \"WKWh\": \"万kWh\",\n" +
            "    \"KW\": \"kW\",\n" +
            "    \"MW\": \"MW\",\n" +
            "    \"GW\":\"GW\",\n" +
            "    \"GWh\":\"GWh\",\n" +
            "    \"co2Unit\": \"t\",\n" +
            "    \"coalUnit\": \"t\",\n" +
            "    \"treeUnit\": \"m³\",\n" +
            "    \"co2WUnit\": \"万t\",\n" +
            "    \"coalWUnit\": \"万t\",\n" +
            "    \"treeWUnit\": \"万m³\",\n" +
            "    \"tree_unit\": \"棵\",\n" +
            "    \"wtree_unit\": \"万棵\",\n" +
            "    \"curRadiantUnit\": \"kW·h/m²\",\n" +
            "    \"radiantUnit\": \"W/m²\",\n" +
            "    \"powerCapacityUnit\": \"kW\",\n" +
            "    \"temperatureUnit\": \"℃\",\n" +
            "    \"lngLatUnit\": \"°\",\n" +
            "    \"speedUnit\": \"m/s\",\n" +
            "    \"installCapacityUnit\": \"MW\",\n" +
            "    \"powerRate\": \"%\",\n" +
            "    \"percentUnit\": \"%\",\n" +
            "    \"times\": \"次\",\n" +
            "    \"productPowerUnit\": \"kWh\",\n" +
            "    \"unit_ton\": \"吨\",\n" +
            "    \"currentUnit\": \"A\",\n" +
            "    \"voltageUnit\": \"V\",\n" +
            "    \"WUnit\": \"W\",\n" +
            "    \"kWUnit\": \"kW\",\n" +
            "    \"WhUnit\": \"Wh\",\n" +
            "    \"kWhUnit\": \"kWh\",\n" +
            "    \"VarUnit\": \"Var\",\n" +
            "    \"kVarUnit\": \"kVar\",\n" +
            "    \"kVarhUnit\": \"kVarh\",\n" +
            "    \"kVAUnit\": \"kVA\",\n" +
            "    \"HzUnit\": \"Hz\",\n" +
            "    \"MΩUnit\": \"MΩ\",\n" +
            "    \"Irradiance\": \"W/m²\",\n" +
            "    \"TInsolation\": \"MJ/m²\",\n" +
            "    \"degree\": \"度\",\n" +
            "    \"NA\": \"无\",\n" +
            "    \"colon\": \"：\",\n" +
            "    \"stationUnit\": \"座\",\n" +
            "    \"inverterUnit\": \"台\",\n" +
            "    \"stringUnit\": \"个\",\n" +
            "    \"RMBUnit\": \"元\",\n" +
            "    \"WRMBUnit\": \"万元\",\n" +
            "    \"KRMBUnit\": \"千元\",\n" +
            "    \"KWUnit\": \"KW\",\n" +
            "    \"WKWUnit\": \"万KW\",\n" +
            "    \"houseUnit\": \"户\",\n" +
            "    \"alarmUnit\": \"条\"\n" +
            "  }";
    //日文环境
    private String jsonStringsjp = "{\n" +
            "    \"topPowerUnit\": \"kW·h\",\n" +
            "    \"powerUnit\": \"万kW·h\",\n" +
            "    \"KWh\": \"kWh\",\n" +
            "    \"WKWh\": \"万kWh\",\n" +
            "    \"KW\": \"kW\",\n" +
            "    \"MW\": \"MW\",\n" +
            "    \"GW\":\"GW\",\n" +
            "    \"GWh\":\"GWh\",\n" +
            "    \"co2Unit\": \"t\",\n" +
            "    \"coalUnit\": \"t\",\n" +
            "    \"treeUnit\": \"m³\",\n" +
            "    \"co2WUnit\": \"万t\",\n" +
            "    \"coalWUnit\": \"万t\",\n" +
            "    \"treeWUnit\": \"万m³\",\n" +
            "    \"tree_unit\": \"棵\",\n" +
            "    \"wtree_unit\": \"万棵\",\n" +
            "    \"curRadiantUnit\": \"kW·h/m²\",\n" +
            "    \"radiantUnit\": \"W/m²\",\n" +
            "    \"powerCapacityUnit\": \"kW\",\n" +
            "    \"temperatureUnit\": \"℃\",\n" +
            "    \"lngLatUnit\": \"°\",\n" +
            "    \"speedUnit\": \"m/s\",\n" +
            "    \"installCapacityUnit\": \"MW\",\n" +
            "    \"powerRate\": \"%\",\n" +
            "    \"percentUnit\": \"%\",\n" +
            "    \"times\": \"回\",\n" +
            "    \"productPowerUnit\": \"kWh\",\n" +
            "    \"unit_ton\": \"吨\",\n" +
            "    \"currentUnit\": \"A\",\n" +
            "    \"voltageUnit\": \"V\",\n" +
            "    \"WUnit\": \"W\",\n" +
            "    \"kWUnit\": \"kW\",\n" +
            "    \"WhUnit\": \"Wh\",\n" +
            "    \"kWhUnit\": \"kWh\",\n" +
            "    \"VarUnit\": \"Var\",\n" +
            "    \"kVarUnit\": \"kVar\",\n" +
            "    \"kVarhUnit\": \"kVarh\",\n" +
            "    \"kVAUnit\": \"kVA\",\n" +
            "    \"HzUnit\": \"Hz\",\n" +
            "    \"MΩUnit\": \"MΩ\",\n" +
            "    \"Irradiance\": \"W/m²\",\n" +
            "    \"TInsolation\": \"MJ/m²\",\n" +
            "    \"degree\": \"度\",\n" +
            "    \"NA\": \"无\",\n" +
            "    \"colon\": \"：\",\n" +
            "    \"stationUnit\": \"座\",\n" +
            "    \"inverterUnit\": \"台\",\n" +
            "    \"stringUnit\": \"个\",\n" +
            "    \"RMBUnit\": \"元\",\n" +
            "    \"WRMBUnit\": \"万元\",\n" +
            "    \"KRMBUnit\": \"千元\",\n" +
            "    \"KWUnit\": \"KW\",\n" +
            "    \"WKWUnit\": \"万KW\",\n" +
            "    \"houseUnit\": \"户\",\n" +
            "    \"alarmUnit\": \"条\"\n" +
            "  }";
    //英文环境
    private String jsonStringsen = "{\n" +
            "    \"topPowerUnit\": \"kW·h\",\n" +
            "    \"powerUnit\": \"万kW·h\",\n" +
            "    \"KWh\": \"kWh\",\n" +
            "    \"WKWh\": \"万kWh\",\n" +
            "    \"KW\": \"kW\",\n" +
            "    \"MW\": \"MW\",\n" +
            "    \"GW\":\"GW\",\n" +
            "    \"GWh\":\"GWh\",\n" +
            "    \"co2Unit\": \"t\",\n" +
            "    \"coalUnit\": \"t\",\n" +
            "    \"treeUnit\": \"m³\",\n" +
            "    \"co2WUnit\": \"万t\",\n" +
            "    \"coalWUnit\": \"万t\",\n" +
            "    \"treeWUnit\": \"万m³\",\n" +
            "    \"tree_unit\": \"棵\",\n" +
            "    \"wtree_unit\": \"万棵\",\n" +
            "    \"curRadiantUnit\": \"kW·h/m²\",\n" +
            "    \"radiantUnit\": \"W/m²\",\n" +
            "    \"powerCapacityUnit\": \"kW\",\n" +
            "    \"temperatureUnit\": \"℃\",\n" +
            "    \"lngLatUnit\": \"°\",\n" +
            "    \"speedUnit\": \"m/s\",\n" +
            "    \"installCapacityUnit\": \"MW\",\n" +
            "    \"powerRate\": \"%\",\n" +
            "    \"percentUnit\": \"%\",\n" +
            "    \"times\": \"Times\",\n" +
            "    \"productPowerUnit\": \"kWh\",\n" +
            "    \"unit_ton\": \"吨\",\n" +
            "    \"currentUnit\": \"A\",\n" +
            "    \"voltageUnit\": \"V\",\n" +
            "    \"WUnit\": \"W\",\n" +
            "    \"kWUnit\": \"kW\",\n" +
            "    \"WhUnit\": \"Wh\",\n" +
            "    \"kWhUnit\": \"kWh\",\n" +
            "    \"VarUnit\": \"Var\",\n" +
            "    \"kVarUnit\": \"kVar\",\n" +
            "    \"kVarhUnit\": \"kVarh\",\n" +
            "    \"kVAUnit\": \"kVA\",\n" +
            "    \"HzUnit\": \"Hz\",\n" +
            "    \"MΩUnit\": \"MΩ\",\n" +
            "    \"Irradiance\": \"W/m²\",\n" +
            "    \"TInsolation\": \"MJ/m²\",\n" +
            "    \"degree\": \"degrees\",\n" +
            "    \"NA\": \"无\",\n" +
            "    \"colon\": \"：\",\n" +
            "    \"stationUnit\": \"座\",\n" +
            "    \"inverterUnit\": \"台\",\n" +
            "    \"stringUnit\": \"个\",\n" +
            "    \"RMBUnit\": \"元\",\n" +
            "    \"WRMBUnit\": \"万元\",\n" +
            "    \"KRMBUnit\": \"千元\",\n" +
            "    \"KWUnit\": \"KW\",\n" +
            "    \"WKWUnit\": \"万KW\",\n" +
            "    \"houseUnit\": \"户\",\n" +
            "    \"alarmUnit\": \"条\"\n" +
            "  }";

    public JSONObject getObject() {
        try {
            if (jsonObject == null) {

                switch (Locale.getDefault().getLanguage()){
                    case "zh":
                        jsonObject = new JSONObject(jsonStringszh);
                        break;
                    case "jp":
                        jsonObject = new JSONObject(jsonStringsjp);
                        break;
                    default:
                        jsonObject = new JSONObject(jsonStringsen);
                        break;
                }
            }
        } catch (JSONException e) {
            Log.e(TAG, "getObject: "+e.getMessage() );
        }
        return jsonObject;
    }
}
