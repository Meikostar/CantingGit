package com.zhongchuang.canting.widget.waitLoading;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;

import com.zhongchuang.canting.R;


/**
 * Created by zzz40500 on 15/6/15.
 */
public class ShapeLoadingDialog extends Dialog {

    private LoadingView mLoadingView;

    private Builder mBuilder;

    private ShapeLoadingDialog(Builder builder) {
        super(builder.mContext, R.style.custom_dialosg);
        mBuilder = builder;
        setCancelable(mBuilder.mCancelable);
        setCanceledOnTouchOutside(mBuilder.mCanceledOnTouchOutside);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_dialog);

        mLoadingView = findViewById(R.id.loadView);

        mLoadingView.setDelay(mBuilder.mDelay);
        mLoadingView.setLoadingText(mBuilder.mLoadText);

        setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                mLoadingView.setVisibility(View.GONE);
            }
        });
    }
   public void setText(String content){
        if(mLoadingView!=null){
            mLoadingView.setLoadingText(content);
        }

   }
    @Override
    public void show() {
        super.show();
        mLoadingView.setVisibility(View.VISIBLE);
    }

    public Builder getBuilder() {
        return mBuilder;
    }

    public static class Builder{

        private Context mContext;

        private int mDelay = 80;

        private CharSequence mLoadText;

        private boolean mCancelable = true;

        private boolean mCanceledOnTouchOutside = true;

        public Builder(Context context) {
            mContext = context;
        }

        public Builder delay(int delay) {
            mDelay = delay;
            return this;
        }

        public Builder loadText(CharSequence loadText) {
            mLoadText = loadText;
            return this;
        }

        public Builder loadText(int resId) {
            mLoadText = mContext.getString(resId);
            return this;
        }

        public Builder cancelable(boolean cancelable) {
            mCancelable = cancelable;
            mCanceledOnTouchOutside = cancelable;
            return this;
        }

        public Builder canceledOnTouchOutside(boolean canceledOnTouchOutside) {
            mCanceledOnTouchOutside = canceledOnTouchOutside;
            return this;
        }

        public ShapeLoadingDialog build(){
            return new ShapeLoadingDialog(this);
        }

        public ShapeLoadingDialog show(){
            ShapeLoadingDialog dialog = build();
            dialog.show();
            return dialog;
        }
    }
}
