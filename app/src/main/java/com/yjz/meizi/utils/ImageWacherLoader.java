package com.yjz.meizi.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;

import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.github.ielse.imagewatcher.ImageWatcher;
import com.yjz.meizi.utils.imageloader.GlideApp;

/**
 * @author lizheng
 * @date created at 2018/9/8 下午3:27
 */
public class ImageWacherLoader implements ImageWatcher.Loader {

    @Override
    public void load(Context context, Uri uri, final ImageWatcher.LoadCallback lc) {


        /*ImageLoader.getInstance().load(uri)
                .bitmap(new BitmapCallBack<Drawable>() {
                    @Override
                    public void onBitmapLoaded(Drawable bitmap) {
                        lc.onResourceReady(bitmap);
                    }
                });*/

        GlideApp.with(context).load(uri).into(new SimpleTarget<Drawable>() {
            @Override
            public void onResourceReady(Drawable resource, Transition<? super Drawable> transition) {
                lc.onResourceReady(resource);
            }
        });

        /*Glide.with(context).load(uri)
                .into(new SimpleTarget<GlideDrawable>() {
                    @Override
                    public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                        lc.onResourceReady(resource);
                    }
                });*/
    }
}
