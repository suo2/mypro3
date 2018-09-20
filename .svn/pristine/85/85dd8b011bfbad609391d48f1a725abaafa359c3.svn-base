package com.huawei.solarsafe.presenter.personal;

import com.huawei.solarsafe.MyApplication;
import com.huawei.solarsafe.R;
import com.huawei.solarsafe.bean.BaseEntity;
import com.huawei.solarsafe.bean.personmanagement.SystemVersionBean;
import com.huawei.solarsafe.model.personal.AboutModel;
import com.huawei.solarsafe.model.personal.IAboutModel;
import com.huawei.solarsafe.net.CommonCallback;
import com.huawei.solarsafe.presenter.BasePresenter;
import com.huawei.solarsafe.utils.ToastUtil;
import com.huawei.solarsafe.view.personal.IAboutView;

import okhttp3.Call;

/**
 * <pre>
 *     author: Tzy
 *     time  : $date$
 *     desc  : 关于界面控制器
 * </pre>
 */
public class AboutPresenter extends BasePresenter<IAboutView, IAboutModel> {
    public static final String TAG = AboutPresenter.class.getSimpleName();

    public AboutPresenter() {
        setModel(new AboutModel());
    }

    public void doGetSystemVersion() {
        model.getSystemVersion(new CommonCallback(SystemVersionBean.class) {
            @Override
            public void onResponse(BaseEntity response, int id) {
                if (view != null) {
                    if (response instanceof SystemVersionBean) {
                        view.getSystemVersionData(response);
                    } else {
                        ToastUtil.showMessage(MyApplication.getContext().getString(R.string.net_error));
                    }
                }
            }

            @Override
            public void onError(Call call, Exception e, int id) {
                super.onError(call, e, id);
                ToastUtil.showMessage(MyApplication.getContext().getString(R.string.net_error));
            }
        });
    }

}
