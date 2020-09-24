package com.idealbank.ib_secretassetcontrol.mvp.ui.fragment;

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
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.idealbank.ib_secretassetcontrol.app.DbManager;
import com.idealbank.ib_secretassetcontrol.mvp.ui.adapter.AssetAdapter;
import com.idealbank.ib_secretassetcontrol.mvp.ui.adapter.SearchAssetAdapter;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import com.idealbank.ib_secretassetcontrol.di.component.DaggerSearchComponent;
import com.idealbank.ib_secretassetcontrol.mvp.contract.SearchContract;
import com.idealbank.ib_secretassetcontrol.mvp.presenter.SearchPresenter;

import com.idealbank.ib_secretassetcontrol.R;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import me.jessyan.armscomponent.commonres.dialog.AppDialog;
import me.jessyan.armscomponent.commonres.dialog.DialogType;
import me.jessyan.armscomponent.commonsdk.base.fragment.BaseFragment;
import me.jessyan.armscomponent.commonsdk.base.fragment.BaseToolBarFragment;
import me.jessyan.armscomponent.commonsdk.bean.Historyrecord.AssetsBean;
import me.jessyan.armscomponent.commonsdk.utils.ToastUtil;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 06/17/2019 12:26
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class SearchFragment extends BaseToolBarFragment<SearchPresenter> implements SearchContract.View {
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.search_edit)
    TextView search_edit;

    @Inject
    SearchAssetAdapter mAdapter;
    @Inject
    List<AssetsBean> mList;

   static String index;
   static String checkid;

    public static SearchFragment newInstance(String id,String  date) {
        SearchFragment fragment = new SearchFragment();
        index = date;
        checkid = id;
        return fragment;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerSearchComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        search_edit.setText(index+checkid);
        initRecyclerView();
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setKey(index);
        getDate();

    }
    private void getDate() {
        mList = new DbManager().queryAssetsBeanWhereAll(checkid,index);
        mAdapter.replaceData(mList);
    }

    /**
     * 初始化RecyclerView
     */
    private void initRecyclerView() {
        ArmsUtils.configRecyclerView(mRecyclerView, new LinearLayoutManager(getActivity()));
        mAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()) {
                    case R.id.stv_remark:
                        new AppDialog(_mActivity, DialogType.ADDASSET)
                                .setTitle("请为此资产添加备注")
                                .setLeftButton("取消", new AppDialog.OnButtonClickListener() {
                                    @Override
                                    public void onClick(String val) {
                                    }
                                })
                                .setRightButton("确定", new AppDialog.OnButtonClickListener() {
                                    @Override
                                    public void onClick(String val) {
                                        //获取数据库中已盘数量-1
                                        new DbManager().upDateAssetsBeanRemarkWhereId(mList.get(position).getId(), val);
                                        mAdapter.notifyItemChanged(position);
                                    }
                                })
                                .show();

                        break;

                }
            }
        });}
    @Override
    public void setData(@Nullable Object data) {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @OnClick({R.id.search_toolbar  })
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.search_toolbar:
                pop();
                break;

        }

    }

    @Override
    protected void initEventAndData() {

    }
}
