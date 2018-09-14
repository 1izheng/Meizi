package com.yjz.meizi.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kingja.loadsir.core.LoadService;
import com.trello.rxlifecycle2.components.support.RxFragment;

import org.greenrobot.eventbus.EventBus;

import butterknife.ButterKnife;

/**
 * @author lizheng
 * @date created at 2018/9/12 上午11:48
 */
public abstract class BaseFragment extends RxFragment {


    protected Bundle bundle;
    protected View rootView;
    protected Context mContext;
    protected Activity mActivity;
    protected LoadService mLoadService;

    protected void initBundle() {
    }

    protected abstract int setLayoutId();

    protected abstract void initView();


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.mContext = activity;
        this.mActivity = activity;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bundle = getArguments();
        if (bundle != null) {
            initBundle();
        }

        if (useEventBus()) {
            EventBus.getDefault().register(this);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (useEventBus()) {
            EventBus.getDefault().unregister(this);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(setLayoutId(), container, false);
            ButterKnife.bind(this, rootView);
        }
        initView();
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initActivityCreated();
    }

    protected void initActivityCreated() {
    }

    /**
     * 是否使用EventBus,使用就重写此方法 return true
     *
     * @return
     */
    protected boolean useEventBus() {
        return false;
    }


    protected void showLoading() {
        mLoadService.showCallback(LoadingCallBack.class);
    }
    protected void showContent() {
        mLoadService.showSuccess();
    }
    protected void showRetry() {
        mLoadService.showCallback(ErrorCallBack.class);
    }
    protected void showEmpty(){
        mLoadService.showCallback(EmptyCallBack.class);
    }
}
