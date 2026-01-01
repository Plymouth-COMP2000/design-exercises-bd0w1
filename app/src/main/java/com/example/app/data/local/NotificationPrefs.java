package com.example.app.data.local;

import android.content.Context;
import android.content.SharedPreferences;

public class NotificationPrefs {

    private static final String PREFS_NAME = "app_prefs";
    private static final String KEY_NOTIFICATIONS = "notifications_enabled";

    public static boolean areNotificationsEnabled(Context ctx) {
        SharedPreferences prefs = ctx.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        return prefs.getBoolean(KEY_NOTIFICATIONS, true);
    }

    public static void setNotificationsEnabled(Context ctx, boolean enabled) {
        SharedPreferences prefs = ctx.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        prefs.edit().putBoolean(KEY_NOTIFICATIONS, enabled).apply();
    }
}
