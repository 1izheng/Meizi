package com.yjz.meizi.http;

import com.yjz.meizi.BuildConfig;
import com.facebook.stetho.okhttp3.StethoInterceptor;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author lizheng
 * @date created at 2018/9/1 下午5:53
 */
public class HttpHelper {

    /**
     * 保存一个retrofit的实例，通过（baseUrl来获取）
     */
    private HashMap<String, Retrofit> map = new HashMap<>();

    public static final String BASE_API = "http://gank.io/api/";
    public static final int CONNECT_TIME_OUT = 30;
    public static final int READ_TIME_OUT = 30;
    public static final int WRITE_TIME_OUT = 30;


    private static HttpHelper mInstance = null;

    private HttpHelper() {
    }

    private static class RetrofitInstance {
        private final static HttpHelper instance = new HttpHelper();
    }

    public static HttpHelper get() {
        return RetrofitInstance.instance;
    }


    private static OkHttpClient getOkHttpClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .connectTimeout(CONNECT_TIME_OUT, TimeUnit.SECONDS)
                .writeTimeout(WRITE_TIME_OUT, TimeUnit.SECONDS)
                .readTimeout(READ_TIME_OUT, TimeUnit.SECONDS);

        //开启日志和调试
        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            builder.addInterceptor(loggingInterceptor)
                    .addNetworkInterceptor(new StethoInterceptor());
        }

        return builder.build();
    }

    private Retrofit getRetrofit(String url) {
        Retrofit mRetrofit;

        if (map.containsKey(url)) {
            mRetrofit = map.get(url);
        } else {
            mRetrofit = createRetrofit(url);
        }
        return mRetrofit;
    }

    private Retrofit createRetrofit(String url) {
        Retrofit mRetrofit = new Retrofit.Builder()
                .client(getOkHttpClient())
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        return mRetrofit;
    }


    public Api getApiService() {
        return getRetrofit(BASE_API).create(Api.class);
    }

}
