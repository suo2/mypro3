package com.huawei.solarsafe.view.maintaince.defects.picker.worker;

import android.content.Intent;
import android.util.Log;

import java.util.List;


public class ImageBrowsePresenter {

    private ImageBrowseView view;
    private List<String> images;
    private int position;

    public ImageBrowsePresenter(ImageBrowseView view) {
        this.view = view;
    }

    public void loadImage(){
        Intent intent = view.getDataIntent();
        if(intent != null) {
            try{
                images = intent.getStringArrayListExtra("images");
                position = intent.getIntExtra("position",0);
                view.setImageBrowse(images,position);
            }catch(Exception e){
                Log.e("ImageBrowsePresenter", "onReceive: " + e.getMessage());
            }
        }
    }

    public List<String> getImages() {
        return images;
    }

    public void setPosition(int position) {
        this.position = position;
    }



}

