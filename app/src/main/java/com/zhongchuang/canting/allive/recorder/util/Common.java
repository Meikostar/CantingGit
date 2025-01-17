/*
 * Copyright (C) 2010-2017 Alibaba Group Holding Limited.
 */

package com.zhongchuang.canting.allive.recorder.util;

import android.content.Context;
import android.util.Log;

import com.aliyun.common.logger.Logger;
import com.aliyun.common.utils.StorageUtils;
import com.aliyun.jasonparse.JSONSupport;
import com.aliyun.jasonparse.JSONSupportImpl;
import com.aliyun.struct.form.PasterForm;
import com.aliyun.struct.form.ResourceForm;
import com.liulishuo.filedownloader.util.FileDownloadUtils;
import com.zhongchuang.canting.allive.downloader.DownloaderManager;
import com.zhongchuang.canting.allive.downloader.FileDownloaderModel;
import com.zhongchuang.canting.allive.editor.http.EffectService;
import com.zhongchuang.canting.app.CanTingAppLication;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;


public class Common {
//    public final static String SD_DIR = Environment.getExternalStorageDirectory().getPath()
//            + "/";

    public static String SD_DIR= StorageUtils.getCacheDirectory(CanTingAppLication.getInstance()).getAbsolutePath() + File.separator;
    //    public static final String BASE_URL = "http://m.api.inner.alibaba.net";
    public static final String BASE_URL = "https://m-api.qupaicloud.com";   //外网地址（正式环境）TODO:上线前要干掉

