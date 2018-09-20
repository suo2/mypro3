package com.huawei.solarsafe.model.maintain.patrol;

import com.huawei.solarsafe.bean.patrol.PatrolItem;
import com.huawei.solarsafe.bean.patrol.PatrolReport;
import com.huawei.solarsafe.bean.patrol.PatrolStep;
import com.huawei.solarsafe.model.BaseModel;
import com.huawei.solarsafe.net.CommonCallback;
import com.huawei.solarsafe.net.NetRequest;
import com.huawei.solarsafe.utils.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Create Date: 2017/3/3
 * Create Author: p00213
 * Description :
 */
public class PatrolGisModel implements IPatrolGisModel, BaseModel {
    NetRequest request = NetRequest.getInstance();

    @Override
    public void requestListItemReport(Map<String, String> param, CommonCallback callback) {
        String url = NetRequest.IP + URL_LIST_ITEM_REPORT;
        request.asynPostJson(url, param, callback);
    }


    @Override
    public void requestComplInspect(PatrolReport patrolReport, CommonCallback callback) {
        String url = URL_SINGLE_INSPEC;
        final HashMap<String, Map<String, String>> urlMap = new HashMap<>();
        urlMap.put("inspectDetail", patrolReport.getBaseInfo());
        // 构造步骤json
        List<Map<String, String>> stepList = new ArrayList<>();
        if (patrolReport.getSteps() != null) {
            for (PatrolStep patrolStep : patrolReport.getSteps()) {
                Map<String, String> map = new HashMap<>();
                map.put(PatrolStep.KEY_STEP_CODE, String.valueOf(patrolStep.getStepCode()));
                map.put(PatrolStep.KEY_STEP_TIME, String.valueOf(patrolStep.getStepTime()));
                map.put(PatrolStep.KEY_STEP_REMARK, patrolStep.getStepRemark());
                stepList.add(map);
            }
        }
        // 构造检查项json
        List<Map<String, String>> itemList = new ArrayList<Map<String, String>>();
        List<PatrolItem> patrolItemList = patrolReport.getItems();
        if (patrolItemList != null && patrolItemList.size() > 0) {
            for (PatrolItem patrolItem : patrolReport.getItems()) {
                Map<String, String> map = new HashMap<String, String>();
                map.put("itemId", patrolItem.getAnnexItemId());
                map.put("itemResult", String.valueOf(patrolItem.getAnnexItemResult()));
                map.put("itemRemark", patrolItem.getAnnexItemRemark());
                itemList.add(map);
            }
        }
        final HashMap<String, List<Map<String, String>>> urlMap2 = new HashMap<String, List<Map<String, String>>>();
        //宋平修改，不用去对集合的size做判断
        urlMap2.put("inspectSteps", stepList);
        urlMap2.put("inspectItems", itemList);
        request.asynPostJsonString(url, Utils.createReqArgs(urlMap, urlMap2), callback);

    }

    @Override
    public void requestCheckItems(Map<String, String> param, CommonCallback callback) {
        String url = NetRequest.IP + URL_LIST_CHECK_ITEM;
        request.asynPostJson(url, param, callback);
    }
}
