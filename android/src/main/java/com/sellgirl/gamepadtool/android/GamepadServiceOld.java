package com.sellgirl.gamepadtool.android;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.hardware.input.InputManager;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.util.SparseArray;
import android.view.InputDevice;
import android.view.KeyEvent;
import android.view.MotionEvent;

// GamepadService.java - 在 Android 原生层监听手柄
public class GamepadServiceOld extends Service {
    private InputManager inputManager;
    private InputManager.InputDeviceListener deviceListener;
    private Handler handler;
    private Runnable gamepadCheckRunnable;

    // 手柄事件回调接口
    public interface GamepadCallback {
        void onButtonPressed(int buttonCode, int deviceId);
        void onButtonReleased(int buttonCode, int deviceId);
        void onAxisMoved(int axis, float value, int deviceId);
    }

    private GamepadCallback callback;

    @Override
    public void onCreate() {
        super.onCreate();
        handler = new Handler();
        setupInputManager();
        startGamepadMonitoring();
    }

    @SuppressLint("NewApi")
    private void setupInputManager() {
        inputManager = (InputManager) getSystemService(Context.INPUT_SERVICE);

        deviceListener = new InputManager.InputDeviceListener() {
            @Override
            public void onInputDeviceAdded(int deviceId) {
                Log.d("GamepadService", "Input device added: " + deviceId);
            }

            @Override
            public void onInputDeviceRemoved(int deviceId) {
                Log.d("GamepadService", "Input device removed: " + deviceId);
            }

            @Override
            public void onInputDeviceChanged(int deviceId) {
                Log.d("GamepadService", "Input device changed: " + deviceId);
            }
        };

        inputManager.registerInputDeviceListener(deviceListener, handler);
    }

    private void startGamepadMonitoring() {
        gamepadCheckRunnable = new Runnable() {
            @Override
            public void run() {
                checkGamepadInput();
                handler.postDelayed(this, 16); // ~60fps
            }
        };
        handler.post(gamepadCheckRunnable);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void checkGamepadInput() {
        int[] deviceIds = inputManager.getInputDeviceIds();

        for (int deviceId : deviceIds) {
            InputDevice device = inputManager.getInputDevice(deviceId);

            if (isGamepad(device)) {
                // 检查按钮状态
                checkButtonStates(device);
                // 检查摇杆状态
                checkAxisStates(device);
            }
        }
    }

    private boolean isGamepad(InputDevice device) {
        if (device == null) return false;

        int sources = device.getSources();
        return (sources & InputDevice.SOURCE_GAMEPAD) == InputDevice.SOURCE_GAMEPAD ||
                (sources & InputDevice.SOURCE_JOYSTICK) == InputDevice.SOURCE_JOYSTICK;
    }

    // 存储上一帧的按钮状态
    private SparseArray<Boolean> previousButtonStates = new SparseArray<>();

    private void checkButtonStates(InputDevice device) {
        if (callback == null) return;

        // 检查常见游戏按钮
        int[] buttonsToCheck = {
                KeyEvent.KEYCODE_BUTTON_A,      // A按钮
                KeyEvent.KEYCODE_BUTTON_B,      // B按钮
                KeyEvent.KEYCODE_BUTTON_X,      // X按钮
                KeyEvent.KEYCODE_BUTTON_Y,      // Y按钮
                KeyEvent.KEYCODE_BUTTON_L1,     // 左肩键
                KeyEvent.KEYCODE_BUTTON_R1,     // 右肩键
                KeyEvent.KEYCODE_BUTTON_THUMBL, // 左摇杆按下
                KeyEvent.KEYCODE_BUTTON_THUMBR  // 右摇杆按下
        };

        for (int buttonCode : buttonsToCheck) {
            boolean currentState = isButtonPressed(device, buttonCode);
            boolean previousState = getPreviousButtonState(device.getId(), buttonCode);

            if (currentState && !previousState) {
                // 按钮按下
                callback.onButtonPressed(buttonCode, device.getId());
            } else if (!currentState && previousState) {
                // 按钮释放
                callback.onButtonReleased(buttonCode, device.getId());
            }

            // 更新状态
            setPreviousButtonState(device.getId(), buttonCode, currentState);
        }
    }

    private boolean isButtonPressed(InputDevice device, int buttonCode) {
        // 这里简化处理，实际应该通过KeyEvent检查
        // 更精确的实现需要使用InputDevice.getKeyCodeState()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            return device.getKeyCodeForKeyLocation(buttonCode)!=0;
        }
        return false;

//        return device.getKeyCodeState(buttonCode) != 0;
    }

    private void checkAxisStates(InputDevice device) {
        if (callback == null) return;

        // 检查摇杆轴
        int[] axesToCheck = {
                MotionEvent.AXIS_X,        // 左摇杆X
                MotionEvent.AXIS_Y,        // 左摇杆Y
                MotionEvent.AXIS_Z,        // 右摇杆X
                MotionEvent.AXIS_RZ,       // 右摇杆Y
                MotionEvent.AXIS_LTRIGGER, // 左扳机
                MotionEvent.AXIS_RTRIGGER  // 右扳机
        };

        for (int axis : axesToCheck) {
            float value = getAxisValue(device, axis);
            if (Math.abs(value) > 0.1f) { // 忽略小数值
                callback.onAxisMoved(axis, value, device.getId());
            }
        }
    }

    private float getAxisValue(InputDevice device, int axis) {
        InputDevice.MotionRange range = device.getMotionRange(axis);
        if (range != null) {
            // 这里需要实际获取当前轴的值
            // 简化实现，实际应该通过MotionEvent获取
            return 0f; // 占位
        }
        return 0f;
    }

    private boolean getPreviousButtonState(int deviceId, int buttonCode) {
        int key = deviceId * 1000 + buttonCode;
        return previousButtonStates.get(key, false);
    }

    private void setPreviousButtonState(int deviceId, int buttonCode, boolean state) {
        int key = deviceId * 1000 + buttonCode;
        previousButtonStates.put(key, state);
    }

    public void setCallback(GamepadCallback callback) {
        this.callback = callback;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (handler != null && gamepadCheckRunnable != null) {
            handler.removeCallbacks(gamepadCheckRunnable);
        }
        if (inputManager != null && deviceListener != null) {
            inputManager.unregisterInputDeviceListener(deviceListener);
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}