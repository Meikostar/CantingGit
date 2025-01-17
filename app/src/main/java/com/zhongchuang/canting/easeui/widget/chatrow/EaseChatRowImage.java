package com.zhongchuang.canting.easeui.widget.chatrow;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.os.AsyncTaskCompat;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMFileMessageBody;
import com.hyphenate.chat.EMImageMessageBody;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMMessageBody;
import com.zhongchuang.canting.R;
import com.zhongchuang.canting.activity.chat.SendDynamicActivity;
import com.zhongchuang.canting.easeui.model.EaseImageCache;
import com.zhongchuang.canting.easeui.ui.EaseShowBigImageActivity;
import com.zhongchuang.canting.easeui.utils.EaseImageUtils;
import com.zhongchuang.canting.easeui.widget.EaseImageView;
import com.zhongchuang.canting.utils.DensityUtil;
import com.zhongchuang.canting.utils.StringUtil;
import com.zhongchuang.canting.utils.TextUtil;
import com.zhongchuang.canting.widget.CircleTransform;

import java.io.File;
public class EaseChatRowImage extends EaseChatRowFile {

    protected ImageView imageView;

    protected RelativeLayout bubble;
    protected RelativeLayout rl_bbg;
    protected TextView tv_reback;
    protected TextView tv_userid;
    private EMImageMessageBody imgBody;

    public EaseChatRowImage(Context context, int chatType,EMMessage message, int position, BaseAdapter adapter) {
        super(context,chatType, message, position, adapter);
    }

    @Override
    protected void onInflateView(int chatType) {
        inflater.inflate(message.direct() == EMMessage.Direct.RECEIVE ? R.layout.ease_row_received_picture : R.layout.ease_row_sent_picture, this);
    }

    @Override
    protected void onFindViewById() {
        percentageView = findViewById(R.id.percentage);
        imageView = findViewById(R.id.image);
        bubble = findViewById(R.id.bubble);
        tv_reback = findViewById(R.id.tv_reback);
        tv_userid = findViewById(R.id.tv_userid);
        rl_bbg = findViewById(R.id.rl_bbg);
    }
   private String path;