    public final static String QU_NAME = "live";
    public static String QU_DIR ;
    static private void copyFileToSD(Context cxt, String src, String dst) throws IOException
    {
        InputStream myInput;
        OutputStream myOutput = new FileOutputStream(dst);
        myInput = cxt.getAssets().open(src);
        byte[] buffer = new byte[1024];
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
    static public void copySelf(Context cxt, String root) {
        try {
            String[] files = cxt.getAssets().list(root);
            if(files.length > 0) {
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
                Logger.getDefaultLogger().d("copy...."+Common.SD_DIR+root);
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

    static public void copyAll(Context cxt) {
        SD_DIR = StorageUtils.getCacheDirectory(cxt).getAbsolutePath() + File.separator;
        QU_DIR = SD_DIR + QU_NAME + File.separator;
//        File dir = new File(Common.QU_DIR);
//        copySelf(cxt,QU_NAME);
//        dir.mkdirs();
//        unZip();

    }

    public static void unZip() {
        SD_DIR = StorageUtils.getCacheDirectory(CanTingAppLication.getInstance()).getAbsolutePath() + File.separator;
        QU_DIR = SD_DIR + QU_NAME + File.separator;
        File[] files = new File(Common.SD_DIR + QU_NAME).listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name != null && name.endsWith(".zip");
            }
        });
        if(files!=null&&files.length>0){
            for(final File file : files) {
                int len = file.getAbsolutePath().length();
                insertDB(file.getAbsolutePath().substring(0, len - 4));
                if (!new File(file.getAbsolutePath().substring(0, len - 4)).exists()) {
                    try {
                        UnZipFolder(file.getAbsolutePath(), Common.SD_DIR + QU_NAME);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }

    }

    public final static String QU_MV = "aliyun_svideo_mv";
    public final static String QU_CAPTION = "aliyun_svideo_caption";
    public final static String QU_OVERLAY = "aliyun_svideo_overlay";


    private final static String MV1_1 = "folder1.1";
    private final static String MV3_4 = "folder3.4";
    private final static String MV4_3 = "folder4.3";
    private final static String MV9_16 = "folder9.16";
    private final static String MV16_9 = "folder16.9";
    private static void insertDB(String name) {
        if(name.endsWith(QU_MV)) {
            insertMV();
        } else if (name.endsWith(QU_CAPTION)) {
            insertCaption();
        } else if (name.endsWith(QU_OVERLAY)) {
            insertOverlay();
        }
    }
    public static void insertMV() {
        File file = new File(QU_DIR, QU_MV);
        if(file.exists() && file.isDirectory()) {
            String path = "";
            File[] files = file.listFiles();
            if(files == null) {
                return;
            }

            for(File fs : files) {
                if(fs.exists() && fs.isDirectory()) {
                    String name = fs.getName();
                    File[] filesTemp = fs.listFiles();
                    if(filesTemp == null) {
                        return;
                    }
                    int id = 103;
                    for(File fileTemp : filesTemp) {
                        FileDownloaderModel model = new FileDownloaderModel();
                        model.setEffectType(EffectService.EFFECT_MV);
                        model.setName(name);
                        model.setId(id);
                        model.setPath(fs.getAbsolutePath());
                        if(path == null || "".equals(path)) {
                            path = fileTemp.getAbsolutePath() + File.separator + "icon.png";
                        }
                        model.setPreviewpic(path);
                        model.setIcon(path);
                        String pathTemp = fileTemp.getAbsolutePath();
                        if(pathTemp.endsWith(MV1_1)) {
                            model.setAspect(1);
                        } else if(pathTemp.endsWith(MV3_4) || pathTemp.endsWith(MV4_3)) {
                            model.setAspect(2);
                        } else if(pathTemp.endsWith(MV9_16) || pathTemp.endsWith(MV16_9)) {
                            model.setAspect(3);
                        }
                        model.setIsunzip(1);
                        model.setTaskId(FileDownloadUtils.generateId(String.valueOf(model.getAspect()), pathTemp));
                        HashMap<String, String> hashMap = new HashMap<>();
                        hashMap.put(FileDownloaderModel.TASK_ID, String.valueOf(FileDownloadUtils.generateId(String.valueOf(model.getAspect()), pathTemp)));
                        hashMap.put(FileDownloaderModel.ID, String.valueOf(model.getId()));
                        hashMap.put(FileDownloaderModel.ASPECT, String.valueOf(model.getAspect()));
                        DownloaderManager.getInstance().getDbController().insertDb(model, hashMap);
                    }
                }
            }
        }
    }

    public static void insertCaption() {
        File file = new File(QU_DIR, QU_CAPTION);
        if(file.exists() && file.isDirectory()) {
            File[] files = file.listFiles();
            if(files == null) {
                return;
            }
            for(File fs : files) {
                FileDownloaderModel model = new FileDownloaderModel();
                model.setEffectType(EffectService.EFFECT_CAPTION);
                model.setId(166);
                model.setIcon(fs.getAbsolutePath() + File.separator + "icon.png");
                model.setSubicon(fs.getAbsolutePath() + File.separator + "icon.png");
                model.setIsunzip(1);
                model.setName(fs.getName());
                model.setPath(fs.getAbsolutePath());
                model.setUrl(fs.getAbsolutePath());
                model.setTaskId(FileDownloadUtils.generateId(String.valueOf(model.getEffectType()), fs.getAbsolutePath()));

                HashMap<String, String> hashMap = new HashMap<>();
                hashMap.put(FileDownloaderModel.ID, String.valueOf(model.getId()));
                hashMap.put(FileDownloaderModel.TASK_ID, String.valueOf(model.getTaskId()));
                DownloaderManager.getInstance().getDbController().insertDb(model, hashMap);
            }
        }
    }

    public static void insertOverlay() {
        File file = new File(QU_DIR, QU_OVERLAY);
        JSONSupport jsonSupport = new JSONSupportImpl();
        ResourceForm paster = null;
        if(file.exists() && file.isDirectory()) {
            File[] files = file.listFiles();
            for(File fs : files) {
                if(fs.exists() && !fs.isDirectory()) {
                    try {
                        paster = jsonSupport.readValue(fs, ResourceForm.class);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                if(paster != null) {
                    List<PasterForm> mPasterlList = paster.getPasterList();
                    if(mPasterlList == null) {
                        return;
                    }
                    String icon = "";
                    for(PasterForm pasterForm : mPasterlList) {
                        FileDownloaderModel model = new FileDownloaderModel();
                        model.setId(paster.getId());
                        model.setPath(QU_DIR + QU_OVERLAY + File.separator + pasterForm.getName());

                        //        if(icon == null || "".equals(icon)) {
                        icon = model.getPath() + "/icon.png";
                        //        }
                        model.setIcon(icon);
                        model.setDescription(paster.getDescription());
                        model.setIsnew(paster.getIsNew());
                        model.setName(paster.getName());
                        model.setLevel(paster.getLevel());
                        model.setEffectType(EffectService.EFFECT_PASTER);

                        model.setSubid(pasterForm.getId());
                        model.setFontid(pasterForm.getFontId());
                        model.setSubicon(icon);
                        model.setSubname(pasterForm.getName());
                        model.setIsunzip(1);

                        model.setTaskId(FileDownloadUtils.generateId(String.valueOf(pasterForm.getId()), model.getPath()));

                        HashMap<String, String> hashMap = new HashMap<>();
                        hashMap.put(FileDownloaderModel.SUBID, String.valueOf(pasterForm.getId()));
                        hashMap.put(FileDownloaderModel.TASK_ID, String.valueOf(model.getTaskId()));
                        DownloaderManager.getInstance().getDbController().insertDb(model, hashMap);
                    }
                }
            }
        }
    }
    public static void UnZipFolder(String zipFileString, String outPathString) throws Exception {
        ZipInputStream inZip = new ZipInputStream(new FileInputStream(zipFileString));
        ZipEntry zipEntry;
        String szName = "";
        while ((zipEntry = inZip.getNextEntry()) != null) {
            szName = zipEntry.getName();
            if (zipEntry.isDirectory()) {
                // get the folder name of the widget
                szName = szName.substring(0, szName.length() - 1);
                File folder = new File(outPathString + File.separator + szName);
                folder.mkdirs();
            } else {

                File file = new File(outPathString + File.separator + szName);
                file.createNewFile();
                // get the output stream of the file
                FileOutputStream out = new FileOutputStream(file);
                int len;
                byte[] buffer = new byte[1024];
                // read (len) bytes into buffer
                while ((len = inZip.read(buffer)) != -1) {
                    // write (len) byte from buffer at the position 0
                    out.write(buffer, 0, len);
                    out.flush();
                }
                out.close();
            }
        }
        inZip.close();
    }
}
