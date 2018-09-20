package com.huawei.solarsafe.presenter.stationmanagement;

import com.huawei.solarsafe.MyApplication;
import com.huawei.solarsafe.R;
import com.huawei.solarsafe.bean.BaseEntity;
import com.huawei.solarsafe.bean.stationmagagement.DevInfo;
import com.huawei.solarsafe.model.stationmanagement.INewEquipmentModel;
import com.huawei.solarsafe.model.stationmanagement.NewEquipmentModel;
import com.huawei.solarsafe.net.CommonCallback;
import com.huawei.solarsafe.presenter.BasePresenter;
import com.huawei.solarsafe.utils.ToastUtil;
import com.huawei.solarsafe.view.stationmanagement.INewEquipmentView;

import okhttp3.Call;

/**
 * <pre>
 *     author: Tzy
 *     time  : $date$
 *     desc  : 新增设备界面控制器
 * </pre>
 */
public class NewEquipmentPresenter extends BasePresenter<INewEquipmentView,INewEquipmentModel> {

    public NewEquipmentPresenter(){
        setModel(new NewEquipmentModel());
    }

    public void doGetDevByEsnRequest(String esn){
        model.getDevByEsnRequest(esn, new CommonCallback(DevInfo.class) {

            @Override
            public void onError(Call call, Exception e, int id) {
                super.onError(call, e, id);
                ToastUtil.showMessage(MyApplication.getContext().getString(R.string.net_error));
                if(view != null){
                    view.getDevByEsnResponse(null);
                }
            }

            @Override
            public void onResponse(BaseEntity response, int id) {
                if (response==null){
                    view.getDevByEsnResponse(null);
                }else{
                    DevInfo devInfo= (DevInfo) response;
                    view.getDevByEsnResponse(devInfo);
                }
            }
        });
    }
}
