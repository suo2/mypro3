package com.huawei.solarsafe.utils.customview;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;

import com.huawei.solarsafe.R;
import com.huawei.solarsafe.view.BaseActivity;

/**
 * Create Date: 2017/3/15
 * Create Author: P00438
 * Description :
 */
public class PersonPagePopupWindow extends PopupWindow {
    private View popupView;
    private Button btnPhoto;
    private Button btnSelectAlbum;
    //private Button btnDefault;
    private Button btnCancel;
    private Activity mActivity;
    public String[] needPermissions = {
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA

    };

    public PersonPagePopupWindow(Activity activity, View.OnClickListener listener) {
        super(activity);
        mActivity = activity;
        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        popupView = inflater.inflate(R.layout.person_page_popup_window, null);
        this.setContentView(popupView);
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        this.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        this.setFocusable(true);
        this.setAnimationStyle(R.style.mypopwindow_anim_style);

        btnPhoto = (Button) popupView.findViewById(R.id.bt_pop_camera);
        btnPhoto.setOnClickListener(listener);
        btnSelectAlbum = (Button) popupView.findViewById(R.id.bt_pop_album);
        btnSelectAlbum.setOnClickListener(listener);
        btnCancel = (Button) popupView.findViewById(R.id.bt_pop_cancel);
        btnCancel.setOnClickListener(listener);
    }

    public static int PERMISSIONS_REQUEST_CAMERA = 100;

    @Override
    public void showAtLocation(View parent, int gravity, int x, int y) {

        if (ActivityCompat.checkSelfPermission(mActivity, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            super.showAtLocation(parent, gravity, x, y);
        } else {
            if (mActivity instanceof BaseActivity) {
                BaseActivity baseActivity = (BaseActivity) mActivity;
//                baseActivity.checkPermissions(baseActivity.needPermissions);
                baseActivity.checkPermissions(needPermissions);
            }
        }
    }
}
