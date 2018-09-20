package com.huawei.solarsafe.view.maintaince.patrol;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Point;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.MarkerOptions;
import com.huawei.solarsafe.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by p00587 on 2017/6/1.
 */

public class MyClusterMarker<T> {
    public List<MarkerOptions> mMarkers = new ArrayList<>();
    public List<T> mBeans = new ArrayList<>();
    public Point mP;

    private Context mContext;
    private LatLng mLatLng;


    public MyClusterMarker(Context context) {
        mContext = context;
    }

    public List<MarkerOptions> getMarkers() {
        return mMarkers;
    }


    public void setPoint(Point p, LatLng latLng) {
        mP = p;
        mLatLng = latLng;
    }

    public void addMarker(MarkerOptions markerOptions, T bean) {
        mBeans.add(bean);
        mMarkers.add(markerOptions);
    }

    public MarkerOptions getMarkerOption() {
        if (mMarkers.size() == 1) {
            return mMarkers.get(0);
        }
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(mLatLng);
        TextView tv = new TextView(mContext);
        tv.setGravity(Gravity.CENTER);
        tv.setBackgroundResource(R.drawable.marker_cluster_10);
        tv.setTextColor(Color.WHITE);
        tv.setText("" + mMarkers.size());
        tv.setTextSize(8);
        markerOptions.icon(BitmapDescriptorFactory.fromBitmap(getViewBitmap(tv)));

        return  markerOptions;
    }

    public static Bitmap getViewBitmap(View view) {
        Bitmap bitmap = null;
        if (view == null) {
            bitmap = null;
        } else {
            view.setDrawingCacheEnabled(true);
            view.destroyDrawingCache();
            if (view.getLayoutParams() == null) {
                view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            }
            view.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
            view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
            view.buildDrawingCache();
            Bitmap map = view.getDrawingCache();
            if(map != null){
                bitmap = map.copy(Bitmap.Config.ARGB_8888, false);
            }

        }
        return bitmap;
    }

    @Override
    public String toString() {
        return "  MyClusterMarker{" +
                "mMarkers=" + mMarkers.size() +
                ", mP=" + mP.toString() +
                ", mLatLng=" + mLatLng.toString() +
                '}';
    }
}

