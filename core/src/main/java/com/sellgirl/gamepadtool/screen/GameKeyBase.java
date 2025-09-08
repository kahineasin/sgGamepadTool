package com.sellgirl.gamepadtool.screen;//package com.mygdx.game.demo.keyboardInput;
//
//import com.badlogic.gdx.Input;
//import com.mygdx.game.share.XBoxKey;
//import com.mygdx.game.share.gamepad.ISGPS5Gamepad;
//import com.sellgirl.sgJavaHelper.config.SGDataHelper;
//
//import java.util.HashMap;
//
///**
// * 游戏按钮, 用于按键设置映射
// * 此类是方案2, 根据游戏screen上的功能来定义影射
// * 暂时不允许设置L2 R2吧, 以后改也可以的
// * 存在的问题:
// * 1. 由于按键可以组合, 那么轴功能是否能用就很难判断了
// */
//public abstract class GameKeyBase implements IKnightSashaGameKey {
//
//
//    protected int l3;
//    protected int r3;
//    protected int back;
//    protected int start;
//
//
//    @Override
//    public int getL3() {
//        return l3;
//    }
//
//    @Override
//    public void setL3(int l3) {
//        this.l3 = l3;
//    }
//
//    @Override
//    public int getR3() {
//        return r3;
//    }
//
//    @Override
//    public void setR3(int r3) {
//        this.r3 = r3;
//    }
//
//    @Override
//    public int getBack() {
//        return back;
//    }
//
//    @Override
//    public void setBack(int back) {
//        this.back = back;
//    }
//
//    @Override
//    public int getStart() {
//        return start;
//    }
//
//    @Override
//    public void setStart(int start) {
//        this.start = start;
//    }
//}
