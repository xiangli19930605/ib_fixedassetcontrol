package com.idealbank.ib_secretassetcontrol.mvp.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.idealbank.ib_secretassetcontrol.app.DbManager;
import com.idealbank.ib_secretassetcontrol.mvp.ui.adapter.SearchAssetAdapter;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import com.idealbank.ib_secretassetcontrol.di.component.DaggerSearchHistoryComponent;
import com.idealbank.ib_secretassetcontrol.mvp.contract.SearchHistoryContract;
import com.idealbank.ib_secretassetcontrol.mvp.presenter.SearchHistoryPresenter;

import com.idealbank.ib_secretassetcontrol.R;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import me.jessyan.armscomponent.commonres.dialog.AppDialog;
import me.jessyan.armscomponent.commonres.dialog.DialogType;
import me.jessyan.armscomponent.commonsdk.base.fragment.BaseFragment;
import me.jessyan.armscomponent.commonsdk.base.fragment.BaseToolBarFragment;
import me.jessyan.armscomponent.commonsdk.bean.Historyrecord.AssetsBean;
import me.jessyan.armscomponent.commonsdk.bean.Historyrecord.HistoryData;
import me.jessyan.armscomponent.commonsdk.utils.GsonUtil;
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
public class SearchHistoryFragment extends BaseToolBarFragment<SearchHistoryPresenter> implements SearchHistoryContract.View,View.OnKeyListener {
    @BindView(R.id.flowlayout)
    TagFlowLayout flowlayout;
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.search_edit)
    EditText et_search;
    @BindView(R.id.ll_content)
    LinearLayout ll_content;
    @Inject
    List<HistoryData> mHistoryDataList;
    @Inject
    SearchAssetAdapter mAdapter;
    @Inject
    List<AssetsBean> mAssetsBeanList;

    String checkid;
    public static SearchHistoryFragment newInstance(String id) {
        SearchHistoryFragment fragment = new SearchHistoryFragment();
        Bundle bundle = new Bundle();
        bundle.putString("id", id);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerSearchHistoryComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_search_history, container, false);
    }


    @Override
    public void initData(@Nullable Bundle savedInstanceState) {

        checkid=  getArguments().getString("id");
        mPresenter.loadAllHistoryData();

        initRecyclerView();
        et_search.addTextChangedListener(watcher);
        et_search.setOnKeyListener(this);

        flowlayout.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
                getDate(mHistoryDataList.get(position).getData());
                return true;
            }
        });
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
    private void initRecyclerView() {
        ArmsUtils.configRecyclerView(mRecyclerView, new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(mAdapter);
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
                                        new DbManager().upDateAssetsBeanRemarkWhereId(mAssetsBeanList.get(position).getId(), val);
                                        mAdapter.notifyItemChanged(position);
                                    }
                                })
                                .show();

                        break;

                }
            }
        });}
    @OnClick({R.id.tv_cancel, R.id.img_del, R.id.search_history_clear_all_tv})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_cancel:
                pop();
                break;
            case R.id.img_del:
                et_search.setText("");
                ll_content.setVisibility(View.VISIBLE);
                break;
            case R.id.search_history_clear_all_tv:
                mPresenter.clearHistoryData();
                break;
        }

    }

    @Override
    public void loadAllHistoryData(List<HistoryData> list) {
        this.mHistoryDataList = list;
        flowlayout.setAdapter(new TagAdapter(list) {
            @Override
            public View getView(FlowLayout parent, int position, Object o) {
                TextView tv = (TextView) LayoutInflater.from(getActivity()).inflate(R.layout.layout_tv,
                        flowlayout, false);

                HistoryData historyData = GsonUtil.GsonToBean(GsonUtil.GsonString(o), HistoryData.class);
                tv.setText(historyData.getData());
                return tv;
            }


        });

    }

    @Override
    public void addHistoryData(String data) {

    }

    @Override
    public void clearHistoryData() {

    }
    TextWatcher watcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void afterTextChanged(Editable s) {
            if(s.toString().equals("")){
                ll_content.setVisibility(View.VISIBLE);
            }else{
            getDate(s.toString());
            }
        }
    };
    /**
     * 监听输入键盘更换后的搜索按键
     * 调用时刻：点击键盘上的搜索键时
     */
    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {
            // 1. 点击搜索按键后，根据输入的搜索字段进行查询
            // 注：由于此处需求会根据自身情况不同而不同，所以具体逻辑由开发者自己实现，此处仅留出接口
//                    if (!(mCallBack == null)){
//                        mCallBack.SearchAciton(et_search.getText().toString());
//                    }
//                    // 2. 点击搜索键后，对该搜索字段在数据库是否存在进行检查（查询）->> 关注1
//                    boolean hasData = hasData(et_search.getText().toString().trim());
//                    // 3. 若存在，则不保存；若不存在，则将该搜索字段保存（插入）到数据库，并作为历史搜索记录
//                    if (!hasData) {
//                        insertData(et_search.getText().toString().trim());
//                        queryData("");
//                    }

//            start(SearchFragment.newInstance(checkid,et_search.getText().toString()));

            getDate(et_search.getText().toString());
            mPresenter.addHistoryData(et_search.getText().toString());
        }
        return false;
    }

    private void getDate(String input) {
        mAssetsBeanList = new DbManager().queryAssetsBeanWhereAll(checkid,input);
        mAdapter.setKey(input);
        if(mAssetsBeanList.size()>0){
            ll_content.setVisibility(View.GONE);
        }else{
            ll_content.setVisibility(View.VISIBLE);
        }
        mAdapter.replaceData(mAssetsBeanList);
    }
}
