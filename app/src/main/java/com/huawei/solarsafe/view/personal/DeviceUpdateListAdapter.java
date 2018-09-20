package com.huawei.solarsafe.view.personal;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.huawei.solarsafe.R;
import com.huawei.solarsafe.bean.update.UpdateListInfo;
import com.huawei.solarsafe.utils.Utils;

import java.util.List;

/**
 * Created by P00517 on 2017/4/10.
 */

public class DeviceUpdateListAdapter extends BaseAdapter {
    private List<UpdateListInfo> list;
    private Context context;

    public DeviceUpdateListAdapter(List<UpdateListInfo> list, Context context) {
        this.list = list;
        this.context = context;
    }

    public void setData(List<UpdateListInfo> list){
        this.list = list;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return list == null ? 0 : list.size();
    }

    @Override
    public UpdateListInfo getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.layout_item_device_update, null);
            holder = new ViewHolder();
            holder.tvDevName = (TextView) convertView.findViewById(R.id.tv_dev_name);
            holder.tvTargetVersion = (TextView) convertView.findViewById(R.id.tv_target_version);
            holder.tvUpgradeTime = (TextView) convertView.findViewById(R.id.tv_upgrade_time);
            holder.tvUpgradeResult = (TextView) convertView.findViewById(R.id.tv_upgrade_result);
            holder.tvStationName = (TextView) convertView.findViewById(R.id.tv_station_name);
            holder.tvLocalVersion = (TextView) convertView.findViewById(R.id.tv_local_version);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        UpdateListInfo info = list.get(position);
        holder.tvDevName.setText(info.getDevName());
        holder.tvStationName.setText(info.getsName());
        holder.tvLocalVersion.setText(context.getString(R.string.cur_ver) + info.getSourceVersion());
        holder.tvTargetVersion.setText(context.getString(R.string.todo_ver) + info.getTargetVersion());
        //格式化日期
        long time = info.getUpgradeTime();
        holder.tvUpgradeTime.setText(Utils.getFormatTimeYYMMDD2(time));

        //升级结果
        int upgradeResult = info.getUpgradeResult();
        switch (upgradeResult) {
            case 0://未开始升级
                holder.tvUpgradeResult.setText(R.string.wait_for_reply);
                holder.tvUpgradeResult.setTextColor(context.getResources().getColor(android.R.color.holo_orange_dark));

                break;
            case 1://成功
                holder.tvUpgradeResult.setText(R.string.upgrade_success);
                holder.tvUpgradeResult.setTextColor(context.getResources().getColor(android.R.color.holo_green_dark));

                break;
            case 2://失败
                holder.tvUpgradeResult.setText(R.string.upgrade_fail);
                holder.tvUpgradeResult.setTextColor(context.getResources().getColor(android.R.color.darker_gray));

                break;
            case 3://放弃
                holder.tvUpgradeResult.setText(R.string.aborted);
                holder.tvUpgradeResult.setTextColor(context.getResources().getColor(android.R.color.holo_red_light));

                break;
            case 4://升级中
                holder.tvUpgradeResult.setText(R.string.upgrading);
                holder.tvUpgradeResult.setTextColor(context.getResources().getColor(android.R.color.holo_green_light));

                break;
            case 5://超时
                holder.tvUpgradeResult.setText(R.string.expired);
                holder.tvUpgradeResult.setTextColor(context.getResources().getColor(android.R.color.holo_purple));

                break;
        }
        return convertView;
    }


    class ViewHolder {
        TextView tvStationName;
        TextView tvDevName;
        TextView tvTargetVersion;
        TextView tvUpgradeTime;
        TextView tvLocalVersion;
        TextView tvUpgradeResult;
    }
}
