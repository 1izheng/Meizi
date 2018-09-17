package com.yjz.meizi.ui.fragment;

import android.os.Bundle;
import android.support.v4.view.ViewPager;

import com.flyco.tablayout.SlidingTabLayout;
import com.yjz.meizi.R;
import com.yjz.meizi.adapter.BaseFragmentAdapter;
import com.yjz.meizi.base.BaseFragment;

import butterknife.BindView;


public class MeiziMainFragment extends BaseFragment {


    @BindView(R.id.tabs)
    SlidingTabLayout tabs;
    @BindView(R.id.viewpager)
    ViewPager viewpager;

    private int type;

    public static MeiziMainFragment newInstance(int type) {
        MeiziMainFragment fragment = new MeiziMainFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("type", type);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected void initBundle() {
        Bundle bd = getArguments();
        type = bd.getInt("type");
    }

    @Override
    protected int setLayoutId() {
        return R.layout.fragment_meizi_main;
    }

    @Override
    protected void initView() {
        BaseFragmentAdapter adapter = new BaseFragmentAdapter(getChildFragmentManager(), type);
        viewpager.setAdapter(adapter);
        tabs.setViewPager(viewpager);
        viewpager.setOffscreenPageLimit(adapter.getCount());
    }


    @Override
    protected void initActivityCreated() {

    }
}
