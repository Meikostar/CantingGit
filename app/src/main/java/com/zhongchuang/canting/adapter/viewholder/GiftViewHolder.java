package com.zhongchuang.canting.adapter.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhongchuang.canting.R;


/**
 * Created by mykar on 17/4/12.
 */
public class GiftViewHolder extends RecyclerView.ViewHolder {
    public TextView name;
    public TextView tvMoney;
    public TextView tvTime;
    public ImageView img;
    public TextView tvName;




    public GiftViewHolder(View itemView) {
        super(itemView);
        img = itemView.findViewById(R.id.iv_img);
        tvName = itemView.findViewById(R.id.tv_name);
        tvMoney = itemView.findViewById(R.id.tv_money);
        tvTime = itemView.findViewById(R.id.tv_time);



    }
}
