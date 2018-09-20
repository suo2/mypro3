package com.huawei.solarsafe.view.devicemanagement;

import android.app.NotificationManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ScrollView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.huawei.solarsafe.MyApplication;
import com.huawei.solarsafe.R;
import com.huawei.solarsafe.bean.BaseEntity;
import com.huawei.solarsafe.bean.device.DevAlarmBean;
import com.huawei.solarsafe.bean.device.DevAlarmInfo;
import com.huawei.solarsafe.bean.device.DevTypeConstant;
import com.huawei.solarsafe.presenter.devicemanagement.BoosterStationDevPresenter;
import com.huawei.solarsafe.utils.ToastUtil;
import com.huawei.solarsafe.view.maintaince.ivcurve.MyHorizontalScrollView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.content.Context.NOTIFICATION_SERVICE;
import static android.view.View.VISIBLE;

/**
 * Created by P00708 on 2018/3/2.
 * 升压站告警信息
 */

public class BoosterStationAlarmInformationFragment extends Fragment implements View.OnClickListener, IBoosterStationDevView {
    private final String TAG = BoosterStationAlarmInformationFragment.class.getName();
    private View rootView;
    private ImageView alarmInformationFilter;
    private AlarmInformationItemView alarmInformationItemView;
    private PullToRefreshScrollView pullToRefreshScrollView;
    private LinearLayout emptyViewContainer;
    private ListView leftListView;
    private ListView rightListView;
    private AlarmLeftInformationAdapter leftInformationAdapter;
    private AlarmRightInformationAdapter rightInformationAdapter;
    private FrameLayout grayBackground;//显示popupWindows屏幕变灰
    private BoosterStationDevPresenter presenter;
    private MyHorizontalScrollView titleHorizontalScrollView;
    private MyHorizontalScrollView contentHorizontalScrollView;
    int page = 1;
    int pageSize = 50;
    private String devId;
    private String devTypeId;
    private View emptyView;
    List<DevAlarmInfo.ListBean> list;
    private String devTypeName;

    public static BoosterStationAlarmInformationFragment newInstance(String devId, String devTypeId) {
        BoosterStationAlarmInformationFragment fragment = new BoosterStationAlarmInformationFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        args.putString("devId", devId);
        args.putString("devTypeId", devTypeId);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new BoosterStationDevPresenter();
        presenter.onViewAttached(this);
        devId = getArguments().getString("devId");
        devTypeId = getArguments().getString("devTypeId");
        list = new ArrayList<>();
        Map<Integer, String> devTypeMap = DevTypeConstant.getDevTypeMap(getContext());
        devTypeName = devTypeMap.get(Integer.valueOf(devTypeId));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.booster_station_alarm_information_layout, container, false);
            alarmInformationFilter = (ImageView) rootView.findViewById(R.id.alarm_information_filter);
            alarmInformationItemView = (AlarmInformationItemView) rootView.findViewById(R.id.alarm_information_item_view);
            pullToRefreshScrollView = (PullToRefreshScrollView) rootView.findViewById(R.id.inverter_report__refresh_scrollview);
            emptyViewContainer = (LinearLayout) rootView.findViewById(R.id.alarm_information_container);
            leftListView = (ListView) rootView.findViewById(R.id.left_container_listview);
            rightListView = (ListView) rootView.findViewById(R.id.right_container_listview);
            titleHorizontalScrollView = (MyHorizontalScrollView) rootView.findViewById(R.id.title_horizontal_scrollView);
            contentHorizontalScrollView = (MyHorizontalScrollView) rootView.findViewById(R.id.iv_content_horizontal_scrollView);
            leftInformationAdapter = new AlarmLeftInformationAdapter(getContext(), list);
            rightInformationAdapter = new AlarmRightInformationAdapter(getContext(), list, devTypeName);
            leftListView.setAdapter(leftInformationAdapter);
            rightListView.setAdapter(rightInformationAdapter);
            emptyView = View.inflate(getContext(), R.layout.empty_view, null);
            alarmInformationFilter.setOnClickListener(this);
            pullToRefreshScrollView.getLoadingLayoutProxy(false, true);
            //BOTH 可以上拉、可以下拉
            pullToRefreshScrollView.setMode(PullToRefreshBase.Mode.BOTH);
            // 下拉刷新
            pullToRefreshScrollView.getLoadingLayoutProxy(true, false).setPullLabel(getString(R.string.pull_refresh));
            pullToRefreshScrollView.getLoadingLayoutProxy(true, false).setRefreshingLabel(getString(R.string.updating));
            pullToRefreshScrollView.getLoadingLayoutProxy(true, false).setReleaseLabel(getString(R.string.release_load));
            // 上拉加载更多，分页加载
            pullToRefreshScrollView.getLoadingLayoutProxy(false, true).setPullLabel(getString(R.string.load_more));
            pullToRefreshScrollView.getLoadingLayoutProxy(false, true).setRefreshingLabel(getString(R.string.updating));
            pullToRefreshScrollView.getLoadingLayoutProxy(false, true).setReleaseLabel(getString(R.string.release_load));
            pullToRefreshScrollView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ScrollView>() {
                @Override
                public void onPullDownToRefresh(PullToRefreshBase<ScrollView> refreshView) {
                    try {
                        NotificationManager manager = (NotificationManager) getActivity().getSystemService(NOTIFICATION_SERVICE);
                        manager.cancelAll();
                    } catch (Exception e) {
                        Log.e(TAG, "no notification NULLPOINTEXCEPTION", e);
                    }
                    titleHorizontalScrollView.scrollTo(0, 0);
                    page = 1;
                    requestData();

                }

                @Override
                public void onPullUpToRefresh(PullToRefreshBase<ScrollView> refreshView) {
                    page++;
                    requestData();

                }
            });
            requestData();

        }
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    public void updateView() {
        if (contentHorizontalScrollView.getCanScrollMaxDuration() == 0) {
            alarmInformationItemView.postDelayed(new Runnable() {
                @Override
                public void run() {
                    int canScrollDuration = alarmInformationItemView.getMeasuredWidth() - titleHorizontalScrollView.getMeasuredWidth();
                    contentHorizontalScrollView.setCanScrollMaxDuration(canScrollDuration);
                }
            }, 200);
        }
        if (rightInformationAdapter.getCount() == 0) {
            if (emptyViewContainer.getChildAt(0).equals(emptyView)) {
                final ViewGroup.LayoutParams params = emptyView.getLayoutParams();
                if (params.width == 0) {
                    pullToRefreshScrollView.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            params.height = pullToRefreshScrollView.getHeight();
                            params.width = pullToRefreshScrollView.getWidth();
                            emptyView.setLayoutParams(params);
                        }
                    }, 100);
                }

            }
        }
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.alarm_information_filter:
                if (grayBackground == null) {
                    addGrayBackground();
                } else {
                    grayBackground.setVisibility(VISIBLE);
                }
                break;

            default:
                break;

        }

    }

    /**
     * 计算ListView的高度
     *
     * @param listView
     */
    public void setListViewHeightBasedOnChildren(ListView listView) {
        /**
         * getAdapter这个方法主要是为了获取到ListView的数据条数，所以设置之前必须设置Adapter
         */
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }
        int totalHeight = 0;
        Context context = getContext() == null ? MyApplication.getApplication().getApplicationContext() : getContext();
        totalHeight = (int) (context.getResources().getDimension(R.dimen.alarm_list_content_high) * listAdapter.getCount());
        ViewGroup.LayoutParams params = listView.getLayoutParams();
