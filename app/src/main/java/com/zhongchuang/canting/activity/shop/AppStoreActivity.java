package com.zhongchuang.canting.activity.shop;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.zhongchuang.canting.R;
import com.zhongchuang.canting.activity.WebViewActivity;
import com.zhongchuang.canting.adapter.AppBasedapter;
import com.zhongchuang.canting.adapter.AppBasedapterSmg;
import com.zhongchuang.canting.adapter.Basedapter;
import com.zhongchuang.canting.base.BaseActivity1;
import com.zhongchuang.canting.been.Smgapply;
import com.zhongchuang.canting.been.apply;
import com.zhongchuang.canting.presenter.BaseContract;
import com.zhongchuang.canting.presenter.BasesPresenter;
import com.zhongchuang.canting.widget.NoScrollGridView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AppStoreActivity extends BaseActivity1 implements BaseContract.View {

    @BindView(R.id.tv_search)
    TextView tvSearch;
    @BindView(R.id.iv_back)
    ImageView ivBack;

    @BindView(R.id.listview)
    ListView listview;
    private BasesPresenter presenter;
    private AppBasedapter adapter;
    private AppBasedapterSmg adapterSmg;

    @Override
    public void initViews() {
        setContentView(R.layout.activity_app_store);
        ButterKnife.bind(this);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        presenter = new BasesPresenter(this);

        presenter.appList();
//        presenter.appSmgList();
    }

    @Override
    public void bindEvents() {


    }

    @Override
    public void initData() {

    }


    public void setData() {

    }



    @Override
    public <T> void toEntity(T entity, int type) {
        if(type==8){
            adapterSmg = new AppBasedapterSmg(this);
            listview.setAdapter(adapterSmg);
            List<Smgapply> list= (List<Smgapply>) entity;
            adapterSmg.setData(list);
            adapterSmg.setOnItemClickListener(new AppBasedapterSmg.OnItemClickListener() {
                @Override
                public void onItemClick(Smgapply data) {
                    Intent intent = new Intent(AppStoreActivity.this, WebViewActivity.class);

                    intent.putExtra(WebViewActivity.WEBTITLE, data.application_image_name);
                    intent.putExtra(WebViewActivity.WEBURL, data.url);
                    startActivity(intent   );
                }
            });
        }else {
            adapter = new AppBasedapter(this);
            listview.setAdapter(adapter);
            List<apply> list= (List<apply>) entity;
            adapter.setData(list);
            adapter.setOnItemClickListener(new AppBasedapter.OnItemClickListener() {
                @Override
                public void onItemClick(apply data) {
                    Intent intent = new Intent(AppStoreActivity.this, WebViewActivity.class);

                    intent.putExtra(WebViewActivity.WEBTITLE, data.application_image_name);
                    intent.putExtra(WebViewActivity.WEBURL, data.application_address);
                    startActivity(intent   );
                }
            });
        }

    }

    @Override
    public void toNextStep(int type) {

    }

    @Override
    public void showTomast(String msg) {
        showToasts(msg);
    }


}
