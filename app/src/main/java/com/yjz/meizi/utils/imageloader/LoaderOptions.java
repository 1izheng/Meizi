package com.yjz.meizi.utils.imageloader;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.annotation.DrawableRes;
import android.view.View;

import java.io.File;

/**
 * 图片加载框架属性统一封装
 *
 * @author lizheng
 * @date created at 2018/9/13 上午10:57
 */
public class LoaderOptions {

    public Context mContext;
    public int placeholderResId;
    public int errorResId;
    public boolean isCenterCrop;
    public boolean isCenterInside;
    public boolean isCircle;
    public boolean isCrossFade;
    /**
     * 跳过内存缓存
     */
    public boolean skipMemoryCache;

    public int sizeWidth;
    public int sizeHeight;
    public Drawable placeholder;
    /**
     * 目标view
     */
    public View targetView;
    /**
     * 获取bitmap显示到任意view
     */
    public BitmapCallBack bitmapCallBack;
    public String url;
    public File file;
    public int drawableResId;
    public Uri uri;
    /**
     * 实时切换图片加载库
     */
    public ILoaderStrategy loader;


    public LoaderOptions(String url) {
        this.url = url;
    }

    public LoaderOptions(File file) {
        this.file = file;
    }

    public LoaderOptions(int drawableResId) {
        this.drawableResId = drawableResId;
    }

    public LoaderOptions(Uri uri) {
        this.uri = uri;
    }

    public void into(View targetView) {
        this.targetView = targetView;
        ImageLoader.getInstance().loadOptions(this);
    }

    public void getBitmap(Context context, BitmapCallBack<Bitmap> bitmapCallBack) {
        this.mContext = context;
        this.bitmapCallBack = bitmapCallBack;
        ImageLoader.getInstance().loadOptions(this);
    }
    public void getDrawable(Context context, BitmapCallBack<Drawable> bitmapCallBack) {
        this.mContext = context;
        this.bitmapCallBack = bitmapCallBack;
        ImageLoader.getInstance().loadOptions(this);
    }

    ///////////////////////////////////////////////////////////////////////////
    //
    ///////////////////////////////////////////////////////////////////////////
    public LoaderOptions loader(ILoaderStrategy imageLoader) {
        this.loader = imageLoader;
        return this;
    }

    public LoaderOptions placeholder(@DrawableRes int placeHolderResId) {
        this.placeholderResId = placeHolderResId;
        return this;
    }

    public LoaderOptions placeholder(Drawable placeholder) {
        this.placeholder = placeholder;
        return this;
    }

    public LoaderOptions error(@DrawableRes int errorResId) {
        this.errorResId = errorResId;
        return this;
    }

    public LoaderOptions centerCrop() {
        isCenterCrop = true;
        return this;
    }

    public LoaderOptions circle() {
        isCircle = true;
        return this;
    }

    public LoaderOptions crossFade() {
        isCrossFade = true;
        return this;
    }

    public LoaderOptions centerInside() {
        isCenterInside = true;
        return this;
    }

    public LoaderOptions resize(int sizeWidth, int sizeHeight) {
        this.sizeWidth = sizeWidth;
        this.sizeHeight = sizeHeight;
        return this;
    }

    public LoaderOptions skipMemoryCache(boolean skipNetCache) {
        this.skipMemoryCache = skipNetCache;
        return this;
    }


}
