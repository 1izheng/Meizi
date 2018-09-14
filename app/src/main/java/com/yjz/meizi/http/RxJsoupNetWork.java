package com.yjz.meizi.http;


import android.support.annotation.IntDef;
import android.support.annotation.NonNull;

import com.yjz.meizi.model.Meizi;
import com.yjz.meizi.utils.JsoupMgr;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;


/**
 * jsoup
 * @author lizheng 
 * created at 2018/9/14 下午3:27
 */

public class RxJsoupNetWork {
    public static final int GET = 1;
    public static final int POST = 2;

    @IntDef({GET, POST})
    @Retention(RetentionPolicy.SOURCE)
    @interface Method {
    }

    private String jsoupHeader =
            "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.4; en-US; rv:1.9.2.2) Gecko/20100316 Firefox/3.6.2";
    private Connection connection = null;

    private RxJsoupNetWork() {
    }


    public Observable<List<Meizi>> getApi(@NonNull String url, int urlType) {
        return getApi(url, GET, urlType);
    }

    public Observable<List<Meizi>> postApi(@NonNull String url,int urlType) {
        return getApi(url, POST, urlType);
    }

    public <T> Observable<T> getApi(@NonNull final String url, @Method final int type, final int urlType) {
        Observable<T> mObservable = Observable.create(new ObservableOnSubscribe<T>() {
            @Override
            public void subscribe(ObservableEmitter<T> emitter) {
                Document document = null;
                try {
                    switch (type) {
                        case GET:
                            document = getDocument(url);
                            break;
                        case POST:
                            document = postDocument(url);
                            break;
                    }
                    emitter.onNext((T) JsoupMgr.get(document).getList(urlType));
                    emitter.onComplete();
                } catch (IOException e) {
                    e.printStackTrace();
                    emitter.onError(ApiException.handlerException(e));
                }
            }
        });
        return mObservable;
    }


    public RxJsoupNetWork setConnection(@NonNull Connection connection) {
        this.connection = connection;
        return this;
    }

    public RxJsoupNetWork setUserAgent(@NonNull String userAgent) {
        this.jsoupHeader = userAgent;
        return this;
    }

    public static RxJsoupNetWork getInstance() {
        return RxJsoupNetWorkHolder.rxJsoupNetWork;
    }

    private Document getDocument(@NonNull String url) throws IOException {
        if (connection != null) {
            return connection.get();
        }
        return connect(url).get();
    }


    private Document postDocument(@NonNull String url) throws IOException {
        if (connection != null) {
            return connection.post();
        }
        return connect(url).post();
    }

    private Connection connect(@NonNull String url) {
        return Jsoup.connect(url.trim()).header("User-Agent", jsoupHeader);
    }

    private static final class RxJsoupNetWorkHolder {
        private static final RxJsoupNetWork rxJsoupNetWork = new RxJsoupNetWork();
    }
}
