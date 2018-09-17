package com.yjz.meizi.utils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Environment;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * path工具类
 *
 * @author lizheng
 *         created at 2018/9/15 上午11:16
 */

public class PathUtils {
    private static File checkAndMkdirs(File file) {
        if (!file.exists()) {
            file.mkdirs();
        }
        return file;
    }

    private static boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(state);
    }

    /**
     * 有 sdcard 的时候，小米是 /storage/sdcard0/Android/data/com.avoscloud.chat/cache/
     * 无 sdcard 的时候，小米是 /data/data/com.avoscloud.chat/cache
     *
     * @return
     */
    private static File getAvailableCacheDir() {
        if (isExternalStorageWritable()) {
            return Utils.getContext().getExternalCacheDir();
        } else {
            // 只有此应用才能访问。拍照的时候有问题，因为拍照的应用写入不了该文件
            return Utils.getContext().getCacheDir();
        }
    }


    public static String checkAndMkdirs(String dir) {
        File file = new File(dir);
        if (file.exists() == false) {
            file.mkdirs();
        }
        return dir;
    }

    private static File getCacheDir() {
        File sdcard = Environment.getExternalStorageDirectory();
        File cacheDir = new File(sdcard, Const.CACHE_PATH);
        return checkAndMkdirs(cacheDir);
    }

    private static File getVoiceFileDir() {
        File filesDir = new File(getCacheDir(), "voices");
        return checkAndMkdirs(filesDir);
    }

    private static File getImageFileDir() {
        File filesDir = new File(getCacheDir(), "image");
        return checkAndMkdirs(filesDir);
    }

    /**
     * 可能文件会被清除掉，需要检查是否存在
     *
     * @param id
     * @return
     */
    public static String getVoiceFilePath(String id) {
        String path = new File(getVoiceFileDir(), id).getAbsolutePath();
        return path;
    }

    public static String getImageFilePath(String id) {
        String path = new File(getImageFileDir(), id).getAbsolutePath();
        return path;
    }


    /**
     * 录音保存的地址
     *
     * @return
     */
    public static String getRecordPathByCurrentTime() {
        return new File(getVoiceFileDir(), "record_" + System.currentTimeMillis()).getAbsolutePath() + ".amr";
    }

    /**
     * 拍照保存的地址
     *
     * @return
     */
    public static String getPicturePathByCurrentTime() {
        String path = new File(getImageFileDir(), "p_" + System.currentTimeMillis() + ".jpg").getAbsolutePath();
        return path;
    }

    /**
     * 检查SD卡是否存在
     */
    public static boolean checkSdCard() {
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED))
            return true;
        else
            return false;
    }

    /**
     * 检测是否是APK文件
     *
     * @param apkFilePath
     * @return
     */
    public static boolean checkApkFile(Context context, String apkFilePath) {
        boolean result = false;
        try {
            PackageManager pManager = context.getPackageManager();
            PackageInfo pInfo = pManager.getPackageArchiveInfo(apkFilePath, PackageManager.GET_ACTIVITIES);
            if (pInfo == null) {
                result = false;
            } else {
                result = true;
            }
        } catch (Exception e) {
            result = false;
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 安装APK文件
     *
     * @param apkFile
     */
    public static void install(Context context, File apkFile) {
        Uri uri = Uri.fromFile(apkFile);
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setDataAndType(uri, "application/vnd.android.package-archive");
        context.startActivity(intent);
    }

    public static File makeDir(String path) {
        File file = new File(getAvailableCacheDir(), path);
        return checkAndMkdirs(file);
    }

    public static File makeCacheDir(String path) {
        File file = new File(getCacheDir(), path);
        return checkAndMkdirs(file);
    }


    public static File getTemplateFileDir() {
        File file = makeDir("tmp");
        return file;
    }


    public static File getDownPathByCurrentTime() {
        File appDir = PathUtils.getTemplateFileDir();
        String fileName = System.currentTimeMillis() + "";
        return new File(appDir.getPath() + File.separator + fileName);
    }


    public static void deleteFile(String path) {
        File file = new File(path);
        if (file.exists())
            file.delete();
    }


    public static boolean writeFileFromIS(final File file,
                                          final InputStream is) {
        OutputStream os = null;
        try {
            os = new BufferedOutputStream(new FileOutputStream(file));
            byte data[] = new byte[8192];
            int len;
            while ((len = is.read(data, 0, 8192)) != -1) {
                os.write(data, 0, len);
            }
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (os != null) {
                    os.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
