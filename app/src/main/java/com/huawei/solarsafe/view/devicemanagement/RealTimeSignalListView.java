package com.huawei.solarsafe.view.devicemanagement;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.huawei.solarsafe.R;
import com.huawei.solarsafe.bean.device.CurrencySignalDataInfo;

import java.util.List;

/**
 * Created by P00708 on 2018/3/7.
 * 升压站实时信息信号量列表
 */

public class RealTimeSignalListView extends LinearLayout implements View.OnClickListener{

    private Context context;
    private ImageView displayMore;
    private LinearLayout dataContentList;
    private boolean isDisplayMore = true;
    public RealTimeSignalListView(Context context) {
        super(context);
        this.context = context;
        initView();
    }
    public RealTimeSignalListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initView();
    }
    private void initView(){
        LayoutInflater.from(context).inflate(R.layout.real_time_signal_list_layout,this,true);
        displayMore = (ImageView) findViewById(R.id.more_data);
        dataContentList = (LinearLayout) findViewById(R.id.signal_list_content);
        displayMore.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(isDisplayMore){
            isDisplayMore = false;
            displayMore.setImageResource(R.drawable.down_arrow);
            dataContentList.setVisibility(GONE);
        }else{
            isDisplayMore = true;
            displayMore.setImageResource(R.drawable.up_arrow);
            if(dataContentList.getChildCount()>0){
                dataContentList.setVisibility(VISIBLE);
            }
        }
    }
    public void setData(List<CurrencySignalDataInfo> data){
        if(data == null || data.size() == 0){
            return;
        }
        dataContentList.setVisibility(VISIBLE);
        int count = (data.size()/2)+(data.size()%2);
        for(int i=0;i<count;i++){
            RealTimeSignalView realTimeSignalView = new RealTimeSignalView(context);
            CurrencySignalDataInfo data2;
            if(i*2+1>=data.size()){
                data2 = null;
            }else{
                data2 = data.get(i*2+1);
            }
            realTimeSignalView.setData(data.get(i*2),data2);
            dataContentList.addView(realTimeSignalView);
        }
    }
}
