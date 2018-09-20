package com.huawei.solarsafe.presenter.personal;

import com.huawei.solarsafe.bean.BaseEntity;
import com.huawei.solarsafe.bean.user.info.CompanyImformationBean;
import com.huawei.solarsafe.model.personal.PimformationModel;
import com.huawei.solarsafe.net.CommonCallback;
import com.huawei.solarsafe.presenter.BasePresenter;
import com.huawei.solarsafe.view.personal.PimformationView;

import okhttp3.Call;

/**
 * Created by p00507
 * on 2017/4/10.
 */
public class CompanyImformationPresenter extends BasePresenter<PimformationView, PimformationModel> {
    public CompanyImformationPresenter() {
        setModel(new PimformationModel());
    }

    public void getComImformation() {
        // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0001_F03_Android_S01  删除Android的demo账号    【修改人】：zhaoyufeng
        model.getCompanyImformation(new CommonCallback(CompanyImformationBean.class) {
            @Override
            public void onError(Call call, Exception e, int id) {
                super.onError(call,e,id);
                view.getDataImformationFailed("获取公司信息失败！");
            }

            @Override
            public void onResponse(BaseEntity response, int id) {
                view.getDataImformationSuccess(response);
            }
        });
    }
}
