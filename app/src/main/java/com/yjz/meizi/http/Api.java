package com.yjz.meizi.http;

import com.yjz.meizi.model.Meizi;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * @author lizheng
 * @date created at 2018/9/1 下午5:40
 */
public interface Api {

    @GET("data/福利/{size}/{page}")
    Observable<BaseResponse<List<Meizi>>> loadMeizi(@Path("size") int size, @Path("page") int page);
}
