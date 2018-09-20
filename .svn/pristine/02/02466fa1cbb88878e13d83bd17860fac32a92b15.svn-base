package com.huawei.solarsafe.view.maintaince.defects;

import com.amap.api.maps.model.LatLng;
import com.huawei.solarsafe.bean.BaseEntity;
import com.huawei.solarsafe.bean.defect.WorkFlowBean;

import java.util.List;

/**
 * Created by p00319 on 2017/2/22.
 */

public interface IDefectDetailView {

    void loadWorkFlow(List<WorkFlowBean> list);

    void loadDefectDetail(BaseEntity data);

    void loadPicture(String path);
    void onFileDelete(boolean success);

    void setStaionPos(LatLng latLng);

    /** 缺陷单更新成功 */
    void updateSuccess(String dfId);

    void updateFailed();

    void checkCanHandleProc(String msg);
}
