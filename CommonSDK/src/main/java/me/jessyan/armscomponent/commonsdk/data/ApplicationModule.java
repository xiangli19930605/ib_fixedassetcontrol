package me.jessyan.armscomponent.commonsdk.data;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import me.jessyan.armscomponent.commonsdk.data.db.DbHelper;
import me.jessyan.armscomponent.commonsdk.data.db.DbHelperImpl;
import me.jessyan.armscomponent.commonsdk.data.http.HttpHelper;
import me.jessyan.armscomponent.commonsdk.data.http.HttpHelperImpl;
import me.jessyan.armscomponent.commonsdk.data.prefs.PreferenceHelper;
import me.jessyan.armscomponent.commonsdk.data.prefs.PreferenceHelperImpl;

/**
 * @author flymegoc
 * @date 2018/2/4
 */
@Module
public class ApplicationModule {


    @Provides
    @Singleton
    AppDataManager provideAppDataManager( DbHelper appDbHelper  , PreferenceHelper mPreferencesHelper,HttpHelper HttpHelper ) {
        return new AppDataManager(appDbHelper,mPreferencesHelper,HttpHelper);
    }
    @Provides
    @Singleton
    DbHelper provideDbHelper(DbHelperImpl appDbHelper) {
        return  appDbHelper;
    }
    @Provides
    @Singleton
    HttpHelper provideHttpHelperImpl(HttpHelperImpl HttpHelperImpl) {
        return HttpHelperImpl;
    }
    @Provides
    @Singleton
    PreferenceHelper providesPreferenceHelper(PreferenceHelperImpl mPreferencesHelper) {
        return mPreferencesHelper;
    }

}
