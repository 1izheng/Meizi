package com.yjz.meizi.adapter;

import android.content.Context;

import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.yjz.meizi.R;
import com.yjz.meizi.model.Meizi;
import com.yjz.meizi.utils.imageloader.GlideApp;
import com.yjz.meizi.view.RatioImageView;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lizheng
 * @date created at 2018/9/6 下午3:46
 */
public class MeiziAdapter extends CommonAdapter<Meizi> {


    public MeiziAdapter(Context context, List<Meizi> datas) {
        super(context, R.layout.adapter_meizi_item, datas);
        setHasStableIds(true);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    protected void convert(ViewHolder holder, Meizi meizi, int position) {


        final RatioImageView imageView = holder.getView(R.id.iv_mz);

        if (position % 2 == 0) {
            imageView.setImageRatio(0.7f);
        } else {
            imageView.setImageRatio(0.6f);
        }

        GlideApp.with(imageView.getContext()).load(meizi.getUrl())
                .placeholder(R.color.gray).error(R.color.gray)
                .centerCrop()
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(imageView);

    }


    public void addDatas(List<Meizi> datas) {
        int oldSize = getDatas().size();
        getDatas().addAll(datas);
        //局部刷新防止图片位移
        notifyItemRangeChanged(oldSize, 20);
    }

    public void setDatas(List<Meizi> datas) {
        if (datas == null) {
            datas = new ArrayList<>();
        }
        this.mDatas.clear();
        this.mDatas.addAll(datas);
        notifyDataSetChanged();
    }
}
