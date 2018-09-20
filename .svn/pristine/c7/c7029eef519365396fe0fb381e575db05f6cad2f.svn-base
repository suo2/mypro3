package com.huawei.solarsafe.utils.customview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.widget.ScrollView;

/**
 * Created by p00507
 * on 2017/8/29.
 * 当滑动到底部时，做相应的操作
 */

public class ScrollBottomScrollView extends ScrollView {
    private OnScrollToBottomListener mOnScrollToBottomListener;
    private int mTouchSlop;
    private int downY;
    private int downX;
    private boolean isSvToBottom;
    public ScrollBottomScrollView(Context context) {
        super(context);
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }

    public ScrollBottomScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }

    public ScrollBottomScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }
    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt){
        super.onScrollChanged(l,t,oldl,oldt);
        // 滑动的距离加上本身的高度与子View的高度对比
        if(t + getHeight() >=  getChildAt(0).getMeasuredHeight()){
            // ScrollView滑动到底部
            isSvToBottom = true;
            if(mOnScrollToBottomListener != null) {
                mOnScrollToBottomListener.onScrollToBottom();
            }
        } else {
            isSvToBottom = false;
            if(mOnScrollToBottomListener != null) {
                mOnScrollToBottomListener.onNotScrollToBottom();
            }
        }
    }

    public void setScrollToBottomListener(OnScrollToBottomListener listener) {
        this.mOnScrollToBottomListener = listener;
    }

    public interface OnScrollToBottomListener {
        void onScrollToBottom();
        void onNotScrollToBottom();
    }

    /**
     * @param e
     * @return
     * 当滑动到底部时，还是滑动事件就将事件拦截了(只要是滑动就拦截)
     */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent e) {
        int action = e.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                downX = (int) e.getRawX();
                downY = (int) e.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                int moveY = (int) e.getRawY();
                if (downY - moveY> mTouchSlop) {
                    return true;
                }
        }
        return super.onInterceptTouchEvent(e);
    }

    /**
     * @param ev
     * @return
     * 当滑动到底部时，如果还是滑动事件，将事件返回到上层
     */
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        int action = ev.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                downX = (int) ev.getRawX();
                downY = (int) ev.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                int moveY = (int) ev.getRawY();
                if (isSvToBottom && downY - moveY> mTouchSlop) {
                    return false;
                }
        }
        return super.onTouchEvent(ev);
    }
}
