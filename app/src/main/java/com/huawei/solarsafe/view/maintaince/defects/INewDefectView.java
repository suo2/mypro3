package com.huawei.solarsafe.view.maintaince.defects;

import com.huawei.solarsafe.bean.defect.DefectDetail;
import com.huawei.solarsafe.bean.defect.WorkFlowBean;

import java.util.List;

/**
 * Created by p00319 on 2017/2/16.
 */

public interface INewDefectView {
    void submitSuccess(String dfId);
    void submitFailed();
    void loadPicture(String path);
    void setData(DefectDetail data);

    void onFileDelete(boolean success);
    void loadWorkFlow(List<WorkFlowBean> list);
}
