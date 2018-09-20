package com.huawei.solarsafe.view.devicemanagement;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.huawei.solarsafe.R;
import com.huawei.solarsafe.bean.device.YhqErrorListBean;
import com.huawei.solarsafe.utils.Utils;

import java.util.List;

/**
 * Created by P00517 on 2017/8/16.
 */

public class YhqErrorListAdapter extends BaseAdapter {
    private Context context;
    private List<YhqErrorListBean.DataBean.ListBean> listBeen;

    public YhqErrorListAdapter(Context context, List<YhqErrorListBean.DataBean.ListBean> listBeen) {
        this.context = context;
        this.listBeen = listBeen;
    }

    @Override
    public int getCount() {
        return listBeen == null ? 0:listBeen.size();
    }

    @Override
    public Object getItem(int i) {
        return listBeen.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if (view == null){
            holder = new ViewHolder();
            view = View.inflate(context, R.layout.item_yhq_error_list,null);
            holder.tvErrorStatus = (TextView) view.findViewById(R.id.tv_yhq_error_status);
            holder.tvYhqName = (TextView) view.findViewById(R.id.tv_yhq_yhq_name);
            holder.tvErrorName = (TextView) view.findViewById(R.id.tv_yhq_error_name);
            holder.tvStationName = (TextView) view.findViewById(R.id.tv_yhq_station_name);
            holder.tvStartTime = (TextView) view.findViewById(R.id.tv_yhq_start_time);
            view.setTag(holder);
        }else {
            holder = (ViewHolder) view.getTag();
        }
        YhqErrorListBean.DataBean.ListBean bean = listBeen.get(i);
        holder.tvStationName.setText(bean.getStationName());
        holder.tvYhqName.setText("1." + bean.getOptName().replace("N","."));
        String faultName = bean.getFaultName();
        if (!TextUtils.isEmpty(faultName)){
            if (faultName.contains("inputOverVol")){
                holder.tvErrorName.setText(R.string.inputOverVol);
            }else if (faultName.contains("outputOverVol")){
                holder.tvErrorName.setText(R.string.outputOverVol);
            }else if (faultName.contains("overTemperature")){
                holder.tvErrorName.setText(R.string.overTemperature);
            }else if (faultName.contains("outputShorCircuit")){
                holder.tvErrorName.setText(R.string.outputShorCircuit);
            }else if (faultName.contains("EEPROMFault")){
                holder.tvErrorName.setText(R.string.EEPROMFault);
            }else if (faultName.contains("hardwareFault")){
                holder.tvErrorName.setText(R.string.hardwareFault);
            }else if (faultName.contains("heartbeatShutdown")){
                holder.tvErrorName.setText(R.string.heartbeatShutdown);
            }
        }
        int status = bean.getStatus();
        if (status == 0){
            holder.tvErrorStatus.setText(context.getString(R.string.activation));
        }else if (status == 1){
            holder.tvErrorStatus.setText(R.string.re);
        }
        holder.tvStartTime.setText(Utils.getFormatTimeYYMMDDHHmmss2(bean.getRaisedDate()));
        return view;
    }

    class ViewHolder{
        TextView tvStationName;
        TextView tvYhqName;
        TextView tvErrorName;
        TextView tvErrorStatus;
        TextView tvStartTime;

    }
}
