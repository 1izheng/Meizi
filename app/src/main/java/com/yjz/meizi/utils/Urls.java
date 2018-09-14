package com.yjz.meizi.utils;

import com.yjz.meizi.R;

/**
 * @author lizheng
 * @date created at 2018/9/11 下午5:44
 */
public class Urls {

    public static final int TYPE_DOUBAN = 0;
    public static final int TYPE_MEIZITU = 1;
    public static final int TYPE_GANK = 2;


    public static final String DOUBAN = "https://www.dbmeinv.com/index.htm?cid=";
    public static final String DOUBAN_APPEND = "&pager_offset=";

    public static final String DOUBAN_DXM = "https://www.dbmeinv.com/index.htm?cid=2&pager_offset=";

    public static final String DOUBAN_MTK = "https://www.dbmeinv.com/index.htm?cid=3&pager_offset=";

    public static final String DOUBAN_YYZ = "https://www.dbmeinv.com/index.htm?cid=4&pager_offset=";

    public static final String DOUBAN_DZH = "https://www.dbmeinv.com/index.htm?cid=5&pager_offset=";

    public static final String DOUBAN_XQT = "https://www.dbmeinv.com/index.htm?cid=6&pager_offset=";

    public static final String DOUBAN_HSW = "https://www.dbmeinv.com/index.htm?cid=7&pager_offset=";


    public static final String MEIZITU = "http://m.xxxiao.com/new/page/";


    public static String[] getLoadUrlTitles(int type) {
        switch (type) {
            case TYPE_DOUBAN:
                return Utils.getContext().getResources().getStringArray(R.array.dbmz_array);
            case TYPE_MEIZITU:
                return Utils.getContext().getResources().getStringArray(R.array.meizitu_array);
            case TYPE_GANK:
                return Utils.getContext().getResources().getStringArray(R.array.gank_io_array);
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
            default:
                return "";
        }
    }
}
