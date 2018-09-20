package com.huawei.solarsafe.view.homepage.station;


import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;

import com.huawei.solarsafe.R;

/**
 * Create Date: 2014-10-22<br>
 * Create Author: dWX240872<br>
 * Description :自定义圆环，用于表示上网电量、并网消耗、逆变器失效各自所占的份额
 */
public class CustomProgressBar extends View
{
    /**
     * 延迟多少毫秒
     */
    private static final int SEND_TIME = 30;
    /**
     * 多少份
     */
    private static final int EVERY_COPIES = 40;
    /**
     *
     */
    private static final int THIRD_DATA = 2;
    /**
     *
     */
    private static final int SECOND_DATA = 1;
    /**
     *
     */
    private static final int FIRST_DATA = 0;
    /** 圆弧的总度数 */
    private static final double ARDIAN_ALL_ANGLE = 357d;
    /** 第一圈的颜色 */
    private int mFirstColor;
    /** 第二圈的颜色 */
    private int mSecondColor;
    /** 第三圈的颜色 */
    private int mThirdColor;
    /** 圈的宽度 */
    private int mCircleWidth;
    /** 画笔 */
    private Paint mPaint;
    /** 各圈的数据比例值 */
    private double[] arrayData;
    /** 圆环中进行比对的份数 */
    private int number = 3;
    /** 圆环背景颜色 */
    String mBackGroundColor = "#F0F0F0";
    private int mBackColor = Color.WHITE;

    public CustomProgressBar(Context context, AttributeSet attrs)
    {
        this(context, attrs, 0);
    }

    public void setArrayData(double[] data)
    {
        arrayDataCaches = new double[3];
        arrayData = new double[3];
        for (int i = 0; i < number; i++)
        {
            arrayDataCaches[i] = (data[i] * ARDIAN_ALL_ANGLE);
        }
        postInvalidate();
        mHandler.sendEmptyMessageDelayed(0, 500);
        mHandler.sendEmptyMessageDelayed(1, 500);
        mHandler.sendEmptyMessageDelayed(2, 500);
    }

    private Handler mHandler = new Handler()
    {
        public void handleMessage(android.os.Message msg)
        {
            switch (msg.what)
            {
                case FIRST_DATA:
                    arrayData[FIRST_DATA] = dynamicAdd(arrayData[FIRST_DATA], arrayDataCaches[FIRST_DATA], FIRST_DATA);
                    break;
                case SECOND_DATA:
                    arrayData[SECOND_DATA] = dynamicAdd(arrayData[SECOND_DATA], arrayDataCaches[SECOND_DATA],
                            SECOND_DATA);
                    break;
                case THIRD_DATA:
                    arrayData[THIRD_DATA] = dynamicAdd(arrayData[THIRD_DATA], arrayDataCaches[THIRD_DATA], THIRD_DATA);
                    break;
                default:
                    break;
            }
        }

        /**
         * 动态计算增加每一份
         */
        private double dynamicAdd(double arrayData, double arrayDataCaches, int what)
        {
            if (arrayData < arrayDataCaches)
            {
                if (arrayDataCaches - arrayData < arrayDataCaches / EVERY_COPIES)
                {
                    arrayData = arrayDataCaches;
                }
                else
                {
                    arrayData += arrayDataCaches / EVERY_COPIES;
                }
                postInvalidate();
                mHandler.sendEmptyMessageDelayed(what, SEND_TIME);
            }
            return arrayData;
        };
    };

    private double[] arrayDataCaches;

    public CustomProgressBar(Context context)
    {
        this(context, null);
    }

