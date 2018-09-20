package com.huawei.solarsafe.view.maintaince.main;

import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.huawei.solarsafe.MyApplication;
import com.huawei.solarsafe.R;
import com.huawei.solarsafe.bean.BaseEntity;
import com.huawei.solarsafe.bean.station.StationStateInfo;
import com.huawei.solarsafe.bean.station.StationStateListInfo;
import com.huawei.solarsafe.presenter.maintaince.stationstate.StationStatePresenter;
import com.huawei.solarsafe.utils.LocalData;
import com.huawei.solarsafe.utils.SysUtils;
import com.huawei.solarsafe.utils.ToastUtil;
import com.huawei.solarsafe.utils.Utils;
import com.huawei.solarsafe.utils.customview.LoadingDialog;
import com.huawei.solarsafe.view.devicemanagement.DeviceManagementActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import toan.android.floatingactionmenu.FloatingActionButton;
import toan.android.floatingactionmenu.FloatingActionsMenu;
import toan.android.nineoldandroids.animation.ObjectAnimator;

public class StationStatusFragment extends Fragment implements View.OnClickListener, IStationStateView {
    private RecyclerView recyclerView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private DeviceAdapter adapter;
    public static final String TAG = "StationStatusFragment";
    private Button btnPk, canclePk;
    private FloatingActionsMenu floatingActionsMenu;
    private LinearLayout llSearch;
    private int mScrollPosition;
    private ObjectAnimator animator;
    private LinearLayout linearLayoutPk, screenLL;
    private StationStatePresenter presenter;
    private StationStateListInfo stationStateListInfo = new StationStateListInfo();
    private List<StationStateInfo> stationStateInfos = new ArrayList<>();
    private String stationName = "", stationStatu = "-1";
    private Button btnQuery, btnQueryCancel;
    private EditText edStationName;
    private int page = 1, pageSize = 10, pageCount = 0;
    private boolean isRefreshing = false;
    private int lastVisibleItem;
    public List<String> rightsList;
    private Spinner spinnerStationSta;
    private List<String> stationSta;

    public void freshData() {
        resetRefreshStatus();
        showLoading();
        requestData();
        screenLL.setVisibility(View.GONE);
    }

    public StationStatusFragment() {
    }

