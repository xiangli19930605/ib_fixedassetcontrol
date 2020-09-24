package com.idealbank.ib_secretassetcontrol.di.module;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.idealbank.ib_secretassetcontrol.mvp.ui.adapter.HomeCurrentAdapter;
import com.jess.arms.di.scope.FragmentScope;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import me.jessyan.armscomponent.commonsdk.bean.Historyrecord.TaskBean;

import com.idealbank.ib_secretassetcontrol.mvp.contract.HomeContract;
import com.idealbank.ib_secretassetcontrol.mvp.model.HomeModel;

import java.util.ArrayList;
import java.util.List;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 06/10/2019 16:15
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@Module
public abstract class HomeModule {

    @Binds
    abstract HomeContract.Model bindHomeModel(HomeModel model);

    @FragmentScope
    @Provides
    static List<TaskBean> provideList() {
        return new ArrayList<>();
    }

    @FragmentScope
    @Provides
    static HomeCurrentAdapter provideCurrentInventoryAdapter(List<TaskBean> list) {
        return new HomeCurrentAdapter(list);
    }
}