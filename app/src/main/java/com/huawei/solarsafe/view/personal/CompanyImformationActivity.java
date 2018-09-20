package com.huawei.solarsafe.view.personal;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.huawei.solarsafe.R;
import com.huawei.solarsafe.bean.BaseEntity;
import com.huawei.solarsafe.bean.GlobalConstants;
import com.huawei.solarsafe.bean.user.info.CompanyImformationBean;
import com.huawei.solarsafe.presenter.personal.CompanyImformationPresenter;
import com.huawei.solarsafe.utils.LocalData;
import com.huawei.solarsafe.utils.ToastUtil;
import com.huawei.solarsafe.utils.Utils;
import com.huawei.solarsafe.view.BaseActivity;

import java.net.URL;

/**
 * Created by p00507
 * on 2017/4/10.
 */
public class CompanyImformationActivity extends BaseActivity<CompanyImformationPresenter> implements PimformationView, View.OnClickListener {
    private TextView tv_left, tv_title, tv_name, tv_imformation, tv_address, tv_strength, company_filingno;
    private CompanyImformationPresenter imformationPresenter;
    private CompanyImformationBean imformationBean;
    private static final int MY_PERMISSIONS_REQUEST_CALL_PHONE = 1;
    private LinearLayout ll_company_filingno;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_company_imformation;
    }

    @Override
    protected void initView() {
        tv_left = (TextView) findViewById(R.id.tv_left);
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_title.setText(R.string.qiye_info_title);
        tv_left.setOnClickListener(this);
        tv_name = (TextView) findViewById(R.id.company_name);
        tv_imformation = (TextView) findViewById(R.id.contact_information);
        tv_address = (TextView) findViewById(R.id.company_address);
        tv_strength = (TextView) findViewById(R.id.company_strength);
        company_filingno = (TextView) findViewById(R.id.company_filingno);
        ll_company_filingno = (LinearLayout) findViewById(R.id.ll_company_filingno);
        imformationPresenter = new CompanyImformationPresenter();
        imformationPresenter.onViewAttached(this);
        imformationPresenter.getComImformation();

        tv_imformation.setOnClickListener(this);
        tv_strength.setOnClickListener(this);

    }

    @Override
    public void getDataImformationSuccess(BaseEntity baseEntity) {
        if (baseEntity instanceof CompanyImformationBean) {
            imformationBean = (CompanyImformationBean) baseEntity;
            imformationBean = imformationBean.getImformationBean();
        }
        if (imformationBean != null) {
            tv_name.setText(imformationBean.getCompanyName());
            tv_imformation.setText(imformationBean.getCompanyTel());
            tv_address.setText(imformationBean.getCompanyAddress());
            tv_strength.setText(imformationBean.getCompanyWebsite());

            if (GlobalConstants.ADMIN.equals(LocalData.getInstance().getLoginName())) {
                ll_company_filingno.setVisibility(View.VISIBLE);
                company_filingno.setText(imformationBean.getFilingNo());
            } else {
                ll_company_filingno.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void getDataImformationFailed(String msg) {
        ToastUtil.showMessage(msg);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {//电话号码
            case R.id.tv_left:
                finish();
                break;
            case R.id.contact_information:
                if (imformationBean == null) {
                    return;
                }
                if (imformationBean.getCompanyTel() == null) return;
                final AlertDialog.Builder builder = new AlertDialog.Builder(CompanyImformationActivity.this);
                builder.setTitle(getString(R.string.hint));
                builder.setMessage(getString(R.string.current_number) + imformationBean.getCompanyTel());
                builder.setNegativeButton(getString(R.string.cancel_defect), null);
                builder.setPositiveButton(R.string.call, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (imformationBean.getCompanyTel() != null && imformationBean.getCompanyTel().trim().length() > 0) {
                            // 检查是否获得了权限（Android6.0运行时权限）
                            if (ContextCompat.checkSelfPermission(CompanyImformationActivity.this, Manifest.permission.CALL_PHONE)
                                    != PackageManager.PERMISSION_GRANTED) {
                                // 没有获得授权，申请授权
                                if (ActivityCompat.shouldShowRequestPermissionRationale(CompanyImformationActivity.this,
                                        Manifest.permission.CALL_PHONE)) {
                                    //如果app之前请求过该权限,被用户拒绝, 这个方法就会返回true.
                                    //如果用户之前拒绝权限的时候勾选了对话框中”Don’t ask again”的选项,那么这个方法会返回false.
                                    //如果设备策略禁止应用拥有这条权限, 这个方法也返回false.
                                    // 弹窗需要解释为何需要该权限，再次请求授权
                                    Toast.makeText(CompanyImformationActivity.this, R.string.shouquan, Toast.LENGTH_LONG).show();
                                    // 帮跳转到该应用的设置界面，让用户手动授权
                                    Intent intent1 = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                    Uri uri = Uri.fromParts("package", getPackageName(), null);
                                    intent1.setData(uri);
                                    startActivity(intent1);
                                } else {
                                    // 不需要解释为何需要该权限，直接请求授权
                                    ActivityCompat.requestPermissions(CompanyImformationActivity.this,
                                            new String[]{Manifest.permission.CALL_PHONE}, MY_PERMISSIONS_REQUEST_CALL_PHONE);
                                }
                            } else {
                                // 已经获得授权，可以打电话
                                Utils.callPhone(CompanyImformationActivity.this,imformationBean.getCompanyTel());
                            }
                            dialogInterface.dismiss();
                        }
                    }
                });
                builder.show();
                break;
            case R.id.company_strength:
                try {
                    String companyStrength=tv_strength.getText().toString();
                    if (TextUtils.isEmpty(companyStrength)){
                        return;
                    }else if (companyStrength.startsWith("http")){
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(tv_strength.getText().toString()));
                        startActivity(intent);
                    }else{
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://" + tv_strength.getText().toString()));
                        startActivity(intent);
                    }
                } catch (Exception e) {
                    Log.e("CompanyImformation", "onClick: "+e.getMessage());
                    ToastUtil.showMessage(getString(R.string.jump_page_error));
                }
                break;
        }
    }
}
