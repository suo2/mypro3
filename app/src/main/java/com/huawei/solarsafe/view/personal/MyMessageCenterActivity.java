package com.huawei.solarsafe.view.personal;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.huawei.solarsafe.R;
import com.huawei.solarsafe.bean.BaseEntity;
import com.huawei.solarsafe.bean.GlobalConstants;
import com.huawei.solarsafe.bean.defect.TodoTaskList;
import com.huawei.solarsafe.bean.notice.InforMationInfo;
import com.huawei.solarsafe.bean.notice.InforMationList;
import com.huawei.solarsafe.bean.update.UpdateCountInfo;
import com.huawei.solarsafe.presenter.maintaince.patrol.PatrolPresenter;
import com.huawei.solarsafe.presenter.personal.MyInfoPresenter;
import com.huawei.solarsafe.presenter.personal.NoticePresenter;
import com.huawei.solarsafe.utils.LocalData;
import com.huawei.solarsafe.utils.SysUtils;
import com.huawei.solarsafe.utils.Utils;
import com.huawei.solarsafe.utils.customview.LoadingDialog;
import com.huawei.solarsafe.view.BaseActivity;
import com.huawei.solarsafe.view.maintaince.todotasks.ITodoTaskView;
import com.huawei.solarsafe.view.maintaince.todotasks.TodoTaskActivity;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.huawei.solarsafe.view.personal.InforMationActivity.KEY_SEND_TIME;

/**
 * Created by P00507
 * on 2018/5/5.
 */
public class MyMessageCenterActivity extends BaseActivity implements View.OnClickListener, IMyInfoView, ITodoTaskView, INoticeView {
    private RelativeLayout rlSystemMassage;
    private RelativeLayout rlPendingTask;
    private LinearLayout rlDeviceUpdate;
    private TextView tvSystemMessageNum;
    private TextView tvPendingTaskNum;
    private TextView tvDeviceUpdateNum;
    private MyInfoPresenter myInfoPresenter;
    private PatrolPresenter patrolPresenter;
    private NoticePresenter noticePresenter;

    public List<String> rightsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myInfoPresenter = new MyInfoPresenter();
        myInfoPresenter.onViewAttached(this);
        patrolPresenter = new PatrolPresenter();
        patrolPresenter.onViewAttached(this);
        noticePresenter = new NoticePresenter();
        noticePresenter.onViewAttached(this);

        String strRights = LocalData.getInstance().getRightString();
        rightsList = Utils.stringToList(strRights);

        if (rightsList.contains("app_mobileOperation")){
            rlPendingTask.setVisibility(View.VISIBLE);
        }else{
            rlPendingTask.setVisibility(View.GONE);
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_message_center;
    }

    @Override
    protected void onResume() {
        super.onResume();
        requestData();
    }

    @Override
    protected void initView() {
        tv_title.setText(getResources().getString(R.string.message_center));
        rlSystemMassage = (RelativeLayout) findViewById(R.id.rl_system_massage);
        rlPendingTask = (RelativeLayout) findViewById(R.id.rl_pending_task);
        rlDeviceUpdate = (LinearLayout) findViewById(R.id.rl_device_update_notifications);
        rlSystemMassage.setOnClickListener(this);
        rlPendingTask.setOnClickListener(this);
        rlDeviceUpdate.setOnClickListener(this);
        tvSystemMessageNum = (TextView) findViewById(R.id.tv_system_message_num);
        tvPendingTaskNum = (TextView) findViewById(R.id.tv_pending_task_num);
        tvDeviceUpdateNum = (TextView) findViewById(R.id.tv_device_update_notifications_num);
        //只有户用用户才能进入设备升级界面
        if(LocalData.getInstance().getIsHouseholdUser()){
            rlDeviceUpdate.setVisibility(View.VISIBLE);
        }else {
            rlDeviceUpdate.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rl_system_massage:
                startActivity(new Intent(this,InforMationActivity.class));
                break;
            case R.id.rl_pending_task:
                startActivity(new Intent(this,TodoTaskActivity.class));
                break;
            case R.id.rl_device_update_notifications:
                //只有户用用户才能进入设备升级界面
                SysUtils.startActivity(this, DeviceUpdateListActivity.class);
                break;
            default:
                break;

        }

    }

    @Override
    public void requestData() {
        showLoading();
        //请求待应答升级通知数
        myInfoPresenter.requestTodoUpgradeCount();
        //请求待办
        Map<String, String> paramDefect = new HashMap<>();
        paramDefect.put("page", "1");
        paramDefect.put("pageSize", "1000");
        patrolPresenter.doRequestProcess(paramDefect);

        Map<String, String> param = new HashMap<>();
        param.put("userId", String.valueOf(GlobalConstants.userId));
        param.put("msgType", "3");
        param.put("page", "1");
        param.put("pageSize", "1000");
        param.put(KEY_SEND_TIME, String.valueOf(getThreeDayAgo()));
        param.put("isRead", "2");
        noticePresenter.doRequestInforMationList(param);
    }

    @Override
    public void getData(BaseEntity baseEntity) {
        dismissLoading();
        if (baseEntity == null) {
            return;
        }
        if (baseEntity instanceof UpdateCountInfo) {//设备升级
            UpdateCountInfo info = (UpdateCountInfo) baseEntity;
            long countUpdate = 0;
            if(info != null){
                countUpdate = info.getTodoUpgradeCount();
            }
            if(countUpdate != 0){
                tvDeviceUpdateNum.setVisibility(View.VISIBLE);
                tvDeviceUpdateNum.setText(countUpdate + "");
            }else {
                tvDeviceUpdateNum.setVisibility(View.GONE);
            }
        } else if (baseEntity instanceof TodoTaskList) {//待办
            TodoTaskList defectTodoProcessList = (TodoTaskList) baseEntity;
            int countTodo = 0;
            if (defectTodoProcessList.getList() != null && defectTodoProcessList.getList().size() > 0) {
                countTodo = defectTodoProcessList.getList().size();
            }
            if(countTodo != 0){
                tvPendingTaskNum.setVisibility(View.VISIBLE);
                tvPendingTaskNum.setText(countTodo + "");
            }else {
                tvPendingTaskNum.setVisibility(View.GONE);
            }
        } else if (baseEntity instanceof InforMationList) {//消息
            InforMationList inforMationList = (InforMationList) baseEntity;
            List<InforMationInfo> inforMationInfos = inforMationList.getinfoMationlist();
            int countInfor = 0;
            if (inforMationInfos != null) {
                for (InforMationInfo info : inforMationInfos) {
                    if (info.getReadflag() == InforMationList.WAIT_READ) {
                        countInfor++;
                    }
                }
            }
            if(countInfor != 0){
                tvSystemMessageNum.setVisibility(View.VISIBLE);
                tvSystemMessageNum.setText(countInfor + "");
            }else {
                tvSystemMessageNum.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void uploadResult(boolean ifSuccess) {

    }
    /**
     * 格式化3天前日期
     */
    private long getThreeDayAgo() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -3);
        Date date = calendar.getTime();
        long yestoday = date.getTime();
        return yestoday;
    }

    private LoadingDialog loadingDialog;

    public void showLoading() {
        if (loadingDialog == null) {
            loadingDialog = new LoadingDialog(this);
        }
        loadingDialog.show();
    }

    public void dismissLoading() {
        if (loadingDialog != null && loadingDialog.isShowing()) {
            loadingDialog.dismiss();
        }
    }
}
