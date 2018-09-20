package com.huawei.solarsafe.utils.customview;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.AbsListView;
import android.widget.ListView;

import com.huawei.solarsafe.R;

/**
 * 使官方的下拉刷新支持上拉加载（暂时只支持ListView）
 * Created by p00319 on 2017/2/15.
 */

public class RefreshLayout extends SwipeRefreshLayout implements AbsListView.OnScrollListener {

    /**
     * 滑动到最下面时的上拉操作
     */

    private int mTouchSlop;
    /**
     * listview实例
     */
    private ListView mListView;

    /**
     * 上拉, 到了最底部的上拉加载操作
     */
    private OnLoadListener mOnLoadListener;

    /**
     * ListView的加载中footer
     */
    private View mListViewFooter;

    /**
     * 按下时的y坐标
     */
    private int mYDown;
    /**
     * 抬起时的y坐标, 与mYDown一起用于滑动到底部时判断是上拉还是下拉
     */
    private int mLastY;
    /**
     * 是否在加载中 ( 上拉加载更多 )
     */
    private boolean isLoading = false;

    /**
     * @param context
     */
    public RefreshLayout(Context context) {
        this(context, null);
    }

    public RefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);

        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();

        mListViewFooter = LayoutInflater.from(context).inflate(R.layout.refresh_foot, null,
                false);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

        // 初始化ListView对象
        if (mListView == null) {
            getListView();
        }
    }

    /**
     * 获取ListView对象
     */
    private void getListView() {
        int childs = getChildCount();
        if (childs > 0) {
            for (int i = 0; i < childs; i++) {
                View childView = getChildAt(i);
                if (childView instanceof ListView) {
                    mListView = (ListView) childView;
                    // 设置滚动给ListView, 使得滚动的情况下也可以自动加载  【安全问题单号】DTS2017113012382   注释中不能包含敏感信息  【修改人】zhaoyufeng
                    mListView.setOnScrollListener(this);
                    // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0004_F01_Android_S02  敏感信息检查-日志中不能包含敏感信息    【修改人】：zhaoyufeng
                    Log.e(VIEW_LOG_TAG, "### 找到listview");
                    break;
                }
            }
        }
    }

    /*
     * (non-Javadoc)
     * @see android.view.ViewGroup#dispatchTouchEvent(android.view.MotionEvent)
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        final int action = event.getAction();

        switch (action) {
            case MotionEvent.ACTION_DOWN:
                // 按下
                mYDown = (int) event.getRawY();
                break;

            case MotionEvent.ACTION_MOVE:
                // 移动
                mLastY = (int) event.getRawY();
                break;

            case MotionEvent.ACTION_UP:
                // 抬起
                if (canLoad()) {
                    loadData();
                }
                break;
            default:
                break;
        }

        return super.dispatchTouchEvent(event);
    }

    /**
     * 是否可以加载更多, 条件是到了最底部, listview不在加载中, 且为上拉操作.
     *
     * @return
     */
    private boolean canLoad() {
        return isBottom() && !isLoading && isPullUp();
    }

    /**
     * 判断是否到了最底部
     */
    private boolean isBottom() {

        if (mListView != null && mListView.getAdapter() != null) {
            return mListView.getLastVisiblePosition() == (mListView.getAdapter().getCount() - 1);
        }
        return false;
    }

    /**
     * 是否是上拉操作
     *
     * @return
     */
    private boolean isPullUp() {
        return (mYDown - mLastY) >= mTouchSlop + 50;
    }

    /**
     * 如果到了最底部,而且是上拉操作.那么执行onLoad方法
     */
    private void loadData() {
        if (mOnLoadListener != null) {
            // 设置状态
            setLoading(true);
            //
            mOnLoadListener.onLoad();
        }
    }

    /**
     * @param loading
     */
    public void setLoading(boolean loading) {
        isLoading = loading;
        if (isLoading) {
            // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0004_F01_Android_S02  敏感信息检查-日志中不能包含敏感信息    【修改人】：zhaoyufeng
            Log.e("", "");
            //mListView.addFooterView(mListViewFooter);
        } else {
            //mListView.removeFooterView(mListViewFooter);
            mYDown = 0;
            mLastY = 0;
        }
    }

    /**
     * @param loadListener
     */
    public void setOnLoadListener(OnLoadListener loadListener) {
        mOnLoadListener = loadListener;
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount,
                         int totalItemCount) {
        // 滚动时到了最底部也可以加载更多
        if (canLoad()) {
            loadData();
        }
    }

    /**
     * 加载更多
     *
     * @author mrsimple
     */
    public static interface OnLoadListener {
        public void onLoad();
    }


}
