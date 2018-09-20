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
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.model.LatLng;
import com.facebook.drawee.view.SimpleDraweeView;
import com.huawei.solarsafe.MyApplication;
import com.huawei.solarsafe.R;
import com.huawei.solarsafe.bean.BaseEntity;
import com.huawei.solarsafe.bean.Constant;
import com.huawei.solarsafe.bean.GlobalConstants;
import com.huawei.solarsafe.bean.ServerRet;
import com.huawei.solarsafe.bean.defect.DefectDetail;
import com.huawei.solarsafe.bean.defect.DefectDetailInfo;
import com.huawei.solarsafe.bean.defect.ProcessReq;
import com.huawei.solarsafe.bean.defect.WorkFlowBean;
import com.huawei.solarsafe.bean.device.DevTypeConstant;
import com.huawei.solarsafe.model.maintain.IProcState;
import com.huawei.solarsafe.model.maintain.defect.DefectProcEnum;
import com.huawei.solarsafe.model.maintain.defect.IDefectModel;
import com.huawei.solarsafe.presenter.maintaince.defect.DefectDetailPresenter;
import com.huawei.solarsafe.utils.ImageFactory;
import com.huawei.solarsafe.utils.LocalData;
import com.huawei.solarsafe.utils.PicUtils;
import com.huawei.solarsafe.utils.ToastUtil;
import com.huawei.solarsafe.utils.Utils;
import com.huawei.solarsafe.utils.customview.LoadingDialog;
import com.huawei.solarsafe.utils.customview.MyRecyclerView;
import com.huawei.solarsafe.utils.customview.StartCustomTextView;
import com.huawei.solarsafe.view.BaseActivity;
import com.huawei.solarsafe.view.MainActivity;
import com.huawei.solarsafe.view.maintaince.defects.picker.worker.ImageBrowseActivity;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import toan.android.floatingactionmenu.FloatingActionButton;

import static com.huawei.solarsafe.R.id.ll_bt;
import static com.huawei.solarsafe.utils.Utils.formetFileSize;
import static com.huawei.solarsafe.view.maintaince.defects.NewDefectActivity.CHOOSE_PHOTO;
import static com.huawei.solarsafe.view.maintaince.defects.NewDefectActivity.TAKE_PHOTO;
import static com.huawei.solarsafe.view.maintaince.main.PatrolFragment.EXECUTION;
import static com.huawei.solarsafe.view.maintaince.main.PatrolFragment.LOOK;
import static com.huawei.solarsafe.view.maintaince.main.PatrolFragment.OPERATION;

