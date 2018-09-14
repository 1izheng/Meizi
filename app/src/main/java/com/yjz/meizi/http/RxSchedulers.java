package com.yjz.meizi.http;

import com.trello.rxlifecycle2.LifecycleProvider;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * rx线程调度绑定生命周期
 *
 * @author lizheng
 * @date created at 2018/9/5 下午2:50
 */
public class RxSchedulers {

    public static <T> ObservableTransformer<T, T> io2Main(final LifecycleProvider lifecycleProvider) {
        return new ObservableTransformer<T, T>() {
            @Override
            public ObservableSource apply(Observable upstream) {
                return upstream.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .compose(lifecycleProvider.bindToLifecycle());
            }
        };
    }
}
