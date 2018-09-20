package com.huawei.solarsafe.view.report;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.GridView;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.huawei.solarsafe.R;
import com.huawei.solarsafe.bean.report.Indicator;

import java.util.LinkedList;

/**
 * Created by P00708 on 2018/1/12.
 */

public class InverterReportPopupWindow implements View.OnClickListener{
    private Context context;
    private PopupWindow popupWindow;
    private GridView reportGradview;
    private Button buttonRestore,buttonSure;
    private GridViewAdapter adapter;
    private LinkedList<Indicator> spinnerList;
    private InverterReportActivity inverterReportActivity;

    public void setSpinnerList(LinkedList<Indicator> spinnerList) {
        this.spinnerList.clear();
        this.spinnerList.addAll(spinnerList);
        adapter.notifyDataSetChanged();
    }

    public InverterReportPopupWindow(Context context){
        this.context = context;
        init();
    }

    private void init() {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View contentView = layoutInflater.inflate(R.layout.popupwindow_report_filter,null);
        int width = (int)(getScreenWidth()*1f*3/4);
        popupWindow = new PopupWindow(contentView,width, ViewGroup.LayoutParams.MATCH_PARENT);
        popupWindow.setFocusable(true);
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        popupWindow.setOutsideTouchable(true);
        popupWindow.setAnimationStyle(R.style.popup_window_animation_display);
        reportGradview = (GridView) contentView.findViewById(R.id.report_gradview);
        buttonRestore = (Button) contentView.findViewById(R.id.button_restore_default);
        buttonSure = (Button) contentView.findViewById(R.id.button_sure_report_pop);
        buttonSure.setOnClickListener(this);
        buttonRestore.setOnClickListener(this);
        spinnerList = new LinkedList<>();
        adapter = new GridViewAdapter();
        reportGradview.setAdapter(adapter);
    }

    /**
     * @param v
     * 显示popupwindow
     */
    public void showPopupwindow(InverterReportActivity inverterReportActivity,View v,PopupWindow.OnDismissListener dismissListener){
        if(popupWindow.isShowing()){
            return;
        }
        this.inverterReportActivity = inverterReportActivity;
        popupWindow.setOnDismissListener(dismissListener);
        popupWindow.showAtLocation(v, Gravity.RIGHT,0,0);
    }

    /**
     * @return
     * 获取屏幕的宽
     */
    private int getScreenWidth(){
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        return wm.getDefaultDisplay().getWidth();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.button_restore_default:
                resetSpinnerList();
                adapter.notifyDataSetChanged();
//                inverterReportActivity.completeFilter(spinnerList);
//                dismiss();
                break;
            case R.id.button_sure_report_pop:
                if(isAllNonSelected()){
                    if(isSelectedThree()){
                        inverterReportActivity.completeFilter(spinnerList);
                        dismiss();
                    }else {
                        Toast.makeText(context,context.getResources().getString(R.string.at_least_three_column_selected_to_display),Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(context,context.getResources().getString(R.string.at_least_three_column_selected_to_display),Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
    }

    /**
     * popupWindow消失
     */
    public void dismiss(){
        popupWindow.dismiss();
    }

    /**
     * @return
     * 是否完全选中
     */
    public boolean isAllIndicatorsSelected(){
        int len = spinnerList.size();
        for(int i=1;i<len;i++){
            if(!spinnerList.get(i).isChecked()){
                return false;
            }
        }
        return true;
    }

    /**
     * 恢复默认选择
     */
    public void resetSpinnerList(){
        int len=spinnerList.size();
        for(int j=0;j<len;j++){
            if(spinnerList.get(j).isDefaultChecked()){
                spinnerList.get(j).setChecked(true);
            }else{
                spinnerList.get(j).setChecked(false);
            }
        }
    }

    /**
     * @return
     * 是否都没有选中
     */
    public boolean isAllNonSelected(){
        int len=spinnerList.size();
        for(int i=0;i<len;i++){
            if(spinnerList.get(i).isChecked()){
                return true;
            }
        }
        return false;
    }

    /**
     * @return
     * 必须选择多于3个
     */
    public boolean isSelectedThree() {
        int len=spinnerList.size();
        int num = 0;
        for(int i=1;i<len;i++){
            if(spinnerList.get(i).isChecked()){
                num ++;
            }
        }
        if(num >= 3){
            return true;
        }else {
            return false;
        }
    }

    private class GridViewAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return spinnerList.size();
        }

        @Override
        public Object getItem(int position) {
            return spinnerList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            final GridViewAdapter.ViewHolder viewHolder;
            if(convertView == null){
                viewHolder = new GridViewAdapter.ViewHolder();
                convertView= LayoutInflater.from(context).inflate(R.layout.report_fragment_spinner_item,null);
                viewHolder.itemCheckBox = (CheckBox) convertView.findViewById(R.id.report_ch);
                convertView.setTag(viewHolder);
            }else {
                viewHolder = (GridViewAdapter.ViewHolder) convertView.getTag();
            }
            Indicator indicator = spinnerList.get(position);
            viewHolder.itemCheckBox.setText(indicator.getItem());
            viewHolder.itemCheckBox.setChecked(indicator.isChecked());

            viewHolder.itemCheckBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(position == 0){
                        for(int k=0;k<spinnerList.size();k++){
                            spinnerList.get(k).setChecked(viewHolder.itemCheckBox.isChecked());
                        }
                        notifyDataSetChanged();
                    }else if(!viewHolder.itemCheckBox.isChecked()){
                        spinnerList.get(0).setChecked(viewHolder.itemCheckBox.isChecked());
                        spinnerList.get(position).setChecked(viewHolder.itemCheckBox.isChecked());
                        notifyDataSetChanged();
                    }else {
                        spinnerList.get(position).setChecked(viewHolder.itemCheckBox.isChecked());
                        if(isAllIndicatorsSelected()){
                            spinnerList.get(0).setChecked(true);
                            notifyDataSetChanged();
                        }
                    }
                }
            });
            return convertView;
        }

        class ViewHolder{
            CheckBox itemCheckBox;
        }
    }
}
