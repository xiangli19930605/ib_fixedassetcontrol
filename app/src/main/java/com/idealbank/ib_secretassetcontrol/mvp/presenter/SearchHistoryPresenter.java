package com.idealbank.ib_secretassetcontrol.mvp.presenter;

import android.app.Application;

import com.jess.arms.integration.AppManager;
import com.jess.arms.di.scope.FragmentScope;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.http.imageloader.ImageLoader;

import me.jessyan.armscomponent.commonsdk.app.MyApplication;
import me.jessyan.armscomponent.commonsdk.bean.Historyrecord.HistoryData;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;

import javax.inject.Inject;

import com.idealbank.ib_secretassetcontrol.mvp.contract.SearchHistoryContract;

import java.util.Collections;
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
@FragmentScope
public class SearchHistoryPresenter extends BasePresenter<SearchHistoryContract.Model, SearchHistoryContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;
    @Inject
    List<HistoryData> mList;
    @Inject
    public SearchHistoryPresenter(SearchHistoryContract.Model model, SearchHistoryContract.View rootView) {
        super(model, rootView);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
        this.mAppManager = null;
        this.mImageLoader = null;
        this.mApplication = null;
    }
    public void loadAllHistoryData() {
        mList= mModel.loadAllHistoryData();
        Collections.reverse(mList);
        mRootView.loadAllHistoryData(mList);

//        Observable.create((ObservableOnSubscribe<List<HistoryData>>) e -> {
//            List<HistoryData> historyDataList = (List<HistoryData>)
//                    MyApplication.mComponent.getAppDataManager().loadAllHistoryData();
//            e.onNext(historyDataList);
//        })
//                .compose(RxUtil.rxSchedulerHelper())
//                .subscribe(historyDataList ->
////                        mList=historyDataList
////                        mAdapter.replaceData(mList);
//                        mRootView.loadAllHistoryData(historyDataList)
// );
    }

    public void addHistoryData(String data) {
        MyApplication.mComponent.getAppDataManager().addHistoryData(data);
        loadAllHistoryData();
    }

    public void clearHistoryData() {
        MyApplication.mComponent.getAppDataManager().clearHistoryData();
        loadAllHistoryData();
    }
}
