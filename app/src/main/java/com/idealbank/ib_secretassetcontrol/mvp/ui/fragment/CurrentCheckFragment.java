package com.idealbank.ib_secretassetcontrol.mvp.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.idealbank.ib_secretassetcontrol.Netty.ChannelMap;
import com.idealbank.ib_secretassetcontrol.Netty.bean.Message;
import com.idealbank.ib_secretassetcontrol.Netty.bean.MsgType;
import com.idealbank.ib_secretassetcontrol.app.DbManager;
import com.idealbank.ib_secretassetcontrol.bean.Upload;
import com.idealbank.ib_secretassetcontrol.mvp.ui.adapter.CurrentCheckAdapter;
import com.jess.arms.base.BaseFragment;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import com.idealbank.ib_secretassetcontrol.di.component.DaggerCurrentCheckComponent;
import com.idealbank.ib_secretassetcontrol.mvp.contract.CurrentCheckContract;
import com.idealbank.ib_secretassetcontrol.mvp.presenter.CurrentCheckPresenter;

import com.idealbank.ib_secretassetcontrol.R;
import com.jess.arms.utils.LogUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.simple.eventbus.Subscriber;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import me.jessyan.armscomponent.commonres.dialog.AppDialog;
import me.jessyan.armscomponent.commonres.dialog.DialogType;
import me.jessyan.armscomponent.commonsdk.base.activity.ScanActivity;
import me.jessyan.armscomponent.commonsdk.base.fragment.BaseActionBarFragment;
import me.jessyan.armscomponent.commonsdk.bean.Event;
import me.jessyan.armscomponent.commonsdk.bean.Historyrecord.AssetsBean;
import me.jessyan.armscomponent.commonsdk.bean.Historyrecord.TaskBean;
import me.jessyan.armscomponent.commonsdk.core.EventBusTags;
import me.jessyan.armscomponent.commonsdk.utils.EventBusUtils;
import me.jessyan.armscomponent.commonsdk.utils.GsonUtil;
import me.jessyan.armscomponent.commonsdk.utils.ToastUtil;

