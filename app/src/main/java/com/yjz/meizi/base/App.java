package com.yjz.meizi.base;

import android.app.Application;
import android.content.Context;
import android.support.v4.content.ContextCompat;

import com.kingja.loadsir.core.LoadSir;
import com.scwang.smartrefresh.header.MaterialHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.DefaultRefreshFooterCreator;
import com.scwang.smartrefresh.layout.api.DefaultRefreshHeaderCreator;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.BallPulseFooter;
import com.squareup.leakcanary.LeakCanary;
import com.yjz.meizi.R;
import com.yjz.meizi.utils.Utils;
import com.yjz.meizi.utils.imageloader.GlideLoader;
import com.yjz.meizi.utils.imageloader.ImageLoader;

/**
 * @author lizheng
 * @date created at 2018/9/6 下午3:39
 */
public class App extends Application {


    @Override
    public void onCreate() {
        super.onCreate();
        if (LeakCanary.isInAnalyzerProcess(this)) {
            return;
        }
        LeakCanary.install(this);
        Utils.init(this);
        initLoadSir();
        //初始化图片加载框架
        ImageLoader.getInstance().setImageLoader(new GlideLoader());
    }

    private void initLoadSir() {
        LoadSir.beginBuilder()
                .addCallback(new ErrorCallBack())
                .addCallback(new EmptyCallBack())
                .addCallback(new LoadingCallBack())
                .commit();
    }


    static {
        //设置全局的Header构建器
        SmartRefreshLayout.setDefaultRefreshHeaderCreator(new DefaultRefreshHeaderCreator() {
            @Override
            public RefreshHeader createRefreshHeader(Context context, RefreshLayout layout) {
                //下拉内容不偏移
                layout.setEnableHeaderTranslationContent(false);
                layout.setEnableAutoLoadMore(false);
                //关闭越界回弹功能
                layout.setEnableOverScrollBounce(false);
                layout.setReboundDuration(300);
                //滑动底部自动加载更多
                layout.setEnableAutoLoadMore(true);
                return new MaterialHeader(context);
            }
        });
        //设置全局的Footer构建器
        SmartRefreshLayout.setDefaultRefreshFooterCreator(new DefaultRefreshFooterCreator() {
            @Override
            public RefreshFooter createRefreshFooter(Context context, RefreshLayout layout) {
                return new BallPulseFooter(context)
                        .setAnimatingColor(ContextCompat.getColor(context, R.color.colorPrimary));
            }
        });
    }
}
