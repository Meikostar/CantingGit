package com.zhongchuang.canting.allive.pusher.data;

import java.util.List;

public class QuestionData {
    String type;
    List<QuestionInfo> contents;

    public String getmType() {
        return type;
    }

    public void setmType(String mType) {
        this.type = mType;
    }

    public List<QuestionInfo> getmContents() {
        return contents;
    }

    public void setmContents(List<QuestionInfo> mContents) {
        this.contents = mContents;
    }
}
