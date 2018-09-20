package com.huawei.solarsafe.view.maintaince.main;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputFilter;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;

import com.amap.api.maps.model.LatLng;
import com.huawei.solarsafe.MyApplication;
import com.huawei.solarsafe.R;
import com.huawei.solarsafe.bean.BaseEntity;
import com.huawei.solarsafe.bean.Constant;
import com.huawei.solarsafe.bean.GlobalConstants;
import com.huawei.solarsafe.bean.defect.TodoTaskBean;
import com.huawei.solarsafe.bean.defect.TodoTaskList;
import com.huawei.solarsafe.bean.station.kpi.StationList;
import com.huawei.solarsafe.bean.station.map.StationForMapInfo;
import com.huawei.solarsafe.bean.station.map.StationMapList;
import com.huawei.solarsafe.model.maintain.IProcState;
import com.huawei.solarsafe.presenter.homepage.StationListPresenter;
import com.huawei.solarsafe.presenter.maintaince.patrol.PatrolPresenter;
import com.huawei.solarsafe.utils.SearchHelper;
import com.huawei.solarsafe.utils.ToastUtil;
import com.huawei.solarsafe.utils.Utils;
import com.huawei.solarsafe.utils.customview.LoadingDialog;
import com.huawei.solarsafe.view.homepage.station.IStationListView;
import com.huawei.solarsafe.view.maintaince.MoblieActivity;
import com.huawei.solarsafe.view.maintaince.defects.DefectDetailActivity;
import com.huawei.solarsafe.view.maintaince.defects.NewDefectActivity;
import com.huawei.solarsafe.view.maintaince.patrol.PatrolTaskDetailActivity;
import com.huawei.solarsafe.view.maintaince.todotasks.ITodoTaskView;
import com.huawei.solarsafe.view.maintaince.todotasks.TodoTaskCompare;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import toan.android.floatingactionmenu.FloatingActionButton;
import toan.android.nineoldandroids.animation.ObjectAnimator;

/**
 * 运维-移动运维碎片
 */
public class PatrolFragment extends Fragment implements IStationListView, ITodoTaskView {

    private SearchView mSearchView;
    private RecyclerView patrolList;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private View foucsView;
    private PatrolAdapter adapter;
    private EditText searchTextView;
    //搜索文字
    private String searchCharacter;
    private List<TodoTaskBean> searchList = new ArrayList<>();
    private List<TodoTaskBean> orignalList = new ArrayList<>();
    private TodoTaskCompare patrolWorkCompare = new TodoTaskCompare();
    private SearchHelper searchHelper;
    private StationListPresenter stationListPresenter;
    private StationMapList stationMapList;
    private ListChangeReceiver receiver;
    private LatLng endPoint;

    private PatrolPresenter presenter;


    private FloatingActionButton fabPatrol;
    private int mScrollPosition;
    private ObjectAnimator animator;
    private int page = 1, pageSize = 20, pageCount = 0;
    private int lastVisibleItem;
    private boolean isRefreshing = true;

    //多人消缺需求新增
    //消缺任务行为类型
    public final static String LOOK="look";//查看
    public final static String OPERATION="operation";//操作
    public final static String EXECUTION="Execution";//执行
    private LocalBroadcastManager lbm;
    private View emptyView;
    private FrameLayout emptyViewFrame;
    public PatrolFragment() {

    }

