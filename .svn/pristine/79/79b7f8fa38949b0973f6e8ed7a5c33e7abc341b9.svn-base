package com.huawei.solarsafe.view.maintaince.defects;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.huawei.solarsafe.R;
import com.huawei.solarsafe.bean.GlobalConstants;
import com.huawei.solarsafe.bean.defect.DefectDetail;
import com.huawei.solarsafe.model.maintain.IProcState;
import com.huawei.solarsafe.model.maintain.defect.DefectProcEnum;
import com.huawei.solarsafe.presenter.maintaince.defect.DefectListPresenter;
import com.huawei.solarsafe.view.BaseActivity;
import com.yanzhenjie.recyclerview.swipe.Closeable;
import com.yanzhenjie.recyclerview.swipe.OnSwipeMenuItemClickListener;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuCreator;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItem;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;

import java.util.ArrayList;
import java.util.List;

import toan.android.floatingactionmenu.FloatingActionButton;
import toan.android.nineoldandroids.animation.ObjectAnimator;

import static com.huawei.solarsafe.view.maintaince.main.PatrolFragment.LOOK;

/**
 * Created by p00319 on 2017/2/16.
 */

public class DefectListActivity extends BaseActivity<DefectListPresenter> implements IDefectListView {

    private DrawerLayout drawerLayout;

    private SwipeRefreshLayout refreshLayout;

    private SwipeMenuRecyclerView mRecyclerView;

    private FloatingActionButton fabSearch;
    private ObjectAnimator animator;

    private DefectListAdapter mAdapter;

    private List<DefectDetail> detailList = new ArrayList<>();

