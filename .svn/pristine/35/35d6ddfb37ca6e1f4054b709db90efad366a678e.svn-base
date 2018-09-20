package com.huawei.solarsafe.view.maintaince.todotasks;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
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
import com.huawei.solarsafe.utils.Utils;
import com.huawei.solarsafe.view.BaseActivity;
import com.huawei.solarsafe.view.homepage.station.IStationListView;
import com.huawei.solarsafe.view.maintaince.defects.DefectDetailActivity;
import com.huawei.solarsafe.view.maintaince.defects.NewDefectActivity;
import com.huawei.solarsafe.view.maintaince.main.PatrolFragment;
import com.huawei.solarsafe.view.maintaince.patrol.PatrolTaskDetailActivity;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TodoTaskActivity extends BaseActivity<PatrolPresenter> implements ITodoTaskView, IStationListView {
    public static final String TAG = TodoTaskActivity.class.getSimpleName();
    private SearchView mSearchView;
    private RecyclerView patrolList;
    private View view;
    private PatrolAdapter adapter;
    private TextView searchTextView;
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

    private View.OnClickListener defectClickListener;
    private LocalBroadcastManager lbm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        lbm = LocalBroadcastManager.getInstance(MyApplication.getContext());
        tv_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        arvTitle.setText(getString(R.string.todo_task_list));
        tv_right.setVisibility(View.GONE);
        stationListPresenter = new StationListPresenter();
        stationListPresenter.onViewAttached(this);
        stationListPresenter.requestStationMapList(null);
        receiver = new ListChangeReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(Constant.ACTION_DEFECTS_UPDATE);
        filter.addAction(Constant.ACTION_PATROL_UPDATE);
        lbm.registerReceiver(receiver, filter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Collections.sort(orignalList, patrolWorkCompare);
        requestData();
        if (!TextUtils.isEmpty(searchTextView.getText().toString())) {
            searchTextView.setText("");
        }
        mSearchView.clearFocus();
        Utils.closeSoftKeyboard(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_patrol_work_list;
    }

    @Override
    protected void initView() {
        patrolList = (RecyclerView) findViewById(R.id.work_list);
        patrolList.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if (newState == RecyclerView.SCROLL_STATE_DRAGGING) {
                    hideSoftInput();
                }
            }
        });
        adapter = new PatrolAdapter();
        patrolList.setAdapter(adapter);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        patrolList.setLayoutManager(manager);
        if (orignalList != null) {
            Collections.sort(orignalList, patrolWorkCompare);
            adapter.setDatas(orignalList);
        }
        view = findViewById(R.id.tips);
        view.requestFocus();
        mSearchView = (SearchView) findViewById(R.id.search_patrol);
        searchHelper = SearchHelper.getInstance();
        mSearchView.setIconifiedByDefault(false);
        mSearchView.setIconified(true);
        mSearchView.onActionViewExpanded();
        //获取搜索框的TextView进行设置
        int id = mSearchView.getContext().getResources().getIdentifier("android:id/search_src_text", null, null);
        searchTextView = (EditText) mSearchView.findViewById(id);
        searchTextView.setTextColor(getResources().getColor(R.color.textview_text_group_homepage_item_tvcolor));
        searchTextView.setTextSize(15);
        searchTextView.setFilters(new InputFilter[]{new InputFilter.LengthFilter(100)});
        searchTextView.setHintTextColor(getResources().getColor(R.color.COLOR_737373));

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

    /**
     * 隐藏键盘
     */
    private void hideSoftInput() {
        if (mSearchView != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null) {
                imm.hideSoftInputFromWindow(mSearchView.getWindowToken(), 0);
            }
            mSearchView.clearFocus();
        }
    }

    @Override
    protected PatrolPresenter setPresenter() {
        return new PatrolPresenter();
    }

    @Override
    public void requestData() {
        Map<String, String> paramDefect = new HashMap<>();
        paramDefect.put("page", "1");
        paramDefect.put("pageSize", "15");
        if (presenter != null) {
            presenter.doRequestProcess(paramDefect);
        }
    }

    @Override
    public void getData(BaseEntity data) {
        if(data == null){
            return;
        }
        if (data instanceof TodoTaskList) {
            TodoTaskList defectTodoProcessList = (TodoTaskList) data;
            if (defectTodoProcessList.getList() != null) {
                orignalList.clear();
                orignalList.addAll(defectTodoProcessList.getList());
            }
            Collections.sort(orignalList, patrolWorkCompare);
            adapter.setDatas(orignalList);
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


    private class PatrolAdapter extends RecyclerView.Adapter<PatrolAdapter.PatrolHolder> {
        private List<TodoTaskBean> datas;

        public void setDatas(List<TodoTaskBean> temp) {
            if (datas == null) {
                datas = new ArrayList<>();
            }
            datas.clear();
            datas.addAll(temp);
            notifyDataSetChanged();
        }

        @Override
        public PatrolHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.patrol_item, parent, false);
            return new PatrolHolder(view);
        }

        @Override
        public void onBindViewHolder(PatrolHolder holder, int position) {
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
                        defectIntent.putExtra("ifJumpFromMessageBox", true);
                        defectIntent.putExtra("procId", String.valueOf(info.getProcId()));
                        if (IProcState.DEFECT_WRITE.equals(info.getProcState())) {
                            defectIntent.putExtra("isModify", true);
                            defectIntent.setClass(TodoTaskActivity.this, NewDefectActivity.class);
                        } else {
                            defectIntent.setClass(TodoTaskActivity.this, DefectDetailActivity.class);
                        }
                        String sid = info.getSId();
                        if(stationMapList != null){
                            if (stationMapList.getStationMapLists() != null) {
                                for (StationForMapInfo stationForMapInfo : stationMapList.getStationMapLists()) {
                                    if (sid.equals(stationForMapInfo.getStationCode())) {
                                        endPoint = new LatLng(stationForMapInfo.getLatitude(), stationForMapInfo.getLongitude());
                                    }
                                }
                            }
                        }
                        defectIntent.putExtra(GlobalConstants.END_LOCATION, endPoint);

                        int viewId=view.getId();
                        switch (viewId){
                            case R.id.btnOperation:
                                defectIntent.putExtra("defectType", PatrolFragment.OPERATION);
                                startActivity(defectIntent);
                                break;
                            case R.id.btnExecution:
                                defectIntent.putExtra("defectType",PatrolFragment.EXECUTION);
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
                            intent.setClass(TodoTaskActivity.this, PatrolTaskDetailActivity.class);
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

        class PatrolHolder extends RecyclerView.ViewHolder {
            private LinearLayout patrolItemContainer;
            private TextView tvTaskName;
            private TextView tvTaskType;
            private TextView tvTaskDesc;
            private TextView tvState;
            private TextView tvTime;
            private LinearLayout llDefect;
            private TextView btnOperation,btnExecution;

            public PatrolHolder(View itemView) {
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
                        tvState.setText(getString(R.string.not_started));
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (receiver != null) {
            lbm.unregisterReceiver(receiver);
        }
    }

    @Override
    public void onBackPressed() {
        if (mSearchView.hasFocus()) {
            if (!TextUtils.isEmpty(searchTextView.getText().toString())) {
                searchTextView.setText("");
            }
            hideSoftInput();
        } else {
            finish();
        }
    }

    private class ListChangeReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(Constant.ACTION_DEFECTS_UPDATE) || intent.getAction().equals(Constant.ACTION_PATROL_UPDATE)) {
                requestData();
            }
        }
    }
}
