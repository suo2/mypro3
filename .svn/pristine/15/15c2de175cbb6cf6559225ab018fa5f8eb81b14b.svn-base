package com.huawei.solarsafe.utils.common;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.huawei.solarsafe.R;

import java.util.ArrayList;
import java.util.List;


/**
 * <pre>
 *     author: Tzy
 *     time  : 2017/5/25
 *     desc  : View工具
 * </pre>
 */

//        removeSelfFromParent
//        requestLayoutParent
//        isTouchInView
//        bigImage
//        setTVUnderLine                            给TextView设置下划线
//        showPopupWindow
//        dismissPopup
//        getViewDrawingCache                       获取view截图,有大小限制
//        drawViewToBitmap                          获取view截图
//        drawScrollViewToBitmap                    获取ScrollView截图
//        drawListViewBitmap                        获取ListView截图
//        getActivityBitmap                         获取Activity的截图,存在垂直ScrollView可以截取完整,存在水平ScrollView和列表不能截取完整
//        getStatusBarHeight                        获取状态栏高度
//        getToolbarHeight                          获取工具栏高度
//        getNavigationBarHeight                    获取导航栏高度
//        measureView                               测量view
//        getViewWidth                              获取view的宽度
//        getViewHeight                             获取view的高度
//        getActivity                               获取view的上下文

public class ViewUtils {

    public static void removeSelfFromParent(View view) {
        if (view != null) {
            ViewParent parent = view.getParent();
            if (parent != null && parent instanceof ViewGroup) {
                ViewGroup group = (ViewGroup) parent;
                group.removeView(view);
            }
        }
    }


    public static void requestLayoutParent(View view, boolean isAll) {
        ViewParent parent = view.getParent();
        while (parent != null && parent instanceof View) {
            if (!parent.isLayoutRequested()) {
                parent.requestLayout();
                if (!isAll) {
                    break;
                }
            }
            parent = parent.getParent();
        }
    }


    public static boolean isTouchInView(MotionEvent ev, View v) {
        int[] vLoc = new int[2];
        v.getLocationOnScreen(vLoc);
        float motionX = ev.getRawX();
        float motionY = ev.getRawY();
        return motionX >= vLoc[0] && motionX <= (vLoc[0] + v.getWidth()) && motionY >= vLoc[1] && motionY <= (vLoc[1] + v.getHeight());
    }

    public static Bitmap bigImage(Bitmap bmp, float big) {
        int bmpWidth = bmp.getWidth();
        int bmpHeight = bmp.getHeight();
        Matrix matrix = new Matrix();
        matrix.postScale(big, big);
        return Bitmap.createBitmap(bmp, 0, 0, bmpWidth, bmpHeight, matrix, true);
    }


    public static void setTVUnderLine(TextView textView) {
        textView.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
        textView.getPaint().setAntiAlias(true);
    }


    static PopupWindow popupWindow;

