package com.example.fcm.fcmforceupdate.app;

import android.app.Application;
import android.content.Intent;

import com.example.fcm.fcmforceupdate.ForceUpdateActivity;

public class MyApplication extends Application {

    public static MyApplication myApplication;

    @Override
    public void onCreate() {
        super.onCreate();
        myApplication = new MyApplication();
    }

    public static MyApplication getMyApplication() {
        return myApplication;
    }

    public void getForceUPdate() {
        Intent intent = new Intent(this, ForceUpdateActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
