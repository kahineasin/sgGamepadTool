package com.sellgirl.gamepadtool.android.simulate;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.GestureDescription;
import android.content.Context;
import android.graphics.Path;
import android.graphics.PointF;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.MotionEvent;
import android.view.accessibility.AccessibilityEvent;

import com.sellgirl.gamepadtool.phone.ISGTouchSimulate;

import java.util.HashMap;
import java.util.Map;
import com.sellgirl.gamepadtool.android.GestureUtils;
import com.sellgirl.gamepadtool.android.FocusOverlayView;
import com.sellgirl.sgGameHelper.list.Array2;
import com.sellgirl.sgJavaHelper.SGDate;
import com.sellgirl.sgJavaHelper.config.SGDataHelper;

// TouchSimulationService.java

/**
 * 手势方式模拟
 * 此版本存在问题
 * 1. 无法准确表达DOWN MOVE UP, 下次手势会cancel上次
 *
 * 尝试使用StrokeDescription.mContinued解决上面问题。成功解决DOWN UP问题
 *
 * 但有新问题：
 * 1. 多点触摸时，mContinued无效了。
 */
public class TouchSimulationService extends AccessibilityService implements ISGTouchSimulate

{
    private static final String TAG = "TouchSimulation";
    private static int instanceCnt=0;
    public int uuid=0;
    private static TouchSimulationService instance;

    //测试用的字段
    private boolean testOneTime=false;
    private Handler handler;
    private Array2<GestureDescription.StrokeDescription> keyStrokes=null;
//    private Array2<GestureDescription.StrokeDescription> needRemoveStrokes=null;
    private Array2<Integer> needRemoveStrokes=null;
    private boolean showLog=false;
//    private GestureDescription.Builder builder=null;
    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {}

    @Override
    public void onInterrupt() {}

    @Override
    protected void onServiceConnected() {
        super.onServiceConnected();
        instance = this;//这句似乎放在onServiceConnected的最后执行比较合适？好像不一定，再考虑一下
        uuid=instanceCnt++;
//         builder=new GestureDescription.Builder();

        handler = new Handler(Looper.getMainLooper());
        gestureResultCallback=new GestureResultCallback() {
            @Override
            public void onCompleted(GestureDescription gestureDescription) {
                super.onCompleted(gestureDescription);
            }

            @Override
            public void onCancelled(GestureDescription gestureDescription) {
                super.onCancelled(gestureDescription);
//                dispatchGesture(gestureDescription, new GestureResultCallback() {
//                    @Override
//                    public void onCompleted(GestureDescription gestureDescription) {
//                        super.onCompleted(gestureDescription);
//                    }
//
//                    @Override
//                    public void onCancelled(GestureDescription gestureDescription) {
//                        super.onCancelled(gestureDescription);
//                    }
//                },null);
            }
        };
        keyStrokes=new Array2<>();
        needRemoveStrokes=new Array2<>();
        downStroke=new Array2<>();
        needMove=new HashMap<>();
    }

    public static TouchSimulationService getInstance() {
        return instance;
    }

