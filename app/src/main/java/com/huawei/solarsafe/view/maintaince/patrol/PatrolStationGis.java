package com.huawei.solarsafe.view.maintaince.patrol;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.huawei.solarsafe.MyApplication;
import com.huawei.solarsafe.R;
import com.huawei.solarsafe.bean.BaseEntity;
import com.huawei.solarsafe.bean.Constant;
import com.huawei.solarsafe.bean.GlobalConstants;
import com.huawei.solarsafe.bean.patrol.PatrolGisBean;
import com.huawei.solarsafe.bean.patrol.PatrolItem;
import com.huawei.solarsafe.bean.patrol.PatrolItemList;
import com.huawei.solarsafe.bean.patrol.PatrolReport;
import com.huawei.solarsafe.bean.patrol.PatrolSingleInspec;
import com.huawei.solarsafe.bean.patrol.PatrolStep;
import com.huawei.solarsafe.database.PatrolItemDBManager;
import com.huawei.solarsafe.model.maintain.IProcState;
import com.huawei.solarsafe.presenter.maintaince.patrol.PatrolGisPresenter;
import com.huawei.solarsafe.utils.ImageFactory;
import com.huawei.solarsafe.utils.LocalData;
import com.huawei.solarsafe.utils.PicUtils;
import com.huawei.solarsafe.utils.ToastUtil;
import com.huawei.solarsafe.utils.Utils;
import com.huawei.solarsafe.utils.ZipCompressing;
import com.huawei.solarsafe.utils.customview.ContainsEmojiEditText;
import com.huawei.solarsafe.utils.customview.CustomGridView;
import com.huawei.solarsafe.utils.customview.GisView;
import com.huawei.solarsafe.utils.customview.LoadingDialog;
import com.huawei.solarsafe.view.maintaince.defects.picker.worker.ImageBrowseActivity;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.huawei.solarsafe.bean.GlobalConstants.KEY_INSPECT_ID;
import static com.huawei.solarsafe.bean.GlobalConstants.KEY_LANGUAGE_TYPE;
import static com.huawei.solarsafe.bean.GlobalConstants.KEY_PLANT_NAME;
import static com.huawei.solarsafe.bean.GlobalConstants.KEY_REMARK;
import static com.huawei.solarsafe.bean.GlobalConstants.KEY_RESULT;
import static com.huawei.solarsafe.bean.GlobalConstants.KEY_S_ID;
import static com.huawei.solarsafe.bean.GlobalConstants.KEY_TASK_ID;
import static com.huawei.solarsafe.utils.PicUtils.saveFile2;

/**
 * Create Date: 2017/3/3
 * Create Author: p00213
 * Description : 巡检报告界面
 */
