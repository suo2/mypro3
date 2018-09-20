package com.huawei.solarsafe.view.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import com.huawei.solarsafe.MyApplication;
import com.huawei.solarsafe.R;
import com.huawei.solarsafe.bean.GlobalConstants;
import com.huawei.solarsafe.utils.customview.AlertDialog;

public class EmptyActivity extends Activity {


    private AlertDialog reLoginDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_empty);
        GlobalConstants.isLogout = false;
        String msg = getResources().getString(R.string.long_time_no_login);
        Intent intent = getIntent();
        if (intent!=null){
             msg = intent.getStringExtra("msg");
            if (TextUtils.isEmpty(msg)){
               msg=getString(R.string.other_device_login);
            }
        }
        showDialog(msg);
    }

    private View.OnClickListener  listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Activity currentActivity = MyApplication.getApplication().getCurrentActivity();
            MyApplication.getApplication().finishAllActivityNotCurrentActivity(currentActivity);
            Intent intent = new Intent(EmptyActivity.this, LoginActivity.class);
            MyApplication.showRootDialog = false;
            startActivity(intent);
            EmptyActivity.this.finish();
            MyApplication.getApplication().delayedFinishActivity(currentActivity);//解决跳转黑屏
        }
    };

    private void  showDialog(String msg){
        if (reLoginDialog==null){
            reLoginDialog=new AlertDialog(this).builder()
                    .setMsg(msg)
                    .setCancelable(false)
                    .setNegativeButton(getString(R.string.determine), true, listener).create();
        }
        reLoginDialog.setMsg(msg);
        reLoginDialog.show();
    }
}
