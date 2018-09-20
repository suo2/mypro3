package com.huawei.solarsafe.view.pnlogger;

import android.content.Context;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.LinearLayout;

public class SlideDeleteView extends LinearLayout {
    private View mContentView;
    private View mDeleteView;
    private ViewDragHelper viewDragHelper;
    private int mContentWidth;
    private int mContentHeight;
    private int mDeleteWidth;
    private int mDeleteHeight;
    public OnSlideStateChangeListener mOnSlideDeleteListener;
    private int mTouchSlop;
    private int lastTouchX;
    private int lastInterceptX;
    boolean touchHandled = false;
    boolean interceptHandled = false;

    public SlideDeleteView(Context context) {
        this(context, null);
    }

    public SlideDeleteView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SlideDeleteView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mTouchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
    }

    public void setOnSlideDeleteListener(OnSlideStateChangeListener onSlideDeleteListener) {
        this.mOnSlideDeleteListener = onSlideDeleteListener;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        mContentWidth = mContentView.getMeasuredWidth();
        mContentHeight = mContentView.getMeasuredHeight();
        mDeleteWidth = mDeleteView.getMeasuredWidth();
        mDeleteHeight = mDeleteView.getMeasuredHeight();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        if (getChildCount() < 2) {
            throw new RuntimeException("childView必须有两个以上以满足contentView和deleteView!");
        }
        mContentView = getChildAt(0);
        mDeleteView = getChildAt(1);
        viewDragHelper = ViewDragHelper.create(this, new MyDrawHelper());
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        int x = (int) ev.getX();
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                lastInterceptX = x;
                interceptHandled = super.onInterceptTouchEvent(ev);
                viewDragHelper.processTouchEvent(ev);
                break;
            case MotionEvent.ACTION_MOVE:
                int detalX = x - lastInterceptX;
                if (Math.abs(detalX) > mTouchSlop) {
                    interceptHandled = true;
                } else {
                    interceptHandled = super.onInterceptTouchEvent(ev);
                }
                break;
            case MotionEvent.ACTION_UP:
                interceptHandled = super.onInterceptTouchEvent(ev);
                break;
            default:
                break;
        }
        return interceptHandled;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        int x = (int) ev.getX();
        viewDragHelper.processTouchEvent(ev);
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                lastTouchX = x;
                touchHandled = true;
                break;
            case MotionEvent.ACTION_MOVE:
                int detalX = x - lastTouchX;
                if (Math.abs(detalX) > mTouchSlop) {
                    touchHandled = true;
                    getParent().requestDisallowInterceptTouchEvent(true);
                } else {
                    touchHandled = super.onTouchEvent(ev);
                }
                break;
            case MotionEvent.ACTION_UP:
                touchHandled = super.onTouchEvent(ev);
                break;
            default:
                break;
        }
        return touchHandled;
    }

    @Override
    public void computeScroll() {
        if (viewDragHelper.continueSettling(true)) {
            invalidate();
        }
    }

    class MyDrawHelper extends ViewDragHelper.Callback {

        @Override
        public boolean tryCaptureView(View view, int i) {
            return view == mContentView || view == mDeleteView;
        }

        @Override
        public void onViewReleased(View releasedChild, float xvel, float yvel) {
            int conLeft = mContentView.getLeft();
            boolean needShowDeleteView = conLeft < -mDeleteWidth / 2;
            showDeleteView(needShowDeleteView);
            if (mOnSlideDeleteListener != null) {
                if (needShowDeleteView) {
                    mOnSlideDeleteListener.onOpen(SlideDeleteView.this);
                } else {
                    mOnSlideDeleteListener.onClose(SlideDeleteView.this);
                }
            }
        }

        @Override
        public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
            invalidate();
            if (changedView == mContentView) {
                int l = mContentWidth + left;
                int t = 0;
                int r = l + mDeleteWidth;
                int b = t + mDeleteHeight;
                mDeleteView.layout(l, t, r, b);
            } else if (changedView == mDeleteView) {
                int l = left - mContentWidth;
                int t = 0;
                int r = l + mContentWidth;
                int b = t + mContentHeight;
                mContentView.layout(l, t, r, b);
            }
        }

        @Override
        public int clampViewPositionHorizontal(View child, int left, int dx) {
            if (child == mContentView) {
                if (left < -mDeleteWidth) {
                    left = -mDeleteWidth;
                } else if (left > 0) {
                    left = 0;
                }
            } else if (child == mDeleteView) {
                if (left < mContentWidth - mDeleteWidth) {
                    left = mContentWidth - mDeleteWidth;
                } else if (left > mContentWidth) {
                    left = mContentWidth;
                }
            }
            return left;
        }

        @Override
        public int clampViewPositionVertical(View child, int top, int dy) {
            return super.clampViewPositionVertical(child, top, dy);
        }
    }

    public interface OnSlideStateChangeListener {
        void onClose(SlideDeleteView slideDelete);

        void onOpen(SlideDeleteView slideDelete);
    }

    public void showDeleteView(boolean isShow) {
        showDeleteView(isShow, true);
    }

    public void showDeleteView(boolean isShow, boolean isSmooth) {
        int conL;
        int conT = 0;
        int delL;
        int delT = 0;
        if (isShow) {
            // 滑动至删除键完全显示
            conL = -mDeleteWidth;
            delL = +mContentWidth - mDeleteWidth;
        } else {
            // 滑动至删除键刚好隐藏
            conL = 0;
            delL = mContentWidth;
        }
        if (isSmooth) {
            viewDragHelper.smoothSlideViewTo(mContentView, conL, conT);
            viewDragHelper.smoothSlideViewTo(mDeleteView, delL, delT);
        } else {
            mContentView.layout(conL, conT, conL + mContentWidth, conT + mContentHeight);
            mDeleteView.layout(delL, delT, delL + mDeleteWidth, delT + mDeleteHeight);
        }
        invalidate();
    }
}
