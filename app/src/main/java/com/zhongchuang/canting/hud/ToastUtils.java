package com.zhongchuang.canting.hud;

import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.zhongchuang.canting.R;

import java.lang.ref.WeakReference;

/**
 * Created by Administrator on 2017/8/25.
 */

public final class ToastUtils {

    private static final int     DEFAULT_COLOR = 0x12000000;
    private static final Handler sHandler      = new Handler(Looper.getMainLooper());
    private static Toast sToast;
    private static WeakReference<View> sViewWeakReference;
    private static int gravity         = Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM;
    private static int xOffset         = 0;
    private static int yOffset         ;
    private static int backgroundColor = DEFAULT_COLOR;
    private static int bgResource      = -1;
    private static int messageColor    = DEFAULT_COLOR;

    private static Application application;

    public static Application init(Application context){
        application = context;
        yOffset         = (int) (64 * application.getResources().getDisplayMetrics().density + 0.5);
        return application;
    }

    private ToastUtils() {
    }

    /**
     * 设置吐司位置
     *
     * @param gravity 位置
     * @param xOffset x偏移
     * @param yOffset y偏移
     */
    public static void setGravity(final int gravity, final int xOffset, final int yOffset) {
        ToastUtils.gravity = gravity;
        ToastUtils.xOffset = xOffset;
        ToastUtils.yOffset = yOffset;
    }

    /**
     * 设置吐司view
     *
     * @param layoutId 视图
     */
    public static void setView(@LayoutRes final int layoutId) {
        LayoutInflater inflate = (LayoutInflater) application.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        sViewWeakReference = new WeakReference<>(inflate.inflate(layoutId, null));
    }

    /**
     * 设置吐司view
     *
     * @param view 视图
     */
    public static void setView(final View view) {
        sViewWeakReference = view == null ? null : new WeakReference<>(view);
    }

    /**
     * 获取吐司view
     *
     * @return view
     */
    public static View getView() {
        if (sViewWeakReference != null) {
            final View view = sViewWeakReference.get();
            if (view != null) {
                return view;
            }
        }
        if (sToast != null) return sToast.getView();
        return null;
    }

    /**
     * 设置背景颜色
     *
     * @param backgroundColor 背景色
     */
    public static void setBgColor(@ColorInt final int backgroundColor) {
        ToastUtils.backgroundColor = backgroundColor;
    }

    /**
     * 设置背景资源
     *
     * @param bgResource 背景资源
     */
    public static void setBgResource(@DrawableRes final int bgResource) {
        ToastUtils.bgResource = bgResource;
    }

    /**
     * 设置消息颜色
     *
     * @param messageColor 颜色
     */
    public static void setMessageColor(@ColorInt final int messageColor) {
        ToastUtils.messageColor = messageColor;
    }

    /**
     * 安全地显示短时吐司
     *
     * @param text 文本
     */
    public static void showShortSafe(@NonNull final CharSequence text) {
        sHandler.post(new Runnable() {
            @Override
            public void run() {
                show(text, Toast.LENGTH_SHORT);
            }
        });
    }

    /**
     * 安全地显示短时吐司
     *
     * @param resId 资源Id
     */
    public static void showShortSafe(@StringRes final int resId) {
        sHandler.post(new Runnable() {
            @Override
            public void run() {
                show(resId, Toast.LENGTH_SHORT);
            }
        });
    }

    /**
     * 安全地显示短时吐司
     *
     * @param resId 资源Id
     * @param args  参数
     */
    public static void showShortSafe(@StringRes final int resId, final Object... args) {
        sHandler.post(new Runnable() {
            @Override
            public void run() {
                show(resId, Toast.LENGTH_SHORT, args);
            }
        });
    }

    /**
     * 安全地显示短时吐司
     *
     * @param format 格式
     * @param args   参数
     */
    public static void showShortSafe(final String format, final Object... args) {
        sHandler.post(new Runnable() {
            @Override
            public void run() {
                show(format, Toast.LENGTH_SHORT, args);
            }
        });
    }

    /**
     * 安全地显示长时吐司
     *
     * @param text 文本
     */
    public static void showLongSafe(@NonNull final CharSequence text) {
        sHandler.post(new Runnable() {
            @Override
            public void run() {
                show(text, Toast.LENGTH_LONG);
            }
        });
    }

    /**
     * 安全地显示长时吐司
     *
     * @param resId 资源Id
     */
    public static void showLongSafe(@StringRes final int resId) {
        sHandler.post(new Runnable() {
            @Override
            public void run() {
                show(resId, Toast.LENGTH_LONG);
            }
        });
    }

