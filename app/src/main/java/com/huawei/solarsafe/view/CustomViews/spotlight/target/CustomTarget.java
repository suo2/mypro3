package com.huawei.solarsafe.view.CustomViews.spotlight.target;

import android.animation.TimeInterpolator;
import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.graphics.PointF;
import android.support.annotation.LayoutRes;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;

import com.huawei.solarsafe.view.CustomViews.spotlight.OnTargetStateChangedListener;
import com.huawei.solarsafe.view.CustomViews.spotlight.shape.Shape;

/**
 * Target
 *
 * @author takusemba
 * @since 26/06/2017
 **/
public class CustomTarget extends Target {

    private CustomTarget(Shape shape, PointF point, View overlay, long duration, TimeInterpolator animation, OnTargetStateChangedListener listener) {
        super(shape, point, overlay, duration, animation, listener);
    }

    public static class Builder extends AbstractTargetBuilder<Builder, CustomTarget> {

        private static final int ABOVE_SPOTLIGHT = 0;
        private static final int BELOW_SPOTLIGHT = 1;

        private boolean isUsestandardposition=false;

        @Override
        protected Builder self() {
            return this;
        }

        private View overlay;

        public Builder(Activity context) {
            super(context);
        }

        public Builder setOverlay(@LayoutRes int layoutId) {
            this.overlay = getContext().getLayoutInflater().inflate(layoutId, null);
            return this;
        }

        public Builder setOverlay(View overlay) {
            this.overlay = overlay;
            return this;
        }

        /**
         * 自定义布局是否使用标准计算位置
         * 如采用,自定义布局根布局中只能包含一个ViewGroup
         * @param isUsestandardposition
         * @return
         */
        public Builder setUseStandardPosition(boolean isUsestandardposition){
            this.isUsestandardposition=isUsestandardposition;
            return this;
        }

        @Override
        public CustomTarget build() {
            if (isUsestandardposition){
                calculatePosition(point, shape, overlay);
            }
            return new CustomTarget(shape, point, overlay, duration, animation, listener);
        }

        /**
         * 计算自定义提示布局的位置
         * target在屏幕上半部分,布局显示在其下,否则相反
         * @param point
         * @param shape
         * @param overlay
         */
        private void calculatePosition(final PointF point, final Shape shape, View overlay) {
            float[] areas = new float[2];
            Point screenSize = new Point();
            ((WindowManager) overlay.getContext()
                    .getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getSize(screenSize);

            areas[ABOVE_SPOTLIGHT] = point.y / screenSize.y;
            areas[BELOW_SPOTLIGHT] = (screenSize.y - point.y) / screenSize.y;

            int largest;
            if (areas[ABOVE_SPOTLIGHT] > areas[BELOW_SPOTLIGHT]) {
                largest = ABOVE_SPOTLIGHT;
            } else {
                largest = BELOW_SPOTLIGHT;
            }

            ViewGroup rootViewGroup= (ViewGroup) overlay;
            final View layout=rootViewGroup.getChildAt(0);

            switch (largest) {
                case ABOVE_SPOTLIGHT:
                    // use viewTreeObserver to use layout.getHeight()
                    layout.getViewTreeObserver()
                            .addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                                @Override
                                public void onGlobalLayout() {
                                    layout.setY(point.y - (shape.getHeight() / 2) - layout.getHeight());
                                }
                            });
                    break;
                case BELOW_SPOTLIGHT:
                    layout.setY((int) (point.y + (shape.getHeight() / 2)));
                    break;
            }
        }
    }
}
