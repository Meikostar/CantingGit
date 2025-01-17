package com.zhongchuang.canting.easeui.widget.chatrow;

import android.content.Context;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hyphenate.chat.EMMessage;
import com.zhongchuang.canting.R;
import com.zhongchuang.canting.easeui.EaseConstant;
import com.zhongchuang.canting.easeui.EaseUI;
import com.zhongchuang.canting.easeui.domain.EaseEmojicon;

/**
 * big emoji icons
 *
 */
public class EaseChatRowBigExpression extends EaseChatRowText {

    private ImageView imageView;


    public EaseChatRowBigExpression(Context context,int chatType, EMMessage message, int position, BaseAdapter adapter) {
        super(context,chatType, message, position, adapter);
    }
    
    @Override
    protected void onInflateView(int chatType) {
        inflater.inflate(message.direct() == EMMessage.Direct.RECEIVE ? 
                R.layout.ease_row_received_bigexpression : R.layout.ease_row_sent_bigexpression, this);
    }

    @Override
    protected void onFindViewById() {
        percentageView = findViewById(R.id.percentage);
        imageView = findViewById(R.id.image);
    }


    @Override
    public void onSetUpView() {
        String emojiconId = message.getStringAttribute(EaseConstant.MESSAGE_ATTR_EXPRESSION_ID, null);
        EaseEmojicon emojicon = null;
        if(EaseUI.getInstance().getEmojiconInfoProvider() != null){
            emojicon =  EaseUI.getInstance().getEmojiconInfoProvider().getEmojiconInfo(emojiconId);
        }
        if(emojicon != null){
            if(emojicon.getBigIcon() != 0){
                Glide.with(activity).load(emojicon.getBigIcon()).placeholder(R.drawable.ease_default_expression).into(imageView);
            }else if(emojicon.getBigIconPath() != null){
                Glide.with(activity).load(emojicon.getBigIconPath()).placeholder(R.drawable.ease_default_expression).into(imageView);
            }else{
                imageView.setImageResource(R.drawable.ease_default_expression);
            }
        }
    }
}
