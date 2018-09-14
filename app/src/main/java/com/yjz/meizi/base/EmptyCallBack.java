package com.yjz.meizi.base;

import com.yjz.meizi.R;
import com.kingja.loadsir.callback.Callback;

/**
 * loadSir emptyView
 * @author lizheng
 * @date created at 2018/9/12 上午9:02
 */
public class EmptyCallBack extends Callback {
    @Override
    protected int onCreateView() {
        return R.layout.loadsir_empty;
    }
}
