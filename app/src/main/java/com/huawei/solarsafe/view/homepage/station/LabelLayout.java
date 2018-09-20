package com.huawei.solarsafe.view.homepage.station;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.huawei.solarsafe.R;

import java.util.List;


/**
 * Created by P00708 on 2018/6/26.
 */

public class LabelLayout extends LinearLayout {

    private List<LabelItemView> labelItemViews;
    private int measureWidth =0;
    private int measureHeight =0;
    private Context context;
    private boolean isAddView = false;
    public LabelLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        setOrientation(VERTICAL);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        measureWidth = getMeasuredWidth();
        measureHeight = getMeasuredHeight();
        if(labelItemViews != null && labelItemViews.size()>0){
            if(isAddView){
                isAddView = false;
                initView();
            }

        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);

    }

    public void addLabelItemView(List<LabelItemView> labelItemViews){
        this.labelItemViews = labelItemViews;
        removeChildView();
        if(measureWidth >0){
            isAddView = false;
            initView();
        }else{
            isAddView = true;
        }
    }
    private void initView(){
        for(LabelItemView labelItemView:labelItemViews){
            labelItemView.measure(measureWidth,measureHeight);
        }
        LinearLayout linearLayout = new LinearLayout(context);
        linearLayout.setOrientation(HORIZONTAL);
        addView(linearLayout);
        int childWidth=0;
        int marginsWidth = (int)context.getResources().getDimension(R.dimen.size_10dp);
        for(LabelItemView labelItemView:labelItemViews){
            if(labelItemView.getMeasuredWidth()>measureWidth){
                return;
            }
            if(childWidth +labelItemView.getMeasuredWidth()+marginsWidth>measureWidth){
                linearLayout = new LinearLayout(context);
                linearLayout.setOrientation(HORIZONTAL);
                childWidth = 0;
                addView(linearLayout);
            }
            linearLayout.addView(labelItemView);
            childWidth +=labelItemView.getMeasuredWidth();
            if(linearLayout.getChildCount() >1){
                LayoutParams params = (LayoutParams) labelItemView.getLayoutParams();
                params.setMargins(marginsWidth,0,0,0);
                labelItemView.setLayoutParams(params);
                childWidth +=marginsWidth;
            }
        }
    }
    private void removeChildView(){
        for(int i=0;i<getChildCount();i++){
            LinearLayout linearLayout = (LinearLayout) getChildAt(i);
            linearLayout.removeAllViews();
        }
        removeAllViews();
    }
}
