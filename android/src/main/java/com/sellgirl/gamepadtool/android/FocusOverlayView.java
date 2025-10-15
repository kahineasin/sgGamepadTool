package com.sellgirl.gamepadtool.android;
import android.content.Context;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Looper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.os.Build;
import android.os.IBinder;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.badlogic.gdx.controllers.android.AndroidController;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.IntFloatMap;
import com.badlogic.gdx.utils.IntIntMap;
import com.badlogic.gdx.utils.Pool;
import com.sellgirl.gamepadtool.android.model.ButtonInfo;
import com.sellgirl.sgGameHelper.gamepad.ISGPS5Gamepad;
import com.sellgirl.sgGameHelper.gamepad.SGPS5Gamepad;
import com.sellgirl.sgJavaHelper.ISGDisposable;
import com.sellgirl.sgJavaHelper.SGDate;
import com.sellgirl.sgJavaHelper.config.SGDataHelper;
import com.sellgirl.sgJavaHelper.time.Waiter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 问题在于,view可以监听到touch事件，但是监听不到手柄的key事件 todo
 */
public class FocusOverlayView extends View
implements View.OnTouchListener, View.OnKeyListener, View.OnGenericMotionListener
    , ISGDisposable
{
    private String TAG="FocusOverlayView";
    private static int instanceCnt=0;
    public int uuid=0;
    private Paint buttonPaint;
    private Paint textPaint;
    public List<ButtonInfo> buttons;
    private List<ButtonInfo> toolButtons;
    public List<ButtonInfo> sticks;
    private GamepadCallback callback;
    private OverlayService service=null;

    private Waiter waiter=new Waiter(1);
    private WindowManager windowManager;
    private WindowManager.LayoutParams layoutParams;

//    private float dragOffsetX=0, dragOffsetY=0;
    private float centerX=0, centerY=0;
    private float radius=40;

    // 用于稳定坐标计算的变量
    private float initialTouchX, initialTouchY;
    private float initialCenterX, initialCenterY;

    private  SGPS5Gamepad gamepad;
    private AndroidController controller;
//    protected final float[] axes;
    //下面字段是参考AndroidController
    //按钮状态
    protected final IntFloatMap axes;
    protected final IntFloatMap axesOld;
    protected final IntIntMap btns;//当前按钮的状态
    protected final int[] btnIds;
//    protected final int[] axesIds;
    //参考AndroidControllers
    public static boolean ignoreNoGamepadButtons = true;
//    private AndroidController2 controller;
    // 需要拦截的系统默认按键
    private final Set<Integer> interceptedKeys = new HashSet<>(Arrays.asList(
            KeyEvent.KEYCODE_BUTTON_B,      // B键（通常映射为返回）
            KeyEvent.KEYCODE_BUTTON_A,      // A键
            KeyEvent.KEYCODE_BUTTON_X,      // X键
            KeyEvent.KEYCODE_BUTTON_Y,      // Y键
            KeyEvent.KEYCODE_BUTTON_L1,     // 左肩键
            KeyEvent.KEYCODE_BUTTON_R1,     // 右肩键
            KeyEvent.KEYCODE_BUTTON_SELECT, // 选择键
            KeyEvent.KEYCODE_BUTTON_START,  // 开始键
            KeyEvent.KEYCODE_BUTTON_MODE    // 模式键
    ));

    //保持摇杆事件
    private Handler handler;
    //此值当前和TouchSimulationService的模拟频率一致
    private static final int JOYSTICK_UPDATE_INTERVAL =16;//200比较正常// 100有时断;//1000适合慢测试;//7;//16; // ~60fps
    private boolean running=false;
    private Runnable joystickUpdateRunnable=null;
    @Deprecated
    private StickMotion stickMotion0=null;
    @Deprecated
    private StickMotion stickMotion1=null;


    public class StickMotion{
        public float x=0;
        public float y=0;
        public boolean active=false;
        public int pointer=0;
    }

    private final Array<AndroidControllerEvent> eventQueue = new Array<AndroidControllerEvent>();
    private final Pool<AndroidControllerEvent> eventPool = new Pool<AndroidControllerEvent>() {
        @Override
        protected AndroidControllerEvent newObject () {
            return new AndroidControllerEvent();
        }
    };
    public FocusOverlayView(Context context,
                            //float x, float y,
                            WindowManager windowManager, WindowManager.LayoutParams params
    , ISGPS5Gamepad gamepad) {
        super(context);
//        setFocusable(true);
//        setFocusableInTouchMode(true);
//        requestFocus();
//        View v=this;
//        v.setOnKeyListener(this);
//        v.setOnTouchListener(this);
//        v.setFocusable(true);
//        v.setFocusableInTouchMode(true);
//        v.requestFocus();
//        v.setOnGenericMotionListener(this);

//        centerX=x;centerY=y;
        centerX=params.x;centerY=params.y;
        this.windowManager = windowManager;
        this.layoutParams = params;
        this.gamepad=(SGPS5Gamepad) gamepad;
        handler = new Handler(Looper.getMainLooper());
        this.controller= (AndroidController) ((SGPS5Gamepad)gamepad).getController();
//        this.controller=new AndroidController2 ((AndroidController) ((SGPS5Gamepad)gamepad).getController()) ;
        setupFocus();

        init();
        axes=new IntFloatMap();
        axesOld=new IntFloatMap();
        btns = new IntIntMap();
        btnIds=new int[]{
                this.gamepad.getCROSS(),this.gamepad.getROUND(),this.gamepad.getSQUARE(),this.gamepad.getTRIANGLE(),
                this.gamepad.getL1(),this.gamepad.getR1(),this.gamepad.getL2(),this.gamepad.getR2()
        };
//        axesIds=new int[]{this.gamepad.getX1(),};
        uuid=instanceCnt++;
        setupEventQueue();

//        axes = new float[axesIDList.size()];
    }

    private void setupFocus() {
        // 关键步骤1：设置为可获取焦点
        setFocusable(true);
        setFocusableInTouchMode(true); // 在触摸模式下也可聚焦

        // 关键步骤2：请求焦点
        requestFocus();

        // 关键步骤3：设置按键监听器
        setOnKeyListener(new OnKeyListener() {
////            @Override
//          public boolean onKeyOld(View v, int keyCode, KeyEvent event) {
//                if (isGamepadButton(keyCode)) {
//                    int action = event.getAction();
//
//                    if (action == KeyEvent.ACTION_DOWN && callback != null) {
//                        callback.onButtonPressed(keyCode, event.getDeviceId());
//                        return true; // 消费事件
//                    } else if (action == KeyEvent.ACTION_UP && callback != null) {
//                        callback.onButtonReleased(keyCode, event.getDeviceId());
//                        return true; // 消费事件
//                    }
//                }
//                return false;
//            }
            //参考自AndroidControllers
            @Override
            public boolean onKey (View view, int keyCode, KeyEvent keyEvent) {
                if (ignoreNoGamepadButtons && !KeyEvent.isGamepadButton(keyCode)) {
                    return false;
                }
//                AndroidController controller = controllerMap.get(keyEvent.getDeviceId());
                if(//controller != null
                        keyEvent.getDeviceId()==controller.getDeviceId()
                ) {
                    if(
//                            controller.getButton(keyCode)
                        btns.containsKey(keyCode)
                            && keyEvent.getAction() == KeyEvent.ACTION_DOWN) {
                        return true;
                    }
//                    //ignore any button trigger input if there is a trigger axis
//                    if (controller.hasTriggerAxis() && (keyCode == KeyEvent.KEYCODE_BUTTON_L2 || keyCode == KeyEvent.KEYCODE_BUTTON_R2)){
//                        return true;
//                    }
                    synchronized(eventQueue) {
                        //事件的方式太快,不适合无障碍类
//                        AndroidControllerEvent event = eventPool.obtain();
////                        event.controller = controller;
//                        if(keyEvent.getAction() == KeyEvent.ACTION_DOWN) {
//                            event.type = AndroidControllerEvent.BUTTON_DOWN;
//                        } else {
//                            event.type = AndroidControllerEvent.BUTTON_UP;
//                        }
//                        event.code = keyCode;
//                        eventQueue.add(event);

                        btns.put(keyCode,keyEvent.getAction() == KeyEvent.ACTION_DOWN?1:0);
                    }
                    return keyCode != KeyEvent.KEYCODE_BACK// || Gdx.input.isCatchBackKey()
                            ;
                } else {
                    return false;
                }
            }
        });

        setOnGenericMotionListener(new OnGenericMotionListener() {
////            @Override
//            public boolean onGenericMotionOld(View v, MotionEvent event) {
////                if((motionEvent.getSource() & InputDevice.SOURCE_CLASS_JOYSTICK) == 0) return false;
////                AndroidController controller = controllerMap.get(motionEvent.getDeviceId());
//                ISGPS5Gamepad controller=gamepad;
//                if(controller != null) {
////                    synchronized(eventQueue) {
//
//                int axisIndex = 0;
//                    MotionEvent motionEvent=event;
////                for (int axisId: controller.getAxesIds()) {
////                    MotionEvent motionEvent=event;
////                    float axisValue = motionEvent.getAxisValue(axisId);
////                    if(controller.getAxis(axisIndex) == axisValue) {
////                        axisIndex++;
////                        continue;
////                    }
//////                    AndroidControllerEvent event = eventPool.obtain();
//////                    event.type = AndroidControllerEvent.AXIS;
//////                    event.controller = controller;
//////                    event.code = axisIndex;
//////                    event.axisValue = axisValue;
//////                    eventQueue.add(event);
////                    axisIndex++;
////                }
//                    float axisValueX = motionEvent.getAxisValue(gamepad.getX1());
//                    float inputX=gamepad.getAxisLeftSpace().filterX(axisValueX);
//                    float axisValueY = motionEvent.getAxisValue(gamepad.getY1());
//                    float inputY=gamepad.getAxisLeftSpace().filterY(axisValueY);
//                    boolean isActive=inputX!=0||inputY!=0;
////                    if(isActive){
//////                        service.handleJoystickTouch();
////                    }
//                    //摇杆半径，todo
////                    float screenX = sticks.get(0).x + inputX * joystick.outerRadius;
////                    float screenY = joystick.centerY + inputY * joystick.outerRadius;
//                    float radius=200;//100
//                    float screenX = sticks.get(0).x + inputX * radius;
//                    float screenY = sticks.get(0).y + inputY * radius;
////                    callback.handleJoystickTouch(screenX,screenY,isActive,gamepad.getX1());
////                    if(null==stickMotion0){stickMotion0=new StickMotion();}
////                    stickMotion0.x=screenX;
////                    stickMotion0.y=screenY;
////                    stickMotion0.active=isActive;
////                    stickMotion0.pointer=gamepad.getX1();
////                    if(isActive&&!running){
////                        startContinuousUpdates();
////                    }
//                    if(null==stickMotion0&&!isActive){
//                    }else{
//                        if(null==stickMotion0){stickMotion0=new StickMotion();}
//                        stickMotion0.x=screenX;
//                        stickMotion0.y=screenY;
//                        stickMotion0.active=isActive;
//                        stickMotion0.pointer=gamepad.getX1();
//                        if(isActive&&!running){
//                            startContinuousUpdates();
//                        }
//                    }
//
//                    //右摇杆
//                     axisValueX = motionEvent.getAxisValue(gamepad.getX2());
//                     inputX=gamepad.getAxisRightSpace().filterX(axisValueX);
//                     axisValueY = motionEvent.getAxisValue(gamepad.getY2());
//                     inputY=gamepad.getAxisRightSpace().filterY(axisValueY);
//                     isActive=inputX!=0||inputY!=0;
//                    // radius=200;//100
//                     screenX = sticks.get(1).x + inputX * radius;
//                     screenY = sticks.get(1).y + inputY * radius;
////                    callback.handleJoystickTouch(screenX,screenY,isActive,gamepad.getX1());
////                    if(null==stickMotion1){stickMotion1=new StickMotion();}
////                    stickMotion1.x=screenX;
////                    stickMotion1.y=screenY;
////                    stickMotion1.active=isActive;
////                    stickMotion1.pointer=gamepad.getX2();
////                    if(isActive&&!running){
////                        startContinuousUpdates();
////                    }
//
//                    if(null==stickMotion1&&!isActive){
//                    }else{
//                        if(null==stickMotion1){stickMotion1=new StickMotion();}
//                        stickMotion1.x=screenX;
//                        stickMotion1.y=screenY;
//                        stickMotion1.active=isActive;
//                        stickMotion1.pointer=gamepad.getX2();
//                        if(isActive&&!running){
//                            startContinuousUpdates();
//                        }
//                    }
////                    }
//                    return true;
//                }
//                return false;
//            }

            //参考自AndroidControllers
            @Override
            public boolean onGenericMotion (View view, MotionEvent motionEvent) {
//                if((motionEvent.getSource() & InputDevice.SOURCE_CLASS_JOYSTICK) == 0) return false;
//                AndroidController controller = controllerMap.get(motionEvent.getDeviceId());
                if(//controller != null
                        motionEvent.getDeviceId()==controller.getDeviceId()
                ) {
                    synchronized(eventQueue) {
//                        if (controller.hasPovAxis()) {
//                            float povX = motionEvent.getAxisValue(MotionEvent.AXIS_HAT_X);
//                            float povY = motionEvent.getAxisValue(MotionEvent.AXIS_HAT_Y);
//                            // map axis movement to dpad buttons
//                            if (povX != controller.povX) {
//                                if (controller.povX == 1f) {
//                                    AndroidControllerEvent event = eventPool.obtain();
//                                    event.controller = controller;
//                                    event.type = AndroidControllerEvent.BUTTON_UP;
//                                    event.code = KeyEvent.KEYCODE_DPAD_RIGHT;
//                                    eventQueue.add(event);
//                                } else if (controller.povX == -1f) {
//                                    AndroidControllerEvent event = eventPool.obtain();
//                                    event.controller = controller;
//                                    event.type = AndroidControllerEvent.BUTTON_UP;
//                                    event.code = KeyEvent.KEYCODE_DPAD_LEFT;
//                                    eventQueue.add(event);
//                                }
//
//                                if (povX == 1f) {
//                                    AndroidControllerEvent event = eventPool.obtain();
//                                    event.controller = controller;
//                                    event.type = AndroidControllerEvent.BUTTON_DOWN;
//                                    event.code = KeyEvent.KEYCODE_DPAD_RIGHT;
//                                    eventQueue.add(event);
//                                } else if (povX == -1f) {
//                                    AndroidControllerEvent event = eventPool.obtain();
//                                    event.controller = controller;
//                                    event.type = AndroidControllerEvent.BUTTON_DOWN;
//                                    event.code = KeyEvent.KEYCODE_DPAD_LEFT;
//                                    eventQueue.add(event);
//                                }
//                                controller.povX = povX;
//                            }
//
//                            if (povY != controller.povY) {
//                                if (controller.povY == 1f) {
//                                    AndroidControllerEvent event = eventPool.obtain();
//                                    event.controller = controller;
//                                    event.type = AndroidControllerEvent.BUTTON_UP;
//                                    event.code = KeyEvent.KEYCODE_DPAD_DOWN;
//                                    eventQueue.add(event);
//                                } else if (controller.povY == -1f) {
//                                    AndroidControllerEvent event = eventPool.obtain();
//                                    event.controller = controller;
//                                    event.type = AndroidControllerEvent.BUTTON_UP;
//                                    event.code = KeyEvent.KEYCODE_DPAD_UP;
//                                    eventQueue.add(event);
//                                }
//
//                                if (povY == 1f) {
//                                    AndroidControllerEvent event = eventPool.obtain();
//                                    event.controller = controller;
//                                    event.type = AndroidControllerEvent.BUTTON_DOWN;
//                                    event.code = KeyEvent.KEYCODE_DPAD_DOWN;
//                                    eventQueue.add(event);
//                                } else if (povY == -1f) {
//                                    AndroidControllerEvent event = eventPool.obtain();
//                                    event.controller = controller;
//                                    event.type = AndroidControllerEvent.BUTTON_DOWN;
//                                    event.code = KeyEvent.KEYCODE_DPAD_UP;
//                                    eventQueue.add(event);
//                                }
//                                controller.povY = povY;
//
//                            }
//                        }
//
//                        if (controller.hasTriggerAxis()){
//                            float lTrigger = motionEvent.getAxisValue(MotionEvent.AXIS_LTRIGGER);
//                            float rTrigger = motionEvent.getAxisValue(MotionEvent.AXIS_RTRIGGER);
//                            //map axis movement to trigger buttons
//                            if (lTrigger != controller.lTrigger){
//                                if (lTrigger == 1){
//                                    AndroidControllerEvent event = eventPool.obtain();
//                                    event.controller = controller;
//                                    event.type = AndroidControllerEvent.BUTTON_DOWN;
//                                    event.code = KeyEvent.KEYCODE_BUTTON_L2;
//                                    eventQueue.add(event);
//                                } else if (lTrigger == 0){
//                                    AndroidControllerEvent event = eventPool.obtain();
//                                    event.controller = controller;
//                                    event.type = AndroidControllerEvent.BUTTON_UP;
//                                    event.code = KeyEvent.KEYCODE_BUTTON_L2;
//                                    eventQueue.add(event);
//                                }
//                                controller.lTrigger = lTrigger;
//
//                            }
//
//                            if (rTrigger != controller.rTrigger){
//                                if (rTrigger == 1){
//                                    AndroidControllerEvent event = eventPool.obtain();
//                                    event.controller = controller;
//                                    event.type = AndroidControllerEvent.BUTTON_DOWN;
//                                    event.code = KeyEvent.KEYCODE_BUTTON_R2;
//                                    eventQueue.add(event);
//                                } else if (rTrigger == 0){
//                                    AndroidControllerEvent event = eventPool.obtain();
//                                    event.controller = controller;
//                                    event.type = AndroidControllerEvent.BUTTON_UP;
//                                    event.code = KeyEvent.KEYCODE_BUTTON_R2;
//                                    eventQueue.add(event);
//                                }
//                                controller.rTrigger = rTrigger;
//
//                            }
//                        }

//                        int axisIndex = 0;
//                        for (int axisId: controller.axesIds) {
//                            float axisValue = motionEvent.getAxisValue(axisId);
//                            if(controller.getAxis(axisIndex) == axisValue) {
//                                axisIndex++;
//                                continue;
//                            }
//                            AndroidControllerEvent event = eventPool.obtain();
//                            event.type = AndroidControllerEvent.AXIS;
//                            event.controller = controller;
//                            event.code = axisIndex;
//                            event.axisValue = axisValue;
//                            eventQueue.add(event);
//                            axisIndex++;
//                        }

                        float axisValueX = motionEvent.getAxisValue(gamepad.getX1());
                        float inputX=gamepad.getAxisLeftSpace().filterX(axisValueX);
                        float axisValueY = motionEvent.getAxisValue(gamepad.getY1());
                        float inputY=gamepad.getAxisLeftSpace().filterY(axisValueY);
                        axes.put(gamepad.getX1(),inputX);
                        axes.put(gamepad.getY1(),inputY);
//                        if(axes.get(gamepad.getX1(),0)!=inputX||
//                                axes.get(gamepad.getY1(),0)!=inputY) {
////                            boolean isActive = inputX != 0 || inputY != 0;
////
////                            //摇杆半径，todo
////                            float radius = 200;//100
////                            float screenX = sticks.get(0).x + inputX * radius;
////                            float screenY = sticks.get(0).y + inputY * radius;
////
////                            if (null == stickMotion0 && !isActive) {
////                            } else {
////                                if (null == stickMotion0) {
////                                    stickMotion0 = new StickMotion();
////                                }
////                                stickMotion0.x = screenX;
////                                stickMotion0.y = screenY;
////                                stickMotion0.active = isActive;
////                                stickMotion0.pointer = gamepad.getX1();
////                                if (isActive && !running) {
////                                    startContinuousUpdates();
////                                }
////                            }
//
//                            AndroidControllerEvent event = eventPool.obtain();
//                            event.type = AndroidControllerEvent.AXIS;
////                            event.controller = controller;
//                            event.code = gamepad.getX1();
//                            event.axisValue = inputX;
//                            event.codeY = gamepad.getY1();
//                            event.axisValueY = inputY;
//                            eventQueue.add(event);
////                            axisIndex++;
//                        }

                        //右摇杆
//                        axisValueX = motionEvent.getAxisValue(gamepad.getX2());
                        axisValueX = motionEvent.getAxisValue(MotionEvent.AXIS_Z);//右摇杆的轴是被gdx转换过的
                         inputX=gamepad.getAxisRightSpace().filterX(axisValueX);
//                         axisValueY = motionEvent.getAxisValue(gamepad.getY2());
                        axisValueY = motionEvent.getAxisValue(MotionEvent.AXIS_RZ);
                         inputY=gamepad.getAxisRightSpace().filterY(axisValueY);
                        axes.put(gamepad.getX2(),inputX);
                        axes.put(gamepad.getY2(),inputY);
//                        if(axes.get(gamepad.getX2(),0)!=inputX||
//                                axes.get(gamepad.getY2(),0)!=inputY) {
//                            AndroidControllerEvent event = eventPool.obtain();
//                            event.type = AndroidControllerEvent.AXIS;
////                            event.controller = controller;
//                            event.code = gamepad.getX2();
//                            event.axisValue = inputX;
//                            event.codeY = gamepad.getY2();
//                            event.axisValueY = inputY;
//                            eventQueue.add(event);
////                            axisIndex++;
//                        }
                    }
                    return true;
                }
                return false;
            }
        });
    }


//    /**
//     * 开始持续更新循环
//     */
//    @Deprecated
//    private void startContinuousUpdates() {
//        running=true;
//        if (joystickUpdateRunnable != null) {
//            handler.removeCallbacks(joystickUpdateRunnable);
//        }
//
//        joystickUpdateRunnable = new Runnable() {
//            @Override
//            public void run() {
////                float axisValueX = motionEvent.getAxisValue(gamepad.getX1());
////                float inputX=gamepad.getAxisLeftSpace().filterX(axisValueX);
////                float axisValueY = motionEvent.getAxisValue(gamepad.getY1());
////                float inputY=gamepad.getAxisLeftSpace().filterY(axisValueY);
////                boolean isActive=inputX!=0||inputY!=0;
////                updateJoystickPositions();
////
////                // 如果任一摇杆仍然活跃，继续更新
////                if (isLeftActive || isRightActive) {
////                    handler.postDelayed(this, JOYSTICK_UPDATE_INTERVAL);
////                }
//                if(null!=stickMotion0){
//                    callback.handleJoystickTouch(stickMotion0.x,stickMotion0.y,stickMotion0.active,stickMotion0.pointer);
//                    if(!stickMotion0.active){stickMotion0=null;}
//                }
//                if(null!=stickMotion1){
//                    callback.handleJoystickTouch(stickMotion1.x,stickMotion1.y,stickMotion1.active,stickMotion1.pointer);
//                    if(!stickMotion1.active){stickMotion1=null;}
//                }
//                if(running){
//                    handler.postDelayed(this, JOYSTICK_UPDATE_INTERVAL);
//                }
//            }
//        };
//
//        handler.post(joystickUpdateRunnable);
////        handler.postDelayed(joystickUpdateRunnable,JOYSTICK_UPDATE_INTERVAL);
//    }
//    //参考自AndroidControllers.setupEventQueue()
//    private void setupEventQueueOld() {
//        running=true;
//        new Runnable() {
//            @SuppressWarnings("synthetic-access")
//            @Override
//            public void run () {
//                synchronized(eventQueue) {
//                    for(AndroidControllerEvent event: eventQueue) {
////                        boolean processed=false;
//                        switch(event.type) {
////                            case AndroidControllerEvent.CONNECTED:
////                                controllers.add(event.controller);
////                                for(ControllerListener listener: listeners) {
////                                    listener.connected(event.controller);
////                                }
////                                break;
////                            case AndroidControllerEvent.DISCONNECTED:
////                                controllers.removeValue(event.controller, true);
////                                for(ControllerListener listener: listeners) {
////                                    listener.disconnected(event.controller);
////                                }
////                                for(ControllerListener listener: event.controller.getListeners()) {
////                                    listener.disconnected(event.controller);
////                                }
////                                break;
//                            case AndroidControllerEvent.BUTTON_DOWN:
////                                event.controller.buttons.put(event.code, event.code);
////                                for(ControllerListener listener: listeners) {
////                                    if(listener.buttonDown(event.controller, event.code)) break;
////                                }
////                                for(ControllerListener listener: event.controller.getListeners()) {
////                                    if(listener.buttonDown(event.controller, event.code)) break;
////                                }
//                                btns.put(event.code, event.code);
//                                break;
//                            case AndroidControllerEvent.BUTTON_UP:
////                                event.controller.buttons.remove(event.code, 0);
////                                for(ControllerListener listener: listeners) {
////                                    if(listener.buttonUp(event.controller, event.code)) break;
////                                }
////                                for(ControllerListener listener: event.controller.getListeners()) {
////                                    if(listener.buttonUp(event.controller, event.code)) break;
////                                }
//
////                                callback.onButtonReleased(event.code, event.getDeviceId());
//                                //todo 改多按钮
//                                btns.remove(event.code, 0);
//                                callback.onButtonReleased(event.code, 0);
//                                break;
//                            case AndroidControllerEvent.AXIS:
////                                event.controller.axes[event.code] = event.axisValue;
////                                for(ControllerListener listener: listeners) {
////                                    if(listener.axisMoved(event.controller, event.code, event.axisValue)) break;
////                                }
////                                for(ControllerListener listener: event.controller.getListeners()) {
////                                    if(listener.axisMoved(event.controller, event.code, event.axisValue)) break;
////                                }
//                                axes.put(event.code,event.axisValue);
//                                axes.put(event.codeY,event.axisValueY);
//                                boolean isActive=event.axisValue!=0||event.axisValueY!=0;
//                                float radius=200;//100
//                                int stickIdx=event.code==gamepad.getX1()?0:1;
//                                float screenX = sticks.get(stickIdx).x + event.axisValue * radius;
//                                float screenY = sticks.get(stickIdx).y + event.axisValueY * radius;
////                                if(null==stickMotion0&&!isActive){
////                                }else{
////                                    if(null==stickMotion0){stickMotion0=new StickMotion();}
////                                    stickMotion0.x=screenX;
////                                    stickMotion0.y=screenY;
////                                    stickMotion0.active=isActive;
////                                    stickMotion0.pointer=gamepad.getX1();
////                                    if(isActive&&!running){
////                                        startContinuousUpdates();
////                                    }
////                                }
//                                //现在发现，其实左右摇杆的行为多是不一样的
//                                //左摇杆: 摇杆回中时xy回中
//                                //右摇杆: 摇杆回中时xy不应该回中（因为一般右摇杆用于转视角，视角也会跟着回中就不对了） todo
//                                callback.handleJoystickTouch(screenX,screenY,isActive,event.code);
//                                break;
//                            default:
//                                break;
//                        }
//                    }
//                    eventPool.freeAll(eventQueue);
//                    eventQueue.clear();
//                }
////                Gdx.app.postRunnable(this);
//                if(running){
//                    handler.postDelayed(this, JOYSTICK_UPDATE_INTERVAL);
//                }
//            }
//        }.run();
//    }

    //参考自AndroidControllers.setupEventQueue()
    //改为每次循环只模拟一次（每个key作为一个stroke）
    private void setupEventQueue() {
        running=true;
        new Runnable() {
            @SuppressWarnings("synthetic-access")
            @Override
            public void run () {
                if(null!=callback){
                synchronized(eventQueue) {
                    for(AndroidControllerEvent event: eventQueue) {
//                        boolean processed=false;
                        switch(event.type) {
//                            case AndroidControllerEvent.CONNECTED:
//                                controllers.add(event.controller);
//                                for(ControllerListener listener: listeners) {
//                                    listener.connected(event.controller);
//                                }
//                                break;
//                            case AndroidControllerEvent.DISCONNECTED:
//                                controllers.removeValue(event.controller, true);
//                                for(ControllerListener listener: listeners) {
//                                    listener.disconnected(event.controller);
//                                }
//                                for(ControllerListener listener: event.controller.getListeners()) {
//                                    listener.disconnected(event.controller);
//                                }
//                                break;
//                            case AndroidControllerEvent.BUTTON_DOWN:
////                                event.controller.buttons.put(event.code, event.code);
////                                for(ControllerListener listener: listeners) {
////                                    if(listener.buttonDown(event.controller, event.code)) break;
////                                }
////                                for(ControllerListener listener: event.controller.getListeners()) {
////                                    if(listener.buttonDown(event.controller, event.code)) break;
////                                }
//                                btns.put(event.code, event.code);
//                                break;
//                            case AndroidControllerEvent.BUTTON_UP:
////                                event.controller.buttons.remove(event.code, 0);
////                                for(ControllerListener listener: listeners) {
////                                    if(listener.buttonUp(event.controller, event.code)) break;
////                                }
////                                for(ControllerListener listener: event.controller.getListeners()) {
////                                    if(listener.buttonUp(event.controller, event.code)) break;
////                                }
//
////                                callback.onButtonReleased(event.code, event.getDeviceId());
//                                //todo 改多按钮
//                                btns.remove(event.code, 0);
//                                callback.onButtonReleased(event.code, 0);
//                                break;
//                            case AndroidControllerEvent.AXIS:
////                                event.controller.axes[event.code] = event.axisValue;
////                                for(ControllerListener listener: listeners) {
////                                    if(listener.axisMoved(event.controller, event.code, event.axisValue)) break;
////                                }
////                                for(ControllerListener listener: event.controller.getListeners()) {
////                                    if(listener.axisMoved(event.controller, event.code, event.axisValue)) break;
////                                }
//                                axes.put(event.code,event.axisValue);
//                                axes.put(event.codeY,event.axisValueY);
//                                boolean isActive=event.axisValue!=0||event.axisValueY!=0;
//                                float radius=200;//100
//                                int stickIdx=event.code==gamepad.getX1()?0:1;
//                                float screenX = sticks.get(stickIdx).x + event.axisValue * radius;
//                                float screenY = sticks.get(stickIdx).y + event.axisValueY * radius;
////                                if(null==stickMotion0&&!isActive){
////                                }else{
////                                    if(null==stickMotion0){stickMotion0=new StickMotion();}
////                                    stickMotion0.x=screenX;
////                                    stickMotion0.y=screenY;
////                                    stickMotion0.active=isActive;
////                                    stickMotion0.pointer=gamepad.getX1();
////                                    if(isActive&&!running){
////                                        startContinuousUpdates();
////                                    }
////                                }
//                                //现在发现，其实左右摇杆的行为多是不一样的
//                                //左摇杆: 摇杆回中时xy回中
//                                //右摇杆: 摇杆回中时xy不应该回中（因为一般右摇杆用于转视角，视角也会跟着回中就不对了） todo
//                                callback.handleJoystickTouch(screenX,screenY,isActive,event.code);
//                                break;
                            default:
                                break;
                        }
                    }
//                    if(0==btns.get(gamepad.getCROSS(),-2)){
//                            btns.remove(gamepad.getCROSS(), -2);
//                            callback.onButtonReleased(gamepad.getCROSS(), controller.getDeviceId());
//                    }

//                    for(int i:btnIds){
//                        if(0==btns.get(i,-2)){
//                            btns.remove(i, -2);
//                            callback.onButtonReleased(i, controller.getDeviceId());
//                        }
//                        idx++;
//                    }
                    int idx=0;
                    //这里需要保证btnIds和buttons的索引是对应的
                    for(int i:btnIds){
                        if(0==btns.get(i,-2)){
                            btns.remove(i, -2);
//                            callback.onButtonReleased(i, controller.getDeviceId());
                            callback.onButtonReleased(buttons.get(idx).x,buttons.get(idx).y, controller.getDeviceId());
                        }
                        idx++;
                    }
                    float x=axes.get(gamepad.getX1(),-2);
                    float y=axes.get(gamepad.getY1(),-2);
                    if(x!=axesOld.get(gamepad.getX1(),-2)
                    ||y!=axesOld.get(gamepad.getY1(),-2)
                    ){
                                boolean isActive=x!=0||y!=0;
                                float radius=200;//100
//                                int stickIdx=event.code==gamepad.getX1()?0:1;
                                int stickIdx=0;
                                float x0=sticks.get(stickIdx).x;
                                float y0=sticks.get(stickIdx).y;
                                float screenX = sticks.get(stickIdx).x + x * radius;
                                float screenY = sticks.get(stickIdx).y +y * radius;
                                //现在发现，其实左右摇杆的行为多是不一样的
                                //左摇杆: 摇杆回中时xy回中
                                //右摇杆: 摇杆回中时xy不应该回中（因为一般右摇杆用于转视角，视角也会跟着回中就不对了） todo
//                                callback.handleJoystickTouch(screenX,screenY,isActive,gamepad.getX1());
                                callback.handleJoystickTouch(x0,y0,screenX,screenY,isActive,gamepad.getX1());
                                axesOld.put(gamepad.getX1(),x);
                                axesOld.put(gamepad.getY1(),y);
                    }
                    //右摇杆
                    x=axes.get(gamepad.getX2(),-2);
                     y=axes.get(gamepad.getY2(),-2);
                    if(
                            (x!=axesOld.get(gamepad.getX2(),-2)
                            ||y!=axesOld.get(gamepad.getY2(),-2))
                            ||(x!=0||y!=0)//右摇杆就算不变动，也要持续事件
                    ){
                        boolean isActive=x!=0||y!=0;
                        float radius=80;//视觉200太快;20视觉不动
//                                int stickIdx=event.code==gamepad.getX1()?0:1;
                        int stickIdx=1;
                        float x0=sticks.get(stickIdx).x;
                        float y0=sticks.get(stickIdx).y;
                        float screenX = sticks.get(stickIdx).x + x * radius;
                        float screenY = sticks.get(stickIdx).y +y * radius;
                        //现在发现，其实左右摇杆的行为多是不一样的
                        //左摇杆: 摇杆回中时xy回中
                        //右摇杆: 摇杆回中时xy不应该回中（因为一般右摇杆用于转视角，视角也会跟着回中就不对了） todo
                        //callback.handleJoystickTouch(x0,y0,screenX,screenY,isActive,gamepad.getX2());
                        callback.simulateDrag(x0, y0, screenX, screenY);
                        axesOld.put(gamepad.getX2(),x);
                        axesOld.put(gamepad.getY2(),y);
                    }

                    if(null!=callback) {
                        callback.simulate();
                    }
                    eventPool.freeAll(eventQueue);
                    eventQueue.clear();
                }
                }
//                Gdx.app.postRunnable(this);
                if(running){
                    handler.postDelayed(this, JOYSTICK_UPDATE_INTERVAL);
//                    SGDataHelper.getLog().print("uuid:"+uuid+" "+SGDate.Now().toString());
                }
            }
        }.run();
    }
    private void init(){
        // 初始化绘制工具
        buttonPaint = new Paint();
        buttonPaint.setColor(Color.argb(128, 0, 100, 255));
        buttonPaint.setStyle(Paint.Style.FILL);
        buttonPaint.setAntiAlias(true);

        textPaint = new Paint();
        textPaint.setColor(Color.WHITE);
        textPaint.setTextSize(30);
        textPaint.setTextAlign(Paint.Align.CENTER);
        textPaint.setAntiAlias(true);

        buttons = new ArrayList<>();
        toolButtons = new ArrayList<>();
        sticks=new ArrayList<>();
        loadButtonPositions();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawButtons(canvas);
    }

    private void drawButtons(Canvas canvas) {

        for (ButtonInfo button : toolButtons) {
            // 绘制圆形按钮
//            canvas.drawCircle(button.x, button.y, button.radius, buttonPaint);
//            canvas.drawCircle(this.getX()+button.x,this.getY()+ button.y, button.radius, buttonPaint);
            canvas.drawCircle(button.radius,button.radius, button.radius, buttonPaint);

//            // 绘制按钮文字
//            float textY = button.y - ((textPaint.descent() + textPaint.ascent()) / 2);
////            canvas.drawText(button.label, button.x, textY, textPaint);
//            canvas.drawText(button.label, this.getX()+button.x,this.getY()+ textY, textPaint);
            float textY = radius - ((textPaint.descent() + textPaint.ascent()) / 2);
            canvas.drawText(button.label, radius, textY, textPaint);
        }
    }
//    private String[] btnLbls=new String[]{"A","B","X","Y"};
    private void loadButtonPositions() {
        SharedPreferences prefs = getContext().getSharedPreferences("button_positions", Context.MODE_PRIVATE);

//        // 从SharedPreferences加载按钮位置
//        buttons.add(new ButtonInfo(
//                "A",
//                prefs.getFloat("buttonA_x", 100),
//                prefs.getFloat("buttonA_y", 100),
//                40
//        ));
        for(String i:ButtonOverlayView.btnLbls){
            // 从SharedPreferences加载按钮位置
            buttons.add(new ButtonInfo(
                    i,//"A",
                    prefs.getFloat("button"+i+"_x", 100),
                    prefs.getFloat("button"+i+"_y", 100),
                    40
            ));
        }

//        toolButtons.add(new ButtonInfo(
//                "setting",
//                100,
//                100,
//                40
//        ));

        toolButtons.add(new ButtonInfo(
                "setting",
                40,
                40,
                40
        ));
        for(int i=0;2>i;i++){
            sticks.add(new ButtonInfo(
                    "S"+i,
//                    prefs.getFloat("stick"+i+"_x", 100),
//                    prefs.getFloat("stick"+i+"_y", 100),
                    prefs.getFloat("S"+i+"_x", 100),
                    prefs.getFloat("S"+i+"_y", 100),
                    40
            ));
        }
        // 添加更多按钮...
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        // 再次确保获取焦点，因为此时View已附加到窗口
        post(new Runnable() {
            @Override
            public void run() {
                requestFocus();
            }
        });
    }
//    // 在FocusOverlayView中添加
//    @Override
//    public void onFocusChanged(boolean gainFocus, int direction, Rect previouslyFocusedRect) {
//        super.onFocusChanged(gainFocus, direction, previouslyFocusedRect);
//        Log.d("FocusOverlay", "Focus changed: " + gainFocus);
//
//        if (gainFocus) {
//            setBackgroundColor(Color.argb(20, 0, 255, 0)); // 获取焦点时显示绿色背景
//        } else {
//            setBackgroundColor(Color.TRANSPARENT); // 失去焦点时透明
//        }
//    }

    public void updateButtonPosition(String buttonId, float x, float y) {
        for (ButtonInfo button : toolButtons) {
            if (button.label.equals(buttonId)) {
                button.x = x;
                button.y = y;
                break;
            }
        }
        //这样不能改事件范围
//        this.setX(x);
//        this.setY(y);
        invalidate(); // 请求重绘

//        // 关键：更新 WindowManager 的布局参数
////        layoutParams.x = (int) (newCenterX - getWidth() / 2);
////        layoutParams.y = (int) (newCenterY - getHeight() / 2);
//        float radius=toolButtons.get(0).radius;
////        layoutParams.x = (int) (x-radius);
////        layoutParams.y = (int) (y-radius);
//        layoutParams.x = (int) (x);
//        layoutParams.y = (int) (y);
//        windowManager.updateViewLayout(this, layoutParams);

//        this.centerX = x;
//        this.centerY = y;
//
//        // 关键：更新 WindowManager 的布局参数
//        layoutParams.x = (int) (x - radius);
//        layoutParams.y = (int) (y - radius);
//
//        windowManager.updateViewLayout(this, layoutParams);
    }

    public void updateButtonPositionLayout(String buttonId, float x, float y) {
//        for (ButtonInfo button : buttons) {
//            if (button.label.equals(buttonId)) {
//                button.x = x;
//                button.y = y;
//                break;
//            }
//        }
        //这样不能改事件范围
//        this.setX(x);
//        this.setY(y);
//        invalidate(); // 请求重绘

//        // 关键：更新 WindowManager 的布局参数
////        layoutParams.x = (int) (newCenterX - getWidth() / 2);
////        layoutParams.y = (int) (newCenterY - getHeight() / 2);
//        float radius=toolButtons.get(0).radius;
////        layoutParams.x = (int) (x-radius);
////        layoutParams.y = (int) (y-radius);
//        layoutParams.x = (int) (x);
//        layoutParams.y = (int) (y);
//        windowManager.updateViewLayout(this, layoutParams);

//        this.centerX = x;
//        this.centerY = y;

//        // 关键：更新 WindowManager 的布局参数
//        layoutParams.x = (int) (x - radius);
//        layoutParams.y = (int) (y - radius);
//        //注意drag过程中改变layout会导致坐标一跳一跳的
//        windowManager.updateViewLayout(this, layoutParams);

        this.centerX = x;
        this.centerY = y;

        // 更新 WindowManager 的布局参数
        layoutParams.x = (int) (x - getWidth() / 2);
        layoutParams.y = (int) (y - getHeight() / 2);

        try {
            windowManager.updateViewLayout(this, layoutParams);
        } catch (Exception e) {
            Log.e("FloatingButton", "Failed to update position: " + e.getMessage());
        }
    }
//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        Log.d("FocusOverlay", "Key down: " + keyCode + ", source: " + event.getSource());
//
//        // 检查是否是游戏手柄按钮
//        if (isGamepadButton(keyCode)) {
//            // 拦截这个事件，阻止系统默认行为
//            if (callback != null) {
//                callback.onButtonPressed(keyCode, event.getDeviceId());
//            }
//
//            // 返回 true 表示我们已经消费了这个事件，系统不会处理
//            return true;
//        }
//
//        // 对于非游戏按钮，让系统处理
//        return super.onKeyDown(keyCode, event);
//    }

//    @Override
//    public boolean onKeyUp(int keyCode, KeyEvent event) {
//        Log.d("FocusOverlay", "Key up: " + keyCode);
//
//        if (isGamepadButton(keyCode)) {
//            // 拦截释放事件
//            if (callback != null) {
//                callback.onButtonReleased(keyCode, event.getDeviceId());
//            }
//            return true;
//        }
//
//        return super.onKeyUp(keyCode, event);
//    }

//    /**
//     * 关键：重写 dispatchKeyEvent 来更早地拦截按键
//     */
//    @Override
//    public boolean dispatchKeyEvent(KeyEvent event) {
//        int keyCode = event.getKeyCode();
//
//        // 只拦截游戏手柄按钮
//        if (isGamepadButton(keyCode)) {
//            if (event.getAction() == KeyEvent.ACTION_DOWN) {
//                onKeyDown(keyCode, event);
//            } else if (event.getAction() == KeyEvent.ACTION_UP) {
//                onKeyUp(keyCode, event);
//            }
//            return true; // 消费事件
//        }
//
//        return super.dispatchKeyEvent(event);
//    }

//    // 在 FocusOverlayView 中集成拦截器
//    @Override
//    public boolean dispatchKeyEvent(KeyEvent event) {
//        int keyCode = event.getKeyCode();
//        int action = event.getAction();
//
//        // 使用拦截器检查是否应该拦截
//        if (KeyInterceptor.getInstance().shouldIntercept(keyCode, action)) {
//            // 拦截这个事件
//            if (action == KeyEvent.ACTION_DOWN) {
//                onKeyDown(keyCode, event);
//            } else if (action == KeyEvent.ACTION_UP) {
//                onKeyUp(keyCode, event);
//            }
//            return true; // 消费事件，阻止系统处理
//        }
//
//        return super.dispatchKeyEvent(event);
//    }
    /**
     * 检查是否是游戏手柄按钮
     */
    private boolean isGamepadButton(int keyCode) {
        // 方法1：使用 Android 内置检查
        if (KeyEvent.isGamepadButton(keyCode)) {
            return true;
        }

        // 方法2：检查常见游戏按钮范围
        if (keyCode >= KeyEvent.KEYCODE_BUTTON_A && keyCode <= KeyEvent.KEYCODE_BUTTON_MODE) {
            return true;
        }

        // 方法3：检查我们自定义的拦截列表
        return interceptedKeys.contains(keyCode);
    }

    private boolean dragging=false;
    private String dragId="";
    private long dragBegin=0;
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // 不处理触摸事件，返回 false 让事件传递到下层
        ButtonOverlayView.printEvent(TAG,event);
//        if(0==event.getAction()||MotionEvent.ACTION_OUTSIDE==event.getAction()
//        ){
//            for(ButtonInfo i: toolButtons){
//                //暂时只有一个工具按钮
//                if(i.isPosIn(event.getX(),event.getY())){
//                    if(waiter.isOK()) {
//                        service.removeSimulateOverlay();
//                        service.showButtonOverlay();
//                        return true;
//                    }
//                }
//            }
//        }

        float rawX = event.getRawX();  // 使用绝对坐标
        float rawY = event.getRawY();  // 使用绝对坐标
        if(0==event.getAction()||MotionEvent.ACTION_OUTSIDE==event.getAction()
        ){
//            for(ButtonInfo i: toolButtons){
//                //暂时只有一个工具按钮
//                if(i.isPosIn(event.getX(),event.getY())){
//                    if(waiter.isOK()) {
//                        service.removeSimulateOverlay();
//                        service.showButtonOverlay();
//                        return true;
//                    }
//                }
//            }

            float x=this.getX()+toolButtons.get(0).x;
            float y=this.getY()+toolButtons.get(0).y;
            float radius=toolButtons.get(0).radius;
            boolean isIn=x-radius<=event.getX()&&event.getX()<=x+radius
                    &&y-radius<=event.getY()&&event.getY()<=y+radius;
            if(isIn){
                dragging=true;
                dragId=toolButtons.get(0).label;
                dragBegin=SGDate.Now().toTimestamp();
//                //移动view的时候一定要这样，否则会一跳一跳
////                dragOffsetX = x - getWidth() / 2;
////                dragOffsetY = y - getHeight() / 2;
//                 x = event.getX();
//                 y = event.getY();
//                dragOffsetX = x - radius;
//                dragOffsetY = y - radius;

                // 记录初始位置（绝对坐标）
                initialTouchX = rawX;
                initialTouchY = rawY;
                initialCenterX = centerX;
                initialCenterY = centerY;

                invalidate(); // 重绘以更新颜色
                return true;
            }
        }else if(2==event.getAction()){
            if(dragging) {
//                updateButtonPosition(dragId, event.getX(), event.getY());

//                // 计算新的屏幕坐标(移动view的时候一定要这样，否则会一跳一跳)
//                float x = event.getX();
//                float y = event.getY();
//                float newCenterX = centerX + (x - dragOffsetX - radius);
//                float newCenterY = centerY + (y - dragOffsetY - radius);
//                updateButtonPosition(dragId,newCenterX, newCenterY);

                // 使用绝对坐标计算位移，避免坐标系变化
                float deltaX = rawX - initialTouchX;
                float deltaY = rawY - initialTouchY;

                // 计算新的中心位置
                float newCenterX = initialCenterX + deltaX;
                float newCenterY = initialCenterY + deltaY;

//                // 限制在屏幕范围内
//                newCenterX = Math.max(radius, Math.min(newCenterX, getScreenWidth() - radius));
//                newCenterY = Math.max(radius, Math.min(newCenterY, getScreenHeight() - radius));

                // 更新位置
                updateButtonPositionLayout(dragId,newCenterX, newCenterY);
                return true;
            }
        }else if(1==event.getAction()){
            if(dragging) {
//                saveButtonPositions(dragId,event.getX(),event.getY());
                dragging = false;
                dragId = "";
                invalidate(); // 重绘以恢复颜色
//                updateButtonPositionLayout(dragId, event.getX(), event.getY());
                if(200>SGDate.Now().toTimestamp()-dragBegin){
                    service.removeSimulateOverlay();
                    service.showButtonOverlay();
                }
                return true;
            }
        }
        return false;
    }

    public void setCallback(GamepadCallback callback) {
        this.callback = callback;
    }

    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        return false;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return false;
    }

    @Override
    public boolean onGenericMotion(View v, MotionEvent event) {
        return false;
    }

//    public interface GamepadCallback {
//        void onButtonPressed(int buttonCode, int deviceId);
//        void onButtonReleased(int buttonCode, int deviceId);
//    }
    public void setService(OverlayService service) {
        this.service = service;
    }
//    @Override
//    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        // 设置 View 大小为按钮直径
//        int size = (int) (radius * 2);
//        setMeasuredDimension(size, size);
//    }
//private int getScreenWidth() {
//    DisplayMetrics displayMetrics = new DisplayMetrics();
//    windowManager.getDefaultDisplay().getMetrics(displayMetrics);
//    return displayMetrics.widthPixels;
//}
//
//    private int getScreenHeight() {
//        DisplayMetrics displayMetrics = new DisplayMetrics();
//        windowManager.getDefaultDisplay().getMetrics(displayMetrics);
//        return displayMetrics.heightPixels;
//    }

    @Override
    public void dispose() {
        running=false;
        if(null!=joystickUpdateRunnable){
            handler.removeCallbacks(joystickUpdateRunnable);
            joystickUpdateRunnable=null;
        }
    }
}