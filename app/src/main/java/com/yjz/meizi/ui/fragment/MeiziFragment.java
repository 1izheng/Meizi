package com.yjz.meizi.ui.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.SparseArray;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.github.ielse.imagewatcher.ImageWatcher;
import com.github.ielse.imagewatcher.ImageWatcherHelper;
import com.kingja.loadsir.callback.Callback;
import com.kingja.loadsir.core.LoadSir;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.yjz.meizi.R;
import com.yjz.meizi.adapter.MeiziAdapter;
import com.yjz.meizi.base.LazyFragment;
import com.yjz.meizi.http.ApiException;
import com.yjz.meizi.http.BaseObserver;
import com.yjz.meizi.http.BaseResponse;
import com.yjz.meizi.http.HttpHelper;
import com.yjz.meizi.http.RxJsoupNetWork;
import com.yjz.meizi.http.RxSchedulers;
import com.yjz.meizi.model.Meizi;
import com.yjz.meizi.utils.ImageWacherLoader;
import com.yjz.meizi.utils.L;
import com.yjz.meizi.utils.PathUtils;
import com.yjz.meizi.utils.To;
import com.yjz.meizi.utils.Urls;
import com.yjz.meizi.utils.Utils;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;


public class MeiziFragment extends LazyFragment {
    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;

    private MeiziAdapter meiziAdapter;
    private int page = 1;
    private int size = 20;
    private ImageWatcherHelper imageWatcherHelper;

    private int type, tabPosition;


    public static MeiziFragment newInstance(int type, int tabPosition) {
        MeiziFragment fragment = new MeiziFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("type", type);
        bundle.putInt("tabPosition", tabPosition);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected void initBundle() {
        Bundle bd = getArguments();
        type = bd.getInt("type");
        tabPosition = bd.getInt("tabPosition");
    }

    @Override
    protected int setLayoutId() {
        return R.layout.fragment_meizi;
    }

    @Override
    protected void initView() {
        mLoadService = LoadSir.getDefault().register(refreshLayout, new Callback.OnReloadListener() {
            @Override
            public void onReload(View v) {
                refreshData();
            }
        });

        imageWatcherHelper = ImageWatcherHelper.with(getActivity(), new ImageWacherLoader());
        imageWatcherHelper.setOnPictureLongPressListener(new ImageWatcher.OnPictureLongPressListener() {
            @Override
            public void onPictureLongPress(ImageView imageView, final Uri uri, int i) {

                new AlertDialog.Builder(getActivity())
                        .setMessage("保存到相册?")
                        .setNegativeButton("保存", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                saveToAlbum(uri);
                            }
                        }).setNeutralButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).create().show();
            }
        });

        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        meiziAdapter = new MeiziAdapter(getActivity(), new ArrayList<Meizi>());
        recyclerView.setAdapter(meiziAdapter);

        meiziAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                Meizi bean = meiziAdapter.getDatas().get(position);
                if (null != bean) {
                    ImageView imageView = view.findViewById(R.id.iv_mz);
                    SparseArray<ImageView> map = new SparseArray<>();
                    map.put(0, imageView);
                    List<String> dataList = new ArrayList<>();
                    dataList.add(bean.getUrl());
                    imageWatcherHelper.show(imageView, map, convert(dataList));
                }
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });

        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                refreshData();
            }
        }).setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                page += 1;
                loadHttpData();
            }
        });
    }

    /**
     * 保存图片到相册
     *
     * @param uri
     */
    private void saveToAlbum(final Uri uri) {

        Observable.create(new ObservableOnSubscribe<File>() {
            @Override
            public void subscribe(ObservableEmitter<File> e) throws Exception {
                final File file = Glide.with(Utils.getContext()).asFile().load(uri).submit().get();
                String newFilePath = PathUtils.getPicturePathByCurrentTime();
                File newFile = new File(newFilePath);
                PathUtils.writeFileFromIS(newFile, new FileInputStream(file));
                //发送广播刷新图片
                MediaStore.Images.Media.insertImage(mActivity.getContentResolver(), newFile.getAbsolutePath(), newFile.getName(), "");
                getActivity().sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse(newFile.getParent())));
                e.onNext(newFile);
            }
        }).compose(RxSchedulers.<File>io2Main(this)).subscribe(new Observer<File>() {
            @Override
            public void onSubscribe(Disposable d) {
            }

            @Override
            public void onNext(File file) {
                To.showLongToast("保存成功");
                L.d("##########--->", file.getPath());
            }

            @Override
            public void onError(Throwable e) {
                To.showLongToast("保存失败");
            }

            @Override
            public void onComplete() {

            }
        });


    }

    @Override
    protected void lazyLoadData() {
        L.d("##########--->","加载数据~");
        refreshData();
        setLoaded();
    }

    private List<Uri> convert(List<String> data) {
        List<Uri> list = new ArrayList<>();
        for (String d : data) list.add(Uri.parse(d));
        return list;
    }


    private void refreshData() {
        page = 1;
        loadHttpData();
    }

    private boolean isFirst() {
        return meiziAdapter.getDatas().size() == 0;
    }

    private void loadHttpData() {
        if (isFirst()) {
            showLoading();
        }

        if (Urls.TYPE_GANK == type) {
            loadFromApi();
        } else {
            loadFromHtml();
        }
    }

    /**
     * 从api接口获取数据
     */
    private void loadFromApi() {
        HttpHelper.get().getApiService()
                .loadMeizi(size, page)
                .compose(RxSchedulers.<BaseResponse<List<Meizi>>>io2Main(this))
                .subscribe(new BaseObserver<List<Meizi>>() {
                    @Override
                    public void onSuccess(List<Meizi> result) {
                        complete();
                        showContent();
                        showData(result);
                    }

                    @Override
                    public void onFailure(int code, String errorMsg) {
                        super.onFailure(code, errorMsg);
                        complete();
                        if (isFirst()) {
                            showRetry();
                        }
                    }
                });
    }


    /**
     * jsoup抓取数据
     */
    private void loadFromHtml() {
        String url = Urls.getLoadUrl(type, tabPosition, page);
        L.d("##########--->","url:--->"+url);
        RxJsoupNetWork.getInstance().getApi(url, type)
                .compose(RxSchedulers.<List<Meizi>>io2Main(this))
                .subscribe(new Observer<List<Meizi>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(List<Meizi> list) {
                        complete();
                        showContent();
                        showData(list);
                    }

                    @Override
                    public void onError(Throwable e) {
                        ApiException apiException = ApiException.handlerException(e);
                        showRetry();
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }

    private void showData(List<Meizi> result) {
        L.d("##########--->", "数据长度----->" + result.size());
        if (page == 1) {
            meiziAdapter.setDatas(result);
            if (meiziAdapter.getDatas().size() == 0) {
                showEmpty();
            }
            refreshLayout.setNoMoreData(false);
        } else {
            meiziAdapter.addDatas(result);
            /*if (result.size() < size) {
                //没有更多
                refreshLayout.setNoMoreData(true);
            }*/
        }
    }


    private void complete() {
        refreshLayout.finishRefresh(0);
        refreshLayout.finishLoadMore(0);
    }


}
