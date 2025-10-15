package com.sellgirl.gamepadtool.android;

public interface GamepadCallback {
//        @Deprecated
//        void onButtonPressed(int buttonCode, int deviceId);
//        void onButtonReleased(int buttonCode, int deviceId);
        void onButtonReleased(float x, float y, int deviceId);
//        @Deprecated
//        void onAxisMoved(int axis, float value, int deviceId);
        //摇杆
        void handleJoystickTouch(//String joystickId,
                                 float x0, float y0,
                                 float x, float y, boolean isActive, int pointerId);
        boolean simulateDrag(float x0, float y0,float x, float y);
        boolean simulate();
}