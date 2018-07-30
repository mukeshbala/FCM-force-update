package com.example.fcm.fcmforceupdate.updatechecker;

import android.content.Context;
import android.support.annotation.NonNull;

import com.example.fcm.fcmforceupdate.BuildConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;

public class ForceUpdateChecker {

    private static final String TAG = ForceUpdateChecker.class.getSimpleName();

    public static final String KEY_UPDATE_REQUIRED = "android_force_update_required";
    public static final String KEY_APP_VERSION_CODE = "android_force_update_current_version";
    public static final String KEY_UPDATE_URL = "android_force_update_store_url";
    public static final String KEY_UPDATE_SKIP = "android_skip";

    private OnUpdateNeededListener onUpdateNeededListener;
    private Context context;

    public ForceUpdateChecker(@NonNull Context context,
                              OnUpdateNeededListener onUpdateNeededListener) {
        this.context = context;
        this.onUpdateNeededListener = onUpdateNeededListener;
    }

    public static Builder with(@NonNull Context context) {
        return new Builder(context);
    }

    public void check() {
        final FirebaseRemoteConfig remoteConfig = FirebaseRemoteConfig.getInstance();
        if (remoteConfig.getBoolean(KEY_UPDATE_REQUIRED)) {
            String playStoreVersionConfig = remoteConfig.getString(KEY_APP_VERSION_CODE);
            int appVersion = BuildConfig.VERSION_CODE;
            boolean isUpdateSkip = remoteConfig.getBoolean(KEY_UPDATE_REQUIRED);
//            String whatsNew = remoteConfig.getString(KEY_PLAY_WHATS_NEW_TEXT);
//            ;
            int playStoreVersion;
            try {
                playStoreVersion = Integer.parseInt(playStoreVersionConfig);
            } catch (NumberFormatException e) {
                playStoreVersion = 0;
            }
            System.out.println("playStoreVersion version:" + playStoreVersion);
            System.out.println("App version:" + appVersion);
            if (playStoreVersion != 0 && playStoreVersion > appVersion
                    && onUpdateNeededListener != null) {
                onUpdateNeededListener.onUpdateNeeded(isUpdateSkip,true);
            }else {
                onUpdateNeededListener.onUpdateNeeded(isUpdateSkip,false);
            }
        }else {
            onUpdateNeededListener.onUpdateNeeded(false,false);
        }

    }

    public interface OnUpdateNeededListener {
        void onUpdateNeeded(boolean isUpdate,boolean isSuccess);
    }

    public static class Builder {

        private Context context;
        private OnUpdateNeededListener onUpdateNeededListener;

        public Builder(Context context) {
            this.context = context;
        }

        public Builder onUpdateNeeded(OnUpdateNeededListener onUpdateNeededListener) {
            this.onUpdateNeededListener = onUpdateNeededListener;
            return this;
        }

        public ForceUpdateChecker build() {
            return new ForceUpdateChecker(context, onUpdateNeededListener);
        }

        public ForceUpdateChecker check() {
            ForceUpdateChecker forceUpdateChecker = build();
            forceUpdateChecker.check();

            return forceUpdateChecker;
        }
    }
}
