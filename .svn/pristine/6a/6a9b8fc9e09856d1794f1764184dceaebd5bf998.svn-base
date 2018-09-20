/*
 * Copyright (C) TD Tech<br>
 * All Rights Reserved.<br>
 * 
 */
package com.huawei.solarsafe.utils.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;

import com.huawei.solarsafe.MyApplication;
import com.huawei.solarsafe.R;
import com.huawei.solarsafe.utils.Utils;

/**
 * Create Date: 2015-1-26<br>
 * Create Author: Description :弧形进度条
 */
public class ArcProgressView extends View {
    /**
     * 圆的半径
     */
    private float mRadius;
    /**
     * 笔触宽度-背景宽度
     */
    private int mBackPaintWidth;
    /**
     * 笔触宽度-进度宽度
     */
    private int mProPaintWidth;
    /**
     * 笔触-背景的颜色
     */
    private int mArcBackgroundColor;
    /**
     * 进度的颜色
     */
    private int mProgressColor;
    /**
     * 笔触
     */
    private Paint mPaint;
    /**
     * 画弧的辅助矩形
     */
    private RectF mRectF;
    /**
     * 数据-名称
     */
    private String mFlagName;
    /**
     * 数据-功率值
     */
    private double mGlValue = 0;
    /**
     * 步进
     */
    private int mStepProgress;
    /**
     * 最大进度
     */
    private float mMaxProgress;
    /**
     * 最大发电量
     */
    private double mMaxPower;
    /**
     * 发电量每次递加的值
     */
    private double powerStep;
    /**
     * 百分比
     */
    private double powerpercent = 0;
    private static final String TAG = "ArcProgressView";
    private final float MIN_RADIUS = 60;

    public void setStepProgress(int stepProgress) {
        mStepProgress = stepProgress;
    }

    public void setmGlValue(double mGlValue) {
        this.mGlValue = mGlValue;
    }

    /**
     * 记录文字宽高的矩形
     */
    private Rect mRect;

    public ArcProgressView(Context context) {
        this(context, null);
    }

