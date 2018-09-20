package com.huawei.solarsafe.view.personal;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.InputFilter;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.huawei.solarsafe.MyApplication;
import com.huawei.solarsafe.R;
import com.huawei.solarsafe.bean.BaseEntity;
import com.huawei.solarsafe.bean.GlobalConstants;
import com.huawei.solarsafe.bean.ResultInfo;
import com.huawei.solarsafe.bean.user.info.UserInfo;
import com.huawei.solarsafe.net.NetRequest;
import com.huawei.solarsafe.presenter.personal.MyInfoPresenter;
import com.huawei.solarsafe.utils.ImageFactory;
import com.huawei.solarsafe.utils.LocalData;
import com.huawei.solarsafe.utils.PicUtils;
import com.huawei.solarsafe.utils.SysUtils;
import com.huawei.solarsafe.utils.ToastUtil;
import com.huawei.solarsafe.utils.Utils;
import com.huawei.solarsafe.utils.customview.AlertDialog;
import com.huawei.solarsafe.utils.customview.LoadingDialog;
import com.huawei.solarsafe.utils.customview.PersonPagePopupWindow;
import com.huawei.solarsafe.view.BaseActivity;
import com.huawei.solarsafe.view.login.LoginActivity;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import static com.huawei.solarsafe.view.maintaince.defects.NewDefectActivity.CHOOSE_PHOTO;
import static com.huawei.solarsafe.view.maintaince.defects.NewDefectActivity.TAKE_PHOTO;

/**
 * Created by P00438 on 2017/2/16.
 */