//        //真正的高度需要加上分割线的高度
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }

    private void addGrayBackground() {
        grayBackground = new FrameLayout(getContext());
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(getActivity().getWindowManager().getDefaultDisplay().getWidth(), getActivity().getWindowManager().getDefaultDisplay().getHeight());
        grayBackground.setBackgroundColor(0x77000000);
        if (getActivity().getParent() != null) {
            getActivity().getParent().addContentView(grayBackground, layoutParams);
        } else {
            getActivity().addContentView(grayBackground, layoutParams);
        }
    }

    @Override
    public void requestData() {
        Map<String, String> params = new HashMap<>();
        params.put("devId", devId);
        params.put("page", page + "");
        params.put("pageSize", pageSize + "");
        presenter.requestDevAlarm(params);
    }

    private void initScrollView() {
        titleHorizontalScrollView.setScrollView(contentHorizontalScrollView);
        contentHorizontalScrollView.setScrollView(titleHorizontalScrollView);
//        //添加左侧数据
        setListViewHeightBasedOnChildren(leftListView);
//        // 添加右边内容数据
        setListViewHeightBasedOnChildren(rightListView);
        alarmInformationItemView.post(new Runnable() {
            @Override
            public void run() {
                int canScrollDuration = alarmInformationItemView.getMeasuredWidth() - titleHorizontalScrollView.getMeasuredWidth();
                contentHorizontalScrollView.setCanScrollMaxDuration(canScrollDuration);
            }
        });
    }


    @Override
    public void getData(BaseEntity baseEntity) {
        pullToRefreshScrollView.onRefreshComplete();
        if (baseEntity == null || getContext() == null) {
            return;
        }
        if (baseEntity instanceof DevAlarmBean) {

            DevAlarmBean devAlarmBean = (DevAlarmBean) baseEntity;
            if (devAlarmBean.getDevAlarmInfo() == null) {
                return;
            }
            List<DevAlarmInfo.ListBean> listBeanList = devAlarmBean.getDevAlarmInfo().getList();
            if (page == 1) {
                list.clear();
            }

            if (listBeanList == null || listBeanList.size() == 0) {
                if (page > 1) {
                    ToastUtil.showMessage(getString(R.string.no_more_data));
                    page--;
                    return;
                }
            } else {
                list.addAll(listBeanList);
            }
            leftInformationAdapter.notifyDataSetChanged();
            rightInformationAdapter.notifyDataSetChanged();
            if (rightInformationAdapter.getCount() == 0) {
                if (!emptyViewContainer.getChildAt(0).equals(emptyView)) {
                    emptyViewContainer.addView(emptyView, 0);
                    ViewGroup.LayoutParams params = emptyView.getLayoutParams();
                    params.height = pullToRefreshScrollView.getHeight();
                    params.width = pullToRefreshScrollView.getWidth();
                    emptyView.setLayoutParams(params);
                }
            } else {
                if (emptyViewContainer.getChildAt(0).equals(emptyView)) {
                    emptyViewContainer.removeView(emptyView);
                }
            }
            initScrollView();
        }

    }
}