    @Override
    protected void onSetUpView() {
        EMMessageBody body = message.getBody();
        String content = message.getStringAttribute(EXETEND, "");
        if(TextUtil.isEmpty(content)){
            if(tv_userid!=null){
                if(message.getChatType()== EMMessage.ChatType.GroupChat){
                    tv_userid.setVisibility(VISIBLE);
                }else {
                    tv_userid.setVisibility(GONE);
                }

            }

        }else {
            if(tv_userid!=null){
                tv_userid.setVisibility(GONE);
            }

        }
        if(body instanceof EMImageMessageBody){
            imgBody= (EMImageMessageBody) body;


            // received messages
            if (message.direct() == EMMessage.Direct.RECEIVE) {
                if (imgBody.thumbnailDownloadStatus() == EMFileMessageBody.EMDownloadStatus.DOWNLOADING ||
                        imgBody.thumbnailDownloadStatus() == EMFileMessageBody.EMDownloadStatus.PENDING) {
                    imageView.setImageResource(R.drawable.ease_default_image);
                    String thumbPath = imgBody.getThumbnailUrl();
                    String remoteUrl = imgBody.getRemoteUrl();
                    if(TextUtil.isNotEmpty(remoteUrl)){
//                    Glide.with(context).load(StringUtil.changeUrl(remoteUrl)).asBitmap().placeholder(R.drawable.ease_default_image).into(imageView);
                        path=remoteUrl;
                    }else {
                        path= imgBody.getLocalUrl();
//                    showImageView(thumbPath, imgBody.getLocalUrl(), message);
                        //set the receive message callback
                        setMessageReceiveCallback();
                    }

                } else {
                    progressBar.setVisibility(View.GONE);
                    percentageView.setVisibility(View.GONE);
                    imageView.setImageResource(R.drawable.ease_default_image);
                    String remoteUrl = imgBody.getRemoteUrl();
                    if(TextUtil.isNotEmpty(remoteUrl)){
//                    Glide.with(context).load(StringUtil.changeUrl(remoteUrl)).asBitmap().placeholder(R.drawable.ease_default_image).into(imageView);

                        path=remoteUrl;
                    }else {
                        path= imgBody.getLocalUrl();
//                    showImageView(thumbPath, imgBody.getLocalUrl(), message);
                        //set the receive message callback
                        setMessageReceiveCallback();
                    }
//                if(TextUtil.isNotEmpty(remoteUrl)){
//                    Glide.with(context).load(StringUtil.changeUrl(remoteUrl)).asBitmap().placeholder(R.drawable.ease_default_image).into(imageView);
//
//                }else {
//                    String thumbPath = imgBody.getRemoteUrl();
//                    if (!new File(thumbPath).exists()) {
//                        // to make it compatible with thumbnail received in previous version
//                        thumbPath = EaseImageUtils.getThumbnailImagePath(imgBody.getLocalUrl());
//                    }
//                    showImageView(thumbPath, imgBody.getLocalUrl(), message);
//                }

                }

            }else {
                String remoteUrl = imgBody.getRemoteUrl();
                if(TextUtil.isNotEmpty(remoteUrl)){
//                    Glide.with(context).load(StringUtil.changeUrl(remoteUrl)).asBitmap().placeholder(R.drawable.ease_default_image).into(imageView);

                    path=remoteUrl;
                }else {
                    path= imgBody.getLocalUrl();
//                    showImageView(thumbPath, imgBody.getLocalUrl(), message);
                    //set the receive message callback
                    setMessageReceiveCallback();
                }
                handleSendMessage();
//            if(TextUtil.isNotEmpty(remoteUrl)){
//
//
//            }else {
//                String thumbPath = imgBody.getRemoteUrl();
//                if (!new File(thumbPath).exists()) {
//                    // to make it compatible with thumbnail received in previous version
//                    thumbPath = EaseImageUtils.getThumbnailImagePath(imgBody.getLocalUrl());
//                }
//                showImageView(thumbPath, imgBody.getLocalUrl(), message);
//            }


            }
            Glide.with(context).load(StringUtil.changeUrl(path)).asBitmap().into(new SimpleTarget<Bitmap>() {
                @Override
                public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                    int width = DensityUtil.dip2px(context, 150);
                    double w=(resource.getWidth())*1.0;
                    int h=resource.getHeight();
                    double fx=width/w;
                    int height= (int) (h*fx);

                    RelativeLayout.LayoutParams params1=new RelativeLayout.LayoutParams(DensityUtil.dip2px(context, 150),height>DensityUtil.dip2px(context, 150)?height-15:height);
                    imageView.setLayoutParams(params1);
                    imageView.setImageBitmap(resource);
                }
            });
            tv_reback.setVisibility(GONE);
            rl_bbg.setVisibility(VISIBLE);
        }else {
            tv_reback.setVisibility(VISIBLE);
            rl_bbg.setVisibility(GONE);
            tv_reback.setText(content);

        }


    }
    public static final String EXETEND = "rb_extend";
    @Override
    protected void onUpdateView() {
        super.onUpdateView();
    }

    @Override
    protected void onBubbleClick() {
        if (imgBody.thumbnailDownloadStatus() == EMFileMessageBody.EMDownloadStatus.DOWNLOADING ||
                imgBody.thumbnailDownloadStatus() == EMFileMessageBody.EMDownloadStatus.PENDING) {
            //thumbnail image downloading
            return;
        } else if(imgBody.thumbnailDownloadStatus() == EMFileMessageBody.EMDownloadStatus.FAILED){
            progressBar.setVisibility(View.VISIBLE);
            percentageView.setVisibility(View.VISIBLE);
            // retry download with click event of user
            EMClient.getInstance().chatManager().downloadThumbnail(message);
        }


        Intent intent = new Intent(context, EaseShowBigImageActivity.class);
        File file = new File(imgBody.getLocalUrl());
        if (file.exists()) {
            Uri uri = Uri.fromFile(file);
            intent.putExtra("uri", uri);
        } else {
            // The local full size pic does not exist yet.
            // ShowBigImage needs to download it from the server
            // first
            String msgId = message.getMsgId();
            intent.putExtra("messageId", msgId);
            intent.putExtra("localUrl", imgBody.getLocalUrl());
        }
        if (message != null && message.direct() == EMMessage.Direct.RECEIVE && !message.isAcked()
                && message.getChatType() == EMMessage.ChatType.Chat) {
            try {
                EMClient.getInstance().chatManager().ackMessageRead(message.getFrom(), message.getMsgId());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        context.startActivity(intent);
    }

    /**
     * load image into image view
     *
     */
    private void showImageView(final String thumbernailPath, final String localFullSizePath,final EMMessage message) {
        // first check if the thumbnail image already loaded into cache
        Bitmap bitmap = EaseImageCache.getInstance().get(thumbernailPath);
        if (bitmap != null) {
            // thumbnail image is already loaded, reuse the drawable
            imageView.setImageBitmap(bitmap);
        } else {
            AsyncTaskCompat.executeParallel( new AsyncTask<Object, Void, Bitmap>() {

                @Override
                protected Bitmap doInBackground(Object... args) {
                    File file = new File(thumbernailPath);
                    if (file.exists()) {
                        return EaseImageUtils.decodeScaleImage(thumbernailPath, 160, 160);
                    } else if (new File(imgBody.thumbnailLocalPath()).exists()) {
                        return EaseImageUtils.decodeScaleImage(imgBody.thumbnailLocalPath(), 160, 160);
                    }
                    else {
                        if (message.direct() == EMMessage.Direct.SEND) {
                            if (localFullSizePath != null && new File(localFullSizePath).exists()) {
                                return EaseImageUtils.decodeScaleImage(localFullSizePath, 160, 160);
                            } else {
                                return null;
                            }
                        } else {
                            return null;
                        }
                    }
                }

                protected void onPostExecute(Bitmap image) {
                    if (image != null) {
                        imageView.setImageBitmap(image);
                        EaseImageCache.getInstance().put(thumbernailPath, image);
                    }
                }
            });
        }
    }

}
