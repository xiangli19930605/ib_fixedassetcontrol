package com.idealbank.ib_secretassetcontrol.mvp.ui.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.allen.library.SuperTextView;
import com.idealbank.ib_secretassetcontrol.app.AppApplication;
import com.idealbank.ib_secretassetcontrol.app.DbManager;
import com.idealbank.ib_secretassetcontrol.app.rfid_module.ONDEVMESSAGE;
import com.idealbank.ib_secretassetcontrol.mvp.ui.adapter.AppBarStateChangeListener;
import com.idealbank.ib_secretassetcontrol.mvp.ui.adapter.WechatPagerFragmentAdapter;
import com.idealbank.ib_secretassetcontrol.mvp.ui.view.CircularProgressView;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import com.idealbank.ib_secretassetcontrol.di.component.DaggerTaskDetailsComponent;
import com.idealbank.ib_secretassetcontrol.mvp.contract.TaskDetailsContract;
import com.idealbank.ib_secretassetcontrol.mvp.presenter.TaskDetailsPresenter;

import com.idealbank.ib_secretassetcontrol.R;

import org.simple.eventbus.Subscriber;

import java.math.BigDecimal;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import me.jessyan.armscomponent.commonres.dialog.AppDialog;
import me.jessyan.armscomponent.commonres.dialog.DialogType;
import me.jessyan.armscomponent.commonsdk.app.MyApplication;
import me.jessyan.armscomponent.commonsdk.base.fragment.BaseFragment;
import me.jessyan.armscomponent.commonsdk.bean.Event;
import me.jessyan.armscomponent.commonsdk.bean.Historyrecord.AssetsBean;
import me.jessyan.armscomponent.commonsdk.bean.Historyrecord.TaskBean;
import me.jessyan.armscomponent.commonsdk.core.EventBusTags;
import me.jessyan.armscomponent.commonsdk.data.db.DataManager;
import me.jessyan.armscomponent.commonsdk.utils.EventBusUtils;
import me.jessyan.armscomponent.commonsdk.utils.ToastUtil;
import me.jessyan.autosize.utils.LogUtils;

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
public class TaskDetailsFragment extends BaseFragment<TaskDetailsPresenter> implements TaskDetailsContract.View {


    @BindView(R.id.tv_tips)
    ImageView tv_tips;
    @BindView(R.id.toolbar_tab)
    TabLayout tabLayout;
    @BindView(R.id.app_bar)
    AppBarLayout app_bar;
    @BindView(R.id.main_vp_container)
    ViewPager mViewPager;
    @BindView(R.id.circleProgressBar)
    CircularProgressView circleProgressBar;

    @BindView(R.id.tv_checked)
    TextView tv_checked;
    @BindView(R.id.tv_unchecked)
    TextView tv_unchecked;
    @BindView(R.id.img_checkType)
    ImageView img_checkType;
    @BindView(R.id.stv_curUser)
    SuperTextView stv_curUser;
    @BindView(R.id.stv_id)
    SuperTextView stv_id;
    @BindView(R.id.stv_name)
    SuperTextView stv_name;
    @BindView(R.id.stv_createName)
    SuperTextView stv_createName;

    @BindView(R.id.cb_check)
    CheckBox cbCheck;
    private static Long numid;
    private static TaskBean taskBean;

    public ONDEVMESSAGE OnMsg = new ONDEVMESSAGE();

