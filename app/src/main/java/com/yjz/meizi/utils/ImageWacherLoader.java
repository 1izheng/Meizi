package com.yjz.meizi.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;

import com.github.ielse.imagewatcher.ImageWatcher;
import com.yjz.meizi.utils.imageloader.BitmapCallBack;
import com.yjz.meizi.utils.imageloader.ImageLoader;

/**
 * @author lizheng
 * @date created at 2018/9/8 下午3:27
 */
public class ImageWacherLoader implements ImageWatcher.Loader {

    @Override
    public void load(Context context, Uri uri, final ImageWatcher.LoadCallback lc) {

        ImageLoader.getInstance().load(uri).getDrawable(context, new BitmapCallBack<Drawable>() {
            @Override
            public void onBitmapLoaded(Drawable bitmap) {
                lc.onResourceReady(bitmap);
            }
        });
    }
}
