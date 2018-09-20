package com.huawei.solarsafe.view.maintaince.ivcurve;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.HorizontalScrollView;

/**
 * 只要将View设置进来就行
 */
public class MyHorizontalScrollView extends HorizontalScrollView {

    private View mView;
    private int canScrollMaxDuration;//能够可以滑动的最大距离
    public MyHorizontalScrollView(Context context) {
        super(context);
    }

    public MyHorizontalScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        //这句的意思就是我滚到哪里，设置进来的空间就滚到哪里
        if (mView != null) {
            mView.scrollTo(l, t);
        }
        if(canScrollMaxDuration>0 && l>canScrollMaxDuration){
            scrollTo(canScrollMaxDuration,t);
        }
    }

    /**
     * 设置联动的view
     *
     * @param view
     */
    public void setScrollView(View view) {
        mView = view;
    }

    public void setCanScrollMaxDuration(int canScrollMaxDuration) {
        this.canScrollMaxDuration = canScrollMaxDuration;
    }
    public  int getCanScrollMaxDuration(){
        return this.canScrollMaxDuration;
    }
}