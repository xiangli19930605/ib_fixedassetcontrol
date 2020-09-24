package me.jessyan.armscomponent.commonsdk.app;



import javax.inject.Singleton;

import dagger.Component;
import me.jessyan.armscomponent.commonsdk.data.AppDataManager;
import me.jessyan.armscomponent.commonsdk.data.ApplicationModule;

/**
 * @author flymegoc
 * @date 2018/2/4
 */
@Singleton
@Component( modules = {ApplicationModule.class})
public interface ApplicationComponent {

    void inject(MyApplication wanAndroidApp);

    AppDataManager getAppDataManager();


}
