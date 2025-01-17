package com.zhongchuang.canting.allive.vodplayerview.view.tipsview;

import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zhongchuang.canting.R;
import com.zhongchuang.canting.allive.vodplayerview.theme.ITheme;
import com.zhongchuang.canting.allive.vodplayerview.widget.AliyunVodPlayerView;
/*
 * Copyright (C) 2010-2018 Alibaba Group Holding Limited.
 */

/**
 * 错误提示对话框。出错的时候会显示。
 */
public class ErrorView extends RelativeLayout implements ITheme{
    private static final String TAG = ErrorView.class.getSimpleName();
    //错误信息
    private TextView mMsgView;
    //错误码
    private TextView mCodeView;
    //重试的图片
    private View mRetryView;
    //重试的按钮
    private TextView mRetryBtn;


    private OnRetryClickListener mOnRetryClickListener = null;//重试点击事件

    public ErrorView(Context context) {
        super(context);
        init();
    }


    public ErrorView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }


    public ErrorView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        LayoutInflater inflater = (LayoutInflater) getContext()
                .getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        Resources resources = getContext().getResources();

        View view = inflater.inflate(R.layout.alivc_dialog_error, null);

        int viewWidth = resources.getDimensionPixelSize(R.dimen.alivc_dialog_err_width);
        int viewHeight = resources.getDimensionPixelSize(R.dimen.alivc_dialog_err_height);

        LayoutParams params = new LayoutParams(viewWidth, viewHeight);
        addView(view, params);

        mRetryBtn = view.findViewById(R.id.retry_btn);
        mMsgView = view.findViewById(R.id.msg);
        mCodeView = view.findViewById(R.id.code);
        mRetryView = view.findViewById(R.id.retry);
        //重试的点击监听
        mRetryView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnRetryClickListener != null) {
                    mOnRetryClickListener.onRetryClick();
                }
            }
        });

    }

    /**
     * 更新提示文字
     * @param errorCode 错误码
     * @param errorEvent 错误事件
     * @param errMsg 错误码
     */
    public void updateTips(int errorCode, int errorEvent,String errMsg) {
        mMsgView.setText(errMsg);
        mCodeView.setText(getContext().getString(R.string.alivc_error_code) + errorCode + " - " + errorEvent);
    }

    /**
     * 更新主题
     * @param theme 支持的主题
     */
    @Override
    public void setTheme(AliyunVodPlayerView.Theme theme) {
        if(theme == AliyunVodPlayerView.Theme.Blue){
            mRetryView.setBackgroundResource(R.drawable.alivc_rr_bg_blue);
            mRetryBtn.setCompoundDrawablesWithIntrinsicBounds(R.drawable.alivc_refresh_blue,0,0,0);
            mRetryBtn.setTextColor(getResources().getColor(R.color.alivc_blue));
        }else if(theme == AliyunVodPlayerView.Theme.Green){
            mRetryView.setBackgroundResource(R.drawable.alivc_rr_bg_green);
            mRetryBtn.setCompoundDrawablesWithIntrinsicBounds(R.drawable.alivc_refresh_green,0,0,0);
            mRetryBtn.setTextColor(getResources().getColor(R.color.alivc_green));
        }else if(theme == AliyunVodPlayerView.Theme.Orange){
            mRetryView.setBackgroundResource(R.drawable.alivc_rr_bg_orange);
            mRetryBtn.setCompoundDrawablesWithIntrinsicBounds(R.drawable.alivc_refresh_orange,0,0,0);
            mRetryBtn.setTextColor(getResources().getColor(R.color.alivc_orange));
        }else if(theme == AliyunVodPlayerView.Theme.Red){
            mRetryView.setBackgroundResource(R.drawable.alivc_rr_bg_red);
            //这个重试图片是白色。。很尴尬
            mRetryBtn.setCompoundDrawablesWithIntrinsicBounds(R.drawable.alivc_refresh_red,0,0,0);
            mRetryBtn.setTextColor(getResources().getColor(R.color.alivc_red));
        }
    }

    /**
     * 重试的点击事件
     */
    public interface OnRetryClickListener {
        /**
         * 重试按钮点击
         */
        void onRetryClick();
    }

    /**
     * 设置重试点击事件
     * @param l 重试的点击事件
     */
    public void setOnRetryClickListener(OnRetryClickListener l) {
        mOnRetryClickListener = l;
    }
}
