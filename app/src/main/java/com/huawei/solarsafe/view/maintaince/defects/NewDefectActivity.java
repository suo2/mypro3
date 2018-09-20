package com.huawei.solarsafe.view.maintaince.defects;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.LinearLayoutManager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.huawei.solarsafe.MyApplication;
import com.huawei.solarsafe.R;
import com.huawei.solarsafe.bean.Constant;
import com.huawei.solarsafe.bean.FillterMsg;
import com.huawei.solarsafe.bean.defect.DefectDetail;
import com.huawei.solarsafe.bean.defect.ProcessReq;
import com.huawei.solarsafe.bean.defect.WorkFlowBean;
import com.huawei.solarsafe.bean.device.DevBean;
import com.huawei.solarsafe.bean.device.DevTypeConstant;
import com.huawei.solarsafe.model.maintain.IProcState;
import com.huawei.solarsafe.presenter.maintaince.defect.NewDefectPresenter;
import com.huawei.solarsafe.utils.ButtonUtils;
import com.huawei.solarsafe.utils.ImageFactory;
import com.huawei.solarsafe.utils.LocalData;
import com.huawei.solarsafe.utils.PicUtils;
import com.huawei.solarsafe.utils.ToastUtil;
import com.huawei.solarsafe.utils.Utils;
import com.huawei.solarsafe.utils.customview.LoadingDialog;
import com.huawei.solarsafe.utils.customview.MyRecyclerView;
import com.huawei.solarsafe.utils.customview.PersonPagePopupWindow;
import com.huawei.solarsafe.view.BaseActivity;
import com.huawei.solarsafe.view.MainActivity;
import com.huawei.solarsafe.view.maintaince.defects.picker.device.DevicePickerActivity;
import com.huawei.solarsafe.view.maintaince.defects.picker.worker.ImageBrowseActivity;
import com.huawei.solarsafe.view.maintaince.operation.MaintenanceActivityNew;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.huawei.solarsafe.utils.Utils.formetFileSize;

/**
 * Created by P00319 on 2016/10/17.
 */
