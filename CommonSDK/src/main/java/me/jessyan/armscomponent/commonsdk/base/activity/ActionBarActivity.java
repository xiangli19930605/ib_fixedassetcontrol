package me.jessyan.armscomponent.commonsdk.base.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.view.WindowManager;

import com.gyf.barlibrary.ImmersionBar;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.LogUtils;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.jessyan.armscomponent.commonsdk.R;
import me.jessyan.armscomponent.commonsdk.utils.EventBusUtils;
import me.jessyan.armscomponent.commonsdk.widget.ActionBar;


/**
 * Describe：所有带actionBar的Activity基类
 * Created by 吴天强 on 2018/10/18.
 */

public abstract class ActionBarActivity<P extends BasePresenter> extends BaseActivity<P> {

    private ViewStub emptyView;
    protected Context mContext;
    ActionBar actionBar;
    protected ImmersionBar mImmersionBar;
    private Unbinder mUnbinder;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        int layoutResID = initView(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        if (isActionBar()) {
            setContentView(R.layout.activity_base);
            ((ViewGroup) findViewById(R.id.fl_content)).addView(getLayoutInflater().inflate( layoutResID, null));
            actionBar= findViewById(R.id.actionbar);

            ImmersionBar.setTitleBar(this, actionBar);
            View statusBarView = findViewById(0);
            ImmersionBar.setStatusBarView(this, statusBarView);
        } else {
            setContentView( layoutResID);
        }
        //初始化ButterKnife
        mUnbinder = ButterKnife.bind(this);

        //沉浸式状态栏
        initImmersionBar(0);

        //加入Activity管理器
//        BaseApplication.getApplication().getActivityManage().addActivity(this);
        if (regEvent()) {
            EventBusUtils.register(this);
        }
//        loadingDialog = new LoadingDialog(mContext);


        initData(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mImmersionBar!=null)
            mImmersionBar.destroy();
    }
    /**
     * 沉浸栏颜色
     */
    protected void initImmersionBar(int color) {
        mImmersionBar = ImmersionBar.with(this);
        if (color != 0) {
            mImmersionBar.statusBarColor(color);
        }
        mImmersionBar.init();
    }
    protected void setTitleText(String title) {
//        LogUtils.warnInfo(title);
        if (actionBar != null) {
            actionBar.setCenterText(title);
        }
    }

    protected void setTitleText(int title) {
        if (actionBar != null) {
            actionBar.setCenterText(getString(title));
        }
    }

    /**
     * 是否需要ActionBar
     * TODO 暂时用此方法 后续优化
     */
    protected boolean isActionBar() {
        return true;
    }

    /**
     * 需要接收事件 重写该方法 并返回true
     */
    protected boolean regEvent() {
        return false;
    }
}
