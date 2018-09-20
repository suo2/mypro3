package com.huawei.solarsafe.view.homepage.station;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.view.Gravity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;

import com.huawei.solarsafe.R;
import com.huawei.solarsafe.bean.GlobalConstants;
import com.huawei.solarsafe.utils.LocalData;
import com.huawei.solarsafe.utils.ToastUtil;

/**
 * Created by P00708 on 2018/7/5.
 */

public class DeviceChoiceDialog extends Dialog implements View.OnClickListener{

    private LabelItemView powerGenerationView;
    private LabelItemView internetPowerView;
    private LabelItemView buyPowerView;
    private LabelItemView chargePowerView;
    private LabelItemView dischargePowerView;
    private Context context;
    private CheckBox powerGenerationBox;
    private CheckBox internetPowerBox;
    private CheckBox buyPowerBox;
    private CheckBox chargePowerBox;
    private CheckBox dischargePowerBox;
    private DeviceEnsureSelect deviceEnsureSelect;
    private LinearLayout powerGenerationViewLayout;
    private LinearLayout internetPowerViewLayout;
    private LinearLayout buyPowerViewLayout;
    private LinearLayout chargePowerViewLayout;
    private LinearLayout dischargePowerViewLayout;
    public DeviceChoiceDialog(Context context,DeviceEnsureSelect deviceEnsureSelect) {
        super(context, R.style.AlertDialogStyle);
        setContentView(R.layout.device_choice_dialog_layout);
        this.context = context;
        setCanceledOnTouchOutside(false);
        getWindow().setGravity(Gravity.CENTER);
        getWindow().setWindowAnimations(R.style.choice_dialog_style_animation);
        this.deviceEnsureSelect = deviceEnsureSelect;
        powerGenerationView = (LabelItemView) findViewById(R.id.power_generation_view);
        internetPowerView = (LabelItemView) findViewById(R.id.internet_power_view);
        buyPowerView =  (LabelItemView) findViewById(R.id.buy_power_view);
        chargePowerView = (LabelItemView) findViewById(R.id.charge_power_view);
        dischargePowerView = (LabelItemView) findViewById(R.id.discharge_power_view);
        powerGenerationBox = (CheckBox) findViewById(R.id.power_generation_box);
        internetPowerBox = (CheckBox) findViewById(R.id.internet_power_box);
        buyPowerBox = (CheckBox) findViewById(R.id.buy_power_box);
        chargePowerBox = (CheckBox) findViewById(R.id.charge_power_box);
        dischargePowerBox = (CheckBox) findViewById(R.id.discharge_power_box);
        powerGenerationViewLayout =(LinearLayout) findViewById(R.id.power_generation_layout);
        powerGenerationViewLayout.setOnClickListener(this);
        internetPowerViewLayout = (LinearLayout)findViewById(R.id.internet_power_layout);
        internetPowerViewLayout.setOnClickListener(this);
        buyPowerViewLayout = (LinearLayout)findViewById(R.id.buy_power_layout);
        buyPowerViewLayout.setOnClickListener(this);
        chargePowerViewLayout = (LinearLayout) findViewById(R.id.charge_power_layout);
        chargePowerViewLayout.setOnClickListener(this);
        dischargePowerViewLayout = (LinearLayout)findViewById(R.id.discharge_power_layout);
        dischargePowerViewLayout.setOnClickListener(this);

        GradientDrawable powerGenerationDrawable = (GradientDrawable) context.getResources().getDrawable(R.drawable.small_circle_role_bg);
        powerGenerationDrawable.setColor(context.getResources().getColor(R.color.self_use_power));
        powerGenerationView.setLabelItemViewData(powerGenerationDrawable,R.string.generating_capacity);
        GradientDrawable  internetPowerDrawable = (GradientDrawable) context.getResources().getDrawable(R.drawable.small_circle_role_bg);
        internetPowerDrawable.setColor(context.getResources().getColor(R.color.internet_power));
        internetPowerView.setLabelItemViewData(internetPowerDrawable,R.string.internet_power);
        GradientDrawable  buyPowerDrawable = (GradientDrawable)  context.getResources().getDrawable(R.drawable.small_circle_role_bg);
        buyPowerDrawable.setColor( context.getResources().getColor(R.color.buy_power));
        buyPowerView.setLabelItemViewData(buyPowerDrawable,R.string.buy_power);
        GradientDrawable  chargePowerDrawable = (GradientDrawable)  context.getResources().getDrawable(R.drawable.small_circle_role_bg);
        chargePowerDrawable.setColor( context.getResources().getColor(R.color.energy_charge));
        chargePowerView.setLabelItemViewData(chargePowerDrawable,R.string.stored_energy_charge);
        GradientDrawable  dischargePowerDrawable = (GradientDrawable)  context.getResources().getDrawable(R.drawable.small_circle_role_bg);
        dischargePowerDrawable.setColor( context.getResources().getColor(R.color.energy_discharge));
        dischargePowerView.setLabelItemViewData(dischargePowerDrawable,R.string.stored_energy_discharge);
        if(LocalData.getInstance().getPowerCurveSelect(GlobalConstants.userId+LocalData.INVERTER_POWER_KEY,true)){
            powerGenerationBox.setChecked(true);
        }else{
            powerGenerationBox.setChecked(false);
        }
        if(LocalData.getInstance().getPowerCurveSelect(GlobalConstants.userId+LocalData.STORED_ENERGY_CHARGE_KEY,false)){
            chargePowerBox.setChecked(true);
        }else{
            chargePowerBox.setChecked(false);
        }
        if(LocalData.getInstance().getPowerCurveSelect(GlobalConstants.userId+LocalData.STORED_ENERGY_DISCHARGE_KEY,false)){
            dischargePowerBox.setChecked(true);
        }else{
            dischargePowerBox.setChecked(false);
        }
        if(LocalData.getInstance().getPowerCurveSelect(GlobalConstants.userId+LocalData.BUY_POWER_KEY,true)){
            buyPowerBox.setChecked(true);
        }else{
            buyPowerBox.setChecked(false);
        }
        if(LocalData.getInstance().getPowerCurveSelect(GlobalConstants.userId+LocalData.INTERNET_POWER_KEY,true)){
            internetPowerBox.setChecked(true);
        }else{
            internetPowerBox.setChecked(false);
        }
        findViewById(R.id.btn_close).setOnClickListener(this);
        findViewById(R.id.btn_notice).setOnClickListener(this);
        if(!GlobalConstants.isHasMeter){
            internetPowerBox.setChecked(false);
            buyPowerBox.setChecked(false);
            internetPowerViewLayout.setVisibility(View.GONE);
            buyPowerViewLayout.setVisibility(View.GONE);

        }
        if(!GlobalConstants.ishasEnergyStore){
            chargePowerBox.setChecked(false);
            dischargePowerBox.setChecked(false);
            chargePowerViewLayout.setVisibility(View.GONE);
            dischargePowerViewLayout.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.btn_close:
                dismiss();
                break;

            case R.id.btn_notice:
                ensureChoice();
                break;
            case R.id.power_generation_layout:
                powerGenerationBox.setChecked(!powerGenerationBox.isChecked());
                break;

            case R.id.internet_power_layout:
                internetPowerBox.setChecked(!internetPowerBox.isChecked());
                break;
            case R.id.buy_power_layout:
                buyPowerBox.setChecked(!buyPowerBox.isChecked());
                break;

            case R.id.charge_power_layout:
                chargePowerBox.setChecked(!chargePowerBox.isChecked());
                break;

            case R.id.discharge_power_layout:
                dischargePowerBox.setChecked(!dischargePowerBox.isChecked());
                break;

            default:
                break;
        }

    }
    private void ensureChoice(){
        if(!powerGenerationBox.isChecked() && !chargePowerBox.isChecked() && !dischargePowerBox.isChecked() && !buyPowerBox.isChecked() && !internetPowerBox.isChecked()){
            ToastUtil.showToastMsg(context,context.getResources().getString(R.string.custom_report_one));
            return;
        }else if(getCheckCount()>3){
            ToastUtil.showToastMsg(context,context.getResources().getString(R.string.custom_report_check_three));
            return;
        }
        LocalData.getInstance().setPowerCurveSelect(GlobalConstants.userId+LocalData.INVERTER_POWER_KEY,powerGenerationBox.isChecked());
        LocalData.getInstance().setPowerCurveSelect(GlobalConstants.userId+LocalData.STORED_ENERGY_CHARGE_KEY,chargePowerBox.isChecked());
        LocalData.getInstance().setPowerCurveSelect(GlobalConstants.userId+LocalData.STORED_ENERGY_DISCHARGE_KEY,dischargePowerBox.isChecked());
        LocalData.getInstance().setPowerCurveSelect(GlobalConstants.userId+LocalData.BUY_POWER_KEY,buyPowerBox.isChecked());
        LocalData.getInstance().setPowerCurveSelect(GlobalConstants.userId+LocalData.INTERNET_POWER_KEY,internetPowerBox.isChecked());
        dismiss();
        deviceEnsureSelect.selectDevice();
    }

    public interface DeviceEnsureSelect{
        void selectDevice();
    }
    private int getCheckCount(){
        int checkCount =0;
        if(powerGenerationBox.isChecked()){
            checkCount++;
        }
        if(chargePowerBox.isChecked()){
            checkCount++;
        }
        if(dischargePowerBox.isChecked()){
            checkCount++;
        }
        if(buyPowerBox.isChecked()){
            checkCount++;
        }
        if(internetPowerBox.isChecked()){
            checkCount++;
        }
        return checkCount;
    }
}
