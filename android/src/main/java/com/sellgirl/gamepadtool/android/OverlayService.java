package com.sellgirl.gamepadtool.android;

import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;

import java.util.HashMap;
import java.util.Map;

public class OverlayService extends Service implements GamepadCallback {

    private WindowManager windowManager;
    private View overlayView;
    private boolean isOverlayShowing = false;

    private FocusOverlayView focusOverlayView;
//    private WindowManager windowManager;

    // 按钮映射配置
    private Map<Integer, ButtonMapping> buttonMappings = new HashMap<>();


    private GamepadService gamepadService;
    private boolean isBound = false;
    @Override
    public void onCreate() {
        super.onCreate();
        setupButtonMappings();
        createInputOverlay();
        showOverlay();

        bindGamepadService();
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
//    /**
//     * 设置按钮映射 - 这里可以配置哪些按钮需要拦截和映射
//     */
//    private void setupButtonMappings() {
//        // B键映射 - 拦截系统返回行为
//        buttonMappings.put(KeyEvent.KEYCODE_BUTTON_B, new ButtonMapping(
//                "B", "attack",  // 映射为攻击
//                true  // 需要拦截系统行为
//        ));
//
//        // A键映射
//        buttonMappings.put(KeyEvent.KEYCODE_BUTTON_A, new ButtonMapping(
//                "A", "jump",
//                true
//        ));
//
//        // X键映射
//        buttonMappings.put(KeyEvent.KEYCODE_BUTTON_X, new ButtonMapping(
//                "X", "reload",
//                true
//        ));
//
//        // Y键映射
//        buttonMappings.put(KeyEvent.KEYCODE_BUTTON_Y, new ButtonMapping(
//                "Y", "switch_weapon",
//                true
//        ));
//
//        // 肩键映射
//        buttonMappings.put(KeyEvent.KEYCODE_BUTTON_L1, new ButtonMapping(
//                "L1", "aim",
//                true
//        ));
//
//        buttonMappings.put(KeyEvent.KEYCODE_BUTTON_R1, new ButtonMapping(
//                "R1", "shoot",
//                true
//        ));
//    }

    private void createInputOverlay() {
        windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);

        WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                1, 1, // 最小尺寸，几乎不可见
                Build.VERSION.SDK_INT >= Build.VERSION_CODES.O ?
                        WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY :
                        WindowManager.LayoutParams.TYPE_PHONE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL |    // 不拦截触摸
                        WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH | // 接收外部触摸
                        WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,       // 可获取焦点
                PixelFormat.TRANSLUCENT
        );

        // 放置在屏幕外
        params.gravity = Gravity.START | Gravity.TOP;
        params.x = -1000;
        params.y = -1000;

        focusOverlayView = new FocusOverlayView(this);
        focusOverlayView.setCallback(this);

        windowManager.addView(focusOverlayView, params);
    }

//    @Override
//    public void onButtonPressed(int buttonCode, int deviceId) {
//        Log.d("OverlayService", "Button pressed: " + buttonCode);
//
//        ButtonMapping mapping = buttonMappings.get(buttonCode);
//        if (mapping != null && mapping.shouldIntercept) {
//            // 执行映射的动作
//            performMappedAction(mapping.action, deviceId);
//
//            // 记录拦截日志
//            Log.i("InputInterceptor", "Intercepted button: " + mapping.buttonName +
//                    ", mapped to: " + mapping.action);
//        } else {
//            Log.d("OverlayService", "No mapping for button: " + buttonCode);
//        }
//    }

//    @Override
//    public void onButtonReleased(int buttonCode, int deviceId) {
//        Log.d("OverlayService", "Button released: " + buttonCode);
//
//        ButtonMapping mapping = buttonMappings.get(buttonCode);
//        if (mapping != null && mapping.shouldIntercept) {
//            // 处理按钮释放逻辑
//            performMappedActionRelease(mapping.action, deviceId);
//        }
//    }

//    /**
//     * 执行映射的动作
//     */
//    private void performMappedAction(String action, int deviceId) {
//        switch (action) {
//            case "jump":
//                simulateTouchAtMappedPosition("jump_button");
//                break;
//            case "attack":
//                simulateTouchAtMappedPosition("attack_button");
//                break;
//            case "reload":
//                simulateTouchAtMappedPosition("reload_button");
//                break;
//            case "switch_weapon":
//                simulateTouchAtMappedPosition("switch_button");
//                break;
//            case "aim":
//                // 处理持续按下的动作，如瞄准
//                startAiming();
//                break;
//            case "shoot":
//                simulateTouchAtMappedPosition("shoot_button");
//                break;
//        }
//    }

    private void performMappedActionRelease(String action, int deviceId) {
        switch (action) {
            case "aim":
                // 停止瞄准
                stopAiming();
                break;
            // 其他需要处理释放的动作
        }
    }

//    private void simulateTouchAtMappedPosition(String buttonId) {
//        // 从配置中获取按钮位置
//        SharedPreferences prefs = getSharedPreferences("button_positions", MODE_PRIVATE);
//        float x = prefs.getFloat(buttonId + "_x", 0);
//        float y = prefs.getFloat(buttonId + "_y", 0);
//
//        if (x > 0 && y > 0) {
////            // 使用无障碍服务模拟点击
////            if (MyAccessibilityService.getInstance() != null) {
////                MyAccessibilityService.getInstance().simulateTap(x, y);
////            }
//
//            if (TouchSimulationService.getInstance() != null) {
//                TouchSimulationService.getInstance().simulateTouchFromGdx(x, y);
//            }
//        }
//    }