//缺陷工单填写中状态界面
public class NewDefectActivity extends BaseActivity<NewDefectPresenter> implements INewDefectView, View.OnClickListener, View
        .OnLongClickListener {

    private static final String TAG = "NewDefectActivity";
    public static final String ALARM_REPAIR_SUGGESTIONS = "repairSuggestionStr";
    public static final String ALARM_DEV_BEAN = "devBean";
    public static final String ALARM_TYPE_ID = "alarmTypeId";
    public static final String ALARM_IDS = "alarmIds";
    private Map<Integer, String> devTypeMap;

    /**
     * 拍照
     */
    public static final int TAKE_PHOTO = 5001;
    /**
     * 从相册选择照片
     */
    public static final int CHOOSE_PHOTO = 5002;
    /**
     * 选择设备
     */
    public static final int PICK_DEVICE = 5003;


    /**
     * 提交
     */
    public static final int SUBMIT = 6003;
    /**
     * 选择电站
     */
    public static final int PICK_STATION = 6004;

    /**
     * 是否为修改缺陷单
     */
    private boolean isModify = false;
    /**
     * 复制
     */
    private boolean isCopy = false;
    //电站名称栏
    private EditText etStationName;
    private ImageView ivSelectStation;

    //设备名称栏
    private EditText etDevName;
    private ImageView ivSelectDev;
    private RelativeLayout rlDevName;
    private DevBean devBean;

    //设备型号栏
    private EditText etDeviceModel;

    //设备类型栏
    private EditText etDevType;

    //缺陷描述栏
    private EditText etFlawDescription;
    private TextView descTextNums;

    //底部功能栏
    private Button btSubmit;
    private Button btFilling;
    private Button btHandOver;

    //附件栏
    private SimpleDraweeView mImageView;
    private String mFilePath;      //压缩前的图片的存放路径
    private Uri mFileUri;
    private boolean mDelPic = false;  //是否要与服务器同步图片状态
    private TextView tvAttachName;

    //底部弹出选择框
    private PersonPagePopupWindow popupWindow;

    /**
     * 附件类型
     */
    private String mFileName;

    private List<String> alarmIds = new ArrayList<>();
    private DefectDetail detail;
    private String alarmType;

    private String procId;

    private String dfId;
    private MyRecyclerView new_work_flow;
    private DefectDetailActivity.WorkFlowAdapter mAdapter;
    private List<WorkFlowBean> flowList = new ArrayList<>();
    private String stationCode;
    private long failSize;
    List<String> rightsList;//权限集合
    LinearLayout viewNoPermission;//无权限界面
    TextView tvPermission;
    private LocalBroadcastManager lbm;
    //用户信息图片存放根目录
    private String userDir = Environment.getExternalStorageDirectory().getPath() + File.separator + "fusionHome" + File.separator + "user";
    private String mCompressPath;  //压缩后的图片的存放路径

    @Override
    @SuppressWarnings("unchecked")
    protected void onCreate(Bundle savedInstanceState) {
        devTypeMap = DevTypeConstant.getDevTypeMap(MyApplication.getContext());
        parseIntent();
        super.onCreate(savedInstanceState);
        lbm = LocalBroadcastManager.getInstance(MyApplication.getContext());
        if (detail != null) {
            setData(detail);
        }
        setDevInfo();
        presenter.setContext(NewDefectActivity.this);
        //【安全特性】获取Intent数据时，未进行异常捕获，如果攻击者传入异常数据，会造成拒绝服务漏洞  【修改人】zhaoyufeng
        Intent intent = getIntent();
        if(intent != null) {
            try {
                if (intent.getStringExtra("repairSuggestionStr") != null) {
                    etFlawDescription.setText(intent.getStringExtra("repairSuggestionStr"));
                    stationCode = devBean.getStationCode();
                }
                if (procId != null) {
                    presenter.getDefectDetail(procId);
                    presenter.requestWorkFlow(procId);
                }
                if (procId != null || intent.getStringExtra("repairSuggestionStr") != null) {
                    rlDevName.setEnabled(false);
                    etDevName.setEnabled(false);
                }else {
                    rlDevName.setEnabled(true);
                    etDevName.setEnabled(true);
                }
            } catch (Exception e) {
                Log.e(TAG, "onCreate: " + e.getMessage());
            }
        }
        rightsList = new ArrayList<>();
        String strRights = LocalData.getInstance().getRightString();
        rightsList = Utils.stringToList(strRights);

        //判断用户是否有对应权限
        if (MainActivity.RIGHTS_LIST_SWITCH) {
            //判断用户是否有消缺管理权限
            if (rightsList.contains("app_defectManagement")) {
                viewNoPermission.setVisibility(View.GONE);
            } else {
                viewNoPermission.setVisibility(View.VISIBLE);
                tvPermission.setText(String.format(getResources().getString(R.string.this_account_without_permission),getResources().getString(R.string.management_of_defect_removal)));
                arvTitle.setText(getResources().getString(R.string.management_of_defect_removal)+getResources().getString(R.string.permission));
                tv_right.setVisibility(View.GONE);
            }
        }
    }

    private void parseIntent() {
        Intent mIntent = getIntent();
        if (mIntent != null) {
            //【安全特性】获取Intent数据时，未进行异常捕获，如果攻击者传入异常数据，会造成拒绝服务漏洞  【修改人】zhaoyufeng
            try {
                isModify = mIntent.getBooleanExtra("isModify", false);
                detail = (DefectDetail) mIntent.getSerializableExtra("detail");
                procId = mIntent.getStringExtra("procId");
                isCopy = mIntent.getBooleanExtra("isCopy", false);
                devBean = (DevBean) mIntent.getSerializableExtra("devBean");
                alarmIds.clear();
                if (mIntent.getStringArrayListExtra("alarmIds") != null) {
                    alarmIds.addAll(mIntent.getStringArrayListExtra("alarmIds"));
                }
                alarmType = mIntent.getStringExtra("alarmTypeId");
            } catch (Exception e) {
                Log.e(TAG, "onCreate: " + e.getMessage());
            }
        }
    }


    @Override
    protected int getLayoutId() {
        return R.layout.activity_new_flaw;
    }

    @Override
    protected void initView() {
        viewNoPermission= (LinearLayout) findViewById(R.id.viewNoPermission);
        tvPermission= (TextView) findViewById(R.id.tvPermission);
        arvTitle.setText(isModify ? getString(R.string.modify_defect_list) : getString(R.string.create_defect_list));
        tv_left.setOnClickListener(this);

        etStationName = (EditText) findViewById(R.id.et_station_name);
        etStationName.setFocusable(false);
        ivSelectStation = (ImageView) findViewById(R.id.iv_select_station);
        ivSelectStation.setOnClickListener(this);
        ivSelectStation.setVisibility(View.GONE);

        etDevName = (EditText) findViewById(R.id.et_device_name);
        etDevName.setOnClickListener(this);
        ivSelectDev = (ImageView) findViewById(R.id.iv_select_dev);
        ivSelectDev.setOnClickListener(this);
        rlDevName = (RelativeLayout) findViewById(R.id.rl_dev_name);
        rlDevName.setOnClickListener(this);

        /*设备类型和型号直接关联，暂时不支持配置*/
        etDevType = (EditText) findViewById(R.id.et_dev_type);
        etDevType.setFocusable(false);
        etDeviceModel = (EditText) findViewById(R.id.et_device_model);
        etDeviceModel.setFocusable(false);

        etFlawDescription = (EditText) findViewById(R.id.et_flaw_description);
        etFlawDescription.addTextChangedListener(descriptionWatcher);
        descTextNums = (TextView) findViewById(R.id.number_of_description_text);
        new_work_flow = (MyRecyclerView) findViewById(R.id.new_work_flow);
        mAdapter = new DefectDetailActivity.WorkFlowAdapter(flowList,this);
        new_work_flow.setLayoutManager(new LinearLayoutManager(this));
        new_work_flow.setAdapter(mAdapter);

        mImageView = (SimpleDraweeView) findViewById(R.id.flaw_pic);
        mImageView.setOnClickListener(this);
        mImageView.setOnLongClickListener(this);

        tvAttachName = (TextView) findViewById(R.id.tv_attach_name);
        tvAttachName.setOnLongClickListener(this);
        tvAttachName.setOnClickListener(this);

        btSubmit = (Button) findViewById(R.id.bt_submiting);
        btSubmit.setOnClickListener(this);
        btFilling = (Button) findViewById(R.id.bt_filing);
        btFilling.setOnClickListener(this);
        btHandOver = (Button) findViewById(R.id.btn_handover);
        btHandOver.setOnClickListener(this);

        //多人消缺需求修改,取消交接功能
        if (isModify) {
            btFilling.setVisibility(View.VISIBLE);
        }
        popupWindow = new PersonPagePopupWindow(this, this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Utils.closeSoftKeyboard(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public void setData(DefectDetail data) {
        this.detail = data;
        dfId = data.getDefectId() + "";
        procId = data.getProcId();
        stationCode = data.getSId();
        devBean = new DevBean();
        devBean.setDevVersion(data.getDeviceVersion());
        devBean.setDevTypeId(data.getDeviceType());
        devBean.setStationName(data.getSName());
        devBean.setStationCode(data.getSId());
        devBean.setDevId(data.getDeviceId());
        devBean.setDevName(data.getDeviceName());
        etStationName.setText(data.getSName());
        etDevName.setText(data.getDeviceName());
        if(!TextUtils.isEmpty(data.getDeviceType())){
            if (!TextUtils.isEmpty(devTypeMap.get(Integer.valueOf(data.getDeviceType())))) {
                etDevType.setText(devTypeMap.get(Integer.valueOf(data.getDeviceType())));
            }
        }
        etDeviceModel.setText(data.getDeviceVersion());
        etFlawDescription.setText(data.getDefectDesc());
        mFileName = data.getFileId();
        if (mFileName != null) {
            tvAttachName.setText(mFileName);
            tvAttachName.setVisibility(View.VISIBLE);
            mImageView.setVisibility(View.GONE);
            final String[] picTypes = {"bmp", "jpg", "jpeg", "png"};
            String fileType = mFileName.substring(mFileName.lastIndexOf(".") + 1);
            for (String t : picTypes) {
                if (t.equals(fileType.toLowerCase())) {
                    tvAttachName.setVisibility(View.GONE);
                    mImageView.setVisibility(View.VISIBLE);
                    mImageView.setImageResource(R.drawable.bg_no_pic);
                    presenter.getAttachment(mFileName, String.valueOf(data.getDefectId()));
                    break;
                }
            }
        }
    }

    @Override
    public void onFileDelete(boolean success) {
        if (success) {
            mImageView.setImageResource(R.drawable.ic_add_pic);
            mFilePath = null;
            mDelPic = false;
            Toast.makeText(this, getString(R.string.attachment_has_deleted), Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, getString(R.string.attachment_delete_faild_retry), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void loadWorkFlow(List<WorkFlowBean> list) {
        flowList.clear();
        if (isCopy) {
            return;
        }
        if (list != null) {
            flowList.addAll(list);
            mAdapter.notifyDataSetChanged();
        }
    }


    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.bt_pop_camera:  //拍照
                popupWindow.dismiss();
                Intent intent1 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                File file = getFile();
                mFileUri = Uri.fromFile(file);
                intent1.putExtra(MediaStore.EXTRA_OUTPUT, mFileUri);
                startActivityForResult(intent1, TAKE_PHOTO);
                break;

            case R.id.bt_pop_album:  //从相册中选择照片
                popupWindow.dismiss();
                Intent intent2 = new Intent(Intent.ACTION_PICK);
                intent2.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent2, CHOOSE_PHOTO);
                break;
            case R.id.bt_pop_cancel:
                popupWindow.dismiss();
                break;
            case R.id.flaw_pic:   //点击照片

                View currentFocus = NewDefectActivity.this.getCurrentFocus();
                if (currentFocus!=null)
                //隐藏软键盘
                ((InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(currentFocus.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

                if (mFilePath == null) {
                    popupWindow.showAtLocation(findViewById(R.id.rl_flaw_bottom), Gravity.CENTER_HORIZONTAL, 0, 0);
                } else {
                    ArrayList<String> filePaths = new ArrayList<String>();
                    filePaths.add("file://" + mFilePath);
                    ImageBrowseActivity.startActivity(NewDefectActivity.this, filePaths, 0);

                }
                break;
            case R.id.et_device_name:
                intent.setClass(NewDefectActivity.this, DevicePickerActivity.class);
                startActivityForResult(intent, PICK_DEVICE);
                break;
            case R.id.rl_dev_name:
                intent.setClass(NewDefectActivity.this, DevicePickerActivity.class);
                startActivityForResult(intent, PICK_DEVICE);
                break;

            case R.id.bt_submiting:
                if(!ButtonUtils.isFastDoubleClick(R.id.bt_submiting)) {
                    submitDefect(IProcState.SUBMIT);
                }
                break;

            case R.id.bt_filing:
                if (isModify) {
                    submitDefect(IProcState.SENDBACK);
                }
                break;

            case R.id.btn_handover:
                if (isModify) {
                    submitDefect(IProcState.TAKEOVER);
                }
                break;

            case R.id.tv_left:  //返回
                showExitDialog();
                break;
        }
    }


    /**
     * 查看必填信息是否已填完整
     */
    private boolean entireInfo() {
        if (TextUtils.isEmpty(etFlawDescription.getText())) {
            Toast.makeText(this, R.string.description_cannot_be_blank, Toast.LENGTH_SHORT).show();
            return false;
        }
        if (TextUtils.isEmpty(etDevName.getText())) {
            Toast.makeText(this, R.string.equipment_cannot_be_blank, Toast.LENGTH_SHORT).show();
            return false;
        }
        if (TextUtils.isEmpty(etStationName.getText())) {
            Toast.makeText(this, R.string.station_name_not_null, Toast.LENGTH_SHORT).show();
            return false;
        }
        String size = formetFileSize(failSize);
        if (size.endsWith("K")) {
            String[] strings = size.split("K");
            if (Double.parseDouble(strings[0]) > 500) {
                Toast.makeText(this, R.string.not_more_than_500, Toast.LENGTH_SHORT).show();
                new CompressFile().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                return false;
            }
        } else if (size.endsWith("M") || size.endsWith("G")) {
            Toast.makeText(this, R.string.not_more_than_500, Toast.LENGTH_SHORT).show();
            new CompressFile().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
            return false;
        }
        return true;
    }


    @Override
    protected NewDefectPresenter setPresenter() {
        return new NewDefectPresenter();
    }

    private void submitDefect(String operation) {
        if (entireInfo()) {
            Intent intent = new Intent();
            intent.putExtra("proc", IProcState.DEFECT_WRITE);
            intent.putExtra("operation", operation);
            intent.putExtra("procId", procId);
            intent.putExtra("procKey", IProcState.DEFECT);
            intent.putExtra("stationCode", stationCode);
            intent.setClass(NewDefectActivity.this, DefectCommitActivity.class);
            startActivityForResult(intent, SUBMIT);
        }
    }


    /**
     * 长按删除照片提示框
     */
    @Override
    public boolean onLongClick(View v) {
        switch (v.getId()) {
            case R.id.flaw_pic:
                if (mFilePath != null) {
                    new AlertDialog.Builder(this)
                            .setTitle(R.string.delete_picture)
                            .setPositiveButton(getResources().getString(R.string.confirm), new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    if (dfId != null && isModify && mDelPic) {
                                        presenter.deleteAttachment(dfId);
                                    } else {
                                        onFileDelete(true);
                                    }
                                }
                            })
                            .setNegativeButton(R.string.cancel_defect, null)
                            .show();
                }
                return true;
            case R.id.tv_attach_name:
                new AlertDialog.Builder(this)
                        .setTitle(getString(R.string.delete_attachment_confirmed))
                        .setPositiveButton(getResources().getString(R.string.confirm), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (dfId != null && isModify) {
                                    presenter.deleteAttachment(dfId);
                                } else {
                                    tvAttachName.setVisibility(View.GONE);
                                    mImageView.setVisibility(View.VISIBLE);
                                }
                            }
                        })
                        .setNegativeButton(R.string.cancel_defect, null)
                        .show();
                return true;
            default:
                return false;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CHOOSE_PHOTO && resultCode == Activity.RESULT_OK && data != null) {
            //相册选择照片处理
            Uri selectedImage = data.getData();
            String[] filePathColumns = {MediaStore.Images.Media.DATA};
            Cursor c = getContentResolver().query(selectedImage, filePathColumns, null, null, null);
            if (null != c) {
                c.moveToFirst();
                int columnIndex = c.getColumnIndex(filePathColumns[0]);
                mFilePath = c.getString(columnIndex);
                showLoading(this);
                //相机拍照处理
                Toast.makeText(this, R.string.wait_for_images_compressed, Toast.LENGTH_SHORT).show();
                new CompressFile().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                c.close();
            }
        } else if (requestCode == TAKE_PHOTO && resultCode == Activity.RESULT_OK) {
            showLoading(this);
            //相机拍照处理
            Toast.makeText(this, R.string.wait_for_images_compressed, Toast.LENGTH_SHORT).show();
            //相机拍照处理
            new CompressFile().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        } else if (requestCode == TAKE_PHOTO && resultCode == 0) {
            mFilePath = null;
        } else if (requestCode == PICK_DEVICE && resultCode == Activity.RESULT_OK && data != null) {
            try{
                devBean = (DevBean) data.getSerializableExtra("devBean");
                stationCode = devBean.getStationCode();
                setDevInfo();
            }catch (Exception e){
                Log.e(TAG, "onActivityResult: "  + e.getMessage());
            }

        } else if (requestCode == SUBMIT && resultCode == RESULT_OK && data != null && devBean != null) {
            try{
                ProcessReq.Process process = (ProcessReq.Process) data.getSerializableExtra("process");
                if (isModify) {
                    detail.setDeviceType(devBean.getDevTypeId());
                    detail.setDeviceVersion(devBean.getDevVersion());
                    detail.setDefectDesc(etFlawDescription.getText().toString().trim());
                    detail.setSName(devBean.getStationName());
                    detail.setSId(devBean.getStationCode());
                    detail.setDeviceId(devBean.getDevId());
                    detail.setAlarmIds(alarmIds);
                    detail.setAlarmType(alarmType);
                    stationCode = devBean.getStationCode();
                    presenter.modifyDefect(detail, process);
                } else {
                    process.setProcId("");
                    presenter.submitDefect("", devBean, etFlawDescription.getText().toString(), process, alarmIds, alarmType);
                }
            }catch (Exception e){
                Log.e(TAG, "onActivityResult: " + e.getMessage());
            }
        } else if (requestCode == PICK_STATION && resultCode == Activity.RESULT_OK && data != null) {
            try{
                etStationName.setText(data.getStringExtra("name"));
            }catch (Exception e){
                Log.e(TAG, "onActivityResult: "  + e.getMessage());
            }
        }
    }

    private void setDevInfo() {
        if (devBean != null) {
            etDevName.setText(devBean.getDevName());
            if(!TextUtils.isEmpty(devBean.getDevTypeId())){
                if (!TextUtils.isEmpty(devTypeMap.get(Integer.valueOf(devBean.getDevTypeId())))) {
                    etDevType.setText(devTypeMap.get(Integer.valueOf(devBean.getDevTypeId())));
                }
            }
            etDeviceModel.setText(devBean.getDevVersion());
            etStationName.setText(devBean.getStationName());
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        String sPath;
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            sPath = Environment.getExternalStorageDirectory().getAbsolutePath();
        } else {
            sPath = MyApplication.getContext().getCacheDir().getAbsolutePath();
        }
        sPath = sPath + File.separator + "fusionHome" + File.separator + "user";
        Utils.deleteDirectory(sPath);
        Utils.deletePicDirectory();
        //使用过后将内容及时清理
        mCompressPath = null;
    }


    /**
     * 缺陷描述字数控制 【安全问题单号】DTS2017113012382   注释中不能包含敏感信息  【修改人】zhaoyufeng
     */
    TextWatcher descriptionWatcher = new TextWatcher() {
        private String nums;

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            nums = "(" + s.toString().length() + "/1000" + ")";
            descTextNums.setText(nums);
        }

        @Override
        public void afterTextChanged(Editable s) {
        }
    };


    /**
     * 创建存储拍摄照片的文件
     *
     * @return 创建的文件
     */

    private File getFile() {
        long time = System.currentTimeMillis();
        String fileName = time + "_defect.jpg";
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
        path = path + File.separator + "fusionHome" + File.separator + "Picture" + File.separator + "Defects";
        // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0004_F01_Android_S02  敏感信息检查-日志中不能包含敏感信息    【修改人】：zhaoyufeng
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
                is = new FileInputStream(mFilePath);
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.outWidth = 480;
                options.outHeight = 800;
                options.inPreferredConfig = Bitmap.Config.RGB_565;
                options.inPurgeable = true;
                options.inSampleSize = 4;
                options.inInputShareable = true;
                Bitmap targetBitmap = BitmapFactory.decodeStream(is, null, options);
                int degree = Utils.getPictureDegree(mFilePath);
                if (targetBitmap == null) {
                    return false;
                }
                if (degree != 0) {
                    targetBitmap = Utils.rotaingPic(degree, targetBitmap);
                }
                Bitmap bitmap = ImageFactory.ratio(targetBitmap, 480, 800);
                mCompressPath = saveFile2(bitmap, System.currentTimeMillis() + "_user.jpeg", userDir);
                return true;
            } catch (FileNotFoundException e) {
                Log.e(TAG, "doInBackground: " + e.getMessage());
                return false;
            }finally {
                if (is!=null){
                    try {
                        is.close();
                    } catch (IOException e) {
                        Log.e(TAG, "doInBackground: "+e.getMessage());
                    }
                }
            }

        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            dismissLoading();
            if (aBoolean) {
                //压缩成功
                Bitmap thumbnailBitmap = PicUtils.getImageThumbnail(mCompressPath, 120, 120);
                if (thumbnailBitmap != null) {
                    mImageView.setImageBitmap(thumbnailBitmap);
                    Toast.makeText(NewDefectActivity.this, R.string.image_compression_succeeded, Toast.LENGTH_SHORT).show();
                } else {
                    //压缩失败
                    mFilePath = null;
                    Toast.makeText(NewDefectActivity.this, R.string.pic_compress_failed, Toast.LENGTH_SHORT).show();
                }
            } else {
                //压缩失败
                mFilePath = null;
                Toast.makeText(NewDefectActivity.this, R.string.pic_compress_failed, Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void showExitDialog() {
        new AlertDialog.Builder(this)
                .setTitle(R.string.sure_exit_str)
                .setPositiveButton(getResources().getString(R.string.confirm), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //setResult(RESULT_OK);
                        finish();
                    }
                })
                .setNegativeButton(R.string.cancel_defect, null)
                .show();
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0004_F01_Android_S02  敏感信息检查-日志中不能包含敏感信息    【修改人】：zhaoyufeng
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                showExitDialog();
                return true;
            default:
                break;
        }
        return super.onKeyUp(keyCode, event);
    }

    @Override
    public void submitSuccess(String dfId) {
        ToastUtil.showMessage(getString(R.string.submit_ok));
        if (dfId != null && !"".equals(dfId) && mCompressPath != null) {
            presenter.uploadAttachment(mCompressPath, dfId);
        }
        Intent broad = new Intent(Constant.ACTION_DEFECTS_UPDATE);
        lbm.sendBroadcast(broad);
        Intent intent = new Intent();
        FillterMsg fillterMsg = new FillterMsg();
        fillterMsg.setType("Fresh");
        intent.putExtra("fillter", fillterMsg);
        intent.setAction(MaintenanceActivityNew.ACTION_FILLTER_MSG);
        lbm.sendBroadcast(intent);
        setResult(RESULT_OK);
        finish();
    }

    @Override
    public void submitFailed() {
        Toast.makeText(this, getString(R.string.submission_failed_retry), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void loadPicture(final String path) {
        if (path == null) {
            mImageView.setVisibility(View.GONE);
            Toast.makeText(this, getString(R.string.attachment_images_load_failed), Toast.LENGTH_SHORT).show();
        } else {
            mFilePath = path;
            if (isCopy) {
                mFilePath = null;
                mImageView.setImageResource(R.drawable.ic_add_pic);
            } else {
                mImageView.setImageURI("file://" + path);
            }
            mDelPic = true;
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
}
