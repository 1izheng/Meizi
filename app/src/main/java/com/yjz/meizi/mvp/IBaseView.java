package com.yjz.meizi.mvp;

/**
 * Created by lizheng on 2018/9/1.
 */

public interface IBaseView {

    void showLoading();

    void closeLoading();

    void showToast(String msg);
}
