package com.huawei.solarsafe.presenter.homepage;

import android.util.Log;

import com.huawei.solarsafe.MyApplication;
import com.huawei.solarsafe.R;
import com.huawei.solarsafe.bean.BaseEntity;
import com.huawei.solarsafe.bean.station.kpi.StationList;
import com.huawei.solarsafe.bean.station.map.StationMapList;
import com.huawei.solarsafe.model.homepage.StationListModel;
import com.huawei.solarsafe.net.CommonCallback;
import com.huawei.solarsafe.presenter.BasePresenter;
import com.huawei.solarsafe.utils.ToastUtil;
import com.huawei.solarsafe.view.homepage.station.IStationListView;

import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;

/**
 * Created by p00322 on 2017/1/4.
 */
public class StationListPresenter extends BasePresenter<IStationListView, StationListModel> {
    public static final String TAG = StationListPresenter.class.getSimpleName();

    public StationListPresenter() {
        setModel(new StationListModel());
    }


    public void requestStationList(HashMap<String,String> params) {

        model.requestStationList(params, new CommonCallback(StationList.class) {
            @Override
            public void onError(Call call, Exception e, int id) {
                if (view != null) {
                    view.getStationListData(null);
                }
                ToastUtil.showMessage(MyApplication.getContext().getString(R.string.net_error));
            }

            @Override
            public void onResponse(BaseEntity response, int id) {
                if (response == null) {
                    if (view != null) {
                        view.getStationListData(null);
                    }
                    return;
                }
                view.getStationListData((StationList) response);

            }
        });
    }

    public void requestStationListByStationCodes(int page, int pagesize, String stationCodes) {
        // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0001_F03_Android_S01  删除Android的demo账号    【修改人】：zhaoyufeng
        model.requestStationListByStationCodes(page, pagesize, stationCodes, new CommonCallback(StationList.class) {
            @Override
            public void onError(Call call, Exception e, int id) {
                super.onError(call,e,id);
                // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0004_F01_Android_S02  敏感信息检查-日志中不能包含敏感信息    【修改人】：zhaoyufeng
                Log.e(TAG, "request StationList failed! " + e);
                if (e.toString().contains("java.net.ConnectException")) {
                    ToastUtil.showMessage(MyApplication.getContext().getString(R.string.net_error));
                }
            }

            @Override
            public void onResponse(BaseEntity response, int id) {
                if (view==null||response == null) {
                    return;
                }
                view.getStationListData((StationList) response);

            }
        });
    }

    public void requestStationMapList(Map<String, String> params) {
        // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0001_F03_Android_S01  删除Android的demo账号    【修改人】：zhaoyufeng
        model.requestStationMapList(params, new CommonCallback(StationMapList.class) {
            @Override
            public void onError(Call call, Exception e, int id) {
                super.onError(call,e,id);
                // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0004_F01_Android_S02  敏感信息检查-日志中不能包含敏感信息    【修改人】：zhaoyufeng
                Log.e(TAG, "request StationList failed !" + e);
                if (e.toString().contains("java.net.ConnectException")) {
                    ToastUtil.showMessage(MyApplication.getContext().getString(R.string.net_error));
                }
                if (view != null) {
                    view.getStationMapListData(null);
                }
            }

            @Override
            public void onResponse(BaseEntity response, int id) {
                if (view != null) {
                    view.getStationMapListData(response);
                }
            }
        });
    }

    public void activityFinish() {
        view.back();
    }

}
