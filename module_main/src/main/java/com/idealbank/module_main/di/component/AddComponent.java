package com.idealbank.module_main.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.idealbank.module_main.di.module.AddModule;
import com.idealbank.module_main.mvp.contract.AddContract;

import com.jess.arms.di.scope.ActivityScope;
import com.idealbank.module_main.mvp.ui.activity.AddActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 10/09/2019 10:45
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = AddModule.class, dependencies = AppComponent.class)
public interface AddComponent {
    void inject(AddActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        AddComponent.Builder view(AddContract.View view);

        AddComponent.Builder appComponent(AppComponent appComponent);

        AddComponent build();
    }
}