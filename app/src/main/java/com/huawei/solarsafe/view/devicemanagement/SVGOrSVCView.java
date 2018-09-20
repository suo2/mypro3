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
 * SVG/SVC电路图
 */

public class SVGOrSVCView extends LinearLayout{
    private Context context;
    private View line1;
    private View line2;
    private ImageView handCar1;
    private ImageView circuitBreaker1;
    private TextView tx1;
    private View line3;
    private View line4;
    private ImageView switchState2;
    private TextView tx3;
    private ImageView arrowImg;
    private TextView tx2;
    public SVGOrSVCView(Context context){
        super(context);
        this.context = context;
        initView();
    }
    public SVGOrSVCView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initView();
    }
    private void initView(){
        LayoutInflater.from(context).inflate(R.layout.svg_or_svc_view_layout,this,true);
        line1 = findViewById(R.id.line1);
        line2 = findViewById(R.id.line2);
        handCar1 = (ImageView) findViewById(R.id.hand_car_state);
        circuitBreaker1 = (ImageView) findViewById(R.id.circuit_breaker_state);
        tx2 = (TextView) findViewById(R.id.tx_hand_car);
        line3 = findViewById(R.id.line3);
        line4 = findViewById(R.id.line4);
        switchState2 = (ImageView) findViewById(R.id.switch_state);
        tx3 = (TextView) findViewById(R.id.tx_15905);
        arrowImg = (ImageView) findViewById(R.id.arrow_img);
        tx1 = (TextView) findViewById(R.id.tx_15900);
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
            if(data1.getValue().equals("1")){
                circuitBreaker1.setImageResource(R.drawable.circuit_breaker_close);
            }else if(data1.getValue().equals("0")){
                circuitBreaker1.setImageResource(R.drawable.circuit_breaker_open);
            }else{
                circuitBreaker1.setImageResource(R.drawable.circuit_breaker_error);
            }
        }else{
            circuitBreaker1.setImageResource(R.drawable.circuit_breaker_error);
            tx1.setText("");
        }

        if(data.containsKey(2)){
            WiringDataBean data2 = data.get(2);
            tx2.setText(data2.getDispatchNumber()+"");
            if( data2.getValue().equals("1")){
                handCar1.setImageResource(R.drawable.one_hand_car_divide);
            }else if( data2.getValue().equals("0")){
                handCar1.setImageResource(R.drawable.two_hand_car_divide);
            }else{
                handCar1.setImageResource(R.drawable.hand_car_error);
            }
        }else{
            handCar1.setImageResource(R.drawable.hand_car_error);
            tx2.setText("");
        }

        if(data.containsKey(3)){
            WiringDataBean data3 = data.get(3);
            tx3.setText(data3.getDispatchNumber()+"");
            if(data3.getValue() != null && data3.getValue().equals("0")){
                switchState2.setImageResource(R.drawable.horizontal_switch_on);
            }else if(data3.getValue() != null && data3.getValue().equals("1")){
                switchState2.setImageResource(R.drawable.horizontal_switch_off);
            }else{
                switchState2.setImageResource(R.drawable.horizontal_switch_error);
            }
        }else{
            switchState2.setImageResource(R.drawable.horizontal_switch_error);
            tx3.setText("");
        }
        if(volLevelValue.equals(VolLevel_35KV)){
            arrowImg.setImageResource(R.drawable.arrow_35kv);
            updateAllLineColor(context.getResources().getColor(R.color.kv_35));
        }else{
            arrowImg.setImageResource(R.drawable.arrow_10kv);
            updateAllLineColor(context.getResources().getColor(R.color.kv_10));
        }
    }

    private void updateAllLineColor(int color){
        line1.setBackgroundColor(color);
        line2.setBackgroundColor(color);
        line3.setBackgroundColor(color);
        line4.setBackgroundColor(color);
    }

}
