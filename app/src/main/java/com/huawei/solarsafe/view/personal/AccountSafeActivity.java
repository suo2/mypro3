package com.huawei.solarsafe.view.personal;

import android.app.Dialog;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.huawei.solarsafe.R;
import com.huawei.solarsafe.bean.GlobalConstants;
import com.huawei.solarsafe.utils.LocalData;
import com.huawei.solarsafe.utils.SysUtils;
import com.huawei.solarsafe.utils.customview.DialogUtil;
import com.huawei.solarsafe.view.BaseActivity;

import java.util.Locale;
import java.util.Map;

import static android.util.TypedValue.COMPLEX_UNIT_SP;


public class AccountSafeActivity extends BaseActivity implements View.OnClickListener {
    private RelativeLayout rlPwd;
    private RelativeLayout rlLoginRecord;
    private LinearLayout rlPermissionSetting;
    private TextView tvPermission;
    private TextView permissionSetting;

    private Dialog permissionSettingDialog;          //运维权限管理弹窗
    private Map<String, String> permisiionSettingMap; //运维权限管理map


    @Override
    protected int getLayoutId() {
        return R.layout.activity_account_safe;
    }

    @Override
    protected void initView() {
        arvTitle.setText(getResources().getString(R.string.account_safe));
        rlPwd = (RelativeLayout) findViewById(R.id.modify_pwd);
        if(LocalData.getInstance().getIsGuestUser()){
            rlPwd.setVisibility(View.GONE);
        }else {
            rlPwd.setVisibility(View.VISIBLE);
        }
        rlLoginRecord = (RelativeLayout) findViewById(R.id.recent_login_record);
        rlPermissionSetting = (LinearLayout) findViewById(R.id.monitor_permision_setting_layout);
        tvPermission = (TextView) findViewById(R.id.tv_permission);
        String strRightsList = LocalData.getInstance().getRightString();
        if (GlobalConstants.privateSupport != 0 && !TextUtils.isEmpty(strRightsList) &&
                strRightsList.contains("app_mobileOperation")) { //拥有移动运维权限
            rlPermissionSetting.setVisibility(View.VISIBLE);
            permisiionSettingMap= LocalData.getInstance().getPermisiionSetting();
            String permissionCodeStr = permisiionSettingMap.get(GlobalConstants.currentEncryptUserId);
            if("0".equals(permissionCodeStr)){
                tvPermission.setText(R.string.notice_str);
            }else if("1".equals(permissionCodeStr)){
                tvPermission.setText(R.string.close_str);
            }else if("2".equals(permissionCodeStr)){
                tvPermission.setText(R.string.open_str);
            }
        }else {
            rlPermissionSetting.setVisibility(View.GONE);
        }
        permissionSetting = (TextView) findViewById(R.id.permission_setting);
        String language = Locale.getDefault().getLanguage();
        if(language.equals("en")){
            permissionSetting.setTextSize(COMPLEX_UNIT_SP,13);
        }
        initListener();
    }

    private void initListener() {
        rlPwd.setOnClickListener(this);
        rlLoginRecord.setOnClickListener(this);
        rlPermissionSetting.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.modify_pwd:
                SysUtils.startActivity(this, NewChangePswActivity.class);
                break;
            case R.id.recent_login_record:
                SysUtils.startActivity(this, LatestLoginActivity.class);
                break;
            case R.id.monitor_permision_setting_layout:
                if(permissionSettingDialog == null || !permissionSettingDialog.isShowing()){
                    permissionSettingDialog = DialogUtil.showSettingUploadLocationDialog(this, new View.OnClickListener(){
                        @Override
                        public void onClick(View view) {
                            tvPermission.setText(getResources().getString(R.string.close_str));
                            permisiionSettingMap.put(GlobalConstants.currentEncryptUserId, "1");
                            LocalData.getInstance().setPermisiionSetting(permisiionSettingMap);
                            permissionSettingDialog.dismiss();
                        }
                    }, new View.OnClickListener(){
                        @Override
                        public void onClick(View view) {
                            tvPermission.setText(getResources().getString(R.string.open_str));
                            permisiionSettingMap.put(GlobalConstants.currentEncryptUserId, "2");
                            LocalData.getInstance().setPermisiionSetting(permisiionSettingMap);
                            permissionSettingDialog.dismiss();
                        }
                    }, new View.OnClickListener(){
                        @Override
                        public void onClick(View view) {
                            tvPermission.setText(getResources().getString(R.string.notice_str));
                            permisiionSettingMap.put(GlobalConstants.currentEncryptUserId, "0");
                            LocalData.getInstance().setPermisiionSetting(permisiionSettingMap);
                            permissionSettingDialog.dismiss();
                        }
                    });
                }
                break;
        }
    }
}
