/*
 * Copyright (C) 2010-2017 Alibaba Group Holding Limited.
 */

package com.zhongchuang.canting.allive.downloader;

import android.content.ContentValues;
import android.database.Cursor;

import java.io.Serializable;
import java.util.Map;

public class FileDownloaderModel implements Serializable {
    public final static String TASK_ID = "task_id";
    public final static String ID = "id";
    public final static String NAME = "name";
    public final static String URL = "url";
    public final static String PATH = "path";
    public final static String ISUNZIP = "isunzip";

    /**
     * imv
     */
    public final static String EFFECTTYPE = "effectType";
    public final static String KEY = "key";
    public final static String SUBEFFECTYPT = "subqueffectype";
    public final static String LEVEL = "level";
    public final static String TAG = "tag";
    public final static String CAT = "cat";
    public final static String PREVIEWPIC = "previewpic";
    public final static String PREVIEWMP4 = "previewmp4";
    public final static String DURATION = "duration";
    public final static String TYPE = "type";
    public final static String SORT = "sort";

    //imv 列表信息
    public final static String ASPECT = "aspect";
    public final static String DOWNLOAD = "download";
    public final static String MD5 = "md5";

    /**
     * 字体
     */
    public final static String CNNAME = "cnname";
//    public final static String LEVEL = "level";
    public final static String CATEGORY = "category";
//    public final static String MD5 = "md5";
    public final static String BANNER = "banner";
    public final static String ICON = "icon";
//    public final static String SORT = "sort";

    /**
     * 动图、字幕动图
     */
    public final static String DESCRIPTION = "description";
    public final static String ISNEW = "isnew";
    public final static String PREVIEW = "preview";

    //素材信息
    public final static String SUBID = "subid";
    public final static String FONTID = "fontid";
    public final static String SUBICON = "subicon";
    public final static String SUBNAME = "subname";
    public final static String PRIORITY = "priority";
    public final static String SUBPREVIEW = "subpreview";
    public final static String SUBSORT = "subsort";
    public final static String SUBTYPE = "subtype";


    /**
     * 滤镜、音乐上述字段即可满足
     */

    private int task_id;
    private int id;
    private String name;
    private String url;
    private String path;
    private int isunzip; // 0 不需要解压 1 需要解压

    /**
     * imv
     */
    private int effectType;
    private int subqueffectype;
    private String key;
    private int level;
    private String tag;
    private int cat;
    private String previewpic;
    private String previewmp4;
    private long duration;
    private int type;
    private int sort;

    //imv 列表信息
    private int aspect;
    private String download;
    private String md5;

    /**
     * 字体
     */
    private String cnname;
    //    public final static String LEVEL = "level";
    private int category;
    //    public final static String MD5 = "md5";
    private String banner;
    private String icon;
//    public final static String SORT = "sort";

    /**
     * 动图、字幕动图
     */
    private String description;
    private int isnew;
    private String preview;

    //素材信息
    private int subid;
    private int fontid;
    private String subicon;
    private String subname;
    private int priority;
    private String subpreview;
    private int subsort;
    private int subtype;

    //扩展字段键值对
    private ContentValues extFieldCv = new ContentValues();

    public int getTaskId() {
        return task_id;
    }

    public void setTaskId(int task_id) {
        this.task_id = task_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }


    public int getSubtype() {
        return subtype;
    }

    public void setSubtype(int subtype) {
        this.subtype = subtype;
    }

    public int getEffectType() {
        return effectType;
    }

    public void setEffectType(int effectType) {
        this.effectType = effectType;
    }

//    public int getSubqueffectype() {
//        return subqueffectype;
//    }
//
//    public void setSubqueffectype(int subqueffectype) {
//        this.subqueffectype = subqueffectype;
//    }


    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public int getCat() {
        return cat;
    }

    public void setCat(int cat) {
        this.cat = cat;
    }

    public String getPreviewpic() {
        return previewpic;
    }

    public void setPreviewpic(String previewpic) {
        this.previewpic = previewpic;
    }

    public String getPreviewmp4() {
        return previewmp4;
    }

    public void setPreviewmp4(String previewmp4) {
        this.previewmp4 = previewmp4;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

//    public int getType() {
//        return type;
//    }
//
//    public void setType(int type) {
//        this.type = type;
//    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public int getAspect() {
        return aspect;
    }

    public void setAspect(int aspect) {
        this.aspect = aspect;
    }

    public String getDownload() {
        return download;
    }

