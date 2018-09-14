package com.yjz.meizi.ui.activity;

import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.yjz.meizi.R;
import com.yjz.meizi.base.BaseActivity;
import com.yjz.meizi.ui.fragment.MeiziMainFragment;
import com.yjz.meizi.utils.AppManager;
import com.yjz.meizi.utils.To;
import com.yjz.meizi.utils.Urls;

import butterknife.BindView;

/**
 * @author lizheng
 *         created at 2018/8/29 下午4:21
 */

public class MainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.fl_content)
    FrameLayout flContent;
    @BindView(R.id.navMenu)
    NavigationView navMenu;
    @BindView(R.id.drawerlayout)
    DrawerLayout drawerlayout;

    private static final String TAG_DOUBAN = "douban";
    private static final String TAG_GANKIO = "gank.io";
    private static final String[] fragmentTags = new String[]{TAG_DOUBAN, TAG_GANKIO};

    private MeiziMainFragment doubanFragment, gankFragment, maizituFragment;

    @Override
    protected int setLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerlayout, toolbar, R.string.nav_open, R.string.nav_close);
        drawerlayout.addDrawerListener(toggle);
        toggle.syncState();

        navMenu.setNavigationItemSelectedListener(this);
    }

    @Override
    public void initData() {
        MenuItem item = navMenu.getMenu().findItem(R.id.nav_douban);
        item.setChecked(true);
        setSelectFragment(0);
    }


    @Override
    public void onBackPressed() {
        if (drawerlayout.isDrawerOpen(GravityCompat.START)) {
            drawerlayout.closeDrawer(GravityCompat.START);
        } else {
            doubleExit();
        }
    }

    private long exitTime = 0;

    public void doubleExit() {
        if ((System.currentTimeMillis() - exitTime) > 2000) {
            To.showShortToast("再按一次退出程序");
            exitTime = System.currentTimeMillis();
        } else {
            AppManager.getAppManager().finishAllActivityAndExit(mContext);
        }
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_douban) {
            setSelectFragment(0);
        } else if (id == R.id.nav_gank) {
            setSelectFragment(1);
        } else if (id == R.id.nav_meizitu) {
            setSelectFragment(2);
        }

        drawerlayout.closeDrawer(GravityCompat.START);
        return true;
    }


    private void setSelectFragment(int position) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        hideFragments(manager, transaction);
        switch (position) {
            case 0:
                if (doubanFragment == null) {
                    doubanFragment = MeiziMainFragment.newInstance(Urls.TYPE_DOUBAN);
                    transaction.add(R.id.fl_content, doubanFragment, TAG_DOUBAN);
                }
                transaction.show(doubanFragment);
                break;
            case 1:
                if (gankFragment == null) {
                    gankFragment = MeiziMainFragment.newInstance(Urls.TYPE_GANK);
                    transaction.add(R.id.fl_content, gankFragment, TAG_GANKIO);
                }
                transaction.show(gankFragment);
                break;
            case 2:
                if (maizituFragment == null) {
                    maizituFragment = MeiziMainFragment.newInstance(Urls.TYPE_MEIZITU);
                    transaction.add(R.id.fl_content, maizituFragment, TAG_GANKIO);
                }
                transaction.show(maizituFragment);
                break;

        }
        transaction.commit();
    }

    private void hideFragments(FragmentManager fragmentManager,
                               FragmentTransaction transaction) {
        for (int i = 0; i < fragmentTags.length; i++) {
            Fragment fragment = fragmentManager.findFragmentByTag(fragmentTags[i]);
            if (fragment != null && fragment.isVisible()) {
                transaction.hide(fragment);
            }
        }
    }


}
