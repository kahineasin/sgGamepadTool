//package com.sellgirl.gamepadtool.android.simulate;
//
//import android.accessibilityservice.GestureDescription;
//import android.graphics.PointF;
//import android.util.Log;
//
//import com.sellgirl.gamepadtool.android.GestureUtils;
//
//public class TouchSimulationTest {
//    public static boolean simulateTouchMultiStrokeTest6(
//            float x, float y, int pointerId,
//            boolean isEnabled,String TAG) {
//        if (!isEnabled) {
//            Log.w(TAG, "Service not enabled");
//            return false;
//        }
//
//        try {
//            PointF p1=new PointF(x,y);
//            PointF p2=new PointF(x+100,y+100);//drag
//            PointF p3=new PointF(x+200,y+200);//click
////            PointF p4=new PointF(x+300,y+300);//
////            GestureDescription.StrokeDescription stroke1=GestureUtils.click(p1);
//            GestureDescription.StrokeDescription stroke1= GestureUtils.pointerDown(p1);
//            GestureDescription.StrokeDescription stroke2=GestureUtils.pointerUp(stroke1);
//
//            GestureDescription gesture1 = new GestureDescription.Builder()
//                    .addStroke(stroke1)
////                    .addStroke(stroke3)
////                    .addStroke(stroke2)
//                    .build();
//            int displayId=gesture1.getDisplayId();
//            GestureDescription.StrokeDescription[] stroke4=new GestureDescription.StrokeDescription[]{null};
//            boolean success = dispatchGesture(gesture1, gestureResultCallback, null);
//            //1秒后drag+click
//            handler.postDelayed(new Runnable() {
//                @Override
//                public void run() {
////                    GestureDescription.StrokeDescription stroke2=GestureUtils.pointerUp(stroke1);
////                     stroke4[0]=GestureUtils.drag(stroke1,p2);
//                    GestureDescription.StrokeDescription stroke3=GestureUtils.click(p3);
//                    GestureDescription gesture2=GestureUtils.getGestureBuilder(
//                            displayId,//displayId,//用1时不会触发
////                            stroke4[0],//会打断
////                            stroke2//,//
////                            stroke4,
//                            stroke1,
//                            stroke3
//                    ).build();
//                    boolean success = dispatchGesture(gesture2, gestureResultCallback, null);
//                }
//            }, 1000);
//            //2秒后click
//            handler.postDelayed(new Runnable() {
//                @Override
//                public void run() {
////                    GestureDescription.StrokeDescription stroke2=GestureUtils.pointerUp(stroke1);
//                    GestureDescription.StrokeDescription stroke3=GestureUtils.click(p3);
//                    stroke4[0]=GestureUtils.drag(stroke1,p2);
//                    GestureDescription gesture2=GestureUtils.getGestureBuilder(displayId,
//                            stroke4[0]//,
////                            stroke3
//                    ).build();
//                    boolean success = dispatchGesture(gesture2, gestureResultCallback, null);
//                }
//            }, 2000);
//            //3秒后UP
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
//////            GestureUtils.getGestureBuilder(gesture1.getDisplayId());
//
//            return true;
//
//        } catch (Exception e) {
//            Log.e(TAG, "Error simulating touch DOWN: " + e.getMessage());
//            return false;
//        }
//    }
//}
