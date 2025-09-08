package com.sellgirl.gamepadtool;

import com.sellgirl.gamepadtool.util.LocalSaveSettingHelper3;
import com.sellgirl.sgGameHelper.ISGGameConfig;
//import com.sellgirl.sgGameHelper.ScreenSetting;
import com.sellgirl.sgGameHelper.gamepad.ISGPS5Gamepad;
import com.sellgirl.sgGameHelper.gamepad.SGPS5GamepadSetting;

public class SGGameConfig implements ISGGameConfig {
    @Override
    public int getGdxControllerVersion() {
        return GameSetting.gdxControllerVersion;
    }

    private com.sellgirl.sgGameHelper.ScreenSetting screenSetting=null;
    @Override
    public com.sellgirl.sgGameHelper.ScreenSetting getScreenSetting() {
        if(null==screenSetting) {
            screenSetting=new com.sellgirl.sgGameHelper.ScreenSetting();
            screenSetting.setWORLD_WIDTH(com.sellgirl.gamepadtool.ScreenSetting.WORLD_WIDTH);
            screenSetting.setWORLD_HEIGHT(com.sellgirl.gamepadtool.ScreenSetting.WORLD_HEIGHT);
            screenSetting.setWORLD_HEIGHT_METER(com.sellgirl.gamepadtool.ScreenSetting.WORLD_HEIGHT_METER);
            screenSetting.setFPS(com.sellgirl.gamepadtool.ScreenSetting.FPS);
        }
        return screenSetting;
    }

    @Override
    public SGPS5GamepadSetting getGamepadSetting(ISGPS5Gamepad isgps5Gamepad) {
        return new LocalSaveSettingHelper3().readGameKey(isgps5Gamepad.getPadUniqueName(),new SGPS5GamepadSetting());
    }
}
