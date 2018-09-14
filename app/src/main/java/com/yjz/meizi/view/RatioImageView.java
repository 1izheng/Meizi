/*
 * Copyright 2015 XiNGRZ <chenxingyu92@gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.yjz.meizi.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;

import com.yjz.meizi.R;

/**
 * 一个能保持比例的 ImageView
 * TODO: 暂时只支持维持宽度适应高度
 *
 * @author XiNGRZ
 */
public class RatioImageView extends AppCompatImageView {

    public static final int RATIO_WIDTH_HEIGHT = 1266;
    public static final int RATIO_HEIGHT_WIDTH = 2266;

    private static final float DEFAULT_RATIO = 1.0f;

    // 默认设置宽高比
    public int imageRatioType;
    private float imageRatio;


    public RatioImageView(Context context) {
        super(context);
    }


    public RatioImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.initAttributes(context, attrs);
    }


    public RatioImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.initAttributes(context, attrs);
    }


/*    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public RatioImageView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.initAttributes(context, attrs);
    }*/


    private void initAttributes(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.RatioImageView);
        this.imageRatioType = typedArray.getInteger(R.styleable.RatioImageView_imageRatioType,
                RATIO_WIDTH_HEIGHT);
        this.imageRatio = typedArray.getFloat(R.styleable.RatioImageView_imageRatio, DEFAULT_RATIO);
        typedArray.recycle();
    }


    public void setImageRatioType(int ratioType) {
        this.imageRatioType = ratioType;
    }


    /**
     * 设置宽高比,高度要跟宽度保持一致
     *
     * @param ratio width / height
     */
    public void setWidthHeightRatio(float ratio) {
        this.imageRatioType = RATIO_WIDTH_HEIGHT;
        this.imageRatio = ratio;
        this.requestLayout();
    }


    /**
     * 设置高宽比,宽度要跟高度保持一致
     *
     * @param ratio height / width
     */
    public void setHeightWidthRatio(float ratio) {
        this.imageRatioType = RATIO_HEIGHT_WIDTH;
        this.imageRatio = ratio;
        this.requestLayout();
    }


    public void setImageRatio(float ratio) {
        this.imageRatio = ratio;
        this.requestLayout();
    }


    @Override protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (this.imageRatio > 0) {
            int width = MeasureSpec.getSize(widthMeasureSpec);
            int height = MeasureSpec.getSize(heightMeasureSpec);
            if (this.imageRatioType == RatioImageView.RATIO_WIDTH_HEIGHT) {
                // 依照宽高比,重算高度
                if (width > 0) {
                    height = (int) ((float) width / this.imageRatio);
                }
            } else {
                // 依照高宽比,重算宽度
                if (height > 0) {
                    width = (int) ((float) height / this.imageRatio);
                }
            }
            this.setMeasuredDimension(width, height);
        } else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
    }

}
