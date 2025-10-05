package com.sellgirl.gamepadtool.android;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class NotificationPermissionHelper {

    // 检查是否已授予通知权限
    public static boolean areNotificationsEnabled(Context context) {
        // 只有在 Android 13 (API 33) 及以上版本才需要此权限
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) {
            return true;
        }
        boolean b1=ContextCompat.checkSelfPermission(
            context,
            android.Manifest.permission.POST_NOTIFICATIONS
        ) == PackageManager.PERMISSION_GRANTED;

        //FOREGROUND_SERVICE 这些权限好像是通知附属的，不需要动态申请
//        boolean b2=ContextCompat.checkSelfPermission(
//            context,
//            android.Manifest.permission.FOREGROUND_SERVICE
//        ) == PackageManager.PERMISSION_GRANTED;
//        boolean b3=ContextCompat.checkSelfPermission(
//            context,
//            android.Manifest.permission.FOREGROUND_SERVICE_MEDIA_PLAYBACK
//        ) == PackageManager.PERMISSION_GRANTED;

        return b1
//            && b2
//            && b3
            ;
    }

    // 请求通知权限
    public static void requestNotificationPermission(Activity activity, int requestCode) {
        // 只有在 Android 13 (API 33) 及以上版本才需要请求此权限
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) {
            return;
        }

        // 检查是否已经有权权限，如果没有则请求
        if (!areNotificationsEnabled(activity)) {
            ActivityCompat.requestPermissions(
                activity,
                new String[]{android.Manifest.permission.POST_NOTIFICATIONS//,
//                    android.Manifest.permission.FOREGROUND_SERVICE,
//                    android.Manifest.permission.FOREGROUND_SERVICE_MEDIA_PLAYBACK
                },
                requestCode
            );
        }
    }

    // 处理权限请求结果
    public static boolean handlePermissionResult(
        int requestCode,
        int[] grantResults,
        int expectedRequestCode
    ) {
        if (requestCode == expectedRequestCode) {
            return grantResults.length > 0 &&
                grantResults[0] == PackageManager.PERMISSION_GRANTED;
        }
        return false;
    }
}