    // 核心：模拟触摸事件
    private void simulateTouch(float x, float y, int action, long duration) {
//        if(true) {
//            return;
//        }
        if (!isEnabled()) return;


////        GestureDescription gesture = new GestureDescription.Builder()
////                .addStroke(stroke)
////                .build();
//        GestureDescription.Builder builder=new GestureDescription.Builder()
//                ;
////        if(true) {//这里为止,摇杆不断
////            return;
////        }

//        if(null!=downStroke){
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//////                Path path2 = new Path(downStroke.getPath());
//                Path path2 = new Path();
//                path2.moveTo(this.x, this.y);
////                path2.lineTo(this.x, this.y);
////                TouchPoint previousPoint = activeTouchPoints.get(pointerId);
////                Path path2 = new Path();
////                path2.moveTo(previousPoint.x, previousPoint.y);
////                path2.lineTo(x, y);
//                GestureDescription.StrokeDescription downStroke2=downStroke.continueStroke(path2,0,time,true);
//                builder.addStroke(downStroke2);
//
////                //有downStroke3的情况下，无论如何摇杆都会断
////                Path path3 = new Path();
//////                path2.moveTo(this.x, this.y);
////                path3.moveTo(x, y);
//////                GestureDescription.StrokeDescription downStroke3=downStroke.continueStroke(path3,0,10,false);//断摇杆
////                GestureDescription.StrokeDescription downStroke3= new GestureDescription.StrokeDescription(path3, 0, 10);//断摇杆
//////                GestureDescription.StrokeDescription downStroke3=downStroke.continueStroke(path3,0,10,true);//断摇杆
////                builder.addStroke(downStroke3);
//
//                downStroke=downStroke2;
//            }
////            if(true) {//这里为止,摇杆不断
////                return;
////            }
//        }
//        else{
//            if(true) {//这里为止,摇杆不断
//                return;
//            }
//            Path path = new Path();
//            path.moveTo(x, y);
//
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N
//            ) {
//
//            }else{
//                return;
//            }
//
//            GestureDescription.StrokeDescription stroke;
//            if (action == MotionEvent.ACTION_DOWN) {
//                // 按下事件
//                stroke = new GestureDescription.StrokeDescription(path, 0, duration);
//            } else {
//                // 其他事件处理
//                stroke = new GestureDescription.StrokeDescription(path, 0, 10);
//            }
//            builder.addStroke(stroke);
//        }

        Path path = new Path();
        path.moveTo(x, y);

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N
//        ) {
//
//        }else{
//            return;
//        }

        GestureDescription.StrokeDescription stroke;
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
//        builder.addStroke(stroke);
        stroke = new GestureDescription.StrokeDescription(path, 0, 10);
        keyStrokes.add(stroke);
        Log.w(TAG, "tap ["+x+","+y+"]");

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
    }

    //----------------------我补充的--------------------
    public boolean isEnabled(){
        return true;
    }
    // 供LibGDX调用的方法
    @Override
    public void simulateTouchFromGdx(
            float x,  float y//, final int action
    ) {

//        // 转换坐标：LibGDX坐标 -> Android屏幕坐标
//        float screenX = x;
////        float screenY = getResources().getDisplayMetrics().heightPixels - y;
//        float screenY = y;
////        simulateTouch(screenX, screenY,
////                MotionEvent.ACTION_DOWN,//action,
////                50);
//        simulateTouch(screenX, screenY,
//                MotionEvent.ACTION_UP,//action,
//                50);

        if (!isEnabled()) return;

        Path path = new Path();
        path.moveTo(x, y);
//        Path path = new Path();
//        path.moveTo(x0, y0);
//        path.lineTo(x, y);

        GestureDescription.StrokeDescription stroke;
        stroke = new GestureDescription.StrokeDescription(path, 0, 10);
        keyStrokes.add(stroke);
        log( "tap ["+x+","+y+"]");

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
        return null!=instance;
    }


    //-----------------------模拟摇杆--------------------------
    // 存储当前活动的触摸点
    private Map<Integer, TouchPoint> activeTouchPoints = new HashMap<>();
//    private Map<Integer, TouchPoint> downTouchPoints = new HashMap<>();

    private long time=10;//100;//13较少cancel;//2000;//50;//17;
//    private GestureDescription.StrokeDescription downStroke=null;
    //此字段一定要保证顺序，是为了尽量使Gesture的pointerId不变动
    private Array2<DownStroke> downStroke=null;
    private static class DownStroke{
        public int pointerId=0;
        public GestureDescription.StrokeDescription stroke=null;
        public float x0;
        public float y0;
    }
//    private boolean needMove=false;
    private HashMap<Integer,Boolean> needMove;
//    private GestureDescription.StrokeDescription clickStroke=null;
//    private float x=0;
//    private float y=0;
    private void setStroke(int pointerId,GestureDescription.StrokeDescription stroke

    ){
        int idx=0;
        int result=-1;
        for(DownStroke i:downStroke){
            if(i.pointerId==pointerId){
                result=idx;
                i.stroke=stroke;
            }
            idx++;
        }
        if(0>result){
            DownStroke n=new DownStroke();
            n.pointerId=pointerId;
            n.stroke=stroke;
            downStroke.add(n);
        }
    }

