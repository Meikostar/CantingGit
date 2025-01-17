package com.zhongchuang.canting.allive.pusher.utils;

import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.view.Gravity;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;

import com.alivc.live.pusher.AlivcLivePusher;
import com.zhongchuang.canting.allive.pusher.ui.myview.VideoRecordCameraPreviewView;
import com.zhongchuang.canting.allive.pusher.ui.myview.VideoRecordSmallView;

import java.lang.reflect.Field;

public class VideoRecordViewManager {
    private static VideoRecordSmallView mVideoRecordSmallWindow;
    private static LayoutParams mVideoRecordWindowParams;

    private static VideoRecordCameraPreviewView mVideoRecordCameraPreviewWindow;
    public static int cameraRotation = 0;
    private static LayoutParams mVideoRecordCameraWindowParams;

    private static WindowManager mWindowManager;

    public static void createViewoRecordWindow(Context context, AlivcLivePusher pusher, CameraOn listener) {
        WindowManager windowManager = getWindowManager(context);
        int screenWidth = windowManager.getDefaultDisplay().getWidth();
        int screenHeight = windowManager.getDefaultDisplay().getHeight();
        if (mVideoRecordSmallWindow == null) {
            mVideoRecordSmallWindow = new VideoRecordSmallView(context);
            if (mVideoRecordWindowParams == null) {
                mVideoRecordWindowParams = new LayoutParams();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    mVideoRecordWindowParams.type = LayoutParams.TYPE_APPLICATION_OVERLAY;
                }else {
                    mVideoRecordWindowParams.type = LayoutParams.TYPE_PHONE;//.type = WindowManager.LayoutParams.TYPE_PHONE;
                }
                mVideoRecordWindowParams.format = PixelFormat.RGBA_8888;
                mVideoRecordWindowParams.flags = LayoutParams.FLAG_NOT_TOUCH_MODAL |
                        LayoutParams.FLAG_NOT_FOCUSABLE |
                        LayoutParams.FLAG_HARDWARE_ACCELERATED;
                mVideoRecordWindowParams.gravity = Gravity.LEFT | Gravity.TOP;
                mVideoRecordWindowParams.width = VideoRecordSmallView.viewWidth;
                mVideoRecordWindowParams.height = VideoRecordSmallView.viewHeight;
                mVideoRecordWindowParams.x = screenWidth;
                mVideoRecordWindowParams.y = screenHeight*4/5;
            }
            mVideoRecordSmallWindow.setParams(pusher,mVideoRecordWindowParams);
            mVideoRecordSmallWindow.setCameraOnListern(listener);
            windowManager.addView(mVideoRecordSmallWindow, mVideoRecordWindowParams);
        }
    }

    public interface CameraOn {
        void onCameraOn(boolean on);
    }

    public static void hideViewRecordWindow()
    {
        if(mVideoRecordSmallWindow != null)
        {
            mVideoRecordSmallWindow.setVisible(false);
        }
    }

    public static void showViewRecordWindow()
    {
        if(mVideoRecordSmallWindow != null)
        {
            mVideoRecordSmallWindow.setVisible(true);
        }
    }


    public static void removeVideoRecordWindow(Context context) {
        if (mVideoRecordSmallWindow != null) {
            WindowManager windowManager = getWindowManager(context);
            windowManager.removeView(mVideoRecordSmallWindow);
            mVideoRecordSmallWindow = null;
        }
    }

    public static void createViewoRecordCameraWindow(Context context, AlivcLivePusher pusher) {
        WindowManager windowManager = getWindowManager(context);
        int screenWidth = windowManager.getDefaultDisplay().getWidth();
        int screenHeight = windowManager.getDefaultDisplay().getHeight();
        if (mVideoRecordCameraPreviewWindow == null) {
            mVideoRecordCameraPreviewWindow = new VideoRecordCameraPreviewView(context);
            if (mVideoRecordCameraWindowParams == null) {
                mVideoRecordCameraWindowParams = new LayoutParams();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    mVideoRecordCameraWindowParams.type = LayoutParams.TYPE_APPLICATION_OVERLAY;
                }else {
                    mVideoRecordCameraWindowParams.type = LayoutParams.TYPE_PHONE;
                }

                mVideoRecordCameraWindowParams.format = PixelFormat.RGBA_8888;
                mVideoRecordCameraWindowParams.flags = LayoutParams.FLAG_NOT_TOUCH_MODAL
                        | LayoutParams.FLAG_NOT_FOCUSABLE;
                mVideoRecordCameraWindowParams.gravity = Gravity.LEFT | Gravity.TOP;
                mVideoRecordCameraWindowParams.width = VideoRecordCameraPreviewView.viewWidth;
                mVideoRecordCameraWindowParams.height = VideoRecordCameraPreviewView.viewHeight;
                mVideoRecordCameraWindowParams.x = screenWidth-540;
                mVideoRecordCameraWindowParams.y = 0;
            }
            mVideoRecordCameraPreviewWindow.setParams(pusher, mVideoRecordCameraWindowParams);
            windowManager.addView(mVideoRecordCameraPreviewWindow, mVideoRecordCameraWindowParams);
        }
        if(mVideoRecordSmallWindow != null)
        {
            //mVideoRecordSmallWindow.setSurfaceView(mVideoRecordCameraPreviewWindow.getSurfaceView());
        }
    }

    public static void removeVideoRecordCameraWindow(Context context) {
        if (mVideoRecordCameraPreviewWindow != null) {
            WindowManager windowManager = getWindowManager(context);
            windowManager.removeView(mVideoRecordCameraPreviewWindow);
            mVideoRecordCameraPreviewWindow = null;
        }
    }

    private static WindowManager getWindowManager(Context context) {
        if (mWindowManager == null) {
            mWindowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        }
        return mWindowManager;
    }

    public static boolean permission(Context context){
        boolean permission = true;
        if (Build.VERSION.SDK_INT >= 23) {
            try {
                if (!Settings.canDrawOverlays(context)) {
                    Class clazz = Settings.class;
                    Field field = clazz.getDeclaredField("ACTION_MANAGE_OVERLAY_PERMISSION");

                    Intent intent = new Intent(field.get(null).toString());
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.setData(Uri.parse("package:" + context.getPackageName()));
                    context.startActivity(intent);
                    permission = false;
                }
            } catch (NoSuchFieldException e)
            {

            }catch (IllegalAccessException e)
            {

            }
        }
        return permission;
    }

    public static void refreshFloatWindowPosition()
    {
        if(mVideoRecordCameraPreviewWindow != null)
        {
            mVideoRecordCameraPreviewWindow.refreshPosition();
        }
    }

}
