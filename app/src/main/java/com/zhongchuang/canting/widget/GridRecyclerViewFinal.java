package com.zhongchuang.canting.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.SystemClock;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.zhongchuang.canting.R;

import java.lang.reflect.Constructor;


/**
 * Desction:
 * Author:pengjianbo
 * Date:16/3/7 下午6:40
 */
public class GridRecyclerViewFinal extends RecyclerView implements OnScrollBottomListener{
    /**
     * 加载更多UI
     */
    ILoadMoreView mLoadMoreView;

    /**
     * 加载更多方式，默认滑动到底部加载更多
     */
    LoadMoreMode mLoadMoreMode = LoadMoreMode.SCROLL;
    /**
     * 加载更多lock
     */
    private boolean mLoadMoreLock;
    /**
     * 是否可以加载跟多
     */
    boolean mHasLoadMore;
    /**
     * 是否加载失败
     */
    private boolean mHasLoadFail;

    /**
     * 加载更多事件回调
     */
    private OnLoadMoreListener mOnLoadMoreListener;

    /**
     * emptyview
     */
    private View mEmptyView;

    /**
     * 没有更多了是否隐藏loadmoreview
     */
    private boolean mNoLoadMoreHideView;

    private HeaderAndFooterRecyclerViewAdapter mHeaderAndFooterRecyclerViewAdapter;
    private boolean mAddLoadMoreFooterFlag;

    public GridRecyclerViewFinal(Context context) {
        super(context);
        init(context, null);
    }

