package com.huawei.solarsafe.view.devicemanagement;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.huawei.solarsafe.R;
import com.huawei.solarsafe.bean.device.CurrencySignalDataInfo;
import com.huawei.solarsafe.bean.device.HistorySignalName;
import com.huawei.solarsafe.utils.Utils;
import com.huawei.solarsafe.utils.device.ExtraVoiceDBManager;
import com.huawei.solarsafe.utils.language.WappLanguage;

import java.util.Locale;

/**
 * Created by P00708 on 2018/3/7.
 * 升压站实时信息模拟量列表内容
 */

public class RealTimeAnalogueScaleView extends RelativeLayout{
    private Context context;
    private TextView firstTxName;
    private TextView firstTxValue;
    private String country ;
    private ExtraVoiceDBManager extraManager;
    public RealTimeAnalogueScaleView(Context context){
        super(context);
        this.context = context;
        initView();
    }
    public RealTimeAnalogueScaleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initView();
    }

    private void initView(){
        country = Locale.getDefault().getCountry();
        LayoutInflater.from(context).inflate(R.layout.real_time_analogu_scale_view_layout,this,true);
        firstTxName = (TextView) findViewById(R.id.first_tx_name);
        firstTxValue = (TextView) findViewById(R.id.first_tx_value);
        extraManager = ExtraVoiceDBManager.getInstance();
    }
    public void setData(CurrencySignalDataInfo data){

        if(data == null){
            return;
        }
        if(TextUtils.isEmpty(data.getSignalName())){
            return;
        }else{
            /**
             * CODEX 162436 修改人:江东
             */
            HistorySignalName signalName = extraManager.getHistorySignalName(data.getSignalName());
            if (signalName!=null)
                switch (country) {
                    case WappLanguage.COUNTRY_CN:
                        firstTxName.setText(signalName.getzName());
                        break;
                    case WappLanguage.COUNTRY_JP:
                        firstTxName.setText(signalName.getJaName());
                        break;
                    case WappLanguage.COUNTYY_US:
                        firstTxName.setText(signalName.getEnName());
                        break;
                    default:
                        firstTxName.setText(signalName.getEnName());
                        break;
                }
            else
                firstTxName.setText("");
        }
        String unit = data.getSignalUnit();
        if(unit != null && Utils.parseUnit(unit).length()>0) {

            unit = Utils.parseUnit(unit);
        }else{
            unit ="";
        }
        if(!TextUtils.isEmpty(unit)){
            firstTxName.setText(firstTxName.getText()+"("+unit+")");
        }
        if(TextUtils.isEmpty(data.getSignalValue())){
            firstTxValue.setText("-");
        }else{
            double value = Double.valueOf(data.getSignalValue());
            firstTxValue.setText(Utils.round(value,3));
        }
    }

}
