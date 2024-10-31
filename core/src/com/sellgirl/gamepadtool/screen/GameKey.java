package com.sellgirl.gamepadtool.screen;

import com.badlogic.gdx.Input;
//import com.mygdx.game.share.gamepad.ISGPS5Gamepad;
import com.sellgirl.sgGameHelper.gamepad.ISGPS5Gamepad;
import com.sellgirl.sgJavaHelper.config.SGDataHelper;

import java.util.HashMap;

/**
 * 游戏按钮, 用于按键设置映射
 * 此类是方案2, 根据游戏screen上的功能来定义影射
 * 暂时不允许设置L2 R2吧, 以后改也可以的
 * 存在的问题:
 * 1. 由于按键可以组合, 那么轴功能是否能用就很难判断了
 */
public class GameKey implements IKnightSashaGameKey//extends GameKeyBase
{

    /**
     * 这些值都是binary模式
     */
    private int jump;
    private int dodge;
    private int attack;
    private int skill;
//    private int kick;
    private int defend;
    private int l2;
    private int kick;
    private int r2;

    private int up;
    private int down;
    private int left;
    private int right;

    protected int l3;
    protected int r3;
    protected int back;
    protected int start;


    protected int stick1Up;
    protected int stick1Down;
    protected int stick1Left;
    protected int stick1Right;
    protected int stick2Up;
    protected int stick2Down;
    protected int stick2Left;
    protected int stick2Right;

    private int gamepadMask;
    public GameKey(){
        init();
    }
    public void init(){
//        jump=XBoxKey.CROSS.getBinary();
//        dodge=XBoxKey.ROUND.getBinary();
//        attack=XBoxKey.SQUARE.getBinary();
//        skill =XBoxKey.TRIANGLE.getBinary();
//        defend=XBoxKey.L1.getBinary();
////        l2=XBoxKey.L2.getBinary();
//        kick =XBoxKey.R1.getBinary();
////        r2=XBoxKey.R2.getBinary();

        jump= Input.Keys.X;
        dodge= Input.Keys.C;
        attack= Input.Keys.Z;
        kick= Input.Keys.V;
        defend= Input.Keys.SHIFT_LEFT;
        skill= Input.Keys.S;
//        l2= Input.Keys.D;
//        r2= Input.Keys.F;
        up= Input.Keys.UP;
        down= Input.Keys.DOWN;
        left= Input.Keys.LEFT;
        right= Input.Keys.RIGHT;
//        l3= Input.Keys.Q;
//        r3= Input.Keys.W;
        //back=Input.Keys
        start=Input.Keys.ESCAPE;
    }

    public ISGPS5Gamepad getGamepad() {
        return gamepad;
    }

    public void setGamepad(ISGPS5Gamepad gamepad) {
        this.gamepad = gamepad;
    }

    private ISGPS5Gamepad gamepad=null;
    public void update(){
        gamepadMask=gamepad.getQuickBtnKey();
        //        //XBoxKey r=null;
//        int mask=0;
//
//        if(gamepad.isSQUARE()) {
//            //return XBoxKey.SQUARE;
////				mask=XBoxKey.config(mask, XBoxKey.SQUARE, true);
//            mask|= XBoxKey.SQUARE.getBinary();
//        }
//        if(gamepad.isTRIANGLE()) {
//            mask|=XBoxKey.TRIANGLE.getBinary();
//        }
//        if(gamepad.isCROSS()) {
////				return XBoxKey.CROSS;
//            //mask=XBoxKey.config(mask, XBoxKey.CROSS, true);
//            mask|=XBoxKey.CROSS.getBinary();
//        }
//        if(gamepad.isROUND()) {
////				return XBoxKey.ROUND;
//            //XBoxKey.config(mask, XBoxKey.ROUND, true);
//            mask|=XBoxKey.ROUND.getBinary();
//        }
//        if(gamepad.isL1()) {
////				return XBoxKey.L1;
//            //XBoxKey.config(mask, XBoxKey.L1, true);
//            mask|=XBoxKey.L1.getBinary();
//        }
//        if(gamepad.isR1()) {
////				return XBoxKey.R1;
//            //XBoxKey.config(mask, XBoxKey.R1, true);
//            mask|=XBoxKey.R1.getBinary();
//        }
////        if(gamepad.isL2()) {
////            mask|=XBoxKey.L2.getBinary();
////        }
////        if(gamepad.isR2()) {
////            mask|=XBoxKey.R2.getBinary();
////        }
////		}
////        return mask;
//        gamepadMask=mask;
    }
    public boolean isJump(){
        return SGDataHelper.EnumHasFlag(gamepadMask,jump);
    }
    public boolean isDodge(){
        return SGDataHelper.EnumHasFlag(gamepadMask,dodge);
    }
    public boolean isAttack(){
        return SGDataHelper.EnumHasFlag(gamepadMask,attack);
    }
    public boolean isSkill(){
        return SGDataHelper.EnumHasFlag(gamepadMask, skill);
    }

//    @Override
//    public boolean isKick() {
//        return SGDataHelper.EnumHasFlag(gamepadMask,heaveAttack);
//    }