    private String proc;
    private int pageNum = 1;
    private int pageSize = 15;
    private String dealResult;
    private String sName;
    private long startTimeS;
    private long startTiemE;
    public static final int REQUEST_CODE = 10012;
    private LinearLayout emptyView;
    private static final String TAG = "DefectListActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent intent = getIntent();
        //【安全特性】null check 【修改人】zhaoyufeng
        if (intent != null){
            //【安全特性】获取Intent数据时，未进行异常捕获，如果攻击者传入异常数据，会造成拒绝服务漏洞  【修改人】zhaoyufeng
            try {
                proc = intent.getStringExtra("proc");
            } catch (Exception e) {
                Log.e(TAG, "onCreate: " + e.getMessage());
            }
        }
        super.onCreate(savedInstanceState);
        presenter.requestList(pageNum, pageSize, proc, dealResult, sName, startTimeS, startTiemE);
    }

    @Override
    protected void initView() {
        tv_title.setText(getString(R.string.list_of_all_defects));
        for (DefectProcEnum procEnum : DefectProcEnum.values()) {
            if (procEnum.getProcId().equals(proc)) {
                tv_title.setText(procEnum.getProcName());
            }
        }
        drawerLayout = (DrawerLayout) findViewById(R.id.defect_drawerLayout);
        refreshLayout = (SwipeRefreshLayout) findViewById(R.id.defect_list_refresh_layout);
        refreshLayout.setOnRefreshListener(onRefreshListener);
        mRecyclerView = (SwipeMenuRecyclerView) findViewById(R.id.rv_defect_list);
        mAdapter = new DefectListAdapter(detailList,DefectListActivity.this);
        mAdapter.setOnItemClickListener(onItemClickListener);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setHasFixedSize(true);
        //滑动上拉加载  【安全问题单号】DTS2017113012382   注释中不能包含敏感信息  【修改人】zhaoyufeng
        mRecyclerView.addOnScrollListener(mOnScrollListener);
        // 侧滑菜单
        mRecyclerView.setSwipeMenuCreator(swipeMenuCreator);
        // 设置菜单Item点击 【安全问题单号】DTS2017113012382   注释中不能包含敏感信息  【修改人】zhaoyufeng
        mRecyclerView.setSwipeMenuItemClickListener(menuItemClickListener);

        fabSearch = (FloatingActionButton) findViewById(R.id.fab_query);
        fabSearch.setVisibility(View.GONE);
        fabSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                TODO: 打开侧边栏
            }
        });
        emptyView = (LinearLayout) findViewById(R.id.ll_empty);
        //禁止侧滑弹出菜单
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        drawerLayout.setDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {}

            @Override
            public void onDrawerOpened(View drawerView) {
                drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
            }

            @Override
            public void onDrawerStateChanged(int newState) {}
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_defect_list;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (animator != null) {
            if (!animator.isRunning()) {
                animator = ObjectAnimator.ofFloat(fabSearch, "translationY", 0);
                animator.setDuration(300);
                animator.start();
            }
        } else {
            animator = ObjectAnimator.ofFloat(fabSearch, "translationY", 0);
            animator.setDuration(300);
            animator.start();
        }
    }


    @Override
    protected DefectListPresenter setPresenter() {
        return new DefectListPresenter();
    }

    @Override
    public void requestListSuccess(List<DefectDetail> list) {
        refreshLayout.setRefreshing(false);
        if (pageNum == 1) {
            detailList.clear();
        }
        detailList.addAll(list);
        if (detailList.size() == 0) {
            emptyView.setVisibility(View.VISIBLE);
            mRecyclerView.setVisibility(View.GONE);
        }else {
            emptyView.setVisibility(View.GONE);
            mRecyclerView.setVisibility(View.VISIBLE);
        }
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void requestListFailed(String errorMsg) {
        refreshLayout.setRefreshing(false);
    }

    /**
     * 下拉刷新 【安全问题单号】DTS2017113012382   注释中不能包含敏感信息  【修改人】zhaoyufeng
     */
    SwipeRefreshLayout.OnRefreshListener onRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            pageNum = 1;
            presenter.requestList(pageNum, pageSize, proc, dealResult, sName, startTimeS, startTiemE);
        }
    };


    private RecyclerView.OnScrollListener mOnScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        }
    };

    /**
     * 菜单创建器。在Item要创建菜单的时候调用。
     */
    private SwipeMenuCreator swipeMenuCreator = new com.yanzhenjie.recyclerview.swipe.SwipeMenuCreator() {
        @Override
        public void onCreateMenu(com.yanzhenjie.recyclerview.swipe.SwipeMenu swipeLeftMenu, com.yanzhenjie.recyclerview.swipe.SwipeMenu swipeRightMenu, int viewType) {
            int width = ViewGroup.LayoutParams.WRAP_CONTENT;

            int height = ViewGroup.LayoutParams.MATCH_PARENT;

            {
                SwipeMenuItem copyItem = new SwipeMenuItem(DefectListActivity.this)
                        .setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.orange)))
                        .setText("  " + getString(R.string.copy) + "  ")
                        .setTextColor(Color.WHITE)
                        .setWidth(width)
                        .setHeight(height);
                swipeRightMenu.addMenuItem(copyItem);
            }
        }
    };

    private DefectListAdapter.OnItemClickListener onItemClickListener = new DefectListAdapter.OnItemClickListener() {
        @Override
        public void onItemClick(View view, int position) {
            Intent intent = new Intent();
            intent.putExtra("detail", detailList.get(position));
            if (detailList.get(position).getProcState().equals(IProcState.DEFECT_WRITE)
                    //【安全特性编号】I1 OR_SmartPVMS60_PVMS830_0004_F01 部分敏感信息APP不做长期存储 【修改人】zhaoyufeng
                    && String.valueOf(GlobalConstants.userId).equals(detailList.get(position).getOwnerId())) {
                intent.putExtra("isModify", true);
                intent.setClass(DefectListActivity.this, NewDefectActivity.class);
            } else {
                intent.putExtra("defectId", detailList.get(position).getDefectId() + "");
                intent.putExtra("ifJumpFromMessageBox", true);
                intent.putExtra("defectType",LOOK);
                intent.setClass(DefectListActivity.this, DefectDetailActivity.class);
            }
            startActivityForResult(intent, REQUEST_CODE);
        }
    };

    private OnSwipeMenuItemClickListener menuItemClickListener = new OnSwipeMenuItemClickListener() {

        @Override
        public void onItemClick(Closeable closeable, int adapterPosition, int menuPosition, int direction) {
            closeable.smoothCloseMenu();// 关闭被点击的菜单。

            if (menuPosition == 0) {// 复制按钮被点击。
                Intent intent = new Intent();
                intent.putExtra("detail", detailList.get(adapterPosition));
                intent.putExtra("isModify", false);
                intent.putExtra("isCopy", true);
                intent.setClass(DefectListActivity.this, NewDefectActivity.class);
                startActivityForResult(intent, REQUEST_CODE);
            }
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            if (presenter != null) {
                presenter.requestList(pageNum, pageSize, proc, dealResult, sName, startTimeS, startTiemE);
            }
        }
    }

}