    public void setDownload(String download) {
        this.download = download;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    public String getCnname() {
        return cnname;
    }

    public void setCnname(String cnname) {
        this.cnname = cnname;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public String getBanner() {
        return banner;
    }

    public void setBanner(String banner) {
        this.banner = banner;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getIsnew() {
        return isnew;
    }

    public void setIsnew(int isnew) {
        this.isnew = isnew;
    }

    public String getPreview() {
        return preview;
    }

    public void setPreview(String preview) {
        this.preview = preview;
    }

    public int getSubid() {
        return subid;
    }

    public void setSubid(int subid) {
        this.subid = subid;
    }

    public int getFontid() {
        return fontid;
    }

    public void setFontid(int fontid) {
        this.fontid = fontid;
    }

    public String getSubicon() {
        return subicon;
    }

    public void setSubicon(String subicon) {
        this.subicon = subicon;
    }

    public String getSubname() {
        return subname;
    }

    public void setSubname(String subname) {
        this.subname = subname;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public String getSubpreview() {
        return subpreview;
    }

    public void setSubpreview(String subpreview) {
        this.subpreview = subpreview;
    }

    public int getSubsort() {
        return subsort;
    }

    public void setSubsort(int subsort) {
        this.subsort = subsort;
    }

    public int isunzip() {
        return isunzip;
    }

    public void setIsunzip(int isunzip) {
        this.isunzip = isunzip;
    }

    public ContentValues toContentValues() {
        ContentValues cv = new ContentValues();
        cv.put(TASK_ID, task_id);
        cv.put(ID, id);
        cv.put(NAME, name);
        cv.put(URL, url);
        cv.put(PATH, path);
        cv.put(ISUNZIP, isunzip);
        cv.put(EFFECTTYPE, effectType);
//        cv.put(SUBEFFECTYPT, subqueffectype);
        cv.put(KEY, key);
        cv.put(LEVEL, level);
        cv.put(TAG, tag);
        cv.put(CAT, cat);
        cv.put(PREVIEWPIC, previewpic);
        cv.put(PREVIEWMP4, previewmp4);
        cv.put(DURATION, duration);
//        cv.put(TYPE, type);
        cv.put(SORT, sort);
        cv.put(ASPECT, aspect);
        cv.put(DOWNLOAD, download);
        cv.put(MD5, md5);
        cv.put(CNNAME, cnname);
        cv.put(CATEGORY, category);
        cv.put(BANNER, banner);
        cv.put(ICON, icon);
        cv.put(DESCRIPTION, description);
        cv.put(ISNEW, isnew);
        cv.put(PREVIEW, preview);
        cv.put(SUBID, subid);
        cv.put(FONTID, fontid);
        cv.put(SUBICON, subicon);
        cv.put(SUBNAME, subname);
        cv.put(PRIORITY, priority);
        cv.put(SUBPREVIEW, subpreview);
        cv.put(SUBSORT, subsort);
        cv.put(SUBTYPE, subtype);
        Map<String, String> extFieldMap = DownloaderManager.getInstance().getDbExtFieldMap();
        if (extFieldMap == null || extFieldMap.size() == 0) {
            return cv;
        }
        for (Map.Entry<String, String> entry : extFieldMap.entrySet()) {
            String key = entry.getKey();
            if ( key == null ) {
                continue;
            }

            String value = extFieldCv.getAsString(key);
            cv.put(key, value);
        }
        return cv;
    }

    public void putExtField(String key, String value) {
        if ( key == null ) {
            return;
        }
        if(value == null){
            value = "";
        }
        extFieldCv.put(key, value);
    }

    public String getExtFieldValue(String key) {
        return extFieldCv.getAsString(key);
    }

    void parseExtField(Cursor cursor) {
        if (cursor == null || cursor.isClosed()) {
            return;
        }
        Map<String, String> extFieldMap = DownloaderManager.getInstance().getDbExtFieldMap();
        if (extFieldMap == null || extFieldMap.size() == 0) {
            return;
        }
        for (Map.Entry<String, String> entry : extFieldMap.entrySet()) {
            String key = entry.getKey();
            if ( key == null ) {
                continue;
            }
            String value = cursor.getString(cursor.getColumnIndex(key));
            extFieldCv.put(key, value);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof FileDownloaderModel) {
            FileDownloaderModel model = (FileDownloaderModel) o;
            return model != null && model.getTaskId() == getTaskId();
        }
        return false;
    }
}
