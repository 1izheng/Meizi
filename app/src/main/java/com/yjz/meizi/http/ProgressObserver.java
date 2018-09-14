package com.yjz.meizi.http;

import android.content.Context;

import io.reactivex.disposables.Disposable;

/**
 * 调用显示dialog
 * @author lizheng
 * @date created at 2018/9/7 下午3:14
 */
public abstract class ProgressObserver<T> extends BaseObserver<T> implements ProgressCancelListener {


    private ProgressDialogHandler mProgressDialogHandler;
    private Disposable d;


    public ProgressObserver(Context context, boolean cancelable) {
        mProgressDialogHandler = new ProgressDialogHandler(context, this, cancelable);
    }

    private void showProgressDialog() {
        if (mProgressDialogHandler != null) {
            mProgressDialogHandler.obtainMessage(ProgressDialogHandler.SHOW_PROGRESS_DIALOG).sendToTarget();
        }
    }

    private void dismissProgressDialog() {
        if (mProgressDialogHandler != null) {
            mProgressDialogHandler.obtainMessage(ProgressDialogHandler.DISMISS_PROGRESS_DIALOG).sendToTarget();
            mProgressDialogHandler = null;
        }
    }

    @Override
    public void onCancelProgress() {
        //如果处于订阅状态，则取消订阅
        if (!d.isDisposed()) {
            d.dispose();
        }
    }


    @Override
    public void onSubscribe(Disposable d) {
        this.d = d;
        showProgressDialog();
    }


    @Override
    public void onError(Throwable e) {
        super.onError(e);
        dismissProgressDialog();
    }

    @Override
    public void onComplete() {
        dismissProgressDialog();
    }

}
