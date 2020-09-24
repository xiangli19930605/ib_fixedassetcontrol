package com.idealbank.ib_secretassetcontrol;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.idealbank.ib_secretassetcontrol.app.AppApplication;
import com.idealbank.ib_secretassetcontrol.app.rfid_module.ONDEVMESSAGE;

public class MainActivity extends AppCompatActivity {
    public ONDEVMESSAGE OnMsg = new ONDEVMESSAGE();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //开启服务
        boolean b = AppApplication.sv_Main.Create(OnMsg);
        //先关闭盘点
        AppApplication.sv_Main.DoInventoryTag(false);
        // rfid power on
        AppApplication.sv_Main.gpio_rfid_config(true);

    }
}