//缺陷消缺处理中状态/消缺确认中状态界面
public class DefectDetailActivity extends BaseActivity<DefectDetailPresenter> implements View.OnClickListener, IDefectDetailView,
        AMapLocationListener, View.OnLongClickListener {

    private static final int COMMIT = 50;

    private TextView tvStationName;
    private TextView tvDevName;
    private TextView tvDevType;
    private TextView tvDevModel;
    private TextView tvHandler,tvHandlerHint;
    private TextView tvStartTime;
    private TextView tvEndTime;
    private TextView tvDesc;
    private TextView ivMark;
    private SimpleDraweeView ivShowImage;
    private RelativeLayout rlAttachment;
    private TextView tvAttachName;
    private TextView tvDealContent;
    private LinearLayout llBt;
    private FloatingActionButton fabAlarm;
    private ImageView detail_flaw_pic;
    private TextView tvDisposeHint, tvDisposeText;
    private LinearLayout llDispose;
    private Button btnSendback, btnSubmit;

    private MyRecyclerView rvWorkFlow;
    private List<WorkFlowBean> flowList = new ArrayList<>();
    private WorkFlowAdapter mAdapter;

    private String fileName;
    private String mFilePath;      //压缩前的图片的存放路径
    private SelectPicPopupWindow popupWindow;
    private Uri mFileUri;
    private String dfId;
    private Map<Integer, String> devTypeMap;
    /**
     * 是否为修改缺陷单
     */
    private boolean isModify = false;
    private boolean mDelPic = false;  //是否要与服务器同步图片状态

    private DefectDetail detail;
    private ProcessReq.Process process;
    private boolean ifJumpFromMessageBox = false;
    private LatLng startPoint;
    private LatLng endPoint;
    // 声明AMapLocationClient类对象
    private AMapLocationClient mLocationClient = null;
    // 声明mLocationOption对象
    public AMapLocationClientOption mLocationOption = null;
    //添加附件的布局
    private FrameLayout flAddFj;
    private TextView tvAddNotice;
    private String stationCode;
    private View viewWorkFlow;
    public List<String> rightsList;
    private long failSize;
    private static final String TAG = "DefectDetailActivity";

    //消缺多人需求新增
    private String defectType = "";
    private boolean isOp = false;//是否是操作行为
    private int dealResult = -1;//操作人选择的处理结果序号
    LinearLayout viewNoPermission;//无权限界面
    TextView tvPermission;
    private String fileName1;
    private String path;
    private LocalBroadcastManager lbm;
    //用户信息图片存放根目录
    private String userDir = Environment.getExternalStorageDirectory().getPath() + File.separator + "fusionHome" + File.separator + "user";
    private String mCompressPath;  //压缩后的图片的存放路径

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        rightsList = new ArrayList<>();
        lbm = LocalBroadcastManager.getInstance(MyApplication.getContext());
        String strRights = LocalData.getInstance().getRightString();
        rightsList = Utils.stringToList(strRights);

        //判断用户是否有对应权限
        if (MainActivity.RIGHTS_LIST_SWITCH) {
            //判断用户是否有消缺管理权限
            if (rightsList.contains("app_defectManagement")) {
                viewNoPermission.setVisibility(View.GONE);
            } else {
                viewNoPermission.setVisibility(View.VISIBLE);
                tvPermission.setText(String.format(getResources().getString(R.string.this_account_without_permission), getResources().getString(R.string.management_of_defect_removal)));
                tv_title.setText(getResources().getString(R.string.management_of_defect_removal) + getResources().getString(R.string.permission));
                tv_right.setVisibility(View.GONE);
            }
        }

        Intent intent = getIntent();
        if (intent != null) {
            //【安全特性】获取Intent数据时，未进行异常捕获，如果攻击者传入异常数据，会造成拒绝服务漏洞  【修改人】zhaoyufeng
            try {
                endPoint = intent.getParcelableExtra(GlobalConstants.END_LOCATION);
                isModify = intent.getBooleanExtra("isModify", false);
                if (intent.getStringExtra("defectType") != null) {
                    defectType = intent.getStringExtra("defectType");
                }
            } catch (Exception e) {
                Log.e(TAG, "onCreate: " + e.getMessage());
            }
        }
        initLocation();
        // 启动定位
        mLocationClient.startLocation();
        String procId = "";
        String defectId = "";
        if (intent != null) {
            try {
                ifJumpFromMessageBox = intent.getBooleanExtra("ifJumpFromMessageBox", false);
                if (ifJumpFromMessageBox) {
                    defectId = intent.getStringExtra("defectId");
                    procId = intent.getStringExtra("procId");
                    presenter.requestDefectDetail(defectId, procId);
                } else {
                    detail = (DefectDetail) intent.getSerializableExtra("detail");
                    //【解DTS单】 DTS2018012208363  修改人：江东
                    if (detail != null) {
                        procId = detail.getProcId();
                        defectId = detail.getDefectCode();
                        setData(detail);
                        presenter.requestDefectDetail(defectId, procId);
                        presenter.requestWorkFlow(procId);
                    }
                }
            } catch (Exception e) {
                Log.e(TAG, "onCreate: " + e.getMessage());
            }
            //多人消缺需求新增
            switch (defectType) {
                case LOOK:
                    llBt.setVisibility(View.GONE);
                    break;
                case OPERATION:
                    llBt.setVisibility(View.VISIBLE);
                    isOp = true;
                    break;
                case EXECUTION:
                    llBt.setVisibility(View.VISIBLE);
                    break;
            }
        }
    }

    private void initLocation() {
        // 初始化定位
        checkPermissions(needPermissions);
        mLocationClient = new AMapLocationClient(this.getApplicationContext());
        // 设置定位回调 【安全问题单号】DTS2017113012382   注释中不能包含敏感信息  【修改人】zhaoyufeng
        mLocationClient.setLocationListener(this);
        // 初始化定位参数
        mLocationOption = new AMapLocationClientOption();
        // 设置定位模式为高精度模式，Battery_Saving为低功耗模式，Device_Sensors是仅设备模式
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        // 设置是否返回地址信息（默认返回地址信息）
        mLocationOption.setNeedAddress(false);
        // 设置是否只定位一次,默认为false
        mLocationOption.setOnceLocation(false);
        // 设置是否强制刷新WIFI，默认为强制刷新
        mLocationOption.setWifiActiveScan(true);
        // 设置是否允许模拟位置,默认为false，不允许模拟位置
        mLocationOption.setMockEnable(false);
        // 设置定位间隔,单位毫秒
        mLocationOption.setInterval(10 * 1000);
        // 给定位客户端对象设置定位参数
        mLocationClient.setLocationOption(mLocationOption);
    }

    @Override
    protected DefectDetailPresenter setPresenter() {
        return new DefectDetailPresenter();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_defect_detail;
    }

    @Override
    protected void initView() {

        viewNoPermission = (LinearLayout) findViewById(R.id.viewNoPermission);
        tvPermission = (TextView) findViewById(R.id.tvPermission);
        rvWorkFlow = (MyRecyclerView) findViewById(R.id.rv_work_flow);
        rvWorkFlow.setLayoutManager(new LinearLayoutManager(this));
        rvWorkFlow.setAdapter(mAdapter = new WorkFlowAdapter(flowList, this));
        tv_title.setText(getString(R.string.defect_details));
        viewWorkFlow = findViewById(R.id.view_work_flow);
        tvStationName = (TextView) findViewById(R.id.tv_station_name);
        tvDevName = (TextView) findViewById(R.id.tv_dev_name);
        tvDevType = (TextView) findViewById(R.id.tv_dev_type);
        tvDevModel = (TextView) findViewById(R.id.tv_dev_model);
        tvHandler = (TextView) findViewById(R.id.tv_handler);
        tvHandlerHint = (TextView) findViewById(R.id.tvHandlerHint);
        tvStartTime = (TextView) findViewById(R.id.tv_start_time);
        tvEndTime = (TextView) findViewById(R.id.tv_end_time);
        tvDesc = (TextView) findViewById(R.id.tv_desc);
        ivMark = (TextView) findViewById(R.id.iv_mark);
        ivShowImage = (SimpleDraweeView) findViewById(R.id.iv_show_image);
        rlAttachment = (RelativeLayout) findViewById(R.id.rl_attachment);
        tvAttachName = (TextView) findViewById(R.id.tv_attach_name);
        tvDealContent = (TextView) findViewById(R.id.tv_deal_content);
        detail_flaw_pic = (ImageView) findViewById(R.id.detail_flaw_pic);
        flAddFj = (FrameLayout) findViewById(R.id.fl_add_image);
        tvAddNotice = (TextView) findViewById(R.id.tv_add_notice);
        detail_flaw_pic.setOnClickListener(this);
        detail_flaw_pic.setOnLongClickListener(this);
        popupWindow = new SelectPicPopupWindow(this, this);
        llBt = (LinearLayout) findViewById(ll_bt);
        btnSendback = (Button) findViewById(R.id.bt_sendback);
        btnSubmit = (Button) findViewById(R.id.bt_submiting);

        tvDisposeHint = (TextView) findViewById(R.id.tvDisposeHint);
        tvDisposeText = (TextView) findViewById(R.id.tvDisposeText);
        llDispose = (LinearLayout) findViewById(R.id.llDispose);

        btnSendback.setOnClickListener(this);
        findViewById(R.id.bt_handover).setOnClickListener(this);
        btnSubmit.setOnClickListener(this);

        tv_right.setVisibility(View.VISIBLE);
        tv_right.setText(getString(R.string.start_navigation));
        tv_right.setOnClickListener(this);
        fabAlarm = (FloatingActionButton) findViewById(R.id.fab_alarm);
        fabAlarm.setVisibility(View.GONE);
        fabAlarm.setOnClickListener(this);
        devTypeMap = DevTypeConstant.getDevTypeMap(this);

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fab_alarm:
                Intent intent1 = new Intent();
                if (detail != null) {
                    intent1.putExtra("alarmType", detail.getAlarmType());
                    intent1.putExtra("dfId", detail.getDefectId() + "");
                }
                intent1.setClass(DefectDetailActivity.this, CorrelateAlarmActivity.class);
                startActivity(intent1);
                break;
            case R.id.bt_sendback:
                commit(IProcState.SENDBACK);
                break;
            case R.id.bt_handover:
                commit(IProcState.TAKEOVER);
                break;
            case R.id.bt_submiting:
                btnSubmit.setEnabled(false);
                //根据权限列表，显示有权限的模块
                if (rightsList.contains("app_operation")) {
                    //执行前先检查可执行的操作
                    canHandleProc();
                } else {
                    ToastUtil.showMessage(getString(R.string.no_opration_jurisdiction));
                    btnSubmit.setEnabled(true);
                }
                break;
            case R.id.tv_right:
                if (startPoint != null && endPoint != null) {

                    //判断语言环境决定启动哪种地图
                    if (Locale.getDefault().getLanguage().equals("zh")) {
                        if (Utils.isInstalledAMap(DefectDetailActivity.this)) {
                            StringBuffer sb = new StringBuffer("androidamap://route?sourceApplication=").append("amap");

                            sb.append("&dlat=")
                                    .append(endPoint.latitude)
                                    .append("&dlon=")
                                    .append(endPoint.longitude)
                                    .append("&dev=")
                                    .append(0)
                                    .append("&t=")
                                    .append(0);

                            Intent intent = new Intent("android.intent.action.VIEW", android.net.Uri.parse(sb.toString()));
                            intent.setPackage("com.autonavi.minimap");
                            startActivity(intent);
                        } else {
                            ToastUtil.showMessage(getResources().getString(R.string.a_map_uninstalled));
                        }
                    }else{
                        //跳转Google地图APP导航
                        Uri gmmIntentUri = Uri.parse("google.navigation:q=" + endPoint.latitude + "," + endPoint.longitude);
                        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                        mapIntent.setPackage("com.google.android.apps.maps");

                        //判断是否安装了Google地图
                        if (mapIntent.resolveActivity(getPackageManager()) != null) {
                            startActivity(mapIntent);
                        } else {
                            ToastUtil.showMessage(getResources().getString(R.string.google_map_uninstalled));
                        }
                    }

                } else {
                    if (startPoint == null) {
                        ToastUtil.showMessage(getString(R.string.locate_failed));

                    } else {
                        ToastUtil.showMessage(getString(R.string.get_geo_position_failed));
                    }
                }
                break;
            case R.id.detail_flaw_pic://点击图片
                if (mFilePath == null) {
                    popupWindow.showAtLocation(findViewById(R.id.rl_attachment), Gravity.CENTER_HORIZONTAL, 0, 0);
                } else {
                    ArrayList<String> filePaths = new ArrayList<String>();
                    filePaths.add("file://" + mFilePath);
                    ImageBrowseActivity.startActivity(DefectDetailActivity.this, filePaths, 0);
                }
                break;
            case R.id.bt_pop_camera:  //拍照
                popupWindow.dismiss();
                Intent intent2 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                File file = getFile();
                mFileUri = Uri.fromFile(file);
                intent2.putExtra(MediaStore.EXTRA_OUTPUT, mFileUri);
                startActivityForResult(intent2, TAKE_PHOTO);
                break;

            case R.id.bt_pop_album:  //从相册中选择照片
                popupWindow.dismiss();
                Intent intent3 = new Intent(Intent.ACTION_PICK);
                intent3.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent3, CHOOSE_PHOTO);
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
        fileName1 = time + "_defect_detail.jpg";
        File dir = getDirFile();
        File file = new File(dir, fileName1);
        mFilePath = file.getAbsolutePath();
        return file;
    }

    /**
     * 创建存储照片的父目录
     *
     * @return 创建的目录
     */
    private File getDirFile() {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            path = Environment.getExternalStorageDirectory().getAbsolutePath();
        } else {
            path = getCacheDir().getAbsolutePath();
        }
        path = path + File.separator + "fusionHome" + File.separator + "Picture" + File.separator + "Defects";
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


    private void commit(String operation) {
        String size = formetFileSize(failSize);
        if (size.endsWith("K")) {
            String[] strings = size.split("K");
            if (Double.parseDouble(strings[0]) > 500) {
                Toast.makeText(this, R.string.not_more_than_500, Toast.LENGTH_SHORT).show();
                new CompressFile().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                return;
            }
        } else if (size.endsWith("M") || size.endsWith("G")) {
            Toast.makeText(this, R.string.not_more_than_500, Toast.LENGTH_SHORT).show();
            new CompressFile().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
            return;
        }
        Intent intent = new Intent();
        if (detail != null) {
            intent.putExtra("proc", detail.getProcState());
        }
        intent.putExtra("operation", operation);
        intent.putExtra("procKey", IProcState.DEFECT);
        intent.putExtra("stationCode", stationCode);
        intent.putExtra("dealResult", dealResult);
        //消缺多人需求新增
        //是否是操作行为
        intent.putExtra("isOp", isOp);

        intent.setClass(DefectDetailActivity.this, DefectCommitActivity.class);
        startActivityForResult(intent, COMMIT);
    }


    private void setData(DefectDetail detail) {
        //判断缺陷流程状态
        if (IProcState.DEFECT_HANDLING.equals(detail.getProcState())) {//消缺处理中
            //显示处理意见框
            llDispose.setVisibility(View.VISIBLE);
            tvDisposeHint.setText(getResources().getString(R.string.processing_opinion));
            tvDisposeText.setText(detail.getPreTaskOpDesc());
        } else if (IProcState.DEFECT_CONFIRMING.equals(detail.getProcState())) {//消缺确认中
            //显示处理结果框
            llDispose.setVisibility(View.VISIBLE);
            tvDisposeHint.setText(getResources().getString(R.string.process_description_colon));
            tvDisposeText.setText(detail.getPreTaskOpDesc());
        } else {
            llDispose.setVisibility(View.GONE);
        }

        if (detail.getAlarmNum() > 0) {
            fabAlarm.setVisibility(View.VISIBLE);
        }
        stationCode = detail.getSId();
        dfId = detail.getDefectId() + "";
        tvStationName.setText(detail.getSName());
        tvDesc.setText(detail.getDefectDesc());
        tvDevModel.setText(detail.getDeviceVersion());
        if (detail.getDealResult() != null && !"".equals(detail.getDealResult())) {
            switch (Integer.parseInt(detail.getDealResult())) {
                case 1:
                    tvDealContent.setText(getString(R.string.not_fully_eliminated));
                    break;
                case 2:
                    tvDealContent.setText(getString(R.string.fully_eliminated));
                    break;
                case 3:
                    tvDealContent.setText(getString(R.string.no_need_dispose));
                    break;
                case 4:
                    tvDealContent.setText(getString(R.string.wait_dispose));
                    break;
            }
        }
        tvDevName.setText(detail.getDeviceName());
        if (!TextUtils.isEmpty(detail.getDeviceType())) {
            if (!TextUtils.isEmpty(devTypeMap.get(Integer.valueOf(detail.getDeviceType())))) {
                tvDevType.setText(devTypeMap.get(Integer.valueOf(detail.getDeviceType())));
            }
        }
        String timeZone = null;
        if (detail.getTimeZone() > 0 || detail.getTimeZone() == 0) {
            timeZone = "+" + detail.getTimeZone();
        } else {
            timeZone = detail.getTimeZone() + "";
        }
        if (detail.getStartTime() > 0) {
            tvStartTime.setText(Utils.getFormatTimeYYMMDDHHmmss2(detail.getStartTime(), timeZone));
        } else {
            tvStartTime.setText("");
        }
        if (detail.getEndTime() > 0) {
            tvEndTime.setText(Utils.getFormatTimeYYMMDDHHmmss2(detail.getEndTime(), timeZone));
        } else {
            tvEndTime.setText("");
        }
        tvHandler.setText(detail.getOwnerName());
        if (IProcState.DEFECT_FINISHED.equals(detail.getProcState())){
            tvHandlerHint.setVisibility(View.GONE);
            tvHandler.setVisibility(View.GONE);
        }

        //消缺多人需求修改,新增判断条件
        if (IProcState.DEFECT_FINISHED.equals(detail.getProcState()) || IProcState.DEFECT_ABORT.equals(detail.getProcState()) || !(OPERATION.equals(defectType) || EXECUTION.equals(defectType))) {
            llBt.setVisibility(View.GONE);
            flAddFj.setVisibility(View.GONE);
            tvAddNotice.setVisibility(View.GONE);
            //动态设置view的位置
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) viewWorkFlow.getLayoutParams();
            params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
            viewWorkFlow.setLayoutParams(params);
        } else {
            flAddFj.setVisibility(View.VISIBLE);
            tvAddNotice.setVisibility(View.VISIBLE);
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) viewWorkFlow.getLayoutParams();
            params.addRule(RelativeLayout.ABOVE, ll_bt);
            viewWorkFlow.setLayoutParams(params);
            detail_flaw_pic.setImageResource(R.drawable.ic_add_pic);
        }
        for (DefectProcEnum proc : DefectProcEnum.values()) {
            if (proc.getProcId().equals(detail.getProcState())) {
                ivMark.setText(proc.getProcName());
            }
        }
        fileName = detail.getFileId();
        if (detail.getFileId() != null) {
            tvAttachName.setText(fileName);
            final String[] picTypes = {"bmp", "jpg", "jpeg", "png"};
            String fileType = fileName.substring(fileName.lastIndexOf(".") + 1);
            for (String t : picTypes) {
                if (t.equals(fileType.toLowerCase())) {
                    rlAttachment.setVisibility(View.GONE);
                    ivShowImage.setVisibility(View.VISIBLE);
                    presenter.getAttachment(fileName, String.valueOf(detail.getDefectId()));
                    break;
                }
            }
            tvAddNotice.setText(getResources().getString(R.string.long_click_delete_th));
        } else if (IProcState.DEFECT_FINISHED.equals(detail.getProcState()) || IProcState.DEFECT_ABORT.equals(detail.getProcState()) || !(OPERATION.equals(defectType) || EXECUTION.equals(defectType))) {
            tvAddNotice.setVisibility(View.VISIBLE);
            tvAddNotice.setText(getResources().getString(R.string.has_no_scarce_pic));
            rlAttachment.setVisibility(View.GONE);
        } else {
            tvAddNotice.setText(getResources().getString(R.string.long_click_delete));
            rlAttachment.setVisibility(View.GONE);
        }
        if (detail.getSId() != null) {
            presenter.requestStationInfo(detail.getSId());
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == COMMIT && data != null) {
                try {

                    process = (ProcessReq.Process) data.getSerializableExtra("process");
                    if (process == null) {
                        return;
                    }
                    if (detail != null) {//解决报空指针
                        process.setProcId(detail.getProcId());
                        if (!IProcState.SENDBACK.equals(process.getOperation()) && IProcState.DEFECT_HANDLING.equals(detail.getProcState())) {
                            try {
                                detail.setDealResult(data.getStringExtra("dealResult"));
                            } catch (Exception e) {
                                Log.e(TAG, "onActivityResult: " + e.getMessage());
                            }
                        }
                        presenter.setContext(DefectDetailActivity.this);

                        //多人消缺需求新增
                        //是否是操作行为
                        if (isOp) {
                            detail.setOper(true);
                        }

                        presenter.updateDefect(detail, process);
                    }
                } catch (Exception e) {
                    Log.e(TAG, "onActivityResult: " + e.getMessage());
                }
            } else if (requestCode == CHOOSE_PHOTO && data != null) {
                //相册选择照片处理
                Uri selectedImage = data.getData();
                String[] filePathColumns = {MediaStore.Images.Media.DATA};
                Cursor c = getContentResolver().query(selectedImage, filePathColumns, null, null, null);
                if (null != c) {
                    c.moveToFirst();
                    int columnIndex = c.getColumnIndex(filePathColumns[0]);
                    mFilePath = c.getString(columnIndex);
                    showLoading(this);
                    new CompressFile().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                    Toast.makeText(this, R.string.wait_for_images_compressed, Toast.LENGTH_SHORT).show();
                    c.close();
                }
            } else if (requestCode == TAKE_PHOTO) {
                showLoading(this);
                //相机拍照处理
                new CompressFile().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                Toast.makeText(this, R.string.wait_for_images_compressed, Toast.LENGTH_SHORT).show();
            }
        } else {
            mFilePath = null;
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
            } catch (FileNotFoundException e) {
                Log.e(TAG, "doInBackground: " + e.getMessage());
                return false;
            } finally {
                if (is != null) {
                    try {
                        is.close();
                    } catch (IOException e) {
                        Log.e(TAG, "doInBackground: " + e.getMessage());
                    }
                }
            }
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
                    detail_flaw_pic.setImageBitmap(thumbnailBitmap);
                    Toast.makeText(DefectDetailActivity.this, R.string.image_compression_succeeded, Toast.LENGTH_SHORT).show();
                } else {
                    mFilePath = null;
                    //压缩失败
                    Toast.makeText(DefectDetailActivity.this, R.string.pic_compress_failed, Toast.LENGTH_SHORT).show();
                }
            } else {
                mFilePath = null;
                //压缩失败
                Toast.makeText(DefectDetailActivity.this, R.string.pic_compress_failed, Toast.LENGTH_SHORT).show();
            }
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
        mLocationClient.stopLocation();// 停止定位
        mLocationClient.onDestroy();// 销毁定位客户端
        mCompressPath = null;
    }

    @Override
    public void loadWorkFlow(List<WorkFlowBean> list) {
        flowList.clear();
        if (list != null) {
            flowList.addAll(list);
            mAdapter.notifyDataSetChanged();

        }
    }

    @Override
    public void loadDefectDetail(BaseEntity data) {

        if (data.isSuccess()) {
            if (data instanceof DefectDetailInfo) {
                DefectDetailInfo defectDetailInfo = (DefectDetailInfo) data;
                if (defectDetailInfo.getDetail() != null && defectDetailInfo.getServerRet() == ServerRet.OK) {
                    this.detail = defectDetailInfo.getDetail();
                    setData(defectDetailInfo.getDetail());
                    presenter.requestWorkFlow(defectDetailInfo.getDetail().getProcId());
                }
            }
        } else {
            tv_right.setClickable(false);
            btnSendback.setClickable(false);
            btnSubmit.setClickable(false);
            ToastUtil.showMessage(MyApplication.getContext().getString(R.string.defect_detail_error));
        }

    }

    @Override
    public void loadPicture(final String path) {
        if (path == null) {
            ivShowImage.setVisibility(View.GONE);
            Toast.makeText(this, getString(R.string.attachment_images_load_failed), Toast.LENGTH_SHORT).show();
        } else {
            ivShowImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ArrayList<String> filePaths = new ArrayList<>();
                    filePaths.add("file://" + path);
                    ImageBrowseActivity.startActivity(DefectDetailActivity.this, filePaths, 0);
                }
            });
            ivShowImage.setImageURI("file://" + path);
            mDelPic = true;
        }
    }

    //长按图片回调
    @Override
    public void onFileDelete(boolean success) {
        if (success) {
            detail_flaw_pic.setImageResource(R.drawable.ic_add_pic);
            mFilePath = null;
            mDelPic = false;
            Toast.makeText(this, getString(R.string.attachment_has_deleted), Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, getString(R.string.attachment_delete_faild_retry), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void setStaionPos(LatLng latLng) {
        this.endPoint = latLng;
    }

    @Override
    public void updateSuccess(String dfId) {
        if (dfId != null && !"".equals(dfId) && mCompressPath != null) {
            presenter.uploadAttachment(mCompressPath, dfId);//上传附件
        }
        Intent broad = new Intent(Constant.ACTION_DEFECTS_UPDATE);
        lbm.sendBroadcast(broad);
        setResult(RESULT_OK);
        finish();
    }

    @Override
    public void updateFailed() {

    }

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        startPoint = new LatLng(aMapLocation.getLatitude(), aMapLocation.getLongitude());
    }

    @Override
    public boolean onLongClick(View v) {
        switch (v.getId()) {
            case R.id.detail_flaw_pic:
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
            default:
                return false;
        }
    }

    /**
     * 流水显示适配器
     */
    public static class WorkFlowAdapter extends RecyclerView.Adapter<WorkFlowAdapter.WorkFlowViewHolder> {


        public void setFlowList(List<WorkFlowBean> flowList) {
            this.flowList = flowList;
        }

        private List<WorkFlowBean> flowList;
        private Context mContext;

        public WorkFlowAdapter(List<WorkFlowBean> flowList, Context c) {
            this.flowList = flowList;
            mContext = c;
        }

        @Override
        public WorkFlowViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new WorkFlowViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_work_flow, parent, false));
        }

        @Override
        public void onBindViewHolder(final WorkFlowViewHolder holder, int position) {
            WorkFlowBean bean = flowList.get(position);

            //操作人名字
            if (TextUtils.isEmpty(bean.getAssigneeName())) {
                holder.tvHandler.setText("");
            } else {
                holder.tvHandler.setText(bean.getAssigneeName());
            }
            //流转意见
            holder.tvHandleDescribe.setText(bean.getOperationDesc() == null ? "" : bean.getOperationDesc());
            if (!TextUtils.isEmpty(String.valueOf(bean.getTaskEndTime())) && bean.getTaskEndTime() > 0) {
                holder.tvHandleTime.setText(Utils.getFormatTimeYYMMDDHHmmss2(bean.getTaskEndTime()));
            } else {
                holder.tvHandleTime.setText("");
            }

            //接收人名字
            holder.tvRecevier.setText(("N/A".equals(bean.getRecipientName()) ? "" : bean.getRecipientName()));

            //处理流水文字
            holder.tvProc.setText(getOperationName(bean.getTaskKey(), bean.getOperation(), bean.getDealMark()));

            //新增多人消缺需求
            holder.cbOperatorNote.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if (b) {
                        compoundButton.setText(mContext.getResources().getString(R.string.up_operation_note));
                        holder.llOperatorNoteContainer.setVisibility(View.VISIBLE);
                    } else {
                        compoundButton.setText(mContext.getResources().getString(R.string.unfold_operation_note));
                        holder.llOperatorNoteContainer.setVisibility(View.GONE);
                    }
                }
            });

            //操作记录数据集
            ArrayList<WorkFlowBean.DefectDisposeCituationBean> ddcbArrayList = bean.getWfhts();

            if (ddcbArrayList != null && ddcbArrayList.size() > 0) {
                holder.llNotDispose.setVisibility(View.VISIBLE);
                holder.llYetDispose.setVisibility(View.VISIBLE);
                holder.cbOperatorNote.setVisibility(View.VISIBLE);

                int isDealNum = 0;//已处理计数

                holder.llOperatorNoteContainer.removeAllViews();//清空操作记录,避免复用问题

                for (WorkFlowBean.DefectDisposeCituationBean ddcb : ddcbArrayList) {
                    View view = LayoutInflater.from(mContext).inflate(R.layout.item_operator_note, null, false);
                    //【安全特性】f:Nullcheck of ddcb at line 861 of value previously dereferenced 【修改人】zhaoyufeng
                    if (ddcb != null) {
                        //操作记录时间
                        TextView tvOperatorTime = (TextView) view.findViewById(R.id.tvOperatorTime);
                        tvOperatorTime.setText(Utils.getFormatTimeYYMMDDHHmmss2(ddcb.getUpdateTime()));
                        StartCustomTextView sctvOperatorContent = (StartCustomTextView) view.findViewById(R.id.sctvOperatorContent);
                        //操作流转意见
                        sctvOperatorContent.setMText(TextUtils.isEmpty(ddcb.getOperationDesc()) ? "" : ddcb.getOperationDesc());
                        //操作人
                        TextView tvOperatorMan = (TextView) view.findViewById(R.id.tvOperatorMan);
                        tvOperatorMan.setText(ddcb.getOperatorName());
                        //处理结果
                        TextView tvDisposeResult = (TextView) view.findViewById(R.id.tvDisposeResult);

                        //判断是否处理
                        if (ddcb.isIsDeal()) {
                            isDealNum++;

                            //判断处理方式
                            if ("back".equals(ddcb.getOperationMark())) {//退回
                                tvDisposeResult.setText(mContext.getResources().getString(R.string.usages_back));
                            } else if ("submit".equals(ddcb.getOperationMark())) {//提交
                                switch (ddcb.getDealMark()) {
                                    case "1"://未完全消除
                                        tvDisposeResult.setText("[" + mContext.getResources().getString(R.string.not_fully_eliminated) + "]");
                                        break;
                                    case "2"://已完全消除
                                        tvDisposeResult.setText("[" + mContext.getResources().getString(R.string.fully_eliminated) + "]");
                                        break;
                                    case "3"://不需处理
                                        tvDisposeResult.setText("[" + mContext.getResources().getString(R.string.no_need_dispose) + "]");
                                        break;
                                    case "4"://待处理
                                        tvDisposeResult.setText("[" + mContext.getResources().getString(R.string.wait_dispose) + "]");
                                        break;
                                }
                            }

                        } else {
                            tvDisposeResult.setText(mContext.getResources().getString(R.string.processing));
                        }
                    }
                    holder.llOperatorNoteContainer.addView(view);
                }

                //已处理数
                holder.tvYetDisposeNum.setText(String.valueOf(isDealNum));
                //未处理数
                holder.tvNotDisposeNum.setText(String.valueOf(ddcbArrayList.size() - isDealNum));
            } else {
                holder.llNotDispose.setVisibility(View.GONE);
                holder.llYetDispose.setVisibility(View.GONE);
                holder.cbOperatorNote.setVisibility(View.GONE);
            }

        }

        @Override
        public int getItemCount() {
            return flowList.size();
        }

        //判断处理流水文字显示文本
        protected String getOperationName(String proc, String operation, String dealMark) {
            StringBuilder result = new StringBuilder();
            result.append("[");

            //判断执行流程状态
            for (DefectProcEnum bean : DefectProcEnum.values()) {
                if (bean.getProcId().equals(proc)) {
                    String string = mContext.getString(bean.getProcName());
                    result.append(string);
                }
            }

            //判断操作类型
            if (operation != null) {
                result.append("/");
                switch (operation) {
                    case IProcState.SUBMIT:
                        result.append(mContext.getString(R.string.defect_ticket_submit));
                        break;
                    case IProcState.TAKEOVER:
                        result.append(mContext.getString(R.string.hand_over));
                        break;
                    case IProcState.SENDBACK:
                        result.append(mContext.getString(R.string.defect_return));
                        break;
                    case IProcState.DEFECT_ABORT:
                        result.append(mContext.getString(R.string.defect_cancel));
                        break;
                    default:
                        break;
                }
            }
            result.append("]");

            //外场问题,对处理流水文本优化
            if (IDefectModel.SUBMIT.equals(operation)) {
                switch (proc) {
                    case IDefectModel.DISPACHING:
                        result.setLength(0);
                        result.append(mContext.getResources().getString(R.string.usages_allocation_and_wait_eliminate_defect));
                        break;
                    case IDefectModel.HANDLING:
                        result.setLength(0);
                        if ("4".equals(dealMark)) {//消除处理时执行人选择的待处理
                            result.append(mContext.getResources().getString(R.string.wait_allocation));
                        } else {
                            result.append(mContext.getResources().getString(R.string.usages_eliminate_defect_and_wait_check));
                        }
                        break;
                    case IDefectModel.CONFIRMING:
                        result.setLength(0);
                        result.append(mContext.getResources().getString(R.string.usages_check_qualified));
                        break;
                }
            } else if (IDefectModel.SENDBACK.equals(operation)) {
                switch (proc) {
                    case IDefectModel.HANDLING:
                        result.setLength(0);
                        result.append(mContext.getResources().getString(R.string.usages_back_and_wait_allocation));
                        break;
                    case IDefectModel.CONFIRMING:
                        result.setLength(0);
                        result.append(mContext.getResources().getString(R.string.usages_back_and_wait_eliminate_defect));
                        break;
                }
            } else if (TextUtils.isEmpty(operation)) {
                result.setLength(0);
                result.append(mContext.getResources().getString(R.string.processing));
            }

            return result.toString();
        }

        class WorkFlowViewHolder extends RecyclerView.ViewHolder {
            private TextView tvHandler;
            private TextView tvHandleTime;
            private TextView tvHandleDescribe;
            private TextView tvRecevier;
            private TextView tvProc;
            private LinearLayout llNotDispose, llYetDispose;
            private TextView tvYetDisposeNum, tvNotDisposeNum;
            private CheckBox cbOperatorNote;
            private LinearLayout llOperatorNoteContainer;

            public WorkFlowViewHolder(View view) {
                super(view);
                tvHandler = (TextView) view.findViewById(R.id.tv_handler);
                tvHandleTime = (TextView) view.findViewById(R.id.tv_handle_time);
                tvHandleDescribe = (TextView) view.findViewById(R.id.tv_handle_describe);
                tvRecevier = (TextView) view.findViewById(R.id.tv_recevier);
                tvProc = (TextView) view.findViewById(R.id.tv_proc);
                llNotDispose = (LinearLayout) view.findViewById(R.id.llNotDispose);
                llYetDispose = (LinearLayout) view.findViewById(R.id.llYetDispose);
                tvYetDisposeNum = (TextView) view.findViewById(R.id.tvYetDisposeNum);
                tvNotDisposeNum = (TextView) view.findViewById(R.id.tvNotDisposeNum);
                cbOperatorNote = (CheckBox) view.findViewById(R.id.cbOperatorNote);
                llOperatorNoteContainer = (LinearLayout) view.findViewById(R.id.llOperatorNoteContainer);
            }
        }
    }

    //检查当前任务是否可以执行
    private void canHandleProc() {
        HashMap<String, String> hashMap = new HashMap<>();
        if (detail != null) {
            hashMap.put("taskId", detail.getCurrentTaskId());
            hashMap.put("procId", detail.getProcId());

        }
        presenter.canHandleProc(hashMap, isOp);
    }

    //反馈任务不可执行原因
    @Override
    public void checkCanHandleProc(String msg) {
        if (msg == null || msg.length() == 0) {
            msg = "error";
        }
        switch (msg) {
            case "isOld":
                commit(IProcState.SUBMIT);
                break;
            case "error":
                ToastUtil.showMessage(getResources().getString(R.string.net_error));
                break;
            case "notdeal":
                Toast.makeText(DefectDetailActivity.this, getResources().getString(R.string.defect_notdeal), Toast.LENGTH_LONG).show();
                break;
            case "notallsame":
                Toast.makeText(DefectDetailActivity.this, getResources().getString(R.string.defect_notallsame), Toast.LENGTH_LONG).show();
                break;
            case "back":
                Toast.makeText(DefectDetailActivity.this, getResources().getString(R.string.defect_back), Toast.LENGTH_LONG).show();
                break;
            case "notsameop":
                Toast.makeText(DefectDetailActivity.this, getResources().getString(R.string.defect_notsameop), Toast.LENGTH_LONG).show();
                break;
            case "isOp":
                commit(IProcState.SUBMIT);
                break;
            default:
                String[] msgInfo = msg.split("#");

                //获取操作人选择的处理结果序号
                if (msgInfo.length == 2) {
                    dealResult = Integer.valueOf(msgInfo[1]);
                }

                commit(IProcState.SUBMIT);
                break;
        }
        btnSubmit.setEnabled(true);
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
            boolean mkdir = dirFile.mkdirs();
            if (!mkdir) {
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

