package com.huawei.solarsafe.presenter.personal;

import com.huawei.solarsafe.bean.BaseEntity;
import com.huawei.solarsafe.bean.user.info.LatestLoginInfo;
import com.huawei.solarsafe.model.personal.LatestLoginModel;
import com.huawei.solarsafe.net.CommonCallback;
import com.huawei.solarsafe.presenter.BasePresenter;
import com.huawei.solarsafe.view.personal.ILatestLoginView;

import java.util.Map;

import okhttp3.Call;

/**
 * Created by yWX543898 on 2018/2/23.
 */

public class LatestLoginPresenter extends BasePresenter<ILatestLoginView,LatestLoginModel> implements ILatestLoginPresenter{

    public LatestLoginPresenter() {
        setModel(new LatestLoginModel());
    }

    @Override
    public void requestData(Map<String, String> param) {
        model.requestLatestLoginInfo(param, new CommonCallback(LatestLoginInfo.class) {
            @Override
            public void onResponse(BaseEntity response, int id) {
                if (view != null){
                    if(response == null) {
                        view.noNet();
                    }else{
                        view.getData(response);
                    }
                }
            }

            @Override
            public void onError(Call call, Exception e, int id) {
                super.onError(call, e, id);
                view.noNet();
            }

        });
    }

}
