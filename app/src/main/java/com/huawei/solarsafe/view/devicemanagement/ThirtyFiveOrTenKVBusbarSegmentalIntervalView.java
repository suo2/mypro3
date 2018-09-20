package com.huawei.solarsafe.view.devicemanagement;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.huawei.solarsafe.R;
import com.huawei.solarsafe.bean.device.WiringDataBean;

import java.util.HashMap;
import java.util.List;

import static com.huawei.solarsafe.bean.device.DevTypeConstant.VolLevel_35KV;

/**
 * Created by P00708 on 2018/3/6.
 * 35/10KV母线/分段间隔电路图
 */

public class ThirtyFiveOrTenKVBusbarSegmentalIntervalView extends LinearLayout{
    private Context context;
    private View line1;
    private View line2;
    private View line3;
    private View line4;
    private View line5;
    private View line6;
    private View line7;
    private View line8;
    private TextView tx1;
    private TextView tx2;
    private ImageView handCarState1;
    private ImageView circuitBreakerState;
    private ImageView secondHandCarState;
    private TextView tx3;
    public ThirtyFiveOrTenKVBusbarSegmentalIntervalView(Context context){
        super(context);
        this.context = context;
        initView();
    }

    public ThirtyFiveOrTenKVBusbarSegmentalIntervalView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initView();
    }
    private void initView(){
        LayoutInflater.from(context).inflate(R.layout.thirty_five_or_ten_kv_busbar_segmental_interval_view_layout,this,true);
        line1 = findViewById(R.id.line1);
        line2 = findViewById(R.id.line2);
        line3 = findViewById(R.id.line3);
        line4 = findViewById(R.id.line4);
        line5 = findViewById(R.id.line5);
        line6 = findViewById(R.id.line6);
        line7 = findViewById(R.id.line7);
        line8 = findViewById(R.id.line8);
        tx1 = (TextView) findViewById(R.id.tx_15900);
        tx2 = (TextView) findViewById(R.id.tx_hand_car);
        tx3 = (TextView) findViewById(R.id.tx_15902);
        handCarState1 = (ImageView) findViewById(R.id.hand_car_state);
        circuitBreakerState = (ImageView) findViewById(R.id.circuit_breaker_state);
        secondHandCarState = (ImageView) findViewById(R.id.second_hand_car_state);
    }
    public void setData(List<WiringDataBean> dataList, String volLevelValue, String volLevelSecondValue) {

        HashMap<Integer, WiringDataBean> data = new HashMap<>();
        if (dataList != null && dataList.size() > 0) {
            for (WiringDataBean dataBean : dataList) {
                data.put(dataBean.getIndex(), dataBean);
            }
        }
        if(data.containsKey(1)){  //判断电路图1号位置是否有数据
            WiringDataBean data1 = data.get(1);
            tx1.setText(data1.getDispatchNumber()+"");
            if(data1.getValue() != null && data1.getValue().equals("1")){
                circuitBreakerState.setImageResource(R.drawable.circuit_breaker_close);
            }else if(data1.getValue() != null && data1.getValue().equals("0")){
                circuitBreakerState.setImageResource(R.drawable.circuit_breaker_open);
            }else{
                circuitBreakerState.setImageResource(R.drawable.circuit_breaker_error);
            }
        }else{
            tx1.setText("");
            circuitBreakerState.setImageResource(R.drawable.circuit_breaker_error);
        }
        if(data.containsKey(2)){  //判断电路图1号位置是否有数据
            WiringDataBean data2 = data.get(2);
            tx2.setText(data2.getDispatchNumber()+"");
            if(data2.getValue().equals("1")){
                handCarState1.setImageResource(R.drawable.one_hand_car_divide);
            }else if(data2.getValue().equals("0")){
                handCarState1.setImageResource(R.drawable.two_hand_car_divide);
            }else{
                handCarState1.setImageResource(R.drawable.hand_car_error);
            }
        }else{
            handCarState1.setImageResource(R.drawable.hand_car_error);
            tx2.setText("");
        }


        if(data.containsKey(3)){
            WiringDataBean data3 = data.get(3);
            tx3.setText(data3.getDispatchNumber()+"");
            if(data3.getValue().equals("1")){
                secondHandCarState.setImageResource(R.drawable.one_hand_car_divide);
            }else if(data3.getValue().equals("0")){
                secondHandCarState.setImageResource(R.drawable.two_hand_car_divide);
            }else{
                secondHandCarState.setImageResource(R.drawable.hand_car_error);
            }
        }else{
            secondHandCarState.setImageResource(R.drawable.hand_car_error);
            tx3.setText("");
        }
        if(volLevelValue.equals(VolLevel_35KV)){
            updateAllLineColor(context.getResources().getColor(R.color.kv_35));
        }else{
            updateAllLineColor(context.getResources().getColor(R.color.kv_10));
        }

    }
    private void updateAllLineColor(int color){
        line1.setBackgroundColor(color);
        line2.setBackgroundColor(color);
        line3.setBackgroundColor(color);
        line4.setBackgroundColor(color);
        line5.setBackgroundColor(color);
        line6.setBackgroundColor(color);
        line7.setBackgroundColor(color);
        line8.setBackgroundColor(color);
    }


}
