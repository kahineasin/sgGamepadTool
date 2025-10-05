//package com.sellgirl.gamepadtool.android;
//
//// MyAccessibilityService.java
//
//import android.accessibilityservice.AccessibilityService;
//import android.accessibilityservice.GestureDescription;
//import android.graphics.Path;
//import android.util.Log;
//
//public class MyAccessibilityService extends AccessibilityService {
//
//    @Override
//    public void onAccessibilityEvent(android.view.accessibility.AccessibilityEvent event) {}
//
//    @Override
//    public void onInterrupt() {}
//
//    // **核心方法：模拟点击指定坐标**
//    public void simulateTap(float x, float y) {
//        if (!isEnabled()) {
//            Log.w("Accessibility", "服务未开启！");
//            return;
//        }
//
//        Path path = new Path();
//        path.moveTo(x, y);
//
//        GestureDescription.StrokeDescription stroke = new GestureDescription.StrokeDescription(path, 0, 50); // 持续50毫秒的点击
//        GestureDescription.Builder builder = new GestureDescription.Builder();
//        builder.addStroke(stroke);
//
//        dispatchGesture(builder.build(), null, null);
//        Log.i("Accessibility", "Tap at: " + x + ", " + y);
//    }
//
//    //----------------------我补充的--------------------
//    private boolean isEnabled(){
//        return true;
//    }
//}