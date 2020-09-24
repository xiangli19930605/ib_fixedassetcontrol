package com.idealbank.ib_secretassetcontrol.mvp.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.idealbank.ib_secretassetcontrol.mvp.ui.fragment.ChildCheckFragment;
import com.idealbank.ib_secretassetcontrol.mvp.ui.fragment.ChildNoCheckFragment;

import me.jessyan.armscomponent.commonsdk.bean.Historyrecord.TaskBean;


/**
 * Created by YoKeyword on 16/6/5.
 */
public class WechatPagerFragmentAdapter extends FragmentPagerAdapter {
    private String[] mTitles={"未盘点","已盘点"};
    TaskBean taskBean;
    public WechatPagerFragmentAdapter(FragmentManager fm, TaskBean taskBean) {
        super(fm);
        this.taskBean = taskBean;
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return ChildNoCheckFragment.newInstance(taskBean);
        } else {
            return ChildCheckFragment.newInstance(taskBean);
        }
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles[position];
    }
}
