/*
 * Copyright (C) 2010-2017 Alibaba Group Holding Limited.
 */

package com.zhongchuang.canting.allive.quview;


import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;

public class CircularImageView extends View {

    public CircularImageView(Context context) {
        this(context, null);
    }

    public CircularImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircularImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        _Drawable.setCallback(this);

        TypedArray a = context.obtainStyledAttributes(attrs, com.zhongchuang.canting.R.styleable.QuViewCircularImageView, defStyle, 0);

        Drawable drawable = a.getDrawable(com.zhongchuang.canting.R.styleable.QuViewCircularImageView_android_src);

        a.recycle();

        if (drawable != null) {
            setImageDrawable(drawable);
        }
    }

    private final CircularImageDrawable _Drawable = new CircularImageDrawable();

    @Override
    protected
    boolean verifyDrawable(Drawable who) {
        return who == _Drawable || super.verifyDrawable(who);
    }


    public void setImageBitmap(Bitmap bitmap) {
        _Drawable.setBitmap(bitmap);
    }

    public void setImageDrawable(Drawable drawable) {
        if(drawable==null){
            setImageBitmap(null);
        }else{
            setImageBitmap(((BitmapDrawable) drawable).getBitmap());
        }

    }

    public void setImageResource(int res) {
        setImageDrawable(getResources().getDrawable(res));
    }

    @Override
    protected
    void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

        _Drawable.setBounds(left + getPaddingLeft(), top + getPaddingTop(), right - getPaddingRight(), bottom - getPaddingBottom());
    }

    @Override
    protected
    void onDraw(Canvas canvas) {
        canvas.save();

        canvas.translate(getPaddingLeft(), getPaddingTop());

        _Drawable.draw(canvas);

        canvas.restore();
    }

}
