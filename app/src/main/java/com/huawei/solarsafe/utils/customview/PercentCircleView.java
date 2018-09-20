package com.huawei.solarsafe.utils.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.huawei.solarsafe.R;
import com.huawei.solarsafe.utils.Utils;

/**
 * Created by p00322 on 2017/4/28.
 */

public class PercentCircleView extends View {
    private int firstCircleColor;
    private int secondCircleColor;
    private int baseBottomColor;
    private int radius;
    private int strokeWidth;
    private int textSize;
    private Paint mPaint;
    private float mProgress = 0;
    private double maxPercent = 0;
    private int progressStr;
    private int rate = 10;
    private int step = 1;
    private double progressText;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                if (progressStr < maxPercent) {
                    mProgress += (360.0 / 100.0) * step;
                    progressStr += step;
                    invalidate();
                }
            }
        }
    };

    public PercentCircleView(Context context) {
        super(context, null);
    }

    public PercentCircleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.PercentCircleView);
        secondCircleColor = array.getColor(R.styleable.PercentCircleView_second_circle_color, Color.parseColor("#009DDF"));
        firstCircleColor = array.getColor(R.styleable.PercentCircleView_first_circle_color, Color.parseColor("#DCDCDC"));
        baseBottomColor = array.getColor(R.styleable.PercentCircleView_base_bottom_color, Color.parseColor("#FFFFFF"));
        radius = array.getDimensionPixelSize(R.styleable.PercentCircleView_cicle_radius, 20);
        strokeWidth = array.getDimensionPixelSize(R.styleable.PercentCircleView_stroke_width, 2);
        textSize = array.getDimensionPixelSize(R.styleable.PercentCircleView_text_size, Utils.dp2Px(context, 12));
        array.recycle();
        mPaint = new Paint();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int centerX = getWidth() / 2;
        int centerY = getHeight() / 2;

        mPaint.setColor(baseBottomColor);
        mPaint.setAntiAlias(true);

        RectF oval = new RectF(centerX - radius, centerY - radius, centerX + radius, centerY + radius);

        canvas.drawArc(oval, 0, 360, true, mPaint);

        mPaint.setColor(firstCircleColor);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(strokeWidth);
        canvas.drawCircle(centerX, centerY, radius, mPaint);

        mPaint.setColor(secondCircleColor);
        canvas.drawArc(oval, -90, mProgress, false, mPaint);

        mPaint.reset();
        mPaint.setColor(Color.parseColor("#333333"));
        mPaint.setTextSize(textSize);
        mPaint.setTextAlign(Paint.Align.CENTER);
        Paint.FontMetrics metrics = mPaint.getFontMetrics();
        canvas.drawText(String.valueOf(progressText) + "%", Float.valueOf(centerX+""), getHeight() / 2f - metrics.descent + (metrics.bottom - metrics.top) / 2, mPaint);

        mHandler.sendEmptyMessageDelayed(1, rate);
    }

    public void setProgress(double progressPercent) {
        progressText = progressPercent;
        if (progressPercent < 0) {
            maxPercent = 0;
        } else if (progressPercent > 100) {
            maxPercent = 100;
        } else {
            maxPercent = progressPercent;
        }
        invalidate();
    }
}
