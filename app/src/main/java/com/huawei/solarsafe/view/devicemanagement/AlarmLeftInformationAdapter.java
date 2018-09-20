package com.huawei.solarsafe.view.devicemanagement;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.huawei.solarsafe.R;
import com.huawei.solarsafe.bean.device.DevAlarmInfo;

import java.util.List;

/**
 * Created by P00708 on 2018/3/5.
 */

public class AlarmLeftInformationAdapter extends BaseAdapter {
    private Context context;
    private List<DevAlarmInfo.ListBean> list;
    public AlarmLeftInformationAdapter(Context context, List<DevAlarmInfo.ListBean> list){
        this.context = context;
        this.list = list;
    }
    @Override
    public int getCount() {
        return this.list == null ? 0:this.list.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
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
            view = LayoutInflater.from(context).inflate(R.layout.alarm_left_information_adapter_layout,null);
            holder.contentBg = (LinearLayout) view.findViewById(R.id.content_list);
            holder.stationName = (TextView) view.findViewById(R.id.tv_station_name);
            view.setTag(holder);
        }else{
            holder = (Holder) view.getTag();
        }
        if(list.get(i).getStationName() != null){
            holder.stationName.setText(list.get(i).getStationName());
        }else{
            holder.stationName.setText("");
        }
        if(i %2 == 0){
            holder.contentBg.setBackgroundResource(R.color.common_white);
        }else{
            holder.contentBg.setBackgroundResource(R.color.hui_white);
        }

        return view;
    }
    class Holder{

        LinearLayout contentBg;
        TextView stationName;
    }
}
