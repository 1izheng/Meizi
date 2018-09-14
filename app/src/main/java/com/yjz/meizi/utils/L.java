package com.yjz.meizi.utils;

import android.util.Log;

import com.yjz.meizi.BuildConfig;

/**
 * Log统一管理
 * Created by lizheng on 2018/5/11.
 */

public class L {

    private L() {
        /* cannot be instantiated */
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    // 是否需要打印bug，可以在application的onCreate函数里面初始化
    public static boolean isDebug = BuildConfig.DEBUG;
    private static final String TAG = "YJZ____";

    private static String getDebugInfo() {
        Throwable stack = new Throwable().fillInStackTrace();
        StackTraceElement[] trace = stack.getStackTrace();
        int n = 2;
        return trace[n].getClassName() + " " + trace[n].getMethodName() + "()" + ":" + trace[n].getLineNumber() +
                " ";
    }

    // 下面四个是默认tag的函数
    public static void i(String msg) {
        if (isDebug)
            Log.i(TAG, msg);
    }

    public static void d(String msg) {
        if (isDebug)
            Log.d(TAG, getDebugInfo() + msg);
    }

    public static void e(String msg) {
        if (isDebug)
            Log.e(TAG, getDebugInfo() + msg);
    }

    public static void v(String msg) {
        if (isDebug)
            Log.v(TAG, getDebugInfo() + msg);
    }

    // 下面是传入自定义tag的函数
    public static void i(String tag, String msg) {
        if (isDebug)
            Log.i(tag, getDebugInfo() + msg);
    }

    public static void d(String tag, String msg) {
        if (isDebug)
            Log.i(tag, getDebugInfo() + msg);
    }

    public static void e(String tag, String msg) {
        if (isDebug)
            Log.i(tag, getDebugInfo() + msg);
    }

    public static void v(String tag, String msg) {
        if (isDebug)
            Log.i(tag, getDebugInfo() + msg);
    }
}