package com.zhongchuang.canting.allive.pusher.ui.myview;

import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import com.alivc.live.pusher.WaterMarkInfo;
import com.zhongchuang.canting.R;
import com.zhongchuang.canting.allive.pusher.utils.Common;

import java.util.ArrayList;
import java.util.List;

public class PushWaterMarkDialog extends DialogFragment {


    private Switch mSwitch1;
    private EditText mX1;
    private TextView sure;
    private EditText mY1;
    private EditText mW1;

    private List<WaterMarkInfo> mWaterMarkInfos;
    private WaterMarkInfo mWaterMarkInfo = new WaterMarkInfo();
    private WaterMarkInfo mWaterMarkInfo1 = new WaterMarkInfo();
    private WaterMarkInfo mWaterMarkInfo2 = new WaterMarkInfo();
    private boolean mWater = true;
    private boolean mWater1 = true;
    private boolean mWater2 = true;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getDialog().getWindow().getAttributes().windowAnimations = R.style.dialog_animation;
        getDialog().setCanceledOnTouchOutside(true);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        View view = inflater.inflate(R.layout.push_watermark, container);

        mX1 = view.findViewById(R.id.x1);
        sure = view.findViewById(R.id.sure);
        mY1 = view.findViewById(R.id.y1);
        mW1 = view.findViewById(R.id.w1);

        mSwitch1 = view.findViewById(R.id.watermark1);
        mSwitch1.setOnCheckedChangeListener(onCheckedChangeListener);
        sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        return view;
    }

    @Override
    public void onResume() {
        getDialog().getWindow().setGravity(Gravity.BOTTOM);
        super.onResume();

        DisplayMetrics dpMetrics = new DisplayMetrics();
        getActivity().getWindow().getWindowManager().getDefaultDisplay().getMetrics(dpMetrics);
        WindowManager.LayoutParams p = getDialog().getWindow().getAttributes();

        p.width = dpMetrics.widthPixels;
        p.height = dpMetrics.heightPixels * 1/3;
        getDialog().getWindow().setAttributes(p);
    }
   public interface OnDisMissListener{
        void dismiss(List<WaterMarkInfo> mWaterMarkInfos);
   }
   public OnDisMissListener listener;

    public void setDismissListener(OnDisMissListener listener){
        this.listener=listener;
    }
    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);

        if(!mX1.getText().toString().isEmpty()) {
            if(mWaterMarkInfo1 != null) {
                mWaterMarkInfo1.mWaterMarkCoordX = Float.valueOf(mX1.getText().toString());
            }
        } else {
            if(mWaterMarkInfo1 != null) {
                mWaterMarkInfo1.mWaterMarkCoordX = 0.1f;
            }
        }

        if(!mY1.getText().toString().isEmpty()) {
            if(mWaterMarkInfo1 != null) {
                mWaterMarkInfo1.mWaterMarkCoordY = Float.valueOf(mY1.getText().toString());
            }
        } else {
            if(mWaterMarkInfo1 != null) {
                mWaterMarkInfo1.mWaterMarkCoordY = 0.3f;
            }
        }

        if(!mW1.getText().toString().isEmpty()) {
            if(mWaterMarkInfo1 != null) {
                mWaterMarkInfo1.mWaterMarkWidth = Float.valueOf(mW1.getText().toString());
            }
        } else {
            if(mWaterMarkInfo1 != null) {
                mWaterMarkInfo1.mWaterMarkWidth = 0.1f;
            }
        }




        mWaterMarkInfo1.mWaterMarkPath = Common.waterMark;

        if(mWaterMarkInfos.size() > 0) {
            mWaterMarkInfos.clear();
        }

        mWaterMarkInfos.add(mWaterMarkInfo1);
        listener.dismiss(mWaterMarkInfos);
    }

    public void setWaterMarkInfo(List<WaterMarkInfo> waterMarkInfos) {
        this.mWaterMarkInfos = waterMarkInfos;
    }

    private CompoundButton.OnCheckedChangeListener onCheckedChangeListener = new CompoundButton.OnCheckedChangeListener() {

        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
            int id = compoundButton.getId();

        }
    };
}
