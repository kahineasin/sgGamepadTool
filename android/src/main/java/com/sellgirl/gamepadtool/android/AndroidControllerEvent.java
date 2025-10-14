package com.sellgirl.gamepadtool.android;

import com.badlogic.gdx.controllers.android.AndroidController;

/**
 * 参考 com.badlogic.gdx.controllers.android.AndroidControllerEvent，
 * 因为AndroidControllerEvent是私有
 */
public class AndroidControllerEvent// extends AndroidController
{
    public static final int BUTTON_DOWN = 0;
    public static final int BUTTON_UP = 1;
    public static final int AXIS = 2;
    public static final int CONNECTED = 4;
    public static final int DISCONNECTED = 5;

//    /** the controller the event belongs to **/
//    public AndroidController controller;
    /** the event type, see constants above **/
    public int type;
    /** the code for the even source, e.g. button keycode, axis index **/
    public int code;
    /** the axis value if this is an #AXIS event **/
    public float axisValue;

    /**y轴和x放同一个event里面，模拟时更方法，性能也更好**/
    public int codeY=0;
    public float axisValueY=0f;
//    public AndroidControllerEvent(int deviceId, String name) {
//        super(deviceId, name);
//    }
}