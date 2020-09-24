package com.idealbank.ib_secretassetcontrol.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.idealbank.ib_secretassetcontrol.di.module.ChildCheckModule;
import com.idealbank.ib_secretassetcontrol.mvp.contract.ChildCheckContract;

import com.jess.arms.di.scope.FragmentScope;
import com.idealbank.ib_secretassetcontrol.mvp.ui.fragment.ChildCheckFragment;


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
@FragmentScope
@Component(modules = ChildCheckModule.class, dependencies = AppComponent.class)
public interface ChildCheckComponent {
    void inject(ChildCheckFragment fragment);

    @Component.Builder
    interface Builder {
        @BindsInstance
        ChildCheckComponent.Builder view(ChildCheckContract.View view);

        Builder appComponent(AppComponent appComponent);

        ChildCheckComponent build();
    }
}