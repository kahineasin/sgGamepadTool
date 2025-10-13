package com.sellgirl.gamepadtool.android.simulate;

import android.app.Instrumentation;
import android.graphics.PointF;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.SystemClock;
import android.util.Log;
import android.view.MotionEvent;

import com.sellgirl.gamepadtool.phone.ISGTouchSimulate;

import java.util.List;

/**
 * 我在win10的powershell下执行：
 * adb shell pm grant com.sellgirl.gamepadtool android.permission.INJECT_EVENTS
 * 报错：
 * java.lang.SecurityException: Permission android.permission.INJECT_EVENTS requested by com.sellgirl.gamepadtool is not a changeable permission type
 */
public class InstrumentationInjector implements ISGTouchSimulate {
    private Instrumentation instrumentation;
    private long downTime=0;

    private HandlerThread handlerThread;
    private Handler backgroundHandler;
    public InstrumentationInjector() {
        instrumentation = new Instrumentation();

        // 创建后台线程
        handlerThread = new HandlerThread("InstrumentationThread");
        handlerThread.start();
        // 获取后台线程的Handler
        backgroundHandler = new Handler(handlerThread.getLooper());
    }

    public void sendTouchEvent(float x, float y, int action) {

        backgroundHandler.post(new Runnable() {
            @Override
            public void run() {
                doSendTouchEvent(x,y,action);
            }
        });
    }
    /**
     * 使用 Instrumentation 发送触摸事件
     */
    public void doSendTouchEvent(float x, float y, int action) {
        long downTime = SystemClock.uptimeMillis();
        long eventTime = SystemClock.uptimeMillis();

        MotionEvent event = MotionEvent.obtain(
                downTime,
                eventTime,
                action,
                x,
                y,
                0
        );

        try {
            instrumentation.sendPointerSync(event);
        } catch (Exception e) {
            Log.e("InstrumentationInjector", "Failed to send event: " + e.getMessage());
        } finally {
            event.recycle();
        }
    }


    /**
     * 发送完整的触摸序列
     */
    public void sendTouchSequence(float startX, float startY, List<PointF> movePoints) {
        // DOWN
        sendTouchEvent(startX, startY, MotionEvent.ACTION_DOWN);

        // MOVE 序列
        for (PointF point : movePoints) {
            try {
                Thread.sleep(16); // 模拟真实触摸间隔
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            sendTouchEvent(point.x, point.y, MotionEvent.ACTION_MOVE);
        }

        // UP
        sendTouchEvent(startX, startY, MotionEvent.ACTION_UP);
    }

    @Override
    public void simulateTouchFromGdx(float x, float y) {

    }

    @Override
    public boolean simulateTouchDown(float x, float y, int pointerId) {

        backgroundHandler.post(new Runnable() {
            @Override
            public void run() {
                doSimulateTouchDown(x,y,pointerId);
            }
        });
        return true;
    }

    public boolean doSimulateTouchDown(float x, float y, int pointerId) {

        downTime = SystemClock.uptimeMillis();
        long eventTime = SystemClock.uptimeMillis();

        MotionEvent event = MotionEvent.obtain(
                downTime,
                eventTime,
                MotionEvent.ACTION_DOWN,
                x,
                y,
                0
        );

        try {
            instrumentation.sendPointerSync(event);
        } catch (Exception e) {
            Log.e("InstrumentationInjector", "Failed to send event: " + e.getMessage());
        } finally {
            event.recycle();
        }
        return true;
    }

    @Override
    public boolean simulateTouchMove(float x, float y, int pointerId) {

        backgroundHandler.post(new Runnable() {
            @Override
            public void run() {
                doSimulateTouchMove(x,y,pointerId);
            }
        });
        return true;
    }

//    @Override
    public boolean doSimulateTouchMove(float x, float y, int pointerId) {
//        downTime = SystemClock.uptimeMillis();
        long eventTime = SystemClock.uptimeMillis();

        MotionEvent event = MotionEvent.obtain(
                downTime,
                eventTime,
                MotionEvent.ACTION_MOVE,
                x,
                y,
                0
        );

        try {
            instrumentation.sendPointerSync(event);
        } catch (Exception e) {
            Log.e("InstrumentationInjector", "Failed to send event: " + e.getMessage());
        } finally {
            event.recycle();
        }
        return true;
    }

    @Override
    public boolean simulateTouchUp(float x, float y, int pointerId) {

        backgroundHandler.post(new Runnable() {
            @Override
            public void run() {
                doSimulateTouchUp(x,y,pointerId);
            }
        });
        return true;
    }
//    @Override
    public boolean doSimulateTouchUp(float x, float y, int pointerId) {
//        downTime = SystemClock.uptimeMillis();
        long eventTime = SystemClock.uptimeMillis();

        MotionEvent event = MotionEvent.obtain(
                downTime,
                eventTime,
                MotionEvent.ACTION_UP,
                x,
                y,
                0
        );

        try {
            instrumentation.sendPointerSync(event);
        } catch (Exception e) {
            Log.e("InstrumentationInjector", "Failed to send event: " + e.getMessage());
        } finally {
            event.recycle();
        }
        return true;
    }
}