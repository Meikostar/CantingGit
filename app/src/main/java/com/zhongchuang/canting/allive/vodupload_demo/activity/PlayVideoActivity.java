package com.zhongchuang.canting.allive.vodupload_demo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.zhongchuang.canting.R;


public class PlayVideoActivity extends AppCompatActivity implements View.OnClickListener{

    public static final int UPLOAD_TYPE_VODMULTIPLE = 0;
    public static final int UPLOAD_TYPE_SVIDEO = 1;
    public static final int UPLOAD_TYPE_OSSMULTIPE = 2;
    public static final int UPLOAD_TYPE_VODAUTH = 3;

    Button vodSTSMultiUpload;
    Button svideoUpload;
    Button ossMultiUpload;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.upload_main);

        vodSTSMultiUpload  = (Button)findViewById(R.id.vod_sts_multi_upload);
        svideoUpload = (Button) findViewById(R.id.svideo_upload);
        ossMultiUpload = (Button) findViewById(R.id.oss_multi_upload);
        vodSTSMultiUpload.setOnClickListener(this);
        svideoUpload.setOnClickListener(this);
        ossMultiUpload.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.vod_sts_multi_upload){
            Intent intent =new Intent();
            intent.setClass(v.getContext(),GetSTStokenActivity.class);
            intent.putExtra("UploadType",UPLOAD_TYPE_VODMULTIPLE);
            startActivity(intent);
        } else if(v.getId() == R.id.svideo_upload){
            Intent intent =new Intent();
            intent.setClass(v.getContext(),GetSTStokenActivity.class);
            intent.putExtra("UploadType",UPLOAD_TYPE_SVIDEO);
            startActivity(intent);
        }else if(v.getId() == R.id.oss_multi_upload){
            Intent intent =new Intent();
            intent.setClass(v.getContext(),GetSTStokenActivity.class);
            intent.putExtra("UploadType",UPLOAD_TYPE_OSSMULTIPE);
            startActivity(intent);
        }
    }
}
