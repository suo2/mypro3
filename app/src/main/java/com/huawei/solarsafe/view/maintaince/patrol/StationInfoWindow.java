package com.huawei.solarsafe.view.maintaince.patrol;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.amap.api.maps.model.LatLng;
import com.huawei.solarsafe.MyApplication;
import com.huawei.solarsafe.R;
import com.huawei.solarsafe.bean.patrol.PatrolStationInfo;
import com.huawei.solarsafe.utils.ToastUtil;
import com.huawei.solarsafe.utils.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by P00319 on 2017/3/3.
 */

public class StationInfoWindow implements View.OnClickListener {
    private Context mContext;
    private AMapFragment aMapFragment;

    private RecyclerView rvStationInfo;

    private List<PatrolStationInfo> infos = new ArrayList<>();
    private ArrayList<com.google.android.gms.maps.model.LatLng> gMapLatLngs=new ArrayList<>();
    private List<LatLng> latLngs = new ArrayList<>();

    private StationInfoAdapter adapter;
    boolean isGMap=false;

    /**
     * PopupWindow菜单
     */
    private PopupWindow mPopupWindow;

    public StationInfoWindow(Context context, AMapFragment aMapFragment) {
        this.mContext = context;
        this.aMapFragment = aMapFragment;
        initPopupWindw();
    }

    private void initPopupWindw() {
        View view;
        view = LayoutInflater.from(mContext).inflate(R.layout.popwindow_patrol_station_info, null);
        mPopupWindow = new PopupWindow(view, ActionBar.LayoutParams.MATCH_PARENT, Utils.dp2Px(mContext, 270), true);
        mPopupWindow.setOutsideTouchable(true);
        mPopupWindow.setFocusable(false);
        rvStationInfo = (RecyclerView) view.findViewById(R.id.rv_station_info);
        rvStationInfo.setLayoutManager(new LinearLayoutManager(mContext));
        adapter = new StationInfoAdapter();
        rvStationInfo.setAdapter(adapter);

    }

    public void addData(PatrolStationInfo info, com.google.android.gms.maps.model.LatLng latLng) {
        this.infos.add(info);
        adapter.notifyDataSetChanged();
        this.gMapLatLngs.add(latLng);
    }

    public void addData(PatrolStationInfo info, LatLng latLng) {
        this.infos.add(info);
        adapter.notifyDataSetChanged();
        this.latLngs.add(latLng);
    }


    public void show(View view) {
        mPopupWindow.showAtLocation(view, Gravity.BOTTOM, 0, 0);
    }

    /**
     * 退出popupwindow
     */
    public void dissmiss() {
        if (mPopupWindow != null && mPopupWindow.isShowing()) {
            mPopupWindow.dismiss();
        }
    }

    /**
     * popupwindow是否正在显示
     */
    public boolean isShowing() {
        if (mPopupWindow != null) {
            return mPopupWindow.isShowing();
        }
        return false;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.goto_station:

                break;
        }
    }

    public void clear() {
        infos.clear();
        adapter.notifyDataSetChanged();
    }

    public class StationInfoAdapter extends RecyclerView.Adapter<StationInfoViewHolder> implements View.OnClickListener {

        @Override
        public StationInfoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new StationInfoViewHolder(LayoutInflater.from(MyApplication.getContext()).inflate(R.layout.item_patrol_map_station_info, parent, false));
        }

        @Override
        public void onBindViewHolder(StationInfoViewHolder holder, int position) {
            PatrolStationInfo info = infos.get(position);
            for (int i = 0; i<holder.params.getChildCount(); i++) {
                ViewGroup view = (ViewGroup) holder.params.getChildAt(i);
                TextView tvTitle = (TextView) view.getChildAt(0);
                tvTitle.setTextColor(Color.BLACK);
                TextView tvInfo = (TextView) view.getChildAt(1);

                switch (i) {
                    case 0:
                        tvTitle.setText(mContext.getString(R.string.power_station_name));
                        tvInfo.setText(info.getStationName() == null ? "" : info.getStationName());
                        break;
                    case 1:
                        tvTitle.setText(mContext.getString(R.string.staion_location));
                        tvInfo.setText(info.getStationAddr() == null ? "" : info.getStationAddr());
                        break;
                    case 2:
                        tvTitle.setText(mContext.getString(R.string.capatity_no_colon));
                        tvInfo.setText(info.getStationCapacity() == 0.0 ? "" : info.getStationCapacity() + "kWp");
                        break;
                    case 3:
                        tvTitle.setText(mContext.getString(R.string.the_current_power));
                        tvInfo.setText(info.getCurActiviPower() == 0.0 ? "" : info.getCurActiviPower() + "kW");
                        break;
                    case 4:
                        tvTitle.setText(mContext.getString(R.string.yield_today));
                        tvInfo.setText(info.getDailyCapacity() == 0.0 ? "" : info.getDailyCapacity() + "kWh");
                        break;
                    case 5:
                        tvTitle.setText(R.string.suggested_contact_person);
                        tvInfo.setText(info.getContactUser() == null ? "" : info.getContactUser());
                        break;
                }
            }
            holder.go.setOnClickListener(this);
            holder.go.setTag(position);
            holder.title.setText(R.string.station_detail);
        }


        @Override
        public int getItemCount() {
            return infos.size();
        }

        @Override
        public void onClick(View v) {
            int p = (int) v.getTag();

            if (isGMap){

                //跳转Google地图APP导航
                Uri gmmIntentUri = Uri.parse("google.navigation:q="+gMapLatLngs.get(p).latitude+","+gMapLatLngs.get(p).longitude);
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");

                //判断是否安装了Google地图
                if (mapIntent.resolveActivity(mContext.getPackageManager())!=null){
                    mContext.startActivity(mapIntent);
                }else{
                    ToastUtil.showMessage(mContext.getResources().getString(R.string.google_map_uninstalled));
                }
            }else{

                if(aMapFragment.mLatLng == null){
                    ToastUtil.showMessage(MyApplication.getContext().getString(R.string.location_fail_again));
                    return;
                }

                if (Utils.isInstalledAMap(mContext)){
                    StringBuffer sb = new StringBuffer("androidamap://route?sourceApplication=").append("amap");

                    sb.append("&dlat=")
                            .append(latLngs.get(p).latitude)
                            .append("&dlon=")
                            .append(latLngs.get(p).longitude)
                            .append("&dev=")
                            .append(0)
                            .append("&t=")
                            .append(0);

                    Intent intent = new Intent("android.intent.action.VIEW", android.net.Uri.parse(sb.toString()));
                    intent.setPackage("com.autonavi.minimap");
                    mContext.startActivity(intent);
                }else{
                    ToastUtil.showMessage(mContext.getResources().getString(R.string.a_map_uninstalled));
                }
            }
        }
    }

    public static class StationInfoViewHolder extends RecyclerView.ViewHolder {

        public LinearLayout params;
        public View go;
        public TextView title;


        public StationInfoViewHolder(View view) {
            super(view);
            params = (LinearLayout) view.findViewById(R.id.params);
            go = view.findViewById(R.id.goto_station);
            title = (TextView) view.findViewById(R.id.title);
        }
    }

    public void setGMap(boolean b){
        this.isGMap=b;
    }

    public int getInfoSize(){

        return infos == null ? 0:infos.size();
    }
}
