package com.huawei.solarsafe.view.maintaince.defects;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;

import com.huawei.solarsafe.R;

/**
 * Created by P00319 on 2016/10/18.
 * 添加照片选择框（拍照 or 相册）
 */
public class SelectPicPopupWindow extends PopupWindow {
    private Button selectCamera;
    private Button selectAlbum;
    private Button cancel;
    private View popupView;

    public SelectPicPopupWindow(Activity context, View.OnClickListener onClickListener){
        super(context);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        popupView = inflater.inflate(R.layout.pic_select_popup_window, null);
        selectCamera = (Button) popupView.findViewById(R.id.bt_pop_camera);
        selectAlbum = (Button) popupView.findViewById(R.id.bt_pop_album);
        cancel = (Button) popupView.findViewById(R.id.bt_pop_cancel);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        selectAlbum.setOnClickListener(onClickListener);
        selectCamera.setOnClickListener(onClickListener);


        this.setContentView(popupView);
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        this.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        this.setFocusable(true);
        this.setAnimationStyle(R.style.mypopwindow_anim_style);
    }
}
