package com.yjz.meizi.utils.imageloader;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;

/**
 * glide图片加载工具类
 *
 * @author lizheng
 * @date created at 2018/9/13 上午11:13
 */
public class GlideLoader implements ILoaderStrategy {


    @Override
    public void loadImage(LoaderOptions options) {

        Context mContext = null;
        if (options.targetView instanceof ImageView) {
            mContext = options.targetView.getContext();
        } else if (options.bitmapCallBack != null) {
            mContext = options.mContext;
        }

        GlideRequest glideRequest = null;
        if (options.url != null) {
            glideRequest = GlideApp.with(mContext).load(options.url);
        } else if (options.file != null) {
            glideRequest = GlideApp.with(mContext).load(options.file);
        } else if (options.drawableResId != 0) {
            glideRequest = GlideApp.with(mContext).load(options.drawableResId);
        } else if (options.uri != null) {
            glideRequest = GlideApp.with(mContext).load(options.uri);
        }


        if (options.sizeHeight > 0 && options.sizeWidth > 0) {
            glideRequest.override(options.sizeWidth, options.sizeHeight);
        }

        if (options.isCenterInside) {
            glideRequest.centerInside();
        } else if (options.isCenterCrop) {
            glideRequest.centerCrop();
        }

        if (options.errorResId != 0) {
            glideRequest.error(options.errorResId);
        }
        if (options.placeholderResId != 0) {
            glideRequest.placeholder(options.placeholderResId);
        }
        if (options.skipMemoryCache) {
            glideRequest.skipMemoryCache(options.skipMemoryCache);
        }
        if (options.isCircle) {
            glideRequest.circleCrop();
        }


        if (options.isCrossFade) {
            glideRequest.transition(DrawableTransitionOptions.withCrossFade());
        }

        if (options.targetView instanceof ImageView) {
            glideRequest.into(((ImageView) options.targetView));
        } else if (options.bitmapCallBack != null) {
            glideRequest.into(new GlideBitmapCallback(options.bitmapCallBack));
        }

    }


    class GlideBitmapCallback<T> extends SimpleTarget<T> {

        BitmapCallBack bitmapCallBack;

        public GlideBitmapCallback(BitmapCallBack bitmapCallBack) {
            this.bitmapCallBack = bitmapCallBack;
        }

        @Override
        public void onResourceReady(T resource, Transition<? super T> transition) {
            if (this.bitmapCallBack != null) {
                bitmapCallBack.onBitmapLoaded(resource);
            }
        }
    }


    @Override
    public void clearMemoryCache() {
    }

    @Override
    public void clearDiskCache() {

    }
}
