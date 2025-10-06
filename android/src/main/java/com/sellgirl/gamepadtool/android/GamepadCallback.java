package com.sellgirl.gamepadtool.android;

public interface GamepadCallback {

        void onButtonPressed(int buttonCode, int deviceId);
        void onButtonReleased(int buttonCode, int deviceId);
        void onAxisMoved(int axis, float value, int deviceId);
}