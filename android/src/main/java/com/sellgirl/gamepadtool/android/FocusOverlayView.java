package com.sellgirl.gamepadtool.android;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 问题在于,view可以监听到touch事件，但是监听不到手柄的key事件 todo
 */
public class FocusOverlayView extends View {
    private GamepadCallback callback;

    // 需要拦截的系统默认按键
    private final Set<Integer> interceptedKeys = new HashSet<>(Arrays.asList(
            KeyEvent.KEYCODE_BUTTON_B,      // B键（通常映射为返回）
            KeyEvent.KEYCODE_BUTTON_A,      // A键
            KeyEvent.KEYCODE_BUTTON_X,      // X键
            KeyEvent.KEYCODE_BUTTON_Y,      // Y键
            KeyEvent.KEYCODE_BUTTON_L1,     // 左肩键
            KeyEvent.KEYCODE_BUTTON_R1,     // 右肩键
            KeyEvent.KEYCODE_BUTTON_SELECT, // 选择键
            KeyEvent.KEYCODE_BUTTON_START,  // 开始键
            KeyEvent.KEYCODE_BUTTON_MODE    // 模式键
    ));

    public FocusOverlayView(Context context) {
        super(context);
        setFocusable(true);
        setFocusableInTouchMode(true);
        requestFocus();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        Log.d("FocusOverlay", "Key down: " + keyCode + ", source: " + event.getSource());

        // 检查是否是游戏手柄按钮
        if (isGamepadButton(keyCode)) {
            // 拦截这个事件，阻止系统默认行为
            if (callback != null) {
                callback.onButtonPressed(keyCode, event.getDeviceId());
            }

            // 返回 true 表示我们已经消费了这个事件，系统不会处理
            return true;
        }

        // 对于非游戏按钮，让系统处理
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        Log.d("FocusOverlay", "Key up: " + keyCode);

        if (isGamepadButton(keyCode)) {
            // 拦截释放事件
            if (callback != null) {
                callback.onButtonReleased(keyCode, event.getDeviceId());
            }
            return true;
        }

        return super.onKeyUp(keyCode, event);
    }

//    /**
//     * 关键：重写 dispatchKeyEvent 来更早地拦截按键
//     */
//    @Override
//    public boolean dispatchKeyEvent(KeyEvent event) {
//        int keyCode = event.getKeyCode();
//
//        // 只拦截游戏手柄按钮
//        if (isGamepadButton(keyCode)) {
//            if (event.getAction() == KeyEvent.ACTION_DOWN) {
//                onKeyDown(keyCode, event);
//            } else if (event.getAction() == KeyEvent.ACTION_UP) {
//                onKeyUp(keyCode, event);
//            }
//            return true; // 消费事件
//        }
//
//        return super.dispatchKeyEvent(event);
//    }

    // 在 FocusOverlayView 中集成拦截器
    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        int keyCode = event.getKeyCode();
        int action = event.getAction();

        // 使用拦截器检查是否应该拦截
        if (KeyInterceptor.getInstance().shouldIntercept(keyCode, action)) {
            // 拦截这个事件
            if (action == KeyEvent.ACTION_DOWN) {
                onKeyDown(keyCode, event);
            } else if (action == KeyEvent.ACTION_UP) {
                onKeyUp(keyCode, event);
            }
            return true; // 消费事件，阻止系统处理
        }

        return super.dispatchKeyEvent(event);
    }
    /**
     * 检查是否是游戏手柄按钮
     */
    private boolean isGamepadButton(int keyCode) {
        // 方法1：使用 Android 内置检查
        if (KeyEvent.isGamepadButton(keyCode)) {
            return true;
        }

        // 方法2：检查常见游戏按钮范围
        if (keyCode >= KeyEvent.KEYCODE_BUTTON_A && keyCode <= KeyEvent.KEYCODE_BUTTON_MODE) {
            return true;
        }

        // 方法3：检查我们自定义的拦截列表
        return interceptedKeys.contains(keyCode);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // 不处理触摸事件，返回 false 让事件传递到下层
        return false;
    }

    public void setCallback(GamepadCallback callback) {
        this.callback = callback;
    }

//    public interface GamepadCallback {
//        void onButtonPressed(int buttonCode, int deviceId);
//        void onButtonReleased(int buttonCode, int deviceId);
//    }
}