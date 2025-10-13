package com.sellgirl.gamepadtool;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.sellgirl.sgJavaHelper.config.SGDataHelper;

// 使用InputProcessor拦截手柄事件

/**
 * 测试情况：
 * 1. 正常情况下Gdx.input.setInputProcessor(MyInputProcessor)
 *     touch:DOWN->DRAGGED多次->UP
 * 2. TouchSimulationService中
 *     touch:Down->Cancelled  这显然是不正常的
 *     后来测试单次模拟，可以正常触发DOWN->DRAGGED->UP，
 *     发现问题在于下次手势模拟时会cancel上次的(这种方式应用于帧游戏是基本无解了)
 */
public class MyInputProcessor implements InputProcessor {
    private String TAG="MyInputProcessor";
    @Override
    public boolean keyDown(int keycode) {
        return true;
//        // 识别并按需拦截手柄按键
//        switch (keycode) {
//            case Input.Keys.BUTTON_B:
//                // 执行你的自定义操作，例如游戏中的跳跃
//                player.jump();
//                // 返回true消费此事件，阻止系统处理
//                return true;
//            case Input.Keys.BUTTON_A:
//                // 处理A键，执行攻击等
//                player.attack();
//                return true;
//            // 按需添加其他按键
//            default:
//                // 不处理的按键返回false
//                return false;
//        }
    }

    @Override
    public boolean keyUp(int keycode) {
        return true;
//        // 按键抬起处理，逻辑类似
//        switch (keycode) {
//            case Input.Keys.BUTTON_B:
//                // 例如，跳跃键抬起
//                return true;
//            // ... 其他按键处理
//            default:
//                return false;
//        }
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
//        SGDataHelper.getLog().print(SGDataHelper.FormatString("touchDown x:{0} y:{1} pointer:{2} button:{3}",screenX,screenY,pointer,button));
        print(SGDataHelper.FormatString("touchDown x:{0} y:{1} pointer:{2} button:{3}",screenX,screenY,pointer,button));
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
//        SGDataHelper.getLog().print(SGDataHelper.FormatString("touchUp x:{0} y:{1} pointer:{2} button:{3}",screenX,screenY,pointer,button));
        print(SGDataHelper.FormatString("touchUp x:{0} y:{1} pointer:{2} button:{3}",screenX,screenY,pointer,button));
        return false;
    }
    private void print(String s){
//        SGDataHelper.getLog().printException(new Exception(s),TAG);
        SGDataHelper.getLog().print(TAG+" "+s);
    }

    @Override
    public boolean touchCancelled(int screenX, int screenY, int pointer, int button) {
//        SGDataHelper.getLog().print(SGDataHelper.FormatString("touchCancelled x:{0} y:{1} pointer:{2} button:{3}",screenX,screenY,pointer,button));
        print(SGDataHelper.FormatString("touchCancelled x:{0} y:{1} pointer:{2} button:{3}",screenX,screenY,pointer,button));
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
//        SGDataHelper.getLog().print(SGDataHelper.FormatString("touchDragged x:{0} y:{1} pointer:{2}",screenX,screenY,pointer));
        print(SGDataHelper.FormatString("touchDragged x:{0} y:{1} pointer:{2}",screenX,screenY,pointer));
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }

    // ... 实现InputProcessor的其他必要方法，根据你的需求返回false或进行处理。
}
