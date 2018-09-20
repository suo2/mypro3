package com.huawei.solarsafe.view.personal;

import android.app.AlertDialog;
import android.app.NotificationManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.huawei.solarsafe.MyApplication;
import com.huawei.solarsafe.R;
import com.huawei.solarsafe.bean.BaseEntity;
import com.huawei.solarsafe.bean.Constant;
import com.huawei.solarsafe.bean.GlobalConstants;
import com.huawei.solarsafe.bean.ResultInfo;
import com.huawei.solarsafe.bean.notice.InforMationInfo;
import com.huawei.solarsafe.bean.notice.InforMationList;
import com.huawei.solarsafe.bean.notice.MarkMessageInfo;
import com.huawei.solarsafe.presenter.personal.NoticePresenter;
import com.huawei.solarsafe.utils.customview.LoadingDialog;
import com.huawei.solarsafe.view.BaseActivity;
import com.huawei.solarsafe.view.maintaince.defects.DefectDetailActivity;
import com.huawei.solarsafe.view.maintaince.patrol.PatrolTaskDetailActivity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InforMationActivity extends BaseActivity<NoticePresenter> implements INoticeView, View.OnClickListener, MessageTypePopupWindow.OnSelectFinish {
    PullToRefreshListView listView;
    public static final String TAG = "InforMationActivity";
    public static final String KEY_SEND_TIME = "sendTime";
    private ImageView allRead;
    private ImageView arrow;
    private MessageTypePopupWindow popupWindow;
    private String msgType = "3";
    /**
     * 要显示的数据列表
     */
    private List<InforMationInfo> list = new ArrayList<>();
    //新增的消息
    InforMationAdapter adapter;
    private LoadingDialog loadingDialog;
    /**
     * 当前查看类型
     */
    private int curType = Constant.InforMationType.ALL;
    private int page = 1;
    private int pageSize = 20;
    private int pageCount = 0;
    private LocalBroadcastManager lbm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new NoticePresenter();
        presenter.onViewAttached(this);
        allRead = (ImageView) findViewById(R.id.iv_right);
        if (allRead.getVisibility() == View.GONE) {
            allRead.setVisibility(View.VISIBLE);
        }
        allRead.setImageResource(R.drawable.yjyd);
        allRead.setOnClickListener(this);
        popupWindow = new MessageTypePopupWindow(this);
        popupWindow.setOnSelectFinish(this);
        arrow = (ImageView) findViewById(R.id.iv_alarm_type_arrow);
        arrow.setVisibility(View.VISIBLE);
        //修改消息界面只显示公告消息
        ImageView imageView = (ImageView) findViewById(R.id.iv_alarm_type_arrow);
        imageView.setVisibility(View.GONE);
        tv_title.setText(getString(R.string.message));
        loadingDialog = new LoadingDialog(this);
    }

    @Override
    public void selectItem(String name, int messageType) {
        if (popupWindow != null && popupWindow.isShowing()) {
            popupWindow.dissmiss();
        }
        curType = messageType;
        switch (curType) {
            case Constant.InforMationType.ALL:
                msgType = "";
                break;
            case Constant.InforMationType.DEFECT:
                msgType = "1";
                break;
            case Constant.InforMationType.PATROL:
                msgType = "2";
                break;
            case Constant.InforMationType.NOTICE:
                msgType = "3";
                break;
            case Constant.InforMationType.UPDATE:
                msgType = "4";
                break;
        }
        list.clear();
        adapter.notifyDataSetChanged();
        tv_title.setText(name);
        page = 1;
        pageCount = 0;
        requestData();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_alarm_type_more:
                popupWindow.show(tv_title);
                break;
            case R.id.iv_right:
                if (list != null && list.size() > 0) {
                    createNoticeDialog();
                }
                break;
            default:
                break;
        }
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

    /**
     * 创建一键阅读提示框
     *
     * @return
     */
    private void createNoticeDialog() {
        /** Dialog标题 */
        // String title = mContext.getResources().getString(R.string.app_name);
        /** Dialog正文信息 */
        //String msg = "是否把全部消息标记为已读？";
        String msg = getString(R.string.sure_all_msg_read);
        /** 确定按钮文本 */
        String posBtnText = getResources().getString(R.string.determine);
        /** 确定响应事件 */
        DialogInterface.OnClickListener posLis = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                MarkMessageInfo markMessageInfo = new MarkMessageInfo();
                ArrayList<String> ids = new ArrayList<>();
                for (InforMationInfo info : list) {
                    ids.add(info.getId());
                }
                markMessageInfo.setIds(ids);
                markMessageInfo.setIsRead(1);
                markMessageInfo.setMsgType(msgType);
                String s = new Gson().toJson(markMessageInfo);
                presenter.doRequestMarkMessage(s);
            }
        };
        /** 取消按钮文本 */
        String negaBtnText = getResources().getString(R.string.cancel);
        /** 取消响应事件 */
        DialogInterface.OnClickListener negalis = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();

            }
        };
        /** 创建Dialog */
        AlertDialog exitDialog = new AlertDialog.Builder(this).setMessage(msg).setPositiveButton(posBtnText, posLis)
                .setNegativeButton(negaBtnText, negalis).create();
        exitDialog.setCanceledOnTouchOutside(false);
        exitDialog.show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_infor_mation;
    }

    @Override
    protected void initView() {
        tv_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        listView = (PullToRefreshListView) findViewById(R.id.listview);
        arrow = (ImageView) findViewById(R.id.iv_alarm_type_arrow);
        arrow.setVisibility(View.VISIBLE);
        tv_title.setText(R.string.all_msg);

        // 下拉刷新
        listView.getLoadingLayoutProxy(true, false).setPullLabel(getString(R.string.pull_refresh));
        listView.getLoadingLayoutProxy(true, false).setRefreshingLabel(getString(R.string.updating));
        listView.getLoadingLayoutProxy(true, false).setReleaseLabel(getString(R.string.release_load));
        // 上拉加载更多，分页加载
        listView.getLoadingLayoutProxy(false, true).setPullLabel(getString(R.string.load_more));
        listView.getLoadingLayoutProxy(false, true).setRefreshingLabel(getString(R.string.updating));
        listView.getLoadingLayoutProxy(false, true).setReleaseLabel(getString(R.string.release_load));
        //分页加载数据
        listView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
                                          @Override
                                          public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                                              try {
                                                  NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                                                  manager.cancelAll();
                                              } catch (Exception e) {
                                                  Log.e(TAG, "no notification NULLPOINTEXCEPTION", e);
                                              }
                                              // 刷新产品第一页
                                              list.clear();
                                              page = 1;
                                              requestData();
                                          }

                                          @Override
                                          public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                                              if (listView.getChildCount() <= 0) {
                                                  return;
                                              }
                                              page++;
                                              if (page > pageCount && pageCount != 0) {
                                                  page--;
                                                  Toast.makeText(InforMationActivity.this, R.string.no_more_data, Toast.LENGTH_SHORT).show();
                                              }
                                              requestData();
                                          }
                                      }
        );

        adapter = new InforMationAdapter(list, InforMationActivity.this);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                InforMationInfo item = (InforMationInfo) (parent.getItemAtPosition(position));
                //list.clear();
                String keyId = item.getKeyId();
                String type = item.getMsgType();
                String msgId = item.getId();
                Intent intent;
                Map<String, String> params = new HashMap<String, String>();
                params.put("id", msgId);
                params.put("isRead", 1 + "");
                if (type.equals(String.valueOf(Constant.InforMationType.PATROL))) {
                    params.put("msgType", 2 + "");
                    intent = new Intent(InforMationActivity.this, PatrolTaskDetailActivity.class);
                    String[] split = keyId.split(",");
                    if (split.length >= 2) {
                        intent.putExtra(Constant.KEY_INSPECT_ID, split[0]);
                        intent.putExtra("currentTaskId", split[1]);
                    }

                    intent.putExtra("ifJumpFromMessageBox", true);
                    startActivity(intent);
                } else if (type.equals(String.valueOf(Constant.InforMationType.DEFECT))) {
                    params.put("msgType", 1 + "");
                    intent = new Intent(InforMationActivity.this, DefectDetailActivity.class);
                    intent.putExtra("defectId", keyId);
                    intent.putExtra("ifJumpFromMessageBox", true);
                    startActivity(intent);
                } else if (type.equals(String.valueOf(Constant.InforMationType.NOTICE))) {
                    params.put("msgType", 3 + "");
                    intent = new Intent(InforMationActivity.this, NoticeDetailActivity.class);
                    intent.putExtra("noticeId", keyId);
                    startActivity(intent);
                } else if (type.equals(String.valueOf(Constant.InforMationType.UPDATE))) {
                    params.put("msgType", 4 + "");
                    intent = new Intent(InforMationActivity.this, DeviceUpdateDetailActivity.class);
                    intent.putExtra("keyId", Long.parseLong(keyId));
                    startActivity(intent);
                }
                MarkMessageInfo markMessageInfo = new MarkMessageInfo();
                ArrayList<String> ids = new ArrayList<>();
                ids.add(params.get("id"));
                markMessageInfo.setIds(ids);
                markMessageInfo.setIsRead(1);
                markMessageInfo.setMsgType(params.get("msgType"));
                String s = new Gson().toJson(markMessageInfo);
                presenter.doRequestMarkMessage(s);
            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();
        loadingDialog.show();
        lbm = LocalBroadcastManager.getInstance(MyApplication.getContext());
        try {
            NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            manager.cancelAll();
        } catch (Exception e) {
            Log.e(TAG, "no notification NULLPOINTEXCEPTION", e);
        }
        //每次进入界面时，要查询请求数据，清空之前的数据，并刷新界面
        list.clear();
        page = 1;
        pageCount = 0;
        adapter.notifyDataSetChanged();
        requestData();
    }

    @Override
    public void requestData() {
        list.clear();
        Map<String, String> param = new HashMap<>();
        //【安全特性编号】I1 OR_SmartPVMS60_PVMS830_0004_F01 部分敏感信息APP不做长期存储 【修改人】zhaoyufeng
        param.put("userId", String.valueOf(GlobalConstants.userId));
        if (!"".equals(msgType)) {
            param.put("msgType", msgType);
        }
        param.put("page", page + "");
        param.put("pageSize", pageSize + "");
        param.put(KEY_SEND_TIME, String.valueOf(getThreeDayAgo()));
        param.put("isRead", "2");
        presenter.doRequestInforMationList(param);
    }

    @Override
    public void getData(BaseEntity baseEntity) {
        loadingDialog.dismiss();
        if(baseEntity == null){
            return;
        }
        listView.onRefreshComplete();
        if (baseEntity instanceof InforMationList) {
            InforMationList inforMationList = (InforMationList) baseEntity;
            if (page > pageCount && pageCount != 0) {
                return;
            }
            if (pageCount == 0) {
                pageCount = inforMationList.getTotal() / pageSize + 1;
            }
            if(page ==1){
                list.clear();
            }
            if (inforMationList.getinfoMationlist() != null) {
                list.addAll(inforMationList.getinfoMationlist());
            }
            if (adapter == null) {
                adapter = new InforMationAdapter(list, InforMationActivity.this);
                listView.setAdapter(adapter);
            }
            adapter.notifyDataSetChanged();
        } else if (baseEntity instanceof ResultInfo) {
            adapter.notifyDataSetChanged();
            ResultInfo resultInfo = (ResultInfo) baseEntity;
            if (resultInfo.isSuccess()) {
                list.clear();
                page = 1;
                pageCount = 0;
                requestData();
            }
        }
    }
}
