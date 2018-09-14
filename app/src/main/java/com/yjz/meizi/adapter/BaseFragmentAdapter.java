package com.yjz.meizi.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.yjz.meizi.ui.fragment.MeiziFragment;
import com.yjz.meizi.utils.Urls;

/**
 * @author lizheng
 *         created at 2018/9/8 下午2:25
 */

public class BaseFragmentAdapter extends FragmentPagerAdapter {

    private String[] mTitles;
    private int type;

    public BaseFragmentAdapter(FragmentManager fm, int type) {
        super(fm);
        this.type = type;
        this.mTitles = Urls.getLoadUrlTitles(type);
    }


    @Override
    public Fragment getItem(int position) {
        return MeiziFragment.newInstance(type, position);
    }

    @Override
    public int getCount() {
        return isEmpty() ? 0 : mTitles.length;
    }

    public boolean isEmpty() {
        return mTitles == null;

    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles[position];
    }

}
