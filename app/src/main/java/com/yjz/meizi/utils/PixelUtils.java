package com.yjz.meizi.utils;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Point;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Window;
import android.view.WindowManager;

import java.lang.reflect.Method;

/**
 * 像素转换工具
 * 
 * @author lizheng
 */
public class PixelUtils {
	private static Resources resources = Resources.getSystem();
	private static int desityDpi = resources.getDisplayMetrics().densityDpi;
	private static float scaledDensity = resources.getDisplayMetrics().scaledDensity;

	public static int dp2px(float value) {
		final float scale = desityDpi;
		return (int) (value * (scale / 160) + 0.5f);
	}

	public static int px2dp(float value) {
		final float scale = desityDpi;
		return (int) ((value * 160) / scale + 0.5f);
	}

	public static int sp2px(float value) {
		float spvalue = value * scaledDensity;
		return (int) (spvalue + 0.5f);
	}

	public static int px2sp(float value) {
		final float scale = scaledDensity;
		return (int) (value / scale + 0.5f);
	}

	public static int getScreenWidth(Activity activity) {
		int width = 0;
		DisplayMetrics dm = activity.getResources().getDisplayMetrics();
		width = dm.widthPixels;
		return width;
	}
	
	public static int getScreenHeight(Activity activity) {
		int height = 0;
		DisplayMetrics dm = activity.getResources().getDisplayMetrics();
		height = dm.heightPixels;
		return height;
	}


	public static int getScreenWidth(Context activity) {
		int width = 0;
		DisplayMetrics dm = activity.getResources().getDisplayMetrics();
		width = dm.widthPixels;
		return width;
	}

	public static int getScreenHeight(Context activity) {
		int height = 0;
		DisplayMetrics dm = activity.getResources().getDisplayMetrics();
		height = dm.heightPixels;
		return height;
	}


	//获取屏幕原始尺寸高度，包括虚拟功能键高度
	public static int getDpi(Context context){
		int dpi = 0;
		WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		Display display = windowManager.getDefaultDisplay();
		DisplayMetrics displayMetrics = new DisplayMetrics();
		@SuppressWarnings("rawtypes")
        Class c;
		try {
			c = Class.forName("android.view.Display");
			@SuppressWarnings("unchecked")
            Method method = c.getMethod("getRealMetrics",DisplayMetrics.class);
			method.invoke(display, displayMetrics);
			dpi=displayMetrics.heightPixels;
		}catch(Exception e){
			e.printStackTrace();
		}
		return dpi;
	}

	/**
	 * 获取 虚拟按键的高度
	 * @param context
	 * @return
	 */
	public static  int getBottomStatusHeight(Context context){
		int totalHeight = getDpi(context);

		int contentHeight = getScreenHeightUse(context);

		return totalHeight  - contentHeight;
	}

	/**
	 * 标题栏高度
	 * @return
	 */
	public static int getTitleHeight(Activity activity){
		return  activity.getWindow().findViewById(Window.ID_ANDROID_CONTENT).getTop();
	}

	/**
	 * 获得状态栏的高度
	 *
	 * @param context
	 * @return
	 */
	public static int getStatusHeight(Context context)
	{

		int statusHeight = -1;
		try
		{
			Class<?> clazz = Class.forName("com.android.internal.R$dimen");
			Object object = clazz.newInstance();
			int height = Integer.parseInt(clazz.getField("status_bar_height")
					.get(object).toString());
			statusHeight = context.getResources().getDimensionPixelSize(height);
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return statusHeight;
	}


	/**
	 * 获得屏幕高度(可用区域)
	 *
	 * @param context
	 * @return
	 */
	public static int getScreenHeightUse(Context context)
	{
		WindowManager wm = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);
		DisplayMetrics outMetrics = new DisplayMetrics();
		wm.getDefaultDisplay().getMetrics(outMetrics);
		return outMetrics.heightPixels;
	}


	/**
	 * 获得屏幕宽度(可用区域)
	 *
	 * @param context
	 * @return
	 */
	public static int getScreenWidthUse(Context context)
	{
		WindowManager wm = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);
		DisplayMetrics outMetrics = new DisplayMetrics();
		wm.getDefaultDisplay().getMetrics(outMetrics);
		return outMetrics.widthPixels;
	}


	/**
	 * 获取屏幕尺寸
	 */
	@SuppressWarnings("deprecation")
	@TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
	public static Point getScreenSize(Context context){
		WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		Display display = windowManager.getDefaultDisplay();
		if(Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB_MR2){
			return new Point(display.getWidth(), display.getHeight());
		}else{
			Point point = new Point();
			display.getSize(point);
			return point;
		}
	}
	
}
