package com.huawei.solarsafe.view.homepage.station;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.huawei.solarsafe.R;
import com.huawei.solarsafe.utils.TextViewUtils;

/**
 * Created by P00708 on 2018/7/3.
 * 双进度条
 *
 * slef_use
 */

public class DoubleProgressBar extends LinearLayout{
    private TextView progressBarTitle;
    private RelativeLayout leftProgressValue;
    private TextView leftProgressTx;
    private RelativeLayout rightProgressValue;
    private TextView rightProgressTx;
    private TextView leftBarValue;
    private TextView rightBarValue;
    private int leftBarColor;
    private int rightBarColor;
    private Context context;
    public DoubleProgressBar(Context context){
        super(context);
        LayoutInflater.from(context).inflate(R.layout.double_progress_bar_layout,this);
        this.context = context;
        initView();
    }
    public DoubleProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.double_progress_bar_layout,this);
        this.context = context;
        initView();
        leftBarColor = context.getResources().getColor(R.color.text_for);
        rightBarColor = context.getResources().getColor(R.color.text_for);
    }
    private void initView(){
        progressBarTitle = (TextView) findViewById(R.id.progress_title);
        leftProgressValue = (RelativeLayout) findViewById(R.id.left_bar_progress_value);
        leftProgressTx = (TextView) findViewById(R.id.left_bar_progress_tx);
        rightProgressValue = (RelativeLayout) findViewById(R.id.right_bar_progress_value);
        rightProgressTx = (TextView) findViewById(R.id.right_bar_progress_tx);
        leftBarValue = (TextView) findViewById(R.id.left_bar_value);
        rightBarValue = (TextView) findViewById(R.id.right_bar_value);
    }
    public void setProgressBarTitle(String title){
        progressBarTitle.setText(title);
    }
    public void setLeftProgressColor(int color){
        leftBarColor = color;
        GradientDrawable background = (GradientDrawable) leftProgressValue.getBackground();
        background.setColor(color);
    }
    public void setRightProgressColor(int color){
        rightBarColor = color;
        GradientDrawable background = (GradientDrawable) rightProgressValue.getBackground();
        background.setColor(color);
    }
    public void setProgressBarValue(float leftValue,float rightValue){
        LinearLayout.LayoutParams leftLayoutParams = (LayoutParams) leftProgressValue.getLayoutParams();
        LinearLayout.LayoutParams rightLayoutParams = (LayoutParams) rightProgressValue.getLayoutParams();
        if(leftValue <0){
            leftValue = 0;
        }
        if(rightValue <0){
            rightValue =0;
        }
        if(leftValue == 0 && rightValue ==0){
            leftLayoutParams.weight = 1;
            rightLayoutParams.weight = 0;
            GradientDrawable background = (GradientDrawable) context.getResources().getDrawable(R.drawable.all_circle_role_bg);
            background.setColor(context.getResources().getColor(R.color.text_for));
            leftProgressValue.setBackground(background);
        }else if(leftValue == 0){
            GradientDrawable background = (GradientDrawable) context.getResources().getDrawable(R.drawable.all_circle_role_bg);
            background.setColor(rightBarColor);
            rightProgressValue.setBackground(background);
            leftLayoutParams.weight = leftValue;
            rightLayoutParams.weight = rightValue;
        }else if(rightValue == 0){
            GradientDrawable background = (GradientDrawable) context.getResources().getDrawable(R.drawable.all_circle_role_bg);
            background.setColor(leftBarColor);
            leftProgressValue.setBackground(background);
            leftLayoutParams.weight = leftValue;
            rightLayoutParams.weight = rightValue;
        }else{
            GradientDrawable leftBackground = (GradientDrawable) context.getResources().getDrawable(R.drawable.left_circle_role_bg);
            leftBackground.setColor(leftBarColor);
            GradientDrawable rightBackground = (GradientDrawable) context.getResources().getDrawable(R.drawable.right_circle_role_bg);
            rightBackground.setColor(rightBarColor);
            leftProgressValue.setBackground(leftBackground);
            rightProgressValue.setBackground(rightBackground);
            leftLayoutParams.weight = leftValue;
            rightLayoutParams.weight = rightValue;
        }
        leftProgressValue.setLayoutParams(leftLayoutParams);
        rightProgressValue.setLayoutParams(rightLayoutParams);
    }

    public void clearProgressBarValue(int color){

        LinearLayout.LayoutParams leftLayoutParams = (LayoutParams) leftProgressValue.getLayoutParams();
        LinearLayout.LayoutParams rightLayoutParams = (LayoutParams) rightProgressValue.getLayoutParams();
        leftLayoutParams.weight = 1;
        rightLayoutParams.weight = 0;
        GradientDrawable background = (GradientDrawable) context.getResources().getDrawable(R.drawable.all_circle_role_bg);
        background.setColor(color);
        leftProgressValue.setBackground(background);

    }
    public void clearProgressBarValueTx(){
        leftProgressTx.setText("");
        rightProgressTx.setText("");
    }
    public void setProgressBarValueTx(String leftValue,String rightValue){
        leftProgressTx.setText(leftValue+"%");
        rightProgressTx.setText(rightValue+"%");
    }
    public void setLeftBarValue(String tx, String subString, int color){
        TextViewUtils.changeTextColor(leftBarValue, tx, subString, color);
    }
    public void setRightBarValue(String tx, String subString, int color){
        TextViewUtils.changeTextColor(rightBarValue, tx, subString, color);
    }
}
