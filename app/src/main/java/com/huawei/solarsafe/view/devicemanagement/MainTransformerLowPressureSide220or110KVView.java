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

import static com.huawei.solarsafe.bean.device.DevTypeConstant.VolLevel_10KV;
import static com.huawei.solarsafe.bean.device.DevTypeConstant.VolLevel_110KV;
import static com.huawei.solarsafe.bean.device.DevTypeConstant.VolLevel_220KV;
import static com.huawei.solarsafe.bean.device.DevTypeConstant.VolLevel_35KV;

/**
 * Created by P00708 on 2018/3/6.
 * 220/110kv主变低压侧电路图
 */

public class MainTransformerLowPressureSide220or110KVView extends LinearLayout{
    private Context context;
    private View line1;
    private View line2;
    private View line3;
    private View line4;
    private ImageView twoCircleJt35110Img;
    private ImageView handCarState;
    private ImageView circuitBreakeState;
    private TextView tx15902;
    private TextView tx2;
    public MainTransformerLowPressureSide220or110KVView(Context context){
        super(context);
        this.context = context;
        initView();
    }
    public MainTransformerLowPressureSide220or110KVView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initView();
    }
    private void initView(){
        LayoutInflater.from(context).inflate(R.layout.main_transformer_low_pressure_side220or110kv_view_layout,this,true);
        line1 = findViewById(R.id.line1);
        line2 = findViewById(R.id.line2);
        line3 = findViewById(R.id.line3);
        line4 = findViewById(R.id.line4);
        twoCircleJt35110Img = (ImageView) findViewById(R.id.two_circle_jt35_110_img);
        handCarState = (ImageView) findViewById(R.id.hand_car_state);
        circuitBreakeState = (ImageView) findViewById(R.id.circuit_breaker_state);
        tx15902 = (TextView) findViewById(R.id.tx_15902);
        tx2 = (TextView) findViewById(R.id.tx_hand_car);
    }
    public void setData(List<WiringDataBean> dataList, String volLevelValue, String volLevelSecondValue){

        HashMap<Integer,WiringDataBean> data=new HashMap<>();
        if(dataList != null && dataList.size()>0){
            for(WiringDataBean dataBean:dataList){
                data.put(dataBean.getIndex(),dataBean);
            }
        }
        if(data.containsKey(1)) {//判断电路图1号位置是否有数据
            WiringDataBean data1 = data.get(1);
            tx15902.setText(data1.getDispatchNumber());
            if(data1.getValue() != null && data1.getValue().equals("0")){
                circuitBreakeState.setImageResource(R.drawable.circuit_breaker_open);
            }else if(data1.getValue() != null && data1.getValue().equals("1")){
                circuitBreakeState.setImageResource(R.drawable.circuit_breaker_close);
            }else{
                circuitBreakeState.setImageResource(R.drawable.circuit_breaker_error);
            }
        }else{
            circuitBreakeState.setImageResource(R.drawable.circuit_breaker_error);
            tx15902.setText("");
        }
        if(data.containsKey(2)) {
            WiringDataBean data2 = data.get(2);
            tx2.setText(data2.getDispatchNumber());
            if(data2.getValue().equals("0")){
                handCarState.setImageResource(R.drawable.two_hand_car_divide);
            }else if(data2.getValue().equals("1")){
                handCarState.setImageResource(R.drawable.one_hand_car_divide);
            }else{
                handCarState.setImageResource(R.drawable.hand_car_error);
            }
        }else{
            handCarState.setImageResource(R.drawable.hand_car_error);
            tx2.setText("");
        }


        if(volLevelValue.equals(VolLevel_220KV)){
            if(volLevelSecondValue.equals(VolLevel_35KV)){
                twoCircleJt35110Img.setImageResource(R.drawable.two_circle_jt35_220);
                line1.setBackgroundResource(R.color.kv_220);
                line2.setBackgroundResource(R.color.kv_35);
                line3.setBackgroundResource(R.color.kv_35);
                line4.setBackgroundResource(R.color.kv_35);

            }else if(volLevelSecondValue.equals(VolLevel_10KV)){
                twoCircleJt35110Img.setImageResource(R.drawable.two_circle_jt10_220);
                line1.setBackgroundResource(R.color.kv_220);
                line2.setBackgroundResource(R.color.kv_10);
                line3.setBackgroundResource(R.color.kv_10);
                line4.setBackgroundResource(R.color.kv_10);
            }

        }else if(volLevelValue.equals(VolLevel_110KV)){
            if(volLevelSecondValue.equals(VolLevel_35KV)){
                twoCircleJt35110Img.setImageResource(R.drawable.two_circle_jt35_110);
                line1.setBackgroundResource(R.color.kv_110);
                line2.setBackgroundResource(R.color.kv_35);
                line3.setBackgroundResource(R.color.kv_35);
                line4.setBackgroundResource(R.color.kv_35);
            }else if(volLevelSecondValue.equals(VolLevel_10KV)){
                twoCircleJt35110Img.setImageResource(R.drawable.two_circle_jt10_110);
                line1.setBackgroundResource(R.color.kv_110);
                line2.setBackgroundResource(R.color.kv_10);
                line3.setBackgroundResource(R.color.kv_10);
                line4.setBackgroundResource(R.color.kv_10);
            }
        }


    }
}
