package com.hao.fdbusproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.hao.fdbus.BusManager;
import com.hao.fdbus.IPCManager;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = new Intent(this, BusManager.class);
        startService(intent);

        IPCManager.Companion.getInstance(this).register(HaoService.class);

        initIp();
    }

    private void initIp() {

        ((TextView) findViewById(R.id.ip_address)).setText("服务端：" + DeviceUtil.getLocalIpAddress(this));

    }
}