    public static StationStatusFragment newInstance() {
        StationStatusFragment fragment = new StationStatusFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new StationStatePresenter();
        presenter.onViewAttached(this);
        String strRights = LocalData.getInstance().getRightString();
        rightsList = Utils.stringToList(strRights);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_station_status, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.station_infos);
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.station_infos_swiperefresh);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                resetRefreshStatus();
                showLoading();
                requestData();
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
                //宋平修改 问题单 54654 当数据很少时会出现下拉刷新同时触发上拉加载
                if(dy > 0 && lastVisibleItem + 1 == adapter.getItemCount()){
                    page++;
                    if (page > pageCount && pageCount != 0) {
                        page--;
                        ToastUtil.showMessage(R.string.no_more_data);
                        return;
                    }
                    mSwipeRefreshLayout.setRefreshing(true);
                    showLoading();
                    requestData();
                }
                View topChild = recyclerView.getChildAt(0);
                LinearLayoutManager lm = (LinearLayoutManager) recyclerView.getLayoutManager();
                lastVisibleItem = lm.findLastVisibleItemPosition();
                int newScrollPosition = 0;
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
                        if (hideHeadViewListener != null) {
                            hideHeadViewListener.hideBackButton(true);
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
                        if (hideHeadViewListener != null) {
                            hideHeadViewListener.hideBackButton(false);
                        }
                        if (hideHeadViewListener != null) {
                            hideHeadViewListener.hideHeadView();
                        }
                    }
                }
                mScrollPosition = newScrollPosition;
            }
        });
        btnPk = (Button) view.findViewById(R.id.goto_zhipai);
        btnPk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectNum = 0;
                ArrayList<String> stationCodes = new ArrayList<String>();
                ArrayList<String> stationNames = new ArrayList<String>();
                if (stationStateInfos.size() == 0) {
                    ToastUtil.showMessage(getString(R.string.less_than_2_reselect));
                    return;
                }
                for (StationStateInfo stationInfo : stationStateInfos) {
                    if (stationInfo.isPkChecked) {
                        selectNum++;
                        stationCodes.add(stationInfo.getStationCode());
                        stationNames.add(stationInfo.getStationName());
                    }
                }
                if (selectNum < 2) {
                    ToastUtil.showMessage(getString(R.string.less_than_2_reselect));
                    return;
                } else if (selectNum > 3) {
                    ToastUtil.showMessage(getString(R.string.sup_2_to_3__only_reselect));
                    return;
                }
                Bundle bundle = new Bundle();
                bundle.putInt("selectNum", selectNum);
                bundle.putStringArrayList("stationCodes", stationCodes);
                bundle.putStringArrayList("stationNames", stationNames);
                SysUtils.startActivity(getActivity(), StationPKActivity.class, bundle);
            }
        });
        floatingActionsMenu = (FloatingActionsMenu) view.findViewById(R.id.multiple_actions);
        final FloatingActionButton buttonA = (FloatingActionButton) view.findViewById(R.id.action_a);
        final FloatingActionButton buttonB = (FloatingActionButton) view.findViewById(R.id.action_b);
        FloatingActionButton buttonC = (FloatingActionButton) view.findViewById(R.id.action_c);
        //选择PK
        buttonB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                floatingActionsMenu.collapse();
                floatingActionsMenu.setVisibility(View.GONE);
                if (stationStateInfos.size() != 0) {
                    for (StationStateInfo stationinfo : stationStateInfos) {
                        stationinfo.isShowPk = true;
                    }
                    adapter.notifyDataSetChanged();
                }
                btnPk.setVisibility(View.VISIBLE);
                if (hideHeadViewListener != null) {
                    hideHeadViewListener.hideHeadView();
                }
                linearLayoutPk.setVisibility(View.VISIBLE);
            }
        });
        //筛选
        buttonA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                floatingActionsMenu.collapse();
                if (hideHeadViewListener != null) {
                    hideHeadViewListener.hideHeadView();
                }
                screenLL.setVisibility(View.VISIBLE);
            }
        });
        //搜索
        buttonC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                floatingActionsMenu.collapse();
                llSearch.setVisibility(View.VISIBLE);
                if (hideHeadViewListener != null) {
                    hideHeadViewListener.hideHeadView();
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
        linearLayoutPk = (LinearLayout) view.findViewById(R.id.ll_zhipai);
        canclePk = (Button) view.findViewById(R.id.goto_zhipai_cancel);
        canclePk.setOnClickListener(this);
        linearLayoutPk.setVisibility(View.GONE);

        btnQuery = (Button) view.findViewById(R.id.btn_search);
        btnQueryCancel = (Button) view.findViewById(R.id.btn_search_cancel);
        btnQuery.setOnClickListener(this);
        btnQueryCancel.setOnClickListener(this);
        edStationName = (EditText) view.findViewById(R.id.et_station_name);
        spinnerStationSta = (Spinner) view.findViewById(R.id.spinner_station_sta);
        stationSta = new ArrayList<>();
        stationSta.add(MyApplication.getContext().getString(R.string.all_of));
        stationSta.add(MyApplication.getContext().getString(R.string.onLine));
        stationSta.add(MyApplication.getContext().getString(R.string.breakdown));
        stationSta.add(MyApplication.getContext().getString(R.string.disconnect));
        ArrayAdapter<String> stationStaAdapter = new ArrayAdapter<String>(getContext(), R.layout.custom_spiner_text_item, stationSta);
        stationStaAdapter.setDropDownViewResource(R.layout.custom_spinner_dropdown_item);
        spinnerStationSta.setAdapter(stationStaAdapter);
        spinnerStationSta.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position == 0){
                    stationStatu = "-1";
                }else if(position == 1){
                    stationStatu = "3";
                }else if(position == 2){
                    stationStatu = "2";
                }else if(position == 3){
                    stationStatu = "1";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        screenLL = (LinearLayout) view.findViewById(R.id.ll_screen);
        screenLL.setVisibility(View.GONE);
        requestData();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
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
    }

    private void resetViewInit() {

        if(page ==1){
            if (stationStateInfos.size() != 0) {
                for (StationStateInfo stationinfo : stationStateInfos) {
                    stationinfo.isShowPk = false;
                    stationinfo.isPkChecked = false;
                }
            }
            floatingActionsMenu.setVisibility(View.VISIBLE);
            btnPk.setVisibility(View.GONE);
            if (floatingActionsMenu.isExpanded()) {
                floatingActionsMenu.collapse();
            }
            linearLayoutPk.setVisibility(View.GONE);
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.goto_zhipai_cancel:
                showLoading();
                resetRefreshStatus();
                requestData();
                break;
            case R.id.cancel_search:
                llSearch.setVisibility(View.GONE);
                break;
            case R.id.btn_search_cancel:
                screenLL.setVisibility(View.GONE);
                break;
            case R.id.btn_search:
                stationName = edStationName.getText().toString().trim();
                floatingActionsMenu.setVisibility(View.VISIBLE);
                linearLayoutPk.setVisibility(View.GONE);
                resetRefreshStatus();
                showLoading();
                requestData();
                break;

        }
    }

    @Override
    public void requestData() {
        resetViewInit();
        HashMap<String, String> params3 = new HashMap<>();
        //【安全特性编号】I1 OR_SmartPVMS60_PVMS830_0004_F01 部分敏感信息APP不做长期存储 【修改人】zhaoyufeng
        params3.put("stationName", stationName);
        if(!"-1".equals(stationStatu)){
            params3.put("status", stationStatu);
        }else {
            params3.put("sort","asc");
            params3.put("orderBy","stationStatus");
        }
        params3.put("stationAddr", "");
        params3.put("pageSize", pageSize + "");
        params3.put("page", page + "");
        presenter.doRequestStationStateList(params3);
    }

    @Override
    public void getData(BaseEntity baseEntity) {
        if (mSwipeRefreshLayout != null && mSwipeRefreshLayout.isRefreshing()) {
            mSwipeRefreshLayout.setRefreshing(false);
        }
        if (baseEntity == null) {
            if (isAdded()) {
                dismissLoading();
            }
            return;
        }
        if (baseEntity instanceof StationStateListInfo) {
            if (isRefreshing) {
                stationStateInfos.clear();
            }
            isRefreshing = false;
            stationStateListInfo = (StationStateListInfo) baseEntity;
            if (page > pageCount && pageCount != 0) {
                if (isAdded()) {
                    dismissLoading();
                }
                return;
            }
            if (pageCount == 0) {
                pageCount = stationStateListInfo.getTotal() / pageSize + 1;
            }
            if (stationStateInfos == null) {
                stationStateInfos = new ArrayList<>();
            }
            if (stationStateListInfo.getStationStateInfos() != null) {
                if(page>1 && stationStateInfos.size()>0){
                    if(stationStateInfos.get(0).isShowPk){
                        for(StationStateInfo stateInfo:stationStateListInfo.getStationStateInfos()){
                            stateInfo.setIsShowPk(true);
                        }
                    }
                }
                stationStateInfos.addAll(stationStateListInfo.getStationStateInfos());
            }
                if (adapter == null) {
                    adapter = new DeviceAdapter();
                    recyclerView.setAdapter(adapter);
                }
                adapter.notifyDataSetChanged();
            if (isAdded()) {
                dismissLoading();
            }
        }
    }


    class RoundDrawable extends Drawable {
        private Paint mPaint;

        public RoundDrawable() {
            mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
            mPaint.setColor(getResources().getColor(R.color.gray));
            mPaint.setStyle(Paint.Style.FILL);
        }

        @Override
        public void draw(@NonNull Canvas canvas) {
            Rect rect = getBounds();
            canvas.drawCircle(rect.width() / 2f, rect.height() / 2f, rect.height() / 2f, mPaint);
        }

        public void setColor(int color) {
            mPaint.setColor(color);
            invalidateSelf();
        }

        @Override
        public void setAlpha(@IntRange(from = 0, to = 255) int alpha) {

        }

        @Override
        public void setColorFilter(@Nullable ColorFilter colorFilter) {

        }

        @Override
        public int getOpacity() {
            return PixelFormat.OPAQUE;
        }
    }


    private class DeviceAdapter extends RecyclerView.Adapter<DeviceAdapter.StationHolder> {
        @Override
        public StationHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(StationStatusFragment.this.getActivity()).inflate(R.layout.station_status_item, parent, false);
            StationHolder holder = new StationHolder(view);
            return holder;
        }

        @Override
        public void onBindViewHolder(final StationHolder viewHolder, final int position) {
            final StationStateInfo stationStateInfo = stationStateInfos.get(position);
            if (stationStateInfo != null) {
                viewHolder.name.setText(stationStateInfo.getStationName());
                viewHolder.deviceWarn.setText(stationStateInfo.getDevAlarm() + "");
                viewHolder.installCapacity.setText(getString(R.string.capatity_no_colon) + "(" + Utils.handlePowerUnitNew(stationStateInfo.getCapacity()) + ")");
                viewHolder.nowElec.setText(Utils.round(stationStateInfo.getCurGeneration(), 2));
                viewHolder.perPower.setText(Utils.round(stationStateInfo.getPerMWPower(), 3));
                viewHolder.power.setText(Utils.round(stationStateInfo.getRealTimePower(), 3));
                viewHolder.validHours.setText(getString(R.string.equivalent_utilization_hours) + "(" + Utils.round(stationStateInfo.getEquHours(), 3) + "h)");
                viewHolder.warning.setText(stationStateInfo.getIntelligentAlarm() + "");

                Drawable drawable = viewHolder.viewStatus.getBackground();
                if (drawable == null)
                    drawable = new RoundDrawable();

                RoundDrawable roundDrawable = (RoundDrawable) drawable;
                viewHolder.viewStatus.setBackground(roundDrawable);
                if ("3".equals(stationStateInfo.getStationStatus())) {
                    roundDrawable.setColor(getResources().getColor(R.color.springgreen));
                } else if ("1".equals(stationStateInfo.getStationStatus())) {
                    roundDrawable.setColor(getResources().getColor(R.color.gray));
                } else if ("2".equals(stationStateInfo.getStationStatus())) {
                    roundDrawable.setColor(getResources().getColor(R.color.red));
                } else {
                    roundDrawable.setColor(getResources().getColor(R.color.white));
                }
                if (stationStateInfo.isShowPk) {
                    viewHolder.pkCheckBox.setVisibility(View.VISIBLE);
                    viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                        }
                    });
                } else {
                    viewHolder.pkCheckBox.setVisibility(View.GONE);
                    viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //根据权限列表，显示有权限的模块
                            if (rightsList.contains("app_deviceManagement")) {
                                Intent intent = new Intent(getActivity(), DeviceManagementActivity.class);
                                intent.putExtra("stationIds", stationStateInfo.getStationCode());
                                intent.putExtra("stationName", stationStateInfo.getStationName());
                                intent.putExtra("showBack",true);
                                startActivity(intent);
                            }
                        }
                    });
                }
                viewHolder.pkCheckBox.setChecked(stationStateInfo.isPkChecked());
                final StationHolder finalViewHolder = viewHolder;
                viewHolder.pkCheckBox.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (finalViewHolder.pkCheckBox.isChecked()) {
                            stationStateInfos.get(position).setIsPkChecked(true);
                        } else {
                            stationStateInfos.get(position).setIsPkChecked(false);
                        }
                    }
                });
            }
        }

        @Override
        public int getItemCount() {
            return stationStateInfos == null ? 0 : stationStateInfos.size();
        }

        class StationHolder extends RecyclerView.ViewHolder {
            private TextView warning, deviceWarn, power, nowElec, validHours, perPower, installCapacity, name;
            private CheckBox pkCheckBox;
            private View viewStatus;
            private LinearLayout llOnline;
            private LinearLayout llDeviceWarn;

            public StationHolder(View itemView) {
                super(itemView);
                warning = (TextView) itemView.findViewById(R.id.warning_num);
                deviceWarn = (TextView) itemView.findViewById(R.id.device_num);
                nowElec = (TextView) itemView.findViewById(R.id.now_elec);
                validHours = (TextView) itemView.findViewById(R.id.valid_hours);
                perPower = (TextView) itemView.findViewById(R.id.per_power);
                installCapacity = (TextView) itemView.findViewById(R.id.install_capacity);
                name = (TextView) itemView.findViewById(R.id.station_name);
                power = (TextView) itemView.findViewById(R.id.now_power);
                pkCheckBox = (CheckBox) itemView.findViewById(R.id.pk_checkbox);
                viewStatus = itemView.findViewById(R.id.station_status_icon);
                llOnline = (LinearLayout) itemView.findViewById(R.id.ll_online_diagnosis_pieces);
                llDeviceWarn = (LinearLayout) itemView.findViewById(R.id.ll_euqipment_alarm_pieces);

                if (rightsList.contains("app_equipmentAlarm")) {
                    llDeviceWarn.setVisibility(View.VISIBLE);
                }else {
                    llDeviceWarn.setVisibility(View.GONE);
                }
                if (rightsList.contains("app_diagnosisWarning")) {
                    llOnline.setVisibility(View.VISIBLE);
                }else {
                    llOnline.setVisibility(View.GONE);
                }
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

    private OnHideHeadViewListener hideHeadViewListener;


    public void setHideHeadViewListener(OnHideHeadViewListener hideHeadViewListener) {
        this.hideHeadViewListener = hideHeadViewListener;
    }

    public interface OnHideHeadViewListener {
        void hideHeadView();

        void hideBackButton(boolean isHide);
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
}