    /**
     * 安全地显示长时吐司
     *
     * @param resId 资源Id
     * @param args  参数
     */
    public static void showLongSafe(@StringRes final int resId, final Object... args) {
        sHandler.post(new Runnable() {
            @Override
            public void run() {
                show(resId, Toast.LENGTH_LONG, args);
            }
        });
    }

    /**
     * 安全地显示长时吐司
     *
     * @param format 格式
     * @param args   参数
     */
    public static void showLongSafe(final String format, final Object... args) {
        sHandler.post(new Runnable() {
            @Override
            public void run() {
                show(format, Toast.LENGTH_LONG, args);
            }
        });
    }

    /**
     * 显示短时吐司
     *
     * @param text 文本
     */
    public static void showShort(@NonNull final CharSequence text) {
        show(text, Toast.LENGTH_SHORT);
    }

    /**
     * 显示短时吐司
     *
     * @param resId 资源Id
     */
    public static void showShort(@StringRes final int resId) {
        show(resId, Toast.LENGTH_SHORT);
    }

    /**
     * 显示短时吐司
     *
     * @param resId 资源Id
     * @param args  参数
     */
    public static void showShort(@StringRes final int resId, final Object... args) {
        show(resId, Toast.LENGTH_SHORT, args);
    }

    /**
     * 显示短时吐司
     *
     * @param format 格式
     * @param args   参数
     */
    public static void showShort(final String format, final Object... args) {
        show(format, Toast.LENGTH_SHORT, args);
    }

    /**
     * 显示长时吐司
     *
     * @param text 文本
     */
    public static void showLong(@NonNull final CharSequence text) {
        show(text, Toast.LENGTH_LONG);
    }

    /**
     * 显示长时吐司
     *
     * @param resId 资源Id
     */
    public static void showLong(@StringRes final int resId) {
        show(resId, Toast.LENGTH_LONG);
    }

    /**
     * 显示长时吐司
     *
     * @param resId 资源Id
     * @param args  参数
     */
    public static void showLong(@StringRes final int resId, final Object... args) {
        show(resId, Toast.LENGTH_LONG, args);
    }

    /**
     * 显示长时吐司
     *
     * @param format 格式
     * @param args   参数
     */
    public static void showLong(final String format, final Object... args) {
        show(format, Toast.LENGTH_LONG, args);
    }

    /**
     * 安全地显示短时自定义吐司
     */
    public static void showCustomShortSafe(@LayoutRes final int layoutId) {
        sHandler.post(new Runnable() {
            @Override
            public void run() {
                setView(layoutId);
                show("", Toast.LENGTH_SHORT);
            }
        });
    }

    /**
     * 安全地显示长时自定义吐司
     */
    public static void showCustomLongSafe(@LayoutRes final int layoutId) {
        sHandler.post(new Runnable() {
            @Override
            public void run() {
                setView(layoutId);
                show("", Toast.LENGTH_LONG);
            }
        });
    }

    /**
     * 显示短时自定义吐司
     */
    public static void showCustomShort(@LayoutRes final int layoutId) {
        setView(layoutId);
        show("", Toast.LENGTH_SHORT);
    }

    /**
     * 显示长时自定义吐司
     */
    public static void showCustomLong(@LayoutRes final int layoutId) {
        setView(layoutId);
        show("", Toast.LENGTH_LONG);
    }

    /**
     * 安全地显示短时自定义吐司
     */
    public static void showCustomShortSafe(@NonNull final View view) {
        sHandler.post(new Runnable() {
            @Override
            public void run() {
                setView(view);
                show("", Toast.LENGTH_SHORT);
            }
        });
    }

    /**
     * 安全地显示长时自定义吐司
     */
    public static void showCustomLongSafe(String msg, @NonNull final View view) {
        sHandler.post(new Runnable() {
            @Override
            public void run() {
                setView(view);
                show("", Toast.LENGTH_LONG);
            }
        });
    }


    /**
     * 安全地显示短时自定义吐司
     */
    public static void showCustomShortSafe(final String msg, final int resId) {
        sHandler.post(new Runnable() {
            @Override
            public void run() {
                setView(R.layout.supertoast);
                show(msg, resId, Toast.LENGTH_SHORT);
            }
        });
    }

    /**
     * 安全地显示长时自定义吐司
     */
    public static void showCustomLongSafe(final String msg, final int resId) {
        sHandler.post(new Runnable() {
            @Override
            public void run() {
                setView(R.layout.supertoast);
                show(msg, resId, Toast.LENGTH_SHORT);
            }
        });
    }



    /**
     * 显示短时自定义吐司
     */
    public static void showCustomShort(@NonNull final View view) {
        setView(view);
        show("", Toast.LENGTH_SHORT);
    }

