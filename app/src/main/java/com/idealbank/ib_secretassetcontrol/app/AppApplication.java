package com.idealbank.ib_secretassetcontrol.app;

import com.idealbank.ib_secretassetcontrol.app.rfid_module.ONDEVMESSAGE;

import me.jessyan.armscomponent.commonsdk.app.MyApplication;
import rfid_interface.RfidService;

public class AppApplication extends MyApplication {

    public static RfidService sv_Main = RfidService.getInstance();

    @Override
    public void onCreate() {
        super.onCreate();

    }

}
