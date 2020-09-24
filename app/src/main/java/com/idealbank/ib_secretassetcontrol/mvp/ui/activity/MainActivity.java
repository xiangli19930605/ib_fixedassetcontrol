package com.idealbank.ib_secretassetcontrol.mvp.ui.activity;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.idealbank.ib_secretassetcontrol.app.service.SocketService;
import com.idealbank.ib_secretassetcontrol.mvp.ui.fragment.MainFragment;
import com.idealbank.ib_secretassetcontrol.mvp.ui.fragment.ScanAssetFragment;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import com.idealbank.ib_secretassetcontrol.di.component.DaggerMainComponent;
import com.idealbank.ib_secretassetcontrol.mvp.contract.MainContract;
import com.idealbank.ib_secretassetcontrol.mvp.presenter.MainPresenter;

import com.idealbank.ib_secretassetcontrol.R;
import com.jess.arms.utils.PermissionUtil;
import com.tbruyelle.rxpermissions2.RxPermissions;


import java.util.List;

import javax.inject.Inject;

import me.jessyan.armscomponent.commonres.dialog.AppDialog;
import me.jessyan.armscomponent.commonres.dialog.DialogType;
import me.jessyan.armscomponent.commonsdk.base.activity.BaseActivity;
import me.jessyan.armscomponent.commonsdk.base.activity.ScanActivity;
import me.jessyan.armscomponent.commonsdk.bean.Event;
import me.jessyan.armscomponent.commonsdk.core.EventBusTags;
import me.jessyan.armscomponent.commonsdk.utils.EventBusUtils;
import me.jessyan.armscomponent.commonsdk.utils.ServiceUtils;
import me.jessyan.armscomponent.commonsdk.utils.ToastUtil;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.yokeyword.fragmentation.ISupportFragment;
import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator;
import me.yokeyword.fragmentation.anim.FragmentAnimator;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 06/10/2019 15:53
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class MainActivity extends BaseActivity<MainPresenter> implements MainContract.View {

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerMainComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_main; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        if (findFragment(MainFragment.class) == null) {

            loadRootFragment(R.id.fl_container, MainFragment.newInstance());
            String page = getIntent().getStringExtra(EventBusTags.JUMP_PAGE);
            if (page != null && page.endsWith(EventBusTags.THREE)) {
                EventBusUtils.sendEvent(new Event(EventBusTags.THREE), EventBusTags.JUMP_PAGE);
            }
        }
        //同时请求多个权限
//       new RxPermissions(MainActivity.this)
//                .requestEach(
//                        Manifest.permission.READ_PHONE_STATE  ,
//                        Manifest.permission.CAMERA  ,
//                        Manifest.permission.READ_EXTERNAL_STORAGE ,
//                        Manifest.permission.WRITE_EXTERNAL_STORAGE )//多个权限用","隔开
//               .subscribe(new Consumer<Permission>() {
//                   @Override
//                   public void accept(Permission permission) throws Exception {
//                       if (permission.granted) {
//                           // 用户已经同意该权限
//                           LogUtils.debugInfo("testRxPermission CallBack onPermissionsGranted() : "+permission.name+
//                                   " request granted , to do something...");
//                           //todo somthing
//                       }
//                       else if (permission.shouldShowRequestPermissionRationale) {
//                           // 用户拒绝了该权限，没有选中『不再询问』（Never ask again）,那么下次再次启动时。还会提示请求权限的对话框
//                           LogUtils.debugInfo("testRxPermission CallBack onPermissionsDenied() : " + permission.name + "request denied");
//                           //todo request permission again
//                           ToastUtil.showToast("拒绝权限，等待下次询问哦");
//                       }
//                       else {
//                           // 用户拒绝了该权限，而且选中『不再询问』
//                           LogUtils.debugInfo("testRxPermission CallBack onPermissionsDenied() : this " + permission.name + " is denied " +
//                                   "and never ask again");
//                           ToastUtil.showToast( "拒绝权限，不再弹出询问框，请前往APP应用设置中打开此权限");
//                           //todo nothing
//                       }
//                   }
//               });


        PermissionUtil.requestPermission(new PermissionUtil.RequestPermission() {
                                             @Override
                                             public void onRequestPermissionSuccess() {
//                ToastUtil.showToast( "tongyi");
                                             }

                                             @Override
                                             public void onRequestPermissionFailure(List<String> permissions) {
                                                 ToastUtil.showToast("拒绝权限，等待下次询问哦");
                                                 new AppDialog(MainActivity.this, DialogType.DEFAULT).setTitle("权限申请").setContent("在设置-应用-微信-权限中开启，以正常使用功能")
                                                         .setLeftButton(new AppDialog.OnButtonClickListener() {
                                                             @Override
                                                             public void onClick(String val) {
                                                                 finish();
                                                             }
                                                         }).setRightButton(new AppDialog.OnButtonClickListener() {
                                                     @Override
                                                     public void onClick(String val) {
                                                         getOverlayPermission();
                                                     }
                                                 }).show();
                                             }

                                             @Override
                                             public void onRequestPermissionFailureWithAskNeverAgain(List<String> permissions) {
                                                 ToastUtil.showToast("拒绝权限，不再弹出询问框，请前往APP应用设置中打开此权限");
                                                 new AppDialog(MainActivity.this, DialogType.DEFAULT).setTitle("权限申请").setContent("在设置-应用-微信-权限中开启，以正常使用功能")
                                                         .setLeftButton(new AppDialog.OnButtonClickListener() {
                                                             @Override
                                                             public void onClick(String val) {
                                                                 finish();
                                                             }
                                                         }).setRightButton(new AppDialog.OnButtonClickListener() {
                                                     @Override
                                                     public void onClick(String val) {
                                                         getOverlayPermission();
                                                     }
                                                 }).show();

                                             }
                                         }, new RxPermissions(this), mErrorHandler, Manifest.permission.CAMERA,
                Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);
//        getOverlayPermission();
        if (ServiceUtils.isServiceRunning(this, "com.idealbank.module_main.app.service.SocketService")) {
            ToastUtil.showToast("服务已开启");
        } else {
            Intent intentFive = new Intent(this, SocketService.class);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                startForegroundService(intentFive);
            } else {
                startService(intentFive);
            }
        }
    }

    //请求悬浮窗权限
    @TargetApi(Build.VERSION_CODES.M)
    private void getOverlayPermission() {
        Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
        intent.setData(Uri.parse("package:" + getPackageName()));
        startActivityForResult(intent, 0);
    }

    @Inject
    RxErrorHandler mErrorHandler;
    // 再点一次退出程序时间设置
    private static final long WAIT_TIME = 2000L;
    private long TOUCH_TIME = 0;

    @Override
    public void onBackPressedSupport() {
        // 对于 4个类别的主Fragment内的回退back逻辑,已经在其onBackPressedSupport里各自处理了
        ISupportFragment topFragment = getTopFragment();
        // 主页的Fragment
        if (topFragment instanceof MainFragment) {
            //中有当前fragment在MainFragment时通知界面跳转
            if (MainFragment.newInstance().getCurrent() == 1 || MainFragment.newInstance().getCurrent() == 2 || MainFragment.newInstance().getCurrent() == 3) {
                EventBusUtils.sendEvent(new Event(EventBusTags.ZERO), EventBusTags.JUMP_PAGE);
                return;
            }
        }
        if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
            pop();
        } else {
            if (System.currentTimeMillis() - TOUCH_TIME < WAIT_TIME) {
                finish();
            } else {
                TOUCH_TIME = System.currentTimeMillis();
                Toast.makeText(this, "再按一次退出系统", Toast.LENGTH_SHORT).show();
            }


        }
    }

    @Override
    public FragmentAnimator onCreateFragmentAnimator() {
        // 设置横向(和安卓4.x动画相同)
        return new DefaultHorizontalAnimator();
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showMessage(@NonNull String message) {
        checkNotNull(message);
        ArmsUtils.snackbarText(message);
    }

    @Override
    public void launchActivity(@NonNull Intent intent) {
        checkNotNull(intent);
        ArmsUtils.startActivity(intent);
    }

    @Override
    public void killMyself() {
        finish();
    }


}
