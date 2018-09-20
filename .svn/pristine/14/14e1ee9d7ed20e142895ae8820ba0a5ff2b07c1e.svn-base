package com.huawei.solarsafe.view.homepage.station;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.huawei.solarsafe.R;
import com.huawei.solarsafe.bean.BaseEntity;
import com.huawei.solarsafe.bean.station.kpi.StationList;
import com.huawei.solarsafe.bean.station.kpi.StationListItemDataBean;
import com.huawei.solarsafe.presenter.homepage.StationListPresenter;
import com.huawei.solarsafe.utils.ToastUtil;
import com.huawei.solarsafe.utils.customview.LoadingDialog;
import com.huawei.solarsafe.view.homepage.station.verticalviewpager.StationSearchListAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 多站页面 电站列表fragment
 */
public class StationFragment extends Fragment implements View.OnClickListener, IStationListView {
    private View mainView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView stationListView;
    private View emptyView;
    //分页使用
    private int page = 1;
    private int pageSize = 10;
    private int pageCount = 0;

    private int lastVisibleItem;

    private StationSearchListAdapter adapter;
    private boolean isRefreshing = false;
    private StationListPresenter presenter;
    private List<StationListItemDataBean> stationInfoList = new ArrayList<>();

    public StationFragment() {
        // Required empty public constructor
    }

    public static StationFragment newInstance() {
        StationFragment fragment = new StationFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new StationListPresenter();
        presenter.onViewAttached(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mainView = inflater.inflate(R.layout.fragment_station_fragment, container, false);
        emptyView = mainView.findViewById(R.id.empty_view);
        swipeRefreshLayout = (SwipeRefreshLayout) mainView.findViewById(R.id.station_infos_swiperefresh);
        stationListView = (RecyclerView) mainView.findViewById(R.id.station_infos);
        adapter = new StationSearchListAdapter(getActivity(), stationInfoList);
        stationListView.setAdapter(adapter);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        stationListView.setLayoutManager(manager);
        stationListView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return isRefreshing;
            }
        });
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                resetRefreshStatus();
                showLoading();
                requestData();
            }
        });
        stationListView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE
                        && lastVisibleItem + 1 == adapter.getItemCount()
                        && pageCount > 1) {
                    if (page > pageCount && pageCount != 0) {
                        page--;
                        ToastUtil.showMessage(R.string.no_more_data);
                        return;
                    }
                    page++;
                    swipeRefreshLayout.setRefreshing(true);
                    showLoading();
                    requestData();
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager lm = (LinearLayoutManager) recyclerView.getLayoutManager();
                lastVisibleItem = lm.findLastVisibleItemPosition();
            }
        });
        return mainView;
    }

    private void requestData() {
        HashMap<String,String> params = new HashMap<>();
        params.put("page", page + "");
        params.put("pageSize", pageSize + "");
        params.put("orderBy", "daycapacity");
        params.put("sort", "desc");
        presenter.requestStationList(params);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

        }
    }

    @Override
    public void onResume() {
        super.onResume();
        resetRefreshStatus();
        requestData();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.onViewDetached();
    }

    @Override
    public void back() {

    }

    @Override
    public void getStationListData(StationList stationInfos) {
        dismissLoading();
        if (swipeRefreshLayout != null && swipeRefreshLayout.isRefreshing()) {
            swipeRefreshLayout.setRefreshing(false);
        }
        if (isRefreshing) {
            stationInfoList.clear();
        }
        if (stationInfos == null) {
            if(adapter.getItemCount() == 0){
                emptyView.setVisibility(View.VISIBLE);
                adapter.notifyDataSetChanged();
            }else{
                emptyView.setVisibility(View.GONE);
            }
            return;
        }
        isRefreshing = false;
        if (page > pageCount && pageCount != 0) {
            return;
        }
        if (pageCount == 0) {
            if(stationInfos.getTotal() % pageSize == 0){
                pageCount = stationInfos.getTotal() / pageSize;
            }else{
                pageCount = stationInfos.getTotal() / pageSize + 1;
            }
        }
        if (stationInfoList == null) {
            stationInfoList = new ArrayList<>();
        }
        if (stationInfos.getStationInfoList() != null) {
            stationInfoList.addAll(stationInfos.getDataBean().getList());
        }
        if (stationInfoList != null) {
            if (adapter == null) {
                adapter = new StationSearchListAdapter(getActivity(), stationInfoList);
                stationListView.setAdapter(adapter);
            }
            adapter.notifyDataSetChanged();
        }
        if(adapter.getItemCount() == 0){
            emptyView.setVisibility(View.VISIBLE);
        }else{
            emptyView.setVisibility(View.GONE);
        }
    }

    @Override
    public void getStationMapListData(BaseEntity baseEntity) {

    }

    @Override
    public void jumpToMap() {

    }

    /**
     * 重置刷新数据 状态
     */
    private void resetRefreshStatus() {
        page = 1;
        pageCount = 0;
        isRefreshing = true;
    }

    private LoadingDialog loadingDialog;

    public void showLoading() {
        if (loadingDialog == null) {
            loadingDialog = new LoadingDialog(getActivity());
        }
        loadingDialog.show();
    }

    public void dismissLoading() {
        if (loadingDialog == null) {
            loadingDialog = new LoadingDialog(getActivity());
        }
        if (loadingDialog != null) {
            loadingDialog.dismiss();
        }
    }
}
