package com.idealbank.ib_secretassetcontrol.di.module;

import com.idealbank.ib_secretassetcontrol.mvp.ui.adapter.SearchAssetAdapter;
import com.jess.arms.di.scope.FragmentScope;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import me.jessyan.armscomponent.commonsdk.bean.Historyrecord.AssetsBean;
import me.jessyan.armscomponent.commonsdk.bean.Historyrecord.HistoryData;

import com.idealbank.ib_secretassetcontrol.mvp.contract.SearchHistoryContract;
import com.idealbank.ib_secretassetcontrol.mvp.model.SearchHistoryModel;

import java.util.ArrayList;
import java.util.List;


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
@Module
public abstract class SearchHistoryModule {

    @Binds
    abstract SearchHistoryContract.Model bindSearchHistoryModel(SearchHistoryModel model);

    @FragmentScope
    @Provides
    static List<HistoryData> provideHistoryDataList() {
        return new ArrayList<>();
    }
    @FragmentScope
    @Provides
    static List<AssetsBean> provideAssetsBeanList() {
        return new ArrayList<>();
    }

    @FragmentScope
    @Provides
    static SearchAssetAdapter provideAssetAdapter(List<AssetsBean> list) {
        return new SearchAssetAdapter(list);
    }
}