    private void setStroke(int pointerId,GestureDescription.StrokeDescription stroke
            ,float x0,float y0
    ){
        int idx=0;
        int result=-1;
        for(DownStroke i:downStroke){
            if(i.pointerId==pointerId){
                result=idx;
                i.stroke=stroke;
                i.x0=x0;
                i.y0=y0;
            }
            idx++;
        }
        if(0>result){
            DownStroke n=new DownStroke();
            n.pointerId=pointerId;
            n.stroke=stroke;
            n.x0=x0;
            n.y0=y0;
            downStroke.add(n);
        }
    }
    private GestureDescription.StrokeDescription getStroke(int pointerId){
//        int idx=0;
//        int result=-1;
        for(DownStroke i:downStroke){
            if(i.pointerId==pointerId){
//                result=idx;
//                i.stroke=stroke;
                return i.stroke;
            }
//            idx++;
        }
        return null;
//        if(0>result){
//            DownStroke n=new DownStroke();
//            n.pointerId=pointerId;
//            n.stroke=stroke;
//            downStroke.add(n);
//        }
    }
    /**
     * 模拟触摸按下事件 (ACTION_DOWN)
     * @param x 屏幕X坐标
     * @param y 屏幕Y坐标
     * @param pointerId 触摸点ID（用于多点触控）
     * @return 是否成功
     */
    public boolean simulateTouchDown(float x0, float y0,float x, float y, int pointerId) {
        if(testOneTime){
//            simulateTouchDownTest(x,y,pointerId);
//            simulateTouchMultiStrokeTest(x,y,pointerId);//ok
//            simulateTouchMultiStrokeTest2(x,y,pointerId);//no
//            simulateTouchMultiStrokeTest3(x,y,pointerId);//no
//            simulateTouchMultiStrokeTest4(x,y,pointerId);//有cancel,但应该可用
            simulateTouchMultiStrokeTest5(x,y,pointerId);
            return true;
        }
        if (!isEnabled()) {
            Log.w(TAG, "Service not enabled");
            return false;
        }

        try {
//            Path path = new Path();
//            path.moveTo(x, y);
            Path path = new Path();
            path.moveTo(x0, y0);
            path.lineTo(x, y);

            // 创建短暂的手势表示按下
            GestureDescription.StrokeDescription stroke = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                stroke = new GestureDescription.StrokeDescription(
                        path, 0,10// time//10 // 持续10毫秒
                        ,true
                );
//                downStroke=stroke;
//                setStroke(pointerId,stroke);
                setStroke(pointerId,stroke,x0,y0);
//                needMove=true;
                needMove.put(pointerId,true);
                log(  "DOWN ["+x+","+y+"]");
//                clickStroke = new GestureDescription.StrokeDescription(
//                        new Path(), 0,10// time//10 // 持续10毫秒
//                        ,true
//                );
            }
//            else{
//                stroke = new GestureDescription.StrokeDescription(
//                        path, 0,time// time//10 // 持续10毫秒
//                        //,true
//                );
//            }

//            GestureDescription gesture = new GestureDescription.Builder()
//                    .addStroke(stroke)
////                    .addStroke(clickStroke)
//                    .build();

            // 存储触摸点信息
            activeTouchPoints.put(pointerId, new TouchPoint(x, y, stroke));
//            downTouchPoints.put(pointerId, new TouchPoint(x, y, stroke));

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

//            Log.d(TAG, "Touch DOWN dispatched: " + success + " at " + x + ", " + y);
            return true;

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

        if(testOneTime){
            return true;
        }
        if (!isEnabled()) {
            Log.w(TAG, "Service not enabled");
            return false;
        }

        if(null!=downStroke){
            TouchPoint previousPoint = activeTouchPoints.get(pointerId);
            Path path = new Path();
            path.moveTo(previousPoint.x, previousPoint.y);
            path.lineTo(x, y);
            float x2=previousPoint.x;
            float y2=previousPoint.y;
//            this.x=x;
//            this.y=y;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                GestureDescription.StrokeDescription downStroke=getStroke(pointerId);
                downStroke=
                        downStroke.continueStroke(path,0,time,true);
//                    clickStroke=clickStroke.continueStroke(new Path(),0,time,true);
                setStroke(pointerId,downStroke);
//                needMove=true;
                needMove.put(pointerId,true);
                log(  "MOVE ["+x2+","+y2+"]->["+x+","+y+"]");
                GestureDescription.StrokeDescription stroke=downStroke;
//                GestureDescription gesture = new GestureDescription.Builder()
//                        .addStroke(stroke)
////                            .addStroke(clickStroke)
//                        .build();

                // 更新触摸点信息
                activeTouchPoints.put(pointerId, new TouchPoint(x, y, stroke));

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
                return true;
            }
        }

