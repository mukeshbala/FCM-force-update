package com.example.fcm.fcmforceupdate.fcmservice;

import android.app.Dialog;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.NonNull;

import com.example.fcm.fcmforceupdate.BuildConfig;
import com.example.fcm.fcmforceupdate.updatechecker.ForceUpdateChecker;
import com.example.fcm.fcmforceupdate.app.MyApplication;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;

import java.util.HashMap;
import java.util.Map;

public class FCMForceUpdateService extends Service implements ForceUpdateChecker.OnUpdateNeededListener {

    Dialog dialog;
    private String versionName;
    boolean versionalerttxt = false;


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        ForceUpdateChecker.with(getBaseContext()).onUpdateNeeded(FCMForceUpdateService.this).check();
        getUpdate();
        return START_NOT_STICKY;
    }


    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();

    }

    public void getUpdate() {
        final FirebaseRemoteConfig firebaseRemoteConfig = FirebaseRemoteConfig.getInstance();

        FirebaseRemoteConfigSettings configSettings = new FirebaseRemoteConfigSettings.Builder()
                // .setDeveloperModeEnabled(BuildConfig.DEBUG) // Enable this on debugging
                .build();
        firebaseRemoteConfig.setConfigSettings(configSettings);

        // set in-app defaults
        Map<String, Object> remoteConfigDefaults = new HashMap();
        remoteConfigDefaults.put(ForceUpdateChecker.KEY_APP_VERSION_CODE, BuildConfig.VERSION_CODE);
        firebaseRemoteConfig.setDefaults(remoteConfigDefaults);


        long cacheExpiration = 1440;
        //onDevelopment make cacheExpiration as zero second;
        if (firebaseRemoteConfig.getInfo().getConfigSettings().isDeveloperModeEnabled()) {
            cacheExpiration = 0;
        }

        firebaseRemoteConfig.fetch(cacheExpiration)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            System.out.println("remote config is fetched.");
                            firebaseRemoteConfig.activateFetched();

                        } else {
                            System.out.println("fetch failed:" + task.getException());
                            getUpdate();
                        }
                    }


                });

    }

    @Override
    public void onUpdateNeeded(boolean isUpdate, boolean isSuccess) {
        if (isSuccess) {
            MyApplication.getMyApplication().getForceUPdate();
        }
    }
}