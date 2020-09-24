package com.idealbank.ib_secretassetcontrol.app.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by 79430 on 2018/11/22.
 */

public class USBReceiver2 extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO Auto-generated method stub
        if(intent.getAction().equals("android.hardware.usb.action.USB_STATE")){
            if (intent.getExtras().getBoolean("connected")){
                // usb 插入
                Toast.makeText(context, "USB插入", Toast.LENGTH_LONG).show();
            }else{
                //   usb 拔出
                Toast.makeText(context, "USB拔出", Toast.LENGTH_LONG).show();
            }
        }
    }
}
