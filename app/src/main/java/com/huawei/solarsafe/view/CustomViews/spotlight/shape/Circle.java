package com.huawei.solarsafe.view.CustomViews.spotlight.shape;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;

public class Circle implements Shape {

    private float radius;

    public Circle(float radius) {
        this.radius = radius;
    }

    @Override
    public void draw(Canvas canvas, PointF point, float value, Paint paint) {
        canvas.drawCircle(point.x, point.y, value * radius, paint);
    }

    @Override
    public int getHeight() {
        return (int) radius * 2;
    }

    @Override
    public int getWidth() {
        return (int) radius * 2;
    }
}
