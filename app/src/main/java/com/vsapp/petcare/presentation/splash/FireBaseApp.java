package com.vsapp.petcare.presentation.splash;

import android.app.Application;

import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by vamshi on 17/3/18.
 */

public class FireBaseApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }
}
