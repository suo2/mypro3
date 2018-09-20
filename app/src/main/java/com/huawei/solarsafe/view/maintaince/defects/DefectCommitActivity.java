package com.huawei.solarsafe.view.maintaince.defects;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.huawei.solarsafe.R;
import com.huawei.solarsafe.bean.BaseEntity;
import com.huawei.solarsafe.bean.defect.ProcessReq;
import com.huawei.solarsafe.bean.defect.WorkerBean;
import com.huawei.solarsafe.bean.defect.WorkerList;
import com.huawei.solarsafe.bean.defect.WorkerReq;
import com.huawei.solarsafe.model.maintain.IProcState;
import com.huawei.solarsafe.net.CommonCallback;
import com.huawei.solarsafe.net.NetRequest;
import com.huawei.solarsafe.utils.ButtonUtils;
import com.huawei.solarsafe.utils.LocalData;
import com.huawei.solarsafe.utils.ToastUtil;
import com.huawei.solarsafe.utils.Utils;
import com.huawei.solarsafe.utils.customview.ContainsEmojiEditText;
import com.huawei.solarsafe.utils.customview.LoadingDialog;
import com.huawei.solarsafe.view.BaseActivity;
import com.huawei.solarsafe.bean.defect.PickerBean;
import com.huawei.solarsafe.view.maintaince.defects.picker.PickerDialog;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;


public class DefectCommitActivity extends BaseActivity implements View.OnClickListener {

    private LinearLayout llPersonContains;
    private TextView tvPerson;
    private TextView numberOfDefectText;
    private ContainsEmojiEditText tvContent;
    private LinearLayout llHandResultContains;
    private RadioGroup rgDealResult;
    private RadioButton rbWaitDispose;
    private Button commit;
    private PickerDialog workerDialog;
    private LinearLayout llContent;
    private TextView tvDisposeHint;//处理输入的提示文本框
    private View dividerOperator;//操作人员框分割线
    private LinearLayout llOperator;//操作人员框
    private TextView tvOperator;//操作人员名字文本框
    private PickerDialog operatorWorkerDialog;//多选(操作人员)选择框

    private String operation;
    private String proc;
    private String stationCode;
    private List<PickerBean> pickerList = new ArrayList<>();

    private LoadingDialog loadingDialog;
    private String operationDescString;
    //宋平修改：只能用用户名去判断，用户名才是唯一，姓名不唯一
    private String name;//用户名

    public ArrayList<String> operatorIdArrayList;
    public ArrayList<String> operatorNameArrayList;

    private boolean isOp;//是否是操作行为
    private String toastString="";//输入框未填写提示文本
    private int dealResult=-1;//操作人选择的处理结果序号
    private int currentDealResult=-1;//当前界面处理结果序号
    private static final String TAG = "DefectCommitActivity";
    private String procKey;
    // 如果是提交和交接  isPass=true;
    // 如果是交接  isCurrent = ture;
    private boolean isCurrent;
    private boolean isPass;
    private String procId;

