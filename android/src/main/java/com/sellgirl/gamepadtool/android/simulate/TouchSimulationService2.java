package com.sellgirl.gamepadtool.android.simulate;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.GestureDescription;
import android.content.Context;
import android.graphics.Path;
import android.os.Build;
import android.util.Log;
import android.view.MotionEvent;
import android.view.accessibility.AccessibilityEvent;

import com.sellgirl.gamepadtool.phone.ISGTouchSimulate;

import java.util.HashMap;
import java.util.Map;

// TouchSimulationService.java

/**
 * 手势方式模拟
 * 此版本存在问题
 * 1. 无法准确表达DOWN MOVE UP, 下次手势会cancel上次
 */
public class TouchSimulationService2 extends AccessibilityService implements ISGTouchSimulate {
    private static final String TAG = "TouchSimulation";
    private static TouchSimulationService2 instance;

    private boolean testOneTime=true;
    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {}

    @Override
    public void onInterrupt() {}

    @Override
    protected void onServiceConnected() {
        super.onServiceConnected();
        instance = this;
    }

    public static TouchSimulationService2 getInstance() {
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

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            dispatchGesture(gesture, new GestureResultCallback() {
                @Override
                public void onCompleted(GestureDescription gestureDescription) {
                    super.onCompleted(gestureDescription);
                }
            }, null);
        }
    }

    //----------------------我补充的--------------------
    public boolean isEnabled(){
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

    //-----------------------模拟摇杆--------------------------
    // 存储当前活动的触摸点
    private Map<Integer, TouchPoint> activeTouchPoints = new HashMap<>();
//    private Map<Integer, TouchPoint> downTouchPoints = new HashMap<>();

    private long time=50;//2000;//50;//17;
    /**
     * 模拟触摸按下事件 (ACTION_DOWN)
     * @param x 屏幕X坐标
     * @param y 屏幕Y坐标
     * @param pointerId 触摸点ID（用于多点触控）
     * @return 是否成功
     */
    public boolean simulateTouchDown(float x, float y, int pointerId) {
        if(testOneTime){
            simulateTouchDownTest(x,y,pointerId);
            return true;
        }
        if (!isEnabled()) {
            Log.w(TAG, "Service not enabled");
            return false;
        }

        try {
            Path path = new Path();
            path.moveTo(x, y);

            // 创建短暂的手势表示按下
            GestureDescription.StrokeDescription stroke = new GestureDescription.StrokeDescription(
                    path, 0,10// time//10 // 持续10毫秒
            );

            GestureDescription gesture = new GestureDescription.Builder()
                    .addStroke(stroke)
                    .build();

            // 存储触摸点信息
            activeTouchPoints.put(pointerId, new TouchPoint(x, y, stroke));
//            downTouchPoints.put(pointerId, new TouchPoint(x, y, stroke));

            boolean success = dispatchGesture(gesture, new GestureResultCallback() {
                @Override
                public void onCompleted(GestureDescription gestureDescription) {
                    super.onCompleted(gestureDescription);
                    Log.d(TAG, "Touch DOWN completed at: " + x + ", " + y);
                }

                @Override
                public void onCancelled(GestureDescription gestureDescription) {
                    super.onCancelled(gestureDescription);
                    Log.w(TAG, "Touch DOWN cancelled");
                }
            }, null);

            Log.d(TAG, "Touch DOWN dispatched: " + success + " at " + x + ", " + y);
            return success;

        } catch (Exception e) {
            Log.e(TAG, "Error simulating touch DOWN: " + e.getMessage());
            return false;
        }
    }

    /**
     * 模拟触摸移动事件 (ACTION_MOVE)
     * @param x 新的屏幕X坐标
     * @param y 新的屏幕Y坐标
     * @param pointerId 触摸点ID
     * @return 是否成功
     */
    public boolean simulateTouchMove(float x, float y, int pointerId) {
        if(testOneTime){return true;}
        if (!isEnabled()) {
            Log.w(TAG, "Service not enabled");
            return false;
        }

        TouchPoint previousPoint = activeTouchPoints.get(pointerId);
        if (previousPoint == null) {
            Log.w(TAG, "No active touch point found for pointer: " + pointerId);
            // 如果没有找到先前的触摸点，创建一个新的按下事件
            return simulateTouchDown(x, y, pointerId);
        }

        try {
            Path path = new Path();

            // 从上一个点移动到新点
//            TouchPoint beginPoint = activeTouchPoints.get(pointerId);
//            if(null!=beginPoint){
//                path.moveTo(beginPoint.x, beginPoint.y);
//                path.lineTo(previousPoint.x, previousPoint.y);
//            }else{
//                path.moveTo(previousPoint.x, previousPoint.y);
//            }
            path.moveTo(previousPoint.x, previousPoint.y);
            path.lineTo(x, y);

            // 创建移动手势，持续时间稍长以模拟真实移动
            GestureDescription.StrokeDescription stroke = new GestureDescription.StrokeDescription(
                    path, 0, time//50 // 持续50毫秒
            );

            GestureDescription gesture = new GestureDescription.Builder()
                    .addStroke(stroke)
                    .build();

            // 更新触摸点信息
            activeTouchPoints.put(pointerId, new TouchPoint(x, y, stroke));

            boolean success = dispatchGesture(gesture, new GestureResultCallback() {
                @Override
                public void onCompleted(GestureDescription gestureDescription) {
                    super.onCompleted(gestureDescription);
                    Log.d(TAG, "Touch MOVE completed to: " + x + ", " + y);
                }

                @Override
                public void onCancelled(GestureDescription gestureDescription) {
                    super.onCancelled(gestureDescription);
                    Log.w(TAG, "Touch MOVE cancelled");
                }
            }, null);

            Log.d(TAG, "Touch MOVE dispatched: " + success + " to " + x + ", " + y);
            return success;

        } catch (Exception e) {
            Log.e(TAG, "Error simulating touch MOVE: " + e.getMessage());
            return false;
        }
    }

    public boolean simulateTouchMove2(float bx,float by,float x, float y, int pointerId) {
        if (!isEnabled()) {
            Log.w(TAG, "Service not enabled");
            return false;
        }

        TouchPoint previousPoint = activeTouchPoints.get(pointerId);
        if (previousPoint == null) {
            Log.w(TAG, "No active touch point found for pointer: " + pointerId);
            // 如果没有找到先前的触摸点，创建一个新的按下事件
            return simulateTouchDown(x, y, pointerId);
        }

        try {
            Path path = new Path();

            // 从上一个点移动到新点
//            path.moveTo(previousPoint.x, previousPoint.y);
            path.moveTo(bx, by);
            path.lineTo(x, y);

            // 创建移动手势，持续时间稍长以模拟真实移动
            GestureDescription.StrokeDescription stroke = new GestureDescription.StrokeDescription(
                    path, 0, time//50 // 持续50毫秒
            );

            GestureDescription gesture = new GestureDescription.Builder()
                    .addStroke(stroke)
                    .build();

            // 更新触摸点信息
            activeTouchPoints.put(pointerId, new TouchPoint(x, y, stroke));

            boolean success = dispatchGesture(gesture, new GestureResultCallback() {
                @Override
                public void onCompleted(GestureDescription gestureDescription) {
                    super.onCompleted(gestureDescription);
                    Log.d(TAG, "Touch MOVE completed to: " + x + ", " + y);
                }

                @Override
                public void onCancelled(GestureDescription gestureDescription) {
                    super.onCancelled(gestureDescription);
                    Log.w(TAG, "Touch MOVE cancelled");
                }
            }, null);

            Log.d(TAG, "Touch MOVE dispatched: " + success + " to " + x + ", " + y);
            return success;

        } catch (Exception e) {
            Log.e(TAG, "Error simulating touch MOVE: " + e.getMessage());
            return false;
        }
    }
    /**
     * 模拟触摸释放事件 (ACTION_UP)
     * @param x 释放时的屏幕X坐标
     * @param y 释放时的屏幕Y坐标
     * @param pointerId 触摸点ID
     * @return 是否成功
     */
    public boolean simulateTouchUp(float x, float y, int pointerId) {
        if(testOneTime){return true;}
        if (!isEnabled()) {
            Log.w(TAG, "Service not enabled");
            return false;
        }

        try {
            Path path = new Path();
            path.moveTo(x, y);

            // 创建短暂的手势表示释放
            GestureDescription.StrokeDescription stroke = new GestureDescription.StrokeDescription(
                    path, 0, time//10 // 持续10毫秒
            );

            GestureDescription gesture = new GestureDescription.Builder()
                    .addStroke(stroke)
                    .build();

            boolean success = dispatchGesture(gesture, new GestureResultCallback() {
                @Override
                public void onCompleted(GestureDescription gestureDescription) {
                    super.onCompleted(gestureDescription);
                    // 从活动触摸点中移除
                    activeTouchPoints.remove(pointerId);
                    Log.d(TAG, "Touch UP completed at: " + x + ", " + y);
                }

                @Override
                public void onCancelled(GestureDescription gestureDescription) {
                    super.onCancelled(gestureDescription);
                    Log.w(TAG, "Touch UP cancelled");
                }
            }, null);

            Log.d(TAG, "Touch UP dispatched: " + success + " at " + x + ", " + y);
            return success;

        } catch (Exception e) {
            Log.e(TAG, "Error simulating touch UP: " + e.getMessage());
            // 即使出错也要移除触摸点
            activeTouchPoints.remove(pointerId);
            return false;
        }
    }

    /**
     * 模拟完整的点击事件（按下+释放）
     * @param x 点击的屏幕X坐标
     * @param y 点击的屏幕Y坐标
     * @param duration 点击持续时间（毫秒）
     * @return 是否成功
     */
    public boolean simulateTap(float x, float y, long duration) {
        if (!isEnabled()) {
            Log.w(TAG, "Service not enabled");
            return false;
        }

        try {
            Path path = new Path();
            path.moveTo(x, y);

            GestureDescription.StrokeDescription stroke = new GestureDescription.StrokeDescription(
                    path, 0, duration
            );

            GestureDescription gesture = new GestureDescription.Builder()
                    .addStroke(stroke)
                    .build();

            boolean success = dispatchGesture(gesture, new GestureResultCallback() {
                @Override
                public void onCompleted(GestureDescription gestureDescription) {
                    super.onCompleted(gestureDescription);
                    Log.d(TAG, "Tap completed at: " + x + ", " + y);
                }
            }, null);

            return success;

        } catch (Exception e) {
            Log.e(TAG, "Error simulating tap: " + e.getMessage());
            return false;
        }
    }

    /**
     * 停止所有活动的触摸点
     */
    public void stopAllTouchPoints() {
        for (Integer pointerId : activeTouchPoints.keySet()) {
            TouchPoint point = activeTouchPoints.get(pointerId);
            if (point != null) {
                simulateTouchUp(point.x, point.y, pointerId);
            }
        }
        activeTouchPoints.clear();
    }

    /**
     * 检查指定触摸点是否活跃
     */
    public boolean isTouchPointActive(int pointerId) {
        return activeTouchPoints.containsKey(pointerId);
    }

    /**
     * 获取活跃触摸点数量
     */
    public int getActiveTouchPointCount() {
        return activeTouchPoints.size();
    }

    /**
     * 触摸点信息类
     */
    private static class TouchPoint {
        float x, y;
        GestureDescription.StrokeDescription stroke;

        TouchPoint(float x, float y, GestureDescription.StrokeDescription stroke) {
            this.x = x;
            this.y = y;
            this.stroke = stroke;
        }
    }

    public boolean simulateTouchDownTest(float x, float y, int pointerId) {
        if (!isEnabled()) {
            Log.w(TAG, "Service not enabled");
            return false;
        }

        try {
            Path path = new Path();
            float x2=x+10;
            float x3=x+20;
            path.moveTo(x, y);
            path.lineTo(x2, y);
            path.lineTo(x3, y);

            // 创建短暂的手势表示按下
            GestureDescription.StrokeDescription stroke = new GestureDescription.StrokeDescription(
                    path, 0,1000// time//10 // 持续10毫秒
            );

            GestureDescription gesture = new GestureDescription.Builder()
                    .addStroke(stroke)
                    .build();

            // 存储触摸点信息
            activeTouchPoints.put(pointerId, new TouchPoint(x, y, stroke));
//            downTouchPoints.put(pointerId, new TouchPoint(x, y, stroke));

            boolean success = dispatchGesture(gesture, new GestureResultCallback() {
                @Override
                public void onCompleted(GestureDescription gestureDescription) {
                    super.onCompleted(gestureDescription);
                    Log.d(TAG, "Touch DOWN completed at: " + x + ", " + y);
                }

                @Override
                public void onCancelled(GestureDescription gestureDescription) {
                    super.onCancelled(gestureDescription);
                    Log.w(TAG, "Touch DOWN cancelled");
                }
            }, null);

            Log.d(TAG, "Touch DOWN dispatched: " + success + " at " + x + ", " + y);
            return success;

        } catch (Exception e) {
            Log.e(TAG, "Error simulating touch DOWN: " + e.getMessage());
            return false;
        }
    }
    //-----------------------模拟摇杆 end--------------------------
}