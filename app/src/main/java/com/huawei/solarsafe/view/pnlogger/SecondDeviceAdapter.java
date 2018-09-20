package com.huawei.solarsafe.view.pnlogger;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.huawei.solarsafe.R;
import com.huawei.solarsafe.bean.pnlogger.SecondInfo;

import java.util.List;

/**
 * Created by P00517 on 2017/5/8.
 */

public class SecondDeviceAdapter extends BaseAdapter {
    private List<SecondInfo> deviceBeens;
    private Context context;

    public SecondDeviceAdapter(List<SecondInfo> deviceBeens, Context context) {
        this.deviceBeens = deviceBeens;
        this.context = context;
    }

    @Override
    public int getCount() {
        return deviceBeens.size();
    }

    @Override
    public Object getItem(int position) {
        return deviceBeens.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        ViewHolder holder;
        if (convertView == null){
            convertView = View.inflate(context, R.layout.item_second_device,null);
            holder = new ViewHolder();
            holder.tvBusiName = (TextView) convertView.findViewById(R.id.tv_busiName);
            holder.tvEsnCode = (TextView) convertView.findViewById(R.id.tv_esnCode);
            holder.tvModelVersionCode = (TextView) convertView.findViewById(R.id.tv_modelVersionCode);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        SecondInfo secondInfo = deviceBeens.get(position);
        holder.tvBusiName.setText(secondInfo.getBusiName());
        holder.tvEsnCode.setText(secondInfo.getEsnCode());
        holder.tvModelVersionCode.setText(secondInfo.getModelVersionCode());
        return convertView;
    }

    class ViewHolder{
        TextView tvBusiName;
        TextView tvEsnCode;
        TextView tvModelVersionCode;
    }
}
