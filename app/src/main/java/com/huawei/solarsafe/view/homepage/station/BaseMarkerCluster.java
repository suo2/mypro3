package com.huawei.solarsafe.view.homepage.station;


/**
 * Created by p00322 on 2017/1/4.
 */

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.amap.api.maps.Projection;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.LatLngBounds;
import com.amap.api.maps.model.MarkerOptions;
import com.huawei.solarsafe.R;
import com.huawei.solarsafe.bean.station.map.ClusterMarkerInfo;

import java.util.ArrayList;
import java.util.Map;

/**
 * Create Date: 2017-1-4<br>
 * Create Author: p00322<br>
 * Description :电站聚合算法
 */
public abstract class BaseMarkerCluster {
    protected String CLUSTER_TITLE;//static

    private static final String TAG = "MarkerCluster";

    private Activity activity;
    private MarkerOptions options;
    private IClusterStationInfo stationInfo;
    public ArrayList<ClusterMarkerInfo> includeMarkers;
    private LatLngBounds bounds;// 创建区域

    // 聚合点坐标
    private LatLng clusterPosition;

    /**
     * @param activity
     * @param firstMarkers
     * @param projection
     * @param gridSize     区域大小参数
     */
    public BaseMarkerCluster(Activity activity, ClusterMarkerInfo firstMarkers, Projection projection, int gridSize, Map<Integer, View> clusterView) {
        options = new MarkerOptions();
        this.activity = activity;
        this.clusterView = clusterView;
        CLUSTER_TITLE = activity.getResources().getString(R.string.congruent_point);
        Point point = projection.toScreenLocation(firstMarkers.getMarkerOptions().getPosition());
        //范围
        Point southwestPoint = new Point(point.x - gridSize, point.y + gridSize);
        Point northeastPoint = new Point(point.x + gridSize, point.y - gridSize);
        LatLng swLatLng = projection.fromScreenLocation(southwestPoint);
        LatLng neLatLng = projection.fromScreenLocation(northeastPoint);
        try {
            bounds = new LatLngBounds(swLatLng, neLatLng);
        } catch (Exception e) {
            Log.e(TAG, "bounds 异常");
        }
        includeMarkers = new ArrayList<>();
        includeMarkers.add(firstMarkers);
        stationInfo = firstMarkers.getStationInfo();
        options.anchor(0.5f, 0.5f).title(firstMarkers.getMarkerOptions().getTitle()).position(firstMarkers.getMarkerOptions().getPosition())
                .snippet(firstMarkers.getMarkerOptions().getSnippet());

    }

    /**
     * 添加marker
     */
    public void addMarker(ClusterMarkerInfo markerOptions) {
        includeMarkers.add(markerOptions);// 添加到列表中
    }

    public int getMarkerNum() {
        return includeMarkers.size();
    }

    /**
     * 设置聚合点的中心位置以及图标
     */
    public void setPositionAndIcon() {
        int size = includeMarkers.size();
        if (size == 1) {
            ClusterMarkerInfo firstMarkers = includeMarkers.get(0);
            View view = firstMarkers.getMarkerView();
            if (view == null) {
                view = LayoutInflater.from(activity).inflate(getMarkerLayoutResid(), null);
                firstMarkers.setMarkerView(view);
            }
            setMarkerView(view);
            //宋平修改，setMarkerView(view)已经设置过一次背景了，就不需要再次 options.icon(BitmapDescriptorFactory.fromBitmap(getViewBitmap(view)))
            return;
        }
        double lat = 0.0;
        double lng = 0.0;
        for (ClusterMarkerInfo op : includeMarkers) {
            lat += op.getMarkerOptions().getPosition().latitude;
            lng += op.getMarkerOptions().getPosition().longitude;
        }
        // 设置marker的位置为中心位置为聚集点的平均位置
        clusterPosition = new LatLng(lat / size, lng / size);
        options.position(new LatLng(lat / size, lng / size));// 设置中心位置为聚集点的平均距离
        options.title(CLUSTER_TITLE);

        View view = clusterView.get(size);
        if (view == null) {
            view = activity.getLayoutInflater().inflate(getClusterLayoutResid(), null);
            clusterView.put(size, view);
        }
        setClusterView(view, size);
        options.icon(BitmapDescriptorFactory.fromBitmap(getViewBitmap(view)));
    }

    public LatLngBounds getBounds() {
        return bounds;
    }

    public MarkerOptions getOptions() {
        return options;
    }

    public void setOptions(MarkerOptions options) {
        this.options = options;
    }

    public IClusterStationInfo getStationInfo() {
        return stationInfo;
    }

    public void setStationInfo(IClusterStationInfo stationInfo) {
        this.stationInfo = stationInfo;
    }

    Map<Integer, View> clusterView;

    /**
     * 把一个view转化成bitmap对象
     *
     * @param view
     * @return
     */
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
            Bitmap drawingCache = view.getDrawingCache();
            if (drawingCache != null){
                bitmap = drawingCache.copy(Bitmap.Config.ARGB_8888, false);
            }
        }
        return bitmap;
    }

    public abstract int getMarkerLayoutResid();

    public abstract void setMarkerView(View parentView);

    public abstract int getClusterLayoutResid();

    public abstract void setClusterView(View parentView, int carNum);

    protected int getDefaultMarkerLayoutResid() {
        return R.layout.amap_marker_text;
    }

    protected void setDefaultMarkerView(View parentView) {
        ImageView ivMarker = (ImageView) parentView.findViewById(R.id.iv_marker);
        TextView tvStationName = (TextView) parentView.findViewById(R.id.tv_station_name);
            switch (stationInfo.getStationState()) {
                case HEALTH:
                    ivMarker.setBackgroundResource(R.drawable.marker_station_normal);
                    break;
                case EXCEPTION:
                    ivMarker.setBackgroundResource(R.drawable.marker_station_warning);
                    break;
                case FAULTCHAIN:
                    ivMarker.setBackgroundResource(R.drawable.marker_station_ineffective);
                    break;
                default:
                    ivMarker.setBackgroundResource(R.drawable.marker_station_normal);
                    break;
            }
        tvStationName.setText(stationInfo.getStationName());
        options.icon(BitmapDescriptorFactory.fromBitmap(getViewBitmap(parentView)));
    }

    protected int getDefaultClusterLayoutResid() {
        return R.layout.group_cluster_view;
    }

    protected void setDefaultClusterView(View parentView, int carNum) {
        TextView carNumTextView = (TextView) parentView.findViewById(R.id.my_car_num);
        RelativeLayout myCarLayout = (RelativeLayout) parentView.findViewById(R.id.my_car_bg);
        myCarLayout.setBackgroundResource(R.drawable.marker_cluster_10);
        carNumTextView.setText(String.valueOf(carNum));
    }
}
