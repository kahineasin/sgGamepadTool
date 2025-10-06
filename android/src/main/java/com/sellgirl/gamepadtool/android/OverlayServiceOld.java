package com.sellgirl.gamepadtool.android;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.os.Build;
import android.os.IBinder;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;

// OverlayService.java
public class OverlayServiceOld extends Service {
    private WindowManager windowManager;
    private View overlayView;
    private boolean isOverlayShowing = false;

    @Override
    public void onCreate() {
        super.onCreate();
        windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
        showOverlay();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (!isOverlayShowing) {
            showOverlay();
        }
        return START_STICKY;
    }

    private void showOverlay() {
        if (overlayView != null) {
            return; // 已经显示
        }

        // 创建悬浮窗布局参数
        WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                Build.VERSION.SDK_INT >= Build.VERSION_CODES.O ?
                        WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY :
                        WindowManager.LayoutParams.TYPE_PHONE,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE |
                        WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, // 注意：这里设置为不接收触摸
                PixelFormat.TRANSLUCENT
        );

        params.gravity = Gravity.TOP | Gravity.START;
        params.x = 0;
        params.y = 100;

        // 创建悬浮窗视图
        overlayView = createOverlayView();
        windowManager.addView(overlayView, params);
        isOverlayShowing = true;
    }

    private View createOverlayViewOld() {
        // 创建自定义视图来显示您的按钮
        FrameLayout layout = new FrameLayout(this) {
            @Override
            protected void onDraw(Canvas canvas) {
                super.onDraw(canvas);
                // 在这里绘制您的按钮
                drawButtons(canvas);
            }
        };

        // 设置为透明背景
        layout.setBackgroundColor(Color.TRANSPARENT);
        // 或者：强制View进行绘制（即使没有背景）
        layout.setWillNotDraw(false);  // 这行很重要！

        layout.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
        ));


        return layout;
    }
    private View createOverlayView() {
        ButtonOverlayView overlayView = new ButtonOverlayView(this);

        overlayView.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
        ));

        // 设置为可触摸（如果需要）
        overlayView.setClickable(false); // 根据需求设置

        return overlayView;
    }

    private void drawButtons(Canvas canvas) {
        // 从共享偏好设置中读取按钮位置和状态
        SharedPreferences prefs = getSharedPreferences("button_positions", MODE_PRIVATE);

        // 绘制每个按钮
        Paint buttonPaint = new Paint();
        buttonPaint.setColor(Color.argb(128, 0, 100, 255)); // 半透明蓝色
        buttonPaint.setStyle(Paint.Style.FILL_AND_STROKE);

        // 示例：绘制几个圆形按钮
        float button1X = prefs.getFloat("button1_x", 100);
        float button1Y = prefs.getFloat("button1_y", 100);
        float buttonRadius = 40;

        canvas.drawCircle(button1X, button1Y, buttonRadius, buttonPaint);

        // 绘制按钮标签
        Paint textPaint = new Paint();
        textPaint.setColor(Color.WHITE);
        textPaint.setTextSize(30);
        textPaint.setTextAlign(Paint.Align.CENTER);

        canvas.drawText("A", button1X, button1Y + 10, textPaint);

        // 可以继续绘制更多按钮...
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        removeOverlay();
    }

    private void removeOverlay() {
        if (overlayView != null) {
            windowManager.removeView(overlayView);
            overlayView = null;
            isOverlayShowing = false;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    // 更新按钮位置的方法
    public void updateButtonPosition(String buttonId, float x, float y) {
        SharedPreferences prefs = getSharedPreferences("button_positions", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putFloat(buttonId + "_x", x);
        editor.putFloat(buttonId + "_y", y);
        editor.apply();

        // 强制重绘
        if (overlayView != null) {
            overlayView.invalidate();
        }
    }
    // 修改 OverlayService 中的参数
    private void setupTouchableOverlay() {
        WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                Build.VERSION.SDK_INT >= Build.VERSION_CODES.O ?
                        WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY :
                        WindowManager.LayoutParams.TYPE_PHONE,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE |
                        WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH,
                PixelFormat.TRANSLUCENT
        );

        // 创建可触摸的视图
//        overlayView = new TouchableOverlayView(this);
        overlayView = createOverlayView();
        overlayView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // 处理触摸事件，判断是否点击了按钮
                handleTouchEvent(event);
                return true;
            }
        });
    }

    private void handleTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

//        // 检查是否点击了某个按钮区域
//        if (isPointInButton(x, y, "button1")) {
//            // 通过无障碍服务模拟点击目标游戏
//            simulateTapAtTarget(x, y);
//        }
    }
}