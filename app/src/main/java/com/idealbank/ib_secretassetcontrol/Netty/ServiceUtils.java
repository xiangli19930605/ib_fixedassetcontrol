package com.idealbank.ib_secretassetcontrol.Netty;

import android.app.ActivityManager;
import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.ACTIVITY_SERVICE;

public class ServiceUtils {

    /**
     * 判断服务是否开启
     *
     * @return
     */
    public static boolean isServiceRunning(Context context, String ServiceName) {
        if (("").equals(ServiceName) || ServiceName == null)
            return false;
        ActivityManager myManager = (ActivityManager) context
                .getSystemService(ACTIVITY_SERVICE);
        ArrayList<ActivityManager.RunningServiceInfo> runningService = (ArrayList<ActivityManager.RunningServiceInfo>) myManager
                .getRunningServices(Integer.MAX_VALUE);
        for (int i = 0; i < runningService.size(); i++) {
            if (runningService.get(i).service.getClassName().toString()
                    .equals(ServiceName)) {
                return true;
            }
        }
        return false;
    }

    //获取所有启动的服务的类名
    public  static String getServiceClassName(Context context){
        ActivityManager myManager = (ActivityManager) context
                .getSystemService(ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> mServiceList= myManager.getRunningServices(Integer.MAX_VALUE);
        Log.e("mServiceList",""+mServiceList.size());
        String res = "";
        for(int i = 0; i < mServiceList.size(); i ++){
            res+=mServiceList.get(i).service.getClassName()+ " /n";
        }

        return res;
    }
}
