package com.idealbank.ib_secretassetcontrol.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.idealbank.ib_secretassetcontrol.di.module.SearchModule;
import com.idealbank.ib_secretassetcontrol.mvp.contract.SearchContract;

import com.jess.arms.di.scope.FragmentScope;
import com.idealbank.ib_secretassetcontrol.mvp.ui.fragment.SearchFragment;


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
@Component(modules = SearchModule.class, dependencies = AppComponent.class)
public interface SearchComponent {
    void inject(SearchFragment fragment);

    @Component.Builder
    interface Builder {
        @BindsInstance
        SearchComponent.Builder view(SearchContract.View view);

        Builder appComponent(AppComponent appComponent);

        SearchComponent build();
    }
}