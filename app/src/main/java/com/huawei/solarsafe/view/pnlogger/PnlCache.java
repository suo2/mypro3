package com.huawei.solarsafe.view.pnlogger;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Create Date: 2017/3/15
 * Create Author: P00028
 * Description :
 */
public class PnlCache {
    private  Map<String, List<String>> pvInfos = new HashMap<>();
    private Map<String, String> pvNames = new HashMap<>();
    private Map<String,List<String>> pvMap=new HashMap<>();

    protected void putPvInfo(Map<String, List<String>> infos) {
        pvInfos = infos;
    }

    public Map<String, List<String>> getPvInfos() {
        return pvInfos;
    }

    protected void clearPvInfo(){
        pvInfos.clear();
    }

    protected void clearPvName(){
        pvNames.clear();
    }
    protected void clearPvMap(){
        pvMap.clear();
    }

    public Map<String, String> getPvNames() {
        return pvNames;
    }

    public void setPvNames(Map<String, String> pvNames) {
        this.pvNames = pvNames;
    }
}