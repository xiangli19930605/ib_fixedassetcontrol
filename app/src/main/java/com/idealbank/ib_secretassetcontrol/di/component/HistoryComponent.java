package com.idealbank.ib_secretassetcontrol.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.idealbank.ib_secretassetcontrol.di.module.HistoryModule;
import com.idealbank.ib_secretassetcontrol.mvp.contract.HistoryContract;

import com.jess.arms.di.scope.FragmentScope;
import com.idealbank.ib_secretassetcontrol.mvp.ui.fragment.HistoryFragment;


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
@FragmentScope
@Component(modules = HistoryModule.class, dependencies = AppComponent.class)
public interface HistoryComponent {
    void inject(HistoryFragment fragment);

    @Component.Builder
    interface Builder {
        @BindsInstance
        HistoryComponent.Builder view(HistoryContract.View view);

        Builder appComponent(AppComponent appComponent);

        HistoryComponent build();
    }
}