    public static PatrolFragment newInstance() {
        PatrolFragment fragment = new PatrolFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_partol, container, false);
        fabPatrol = (FloatingActionButton) view.findViewById(R.id.bt_goto_partol);
        fabPatrol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), MoblieActivity.class));
            }
        });
        patrolList = (RecyclerView) view.findViewById(R.id.work_list);
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.work_list_swiperefresh);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                resetRefreshStatus();
                showLoading();
                requestData();
            }
        });
        emptyView = View.inflate(getActivity(), R.layout.empty_view, null);
        emptyViewFrame = (FrameLayout) view.findViewById(R.id.empty_view_frame);
        patrolList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if (newState == RecyclerView.SCROLL_STATE_DRAGGING) {
                    hideSoftInput();
                }
                LinearLayoutManager manager = (LinearLayoutManager) recyclerView.getLayoutManager();
                // 当不滚动时
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    //获取最后一个完全显示的ItemPosition
                    int lastVisibleItem = manager.findLastCompletelyVisibleItemPosition();
                    int totalItemCount = manager.getItemCount();

                    // 判断是否滚动到底部
                    if (lastVisibleItem == (totalItemCount - 1)) {
                        if (animator != null) {
                            if (!animator.isRunning()) {
                                animator = ObjectAnimator.ofFloat(fabPatrol, "translationY", 0);
                                animator.setDuration(300);
                                animator.start();
                            }
                        } else {
                            animator = ObjectAnimator.ofFloat(fabPatrol, "translationY", 0);
                            animator.setDuration(300);
                            animator.start();
                        }
                    }
                }
                if (newState == RecyclerView.SCROLL_STATE_IDLE
                        && lastVisibleItem + 1 == adapter.getItemCount()) {
                    page++;
                    if (page > pageCount && pageCount != 0) {
                        page--;
                        if (pageCount > 1) {
                            ToastUtil.showMessage(R.string.no_more_data);
                        }
                        return;
                    }
                    mSwipeRefreshLayout.setRefreshing(true);
                    showLoading();
                    requestData();
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                View topChild = recyclerView.getChildAt(0);
                LinearLayoutManager lm = (LinearLayoutManager) recyclerView.getLayoutManager();
                lastVisibleItem = lm.findLastVisibleItemPosition();
                int newScrollPosition ;
                if (topChild == null) {
                    newScrollPosition = 0;
                } else {
                    newScrollPosition = -topChild.getTop() + lm.findFirstVisibleItemPosition() * topChild.getHeight();
                }
                if (Math.abs(newScrollPosition - mScrollPosition) > 8) {
                    if (newScrollPosition < mScrollPosition) {
                        if (animator != null) {
                            if (!animator.isRunning()) {
                                animator = ObjectAnimator.ofFloat(fabPatrol, "translationY", 0);
                                animator.setDuration(300);
                                animator.start();
                            }
                        } else {
                            animator = ObjectAnimator.ofFloat(fabPatrol, "translationY", 0);
                            animator.setDuration(300);
                            animator.start();
                        }
                        if (hideHeadViewListener != null) {
                            hideHeadViewListener.hideButtonBackWarn(true);
                        }

                    } else if (newScrollPosition > mScrollPosition) {
                        if (animator != null) {
                            if (!animator.isRunning()) {
                                animator = ObjectAnimator.ofFloat(fabPatrol, "translationY", fabPatrol.getHeight() + Utils.dp2Px(getActivity(), 20));
                                animator.setDuration(300);
                                animator.start();
                            }
                        } else {
                            animator = ObjectAnimator.ofFloat(fabPatrol, "translationY", fabPatrol.getHeight() + Utils.dp2Px(getActivity(), 20));
                            animator.setDuration(300);
                            animator.start();
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
        adapter = new PatrolAdapter();
        patrolList.setAdapter(adapter);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        patrolList.setLayoutManager(manager);
        if (orignalList != null) {
            Collections.sort(orignalList, patrolWorkCompare);
            adapter.setDatas(orignalList);
        }
        foucsView = view.findViewById(R.id.tips);
        foucsView.requestFocus();
        mSearchView = (SearchView) view.findViewById(R.id.search_patrol);
        searchHelper = SearchHelper.getInstance();
        setupSearchView();
        requestData();
        return view;
    }

    private void setupSearchView() {
        if (mSearchView != null) {
            mSearchView.setIconifiedByDefault(false);
            mSearchView.setIconified(true);
            mSearchView.onActionViewExpanded();
            //获取搜索框的TextView进行设置
            int id = mSearchView.getContext().getResources().getIdentifier("android:id/search_src_text", null, null);
            searchTextView = (EditText) mSearchView.findViewById(id);

            searchTextView.setTextColor(getResources().getColor(R.color.textview_text_group_homepage_item_tvcolor));
            searchTextView.setTextSize(12);
            searchTextView.setFilters(new InputFilter[]{new InputFilter.LengthFilter(30)});
            searchTextView.setHintTextColor(Color.parseColor("#b2b2b2"));

            //通过反射获取SearchView的属性，进行设置
            try {
                Class<?> argClass = mSearchView.getClass();
                // 指定某个私有属性
                Field mSearchHintIconField = argClass.getDeclaredField("mSearchHintIcon");
                mSearchHintIconField.setAccessible(true);
                Object searchHintIconObj = mSearchHintIconField.get(mSearchView);
                if (searchHintIconObj instanceof BitmapDrawable) {
                    mSearchHintIconField.set(mSearchView,getResources().getDrawable(R.drawable.search_icon));
                } else {
                    ImageView mSearchHintIcon = (ImageView) searchHintIconObj;
                    // mSearchHintIcon.setBackgroundResource(R.drawable.icon_search);
                    mSearchHintIcon.setImageResource(R.drawable.search_icon);
                }
                // 注意mSearchPlate的背景是stateListDrawable(不同状态不同的图片)
                // 所以不能用BitmapDrawable
                Field ownField = argClass.getDeclaredField("mSearchPlate");
                // setAccessible 它是用来设置是否有权限访问反射类中的私有属性的，只有设置为true时才可以访问，默认为false
                ownField.setAccessible(true);
                LinearLayout mView = (LinearLayout) ownField.get(mSearchView);
                mView.setBackground(getResources().getDrawable(R.drawable.search_input_area_bordershape));
                Field mCloseButton = argClass.getDeclaredField("mCloseButton");
                mCloseButton.setAccessible(true);
                ImageView backView = (ImageView) mCloseButton.get(mSearchView);
                backView.setImageResource(R.drawable.icon_clear);
            } catch (Exception e) {
                // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0004_F01_Android_S02  敏感信息检查-日志中不能包含敏感信息    【修改人】：zhaoyufeng
                Log.e("Exception", e.toString());
            }
            mSearchView.setFocusable(false);
            mSearchView.clearFocus();
            mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    searchList.clear();
                    hideSoftInput();
                    if (TextUtils.isEmpty(searchCharacter)) {
                        Collections.sort(orignalList, patrolWorkCompare);
                        adapter.setDatas(orignalList);
                    } else {
                        for (TodoTaskBean bean : orignalList) {
                            String name = bean.getProcName();
                            if (name == null) {
                                name = getString(R.string.defect_task);
                            }
                            if (searchHelper.searchResult(name, searchCharacter)) {
                                searchList.add(bean);
                            }
                        }
                        Collections.sort(searchList, patrolWorkCompare);
                        adapter.setDatas(searchList);
                    }
                    return true;
                }

                @Override
                public boolean onQueryTextChange(String queryText) {
                    searchList.clear();
                    searchCharacter = queryText.trim();
                    if (TextUtils.isEmpty(searchCharacter)) {
                        Collections.sort(orignalList, patrolWorkCompare);
                        adapter.setDatas(orignalList);
                    } else {
                        for (TodoTaskBean bean : orignalList) {
                            String name = bean.getProcName();
                            if (name == null) {
                                name = getString(R.string.defect_task);
                            }
                            if (searchHelper.searchResult(name, searchCharacter)) {
                                searchList.add(bean);
                            }
                        }
                        Collections.sort(searchList, patrolWorkCompare);
                        adapter.setDatas(searchList);
                    }
                    return true;
                }
            });
        }
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new PatrolPresenter();
        presenter.onViewAttached(this);
        stationListPresenter = new StationListPresenter();
        stationListPresenter.onViewAttached(this);
        stationListPresenter.requestStationMapList(null);
        receiver = new ListChangeReceiver();
        lbm = LocalBroadcastManager.getInstance(MyApplication.getContext());
        IntentFilter filter = new IntentFilter();
        filter.addAction(Constant.ACTION_DEFECTS_UPDATE);
        filter.addAction(Constant.ACTION_PATROL_UPDATE);
        lbm.registerReceiver(receiver, filter);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!TextUtils.isEmpty(searchTextView.getText().toString())) {
            searchTextView.setText("");
        }
        mSearchView.clearFocus();
        Utils.closeSoftKeyboard(getActivity());
        if (animator != null) {
            if (!animator.isRunning()) {
                animator = ObjectAnimator.ofFloat(fabPatrol, "translationY", 0);
                animator.setDuration(300);
                animator.start();
            }
        } else {
            animator = ObjectAnimator.ofFloat(fabPatrol, "translationY", 0);
            animator.setDuration(300);
            animator.start();
        }
    }


    /**
     * 隐藏键盘
     */
    private void hideSoftInput() {
        if (mSearchView != null) {
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null) {
                imm.hideSoftInputFromWindow(mSearchView.getWindowToken(), 0);
            }
            mSearchView.clearFocus();
        }
    }


    @Override
    public void requestData() {
        Map<String, String> paramDefect = new HashMap<>();
        paramDefect.put("page", page + "");
        paramDefect.put("pageSize", pageSize + "");
        if (presenter != null) {
            presenter.doRequestProcess(paramDefect);
        }
    }

    @Override
    public void getData(BaseEntity data) {
        if (isAdded()) {
            dismissLoading();
        }
        if(mSwipeRefreshLayout!=null&&mSwipeRefreshLayout.isRefreshing()){
            mSwipeRefreshLayout.setRefreshing(false);
        }
        if (data == null) {
            return;
        }
        if (data instanceof TodoTaskList) {
            TodoTaskList defectTodoProcessList = (TodoTaskList) data;

            //每次获取数据清空原数据,避免列表数据为0时不刷新列表
            orignalList.clear();
            searchTextView.setText("");

            if (defectTodoProcessList.getList() != null) {
                if (isRefreshing) {
                    orignalList.clear();
                }
                isRefreshing = false;
                if (page > pageCount && pageCount != 0) {
                    return;
                }
                if (pageCount == 0) {
                    pageCount = defectTodoProcessList.getTotal() / pageSize + 1;
                }
                orignalList.addAll(defectTodoProcessList.getList());
            }
            Collections.sort(orignalList, patrolWorkCompare);
            adapter.setDatas(orignalList);
            if(adapter.getItemCount() == 0){
                if(emptyViewFrame.getChildCount() == 1){
                    emptyViewFrame.addView(emptyView);
                    ViewGroup.LayoutParams params = emptyView.getLayoutParams();
                    params.height =  ViewGroup.LayoutParams.MATCH_PARENT;
                    params.width = ViewGroup.LayoutParams.MATCH_PARENT;
                    emptyView.setLayoutParams(params);
                }
            }else{
                if(emptyViewFrame.getChildCount() == 2){
                    emptyViewFrame.removeView(emptyView);
                }
            }
        }
    }


    @Override
    public void back() {

    }

    @Override
    public void getStationListData(StationList stationInfos) {

    }

    @Override
    public void getStationMapListData(BaseEntity baseEntity) {
        if (baseEntity instanceof StationMapList) {
            stationMapList = (StationMapList) baseEntity;
        }
    }

    @Override
    public void jumpToMap() {

    }


    private class PatrolAdapter extends RecyclerView.Adapter<PatrolAdapter.TodoHolder> {
        private List<TodoTaskBean> datas;
        View.OnClickListener defectClickListener;

        public void setDatas(List<TodoTaskBean> temp) {
            if (datas == null) {
                datas = new ArrayList<>();
            }
            datas.clear();
            datas.addAll(temp);
            notifyDataSetChanged();
        }

        @Override
        public TodoHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.patrol_item, parent, false);
            return new TodoHolder(view);
        }

        @Override
        public void onBindViewHolder(TodoHolder holder, int position) {
            final TodoTaskBean info = datas.get(position);
            if (info != null) {
                if (IProcState.DEFECT.equals(info.getProcKey())) {
                    holder.setTaskName(getString(R.string.defect_task));
                } else if (info.getProcName() != null) {
                    holder.setTaskName(info.getProcName());
                }
                holder.setTaskType(info.getProcKey());
                holder.setTvTaskDesc(info.getProcDesc());
                holder.setState(info.getProcState());
                holder.setTime(info.getStartTime());

                //多人消缺需求新增
                //操作和执行点击事件
                defectClickListener=new View.OnClickListener() {

                    Intent defectIntent=new Intent();

                    @Override
                    public void onClick(View view) {
                        int viewId=view.getId();

                        defectIntent.putExtra("ifJumpFromMessageBox", true);
                        defectIntent.putExtra("procId", String.valueOf(info.getProcId()));
                        defectIntent.putExtra("isModify", true);

                        if (IProcState.DEFECT_WRITE.equals(info.getProcState())) {
                            defectIntent.setClass(getActivity(), NewDefectActivity.class);
                        } else {
                            defectIntent.setClass(getActivity(), DefectDetailActivity.class);
                        }
                        String sid = info.getSId();
                        if (stationMapList != null) {
                            if (stationMapList.getStationMapLists() != null) {
                                for (StationForMapInfo stationForMapInfo : stationMapList.getStationMapLists()) {
                                    if (sid.equals(stationForMapInfo.getStationCode())) {
                                        endPoint = new LatLng(stationForMapInfo.getLatitude(), stationForMapInfo.getLongitude());
                                    }
                                }
                            }
                            defectIntent.putExtra(GlobalConstants.END_LOCATION, endPoint);
                        }

                        switch (viewId){
                            case R.id.btnOperation:
                                defectIntent.putExtra("defectType",OPERATION);
                                startActivity(defectIntent);
                                break;
                            case R.id.btnExecution:
                                defectIntent.putExtra("defectType",EXECUTION);
                                startActivity(defectIntent);
                                break;
                        }
                    }
                };

                holder.btnOperation.setOnClickListener(defectClickListener);
                holder.btnExecution.setOnClickListener(defectClickListener);

                //判断消缺单可进行哪些行为
                if (IProcState.DEFECT.equals(info.getProcKey())){
                    holder.llDefect.setVisibility(View.VISIBLE);

                    holder.getItemContainer().setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                        }
                    });

                    //执行
                    if (String.valueOf(GlobalConstants.userId).equals(info.getCurrentAssignee())){
                        holder.btnExecution.setVisibility(View.VISIBLE);
                    }else{
                        holder.btnExecution.setVisibility(View.GONE);
                    }

                    //操作
                    if (info.isOp()){
                        holder.btnOperation.setVisibility(View.VISIBLE);
                    }else {
                        holder.btnOperation.setVisibility(View.GONE);
                    }

                }else if (IProcState.INSPECT.equals(info.getProcKey())){
                    holder.llDefect.setVisibility(View.GONE);

                    holder.getItemContainer().setOnClickListener(new View.OnClickListener() {
                            Intent intent = new Intent();
                            @Override
                            public void onClick(View v) {
                                intent.putExtra("executor", info.getCurrentAssignee());
                                intent.putExtra("inspectId", info.getProcId());
                                intent.putExtra("procState", info.getProcState());
                                intent.putExtra("currentTaskId", info.getCurrentTaskId());
                                intent.setClass(getActivity(), PatrolTaskDetailActivity.class);
                                startActivity(intent);
                            }
                        });
                }
            }
        }

        @Override
        public int getItemCount() {
            return datas == null ? 0 : datas.size();
        }

        class TodoHolder extends RecyclerView.ViewHolder {
            private LinearLayout patrolItemContainer;
            private TextView tvTaskName;
            private TextView tvTaskType;
            private TextView tvTaskDesc;
            private TextView tvState;
            private TextView tvTime;
            private LinearLayout llDefect;
            private TextView btnOperation,btnExecution;

            public TodoHolder(View itemView) {
                super(itemView);
                patrolItemContainer = (LinearLayout) itemView.findViewById(R.id.patrol_item_container);
                tvTaskName = (TextView) itemView.findViewById(R.id.tv_task_name);
                tvTaskType = (TextView) itemView.findViewById(R.id.tv_task_type);
                tvTaskDesc = (TextView) itemView.findViewById(R.id.tv_task_desc);
                tvState = (TextView) itemView.findViewById(R.id.tv_state);
                tvTime = (TextView) itemView.findViewById(R.id.tv_time);
                llDefect= (LinearLayout) itemView.findViewById(R.id.llDefect);
                btnOperation= (TextView) itemView.findViewById(R.id.btnOperation);
                btnExecution= (TextView) itemView.findViewById(R.id.btnExecution);
            }

            public void setTaskName(String taskName) {
                tvTaskName.setText(taskName);
            }

            public void setTvTaskDesc(String taskDesc) {
                tvTaskDesc.setText(taskDesc == null ? "" : taskDesc);
            }


            public void setTaskType(String type) {
                if (IProcState.INSPECT.equals(type)) {
                    this.tvTaskType.setText(getString(R.string.patrol));
                } else if (IProcState.DEFECT.equals(type)) {
                    this.tvTaskType.setText(getString(R.string.defect));
                } else {
                    tvTaskType.setText(getString(R.string.unknown));
                }
            }

            public void setTime(long time) {
                tvTime.setText(Utils.getFormatTimeYYMMDDHHmmss2(time));
            }


            public void setState(String state) {
                switch (state) {
                    case IProcState.INSPECT_CREATE:
                    case IProcState.DEFECT_WRITE:
                        tvState.setText(getString(R.string.to_be_assigned));
                        tvState.setTextColor(getResources().getColor(R.color.orangered));
                        tvState.setBackgroundResource(R.drawable.patrol_status_shape_red);
                        break;
                    case IProcState.INSPECT_START:
                        tvState.setText(R.string.not_start);
                        tvState.setTextColor(getResources().getColor(R.color.orangered));
                        tvState.setBackgroundResource(R.drawable.patrol_status_shape_red);
                        break;
                    case IProcState.INSPECT_EXCUTE:
                        tvState.setText(getString(R.string.in_patrol));
                        tvState.setTextColor(getResources().getColor(R.color.text_yellow));
                        tvState.setBackgroundResource(R.drawable.patrol_status_shape_yellow);
                        break;
                    case IProcState.DEFECT_HANDLING:
                        tvState.setText(getString(R.string.defect_eliminating));
                        tvState.setTextColor(getResources().getColor(R.color.text_yellow));
                        tvState.setBackgroundResource(R.drawable.patrol_status_shape_yellow);
                        break;
                    case IProcState.INSPECT_CONFIRM:
                    case IProcState.DEFECT_CONFIRMING:
                        tvState.setText(getString(R.string.to_be_determined));
                        tvState.setTextColor(getResources().getColor(R.color.lightskyblue));
                        tvState.setBackgroundResource(R.drawable.patrol_status_shape_blue);
                        break;
                    case IProcState.INSPECT_FINISHED:
                        tvState.setText(getString(R.string.completed));
                        tvState.setTextColor(Color.GREEN);
                        tvState.setBackgroundResource(R.drawable.patrol_status_shape_green);
                        break;
                    case IProcState.DEFECT_ABORT:
                        tvState.setText(getString(R.string.has_given_up));
                        tvState.setTextColor(Color.BLACK);
                        tvState.setBackgroundResource(R.drawable.patrol_status_shape_black);
                        break;
                }
            }

            public LinearLayout getItemContainer() {
                return patrolItemContainer;
            }
        }
    }

    private class ListChangeReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(Constant.ACTION_DEFECTS_UPDATE) || intent.getAction().equals(Constant.ACTION_PATROL_UPDATE)) {
                resetRefreshStatus();
                showLoading();
                requestData();
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

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stationListPresenter.onViewDetached();
        presenter.onViewDetached();
        if (receiver != null) {
            lbm.unregisterReceiver(receiver);
        }
    }

    private RealTimeAlarmFragment.OnHideHeadViewListenerWarn hideHeadViewListener;

    public void setHideHeadViewListener(RealTimeAlarmFragment.OnHideHeadViewListenerWarn hideHeadViewListener) {
        this.hideHeadViewListener = hideHeadViewListener;
    }

    public void dismissReflashLoading() {
        if (mSwipeRefreshLayout != null && mSwipeRefreshLayout.isRefreshing()) {
            mSwipeRefreshLayout.setRefreshing(false);
        }
    }
}
