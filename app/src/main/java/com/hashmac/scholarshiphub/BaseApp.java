package com.hashmac.scholarshiphub;

import android.app.Application;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;

import timber.log.Timber;

public class BaseApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Timber.plant(new Timber.DebugTree());
        FirebaseMessaging.getInstance().subscribeToTopic("scholarship")
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Timber.d("Subscribed to topic scholarship");
                    } else {
                        Timber.e("Error subscribing to topic scholarship");
                    }
                });
    }
}
