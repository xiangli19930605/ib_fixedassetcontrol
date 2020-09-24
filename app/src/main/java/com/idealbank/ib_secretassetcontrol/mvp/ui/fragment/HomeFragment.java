package com.idealbank.ib_secretassetcontrol.mvp.ui.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.idealbank.ib_secretassetcontrol.app.DbManager;
import com.idealbank.ib_secretassetcontrol.mvp.ui.adapter.HomeCurrentAdapter;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import com.idealbank.ib_secretassetcontrol.di.component.DaggerHomeComponent;
import com.idealbank.ib_secretassetcontrol.mvp.contract.HomeContract;
import com.idealbank.ib_secretassetcontrol.mvp.presenter.HomePresenter;

import com.idealbank.ib_secretassetcontrol.R;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.simple.eventbus.Subscriber;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import me.jessyan.armscomponent.commonres.dialog.AppDialog;
import me.jessyan.armscomponent.commonres.dialog.DialogType;
import me.jessyan.armscomponent.commonsdk.base.activity.ScanActivity;
import me.jessyan.armscomponent.commonsdk.base.fragment.BaseFragment;
import me.jessyan.armscomponent.commonsdk.bean.Event;
import me.jessyan.armscomponent.commonsdk.bean.Historyrecord.TaskBean;
import me.jessyan.armscomponent.commonsdk.core.EventBusTags;
import me.jessyan.armscomponent.commonsdk.utils.DateUtils;
import me.jessyan.armscomponent.commonsdk.utils.EventBusUtils;
import q.rorbin.badgeview.QBadgeView;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 06/10/2019 16:15
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class HomeFragment extends BaseFragment<HomePresenter> implements HomeContract.View {
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.tv_tips)
    TextView tv_tips;
    @BindView(R.id.btn_current)
    Button btn_current;
    @BindView(R.id.tv_date)
    TextView tv_date;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;

    @Inject
    HomeCurrentAdapter mAdapter;
    @Inject
    List<TaskBean> mList;
    QBadgeView badgeView;
    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerHomeComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }


    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        initRecyclerView();
        tv_date.setText(DateUtils.getCurrentDateStr()+"  "+DateUtils.getCurrentWeekStr());

        mRecyclerView.setAdapter(mAdapter);
        refreshLayout.autoRefresh();
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                getDate();
            }
        });

        badgeView=new QBadgeView(_mActivity);
        badgeView.bindTarget(btn_current);
    }

    private void getDate() {
        mList.clear();
        mList.addAll(new DbManager().queryTaskBeanWhereState(0));
        mList.addAll(new DbManager().queryTaskBeanWhereState(1));
        if (mList.size() > 0) {
            tv_tips.setVisibility(View.VISIBLE);
            tv_tips.setText("您有" + mList.size() + "条盘点任务正在进行...");
            badgeView.setBadgeNumber(mList.size());
        } else {
            tv_tips.setVisibility(View.GONE);
            badgeView.setBadgeNumber(0);
        }
        mAdapter.replaceData(mList);
        refreshLayout.finishRefresh();//结束刷新
    }

    /**
     * 初始化RecyclerView
     */
    private void initRecyclerView() {
        ArmsUtils.configRecyclerView(mRecyclerView, new LinearLayoutManager(getActivity()));
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                ((MainFragment) getParentFragment()).startBrotherFragment(TaskDetailsFragment.newInstance(mList.get(position).getNumid()));
            }
        });
    }


    @OnClick({R.id.btn_search, R.id.btn_current, R.id.btn_history})
    void onClick(View view) {
        int i = view.getId();
        if (i == R.id.btn_search) {
//            ((MainFragment) getParentFragment()).startBrotherFragment(SearchHistoryFragment.newInstance());
            startActivityForResult(new Intent(getActivity(), ScanActivity.class), ScanActivity.REQUEST_CODE_SUCCESS);
        } else if (i == R.id.btn_current) {
            EventBusUtils.sendEvent(new Event(EventBusTags.ONE), EventBusTags.JUMP_PAGE);
        } else if (i == R.id.btn_history) {
            EventBusUtils.sendEvent(new Event(EventBusTags.TWO), EventBusTags.JUMP_PAGE);
        }

    }

    @Override
    public void setData(@Nullable Object data) {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    protected void initEventAndData() {

    }

    @Subscriber(tag = EventBusTags.REFRESH)
    private void updateUser(Event event) {
        getDate();
    }

//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (resultCode == Activity.RESULT_OK && requestCode == ScanActivity.REQUEST_CODE_SUCCESS) {
//
//            new AppDialog(_mActivity, DialogType.QUERY).setTitle("确定在盘点任务中查资产？").setAsset(data.getStringExtra(ScanActivity.RESULT))
//                    .setLeftButton("取消", new AppDialog.OnButtonClickListener() {
//                        @Override
//                        public void onClick(String val) {
//                        }
//                    })
//                    .setRightButton("确定", new AppDialog.OnButtonClickListener() {
//                        @Override
//                        public void onClick(String val) {
//                            ((MainFragment) getParentFragment()).startBrotherFragment(ScanAssetFragment.newInstance(val));
//                        }
//                    })
//                    .show();
//
//        }
//    }
}
