package com.zaferayan.newsportal.helper;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import com.zaferayan.newsportal.R;

public class ShortcutHelper {

    private static final String IS_ICON_CREATED = "IS_ICON_CREATED";
    private static final String APP_PREFERENCE = "APP_PREFERENCE";

    public static void addShortcut(Context context, Class clazz) {
        if (context.getSharedPreferences(APP_PREFERENCE, Activity.MODE_PRIVATE).getBoolean(IS_ICON_CREATED, false)) {
            return;
        } else {
            context.getSharedPreferences(APP_PREFERENCE, Activity.MODE_PRIVATE).edit().putBoolean(IS_ICON_CREATED, true).apply();
        }
        Intent shortcutIntent = new Intent(context, clazz);

        shortcutIntent.setAction(Intent.ACTION_MAIN);

        Intent addIntent = new Intent();
        addIntent
                .putExtra(Intent.EXTRA_SHORTCUT_INTENT, shortcutIntent);
        addIntent.putExtra(Intent.EXTRA_SHORTCUT_NAME, "HelloWorldShortcut");
        addIntent.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE,
                Intent.ShortcutIconResource.fromContext(context, R.drawable.ic_launcher_foreground));

        addIntent
                .setAction("com.android.launcher.action.INSTALL_SHORTCUT");
        addIntent.putExtra("duplicate", false);  //may it's already there so don't duplicate
        context.sendBroadcast(addIntent);
    }
}
