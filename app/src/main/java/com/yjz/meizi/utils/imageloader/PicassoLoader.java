package com.yjz.meizi.utils.imageloader;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.yjz.meizi.utils.Utils;
import com.squareup.picasso.LruCache;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;
import com.squareup.picasso.Target;
import com.squareup.picasso.Transformation;

import java.io.File;

/**
 * Created by JohnsonFan on 2017/6/27.
 */

public class PicassoLoader implements ILoaderStrategy {
	private volatile static Picasso sPicassoSingleton;
	private final String PICASSO_CACHE = "picasso-cache";
	private static LruCache sLruCache = new LruCache(Utils.getContext());

	private static Picasso getPicasso() {
		if (sPicassoSingleton == null) {
			synchronized (PicassoLoader.class) {
				if (sPicassoSingleton == null) {
					sPicassoSingleton = new Picasso.Builder(Utils.getContext()).memoryCache(sLruCache).build();
				}
			}
		}
		return sPicassoSingleton;
	}

	@Override
	public void clearMemoryCache() {
		sLruCache.clear();
	}

	@Override
	public void clearDiskCache() {
		File diskFile = new File(Utils.getContext().getCacheDir(), PICASSO_CACHE);
		if (diskFile.exists()) {
			//这边自行写删除代码
//	        FileUtil.deleteFile(diskFile);
		}
	}

	@Override
	public void loadImage(LoaderOptions options) {
		RequestCreator requestCreator = null;
		if (options.url != null) {
			requestCreator = getPicasso().load(options.url);
		} else if (options.file != null) {
			requestCreator = getPicasso().load(options.file);
		}else if (options.drawableResId != 0) {
			requestCreator = getPicasso().load(options.drawableResId);
		} else if (options.uri != null){
			requestCreator = getPicasso().load(options.uri);
		}

		if (requestCreator == null) {
			throw new NullPointerException("requestCreator must not be null");
		}
		if (options.sizeWidth > 0 && options.sizeHeight > 0) {
			requestCreator.resize(options.sizeWidth, options.sizeHeight);
		}
		if (options.isCenterInside) {
			requestCreator.centerInside();
		} else if (options.isCenterCrop) {
			requestCreator.centerCrop();
		}
		if (options.errorResId != 0) {
			requestCreator.error(options.errorResId);
		}
		if (options.placeholderResId != 0) {
			requestCreator.placeholder(options.placeholderResId);
		}

		if (options.targetView instanceof ImageView) {
			requestCreator.into(((ImageView)options.targetView));
		} else if (options.bitmapCallBack != null){
			requestCreator.into(new PicassoTarget(options.bitmapCallBack));
		}
	}

	class PicassoTarget implements Target {
		BitmapCallBack callBack;

		protected PicassoTarget(BitmapCallBack callBack) {
			this.callBack = callBack;
		}

		@Override
		public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
			if (this.callBack != null) {
				this.callBack.onBitmapLoaded(bitmap);
			}
		}

		@Override
		public void onBitmapFailed(Exception e, Drawable errorDrawable) {
			if (this.callBack != null) {
			}
		}

		@Override
		public void onPrepareLoad(Drawable placeHolderDrawable) {

		}
	}

	class PicassoTransformation implements Transformation {
		private float bitmapAngle;

		protected PicassoTransformation(float corner){
			this.bitmapAngle = corner;
		}

		@Override
		public Bitmap transform(Bitmap source) {
			float roundPx = bitmapAngle;//圆角的横向半径和纵向半径
			Bitmap output = Bitmap.createBitmap(source.getWidth(),
					source.getHeight(), Bitmap.Config.ARGB_8888);
			Canvas canvas = new Canvas(output);
			final int color = 0xff424242;
			final Paint paint = new Paint();
			final Rect rect = new Rect(0, 0, source.getWidth(),source.getHeight());
			final RectF rectF = new RectF(rect);
			paint.setAntiAlias(true);
			canvas.drawARGB(0, 0, 0, 0);
			paint.setColor(color);
			canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
			paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
			canvas.drawBitmap(source, rect, rect, paint);
			source.recycle();
			return output;
		}

		@Override
		public String key() {
			return "bitmapAngle()";
		}
	}

}