public class PatrolStationGis extends Activity implements IPatrolGisView, View.OnClickListener,
        AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {
    private final String TAG = "PatrolStationGis";
    private int UPDATE_UI = 11;

    private Button backButton;
    private TextView tvTitle;
    private Button completeButton;
    private Context mContext;
    /**
     * 请求的code
     */
    public static final int REQCODE = 6;

    //presenter
    private PatrolGisPresenter patrolGisPresenter;
    //缩略图adapter
    private PatrolPicAdapter picAdapter;
    //缩略图控件
    private CustomGridView gridView;
    //巡检条目容器
    private LinearLayout itemsContains;
    //备注容器
    private ContainsEmojiEditText remarkContains;
    //加载中dialog
    private LoadingDialog loadingDialog;


    //当前巡检单站的状态 0 巡检中 1 完成 2 放弃
    private int inspectResult;
    //巡检id
    private String inspectId;
    //电站id
    private String stationId;
    //procName
    private String procName = "";
    //任务id
    private String taskId;
    //备注
    private String remark;

    //服务器返回的当个电站所有的图片id
    private List<String> stationPicIds;
    //当前获取第几张图片
    private int picIndex ;

    //如果不保存报告或者提交了报告，需要删除的本地图片路径(压缩过的)
    private String deleteBitmapPath;
    //当前任务的拍照原图的符路径
    private String cameraPath;
    //zip文件路径
    private String zipPath;

    private List<Bitmap> mBitmaps = new ArrayList<>();
    //mBItmaps中各项名与大图路径对应关系
    private List<String> mBitmapPath = new ArrayList<>();

    private List<Boolean> mIsLocals = new ArrayList<>();

    //每次拍照后图片的路径及文件名
    private String cameraPic;
    //大图的路径
    ArrayList<String> filePaths = new ArrayList<>();

    private TextView textViewNotice;
    private Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            if (msg.what == UPDATE_UI) {
                loadImg();
            }
        }
    };
    private String patrolReportDir;
    private LocalBroadcastManager lbm;

    @Override
    public void getData(BaseEntity data) {
        if (data == null) {
            if (loadingDialog != null && loadingDialog.isShowing()) {
                loadingDialog.dismiss();
            }
            Toast.makeText(this, getString(R.string.submit_report_failed), Toast.LENGTH_SHORT).show();
        }
        //巡检报告
        if (data instanceof PatrolGisBean) {
            PatrolGisBean bean = (PatrolGisBean) data;
            stationPicIds = bean.getPicIds();
            if (stationPicIds != null && stationPicIds.size() > 0) {
                picIndex=0;
                patrolGisPresenter.doRequestPic(true, stationPicIds.get(picIndex), inspectId);
                textViewNotice.setVisibility(View.GONE);
            } else {//没有图片时
                if (!canMofify())
                    textViewNotice.setVisibility(View.VISIBLE);
            }
            if (bean != null && bean.getGisBeans() != null && bean.getGisBeans().size() > 0) {
                initGisView(bean.getGisBeans());
            }
            if (canMofify())
                loadImg();

        }
        //巡检条目
        else if (data instanceof PatrolItemList) {
            PatrolItemList patrolItems = (PatrolItemList) data;
            initGisView2(patrolItems.getItems());
            loadImg();
        }

        //提交单个电站巡检任务结果
        else if (data instanceof PatrolSingleInspec) {
            PatrolSingleInspec patrolSingleInspec = (PatrolSingleInspec) data;
            if (patrolSingleInspec.isSuccess()) {
                //提交成功时删除数据库中保存的报告
                PatrolItemDBManager.getInstance().deletePatrolItem( LocalData.getInstance().getLoginName(), inspectId);
                //发送广播
                Intent intent = new Intent(Constant.ACTION_PATROL_UPDATE);
                lbm.sendBroadcast(intent);
                assginSuccess();
                //如果有照片，则上传
                if (filePaths.size() > 0) {
                    Toast.makeText(this, getString(R.string.upload_start_wait), Toast.LENGTH_SHORT).show();
                    new Thread(new ZipCompressing(zipPath, "patrol", new File(deleteBitmapPath + File.separator), new ZipCompressing.OnZipOverListener() {

                        @Override
                        public void onZipFinish(String zipName) {

                            //上传附件
                            patrolGisPresenter.uploadAttachment(zipName, inspectId);
                        }

                        @Override
                        public void onZipFail() {
                            if (loadingDialog != null && loadingDialog.isShowing()) {
                                loadingDialog.dismiss();
                            }
                        }
                    })).start();
                } else {
                    Toast.makeText(this, getString(R.string.submit_report_succeed), Toast.LENGTH_SHORT).show();
                    finish();
                }

            } else {
                if (loadingDialog != null && loadingDialog.isShowing()) {
                    loadingDialog.dismiss();
                }
                Toast.makeText(this, getString(R.string.submit_report_failed), Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void uploadFileFailed(String message) {
        if (loadingDialog != null && loadingDialog.isShowing()) {
            loadingDialog.dismiss();
        }
        Toast.makeText(PatrolStationGis.this, getString(R.string.attachment_upload_failed), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void uploadFileSuccess() {
        if (loadingDialog != null && loadingDialog.isShowing()) {
            loadingDialog.dismiss();
        }
        Utils.deleteDirectory(patrolReportDir);
        PatrolItemDBManager.getInstance().deleteUserPic( LocalData.getInstance().getLoginName(), inspectId);
        Toast.makeText(this, getString(R.string.submit_report_succeed), Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public void loadPicture(final Bitmap bitmap) {
        String fileName = System.currentTimeMillis() + ".jpg";
        String path = PicUtils.saveFile2(bitmap, fileName, deleteBitmapPath);
        if (!bitmap.isRecycled()) {
            bitmap.recycle();
        }
        mBitmaps.add(Utils.getImageThumbnail(path, 120, 120));
        mBitmapPath.add(path);
        filePaths.add("file://" + path);
        mIsLocals.add(false); //..................................
        Message message = Message.obtain();
        message.what = UPDATE_UI;
        mHandler.sendMessage(message);
        if (picIndex < stationPicIds.size() - 1) {
            picIndex++;
            patrolGisPresenter.doRequestPic(true, stationPicIds.get(picIndex), inspectId);
        } else {
            ArrayList<String> picPaths = PatrolItemDBManager.getInstance().getAllPicPath( LocalData.getInstance().getLoginName(), inspectId);
            if (picPaths != null && picPaths.size() > 0) {
                for (String pa : picPaths) {
                    mBitmaps.add(Utils.getImageThumbnail(pa, 120, 120));
                    filePaths.add("file://" + pa);
                    mBitmapPath.add(pa);
                    mIsLocals.add(true);
                }
            }
            loadImg();
        }
    }

    @Override
    public void assginSuccess() {
        Intent intent = new Intent();
        intent.putExtra("finish", true);
        setResult(RESULT_OK, intent);
    }

    @Override
    public void initGisView(List<PatrolGisBean.GisBean> gisBeans) {
        if (gisBeans != null && gisBeans.size() > 0) {
            for (PatrolGisBean.GisBean bean : gisBeans) {
                GisView gisView = new GisView(this);
                PatrolItem item = new PatrolItem(bean.getItemId(), bean.getItemResult(), bean.getRemark());
                item.setAnnexItemName(bean.getItemName());
                gisView.setItem(item);
                if (!canMofify()) {
                    gisView.setStautsEditable(false);
                    remarkContains.setEnabled(false);
                }
                itemsContains.addView(gisView);
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_left:
                if (canMofify()) {
                    createBackDialog();
                } else {
                    String path;
                    if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                        path = Environment.getExternalStorageDirectory().getAbsolutePath();
                    } else {
                        path = getCacheDir().getAbsolutePath();
                    }
                    path = path + File.separator + "fusionHome" + File.separator + "patrol";
                    Utils.deleteDirectory(path);
                    finish();
                }
                break;
            case R.id.btn_gis_over:
                HashMap<String, String> reportParam = new HashMap<>();
                reportParam.put(KEY_S_ID, stationId);
                reportParam.put(KEY_TASK_ID, taskId);
                reportParam.put("objId", stationId);
                reportParam.put("inspectResult", "1");
                reportParam.put("completeTime", System.currentTimeMillis() + "");
                reportParam.put("remark", remarkContains.getText().toString());

                PatrolReport patrolReport = new PatrolReport();
                patrolReport.setBaseInfo(reportParam);
                patrolReport.setItems(getPatrolItems());

                List<PatrolStep> annexSteps = new ArrayList<PatrolStep>();
                annexSteps.add(new PatrolStep(1, System.currentTimeMillis(), ""));
                annexSteps.add(new PatrolStep(2, System.currentTimeMillis(), ""));
                patrolReport.setSteps(annexSteps);
                patrolGisPresenter.doCompleteSingleInspec(patrolReport);

                loadingDialog.show();
                break;
            default:
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (canMofify()) {
                createBackDialog();
            } else {
                finish();
            }
        }

        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        PatrolPicAdapter adapter = (PatrolPicAdapter) parent.getAdapter();
        if (adapter.getItemViewType(position) == adapter.TYPE_ADD) {
            if (canMofify()) {
                if (mBitmaps != null && mBitmaps.size() < 15) {
                    startCamera();
                } else {
                    Toast.makeText(mContext, getString(R.string.pictures_should_less_than_15), Toast.LENGTH_SHORT).show();
                }
            }

        } else {
            ImageBrowseActivity.startActivity(mContext, filePaths, position);
        }
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        if (canMofify()) {
            PatrolPicAdapter adapter = (PatrolPicAdapter) parent.getAdapter();
            if (adapter.getItemViewType(position) == adapter.TYPE_PIC) {
                if(mIsLocals.get(position)){
                    createDeleteImgDialog(position);
                }else {
                    ToastUtil.showMessage(getResources().getString(R.string.not_to_delete_pic));
                }
            }
        }
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gis_main);
        if(savedInstanceState != null){
            GlobalConstants.isLoginSuccess = true;
            MyApplication.reLogin(MyApplication.getContext().getResources().getString(R.string.change_system_setting));
        }
        MyApplication.getApplication().addActivity(this);
        mContext = this;
        lbm = LocalBroadcastManager.getInstance(MyApplication.getContext());
        getIntentData();
        initView();
        //得到对应电站保存图片的位置
        String path;
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            path = Environment.getExternalStorageDirectory().getAbsolutePath();
        } else {
            path = getCacheDir().getAbsolutePath();
        }
        patrolReportDir = path + File.separator + "fusionHome" + File.separator + "patrol";
        deleteBitmapPath = patrolReportDir + File.separator + "compressImg" + File.separator + inspectId;
        zipPath = patrolReportDir + File.separator + "zip" + File.separator + inspectId;
        cameraPath = patrolReportDir + File.separator + "oricamera" + File.separator + inspectId;
        patrolGisPresenter = new PatrolGisPresenter();
        patrolGisPresenter.onViewAttached(this);
        patrolGisPresenter.setModel();
        requestItemsOrReport();
    }

    @Override
    protected void onDestroy() {
        Thread thread = new Thread() {
            @Override
            public void run() {
                super.run();
                for (int i = 0; i < mIsLocals.size(); i++) {
                    if (!mIsLocals.get(i)) {
                        Utils.deleteFile(mBitmapPath.get(i));
                    }
                }

            }
        };
        thread.start();
        //删除
        Utils.deletePicDirectory();
        String path;
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            path = Environment.getExternalStorageDirectory().getAbsolutePath();
        } else {
            path = getCacheDir().getAbsolutePath();
        }
        path = path + File.separator + "fusionHome" + File.separator + "patrol";
        Utils.deleteDirectory(path);
        MyApplication.getApplication().removeActivity(this);
        super.onDestroy();
    }

    private void initView() {
        backButton = (Button) findViewById(R.id.bt_left);
        backButton.setOnClickListener(this);
        tvTitle = (TextView) findViewById(R.id.tv_title);
        tvTitle.setText(procName);
        itemsContains = (LinearLayout) findViewById(R.id.llyt_item);
        gridView = (CustomGridView) findViewById(R.id.gv_pic);
        gridView.setOnItemClickListener(this);
        gridView.setOnItemLongClickListener(this);
        completeButton = (Button) findViewById(R.id.btn_gis_over);
        completeButton.setOnClickListener(this);
        remarkContains = (ContainsEmojiEditText) findViewById(R.id.gis_remark);
        remarkContains.setText(remark);
        loadingDialog = new LoadingDialog(mContext);
        loadingDialog.setCancelable(false);
        textViewNotice = (TextView) findViewById(R.id.textView7);
        textViewNotice.setVisibility(View.GONE);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQCODE && resultCode == Activity.RESULT_OK) {
            new CompressFile().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        }
    }


    /**
     * 获取intent中的数据，从上一页面获取
     */
    void getIntentData() {
        Intent intent = getIntent();
        if (intent != null) {
            //【安全特性】获取Intent数据时，未进行异常捕获，如果攻击者传入异常数据，会造成拒绝服务漏洞  【修改人】zhaoyufeng
            try {
                inspectResult = intent.getIntExtra(KEY_RESULT, 0);
                stationId = intent.getStringExtra(KEY_S_ID);
                inspectId = intent.getStringExtra(KEY_INSPECT_ID);
                procName = intent.getStringExtra(KEY_PLANT_NAME);
                taskId = intent.getStringExtra(KEY_TASK_ID);
                remark = intent.getStringExtra(KEY_REMARK);
            } catch (Exception e) {
                Log.e(TAG, "getIntentData: " + e.getMessage());
            }
        }
        if (TextUtils.isEmpty(remark)) {
            remark = getString(R.string.no_bz_data);
        }
    }

    /**
     * 请求报告或者条目
     */
    void requestItemsOrReport() {
        HashMap<String, String> params = new HashMap<>();
        //
//隐藏提交按键
        if (!canMofify())
            completeButton.setVisibility(View.GONE);

        if (inspectResult == 1) {
            params.put(KEY_INSPECT_ID, inspectId);
            params.put(KEY_S_ID, stationId);
            params.put(KEY_LANGUAGE_TYPE, "zh");
            patrolGisPresenter.doRequestItemReport(params);

        } else {  //否则获取巡检
            remarkContains.setText("");
            List<PatrolItem> patrolItemsFromDB = (ArrayList) PatrolItemDBManager.getInstance().loadPatrolItem(LocalData.getInstance().getLoginName(), inspectId);
            if (patrolItemsFromDB != null && patrolItemsFromDB.size() > 0) {
                for (PatrolItem bean : patrolItemsFromDB) {
                    GisView gisView = new GisView(this);
                    PatrolItem item = new PatrolItem(bean.getAnnexItemId(), bean.getAnnexItemResult(), bean.getAnnexItemRemark());
                    item.setAnnexItemName(bean.getAnnexItemName());
                    gisView.setItem(item);
                    gisView.setStautsEditable(true);
                    itemsContains.addView(gisView);
                }
                if (!TextUtils.isEmpty(patrolItemsFromDB.get(0).getAnnexItemRemark())) {
                    remarkContains.setText(patrolItemsFromDB.get(0).getAnnexItemRemark());
                }
                ArrayList<String> picPaths = PatrolItemDBManager.getInstance().getAllPicPath( LocalData.getInstance().getLoginName(), inspectId);
                if (picPaths != null && picPaths.size() > 0) {
                    for (String path : picPaths) {
                        mBitmaps.add(Utils.getImageThumbnail(path, 120, 120));
                        filePaths.add("file://" + path);
                        mBitmapPath.add(path);
                        mIsLocals.add(true);
                    }
                }
                loadImg();

            } else {
                params.put(KEY_S_ID, stationId);
                params.put(KEY_LANGUAGE_TYPE, "zh");
                params.put("page", 1 + "");
                params.put("pageSize", 50 + "");
                patrolGisPresenter.doRequestItem(params);
            }
        }
    }

    /**
     * 加载图片
     */
    private void loadImg() {
        if (picAdapter == null) {
            picAdapter = new PatrolPicAdapter(this);
            if (!canMofify()) {
                picAdapter.setShowAdd(false);
            }
            picAdapter.setmBitmaps(mBitmaps);
            gridView.setAdapter(picAdapter);
        } else {
            picAdapter.setmBitmaps(mBitmaps);
        }
        if (loadingDialog != null && loadingDialog.isShowing()) {
            loadingDialog.dismiss();
        }
    }

    /**
     * 开始拍照
     */
    private void startCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraPic = cameraPath + File.separator + "pic_" + System.currentTimeMillis() + ".jpg";
        File mFile = new File(cameraPath);
        if (!mFile.exists()) {
            if (!mFile.mkdirs()) {
                Toast.makeText(this, getString(R.string.other_app_claim_res), Toast.LENGTH_SHORT).show();
            }
        }
        File file = new File(cameraPic);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
        startActivityForResult(intent, REQCODE);
    }


    /**
     * 退出对话框
     */
    private void createBackDialog() {
        /** 确定响应事件 */
        DialogInterface.OnClickListener posLis = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                PatrolItemDBManager.getInstance().savePatrolItem( LocalData.getInstance().getLoginName(),
                        inspectId, getPatrolItems(), remarkContains.getText().toString());
                finish();
            }
        };
        /** 取消响应事件 */
        DialogInterface.OnClickListener negalis = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                PatrolItemDBManager.getInstance().deletePatrolItem(LocalData.getInstance().getLoginName(),
                        inspectId);
                //真正删除本地保存图片
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Utils.deleteDirectory(deleteBitmapPath);
                    }
                }).start();

                PatrolItemDBManager.getInstance().deleteUserPic( LocalData.getInstance().getLoginName(), inspectId);
                finish();
            }
        };
        /** 创建Dialog */
        AlertDialog completeDialog = new AlertDialog.Builder(this).setMessage(getString(R.string.save_to_local))
                .setPositiveButton(getString(R.string.save), posLis)
                .setNegativeButton(getString(R.string.not_save), negalis).create();
        completeDialog.setCanceledOnTouchOutside(false);
        completeDialog.show();
    }

    /**
     * 获取每个巡检项的选择结果
     *
     * @return
     */

    private List<PatrolItem> getPatrolItems() {
        List<PatrolItem> mResult = new ArrayList<PatrolItem>();
        int count = itemsContains.getChildCount();
        for (int i = 0; i < count; i++) {
            GisView child = (GisView) itemsContains.getChildAt(i);
            PatrolItem item = child.getItem();
            mResult.add(item);
        }
        return mResult;
    }

    public void initGisView2(List<PatrolItemList.PatrolCheckItem> gisBeans) {
        if (gisBeans != null && gisBeans.size() > 0) {

            for (PatrolItemList.PatrolCheckItem bean : gisBeans) {
                GisView gisView = new GisView(this);
                PatrolItem item = new PatrolItem(bean.getItemId(), 3, "");
                item.setAnnexItemName(bean.getItemName());
                gisView.setItem(item);
                //是否可编辑
                gisView.setStautsEditable(true);
                itemsContains.addView(gisView);
            }
        }
    }


    /**
     * 创建删除对话框
     */
    private void createDeleteImgDialog(final int position) {
        DialogInterface.OnClickListener posLis = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mBitmaps.remove(position);
                String path = mBitmapPath.get(position);
                filePaths.remove("file://" + path);
                mBitmapPath.remove(position);
                mIsLocals.remove(position);
                try {
                    //删除本地文件
                    Utils.deleteFile(path);
                } catch (Exception e) {
                    // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0004_F01_Android_S02  敏感信息检查-日志中不能包含敏感信息    【修改人】：zhaoyufeng
                    Log.e(TAG, e.getMessage());
                }
                //删除数据中该条记录
                PatrolItemDBManager.getInstance().deletePicPaht2( LocalData.getInstance().getLoginName(),
                        inspectId, path);
                Message message = Message.obtain();
                message.what = UPDATE_UI;
                mHandler.sendMessage(message);
            }
        };
        DialogInterface.OnClickListener negLis = null;
        AlertDialog deleteDialog = new AlertDialog.Builder(mContext).setMessage(getString(R.string.delete_pic_or_not)).setPositiveButton(getString(R.string.delete), posLis).setNegativeButton(getString(R.string.not_delete), negLis).create();
        deleteDialog.setCanceledOnTouchOutside(false);
        deleteDialog.show();
    }

    /**
     * 后台图片压缩任务
     */
    private class CompressFile extends AsyncTask<Object, Object, Boolean> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (!loadingDialog.isShowing()) {
                loadingDialog.show();
            }
        }

        @Override
        protected Boolean doInBackground(Object... params) {
            //优化压缩流程，防止高像素手机OOM
            InputStream is = null;
            try {
                is = new FileInputStream(cameraPic);
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.outWidth = 480;
                options.outHeight = 800;
                options.inPreferredConfig = Bitmap.Config.RGB_565;
                options.inPurgeable = true;
                options.inSampleSize = 4;
                options.inInputShareable = true;
                Bitmap targetBitmap = BitmapFactory.decodeStream(is, null, options);
                int degree = Utils.getPictureDegree(cameraPic);
                if (targetBitmap == null) {
                    return false;
                }
                if (degree != 0) {
                    targetBitmap = Utils.rotaingPic(degree, targetBitmap);
                }
                Bitmap bitmap = ImageFactory.ratio(targetBitmap, 480, 800);
                String mCompressPath = saveFile2(bitmap, System.currentTimeMillis() + "camera.jpg", deleteBitmapPath);
                Bitmap thumbnailBitmap = Utils.getImageThumbnail(mCompressPath, 120, 120);
                mBitmaps.add(thumbnailBitmap);
                PatrolItemDBManager.getInstance().addPicPath( LocalData.getInstance().getLoginName(), inspectId, mCompressPath);
                mBitmapPath.add(mCompressPath);
                filePaths.add("file://" + mCompressPath);
                mIsLocals.add(true);
                if (!targetBitmap.isRecycled()) {
                    targetBitmap.recycle();
                }
                return true;
            } catch (FileNotFoundException e) {
                Log.e(TAG, "doInBackground: "+e.getMessage());
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
            Message message = Message.obtain();
            message.what = UPDATE_UI;
            mHandler.sendMessage(message);
        }
    }

    public boolean canMofify() {
        Intent intent = getIntent();
        String procState = null;
        if (intent != null) {
            //【安全特性】获取Intent数据时，未进行异常捕获，如果攻击者传入异常数据，会造成拒绝服务漏洞  【修改人】zhaoyufeng
            try {
                procState = intent.getStringExtra("procState");
            } catch (Exception e) {
                Log.e(TAG, "onCreate: " + e.getMessage());
            }
        }
        return procState != null && procState.equals(IProcState.INSPECT_EXCUTE);
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
}
