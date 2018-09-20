package com.huawei.solarsafe.presenter.stationmanagement;


import com.huawei.solarsafe.MyApplication;
import com.huawei.solarsafe.R;
import com.huawei.solarsafe.bean.BaseEntity;
import com.huawei.solarsafe.bean.GlobalConstants;
import com.huawei.solarsafe.bean.stationmagagement.DevInfoListBean;
import com.huawei.solarsafe.model.stationmanagement.INewDeviceAccessModel;
import com.huawei.solarsafe.model.stationmanagement.NewDeviceAccessModel;
import com.huawei.solarsafe.net.CommonCallback;
import com.huawei.solarsafe.presenter.BasePresenter;
import com.huawei.solarsafe.utils.ToastUtil;
import com.huawei.solarsafe.view.stationmanagement.INewDeviceAccessView;

import java.util.HashMap;

import okhttp3.Call;

/**
 * <pre>
 *     author: Tzy
 *     time  : $date$
 *     desc  : 新接入设备界面控制器
 * </pre>
 */
public class NewDeviceAccessPresenter extends BasePresenter<INewDeviceAccessView,INewDeviceAccessModel> {
    public static final String TAG=NewDeviceAccessPresenter.class.getSimpleName();

    public NewDeviceAccessPresenter(){
        setModel(new NewDeviceAccessModel());
    }

    //执行获取新接入设备信息请求
    public void doGetNewDeviceInfos(){
        HashMap<String,String> hashMap=new HashMap<>();
        String userId= String.valueOf(GlobalConstants.userId);
        hashMap.put("userId",userId);
        model.getNewDeviceInfos(hashMap, new CommonCallback(DevInfoListBean.class) {
            @Override
            public void onResponse(BaseEntity response, int id) {
                if (view!=null){
                    DevInfoListBean devInfoListBean= (DevInfoListBean) response;
                    if (devInfoListBean == null || devInfoListBean.getData()==null || devInfoListBean.getData().isEmpty()){
                        view.getNewDeviceInfos(null);
                    }else{
                        view.getNewDeviceInfos(response);
                    }
                }
            }

            @Override
            public void onError(Call call, Exception e, int id) {
                super.onError(call, e, id);
                ToastUtil.showMessage(MyApplication.getContext().getString(R.string.net_error));
                if (view!=null){
                    view.getNewDeviceInfos(null);
                }
            }
        });
    }
}
