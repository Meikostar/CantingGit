package com.zhongchuang.canting.adapter;

import android.content.Context;
import android.graphics.Color;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zhongchuang.canting.R;
import com.zhongchuang.canting.been.Message;


/**
 * Created by WZH on 2016/12/25.
 */

public class MessageAdapter extends BaseListAdapter<Message> {
    public MessageAdapter(Context ctx) {
        super(ctx);
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder ;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = inflater.inflate(R.layout.item_message, null, false);
            viewHolder.level = convertView.findViewById(R.id.level);
            viewHolder.message = convertView.findViewById(R.id.message);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Message message = datas.get(position);
        viewHolder.level.setText("["+message.name+"] :");
        viewHolder.message.setText(message.message);
        SpannableStringBuilder builder = new SpannableStringBuilder(viewHolder.message.getText().toString());
        //ForegroundColorSpan 为文字前景色，BackgroundColorSpan为文字背景色
        ForegroundColorSpan yellowSpan  = new ForegroundColorSpan(Color.YELLOW);
        ForegroundColorSpan whiteSpan = new ForegroundColorSpan(Color.WHITE);
//        builder.setSpan(yellowSpan, 0, message.name.length()+1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//        builder.setSpan(whiteSpan, message.name.length()+1, builder.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
//        viewHolder.message.setText(builder);
        return  convertView ;
    }
    public class ViewHolder{
        TextView level ;
        TextView message ;
    }
}
