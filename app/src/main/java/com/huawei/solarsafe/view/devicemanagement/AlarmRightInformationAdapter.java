package com.huawei.solarsafe.view.devicemanagement;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.huawei.solarsafe.R;
import com.huawei.solarsafe.bean.Constant;
import com.huawei.solarsafe.bean.device.DevAlarmInfo;
import com.huawei.solarsafe.utils.Utils;

import java.util.List;
import java.util.TimeZone;

/**
 * Created by P00708 on 2018/3/5.
 */

public class AlarmRightInformationAdapter extends BaseAdapter{

    private Context context;
    private List<DevAlarmInfo.ListBean> list;
    private String intervalTypeName;
    public AlarmRightInformationAdapter(Context context, List<DevAlarmInfo.ListBean> list,String intervalTypeName){
       this.context = context;
       this.list =list;
       this.intervalTypeName = intervalTypeName;
    }

    @Override
    public int getCount() {
        return this.list==null?0:this.list.size();
    }

    @Override
    public Object getItem(int i) {
        return this.list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Holder holder;
        if(view == null){
            holder = new Holder();
            view = LayoutInflater.from(context).inflate(R.layout.alarm_right_information_adapter_layout,null);
            holder.contentBg = (LinearLayout) view.findViewById(R.id.content_list);
            holder.levelTx = (TextView) view.findViewById(R.id.tv_level);
            holder.levelView = view.findViewById(R.id.view_level);
            holder.intervalTypeTx = (TextView) view.findViewById(R.id.tv_interval_type);
            holder.intervalTypeView = view.findViewById(R.id.view_interval_type);
            holder.intervalNameTx = (TextView) view.findViewById(R.id.tv_interval_name);
            holder.intervalNameView = view.findViewById(R.id.interval_name_view);
            holder.alarmTypeTx = (TextView) view.findViewById(R.id.tv_alarm_type);
            holder.alarmTypeView = view.findViewById(R.id.view_alarm_type);
            holder.alarmNameTx = (TextView) view.findViewById(R.id.tv_alarm_name);
            holder.alarmNameView = view.findViewById(R.id.alarm_name_view);
            holder.statesTx = (TextView) view.findViewById(R.id.tv_states);
            holder.statesView =  view.findViewById(R.id.states_view);
            holder.localTimeTx = (TextView) view.findViewById(R.id.tv_local_time);
            holder.localTimeView = view.findViewById(R.id.view_local_time);
            holder.produceTimeTx = (TextView) view.findViewById(R.id.tv_produce_time);
            holder.produceTimeView = view.findViewById(R.id.view_produce_time);
            holder.recoveryTimeTx = (TextView) view.findViewById(R.id.tv_recovery_time);
            holder.recoveryTimeView = view.findViewById(R.id.recovery_time_view);
            holder.repairAdviceTx = (TextView) view.findViewById(R.id.tv_repair_advice);
            holder.repairAdviceView =  view.findViewById(R.id.repair_advice_view);
            view.setTag(holder);
        }else{
            holder = (Holder) view.getTag();
        }

        handlerViewData(i,holder);
        if(i %2 == 0){
            holder.contentBg.setBackgroundResource(R.color.common_white);
        }else{
            holder.contentBg.setBackgroundResource(R.color.hui_white);
        }

        return view;
    }

    class Holder{
        LinearLayout contentBg;
        TextView levelTx;
        View levelView;
        TextView intervalTypeTx;
        View intervalTypeView;
        TextView intervalNameTx;
        View intervalNameView;
        TextView alarmTypeTx;
        View alarmTypeView;
        TextView alarmNameTx;
        View alarmNameView;
        TextView statesTx;
        View statesView;
        TextView localTimeTx;
        View localTimeView;
        TextView produceTimeTx;
        View produceTimeView;
        TextView recoveryTimeTx;
        View recoveryTimeView;
        TextView repairAdviceTx;
        View repairAdviceView;
    }

