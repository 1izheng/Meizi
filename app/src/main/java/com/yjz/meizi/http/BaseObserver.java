package com.yjz.meizi.http;


import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.yjz.meizi.utils.To;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 *
 * @author lizheng
 * created at 2018/9/7 下午2:37
 */

public abstract class BaseObserver<T> implements Observer<BaseResponse<T>> {


    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public final void onNext(BaseResponse<T> response) {
        if(response.isSuccess()){
            onSuccess(response.getData());
        }else{
            onFailure(response.getCode(),response.getMsg());
        }
    }

    @Override
    public void onError(@NonNull Throwable e) {
        ApiException apiException = ApiException.handlerException(e);
        onFailure(apiException.getCode(), apiException.getMsg());
    }

    @Override
    public void onComplete() {

    }

    public abstract void onSuccess(T result);

    public void onFailure(int code, String errorMsg) {
        if(!TextUtils.isEmpty(errorMsg)){
            To.showShortToast(errorMsg);
        }
    }

}
