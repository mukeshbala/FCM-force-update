package com.example.fcm.fcmforceupdate;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.fcm.fcmforceupdate.fcmservice.FCMForceUpdateService;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = new Intent(this, FCMForceUpdateService.class);
        startService(intent);
    }
}