        return false;
//        TouchPoint previousPoint = activeTouchPoints.get(pointerId);
//        if (previousPoint == null) {
//            Log.w(TAG, "No active touch point found for pointer: " + pointerId);
//            // 如果没有找到先前的触摸点，创建一个新的按下事件
//            return simulateTouchDown(x, y, pointerId);
//        }
//        return false;

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
    }

//    public boolean simulateTouchMove2(float bx,float by,float x, float y, int pointerId) {
//        if (!isEnabled()) {
//            Log.w(TAG, "Service not enabled");
//            return false;
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
////            path.moveTo(previousPoint.x, previousPoint.y);
//            path.moveTo(bx, by);
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
//                    Log.d(TAG, "Touch MOVE completed to: " + x + ", " + y);
//                }
//
//                @Override
//                public void onCancelled(GestureDescription gestureDescription) {
//                    super.onCancelled(gestureDescription);
//                    Log.w(TAG, "Touch MOVE cancelled");
//                }
//            }, null);
//
//            Log.d(TAG, "Touch MOVE dispatched: " + success + " to " + x + ", " + y);
//            return success;
//
//        } catch (Exception e) {
//            Log.e(TAG, "Error simulating touch MOVE: " + e.getMessage());
//            return false;
//        }
//    }
    /**
     * 模拟触摸释放事件 (ACTION_UP)
     * @param x 释放时的屏幕X坐标
     * @param y 释放时的屏幕Y坐标
     * @param pointerId 触摸点ID
     * @return 是否成功
     */
    public boolean simulateTouchUp(float x, float y, int pointerId) {
        if(testOneTime){
            return true;
        }
        if (!isEnabled()) {
            Log.w(TAG, "Service not enabled");
            return false;
        }

        if(null!=downStroke){
            TouchPoint previousPoint = activeTouchPoints.get(pointerId);
            Path path = new Path();
            path.moveTo(previousPoint.x, previousPoint.y);
            path.lineTo(x, y);
            float x2=previousPoint.x;
            float y2=previousPoint.y;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                GestureDescription.StrokeDescription downStroke=getStroke(pointerId);
                downStroke=
                        downStroke.continueStroke(path,0,10,false);
                setStroke(pointerId,downStroke);
//                    clickStroke=clickStroke.continueStroke(new Path(),0,10,false);

//                GestureDescription.StrokeDescription stroke=downStroke;
////                downStroke=null;
//                needMove=true;
                needMove.put(pointerId,true);
                log( "UP ["+x2+","+y2+"]->["+x+","+y+"]");

//                needRemoveStrokes.add(downStroke);
                needRemoveStrokes.add(pointerId);
//                GestureDescription gesture = new GestureDescription.Builder()
//                        .addStroke(stroke)
////                            .addStroke(clickStroke)
//                        .build();

                // 更新触摸点信息
//                activeTouchPoints.put(pointerId, new TouchPoint(x, y, stroke));
                activeTouchPoints.remove(pointerId);

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
                return true;
            }
        }
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
        return false;
    }

    @Override
    public boolean simulateDrag(float x0, float y0, float x, float y) {

        if (!isEnabled()) return false;

//        Path path = new Path();
//        path.moveTo(x, y);
        Path path = new Path();
        path.moveTo(x0, y0);
        path.lineTo(x, y);

        GestureDescription.StrokeDescription stroke;
        stroke = new GestureDescription.StrokeDescription(path, 0, 10);
        keyStrokes.add(stroke);
        log( "UP ["+x0+","+y0+"]->["+x+","+y+"]");
        return true;
    }


    /**
     * 模拟完整的点击事件（按下+释放）
     * @param x 点击的屏幕X坐标
     * @param y 点击的屏幕Y坐标
     * @param duration 点击持续时间（毫秒）
     * @return 是否成功
     * @Deprecated 改为批量simulate()
     */
    @Deprecated
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

    //一开始就2个stroke的情况，pointer0 pointer1都非常正常
    public boolean simulateTouchMultiStrokeTest(float x, float y, int pointerId) {
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

            Path path2 = new Path();
            path2.moveTo(500, y);
            // 创建短暂的手势表示按下
            GestureDescription.StrokeDescription stroke = new GestureDescription.StrokeDescription(
                    path, 0,2000// time//10 // 持续10毫秒
            );

            GestureDescription.StrokeDescription stroke2 = new GestureDescription.StrokeDescription(
                    path2, 1000,10// time//10 // 持续10毫秒
            );

            GestureDescription gesture = new GestureDescription.Builder()
                    .addStroke(stroke)
                    .addStroke(stroke2)
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

    //第2次stroke会打断第一次的stroke
    public boolean simulateTouchMultiStrokeTest2(float x, float y, int pointerId) {
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

            Path path2 = new Path();
            path2.moveTo(500, y);
            // 创建短暂的手势表示按下
            GestureDescription.StrokeDescription stroke = new GestureDescription.StrokeDescription(
                    path, 0,2000// time//10 // 持续10毫秒
            );

            GestureDescription.StrokeDescription stroke2 = new GestureDescription.StrokeDescription(
                    path2,
                    0,//1000,
                    10// time//10 // 持续10毫秒
            );

            GestureDescription gesture = new GestureDescription.Builder()
                    .addStroke(stroke)
//                    .addStroke(stroke2)
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

            GestureDescription gesture2 = new GestureDescription.Builder()
//                    .addStroke(stroke)
                    .addStroke(stroke2)
                    .build();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {

                    boolean success = dispatchGesture(gesture2, new GestureResultCallback() {
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
                }
            }, 1000);
//            Thread.sleep(1000);

            Log.d(TAG, "Touch DOWN dispatched: " + success + " at " + x + ", " + y);
            return success;

        } catch (Exception e) {
            Log.e(TAG, "Error simulating touch DOWN: " + e.getMessage());
            return false;
        }
    }

    //第二次dispatchGesture时带上stroke1也还是会打断stroke1
    public boolean simulateTouchMultiStrokeTest3(float x, float y, int pointerId) {
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

            Path path2 = new Path();
            path2.moveTo(500, y);
            Path path3 = new Path();
            path3.moveTo(x+20, y);
            // 创建短暂的手势表示按下
            GestureDescription.StrokeDescription stroke = new GestureDescription.StrokeDescription(
                    path, 0,2000// time//10 // 持续10毫秒
                    ,true
            );

            GestureDescription.StrokeDescription stroke2 = new GestureDescription.StrokeDescription(
                    path2,
                    0,//1000,
                    10// time//10 // 持续10毫秒
            );

            GestureDescription gesture = new GestureDescription.Builder()
                    .addStroke(stroke)
//                    .addStroke(stroke2)
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

//            GestureDescription.StrokeDescription stroke3 = stroke.continueStroke(
//                    path3,
//                    0,//1000,
//                    1000// time//10 // 持续10毫秒
//                    ,true
//            );
            int displayId=gesture.getDisplayId();
            GestureDescription gesture2 = new GestureDescription.Builder()
                    .setDisplayId(displayId)
                    .addStroke(stroke)
//                    .addStroke(stroke3)
                    .addStroke(stroke2)
                    .build();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {

                    boolean success = dispatchGesture(gesture2, new GestureResultCallback() {
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
                }
            }, 1000);
//            Thread.sleep(1000);

            Log.d(TAG, "Touch DOWN dispatched: " + success + " at " + x + ", " + y);
            return success;

        } catch (Exception e) {
            Log.e(TAG, "Error simulating touch DOWN: " + e.getMessage());
            return false;
        }
    }

    private GestureResultCallback gestureResultCallback=null;
    //试试用GestureUtils
    //虽然也会cancel,但pointer0 和 pointer1没错，已经是最好的情况了
    public boolean simulateTouchMultiStrokeTest4(float x, float y, int pointerId) {
        if (!isEnabled()) {
            Log.w(TAG, "Service not enabled");
            return false;
        }

        try {
            PointF p1=new PointF(x,y);
            PointF p2=new PointF(x,y);
            PointF p3=new PointF(x+200,y+200);
//            GestureDescription.StrokeDescription stroke1=GestureUtils.click(p1);
            GestureDescription.StrokeDescription stroke1=GestureUtils.pointerDown(p1);
            GestureDescription.StrokeDescription stroke2=GestureUtils.pointerUp(stroke1);
            GestureDescription.StrokeDescription stroke3=GestureUtils.click(p3);

            GestureDescription gesture1 = new GestureDescription.Builder()
                    .addStroke(stroke1)
//                    .addStroke(stroke3)
//                    .addStroke(stroke2)
                    .build();
            int displayId=gesture1.getDisplayId();

            boolean success = dispatchGesture(gesture1, gestureResultCallback, null);
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
//                    GestureDescription.StrokeDescription stroke2=GestureUtils.pointerUp(stroke1);
                    GestureDescription.StrokeDescription stroke4=GestureUtils.drag(stroke1,p2);
                    GestureDescription gesture2=GestureUtils.getGestureBuilder(
                            displayId,//displayId,//用1时不会触发
                            stroke1,//会打断
//                            stroke2//,//
//                            stroke4,
                            stroke3
                    ).build();
                    boolean success = dispatchGesture(gesture2, gestureResultCallback, null);
                }
            }, 1000);
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
//                    GestureDescription.StrokeDescription stroke2=GestureUtils.pointerUp(stroke1);
                    GestureDescription gesture2=GestureUtils.getGestureBuilder(displayId,stroke2).build();
                    boolean success = dispatchGesture(gesture2, gestureResultCallback, null);
                }
            }, 2000);
