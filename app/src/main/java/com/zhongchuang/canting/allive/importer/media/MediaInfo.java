/*
 * Copyright (C) 2010-2017 Alibaba Group Holding Limited.
 */

package com.zhongchuang.canting.allive.importer.media;

public class MediaInfo {

    public String filePath;
    public String thumbnailPath;
    public String mimeType;
    public String title;
    public long startTime;
    public int duration;
    public int id;
    public long addTime;
    public boolean isSquare;
    public int type;

    @Override
    public boolean equals(Object o) {
        if(o instanceof MediaInfo){
            MediaInfo info = (MediaInfo)o;
            return id == info.id;
        }
        return false;
    }
}
