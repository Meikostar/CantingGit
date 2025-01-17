/*
 * Copyright (C) 2010-2017 Alibaba Group Holding Limited.
 */

package com.zhongchuang.canting.allive.editor.editor;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.zhongchuang.canting.R;

import java.util.ArrayList;
import java.util.List;

public class ColorViewHolder extends ColorViewPagerAdapter.ViewHolder {
    private GridView mGridView;
    private Context mContext;
    private boolean mIsStroke;
    private int mInitColor;
    private OnItemClickListener mItemClickListener;
    private SparseArray<GradientDrawable> mGradientDrawCache = new SparseArray<>();
    private SparseArray<ColorDrawable> mColorDrawCache = new SparseArray<>();

    public ColorViewHolder(Context context,
                           String title,
                           boolean isStroke,
                           int initColor) {
        super(context, title);
        this.mContext = context;
        this.mIsStroke = isStroke;
        this.mInitColor = initColor;
    }

    @Override
    protected View onCreateView(Context context) {
        View rootView = LayoutInflater.from(context).inflate( R.layout.aliyun_svideo_layout_color_tab_content, null, false);
        mGridView = rootView.findViewById( R.id.grid_view);
        return rootView;
    }

    @Override
    protected void onBindViewHolder() {
        ColorAdapter colorAdapter = new ColorAdapter();
        colorAdapter.setData(initColors(mIsStroke, mInitColor));
        mGridView.setAdapter(colorAdapter);
    }

    private class ColorAdapter extends BaseAdapter {

        public List<ColorItem> list = new ArrayList<>();

        public void setData(List<ColorItem> data) {
            if (data == null || data.size() == 0) {
                return;
            }
            list.addAll(data);
            notifyDataSetChanged();
        }


        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return list.size();
        }

        @Override
        public ColorItem getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return 0;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            ColorItemViewMediator localViewHolder;
            if (convertView == null) {
                localViewHolder = new ColorItemViewMediator(parent);
                convertView = localViewHolder.getView();
                //Log.d("share_menu", "分享菜单的position：" + paramInt);
            } else {
                localViewHolder = (ColorItemViewMediator) convertView.getTag();
            }
            final ColorItem item = getItem(position);

            localViewHolder.setData(item);
            if (mGridView.getCheckedItemPosition() == position) {
                localViewHolder.setSelected(true);
            } else {
                localViewHolder.setSelected(false);
            }

            localViewHolder.setListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    ColorItemViewMediator mediator = (ColorItemViewMediator) v.getTag();
                    ColorItem item = mediator.getData();
                    if (mItemClickListener != null) {
                        mItemClickListener.onItemClick(item);
                    }
                    mGridView.setItemChecked(position, true);

                }
            });

            return convertView;
        }

    }

    private class ColorItemViewMediator {
        private View root;
        private ColorItem _Data;
        private ImageView mIvColor;
        private View select;

        ColorItemViewMediator(ViewGroup parent) {
            root = View.inflate(parent.getContext(),  R.layout.aliyun_svideo_item_qupai_textcolor, null);
            mIvColor = root.findViewById( R.id.iv_color);
            select = root.findViewById( R.id.selected);

            root.setTag(this);
        }

        public View getView() {
            return root;
        }

        public ColorItem getData() {
            return _Data;
        }

        public void setListener(View.OnClickListener listener) {
            root.setOnClickListener(listener);
        }

        public void setSelected(boolean selected) {
            select.setVisibility(selected ? View.VISIBLE : View.GONE);
        }

        public void setData(ColorItem item) {
            _Data = item;
            if (item.isStroke) {
                GradientDrawable drawable = mGradientDrawCache.get(item.strokeColor);
                if (drawable == null) {
                    drawable = new GradientDrawable();
                    drawable.setStroke(8, item.strokeColor);
                    drawable.setShape(GradientDrawable.RECTANGLE);
                    mGradientDrawCache.put(item.strokeColor, drawable);
                }
                mIvColor.setImageDrawable(drawable);
            } else {
                ColorDrawable drawable = mColorDrawCache.get(item.color);
                if (drawable == null) {
                    drawable = new ColorDrawable(item.color);
                    drawable.setBounds(0, 0, mIvColor.getMeasuredWidth(), mIvColor.getMeasuredHeight());
                    mColorDrawCache.put(item.color, drawable);
                }
                mIvColor.setImageDrawable(drawable);
            }
        }
    }

    private List<ColorItem> initColors(boolean stroke, int initColor) {
        List<ColorItem> list = new ArrayList<>();
        TypedArray colors = mContext.getResources().obtainTypedArray( R.array.qupai_text_edit_colors);

        for (int i = 0; i < 21; i++) {
            int color = colors.getColor(i, Color.WHITE);
            ColorItem ci = new ColorItem();
            ci.color = color;
            ci.isStroke = stroke;
            list.add(ci);
            if (stroke) {
                ci.strokeColor = color;
            }

        }
        colors.recycle();
        return list;
    }

    public class ColorItem {

        public boolean isStroke;
        public int color;
        public int strokeColor;

    }


    public interface OnItemClickListener {
        void onItemClick(ColorItem item);
    }

    public void setItemClickListener(OnItemClickListener itemClickListener) {
        mItemClickListener = itemClickListener;
    }
}
