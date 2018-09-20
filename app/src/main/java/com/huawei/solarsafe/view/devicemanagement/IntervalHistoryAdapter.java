package com.huawei.solarsafe.view.devicemanagement;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;

import com.huawei.solarsafe.R;
import com.huawei.solarsafe.bean.report.Indicator;

import java.util.LinkedList;

/**
 * Created by P00507
 * on 2018/3/7.
 */

public class IntervalHistoryAdapter extends BaseAdapter {
    private LinkedList<Indicator> stringList;
    private Context context;
    private int checkBosPosition = Integer.MIN_VALUE;
    private IntervalUnitString intervalUnitString;

    public IntervalHistoryAdapter(Context context, IntervalUnitString intervalUnitString) {
        this.context = context;
        this.intervalUnitString = intervalUnitString;
        stringList = new LinkedList<>();
    }

    public void setStringList(LinkedList<Indicator> stringList) {
        if (stringList != null && stringList.size() != 0) {
            this.stringList.clear();
            this.stringList.addAll(stringList);
            notifyDataSetChanged();
        }
    }

    @Override
    public int getCount() {
        return stringList.size();
    }

    @Override
    public Object getItem(int position) {
        return stringList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final IntervalViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new IntervalViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.interval_history_signal_unit, null);
            viewHolder.tvItemSignalNuit = (CheckBox) convertView.findViewById(R.id.tv_item_signal_nuit);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (IntervalViewHolder) convertView.getTag();
        }
        viewHolder.tvItemSignalNuit.setText(stringList.get(position).getItem());

        if (stringList.get(position).isChecked()) {
            viewHolder.tvItemSignalNuit.setChecked(true);
            checkBosPosition = position;
        } else {
            viewHolder.tvItemSignalNuit.setChecked(false);

        }
        viewHolder.tvItemSignalNuit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkBosPosition != position) {
                    for (int i = 0; i < stringList.size(); i++) {
                        if (i == position) {
                            stringList.get(i).setChecked(true);
                        } else {
                            stringList.get(i).setChecked(false);
                        }
                    }
                    checkBosPosition = position;
                    notifyDataSetChanged();
                    intervalUnitString.getUnitString(stringList.get(position).getItem(), position);
                } else {
                    viewHolder.tvItemSignalNuit.setChecked(true);
                }
            }
        });
        return convertView;
    }

    public class IntervalViewHolder {
        CheckBox tvItemSignalNuit;
    }

    /**
     * 回调接口
     */
    public interface IntervalUnitString {
        void getUnitString(String name, int position);
    }
}
