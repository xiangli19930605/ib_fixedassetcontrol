package com.idealbank.ib_secretassetcontrol.mvp.ui.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.idealbank.ib_secretassetcontrol.app.DbManager;
import com.idealbank.ib_secretassetcontrol.mvp.ui.adapter.HistoryAdapter;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import com.idealbank.ib_secretassetcontrol.di.component.DaggerHistoryComponent;
import com.idealbank.ib_secretassetcontrol.mvp.contract.HistoryContract;
import com.idealbank.ib_secretassetcontrol.mvp.presenter.HistoryPresenter;

import com.idealbank.ib_secretassetcontrol.R;
import com.jess.arms.utils.LogUtils;

import org.simple.eventbus.Subscriber;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import me.jessyan.armscomponent.commonres.dialog.AppDialog;
import me.jessyan.armscomponent.commonres.dialog.DialogType;
import me.jessyan.armscomponent.commonsdk.app.MyApplication;
import me.jessyan.armscomponent.commonsdk.base.activity.ScanActivity;
import me.jessyan.armscomponent.commonsdk.base.fragment.BaseActionBarFragment;
import me.jessyan.armscomponent.commonsdk.base.fragment.BaseFragment;
import me.jessyan.armscomponent.commonsdk.bean.Event;
import me.jessyan.armscomponent.commonsdk.bean.Historyrecord.TaskBean;
import me.jessyan.armscomponent.commonsdk.core.EventBusTags;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 06/17/2019 12:25
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class HistoryFragment extends BaseActionBarFragment<HistoryPresenter> implements HistoryContract.View {
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.ll_mycollection_bottom_dialog)
    LinearLayout mLlMycollectionBottomDialog;
//    @BindView(R.id.tv_select_num)
//    TextView mTvSelectNum;
    @BindView(R.id.btn_delete)
    Button mBtnDelete;
    @BindView(R.id.select_all)
    TextView mSelectAll;
    @Inject
    HistoryAdapter mAdapter;
    @Inject
    List<TaskBean> mList;

    public static HistoryFragment newInstance() {
        HistoryFragment fragment = new HistoryFragment();
        return fragment;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerHistoryComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_history, container, false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        setTitleText("历史记录");
        setRightText("编辑", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updataEditMode();
            }
        });

        initRecyclerView();
        getDate();
        mRecyclerView.setAdapter(mAdapter);
    }

    private static final int MYLIVE_MODE_CHECK = 0;
    private static final int MYLIVE_MODE_EDIT = 1;
    private int mEditMode = MYLIVE_MODE_CHECK;
    private boolean isSelectAll = false;
    private boolean editorStatus = false;
    private int index = 0;

    private void updataEditMode() {
        mEditMode = mEditMode == MYLIVE_MODE_CHECK ? MYLIVE_MODE_EDIT : MYLIVE_MODE_CHECK;
        if (mEditMode == MYLIVE_MODE_EDIT) {
            setRightText("取消");
            mLlMycollectionBottomDialog.setVisibility(View.VISIBLE);
            editorStatus = true;
        } else {
            setRightText("编辑");
            mLlMycollectionBottomDialog.setVisibility(View.GONE);
            editorStatus = false;
//            clearAll();
        }
        mAdapter.setEditMode(mEditMode);
    }
    //全选删除
    private void clearAll() {
//        mTvSelectNum.setText(String.valueOf(0));
        isSelectAll = false;
        mSelectAll.setText("全选");
        setBtnBackground(0);
    }

    private void getDate() {
        mList = new DbManager().queryTaskBeanWhereState(3);
        mAdapter.replaceData(mList);
        if (mList.size() > 0) {
        } else {
            mAdapter.setEmptyView(LayoutInflater.from(getContext()).inflate(R.layout.empty_layout, null));
        }
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
                    case R.id.content_main:
                        LogUtils.warnInfo("" + position);


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
                                        new DbManager().delTaskBeanWhereId(mAdapter.getData().get(position).getNumid());
//删除该任务id下的资产
                                        new DbManager().delAssetsBeanWhereId(mAdapter.getData().get(position).getId());
                                        getDate();
                                    }
                                })
                                .show();
                        break;
                    case R.id.check_box:
                        TaskBean myLive = mAdapter.getData().get(position);
                        boolean isSelect = myLive.isSelect();
                        if (!isSelect) {
                            index++;
                            myLive.setSelect(true);
                            if (index == mAdapter.getData().size()) {
                                isSelectAll = true;
                                mSelectAll.setText("取消全选");
                            }

                        } else {
                            myLive.setSelect(false);
                            index--;
                            isSelectAll = false;
                            mSelectAll.setText("全选");
                        }
                        setBtnBackground(index);
