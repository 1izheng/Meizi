package com.yjz.meizi.base;

import android.app.Application;
import android.content.Context;

import com.yjz.meizi.utils.Utils;
import com.yjz.meizi.utils.imageloader.GlideLoader;
import com.yjz.meizi.utils.imageloader.ImageLoader;
import com.kingja.loadsir.core.LoadSir;
import com.scwang.smartrefresh.header.MaterialHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.DefaultRefreshFooterCreator;
import com.scwang.smartrefresh.layout.api.DefaultRefreshHeaderCreator;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.squareup.leakcanary.LeakCanary;

/**
 * @author lizheng
 * @date created at 2018/9/6 下午3:39
 */
public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Utils.init(this);
        initLoadSir();
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(this);
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
//                layout.setPrimaryColorsId(R.color.colorPrimary, android.R.color.white);//全局设置主题颜色
                //下拉内容不偏移
                layout.setEnableHeaderTranslationContent(false);
                layout.setEnableAutoLoadMore(false);
                layout.setEnableOverScrollBounce(false);//关闭越界回弹功能
                layout.setReboundDuration(300);
                layout.setEnableAutoLoadMore(true); //滑动底部自动加载更多
                return new MaterialHeader(context);
//                return new ClassicsHeader(context).setTextSizeTitle(14).setEnableLastTime(false);
            }
        });
        //设置全局的Footer构建器
        SmartRefreshLayout.setDefaultRefreshFooterCreator(new DefaultRefreshFooterCreator() {
            @Override
            public RefreshFooter createRefreshFooter(Context context, RefreshLayout layout) {
                return new ClassicsFooter(context).setTextSizeTitle(14);
            }
        });

    }
}
