//package com.sellgirl.gamepadtool.android;
//
//import android.annotation.SuppressLint;
//import android.annotation.TargetApi;
//import android.app.Service;
//import android.content.Context;
//import android.content.Intent;
//import android.hardware.input.InputManager;
//import android.os.Binder;
//import android.os.Build;
//import android.os.Handler;
//import android.os.IBinder;
//import android.os.Looper;
//import android.util.Log;
//import android.util.SparseArray;
//import android.util.SparseBooleanArray;
//import android.view.InputDevice;
//import android.view.KeyEvent;
//import android.view.MotionEvent;
//public class GamepadService extends Service {
//    private static final String TAG = "GamepadService";
//    private Handler handler;
//    private Runnable gamepadCheckRunnable;
//
//    // 存储设备状态
//    private SparseArray<GamepadState> gamepadStates = new SparseArray<>();
//    private InputManager inputManager;
//
////    public interface GamepadCallback {
////        void onButtonPressed(int buttonCode, int deviceId);
////        void onButtonReleased(int buttonCode, int deviceId);
////        void onAxisMoved(int axis, float value, int deviceId);
////    }
//
//    private GamepadCallback callback;
//
//    @Override
//    public void onCreate() {
//        super.onCreate();
//        Log.d(TAG, "GamepadService created");
//
//        handler = new Handler(Looper.getMainLooper());
//        inputManager = (InputManager) getSystemService(Context.INPUT_SERVICE);
//
//        setupInputManager();
//        startGamepadMonitoring();
//    }
//
//    @SuppressLint("NewApi")
//    private void setupInputManager() {
//        int[] deviceIds = inputManager.getInputDeviceIds();
//        for (int deviceId : deviceIds) {
//            InputDevice device = inputManager.getInputDevice(deviceId);
//            if (isGamepad(device)) {
//                gamepadStates.put(deviceId, new GamepadState(device));
//                Log.d(TAG, "Found gamepad: " + device.getName() + " (ID: " + deviceId + ")");
//            }
//        }
//
//        InputManager.InputDeviceListener deviceListener = new InputManager.InputDeviceListener() {
//            @Override
//            public void onInputDeviceAdded(int deviceId) {
//                InputDevice device = inputManager.getInputDevice(deviceId);
//                if (isGamepad(device)) {
//                    gamepadStates.put(deviceId, new GamepadState(device));
//                    Log.d(TAG, "Gamepad connected: " + device.getName());
//                }
//            }
//
//            @Override
//            public void onInputDeviceRemoved(int deviceId) {
//                gamepadStates.remove(deviceId);
//                Log.d(TAG, "Gamepad disconnected: " + deviceId);
//            }
//
//            @Override
//            public void onInputDeviceChanged(int deviceId) {
//                InputDevice device = inputManager.getInputDevice(deviceId);
//                if (isGamepad(device)) {
//                    GamepadState state = gamepadStates.get(deviceId);
//                    if (state != null) {
//                        state.updateDevice(device);
//                    } else {
//                        gamepadStates.put(deviceId, new GamepadState(device));
//                    }
//                    Log.d(TAG, "Gamepad changed: " + device.getName());
//                }
//            }
//        };
//
//        inputManager.registerInputDeviceListener(deviceListener, handler);
//    }
//
//    private void startGamepadMonitoring() {
//        gamepadCheckRunnable = new Runnable() {
//            @Override
//            public void run() {
//                checkAllGamepads();
//                handler.postDelayed(this, 16); // ~60fps
//            }
//        };
//        handler.post(gamepadCheckRunnable);
//    }
//
//    private void checkAllGamepads() {
//        for (int i = 0; i < gamepadStates.size(); i++) {
//            int deviceId = gamepadStates.keyAt(i);
//            GamepadState state = gamepadStates.valueAt(i);
//            checkGamepadInput(state, deviceId);
//        }
//    }
//
//    private void checkGamepadInput(GamepadState state, int deviceId) {
//        if (callback == null || state == null) return;
//
//        checkButtons(state, deviceId);
//        checkAxes(state, deviceId);
//    }
//
//    private void checkButtons(GamepadState state, int deviceId) {
//        int[] buttonsToCheck = {
//                KeyEvent.KEYCODE_BUTTON_A, KeyEvent.KEYCODE_BUTTON_B,
//                KeyEvent.KEYCODE_BUTTON_X, KeyEvent.KEYCODE_BUTTON_Y,
//                KeyEvent.KEYCODE_BUTTON_L1, KeyEvent.KEYCODE_BUTTON_R1,
//                KeyEvent.KEYCODE_BUTTON_L2, KeyEvent.KEYCODE_BUTTON_R2,
//                KeyEvent.KEYCODE_BUTTON_THUMBL, KeyEvent.KEYCODE_BUTTON_THUMBR,
//                KeyEvent.KEYCODE_BUTTON_START, KeyEvent.KEYCODE_BUTTON_SELECT,
//                KeyEvent.KEYCODE_BUTTON_MODE
//        };
//
//        for (int buttonCode : buttonsToCheck) {
//            // 使用 InputDevice.getKeyCodeState 检查按键状态
////            boolean currentState = state.device != null && state.device.getKeyCodeState(buttonCode) != 0;
//            boolean currentState=true;//todo
//            boolean previousState = state.getPreviousButtonState(buttonCode);
//
//            if (currentState && !previousState) {
//                if (callback != null) {
//                    callback.onButtonPressed(buttonCode, deviceId);
//                    Log.d(TAG, "Button pressed: " + buttonCode);
//                }
//            } else if (!currentState && previousState) {
//                if (callback != null) {
//                    callback.onButtonReleased(buttonCode, deviceId);
//                    Log.d(TAG, "Button released: " + buttonCode);
//                }
//            }
//            state.setButtonState(buttonCode, currentState);
//        }
//    }
//
//    private void checkAxes(GamepadState state, int deviceId) {
//        // 由于在服务中直接获取实时轴数据比较复杂
//        // 这里提供一个框架，实际使用时可能需要其他方法
//        float[] axisValues = state.getCurrentAxisValues();
//
//        // 主要摇杆和扳机轴
//        int[] axesToCheck = {
//                MotionEvent.AXIS_X, MotionEvent.AXIS_Y,
//                MotionEvent.AXIS_Z, MotionEvent.AXIS_RZ,
//                MotionEvent.AXIS_LTRIGGER, MotionEvent.AXIS_RTRIGGER
//        };
//
//        for (int i = 0; i < axesToCheck.length; i++) {
//            int axis = axesToCheck[i];
//            float currentValue = axisValues[i];
//            float previousValue = state.getPreviousAxisValue(axis);
//            float delta = Math.abs(currentValue - previousValue);
//
//            if (delta > 0.1f && callback != null) {
//                callback.onAxisMoved(axis, currentValue, deviceId);
//            }
//
//            state.setAxisValue(axis, currentValue);
//        }
//    }
//
//    private boolean isGamepad(InputDevice device) {
//        if (device == null) return false;
//
//        int sources = device.getSources();
//        return (sources & InputDevice.SOURCE_GAMEPAD) == InputDevice.SOURCE_GAMEPAD ||
//                (sources & InputDevice.SOURCE_JOYSTICK) == InputDevice.SOURCE_JOYSTICK ||
//                (sources & InputDevice.SOURCE_DPAD) == InputDevice.SOURCE_DPAD;
//    }
//
//    public void setCallback(GamepadCallback callback) {
//        this.callback = callback;
//    }
//
//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//        Log.d(TAG, "GamepadService destroyed");
//
//        if (handler != null && gamepadCheckRunnable != null) {
//            handler.removeCallbacks(gamepadCheckRunnable);
//        }
//    }
//
//    @Override
//    public IBinder onBind(Intent intent) {
//        return new GamepadBinder();
//    }
//
//    public class GamepadBinder extends Binder {
//        public GamepadService getService() {
//            return GamepadService.this;
//        }
//    }
//
//    /**
//     * 修复后的 GamepadState 类
//     */
//    private static class GamepadState {
//        private InputDevice device;
//
//        // 修复：使用 SparseBooleanArray 替代不存在的 SparseFloatArray
//        private SparseBooleanArray buttonStates = new SparseBooleanArray();
//        private SparseBooleanArray previousButtonStates = new SparseBooleanArray();
//
//        // 用于存储轴数据的替代方案
//        private SparseArray<Float> axisStates = new SparseArray<>();
//        private SparseArray<Float> previousAxisStates = new SparseArray<>();
//
//        public GamepadState(InputDevice device) {
//            this.device = device;
//            // 初始化轴状态
//            int[] axes = {MotionEvent.AXIS_X, MotionEvent.AXIS_Y, MotionEvent.AXIS_Z,
//                    MotionEvent.AXIS_RZ, MotionEvent.AXIS_LTRIGGER, MotionEvent.AXIS_RTRIGGER};
//            for (int axis : axes) {
//                axisStates.put(axis, 0f);
//                previousAxisStates.put(axis, 0f);
//            }
//        }
//
//        public void updateDevice(InputDevice device) {
//            this.device = device;
//        }
//
//        public boolean getPreviousButtonState(int buttonCode) {
//            return previousButtonStates.get(buttonCode, false);
//        }
//
//        public float getPreviousAxisValue(int axis) {
//            Float value = previousAxisStates.get(axis);
//            return value != null ? value : 0f;
//        }
//
//        public void setButtonState(int buttonCode, boolean pressed) {
//            previousButtonStates.put(buttonCode, buttonStates.get(buttonCode, false));
//            buttonStates.put(buttonCode, pressed);
//        }
//
//        public void setAxisValue(int axis, float value) {
//            Float prev = axisStates.get(axis);
//            previousAxisStates.put(axis, prev != null ? prev : 0f);
//            axisStates.put(axis, value);
//        }
//
//        public float[] getCurrentAxisValues() {
//            // 这里返回当前轴值的占位实现
//            // 实际使用时需要通过 MotionEvent 获取实时数据
//            return new float[]{0f, 0f, 0f, 0f, 0f, 0f};
//        }
//    }
//}