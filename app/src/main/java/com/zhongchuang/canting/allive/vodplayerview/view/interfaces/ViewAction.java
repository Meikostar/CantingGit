package com.zhongchuang.canting.allive.vodplayerview.view.interfaces;

import com.zhongchuang.canting.allive.vodplayerview.widget.AliyunScreenMode;

/*
 * Copyright (C) 2010-2018 Alibaba Group Holding Limited.
 */

/**
 * 定义UI界面通用的操作。
 * 主要实现类有{@link com.zhongchuang.canting.allive.vodplayerview.view.control.ControlView} ,
 * {@link com.zhongchuang.canting.allive.vodplayerview.view.gesture.GestureView}
 */

public interface ViewAction {

    /**
     * 隐藏类型
     */
    enum HideType {
        /**
         * 正常情况下的隐藏
         */
        Normal,
        /**
         * 播放结束的隐藏，比如出错了
         */
        End
    }

    /**
     * 重置
     */
    void reset();

    /**
     * 显示
     */
    void show();

    /**
     * 隐藏
     *
     * @param hideType 隐藏类型
     */
    void hide(HideType hideType);

    /**
     * 设置屏幕全屏情况
     *
     * @param mode {@link AliyunScreenMode#Small}：小屏. {@link AliyunScreenMode#Full}:全屏
     */
    void setScreenModeStatus(AliyunScreenMode mode);
    
}
