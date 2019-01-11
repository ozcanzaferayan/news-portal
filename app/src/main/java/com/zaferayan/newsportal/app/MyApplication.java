package com.zaferayan.newsportal.app;

import android.app.Application;
import android.util.Log;

public class MyApplication extends Application {

    private static final String TAG = "MyApp";

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "App started up");
        //startService(new Intent(this, UpdaterService.class));
    }
}
