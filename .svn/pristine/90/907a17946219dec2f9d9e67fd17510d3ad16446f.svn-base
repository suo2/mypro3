package com.huawei.solarsafe.view.maintaince.patrol;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.huawei.solarsafe.MyApplication;
import com.huawei.solarsafe.R;
import com.huawei.solarsafe.bean.BaseEntity;
import com.huawei.solarsafe.bean.Constant;
import com.huawei.solarsafe.bean.patrol.PatrolInspectObjList;
import com.huawei.solarsafe.bean.patrol.PatrolInspectTaskBean;
import com.huawei.solarsafe.bean.patrol.PatrolInspectTaskList;
import com.huawei.solarsafe.bean.patrol.PatrolObj;
import com.huawei.solarsafe.model.maintain.IProcState;
import com.huawei.solarsafe.presenter.maintaince.patrol.PatrolMgrPresenter;
import com.huawei.solarsafe.utils.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.huawei.solarsafe.bean.GlobalConstants.KEY_INSPECT_ID;
import static com.huawei.solarsafe.bean.GlobalConstants.KEY_PATROL_STATUS;
import static com.huawei.solarsafe.bean.GlobalConstants.KEY_REMARK;
import static com.huawei.solarsafe.bean.GlobalConstants.KEY_S_ID;

/**
 * Created by p00319 on 2017/2/15.
 */

public class PatrolMgrFragment extends Fragment implements IPatrolMrgView, OnClickListener, PullToRefreshListView.OnRefreshListener2, CompoundButton.OnCheckedChangeListener {
    private PullToRefreshListView listView;
    private List<PatrolObj> patrolObjs = new ArrayList<>();
    private List<PatrolInspectTaskBean> patrolInspectTaskBeans = new ArrayList<>();
    private PlantListAdapter plantListAdapter;
    private TaskListAdapter taskListAdapter;
    private HashMap<String, String> params = new HashMap<>();
    private PatrolMgrPresenter patrolMgrPresenter = new PatrolMgrPresenter();
    private final int PAGE_SIZE = 20;
    private int current_page = 1;
    private RadioButton rbtStaion, rbtTask;
    private StatusUpdateReceiver receiver;
    private LocalBroadcastManager lbm;