public class NewChangePersonInfoActivity extends BaseActivity<MyInfoPresenter> implements IMyInfoView,
        View.OnClickListener,View.OnFocusChangeListener {
    private static final String TAG = "NewChangePersonInfoActi";
    private PersonPagePopupWindow window;
    private ImageView imageView;
    private MyInfoPresenter myInfoPresenter;
    private EditText etName, etMail, etPhone, etUserName;
    private Button saveButton;
    private SimpleDraweeView myImageView;
    private Uri mFileUri;
    //压缩前的图片的存放路径
    private String mFilePath;
    private String mCompressPath;  //压缩后的图片的存放路径
    private String userNameString;
    private String nameString;
    private String emilString;
    private String phoString;
    private LinearLayout llHeadPortraits;
    private long tTime=0;

    //用户信息图片存放根目录
    private String userDir = Environment.getExternalStorageDirectory().getPath() + File.separator + "fusionHome" + File.separator + "user";
    /**
     * 输入发管理
     */
    private InputMethodManager inputMethodManager;
    private AlertDialog alertDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myInfoPresenter = new MyInfoPresenter();
        myInfoPresenter.onViewAttached(this);
        Map<String, String> params = new HashMap<>();
        params.put("userid",String.valueOf(GlobalConstants.userId));
        myInfoPresenter.doRequestUserInfo(params);
        inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        try {
            tTime=getIntent().getLongExtra("tTime",0);
        } catch (Exception e) {
            Log.e(TAG, "onCreate: " + e.getMessage());
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_new_change_person_info;
    }

    @Override
    protected void initView() {
        imageView = (ImageView) findViewById(R.id.change_view_to);
        tv_title.setText(R.string.personal_details);
        tv_left.setOnClickListener(this);
        tv_right.setOnClickListener(this);
        tv_left.setOnClickListener(this);
        tv_right.setText(getString(R.string.update));
        if(LocalData.getInstance().getIsGuestUser()){
            tv_right.setVisibility(View.GONE);
        }else {
            tv_right.setVisibility(View.VISIBLE);
        }
        etMail = (EditText) findViewById(R.id.et_mail);
        etMail.setOnFocusChangeListener(this);
        etPhone = (EditText) findViewById(R.id.et_phone);
        etPhone.setOnFocusChangeListener(this);
        etName = (EditText) findViewById(R.id.et_name);
        etName.setOnFocusChangeListener(this);
        etUserName = (EditText) findViewById(R.id.et_username);

        etName.setFilters(new InputFilter[]{Utils.getEmojiFilter()});
        etUserName.setFilters(new InputFilter[]{Utils.getEmojiFilter()});
        saveButton = (Button) findViewById(R.id.bt_save);
        saveButton.setOnClickListener(this);
        window = new PersonPagePopupWindow(this, this);
        myImageView = (SimpleDraweeView) findViewById(R.id.my_image_view);
        llHeadPortraits= (LinearLayout) findViewById(R.id.llHeadPortraits);
        llHeadPortraits.setOnClickListener(this);
        llHeadPortraits.setClickable(false);
    }

    @Override
    public void onFocusChange(View view, boolean b) {
        switch (view.getId()){
            case R.id.et_mail:
                changeEdit(etMail,b,emilString);
                break;
            case R.id.et_phone:
                changeEdit(etPhone,b,phoString);
                break;
            case R.id.et_name:
                changeEdit(etName,b,nameString);
                break;
        }

    }


    private void changeEdit(EditText view,boolean b ,String s){
        String toString = view.getText().toString();
        if (b){
            if (!TextUtils.isEmpty(toString)){
                if (toString.contains("*")){
                    view.setText("");
                }
            }else {
                if (!TextUtils.isEmpty(s)){
                    view.setText(s);
                }
            }

        }else {
            if (!TextUtils.isEmpty(s)){
                if (TextUtils.isEmpty(toString)){
                    view.setText(s);
                }
            }
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        //不能再xml中设置获取焦点，因为当去拍照时返回后会让其失去焦点
        if (saveButton.getVisibility() == View.VISIBLE) {
            etUserName.setFocusable(true);
            etUserName.setFocusableInTouchMode(true);
            etName.setFocusable(true);
            etName.setFocusableInTouchMode(true);
            etPhone.setFocusable(true);
            etPhone.setFocusableInTouchMode(true);
            etMail.setFocusable(true);
            etMail.setFocusableInTouchMode(true);
        } else {
            etUserName.setFocusable(false);
            etName.setFocusable(false);
            etPhone.setFocusable(false);
            etMail.setFocusable(false);
        }
    }

    @Override
    public void requestData() {

    }

    @Override
    public void getData(BaseEntity baseEntity) {
        dismissLoading();
        if (baseEntity == null) {
            return;
        }
        //获取用户信息
        if (baseEntity instanceof UserInfo) {
            UserInfo userInfo = (UserInfo) baseEntity;
            if (userInfo.getUserInfo() == null) {
                return;
            }
            userNameString = userInfo.getUserInfo().getLoginName();
            nameString = userInfo.getUserInfo().getUserName();
            emilString = userInfo.getUserInfo().getMail();
            phoString = userInfo.getUserInfo().getTel();
            etUserName.setText(userNameString);
            etName.setText(nameString);
            etPhone.setText(phoString);
            etMail.setText(emilString);
            if (tTime==0){
                Utils.downloadPic(userInfo.getUserid()+"",String.valueOf(GlobalConstants.userId),System.currentTimeMillis(),myImageView);
            }else{
                Utils.downloadPic(userInfo.getUserid()+"",String.valueOf(GlobalConstants.userId),tTime,myImageView);
            }
        }
        //修改用户信息
        else if (baseEntity instanceof ResultInfo) {
            ResultInfo resultInfo = (ResultInfo) baseEntity;
            if (resultInfo.isSuccess()) {
                MyApplication.getApplication().exit();
                ToastUtil.showMessage(MyApplication.getContext().getResources().getString(R.string.massage_modify_success));
                SysUtils.startActivity(this, LoginActivity.class);
            } else if(resultInfo.getFailCode() != 444){
                Toast.makeText(this, resultInfo.getRetMsg(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void uploadResult(boolean ifSuccess) {
        dismissLoading();
        if (ifSuccess) {
            Toast.makeText(this, R.string.head_upload_success, Toast.LENGTH_SHORT).show();
            //修改bug  返回上个界面没有调用finish  修改人:江东
            finish();
        } else {
            ToastUtil.showMessage(getString(R.string.head_upload_fail));
        }
        //使用过后将内容及时清理
        mCompressPath = null;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.llHeadPortraits:
                inputMethodManager.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);
                //弹出拍照弹窗
                window.showAtLocation(findViewById(R.id.ll_personal_page), Gravity.CENTER, 0, 0);
                break;
            case R.id.bt_pop_camera:
                window.dismiss();
                Intent intent1 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                File file = getFile();
                mFileUri = Uri.fromFile(file);
                intent1.putExtra(MediaStore.EXTRA_OUTPUT, mFileUri);
                startActivityForResult(intent1, TAKE_PHOTO);
                break;
            case R.id.bt_pop_album:
                window.dismiss();
                Intent intent2 = new Intent(Intent.ACTION_PICK);
                intent2.setData(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent2, CHOOSE_PHOTO);
                break;
            case R.id.bt_pop_cancel:
                window.dismiss();
                break;
            case R.id.bt_save:
                if (TextUtils.isEmpty(etUserName.getText().toString().trim())) {
                    Toast.makeText(this, R.string.username_no_empty, Toast.LENGTH_SHORT).show();
                    return;
                } else if (TextUtils.isEmpty(etName.getText().toString().trim())) {
                    Toast.makeText(this, R.string.name_nots_empty, Toast.LENGTH_SHORT).show();
                    return;
                } else if (TextUtils.isEmpty(etPhone.getText().toString().trim())) {
                    Toast.makeText(this, R.string.phone_nots_empty, Toast.LENGTH_SHORT).show();
                    return;
                }
                requestModifyUserInfo();
                break;
            case R.id.tv_right:
                if (saveButton.getVisibility() == View.VISIBLE) {
                    Utils.closeSoftKeyboard(NewChangePersonInfoActivity.this);
                    tv_right.setText(getString(R.string.update));
                    etUserName.setFocusable(false);
                    etName.setFocusable(false);
                    etPhone.setFocusable(false);
                    etMail.setFocusable(false);
                    saveButton.setVisibility(View.GONE);
                    imageView.setVisibility(View.GONE);
                    llHeadPortraits.setClickable(false);
                    etUserName.setText(userNameString);
                    etName.setText(nameString);
                    etPhone.setText(phoString);
                    etMail.setText(emilString);
                    etMail.setHint("");

                } else {
                    tv_right.setText(getString(R.string.cancel_defect));
                    etUserName.setFocusable(true);
                    etUserName.setFocusableInTouchMode(true);
                    etName.setFocusable(true);
                    etName.setFocusableInTouchMode(true);
                    etPhone.setFocusable(true);
                    etPhone.setFocusableInTouchMode(true);
                    etMail.setFocusable(true);
                    etMail.setFocusableInTouchMode(true);
                    etMail.setHint(getResources().getString(R.string.please_input_email));
                    saveButton.setVisibility(View.VISIBLE);
                    imageView.setVisibility(View.VISIBLE);
                    llHeadPortraits.setClickable(true);
                }
                break;
            case R.id.tv_left:
                if(tv_right.getText().toString().equals(getString(R.string.cancel_defect))){
                    backStep();
                }else{
                    finish();
                }
                break;
            default:
                break;
        }
    }

    /**
     * 创建存储拍摄照片的文件
     *
     * @return 创建的文件
     */
    private File getFile() {
        long time = System.currentTimeMillis();
        String fileName = time + "_user.jpeg";
        File dir = getDirFile();
        File file = new File(dir, fileName);
        mFilePath = file.getAbsolutePath();
        return file;
    }

    /**
     * 创建存储照片的父目录
     *
     * @return 创建的目录
     */
    private File getDirFile() {
        String path;
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            path = Environment.getExternalStorageDirectory().getAbsolutePath();
        } else {
            path = getCacheDir().getAbsolutePath();
        }
        //【安全特性编号】I1 OR_SmartPVMS60_PVMS830_0004_F01 部分敏感信息APP不做长期存储 【修改人】zhaoyufeng
        path = path + File.separator + "fusionHome" + File.separator + "Picture" + File.separator + GlobalConstants.userId + File.separator + "user";
        // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0004_F01_Android_S02  敏感信息检查-日志中不能包含敏感信息    【修改人】：zhaoyufeng
        File dir = new File(path);
        if (!dir.exists()) {
            boolean mkdir = dir.mkdirs();
            if (!mkdir) {
                return null;
            }
        }
        return dir;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CHOOSE_PHOTO && resultCode == Activity.RESULT_OK && data != null) {
            //相册选择照片处理
            Uri selectedImage = data.getData();
            String[] filePathColumns = {MediaStore.Images.Media.DATA};
            Cursor c = getContentResolver().query(selectedImage, filePathColumns, null, null, null);
            if (null != c) {
                showLoading(this);
                c.moveToFirst();
                int columnIndex = c.getColumnIndex(filePathColumns[0]);
                mFilePath = c.getString(columnIndex);
                c.close();
                new CompressFile().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                Toast.makeText(this, R.string.wait_for_images_compressed, Toast.LENGTH_SHORT).show();
            }
        } else if (requestCode == TAKE_PHOTO && resultCode == Activity.RESULT_OK) {
            //相机拍照处理
            showLoading(this);
            new CompressFile().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
            Toast.makeText(this, R.string.wait_for_images_compressed, Toast.LENGTH_SHORT).show();
        }
    }



    /**
     * 后台图片压缩任务
     */
    public class CompressFile extends AsyncTask<Object, Object, Boolean> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Boolean doInBackground(Object... params) {
            //优化压缩流程，防止高像素手机OOM
            InputStream is = null;
            try {
                if(mFilePath != null) {
                    is = new FileInputStream(mFilePath);
                }
            } catch (FileNotFoundException e) {
                Log.e(TAG, "doInBackground: " + e.getMessage());
            }
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.outWidth = 480;
            options.outHeight = 800;
            options.inPreferredConfig = Bitmap.Config.RGB_565;
            options.inPurgeable = true;
            options.inSampleSize = 4;
            options.inInputShareable = true;
            Bitmap targetBitmap = BitmapFactory.decodeStream(is, null, options);
            if (targetBitmap == null) {
                return false;
            }

            int degree = Utils.getPictureDegree(mFilePath);

            if (degree != 0) {
                targetBitmap = Utils.rotaingPic(degree, targetBitmap);
            }
            Bitmap bitmap = ImageFactory.ratio(targetBitmap, 480, 800);
            mCompressPath = saveFile2(bitmap, System.currentTimeMillis() + "_user.jpeg", userDir);
            return true;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            dismissLoading();
            if (aBoolean) {
                //压缩成功
                Bitmap thumbnailBitmap = PicUtils.getImageThumbnail(mCompressPath, 120, 120);
                if (thumbnailBitmap != null) {
                    myImageView.setImageBitmap(thumbnailBitmap);
                    Toast.makeText(NewChangePersonInfoActivity.this, R.string.image_compression_succeeded, Toast.LENGTH_SHORT).show();
                } else {
                    //压缩失败
                    Toast.makeText(NewChangePersonInfoActivity.this, R.string.pic_compress_failed, Toast.LENGTH_SHORT).show();
                }
            } else {
                //压缩失败
                Toast.makeText(NewChangePersonInfoActivity.this, R.string.pic_compress_failed, Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        String path;
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            path = Environment.getExternalStorageDirectory().getAbsolutePath();
        } else {
            path = getCacheDir().getAbsolutePath();
        }
        final String temp = path;
        new Thread(new Runnable() {
            @Override
            public void run() {
                Utils.deleteDirectory(temp + File.separator + "fusionHome" + File.separator + "user");
            }
        }).start();
        //【安全特性编号】I1 OR_SmartPVMS60_PVMS830_0004_F01 部分敏感信息APP不做长期存储 【修改人】zhaoyufeng
        Utils.deletePicDirectory();
    }

    public static String saveFile2(Bitmap bm, String fileName, String dir) {
        String path = dir;

        File dirFile = new File(path);
        if (!dirFile.exists()) {
            boolean mkdir=dirFile.mkdirs();
            if(!mkdir){
                return null;
            }
        }
        File myCaptureFile = new File(path, fileName);
        BufferedOutputStream bos = null;
        try {
            bos = new BufferedOutputStream(new FileOutputStream(myCaptureFile));
            bm.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            bos.flush();
        } catch (Exception e) {
            Log.e(MyApplication.TAG, "saveFile error", e);
        } finally {
            try {
                if (bos != null) {
                    bos.close();
                }
            } catch (IOException ioe) {
                Log.e(MyApplication.TAG, "saveFile error", ioe);
            }
        }
        if (!bm.isRecycled()) {
            bm.recycle();
        }
        return myCaptureFile.getAbsolutePath();
    }


    /**
     * 发送修改用户信息的请求
     */
    private void requestModifyUserInfo() {
        boolean isChanged = false;
        HashMap<String, String> params = new HashMap<>();
        String loginName = etUserName.getText().toString().trim();
        String userName = etName.getText().toString().trim();
        String phone = etPhone.getText().toString().trim();
        String email = etMail.getText().toString().trim();
        //修改只改头像跳转登录界面的问题  修改人:江东
        if (!loginName.equals(userNameString)) {
            params.put("loginName", loginName);
        }
        if (!userName.equals(nameString)) {
            params.put("userName", userName);
        }
        if (!phone.equals(phoString)) {
            if (Utils.checkInPutIsOk(etPhone,25,getString(R.string.tel_len_max_25))){
                return;
            }
            params.put("tel", phone);
        }
        if (!email.equals(emilString)) {
            //将验证邮箱的操作放在这里  避免校验模糊化的邮箱    修改人:江东
            if (!TextUtils.isEmpty(email)) {
                if (!Utils.emailValidation(email)) {
                    ToastUtil.showMessage(R.string.please_input_corret_email);
                    return;
                }
            }
            params.put("mail", email);
        }
        //  判断其中任何一项是否改动过，大于1则判断为改动过信息则将改动后的信息put进去 修改人:江东
        showLoading(this);
        if (params.size() > 0) {
            isChanged = true;
            myInfoPresenter.doRequestChangeUserInfo(params);
            //【安全特性】及时清除敏感信息    【修改人】赵宇凤
        }
        params.put("userid",String.valueOf(GlobalConstants.userId));
        if (mCompressPath != null) {
            myInfoPresenter.uploadAttachment(mCompressPath, params);
            //【安全特性】及时清除敏感信息    【修改人】赵宇凤
            mCompressPath = null;
        } else {
            if (!isChanged) {
                dismissLoading();
                ToastUtil.showMessage(getResources().getString(R.string.there_is_no_modification_please_confirm));  //头像  个人信息 都没有修改过的情况
            }
        }
    }

    private LoadingDialog loadingDialog;

    public void showLoading(Context context) {
        if (loadingDialog == null) {
            loadingDialog = new LoadingDialog(context);
            loadingDialog.show();
        } else if (!loadingDialog.isShowing()) {
            loadingDialog.show();
        }
    }

    public void dismissLoading() {
        if (loadingDialog != null && loadingDialog.isShowing()) {
            loadingDialog.dismiss();
            loadingDialog = null;
        }
    }
    @Override
    public void onBackPressed() {
        if(tv_right.getText().toString().equals(getString(R.string.cancel_defect))){
            backStep();
        }else{
            finish();
        }
    }
    /**
     * 取消方法
     */
    public void backStep() {
        try {
            alertDialog = new AlertDialog(NewChangePersonInfoActivity.this).builder()
                    .setMsg(getString(R.string.cancel_save_str))
                    .setCancelable(false)
                    .setPositiveButton(getResources().getString(R.string.cancel), new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            alertDialog.dismiss();
                        }
                    })
                    .setNegativeButton(getResources().getString(R.string.sure), false, new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            finish();
                        }
                    });
            alertDialog.show();
        } catch (Exception e) {
            Log.e("Exception", e.toString());
        }

    }

    /**
     * 取消方法
     */
    public void cancelStep() {
        try {
            alertDialog = new com.huawei.solarsafe.utils.customview.AlertDialog(NewChangePersonInfoActivity.this).builder()
                    .setMsg(getString(R.string.cancel_save_str))
                    .setCancelable(false)
                    .setPositiveButton(getResources().getString(R.string.cancel), new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            alertDialog.dismiss();
                        }
                    })
                    .setNegativeButton(getResources().getString(R.string.sure), false, new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            finish();
                        }
                    });

            alertDialog.show();
        } catch (Exception e) {
            Log.e("Exception", e.toString());
        }

    }
}
