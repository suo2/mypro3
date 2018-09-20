package com.huawei.solarsafe.view.stationmanagement;

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
import android.support.annotation.Nullable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.huawei.solarsafe.R;
import com.huawei.solarsafe.bean.GlobalConstants;
import com.huawei.solarsafe.bean.stationmagagement.CreateStationArgs;
import com.huawei.solarsafe.utils.PicUtils;
import com.huawei.solarsafe.utils.ToastUtil;
import com.huawei.solarsafe.utils.Utils;
import com.huawei.solarsafe.utils.customview.PersonPagePopupWindow;
import com.huawei.solarsafe.view.CustomViews.pickerview.TimePickerView;
import com.huawei.solarsafe.view.CustomViews.pickerview.listener.CustomListener;

import java.io.File;
import java.util.Calendar;
import java.util.Date;

import static com.huawei.solarsafe.view.maintaince.defects.NewDefectActivity.CHOOSE_PHOTO;
import static com.huawei.solarsafe.view.maintaince.defects.NewDefectActivity.TAKE_PHOTO;

/**
 * Create Date: 2017/4/24
 * Create Author: P00171
 * Description :创建电站-其他信息Fragment
 */
public class OtherInfoFragment extends CreateBaseFragmnet implements View.OnClickListener {
    private static final String TAG = "OtherInfoFragment";
    private FrameLayout flytPhoto;
    private ImageView ivPhoto;
    private ImageView ivDeletePhoto;
    private ImageView ivUploadSuccess;
    private TextView tvUploadSuccess;
    private Button btnUpload;
    private TextView tvStartTime;
    private EditText etLng;
    private EditText etLat;
    private EditText etProfile;
    private Spinner spTimezone;
    private TimePickerView timePickerView;
    private TextView dateTitle;
    private String[] timezoneString;
    private int[] timezoneValue;
    private PersonPagePopupWindow window;
    private InputMethodManager inputMethodManager;
    private long selectTime = System.currentTimeMillis();


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0004_F01_Android_S02  敏感信息检查-日志中不能包含敏感信息    【修改人】：zhaoyufeng
        initContent();
        timezoneString = new String[]{getString(R.string.e_12), getString(R.string.e_11), getString(R.string.e_10), getString(R.string.e_9), getString(R.string.e_8), getString(R.string.e_7), getString(R.string.e_6), getString(R.string.e_5), getString(R.string.e_4),
                getString(R.string.e_3), getString(R.string.e_2), getString(R.string.e_1), getString(R.string.middle_0), getString(R.string.w_1),
                getString(R.string.w_2), getString(R.string.w_3), getString(R.string.w_4), getString(R.string.w_5), getString(R.string.w_6), getString(R.string.w_7), getString(R.string.w_8), getString(R.string.w_9), getString(R.string.w_10), getString(R.string.w_11), getString(R.string.w_12)};
        timezoneValue = new int[]{12, 11, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1, 0, -1, -2, -3, -4, -5, -6, -7,
                -8, -9, -10, -11, -12};
        View view = inflater.inflate(R.layout.fragment_create_station_other_info, container, false);
        //电站照片
        flytPhoto = (FrameLayout) view.findViewById(R.id.flyt_photo);
        flytPhoto.setOnClickListener(this);
        ivPhoto = (ImageView) view.findViewById(R.id.iv_photo);
        ivPhoto.setOnClickListener(this);
        ivPhoto.setVisibility(View.GONE);
        //删除照片
        ivDeletePhoto = (ImageView) view.findViewById(R.id.iv_delete_photo);
        ivDeletePhoto.setOnClickListener(this);
        ivDeletePhoto.setVisibility(View.GONE);
        //上传照片
        ivUploadSuccess = (ImageView) view.findViewById(R.id.iv_upload_success);
        tvUploadSuccess = (TextView) view.findViewById(R.id.tv_upload_success);
        btnUpload = (Button) view.findViewById(R.id.btn_upload);
        btnUpload.setOnClickListener(this);
        //开始时间
        tvStartTime = (TextView) view.findViewById(R.id.tv_start_time);
        tvStartTime.setOnClickListener(this);
        //电站简介
        etProfile = (EditText) view.findViewById(R.id.et_profile);
        etProfile.setFilters(new InputFilter[]{Utils.getEmojiFilter()});
        //时区
        spTimezone = (Spinner) view.findViewById(R.id.sp_timezone);
        ArrayAdapter timezoneAdapter = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item, timezoneString);
        timezoneAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spTimezone.setAdapter(timezoneAdapter);
        spTimezone.setSelection(4);//默认选择东八区
        //
        window = new PersonPagePopupWindow(getActivity(), this);
        userDir = "CreatStation";
        inputMethodManager = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);

        return view;
    }

    @Override
    public boolean validateAndSetArgs(CreateStationArgs args) {
        CreateStationArgs.StationBean stationBean = args.getStation();
        if (stationBean == null) {
            stationBean = args.new StationBean();
            args.setStation(stationBean);
        }

        //开始时间
        Object tag;
        tag = tvStartTime.getTag();
        if (tag != null) {
            stationBean.setSafeRunningDate(String.valueOf((long) tag));
        }

        //电站图片

        //电站简介
        String profile = etProfile.getText().toString().trim();
        if (!profile.isEmpty()) {
            stationBean.setIntroduction(profile);
        }
        //电站时区
        int timezoneId = getTimezoneId();
        if (timezoneId == Integer.MIN_VALUE) {
            ToastUtil.showMessage(getString(R.string.input_station_timezone));
            return false;
        }
        stationBean.setTimeZone(String.valueOf(timezoneId));
        return true;
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.flyt_photo:
                //选择照片
                inputMethodManager.hideSoftInputFromWindow(getActivity().getWindow().getDecorView().getWindowToken(), 0);
                //弹出拍照弹窗
                window.showAtLocation(getView(), Gravity.CENTER, 0, 0);
                break;
            case R.id.iv_photo:
                break;
            case R.id.iv_delete_photo:
                //删除照片
                ivPhoto.setImageBitmap(null);
                ivPhoto.setVisibility(View.GONE);
                ivDeletePhoto.setVisibility(View.GONE);
                mCompressPath = null;
                //将参数设置至提交参数对象中
                ((CreateStationActivity) getActivity()).clickDeleteImage();
                ivUploadSuccess.setVisibility(View.INVISIBLE);
                tvUploadSuccess.setText(null);
                break;
            case R.id.btn_upload:
                //上传照片
                if (mCompressPath != null) {
                    ((CreateStationActivity) getActivity()).clickUploadImage(mCompressPath);
                } else {
                    ToastUtil.showMessage(getString(R.string.select_image));
                }
                break;
            case R.id.tv_start_time:
                //开始时间
                showDateDialog((TextView) v);
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
                intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, CHOOSE_PHOTO);
                break;
            case R.id.bt_pop_cancel:
                window.dismiss();
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case TAKE_PHOTO:
                    //相机拍照处理
                    new CompressFile().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                    tvUploadSuccess.setText(R.string.wait_for_images_compressed);
                    break;
                case CHOOSE_PHOTO:
                    //相册选择照片处理
                    Uri selectedImage = data.getData();
                    String[] filePathColumns = {MediaStore.Images.Media.DATA};
                    Cursor c = getContext().getContentResolver().query(selectedImage, filePathColumns, null, null, null);
                    if (null != c) {
                        c.moveToFirst();
                        int columnIndex = c.getColumnIndex(filePathColumns[0]);
                        mFilePath = c.getString(columnIndex);
                        c.close();
                        new CompressFile().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                        tvUploadSuccess.setText(R.string.wait_for_images_compressed);
                    }
                    break;
            }
        }
        
    }

    /**
     * 显示日期选择框
     */
    private int minYear = 2000;  //最小年份
    private  String[] yearContent = null;
    private  String[] monthContent = null;
    private  String[] dayContent = null;
    private  String[] hourContent = null;
    private  String[] minuteContent = null;
    private  String[] secondContent = null;

    /**
     * 显示时间选择对话框
     *
     * @param tvDate 回调显示选择的时间的文本框
     */
    public void showDateDialog(final TextView tvDate) {
        if (timePickerView == null) {
            timePickerView = new TimePickerView.Builder(getActivity(), new TimePickerView.OnTimeSelectListener() {
                @Override
                public void onTimeSelect(Date date, View v) {
                    tvDate.setText(Utils.getFormatTimeYYMMDD(date.getTime()));
                    tvDate.setTag(date.getTime());
                    selectTime = date.getTime();
                    if (selectTime == 0L) {
                        dateTitle.setText(R.string.please_select_time_str);
                    } else {
                        dateTitle.setText(Utils.getFormatTimeYYMMDD(selectTime));
                    }
                }
            })
                    .setLayoutRes(R.layout.dialog_time_picker, new CustomListener() {
                        @Override
                        public void customLayout(View v) {
                            dateTitle = (TextView) v.findViewById(R.id.dateTitle);
                            TextView tv_cancel = (TextView) v.findViewById(R.id.tv_cancel);
                            TextView tv_setting = (TextView) v.findViewById(R.id.tv_setting);
                            com.huawei.solarsafe.view.CustomViews.pickerview.lib.WheelView year = (com.huawei.solarsafe.view.CustomViews.pickerview.lib.WheelView) v.findViewById(R.id.year);
                            com.huawei.solarsafe.view.CustomViews.pickerview.lib.WheelView month = (com.huawei.solarsafe.view.CustomViews.pickerview.lib.WheelView) v.findViewById(R.id.month);
                            com.huawei.solarsafe.view.CustomViews.pickerview.lib.WheelView day = (com.huawei.solarsafe.view.CustomViews.pickerview.lib.WheelView) v.findViewById(R.id.day);

                            DisplayMetrics dm = getResources().getDisplayMetrics();
                            float scale = dm.density;
                            LinearLayout.LayoutParams lpYear = (LinearLayout.LayoutParams) year.getLayoutParams();
                            lpYear.setMargins((int) (40 * scale), 0, (int) (10 * scale), 0);
                            LinearLayout.LayoutParams lpMonth = (LinearLayout.LayoutParams) month.getLayoutParams();
                            lpMonth.setMargins((int) (10 * scale), 0, (int) (10 * scale), 0);
                            LinearLayout.LayoutParams lpDay = (LinearLayout.LayoutParams) day.getLayoutParams();
                            lpDay.setMargins((int) (10 * scale), 0, (int) (40 * scale), 0);

                            if (selectTime == 0L) {
                                dateTitle.setText(R.string.please_select_time_str);
                            } else {
                                dateTitle.setText(Utils.getFormatTimeYYMMDD(selectTime));
                            }

                            tv_cancel.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    timePickerView.dismiss();
                                }
                            });
                            tv_setting.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    timePickerView.returnData();
                                    timePickerView.dismiss();
                                }
                            });
                        }
                    })
                    .setItemsVisible(3)
                    .setDividerType(com.huawei.solarsafe.view.CustomViews.pickerview.lib.WheelView.DividerType.FILL)
                    .setDividerColor(getResources().getColor(R.color.inverter_upgrade_btn_bg))
                    .setDividerWidth(5)
                    .setType(new boolean[]{true, true, true, false, false, false})
                    .setLabel("", "", "", "", "", "")
                    .setLineSpacingMultiplier(3f)
                    .setContentSize(18)
                    .setOutSideCancelable(false)
                    .isCyclic(true)
                    .isDialog(true)
                    .build();
        }
        timePickerView.show();
    }

    /**
     * 获取时区提交数据
     *
     * @return
     */
    private int getTimezoneId() {
        int pos = spTimezone.getSelectedItemPosition();
        if (pos < 0 || pos > timezoneValue.length - 1) {
            return Integer.MIN_VALUE;
        }
        return timezoneValue[pos];
    }

    private String mFilePath;

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
            path = getActivity().getCacheDir().getAbsolutePath();
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

    private String mCompressPath;  //压缩后的图片的存放路径
    //用户信息图片存放根目录
    private String userDir;

    @Override
    public void onDestroy() {
        super.onDestroy();
        Utils.deletePicDirectory();
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
        protected Boolean doInBackground(Object... params) {
            Bitmap imageThumbnail = PicUtils.getImageThumbnail(mFilePath, 720, 1080);
            if (imageThumbnail==null){
                return false;
            }
            mCompressPath = PicUtils.saveComprassFile(imageThumbnail, System.currentTimeMillis() + "_user.jpeg", userDir, 500);
            if (TextUtils.isEmpty(mCompressPath)){
                return false;
            }
            return true;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            if (aBoolean){
                Bitmap thumbnailBitmap = BitmapFactory.decodeFile(mCompressPath);
                ivPhoto.setImageBitmap(thumbnailBitmap);
                ivPhoto.setVisibility(View.VISIBLE);
                ivDeletePhoto.setVisibility(View.VISIBLE);
                tvUploadSuccess.setText(R.string.image_compression_succeeded);
                ivUploadSuccess.setVisibility(View.VISIBLE);
            }else {
                ivPhoto.setVisibility(View.GONE);
                ivDeletePhoto.setVisibility(View.GONE);
                tvUploadSuccess.setText(R.string.pic_compress_failed);
                ivUploadSuccess.setVisibility(View.GONE);
            }
            mFilePath = null;
        }
    }

    public void uploadImgResult(boolean isSuccess) {
        tvUploadSuccess.setText(isSuccess ? getString(R.string.image_upload_success) : getString(R.string.image_upload_fail));
        ivUploadSuccess.setVisibility(isSuccess ? View.VISIBLE : View.INVISIBLE);
    }

    public void initContent() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        int year = calendar.get(Calendar.YEAR);
        yearContent = new String[year - minYear + 1];
        for (int i = 0; i <= (year - minYear); i++)
            yearContent[i] = String.valueOf(minYear + i);
        monthContent = new String[12];
        for (int i = 0; i < 12; i++) {
            monthContent[i] = String.valueOf(i + 1);
            if (monthContent[i].length() < 2) {
                monthContent[i] = "0" + monthContent[i];
            }
        }
        dayContent = new String[31];
        for (int i = 0; i < 31; i++) {
            dayContent[i] = String.valueOf(i + 1);
            if (dayContent[i].length() < 2) {
                dayContent[i] = "0" + dayContent[i];
            }
        }
        hourContent = new String[24];
        for (int i = 0; i < 24; i++) {
            hourContent[i] = String.valueOf(i);
            if (hourContent[i].length() < 2) {
                hourContent[i] = "0" + hourContent[i];
            }
        }
        minuteContent = new String[60];
        for (int i = 0; i < 60; i++) {
            minuteContent[i] = String.valueOf(i);
            if (minuteContent[i].length() < 2) {
                minuteContent[i] = "0" + minuteContent[i];
            }
        }
        secondContent = new String[60];
        for (int i = 0; i < 60; i++) {
            secondContent[i] = String.valueOf(i);
            if (secondContent[i].length() < 2) {
                secondContent[i] = "0" + secondContent[i];
            }
        }
    }


    public void setDefaultSafeStartTime(){
        //安全开始时间默认为并网时间
        CreateStationActivity createStationActivity = (CreateStationActivity) getActivity();
        selectTime = Long.parseLong(createStationActivity.getStationBean().getGridTime());
        tvStartTime.setTag(selectTime);//设置安全运行开始时间默认为当前时间
        tvStartTime.setText(Utils.getFormatTimeYYMMDD(selectTime));
    }
}
