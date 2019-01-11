package com.zaferayan.newsportal.util;

import android.app.Application;
import android.widget.Toast;

public class ToastHelper {
    public static void showError(Application context, String messageText) {
        Toast.makeText(context, messageText, Toast.LENGTH_LONG).show();
    }

    public static void showToast(Application application, String messageText) {
        Toast.makeText(application, messageText, Toast.LENGTH_SHORT).show();
    }
}
