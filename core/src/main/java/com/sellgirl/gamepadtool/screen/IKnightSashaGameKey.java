package com.sellgirl.gamepadtool.screen;

//import com.mygdx.game.share.gamepad.ISGPS5Gamepad;
import com.badlogic.gdx.math.Vector2;
import com.sellgirl.gamepadtool.model.KeySimulateItem;
import com.sellgirl.sgGameHelper.gamepad.ISGPS5Gamepad;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 手柄映射键盘
 */
public interface IKnightSashaGameKey {
    void init();
    void update();
    ISGPS5Gamepad getGamepad();

    void setGamepad(ISGPS5Gamepad gamepad);

    //-------------按键状态-----------
    boolean isJump();
    boolean isDodge();
    boolean isAttack();
    boolean isSkill();
//    boolean isKick();
    boolean isDefend();
    boolean isKick();

    String getKeyNamesByMask(int mask);

    //-------------properties-----------
    int getJump();

    void setJump(int jump);

    int getDodge();

    void setDodge(int dodge);

    int getAttack();

    void setAttack(int attack);

    int getSkill();

    void setSkill(int heaveAttack);

    int getDefend();

    void setDefend(int defend);

    int getL2();

    void setL2(int l2);

    int getKick();

    void setKick(int r1);

    int getR2();

    void setR2(int r2);

    int getUp();
    void setUp(int r2);
    int getDown();
    void setDown(int r2);
    int getLeft();
    void setLeft(int r2);
    int getRight();
    void setRight(int r2);

    int getL3();
    void setL3(int r2);
    int getR3();
    void setR3(int r2);
    int getBack();
    void setBack(int r2);
    int getStart();
    void setStart(int r2);

    int getStick1Up();
    void setStick1Up(int r2);
    int getStick1Down();
    void setStick1Down(int r2);
    int getStick1Left();
    void setStick1Left(int r2);
    int getStick1Right();
    void setStick1Right(int r2);

    int getStick2Up();
    void setStick2Up(int r2);
    int getStick2Down();
    void setStick2Down(int r2);
    int getStick2Left();
    void setStick2Left(int r2);
    int getStick2Right();
    void setStick2Right(int r2);

    //-------------Map转换-----------
    HashMap<String,Integer> toMap(HashMap<String, Integer> r);
    void applyMap(HashMap<String, Integer> map);
    @Deprecated
    void applyCombinedMap(Map<Integer, Integer> map);
    @Deprecated
    Map<Integer, Integer> getCombinedMap();

    void setCombinedMap2(List< KeySimulateItem> map);
    List< KeySimulateItem> getCombinedMap2();
     void setKeyMap(List<KeySimulateItem> map);
     List< KeySimulateItem> getKeyMap();

     void setMouseBezier(List<Vector2> mouseBezier);
     List<Vector2> getMouseBezier();
    /**
     * 判断按下了手柄按键
     * @param btn btnMask
     * @return
     */
    boolean isBtn(int btn);
}
