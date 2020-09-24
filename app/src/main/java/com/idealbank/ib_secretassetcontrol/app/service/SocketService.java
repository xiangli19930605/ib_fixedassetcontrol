package com.idealbank.ib_secretassetcontrol.app.service;
import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.idealbank.ib_secretassetcontrol.Netty.FileServer;
import com.idealbank.ib_secretassetcontrol.Netty.GlobalHandler;

//import com.idealbank.module_main.Netty.FileServer;
//import com.idealbank.module_main.Netty.GlobalHandler;


public class SocketService extends Service {
    public static boolean isStarted = false;
    @Override
    public void onCreate() {
        isStarted = true;
        startForeground(1,new Notification());
        Log.i("Kathy","onCreate - Thread ID = " + Thread.currentThread().getId());
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("Kathy", "onStartCommand - startId = " + startId + ", Thread ID = " + Thread.currentThread().getId());

//   new FileServer(GlobalHandler.getInstance()).getInstance().start();
        FileServer.getInstance().start();
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.i("Kathy", "onBind - Thread ID = " + Thread.currentThread().getId());
        return null;
    }

    @Override
    public void onDestroy() {
        Log.i("Kathy", "onDestroy - Thread ID = " + Thread.currentThread().getId());
        super.onDestroy();
    }
}
