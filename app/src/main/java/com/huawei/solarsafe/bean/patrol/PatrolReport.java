package com.huawei.solarsafe.bean.patrol;

import java.util.List;
import java.util.Map;

/**
 * Create Date: 2017/3/6
 * Create Author: p00213
 * Description :巡检报告
 */
public class PatrolReport {

    // 巡检记录基础信息(sId 电站编号, taskId 任务编号, annexObjId 巡检对象编号, annexRemark 巡检备注(可空),
    // annexResult 巡检结果（结果码）)
    private Map<String, String> mBaseInfo;
    // 巡检步骤(stepCode 步骤码(进站、出站), stepTime 时标, stepRemark 步骤备注（可空）)
    private List<PatrolStep> mSteps;
    // 巡检项结果
    private List<PatrolItem> mItems;

    public Map<String, String> getBaseInfo()
    {
        return mBaseInfo;
    }

    public void setBaseInfo(Map<String, String> baseInfo)
    {
        mBaseInfo = baseInfo;
    }

    public List<PatrolStep> getSteps()
    {
        return mSteps;
    }

    public void setSteps(List<PatrolStep> steps)
    {
        mSteps = steps;
    }

    public List<PatrolItem> getItems()
    {
        return mItems;
    }

    public void setItems(List<PatrolItem> items)
    {
        mItems = items;
    }


    @Override
    public String toString()
    {
        return "PatrolReport [mBaseInfo=" + mBaseInfo.toString() + ", mSteps=" + mSteps.toString() + ", mItems=" + mItems.toString()
                +  "]";
    }

}
