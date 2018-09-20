package com.huawei.solarsafe.utils.customview;


import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.huawei.solarsafe.R;

import java.lang.ref.WeakReference;

public class LoadingDialog extends Dialog {
    private static final String TAG = "LoadingDialog";
    private final int SHOW_TIME_1000 = 1000;//ms
    private TextView tv;
    private boolean cancelable = true;
    public boolean dismissDialog = false;
    public boolean showOneSecondTime=false;
    private WeakReference<LoadingDialog> softReference;
    public LoadingDialog(Context context) {
        super(context, R.style.loading_dialog_style);
        init();
    }

    public LoadingDialog(Context context, boolean fullscreen) {
        super(context, R.style.loading_dialog_style);
        init();
    }

    @SuppressWarnings("ResourceType")
    private void init() {
        View contentView = View.inflate(getContext(), R.layout.loding_dialog_layout, null);
        softReference = new WeakReference<>(this);
        ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        setContentView(contentView, lp);
        contentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cancelable) {
                    dismiss();
                }
            }
        });
        tv = (TextView) contentView.findViewById(R.id.tv_view);
    }

    @Override
    public void show() {
        if(isShowing()){
            return;
        }
        dismissDialog = false;
        showOneSecondTime = false;
        delayedHandler.sendEmptyMessageDelayed(0,SHOW_TIME_1000);
        super.show();

    }

    @SuppressLint("NewApi")
    @Override
    public void dismiss() {
        if(isShowing()){
            dismissDialog = true;
            if(showOneSecondTime){
                super.dismiss();
                showOneSecondTime = false;
            }
        }else{
            dismissDialog = false;
        }
    }
    private void dismissDialog(){
        super.dismiss();
    }

    @Override
    public void setCancelable(boolean flag) {
        cancelable = flag;
        super.setCancelable(flag);
    }

    @Override
    public void setTitle(CharSequence title) {
        tv.setText(title);
    }

    @Override
    public void setTitle(int titleId) {
        setTitle(getContext().getString(titleId));
    }
    private  Handler delayedHandler = new Handler(){

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            LoadingDialog loadingDialog = softReference.get();
            if(loadingDialog == null){
                return;
            }
            if(loadingDialog.dismissDialog){
                if(loadingDialog.isShowing()){
                    try{
                        loadingDialog.dismissDialog();
                    }catch (IllegalArgumentException e){
                        Log.e(TAG, "handleMessage: "+e.getMessage());
                    }

                }
                loadingDialog.dismissDialog = false;
            }
            loadingDialog.showOneSecondTime = true;

        }
    };

}