    private void handlerViewData(int position ,Holder holder){

        int alarmLevel = (int) list.get(position).getLevId();
        switch (alarmLevel){

            case Constant.SERIOUS_ALARM:
                holder.levelTx.setTextColor(context.getResources().getColor(R.color.device_alarm_item_major_my));
                holder.levelTx.setText(context.getResources().getString(R.string.serious));
                break;
            case Constant.IMPORTANT_ALARM:
                holder.levelTx.setTextColor(context.getResources().getColor(R.color.device_alarm_item_major_im));
                holder.levelTx.setText(context.getResources().getString(R.string.important));
                break;
            case Constant.SECONDARY_ALARM:
                holder.levelTx.setTextColor(context.getResources().getColor(R.color.device_alarm_item_major_sub));
                holder.levelTx.setText(context.getResources().getString(R.string.subordinate));
                break;
            case Constant.TOAST_ALARM:
                holder.levelTx.setTextColor(context.getResources().getColor(R.color.device_alarm_item_major_sug));
                holder.levelTx.setText(context.getResources().getString(R.string.suggestive));
                break;
            default:
                break;
        }

        int alarmType =  list.get(position).getTeleTypeId();
        switch (alarmType){

            case Constant.CHANGE_SIGNAL:
                holder.alarmTypeTx.setText(context.getResources().getString(R.string.displacement_signal));
                break;
            case Constant.ABNORMAL_ALARM:
                holder.alarmTypeTx.setText(context.getResources().getString(R.string.abnormal_alarm));
                break;
            case Constant.PROTECTION_EVENT:
                holder.alarmTypeTx.setText(context.getResources().getString(R.string.protection_event));
                break;
            case Constant.COMMUNICATION_STATE:
                holder.alarmTypeTx.setText(context.getResources().getString(R.string.communication_state));
                break;
            case Constant.NOTIFY_INFORMATION:
                holder.alarmTypeTx.setText(context.getResources().getString(R.string.informing_information));
                break;
            default:
                break;
        }
        if(list.get(position).getDevName() != null){  //间隔名称可能为空，取设备名称
            holder.intervalNameTx.setText(list.get(position).getDevName());
        }else{
            holder.intervalNameTx.setText("");
        }
        if(intervalTypeName != null){
            holder.intervalTypeTx.setText(intervalTypeName);
        }else{
            holder.intervalTypeTx.setText("");
        }
        if(list.get(position).getAlarmName() != null){
            holder.alarmNameTx.setText(list.get(position).getAlarmName());
        }else{
            holder.alarmNameTx.setText("-");
        }
        String alarmStatus = "";
        switch (list.get(position).getStatusId()) {
            case Constant.AlarmStatusID.ALARM_STATUS_ACTIVE:
                alarmStatus = context.getResources().getString(R.string.activation);
                break;
            case Constant.AlarmStatusID.ALARM_STATUS_ACKED:
                alarmStatus =context.getResources(). getString(R.string.pvmodule_alarm_sured);
                break;
            case Constant.AlarmStatusID.ALARM_STATUS_PROCESSING:
                alarmStatus = context.getResources().getString(R.string.in_hand);
                break;
            case Constant.AlarmStatusID.ALARM_STATUS_PROCESSED:
                alarmStatus = context.getResources().getString(R.string.handled);
                break;
            case Constant.AlarmStatusID.ALARM_STATUS_CLEARED:
                alarmStatus = context.getResources().getString(R.string.cleared);
                break;
            case Constant.AlarmStatusID.ALARM_STATUS_RECOVERED:
                alarmStatus = context.getResources().getString(R.string.restored);
                break;
            default:
                break;
        }
        int localTimeZone = TimeZone.getDefault().getRawOffset() / 3600000;

        String serviceTimeZone;
        String localTimeZoneStr;
        if(list.get(position).getTimeZone() > 0 || list.get(position).getTimeZone() == 0){
            serviceTimeZone = "+" + list.get(position).getTimeZone();
        }else {
            serviceTimeZone = list.get(position).getTimeZone() + "";
        }
        if(localTimeZone>=0){
            localTimeZoneStr = "+"+localTimeZone;
        }else{
            localTimeZoneStr = localTimeZone+"";
        }
        if(list.get(position).getRaiseDate() != 0){
            holder.localTimeTx.setText(Utils.getFormatTimeYYMMDDHHmmss2(list.get(position).getRaiseDate(),localTimeZoneStr));//本地时间
            holder.produceTimeTx.setText(Utils.getFormatTimeYYMMDDHHmmss2(list.get(position).getRaiseDate(),serviceTimeZone));//产生时间
        }else{
            holder.localTimeTx.setText("-");//本地时间
            holder.produceTimeTx.setText("-");
        }
        if(list.get(position).getRecoverDate() != 0){
            holder.recoveryTimeTx.setText(Utils.getFormatTimeYYMMDDHHmmss2(list.get(position).getRecoverDate(),serviceTimeZone));//修复时间
        }else{
            holder.recoveryTimeTx.setText("-");//修复时间
        }

        if(list.get(position).getRepairSuggestion() != null){
            holder.repairAdviceTx.setText(list.get(position).getRepairSuggestion());
        }else{
            holder.repairAdviceTx.setText("-");
        }
        holder.statesTx.setText(alarmStatus);
    }
}