//                        mTvSelectNum.setText(String.valueOf(index));
                        mAdapter.notifyDataSetChanged();
                        break;
                    default:
                }
            }
        });

    }

    /**
     * 全选和反选
     */
    private void selectAllMain() {
        if (mAdapter == null) {
            return;
        }
        if (!isSelectAll) {
            for (int i = 0, j = mAdapter.getData().size(); i < j; i++) {
                mAdapter.getData().get(i).setSelect(true);
            }
            index = mAdapter.getData().size();
            mBtnDelete.setEnabled(true);
            mSelectAll.setText("取消全选");
            isSelectAll = true;
        } else {
            for (int i = 0, j = mAdapter.getData().size(); i < j; i++) {
                mAdapter.getData().get(i).setSelect(false);
            }
            index = 0;
            mBtnDelete.setEnabled(false);
            mSelectAll.setText("全选");
            isSelectAll = false;
        }
        mAdapter.notifyDataSetChanged();
        setBtnBackground(index);
//        mTvSelectNum.setText(String.valueOf(index));
    }

    /**
     * 删除逻辑
     */
    private void deleteVideo() {
        if (index == 0) {
            mBtnDelete.setEnabled(false);
            return;
        }
        new AppDialog(_mActivity, DialogType.DEFAULT).setContent("删除后不可恢复，是否删除这" + index + "个条目？")
                .setLeftButton("取消", new AppDialog.OnButtonClickListener() {
                    @Override
                    public void onClick(String val) {
                    }
                })
                .setRightButton("确定", new AppDialog.OnButtonClickListener() {
                    @Override
                    public void onClick(String val) {
                        for (int i = mAdapter.getData().size(), j = 0; i > j; i--) {
                            TaskBean myLive = mAdapter.getData().get(i - 1);
                            if (myLive.isSelect()) {
                                new DbManager().delTaskBeanWhereId(mAdapter.getData().get(i-1).getNumid());
//删除该任务id下的资产
                                new DbManager().delAssetsBeanWhereId(mAdapter.getData().get(i-1).getId());
                                mAdapter.getData().remove(myLive);
                                index--;
                            }
                        }
                        index = 0;
//                        mTvSelectNum.setText(String.valueOf(0));
                        setBtnBackground(index);
                        if (mAdapter.getData().size() == 0) {
                            mLlMycollectionBottomDialog.setVisibility(View.GONE);
                        }
                        mAdapter.notifyDataSetChanged();

                        getDate();
                    }
                })
                .show();

    }

    /**
     * 根据选择的数量是否为0来判断按钮的是否可点击.
     *
     * @param size
     */
    private void setBtnBackground(int size) {
        if (size != 0) {
            mBtnDelete.setBackgroundResource(R.drawable.button_shape);
            mBtnDelete.setEnabled(true);
            mBtnDelete.setTextColor(Color.WHITE);
        } else {
            mBtnDelete.setBackgroundResource(R.drawable.button_noclickable_shape);
            mBtnDelete.setEnabled(false);
            mBtnDelete.setTextColor(ContextCompat.getColor(_mActivity, R.color.color_b7b8bd));
        }
    }

    @OnClick({R.id.btn_delete, R.id.select_all})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_delete:
                deleteVideo();
                break;
            case R.id.select_all:
                selectAllMain();
                break;
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
}
