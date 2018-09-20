package com.huawei.solarsafe.view.homepage.station;

import android.Manifest;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.drawee.controller.ControllerListener;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.image.ImageInfo;
import com.google.gson.Gson;
import com.huawei.solarsafe.MyApplication;
import com.huawei.solarsafe.R;
import com.huawei.solarsafe.bean.BaseEntity;
import com.huawei.solarsafe.bean.GlobalConstants;
import com.huawei.solarsafe.bean.station.ChangeStationInfo;
import com.huawei.solarsafe.bean.stationmagagement.ChangeStationResultBean;
import com.huawei.solarsafe.bean.stationmagagement.ChangeStationResultInfo;
import com.huawei.solarsafe.bean.stationmagagement.StationListRequest;
import com.huawei.solarsafe.bean.stationmagagement.StationManagementListInfo;
import com.huawei.solarsafe.bean.stationmagagement.UpdateStationOtherInfoReq;
import com.huawei.solarsafe.presenter.stationmanagement.ChangeStationPresenter;
import com.huawei.solarsafe.presenter.stationmanagement.CreateStationPresenter;
import com.huawei.solarsafe.utils.PicUtils;
import com.huawei.solarsafe.utils.Utils;
import com.huawei.solarsafe.utils.customview.PersonPagePopupWindow;
import com.huawei.solarsafe.view.stationmanagement.ICreateStationView;
import com.huawei.solarsafe.view.stationmanagement.changestationinfo.IChangeStationView;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import static com.huawei.solarsafe.view.maintaince.defects.NewDefectActivity.CHOOSE_PHOTO;
import static com.huawei.solarsafe.view.maintaince.defects.NewDefectActivity.TAKE_PHOTO;

/**
 * Created by P00708 on 2018/1/17.
 */

public class StationHeadPhotoActivity extends FragmentActivity implements View.OnClickListener,ICreateStationView,IChangeStationView,View.OnTouchListener {

    private TextView tv_left;
    private TextView tv_right;
    private SimpleDraweeView stationPhoto;
    private PersonPagePopupWindow window;
    private InputMethodManager inputMethodManager;
    private String mFilePath;
    private Bitmap imageThumbnail;//用于显示用的bitmap
    private String mCompressPath;  //压缩后的图片的存放路径
    //用户信息图片存放根目录
    private String userDir = "ChangeStationPhoto";
    private ChangeStationPresenter changeStationPresenter;
    private CreateStationPresenter createStationPresenter;
    private String stationId;
    private String[] stationCodes;
    private ChangeStationInfo stationInformation;
    private LinearLayout stationPhotoLinear;

    protected Map<Integer, Runnable> allowablePermissionRunnables = new HashMap<>();
    protected Map<Integer, Runnable> disallowablePermissionRunnables = new HashMap<>();
    private static final String TAG = "StationHeadPhotoActivit";

