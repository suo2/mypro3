package com.huawei.solarsafe.utils;

import android.content.Context;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.huawei.solarsafe.R;
import com.huawei.solarsafe.view.CustomViews.dialogplus.DialogPlus;
import com.huawei.solarsafe.view.CustomViews.dialogplus.OnClickListener;
import com.huawei.solarsafe.view.CustomViews.dialogplus.ViewHolder;

/**
 * <pre>
 *     author: Tzy
 *     time  : 2018/3/5
 *     desc  : 对话框工具类
 * </pre>
 */
public class DialogUtils {

    /**
     * 创建带2个按钮的对话框
     * @param context
     * @param cancelable 是否可以点击对话框外部取消对话框
     * @param title 标题
     * @param msg 消息
     * @param negativeStr 左按钮文本
     * @param positiveStr 右按钮文本
     * @param negativeClickListener 左按钮点击事件
     * @param positiveClickListener 右按钮点击事件
     */
    private static DialogPlus createTwoBtnDialog(Context context, boolean cancelable, String title, String msg, String negativeStr, String positiveStr, final OnClickListener negativeClickListener, final OnClickListener positiveClickListener){

        final DialogPlus dialog=DialogPlus.newDialog(context)
                .setContentHolder(new ViewHolder(R.layout.dialog_center_default))
                .setGravity(Gravity.CENTER)
                .setCancelable(cancelable)
                .setContentBackgroundResource(R.drawable.shape_dialog_center_default_bg)
                .setMargin(Utils.dp2Px(context,50f),0,Utils.dp2Px(context,50f),0)
                .setPadding(0,Utils.dp2Px(context,30f),0,0)
                .create();

        View view=dialog.getHolderView();
        TextView tvTitle= (TextView) view.findViewById(R.id.tvTitle);
        TextView tvMsg= (TextView) view.findViewById(R.id.tvMsg);
        Button btnNegative= (Button) view.findViewById(R.id.btnNegative);
        Button btnPositive= (Button) view.findViewById(R.id.btnPositive);

        if (TextUtils.isEmpty(title)){
            tvTitle.setVisibility(View.GONE);
        }else{
            tvTitle.setText(title);
        }

        tvMsg.setText(msg);

        if (!TextUtils.isEmpty(negativeStr)){
            btnNegative.setText(negativeStr);
        }
        if (!TextUtils.isEmpty(positiveStr)){
            btnPositive.setText(positiveStr);
        }

        if (negativeClickListener==null){
            btnNegative.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });
        }else{
            btnNegative.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    negativeClickListener.onClick(dialog,view);
                }
            });
        }

        btnPositive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                positiveClickListener.onClick(dialog,view);
            }
        });

        return dialog;
    }

    /**
     * 显示带2个按钮的对话框
     * @param context
     * @param cancelable 是否可以点击对话框外部取消对话框
     * @param title 标题
     * @param msg 消息
     * @param positiveClickListener 右按钮点击事件
     */
    public static void showTwoBtnDialog(Context context,boolean cancelable,String title, String msg, OnClickListener positiveClickListener){

        DialogPlus dialog=createTwoBtnDialog(context,cancelable,title,msg,null,null,null,positiveClickListener);
        dialog.show();

    }

    /**
     * 显示带2个按钮的对话框
     * @param context
     * @param msg 消息
     * @param positiveClickListener 右按钮点击事件
     */
    public static void showTwoBtnDialog(Context context, String msg,OnClickListener positiveClickListener){

        DialogPlus dialog=createTwoBtnDialog(context,true,null,msg,null,null,null,positiveClickListener);
        dialog.show();

    }

    /**
     * 创建单个按钮的对话框
     * @param context
     * @param cancelable 是否可以点击对话框外部取消对话框
     * @param title 标题
     * @param msg 消息
     * @param btnStr 按钮文本
     * @param btnClickListener 按钮点击事件
     * @return
     */
    private static DialogPlus createSingleBtnDialog(Context context, boolean cancelable, String title, String msg, String btnStr, final OnClickListener btnClickListener){

        final DialogPlus dialog=DialogPlus.newDialog(context)
                .setContentHolder(new ViewHolder(R.layout.dialog_center_default))
                .setGravity(Gravity.CENTER)
                .setCancelable(cancelable)
                .setContentBackgroundResource(R.drawable.shape_dialog_center_default_bg)
                .setMargin(Utils.dp2Px(context,50f),0,Utils.dp2Px(context,50f),0)
                .setPadding(0,Utils.dp2Px(context,30f),0,0)
                .create();

        View view=dialog.getHolderView();
        View divVertical=view.findViewById(R.id.divVertical);
        divVertical.setVisibility(View.GONE);
        TextView tvTitle= (TextView) view.findViewById(R.id.tvTitle);
        TextView tvMsg= (TextView) view.findViewById(R.id.tvMsg);
        Button btnNegative= (Button) view.findViewById(R.id.btnNegative);
        btnNegative.setVisibility(View.GONE);
        Button btnPositive= (Button) view.findViewById(R.id.btnPositive);

        if (TextUtils.isEmpty(title)){
            tvTitle.setVisibility(View.GONE);
        }else{
            tvTitle.setText(title);
        }

        tvMsg.setText(msg);

        if (!TextUtils.isEmpty(btnStr)){
            btnPositive.setText(btnStr);
        }

        if (btnClickListener==null){
            btnPositive.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });
        }else{
            btnPositive.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    btnClickListener.onClick(dialog,view);
                }
            });
        }

        return dialog;
    }

    /**
     * 显示单个按钮的对话框
     * @param context
     * @param cancelable 是否可以点击对话框外部取消对话框
     * @param msg 消息
     * @param btnClickListener 按钮点击事件
     */
    public static void showSingleBtnDialog(Context context,boolean cancelable, String msg, OnClickListener btnClickListener){
        DialogPlus dialog=createSingleBtnDialog(context,cancelable,null, msg,null,btnClickListener);
        dialog.show();
    }

    /**
     * 显示单个按钮的对话框
     * @param context
     * @param msg 消息
     */
    public static void showSingleBtnDialog(Context context, String msg){
        DialogPlus dialog=createSingleBtnDialog(context,false,null, msg,null,null);
        dialog.show();
    }


}
