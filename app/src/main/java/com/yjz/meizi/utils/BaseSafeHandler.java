package com.yjz.meizi.utils;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import java.lang.ref.WeakReference;

/**
 * 避免内存溢出handler
 * Created by lizheng on 2018/7/2.
 */

public abstract class BaseSafeHandler<T> extends Handler {

    private final WeakReference<T> mWeakReference;

    public BaseSafeHandler(T t) {
        super(Looper.getMainLooper());
        mWeakReference = new WeakReference<>(t);
    }

    @Override
    public void handleMessage(Message msg) {
        T t = mWeakReference.get();
        if (t != null) {
            handleMessage(t, msg);
        }
    }

    protected abstract void handleMessage(T t, Message msg);
}
