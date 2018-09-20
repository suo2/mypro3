package com.huawei.solarsafe.view.personmanagement;

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
import android.view.ActionMode;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.huawei.solarsafe.R;
import com.huawei.solarsafe.bean.BaseEntity;
import com.huawei.solarsafe.bean.GlobalConstants;
import com.huawei.solarsafe.bean.MyStationBean;
import com.huawei.solarsafe.bean.ResultInfo;
import com.huawei.solarsafe.bean.common.LogCallBack;
import com.huawei.solarsafe.bean.common.RetMsg;
import com.huawei.solarsafe.bean.defect.StationBean;
import com.huawei.solarsafe.bean.personmanagement.AreaId;
import com.huawei.solarsafe.bean.personmanagement.CountUsersByNameBean;
import com.huawei.solarsafe.bean.personmanagement.CreatePersonInfo;
import com.huawei.solarsafe.bean.personmanagement.HeaherImagePathBean;
import com.huawei.solarsafe.bean.personmanagement.RoleListBean;
import com.huawei.solarsafe.bean.personmanagement.RoleListInfo;
import com.huawei.solarsafe.bean.station.StationListBeanForPerson;
import com.huawei.solarsafe.bean.stationmagagement.DevInfo;
import com.huawei.solarsafe.bean.stationmagagement.SubDev;
import com.huawei.solarsafe.net.NetRequest;
import com.huawei.solarsafe.presenter.personal.MyInfoPresenter;
import com.huawei.solarsafe.presenter.personmanagement.PersonmanagementPresenter;
import com.huawei.solarsafe.presenter.stationmanagement.CreateStationPresenter;
import com.huawei.solarsafe.utils.MySpinner;
import com.huawei.solarsafe.utils.PicUtils;
import com.huawei.solarsafe.utils.ToastUtil;
import com.huawei.solarsafe.utils.Utils;
import com.huawei.solarsafe.utils.customview.DialogUtil;
import com.huawei.solarsafe.utils.customview.LoadingDialog;
import com.huawei.solarsafe.utils.customview.PersonPagePopupWindow;
import com.huawei.solarsafe.view.BaseActivity;
import com.huawei.solarsafe.view.personal.IMyInfoView;
import com.huawei.solarsafe.view.pnlogger.ZxingActivity;
import com.huawei.solarsafe.view.stationmanagement.ICreateStationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.huawei.solarsafe.view.maintaince.defects.NewDefectActivity.CHOOSE_PHOTO;
import static com.huawei.solarsafe.view.maintaince.defects.NewDefectActivity.TAKE_PHOTO;

