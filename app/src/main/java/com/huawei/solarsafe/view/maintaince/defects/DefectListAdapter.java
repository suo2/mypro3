package com.huawei.solarsafe.view.maintaince.defects;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.huawei.solarsafe.R;
import com.huawei.solarsafe.bean.GlobalConstants;
import com.huawei.solarsafe.bean.defect.DefectDetail;
import com.huawei.solarsafe.bean.defect.WorkFlowBean;
import com.huawei.solarsafe.model.maintain.IProcState;
import com.huawei.solarsafe.model.maintain.defect.DefectProcEnum;
import com.huawei.solarsafe.utils.Utils;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuAdapter;

import java.util.ArrayList;
import java.util.List;

import static com.huawei.solarsafe.view.maintaince.defects.DefectListActivity.REQUEST_CODE;
import static com.huawei.solarsafe.view.maintaince.main.PatrolFragment.EXECUTION;
import static com.huawei.solarsafe.view.maintaince.main.PatrolFragment.OPERATION;

/**
 * Created by p00319 on 2017/3/2.
 */

public class DefectListAdapter extends SwipeMenuAdapter<DefectListAdapter.DfHolder> {

    private List<DefectDetail> list;
    Activity activity;

    private OnItemClickListener mOnItemClickListener;
    private View.OnClickListener defectClickListener;

    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    public DefectListAdapter(List<DefectDetail> list , Activity activity) {
        this.list = list;
        this.activity=activity;
    }

    @Override
    public View onCreateContentView(ViewGroup parent, int viewType) {
        return LayoutInflater.from(parent.getContext()).inflate(R.layout.item_defect_list, parent, false);
    }

    @Override
    public DfHolder onCompatCreateViewHolder(View realContentView, int viewType) {
        return new DfHolder(realContentView);
    }


    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    @Override
    public void onBindViewHolder(final DfHolder holder, int position) {
        final DefectDetail bean = list.get(position);
        holder.tvStationName.setText(bean.getSName());
        holder.tvDevName.setText(bean.getDeviceName());
        String timeZone = null;
        if(bean.getTimeZone() > 0 || bean.getTimeZone() == 0){
            timeZone = "+" + bean.getTimeZone();
        }else {
            timeZone = bean.getTimeZone() + "";
        }
        holder.tvStartTime.setText(bean.getStartTime() == 0 ? "" : Utils.getFormatTimeYYMMDDHHmmss2(bean.getStartTime(),timeZone));
        holder.tvEndTime.setText(bean.getEndTime() == 0 ? "" : Utils.getFormatTimeYYMMDDHHmmss2(bean.getEndTime(),timeZone));
        holder.tvDesc.setText(bean.getDefectDesc());
        for (DefectProcEnum proc : DefectProcEnum.values()) {
            if (proc.getProcId().equals(bean.getProcState())) {
                holder.imMark.setText(proc.getProcName());
            }
        }
        if (mOnItemClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = holder.getLayoutPosition();
                    mOnItemClickListener.onItemClick(holder.itemView, pos);
                }
            });
        }

        defectClickListener=new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.putExtra("detail", bean);
                if (bean.getProcState().equals(IProcState.DEFECT_WRITE)
                        && String.valueOf(GlobalConstants.userId).equals(bean.getOwnerId())) {
                    intent.putExtra("isModify", true);
                    intent.setClass(activity, NewDefectActivity.class);
                } else {
                    intent.putExtra("defectId", bean.getDefectId() + "");
                    intent.putExtra("ifJumpFromMessageBox", true);
                    intent.setClass(activity, DefectDetailActivity.class);
                }

                switch (view.getId()){
                    case R.id.btnOperation:
                        intent.putExtra("defectType",OPERATION);
                        break;
                    case R.id.btnExecution:
                        intent.putExtra("defectType",EXECUTION);
                        break;
                }

                if (activity!=null){
                    activity.startActivityForResult(intent, REQUEST_CODE);
                }
            }
        };

        holder.btnOperation.setOnClickListener(defectClickListener);
        holder.btnExecution.setOnClickListener(defectClickListener);

        //多人消缺新增
        //判断消缺是否已经结束/已放弃
        if (IProcState.DEFECT_FINISHED.equals(bean.getProcState()) || IProcState.DEFECT_ABORT.equals(bean.getProcState())){
            holder.llDefect.setVisibility(View.GONE);
        }else{
            holder.llDefect.setVisibility(View.VISIBLE);
            //判断是否可以执行
            if (String.valueOf(GlobalConstants.userId).equals(bean.getOwnerId())){
                holder.btnExecution.setVisibility(View.VISIBLE);
            }else{
                holder.btnExecution.setVisibility(View.GONE);
            }

            //判断是否可以操作
            ArrayList<WorkFlowBean.DefectDisposeCituationBean> ddcbArrayList=bean.getWfhts();
            if (ddcbArrayList!=null && !ddcbArrayList.isEmpty()){
                ArrayList<String> operatorArrayList=new ArrayList<>();
                for (WorkFlowBean.DefectDisposeCituationBean ddcb : ddcbArrayList){
                    operatorArrayList.add(ddcb.getOperator());
                }

                if (operatorArrayList.contains(String.valueOf(GlobalConstants.userId))){
                    holder.btnOperation.setVisibility(View.VISIBLE);
                }else{
                    holder.btnOperation.setVisibility(View.GONE);
                }
            }else{
                holder.btnOperation.setVisibility(View.GONE);
            }
        }

    }


    protected class DfHolder extends RecyclerView.ViewHolder {

        private TextView tvStationName;
        private TextView tvDevName;
        private TextView tvStartTime;
        private TextView tvEndTime;
        private TextView tvDesc;
        private TextView imMark;
        private LinearLayout llDefect;
        private Button btnOperation,btnExecution;

        public DfHolder(View view) {
            super(view);
            tvStationName = (TextView) view.findViewById(R.id.tv_station_name);
            tvDevName = (TextView) view.findViewById(R.id.tv_dev_name);
            tvStartTime = (TextView) view.findViewById(R.id.tv_start_time);
            tvEndTime = (TextView) view.findViewById(R.id.tv_end_time);
            tvDesc = (TextView) view.findViewById(R.id.tv_desc);
            imMark = (TextView) view.findViewById(R.id.im_mark);
            llDefect= (LinearLayout) view.findViewById(R.id.llDefect);
            btnOperation= (Button) view.findViewById(R.id.btnOperation);
            btnExecution= (Button) view.findViewById(R.id.btnExecution);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }
}