    public static PatrolMgrFragment newInstance() {
        PatrolMgrFragment patrolMgrFragment = new PatrolMgrFragment();
        Bundle args = new Bundle();
        patrolMgrFragment.setArguments(args);
        return patrolMgrFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        lbm = LocalBroadcastManager.getInstance(MyApplication.getContext());
        patrolMgrPresenter.setModel();
        receiver = new StatusUpdateReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(Constant.ACTION_PATROL_UPDATE);
        lbm.registerReceiver(receiver, filter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (receiver != null) {
            lbm.unregisterReceiver(receiver);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_patrol_mgr, container, false);
        initView(view);
        return view;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
    }

    private void initView(View view) {
        patrolMgrPresenter.onViewAttached(this);
        rbtStaion = (RadioButton) view.findViewById(R.id.rbt_station_list);
        rbtStaion.setOnCheckedChangeListener(this);
        rbtTask = (RadioButton) view.findViewById(R.id.rbt_task_list);
        rbtTask.setOnCheckedChangeListener(this);
        listView = (PullToRefreshListView) view.findViewById(R.id.pull_list);
        listView.setOnRefreshListener(this);
        listView.setMode(PullToRefreshBase.Mode.BOTH);
        View emptyView = LayoutInflater.from(getActivity()).inflate(R.layout.empty_view, null);
        listView.setEmptyView(emptyView);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                if (rbtStaion.isChecked()) {
                    intent.setClass(getActivity(), PlantPatrolHistroryActivity.class);
                    PatrolObj obj = (PatrolObj) parent.getItemAtPosition(position);
                    intent.putExtra(PlantPatrolHistroryActivity.KEY_SID, obj.getsId());
                    intent.putExtra("plantName", obj.getAnnexObjName());
                    startActivity(intent);
                } else {
                    PatrolInspectTaskBean bean = (PatrolInspectTaskBean) parent.getItemAtPosition(position);
                    intent.setClass(getActivity(), PatrolTaskDetailActivity.class);
                    intent.putExtra(KEY_S_ID, bean.getSId());
                    intent.putExtra(KEY_INSPECT_ID, bean.getProcId());
                    intent.putExtra("taskId", String.valueOf(bean.getId()));
                    intent.putExtra("procState", bean.getProcState());
                    intent.putExtra(KEY_REMARK, bean.getProcDesc());
                    intent.putExtra(KEY_PATROL_STATUS, bean.getProcState());
                    intent.putExtra("executor", bean.getCurrentAssignee());
                    intent.putExtra("currentTaskId", bean.getCurrentTaskId());
                    startActivity(intent);
                }
            }
        });
        if (plantListAdapter == null) {
            plantListAdapter = new PlantListAdapter(getActivity());
        }
        listView.setAdapter(plantListAdapter);
        if (taskListAdapter == null) {
            taskListAdapter = new TaskListAdapter(getActivity());
        }
        toRequestData();
    }

    private void toRequestData() {
        params.put("page", String.valueOf(current_page));
        params.put("pageSize", String.valueOf(PAGE_SIZE));
        patrolMgrPresenter.requestInspectObj(params);
        patrolMgrPresenter.requestInspectTask(params);
    }

    @Override
    public void getInspectObj(BaseEntity data) {
        if (data instanceof PatrolInspectObjList) {
            listView.onRefreshComplete();
            PatrolInspectObjList patrolInspectObjList = (PatrolInspectObjList) data;
            if (current_page == 1) {
                patrolObjs.clear();
            }
            if (patrolInspectObjList != null && patrolInspectObjList.getList() != null) {
                patrolObjs.addAll(patrolInspectObjList.getList());
            }
            plantListAdapter.setmList(patrolObjs);
            plantListAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void getInspectTask(BaseEntity data) {
        if (data instanceof PatrolInspectTaskList) {
            listView.onRefreshComplete();
            if (current_page == 1) {
                patrolInspectTaskBeans.clear();
            }
            PatrolInspectTaskList patrolInspectTaskList = (PatrolInspectTaskList) data;
            if (patrolInspectTaskList != null && patrolInspectTaskList.getList() != null) {
                patrolInspectTaskBeans.addAll(patrolInspectTaskList.getList());
            }
            taskListAdapter.setmList(patrolInspectTaskBeans);
            taskListAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()) {
            case R.id.rbt_station_list:
                if (isChecked) {
                    setAdapter(plantListAdapter);
                    rbtStaion.setBackground(getResources().getDrawable(R.drawable.shape_single_item_left_corner_fill));
                    rbtTask.setBackground(getResources().getDrawable(R.drawable.shape_single_item_right_corner));
                }
                break;
            case R.id.rbt_task_list:
                if (isChecked) {
                    setAdapter(taskListAdapter);
                    rbtTask.setBackground(getResources().getDrawable(R.drawable.shape_single_item_right_corner_fill));
                    rbtStaion.setBackground(getResources().getDrawable(R.drawable.shape_single_item_left_corner));
                }
                break;
            default:
                break;
        }
    }


    @Override
    public void onPullDownToRefresh(PullToRefreshBase refreshView) {
        current_page = 1;
        params.put("page", String.valueOf(current_page));
        if (rbtStaion.isChecked()) {
            patrolObjs.clear();
            patrolMgrPresenter.requestInspectObj(params);
        } else {
            patrolInspectTaskBeans.clear();
            patrolMgrPresenter.requestInspectTask(params);
        }
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase refreshView) {
        current_page++;
        params.put("page", String.valueOf(current_page));
        if (rbtStaion.isChecked()) {
            patrolMgrPresenter.requestInspectObj(params);
        } else {
            patrolMgrPresenter.requestInspectTask(params);
        }
    }

    @Override
    public void onClick(View v) {

    }


    public static class PlantListAdapter extends BaseAdapter {
        List<PatrolObj> mList;
        Context mContext;

        @Override
        public int getCount() {
            return mList == null ? 0 : mList.size();
        }

        @Override
        public Object getItem(int position) {
            return mList == null ? null : mList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            MyViewHolder holder;
            if (convertView == null) {
                holder = new MyViewHolder();
                convertView = LayoutInflater.from(mContext).inflate(R.layout.fragment_patrol_manage_obj_item, null);
                holder.tvPlantName = (TextView) convertView.findViewById(R.id.tv_plant_name);
                holder.tvLastInspecDays = (TextView) convertView.findViewById(R.id.tv_message);
                holder.tvLastInspectTime = (TextView) convertView.findViewById(R.id.tv_last_inspect_time);
                holder.tvLastInspectPerson = (TextView) convertView.findViewById(R.id.tv_person);
                holder.tvStatus = (TextView) convertView.findViewById(R.id.tv_plant_status);
                convertView.setTag(holder);
            } else {
                holder = (MyViewHolder) convertView.getTag();
            }

            holder.tvPlantName.setText(mList.get(position).getAnnexObjName());
            String temp = "--";
            if (Integer.valueOf(mList.get(position).getLastInspectDay()) >= 0) {
                temp = String.valueOf(mList.get(position).getLastInspectDay());
            }
            holder.tvLastInspecDays.setText(mContext.getString(R.string.staion_has_patroled) + " " +mList.get(position).getInspectCount() + " " + mContext.getString(R.string.times) + "," + mContext.getString(R.string.time_interval_since_last_patrol) +" "+ temp + " " + mContext.getString(R.string.unit_day));
            long lastAnnexTime = mList.get(position).getLastAnnexTime();
            if ("null".equals(lastAnnexTime + "") || lastAnnexTime == 0) {
                holder.tvLastInspectTime.setText(R.string.has_not_time);
            } else {
                holder.tvLastInspectTime.setText(Utils.getFormatTimeYYMMDDHHmmss2(mList.get(position).getLastAnnexTime()));
            }

            holder.tvLastInspectPerson.setText(mList.get(position).getLastInspectPerson());
            int statusCode = mList.get(position).getLastInspectResult();
            String status = "--";
            switch (statusCode) {
                case 0:
                    status = mContext.getString(R.string.in_patrol);
                    break;
                case 1:
                    status = mContext.getString(R.string.finished);
                    break;
                case 2:
                    status = mContext.getString(R.string.discarded);
                    break;
                case 3:
                    status = mContext.getString(R.string.to_be_determined);
                    break;
                default:
                    break;
            }

            holder.tvStatus.setText(status);

            return convertView;
        }


        public void setmList(List<PatrolObj> mList) {
            this.mList.clear();
            this.mList.addAll(mList);
        }

        public PlantListAdapter(Context mContext) {
            this.mList = new ArrayList<>();
            this.mContext = mContext;
        }


        class MyViewHolder {
            TextView tvPlantName, tvLastInspecDays, tvLastInspectTime, tvLastInspectPerson, tvStatus;
        }
    }


    class TaskListAdapter extends BaseAdapter {
        LayoutInflater mLayoutInflater;
        List<PatrolInspectTaskBean> mList;
        Context mContext;

        @Override
        public int getCount() {
            return mList == null ? 0 : mList.size();
        }

        @Override
        public Object getItem(int position) {
            return mList == null ? null : mList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            MyViewHolder holder;
            if (convertView == null) {
                holder = new MyViewHolder();
                convertView = mLayoutInflater.inflate(R.layout.fragment_patrol_manage_task_item, parent, false);
                holder.tvTaskName = (TextView) convertView.findViewById(R.id.tv_task_name);
                holder.tvTime = (TextView) convertView.findViewById(R.id.tv_task_time);
                holder.tvPerson = (TextView) convertView.findViewById(R.id.tv_current_person);
                holder.tvFaultCount = (TextView) convertView.findViewById(R.id.tv_fault_count);
                holder.tvStatus = (TextView) convertView.findViewById(R.id.tv_status);
                holder.ivTaskName = (ImageView) convertView.findViewById(R.id.iv_task_name);
                convertView.setTag(holder);
            } else {
                holder = (MyViewHolder) convertView.getTag();
            }

            holder.tvTaskName.setText(mList.get(position).getProcName());
            if (mList.get(position).getEndTime() == 0) {
                holder.tvTime.setText(Utils.getFormatTimeYYMMDDHHmmss2(mList.get(position).getStartTime()) + " --> ");
            } else {
                holder.tvTime.setText(Utils.getFormatTimeYYMMDDHHmmss2(mList.get(position).getStartTime()) + " --> " + Utils
                        .getFormatTimeYYMMDDHHmmss2(mList.get(position).getEndTime()));
            }

            holder.tvPerson.setText(mList.get(position).getAssigner());
            holder.tvFaultCount.setText("" + mList.get(position).getExeptionCount());
            String status = mList.get(position).getProcState();
            int imgResId = R.drawable.ic_task_comp;
            switch (status) {
                case "all":
                    status = getString(R.string.all_of);
                    break;
                case IProcState.INSPECT_CREATE:
                    status = getString(R.string.undistributed);
                    imgResId = R.drawable.ic_task_comp;
                    break;
                case IProcState.INSPECT_START:
                    imgResId = R.drawable.ic_task_start;
                    status = getString(R.string.unopened);
                    break;
                case IProcState.INSPECT_EXCUTE:
                    imgResId = R.drawable.ic_task_ing;
                    status = getString(R.string.in_patrol);
                    break;
                case IProcState.INSPECT_CONFIRM:
                    imgResId = R.drawable.ic_task_daiqueren;
                    status = getString(R.string.to_be_determined);
                    break;
                case IProcState.INSPECT_FINISHED:
                    imgResId = R.drawable.ic_task_comp;
                    status = getString(R.string.terminated);
                    break;
                default:
                    break;
            }
            holder.tvStatus.setText(status);
            holder.ivTaskName.setImageResource(imgResId);

            return convertView;
        }


        public void setmList(List<PatrolInspectTaskBean> mList) {
            this.mList.clear();
            this.mList.addAll(mList);
        }

        public TaskListAdapter(Context mContext) {
            mList = new ArrayList<>();
            this.mContext = mContext;
            this.mLayoutInflater = LayoutInflater.from(mContext);
        }


        class MyViewHolder {
            TextView tvTaskName, tvTime, tvPerson, tvFaultCount, tvStatus;
            ImageView ivTaskName;
        }
    }


    private void setAdapter(BaseAdapter adapter) {
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    private class StatusUpdateReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(Constant.ACTION_PATROL_UPDATE)) {
                params.put("page", String.valueOf(current_page));
                params.put("pageSize", String.valueOf(PAGE_SIZE));
                //宋平修改  单号：45583
                patrolMgrPresenter.requestInspectObj(params);
                patrolMgrPresenter.requestInspectTask(params);
            }
        }
    }
}