public class CreatePersonActivity extends BaseActivity implements View.OnClickListener, IMyInfoView, IPersonManagementView, CompoundButton.OnCheckedChangeListener, ICreateStationView {
    private PersonmanagementPresenter personmanagementPresenter;
    private MyInfoPresenter myInfoPresenter;
    private EditText editText1, editText2, editText3, editText4, editText6, editText7;
    private MySpinner spinner;
    private RadioButton radioButton3, radioButton4, radioButton5, radioButton6;
    private Button btnUpdateHeader, btnCancel, btnSure;
    private ImageView shaoMa;
    private TextView zhiYuan;
    private List<RoleListBean.ListBean> rolelistBeans;
    private String[] roleid;
    private List<AreaId> areaid;
    private String status;
    private String domainid = "";
    String loginName;
    String userName;
    String pwd;
    String phone;
    String email;
    String description;
    private Uri mFileUri;
    //压缩前的图片的存放路径
    private String mFilePath;
    private String mCompressPath;  //压缩后的图片的存放路径
    private Bitmap imageThumbnail;//用于显示用的bitmap
    //用户信息图片存放根目录
    private String userDir = "CreatUser";
    //【安全特性编号】I1 OR_SmartPVMS60_PVMS830_0004_F01 部分敏感信息APP不做长期存储 【修改人】zhaoyufeng
    private PersonPagePopupWindow window;
    private boolean uploadPho = true;
    /**
     * 输入发管理
     */
    private InputMethodManager inputMethodManager;
    private SimpleDraweeView myHeaderView;
    private String attrString;
    private List<StationBean> stationList = new ArrayList<>();
    private StationListBeanForPerson stationListBeanForPerson;
    //树结构
    MyStationBean root;
    //是否是第一次进入
    private boolean isFirst = true;
    private CreateStationPresenter createStationPresenter;
    //是否发送邮件
    private boolean isSend = true;
    private static final String TAG = "CreatePersonActivity";
    private final String SCAN_MODULE="scanModule";
    private final int CREATE_PERSON_MODULE=4;//新建业主

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE,
                WindowManager.LayoutParams.FLAG_SECURE);
        isFirst = true;
        personmanagementPresenter = new PersonmanagementPresenter();
        personmanagementPresenter.onViewAttached(this);
        createStationPresenter = new CreateStationPresenter();
        createStationPresenter.onViewAttached(this);
        myInfoPresenter = new MyInfoPresenter();
        myInfoPresenter.onViewAttached(this);
        inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        Intent intent = getIntent();
        if (intent != null) {
            //【安全特性】获取Intent数据时，未进行异常捕获，如果攻击者传入异常数据，会造成拒绝服务漏洞  【修改人】zhaoyufeng
            try {
                Bundle b = intent.getBundleExtra("b");
                //【安全特性】DTS2018032000796    【修改人】zhaoyufeng
                if (b != null) {
                    domainid = b.getString("domainid");
                    if (domainid == null) {
                        domainid = "";
                    }
                }
            } catch (Exception e) {
                Log.e(TAG, "onCreate: " + e.getMessage());
            }
        }
        HashMap<String, String> params = new HashMap<>();
        //【安全特性编号】I1 OR_SmartPVMS60_PVMS830_0004_F01 部分敏感信息APP不做长期存储 【修改人】zhaoyufeng
        params.put("domainid", domainid);
        params.put("page", 1 + "");
        params.put("pageSize", 50 + "");
        personmanagementPresenter.doRequestQueryRoles(params);
        stationListBeanForPerson = new StationListBeanForPerson();
        requestStationList();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_create_person;
    }

    @Override
    protected void initView() {
        arvTitle.setText(R.string.add_user_title);
        tv_left.setOnClickListener(this);
        InputFilter emojiFilter = Utils.getEmojiFilter();
        InputFilter spaceFilter = Utils.getSpaceFilter();
        editText1 = (EditText) findViewById(R.id.loginname_string);
        editText2 = (EditText) findViewById(R.id.username_string);
        editText3 = (EditText) findViewById(R.id.pwd_string);
        editText3.setCustomSelectionActionModeCallback(new ActionMode.Callback() {
            @Override
            public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
                return false;
            }

            @Override
            public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
                return false;
            }

            @Override
            public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
                return false;
            }

            @Override
            public void onDestroyActionMode(ActionMode actionMode) {

            }
        });
        editText4 = (EditText) findViewById(R.id.phone_string);
        editText6 = (EditText) findViewById(R.id.mail_string);
        editText7 = (EditText) findViewById(R.id.detail_string);
        //输入框禁止表情输入,空格输入
        editText1.setFilters(new InputFilter[]{new InputFilter.LengthFilter(32),emojiFilter, spaceFilter});
        editText2.setFilters(new InputFilter[]{new InputFilter.LengthFilter(32),emojiFilter, spaceFilter});
        editText3.setFilters(new InputFilter[]{new InputFilter.LengthFilter(32),emojiFilter, spaceFilter});
        editText4.setFilters(new InputFilter[]{new InputFilter.LengthFilter(25),emojiFilter, spaceFilter});
        editText6.setFilters(new InputFilter[]{new InputFilter.LengthFilter(50),emojiFilter, spaceFilter});
        editText7.setFilters(new InputFilter[]{new InputFilter.LengthFilter(130),emojiFilter, spaceFilter});
        radioButton3 = (RadioButton) findViewById(R.id.rb_ok);
        radioButton4 = (RadioButton) findViewById(R.id.rb_no_ok);
        radioButton5 = (RadioButton) findViewById(R.id.rb_send);
        radioButton6 = (RadioButton) findViewById(R.id.rb_no_send);
        spinner = (MySpinner) findViewById(R.id.spinner_search_option_roles);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    RoleListBean.ListBean listBean = rolelistBeans.get(position);
                    roleid[0] = listBean.getId() + "";
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                roleid[0] = "";
            }
        });
        zhiYuan = (TextView) findViewById(R.id.zhiyuan);
        shaoMa = (ImageView) findViewById(R.id.shaoma);
        radioButton3.setOnCheckedChangeListener(this);
        radioButton4.setOnCheckedChangeListener(this);
        radioButton5.setOnCheckedChangeListener(this);
        radioButton6.setOnCheckedChangeListener(this);
        zhiYuan.setOnClickListener(this);
        shaoMa.setOnClickListener(this);
        shaoMa.setVisibility(View.VISIBLE);
        btnCancel = (Button) findViewById(R.id.btn_cancel);
        btnUpdateHeader = (Button) findViewById(R.id.update_header);
        window = new PersonPagePopupWindow(this, this);
        btnSure = (Button) findViewById(R.id.btn_sure);
        btnCancel.setOnClickListener(this);
        btnUpdateHeader.setOnClickListener(this);
        btnSure.setOnClickListener(this);
        roleid = new String[1];
        status = "ACTIVE";
        myHeaderView = (SimpleDraweeView) findViewById(R.id.my_image_view);
        myHeaderView.setImageResource(R.drawable.icon_info_head);

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_left:
                showBackDailog();
                break;
            case R.id.zhiyuan:
                Intent intent = new Intent(this, MyStationPickerActivity.class);
                intent.putExtra("root", root);
                intent.putExtra("domainId", domainid);
                intent.putExtra("isFirst", isFirst);
                startActivityForResult(intent, 100);
                break;
            case R.id.shaoma://标记
                new IntentIntegrator(CreatePersonActivity.this)
                        .setOrientationLocked(false)
                        .setCaptureActivity(ZxingActivity.class)
                        .addExtra(SCAN_MODULE,CREATE_PERSON_MODULE)
                        .initiateScan();
                break;
            case R.id.btn_cancel:
                showBackDailog();
                break;
            case R.id.btn_sure:
                requestData();
                break;
            case R.id.update_header:
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
        }
    }

    private ArrayList<MyStationBean> myStationBeanArrayList = new ArrayList<>();
    StringBuffer stringExtra = new StringBuffer();

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (requestCode == 100 && resultCode == RESULT_OK) {
                if (data != null) {
                    try {
                        root = (MyStationBean) data.getSerializableExtra("root");
                    } catch (Exception e) {
                        Log.e(TAG, "onActivityResult: " + e.getMessage());
                    }
                    if (root == null) {
                        return;
                    }
                    myStationBeanArrayList.clear();
                    myStationBeanArrayList = MyStationPickerActivity.collectCheckedStations(root, myStationBeanArrayList);
                    isFirst = false;
                    if (stringExtra.length() != 0) {
                        stringExtra.replace(0, stringExtra.length(), "");
                    }
                    if (myStationBeanArrayList != null) {
                        for (MyStationBean s : myStationBeanArrayList) {
                            //如果当前是域 并且无父级域，或者有父级域且父级域未被选中时
                            if ("DOMAIN".equals(s.getModel()) && (s.getP() == null || !s.getP().isChecked())) {
                                if (s.isChecked()) {
                                    if ("Msg.&topdomain".equals(s.getName())) {
                                        stringExtra.append(getString(R.string.topdomain) + ",");
                                    } else {
                                        stringExtra.append(s.getName() + ",");
                                    }
                                }
                            } else if ("STATION".equals(s.getModel()) && !s.getP().isChecked()) {
                                if (s.isChecked()) {
                                    stringExtra.append(s.getName() + ",");
                                }
                            }
                        }
                        if (!TextUtils.isEmpty(stringExtra)) {
                            String stringExtras = stringExtra.substring(0, stringExtra.length() - 1);
                            zhiYuan.setText(stringExtras);
                        } else {
                            zhiYuan.setText(R.string.click_to_select_res);
                        }
                    }
                    areaid = new ArrayList<>();
                    areaid.clear();
                    //递归遍历树结构获取所选资源
                    collectionRes(root);
                }
            } else if (requestCode == CHOOSE_PHOTO && resultCode == Activity.RESULT_OK && data != null) {
                //相册选择照片处理
                Uri selectedImage = data.getData();
                String[] filePathColumns = {MediaStore.Images.Media.DATA};
                Cursor c = getContentResolver().query(selectedImage, filePathColumns, null, null, null);
                if (null != c) {
                    c.moveToFirst();
                    int columnIndex = c.getColumnIndex(filePathColumns[0]);
                    mFilePath = c.getString(columnIndex);
                    c.close();
                    new CompressFile().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                    Toast.makeText(this, R.string.wait_for_images_compressed, Toast.LENGTH_SHORT).show();
                }
            } else if (requestCode == TAKE_PHOTO && resultCode == Activity.RESULT_OK) {
                //相机拍照处理
                new CompressFile().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                Toast.makeText(this, R.string.wait_for_images_compressed, Toast.LENGTH_SHORT).show();
            } else if (requestCode == IntentIntegrator.REQUEST_CODE && resultCode == RESULT_OK) {
                IntentResult intentResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
                if (intentResult != null) {
                    String scanResult = intentResult.getContents().trim();
                    if (TextUtils.isEmpty(scanResult)) {
                        ToastUtil.showMessage(getString(R.string.scan_content_isempty));
                    } else {
                        createStationPresenter.getStationDevByEsn(scanResult);
                    }
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "onActivityResult: " + e.getMessage());
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {//标记
        super.onNewIntent(intent);
        String scanResult=intent.getStringExtra("SN");
        if (TextUtils.isEmpty(scanResult)) {
            ToastUtil.showMessage(getString(R.string.scan_content_isempty));
        } else {
            createStationPresenter.getDevByEsn(scanResult);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //【安全特性】及时清理使用后的敏感数据  【修改人】赵宇凤
        loginName = "";
        userName = "";
        pwd = "";
        phone = "";
        email = "";
        description = "";
        Utils.deletePicDirectory();
    }

    @Override
    public void requestData() {
        loginName = editText1.getText().toString();
        userName = editText2.getText().toString();
        pwd = editText3.getText().toString();
        phone = editText4.getText().toString();
        email = editText6.getText().toString();
        description = editText7.getText().toString();
        if (Utils.checkInPutIsOk(editText1,getString(R.string.input_user_name_),32,getString(R.string.username_len_max_32))){
            return;
        }
        if (Utils.checkInPutIsOk(editText2,getString(R.string.please_input_name_),32,getString(R.string.username_len_max_32))){
            return;
        }
        if (Utils.checkInPutIsOk(editText3,getString(R.string.please_input_pwd_),32,getString(R.string.pwd_len_max_32))){
            return;
        }

        /**【安全特性】OR_SmartPVMS60_PVMS830_0001_F04_Android_S02 修改口令需满足其他安全需求
         * 手机APP约束修改口令不与当前口令重复
         * 【修改人】zhaoyufeng
         */
        if (Utils.checkInPutIsOk(editText4,getString(R.string.please_input_phone_),25,getString(R.string.tel_len_max_25))){
            return;
        }
        if (!TextUtils.isEmpty(email) && !Utils.emailValidation(email)) {
            ToastUtil.showMessage(getString(R.string.input_email_format_error_));
            return;
        }
        if ("".equals(roleid[0])) {
            ToastUtil.showMessage(getString(R.string.please_select_role_));
            return;
        }
        if (areaid == null || areaid.size() == 0) {
            ToastUtil.showMessage(getString(R.string.please_select_res_));
            return;
        }
        isLoginNameNotUnique(loginName, email,phone);
    }

    private void creatPerson() {
        CreatePersonInfo personInfo = new CreatePersonInfo();
        CreatePersonInfo.CreatePerson person = personInfo.newCreatePerson();
        person.setLoginName(loginName);
        person.setUserName(userName);
        //【安全特性编号】修改加密算法 【修改人】zhaoyufeng
        person.setPassword(pwd);
        person.setTel(phone);
        //【安全特性编号】I1 OR_SmartPVMS60_PVMS830_0004_F01 部分敏感信息APP不做长期存储 【修改人】zhaoyufeng
        person.setMail(email);
        person.setDescription(description);
        person.setDomainid(domainid);
        person.setToSend(isSend + "");
        person.setUserType("PROPRIETOR");
        person.setStatus(status);
        person.setUserAvatar(attrString);
        personInfo.setUser(person);
        personInfo.setAreaid(areaid);
        personInfo.setRoleid(roleid);
        //【安全特性编号】I1 OR_SmartPVMS60_PVMS830_0004_F01 部分敏感信息APP不做长期存储 【修改人】zhaoyufeng
        Gson gson = new Gson();
        String params = gson.toJson(personInfo);
        personmanagementPresenter.doRequestSaveUser(params);
    }

    private void isLoginNameNotUnique(String loginName, String email,String phone) {
        Map<String, String> params = new HashMap<>();
        params.put("loginName", loginName);
        params.put("mail", email);
        params.put("isSeachAll", "true");
        params.put("tel", phone);
        showLoading();
        personmanagementPresenter.doRequestCountUsersByName(params);
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
    public void getData(BaseEntity baseEntity) {
        dismissLoading();
        if (baseEntity == null) {
            return;
        }
        if (baseEntity instanceof ResultInfo) {
            ResultInfo result = (ResultInfo) baseEntity;
            if (result.success1) {
                if (uploadPho) {
                    ToastUtil.showMessage(getString(R.string.add_user_success));
                } else {
                    ToastUtil.showMessage(getString(R.string.add_user_success) + " " + getString(R.string.head_upload_fail));
                }
                setResult(RESULT_OK);
                finish();
            } else if(result.getFailCode() != 444){
                if(!TextUtils.isEmpty(result.getData()) && !result.getData().contains(getResources().getString(R.string.pwd_error_msg))){
                    ToastUtil.showMessage(result.getData());
                }
            }
        } else if (baseEntity instanceof RoleListInfo) {
            RoleListInfo roleListInfo = (RoleListInfo) baseEntity;
            if (roleListInfo != null && roleListInfo.getRoleListBean() != null && roleListInfo.getRoleListBean().getList() != null) {
                rolelistBeans = roleListInfo.getRoleListBean().getList();

                String[] strings = new String[rolelistBeans.size()];

                int tempIndex=-1;
                for (int i = 0; i < rolelistBeans.size(); i++) {
                    if ("Msg.&role_preHouseUser".equals(rolelistBeans.get(i).getRoleName())) {
                        strings[i] = getString(R.string.pre_house_user);
                        tempIndex=i;
                    } else if ("Msg.&role_preInstaller".equals(rolelistBeans.get(i).getRoleName())) {
                        strings[i] = getString(R.string.pre_installer);
                    } else if("Msg.&role_preGuest".equals(rolelistBeans.get(i).getRoleName())){
                        strings[i] = getString(R.string.pre_guest);
                    }else {
                        strings[i] = rolelistBeans.get(i).getRoleName();
                    }
                }

                if (tempIndex>=0){
                    String tempStr=strings[tempIndex];
                    strings[tempIndex]=strings[0];
                    strings[0]=tempStr;
                }

                ArrayAdapter<String> spinnerAdapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,
                        strings);
                spinnerAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner.setAdapter(spinnerAdapter1);
            } else {
                ToastUtil.showMessage(getString(R.string.role_get_fail_));
            }
        } else if (baseEntity instanceof CountUsersByNameBean) {
            CountUsersByNameBean byNameBean = (CountUsersByNameBean) baseEntity;
            if (byNameBean.getData() > 0) {
                DialogUtil.showErrorMsg(CreatePersonActivity.this, getString(R.string.username_used));
                return;
            }
            if(byNameBean.getData() ==-2){
                DialogUtil.showErrorMsg(CreatePersonActivity.this, getString(R.string.phone_used));
                return;
            }else if(byNameBean.getData()<0){
                DialogUtil.showErrorMsg(CreatePersonActivity.this, getString(R.string.email_used));
                return;
            }
            creatPerson();

        } else if (baseEntity instanceof HeaherImagePathBean) {
            HeaherImagePathBean msg = (HeaherImagePathBean) baseEntity;
            attrString = msg.getData();
        } else if (baseEntity instanceof DevInfo) {
            DevInfo devInfo = (DevInfo) baseEntity;
            if (devInfo.isExits()) {
                SubDev dev = devInfo.getDev();
                if (dev != null) {
                    if (!TextUtils.isEmpty(dev.getStationCode())) {
                        if (stationList != null && stationList.size() > 0) {
                            String strName = "";
                            boolean isContance = false;
                            for (StationBean stationBean : stationList) {
                                if (dev.getStationCode().equals(stationBean.getId())) {
                                    strName = stationBean.getName();
                                    isContance = true;
                                    break;
                                }
                            }
                            if (isContance) {
                                if (!TextUtils.isEmpty(strName)) {
                                    //请空root被选中的项
                                    if (root != null) {
                                        clearCheckedCollectionRes(root);
                                        checkedCollectionRes(root, dev.getStationCode());
                                    }
                                    zhiYuan.setText(strName);
                                    areaid = new ArrayList<>();
                                    areaid.clear();
                                    AreaId areaId = new AreaId();
                                    areaId.setId(dev.getStationCode());
                                    areaId.setModel("STATION");
                                    areaid.add(areaId);
                                } else {
                                    zhiYuan.setText(R.string.click_to_select_res);
                                }
                            } else {
                                ToastUtil.showMessage(getString(R.string.not_have_this_station_right_str));
                            }
                        }
                    } else {
                        ToastUtil.showMessage(getString(R.string.dev_not_have_station_str));
                    }
                } else {
                    ToastUtil.showMessage(getString(R.string.dev_not_have_station_str));
                }
            } else {
                ToastUtil.showMessage(getString(R.string.dev_not_have_station_str));
            }
        }
    }

    /**
     * 处理错误码
     *
     * @param data
     */
    private void dealFailCode(int data) {
        switch (data) {
            case 10025:
                DialogUtil.showErrorMsg(CreatePersonActivity.this, getString(R.string.username_used));
                return;
            case -1:
                DialogUtil.showErrorMsg(CreatePersonActivity.this, getString(R.string.email_used));
                return;
            case 10026:
                DialogUtil.showErrorMsg(CreatePersonActivity.this, getString(R.string.username_and_email_used));
                return;
        }
        showLoading();
        creatPerson();
    }

    @Override
    public void createStationSuccess() {

    }

    @Override
    public void createStationFail(int failCode,String data) {

    }

    @Override
    public void uploadResult(boolean ifSuccess, String key) {

    }

    @Override
    public void uploadResult(boolean ifSuccess) {
        if (ifSuccess) {
            // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0004_F01_Android_S02  敏感信息检查-日志中不能包含敏感信息    【修改人】：zhaoyufeng
            android.util.Log.e("uploadResult", getString(R.string.head_upload_success));
            uploadPho = true;
        } else {
            // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0004_F01_Android_S02  敏感信息检查-日志中不能包含敏感信息    【修改人】：zhaoyufeng
            android.util.Log.e("uploadResult", getString(R.string.head_upload_fail));
            uploadPho = false;
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()) {
            case R.id.rb_ok:
                if (isChecked) {
                    status = "ACTIVE";
                }
                break;
            case R.id.rb_no_ok:
                if (isChecked) {
                    status = "LOCKED";
                }
                break;
            case R.id.rb_send:
                if (isChecked) {
                    isSend = true;
                }
                break;
            case R.id.rb_no_send:
                if (isChecked) {
                    isSend = false;
                }
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
            imageThumbnail = PicUtils.getImageThumbnail(mFilePath, 720, 1080);
            mCompressPath = PicUtils.saveComprassFile(imageThumbnail, System.currentTimeMillis() + "_user.jpeg", userDir, 500);
            HashMap<String, String> param = new HashMap<>();
            param.put("userid", "-1");
            myInfoPresenter.uploadAttachment(mCompressPath, param);
            return true;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            Bitmap thumbnailBitmap = BitmapFactory.decodeFile(mCompressPath);
            myHeaderView.setImageBitmap(thumbnailBitmap);
            Toast.makeText(CreatePersonActivity.this, R.string.image_compression_succeeded, Toast.LENGTH_SHORT).show();
        }
    }


    private void requestStationList() {
        // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0001_F03_Android_S01  删除Android的demo账号    【修改人】：zhaoyufeng
        Map<String, String> params = new HashMap<>();
        params.put("domainId", domainid);
        NetRequest.getInstance().asynPostJson(NetRequest.IP + "/domain/queryUserDomainAndStaRes", params, new LogCallBack() {

            @Override
            protected void onFailed(Exception e) {

            }

            @Override
            protected void onSuccess(String data) {
                try {
                    Gson gson = new Gson();
                    Type type = new TypeToken<RetMsg<List<StationBean>>>() {
                    }.getType();
                    RetMsg<List<StationBean>> retMsg = gson.fromJson(data, type);
                    stationList = retMsg.getData();
                    stationListBeanForPerson.setStationBeans(stationList);

                    JSONArray jsonArray = new JSONObject(data).optJSONArray("data");
                    JSONObject min = null; // min id is root
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        if (!jsonObject.optString("model").equals("STATION")) {
                            // domain
                            if (min != null) {
                                if (Integer.parseInt(jsonObject.optString("id"))
                                        < Integer.parseInt(min.optString("id"))) {
                                    min = jsonObject;
                                }
                            } else {
                                min = jsonObject;
                            }
                        }
                    }

                    if (min == null) return;

                    MyStationBean station = new MyStationBean();
                    station.id = min.optString("id");
                    station.pid = min.optString("pid");
                    station.sort = min.optString("sort");
                    station.name = min.optString("name");
                    station.model = min.optString("model");
                    String check = min.optString("check");
                    if ("true".equals(check)) {
                        station.isChecked = true;
                    } else {
                        station.isChecked = false;
                    }
                    station.children = new ArrayList<>();
                    root = station;
                    // now we find the root
                    findChildren(root, jsonArray);

                } catch (Exception e) {
                    Log.e("error", e.toString());
                }
            }
        });
    }

    private void findChildren(MyStationBean parent, JSONArray jsonArray) throws JSONException {
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            MyStationBean stationBean = new MyStationBean();
            stationBean.id = jsonObject.optString("id");
            stationBean.pid = jsonObject.optString("pid");
            stationBean.sort = jsonObject.optString("sort");
            stationBean.name = jsonObject.optString("name");
            stationBean.model = jsonObject.optString("model");
            String check = jsonObject.optString("check");
            if (!stationBean.model.equals("STATION")) {
                // domain
                stationBean.children = new ArrayList<>();
                if ("true".equals(check)) {
                    stationBean.isChecked = true;
                } else {
                    stationBean.isChecked = false;
                }
                if (parent != null && parent.id.equals(String.valueOf(stationBean.pid))) {
                    stationBean.p = parent;
                    parent.children.add(stationBean);

                    findChildren(stationBean, jsonArray);
                }
            } else {
                // station
                if (parent != null && parent.id.equals(String.valueOf(stationBean.pid))) {
                    if ("true".equals(check)) {
                        stationBean.isChecked = true;
                    } else {
                        stationBean.isChecked = false;
                    }
                    stationBean.p = parent;
                    parent.children.add(stationBean);
                }
            }
        }
    }

    /**
     * 递归遍历资源
     *
     * @param root
     */
    private void collectionRes(MyStationBean root) {
        if (root.isChecked()) {
            AreaId areaId = new AreaId();
            areaId.setId(root.getId());
            areaId.setModel(root.getModel());
            areaid.add(areaId);
        } else {
            ArrayList<MyStationBean> children = root.getChildren();
            if (children != null && children.size() > 0) {
                for (MyStationBean s : children) {
                    collectionRes(s);
                }
            }
        }
    }

    /**
     * 递归遍历资源清空被选项；
     *
     * @param root
     */
    private void clearCheckedCollectionRes(MyStationBean root) {
        if (root.isChecked()) {
            root.setChecked(false);
            ArrayList<MyStationBean> children = root.getChildren();
            if (children != null && children.size() > 0) {
                for (MyStationBean s : children) {
                    clearCheckedCollectionRes(s);
                }
            }
        } else {
            ArrayList<MyStationBean> children = root.getChildren();
            if (children != null && children.size() > 0) {
                for (MyStationBean s : children) {
                    clearCheckedCollectionRes(s);
                }
            }
        }
    }

    /**
     * 递归遍历资源选中选项；
     *
     * @param root
     */
    private void checkedCollectionRes(MyStationBean root, String sid) {
        if (root.getId().equals(sid)) {
            root.setChecked(true);
        } else {
            ArrayList<MyStationBean> children = root.getChildren();
            if (children != null && children.size() > 0) {
                for (MyStationBean s : children) {
                    checkedCollectionRes(s, sid);
                }
            }
        }
    }

    private LoadingDialog loadingDialog;

    public void showLoading() {
        if (loadingDialog == null) {
            loadingDialog = new LoadingDialog(this);
        }
        loadingDialog.show();
    }

    public void dismissLoading() {
        if (loadingDialog == null) {
            loadingDialog = new LoadingDialog(this);
        }
        loadingDialog.dismiss();
    }

    private void showBackDailog(){
        DialogUtil.showChooseDialog(this, "", getString(R.string.cancel_save_str), getResources().getString(R.string.sure), getResources().getString(R.string.cancel), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        showBackDailog();
    }
}
