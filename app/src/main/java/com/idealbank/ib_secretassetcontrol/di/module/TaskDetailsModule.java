package com.idealbank.ib_secretassetcontrol.di.module;

import com.jess.arms.di.scope.FragmentScope;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

import com.idealbank.ib_secretassetcontrol.mvp.contract.TaskDetailsContract;
import com.idealbank.ib_secretassetcontrol.mvp.model.TaskDetailsModel;


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
public abstract class TaskDetailsModule {

    @Binds
    abstract TaskDetailsContract.Model bindTaskDetailsModel(TaskDetailsModel model);
}