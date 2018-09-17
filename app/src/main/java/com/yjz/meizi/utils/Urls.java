package com.yjz.meizi.utils;

import com.yjz.meizi.R;

import java.util.Locale;

/**
 * @author lizheng
 * @date created at 2018/9/11 下午5:44
 */
public class Urls {

    public static final int TYPE_DOUBAN = 0;
    public static final int TYPE_MEIZITU = 1;
    public static final int TYPE_GANK = 2;
    public static final int TYPE_KANMEIZI = 3;


    public static final String DOUBAN = "https://www.dbmeinv.com/index.htm?cid=";
    public static final String DOUBAN_APPEND = "&pager_offset=";

    public static final String MEIZITU = "http://m.xxxiao.com/new/page/";

    public static final String KANMEIZI = "http://www.kanmeizi.cn/tag_%1$d_%2$d_16.html";


    public static String[] getLoadUrlTitles(int type) {
        switch (type) {
            case TYPE_DOUBAN:
                return Utils.getContext().getResources().getStringArray(R.array.dbmz_array);
            case TYPE_MEIZITU:
                return Utils.getContext().getResources().getStringArray(R.array.meizitu_array);
            case TYPE_GANK:
                return Utils.getContext().getResources().getStringArray(R.array.gank_io_array);
            case TYPE_KANMEIZI:
                return Utils.getContext().getResources().getStringArray(R.array.kanmeizi_array);
            default:
                return new String[]{};
        }
    }

    /**
     * 取地址
     *
     * @param type
     * @param tabPosition
     * @param page
     * @return
     */
    public static String getLoadUrl(int type, int tabPosition, int page) {
        switch (type) {
            case TYPE_DOUBAN:
                return DOUBAN + (tabPosition + 2) + DOUBAN_APPEND + page;
            case TYPE_MEIZITU:
                return MEIZITU + page;
            case TYPE_KANMEIZI:
                return String.format(Locale.CHINA, KANMEIZI, (tabPosition + 1), page);
            default:
                return "";
        }
    }
}