    public static View showPopupWindow(Context context, int resId, View root, int paramsType) {
        View popupView;
        popupView = LayoutInflater.from(context).inflate(resId, null);

        switch (paramsType) {
            case 1:
                popupWindow = new PopupWindow(popupView,
                        ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, true);
                break;
            case 2:
                popupWindow = new PopupWindow(popupView,
                        ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
                break;
            case 3:
                popupWindow = new PopupWindow(popupView,
                        ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT, true);
                break;
            case 4:
                popupWindow = new PopupWindow(popupView,
                        ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
                break;
            default:
                popupWindow = new PopupWindow(popupView,
                        ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, true);
                break;
        }
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setTouchable(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.showAsDropDown(root);
        return popupView;
    }


    public static void dismissPopup() {
        if (popupWindow != null && popupWindow.isShowing()) {
            popupWindow.dismiss();
            popupWindow = null;
        }
    }

    /**
     * 获取View的绘制缓存Bitmap
     * 这种方式有缓存大小限制,可能返回null
     * @param v
     * @param isUNSPECIFIED 是否以无限制模式重新测算View大小(View可能比原来大或小,可能改变界面布局)
     * @return
     */
    public static Bitmap getViewDrawingCache(View v,boolean isUNSPECIFIED){
        if (isUNSPECIFIED){
            v.measure(MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED), MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
            v.layout(0, 0, v.getMeasuredWidth(), v.getMeasuredHeight());
        }
        v.setDrawingCacheEnabled(true);
        return v.getDrawingCache();
    }

    /**
     * 将View绘制在Bitmap上
     * @param v
     * @param isUNSPECIFIED 是否以无限制模式重新测算View大小(View可能比原来大或小,可能改变界面布局)
     * @return
     */
    public static Bitmap drawViewToBitmap(View v,boolean isUNSPECIFIED){
        if (isUNSPECIFIED){
            v.measure(MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED), MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
            v.layout(0, 0, v.getMeasuredWidth(), v.getMeasuredHeight());
        }
        Bitmap bitmap = Bitmap.createBitmap(v.getWidth(), v.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        v.draw(canvas);
        return bitmap;
    }

    /**
     * 将ScrollView绘制在Bitmap上
     * @param isVertical 是否是垂直的
     * @param scrollView
     * @return
     */
    public static Bitmap drawScrollViewToBitmap(ScrollView scrollView,boolean isVertical ){

        if (isVertical){
            scrollView.measure(MeasureSpec.makeMeasureSpec(scrollView.getWidth(), MeasureSpec.EXACTLY), MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
        }else{
            scrollView.measure(MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED), MeasureSpec.makeMeasureSpec(scrollView.getHeight(), MeasureSpec.EXACTLY));
        }
        scrollView.layout(0, 0, scrollView.getMeasuredWidth(), scrollView.getMeasuredHeight());
        Bitmap bitmap = Bitmap.createBitmap(scrollView.getWidth(), scrollView.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        scrollView.draw(canvas);
        return bitmap;
    }

    /**
     * 绘制ListView的截图Bitmap
     * 先得到每个childView的Bitmap,然后再绘制在一张画布上
     * @param listview
     * @return
     */
    public static Bitmap drawListViewBitmap(ListView listview) {

        //获取列表适配器
        ListAdapter adapter  = listview.getAdapter();
        int itemscount       = adapter.getCount();
        //判断数据是否为空
        if (itemscount<1){
            return null;
        }

        int allitemsheight   = 0;//所有item总高度,即ListView高度
        List<Bitmap> bmps    = new ArrayList<Bitmap>();//item截图集合

        for (int i = 0; i < itemscount; i++) {

            //获取item
            View childView      = adapter.getView(i, null, listview);
            //测量item大小,宽度为ListView宽,高度无限制
            childView.measure(View.MeasureSpec.makeMeasureSpec(listview.getWidth(), View.MeasureSpec.EXACTLY),View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
            childView.layout(0, 0, childView.getMeasuredWidth(), childView.getMeasuredHeight());

            //获得item截图,以下方法2选1
            //获取每个childView的绘制缓存Bitmap
            Bitmap childBitmap=getViewDrawingCache(childView,false);
//            //将每个childView绘制在Bitmap上
//            Bitmap childBitmap=drawViewToBitmap(childView,false);

            bmps.add(childBitmap);
            allitemsheight+=childView.getMeasuredHeight();
        }

        //创建能画下整个ListView的Bitmap
        Bitmap bigbitmap    = Bitmap.createBitmap(listview.getMeasuredWidth(), allitemsheight, Bitmap.Config.ARGB_8888);
        //将Bitmap作为画布
        Canvas bigcanvas    = new Canvas(bigbitmap);
        //创建画笔
        Paint paint = new Paint();
        int iHeight = 0;

        //将所有childBitmap按指定位置绘制在画布上
        for (int i = 0; i < bmps.size(); i++) {
            Bitmap bmp = bmps.get(i);
            bigcanvas.drawBitmap(bmp, 0, iHeight, paint);
            iHeight+=bmp.getHeight();

            bmp.recycle();
            bmp=null;
        }

        return bigbitmap;
    }

    public static Bitmap getActivityBitmap(Activity activity) {
        //获取Activity的ContentView
        View view = activity.getWindow().getDecorView().findViewById(android.R.id.content);
        //记录Activity的原布局高度
        int oldHeight=view.getHeight();

        int statusBarHeight=getStatusBarHeight(activity);
        //测量Activity完整高度时的大小
        view.measure(MeasureSpec.makeMeasureSpec(view.getWidth(),MeasureSpec.EXACTLY),MeasureSpec.makeMeasureSpec(0,MeasureSpec.UNSPECIFIED));
        view.layout(0,statusBarHeight,view.getMeasuredWidth(),view.getMeasuredHeight());
        //获取Activity截图
        Bitmap bitmap=drawViewToBitmap(view,false);
        //还原Activity的布局大小
        view.measure(MeasureSpec.makeMeasureSpec(view.getWidth(),MeasureSpec.EXACTLY),MeasureSpec.makeMeasureSpec(oldHeight,MeasureSpec.EXACTLY));
        view.layout(0,statusBarHeight,view.getMeasuredWidth(),view.getMeasuredHeight()+statusBarHeight);

        return bitmap;
    }

    public static int getStatusBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }


    public static int getToolbarHeight(Context context) {
        final TypedArray styledAttributes = context.getTheme().obtainStyledAttributes(new int[]{R.attr.actionBarSize});
        int toolbarHeight = (int) styledAttributes.getDimension(0, 0);
        styledAttributes.recycle();
        return toolbarHeight;
    }

    public static int getNavigationBarHeight(Activity activity) {
        Resources resources = activity.getResources();
        int resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android");
        if (resourceId > 0) {
            return resources.getDimensionPixelSize(resourceId);
        }
        return 0;
    }

    public static void measureView(View view) {
        ViewGroup.LayoutParams p = view.getLayoutParams();
        if (p == null) {
            p = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }

        int childWidthSpec = ViewGroup.getChildMeasureSpec(0, 0, p.width);
        int lpHeight = p.height;
        int childHeightSpec;
        if (lpHeight > 0) {
            childHeightSpec = MeasureSpec.makeMeasureSpec(lpHeight, MeasureSpec.EXACTLY);
        } else {
            childHeightSpec = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
        }
        view.measure(childWidthSpec, childHeightSpec);
    }

    public static int getViewWidth(View view) {
        measureView(view);
        return view.getMeasuredWidth();
    }

    public static int getViewHeight(View view) {
        measureView(view);
        return view.getMeasuredHeight();
    }

    public static Activity getActivity(View view) {
        Context context = view.getContext();
        while (context instanceof ContextWrapper) {
            if (context instanceof Activity) {
                return (Activity) context;
            }
            context = ((ContextWrapper) context).getBaseContext();
        }
        throw new IllegalStateException("View " + view + " is not attached to an Activity");
    }

    public static void showToast(Context context, String content) {
        Toast.makeText(context, content, Toast.LENGTH_SHORT).show();
    }

    public static void showToast(Context context, int res) {
        Toast.makeText(context, res, Toast.LENGTH_SHORT).show();
    }
}
