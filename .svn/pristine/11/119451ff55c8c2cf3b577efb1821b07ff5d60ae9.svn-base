package com.huawei.solarsafe.view.devicemanagement;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.huawei.solarsafe.R;
import com.huawei.solarsafe.bean.device.HouseHoldResultBean;

import java.util.ArrayList;

/**
 * Created by p00229 on 2017/7/6.
 */

public class HouseHoldSetResultAdpater extends BaseAdapter {
    private ArrayList<HouseHoldResultBean> houseHoldResultBeanArrayList;
    private Context context;

    public HouseHoldSetResultAdpater(ArrayList<HouseHoldResultBean> houseHoldResultBeanArrayList, Context context) {
        this.houseHoldResultBeanArrayList = houseHoldResultBeanArrayList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return houseHoldResultBeanArrayList == null ? 0 : houseHoldResultBeanArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.set_result_item, null);
            viewHolder.tvName = (TextView) convertView.findViewById(R.id.param_name);
            viewHolder.tvSetResult = (TextView) convertView.findViewById(R.id.result);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        HouseHoldResultBean houseHoldResultBean = houseHoldResultBeanArrayList.get(position);
        viewHolder.tvName.setText(houseHoldResultBean.getParamName());
        if (houseHoldResultBean.isSuccess()) {
            viewHolder.tvSetResult.setText(R.string.success);
            viewHolder.tvSetResult.setTextColor(context.getResources().getColor(R.color.color_result_green));
        } else {
            viewHolder.tvSetResult.setText(R.string.fail);
            viewHolder.tvSetResult.setTextColor(context.getResources().getColor(R.color.red));
        }
        return convertView;
    }

    class ViewHolder {
        TextView tvName;
        TextView tvSetResult;
    }
}
