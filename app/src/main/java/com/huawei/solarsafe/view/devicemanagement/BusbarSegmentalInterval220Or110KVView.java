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

import static com.huawei.solarsafe.bean.device.DevTypeConstant.VolLevel_220KV;

/**
 * Created by P00708 on 2018/3/6.
 * 220/110母线/分段间隔电路图
 */

public class BusbarSegmentalInterval220Or110KVView extends LinearLayout{
    private Context context;
    private View line1;
    private View line2;
    private View line3;
    private View line4;
    private View line5;
    private View line6;
    private View line7;
    private View line8;
    private View line9;
    private View line10;
    private View line11;
    private View line12;
    private View line13;
    private View line14;
    private View line15;
    private View line16;
    private ImageView switch1;
    private TextView tx1;
    private ImageView switch2;
    private TextView tx2;
    private ImageView switch4;
    private TextView tx4;
    private ImageView switch5;
    private TextView tx5;
    private ImageView circuitBreaker3;
    private TextView tx3;
    public BusbarSegmentalInterval220Or110KVView(Context context){
        super(context);
        this.context = context;
        initView();
    }
    public BusbarSegmentalInterval220Or110KVView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initView();
    }
    private void initView(){
        LayoutInflater.from(context).inflate(R.layout.busbar_segmental_interval_220_or_110_kv_view_layout,this,true);
        line1 = findViewById(R.id.line1);
        line2 = findViewById(R.id.line2);
        line3 = findViewById(R.id.line3);
        line4 = findViewById(R.id.line4);
        line5 = findViewById(R.id.line5);
        line6 = findViewById(R.id.line6);
        line7 = findViewById(R.id.line7);
        line8 = findViewById(R.id.line8);
        line9 = findViewById(R.id.line9);
        line10 = findViewById(R.id.line10);
        line11 = findViewById(R.id.line11);
        line12 = findViewById(R.id.line12);
        line13 = findViewById(R.id.line13);
        line14 = findViewById(R.id.line14);
        line15 = findViewById(R.id.line15);
        line16 = findViewById(R.id.line16);
        switch1 = (ImageView) findViewById(R.id.switch_15900);
        tx1 = (TextView) findViewById(R.id.tx_15900);
        switch2 = (ImageView) findViewById(R.id.switch_15903);
        tx2 = (TextView) findViewById(R.id.tx_15903);
        circuitBreaker3 = (ImageView) findViewById(R.id.circuit_breaker_15902);
        tx3 = (TextView) findViewById(R.id.tx_15902);
        switch4 = (ImageView) findViewById(R.id.switch_15901);
        tx4 = (TextView) findViewById(R.id.tx_15901);
        switch5 = (ImageView) findViewById(R.id.switch_15905);
        tx5 = (TextView) findViewById(R.id.tx_15905);
    }

    public void setData(List<WiringDataBean> dataList, String volLevelValue, String volLevelSecondValue){

        HashMap<Integer,WiringDataBean> data=new HashMap<>();
        if(dataList != null && dataList.size()>0){
            for(WiringDataBean dataBean:dataList){
                data.put(dataBean.getIndex(),dataBean);
            }
        }
        if(data.containsKey(1)){  //判断电路图1号位置是否有数据
            WiringDataBean data1 = data.get(1);
            tx1.setText(data1.getDispatchNumber()+"");
            if(data1.getValue() != null && data1.getValue().equals("0")){
                switch1.setImageResource(R.drawable.vertical_switch_on);
            }else if(data1.getValue() != null && data1.getValue().equals("1")){
                switch1.setImageResource(R.drawable.vertical_switch_off);
            }else{
                switch1.setImageResource(R.drawable.vertical_switch_error);
            }
        }else{
            switch1.setImageResource(R.drawable.vertical_switch_error);
            tx1.setText("");
        }
        if(data.containsKey(2)){
            WiringDataBean data2 = data.get(2);
            tx2.setText(data2.getDispatchNumber()+"");
            if(data2.getValue() != null && data2.getValue().equals("0")){
                switch2.setImageResource(R.drawable.horizontal_switch_on);
            }else if(data2.getValue() != null && data2.getValue().equals("1")){
                switch2.setImageResource(R.drawable.horizontal_switch_off);
            }else{
                switch2.setImageResource(R.drawable.horizontal_switch_error);
            }
        }else{
            switch2.setImageResource(R.drawable.horizontal_switch_error);
            tx2.setText("");
        }
        if(data.containsKey(3)){
            WiringDataBean data3 = data.get(3);
            tx3.setText(data3.getDispatchNumber()+"");
            if(data3.getValue() != null && data3.getValue().equals("1")){
                circuitBreaker3.setImageResource(R.drawable.circuit_breaker_close_horizontal);
            }else if(data3.getValue() != null && data3.getValue().equals("0")){
                circuitBreaker3.setImageResource(R.drawable.circuit_breaker_open_horizontal);
            }else{
                circuitBreaker3.setImageResource(R.drawable.circuit_breaker_error_horizontal);
            }
        }else{
            circuitBreaker3.setImageResource(R.drawable.circuit_breaker_error_horizontal);
            tx3.setText("");
        }
        if(data.containsKey(4)){
            WiringDataBean data4 = data.get(4);
            tx4.setText(data4.getDispatchNumber()+"");
            if(data4.getValue() != null && data4.getValue().equals("0")){
                switch4.setImageResource(R.drawable.vertical_switch_on);
            }else if(data4.getValue() != null && data4.getValue().equals("1")){
                switch4.setImageResource(R.drawable.vertical_switch_off);
            }else{
                switch4.setImageResource(R.drawable.vertical_switch_error);
            }
        }else{
            switch4.setImageResource(R.drawable.vertical_switch_error);
            tx4.setText("");
        }
        if(data.containsKey(5)){
            WiringDataBean data5 = data.get(5);
            tx5.setText(data5.getDispatchNumber()+"");
            if(data5.getValue() != null && data5.getValue().equals("0")){
                switch5.setImageResource(R.drawable.horizontal_switch_on);
            }else if(data5.getValue() != null && data5.getValue().equals("1")){
                switch5.setImageResource(R.drawable.horizontal_switch_off);
            }else{
                switch5.setImageResource(R.drawable.horizontal_switch_error);
            }
        }else{
            switch5.setImageResource(R.drawable.horizontal_switch_error);
            tx5.setText("");
        }
        if(volLevelValue.equals(VolLevel_220KV)){
            updateAllLineColor(context.getResources().getColor(R.color.kv_220));
        }else{
            updateAllLineColor(context.getResources().getColor(R.color.kv_110));
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
        line9.setBackgroundColor(color);
        line10.setBackgroundColor(color);
        line11.setBackgroundColor(color);
        line12.setBackgroundColor(color);
        line13.setBackgroundColor(color);
        line14.setBackgroundColor(color);
        line15.setBackgroundColor(color);
        line16.setBackgroundColor(color);
    }
}
