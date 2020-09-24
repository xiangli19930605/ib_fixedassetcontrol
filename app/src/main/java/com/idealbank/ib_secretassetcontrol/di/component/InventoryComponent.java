package com.idealbank.ib_secretassetcontrol.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.idealbank.ib_secretassetcontrol.di.module.InventoryModule;
import com.idealbank.ib_secretassetcontrol.mvp.contract.InventoryContract;

import com.jess.arms.di.scope.FragmentScope;
import com.idealbank.ib_secretassetcontrol.mvp.ui.fragment.InventoryFragment;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 06/10/2019 16:06
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@FragmentScope
@Component(modules = InventoryModule.class, dependencies = AppComponent.class)
public interface InventoryComponent {
    void inject(InventoryFragment fragment);

    @Component.Builder
    interface Builder {
        @BindsInstance
        InventoryComponent.Builder view(InventoryContract.View view);

        Builder appComponent(AppComponent appComponent);

        InventoryComponent build();
    }
}