    /**
     * 必要的初始化，获得一些自定义的值
     *
     * @param context
     * @param attrs
     * @param defStyle
     */
    public CustomProgressBar(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        TypedArray mTypedArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.CustomProgressBar,
                defStyle, 0);
        /** 圆环的段数 */
        int num = mTypedArray.getIndexCount();
        /** 给圆环加颜色 */
        for (int i = 0; i < num; i++)
        {
            int attr = mTypedArray.getIndex(i);
            switch (attr)
            {
                case R.styleable.CustomProgressBar_firstColor:
                    mFirstColor = mTypedArray.getColor(attr, getResources().getColor(R.color.important_town_color));
                    break;
                case R.styleable.CustomProgressBar_secondColor:
                    mSecondColor = mTypedArray.getColor(attr, getResources().getColor(R.color.poor_town));
                    break;
                case R.styleable.CustomProgressBar_thirdColor:
                    mThirdColor = mTypedArray.getColor(attr, getResources().getColor(R.color.poor_users));
                    break;
                case R.styleable.CustomProgressBar_circleWidth:
                    mCircleWidth = mTypedArray.getDimensionPixelSize(attr, (int) TypedValue.applyDimension(
                            TypedValue.COMPLEX_UNIT_PX, 20, getResources().getDisplayMetrics()));
                    break;
                case R.styleable.CustomProgressBar_defaultBg:
                    mBackColor = mTypedArray.getColor(attr, Color.WHITE);
                    break;

                default:
                    break;
            }
        }
        /** 把Java对象设置为可垃圾回收的状态 */
        mTypedArray.recycle();
        /** 刷新界面 , 进行重绘 */
        postInvalidate();
    }

    /**
     * 绘制圆环
     *
     * @param canvas
     */
    protected void onDraw(Canvas canvas)
    {
        mPaint = new Paint();
        /**
         * 获取圆心的x坐标
         */
        int centre = getWidth() / 2;
        /**
         * 半径
         */
        int radius = centre - mCircleWidth / 2 - 5;
        /**
         * 设置圆环的宽度
         */
        mPaint.setStrokeWidth(mCircleWidth);
        /**
         * 消除锯齿
         */
        mPaint.setAntiAlias(true);
        /**
         * 设置空心
         */
        mPaint.setStyle(Paint.Style.STROKE);
        /**
         * 用于定义的圆弧的形状和大小的界限
         */
        RectF oval = new RectF(centre - radius, centre - radius, centre + radius, centre + radius);

        mPaint.setColor(Color.parseColor(mBackGroundColor));
        if (mBackColor!=Color.WHITE){
            mPaint.setColor(mBackColor);
        }
        // 先画一个背景颜色
        canvas.drawArc(oval, 0, 360, false, mPaint);

        if (null != arrayData)
        {
            if (arrayData[0] > 0)
            {
                // 如果小于1度的情况
                chargeBelowOneDegree(canvas, oval);
                /**
                 * 设置第一段圆环的颜色
                 */
                mPaint.setColor(mFirstColor);
                /**
                 * 画圆弧
                 */
                canvas.drawArc(oval, 0, (float) arrayData[0], false, mPaint);
            }
            setSecondCircleColor(canvas, oval);
            /**
             * 设置第三段圆环的颜色
             */
            setThirdCircleColor(canvas, oval);
            // 判断只有一段时重画全部颜色
            setOnlyOneColor(canvas, oval);
        }
    }

    /**
     * 损耗和失效小于1度，按一度处理
     *
     * @param canvas
     * @param oval
     */
    private void chargeBelowOneDegree(Canvas canvas, RectF oval)
    {
        //第二段、三段小于1但大于0
        if (arrayData[1] <= 1 && arrayData[1] > 0.0d && arrayData[2] <= 1 && arrayData[2] > 0.0d)
        {
            mPaint.setColor(mFirstColor);
            canvas.drawArc(oval, 0, 355, false, mPaint);
            mPaint.setColor(mSecondColor);
            canvas.drawArc(oval, 356, 1, false, mPaint);
            mPaint.setColor(mThirdColor);
            canvas.drawArc(oval, 358, 1, false, mPaint);
            return;
        }
        //第二段小于1大于0，第三段为0
        else if (arrayData[1] <= 1 && arrayData[1] > 0.0d && arrayData[2] == 0.0d)
        {
            mPaint.setColor(mFirstColor);
            canvas.drawArc(oval, 0, 357, false, mPaint);
            mPaint.setColor(mSecondColor);
            canvas.drawArc(oval, 358, 1, false, mPaint);
            return;
        }
        //第二段为0，三段小于1大于0
        else if (arrayData[2] <= 1 && arrayData[2] > 0.0d && arrayData[1] == 0.0d)
        {
            mPaint.setColor(mFirstColor);
            canvas.drawArc(oval, 0, 357, false, mPaint);
            mPaint.setColor(mThirdColor);
            canvas.drawArc(oval, 358, 1, false, mPaint);
            return;
        }
    }

    /**
     * 判断只有一段时重画全部颜色
     *
     * @param canvas
     *            画布
     * @param oval
     */
    private void setOnlyOneColor(Canvas canvas, RectF oval)
    {
        if (arrayData[0] == 0 && arrayData[1] == 0 && arrayData[2] != 0)
        {
            mPaint.setColor(mThirdColor);
            canvas.drawArc(oval, 0, 360, false, mPaint);
        }
        else if (arrayData[1] == 0 && arrayData[2] == 0 && arrayData[0] != 0)
        {
            mPaint.setColor(mFirstColor);
            canvas.drawArc(oval, 0, 360, false, mPaint);
        }
        else if (arrayData[0] == 0 && arrayData[2] == 0 && arrayData[1] != 0)
        {
            mPaint.setColor(mSecondColor);
            canvas.drawArc(oval, 0, 360, false, mPaint);
        }
    }

    /**
     * 设置第三段圆环的颜色
     *
     * @param canvas
     * @param oval
     */
    private void setThirdCircleColor(Canvas canvas, RectF oval)
    {
        mPaint.setColor(mThirdColor);
        if (arrayData[2] > 0 && arrayData[0] > 0 && arrayData[1] > 0)
        {

            /**
             * 画圆弧
             */
            canvas.drawArc(oval, (float) (0 + arrayData[0] + arrayData[1] + 2), (float) (360 - arrayData[1]
                    - arrayData[0] - 3), false, mPaint);
        }
        else if (arrayData[2] > 0 && arrayData[0] <= 0 && arrayData[1] > 0)
        {
            canvas.drawArc(oval, (float) (0 + arrayData[1] + 1), (float) (360 - arrayData[1] - 2), false, mPaint);
        }
        else if (arrayData[2] > 0 && arrayData[0] > 0 && arrayData[1] <= 0)
        {
            canvas.drawArc(oval, (float) (0 + arrayData[0] + 1), (float) (360 - arrayData[1] - arrayData[0] - 2),
                    false, mPaint);
        }
        else if (arrayData[2] > 0 && (arrayData[0] + arrayData[1] == 0))
        {
            canvas.drawArc(oval, 0, 360, false, mPaint);
        }
    }

    /**
     * 设置第二个圆环的颜色
     *
     * @param canvas
     * @param oval
     */
    private void setSecondCircleColor(Canvas canvas, RectF oval)
    {
        /**
         * 设置第二段圆环的颜色
         */
        mPaint.setColor(mSecondColor);
        if (arrayData[1] > 0 && arrayData[0] > 0)
        {

            /**
             * 画圆弧
             */
            canvas.drawArc(oval, (float) (0 + arrayData[0] + 1), (float) (arrayData[1]), false, mPaint);
        }
        else if (arrayData[1] > 0 && arrayData[0] <= 0)
        {
            canvas.drawArc(oval, (float) (0 + arrayData[0]), (float) (arrayData[1]), false, mPaint);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction()==MotionEvent.ACTION_DOWN)
            return false;
        return super.onTouchEvent(event);
    }
}