    public boolean isDefend(){
        return SGDataHelper.EnumHasFlag(gamepadMask,defend);
    }
    public boolean isKick(){
        return SGDataHelper.EnumHasFlag(gamepadMask, kick);
    }
//    public float axisL2(ISGPS5Gamepad gamepad) {
//        return gamepad.axisL2();
//    }
//    public float axisR2(ISGPS5Gamepad gamepad) {
//        return gamepad.axisR2();
//    }

    //-------------properties-----------
    public int getJump() {
        return jump;
    }

    public void setJump(int jump) {
        this.jump = jump;
    }

    public int getDodge() {
        return dodge;
    }

    public void setDodge(int dodge) {
        this.dodge = dodge;
    }

    public int getAttack() {
        return attack;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    public int getSkill() {
        return skill;
    }

    public void setSkill(int heaveAttack) {
        this.skill = heaveAttack;
    }

    public int getDefend() {
        return defend;
    }

    public void setDefend(int defend) {
        this.defend = defend;
    }

    public int getL2() {
        return l2;
    }

    public void setL2(int l2) {
        this.l2 = l2;
    }

    public int getKick() {
        return kick;
    }

    public void setKick(int r1) {
        this.kick = r1;
    }

    public int getR2() {
        return r2;
    }

    public void setR2(int r2) {
        this.r2 = r2;
    }


    @Override
    public int getUp() {
        return up;
    }

    @Override
    public void setUp(int up) {
        this.up = up;
    }

    @Override
    public int getDown() {
        return down;
    }

    @Override
    public void setDown(int down) {
        this.down = down;
    }

    @Override
    public int getLeft() {
        return left;
    }

    @Override
    public void setLeft(int left) {
        this.left = left;
    }

    @Override
    public int getRight() {
        return right;
    }

    @Override
    public void setRight(int right) {
        this.right = right;
    }


    @Override
    public int getL3() {
        return l3;
    }

    @Override
    public void setL3(int l3) {
        this.l3 = l3;
    }

    @Override
    public int getR3() {
        return r3;
    }

    @Override
    public void setR3(int r3) {
        this.r3 = r3;
    }

    @Override
    public int getBack() {
        return back;
    }

    @Override
    public void setBack(int back) {
        this.back = back;
    }

    @Override
    public int getStart() {
        return start;
    }

    @Override
    public void setStart(int start) {
        this.start = start;
    }

    @Override
    public int getStick1Up() {
        return stick1Up;
    }

    public void setStick1Up(int stick1Up) {
        this.stick1Up = stick1Up;
    }

    @Override
    public int getStick1Down() {
        return stick1Down;
    }

    public void setStick1Down(int stick1Down) {
        this.stick1Down = stick1Down;
    }

    @Override
    public int getStick1Left() {
        return stick1Left;
    }

    public void setStick1Left(int stick1Left) {
        this.stick1Left = stick1Left;
    }

    @Override
    public int getStick1Right() {
        return stick1Right;
    }

    public void setStick1Right(int stick1Right) {
        this.stick1Right = stick1Right;
    }

    @Override
    public int getStick2Up() {
        return stick2Up;
    }

    public void setStick2Up(int stick2Up) {
        this.stick2Up = stick2Up;
    }

    @Override
    public int getStick2Down() {
        return stick2Down;
    }

    public void setStick2Down(int stick2Down) {
        this.stick2Down = stick2Down;
    }

    @Override
    public int getStick2Left() {
        return stick2Left;
    }

    public void setStick2Left(int stick2Left) {
        this.stick2Left = stick2Left;
    }

    @Override
    public int getStick2Right() {
        return stick2Right;
    }

    public void setStick2Right(int stick2Right) {
        this.stick2Right = stick2Right;
    }
    public HashMap<String,Integer> toMap(HashMap<String,Integer> r){

        return gameKeyToMap(this,r);

    }
    public static HashMap<String,Integer> gameKeyToMap(IKnightSashaGameKey gameKey, HashMap<String,Integer> r){
        //HashMap<String,Integer> r=new HashMap<>();
        r.put("a",gameKey.getJump());
        r.put("b",gameKey.getDodge());
        r.put("x",gameKey.getAttack());
        r.put("y",gameKey.getKick());
        r.put("l1",gameKey.getDefend());
        r.put("r1",gameKey.getSkill());
        r.put("l2",gameKey.getL2());
        r.put("r2",gameKey.getR2());
        r.put("up",gameKey.getUp());
        r.put("down",gameKey.getDown());
        r.put("left",gameKey.getLeft());
        r.put("right",gameKey.getRight());
        r.put("l3",gameKey.getL3());
        r.put("r3",gameKey.getR3());
        r.put("back",gameKey.getBack());
        r.put("start",gameKey.getStart());

        r.put("stick1Up",gameKey.getStick1Up());
        r.put("stick1Down",gameKey.getStick1Down());
        r.put("stick1Left",gameKey.getStick1Left());
        r.put("stick1Right",gameKey.getStick1Right());
        r.put("stick2Up",gameKey.getStick2Up());
        r.put("stick2Down",gameKey.getStick2Down());
        r.put("stick2Left",gameKey.getStick2Left());
        r.put("stick2Right",gameKey.getStick2Right());
        return r;

    }
    public void applyMap(HashMap<String,Integer> map){

        gameKeyApplyMap(this,map);
    }
    public static void gameKeyApplyMap(IKnightSashaGameKey gameKey,HashMap<String,Integer> map){
        //HashMap<String,Integer> r=new HashMap<>();
        gameKey.setJump(map.get("a"));
        gameKey.setDodge(map.get("b"));
        gameKey.setAttack(map.get("x"));
        gameKey.setSkill(map.get("r1"));
        gameKey.setDefend(map.get("l1"));
        gameKey.setKick(map.get("y"));
        gameKey.setL2(map.get("l2"));
        gameKey.setR2(map.get("r2"));
        gameKey.setUp(map.get("up"));
        gameKey.setDown(map.get("down"));
        gameKey.setLeft(map.get("left"));
        gameKey.setRight(map.get("right"));
        gameKey.setL3(map.get("l3"));
        gameKey.setR3(map.get("r3"));
        gameKey.setBack(map.get("back"));
        gameKey.setStart(map.get("start"));

        gameKey.setStick1Up(map.get("stick1Up"));
        gameKey.setStick1Down(map.get("stick1Down"));
        gameKey.setStick1Left(map.get("stick1Left"));
        gameKey.setStick1Right(map.get("stick1Right"));
        gameKey.setStick2Up(map.get("stick2Up"));
        gameKey.setStick2Down(map.get("stick2Down"));
        gameKey.setStick2Left(map.get("stick2Left"));
        gameKey.setStick2Right(map.get("stick2Right"));

    }
    public String getKeyNamesByMask(int mask){

//        return XBoxKey.getTexts(mask);
        if(255<mask){return "";}
        else if(Input.Keys.UNKNOWN==mask){return "";}
        return Input.Keys.toString(mask);
    }
}
