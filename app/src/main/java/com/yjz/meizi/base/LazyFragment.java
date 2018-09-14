package com.yjz.meizi.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;

/**
 * 延迟加载LazyFragment
 * @author lizheng
 * created at 2018/9/12 下午1:56
 */
public abstract class LazyFragment extends BaseFragment {

    //是否加载过
    protected boolean isLoad;
    //是否初始化完成
    protected boolean isPrepared;
    protected boolean isVisible;

    protected abstract void lazyLoadData();

    protected void setLoaded() {
        isLoad = true;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(setLayoutId(), container, false);
            ButterKnife.bind(this, rootView);
            isPrepared = true;
        }
        return rootView;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint()) {
            isVisible = true;
            onVisible();
        } else {
            isVisible = false;
        }
    }

    private void onVisible() {
        initActivityCreated();
    }

    protected void initActivityCreated() {
        if (!isPrepared || !isVisible || isLoad) {
            return;
        }
        initView();
        lazyLoadData();
    }
}