    public GridRecyclerViewFinal(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public GridRecyclerViewFinal(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    public boolean ismLoadMoreLock() {
        return mLoadMoreLock;
    }

    public void setmLoadMoreLock(boolean mLoadMoreLock) {
        this.mLoadMoreLock = mLoadMoreLock;
    }

    private void init(Context context, AttributeSet attrs) {
        mHeaderAndFooterRecyclerViewAdapter = new HeaderAndFooterRecyclerViewAdapter();
//        super.setAdapter(mHeaderAndFooterRecyclerViewAdapter);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.LoadingViewFinal);

        if (a.hasValue(R.styleable.LoadingViewFinal_loadMoreMode)) {
            mLoadMoreMode = LoadMoreMode.mapIntToValue(a.getInt(R.styleable.LoadingViewFinal_loadMoreMode, 0x01));
        } else {
            mLoadMoreMode = LoadMoreMode.SCROLL;
        }

        if (a.hasValue(R.styleable.LoadingViewFinal_noLoadMoreHideView)) {
            mNoLoadMoreHideView = a.getBoolean(R.styleable.LoadingViewFinal_noLoadMoreHideView, true);
        } else {
            mNoLoadMoreHideView = true;
        }

        if (a.hasValue(R.styleable.LoadingViewFinal_loadMoreView)) {
            try {
                String loadMoreViewName = a.getString(R.styleable.LoadingViewFinal_loadMoreView);
                Class clazz = Class.forName(loadMoreViewName);
                Constructor c = clazz.getConstructor(Context.class);
                ILoadMoreView loadMoreView = (ILoadMoreView) c.newInstance(context);
                mLoadMoreView = loadMoreView;
            } catch (Exception e) {
                e.printStackTrace();
                mLoadMoreView = new DefaultLoadMoreView(context);
            }
        } else {
            mLoadMoreView = new DefaultLoadMoreView(context);
        }

        mLoadMoreView.getFooterView().setOnClickListener(new OnMoreViewClickListener());

        setHasLoadMore(false);
        a.recycle();
        addOnScrollListener(new RecyclerViewOnScrollListener());
    }

    @Override
    public void setAdapter(Adapter adapter) {
        super.setAdapter(adapter);
        if (mHeaderAndFooterRecyclerViewAdapter.getHeaderViews().size()<1 && mHeaderAndFooterRecyclerViewAdapter.getFooterViews().size()<1) {
            super.setAdapter(adapter);
        } else {
            mHeaderAndFooterRecyclerViewAdapter.setAdapter(adapter);

            super.setAdapter(mHeaderAndFooterRecyclerViewAdapter);
        }




////        super.setAdapter(adapter);
//        try {
//            adapter.unregisterAdapterDataObserver(mDataObserver);
//        } catch (Exception e){}
//
//        adapter.registerAdapterDataObserver(mDataObserver);
//        adapter.onAttachedToRecyclerView(this);
//        mHeaderAndFooterRecyclerViewAdapter.setAdapter(adapter);
    }

    @Override
    public void onScorllBootom() {
        if(mHasLoadMore && mLoadMoreMode == LoadMoreMode.SCROLL) {
            executeLoadMore();
        }
    }

    /**
     * 设置recyclerview emptyview
     * @param emptyView
     */
    public void setEmptyView(View emptyView) {
        this.mEmptyView = emptyView;
    }

    /**
     * 设置LoadMoreView
     * @param loadMoreView
     */
    public void setLoadMoreView(ILoadMoreView loadMoreView) {
        if (mLoadMoreView != null) {
            try {
                removeFooterView(mLoadMoreView.getFooterView());
                mAddLoadMoreFooterFlag = false;
            } catch (Exception e){}
        }
        mLoadMoreView = loadMoreView;
        mLoadMoreView.getFooterView().setOnClickListener(new OnMoreViewClickListener());
    }

    /**
     * 设置加载更多模式
     * @param mode
     */
    public void setLoadMoreMode(LoadMoreMode mode) {
        mLoadMoreMode = mode;
    }

    /**
     * 设置没有更多数据了，是否隐藏fooler view
     * @param hide
     */
    public void setNoLoadMoreHideView(boolean hide) {
        this.mNoLoadMoreHideView = hide;
    }

    /**
     * 没有很多了
     */
    void showNoMoreUI() {
        mLoadMoreLock = false;
        mLoadMoreView.showNoMore(null);
    }

    /**
     * 没有很多了
     */
    void showNoMoreUI(String noMoreStr) {
        mLoadMoreLock = false;
        mLoadMoreView.showNoMore(noMoreStr);
    }

    /**
     * 显示失败UI
     */
    public void showFailUI() {
        mHasLoadFail = true;
        mLoadMoreLock = false;
        mLoadMoreView.showFail();
    }

    /**
     * 显示默认UI
     */
    void showNormalUI() {
        mLoadMoreLock = false;
        mLoadMoreView.showNormal();
    }

    /**
     * 显示加载中UI
     */
    void showLoadingUI(){
        mHasLoadFail = false;
        mLoadMoreView.showLoading();
    }

    /**
     * 是否有更多
     * @param hasLoadMore
     */
    public void setHasLoadMore(boolean hasLoadMore) {
        mHasLoadMore = hasLoadMore;
        if (!mHasLoadMore) {
            showNoMoreUI();
            if(mNoLoadMoreHideView) {
                removeFooterView(mLoadMoreView.getFooterView());
                mAddLoadMoreFooterFlag = false;
            }else {
                if(!mAddLoadMoreFooterFlag) {
                    mAddLoadMoreFooterFlag = true;
                    addFooterView(mLoadMoreView.getFooterView());
                }
            }

        } else {
            if(!mAddLoadMoreFooterFlag) {
                mAddLoadMoreFooterFlag = true;
                addFooterView(mLoadMoreView.getFooterView());
            }
            showNormalUI();
        }
    }

    /**
     * 是否有更多
     * @param hasLoadMore
     */
    public void setHasLoadMore(boolean hasLoadMore,String noMoreStr) {
        mHasLoadMore = hasLoadMore;
        if (!mHasLoadMore) {
            showNoMoreUI(noMoreStr);
            if(mNoLoadMoreHideView) {
                removeFooterView(mLoadMoreView.getFooterView());
                mAddLoadMoreFooterFlag = false;
            }else {
                if(!mAddLoadMoreFooterFlag) {
                    mAddLoadMoreFooterFlag = true;
                    addFooterView(mLoadMoreView.getFooterView());
                }
            }

        } else {
            if(!mAddLoadMoreFooterFlag) {
                mAddLoadMoreFooterFlag = true;
                addFooterView(mLoadMoreView.getFooterView());
            }
            showNormalUI();
        }
    }

    /**
     * 设置加载更多事件回调
     * @param loadMoreListener
     */
    public void setOnLoadMoreListener(OnLoadMoreListener loadMoreListener) {
        this.mOnLoadMoreListener = loadMoreListener;
    }

    /**
     * 完成加载更多
     */
    public void onLoadMoreComplete() {
        if (mHasLoadFail) {
            showFailUI();
        } else if (mHasLoadMore) {
            showNormalUI();
        }
    }

    /**
     * 添加footer view
     * @param footerView
     */
    public void addFooterView(View footerView) {
        mHeaderAndFooterRecyclerViewAdapter.addFooterView(footerView);
    }

    /**
     * 添加header view
     * @param headerView
     */
    public void addHeaderView(View headerView) {
        mHeaderAndFooterRecyclerViewAdapter.addHeaderView(headerView);
    }

    public void removeFooterView(View footerView) {
        mHeaderAndFooterRecyclerViewAdapter.removeFooter(footerView);
    }

    public void removeHeaderView(View headerView) {
        mHeaderAndFooterRecyclerViewAdapter.removeHeader(headerView);
    }

    /**
     * 点击more view加载更多
     */
    class OnMoreViewClickListener implements OnClickListener {
        @Override
        public void onClick(View view) {
            if(mHasLoadMore) {
                executeLoadMore();
            }
        }
    }

    /**
     * 加载更多
     */
    void executeLoadMore() {
        if(!mLoadMoreLock && mHasLoadMore) {
            if (mOnLoadMoreListener != null) {
                mOnLoadMoreListener.loadMore();
            }
            mLoadMoreLock = true;//上锁
            showLoadingUI();
        }
    }

    /**
     * 设置OnItemClickListener
     * @param listener
     */
    public void setOnItemClickListener(HeaderAndFooterRecyclerViewAdapter.OnItemClickListener listener) {
        mHeaderAndFooterRecyclerViewAdapter.setOnItemClickListener(listener);
    }

    /**
     * 设置OnItemLongClickListener
     * @param listener
     */
    public void setOnItemLongClickListener(HeaderAndFooterRecyclerViewAdapter.OnItemLongClickListener listener) {
        mHeaderAndFooterRecyclerViewAdapter.setOnItemLongClickListener(listener);
    }

    /**
     * 滚动到底部自动加载更多数据
     */
    private class RecyclerViewOnScrollListener extends OnScrollListener {

        /**
         * 最后一个的位置
         */
        private int[] lastPositions;

        /**
         * 最后一个可见的item的位置
         */
        private int lastVisibleItemPosition;

        /**
         * 当前滑动的状态
         */
        private int currentScrollState = 0;

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);

            LayoutManager layoutManager = recyclerView.getLayoutManager();

            if (layoutManager instanceof LinearLayoutManager) {
                lastVisibleItemPosition = ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition();
            } else if (layoutManager instanceof GridLayoutManager) {
                lastVisibleItemPosition = ((GridLayoutManager) layoutManager).findLastVisibleItemPosition();
            } else if (layoutManager instanceof StaggeredGridLayoutManager) {
                StaggeredGridLayoutManager staggeredGridLayoutManager = (StaggeredGridLayoutManager) layoutManager;
                if (lastPositions == null) {
                    lastPositions = new int[staggeredGridLayoutManager.getSpanCount()];
                }
                staggeredGridLayoutManager.findLastVisibleItemPositions(lastPositions);
                lastVisibleItemPosition = findMax(lastPositions);
            } else {
                throw new RuntimeException(
                        "Unsupported LayoutManager used. Valid ones are LinearLayoutManager, GridLayoutManager and StaggeredGridLayoutManager");
            }
        }

        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            currentScrollState = newState;
            LayoutManager layoutManager = recyclerView.getLayoutManager();
            int visibleItemCount = layoutManager.getChildCount();
            int totalItemCount = layoutManager.getItemCount();
            if ((visibleItemCount > 0 && currentScrollState == RecyclerView.SCROLL_STATE_IDLE && (lastVisibleItemPosition) >= totalItemCount - 1)) {
                onScorllBootom();
            }
        }

        /**
         * 取数组中最大值
         *
         * @param lastPositions
         * @return
         */
        private int findMax(int[] lastPositions) {
            int max = lastPositions[0];
            for (int value : lastPositions) {
                if (value > max) {
                    max = value;
                }
            }

            return max;
        }
    }

    /**
     * 刷新数据时停止滑动,避免出现数组下标越界问题
     */
    private AdapterDataObserver mDataObserver = new AdapterDataObserver() {
        @Override
        public void onChanged() {
            Adapter<?> adapter =  getAdapter();
            if(adapter != null && mEmptyView != null) {
                if(adapter.getItemCount() == 0) {
                    mEmptyView.setVisibility(View.VISIBLE);
                    setVisibility(View.GONE);
                } else {
                    mEmptyView.setVisibility(View.GONE);
                    setVisibility(View.VISIBLE);
                }
            }

            dispatchTouchEvent(MotionEvent.obtain(SystemClock.uptimeMillis(), SystemClock.uptimeMillis(), MotionEvent.ACTION_CANCEL, 0, 0, 0));
        }
    };
}
