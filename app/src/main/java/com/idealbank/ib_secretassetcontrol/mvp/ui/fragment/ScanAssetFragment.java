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
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.idealbank.ib_secretassetcontrol.app.DbManager;
import com.idealbank.ib_secretassetcontrol.mvp.ui.adapter.AssetAdapter;
import com.idealbank.ib_secretassetcontrol.mvp.ui.adapter.AssetScanAdapter;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import com.idealbank.ib_secretassetcontrol.di.component.DaggerScanAssetComponent;
import com.idealbank.ib_secretassetcontrol.mvp.contract.ScanAssetContract;
import com.idealbank.ib_secretassetcontrol.mvp.presenter.ScanAssetPresenter;

import com.idealbank.ib_secretassetcontrol.R;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import me.jessyan.armscomponent.commonres.dialog.AppDialog;
import me.jessyan.armscomponent.commonres.dialog.DialogType;
import me.jessyan.armscomponent.commonsdk.base.activity.ScanActivity;
import me.jessyan.armscomponent.commonsdk.base.fragment.BaseFragment;
import me.jessyan.armscomponent.commonsdk.base.fragment.BaseToolBarFragment;
import me.jessyan.armscomponent.commonsdk.bean.Historyrecord.AssetsBean;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 06/21/2019 09:53
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class ScanAssetFragment extends BaseToolBarFragment<ScanAssetPresenter> implements ScanAssetContract.View {
    @BindView(R.id.tv_scan)
    TextView tv_scan;
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @Inject
    AssetScanAdapter mAdapter;
    @Inject
    List<AssetsBean> mList;
    String  assets;
    public static ScanAssetFragment newInstance(String assets) {
        ScanAssetFragment fragment = new ScanAssetFragment();
        Bundle bundle = new Bundle();
        bundle.putString("assets", assets);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerScanAssetComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_scan_asset, container, false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        assets=  getArguments().getString("assets");
        initRecyclerView();
        setScanResult(assets);
        mRecyclerView.setAdapter(mAdapter);

    }
    private void setScanResult(String assets) {
        tv_scan.setText(assets);
        mList = new DbManager().queryAssetsBeanWhereAssets(assets);
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
                                        //修改
                                        new DbManager().upDateAssetsBeanRemarkWhereId(mList.get(position).getId(), val);
                                        mAdapter.notifyItemChanged(position);
                                    }
                                })
                                .show();

                        break;
                    case R.id.stv_taskid:

//                        ((MainFragment) getParentFragment()).startBrotherFragment(TaskDetailsFragment.newInstance(mList.get(position).getId()));
                        start(TaskDetailsFragment.newInstance(mList.get(position).getId()));

                        break;

                }
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

    @OnClick({R.id.search_back_ib, R.id.tv_scan})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.search_back_ib:
                pop();
                break;

            case R.id.tv_scan:
                startActivityForResult(new Intent(getActivity(), ScanActivity.class), ScanActivity.REQUEST_CODE_SUCCESS);
                break;
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && requestCode == ScanActivity.REQUEST_CODE_SUCCESS) {

            Toast.makeText(_mActivity, data.getStringExtra(ScanActivity.RESULT), Toast.LENGTH_SHORT).show();
            setScanResult(data.getStringExtra(ScanActivity.RESULT));
        }
    }
}
