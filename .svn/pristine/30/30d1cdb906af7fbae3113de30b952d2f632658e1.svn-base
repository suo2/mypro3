package com.huawei.solarsafe.view.devicemanagement.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.huawei.solarsafe.R;
import com.huawei.solarsafe.bean.device.DevDetailInfo;

import java.util.ArrayList;

/**
 * <pre>
 *     author: Tzy
 *     time  : $date$
 *     desc  : 升压站设备信息碎片间隔拥有设备列表 适配器
 * </pre>
 */
public class IntervalHaveDeviceAdapter extends BaseExpandableListAdapter {
    Context context;
    ArrayList<DevDetailInfo.SignalArrBean> signalArrBeenList;

    public IntervalHaveDeviceAdapter(Context context){
        this.context=context;
        signalArrBeenList=new ArrayList<>();
    }

    @Override
    public int getGroupCount() {
        return signalArrBeenList.size();
    }

    @Override
    public int getChildrenCount(int i) {
        return signalArrBeenList.get(i).getSignals().size();
    }

    @Override
    public Object getGroup(int i) {
        return signalArrBeenList.get(i);
    }

    @Override
    public Object getChild(int i, int i1) {
        return signalArrBeenList.get(i).getSignals().get(i1);
    }

    @Override
    public long getGroupId(int i) {
        return i;
    }

    @Override
    public long getChildId(int i, int i1) {
        return i1;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {
        GroupViewHolder groupViewHolder;
        if (view==null){
            view= LayoutInflater.from(context).inflate(R.layout.list_interval_have_device_group,viewGroup,false);
            groupViewHolder=new GroupViewHolder();
            groupViewHolder.tvCurrencyDevName= (TextView) view.findViewById(R.id.tvCurrencyDevName);
            view.setTag(groupViewHolder);
        }else{
            groupViewHolder= (GroupViewHolder) view.getTag();
        }

        groupViewHolder.tvCurrencyDevName.setText(signalArrBeenList.get(i).getCurrencyDevName());

        return view;
    }

    @Override
    public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
        ChildViewHolder childViewHolder;
        if (view==null){
            view=LayoutInflater.from(context).inflate(R.layout.list_interval_have_device_child,viewGroup,false);
            childViewHolder=new ChildViewHolder();
            childViewHolder.tvSignalHint= (TextView) view.findViewById(R.id.tvSignalHint);
            childViewHolder.tvSignalName= (TextView) view.findViewById(R.id.tvSignalName);
            view.setTag(childViewHolder);
        }else{
            childViewHolder= (ChildViewHolder) view.getTag();
        }

        childViewHolder.tvSignalHint.setText(context.getResources().getString(R.string.signal_location)+(i1+1));
        childViewHolder.tvSignalName.setText(signalArrBeenList.get(i).getSignals().get(i1));

        return view;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return false;
    }

    static class GroupViewHolder{
        public TextView tvCurrencyDevName;
    }

    static class ChildViewHolder{
        public TextView tvSignalHint,tvSignalName;
    }

    public void refreshData(ArrayList<DevDetailInfo.SignalArrBean> newData){
        if (newData!=null){
            signalArrBeenList.clear();
            signalArrBeenList.addAll(newData);
        }
        notifyDataSetChanged();
    }
}
