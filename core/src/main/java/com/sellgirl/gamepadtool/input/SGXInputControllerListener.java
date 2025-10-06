package com.sellgirl.gamepadtool.input;


import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.ControllerAdapter;

 public class SGXInputControllerListener extends ControllerAdapter {
    @Override
    public boolean buttonDown(Controller controller, int buttonIndex) {
        return true;
////		         if(XBoxKey.CROSS.ordinal()==buttonIndex) {
////			         Gdx.app.log(TAG, "jump: " +buttonIndex);
////		         }
//
//        if (buttonIndex == controller.getMapping().buttonA) {
////			         Gdx.app.log(TAG, "jump: " +buttonIndex);
//            // gotoGamePage();
//        }
//
//        return false;
    }

    @Override
    public boolean buttonUp(Controller controller, int buttonIndex) {
        return true;
//        return false;
    }

    @Override
    public void connected(Controller controller) {
    }

    @Override
    public void disconnected(Controller controller) {
    }
}
