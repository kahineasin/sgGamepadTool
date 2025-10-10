package com.sellgirl.gamepadtool.android;

public interface GamepadCallback {
        @Deprecated
        void onButtonPressed(int buttonCode, int deviceId);
        void onButtonReleased(int buttonCode, int deviceId);
        @Deprecated
        void onAxisMoved(int axis, float value, int deviceId);
        //摇杆
        void handleJoystickTouch(//String joystickId,
                                 float x, float y, boolean isActive, int pointerId);
}