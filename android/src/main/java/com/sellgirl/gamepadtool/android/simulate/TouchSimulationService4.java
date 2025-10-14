//package com.sellgirl.gamepadtool.android.simulate;
//
//import android.accessibilityservice.AccessibilityService;
//import android.accessibilityservice.GestureDescription;
//import android.content.Context;
//import android.graphics.Path;
//import android.graphics.PointF;
//import android.os.Build;
//import android.os.Handler;
//import android.os.Looper;
//import android.util.Log;
//import android.view.MotionEvent;
//import android.view.accessibility.AccessibilityEvent;
//
//import com.sellgirl.gamepadtool.android.GestureUtils;
//import com.sellgirl.gamepadtool.phone.ISGTouchSimulate;
//import com.sellgirl.sgJavaHelper.config.SGDataHelper;
//
//import java.util.HashMap;
//import java.util.Map;
//
//// TouchSimulationService.java
//
///**
// * 手势方式模拟
// * 此版本存在问题
// * 1. 无法准确表达DOWN MOVE UP, 下次手势会cancel上次
// *
// * 尝试使用StrokeDescription.mContinued解决上面问题。成功解决DOWN UP问题
// *
// * 但有新问题：
// * 1. 多点触摸时，mContinued无效了。
// */
//public class TouchSimulationService4 extends AccessibilityService implements ISGTouchSimulate
//
//{
//    private static final String TAG = "TouchSimulation";
//    private static TouchSimulationService4 instance;
//
//    //测试用的字段
//    private boolean testOneTime=true;
//    private Handler handler;
////    private GestureDescription.Builder builder=null;
//    @Override
//    public void onAccessibilityEvent(AccessibilityEvent event) {}
//
//    @Override
//    public void onInterrupt() {}
//
//    @Override
//    protected void onServiceConnected() {
//        super.onServiceConnected();
//        instance = this;
////         builder=new GestureDescription.Builder();
//
//        handler = new Handler(Looper.getMainLooper());
//        gestureResultCallback=new GestureResultCallback() {
//            @Override
//            public void onCompleted(GestureDescription gestureDescription) {
//                super.onCompleted(gestureDescription);
//            }
//
//            @Override
//            public void onCancelled(GestureDescription gestureDescription) {
//                super.onCancelled(gestureDescription);
////                dispatchGesture(gestureDescription, new GestureResultCallback() {
////                    @Override
////                    public void onCompleted(GestureDescription gestureDescription) {
////                        super.onCompleted(gestureDescription);
////                    }
////
////                    @Override
////                    public void onCancelled(GestureDescription gestureDescription) {
////                        super.onCancelled(gestureDescription);
////                    }
////                },null);
//            }
//        };
//    }
//
//    public static TouchSimulationService4 getInstance() {
//        return instance;
//    }
//
//    // 核心：模拟触摸事件
//    private void simulateTouch(float x, float y, int action, long duration) {
////        if(true) {
////            return;
////        }
//        if (!isEnabled()) return;
//
//
////        GestureDescription gesture = new GestureDescription.Builder()
////                .addStroke(stroke)
////                .build();
//        GestureDescription.Builder builder=new GestureDescription.Builder()
//                ;
////        if(true) {//这里为止,摇杆不断
////            return;
////        }
//
////        if(null!=downStroke){
////            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
////////                Path path2 = new Path(downStroke.getPath());
////                Path path2 = new Path();
////                path2.moveTo(this.x, this.y);
//////                path2.lineTo(this.x, this.y);
//////                TouchPoint previousPoint = activeTouchPoints.get(pointerId);
//////                Path path2 = new Path();
//////                path2.moveTo(previousPoint.x, previousPoint.y);
//////                path2.lineTo(x, y);
////                GestureDescription.StrokeDescription downStroke2=downStroke.continueStroke(path2,0,time,true);
////                builder.addStroke(downStroke2);
////
//////                //有downStroke3的情况下，无论如何摇杆都会断
//////                Path path3 = new Path();
////////                path2.moveTo(this.x, this.y);
//////                path3.moveTo(x, y);
////////                GestureDescription.StrokeDescription downStroke3=downStroke.continueStroke(path3,0,10,false);//断摇杆
//////                GestureDescription.StrokeDescription downStroke3= new GestureDescription.StrokeDescription(path3, 0, 10);//断摇杆
////////                GestureDescription.StrokeDescription downStroke3=downStroke.continueStroke(path3,0,10,true);//断摇杆
//////                builder.addStroke(downStroke3);
////
////                downStroke=downStroke2;
////            }
//////            if(true) {//这里为止,摇杆不断
//////                return;
//////            }
////        }
////        else{
////            if(true) {//这里为止,摇杆不断
////                return;
////            }
////            Path path = new Path();
////            path.moveTo(x, y);
////
////            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N
////            ) {
////
////            }else{
////                return;
////            }
////
////            GestureDescription.StrokeDescription stroke;
////            if (action == MotionEvent.ACTION_DOWN) {
////                // 按下事件
////                stroke = new GestureDescription.StrokeDescription(path, 0, duration);
////            } else {
////                // 其他事件处理
////                stroke = new GestureDescription.StrokeDescription(path, 0, 10);
////            }
////            builder.addStroke(stroke);
////        }
//
//        Path path = new Path();
//        path.moveTo(x, y);
//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N
//        ) {
//
//        }else{
//            return;
//        }
//
//        GestureDescription.StrokeDescription stroke;
//        if(null!=downStroke){
//            builder.addStroke(downStroke);
//        }
//        if (action == MotionEvent.ACTION_DOWN) {
//            // 按下事件
//            stroke = new GestureDescription.StrokeDescription(path, 0, duration);
//        } else {
//            // 其他事件处理
//            stroke = new GestureDescription.StrokeDescription(path, 0, 10);
//        }
//
//        builder.addStroke(stroke);
//
////        builder.addStroke(stroke);
//        GestureDescription gesture =builder
//                .build();
//
//        SGDataHelper.getLog().print("displayId:"+gesture.getDisplayId());
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//            dispatchGesture(gesture, new GestureResultCallback() {
//                @Override
//                public void onCompleted(GestureDescription gestureDescription) {
//                    super.onCompleted(gestureDescription);
//                }
//            }, null);
//        }
//    }
//
//    //----------------------我补充的--------------------
//    public boolean isEnabled(){
//        return true;
//    }
//    // 供LibGDX调用的方法
//    @Override
//    public void simulateTouchFromGdx( float x,  float y//, final int action
//    ) {
//
//        // 转换坐标：LibGDX坐标 -> Android屏幕坐标
//        float screenX = x;
////        float screenY = getResources().getDisplayMetrics().heightPixels - y;
//        float screenY = y;
//
////        simulateTouch(screenX, screenY,
////                MotionEvent.ACTION_DOWN,//action,
////                50);
//        simulateTouch(screenX, screenY,
//                MotionEvent.ACTION_UP,//action,
//                50);
//    }
//
//    public static boolean areNotificationsEnabled(Context context) {
////        // 只有在 Android 13 (API 33) 及以上版本才需要此权限
////        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) {
////            return true;
////        }
////        boolean b1= ContextCompat.checkSelfPermission(
////                context,
////                android.Manifest.permission.POST_NOTIFICATIONS
////        ) == PackageManager.PERMISSION_GRANTED;
////
////        return b1
////                ;
//        return true;
//    }
//
//    //-----------------------模拟摇杆--------------------------
//    // 存储当前活动的触摸点
//    private Map<Integer, TouchPoint> activeTouchPoints = new HashMap<>();
////    private Map<Integer, TouchPoint> downTouchPoints = new HashMap<>();
//
//    private long time=10;//100;//13较少cancel;//2000;//50;//17;
//    private GestureDescription.StrokeDescription downStroke=null;
////    private GestureDescription.StrokeDescription clickStroke=null;
//    private float x=0;
//    private float y=0;
//    /**
//     * 模拟触摸按下事件 (ACTION_DOWN)
//     * @param x 屏幕X坐标
//     * @param y 屏幕Y坐标
//     * @param pointerId 触摸点ID（用于多点触控）
//     * @return 是否成功
//     */
//    public boolean simulateTouchDown(float x, float y, int pointerId) {
//        if(testOneTime){
////            simulateTouchDownTest(x,y,pointerId);
////            simulateTouchMultiStrokeTest(x,y,pointerId);//ok
////            simulateTouchMultiStrokeTest2(x,y,pointerId);//no
//            simulateTouchMultiStrokeTest3(x,y,pointerId);//no
////            simulateTouchMultiStrokeTest4(x,y,pointerId);//有cancel,但应该可用
//            return true;
//        }
//        if (!isEnabled()) {
//            Log.w(TAG, "Service not enabled");
//            return false;
//        }
//
//        try {
//            Path path = new Path();
//            path.moveTo(x, y);
//
//            // 创建短暂的手势表示按下
//            GestureDescription.StrokeDescription stroke = null;
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//                stroke = new GestureDescription.StrokeDescription(
//                        path, 0,10// time//10 // 持续10毫秒
//                        ,true
//                );
//                downStroke=stroke;
////                clickStroke = new GestureDescription.StrokeDescription(
////                        new Path(), 0,10// time//10 // 持续10毫秒
////                        ,true
////                );
//            }else{
//                stroke = new GestureDescription.StrokeDescription(
//                        path, 0,time// time//10 // 持续10毫秒
//                        //,true
//                );
//            }
//
//            GestureDescription gesture = new GestureDescription.Builder()
//                    .addStroke(stroke)
////                    .addStroke(clickStroke)
//                    .build();
//
//            // 存储触摸点信息
//            activeTouchPoints.put(pointerId, new TouchPoint(x, y, stroke));
////            downTouchPoints.put(pointerId, new TouchPoint(x, y, stroke));
//
//            boolean success = dispatchGesture(gesture, new GestureResultCallback() {
//                @Override
//                public void onCompleted(GestureDescription gestureDescription) {
//                    super.onCompleted(gestureDescription);
////                    Log.d(TAG, "Touch DOWN completed at: " + x + ", " + y);
//                }
//
//                @Override
//                public void onCancelled(GestureDescription gestureDescription) {
//                    super.onCancelled(gestureDescription);
////                    Log.w(TAG, "Touch DOWN cancelled");
//                }
//            }, null);
//
////            Log.d(TAG, "Touch DOWN dispatched: " + success + " at " + x + ", " + y);
//            return success;
//
//        } catch (Exception e) {
//            Log.e(TAG, "Error simulating touch DOWN: " + e.getMessage());
//            return false;
//        }
//    }
//
//    /**
//     * 模拟触摸移动事件 (ACTION_MOVE)
//     * @param x 新的屏幕X坐标
//     * @param y 新的屏幕Y坐标
//     * @param pointerId 触摸点ID
//     * @return 是否成功
//     */
//    public boolean simulateTouchMove(float x, float y, int pointerId) {
//
//        if(testOneTime){
//            return true;
//        }
//        if (!isEnabled()) {
//            Log.w(TAG, "Service not enabled");
//            return false;
//        }
//
//        if(null!=downStroke){
//            TouchPoint previousPoint = activeTouchPoints.get(pointerId);
//            Path path = new Path();
//            path.moveTo(previousPoint.x, previousPoint.y);
//            path.lineTo(x, y);
//            this.x=x;
//            this.y=y;
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//                downStroke=
//                        downStroke.continueStroke(path,0,time,true);
////                    clickStroke=clickStroke.continueStroke(new Path(),0,time,true);
//
//                GestureDescription.StrokeDescription stroke=downStroke;
//                GestureDescription gesture = new GestureDescription.Builder()
//                        .addStroke(stroke)
////                            .addStroke(clickStroke)
//                        .build();
//
//                // 更新触摸点信息
//                activeTouchPoints.put(pointerId, new TouchPoint(x, y, stroke));
//
//                boolean success = dispatchGesture(gesture, new GestureResultCallback() {
//                    @Override
//                    public void onCompleted(GestureDescription gestureDescription) {
//                        super.onCompleted(gestureDescription);
////                            Log.d(TAG, "Touch MOVE completed to: " + x + ", " + y);
//                    }
//
//                    @Override
//                    public void onCancelled(GestureDescription gestureDescription) {
//                        super.onCancelled(gestureDescription);
////                            Log.w(TAG, "Touch MOVE cancelled");
//                    }
//                }, null);
//                return success;
//            }
//        }
//
//        TouchPoint previousPoint = activeTouchPoints.get(pointerId);
//        if (previousPoint == null) {
//            Log.w(TAG, "No active touch point found for pointer: " + pointerId);
//            // 如果没有找到先前的触摸点，创建一个新的按下事件
//            return simulateTouchDown(x, y, pointerId);
//        }
//
//        try {
//            Path path = new Path();
//
//            // 从上一个点移动到新点
////            TouchPoint beginPoint = activeTouchPoints.get(pointerId);
////            if(null!=beginPoint){
////                path.moveTo(beginPoint.x, beginPoint.y);
////                path.lineTo(previousPoint.x, previousPoint.y);
////            }else{
////                path.moveTo(previousPoint.x, previousPoint.y);
////            }
//            path.moveTo(previousPoint.x, previousPoint.y);
//            path.lineTo(x, y);
//
//            // 创建移动手势，持续时间稍长以模拟真实移动
//            GestureDescription.StrokeDescription stroke = new GestureDescription.StrokeDescription(
//                    path, 0, time//50 // 持续50毫秒
//            );
//
//            GestureDescription gesture = new GestureDescription.Builder()
//                    .addStroke(stroke)
//                    .build();
//
//            // 更新触摸点信息
//            activeTouchPoints.put(pointerId, new TouchPoint(x, y, stroke));
//
//            boolean success = dispatchGesture(gesture, new GestureResultCallback() {
//                @Override
//                public void onCompleted(GestureDescription gestureDescription) {
//                    super.onCompleted(gestureDescription);
////                    Log.d(TAG, "Touch MOVE completed to: " + x + ", " + y);
//                }
//
//                @Override
//                public void onCancelled(GestureDescription gestureDescription) {
//                    super.onCancelled(gestureDescription);
////                    Log.w(TAG, "Touch MOVE cancelled");
//                }
//            }, null);
//
////            Log.d(TAG, "Touch MOVE dispatched: " + success + " to " + x + ", " + y);
//            return success;
//
//        } catch (Exception e) {
//            Log.e(TAG, "Error simulating touch MOVE: " + e.getMessage());
//            return false;
//        }
//    }
//
////    public boolean simulateTouchMove2(float bx,float by,float x, float y, int pointerId) {
////        if (!isEnabled()) {
////            Log.w(TAG, "Service not enabled");
////            return false;
////        }
////
////        TouchPoint previousPoint = activeTouchPoints.get(pointerId);
////        if (previousPoint == null) {
////            Log.w(TAG, "No active touch point found for pointer: " + pointerId);
////            // 如果没有找到先前的触摸点，创建一个新的按下事件
////            return simulateTouchDown(x, y, pointerId);
////        }
////
////        try {
////            Path path = new Path();
////
////            // 从上一个点移动到新点
//////            path.moveTo(previousPoint.x, previousPoint.y);
////            path.moveTo(bx, by);
////            path.lineTo(x, y);
////
////            // 创建移动手势，持续时间稍长以模拟真实移动
////            GestureDescription.StrokeDescription stroke = new GestureDescription.StrokeDescription(
////                    path, 0, time//50 // 持续50毫秒
////            );
////
////            GestureDescription gesture = new GestureDescription.Builder()
////                    .addStroke(stroke)
////                    .build();
////
////            // 更新触摸点信息
////            activeTouchPoints.put(pointerId, new TouchPoint(x, y, stroke));
////
////            boolean success = dispatchGesture(gesture, new GestureResultCallback() {
////                @Override
////                public void onCompleted(GestureDescription gestureDescription) {
////                    super.onCompleted(gestureDescription);
////                    Log.d(TAG, "Touch MOVE completed to: " + x + ", " + y);
////                }
////
////                @Override
////                public void onCancelled(GestureDescription gestureDescription) {
////                    super.onCancelled(gestureDescription);
////                    Log.w(TAG, "Touch MOVE cancelled");
////                }
////            }, null);
////
////            Log.d(TAG, "Touch MOVE dispatched: " + success + " to " + x + ", " + y);
////            return success;
////
////        } catch (Exception e) {
////            Log.e(TAG, "Error simulating touch MOVE: " + e.getMessage());
////            return false;
////        }
////    }
//    /**
//     * 模拟触摸释放事件 (ACTION_UP)
//     * @param x 释放时的屏幕X坐标
//     * @param y 释放时的屏幕Y坐标
//     * @param pointerId 触摸点ID
//     * @return 是否成功
//     */
//    public boolean simulateTouchUp(float x, float y, int pointerId) {
//        if(testOneTime){
//            return true;
//        }
//        if (!isEnabled()) {
//            Log.w(TAG, "Service not enabled");
//            return false;
//        }
//
//        if(null!=downStroke){
//            TouchPoint previousPoint = activeTouchPoints.get(pointerId);
//            Path path = new Path();
//            path.moveTo(previousPoint.x, previousPoint.y);
//            path.lineTo(x, y);
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//                downStroke=
//                        downStroke.continueStroke(path,0,10,false);
////                    clickStroke=clickStroke.continueStroke(new Path(),0,10,false);
//
//                GestureDescription.StrokeDescription stroke=downStroke;
//                downStroke=null;
//                GestureDescription gesture = new GestureDescription.Builder()
//                        .addStroke(stroke)
////                            .addStroke(clickStroke)
//                        .build();
//
//                // 更新触摸点信息
//                activeTouchPoints.put(pointerId, new TouchPoint(x, y, stroke));
//
//                boolean success = dispatchGesture(gesture, new GestureResultCallback() {
//                    @Override
//                    public void onCompleted(GestureDescription gestureDescription) {
//                        super.onCompleted(gestureDescription);
////                            Log.d(TAG, "Touch MOVE completed to: " + x + ", " + y);
//                    }
//
//                    @Override
//                    public void onCancelled(GestureDescription gestureDescription) {
//                        super.onCancelled(gestureDescription);
////                            Log.w(TAG, "Touch MOVE cancelled");
//                    }
//                }, null);
//                return success;
//            }
//        }
//        try {
//            Path path = new Path();
//            path.moveTo(x, y);
//
//            // 创建短暂的手势表示释放
//            GestureDescription.StrokeDescription stroke = new GestureDescription.StrokeDescription(
//                    path, 0, time//10 // 持续10毫秒
//            );
//
//            GestureDescription gesture = new GestureDescription.Builder()
//                    .addStroke(stroke)
//                    .build();
//
//            boolean success = dispatchGesture(gesture, new GestureResultCallback() {
//                @Override
//                public void onCompleted(GestureDescription gestureDescription) {
//                    super.onCompleted(gestureDescription);
//                    // 从活动触摸点中移除
//                    activeTouchPoints.remove(pointerId);
////                    Log.d(TAG, "Touch UP completed at: " + x + ", " + y);
//                }
//
//                @Override
//                public void onCancelled(GestureDescription gestureDescription) {
//                    super.onCancelled(gestureDescription);
////                    Log.w(TAG, "Touch UP cancelled");
//                }
//            }, null);
//
////            Log.d(TAG, "Touch UP dispatched: " + success + " at " + x + ", " + y);
//            return success;
//
//        } catch (Exception e) {
//            Log.e(TAG, "Error simulating touch UP: " + e.getMessage());
//            // 即使出错也要移除触摸点
//            activeTouchPoints.remove(pointerId);
//            return false;
//        }
//    }
//
//    /**
//     * 模拟完整的点击事件（按下+释放）
//     * @param x 点击的屏幕X坐标
//     * @param y 点击的屏幕Y坐标
//     * @param duration 点击持续时间（毫秒）
//     * @return 是否成功
//     */
//    public boolean simulateTap(float x, float y, long duration) {
//        if (!isEnabled()) {
//            Log.w(TAG, "Service not enabled");
//            return false;
//        }
//
//        try {
//            Path path = new Path();
//            path.moveTo(x, y);
//
//            GestureDescription.StrokeDescription stroke = new GestureDescription.StrokeDescription(
//                    path, 0, duration
//            );
//
//            GestureDescription gesture = new GestureDescription.Builder()
//                    .addStroke(stroke)
//                    .build();
//
//            boolean success = dispatchGesture(gesture, new GestureResultCallback() {
//                @Override
//                public void onCompleted(GestureDescription gestureDescription) {
//                    super.onCompleted(gestureDescription);
//                    Log.d(TAG, "Tap completed at: " + x + ", " + y);
//                }
//            }, null);
//
//            return success;
//
//        } catch (Exception e) {
//            Log.e(TAG, "Error simulating tap: " + e.getMessage());
//            return false;
//        }
//    }
//
//    /**
//     * 停止所有活动的触摸点
//     */
//    public void stopAllTouchPoints() {
//        for (Integer pointerId : activeTouchPoints.keySet()) {
//            TouchPoint point = activeTouchPoints.get(pointerId);
//            if (point != null) {
//                simulateTouchUp(point.x, point.y, pointerId);
//            }
//        }
//        activeTouchPoints.clear();
//    }
//
//    /**
//     * 检查指定触摸点是否活跃
//     */
//    public boolean isTouchPointActive(int pointerId) {
//        return activeTouchPoints.containsKey(pointerId);
//    }
//
//    /**
//     * 获取活跃触摸点数量
//     */
//    public int getActiveTouchPointCount() {
//        return activeTouchPoints.size();
//    }
//
//    /**
//     * 触摸点信息类
//     */
//    private static class TouchPoint {
//        float x, y;
//        GestureDescription.StrokeDescription stroke;
//
//        TouchPoint(float x, float y, GestureDescription.StrokeDescription stroke) {
//            this.x = x;
//            this.y = y;
//            this.stroke = stroke;
//        }
//    }
//
//    public boolean simulateTouchDownTest(float x, float y, int pointerId) {
//        if (!isEnabled()) {
//            Log.w(TAG, "Service not enabled");
//            return false;
//        }
//
//        try {
//            Path path = new Path();
//            float x2=x+10;
//            float x3=x+20;
//            path.moveTo(x, y);
//            path.lineTo(x2, y);
//            path.lineTo(x3, y);
//
//            // 创建短暂的手势表示按下
//            GestureDescription.StrokeDescription stroke = new GestureDescription.StrokeDescription(
//                    path, 0,1000// time//10 // 持续10毫秒
//            );
//
//            GestureDescription gesture = new GestureDescription.Builder()
//                    .addStroke(stroke)
//                    .build();
//
//            // 存储触摸点信息
//            activeTouchPoints.put(pointerId, new TouchPoint(x, y, stroke));
////            downTouchPoints.put(pointerId, new TouchPoint(x, y, stroke));
//
//            boolean success = dispatchGesture(gesture, new GestureResultCallback() {
//                @Override
//                public void onCompleted(GestureDescription gestureDescription) {
//                    super.onCompleted(gestureDescription);
//                    Log.d(TAG, "Touch DOWN completed at: " + x + ", " + y);
//                }
//
//                @Override
//                public void onCancelled(GestureDescription gestureDescription) {
//                    super.onCancelled(gestureDescription);
//                    Log.w(TAG, "Touch DOWN cancelled");
//                }
//            }, null);
//
//            Log.d(TAG, "Touch DOWN dispatched: " + success + " at " + x + ", " + y);
//            return success;
//
//        } catch (Exception e) {
//            Log.e(TAG, "Error simulating touch DOWN: " + e.getMessage());
//            return false;
//        }
//    }
//
//    //一开始就2个stroke的情况，pointer0 pointer1都非常正常
//    public boolean simulateTouchMultiStrokeTest(float x, float y, int pointerId) {
//        if (!isEnabled()) {
//            Log.w(TAG, "Service not enabled");
//            return false;
//        }
//
//        try {
//            Path path = new Path();
//            float x2=x+10;
//            float x3=x+20;
//            path.moveTo(x, y);
//            path.lineTo(x2, y);
//            path.lineTo(x3, y);
//
//            Path path2 = new Path();
//            path2.moveTo(500, y);
//            // 创建短暂的手势表示按下
//            GestureDescription.StrokeDescription stroke = new GestureDescription.StrokeDescription(
//                    path, 0,2000// time//10 // 持续10毫秒
//            );
//
//            GestureDescription.StrokeDescription stroke2 = new GestureDescription.StrokeDescription(
//                    path2, 1000,10// time//10 // 持续10毫秒
//            );
//
//            GestureDescription gesture = new GestureDescription.Builder()
//                    .addStroke(stroke)
//                    .addStroke(stroke2)
//                    .build();
//
//            // 存储触摸点信息
//            activeTouchPoints.put(pointerId, new TouchPoint(x, y, stroke));
////            downTouchPoints.put(pointerId, new TouchPoint(x, y, stroke));
//
//            boolean success = dispatchGesture(gesture, new GestureResultCallback() {
//                @Override
//                public void onCompleted(GestureDescription gestureDescription) {
//                    super.onCompleted(gestureDescription);
//                    Log.d(TAG, "Touch DOWN completed at: " + x + ", " + y);
//                }
//
//                @Override
//                public void onCancelled(GestureDescription gestureDescription) {
//                    super.onCancelled(gestureDescription);
//                    Log.w(TAG, "Touch DOWN cancelled");
//                }
//            }, null);
//
//            Log.d(TAG, "Touch DOWN dispatched: " + success + " at " + x + ", " + y);
//            return success;
//
//        } catch (Exception e) {
//            Log.e(TAG, "Error simulating touch DOWN: " + e.getMessage());
//            return false;
//        }
//    }
//
//    //第2次stroke会打断第一次的stroke
//    public boolean simulateTouchMultiStrokeTest2(float x, float y, int pointerId) {
//        if (!isEnabled()) {
//            Log.w(TAG, "Service not enabled");
//            return false;
//        }
//
//        try {
//            Path path = new Path();
//            float x2=x+10;
//            float x3=x+20;
//            path.moveTo(x, y);
//            path.lineTo(x2, y);
//            path.lineTo(x3, y);
//
//            Path path2 = new Path();
//            path2.moveTo(500, y);
//            // 创建短暂的手势表示按下
//            GestureDescription.StrokeDescription stroke = new GestureDescription.StrokeDescription(
//                    path, 0,2000// time//10 // 持续10毫秒
//            );
//
//            GestureDescription.StrokeDescription stroke2 = new GestureDescription.StrokeDescription(
//                    path2,
//                    0,//1000,
//                    10// time//10 // 持续10毫秒
//            );
//
//            GestureDescription gesture = new GestureDescription.Builder()
//                    .addStroke(stroke)
////                    .addStroke(stroke2)
//                    .build();
//
//            // 存储触摸点信息
//            activeTouchPoints.put(pointerId, new TouchPoint(x, y, stroke));
////            downTouchPoints.put(pointerId, new TouchPoint(x, y, stroke));
//
//            boolean success = dispatchGesture(gesture, new GestureResultCallback() {
//                @Override
//                public void onCompleted(GestureDescription gestureDescription) {
//                    super.onCompleted(gestureDescription);
//                    Log.d(TAG, "Touch DOWN completed at: " + x + ", " + y);
//                }
//
//                @Override
//                public void onCancelled(GestureDescription gestureDescription) {
//                    super.onCancelled(gestureDescription);
//                    Log.w(TAG, "Touch DOWN cancelled");
//                }
//            }, null);
//
//            GestureDescription gesture2 = new GestureDescription.Builder()
////                    .addStroke(stroke)
//                    .addStroke(stroke2)
//                    .build();
//            handler.postDelayed(new Runnable() {
//                @Override
//                public void run() {
//
//                    boolean success = dispatchGesture(gesture2, new GestureResultCallback() {
//                        @Override
//                        public void onCompleted(GestureDescription gestureDescription) {
//                            super.onCompleted(gestureDescription);
//                            Log.d(TAG, "Touch DOWN completed at: " + x + ", " + y);
//                        }
//
//                        @Override
//                        public void onCancelled(GestureDescription gestureDescription) {
//                            super.onCancelled(gestureDescription);
//                            Log.w(TAG, "Touch DOWN cancelled");
//                        }
//                    }, null);
//                }
//            }, 1000);
////            Thread.sleep(1000);
//
//            Log.d(TAG, "Touch DOWN dispatched: " + success + " at " + x + ", " + y);
//            return success;
//
//        } catch (Exception e) {
//            Log.e(TAG, "Error simulating touch DOWN: " + e.getMessage());
//            return false;
//        }
//    }
//
//    //第二次dispatchGesture时带上stroke1也还是会打断stroke1
//    public boolean simulateTouchMultiStrokeTest3(float x, float y, int pointerId) {
//        if (!isEnabled()) {
//            Log.w(TAG, "Service not enabled");
//            return false;
//        }
//
//        try {
//            Path path = new Path();
//            float x2=x+10;
//            float x3=x+20;
//            path.moveTo(x, y);
//            path.lineTo(x2, y);
//            path.lineTo(x3, y);
//
//            Path path2 = new Path();
//            path2.moveTo(500, y);
//            Path path3 = new Path();
//            path3.moveTo(x+20, y);
//            // 创建短暂的手势表示按下
//            GestureDescription.StrokeDescription stroke = new GestureDescription.StrokeDescription(
//                    path, 0,2000// time//10 // 持续10毫秒
//                    ,true
//            );
//
//            GestureDescription.StrokeDescription stroke2 = new GestureDescription.StrokeDescription(
//                    path2,
//                    0,//1000,
//                    10// time//10 // 持续10毫秒
//            );
//
//            GestureDescription gesture = new GestureDescription.Builder()
//                    .addStroke(stroke)
////                    .addStroke(stroke2)
//                    .build();
//
//            // 存储触摸点信息
//            activeTouchPoints.put(pointerId, new TouchPoint(x, y, stroke));
////            downTouchPoints.put(pointerId, new TouchPoint(x, y, stroke));
//
//            boolean success = dispatchGesture(gesture, new GestureResultCallback() {
//                @Override
//                public void onCompleted(GestureDescription gestureDescription) {
//                    super.onCompleted(gestureDescription);
//                    Log.d(TAG, "Touch DOWN completed at: " + x + ", " + y);
//                }
//
//                @Override
//                public void onCancelled(GestureDescription gestureDescription) {
//                    super.onCancelled(gestureDescription);
//                    Log.w(TAG, "Touch DOWN cancelled");
//                }
//            }, null);
//
////            GestureDescription.StrokeDescription stroke3 = stroke.continueStroke(
////                    path3,
////                    0,//1000,
////                    1000// time//10 // 持续10毫秒
////                    ,true
////            );
//            int displayId=gesture.getDisplayId();
//            GestureDescription gesture2 = new GestureDescription.Builder()
//                    .setDisplayId(displayId)
//                    .addStroke(stroke)
////                    .addStroke(stroke3)
//                    .addStroke(stroke2)
//                    .build();
//            handler.postDelayed(new Runnable() {
//                @Override
//                public void run() {
//
//                    boolean success = dispatchGesture(gesture2, new GestureResultCallback() {
//                        @Override
//                        public void onCompleted(GestureDescription gestureDescription) {
//                            super.onCompleted(gestureDescription);
//                            Log.d(TAG, "Touch DOWN completed at: " + x + ", " + y);
//                        }
//
//                        @Override
//                        public void onCancelled(GestureDescription gestureDescription) {
//                            super.onCancelled(gestureDescription);
//                            Log.w(TAG, "Touch DOWN cancelled");
//                        }
//                    }, null);
//                }
//            }, 1000);
////            Thread.sleep(1000);
//
//            Log.d(TAG, "Touch DOWN dispatched: " + success + " at " + x + ", " + y);
//            return success;
//
//        } catch (Exception e) {
//            Log.e(TAG, "Error simulating touch DOWN: " + e.getMessage());
//            return false;
//        }
//    }
//
//    private GestureResultCallback gestureResultCallback=null;
//    //试试用GestureUtils
//    //虽然也会cancel,但pointer0 和 pointer1没错，已经是最好的情况了
//    public boolean simulateTouchMultiStrokeTest4(float x, float y, int pointerId) {
//        if (!isEnabled()) {
//            Log.w(TAG, "Service not enabled");
//            return false;
//        }
//
//        try {
//            PointF p1=new PointF(x,y);
//            PointF p2=new PointF(x,y);
//            PointF p3=new PointF(x+200,y+200);
////            GestureDescription.StrokeDescription stroke1=GestureUtils.click(p1);
//            GestureDescription.StrokeDescription stroke1=GestureUtils.pointerDown(p1);
//            GestureDescription.StrokeDescription stroke2=GestureUtils.pointerUp(stroke1);
//            GestureDescription.StrokeDescription stroke3=GestureUtils.click(p3);
//
//            GestureDescription gesture1 = new GestureDescription.Builder()
//                    .addStroke(stroke1)
////                    .addStroke(stroke3)
////                    .addStroke(stroke2)
//                    .build();
//            int displayId=gesture1.getDisplayId();
//
//            boolean success = dispatchGesture(gesture1, gestureResultCallback, null);
//            handler.postDelayed(new Runnable() {
//                @Override
//                public void run() {
////                    GestureDescription.StrokeDescription stroke2=GestureUtils.pointerUp(stroke1);
//                    GestureDescription.StrokeDescription stroke4=GestureUtils.drag(stroke1,p2);
//                    GestureDescription gesture2=GestureUtils.getGestureBuilder(
//                            displayId,//displayId,//用1时不会触发
//                            stroke1,//会打断
////                            stroke2//,//
////                            stroke4,
//                            stroke3
//                    ).build();
//                    boolean success = dispatchGesture(gesture2, gestureResultCallback, null);
//                }
//            }, 1000);
//            handler.postDelayed(new Runnable() {
//                @Override
//                public void run() {
////                    GestureDescription.StrokeDescription stroke2=GestureUtils.pointerUp(stroke1);
//                    GestureDescription gesture2=GestureUtils.getGestureBuilder(displayId,stroke2).build();
//                    boolean success = dispatchGesture(gesture2, gestureResultCallback, null);
//                }
//            }, 2000);
//////            GestureUtils.getGestureBuilder(gesture1.getDisplayId());
//
//            return true;
//
//        } catch (Exception e) {
//            Log.e(TAG, "Error simulating touch DOWN: " + e.getMessage());
//            return false;
//        }
//    }
//    //-----------------------模拟摇杆 end--------------------------
//}