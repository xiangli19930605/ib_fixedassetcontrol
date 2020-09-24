package me.jessyan.armscomponent.commonsdk.base.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.base.BaseFragment;
import com.jess.arms.mvp.BasePresenter;

import me.jessyan.armscomponent.commonsdk.R;
import me.yokeyword.fragmentation.ISupportActivity;
import me.yokeyword.fragmentation.ISupportFragment;


/**
 * @author quchao
 * @date 2018/3/30
 */

public abstract class BaseRootActivity<T extends BasePresenter> extends BaseActivity<T> implements ISupportActivity {

    private static final int NORMAL_STATE = 0;
    private static final int LOADING_STATE = 1;
    public static final int ERROR_STATE = 2;

    private LottieAnimationView mLoadingAnimation;
    private View mErrorView;
    private View mLoadingView;
    private ViewGroup mNormalView;

    private int currentState = NORMAL_STATE;


//        protected void initView() {
//        Log.e("测试2","2");
//        if (getView() == null) {
//            return ;
//        }
//        mNormalView = getView().findViewById(R.id.normal_view);
//        if (mNormalView == null) {
//            throw new IllegalStateException(
//                    "The subclass of RootActivity must contain a View named 'mNormalView'.");
//        }
//        if (!(mNormalView.getParent() instanceof ViewGroup)) {
//            throw new IllegalStateException(
//                    "mNormalView's ParentView should be a ViewGroup.");
//        }
//        ViewGroup parent = (ViewGroup) mNormalView.getParent();
//        View.inflate(_mActivity, R.layout.loading_view, parent);
//        View.inflate(_mActivity, R.layout.error_view, parent);
//        mLoadingView = parent.findViewById(R.id.loading_group);
//        mErrorView = parent.findViewById(R.id.error_group);
//        TextView reloadTv = mErrorView.findViewById(R.id.error_reload_tv);
//        reloadTv.setOnClickListener(v -> reload());
//        mLoadingAnimation = mLoadingView.findViewById(R.id.loading_animation);
//        mErrorView.setVisibility(View.GONE);
//        mLoadingView.setVisibility(View.GONE);
//        mNormalView.setVisibility(View.VISIBLE);
//    }
//
//    @Override
//    public void onDestroyView() {
//        if (mLoadingAnimation != null) {
//            mLoadingAnimation.cancelAnimation();
//        }
//        super.onDestroyView();
//    }

    public void showLoading() {
        if (currentState == LOADING_STATE || mLoadingView == null) {
            return;
        }
        hideCurrentView();
        currentState = LOADING_STATE;
        mLoadingView.setVisibility(View.VISIBLE);
        mLoadingAnimation.setAnimation("loading_bus.json");
        mLoadingAnimation.loop(true);
        mLoadingAnimation.playAnimation();
    }

    public void showError() {
        if (currentState == ERROR_STATE) {
            return;
        }
        hideCurrentView();
        currentState = ERROR_STATE;
        mErrorView.setVisibility(View.VISIBLE);
    }

    public void showNormal() {
        if (currentState == NORMAL_STATE) {
            return;
        }
        hideCurrentView();
        currentState = NORMAL_STATE;
        mNormalView.setVisibility(View.VISIBLE);
    }
    public void reload() {

    }

    private void hideCurrentView() {
        switch (currentState) {
            case NORMAL_STATE:
                if (mNormalView == null) {
                    return;
                }
                mNormalView.setVisibility(View.INVISIBLE);
                break;
            case LOADING_STATE:
                mLoadingAnimation.cancelAnimation();
                mLoadingView.setVisibility(View.GONE);
                break;
            case ERROR_STATE:
                mErrorView.setVisibility(View.GONE);
            default:
                break;
        }
    }


}
