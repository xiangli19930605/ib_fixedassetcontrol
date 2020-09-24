package me.jessyan.armscomponent.commonsdk.base.fragment;

import com.jess.arms.mvp.BasePresenter;


/**
 * 有自定义ActionBar、NormalView
 *
 * @author quchao
 * @date 2018/3/30
 */

public abstract class BaseRootFragment<P extends BasePresenter> extends  BaseActionBarFragment<P>   {
    /**
     * 是否需要ActionBar
     * TODO 暂时用此方法 后续优化
     */
    protected boolean isActionBar() {
        return true;
    }
    protected boolean isNormalView() {
        return true;
    }

}