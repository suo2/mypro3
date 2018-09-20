package com.huawei.solarsafe.presenter.pnlogger;


import com.huawei.solarsafe.MyApplication;
import com.huawei.solarsafe.R;
import com.huawei.solarsafe.bean.BaseEntity;
import com.huawei.solarsafe.bean.pnlogger.PntStationList;
import com.huawei.solarsafe.model.pnlogger.StationOperator;
import com.huawei.solarsafe.net.CommonCallback;
import com.huawei.solarsafe.presenter.BasePresenter;
import com.huawei.solarsafe.view.pnlogger.IStationListView;
import com.huawei.solarsafe.view.pnlogger.PnlCache;
import java.util.List;
import java.util.Map;

/**
 * Create Date: 2017/3/7
 * Create Author: P00028
 * Description :
 */
public class StationListPresenter extends BasePresenter<IStationListView, StationOperator> implements
        IStationListPresenter {
    private int count = 0;
    private PnlCache pnlCache = new PnlCache();

    public StationListPresenter() {
        setModel(new StationOperator());
    }

    @Override
    public void refreshListData() {
        model.getStationList(new CommonCallback(PntStationList.class) {
            @Override
            public void onResponse(BaseEntity response, int id) {
                PntStationList temp = (PntStationList) response;
                if (view != null) {
                    if (temp != null && temp.isSuccess()) {
                        view.nofifyDataChange(temp.getData());
                    } else {
                        view.toastFail(MyApplication.getContext().getString(R.string.get_station_list_failed));
                    }
                }

            }
        });
    }

    @Override
    public void onItemClick() {
        if (view != null) {
            view.itemClick();
        }
    }


    @Override
    public void queryDevBindStatus(final List<String> esns) {
        if (esns == null || esns.isEmpty()) {
            return;
        }
        if (view != null) {
            Map<String, List<String>> pvs = pnlCache.getPvInfos();
            Map<String, String> names = pnlCache.getPvNames();
            count = pvs.size() + names.size() + esns.size();
            if (count == 0) {
                view.dismissDialog();
                return;
            }
        }
    }
}