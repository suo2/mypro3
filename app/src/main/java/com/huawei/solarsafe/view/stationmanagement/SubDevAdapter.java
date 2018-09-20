package com.huawei.solarsafe.view.stationmanagement;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.huawei.solarsafe.R;
import com.huawei.solarsafe.bean.device.DevTypeConstant;
import com.huawei.solarsafe.bean.stationmagagement.SubDev;

/**
 * Created by P00229 on 2017/5/16.
 */
public class SubDevAdapter extends BaseAdapter {
    private Context context;
    private SubDev[] subDev;

    public SubDevAdapter(Context context, SubDev[] subDev) {
        this.context = context;
        this.subDev = subDev;
    }

    @Override
    public int getCount() {
        return subDev == null ? 0 : subDev.length;
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
        ViewHolder viewHolder ;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.sub_dev_item, null);
            viewHolder = new ViewHolder();
            viewHolder.name = (TextView) convertView.findViewById(R.id.tv_subdev_name);
            viewHolder.esn = (TextView) convertView.findViewById(R.id.tv_subdev_esn);
            viewHolder.bb = (TextView) convertView.findViewById(R.id.tv_bb_no);
            viewHolder.type = (TextView) convertView.findViewById(R.id.tv_device_type);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.name.setText(subDev[position].getBusiName());
        viewHolder.esn.setText(subDev[position].getEsnCode());
        viewHolder.bb.setText(subDev[position].getModelVersionCode());
        viewHolder.type.setText(DevTypeConstant.getDevTypeMap(context).get(subDev[position].getDevTypeId()));
        return convertView;
    }

    class ViewHolder {
        TextView name;
        TextView esn;
        TextView bb;
        TextView type;
    }
}
