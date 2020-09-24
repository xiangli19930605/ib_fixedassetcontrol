package me.jessyan.armscomponent.commonsdk.app;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.multidex.MultiDex;
import android.support.v7.app.AppCompatDelegate;

import com.jess.arms.base.BaseApplication;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.DefaultRefreshFooterCreator;
import com.scwang.smartrefresh.layout.api.DefaultRefreshHeaderCreator;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;

import javax.inject.Inject;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import me.jessyan.armscomponent.commonsdk.R;
import me.jessyan.armscomponent.commonsdk.data.AppDataManager;
import me.jessyan.armscomponent.commonsdk.data.ApplicationComponent;
import me.jessyan.armscomponent.commonsdk.data.ApplicationModule;
import me.jessyan.armscomponent.commonsdk.data.DaggerApplicationComponent;
import me.jessyan.armscomponent.commonsdk.greendao.DaoMaster;
import me.jessyan.armscomponent.commonsdk.greendao.DaoSession;
import me.jessyan.armscomponent.commonsdk.manage.ActivityManage;
import me.jessyan.armscomponent.commonsdk.utils.ServiceUtils;
import me.jessyan.armscomponent.commonsdk.utils.ToastUtil;
import me.yokeyword.fragmentation.Fragmentation;
import me.yokeyword.fragmentation.helper.ExceptionHandler;
/**
 * Describe：基础Application所有需要模块化开发的module都需要继承自BaseApplication
 * Created by 吴天强 on 2018/10/12.
 */
public class MyApplication extends BaseApplication  {

    //全局唯一的context
    public static BaseApplication application;

    //Activity管理器
    private ActivityManage activityManage;

    public static ApplicationComponent mComponent;

    @Inject
    AppDataManager appDataManager;
    private static Context context;
    public static Context getContext(){
        return context;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        application = this;
    }

    private void initFragmentation() {
        Fragmentation.builder()
                // 设置 栈视图 模式为 （默认）悬浮球模式   SHAKE: 摇一摇唤出  NONE：隐藏， 仅在Debug环境生效
                .stackViewMode(Fragmentation.BUBBLE)
                .debug(true) // 实际场景建议.debug(BuildConfig.DEBUG)
                /**
                 * 可以获取到{@link me.yokeyword.fragmentation.exception.AfterSaveStateTransactionWarning}
                 * 在遇到After onSaveInstanceState时，不会抛出异常，会回调到下面的ExceptionHandler
                 */
                .handleException(new ExceptionHandler() {
                    @Override
                    public void onException(Exception e) {
                        // 以Bugtags为例子: 把捕获到的 Exception 传到 Bugtags 后台。
                        // Bugtags.sendException(e);
                    }
                })
                .install();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context=getApplicationContext();
        activityManage = new ActivityManage();
        //MultiDex分包方法 必须最先初始化
        MultiDex.install(this);
//        initFragmentation();
//         DaoManager.getInstance().getDaoMaster();
//        setupDatabase();

        mComponent = DaggerApplicationComponent.builder().applicationModule(new ApplicationModule()).build();
        mComponent.inject(this);
    }

    /**
     * 配置数据库
     */
    private void setupDatabase() {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "record.db", null);
        SQLiteDatabase db = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
    }
    private static DaoSession daoSession;
    public static DaoSession getDaoSession() {
        return daoSession;
    }
    /**
     * 程序终止的时候执行
     */
    @Override
    public void onTerminate() {
        super.onTerminate();
        exitApp();
    }

    /**
     * 获取全局唯一上下文
     *
     * @return BaseApplication
     */
    public static BaseApplication getInstance() {
        return application;
    }


    /**
     * 退出应用
     */
    public void exitApp() {
        activityManage.finishAll();
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(0);
    }

    /**
     * 返回Activity管理器
     */
    public ActivityManage getActivityManage() {
        if (activityManage == null) {
            activityManage = new ActivityManage();
        }
        return activityManage;
    }

    //static 代码段可以防止内存泄露
    static {
        AppCompatDelegate.setDefaultNightMode(
                AppCompatDelegate.MODE_NIGHT_AUTO);
        //设置全局的Header构建
        SmartRefreshLayout.setDefaultRefreshHeaderCreator(new DefaultRefreshHeaderCreator() {
            @Override
            public RefreshHeader createRefreshHeader(Context context, RefreshLayout layout) {
                layout.setPrimaryColorsId(R.color.colorPrimary, android.R.color.white);//全局设置主题颜色
                return new ClassicsHeader(context);//.setTimeFormat(new DynamicTimeFormat("更新于 %s"));//指定为经典Header，默认是 贝塞尔雷达Header
            }
        });
        //设置全局的Footer构建器
        SmartRefreshLayout.setDefaultRefreshFooterCreator(new DefaultRefreshFooterCreator() {
            @Override
            public RefreshFooter createRefreshFooter(Context context, RefreshLayout layout) {
                //指定为经典Footer，默认是 BallPulseFooter
                return new ClassicsFooter(context).setDrawableSize(20);
            }
        });
    }


}