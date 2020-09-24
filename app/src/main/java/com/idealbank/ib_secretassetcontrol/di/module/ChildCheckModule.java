package com.idealbank.ib_secretassetcontrol.di.module;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.idealbank.ib_secretassetcontrol.mvp.ui.adapter.AssetAdapter;
import com.jess.arms.di.scope.FragmentScope;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import me.jessyan.armscomponent.commonsdk.bean.Historyrecord.AssetsBean;

import com.idealbank.ib_secretassetcontrol.mvp.contract.ChildCheckContract;
import com.idealbank.ib_secretassetcontrol.mvp.model.ChildCheckModel;

import java.util.ArrayList;
import java.util.List;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 06/17/2019 12:22
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@Module
public abstract class ChildCheckModule {

    @Binds
    abstract ChildCheckContract.Model bindChildCheckModel(ChildCheckModel model);

    @FragmentScope
    @Provides
    static List<AssetsBean> provideList() {
        return new ArrayList<>();
    }

    @FragmentScope
    @Provides
    static AssetAdapter provideAssetAdapter(List<AssetsBean> list) {
        return new AssetAdapter(list);
    }



}