    String cameraPermission = Manifest.permission.CAMERA;
    final int REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.station_head_photo_activity_layout);
        MyApplication.getApplication().addActivity(this);
        initView();
        if(savedInstanceState != null){
            GlobalConstants.isLoginSuccess = true;
            MyApplication.reLogin(MyApplication.getContext().getResources().getString(R.string.change_system_setting));
        }
    }

    /**
     * 设置字体大小不随手机字体大小改变
     */
    @Override
    public Resources getResources() {
        Resources resources = super.getResources();
        Configuration configuration = new Configuration();
        configuration.setToDefaults();
        resources.updateConfiguration(configuration, resources.getDisplayMetrics());
        return resources;
    }

    private void initView(){
        tv_left = (TextView) findViewById(R.id.tv_left);
        tv_right = (TextView) findViewById(R.id.tv_right);
        tv_right.setText(getString(R.string.change_photo));
        tv_right.setVisibility(View.VISIBLE);
        Drawable img = getResources().getDrawable(R.drawable.dev_switch);
        img.setBounds(0,(int)getResources().getDimension(R.dimen.size_1dp),img.getIntrinsicWidth(),img.getIntrinsicHeight());
        tv_right.setCompoundDrawables(img,null,null,null);
        tv_right.setCompoundDrawablePadding((int)getResources().getDimension(R.dimen.size_5dp));
        tv_right.setVisibility(View.VISIBLE);
        tv_right.setOnClickListener(this);
        tv_left.setVisibility(View.VISIBLE);
        tv_left.setOnClickListener(this);
        stationPhoto = (SimpleDraweeView) findViewById(R.id.station_photo);
        stationPhotoLinear = (LinearLayout) findViewById(R.id.station_photo_linear);
        stationPhotoLinear.setOnTouchListener(this);
        window = new PersonPagePopupWindow(this, this);
        inputMethodManager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        //【安全特性】获取getExtras数据时，未进行异常捕获，如果攻击者传入异常数据，会造成拒绝服务漏洞  【修改人】zhaoyufeng
        Intent intent = getIntent();
        if(intent != null) {
            try {
                Bundle bundle = intent.getExtras();
                if(bundle != null){
                    Uri uri = (Uri) bundle.get("StationPhoto");
                    stationId =  bundle.getString("stationCode");
                    stationCodes = new String[1];
                    stationCodes[0] = stationId;
                    Uri locationUri = Uri.parse("res://com.huawei.solarsafe/" + R.drawable.single_station_bg);
                    if(uri != null && !locationUri.toString().equals(uri.toString())){
                        DraweeController controller = Fresco.newDraweeControllerBuilder()
                                .setControllerListener(controllerListener)
                                .setUri(uri)
                                .build();
                        stationPhoto.setController(controller);
                    }else{
                        Drawable drawable = getResources().getDrawable(R.drawable.single_station_bg);
                        stationPhoto.setImageURI(uri);
                        handlerDisplayImageSize(drawable);
                    }
                }
            } catch (Exception e) {
                Log.e(TAG, "onCreate: " + e.getMessage());
            }
        }
        createStationPresenter = new CreateStationPresenter();
        changeStationPresenter = new ChangeStationPresenter();
        createStationPresenter.onViewAttached(this);
        changeStationPresenter.onViewAttached(this);
        requestStationData();
    }

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()){
            case R.id.tv_left:
                finish();
                break;
            case R.id.tv_right:

                requestPermission(
                        REQUEST_CODE,
                        cameraPermission,
                        new Runnable() {
                            @Override
                            public void run() {
                                //选择照片
                                inputMethodManager.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);
                                //弹出拍照弹窗
                                window.showAtLocation(findViewById(R.id.station_head_view), Gravity.CENTER, 0, 0);
                            }
                        },
                        new Runnable() {
                            @Override
                            public void run() {

                            }
                        });
                break;
            case R.id.bt_pop_camera:
                window.dismiss();
                intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                File file = getFile();
                Uri mFileUri = Uri.fromFile(file);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, mFileUri);
                startActivityForResult(intent, TAKE_PHOTO);
                break;
            case R.id.bt_pop_album:
                window.dismiss();
                intent = new Intent(Intent.ACTION_PICK);
                intent.setData(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, CHOOSE_PHOTO);
                break;
            case R.id.bt_pop_cancel:
                window.dismiss();
                break;
            default:
                break;
        }
    }

    /**
     * 请求电站数据
     */
    private void requestStationData(){
        Gson gson = new Gson();
        StationListRequest stationListRequest = new StationListRequest(1, 50, stationCodes, null);
        String s = gson.toJson(stationListRequest);
        createStationPresenter.requestStationList(s);
    }

    private ControllerListener controllerListener = new BaseControllerListener<ImageInfo>() {

        @Override
        public void onFinalImageSet(String id, @Nullable ImageInfo imageInfo, @Nullable Animatable anim) {

            final ImageInfo info = imageInfo;
            stationPhoto.post(new Runnable() {
                @Override
                public void run() {
                    int imageHeight = info.getHeight();
                    int imageWidth = info.getWidth();
                    LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) stationPhoto.getLayoutParams();
                    float imageScale = imageHeight*1.0f/imageWidth;
                    float screenScale = stationPhoto.getMeasuredHeight()*1.0f/stationPhoto.getMeasuredWidth();
                    if(imageScale>screenScale){
                        params.width = (int) ((imageWidth*1.0f/imageHeight)*stationPhoto.getMeasuredHeight());
                    }else{
                        params.height = (int) ((imageHeight*1.0f/imageWidth)*stationPhoto.getMeasuredWidth());
                    }
                    stationPhoto.setLayoutParams(params);
                }
            });

        }

        @Override
        public void onIntermediateImageSet(String id, @Nullable ImageInfo imageInfo) {

        }
        @Override
        public void onFailure(String id, Throwable throwable) {
            Uri uri = Uri.parse("res://com.huawei.solarsafe/" + R.drawable.single_station_bg);
            stationPhoto.setImageURI(uri);
            Drawable drawable = getResources().getDrawable(R.drawable.single_station_bg);
            handlerDisplayImageSize(drawable);
        }
    };

    @Override
    protected void onDestroy() {
        MyApplication.getApplication().removeActivity(this);
        super.onDestroy();
        Utils.deletePicDirectory();
    }

    /**
     * 根据图片尺寸修改控件大小
     * @param image
     */
    private void handlerDisplayImageSize(final Drawable image){

        stationPhoto.post(new Runnable() {
            @Override
            public void run() {
                int imageHeight = image.getMinimumHeight();
                int imageWidth = image.getMinimumWidth();
                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) stationPhoto.getLayoutParams();
                float imageScale = imageHeight*1.0f/imageWidth;
                float screenScale = stationPhoto.getMeasuredHeight()*1.0f/stationPhoto.getMeasuredWidth();
                if(imageScale>screenScale){
                    params.width = (int) ((imageWidth*1.0f/imageHeight)*stationPhoto.getMeasuredHeight());
                }else{
                    params.height = (int) ((imageHeight*1.0f/imageWidth)*stationPhoto.getMeasuredWidth());
                }
                stationPhoto.setLayoutParams(params);
            }
        });
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == Activity.RESULT_OK) {

            switch (requestCode) {
                    case TAKE_PHOTO:
                        //相机拍照处理
                        new CompressFile().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                        Toast.makeText(this, R.string.wait_for_images_compressed, Toast.LENGTH_SHORT).show();
                        break;
                    case CHOOSE_PHOTO:
                        //相册选择照片处理
                        Uri selectedImage = data.getData();
                        String[] filePathColumns = {MediaStore.Images.Media.DATA};
                        Cursor c = getContentResolver().query(selectedImage, filePathColumns, null, null, null);
                        if (null != c) {
                            c.moveToFirst();
                            int columnIndex = c.getColumnIndex(filePathColumns[0]);
                            mFilePath = c.getString(columnIndex);
                            new CompressFile().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                            c.close();
                        }
                        break;
                    default:
                        break;
                }

            }

        }

    @Override
    public void preStep() {

    }

    @Override
    public void cancelStep() {

    }

    @Override
    public void nextStep() {

    }

    @Override
    public void requestData() {

    }

    @Override
    public void getData(BaseEntity entity) {
        if(entity == null){
            return;
        }
        if (entity instanceof ChangeStationResultInfo) {
            ChangeStationResultInfo changeStationResultInfo = (ChangeStationResultInfo) entity;
            ChangeStationResultBean changeStationResultBean = changeStationResultInfo.getChangeStationResultBean();
            if (changeStationResultBean != null) {
                if (changeStationResultBean.isSuccess()) {
                    Toast.makeText(StationHeadPhotoActivity.this, R.string.image_upload_success, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(StationHeadPhotoActivity.this, R.string.image_upload_fail, Toast.LENGTH_SHORT).show();
                }
            }else{
                Toast.makeText(StationHeadPhotoActivity.this, R.string.image_upload_fail, Toast.LENGTH_SHORT).show();
            }
        }else if(entity instanceof StationManagementListInfo){
            StationManagementListInfo stationManagementListInfo = (StationManagementListInfo) entity;
            if(stationManagementListInfo.getStationManegementList() != null && stationManagementListInfo.getStationManegementList().getData() != null){
                if(stationManagementListInfo.getStationManegementList().getData().getList() != null &&stationManagementListInfo.getStationManegementList().getData().getList().size()>0 ){
                    stationInformation = stationManagementListInfo.getStationManegementList().getData().getList().get(0);
                }
            }
        }

    }

    @Override
    public void createStationSuccess() {

    }

    @Override
    public void createStationFail(int failCode,String data) {

    }

    @Override
    public void uploadResult(boolean ifSuccess, String key) {
        if(ifSuccess && stationInformation != null){
            UpdateStationOtherInfoReq updateStationOtherInfoReq = new UpdateStationOtherInfoReq();
            UpdateStationOtherInfoReq.StationBean stationBean = new UpdateStationOtherInfoReq.StationBean();
            //电站图片
            stationBean.setId(stationInformation.getId());
            stationBean.setImage(key);
            stationBean.setStationAddress(stationInformation.getStationAddr());
            if(stationInformation.getLatitude() != Double.MIN_VALUE ){
                stationBean.setLatitude(stationInformation.getLatitude()+"");
            }
            if(stationInformation.getLongitude() != Double.MIN_VALUE){
                stationBean.setLongtitude(stationInformation.getLongitude()+"");
            }
            stationBean.setSafeBeginDate(stationInformation.getSafeBeginDate());
            stationBean.setTimeZone(stationInformation.getTimeZone()+"");
            stationBean.setServiceLocation(stationInformation.getServiceLocation());
            updateStationOtherInfoReq.setStation(stationBean);
            updateStationOtherInfoReq.setType("OTHERS");
            Gson gson = new Gson();
            String s = gson.toJson(updateStationOtherInfoReq);
            Utils.deletePicDirectory();
            changeStationPresenter.requestStationUpdate(s);
        }else{
            Toast.makeText(StationHeadPhotoActivity.this, R.string.image_upload_fail + key, Toast.LENGTH_SHORT).show();
        }
    }

    private float xPosition; //滑动X坐标值
    private float yPosition; //滑动Y坐标值
    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {

        switch (motionEvent.getAction()){

            case MotionEvent.ACTION_DOWN:
                xPosition = motionEvent.getX();
                yPosition = motionEvent.getY();
                break;

            case MotionEvent.ACTION_MOVE:
                if(Math.abs(motionEvent.getX()-xPosition)>=5 || Math.abs(motionEvent.getY()-yPosition)>=5){
                    stationPhoto.setTranslationX((motionEvent.getX()-xPosition)/2);
                    stationPhoto.setTranslationY((motionEvent.getY()-yPosition)/2);
                }
                break;
            case MotionEvent.ACTION_UP:
                if(Math.abs(stationPhoto.getTranslationX())>=50 ||Math.abs(stationPhoto.getTranslationY())>=50){
                    ObjectAnimator translationYAnimator = ObjectAnimator.ofFloat(stationPhoto, "translationY",stationPhoto.getTranslationY(),0 );
                    translationYAnimator.setDuration(200);
                    translationYAnimator.start();
                    ObjectAnimator translationXAnimator = ObjectAnimator.ofFloat(stationPhoto, "translationX",stationPhoto.getTranslationX(),0 );
                    translationXAnimator.setDuration(200);
                    translationXAnimator.start();
                }else{
                    stationPhoto.setTranslationX(0);
                    stationPhoto.setTranslationY(0);
                }

                break;

            default:
                break;

        }



        return true;
    }

    /**
     * 后台图片压缩任务
     */
    private class CompressFile extends AsyncTask<Object, Object, Boolean> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Boolean doInBackground(Object... params
        ) {
            imageThumbnail = PicUtils.getImageThumbnail(mFilePath, 720, 1080);
            if (imageThumbnail==null) return false;
            mCompressPath = PicUtils.saveComprassFile(imageThumbnail, System.currentTimeMillis() + "_user.jpeg", userDir, 500);
            if (TextUtils.isEmpty(mCompressPath)) return false;
            createStationPresenter.uploadStationFile(mCompressPath,stationInformation.getStationName());
            return true;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            if (aBoolean){
                Bitmap thumbnailBitmap = BitmapFactory.decodeFile(mCompressPath);
                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) stationPhoto.getLayoutParams();
                params.height=-1;
                params.width =-1;
                stationPhoto.setLayoutParams(params);
                stationPhoto.setImageBitmap(thumbnailBitmap);
                Toast.makeText(StationHeadPhotoActivity.this, R.string.image_compression_succeeded, Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(StationHeadPhotoActivity.this, R.string.pic_compress_failed, Toast.LENGTH_SHORT).show();
            }
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
            path =getCacheDir().getAbsolutePath();
        }
        path = path + File.separator + "fusionHome" + File.separator + "Picture" + File.separator + GlobalConstants.userId + File.separator + "user";
        File dir = new File(path);
        if (!dir.exists()) {
            boolean mkdir=dir.mkdirs();
            if(!mkdir){
                return null;
            }
        }
        return dir;
    }
    /**
     * 请求权限
     *
     * @param id                   请求授权的id 唯一标识即可
     * @param permission           请求的权限
     * @param allowableRunnable    同意授权后的操作
     * @param disallowableRunnable 禁止权限后的操作
     */
    protected void requestPermission(int id, String permission, Runnable allowableRunnable, Runnable disallowableRunnable) {
        if (allowableRunnable == null) {
            throw new IllegalArgumentException("allowableRunnable == null");
        }

        allowablePermissionRunnables.put(id, allowableRunnable);
        if (disallowableRunnable != null) {
            disallowablePermissionRunnables.put(id, disallowableRunnable);
        }

        //版本判断
        if (Build.VERSION.SDK_INT >= 23) {
            //检查是否拥有权限
            int checkCallPhonePermission = ContextCompat.checkSelfPermission(getApplicationContext(), permission);
            if (checkCallPhonePermission != PackageManager.PERMISSION_GRANTED) {
                //弹出对话框请求权限
                ActivityCompat.requestPermissions(StationHeadPhotoActivity.this, new String[]{permission}, id);
                return;
            } else {
                allowableRunnable.run();
            }
        } else {
            allowableRunnable.run();
        }
    }

    /**
     * 请求权限回调
     * @param requestCode 权限请求码,唯一标识
     * @param permissions 权限数组
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Runnable allowRun = allowablePermissionRunnables.get(requestCode);
            allowRun.run();
        } else {
            Runnable disallowRun = disallowablePermissionRunnables.get(requestCode);
            disallowRun.run();
        }
    }

}