    private String loginName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent intent = getIntent();
        if (intent != null){
            //【安全特性】获取Intent数据时，未进行异常捕获，如果攻击者传入异常数据，会造成拒绝服务漏洞  【修改人】zhaoyufeng
            try {
                operation = intent.getStringExtra("operation");
                proc = intent.getStringExtra("proc");
                stationCode = intent.getStringExtra("stationCode");
                isOp=intent.getBooleanExtra("isOp",false);
                dealResult=intent.getIntExtra("dealResult",-1);
                procId = intent.getStringExtra("procId");
                procKey = intent.getStringExtra("procKey");
            } catch (Exception e) {
                Log.e(TAG, "onCreate: " + e.getMessage());
            }
        }
        toastString=getResources().getString(R.string.fill_circulation_opinions);//输入框未填写提示文本
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView() {
        operatorIdArrayList=new ArrayList<>();
        operatorNameArrayList=new ArrayList<>();

        loadingDialog = new LoadingDialog(this);
        llPersonContains = (LinearLayout) findViewById(R.id.ll_person_contains);
        llPersonContains.setOnClickListener(this);
        tvPerson = (TextView) findViewById(R.id.tv_person);
        numberOfDefectText = (TextView) findViewById(R.id.number_of_defect_text);
        llContent = (LinearLayout) findViewById(R.id.ll_content);
        tvContent = (ContainsEmojiEditText) findViewById(R.id.tv_content);
        tvContent.addTextChangedListener(textWatcher);
        llHandResultContains = (LinearLayout) findViewById(R.id.ll_hand_result_contains);
        rgDealResult = (RadioGroup) findViewById(R.id.rg_deal_result);
        rbWaitDispose= (RadioButton) findViewById(R.id.rbWaitDispose);
        tvDisposeHint= (TextView) findViewById(R.id.tvDisposeHint);
        dividerOperator=findViewById(R.id.tvOperator);
        llOperator= (LinearLayout) findViewById(R.id.llOperator);
        tvOperator= (TextView) findViewById(R.id.tvOperator);

        llOperator.setOnClickListener(this);

        if(Integer.valueOf(LocalData.getInstance().getWebBuildCode())<1){
            rbWaitDispose.setVisibility(View.GONE);
        }

        //给待处理选项添加选择事件
        rbWaitDispose.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    //选中不显示人员选择框,并重置其状态
                    llPersonContains.setVisibility(View.GONE);
                    tvPerson.setText("");
                    setName("");
                }else{
                    llPersonContains.setVisibility(View.VISIBLE);
                }
            }
        });

        commit = (Button) findViewById(R.id.bt_defect_submit);
        commit.setOnClickListener(this);
        workerDialog = new PickerDialog(this, getString(R.string.select_main_responsibility_man), tvPerson);
        workerDialog.setCanceledOnTouchOutside(true);

        if(!TextUtils.isEmpty(proc)) {
            switch (proc){
                case IProcState.DEFECT_WRITE:
                    //工单填写中状态 输入框提示显示"处理意见"
                    tvDisposeHint.setText(getResources().getString(R.string.processing_opinion));
                    dividerOperator.setVisibility(View.VISIBLE);
                    toastString=getResources().getString(R.string.please_fill_in_the_processing_opinion);

                    if (IProcState.SENDBACK.equals(operation)){
                        llOperator.setVisibility(View.GONE);
                    }else{
                    if(Integer.valueOf(LocalData.getInstance().getWebBuildCode())>0 && !getPackageName().equals("com.huawei.solarsafe")){
                        llOperator.setVisibility(View.VISIBLE);
                        operatorWorkerDialog=new PickerDialog(this,getString(R.string.select_operation_man),tvOperator,2);
                        operatorWorkerDialog.setCanceledOnTouchOutside(true);
                    }
                }

                    break;
                case IProcState.DEFECT_HANDLING:
                    //消缺处理中状态 输入框提示显示"处理描述"
                    tvDisposeHint.setText(getResources().getString(R.string.process_description_colon));
                    dividerOperator.setVisibility(View.GONE);
                    llOperator.setVisibility(View.GONE);
                    toastString=getResources().getString(R.string.please_fill_in_the_processing_description);

                    if (isOp){
                        llPersonContains.setVisibility(View.GONE);
                    }

                    break;
                case IProcState.DEFECT_CONFIRMING:
                    //消缺确认中状态 输入框提示显示"验收意见"
                    tvDisposeHint.setText(getResources().getString(R.string.check_opinion));
                    dividerOperator.setVisibility(View.GONE);
                    llOperator.setVisibility(View.GONE);
                    toastString=getResources().getString(R.string.please_fill_in_the_acceptance_opinion);
                    break;
            }
        }

        if(!TextUtils.isEmpty(operation)) {
            switch (operation) {
                case IProcState.SUBMIT:
                    isPass = true;
                    isCurrent = false;
                    if (IProcState.DEFECT_HANDLING.equals(proc)) {
                        llHandResultContains.setVisibility(View.VISIBLE);
                    }
                    if (IProcState.INSPECT_EXCUTE.equals(proc)) {
                        llContent.setVisibility(View.GONE);
                        commit.setBackgroundResource(R.drawable.chamfering_button_shape_blue);
                        commit.setClickable(true);
                    }
                    if (IProcState.INSPECT_CONFIRM.equals(proc) || IProcState.DEFECT_CONFIRMING.equals(proc)) {
                        llPersonContains.setVisibility(View.GONE);
                    }
                    arvTitle.setText(getString(R.string.defect_ticket_submit));
                    commit.setText(getString(R.string.submit_confirmed));
                    break;
                case IProcState.TAKEOVER:
                    isPass = true;
                    isCurrent = true;
                    if (IProcState.DEFECT_HANDLING.equals(proc)) {
                        llHandResultContains.setVisibility(View.VISIBLE);
                    }
                    if (IProcState.INSPECT_CREATE.equals(proc)) {
                        arvTitle.setText(getString(R.string.defect_assign));
                        commit.setText(getString(R.string.defect_assign_confirmed));
                    }
                    arvTitle.setText(getString(R.string.hand_over));
                    commit.setText(getString(R.string.hand_over_confirmed));
                    break;
                case IProcState.SENDBACK:
                    isPass = false;
                    isCurrent = false;
                    llPersonContains.setVisibility(View.GONE);
                    if (IProcState.DEFECT_WRITE.equals(proc)) {
                        arvTitle.setText(R.string.fill_opinion);
                        commit.setText(getString(R.string.give_up_confirmed));
                    } else {
                        arvTitle.setText(getString(R.string.fill_return_opinion));
                        commit.setText(getString(R.string.return_confirmed));
                    }
                    break;
            }
        }

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_defect_commit;
    }

    private void showUserPicker(final int dialogType) {
        pickerList.clear();
        final WorkerReq req = new WorkerReq();
        req.setPage(1);
        req.setPageSize(50);
        req.setProcKey(procKey);
        req.setTaskKey(proc);
        req.setCurrent(isCurrent);
        req.setPass(isPass + "");
        if (stationCode != null) {
            req.setStationCode(stationCode);
        }
        String params = new Gson().toJson(req);
        NetRequest.getInstance().asynPostJsonString("/workflow/getTaskUser", params, new CommonCallback(WorkerList.class) {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
            @Override
            public void onResponse(BaseEntity response, int id) {
                if (loadingDialog != null && loadingDialog.isShowing()) {
                    loadingDialog.dismiss();
                }
                if (response instanceof WorkerList) {
                    if (((WorkerList) response).getList() != null) {
                        for (WorkerBean worker : ((WorkerList) response).getList()) {
                            PickerBean bean = new PickerBean();
                            bean.setName(worker.getLoginName());
                            bean.setId(String.valueOf(worker.getUserid()));
                            bean.setUseName(worker.getUserName());
                            bean.setMobile(worker.getTel());
                            bean.setUserType(worker.getOccupLevel());
                            pickerList.add(bean);
                        }
                        if (dialogType==2){
                            operatorWorkerDialog.setData(pickerList);
                            if(!isFinishing()||isDestroyed()) {
                                operatorWorkerDialog.show();
                            }

                        }else{
                            workerDialog.setData(pickerList, name);
                            if(!isFinishing()||isDestroyed()) {
                                workerDialog.show();
                            }
                        }
                        DisplayMetrics dm = getResources().getDisplayMetrics();
                        int displayWidth = dm.widthPixels;
                        int displayHeight = dm.heightPixels;

                        if (dialogType==2){
                            android.view.WindowManager.LayoutParams p = operatorWorkerDialog.getWindow().getAttributes(); //获取对话框当前的参数值
                            p.width = (int) (displayWidth * 0.9); //宽度设置为屏幕的0.55
                            p.height = (int) (displayHeight * 0.7); //高度设置为屏幕的0.28
                            operatorWorkerDialog.getWindow().setAttributes(p);  //设置生效
                            operatorWorkerDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent); //设置对话框背景透明 ，对于AlertDialog 就不管用了
                        }else {
                            android.view.WindowManager.LayoutParams p = workerDialog.getWindow().getAttributes(); //获取对话框当前的参数值
                            p.width = (int) (displayWidth * 0.9); //宽度设置为屏幕的0.55
                            p.height = (int) (displayHeight * 0.7); //高度设置为屏幕的0.28
                            workerDialog.getWindow().setAttributes(p);  //设置生效
                            workerDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent); //设置对话框背景透明 ，对于AlertDialog 就不管用了
                        }

                    } else {
                        ToastUtil.showMessage(getString(R.string.not_have_get_person_select));
                    }
                }
            }

            @Override
            public void onError(Call call, Exception e, int id) {
                super.onError(call, e, id);
                if (loadingDialog != null && loadingDialog.isShowing()) {
                    loadingDialog.dismiss();
                }
                ToastUtil.showMessage(getString(R.string.not_have_get_person_select));

            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Utils.deletePicDirectory();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_defect_submit:
                if(!ButtonUtils.isFastDoubleClick(R.id.bt_defect_submit)) {
                    done();
                }
                break;
            case R.id.ll_person_contains:
                loadingDialog.show();
                showUserPicker(1);
                break;
            case R.id.llOperator:
                loadingDialog.show();
                showUserPicker(2);
                break;
        }
    }

    /**
     * 完成操作
     */
    private void done() {
        Intent intent = new Intent();
        ProcessReq.Process process = new ProcessReq.Process();
        //当交接时也可以选择处理结果（可选，可不选）
        //提交时必选选择处理结果  （宋平改）
        if (IProcState.DEFECT_HANDLING.equals(proc)) {
            switch (rgDealResult.getCheckedRadioButtonId()) {
                case R.id.rb_terminated:
                    currentDealResult=2;
                    //判断操作处理结果是否和执行一致
                    if (dealResult!=-1 && dealResult!=currentDealResult){
                        Toast.makeText(this, getString(R.string.defect_notsameop), Toast.LENGTH_SHORT).show();
                        return;
                    }else{
                        intent.putExtra("dealResult", "2");
                    }
                    break;
                case R.id.rb_unterminated:
                    currentDealResult=1;
                    if (dealResult!=-1 && dealResult!=currentDealResult){
                        Toast.makeText(this, getString(R.string.defect_notsameop), Toast.LENGTH_SHORT).show();
                        return;
                    }else{
                        intent.putExtra("dealResult", "1");
                    }
                    break;
                case R.id.rb_abort:
                    currentDealResult=3;
                    if (dealResult!=-1 && dealResult!=currentDealResult){
                        Toast.makeText(this, getString(R.string.defect_notsameop), Toast.LENGTH_SHORT).show();
                        return;
                    }else{
                        intent.putExtra("dealResult", "3");
                    }
                    break;
                case R.id.rbWaitDispose:
                    currentDealResult=4;
                    if (dealResult!=-1 && dealResult!=currentDealResult){
                        Toast.makeText(this, getString(R.string.defect_notsameop), Toast.LENGTH_SHORT).show();
                        return;
                    }else{
                        intent.putExtra("dealResult", "4");
                    }
                    break;
                default:
                    if(IProcState.SUBMIT.equals(operation)){
                        Toast.makeText(this, getString(R.string.processing_result_selection), Toast.LENGTH_SHORT).show();
                        return;
                    }else {
                        break;
                    }
            }
        }
        if (llPersonContains.getVisibility() != View.GONE) {
            if ("".equals(tvPerson.getText().toString())) {
                Toast.makeText(this, getString(R.string.please_select_receipient), Toast.LENGTH_SHORT).show();
                return;
            }
        }

        if (IProcState.DEFECT_CONFIRMING.equals(proc) && IProcState.TAKEOVER.equals(operation)) {
            if ("".equals(tvPerson.getText().toString())) {
                Toast.makeText(this, getString(R.string.please_select_takeover), Toast.LENGTH_SHORT).show();
                return;
            }

        }
        if (!IProcState.SENDBACK.equals(operation)) {
            if (!IProcState.DEFECT_CONFIRMING.equals(proc) && !IProcState.INSPECT_CONFIRM.equals(proc)) {
                if (!isOp && llPersonContains.getVisibility() != View.GONE){
                    if ("".equals(tvPerson.getText().toString())) {
                        Toast.makeText(this, getString(R.string.please_select_receipient), Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
            }
            process.setIsPass("true");
            for (PickerBean bean : pickerList) {
                if (tvPerson.getText().toString().trim().equals(bean.getUseName()) && !TextUtils.isEmpty(name) && !TextUtils.isEmpty(loginName) && loginName.equals(bean.getName())) {
                    process.setRecipient(bean.getId());
                    intent.putExtra("userId", bean.getId());
                    break;
                }
            }
        } else {
            if (IProcState.DEFECT_WRITE.equals(proc)) {
                process.setIsPass("giveup");
                operation = "giveup";
            } else {
                process.setIsPass("back");
            }
        }
        //【安全特性】获取Intent数据时，未进行异常捕获，如果攻击者传入异常数据，会造成拒绝服务漏洞  【修改人】zhaoyufeng

        try {
            if (procId != null) {
                process.setProcId(procId);
            }
        } catch (Exception e) {
            Log.e(TAG, "onCreate: " + e.getMessage());
        }

        process.setOperation(operation);

        if (operatorIdArrayList.size()>0){
            process.setOperators(operatorIdArrayList);
        }


        //检查是否填写处理描述
        operationDescString = tvContent.getText().toString();
        if (llContent.getVisibility()==View.VISIBLE && TextUtils.isEmpty(operationDescString)){
            Toast.makeText(this, toastString, Toast.LENGTH_SHORT).show();
            return;
        }

        process.setOperationDesc(operationDescString);
        // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0004_F01_Android_S02  敏感信息检查-日志中不能打印d和v级别的日志    【修改人】：zhaoyufeng
        intent.putExtra("process", process);
        intent.putExtra("desc", operationDescString);
        setResult(RESULT_OK, intent);
        finish();
    }


    @Override
    protected void onResume() {
        super.onResume();
    }


    TextWatcher textWatcher = new TextWatcher() {

        private String nums;

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            nums = "(" + s.length() + "/1000" + ")";
            numberOfDefectText.setText(nums);
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };


    @Override
    protected void onPause() {
        super.onPause();
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }
}
