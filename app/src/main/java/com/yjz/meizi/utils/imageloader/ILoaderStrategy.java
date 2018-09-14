package com.yjz.meizi.utils.imageloader;

/**
 * @author lizheng
 * created at 2018/9/13 上午11:07
 */
public interface ILoaderStrategy {

	void loadImage(LoaderOptions options);

	/**
	 * 清理内存缓存
	 */
	void clearMemoryCache();

	/**
	 * 清理磁盘缓存
	 */
	void clearDiskCache();

}
