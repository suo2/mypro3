package com.huawei.solarsafe.view.homepage.station;


import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

import com.huawei.solarsafe.R;
import com.huawei.solarsafe.utils.Utils;

public class TopSegmentationView extends View {
    //progress bar color
    int barColor = getResources().getColor(R.color.common_white);
    //every bar segment height
    int strokeWidth = 10;
    Paint mPaint;
    Path path;

    public TopSegmentationView(Context context) {
        super(context);
    }

    public TopSegmentationView(Context context, AttributeSet attrs) {
        super(context, attrs, 0);
        TypedArray t = context.obtainStyledAttributes(attrs,
                R.styleable.topView, 0, 0);
        strokeWidth = t.getDimensionPixelSize(R.styleable.topView_topView_stroke_width, strokeWidth);
        barColor = t.getColor(R.styleable.topView_topView_color, barColor);
        t.recycle();
        path = new Path();
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(barColor);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float sw = this.getMeasuredWidth();
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(getResources().getColor(R.color.common_white));
        path.moveTo(0, Utils.dp2Px(this.getContext(), 100));
        path.quadTo(sw / 2, Utils.dp2Px(this.getContext(), 80), sw, Utils.dp2Px(this.getContext(), 100));
        path.close();
        canvas.drawPath(path, mPaint);
        path.reset();
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(strokeWidth);
        mPaint.setColor(getResources().getColor(R.color.top_arc_view));
        path.moveTo(0, Utils.dp2Px(this.getContext(), 98));
        path.quadTo(sw / 2, Utils.dp2Px(this.getContext(), 78), sw, Utils.dp2Px(this.getContext(), 98));
        canvas.drawPath(path, mPaint);
    }

}