import static com.jess.arms.utils.Preconditions.checkNotNull;
import static me.jessyan.armscomponent.commonsdk.utils.ToastUtil.showToast;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 06/17/2019 12:24
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class CurrentCheckFragment extends BaseActionBarFragment<CurrentCheckPresenter> implements CurrentCheckContract.View {
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    //    @BindView(R.id.refreshLayout)
//    SmartRefreshLayout refreshLayout;
    @Inject
    CurrentCheckAdapter mAdapter;
    @Inject
    List<TaskBean> mList;


    private List<String> list = new ArrayList<>();

    public static CurrentCheckFragment newInstance() {
        CurrentCheckFragment fragment = new CurrentCheckFragment();
        return fragment;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerCurrentCheckComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_current_check, container, false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        setTitleText("盘点任务", R.mipmap.ic_scan, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(getActivity(), ScanActivity.class), ScanActivity.REQUEST_CODE_SUCCESS);
            }
        });


        initRecyclerView();
        getDate();
        mRecyclerView.setAdapter(mAdapter);
    }

    private void getDate() {
        mList.clear();
        mList.addAll(new DbManager().queryTaskBeanWhereState(0));
        mList.addAll(new DbManager().queryTaskBeanWhereState(1));
        mAdapter.replaceData(mList);
//        refreshLayout.finishRefresh();//结束刷新
    }

    /**
     * 初始化RecyclerView
     */
    private void initRecyclerView() {
        ArmsUtils.configRecyclerView(mRecyclerView, new LinearLayoutManager(getActivity()));
//        refreshLayout.autoRefresh();
//        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
//
//            @Override
//            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
//                getDate();
//            }
//        });
        mAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()) {
                    case R.id.btn_left:
                        new AppDialog(_mActivity, DialogType.DEFAULT).setTitle("确定上传盘点记录？")
                                .setLeftButton("取消", new AppDialog.OnButtonClickListener() {
                                    @Override
                                    public void onClick(String val) {
                                    }
                                })
                                .setRightButton("确定", new AppDialog.OnButtonClickListener() {
                                    @Override
                                    public void onClick(String val) {
                                        //跟电脑交互成功后再更新数据库
                                        if (ChannelMap.getChannel("1") != null) {
                                            com.idealbank.ib_secretassetcontrol.Netty.bean.Message message = new Message();
                                            message.setId(1);
                                            message.setType(MsgType.UPLOADDATA);
                                            Upload upload = new Upload();
                                            upload.setCheck(mList.get(position));
                                            upload.setCheckAssets(new DbManager().queryAssetsBeanWhereId(mList.get(position).getId()));
                                            message.setResponseMessage(upload);
                                            ChannelMap.getChannel("1").writeAndFlush(new TextWebSocketFrame(GsonUtil.GsonString(message)));
                                        } else {
                                            showToast("未连接");
                                        }

//                                        new DbManager().  upDateTaskBeanWhereId(mList.get(position).getNumid(),2);
//                                        queryAllOrders();
                                    }
                                })
                                .show();

//                        new AppDialog(_mActivity, DialogType.ADDASSET)
//                                .setTitle("请为此资产添加备注")
//                                .setLeftButton("确定")
//                                .setRightButton("取消", new AppDialog.OnButtonClickListener() {
//                                    @Override
//                                    public void onClick(String val) {
//                                        ToastUtil.showToast(val);
//                                    }
//                                })
//                                .show();
                        break;
                    case R.id.btn_right:
                        new AppDialog(_mActivity, DialogType.DEFAULT).setTitle("确定转为记录？")
                                .setLeftButton("取消", new AppDialog.OnButtonClickListener() {
                                    @Override
                                    public void onClick(String val) {
                                    }
                                })
                                .setRightButton("确定", new AppDialog.OnButtonClickListener() {
                                    @Override
                                    public void onClick(String val) {
//                                        mAdapter.remove(position);
                                        new DbManager().upDateTaskBeanWhereId(mList.get(position).getNumid(), 3);
                                        getDate();
                                        EventBusUtils.sendEvent(new Event(""), EventBusTags.REFRESH);
                                    }
                                })
                                .show();
                        break;
                    case R.id.content:
//                        start(TaskDetailsFragment.newInstance());
                        ((MainFragment) getParentFragment()).startBrotherFragment(TaskDetailsFragment.newInstance(mList.get(position).getNumid()));

                        break;
                    case R.id.right_menu_1:
                        new AppDialog(_mActivity, DialogType.DEFAULT).setTitle("确定从盘查表中删除此项？")
                                .setLeftButton("取消", new AppDialog.OnButtonClickListener() {
                                    @Override
                                    public void onClick(String val) {
                                    }
                                })
                                .setRightButton("确定", new AppDialog.OnButtonClickListener() {
                                    @Override
                                    public void onClick(String val) {
                                        new DbManager().delTaskBeanWhereId(mList.get(position).getNumid());
                                        //删除该任务id下的资产
                                        new DbManager().delAssetsBeanWhereId(mList.get(position).getId());
                                        EventBusUtils.sendEvent(new Event(""), EventBusTags.REFRESH);
                                        getDate();
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

    @OnClick({R.id.insert, R.id.query, R.id.queryAllOrders})
    void onClick(View view) {
        int i = view.getId();
        if (i == R.id.insert) {
            insert();
            EventBusUtils.sendEvent(new Event(""), EventBusTags.REFRESH);
        } else if (i == R.id.query) {
            query();
        } else if (i == R.id.queryAllOrders) {
            queryAllOrders();
        }
    }


    public void insert() {
        TaskBean taskBean = new TaskBean();
        taskBean.setId("1233");
        taskBean.setCheckType(0);
        taskBean.setState(0);
        taskBean.setTotal(4);
        taskBean.setCheckName("小水");
        taskBean.setCurUser("小王");
        TaskBean taskBean2 = new TaskBean();
        taskBean2.setId("1234");
        taskBean2.setCheckType(1);
        taskBean2.setState(0);
        taskBean2.setTotal(5);
        taskBean2.setBelongDeptName("智能系统部");
        taskBean2.setCurUser("小冯");
//        TaskBean taskBean3 = new TaskBean();
//        taskBean3.setId("1230");
//        taskBean3.setCheckType(1);
//        taskBean3.setState(0);
//        taskBean3.setTotal(2);
//        taskBean3.setBelongDeptName("智能系统部");
//        taskBean3.setCurUser("小孟");

        long insertID = new DbManager().insertTaskBean(taskBean);
        new DbManager().insertTaskBean(taskBean2);
//        new DbManager().insertTaskBean(taskBean3);
        if (insertID >= 0) {
            Toast.makeText(getActivity(), "插入 User 成功", Toast.LENGTH_SHORT).show();
            insertOrders(taskBean.getId(),taskBean.getNumid(),taskBean2.getNumid());
        } else {
            Toast.makeText(getActivity(), "插入User 失败", Toast.LENGTH_SHORT).show();
        }


    }

    private void insertOrders(String taskid,Long id1,Long id2) {
        AssetsBean myOrder1 = new AssetsBean();
        myOrder1.setTaskbeanid(id1);
        myOrder1.setAssetName("电脑");
        myOrder1.setTypeName("电器");
        myOrder1.setAssets("1001");
        myOrder1.setCheckId(taskid);
        myOrder1.setRfidId("2019073001");
        AssetsBean myOrder2 = new AssetsBean();
        myOrder2.setTaskbeanid(id1);
        myOrder2.setAssetName("手机");
        myOrder2.setTypeName("电器");
        myOrder2.setAssets("1002");
        myOrder2.setCheckId(taskid);
        myOrder2.setRfidId("2019073002");
        AssetsBean myOrder3 = new AssetsBean();
        myOrder3.setTaskbeanid(id1);
        myOrder3.setAssetName("耳机");
        myOrder3.setTypeName("电器");
        myOrder3.setAssets("1003");
        myOrder3.setCheckId(taskid);
        myOrder3.setRfidId("2019073003");
        AssetsBean myOrder4 = new AssetsBean();
        myOrder4.setTaskbeanid(id1);
        myOrder4.setAssetName("打印机");
        myOrder4.setTypeName("电器");
        myOrder4.setAssets("1004");
        myOrder4.setCheckId(taskid);
        myOrder4.setRfidId("2019073004");
        long orderId1 = new DbManager().insertAssetsBean(myOrder1);
        long orderId2 = new DbManager().insertAssetsBean(myOrder2);
        new DbManager().insertAssetsBean(myOrder3);
        new DbManager().insertAssetsBean(myOrder4);

        AssetsBean myOrder5 = new AssetsBean();
        myOrder5.setTaskbeanid(id2);
        myOrder5.setAssetName("电脑");
        myOrder5.setTypeName("电器");
        myOrder5.setCheckId("1234");
        myOrder5.setAssets("1005");
        myOrder5.setRfidId("2019073005");
        AssetsBean myOrder6 = new AssetsBean();
        myOrder6.setTaskbeanid(id2);
        myOrder6.setAssetName("手机");
        myOrder6.setTypeName("电器");
        myOrder6.setAssets("1006");
        myOrder6.setCheckId("1234");
        myOrder6.setRfidId("2019073006");
        AssetsBean myOrder7 = new AssetsBean();
        myOrder7.setTaskbeanid(id2);
        myOrder7.setAssetName("耳机");
        myOrder7.setAssets("1007");
        myOrder7.setTypeName("电器");
        myOrder7.setCheckId("1234");
        myOrder7.setRfidId("2019073007");
        AssetsBean myOrder8 = new AssetsBean();
        myOrder8.setTaskbeanid(id2);
        myOrder8.setAssetName("打印机");
        myOrder8.setTypeName("电器");
        myOrder8.setAssets("1008");
        myOrder8.setCheckId("1234");
        myOrder8.setRfidId("20190008");
        AssetsBean myOrder9 = new AssetsBean();
        myOrder9.setTaskbeanid(id2);
        myOrder9.setAssetName("打印机");
        myOrder9.setTypeName("电器");
        myOrder9.setAssets("1009");
        myOrder9.setCheckId("1234");
        myOrder9.setRfidId("20190009");
//
//        AssetsBean myOrder10 = new AssetsBean();
//        myOrder10.setAssetName("杯子");
//        myOrder10.setTypeName("生活用品");
//        myOrder10.setAssets("1010");
//        myOrder10.setCheckId("1230");
//        myOrder10.setRfidId("C090C0000008388C");
//        AssetsBean myOrder11 = new AssetsBean();
//        myOrder11.setAssetName("纸巾");
//        myOrder11.setTypeName("生活用品");
//        myOrder11.setAssets("1011");
//        myOrder11.setCheckId("1230");
//        myOrder11.setRfidId("C090C000000806FF");
        new DbManager().insertAssetsBean(myOrder5);
        new DbManager().insertAssetsBean(myOrder6);
        new DbManager().insertAssetsBean(myOrder7);
        new DbManager().insertAssetsBean(myOrder8);
        new DbManager().insertAssetsBean(myOrder9);
//        new DbManager().insertAssetsBean(myOrder10);
//        new DbManager().insertAssetsBean(myOrder11);

        if (orderId1 >= 0 && orderId2 >= 0) {
            Toast.makeText(getActivity(), "插入 MyOrder 成功", Toast.LENGTH_SHORT).show();
        }
    }

    public void query() {
        List<AssetsBean> orders = new DbManager().loadAllAssetsBean();
        for (AssetsBean order : orders) {
            Log.e("TAG", "get AssetsBean " + "getAssets:" + order.getAssets() + "getRfidId:" + order.getRfidId() + order.getAssetName() + "getCheckId:" + order.getCheckId());
        }
    }

    public void queryAllOrders() {
        List<TaskBean> taskBeans = new DbManager().loadAllTaskBean();
        mList = taskBeans;
        mAdapter.replaceData(mList);


    }

    @Subscriber(tag = EventBusTags.REFRESH)
    private void updateUser(Event event) {
        getDate();
    }
}
