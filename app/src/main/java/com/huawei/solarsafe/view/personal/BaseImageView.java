package com.huawei.solarsafe.view.personal;


import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Xfermode;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ImageView;

import java.lang.ref.WeakReference;

public abstract class BaseImageView extends ImageView {

    protected Context mContext;

    private static final Xfermode sXfermode = new PorterDuffXfermode(PorterDuff.Mode.DST_IN);
    private Bitmap mMaskBitmap;
    private Paint mPaint;
    private WeakReference<Bitmap> mWeakBitmap;

    public BaseImageView(Context context) {
        super(context);
        sharedConstructor(context);
    }

    public BaseImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        sharedConstructor(context);
    }

    public BaseImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        sharedConstructor(context);
    }

    private void sharedConstructor(Context context) {
        mContext = context;

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    }

    public void invalidate() {
        mWeakBitmap = null;
        if (mMaskBitmap != null) {
            mMaskBitmap.recycle();
        }
        super.invalidate();
    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {
        if (!isInEditMode()) {
            int i = canvas.saveLayer(0.0f, 0.0f, getWidth(), getHeight(),
                    null, Canvas.ALL_SAVE_FLAG);
            try {
                Bitmap bitmap = mWeakBitmap != null ? mWeakBitmap.get() : null;
                if (bitmap == null || bitmap.isRecycled()) {
                    Drawable drawable = getDrawable();
                    if (drawable != null) {
                        bitmap = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
                        Canvas bitmapCanvas = new Canvas(bitmap);
                        drawable.setBounds(0, 0, getWidth(), getHeight());
                        drawable.draw(bitmapCanvas);

                        if (mMaskBitmap == null || mMaskBitmap.isRecycled()) {
                            mMaskBitmap = getBitmap();
                        }
                        mPaint.reset();
                        mPaint.setFilterBitmap(false);
                        mPaint.setXfermode(sXfermode);
                        if(mMaskBitmap != null){
                            bitmapCanvas.drawBitmap(mMaskBitmap, 0.0f, 0.0f, mPaint);
                        }
                        mWeakBitmap = new WeakReference<>(bitmap);
                    }
                }

                if (bitmap != null) {
                    mPaint.setXfermode(null);
                    canvas.drawBitmap(bitmap, 0.0f, 0.0f, mPaint);
                    return;
                }
            } catch (Exception e) {
                // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0004_F01_Android_S02  敏感信息检查-日志中不能包含敏感信息    【修改人】：zhaoyufeng
                Log.e("BaseImageView", e.toString());
                //System.gc();
            } finally {
                canvas.restoreToCount(i);
            }
        } else {
            super.onDraw(canvas);
        }
    }

    public abstract Bitmap getBitmap();

}
