package com.huawei.solarsafe.view.maintaince.patrol;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.huawei.solarsafe.MyApplication;
import com.huawei.solarsafe.R;
import com.huawei.solarsafe.bean.defect.MapTodoBean;
import com.huawei.solarsafe.bean.defect.WorkerBean;
import com.huawei.solarsafe.model.maintain.IProcState;
import com.huawei.solarsafe.utils.Utils;
import com.huawei.solarsafe.view.maintaince.defects.NewDefectActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by P00319 on 2017/3/3.
 */

public class WorkerInfoWindow {
    private Context mContext;

    private RecyclerView rvTodoList;

    private WorkerInfoAdapter adapter;

    private List<MapTodoBean> list = new ArrayList<>();

    private TextView userName;

    private TextView userPhone;

    boolean haveDefectManagement = true;

    /**
     * PopupWindow菜单
     */
    private PopupWindow mPopupWindow;
    private Button newTask;

    public WorkerInfoWindow(Context context) {
        this.mContext = context.getApplicationContext();
        initPopupWindw();
    }

    private void initPopupWindw() {
        View view;
        view = LayoutInflater.from(mContext).inflate(R.layout.popwindow_patrol_worker_info, null);
        mPopupWindow = new PopupWindow(view, ActionBar.LayoutParams.MATCH_PARENT, Utils.dp2Px(mContext, 270), true);
        mPopupWindow.setOutsideTouchable(false);
        mPopupWindow.setFocusable(false);
        rvTodoList = (RecyclerView) view.findViewById(R.id.rv_todo_list);
        rvTodoList.setLayoutManager(new LinearLayoutManager(mContext));
        userName = (TextView) view.findViewById(R.id.user_name);
        userPhone = (TextView) view.findViewById(R.id.user_phone);
        adapter = new WorkerInfoAdapter();
        rvTodoList.setAdapter(adapter);
        newTask = (Button) view.findViewById(R.id.new_task);
        newTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(mContext, NewDefectActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(intent);
            }
        });
    }

    public void setData(List<MapTodoBean> list, WorkerBean workerBean) {
        this.userPhone.setText(workerBean.getTel());
        this.userName.setText(workerBean.getUserName());
        this.list.clear();
        this.list.addAll(list);
        adapter.notifyDataSetChanged();
    }

    public void show(View view) {
        mPopupWindow.showAtLocation(view, Gravity.BOTTOM, 0, 0);
    }

    public void show(View view,boolean haveDefectManagement) {
        mPopupWindow.showAtLocation(view, Gravity.BOTTOM, 0, 0);
        this.haveDefectManagement = haveDefectManagement;
        if (haveDefectManagement){
            newTask.setVisibility(View.VISIBLE);
        }else{
            newTask.setVisibility(View.GONE);
        }
    }

    /**
     * 退出popupwindow
     */
    public void dissmiss() {
        if (mPopupWindow != null && mPopupWindow.isShowing()) {
            mPopupWindow.dismiss();
        }
    }

    /**
     * popupwindow是否正在显示
     */
    public boolean isShowing() {
        if (mPopupWindow != null) {
            return mPopupWindow.isShowing();
        }
        return false;
    }

    public class WorkerInfoAdapter extends RecyclerView.Adapter<WorkerInfoAdapter.WorkerViewHolder> {

        @Override
        public WorkerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new WorkerViewHolder(LayoutInflater.from(MyApplication.getContext()).inflate(R.layout.item_patrol_map_worker_info, parent, false));
        }

        @Override
        public void onBindViewHolder(WorkerViewHolder holder, int position) {
            if (position == 0) {
                    holder.llContent.setBackgroundColor(MyApplication.getContext().getResources().getColor(R.color.coral));
                    holder.tvStationName.setBackgroundColor(MyApplication.getContext().getResources().getColor(R.color.coral));
                    holder.tvStationName.setText(mContext.getString(R.string.power_station_name));
                    holder.tvStationName.setTextColor(Color.WHITE);
                    holder.tvTaskType.setBackgroundColor(MyApplication.getContext().getResources().getColor(R.color.coral));
                    holder.tvTaskType.setText(R.string.task_type);
                    holder.tvTaskType.setTextColor(Color.WHITE);
                    holder.tvTaskDesc.setBackgroundColor(MyApplication.getContext().getResources().getColor(R.color.coral));
                    holder.tvTaskDesc.setText(mContext.getString(R.string.mission_detail));
                    holder.tvTaskDesc.setTextColor(Color.WHITE);
            } else {
                if(list.size() > 0){
                    holder.tvStationName.setText(list.get(position - 1).getSName());
                    holder.tvTaskDesc.setText(list.get(position - 1).getProcDes());
                    holder.tvTaskType.setText(IProcState.INSPECT.equals(list.get(position - 1).getProcKey()) ? mContext.getString(R.string.patrol) : mContext.getString(R.string.defect));
                }
            }
        }

        @Override
        public int getItemCount() {
            return list.size() + 1;
        }

        class WorkerViewHolder extends RecyclerView.ViewHolder {

            private TextView tvStationName;
            private TextView tvTaskType;
            private TextView tvTaskDesc;
            private LinearLayout llContent;

            public WorkerViewHolder(View view) {
                super(view);
                tvStationName = (TextView) view.findViewById(R.id.tv_station_name);
                tvTaskType = (TextView) view.findViewById(R.id.tv_task_type);
                tvTaskDesc = (TextView) view.findViewById(R.id.tv_task_desc);
                llContent=view.findViewById(R.id.llContent);
            }
        }
    }
}
