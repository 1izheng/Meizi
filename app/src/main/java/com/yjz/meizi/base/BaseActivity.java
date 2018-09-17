package com.yjz.meizi.base;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;

import com.yjz.meizi.utils.AppManager;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import org.greenrobot.eventbus.EventBus;

import butterknife.ButterKnife;

/**
 * @author lizheng
 * @date created at 2018/9/5 下午2:24
 */
public abstract class BaseActivity extends RxAppCompatActivity {

    protected Context mContext;
    protected Activity mActivity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        operStatusBar();
        // 指定屏幕竖向
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        // 设置全屏显示
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(setLayoutId());
        mContext = this;
        mActivity = this;
        AppManager.getAppManager().addActivity(this);
        ButterKnife.bind(this);
        if(useEventBus()){
            EventBus.getDefault().register(this);
        }
        init();
        initView();
        initData();
    }


    protected abstract int setLayoutId();

    protected abstract void initView();

    /**
     * 加载数据
     */
    public void initData() {
    }

    /**
     * 初始化数据,SP或者intent
     */
    public void init() {
    }

    /**
     * 操作状态栏
     */
    public void operStatusBar() {
    }


    @Override
    public void onBackPressed() {
        hideSoftInput(this);
        super.onBackPressed();
    }


    @Override
    protected void onPause() {
        super.onPause();
        hideSoftInput(this);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        AppManager.getAppManager().finishActivity(this);
        if(useEventBus()){
            EventBus.getDefault().unregister(this);
        }
    }


    public static void hideSoftInput(Activity activity) {
        View view = activity.getCurrentFocus();
        if (view == null) {
            view = new View(activity);
        }
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        if (imm == null) {
            return;
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }



    /**
     * 是否使用EventBus,使用就重写此方法 return true
     * @return
     */
    public boolean useEventBus(){
        return false;
    }
}
