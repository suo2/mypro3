package com.huawei.solarsafe.utils.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.huawei.solarsafe.R;

;

/**
 * Created by p00322 on 2017/4/11.
 */
public class FlowLineView extends View {

    private int flowLineColor = Color.GREEN;
    public static final String VERTICAL = "vertical";
    public static final String HORIZONTAL = "horizontal";
    public static final String UP = "up";
    public static final String DOWN = "down";
    public static final String TO_LEFT = "toLeft";
    public static final String TO_RIGHT = "toRight";
    //主线动画控制
    private int mainIndex = 0;
    //支线动画控制
    private int branchIndex = 0;
    //主线圆点总个数
    private int mainLineCircleNum;
    //支线圆点个数
    private int branchLineCircleNum;
    //主线高度
    private float mainLineHeight;
    //主线宽度
    private float mainLineWidth;
    private Paint mPaint;
    private int radius = 8;
    private int distance = 18;
    //竖直还是横向
    private String lineDirection;
    //流向
    private String flowDirection;
    private static final String TAG = "FlowLineView";
    private boolean isToInvalidate = true;//是否去重画View;

    public void setToInvalidate(boolean toInvalidate) {
        isToInvalidate = toInvalidate;
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                mainIndex += 1;
                if (mainIndex < mainLineCircleNum) {
                    invalidate();
                } else {
                    mainIndex = 0;
                    invalidate();
                }
            } else if (msg.what == 2) {
                branchIndex += 1;
                if (branchIndex < branchLineCircleNum + 1) {
                    invalidate();
                } else {
                    branchIndex = 0;
                    invalidate();
                }
            }
        }
    };

    public FlowLineView(Context context) {
        super(context, null);
    }


    public FlowLineView(Context context, AttributeSet attrs) {
        super(context, attrs, 0);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.FlowLineView);
        flowLineColor = typedArray.getColor(R.styleable.FlowLineView_flowLineColor, Color.parseColor("#009DDF"));
        lineDirection = typedArray.getString(R.styleable.FlowLineView_line_dirction);
        flowDirection = typedArray.getString(R.styleable.FlowLineView_flow_dirction);
        typedArray.recycle();
        mPaint = new Paint();
        mPaint.setColor(flowLineColor);

    }

    public FlowLineView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (lineDirection.equals(VERTICAL)) {
            mainLineCircleNum = (getMeasuredHeight() + distance) / (distance + 2 * radius);
        } else if (lineDirection.equals(HORIZONTAL)) {
            mainLineCircleNum = (getMeasuredWidth() + distance) / (distance + 2 * radius);
        }
        mainLineHeight = getMeasuredHeight() / 2f;
        mainLineWidth = getMeasuredWidth() / 2f;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        try {
            for (int i = 0; i < mainLineCircleNum; i++) {
                if (i == mainIndex) {
                    float startX = 0;
                    float startY = 0;
                    Path mPath = new Path();
                    if (flowDirection.equals(TO_RIGHT)) {
                        startX = (distance + radius * 2) * (i + 1);
                        startY = mainLineHeight;

                        mPath.moveTo(startX, startY);
                        mPath.lineTo(startX - (radius * 2 + distance), startY - radius * 2);
                        mPath.lineTo(startX - (radius * 2 + distance), startY + radius * 2);
                    } else if (flowDirection.equals(TO_LEFT)) {
                        startX = getMeasuredWidth() - (radius * 2 + distance) * i - radius * 2 - distance;
                        startY = mainLineHeight;

                        mPath.moveTo(startX, startY);
                        mPath.lineTo(startX + radius * 2 + distance, startY - radius * 2);
                        mPath.lineTo(startX + radius * 2 + distance, startY + radius * 2);
                    } else if (flowDirection.equals(UP)) {
                        startX = mainLineWidth;
                        startY = getMeasuredHeight() - (radius * 2 + distance) * i - radius * 2 - distance;

                        mPath.moveTo(startX, startY);
                        mPath.lineTo(startX - radius * 2, startY + radius * 2 + distance);
                        mPath.lineTo(startX + radius * 2, startY + radius * 2 + distance);
                    } else if (flowDirection.equals(DOWN)) {
                        startX = mainLineWidth;
                        startY = (distance + radius * 2) * (i + 1);

                        mPath.moveTo(startX, startY);
                        mPath.lineTo(startX - radius * 2, startY - (radius * 2 + distance));
                        mPath.lineTo(startX + radius * 2, startY - (radius * 2 + distance));
                    }
                    mPath.close();
                    canvas.drawPath(mPath, mPaint);
                } else {
                    if (lineDirection.equals(HORIZONTAL)) {
                        if (flowDirection.equals(TO_LEFT)) {
                            canvas.drawCircle(getMeasuredWidth() - (radius * 2 + distance) * i - radius, mainLineHeight, radius, mPaint);
                        } else if (flowDirection.equals(TO_RIGHT)) {
                            canvas.drawCircle((radius * 2 + distance) * i + radius, mainLineHeight, radius, mPaint);
                        }
                    } else if (lineDirection.equals(VERTICAL)) {
                        if (flowDirection.equals(UP)) {
                            canvas.drawCircle(mainLineWidth, getMeasuredHeight() - (radius * 2 + distance) * i - radius, radius, mPaint);
                        } else if (flowDirection.equals(DOWN)) {
                            canvas.drawCircle(mainLineWidth, (radius * 2 + distance) * i + radius, radius, mPaint);
                        }
                    }
                }
            }
            //宋平修改 问题单 60825
            if(isToInvalidate){
                if(!mHandler.hasMessages(1)){
                    if(mainLineCircleNum >0){
                        int refreshTime = 3000/mainLineCircleNum;
                        if(refreshTime>1000){
                            refreshTime = 1000;
                        }
                        mHandler.sendEmptyMessageDelayed(1,refreshTime);
                    }
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "onDraw: " + e.toString() );
        }
    }
}
