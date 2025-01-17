package com.zhongchuang.canting.allive.pusher.utils;

import android.content.Context;
import android.content.DialogInterface;
import android.content.res.AssetManager;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AlertDialog;

import com.zhongchuang.canting.R;
import com.zhongchuang.canting.allive.pusher.adapter.MusicAdapter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.TimeZone;

public class Common {

    private static String SD_DIR = Environment.getExternalStorageDirectory().getPath() + File.separator;
    private static String RESOURCE_DIR = "alivc_resource";
    private static String filename = RESOURCE_DIR + File.separator + "watermark.png";
    public static final String waterMark = SD_DIR + filename;

    public static void copyAsset(Context context) {
        if(new File(SD_DIR + filename).exists()) {
            return;
        }
        if(SD_DIR != null && new File(SD_DIR).exists()) {

            Boolean isSuccess = true;
            AssetManager assetManager = context.getAssets();

            InputStream in = null;
            OutputStream out = null;
            try {
                in = assetManager.open(filename);
                String newFileName = SD_DIR + filename;
                out = new FileOutputStream(newFileName);

                byte[] buffer = new byte[1024];
                int read;
                while ((read = in.read(buffer)) != -1) {
                    out.write(buffer, 0, read);
                }
                in.close();
                in = null;
                out.flush();
                out.close();
                out = null;
            } catch (Exception e) {
                e.printStackTrace();
                isSuccess = false;
            }
        }
    }

    public static void showDialog(final Context context, final String message) {
        if(context == null || message == null) {
            return;
        }
        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {
                if(context != null) {
                    AlertDialog.Builder dialog = new AlertDialog.Builder(context);
                    dialog.setTitle(context.getString(R.string.dialog_title));
                    dialog.setMessage(message);
                    dialog.setNegativeButton(context.getString(R.string.ok), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                        }
                    });
                    dialog.show();
                }
            }
        });
    }

    public static void copyAll(Context cxt) {
        File dir = new File(Common.SD_DIR);
        copySelf(cxt,"alivc_resource");
        dir.mkdirs();
    }

    public static void copySelf(Context cxt, String root) {
        try {
            String[] files = cxt.getAssets().list(root);
            if(files != null && files.length > 0) {
                File subdir = new File(Common.SD_DIR + root);
                if (!subdir.exists()) {
                    subdir.mkdirs();
                }
                for (String fileName : files) {
                    if (new File(Common.SD_DIR + root + File.separator + fileName).exists()){
                        continue;
                    }
                    copySelf(cxt,root + "/" + fileName);
                }
            }else{
                OutputStream myOutput = new FileOutputStream(Common.SD_DIR+root);
                InputStream myInput = cxt.getAssets().open(root);
                byte[] buffer = new byte[1024 * 8];
                int length = myInput.read(buffer);
                while(length > 0)
                {
                    myOutput.write(buffer, 0, length);
                    length = myInput.read(buffer);
                }

                myOutput.flush();
                myInput.close();
                myOutput.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<MusicAdapter.MusicInfo> getResource() {
        ArrayList<MusicAdapter.MusicInfo> list = new ArrayList<>();
        if(new File(SD_DIR + RESOURCE_DIR).exists()) {

            File[] files = new File(SD_DIR + RESOURCE_DIR).listFiles(new FilenameFilter() {

                @Override
                public boolean accept(File file, String s) {
                    return s.endsWith(".mp3");
                }
            });
            if(files != null && files.length > 0) {
                for(int i = 0; i < files.length; i++) {
                    MusicAdapter.MusicInfo musicInfo = new MusicAdapter.MusicInfo();
                    musicInfo.setMusicName(files[i].getName());
                    musicInfo.setPath(files[i].getAbsolutePath());
                    list.add(musicInfo);
                }
            }
        }
        return list;
    }

    public static String getTime(long ms) {
        SimpleDateFormat formatter = new SimpleDateFormat("mm:ss");
        formatter.setTimeZone(TimeZone.getTimeZone("GMT+00:00"));
        String hms = formatter.format(ms);
        return hms;
    }

}
