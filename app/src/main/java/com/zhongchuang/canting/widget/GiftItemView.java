package com.zhongchuang.canting.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.os.Handler;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.zhongchuang.canting.R;
import com.zhongchuang.canting.been.Gift;


/**
 * author：Administrator on 2016/12/27 09:34
 * description:文件说明
 * version:版本
 */
public class GiftItemView extends LinearLayout {

    private ImageView avatar ;
    private TextView name ;
    private TextView giftName ;
    private TextView giftNumTv ;
    private ImageView giftIv ;
    private Gift gift ;

    private int giftNum = 0 ;
    private boolean isShow = false ;

    public GiftItemView(Context context) {
        this(context,null);
    }

    public GiftItemView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public GiftItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setOrientation(VERTICAL);
        setVisibility(INVISIBLE);
        LayoutParams lp = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        setLayoutParams(lp);
        View convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_gift_message,null,false);
        avatar = convertView.findViewById(R.id.avatar);
        giftIv = convertView.findViewById(R.id.gift_type);
        name = convertView.findViewById(R.id.name);
        giftName = convertView.findViewById(R.id.gift_name);
        giftNumTv = convertView.findViewById(R.id.gift_num);
        addView(convertView);
    }

    public void setGift(Gift gift) {
        this.gift = gift;
        refreshView();
    }

    /**
     * 设置礼物数量放大和复原的View
     * @param view
     * @param duration
     */
    public void scaleView(View view,long duration){
        AnimatorSet animatorSet = new AnimatorSet();//组合动画
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(view, "scaleX", 2f, 1f);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(view, "scaleY", 2f, 1f);
        animatorSet.setDuration(duration);
        animatorSet.setInterpolator(new LinearInterpolator());
        animatorSet.play(scaleY).with(scaleX);//两个动画同时开始
        animatorSet.start();
        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                if (onAnimatorListener!=null){
                    onAnimatorListener.onAnimationEnd(gift);
                }
            }

            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                if (onAnimatorListener!=null){
                    onAnimatorListener.onAnimationStart(animation);
                }
            }
        });
    }

    /**
     * 刷新view
     */
    public void refreshView(){
        if (gift==null){
            return;
        }
//        giftNum = gift.num ;
        if (!TextUtils.isEmpty(gift.send_img)){
            Glide.with(getContext()).load(gift.send_img).into(avatar);
        }
        if(!TextUtils.isEmpty(gift.giftname)){
            name.setText(gift.giftname+getContext().getString(R.string.gnslyg));
        }

//        giftName.setText(gift.giftname);
        giftNumTv.setText("x"+giftNum);
        Glide.with(getContext()).load(gift.gift_image).into(giftIv);
//        giftIv.setImageResource(gift.giftType);
        scaleView(giftNumTv,200);
    }

    /**
     * 连续点击送礼物的时候数字缩放效果
     * @param num
     */
    public void addNum(int num){
        giftNum += num ;
        giftNumTv.setText("x"+giftNum);
        scaleView(giftNumTv,200);
        handler.removeCallbacks(runnable);
        if (!isShow()){
            show();
        }
        handler.postDelayed(runnable, 2000);
    }
    Handler handler=new Handler();
    Runnable runnable=new Runnable() {
        @Override
        public void run() {
            isShow = false ;
            giftNum = 0;
            setVisibility(INVISIBLE);
        }
    };

    /**
     * 显示view，并开启定时器
     */
    public void show(){
        isShow = true ;
        setVisibility(VISIBLE);
        handler.postDelayed(runnable, 3000);
    }

    public boolean isShow() {
        return isShow;
    }

    private OnAnimatorListener onAnimatorListener ;

    public void setOnAnimatorListener(OnAnimatorListener onAnimatorListener) {
        this.onAnimatorListener = onAnimatorListener;
    }

    public interface OnAnimatorListener{
        void onAnimationEnd(Gift gift);
        void onAnimationStart(Animator animation);
    }
}