////            GestureUtils.getGestureBuilder(gesture1.getDisplayId());

            return true;

        } catch (Exception e) {
            Log.e(TAG, "Error simulating touch DOWN: " + e.getMessage());
            return false;
        }
    }

    public boolean simulateTouchMultiStrokeTest5(float x, float y, int pointerId) {
        if (!isEnabled()) {
            Log.w(TAG, "Service not enabled");
            return false;
        }

        try {
            PointF p1=new PointF(x,y);
            PointF p2=new PointF(x+100,y+100);//drag
            PointF p3=new PointF(x+200,y+200);//click
//            PointF p4=new PointF(x+300,y+300);//
//            GestureDescription.StrokeDescription stroke1=GestureUtils.click(p1);
            GestureDescription.StrokeDescription stroke1=GestureUtils.pointerDown(p1);
            GestureDescription.StrokeDescription stroke2=GestureUtils.pointerUp(stroke1);

            GestureDescription gesture1 = new GestureDescription.Builder()
                    .addStroke(stroke1)
//                    .addStroke(stroke3)
//                    .addStroke(stroke2)
                    .build();
            int displayId=gesture1.getDisplayId();
            GestureDescription.StrokeDescription[] stroke4=new GestureDescription.StrokeDescription[]{null};
            boolean success = dispatchGesture(gesture1, gestureResultCallback, null);
            //1秒后drag+click
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
//                    GestureDescription.StrokeDescription stroke2=GestureUtils.pointerUp(stroke1);
//                     stroke4[0]=GestureUtils.drag(stroke1,p2);
                    GestureDescription.StrokeDescription stroke3=GestureUtils.click(p3);
                    Path path = new Path();
                    path.moveTo(p1.x, p1.y);
                    path.lineTo(p2.x, p2.y);
                    stroke4[0]=new GestureDescription.StrokeDescription(path,0,10,true);
                    GestureDescription gesture2=GestureUtils.getGestureBuilder(
                            displayId,//displayId,//用1时不会触发
                            stroke4[0],//会打断
//                            stroke2//,//
//                            stroke1,
                            stroke3
                    ).build();
                    boolean success = dispatchGesture(gesture2, gestureResultCallback, null);
                }
            }, 1000);
            //2秒后click
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
//                    GestureDescription.StrokeDescription stroke2=GestureUtils.pointerUp(stroke1);
                    GestureDescription.StrokeDescription stroke3=GestureUtils.click(p3);
