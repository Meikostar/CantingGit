package com.zhongchuang.canting.fragment.mall;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.malinskiy.superrecyclerview.OnMoreListener;
import com.malinskiy.superrecyclerview.SuperRecyclerView;
import com.zhongchuang.canting.R;
import com.zhongchuang.canting.activity.mall.ShopMallDetailActivity;
import com.zhongchuang.canting.adapter.recycle.GoodRecyAdapter;
import com.zhongchuang.canting.base.BaseFragment;
import com.zhongchuang.canting.been.Product;
import com.zhongchuang.canting.been.SubscriptionBean;
import com.zhongchuang.canting.presenter.BaseContract;
import com.zhongchuang.canting.presenter.BasesPresenter;
import com.zhongchuang.canting.utils.TextUtil;
import com.zhongchuang.canting.widget.DivItemDecoration;
import com.zhongchuang.canting.widget.RxBus;
import com.zhongchuang.canting.widget.loadingView.LoadingPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import rx.Subscription;
import rx.functions.Action1;

/**
 * Created by Administrator on 2017/11/8.
 */

@SuppressLint("ValidFragment")
public class GoodSpecialFragment extends BaseFragment implements BaseContract.View {

    @BindView(R.id.super_recycle_view)
    SuperRecyclerView mSuperRecyclerView;
    Unbinder unbinder;
    @BindView(R.id.loadingView)
    LoadingPager loadingView;

    private GoodRecyAdapter adapter;
    private BasesPresenter presenter;
    private Context mContext;

    public GoodSpecialFragment() {
    }

    public GoodSpecialFragment(Context context) {
        this.mContext = context;
    }

    private int type = 1;
    private int status = 1;
    private String content;

    public void setType(int type, String content, int status) {
        this.type = type;
        this.content = content;
        this.status = status;
    }

    private LinearLayoutManager layoutManager;

    private SwipeRefreshLayout.OnRefreshListener refreshListener;
    private final int TYPE_PULL_REFRESH = 1;
    private final int TYPE_PULL_MORE = 2;
    private int currpage = 1;//第几页

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.good_fragment, container, false);
        unbinder = ButterKnife.bind(this, view);


        layoutManager = new LinearLayoutManager(getActivity());
        mSuperRecyclerView.setLayoutManager(layoutManager);
        mSuperRecyclerView.addItemDecoration(new DivItemDecoration(2, true));

        mSuperRecyclerView.getMoreProgressView().getLayoutParams().width = ViewGroup.LayoutParams.MATCH_PARENT;
        presenter = new BasesPresenter(this);
        adapter = new GoodRecyAdapter(getActivity());
        mSuperRecyclerView.setAdapter(adapter);
        refreshListener = new SwipeRefreshLayout.OnRefreshListener() {

            @Override
            public void onRefresh() {
                //  mSuperRecyclerView.showMoreProgress();

                presenter.getActivityProductList(TYPE_PULL_REFRESH, 1 + "", cout + "", TextUtil.isEmpty(content) ? "" : content, "" + status, "" + "2");

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (mSuperRecyclerView != null) {
                            mSuperRecyclerView.hideMoreProgress();
                        }


                    }
                }, 2000);

            }
        };

        mSuperRecyclerView.setRefreshListener(refreshListener);
        mSubscription = RxBus.getInstance().toObserverable(SubscriptionBean.RxBusSendBean.class).subscribe(new Action1<SubscriptionBean.RxBusSendBean>() {
            @Override
            public void call(SubscriptionBean.RxBusSendBean bean) {
                if (bean == null) return;


            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                throwable.printStackTrace();
            }
        });
        RxBus.getInstance().addSubscription(mSubscription);
        loadingView.showPager(LoadingPager.STATE_LOADING);
        itemClick();
        reflash();

        return view;
    }

    private Subscription mSubscription;

    private void reflash() {
        if (mSuperRecyclerView != null) {
            //实现自动下拉刷新功能
            mSuperRecyclerView.getSwipeToRefresh().post(new Runnable() {
                @Override
                public void run() {
                    mSuperRecyclerView.setRefreshing(true);//执行下拉刷新的动画
                    refreshListener.onRefresh();//执行数据加载操作
                }
            });
        }
    }

    private List<Product> datas = new ArrayList<>();
    private int cout = 12;

    public void onDataLoaded(int loadType, final boolean haveNext, List<Product> list) {

        if (loadType == TYPE_PULL_REFRESH) {
            currpage = 1;
            datas.clear();
            for (Product info : list) {
                datas.add(info);
            }
        } else {
            for (Product info : list) {
                datas.add(info);
            }
        }
        if(datas!=null&&datas.size()!=0){

            loadingView.showPager(LoadingPager.STATE_SUCCEED);

        } else {

            loadingView.setContent(getString(R.string.myssdxgsp));
            loadingView.showPager(LoadingPager.STATE_EMPTY);

        }
        adapter.setData(datas);
        adapter.notifyDataSetChanged();


        mSuperRecyclerView.hideMoreProgress();

        if (haveNext) {
            mSuperRecyclerView.setupMoreListener(new OnMoreListener() {
                @Override
                public void onMoreAsked(int overallItemsCount, int itemsBeforeMore, int maxLastVisiblePosition) {
                    currpage++;
                    mSuperRecyclerView.showMoreProgress();

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if (haveNext)
                                presenter.getActivityProductList(TYPE_PULL_MORE, currpage + "", cout + "", TextUtil.isEmpty(content) ? "" : content, "" + status, "" + "2");




                        }
                    }, 2000);
                }
            }, 1);
        } else {
            mSuperRecyclerView.removeMoreListener();
            mSuperRecyclerView.hideMoreProgress();

        }


    }

    private void itemClick() {
        //条目点击事件的操作
        //TODO
        adapter.setItemCikcListener(new GoodRecyAdapter.ItemClikcListener() {
            @Override
            public void itemClick(String data, String type, int poition) {
                Intent intent = new Intent(getActivity(), ShopMallDetailActivity.class);
                intent.putExtra("id", data);
                intent.putExtra("type", type);
                startActivity(intent);
            }
        });


    }

    @Override
    public void onStop() {
        super.onStop();
        hidePress();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        if (mSubscription != null) {
            mSubscription.unsubscribe();
        }
    }


    @Override
    public <T> void toEntity(T entity, int type) {
        List<Product> data = (List<Product>) entity;

        if(data==null){
            adapter.notifyDataSetChanged();
            return;
        }

        onDataLoaded(type, cout == data.size(), data);


    }

    @Override
    public void toNextStep(int type) {

    }

    @Override
    public void showTomast(String msg) {
        adapter.notifyDataSetChanged();

        loadingView.setContent(getString(R.string.zwsj) );
        loadingView.showPager(LoadingPager.STATE_EMPTY);
    }
}
