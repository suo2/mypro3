package com.huawei.solarsafe.view.maintaince.defects.picker.worker;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Animatable;
import android.net.Uri;
import android.support.v4.view.PagerAdapter;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.controller.AbstractDraweeController;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.image.ImageInfo;
import com.huawei.solarsafe.R;
import java.util.List;
import uk.co.senab.photoview.PhotoViewAttacher;


/**
 * 图片浏览ViewPageAdapter
 */
public class ViewPageAdapter extends PagerAdapter {

    private Context context;
    private List<String> images;
    private SparseArray<View> cacheView;
    private ViewGroup containerTemp;

    public ViewPageAdapter(Context context, List<String> images) {
        this.context = context;
        this.images = images;
        cacheView = new SparseArray<>(images.size());
    }

    @Override
    public Object instantiateItem(final ViewGroup container, final int position) {
        if(containerTemp == null) containerTemp = container;

        View view = cacheView.get(position);

        if(view == null){
            view = LayoutInflater.from(context).inflate(R.layout.vp_item_image,container,false);
            view.setTag(position);
            SimpleDraweeView image = (SimpleDraweeView) view.findViewById(R.id.image);
            final PhotoViewAttacher photoViewAttacher = new PhotoViewAttacher(image);
            Uri uri = Uri.parse(images.get(position));
            AbstractDraweeController build = Fresco.newDraweeControllerBuilder()
                    .setUri(uri)
                    .setAutoPlayAnimations(true)
                    .setControllerListener(new BaseControllerListener<ImageInfo>(){
                        @Override
                        public void onFinalImageSet(String id, ImageInfo imageInfo, Animatable animatable) {
                            super.onFinalImageSet(id, imageInfo, animatable);
                            photoViewAttacher.update();
                        }

                        @Override
                        public void onFailure(String id, Throwable throwable) {
                            super.onFailure(id, throwable);
                            Toast.makeText(context, context.getString(R.string.the) + position + context.getString(R.string.picture_load_failed), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .build();
            image.setController(build);
            photoViewAttacher.setOnPhotoTapListener(new PhotoViewAttacher.OnPhotoTapListener() {
                @Override
                public void onPhotoTap(View view, float x, float y) {
                    Activity activity = (Activity) context;
                    activity.finish();
                }
            });
            cacheView.put(position,view);
        }
        container.addView(view);
        return view;
    }

    @Override
    public int getCount() {
        return images.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        View view = (View) object;
        container.removeView(view);
    }

}
