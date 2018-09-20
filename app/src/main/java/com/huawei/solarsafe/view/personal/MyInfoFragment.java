package com.huawei.solarsafe.view.personal;


import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.GridView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.huawei.solarsafe.MyApplication;
import com.huawei.solarsafe.R;
import com.huawei.solarsafe.bean.BaseEntity;
import com.huawei.solarsafe.bean.GlobalConstants;
import com.huawei.solarsafe.bean.defect.TodoTaskList;
import com.huawei.solarsafe.bean.notice.InforMationInfo;
import com.huawei.solarsafe.bean.notice.InforMationList;
import com.huawei.solarsafe.bean.update.UpdateCountInfo;
import com.huawei.solarsafe.bean.user.info.UserInfo;
import com.huawei.solarsafe.presenter.maintaince.patrol.PatrolPresenter;
import com.huawei.solarsafe.presenter.personal.MyInfoPresenter;
import com.huawei.solarsafe.presenter.personal.NoticePresenter;
import com.huawei.solarsafe.utils.LocalData;
import com.huawei.solarsafe.utils.Utils;
import com.huawei.solarsafe.utils.customview.LoadingDialog;
import com.huawei.solarsafe.view.MainActivity;
import com.huawei.solarsafe.view.maintaince.todotasks.ITodoTaskView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.huawei.solarsafe.view.personal.InforMationActivity.KEY_SEND_TIME;

/**
 * Created by P00028 on 2017/1/3.
 */
public class MyInfoFragment extends Fragment implements IMyInfoView, ITodoTaskView, INoticeView, View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {
    private View mainView;
    private SimpleDraweeView userHeadPhoto;
    private TextView userName, email, tel;
    private GridView gridView;
    private MyInfoGridViewAdapter adapter;
    private MyInfoPresenter myInfoPresenter;
    private PatrolPresenter patrolPresenter;
    private NoticePresenter noticePresenter;
    private UserInfo userInfo;
    private List<String> rightsList;
    private NotificationManager notificationManager;
    private boolean needRefreshData = true;
    private LocalBroadcastManager lbm;

    private List<String> titleList;
    private List<Integer> photoList;
    private int allCount;//所有消息的个数
    private int heightView;

    public MyInfoFragment() {
    }

    public static MyInfoFragment newInstance() {
        MyInfoFragment fragment = new MyInfoFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myInfoPresenter = new MyInfoPresenter();
        myInfoPresenter.onViewAttached(this);
        patrolPresenter = new PatrolPresenter();
        patrolPresenter.onViewAttached(this);
        noticePresenter = new NoticePresenter();
        noticePresenter.onViewAttached(this);
        notificationManager = (NotificationManager) getContext().getSystemService(Context.NOTIFICATION_SERVICE);
        lbm = LocalBroadcastManager.getInstance(MyApplication.getContext());
        onRefresh();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mainView = inflater.inflate(R.layout.fragment_myinfo, container, false);
        initView();
        return mainView;
    }

    private void initView() {
        rightsList = new ArrayList<>();
        String strRights = LocalData.getInstance().getRightString();
        rightsList = Utils.stringToList(strRights);
        titleList = new ArrayList<>();
        photoList = new ArrayList<>();
        userHeadPhoto = (SimpleDraweeView) mainView.findViewById(R.id.my_image_view);
        userHeadPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), NewChangePersonInfoActivity.class));
            }
        });
        gridView = (GridView) mainView.findViewById(R.id.my_information_gradview);

        userName = (TextView) mainView.findViewById(R.id.user_name);
        email = (TextView) mainView.findViewById(R.id.email);
        tel = (TextView) mainView.findViewById(R.id.tel);
        //消息中心
        titleList.add(getResources().getString(R.string.message_center_my));
        photoList.add(R.drawable.icon_message_center);
