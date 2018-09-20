/*
 * Copyright (C) TD Tech<br>
 * All Rights Reserved.<br>
 * 
 */
package com.huawei.solarsafe.view.homepage.station;


import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;

import com.huawei.solarsafe.MyApplication;
import com.huawei.solarsafe.R;
import com.huawei.solarsafe.utils.Utils;

/**
 * Create Date: 2014-12-8<br>
 * Create Author: zWX239308<br>
 * Description :水平进度控件
 */
public class HorizontalProgress extends View {

    /**
     * 默认控件高度
     */
    private final static int DEFAULTHEIGHT = 60;
    /**
     * 默认Margin值
     */
    private final static int DEFAULTMARGIN = 30;
    /**
     * 默认字体大小
     */
    private final static int DEFAULTTEXTSIZE = 15;

    /**
     * 进度值
     */
    private double mProgress = 0;
    // 进度值最大值
    private double progressMax;
    // 初始进度值
    private double mOriginalProgress;
    /**
     * 进度颜色
     */
    private int mProgressColor = Color.parseColor("#FFB240");

    /**
     * 控件宽度
     */
    private int width = 0;
    /**
     * 控件高度
     */
    private int height = 0;
    /**
     * 边框颜色
     */
    private int mBorderColor = 0;
    /**
     * 进度文字margin
     */
    private int mTextMargin = 0;
    /**
     * 进度字体大小
     */
    private int mTextSize = 0;
    /**
     * 最大进度值，默认100%
     */
    private float mStandard = 100;

    // 数据
    private String textValue = "";

    /**
     * @param context
     */
    public HorizontalProgress(Context context) {
        this(context, null);
    }

    /**
     * @param context
     * @param attrs
     */
    public HorizontalProgress(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    /**
     * @param context
     * @param attrs
     * @param defStyleAttr
     */
    public HorizontalProgress(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray mTypedArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.HorizontalProgress,
                defStyleAttr, 0);
        // 获取控件的高度
        if (mTypedArray.hasValue(R.styleable.HorizontalProgress_hpHeight)) {
            height = mTypedArray.getDimensionPixelSize(R.styleable.HorizontalProgress_hpHeight, DEFAULTHEIGHT);
        }
        // 获取进度值margin left的值
        if (mTypedArray.hasValue(R.styleable.HorizontalProgress_hpTextMarginLeft)) {
            mTextMargin = mTypedArray.getDimensionPixelSize(R.styleable.HorizontalProgress_hpTextMarginLeft,
                    DEFAULTMARGIN);
        }
        // 获取进度值字体大小
        if (mTypedArray.hasValue(R.styleable.HorizontalProgress_hpTextSize)) {
            mTextSize = mTypedArray.getDimensionPixelSize(R.styleable.HorizontalProgress_hpTextSize, DEFAULTTEXTSIZE);
        }
        mTypedArray.recycle();
        postInvalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        init(canvas);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        width = getMeasuredWidth();
    }

    private void init(Canvas canvas) {
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        RectF r2 = new RectF();
        r2.left = 0;
        r2.right = width;
        r2.top = 0;
        r2.bottom = height;
        // 画矩形背景
        paint.setColor(Color.parseColor("#FFF4E4"));
        paint.setAlpha(100);
        paint.setStyle(Style.FILL);
        canvas.drawRoundRect(r2, Utils.dp2Px(MyApplication.getContext(), 5), Utils.dp2Px(MyApplication.getContext(), 5), paint);
        // 画进度矩形
        paint.setColor(mProgressColor);
        paint.setAlpha(255);
        paint.setStyle(Style.FILL);
        RectF r1 = new RectF();
        r1.left = 0;
        r1.right = (float) mProgress / mStandard * width;
        r1.top = 0;
        r1.bottom = height;
        canvas.drawRoundRect(r1, Utils.dp2Px(MyApplication.getContext(), 5), Utils.dp2Px(MyApplication.getContext(), 5), paint);
        // 画矩形边框
        paint.setColor(mBorderColor);
        paint.setStrokeWidth(1);
        paint.setStyle(Style.STROKE);
        canvas.drawRoundRect(r2, Utils.dp2Px(MyApplication.getContext(), 5), Utils.dp2Px(MyApplication.getContext(), 5), paint);
        if (!Utils.isDoubleMinValue(mOriginalProgress)) {
            // 画进度值
            paint.setColor(Color.parseColor("#A26100"));
            paint.setTextSize(mTextSize);
            paint.setStyle(Style.FILL);

            Rect rect2 = new Rect();
            paint.getTextBounds(textValue, 0, textValue.length(), rect2);

            //进度条长度
            double progressWidth=mProgress/mStandard*width;
            //文本长度
            double textNeedWidth=rect2.width() + 2*mTextMargin;

            //判断文本显示方式
            if (progressWidth>=textNeedWidth){
                float x = (float) mProgress / mStandard * width - rect2.width() - mTextMargin;
                float y = height / 2 + rect2.height() / 2;
                canvas.drawText(textValue, x, y, paint);
            }else{
                float x = mTextMargin;
                float y = height / 2 + rect2.height() / 2;
                canvas.drawText(textValue, x, y, paint);
            }
        }
    }


    /**
     * 设置进度
     *
     * @param progress
     */
    public void setProgress(double progress, String textValue) {
        this.textValue = "";
        mProgress = 0;
        mOriginalProgress = progress;
        if (!Utils.isDoubleMinValue(mOriginalProgress)) {
            progressMax = progress;
            // 进度大于100，按100计算
            if (progressMax > mStandard) {
                progressMax = mStandard;
            }
            mHandler.sendEmptyMessageDelayed(0, 500);
        }
    }

    private Handler mHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            if (mProgress <= progressMax) {
                if (progressMax - mProgress < progressMax / 30) {
                    mProgress = progressMax;
                } else {
                    mProgress += (progressMax / 30);
                }
                postInvalidate();
                mHandler.sendEmptyMessageDelayed(0, 30);
            }
        }
    };

    /**
     * 设置进度颜色
     *
     * @param progressColor
     */
    public void setProgressColor(int progressColor) {
        mProgressColor = progressColor;
    }

    /**
     * 设置边框颜色
     *
     * @param borderColor
     */
    public void setBorderColor(int borderColor) {
        mBorderColor = borderColor;
    }

}
