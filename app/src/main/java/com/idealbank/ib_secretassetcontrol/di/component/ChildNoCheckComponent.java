package com.idealbank.ib_secretassetcontrol.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.idealbank.ib_secretassetcontrol.di.module.ChildNoCheckModule;
import com.idealbank.ib_secretassetcontrol.mvp.contract.ChildNoCheckContract;

import com.jess.arms.di.scope.FragmentScope;
import com.idealbank.ib_secretassetcontrol.mvp.ui.fragment.ChildNoCheckFragment;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 06/17/2019 12:24
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@FragmentScope
@Component(modules = ChildNoCheckModule.class, dependencies = AppComponent.class)
public interface ChildNoCheckComponent {
    void inject(ChildNoCheckFragment fragment);

    @Component.Builder
    interface Builder {
        @BindsInstance
        ChildNoCheckComponent.Builder view(ChildNoCheckContract.View view);

        Builder appComponent(AppComponent appComponent);

        ChildNoCheckComponent build();
    }
}