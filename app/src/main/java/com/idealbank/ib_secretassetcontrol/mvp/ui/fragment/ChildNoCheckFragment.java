package com.idealbank.ib_secretassetcontrol.mvp.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.idealbank.ib_secretassetcontrol.app.DbManager;
import com.idealbank.ib_secretassetcontrol.app.rfid_module.RfidData;
import com.idealbank.ib_secretassetcontrol.mvp.ui.adapter.AssetAdapter;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import com.idealbank.ib_secretassetcontrol.di.component.DaggerChildNoCheckComponent;
import com.idealbank.ib_secretassetcontrol.mvp.contract.ChildNoCheckContract;
import com.idealbank.ib_secretassetcontrol.mvp.presenter.ChildNoCheckPresenter;

import com.idealbank.ib_secretassetcontrol.R;

import org.simple.eventbus.Subscriber;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import me.jessyan.armscomponent.commonres.dialog.AppDialog;
import me.jessyan.armscomponent.commonres.dialog.DialogType;
import me.jessyan.armscomponent.commonsdk.base.fragment.BaseFragment;
import me.jessyan.armscomponent.commonsdk.bean.Event;
import me.jessyan.armscomponent.commonsdk.bean.Historyrecord.AssetsBean;
import me.jessyan.armscomponent.commonsdk.bean.Historyrecord.TaskBean;
import me.jessyan.armscomponent.commonsdk.core.EventBusTags;
import me.jessyan.armscomponent.commonsdk.utils.EventBusUtils;
import me.jessyan.armscomponent.commonsdk.utils.ToastUtil;
import me.jessyan.autosize.utils.LogUtils;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 06/17/2019 12:24
 * 未盘点
 */
public class ChildNoCheckFragment extends BaseFragment<ChildNoCheckPresenter> implements ChildNoCheckContract.View {
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;

    @Inject
    AssetAdapter mAdapter;
    @Inject
    List<AssetsBean> mList;
    String taskid;
    TaskBean taskBean;
    public static ChildNoCheckFragment newInstance(TaskBean bean) {
        ChildNoCheckFragment fragment = new ChildNoCheckFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("taskBean", (Serializable) bean);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerChildNoCheckComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_child_no_check, container, false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {

        initRecyclerView();
        getDate();
        mRecyclerView.setAdapter(mAdapter);
    }

    private void getDate() {
        taskBean = (TaskBean) getArguments().getSerializable("taskBean");
        taskid= taskBean.getId();
        mList  =   new DbManager(). queryAssetsBeanWhereIdandState(taskid,0);
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
                                        new DbManager().upDateAssetsBeanRemarkWhereId(mAdapter.getData().get(position).getId(), val);
                                        mAdapter.notifyItemChanged(position);
                                    }
                                })
                                .show();

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
    @Subscriber(tag = EventBusTags.CHECK)
    private void updateUser(Event event) {
        getDate();
    }


    @Subscriber(tag = EventBusTags.SCANRFID)
    private void scanRfid(Event event) {
        Log.e("ChildNoCheckFragment","ChildNoCheckFragment");
        RfidData rfidData = (RfidData) event.getData();
        String val=rfidData.getTagid();
        for (int i = 0; i <mAdapter.getData().size() ; i++) {
            //手持机扫描后获取的RFID与下载列表比较
            if(mAdapter.getData().get(i).getAssets().equals(val)){
                //先模糊查询FRID编号，得到的资产ID；再通过资产ID，修改资产状态
//                AssetsBean assetsBean = new DbManager().queryAssetsBeanWhereRfid(val);
                AssetsBean assetsBean = new DbManager().queryAssetsBeanWhereAssetsAndCheckId(taskid,val);
                new DbManager().upDateAssetsBeanStateWhereId(assetsBean.getId(), 1);
               TaskBean taskBean= new DbManager().  queryTaskBeanWhereID(assetsBean.getCheckId());
                //获取数据库中已盘数量+1
                int check = new DbManager().queryTaskBeanWhereUid(taskBean.getNumid()).getCheckNum() + 1;
                new DbManager().upDateTaskBeanWhereId(taskBean.getNumid(), taskBean.getId(), check);
                EventBusUtils.sendEvent(new Event(""), EventBusTags.CHECK);
            }
        }

    }

}