    public ArcProgressView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ArcProgressView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initData();
    }

    /**
     * 初始化数据
     */
    private void initData() {
        mRadius = MIN_RADIUS;
        mStepProgress = 0;
        mBackPaintWidth = (int) (mRadius / 30);
        mProPaintWidth = (int) (mRadius / 30);
        mArcBackgroundColor = getResources().getColor(android.R.color.white);
        mProgressColor = getResources().getColor(R.color.circle_orangered);
        //mFlagName = getResources().getString(R.string.ele_rate);
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mRectF = new RectF();
        mRect = new Rect();
    }

    /**
     * @param progress 进度
     * @param name     名称
     * @param glValue  功率值
     */
    public void setData(double progress, String name, double glValue) {
        this.mFlagName = name;
        mMaxPower = glValue;
        powerStep = glValue / 50.0;
        mMaxProgress = getMaxProgress(progress);
        handler.sendEmptyMessageDelayed(0, 500);
        handler.sendEmptyMessageDelayed(1, 500);
        powerpercent = progress;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int measuredWidth = getMeasuredWidth();
        int measuredHeight = getMeasuredHeight();
        float left = mProPaintWidth;
        float top = mProPaintWidth;
        if (measuredWidth > measuredHeight) {
            left += (measuredWidth - measuredHeight) / 2f;
        } else {
            top += (measuredHeight - measuredWidth) / 2f;
        }

        float right = left + 2 * mRadius;
        float bottom = top + 2 * mRadius;
        mRectF.set(left, top, right, bottom);
        // 画背景
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(mArcBackgroundColor);
        mPaint.setStrokeWidth(mBackPaintWidth);
        canvas.drawArc(mRectF, 135, 270, false, mPaint);
        drawText(canvas);
        // 画进度
        if (mStepProgress <= (int) mMaxProgress) {
            mPaint.setStyle(Paint.Style.STROKE);
            mPaint.setColor(mProgressColor);
            mPaint.setStrokeWidth(mProPaintWidth);
            canvas.drawArc(mRectF, 135, mStepProgress, false, mPaint);
        }
    }

    /**
     * @param canvas
     */
    private void drawText(Canvas canvas) {
        int measuredWidth = getMeasuredWidth();
        int measuredHeight = getMeasuredHeight();
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(getResources().getColor(android.R.color.white));
        // 画当前功率值
        String kwString = Utils.handlePowerUnit(mGlValue);
        mPaint.setTextSize((float) (mRadius / 3));
        if (kwString != null && !kwString.isEmpty()) {
            mPaint.getTextBounds(kwString, 0, kwString.length(), mRect);
        } else {
            // 用空串代替，以便测出功率height
            String tempGlValue = "1KW";
            mPaint.getTextBounds(tempGlValue, 0, tempGlValue.length(), mRect);
        }
        int width = mRect.width();
        int height = mRect.height();
        float x1 = mProPaintWidth + mRadius - width / 2.0f;
        float y1 = mProPaintWidth + mRadius + height / 3.0f;
        if (measuredWidth > measuredHeight) {
            x1 += (measuredWidth - measuredHeight) / 2f;
        } else {
            y1 += (measuredHeight - measuredWidth) / 2f;
        }
        canvas.drawText(kwString, x1, y1, mPaint);
        // 画百分比
        String bf = MyApplication.getContext().getResources().getString(R.string.real_time_load) + Utils.round(powerpercent, 0) + "%" ;
        String s = MyApplication.getContext().getResources().getString(R.string.real_time_load) + 0+"%";
        mPaint.setTextSize(mRadius / 7);
        if (bf.equals(s)) {
            mPaint.getTextBounds(s, 0, s.length(), mRect);
        } else {
            mPaint.getTextBounds(bf, 0, bf.length(), mRect);
        }
        int width2 = mRect.width();
        int height2 = mRect.height();
        float x2 = mProPaintWidth + mRadius - width2 / 2.0f;
        float y2 = mProPaintWidth + mRadius + height2 + height;
        if (measuredWidth > measuredHeight) {
            x2 += (measuredWidth - measuredHeight) / 2f;
        } else {
            y2 += (measuredHeight - measuredWidth) / 2f;
        }
//        Toast.makeText(getContext(), "bf__"+bf, Toast.LENGTH_SHORT).show();
       // Log.e(TAG, "getData:  bf" + bf  );
        //Log.e(TAG, "getData:  powerpercent" + powerpercent  );
        canvas.drawText(bf, x2, y2, mPaint);
        if (mFlagName != null && !mFlagName.isEmpty()) {
            // 画当前功率名称
            mPaint.setTextSize(mRadius / 6);
            mPaint.getTextBounds(mFlagName, 0, mFlagName.length(), mRect);
            int width3 = mRect.width();
            int height3 = mRect.height();
            float x3 = mProPaintWidth + mRadius - width3 / 2.0f;
            float y3 = mProPaintWidth + mRadius + mRadius / 1.5f + height3;
            if (measuredWidth > measuredHeight) {
                x3 += (measuredWidth - measuredHeight) / 2f;
            } else {
                y3 += (measuredHeight - measuredWidth) / 2f;
            }
            //canvas.drawText(mFlagName, x3, y3, mPaint);
        }
        // 画标题
        String title = MyApplication.getContext().getResources().getString(R.string.current_generation_capacity);
        mPaint.setTextSize(mRadius / 6);
        mPaint.getTextBounds(title, 0, title.length(), mRect);
        int width4 = mRect.width();
        int height4 = mRect.height();
        float x4 = mProPaintWidth + mRadius - width4 / 2.0f;
        float y4 = mProPaintWidth + mRadius -height - height4;
        if (measuredWidth > measuredHeight) {
            x4 += (measuredWidth - measuredHeight) / 2f;
        } else {
            y4 += (measuredHeight - measuredWidth) / 2f;
        }
        canvas.drawText(title, x4, y4, mPaint);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = resolveMeasured(widthMeasureSpec, getSuggestedMinimumWidth());
        int height = resolveMeasured(heightMeasureSpec, getSuggestedMinimumHeight());
        setMeasuredDimension(width, height);
        mRadius = Math.min(width, height) / (2 + 1 / 20f);
        mBackPaintWidth = (int) (mRadius / 30);
        mProPaintWidth = (int) (mRadius / 30);
    }

    private int resolveMeasured(int measureSpec, int desired) {
        int result = 0;
        int specSize = MeasureSpec.getSize(measureSpec);
        switch (MeasureSpec.getMode(measureSpec)) {
            case MeasureSpec.UNSPECIFIED:
                result = desired;
                break;
            case MeasureSpec.AT_MOST:
                result = Math.min(specSize, desired);
                break;
            case MeasureSpec.EXACTLY:
            default:
                result = specSize;
                break;
        }
        return result;
    }

    /**
     * 把数据转化为度数
     */
    private float getMaxProgress(double rate) {
        float maxProgress = (float) (rate * 2.7);
        return maxProgress;
    }

    @Override
    protected int getSuggestedMinimumHeight() {
        int tempMinHeight = (int) (2 * MIN_RADIUS + 2 * mProPaintWidth);
        return Math.max(tempMinHeight, super.getSuggestedMinimumHeight());
    }

    @Override
    protected int getSuggestedMinimumWidth() {
        int tempMinWidth = (int) (2 * MIN_RADIUS + 2 * mProPaintWidth);
        return Math.max(tempMinWidth, super.getSuggestedMinimumWidth());
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    if (mStepProgress <= mMaxProgress) {
                        mStepProgress += 5;
                        if (mStepProgress > mMaxProgress) {
                            mStepProgress = (int) mMaxProgress;
                        } else {
                            handler.sendEmptyMessageDelayed(0, 30);
                        }
                        postInvalidate();
                    }
                    break;
                case 1:
                    if (mGlValue <= mMaxPower) {
                        mGlValue += powerStep;
                        if (mGlValue > mMaxPower) {
                            mGlValue = mMaxPower;
                        } else {
                            handler.sendEmptyMessageDelayed(1, 30);
                        }
                        postInvalidate();
                    }
                    break;
                default:
                    break;
            }
        }
    };
}