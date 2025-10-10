package com.sellgirl.gamepadtool.android;

import com.badlogic.gdx.controllers.android.AndroidController;

public class AndroidController2 extends AndroidController {
    public AndroidController2(int deviceId, String name) {
        super(deviceId, name);
    }
    public AndroidController2(AndroidController controller) {
        super(controller.getDeviceId(), controller.getName());
    }
    public int[] getAxesIds(){
        return axesIds;
    }
}