//                    stroke4[0]=GestureUtils.drag(stroke1,p2);
                    stroke4[0]=GestureUtils.pointerUp(stroke1);
                    GestureDescription gesture2=GestureUtils.getGestureBuilder(displayId,
                            stroke4[0]//,
//                            stroke3
                    ).build();
                    boolean success = dispatchGesture(gesture2, gestureResultCallback, null);
                }
            }, 2000);
            //3秒后UP
//            handler.postDelayed(new Runnable() {
//                @Override
//                public void run() {
////                    GestureDescription.StrokeDescription stroke2=GestureUtils.pointerUp(stroke1);
//                    stroke4[0]=GestureUtils.pointerUp(stroke4[0]);
////                    GestureDescription.StrokeDescription stroke3=GestureUtils.click(p3);
//                    GestureDescription gesture2=GestureUtils.getGestureBuilder(
//                            displayId,//displayId,//用1时不会触发
//                            stroke4[0]//,//会打断
////                            stroke2//,//
////                            stroke4,
////                            stroke3
//                    ).build();
//                    boolean success = dispatchGesture(gesture2, gestureResultCallback, null);
//                }
//            }, 3000);
////            GestureUtils.getGestureBuilder(gesture1.getDisplayId());

            return true;

        } catch (Exception e) {
            Log.e(TAG, "Error simulating touch DOWN: " + e.getMessage());
            return false;
        }
    }

    //-----------------------模拟摇杆 end--------------------------

    @Override
    public boolean simulate() {
        if((downStroke.isEmpty()||needMove.isEmpty())&&keyStrokes.isEmpty()){
            return true;
        }

//        //uuid永远是0
//        SGDataHelper.getLog().print(TAG+" uuid:"+uuid+" "+ SGDate.Now().toString());

        int cnt=0;
        GestureDescription.Builder builder=new GestureDescription.Builder();
        if(!downStroke.isEmpty()){
            //根据测试，只有单stroke时支持多次dispatch&continue
            if(keyStrokes.isEmpty()&&2>downStroke.size()) {
//                builder.addStroke(downStroke);
                builder.addStroke(downStroke.get(0).stroke);
                needMove.remove(downStroke.get(0).pointerId);
                cnt++;
            }else{
                //stroke数量有变动时，用continue反而没办法处理了
                //这样是新路径，会使虚拟摇杆的中点偏移（不同游戏可能有不同的表现，不好处理）
//                GestureDescription.StrokeDescription downStroke=getStroke()
                //for(DownStroke i:downStroke){
                for(int idx=0;downStroke.size()>idx;idx++){
                    DownStroke i=downStroke.get(idx);
                    TouchPoint previousPoint = activeTouchPoints.get(i.pointerId);

                    Path path2 = new Path();
                    path2.moveTo(i.x0, i.y0);
                    if(null!=previousPoint) {
                        path2.lineTo(previousPoint.x, previousPoint.y);
                    }
//                    GestureDescription.StrokeDescription stroke=new GestureDescription.StrokeDescription(i.stroke.getPath(),0,10,true);
                    GestureDescription.StrokeDescription stroke=new GestureDescription.StrokeDescription(path2,0,10,true);
//                    if(!needRemoveStrokes.isEmpty()){
//                        needRemoveStrokes.clear();
//                        needRemoveStrokes.add(stroke);
//                    }
//                    downStroke=stroke;
                    setStroke(i.pointerId,stroke);

                    builder.addStroke(stroke);
                    needMove.remove(i.pointerId);
                    cnt++;
                }
            }
        }
        for(GestureDescription.StrokeDescription i:keyStrokes){
            builder.addStroke(i);
            cnt++;
        }
        log( "simulate strokeCnt "+cnt);

        GestureDescription gesture=builder.build();
        boolean success = dispatchGesture(gesture, gestureResultCallback, null);
        keyStrokes.clear();
        //todo 改为多摇杆stroke
        if(!needRemoveStrokes.isEmpty()) {
            for (Integer i : needRemoveStrokes) {
//                if (i == downStroke) {
//                    downStroke = null;
//                }
                int find=-1;
                for(int j=0;downStroke.size()>j;j++){
                    if(downStroke.get(j).pointerId==i){
                        find=j;
                        break;
                    }
                }
                if(-1<find) {
                    downStroke.removeIndex(find);
                }
            }
            needRemoveStrokes.clear();
            //为保证pointerId,从后往前删除，遇到未UP的，往前都保留
            for(int j=downStroke.size()-1;0<=j;j++){
                int find=-1;
//                int idx=0;
                for (Integer i : needRemoveStrokes) {
                    if(downStroke.get(j).pointerId==i){
                        find=j;
                        break;
                    }
//                    idx++;
                }
                if(-1<find){
                    downStroke.removeIndex(j);
                    needRemoveStrokes.removeValue(downStroke.get(j).pointerId,true);
                }else{
                    break;
                }
            }
        }
        return success;
    }
    private void log(String s){
        if(showLog) {
            Log.d(TAG, s);
        }
    }
}