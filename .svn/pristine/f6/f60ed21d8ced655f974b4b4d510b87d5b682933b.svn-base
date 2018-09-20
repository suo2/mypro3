
package com.huawei.solarsafe.utils.customview.sdlv;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

class DragListView extends ListView {

    private SlideAndDragListView.OnDragDropListener mOnDragDropListener;

    private Callback.OnDragDropListener mAdapterDragDropListener;
    private Callback.OnDragDropListener mDragListDragDropListener;

    public DragManager mDragManager;

    public DragListView(Context context) {
        this(context, null);
    }

    public DragListView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DragListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        ViewGroup decorView = null;
        if (context instanceof Activity) {
            decorView = (ViewGroup) ((Activity) context).getWindow().getDecorView();
        }
        if (decorView!=null){
            mDragManager = new DragManager(context, this, decorView);
        }
    }

    protected void setDragPosition(int position) {
        View view = getChildAt(position - getFirstVisiblePosition());
        if (mOnDragDropListener != null && view instanceof ItemMainLayout) {
            ItemMainLayout itemMainLayout = (ItemMainLayout) getChildAt(position - getFirstVisiblePosition());
            itemMainLayout.getItemLeftBackGroundLayout().setVisibility(GONE);
            itemMainLayout.getItemRightBackGroundLayout().setVisibility(GONE);
            mDragManager.setDragging(true);
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return mDragManager.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return mDragManager.onTouchEvent(event) || super.onTouchEvent(event);
    }

    private View getViewByPoint(int x, int y) {
        int count = getChildCount();
        View child;
        for (int childIdx = 0; childIdx < count; childIdx++) {
            child = getChildAt(childIdx);
            if (y >= child.getTop() && y <= child.getBottom() && x >= child.getLeft() && x <= child.getRight()) {
                return child;
            }
        }
        return null;
    }

    protected void handleDragStarted(int x, int y) {
        View view = getViewByPoint(x, y);
        if (view == null) {
            return;
        }
        boolean isDragging = false;
        if (mAdapterDragDropListener != null) {
            isDragging = mAdapterDragDropListener.onDragStarted(x, y, view);
        }
        if (mDragListDragDropListener != null && isDragging) {
            mDragListDragDropListener.onDragStarted(x, y, view);
        }
        if (mOnDragDropListener != null && isDragging) {
            mOnDragDropListener.onDragViewStart(getPositionForView(view) - getHeaderViewsCount());
        }
    }

    protected void handleDragMoving(int x, int y) {
        View view = getViewByPoint(x, y);
        if (view == null) {
            return;
        }
        if (mAdapterDragDropListener != null) {
            mAdapterDragDropListener.onDragMoving(x, y, view, mOnDragDropListener);
        }
        if (mDragListDragDropListener != null) {
            mDragListDragDropListener.onDragMoving(x, y, view, null);
        }
    }

    protected void handleDragFinished(int x, int y) {
        if (mAdapterDragDropListener != null) {
            mAdapterDragDropListener.onDragFinished(x, y, mOnDragDropListener);
        }
        if (mDragListDragDropListener != null) {
            mDragListDragDropListener.onDragFinished(x, y, null);
        }
    }

    protected void setListDragDropListener(Callback.OnDragDropListener listener) {
        mDragListDragDropListener = listener;
    }

    protected void serAdapterDragDropListener(Callback.OnDragDropListener listener) {
        mAdapterDragDropListener = listener;
    }

    protected boolean isDragging() {
        return mDragManager.isDragging();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mDragManager.onSizeChanged();
    }
}
