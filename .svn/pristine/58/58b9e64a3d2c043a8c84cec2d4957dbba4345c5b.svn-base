package com.huawei.solarsafe.view.devicemanagement;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.huawei.solarsafe.R;
import com.huawei.solarsafe.bean.device.CurrencySignalDataInfo;

/**
 * Created by P00708 on 2018/3/7.
 * 升压站实时信息信号量列表内容
 */

public class RealTimeSignalView extends RelativeLayout{
    private Context context;
    private ImageView fristImageState;
    private TextView  fristTxValue;
    private ImageView secondImageState;
    private TextView  secondTxValue;
    private LinearLayout secondDataContent;
    public RealTimeSignalView(Context context){
        super(context);
        this.context = context;
        initView();

    }
    public RealTimeSignalView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initView();
    }
    private void initView(){
        LayoutInflater.from(context).inflate(R.layout.real_time_signal_view_layout,this,true);
        fristImageState = (ImageView) findViewById(R.id.first_image_state);
        fristTxValue = (TextView) findViewById(R.id.first_tx_value);
        secondImageState = (ImageView) findViewById(R.id.second_image_state);
        secondTxValue = (TextView) findViewById(R.id.second_tx_value);
        secondDataContent = (LinearLayout) findViewById(R.id.second_data_linear);
    }
    public void setData(CurrencySignalDataInfo data1,CurrencySignalDataInfo data2){
        if(data1 == null){
            return;
        }
        if(data2 == null){
            secondDataContent.setVisibility(INVISIBLE);
        }else{
            secondDataContent.setVisibility(VISIBLE);
            if(!TextUtils.isEmpty(data2.getSignalName())){
                secondTxValue.setText(data2.getSignalName());
            }
            if(!TextUtils.isEmpty(data2.getSignalValue())){
                if(data2.getSignalValue().equals("1")){
                    secondImageState.setImageResource(R.drawable.red_circle);
                }else{
                    secondImageState.setImageResource(R.drawable.green_circle);
                }
            }

        }
        if(!TextUtils.isEmpty(data1.getSignalName())){
            fristTxValue.setText(data1.getSignalName());
        }
        if(!TextUtils.isEmpty(data1.getSignalValue())){
            if(data1.getSignalValue().equals("1")){
                fristImageState.setImageResource(R.drawable.red_circle);
            }else{
                fristImageState.setImageResource(R.drawable.green_circle);
            }
        }



    }
}
