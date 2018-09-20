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
 * 220/110母线间隔电路图
 */

public class BusbarInterval220Or110KVView extends LinearLayout{

    private Context context;
    private View line1;
    private View line2;
    private View line3;
    private View line4;
    private View line5;
    private View line6;
    private ImageView switch1;
    private TextView tx1;
    private ImageView switch2;
    private TextView tx2;
    private ImageView switch3;
    private TextView tx3;
    private ImageView pt4G3dState;
    private TextView tx15902;
    public BusbarInterval220Or110KVView(Context context){
        super(context);
        this.context = context;
        initView();
    }
    public BusbarInterval220Or110KVView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initView();
    }

    private void initView(){
        LayoutInflater.from(context).inflate(R.layout.busbar_interval_220_or_110_kv_view_layout,this,true);
        line1 = findViewById(R.id.line1);
        line2 = findViewById(R.id.line2);
        line3 = findViewById(R.id.line3);
        line4 = findViewById(R.id.line4);
        line5 = findViewById(R.id.line5);
        line6 = findViewById(R.id.line6);
        switch1 = (ImageView) findViewById(R.id.switch_15900);
        tx1 = (TextView) findViewById(R.id.tx_15900);
        switch2 = (ImageView) findViewById(R.id.switch_15903);
        tx2 = (TextView) findViewById(R.id.tx_15903);
        switch3 = (ImageView) findViewById(R.id.switch_15905);
        tx3 = (TextView) findViewById(R.id.tx_15905);
        pt4G3dState = (ImageView) findViewById(R.id.pt4_g3d_state);
        tx15902 = (TextView) findViewById(R.id.tx_15902);
        tx15902.setText("");
    }
    public void setData(List<WiringDataBean> dataList, String volLevelValue, String volLevelSecondValue){

        HashMap<Integer,WiringDataBean> data=new HashMap<>();
        if(dataList != null && dataList.size()>0){
            for(WiringDataBean dataBean:dataList){
                data.put(dataBean.getIndex(),dataBean);
            }
        }
        if(data.containsKey(1)) {  //判断电路图1号位置是否有数据
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
        if(data.containsKey(2)) {  //判断电路图2号位置是否有数据
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
        if(data.containsKey(3)) {  //判断电路图3号位置是否有数据
            WiringDataBean data3 = data.get(3);
            tx3.setText(data3.getDispatchNumber()+"");
            if(data3.getValue() != null && data3.getValue().equals("0")){
                switch3.setImageResource(R.drawable.horizontal_switch_on);
            }else if(data3.getValue() != null && data3.getValue().equals("1")){
                switch3.setImageResource(R.drawable.horizontal_switch_off);
            }else{
                switch3.setImageResource(R.drawable.horizontal_switch_error);
            }
        }else{
            switch3.setImageResource(R.drawable.horizontal_switch_error);
            tx3.setText("");
        }
        if(volLevelValue.equals(VolLevel_220KV)){
            pt4G3dState.setImageResource(R.drawable.pt4_gd220kv);
            updateAllLineColor(context.getResources().getColor(R.color.kv_220));
        }else if(volLevelValue.equals(VolLevel_110KV)){
            pt4G3dState.setImageResource(R.drawable.pt4_gd110kv);
            updateAllLineColor(context.getResources().getColor(R.color.kv_110));
        }else if(volLevelValue.equals(VolLevel_35KV)){
            pt4G3dState.setImageResource(R.drawable.pt4_gd35kv);
            updateAllLineColor(context.getResources().getColor(R.color.kv_35));
        }else if(volLevelValue.equals(VolLevel_10KV)){
            pt4G3dState.setImageResource(R.drawable.pt4_gd10kv);
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
    }
}