//    private void startAiming() {
//        // 实现瞄准开始逻辑
//    }
//
//    private void stopAiming() {
//        // 实现瞄准结束逻辑
//    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        removeOverlay();
        if (focusOverlayView != null && windowManager != null) {
            windowManager.removeView(focusOverlayView);
        }

        if (isBound) {
            unbindService(serviceConnection);
            isBound = false;
        }
    }
    private void removeOverlay() {
        if (overlayView != null) {
            windowManager.removeView(overlayView);
            overlayView = null;
            isOverlayShowing = false;
        }
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
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    /**
     * 按钮映射配置类
     */
//    private static class ButtonMapping {
//        String buttonName;
//        String action;
//        boolean shouldIntercept;
//
//        ButtonMapping(String buttonName, String action, boolean shouldIntercept) {
//            this.buttonName = buttonName;
//            this.action = action;
//            this.shouldIntercept = shouldIntercept;
//        }
//    }

    /**
     * 按钮映射配置
     */
    private static class ButtonMapping {
        String buttonName;
        String action;

        ButtonMapping(String buttonName, String action) {
            this.buttonName = buttonName;
            this.action = action;
        }
    }
    //-------------------------------手柄事件-----------------------

    private void bindGamepadService() {
        Intent intent = new Intent(this, GamepadService.class);
        intent.setAction("com.yourpackage.GAMEPAD_SERVICE");
        bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
    }

    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.d("OverlayService", "GamepadService connected");
            GamepadService.GamepadBinder binder = (GamepadService.GamepadBinder) service;
            gamepadService = binder.getService();
            gamepadService.setCallback(OverlayService.this);
            isBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.d("OverlayService", "GamepadService disconnected");
            gamepadService = null;
            isBound = false;
        }
    };

    private void setupButtonMappings() {
        // 配置按钮映射
        buttonMappings.put(KeyEvent.KEYCODE_BUTTON_A, new ButtonMapping("A", "jump"));
        buttonMappings.put(KeyEvent.KEYCODE_BUTTON_B, new ButtonMapping("B", "attack"));
        buttonMappings.put(KeyEvent.KEYCODE_BUTTON_X, new ButtonMapping("X", "reload"));
        buttonMappings.put(KeyEvent.KEYCODE_BUTTON_Y, new ButtonMapping("Y", "switch_weapon"));
        buttonMappings.put(KeyEvent.KEYCODE_BUTTON_L1, new ButtonMapping("L1", "aim"));
        buttonMappings.put(KeyEvent.KEYCODE_BUTTON_R1, new ButtonMapping("R1", "shoot"));
    }

    @Override
    public void onButtonPressed(int buttonCode, int deviceId) {
        Log.d("OverlayService", "Button pressed: " + buttonCode + ", device: " + deviceId);

        ButtonMapping mapping = buttonMappings.get(buttonCode);
        if (mapping != null) {
            // 执行映射动作
            performMappedAction(mapping.action, true);

            // 记录日志
            Log.i("GamepadInput", "Button " + mapping.buttonName + " pressed, action: " + mapping.action);
        }
    }

    @Override
    public void onButtonReleased(int buttonCode, int deviceId) {
        Log.d("OverlayService", "Button released: " + buttonCode + ", device: " + deviceId);

        ButtonMapping mapping = buttonMappings.get(buttonCode);
        if (mapping != null) {
            performMappedAction(mapping.action, false);
        }
    }
//
    @Override
    public void onAxisMoved(int axis, float value, int deviceId) {
        // 处理摇杆移动
        Log.d("OverlayService", "Axis moved: " + axis + " = " + value + ", device: " + deviceId);

        // 这里可以根据摇杆输入实现更复杂的控制
        if (Math.abs(value) > 0.5f) {
            // 处理显著的摇杆移动
        }
    }

    private void performMappedAction(String action, boolean pressed) {
        switch (action) {
            case "jump":
                if (pressed) simulateTouchAtMappedPosition("jump_button");
                break;
            case "attack":
                if (pressed) simulateTouchAtMappedPosition("attack_button");
                break;
            case "reload":
                if (pressed) simulateTouchAtMappedPosition("reload_button");
                break;
            case "switch_weapon":
                if (pressed) simulateTouchAtMappedPosition("switch_button");
                break;
            case "aim":
                if (pressed) startAiming();
                else stopAiming();
                break;
            case "shoot":
                if (pressed) simulateTouchAtMappedPosition("shoot_button");
                break;
        }
    }

    private void simulateTouchAtMappedPosition(String buttonId) {
        // 从配置中获取按钮位置并模拟点击
        SharedPreferences prefs = getSharedPreferences("button_positions", MODE_PRIVATE);
        float x = prefs.getFloat(buttonId + "_x", 0);
        float y = prefs.getFloat(buttonId + "_y", 0);

        if (x > 0 && y > 0) {
            // 使用无障碍服务模拟点击
            if (TouchSimulationService.getInstance() != null) {
                TouchSimulationService.getInstance().simulateTouchFromGdx(x, y);
            } else {
                Log.w("OverlayService", "Accessibility service not available");
            }
        }
    }

    private void startAiming() {
        // 实现瞄准开始逻辑
    }

    private void stopAiming() {
        // 实现瞄准结束逻辑
    }

}