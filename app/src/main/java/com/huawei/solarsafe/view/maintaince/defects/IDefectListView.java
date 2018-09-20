package com.huawei.solarsafe.view.maintaince.defects;

import com.huawei.solarsafe.bean.defect.DefectDetail;

import java.util.List;

/**
 * Created by p00319 on 2017/2/16.
 */

public interface IDefectListView {
    /** 请求缺陷列表失败 */
    void requestListFailed(String errorMsg);

    /** 请求缺陷列表成功 */
    void requestListSuccess(List<DefectDetail> list);
}
