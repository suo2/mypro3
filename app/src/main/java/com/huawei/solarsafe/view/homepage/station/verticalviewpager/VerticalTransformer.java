package com.huawei.solarsafe.view.homepage.station.verticalviewpager;


import android.support.v4.view.ViewPager;
import android.view.View;

/**
 * Created by p00322 on 2017/1/20.
 */
public class VerticalTransformer implements ViewPager.PageTransformer {
    @Override
    public void transformPage(View page, float position) {
        float alpha = 0;
        if (0 <= position && position <= 1) {
            alpha = 1 - position;
        } else if (-1 < position && position < 0) {
            alpha = position + 1;
        }
        page.setAlpha(alpha);
        float transX = page.getWidth() * -position;
        page.setTranslationX(transX);
        float transY = position * page.getHeight();
        page.setTranslationY(transY);
    }
}
