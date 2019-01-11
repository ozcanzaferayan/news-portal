package com.zaferayan.newsportal.util;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.content.Intent;

public class ServiceUtil {
    private static boolean isServiceRunning(Application application, Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) application.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    public static void startService(Application app, Class<?> serviceClass) {
        if (!isServiceRunning(app, serviceClass)) {
            app.startService(new Intent(app, serviceClass));
        }
    }
}
