package com.huawei.solarsafe.view.maintaince.main;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.huawei.solarsafe.R;

/**
 * Created by p00507
 * on 2017/11/17.
 */

public class GroupPVAdapter extends BaseAdapter {
    private Context context;
    private String[] voltageValue;
    private String[] pvNumString;
    private String[] currentValue;
    private double[] currentDoubleValue;
    private boolean needDyeing = false;
    public GroupPVAdapter(Context context, String[] voltageValue,String[] currentValue, String[] pvNumString,boolean needDyeing) {
        this.context = context;
        this.voltageValue = voltageValue;
        this.pvNumString = pvNumString;
        this.currentValue = currentValue;
        this.needDyeing = needDyeing;
        stringDataToDouble(currentValue);
    }

    @Override
    public int getCount() {
        return currentValue==null?0:currentValue.length;
    }

    @Override
    public Object getItem(int position) {
        return currentValue[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if(convertView == null){
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.group_pv_item,parent,false);
            viewHolder.pvNum = (TextView) convertView.findViewById(R.id.tv_pv_num);
            viewHolder.voltageTX = (TextView) convertView.findViewById(R.id.tv_voltage_value);
            viewHolder.currentTx = (TextView) convertView.findViewById(R.id.tv_current_value);
            viewHolder.voltageLine = convertView.findViewById(R.id.voltage_value_line);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        if(voltageValue == null){
            viewHolder.voltageTX.setVisibility(View.GONE);
            viewHolder.voltageLine.setVisibility(View.GONE);
        }else{
            if(TextUtils.isEmpty(voltageValue[position])){
                viewHolder.voltageTX.setText("");
            }else{
                viewHolder.voltageTX.setText(voltageValue[position]);
            }
        }
        viewHolder.pvNum.setText(pvNumString[position]);
        if(TextUtils.isEmpty(currentValue[position])){
            viewHolder.currentTx.setText("");
        }else{
            viewHolder.currentTx.setText(currentValue[position]);
        }
        if(needDyeing){
            handlerNeedDyeing(viewHolder,position);
        }else{
            viewHolder.voltageTX.setBackgroundColor(context.getResources().getColor(R.color.white));
            viewHolder.voltageTX.setTextColor(context.getResources().getColor(R.color.danzhan_color));
            viewHolder.currentTx.setBackgroundColor(context.getResources().getColor(R.color.white));
            viewHolder.currentTx.setTextColor(context.getResources().getColor(R.color.danzhan_color));
        }
        return convertView;
    }
    class ViewHolder {
        TextView pvNum;
        TextView voltageTX;
        TextView currentTx;
        View voltageLine;
    }

    /**
     * ����Ⱦɫ
     * @param viewHolder
     * @param position
     */
    private void handlerNeedDyeing(ViewHolder viewHolder,int position){
        if(voltageValue != null){
            if(TextUtils.isEmpty(voltageValue[position])||voltageValue[position].equals("--")){
                viewHolder.voltageTX.setBackgroundColor(context.getResources().getColor(R.color.white));
                viewHolder.voltageTX.setTextColor(context.getResources().getColor(R.color.danzhan_color));
            }
        }
       if(currentValue != null){
           if(TextUtils.isEmpty(currentValue[position])||currentValue[position].equals("--")){
               viewHolder.currentTx.setBackgroundColor(context.getResources().getColor(R.color.white));
               viewHolder.currentTx.setTextColor(context.getResources().getColor(R.color.danzhan_color));
           }else{
               if(currentDoubleValue[position]<=0.3){
                   viewHolder.currentTx.setBackgroundColor(context.getResources().getColor(R.color.red));
                   viewHolder.currentTx.setTextColor(context.getResources().getColor(R.color.white));
                   if(voltageValue != null){
                       viewHolder.voltageTX.setBackgroundColor(context.getResources().getColor(R.color.red));
                       viewHolder.voltageTX.setTextColor(context.getResources().getColor(R.color.white));
                   }
               }else if(currentDeviationsFromAverageMax(currentDoubleValue[position])){
                   viewHolder.currentTx.setBackgroundColor(context.getResources().getColor(R.color.orange));
                   viewHolder.currentTx.setTextColor(context.getResources().getColor(R.color.white));
                   if(voltageValue != null){
                       viewHolder.voltageTX.setBackgroundColor(context.getResources().getColor(R.color.orange));
                       viewHolder.voltageTX.setTextColor(context.getResources().getColor(R.color.white));
                   }

               }else{
                   viewHolder.currentTx.setBackgroundColor(context.getResources().getColor(R.color.white));
                   viewHolder.currentTx.setTextColor(context.getResources().getColor(R.color.danzhan_color));
                   if(voltageValue != null){
                       viewHolder.voltageTX.setBackgroundColor(context.getResources().getColor(R.color.white));
                       viewHolder.voltageTX.setTextColor(context.getResources().getColor(R.color.danzhan_color));
                   }
               }
           }
       }


    }
    /**
     * @param value
     * @return
     */

    private boolean currentDeviationsFromAverageMax(double value){
        int len = getEffectiveDataLength(currentValue);
        if(len <=2){
            return false;
        }
         if(currentDoubleValue == null){
             return false;
         }
        double averageValue = 0;
        for(double data:currentDoubleValue){
            averageValue += data;
        }
        averageValue = averageValue/len;
        //f:FE_FLOATING_POINT_EQUALITY
        if((Math.abs(value - averageValue) < .0000001 )){
            return false;
        }
        double maxValue = Math.abs(averageValue - value);
        for(int i=0;i<currentDoubleValue.length;i++){
            if(TextUtils.isEmpty(currentValue[i])||currentValue[i].equals("--")){
                continue;
            }
            double dataValue = Math.abs(averageValue - currentDoubleValue[i]);
            if(dataValue>maxValue){
                return false;
            }else{
                if(i == currentDoubleValue.length-1){
                    return true;
                }
                continue;
            }
        }
        return true;

    }

    /**
     * @param currentValue
     */
    private void stringDataToDouble(String[] currentValue){
        if(currentValue != null){
            currentDoubleValue = new double[currentValue.length];
            for(int j=0;j<currentValue.length;j++){
                if(!TextUtils.isEmpty(currentValue[j])){
                    try{
                        currentDoubleValue[j] = Double.valueOf(currentValue[j]);
                    }catch (NumberFormatException e){
                        currentDoubleValue[j] = 0;
                    }
                }else{
                    currentDoubleValue[j] = 0;
                }
            }
        }
    }

    /**
     * @param data
     * @return
     */
    private int getEffectiveDataLength(String[] data){
        int num =0;
        if(data == null){
            return num;
        }
        for(int i=0;i<data.length;i++){
            if(TextUtils.isEmpty(data[i])||data[i].equals("--")){
                continue;
            }else{
                num++;
            }

        }
        return num;
    }
}