    public static TaskDetailsFragment newInstance(Long id) {
        TaskDetailsFragment fragment = new TaskDetailsFragment();
        numid = id;
        return fragment;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerTaskDetailsComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_task_details, container, false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {

        AppBarStateChangeListener listener = new AppBarStateChangeListener();
        listener.setOnStateChangedListener(new AppBarStateChangeListener.OnStateChangedListener() {
            @Override
            public void onExpanded() {
                tv_tips.setImageResource(R.mipmap.ic_expanded);
            }

            @Override
            public void onCollapsed() {
                Log.i(TAG, "onCollapsed: =");
                tv_tips.setImageResource(R.mipmap.ic_collapsed);
//                AppBarLayout.LayoutParams lp = new AppBarLayout.LayoutParams(tv_tips.getLayoutParams());
//                lp.setMargins(0, 0, 0, 0);
//                tv_tips.setLayoutParams(lp);
            }

            @Override
            public void onInternediateFromExpand() {
                tv_tips.setImageResource(R.mipmap.ic_expanded);
            }

            @Override
            public void onInternediateFromCollapsed() {
                tv_tips.setImageResource(R.mipmap.ic_expanded);
            }

            @Override
            public void onInternediate() {

            }
        });
        app_bar.addOnOffsetChangedListener(listener);
        time = new TimeCount(15000, 1000);
        setView();

//开启服务
//        boolean b = AppApplication.sv_Main.Create(OnMsg);
//        //先关闭盘点
//        AppApplication.sv_Main.DoInventoryTag(false);
//        // rfid power on
//        AppApplication. sv_Main.gpio_rfid_config(true);
    }

    private void setView() {
        //先查询taskBean
        taskBean = new DbManager().queryTaskBeanWhereUid(numid);
        if (taskBean.getCheckType() == 0) {
            //责任人
            img_checkType.setImageResource(R.mipmap.ic_type_person);
            stv_curUser.setLeftString("负责人：").setCenterString(taskBean.getCurUser()).setLeftTvDrawableLeft(_mActivity.getResources().getDrawable(
                    R.mipmap.ic_person));
        } else {
            //部门
            img_checkType.setImageResource(R.mipmap.ic_type_department);
            stv_curUser.setLeftString("部    门：").setCenterString(taskBean.getBelongDeptName()).setLeftTvDrawableLeft(_mActivity.getResources().getDrawable(
                    R.mipmap.department));
        }
        stv_id.setCenterString(taskBean.getId());
        stv_name.setLeftString(taskBean.getCheckName());
        stv_createName.setCenterString(taskBean.getCreateName());


        mViewPager.setAdapter(new WechatPagerFragmentAdapter(getChildFragmentManager(), taskBean));
        tabLayout.setupWithViewPager(mViewPager);

        handler.sendEmptyMessageDelayed(0, 100);

        cbCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                LogUtils.e("isChecked:"+isChecked);
                if (isChecked) {
                    openRfid();
                    cbCheck.setText("停止盘点");
                    time.start();
                } else {
                    cbCheck.setText("开始盘点");
                    closeRfid();
                    time.onFinish();
                }
            }
        });

    }
    private TimeCount time;

    class TimeCount extends CountDownTimer {
        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            if (cbCheck != null && cbCheck.isChecked()) {
                cbCheck.setText("停止盘点倒计时" + "(" + millisUntilFinished / 1000 + ") ");
            }
        }

        @Override
        public void onFinish() {
            if (cbCheck != null) {
                cbCheck.setText("开始盘点");
                cbCheck.setChecked(false);
            }
//            closeRfid();
            AppApplication.sv_Main.DoInventoryTag(false);
        }
    }
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            AppApplication.sv_Main.DoInventoryTag(true);
            //更新查询taskBean状态   顶部完成率状态UI更新
            taskBean = new DbManager().queryTaskBeanWhereUid(numid);
            circleProgressBar.setProgress((int) ((new BigDecimal((float) taskBean.getCheckNum() / taskBean.getTotal()).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue()) * 100));
            circleProgressBar.setTotalText("" + taskBean.getTotal());
            circleProgressBar.setFinishTextt("" + circleProgressBar.getProgress());
            tv_checked.setText("已盘：" + taskBean.getCheckNum());
            tv_unchecked.setText("未盘：" + (taskBean.getTotal() - taskBean.getCheckNum()));

        }
    };

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


    @OnClick({R.id.public_toolbar_back, R.id.public_toolbar_right})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.public_toolbar_back:
                pop();
                break;
            case R.id.public_toolbar_right:

//                new AppDialog(_mActivity, DialogType.ADDASSET)
//                        .setTitle("修改状态")
//                        .setLeftButton("设置为已盘", new AppDialog.OnButtonClickListener() {
//                            @Override
//                            public void onClick(String val) {
////先模糊查询FRID编号，得到的资产ID；再通过资产ID，修改资产状态
//                                AssetsBean assetsBean = new DbManager().queryAssetsBeanWhereRfid(val);
//                                new DbManager().upDateAssetsBeanStateWhereId(assetsBean.getId(), 1);
//                                //获取数据库中已盘数量+1
//                                int check = new DbManager().queryTaskBeanWhereUid(taskBean.getNumid()).getCheckNum() + 1;
////                                setProgress(check);
//                                handler.sendEmptyMessageDelayed(0, 100);
//                                new DbManager().upDateTaskBeanWhereId(taskBean.getNumid(), taskBean.getId(), check);
//                                EventBusUtils.sendEvent(new Event(""), EventBusTags.CHECK);
//                            }
//                        })
//                        .setRightButton("设置为未盘", new AppDialog.OnButtonClickListener() {
//                            @Override
//                            public void onClick(String val) {
//                                AssetsBean assetsBean = new DbManager().queryAssetsBeanWhereRfid(val);
//                                new DbManager().upDateAssetsBeanStateWhereId(assetsBean.getId(), 0);
//                                //获取数据库中已盘数量-1
//                                int check = new DbManager().queryTaskBeanWhereUid(taskBean.getNumid()).getCheckNum() - 1;
//                                new DbManager().upDateTaskBeanWhereId(taskBean.getNumid(), taskBean.getId(), check);
//                                handler.sendEmptyMessageDelayed(0, 100);
//                                EventBusUtils.sendEvent(new Event(""), EventBusTags.CHECK);
//                            }
//                        })
//                        .show();

                start(SearchHistoryFragment.newInstance(taskBean.getId()));
                break;

        }

    }

    @Subscriber(tag = EventBusTags.CHECK)
    private void updateUser(Event event) {
        handler.sendEmptyMessageDelayed(0, 100);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        //关闭服务
        closeRfid();
        EventBusUtils.sendEvent(new Event(""), EventBusTags.REFRESH);
    }
    private void openRfid() {
        //开启服务
        boolean b = AppApplication.sv_Main.Create(OnMsg);
        //先关闭盘点
        AppApplication.sv_Main.DoInventoryTag(false);
        // rfid power on
        AppApplication.sv_Main.gpio_rfid_config(true);

        AppApplication.sv_Main.DoInventoryTag(true);
        handler.sendMessageDelayed(new Message(),2000);

    }

    private void closeRfid() {
        try {
            AppApplication.sv_Main.DoInventoryTag(false);
            AppApplication.sv_Main.DoIndentify(false);
            AppApplication.sv_Main.gpio_rfid_config(false);
            AppApplication.sv_Main.Free();
        } catch (UnsupportedOperationException e) {
            System.out.print("******************");
        } catch (Exception e) {
            System.out.print("******************");
        }
    }

}
