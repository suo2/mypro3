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
 * Created by p00507
 * on 2018/3/9.
 */
public class IntervalListAdapter extends BaseAdapter {
    private Context context;
    private LinkedList<Indicator> signalString;
    private int num = 0;
    private IntervalHistoryDataPopupWindow popupWindow;

    public IntervalListAdapter(Context context, IntervalHistoryDataPopupWindow popupWindow) {
        this.context = context;
        this.popupWindow=popupWindow;
        signalString = new LinkedList<>();
    }

    public void setSignalString(LinkedList<Indicator> signalString) {
        if(signalString != null && signalString.size() != 0){
            this.signalString.clear();
            this.signalString.addAll(signalString);
            num = 0;
            if (signalString.size()>6){
                popupWindow.showMoreArrow(true);
            }else{
                popupWindow.showMoreArrow(false);
            }

            notifyDataSetChanged();
            getCheckedNum(signalString);
        }
    }

    public LinkedList<Indicator> getSignalString() {
        return signalString;
    }

    private void getCheckedNum(LinkedList<Indicator> signalString) {
        for (int i = 0; i < signalString.size(); i++) {
            if(signalString.get(i).isChecked()){
                num ++;
            }
        }
    }

    @Override
    public int getCount() {
        return signalString.size();
    }

    @Override
    public Object getItem(int position) {
        return signalString.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;
        if(convertView == null){
            viewHolder = new ViewHolder();
            convertView= LayoutInflater.from(context).inflate(R.layout.report_fragment_spinner_item,null);
            viewHolder.itemCheckBox = (CheckBox) convertView.findViewById(R.id.report_ch);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.itemCheckBox.setText(signalString.get(position).getItem());
        if(signalString.get(position).isChecked()){
            viewHolder.itemCheckBox.setChecked(true);
        }else {
            viewHolder.itemCheckBox.setChecked(false);
        }
        viewHolder.itemCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(viewHolder.itemCheckBox.isChecked()){
                    if(num < 5){
                        num ++;
                        signalString.get(position).setChecked(true);
                    }else {
                        signalString.get(position).setChecked(false);
                        viewHolder.itemCheckBox.setChecked(false);
                    }
                }else {
                    num --;
                    signalString.get(position).setChecked(false);
                }
            }
        });
        //解决上下滑动勾选错乱的问题
        if (signalString.get(position).isChecked()) {
            viewHolder.itemCheckBox.setChecked(true);
        } else {
            viewHolder.itemCheckBox.setChecked(false);
        }
        return convertView;
    }
    class ViewHolder{
        CheckBox itemCheckBox;
    }
}
