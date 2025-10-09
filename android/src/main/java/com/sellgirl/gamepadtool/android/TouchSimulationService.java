package com.sellgirl.gamepadtool.android;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.GestureDescription;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Path;
import android.os.Build;
import android.view.MotionEvent;
import android.view.accessibility.AccessibilityEvent;

import androidx.core.content.ContextCompat;

import com.sellgirl.gamepadtool.phone.ISGTouchSimulate;

// TouchSimulationService.java
public class TouchSimulationService extends AccessibilityService implements ISGTouchSimulate {
    private static TouchSimulationService instance;

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {}

    @Override
    public void onInterrupt() {}

    @Override
    protected void onServiceConnected() {
        super.onServiceConnected();
        instance = this;
    }

    public static TouchSimulationService getInstance() {
        return instance;
    }

    // 核心：模拟触摸事件
    public void simulateTouch(float x, float y, int action, long duration) {
        if (!isEnabled()) return;

        Path path = new Path();
        path.moveTo(x, y);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N
        ) {

        }else{
            return;
        }

        GestureDescription.StrokeDescription stroke;
        if (action == MotionEvent.ACTION_DOWN) {
            // 按下事件
            stroke = new GestureDescription.StrokeDescription(path, 0, duration);
        } else {
            // 其他事件处理
            stroke = new GestureDescription.StrokeDescription(path, 0, 10);
        }

        GestureDescription gesture = new GestureDescription.Builder()
                .addStroke(stroke)
                .build();

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            dispatchGesture(gesture, new GestureResultCallback() {
                @Override
                public void onCompleted(GestureDescription gestureDescription) {
                    super.onCompleted(gestureDescription);
                }
            }, null);
        }
    }

    //----------------------我补充的--------------------
    private boolean isEnabled(){
        return true;
    }
    // 供LibGDX调用的方法
    @Override
    public void simulateTouchFromGdx( float x,  float y//, final int action
    ) {
//        runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                if (touchService != null) {
//                    // 转换坐标：LibGDX坐标 -> Android屏幕坐标
//                    float screenX = x;
//                    float screenY = getResources().getDisplayMetrics().heightPixels - y;
//
//                    touchService.simulateTouch(screenX, screenY, action, 50);
//                } else {
////                    // 提示用户开启无障碍服务
////                    showAccessibilityPrompt();
//
//                }
//            }
//        });

        // 转换坐标：LibGDX坐标 -> Android屏幕坐标
        float screenX = x;
        float screenY = getResources().getDisplayMetrics().heightPixels - y;

        simulateTouch(screenX, screenY,
                MotionEvent.ACTION_DOWN,//action,
                50);
        simulateTouch(screenX, screenY,
                MotionEvent.ACTION_UP,//action,
                50);
    }

    public static boolean areNotificationsEnabled(Context context) {
//        // 只有在 Android 13 (API 33) 及以上版本才需要此权限
//        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) {
//            return true;
//        }
//        boolean b1= ContextCompat.checkSelfPermission(
//                context,
//                android.Manifest.permission.POST_NOTIFICATIONS
//        ) == PackageManager.PERMISSION_GRANTED;
//
//        return b1
//                ;
        return true;
    }
    
}