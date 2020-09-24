package me.jessyan.armscomponent.commonsdk.base.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;


import com.jess.arms.mvp.BasePresenter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import me.jessyan.armscomponent.commonsdk.R;
import me.jessyan.armscomponent.commonsdk.base.activity.ActionBarActivity;

/**
 * Describe：带下拉刷新 上拉加载更多的Activity
 * 内部实现为刷新控件 PullToRefreshLayout + 列表控件 RecyclerView
 * Created by 吴天强 on 2018/10/23.
 */
public abstract class RefreshListActivity<P extends BasePresenter> extends ActionBarActivity<P> {

//    @BindView(R2.id.ptrl_list)
//    PullToRefreshLayout refreshLayout;
//
//    @BindView(R2.id.recycle_view)
//    RecyclerView recyclerView;

//    @BindView(R2.id.srl_smart_refresh)
    SmartRefreshLayout srl_smart_refresh;
//    @BindView(R2.id.rv_smart_refresh)
    RecyclerView mRecyclerView;

    protected RecyclerView.Adapter mAdapter;

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.layout_refresh;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        initRecyclerView();
        mRecyclerView.setAdapter(mAdapter);
    }
    /**
     * 初始化RecyclerView
     */
    private void initRecyclerView() {
//        ArmsUtils.configRecyclerView(mRecyclerView, mLayoutManager);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setHasFixedSize(true);
        srl_smart_refresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
//                mPresenter.requestGirls(true);

            }
        });
        srl_smart_refresh.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull final RefreshLayout refreshLayout) {
//                mPresenter.requestGirls(false);

            }
        });
        srl_smart_refresh.autoRefresh();
    }

    protected abstract RecyclerView.LayoutManager getLayoutManager();

    protected abstract RecyclerView.Adapter createAdapter();



}
