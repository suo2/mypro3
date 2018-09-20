package com.huawei.solarsafe.view.maintaince.main;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.huawei.solarsafe.MyApplication;
import com.huawei.solarsafe.R;
import com.huawei.solarsafe.bean.BaseEntity;
import com.huawei.solarsafe.bean.FillterMsg;
import com.huawei.solarsafe.bean.ResultInfo;
import com.huawei.solarsafe.bean.alarm.RealTimeAlarmListInfo;
import com.huawei.solarsafe.bean.alarm.StationSourceBean;
import com.huawei.solarsafe.bean.device.DevBean;
import com.huawei.solarsafe.bean.device.DevHistorySignalData;
import com.huawei.solarsafe.bean.device.DevTypeConstant;
import com.huawei.solarsafe.bean.device.DevTypeListInfo;
import com.huawei.solarsafe.bean.device.SignalData;
import com.huawei.solarsafe.presenter.devicemanagement.DevManagementPresenter;
import com.huawei.solarsafe.presenter.maintaince.alarm.RealTimeAlarmPresenter;
import com.huawei.solarsafe.utils.ButtonUtils;
import com.huawei.solarsafe.utils.LocalData;
import com.huawei.solarsafe.utils.SysUtils;
import com.huawei.solarsafe.utils.ToastUtil;
import com.huawei.solarsafe.utils.Utils;
import com.huawei.solarsafe.utils.customview.DialogUtil;
import com.huawei.solarsafe.utils.customview.LoadingDialog;
import com.huawei.solarsafe.view.devicemanagement.IDevManagementView;
import com.huawei.solarsafe.view.maintaince.defects.NewDefectActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import toan.android.floatingactionmenu.FloatingActionButton;
import toan.android.floatingactionmenu.FloatingActionsMenu;
import toan.android.nineoldandroids.animation.ObjectAnimator;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link RealTimeAlarmFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RealTimeAlarmFragment extends Fragment implements View.OnClickListener, IRealTimeAlarmView, IDevManagementView {
    public static final String TAG = "RealTimeAlarmFragment";
    private RecyclerView recyclerView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private DeviceAdapter adapter;
    private LinearLayout llSearch;
    private Button btnzhiPai, cancleSearch, cancelzhiPai, confir, clear;
    private FloatingActionsMenu floatingActionsMenu;
    private int mScrollPosition;
    private ObjectAnimator animator;
    private ObjectAnimator animator1;
    private LinearLayout linearLayoutZhiPai;
    private RealTimeAlarmPresenter realTimeAlarmPresenter;
    private int page = 1;
    private int pageSize = 50;
    private int pageCount = 0;
    private int lastVisibleItem;
    private boolean isRefreshing = false;
    private RealTimeAlarmListInfo realTimeAlarmListInfo;
    List<RealTimeAlarmListInfo.ListBean> list = new ArrayList<>();
    private FillterMsg fillterMsg;
    private DevManagementPresenter devManagementPresenter;
    private List<DevTypeListInfo.DevType> devTypes;
    private Intent intent;
    private ImageView floatingRealQcsx;
    public static final int REQUEST_CODE = 10001;
    private String locationTimeZone;
    private StringBuffer sbComfirId;
    private StringBuffer sbClearId;
    //用于去查询是否是710电站的
    private String stationCode;
    private RealTimeAlarmListInfo.ListBean listBeanZhiPai = new RealTimeAlarmListInfo.ListBean();
    private ArrayList<String> alarmIds;
    private  Map<Integer, String> devTypeMap;

    public void freshData() {
        resetRefreshStatus();
        resetViewInit();
        showLoading();
        requestData();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    public void fillterListData(FillterMsg fillterMsg) {
        resetRefreshStatus();
        this.fillterMsg = fillterMsg;
        HashMap<String, String> params = new HashMap<>();
        //【安全特性编号】I1 OR_SmartPVMS60_PVMS830_0004_F01 部分敏感信息APP不做长期存储 【修改人】zhaoyufeng
        if (!TextUtils.isEmpty(fillterMsg.getStationCodes())) {
            params.put("stationIds", fillterMsg.getStationCodes());
        }
        if (!TextUtils.isEmpty(fillterMsg.getAlarmName())) {
            params.put("alarmName", fillterMsg.getAlarmName());
        }
        if (!TextUtils.isEmpty(fillterMsg.getDevName())) {
            params.put("devName", fillterMsg.getDevName());
        }
//        这三个参数必须传，否则报423
        if(!TextUtils.isEmpty(fillterMsg.getDevType())){
            params.put("devTypeId", fillterMsg.getDevType());
        }
        if(!"0".equals(fillterMsg.getAlarmLevel())){
            params.put("severityId", fillterMsg.getAlarmLevel());
        }
        if(!"0".equals(fillterMsg.getAlarmStatus())){
            params.put("alarmState", fillterMsg.getAlarmStatus());
        }
        if (!"0".equals(fillterMsg.getStartTime())) {
            params.put("startTime", fillterMsg.getStartTime());
        }
        if (!"0".equals(fillterMsg.getEndTime())) {
            params.put("endTime", fillterMsg.getEndTime());
        }
        params.put("pageSize", pageSize + "");
        params.put("page", page + "");
        realTimeAlarmPresenter.doRequestListRealTimeAlarm(params);
    }

    public RealTimeAlarmFragment() {

    }


    public static RealTimeAlarmFragment newInstance() {
        RealTimeAlarmFragment fragment = new RealTimeAlarmFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        devTypeMap = DevTypeConstant.getDevTypeMap(MyApplication.getContext());
        realTimeAlarmPresenter = new RealTimeAlarmPresenter();
        realTimeAlarmPresenter.onViewAttached(this);
        devManagementPresenter = new DevManagementPresenter();
        devManagementPresenter.onViewAttached(this);
        devManagementPresenter.doRequestDevType(new HashMap<String, String>());
        if (Integer.valueOf(LocalData.getInstance().getTimezone()) > 0 || Integer.valueOf(LocalData.getInstance().getTimezone()) == 0) {
            locationTimeZone = "+" + LocalData.getInstance().getTimezone();
        } else {
            locationTimeZone = LocalData.getInstance().getTimezone();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_repair, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.device_infos);
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.device_infos_swiperefresh);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                resetRefreshStatus();
                showLoading();
                requestData();
                resetViewInit();
            }
        });
        recyclerView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return isRefreshing;
            }
        });
        adapter = new DeviceAdapter();
        recyclerView.setAdapter(adapter);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                View topChild = recyclerView.getChildAt(0);
                LinearLayoutManager lm = (LinearLayoutManager) recyclerView.getLayoutManager();
                int newScrollPosition = 0;
                //宋平修改 问题单 54654 当数据很少时会出现下拉刷新同时触发上拉加载
                if (dy > 0 && lastVisibleItem + 1 == adapter.getItemCount()) {
                    page++;
                    if (page > pageCount && pageCount != 0) {
                        page--;
                        if (list.size() == realTimeAlarmListInfo.getTotal()) {
                            ToastUtil.showMessage(R.string.no_more_data);
                        }
                        return;
                    }
                    mSwipeRefreshLayout.setRefreshing(true);
                    showLoading();
                    requestData();
                    resetViewInit();
                }
                lastVisibleItem = lm.findLastVisibleItemPosition();
                if (topChild == null) {
                    newScrollPosition = 0;
                } else {
                    newScrollPosition = -topChild.getTop() + lm.findFirstVisibleItemPosition() * topChild.getHeight();
                }
                if (Math.abs(newScrollPosition - mScrollPosition) > 10) {
                    floatingActionsMenu.collapse();
                    if (newScrollPosition < mScrollPosition) {
                        if (animator != null) {
                            if (!animator.isRunning()) {
                                animator = ObjectAnimator.ofFloat(floatingActionsMenu, "translationY", 0);
                                animator.setDuration(300);
                                animator.start();
                            }
                        } else {
                            animator = ObjectAnimator.ofFloat(floatingActionsMenu, "translationY", 0);
                            animator.setDuration(300);
                            animator.start();
                        }
                        if (animator1 != null) {
                            if (!animator1.isRunning()) {
                                animator1 = ObjectAnimator.ofFloat(floatingRealQcsx, "translationY", 0);
                                animator1.setDuration(300);
                                animator1.start();
                            }
                        } else {
                            animator1 = ObjectAnimator.ofFloat(floatingRealQcsx, "translationY", 0);
                            animator1.setDuration(300);
                            animator1.start();
                        }
                        if (hideHeadViewListener != null) {
                            hideHeadViewListener.hideButtonBackWarn(true);
                        }

                    } else if (newScrollPosition > mScrollPosition) {
                        if (animator != null) {
                            if (!animator.isRunning()) {
                                animator = ObjectAnimator.ofFloat(floatingActionsMenu, "translationY", floatingActionsMenu.getHeight() + Utils.dp2Px(getActivity(), 16));
                                animator.setDuration(300);
                                animator.start();
                            }
                        } else {
                            animator = ObjectAnimator.ofFloat(floatingActionsMenu, "translationY", floatingActionsMenu.getHeight() + Utils.dp2Px(getActivity(), 16));
                            animator.setDuration(300);
                            animator.start();
                        }
                        if (animator1 != null) {
                            if (!animator1.isRunning()) {
                                animator1 = ObjectAnimator.ofFloat(floatingRealQcsx, "translationY", floatingRealQcsx.getHeight() +
                                        Utils.dp2Px(getActivity(), 16));
                                animator1.setDuration(300);
                                animator1.start();
                            }
                        } else {
                            animator1 = ObjectAnimator.ofFloat(floatingRealQcsx, "translationY", floatingRealQcsx.getHeight() +
                                    Utils.dp2Px(getActivity(), 16));
                            animator1.setDuration(300);
                            animator1.start();
                        }
                        if (hideHeadViewListener != null) {
                            hideHeadViewListener.hideButtonBackWarn(false);
                        }
                        if (hideHeadViewListener != null) {
                            hideHeadViewListener.hideHeadViewWarn();
                        }
                    }
                }
                mScrollPosition = newScrollPosition;

            }
        });
        //wujing 转消缺
        btnzhiPai = (Button) view.findViewById(R.id.goto_zhipai);
        btnzhiPai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alarmIds = new ArrayList<>();
                int count1 = 0;
                int status = 0;
                if (list != null) {
                    for (RealTimeAlarmListInfo.ListBean dev : list) {
                        if (dev.isChecked()) {
                            stationCode = dev.getStationId();
                            count1++;
                            alarmIds.add(dev.getId() + "");
                            status = dev.getAlarmState();
                            listBeanZhiPai = dev;
                        }
                    }
                }
                if (count1 == 0) {
                    ToastUtil.showMessage(getString(R.string.select_operate_data));
                    return;
                }
                if (count1 > 1) {
                    ToastUtil.showMessage(getString(R.string.deselect_extra_data));
                    return;
                }
                if (status == 1 || status == 2) {
                    toRequestStationSource(stationCode, "zhipai");
                } else {
                    ToastUtil.showMessage(getString(R.string.transform_to_defect_only));
                    return;
                }
            }
        });
        floatingActionsMenu = (FloatingActionsMenu) view.findViewById(R.id.multiple_actions);
        final FloatingActionButton buttonA = (FloatingActionButton) view.findViewById(R.id.action_a);
        final FloatingActionButton buttonB = (FloatingActionButton) view.findViewById(R.id.action_b);
        FloatingActionButton buttonC = (FloatingActionButton) view.findViewById(R.id.action_c);
        floatingRealQcsx = (ImageView) view.findViewById(R.id.floating_real_qcsx);
        floatingRealQcsx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!ButtonUtils.isFastDoubleClick(R.id.floating_real_qcsx)) {
                    resetRefreshStatus();
                    fillterMsg = null;
                    Map<String, String> params = new HashMap<>();
                    params.put("pageSize", pageSize + "");
                    params.put("page", page + "");
                    realTimeAlarmPresenter.doRequestListRealTimeAlarm(params);
                }
            }
        });
        //选者指派
        buttonB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                floatingActionsMenu.collapse();
                floatingActionsMenu.setVisibility(View.GONE);
                floatingRealQcsx.setVisibility(View.GONE);
                initZPData(true);
                btnzhiPai.setVisibility(View.VISIBLE);
                if (hideHeadViewListener != null) {
                    hideHeadViewListener.hideHeadViewWarn();
                }
                linearLayoutZhiPai.setVisibility(View.VISIBLE);
            }
        });
        //筛选
        buttonA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                floatingActionsMenu.collapse();
                if (hideHeadViewListener != null) {
                    hideHeadViewListener.hideHeadViewWarn();
                }
                Intent intent = new Intent(getActivity(), CustomFillterActivity.class);
                intent.putExtra("TYPE", TAG);
                startActivity(intent);

            }
        });
        //搜索
        buttonC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                floatingActionsMenu.collapse();
                llSearch.setVisibility(View.VISIBLE);
                if (hideHeadViewListener != null) {
                    hideHeadViewListener.hideHeadViewWarn();
                }
            }
        });
        floatingActionsMenu.getChildAt(floatingActionsMenu.getChildCount()-1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(floatingActionsMenu.isExpanded()){
                    floatingActionsMenu.collapse();
                    buttonA.setVisibility(View.GONE);
                    buttonB.setVisibility(View.GONE);
                }else{
                    buttonA.setVisibility(View.VISIBLE);
                    buttonB.setVisibility(View.VISIBLE);
                    floatingActionsMenu.expand();
                }
            }
        });
        cancleSearch = (Button) view.findViewById(R.id.cancel_search);
        cancleSearch.setOnClickListener(this);
        llSearch = (LinearLayout) view.findViewById(R.id.ll_search);
        llSearch.setVisibility(View.GONE);

        linearLayoutZhiPai = (LinearLayout) view.findViewById(R.id.ll_zhipai);
        cancelzhiPai = (Button) view.findViewById(R.id.goto_zhipai_cancel);
        cancelzhiPai.setOnClickListener(this);
        linearLayoutZhiPai.setVisibility(View.GONE);

        confir = (Button) view.findViewById(R.id.goto_comfir);
        clear = (Button) view.findViewById(R.id.goto_clear);
        confir.setOnClickListener(this);
        clear.setOnClickListener(this);
        requestData();
        return view;
    }


    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.goto_zhipai_cancel:
                resetViewInit();
                break;
            case R.id.cancel_search:
                llSearch.setVisibility(View.GONE);
                break;
            case R.id.goto_comfir:
                sbComfirId = new StringBuffer();
                StringBuffer sbComStationCode = new StringBuffer();
                int count = 0;
                boolean allISActive = false;
                for (RealTimeAlarmListInfo.ListBean listBean : list) {
                    if (listBean.isChecked()) {
                        count++;
                        if (listBean.getAlarmState() == 1) {
                            allISActive = true;
                        } else {
                            allISActive = false;
                        }
                        sbComfirId.append(listBean.getId() + ",");
                        sbComStationCode.append(listBean.getStationId() + ",");
                    }
                }
                if (count == 0) {
                    ToastUtil.showMessage(getString(R.string.select_operate_data));
                    return;
                }
                if (!allISActive) {
                    ToastUtil.showMessage(getString(R.string.re_operation));
                    resetViewInit();
                    return;
                }
                String substring = sbComStationCode.toString().substring(0, sbComStationCode.toString().length() - 1);
                toRequestStationSource(substring, "comfir");
                break;
            case R.id.goto_clear:
                sbClearId = new StringBuffer();
                StringBuffer sbClearStationCode = new StringBuffer();
                boolean allISConfirm = false;
                int count1 = 0;
                for (RealTimeAlarmListInfo.ListBean dev : list) {
                    if (dev.isChecked()) {
                        count1++;
                        if (dev.getAlarmState() == 5) {
                            allISConfirm = true;
                        } else {
                            allISConfirm = false;
                        }
                        sbClearId.append(dev.getId() + ",");
                        sbClearStationCode.append(dev.getStationId() + ",");
                    }
                }
                if (count1 == 0) {
                    ToastUtil.showMessage(getString(R.string.select_operate_data));
                    return;
                }
                if (allISConfirm) {
                    ToastUtil.showMessage(getString(R.string.no_repeat_reselected));
                    resetViewInit();
                    return;
                }
                String substring1 = sbClearStationCode.toString().substring(0, sbClearStationCode.toString().length() - 1);
                toRequestStationSource(substring1, "clear");
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        resetViewInit();
        if (animator != null) {
            if (!animator.isRunning()) {
                animator = ObjectAnimator.ofFloat(floatingActionsMenu, "translationY", 0);
                animator.setDuration(300);
                animator.start();
            }
        } else {
            animator = ObjectAnimator.ofFloat(floatingActionsMenu, "translationY", 0);
            animator.setDuration(300);
            animator.start();
        }
        if (animator1 != null) {
            if (!animator1.isRunning()) {
                animator1 = ObjectAnimator.ofFloat(floatingRealQcsx, "translationY", 0);
                animator1.setDuration(300);
                animator1.start();
            }
        } else {
            animator1 = ObjectAnimator.ofFloat(floatingRealQcsx, "translationY", 0);
            animator1.setDuration(300);
            animator1.start();
        }
    }

    public void setDatas(List<RealTimeAlarmListInfo.ListBean> deviceInfos) {
        if (deviceInfos != null && adapter != null) {
            list = deviceInfos;
            adapter.notifyDataSetChanged();
        }
    }

    private void resetViewInit() {
        initZPData(false);
        btnzhiPai.setVisibility(View.GONE);
        floatingActionsMenu.setVisibility(View.VISIBLE);
        floatingRealQcsx.setVisibility(View.VISIBLE);
        if (floatingActionsMenu.isExpanded()) {
            floatingActionsMenu.collapse();
        }
        linearLayoutZhiPai.setVisibility(View.GONE);
    }

    @Override
    public void requestData() {
        if (fillterMsg != null) {
            HashMap<String, String> params = new HashMap<>();
            if (!TextUtils.isEmpty(fillterMsg.getStationCodes())) {
                params.put("stationIds", fillterMsg.getStationCodes());
            }
            if (!TextUtils.isEmpty(fillterMsg.getAlarmName())) {
                params.put("alarmName", fillterMsg.getAlarmName());
            }
            if (!TextUtils.isEmpty(fillterMsg.getDevName())) {
                params.put("devName", fillterMsg.getDevName());
            }
//        这三个参数必须传，否则报423
            if(!TextUtils.isEmpty(fillterMsg.getDevType())){
                params.put("devTypeId", fillterMsg.getDevType());
            }
            if(!"0".equals(fillterMsg.getAlarmLevel())){
                params.put("severityId", fillterMsg.getAlarmLevel());
            }
            if(!"0".equals(fillterMsg.getAlarmStatus())){
                params.put("alarmState", fillterMsg.getAlarmStatus());
            }
            if (!"0".equals(fillterMsg.getStartTime())) {
                params.put("startTime", fillterMsg.getStartTime());
            }
            if (!"0".equals(fillterMsg.getEndTime())) {
                params.put("endTime", fillterMsg.getEndTime());
            }
            params.put("pageSize", pageSize + "");
            params.put("page", page + "");
            realTimeAlarmPresenter.doRequestListRealTimeAlarm(params);
        } else {
            HashMap<String, String> params = new HashMap<>();
            //【安全特性编号】I1 OR_SmartPVMS60_PVMS830_0004_F01 部分敏感信息APP不做长期存储 【修改人】zhaoyufeng
            params.put("pageSize", pageSize + "");
            params.put("page", page + "");
            realTimeAlarmPresenter.doRequestListRealTimeAlarm(params);
        }
    }

    @Override
    public void getData(BaseEntity baseEntity) {
        if (isAdded()) {
            dismissLoading();
        }
        if (mSwipeRefreshLayout != null && mSwipeRefreshLayout.isRefreshing()) {
            mSwipeRefreshLayout.setRefreshing(false);
        }
        if (baseEntity == null) {
            return;
        }
        if (baseEntity instanceof RealTimeAlarmListInfo) {
            if (isRefreshing) {
                list.clear();
            }
            isRefreshing = false;
            realTimeAlarmListInfo = (RealTimeAlarmListInfo) baseEntity;
            if (page > pageCount && pageCount != 0) {
                return;
            }
            if (pageCount == 0) {
                pageCount = realTimeAlarmListInfo.getTotal() / pageSize + 1;
            }
            if (list == null) {
                list = new ArrayList<>();
            }
            if (realTimeAlarmListInfo.getList() != null) {
                list.addAll(realTimeAlarmListInfo.getList());
            }
            adapter.notifyDataSetChanged();
        } else if (baseEntity instanceof DevTypeListInfo) {
            DevTypeListInfo devTypeListInfo = (DevTypeListInfo) baseEntity;
            if (devTypeListInfo.getDevTypes() != null) {
                devTypes = devTypeListInfo.getDevTypes();
            }
        } else if (baseEntity instanceof ResultInfo) {
            ResultInfo res = (ResultInfo) baseEntity;
            if (res.isSuccess()) {
                ToastUtil.showMessage(getString(R.string.operate_data_succeed));
                resetRefreshStatus();
                resetViewInit();
                showLoading();
                requestData();
            } else {
                ToastUtil.showMessage(getString(R.string.operate_data_failed) + res.getRetMsg());
                resetViewInit();
            }
        } else if (baseEntity instanceof StationSourceBean) {
            StationSourceBean stationSouce = (StationSourceBean) baseEntity;
            if (stationSouce.isUserStation()) {
                if ("comfir".equals(stationSouce.getOprtion())) {
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(getActivity(), R.style.MyDialogTheme);
                    builder1.setTitle(getString(R.string.confirm_and_submit));
                    final String finalIds = sbComfirId.toString().substring(0, sbComfirId.toString().length() - 1);
                    builder1.setPositiveButton(getString(R.string.determine), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Map<String, String> params = new HashMap<>();
                            params.put("ids", finalIds);
                            params.put("handleType", "confirmAlarm");
                            realTimeAlarmPresenter.doRequestRealTimeAlarmHandle(params);
                        }
                    });
                    builder1.setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    builder1.show();
                } else if ("clear".equals(stationSouce.getOprtion())) {
                    AlertDialog.Builder builder2 = new AlertDialog.Builder(getActivity(), R.style.MyDialogTheme);
                    builder2.setTitle(getString(R.string.sure_clear_all));
                    final String finalIds1 = sbClearId.toString().substring(0, sbClearId.toString().length() - 1);
                    builder2.setPositiveButton(getString(R.string.determine), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Map<String, String> params1 = new HashMap<>();
                            params1.put("ids", finalIds1);
                            params1.put("handleType", "clearAlarm");
                            realTimeAlarmPresenter.doRequestRealTimeAlarmHandle(params1);
                        }
                    });
                    builder2.setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    builder2.show();
                } else if ("zhipai".equals(stationSouce.getOprtion())) {
                    Intent intent = new Intent(getActivity(), NewDefectActivity.class);
                    DevBean devBean = new DevBean();
                    devBean.setStationCode(listBeanZhiPai.getStationId());
                    devBean.setStationName(listBeanZhiPai.getStationName());
                    devBean.setDevTypeId(listBeanZhiPai.getDevTypeId() + "");
                    devBean.setDevName(listBeanZhiPai.getDevName());
                    devBean.setDevTypeName(listBeanZhiPai.getDevTypeName());
                    devBean.setDevId(listBeanZhiPai.getDevId() + "");
                    devBean.setDevVersion(listBeanZhiPai.getModelVersionName() + "");
                    intent.putStringArrayListExtra("alarmIds", alarmIds);
                    intent.putExtra("alarmTypeId", "realTimeAlarm");
                    intent.putExtra("devBean", devBean);
                    intent.putExtra("repairSuggestionStr", listBeanZhiPai.getRepairSuggestion());
                    startActivityForResult(intent, SysUtils.REQUEST_CODE);
                }
            } else {
                DialogUtil.showErrorMsgWithClick(getContext(), MyApplication.getContext().getString(R.string.station_source_title), getString(R.string.determine), true, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });
            }

        }
    }

    @Override
    public void getHistorySignalData(List<DevHistorySignalData> dataList) {

    }

    @Override
    public void getgetHistoryData(List<SignalData> dataList) {

    }

    @Override
    public void getOptHistoryData(List<List<SignalData>> lists) {

    }

    private class DeviceAdapter extends RecyclerView.Adapter<DeviceAdapter.StationHolder> {


        @Override
        public StationHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(RealTimeAlarmFragment.this.getActivity()).inflate(R.layout.new_device_alarm_item, parent, false);
            StationHolder holder = new StationHolder(view);
            return holder;
        }

        @Override
        public void onBindViewHolder(final StationHolder holder, final int position) {
            final RealTimeAlarmListInfo.ListBean deviceInfo = list.get(position);
            if (deviceInfo != null) {
                holder.singleAlarmIds = new ArrayList<>();
                holder.alarmName = deviceInfo.getAlarmName();
                // 2.告警对象
                int devTypeId = deviceInfo.getDevTypeId();
                holder.strDevType = devTypeMap.get(devTypeId);
                holder.strDevName = deviceInfo.getDevName();
                holder.tvAlarmTarget.setText(holder.strDevName);

                // 3.电站名称
                holder.tvAlarmStationName.setText(deviceInfo.getStationName());

                // 4.电站状态
                int statusId = deviceInfo.getAlarmState();
                if (statusId == 1) {
                    holder.strAlarmStatus = getString(R.string.activation);
                } else if (statusId == 2) {
                    holder.strAlarmStatus = getString(R.string.pvmodule_alarm_sured);
                } else if (statusId == 3) {
                    holder.strAlarmStatus = getString(R.string.in_hand);
                } else if (statusId == 4) {
                    holder.strAlarmStatus = getString(R.string.handled);
                } else if (statusId == 5) {
                    holder.strAlarmStatus = getString(R.string.cleared);
                } else if (statusId == 6) {
                    holder.strAlarmStatus = getString(R.string.restored);
                } else {
                    holder.strAlarmStatus = getResources().getString(R.string.invalid_value);
                }
                String timeZone = null;
                if (deviceInfo.getTimeZone() != null) {
                    if (Integer.valueOf(deviceInfo.getTimeZone()) > 0 || Integer.valueOf(deviceInfo.getTimeZone()) == 0) {
                        timeZone = "+" + deviceInfo.getTimeZone();
                    } else {
                        timeZone = deviceInfo.getTimeZone();
                    }
                } else {
                    timeZone = locationTimeZone;
                }
                holder.tvAlarmStatus.setText(holder.strAlarmStatus);
                //本地时间
                holder.tvLocationTime.setText(Utils.getFormatTimeYYMMDDHHmmss2(Utils.summerTime(deviceInfo.getHappenTime()), locationTimeZone));
                // 5.告警产生时间
                holder.tvAlarmCreateTime.setText(Utils.getFormatTimeYYMMDDHHmmss2(deviceInfo.getHappenTime() + deviceInfo.getDtsSaving(), timeZone));
                //恢复时间
                if (deviceInfo.getRecoveredTime() == 0) {
                    holder.tvRecoveredTime.setText("--");
                } else {
                    holder.tvRecoveredTime.setText(Utils.getFormatTimeYYMMDDHHmmss2(deviceInfo.getRecoveredTime() + deviceInfo.getDtsSaving(), timeZone));
                }
                // 6.告警级别
                int levId = deviceInfo.getSeverityId();
                if (levId == 1) {
                    // 1.告警名称+告警级别
                    holder.tvAlarmName.setText(deviceInfo.getAlarmName() + getString(R.string.device_alarm_serious));
                    holder.ivAlarmLevel.setImageResource(R.drawable.shape_alarm_level_red);
                    holder.tvAlarmStatus.setTextColor(getContext().getResources().getColor(R.color.device_alarm_item_major_my));
                    holder.strAlarmLevel = getString(R.string.serious);
                    holder.tvAlarmName.setTextColor(getContext().getResources().getColor(R.color.device_alarm_item_major_my));
                } else if (levId == 2) {
                    // 1.告警名称+告警级别
                    holder.tvAlarmName.setText(deviceInfo.getAlarmName() + getString(R.string.device_alarm_important));
                    holder.ivAlarmLevel.setImageResource(R.drawable.shape_alarm_level_yellow);
                    holder.tvAlarmStatus.setTextColor(getContext().getResources().getColor(R.color.device_alarm_item_major_im));
                    holder.strAlarmLevel = getString(R.string.important);
                    holder.tvAlarmName.setTextColor(getContext().getResources().getColor(R.color.device_alarm_item_major_im));
                } else if (levId == 3) {
                    // 1.告警名称+告警级别
                    holder.tvAlarmName.setText(deviceInfo.getAlarmName() + getString(R.string.device_alarm_subordinate));
                    holder.ivAlarmLevel.setImageResource(R.drawable.shape_alarm_level_blue);
                    holder.tvAlarmStatus.setTextColor(getContext().getResources().getColor(R.color.device_alarm_item_major_sub));
                    holder.strAlarmLevel = getString(R.string.subordinate);
                    holder.tvAlarmName.setTextColor(getContext().getResources().getColor(R.color.device_alarm_item_major_sub));
                } else if (levId == 4) {
                    // 1.告警名称+告警级别
                    holder.tvAlarmName.setText(deviceInfo.getAlarmName() + getString(R.string.device_alarm_sug));
                    holder.ivAlarmLevel.setImageResource(R.drawable.shape_alarm_level_gray);
                    holder.tvAlarmStatus.setTextColor(getContext().getResources().getColor(R.color.device_alarm_item_major_sug));
                    holder.strAlarmLevel = getString(R.string.suggestive);
                    holder.tvAlarmName.setTextColor(getContext().getResources().getColor(R.color.device_alarm_item_major_sug));
                } else {
                    holder.ivAlarmLevel.setImageResource(R.drawable.shape_alarm_level_gray);
                    holder.tvAlarmStatus.setTextColor(getContext().getResources().getColor(R.color.gray));
                    holder.strAlarmLevel = getString(R.string.suggestive);
                }

                // 7.设备型号
                holder.strDevModel = deviceInfo.getModelVersionName();

                if (deviceInfo.isShowCheck()) {
                    holder.zpCheckBox.setVisibility(View.VISIBLE);
                    //item 点击事件
                    holder.itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //Toast.makeText(getActivity(), "告警详情", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    holder.zpCheckBox.setVisibility(View.GONE);
                    holder.itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            intent = new Intent(getActivity(), RealTimeAlarmDetailsActivity.class);
                            intent.putExtra("tag", "real_time_alarm_fragment");
                            //告警名称
                            intent.putExtra("alarm_name", holder.alarmName);
                            //告警对象
                            intent.putExtra("alarm_target", holder.tvAlarmTarget.getText().toString());
                            //电站名称
                            intent.putExtra("alarm_station_name", holder.tvAlarmStationName.getText().toString());
                            //告警级别
                            intent.putExtra("alarm_level", holder.strAlarmLevel);
                            //告警状态
                            intent.putExtra("alarm_status", holder.strAlarmStatus);
                            //设备名称
                            intent.putExtra("alarm_device_name", holder.strDevName);
                            //设备类型
                            intent.putExtra("alarm_device_type", holder.strDevType);
                            //本地时间
                            intent.putExtra("tv_location_time", holder.tvLocationTime.getText().toString());
                            //产生时间
                            intent.putExtra("alarm_occur_time", holder.tvAlarmCreateTime.getText().toString());
                            //恢复时间
                            intent.putExtra("tv_recovered_time", holder.tvRecoveredTime.getText().toString());
                            //确认告警id
                            intent.putExtra("alarm_id", holder.alarmId);
                            //设备型号
                            intent.putExtra("alarm_dev_model", holder.strDevModel);

                            //电站代码getStationCode
                            intent.putExtra("alarm_station_code", holder.strStationCode);
                            //getDevTypeId
                            intent.putExtra("alarm_dev_type_id", holder.devTypeId);
                            //getDevId
                            intent.putExtra("alarm_dev_id", holder.devId);
                            //getModelVersionCode
                            intent.putExtra("alarm_model_version_code", holder.strDevModel);
                            //alarmTypeId
                            intent.putExtra("alarm_type_id", "realTimeAlarm");
                            //alarmIds
                            intent.putStringArrayListExtra("alarm_ids", holder.singleAlarmIds);
                            intent.putExtra("repairSuggestionStr", holder.repairSuggestion);
                            intent.putExtra("devId", deviceInfo.getDevId());
                            intent.putExtra("devTypeId", deviceInfo.getDevTypeId());
                            intent.putExtra("deviceName", deviceInfo.getDevName());
                            intent.putExtra("devEsn", deviceInfo.getEsnCode());
                            intent.putExtra("isMainCascaded", deviceInfo.isMainCascaded());
                            intent.putExtra("invType", deviceInfo.getInvType());
                            //跳转到告警详情界面
                            startActivityForResult(intent, REQUEST_CODE);
                        }
                    });
                }
                holder.zpCheckBox.setChecked(list.get(position).isChecked());
                holder.zpCheckBox.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (holder.zpCheckBox.isChecked()) {
                            list.get(position).setIsChecked(true);
                        } else {
                            list.get(position).setIsChecked(false);
                        }
                    }
                });

                //根据告警id,请求修复建议
                holder.alarmId = deviceInfo.getId();
                //电站代码
                holder.strStationCode = deviceInfo.getStationId();
                //设备类型ID
                holder.devTypeId = deviceInfo.getDevTypeId();
                //设备ID
                holder.devId = deviceInfo.getDevId();
                //alarmIds
                holder.singleAlarmIds.add(deviceInfo.getId() + "");
                //修复意见
                holder.repairSuggestion = deviceInfo.getRepairSuggestion();

            }
        }

        @Override
        public int getItemCount() {
            return list == null ? 0 : list.size();
        }

        class StationHolder extends RecyclerView.ViewHolder {
            private TextView tvAlarmName;
            private TextView tvAlarmTarget;
            private TextView tvAlarmStationName;
            private TextView tvAlarmCreateTime;
            private TextView tvAlarmStatus;
            private ImageView ivAlarmLevel;
            private TextView tvLocationTime;
            private TextView tvRecoveredTime;
            private CheckBox zpCheckBox;
            private LinearLayout alarmContainer;
            private String strDevType;
            private String strDevName;
            private String strAlarmStatus;
            private String strAlarmLevel;
            private String alarmName;
            private String strDevModel;
            private String strStationCode;
            private String repairSuggestion;
            private int devTypeId;
            private long devId;
            private long alarmId;
            private ArrayList<String> singleAlarmIds;


            public StationHolder(View itemView) {
                super(itemView);
                tvAlarmName = (TextView) itemView.findViewById(R.id.alarm_name);
                tvAlarmTarget = (TextView) itemView.findViewById(R.id.alarm_target);
                tvAlarmStationName = (TextView) itemView.findViewById(R.id.alarm_station_name);
                tvAlarmCreateTime = (TextView) itemView.findViewById(R.id.alarm_happen_time);
                tvAlarmStatus = (TextView) itemView.findViewById(R.id.alarm_state);
                ivAlarmLevel = (ImageView) itemView.findViewById(R.id.iv_alarm_level);
                zpCheckBox = (CheckBox) itemView.findViewById(R.id.cb_alarm_details);
                tvRecoveredTime = (TextView) itemView.findViewById(R.id.tv_recovered_time);
                tvLocationTime = (TextView) itemView.findViewById(R.id.tv_location_time);
                //跳转告警详情界面
                alarmContainer = (LinearLayout) itemView.findViewById(R.id.ll_alarm_item_container);
                alarmContainer.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //传递除告警原因和修复建议之外的其余参数
                        intent = new Intent(getActivity(), RealTimeAlarmDetailsActivity.class);
                        intent.putExtra("tag", "real_time_alarm_fragment");
                        //告警名称
                        intent.putExtra("alarm_name", alarmName);
                        //告警对象
                        intent.putExtra("alarm_target", tvAlarmTarget.getText().toString());
                        //电站名称
                        intent.putExtra("alarm_station_name", tvAlarmStationName.getText().toString());
                        //告警级别
                        intent.putExtra("alarm_level", strAlarmLevel);
                        //告警状态
                        intent.putExtra("alarm_status", strAlarmStatus);
                        //设备名称
                        intent.putExtra("alarm_device_name", strDevName);
                        //设备类型
                        intent.putExtra("alarm_device_type", strDevType);
                        //本地时间
                        intent.putExtra("tv_location_time", tvLocationTime.getText().toString());
                        //恢复时间
                        intent.putExtra("tv_recovered_time", tvRecoveredTime.getText().toString());
                        //产生时间
                        intent.putExtra("alarm_occur_time", tvAlarmCreateTime.getText().toString());
                        //确认告警id
                        intent.putExtra("alarm_id", alarmId);
                        //设备型号
                        intent.putExtra("alarm_dev_model", strDevModel);

                        //电站代码getStationCode
                        intent.putExtra("alarm_station_code", strStationCode);
                        //getDevTypeId
                        intent.putExtra("alarm_dev_type_id", devTypeId);
                        //getDevId
                        intent.putExtra("alarm_dev_id", devId);
                        //getModelVersionCode
                        intent.putExtra("alarm_model_version_code", strDevModel);
                        //alarmTypeId
                        intent.putExtra("alarm_type_id", "realTimeAlarm");
                        //alarmIds
                        intent.putStringArrayListExtra("alarm_ids", singleAlarmIds);
                        intent.putExtra("repairSuggestionStr", repairSuggestion);
                        //跳转到告警详情界面
                        startActivityForResult(intent, REQUEST_CODE);
                    }
                });
            }
        }
    }

    /**
     * 重置刷新数据 状态
     */
    private void resetRefreshStatus() {
        page = 1;
        pageCount = 0;
        isRefreshing = true;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            freshData();
        }
        if (requestCode == SysUtils.REQUEST_CODE && resultCode == RESULT_OK) {
            showLoading();
            requestData();
        }
    }

    private OnHideHeadViewListenerWarn hideHeadViewListener;

    public void setHideHeadViewListener(OnHideHeadViewListenerWarn hideHeadViewListener) {
        this.hideHeadViewListener = hideHeadViewListener;
    }

    public interface OnHideHeadViewListenerWarn {
        void hideHeadViewWarn();

        void hideButtonBackWarn(boolean isHide);
    }

    private void initZPData(boolean isZP) {
        if (list != null) {
            for (RealTimeAlarmListInfo.ListBean deviceInfo : list) {
                deviceInfo.setIsChecked(false);
                deviceInfo.setIsShowCheck(isZP);
            }
            adapter.notifyDataSetChanged();
        }
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
        loadingDialog.dismiss();
    }

    public void dismissReflashLoading() {
        if (mSwipeRefreshLayout != null && mSwipeRefreshLayout.isRefreshing()) {
            mSwipeRefreshLayout.setRefreshing(false);
        }
    }

    /**
     * @param stationCode 请求所选电站是否是710电站
     */
    private void toRequestStationSource(String stationCode, String op) {
        showLoading();
        HashMap<String, String> params = new HashMap<>();
        params.put("stationCodes", stationCode);
        realTimeAlarmPresenter.doRequesetStationSource(params, op);
    }
}
