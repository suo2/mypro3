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
import static com.huawei.solarsafe.bean.device.DevTypeConstant.VolLevel_35KV;

/**
 * Created by P00708 on 2018/3/6.
 * 35/10KV母线间隔电路图
 */

public class ThirtyFiveOrTenKVBusbarIntervalView extends LinearLayout{
    private Context context;
    private View line1;
    private View line2;
    private View line3;
    private View line4;
    private ImageView handCarState;
    private TextView tx1;
    private ImageView pt4G3dState;
    public ThirtyFiveOrTenKVBusbarIntervalView(Context context){
        super(context);
        this.context = context;
        initView();
    }
    public ThirtyFiveOrTenKVBusbarIntervalView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initView();
    }
    private void initView(){
        LayoutInflater.from(context).inflate(R.layout.thirty_five_or_ten_kv_busbar_interval_view_layout,this,true);
        line1 = findViewById(R.id.line1);
        line2 = findViewById(R.id.line2);
        line3 = findViewById(R.id.line3);
        line4 = findViewById(R.id.line4);
        handCarState = (ImageView) findViewById(R.id.hand_car_state);
        tx1 = (TextView) findViewById(R.id.tx_15900);
        pt4G3dState = (ImageView) findViewById(R.id.pt4_g3d_state);
    }
    public void setData(List<WiringDataBean> dataList, String volLevelValue){

        HashMap<Integer,WiringDataBean> data=new HashMap<>();
        if(dataList != null && dataList.size()>0){
            for(WiringDataBean dataBean:dataList){
                data.put(dataBean.getIndex(),dataBean);
            }
        }
        if(data.containsKey(1)){  //判断电路图1号位置是否有数据
            WiringDataBean data1 = data.get(1);
            tx1.setText(data1.getDispatchNumber()+"");
            if( data1.getValue() != null && data1.getValue().equals("1")){
                handCarState.setImageResource(R.drawable.one_hand_car_divide);
            }else if(data1.getValue() != null && data1.getValue().equals("0")){
                handCarState.setImageResource(R.drawable.two_hand_car_divide);
            }else{
                handCarState.setImageResource(R.drawable.hand_car_error);
            }
        }else{
            handCarState.setImageResource(R.drawable.hand_car_error);
            tx1.setText("");
        }

        if(volLevelValue.equals(VolLevel_35KV)){
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
    }
}
