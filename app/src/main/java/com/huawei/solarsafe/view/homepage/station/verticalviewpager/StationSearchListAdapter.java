package com.huawei.solarsafe.view.homepage.station.verticalviewpager;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.huawei.solarsafe.R;
import com.huawei.solarsafe.bean.Constant;
import com.huawei.solarsafe.bean.station.kpi.StationListItemDataBean;
import com.huawei.solarsafe.net.NetRequest;
import com.huawei.solarsafe.utils.ButtonUtils;
import com.huawei.solarsafe.utils.LocalData;
import com.huawei.solarsafe.utils.Utils;
import com.huawei.solarsafe.view.homepage.station.StationDetailActivity;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by P00784 on 2018/7/18.
 */

public class StationSearchListAdapter extends RecyclerView.Adapter<StationSearchListAdapter.StationHolder> {
    private Context mContext;
    private List<StationListItemDataBean> stationInfoList = new ArrayList<>();
    private DecimalFormatSymbols dfs;
    private DecimalFormat df2 = new DecimalFormat("0.00");
    private DecimalFormat df3 = new DecimalFormat("0.000");

    public List<String> rightsList;

    public StationSearchListAdapter(Context context, List data){
        this.mContext = context;
        this.stationInfoList = data;

        dfs=new DecimalFormatSymbols();
        dfs.setDecimalSeparator('.');
        df2.setDecimalFormatSymbols(dfs);
        df3.setDecimalFormatSymbols(dfs);

        String strRights = LocalData.getInstance().getRightString();
        rightsList = Utils.stringToList(strRights);
    }

    @Override
    public StationSearchListAdapter.StationHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.adapter_station_list_new, parent, false);
        StationSearchListAdapter.StationHolder holder = new StationSearchListAdapter.StationHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final StationSearchListAdapter.StationHolder viewHolder, final int position) {
        final StationListItemDataBean stationInfo = stationInfoList.get(position);
        if (stationInfo != null) {
            viewHolder.stationNameTv.setText(stationInfo.getStationName());
            viewHolder.stationAddrTv.setText(stationInfo.getStationAddr());
            if(TextUtils.isEmpty(stationInfo.getRealcapacity()) || stationInfo.getRealcapacity().toUpperCase().equals("NULL")){
                viewHolder.installCapacityTv.setText(mContext.getString(R.string.guihuarongliang)+df3.format(0)+
                        mContext.getString(R.string.power_unit_kwp));
            }else{
                viewHolder.installCapacityTv.setText(mContext.getString(R.string.guihuarongliang)+
                        castToMWpOrGWp(Double.parseDouble(stationInfo.getRealcapacity())));
            }
            if(TextUtils.isEmpty(stationInfo.getDaycapacity()) || stationInfo.getDaycapacity().toUpperCase().equals("NULL")){
                viewHolder.currentDayPowerTv.setText(mContext.getString(R.string.on_the_day_of_electricity)+
                        df2.format(0)+mContext.getString(R.string.power_unit_kwh));
            }else{
                viewHolder.currentDayPowerTv.setText(mContext.getString(R.string.on_the_day_of_electricity)+
                        castToMWhOrGWh(Double.parseDouble(stationInfo.getDaycapacity())));
            }
            if("1".equals(stationInfo.getState())){
                viewHolder.tipTv.setText(mContext.getString(R.string.disconnect));
                viewHolder.tipTv.setBackgroundResource(R.drawable.disconnect);
            }else if("2".equals(stationInfo.getState())){
                viewHolder.tipTv.setText(mContext.getString(R.string.breakdown));
                viewHolder.tipTv.setBackgroundResource(R.drawable.matter);
            }else if("3".equals(stationInfo.getState())){
                viewHolder.tipTv.setText(mContext.getString(R.string.onLine));
                viewHolder.tipTv.setBackgroundResource(R.drawable.health);
            }
            String url = NetRequest.IP + "/fileManager/downloadCompleteInmage" + "?fileId=" + stationInfo.getStationPic() + "&serviceId=" + 1;
            if (TextUtils.isEmpty(stationInfo.getStationPic())) {
                Uri uri = Uri.parse("res://com.hauwei.solar/" + R.drawable.single_station_bg);
                viewHolder.simpleDraweeView.setImageURI(uri);
            } else {
                viewHolder.simpleDraweeView.setImageURI(Uri.parse(url));
            }

            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!ButtonUtils.isFastDoubleClick(viewHolder.itemView.getId())) {
                        Intent intent = new Intent(mContext, StationDetailActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putString(Constant.STATION_CODE, stationInfo.getStationCode());
                        bundle.putString("title", stationInfo.getStationName());
                        intent.putExtra("b", bundle);
                        mContext.startActivity(intent);
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return stationInfoList == null ? 0 : stationInfoList.size();
    }

    class StationHolder extends RecyclerView.ViewHolder {
        TextView tipTv, stationNameTv, stationAddrTv, installCapacityTv, currentDayPowerTv;
        SimpleDraweeView simpleDraweeView;

        public StationHolder(View itemView) {
            super(itemView);
            tipTv = (TextView) itemView.findViewById(R.id.tip_txt);
            stationNameTv = (TextView) itemView.findViewById(R.id.station_name);
            stationAddrTv = (TextView) itemView.findViewById(R.id.station_addr);
            installCapacityTv = (TextView) itemView.findViewById(R.id.install_capacity);
            currentDayPowerTv = (TextView) itemView.findViewById(R.id.current_day_power);
            simpleDraweeView = (SimpleDraweeView) itemView.findViewById(R.id.my_image_view);
        }
    }

    /**
     * 将kwp转化为MWp或GWp
     */
    private String castToMWpOrGWp(double number){
        if(number >= 1000000){
            return df3.format(number / 1000000) + mContext.getString(R.string.power_unit_gwp);
        }else if(number >= 1000){
            return df3.format(number / 1000) + mContext.getString(R.string.power_unit_mwp);
        }else{
            return df3.format(number) + mContext.getString(R.string.power_unit_kwp);
        }
    }

    /**
     * 将kwh转化为MWh或GWh
     */
    private String castToMWhOrGWh(double number){
        if(number >= 1000000){
            return df2.format(number / 1000000) + mContext.getString(R.string.power_unit_gwh);
        }else if(number >= 1000){
            return df2.format(number / 1000) + mContext.getString(R.string.power_unit_mwh);
        }else{
            return df2.format(number) + mContext.getString(R.string.power_unit_kwh);
        }
    }

}