//        //近端接入
//        titleList.add(getResources().getString(R.string.local_debugging_tools));
//        photoList.add(R.drawable.setting_tools);
        /**
         *  如果RIGHTS_LIST_SWITCH  true 则表示开启权限列表开关，否则表示关闭权限列表
         */
        if (MainActivity.RIGHTS_LIST_SWITCH) {
            //数采接入
            if (rightsList.contains("app_oneKeyPlant")) {
                titleList.add(getResources().getString(R.string.product_for_access_));
                photoList.add(R.drawable.icon_access_to_data_acquisition);
            }
            //根据权限列表，显示有权限的模块
            //电站管理
            if (rightsList.contains("app_plantManagement")) {
                titleList.add(getResources().getString(R.string.station_management_my));
                photoList.add(R.drawable.icon_station_manager_myimfor);
            }
            //业主管理
            if (rightsList.contains("app_userManagement")) {
                titleList.add(getResources().getString(R.string.yezhu_manager_my));
                photoList.add(R.drawable.icon_user_manager_myinfor);
            }
        }
        //企业信息
        titleList.add(getResources().getString(R.string.imformation_qiye));
        photoList.add(R.drawable.icon_company_information_myinfor);
        //设置
        titleList.add(getResources().getString(R.string.setting_my));
        photoList.add(R.drawable.icon_set_myinfor);

        ViewTreeObserver viewTreeObserver=gridView.getViewTreeObserver();
        viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (gridView.getMeasuredHeight()>0){
                    gridView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    initViewHight();//Item动态高度
                    adapter.setList(titleList, photoList, heightView);
                }
            }
        });
        adapter = new MyInfoGridViewAdapter(getContext());
        gridView.setAdapter(adapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        //取消所有状态栏消息
        notificationManager.cancelAll();
        if (needRefreshData) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    requestData();
                    showLoading();
                    needRefreshData = true;
                }
            }, 500);
            needRefreshData = false;
        }
    }

    @Override
    public void requestData() {
        allCount = 0;
        HashMap<String, String> params = new HashMap<>();
        params.put("userid", GlobalConstants.userId + "");
        myInfoPresenter.doRequestUserInfo(params);
        //请求待应答升级通知数
        myInfoPresenter.requestTodoUpgradeCount();
        //请求待办
        Map<String, String> paramDefect = new HashMap<>();
        paramDefect.put("page", "1");
        paramDefect.put("pageSize", "1000");
        if (patrolPresenter != null) {
            patrolPresenter.doRequestProcess(paramDefect);
        }

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
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
    }

    @Override
    public void getData(BaseEntity baseEntity) {
        dismissLoading();
        if (baseEntity == null) {
            return;
        }
        //用户信息
        if (baseEntity instanceof UserInfo) {
            userInfo = (UserInfo) baseEntity;
            userInfo = userInfo.getUserInfo();
            if (userInfo == null) {
                return;
            }
            if (userInfo.getUserName() != null) {
                initData();
            }
            if (userInfo.getUserAvatar() != null) {
                Utils.downloadPic(userInfo.getUserid()+"",String.valueOf(GlobalConstants.userId),System.currentTimeMillis(),userHeadPhoto);
            } else {
                Uri uri = Uri.parse("res://com.huawei.solarsafe/" + R.drawable.icon_info_head);
                userHeadPhoto.setImageURI(uri);
            }
        } else if (baseEntity instanceof UpdateCountInfo) {//设备升级
            UpdateCountInfo info = (UpdateCountInfo) baseEntity;
            long countUpdate = 0;
            if(info != null){
                countUpdate = info.getTodoUpgradeCount();
            }
            allCount += (int) countUpdate;
        } else if (baseEntity instanceof TodoTaskList) {//待办
            TodoTaskList defectTodoProcessList = (TodoTaskList) baseEntity;
            int countTodo = 0;
            if (defectTodoProcessList.getList() != null && defectTodoProcessList.getList().size() > 0) {
                countTodo = defectTodoProcessList.getList().size();
            }
            allCount += countTodo;
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
            allCount += countInfor;
        }
        //根据消息的有无来显示红点，发送广播去改变地图图标
        if(allCount != 0){
            LocalData.getInstance().setIsShowPushMassage(LocalData.getInstance().IS_PUSH_MASSAGE,false);
            adapter.setAllCount(allCount);
            Intent intent = new Intent(GlobalConstants.ACTION_SHOW_NOTIFICATION);
            lbm.sendBroadcast(intent);
        }else {
            adapter.setAllCount(allCount);
            Intent intent = new Intent();
            intent.setAction(GlobalConstants.ACTION_CANCEL_NOTIFICATION);
            lbm.sendBroadcast(intent);
        }
    }

    private static final String TAG = "MyInfoFragment";

    @Override
    public void uploadResult(boolean ifSuccess) {

    }


    private void initData() {
        //对返回数据做判空处理
        if (userInfo.getUserName() == null) {
            userName.setText("");
        } else {
            userName.setText(userInfo.getUserName());
        }
        if (TextUtils.isEmpty(userInfo.getMail())) {
            email.setVisibility(View.GONE);
        } else {
            email.setText(MyApplication.getContext().getResources().getString(R.string.email_) + userInfo.getMail());
        }
        if (userInfo.getTel() == null) {
            tel.setText(MyApplication.getContext().getResources().getString(R.string.tel_));
        } else {
            tel.setText(MyApplication.getContext().getResources().getString(R.string.tel_) + userInfo.getTel());
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }


    @Override
    public void onRefresh() {
        showLoading();
        requestData();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
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
            loadingDialog = new LoadingDialog(getActivity());
        }
        loadingDialog.show();
    }

    public void dismissLoading() {
        if (loadingDialog == null) {
            if (getActivity() != null) {
                loadingDialog = new LoadingDialog(getActivity());
                loadingDialog.dismiss();
            }
        } else {
            loadingDialog.dismiss();
        }
    }

    /**
     * 动态设置item的高度
     */
    private void initViewHight() {

        int totalHeight=gridView.getMeasuredHeight();
        //view的高度以及距离顶部的高度值
        int num = 3;
        if ((double) photoList.size() / 3 > 2 && (double) photoList.size() / 3 <= 3) {
            num = 3;
        } else if ((double) photoList.size() / 3 > 3 && (double) photoList.size() / 3 <= 4) {
            num = 4;
        }
        heightView = totalHeight / num;
    }

    @Override
    public void onClick(View v) {

    }
}
