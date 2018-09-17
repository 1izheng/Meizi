package com.yjz.meizi.utils;

import android.text.TextUtils;

import com.yjz.meizi.model.Meizi;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.annotations.NonNull;

/**
 * @author lizheng
 * @date created at 2018/9/11 下午5:29
 */
public class JsoupMgr {

    private Document document;

    private JsoupMgr(Document document) {
        this.document = document;
    }

    public static JsoupMgr get(@NonNull Document document) {
        return new JsoupMgr(document);
    }


    public List<Meizi> getList(int type) {
        switch (type) {
            case Urls.TYPE_DOUBAN:
                return getDoubanMeizi();
            case Urls.TYPE_MEIZITU:
                return getMeizitu();
            case Urls.TYPE_KANMEIZI:
                return getDoubanMeizi();
            default:
                return new ArrayList<>();
        }
    }


    public List<Meizi> getDoubanMeizi() {
        List<Meizi> listModels = new ArrayList<>();
        Meizi imageListModel;
        Elements a = document.select("div.img_single").select("a");
        for (Element element : a) {
            imageListModel = new Meizi();
            imageListModel.setUrl(element.select("img[class]").attr("src"));
            listModels.add(imageListModel);
        }
        return listModels;
    }

    public List<Meizi> getMeizitu() {
        List<Meizi> listModels = new ArrayList<>();
        Meizi imageListModel;
        Elements elements = document.select("div.entry-thumb").select("a");
        L.d("##########--->", elements.size() + "---elements.size()");
        for (Element element : elements) {
            imageListModel = new Meizi();
            String url = element.select("img").attr("src");
            if(TextUtils.isEmpty(url)){
                continue;
            }

            L.d("##########--->", url);
            imageListModel.setUrl(url);
            listModels.add(imageListModel);
        }
        return listModels;
    }

}
