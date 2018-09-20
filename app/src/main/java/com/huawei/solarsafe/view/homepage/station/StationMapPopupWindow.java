package com.huawei.solarsafe.view.homepage.station;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.huawei.solarsafe.R;
import com.huawei.solarsafe.bean.station.kpi.StationInfo;
import com.huawei.solarsafe.utils.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by p00507
 * on 2017/11/10.
 */

public class StationMapPopupWindow extends PopupWindow {
    private Context context;
    private ListView listView;
    private LinearLayout llPopwindowTop;
    List<StationInfo> stationInfoList = new ArrayList<>();
    private StationListAdapter adapter;
    public StationMapPopupWindow(Context context, AdapterView.OnItemClickListener listener) {
        super(context);
        this.context = context;
        View contentView = LayoutInflater.from(context).inflate(R.layout.station_popupwindow, null);
        listView = (ListView) contentView.findViewById(R.id.pop_station_list);
        llPopwindowTop = (LinearLayout) contentView.findViewById(R.id.ll_popwindow_top);
        llPopwindowTop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismissPopupWindow();
            }
        });
        adapter = new StationListAdapter(context, stationInfoList);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(listener);

        //设置PopupWindow的View
        this.setContentView(contentView);
        //设置PopupWindow弹出窗体可点击
        this.setFocusable(true);
        //设置SelectPicPopupWindow弹出窗体动画效果
        this.setAnimationStyle(R.style.pop_animation);
        //实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0xb0000000);
        //设置SelectPicPopupWindow弹出窗体的背景
        this.setBackgroundDrawable(dw);
    }
    public void setDatas(List<StationInfo> datas) {
        //设置PopupWindow弹出窗体的宽
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        //设置PopupWindow弹出窗体的高
        if(datas.size() == 1){
            this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        }else if(datas.size() > 1){
            this.setHeight(Utils.dp2Px(context, 260));
        }
        this.stationInfoList = datas;
        adapter.setDatas(datas);
    }
    private void dismissPopupWindow(){
        dismiss();
    }

    public StationListAdapter getAdapter() {
        return adapter;
    }

    public void setAdapter(StationListAdapter adapter) {
        this.adapter = adapter;
    }
}
