package com.huawei.solarsafe.view.CustomViews;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.Rect;
import android.os.Build;
import android.util.AttributeSet;
import android.util.DisplayMetrics;

import com.google.zxing.ResultPoint;
import com.huawei.solarsafe.utils.SysUtils;
import com.journeyapps.barcodescanner.ViewfinderView;

import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 *     author: Tzy
 *     time  : $date$
 *     desc  : 自定义zxing中的ViewfinderView,为了修改扫描框样式
 * </pre>
 */
public class MyViewfinderView extends ViewfinderView {
    private boolean isBarcode=false;

    public MyViewfinderView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void refreshSizes() {
        if(cameraPreview == null) {
            return;
        }

        //做多分辨率适配
        DisplayMetrics displayMetrics=getResources().getDisplayMetrics();
        int screenWidth=displayMetrics.widthPixels;
        int screenHeight=displayMetrics.heightPixels;

        double widthScale=(double) screenWidth/1080;
        double heightScale=(double) screenHeight/1920;

        //切换扫描框大小
        Rect framingRect;
        if (isBarcode){
            //【安全特性】f:BX_BOXING_IMMEDIATELY_UNBOXED_TO_PERFORM_COERCION    【修改人】zhaoyufeng
            framingRect=new Rect((int)(30*widthScale),(int)(582*heightScale),
                    (int)(1050*widthScale),(int)(1032*heightScale));//条形码框
        }else{
            framingRect=new Rect((int)(165*widthScale),(int)(432*heightScale),
                    (int)(915*widthScale),(int)(1182*heightScale));//二维码框
        }

        Rect previewFramingRect = cameraPreview.getPreviewFramingRect();
        if( previewFramingRect != null) {
            this.framingRect = framingRect;
            this.previewFramingRect = previewFramingRect;
        }
    }

    @SuppressLint("DrawAllocation")
    @Override
    public void onDraw(Canvas canvas) {
        //刷新扫描框大小数据
        refreshSizes();
        if (framingRect == null || previewFramingRect == null) {
            return;
        }

        Rect frame = framingRect;
        Rect previewFrame = previewFramingRect;

        int width = canvas.getWidth();
        int height = canvas.getHeight();

        // Draw the exterior (i.e. outside the framing rect) darkened 绘制
        paint.setColor(resultBitmap != null ? resultColor : maskColor);
        canvas.drawRect(0, 0, width, frame.top, paint);
        canvas.drawRect(0, frame.top, frame.left, frame.bottom + 1, paint);
        canvas.drawRect(frame.right + 1, frame.top, width, frame.bottom + 1, paint);
        canvas.drawRect(0, frame.bottom + 1, width, height, paint);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //绘制扫码框4个边角弧形

            //多分辨率适配
            int resultRadius= SysUtils.dp2Px(getContext(),10);

            //左上角
            Path ltPath1=new Path();
            Path ltPath2=new Path();
            ltPath1.addRect(frame.left,frame.top,frame.left+resultRadius,frame.top+resultRadius, Path.Direction.CCW);
            ltPath2.addCircle(frame.left+resultRadius,frame.top+resultRadius,resultRadius, Path.Direction.CCW);
            ltPath1.op(ltPath2, Path.Op.DIFFERENCE);
            canvas.drawPath(ltPath1,paint);
            //右上角
            Path rtPath1=new Path();
            Path rtPath2=new Path();
            rtPath1.addRect(frame.right-resultRadius,frame.top,frame.right+1,frame.top+resultRadius, Path.Direction.CCW);
            rtPath2.addCircle(frame.right-resultRadius,frame.top+resultRadius,resultRadius, Path.Direction.CCW);
            rtPath1.op(rtPath2, Path.Op.DIFFERENCE);
            canvas.drawPath(rtPath1,paint);
            //左下角
            Path lbPath1=new Path();
            Path lbPath2=new Path();
            lbPath1.addRect(frame.left,frame.bottom-resultRadius,frame.left+resultRadius,frame.bottom+1, Path.Direction.CCW);
            lbPath2.addCircle(frame.left+resultRadius,frame.bottom-resultRadius,resultRadius, Path.Direction.CCW);
            lbPath1.op(lbPath2, Path.Op.DIFFERENCE);
            canvas.drawPath(lbPath1,paint);
            //右下角
            Path rbPath1=new Path();
            Path rbPath2=new Path();
            rbPath1.addRect(frame.right-resultRadius,frame.bottom-resultRadius,frame.right+1,frame.bottom+1, Path.Direction.CCW);
            rbPath2.addCircle(frame.right-resultRadius,frame.bottom-resultRadius,resultRadius, Path.Direction.CCW);
            rbPath1.op(rbPath2, Path.Op.DIFFERENCE);
            canvas.drawPath(rbPath1,paint);
        }

        if (resultBitmap != null) {
            // Draw the opaque result bitmap over the scanning rectangle
            paint.setAlpha(CURRENT_POINT_OPACITY);
            canvas.drawBitmap(resultBitmap, null, frame, paint);
        } else {

            // Draw a red "laser scanner" line through the middle to show decoding is active
            paint.setColor(laserColor);
            paint.setAlpha(SCANNER_ALPHA[scannerAlpha]);
            scannerAlpha = (scannerAlpha + 1) % SCANNER_ALPHA.length;
            int middle = frame.height() / 2 + frame.top;
            canvas.drawRect(frame.left + 2, middle - 1, frame.right - 1, middle + 2, paint);

            float scaleX = frame.width() / (float) previewFrame.width();
            float scaleY = frame.height() / (float) previewFrame.height();

            List<ResultPoint> currentPossible = possibleResultPoints;
            List<ResultPoint> currentLast = lastPossibleResultPoints;
            int frameLeft = frame.left;
            int frameTop = frame.top;
            if (currentPossible.isEmpty()) {
                lastPossibleResultPoints = null;
            } else {
                possibleResultPoints = new ArrayList<>(5);
                lastPossibleResultPoints = currentPossible;
                paint.setAlpha(CURRENT_POINT_OPACITY);
                paint.setColor(resultPointColor);
                for (ResultPoint point : currentPossible) {
                    canvas.drawCircle(frameLeft + (int) (point.getX() * scaleX),
                            frameTop + (int) (point.getY() * scaleY),
                            POINT_SIZE, paint);
                }
            }
            if (currentLast != null) {
                paint.setAlpha(CURRENT_POINT_OPACITY / 2);
                paint.setColor(resultPointColor);
                float radius = POINT_SIZE / 2.0f;
                for (ResultPoint point : currentLast) {
                    canvas.drawCircle(frameLeft + (int) (point.getX() * scaleX),
                            frameTop + (int) (point.getY() * scaleY),
                            radius, paint);
                }
            }

            postInvalidateDelayed(ANIMATION_DELAY,
                    frame.left - POINT_SIZE,
                    frame.top - POINT_SIZE,
                    frame.right + POINT_SIZE,
                    frame.bottom + POINT_SIZE);
        }
    }

    //切换扫描框大小方法
    public void switchScanModel(boolean isBarcode){
        this.isBarcode=isBarcode;
        refreshSizes();
    }
}
