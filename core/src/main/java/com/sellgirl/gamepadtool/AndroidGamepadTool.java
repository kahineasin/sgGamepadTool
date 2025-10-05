package com.sellgirl.gamepadtool;

import com.sellgirl.gamepadtool.phone.ISGTouchSimulate;
import com.sellgirl.gamepadtool.screen.SimulateScreen;

public abstract class AndroidGamepadTool extends GamepadTool {
    public abstract void startPlayService();
    public abstract void pausePlayService();
    public abstract void stopPlayService();
    public abstract void updateApk(String url);
    public abstract ISGTouchSimulate getTouchSimulate();
//    private  void goToGamepadToKeyboardPage() {
//
////		game.setScreen(new com.mygdx.game.sasha.screen.KeySettingScreen(game, //manager,
////				sasha));
//
//        game.setScreen(new SimulateScreen(game));
//
//        dispose();
//
//    }
}