    /**
     * 显示长时自定义吐司
     */
    public static void showCustomLong(@NonNull final View view) {
        setView(view);
        show("", Toast.LENGTH_LONG);
    }

    /**
     * 显示吐司
     *
     * @param resId    资源Id
     * @param duration 显示时长
     */
    private static void show(@StringRes final int resId, final int duration) {
        show(application.getResources().getText(resId).toString(), duration);
    }

    /**
     * 显示吐司
     *
     * @param resId    资源Id
     * @param duration 显示时长
     * @param args     参数
     */
    private static void show(@StringRes final int resId, final int duration, final Object... args) {
        show(String.format(application.getResources().getString(resId), args), duration);
    }

    /**
     * 显示吐司
     *
     * @param format   格式
     * @param duration 显示时长
     * @param args     参数
     */
    private static void show(final String format, final int duration, final Object... args) {
        show(String.format(format, args), duration);
    }

    /**
     * 显示吐司
     *
     * @param text     文本
     * @param duration 显示时长
     */
    private static void show(final CharSequence text, final int duration) {
        cancelToast();
        boolean isCustom = false;
        if (sViewWeakReference != null) {
            final View view = sViewWeakReference.get();
            if (view != null) {
                sToast = new Toast(application);
                sToast.setView(view);
                sToast.setDuration(duration);
                isCustom = true;
            }
        }
        if (!isCustom) {
            if (messageColor != DEFAULT_COLOR) {
                SpannableString spannableString = new SpannableString(text);
                ForegroundColorSpan colorSpan = new ForegroundColorSpan(messageColor);
                spannableString.setSpan(colorSpan, 0, spannableString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                sToast = Toast.makeText(application, spannableString, duration);
            } else {
                sToast = Toast.makeText(application, text, duration);
            }
        }
        View view = sToast.getView();
        if (bgResource != -1) {
            view.setBackgroundResource(bgResource);
        } else if (backgroundColor != DEFAULT_COLOR) {
            view.setBackgroundColor(backgroundColor);
        }
        sToast.setGravity(gravity, xOffset, yOffset);
        sToast.show();
    }

    /**
     * 显示吐司
     *
     * @param text     文本
     * @param duration 显示时长
     */
    private static void show(CharSequence text, int resId, int duration) {
        cancelToast();
        boolean isCustom = false;
        if (sViewWeakReference != null) {
            final View view = sViewWeakReference.get();
            if (view != null) {
                TextView tv = view.findViewById(R.id.message);
                if (resId!=-1){
                    ImageView imge = view.findViewById(R.id.border);
                    imge.setVisibility(View.VISIBLE);
                    imge.setImageResource(resId);
                }
                tv.setText(text);

                sToast = new Toast(application);
                sToast.setView(view);
                sToast.setDuration(duration);
                isCustom = true;
            }
        }
        if (!isCustom) {
            if (messageColor != DEFAULT_COLOR) {
                SpannableString spannableString = new SpannableString(text);
                ForegroundColorSpan colorSpan = new ForegroundColorSpan(messageColor);
                spannableString.setSpan(colorSpan, 0, spannableString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                sToast = Toast.makeText(application, spannableString, duration);
            } else {
                sToast = Toast.makeText(application, text, duration);
            }
        }
        View view = sToast.getView();
        if (bgResource != -1) {
            view.setBackgroundResource(bgResource);
        } else if (backgroundColor != DEFAULT_COLOR) {
            view.setBackgroundColor(backgroundColor);
        }
        sToast.setGravity(gravity, xOffset, yOffset);
        sToast.show();
    }

    /**
     * 取消吐司显示
     */
    private static void cancelToast() {
        if (sToast != null) {
            sToast.cancel();
            sToast = null;
//            resetToast();
        }
    }

    /**
     * 取消吐司显示
     */
    public static void cancel() {
        if (sToast != null) {
            sToast.cancel();
            sToast = null;
//            resetToast();
        }
    }

    public static void resetToast() {
        ToastUtils.setMessageColor(0x12000000);
        ToastUtils.setBgResource(-1);
        ToastUtils.setView(null);
        ToastUtils.setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, application.getResources().getDimensionPixelSize(R.dimen.offset_64));
    }

    public static void showNormalToast(String msg){
        resetToast();
        showShortSafe(msg);
    }

    public static void showEnterMsgImage(String msg,int resId){
        ToastUtils.resetToast();
        ToastUtils.setGravity(Gravity.CENTER,0,0);
        ToastUtils.showCustomShortSafe(msg,resId);
    }
}
