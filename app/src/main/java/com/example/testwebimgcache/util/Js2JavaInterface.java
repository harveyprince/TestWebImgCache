package com.example.testwebimgcache.util;

import android.content.Context;
import android.util.Log;

import com.example.testwebimgcache.ImageShow;

/**
 * Created by harveyprince on 16/6/27.
 */
public class Js2JavaInterface {

    private Context context;
    private String TAG = "JsUseJaveInterface";

    public Js2JavaInterface(Context context){
        this.context = context;
    }


    @android.webkit.JavascriptInterface
    public void setImgSrc(String imgSrc) {
        Log.i(TAG, "setImgSrc : " + imgSrc);
        ImageShow.showImage(context, imgSrc);
    }

}