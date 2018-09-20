package com.huawei.solarsafe.view.login;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.huawei.solarsafe.R;
import com.huawei.solarsafe.bean.BaseEntity;
import com.huawei.solarsafe.bean.ResultInfo;
import com.huawei.solarsafe.model.login.ILoginModel;
import com.huawei.solarsafe.net.NetRequest;
import com.huawei.solarsafe.net.StringCallback;
import com.huawei.solarsafe.presenter.personal.MyInfoPresenter;
import com.huawei.solarsafe.utils.ToastUtil;
import com.huawei.solarsafe.view.BaseActivity;
import com.huawei.solarsafe.view.personal.IMyInfoView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;

public class FindPwdActivity1 extends BaseActivity implements View.OnClickListener, IMyInfoView {
    private static final String TAG = "FindPwdActivity1";
    private EditText editTextName, codeEditText;
    private Button buttonNext;
    private SimpleDraweeView simpleDraweeView;
    private ImageView codeView;
    private MyInfoPresenter myInfoPresenter;
    private NetRequest request ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        isNeedReLog = false;
        super.onCreate(savedInstanceState);
        myInfoPresenter = new MyInfoPresenter();
        myInfoPresenter.onViewAttached(this);
        request = NetRequest.getInstance();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                requestCodeImage();
            }
        },1000);
    }

    private void requestCodeImage() {
        request.asynPostJsonString(ILoginModel.URL_CODEIMG, "", new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                super.onError(call, e, id);
                Log.e(TAG, "onError: " + e.getMessage());
            }

            @Override
            public void onResponse(Object response, int id) {
                String body = (String) response;
                    try {
                        JSONObject jsonObject = new JSONObject(body);
                        String base64Url = jsonObject.getString("data");
                        base64Url2Img(base64Url);
                    } catch (JSONException e) {
                        Log.e(TAG, "onResponse: " + e.getMessage());
                    }
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_find_pwd1;
    }

    @Override
    protected void initView() {
        tv_left.setText(R.string.close_str);
        tv_left.setOnClickListener(this);
        arvTitle.setText(R.string.sure_username);
        editTextName = (EditText) findViewById(R.id.name_keys);
        buttonNext = (Button) findViewById(R.id.btn_next);
        buttonNext.setOnClickListener(this);
        codeEditText = (EditText) findViewById(R.id.find_pwd1_code_edit);
        codeView = (ImageView) findViewById(R.id.find_pwd1_code_view);
        codeView.setOnClickListener(this);
        simpleDraweeView = (SimpleDraweeView) findViewById(R.id.code_image_view);
        simpleDraweeView.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_left:
                finish();
                break;
            case R.id.btn_next:
                String nameStr = editTextName.getText().toString();
                String codeStr = codeEditText.getText().toString();
                if (TextUtils.isEmpty(nameStr)) {
                    ToastUtil.showMessage(getString(R.string.username_not_null));
                    return;
                }else if (TextUtils.isEmpty(codeStr)){
                    ToastUtil.showMessage(getString(R.string.yz_code_not_null));
                    return;
                }
                requestData();
                break;
            case R.id.find_pwd1_code_view:
                 requestCodeImage();
                break;

        }
    }

    @Override
    public void requestData() {
        Map<String, String> params = new HashMap<>();
        params.put("loginName", editTextName.getText().toString());
        params.put("checkCode", codeEditText.getText().toString());
        myInfoPresenter.doRequestValidAccount(params);
    }

    @Override
    public void getData(BaseEntity baseEntity) {
        if (baseEntity == null) {
            return;
        }
        if (baseEntity instanceof ResultInfo) {
            ResultInfo resultInfo = (ResultInfo) baseEntity;
            if (resultInfo.isSuccess()) {
                Intent intent = new Intent(this, FindPwdActivity2.class);
                intent.putExtra("mail", resultInfo.getData());
                intent.putExtra("loginName", editTextName.getText().toString());
                startActivity(intent);
                finish();
            } else {
                if (resultInfo.getFailCode()==10005){
                    requestCodeImage();
                    ToastUtil.showMessage(R.string.identify_code_error);
                    codeEditText.setText("");
                }else {
                    ToastUtil.showMessage(R.string.user_is_null);
                }
            }
        }
    }
    /**
     * 字节数组流  为了好关流 改成全局变量
     */
    private ByteArrayInputStream bis;
    //将base64字符串转化为图片
    private void base64Url2Img(final String base64Url) {
        if (TextUtils.isEmpty(base64Url)) return;
        try {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    byte[] decode = Base64.decode(base64Url, Base64.NO_WRAP);
                    bis = new ByteArrayInputStream(decode);
                    final Bitmap bitmap = BitmapFactory.decodeStream(bis);
                    if (bitmap != null) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                codeView.setImageBitmap(bitmap);
                            }
                        });
                    }
                }
            }).start();
        } catch (Exception e) {
            Log.e(TAG, "base64Url2Img: " + e.getMessage());
        } finally {
            if (bis != null) {
                try {
                    bis.close();
                } catch (IOException e) {
                    Log.e(TAG, "base64Url2Img: " + e.getMessage());
                }
            }
        }
    }



    @Override
    public void uploadResult(boolean ifSuccess) {}

}
