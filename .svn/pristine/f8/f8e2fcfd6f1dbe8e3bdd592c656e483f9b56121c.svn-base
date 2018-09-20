package com.huawei.solarsafe.view.homepage.station;


import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.huawei.solarsafe.R;
import com.huawei.solarsafe.bean.station.kpi.StationInfo;
import com.huawei.solarsafe.net.NetRequest;
import com.huawei.solarsafe.utils.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by P00322 on 2017/1/5.
 */
public class StationListAdapter extends BaseAdapter {
    private Context mContext;
    List<StationInfo> stationInfoList = new ArrayList<>();


    public StationListAdapter(Context context, List<StationInfo> stationInfos) {
        this.mContext = context;
        this.stationInfoList = stationInfos;
    }

    public void setDatas(List<StationInfo> datas) {
        this.stationInfoList = datas;
        notifyDataSetChanged();
    }

    public List<StationInfo> getData() {
        return stationInfoList;
    }

    @Override
    public int getCount() {
        return stationInfoList == null ? 0 : stationInfoList.size();
    }

    @Override
    public Object getItem(int position) {
        return stationInfoList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.adapter_station_list, null, false);
            viewHolder.stationName = (TextView) convertView.findViewById(R.id.tv_station_name);
            viewHolder.stationAddress = (TextView) convertView.findViewById(R.id.tv_station_address);
            viewHolder.currentPower = (TextView) convertView.findViewById(R.id.tv_power);
            viewHolder.installCapatity = (TextView) convertView.findViewById(R.id.tv_install_capacity);
            viewHolder.curDay = (TextView) convertView.findViewById(R.id.tv_update_time);
            viewHolder.simpleDraweeView = (SimpleDraweeView) convertView.findViewById(R.id.my_image_view);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        StationInfo stationInfo = stationInfoList.get(position);
        viewHolder.stationName.setText(stationInfo.getStationName());
        viewHolder.stationAddress.setText(stationInfo.getPlantAddr());
        viewHolder.currentPower.setText(Utils.handlePowerUnit(stationInfo.getCurrentPower()));
        viewHolder.installCapatity.setText(Utils.handlePowerUnitNew(stationInfo.getInstallCapacity()));
        viewHolder.curDay.setText(Utils.getUnitConversionkWhValue(stationInfo.getCurDay(),mContext));
        // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0001_F03_Android_S01  删除Android的demo账号    【修改人】：zhaoyufeng
        if ("".equals(stationInfo.getStationPic())) {
            viewHolder.simpleDraweeView.setVisibility(View.INVISIBLE);
        } else {
            viewHolder.simpleDraweeView.setVisibility(View.VISIBLE);
            String url = NetRequest.IP + "/fileManager/downloadCompleteInmage" + "?fileId=" + stationInfo.getStationPic() + "&serviceId=" + 1;
            // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0004_F01_Android_S02  敏感信息检查-日志中不能包含敏感信息    【修改人】：zhaoyufeng
                viewHolder.simpleDraweeView.setImageURI(Uri.parse(url));
        }
        return convertView;
    }

    private class ViewHolder {
        TextView stationName, stationAddress, currentPower, installCapatity, curDay;
        SimpleDraweeView simpleDraweeView;
    }
}
