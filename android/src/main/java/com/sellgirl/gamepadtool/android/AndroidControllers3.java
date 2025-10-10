//package com.sellgirl.gamepadtool.android;//package com.sellgirl.gamepadtool.android;
//
//import android.view.InputDevice;
//import android.view.KeyEvent;
//import android.view.MotionEvent;
//import android.view.View;
//import android.view.View.OnGenericMotionListener;
//import android.view.View.OnKeyListener;
//
//import com.badlogic.gdx.Gdx;
//import com.badlogic.gdx.LifecycleListener;
//import com.badlogic.gdx.backends.android.AndroidInput;
//import com.badlogic.gdx.controllers.AbstractControllerManager;
//import com.badlogic.gdx.controllers.ControllerListener;
//import com.badlogic.gdx.controllers.android.AndroidControllerEvent;
//import com.badlogic.gdx.utils.Array;
//import com.badlogic.gdx.utils.IntMap;
//import com.badlogic.gdx.utils.IntMap.Entry;
//import com.badlogic.gdx.utils.Pool;
//import com.badlogic.gdx.controllers.android.*;
//import com.sellgirl.sgGameHelper.gamepad.ISGPS5Gamepad;
//
///**
// * 想参考com.badlogic.gdx.controllers.android.AndroidControllers，
// * 但AndroidControllerEvent类是私有
// */
//public class AndroidControllers3  extends AndroidControllers {
//    private final static String TAG = "AndroidControllers3";
//    private ISGPS5Gamepad gamepad;
//    public AndroidControllers3(ISGPS5Gamepad gamepad,AndroidInput input) {
//        this.gamepad=gamepad;
////        listeners.add(new ManageCurrentControllerListener());
//        Gdx.app.addLifecycleListener(this);
////        gatherControllers(false);
////        setupEventQueue();
////        ((AndroidInput)Gdx.input).addKeyListener(this);
////        ((AndroidInput)Gdx.input).addGenericMotionListener(this);
//        input.addGenericMotionListener(this);
//
//        // use InputManager on Android +4.1 to receive (dis-)connect events
//        if(Gdx.app.getVersion() >= 16) {
//            try {
//                String className = "com.badlogic.gdx.controllers.android.ControllerLifeCycleListener";
//                Class.forName(className).getConstructor(AndroidControllers.class).newInstance(this);
//            } catch(Exception e) {
//                Gdx.app.log(TAG, "Couldn't register controller life-cycle listener");
//            }
//        }
//    }
////    private void gatherControllers(boolean sendEvent) {
////        // gather all joysticks and gamepads, remove any disconnected ones
////        IntMap<AndroidController> removedControllers = new IntMap<AndroidController>();
////        removedControllers.putAll(controllerMap);
////
////        for(int deviceId: InputDevice.getDeviceIds()) {
////            AndroidController controller = controllerMap.get(deviceId);
////            if(controller != null) {
////                removedControllers.remove(deviceId);
////            } else {
////                addController(deviceId, sendEvent);
////            }
////        }
////
////        for(Entry<AndroidController> entry: removedControllers.entries()) {
////            removeController(entry.key);
////        }
////    }
//
//    @Override
//    public boolean onGenericMotion (View view, MotionEvent motionEvent) {
//        if((motionEvent.getSource() & InputDevice.SOURCE_CLASS_JOYSTICK) == 0) return false;
//        AndroidController controller = controllerMap.get(motionEvent.getDeviceId());
//        if(controller != null) {
//            synchronized(eventQueue) {
//                if (controller.hasPovAxis()) {
//                    float povX = motionEvent.getAxisValue(MotionEvent.AXIS_HAT_X);
//                    float povY = motionEvent.getAxisValue(MotionEvent.AXIS_HAT_Y);
//                    // map axis movement to dpad buttons
//                    if (povX != controller.povX) {
//                        if (controller.povX == 1f) {
//                            com.badlogic.gdx.controllers.android.AndroidControllerEvent event = eventPool.obtain();
//                            event.controller = controller;
//                            event.type = com.badlogic.gdx.controllers.android.AndroidControllerEvent.BUTTON_UP;
//                            event.code = KeyEvent.KEYCODE_DPAD_RIGHT;
//                            eventQueue.add(event);
//                        } else if (controller.povX == -1f) {
//                            com.badlogic.gdx.controllers.android.AndroidControllerEvent event = eventPool.obtain();
//                            event.controller = controller;
//                            event.type = com.badlogic.gdx.controllers.android.AndroidControllerEvent.BUTTON_UP;
//                            event.code = KeyEvent.KEYCODE_DPAD_LEFT;
//                            eventQueue.add(event);
//                        }
//
//                        if (povX == 1f) {
//                            com.badlogic.gdx.controllers.android.AndroidControllerEvent event = eventPool.obtain();
//                            event.controller = controller;
//                            event.type = com.badlogic.gdx.controllers.android.AndroidControllerEvent.BUTTON_DOWN;
//                            event.code = KeyEvent.KEYCODE_DPAD_RIGHT;
//                            eventQueue.add(event);
//                        } else if (povX == -1f) {
//                            com.badlogic.gdx.controllers.android.AndroidControllerEvent event = eventPool.obtain();
//                            event.controller = controller;
//                            event.type = com.badlogic.gdx.controllers.android.AndroidControllerEvent.BUTTON_DOWN;
//                            event.code = KeyEvent.KEYCODE_DPAD_LEFT;
//                            eventQueue.add(event);
//                        }
//                        controller.povX = povX;
//                    }
//
//                    if (povY != controller.povY) {
//                        if (controller.povY == 1f) {
//                            com.badlogic.gdx.controllers.android.AndroidControllerEvent event = eventPool.obtain();
//                            event.controller = controller;
//                            event.type = com.badlogic.gdx.controllers.android.AndroidControllerEvent.BUTTON_UP;
//                            event.code = KeyEvent.KEYCODE_DPAD_DOWN;
//                            eventQueue.add(event);
//                        } else if (controller.povY == -1f) {
//                            com.badlogic.gdx.controllers.android.AndroidControllerEvent event = eventPool.obtain();
//                            event.controller = controller;
//                            event.type = com.badlogic.gdx.controllers.android.AndroidControllerEvent.BUTTON_UP;
//                            event.code = KeyEvent.KEYCODE_DPAD_UP;
//                            eventQueue.add(event);
//                        }
//
//                        if (povY == 1f) {
//                            com.badlogic.gdx.controllers.android.AndroidControllerEvent event = eventPool.obtain();
//                            event.controller = controller;
//                            event.type = com.badlogic.gdx.controllers.android.AndroidControllerEvent.BUTTON_DOWN;
//                            event.code = KeyEvent.KEYCODE_DPAD_DOWN;
//                            eventQueue.add(event);
//                        } else if (povY == -1f) {
//                            com.badlogic.gdx.controllers.android.AndroidControllerEvent event = eventPool.obtain();
//                            event.controller = controller;
//                            event.type = com.badlogic.gdx.controllers.android.AndroidControllerEvent.BUTTON_DOWN;
//                            event.code = KeyEvent.KEYCODE_DPAD_UP;
//                            eventQueue.add(event);
//                        }
//                        controller.povY = povY;
//
//                    }
//                }
//
//                if (controller.hasTriggerAxis()){
//                    float lTrigger = motionEvent.getAxisValue(MotionEvent.AXIS_LTRIGGER);
//                    float rTrigger = motionEvent.getAxisValue(MotionEvent.AXIS_RTRIGGER);
//                    //map axis movement to trigger buttons
//                    if (lTrigger != controller.lTrigger){
//                        if (lTrigger == 1){
//                            com.badlogic.gdx.controllers.android.AndroidControllerEvent event = eventPool.obtain();
//                            event.controller = controller;
//                            event.type = com.badlogic.gdx.controllers.android.AndroidControllerEvent.BUTTON_DOWN;
//                            event.code = KeyEvent.KEYCODE_BUTTON_L2;
//                            eventQueue.add(event);
//                        } else if (lTrigger == 0){
//                            com.badlogic.gdx.controllers.android.AndroidControllerEvent event = eventPool.obtain();
//                            event.controller = controller;
//                            event.type = com.badlogic.gdx.controllers.android.AndroidControllerEvent.BUTTON_UP;
//                            event.code = KeyEvent.KEYCODE_BUTTON_L2;
//                            eventQueue.add(event);
//                        }
//                        controller.lTrigger = lTrigger;
//
//                    }
//
//                    if (rTrigger != controller.rTrigger){
//                        if (rTrigger == 1){
//                            com.badlogic.gdx.controllers.android.AndroidControllerEvent event = eventPool.obtain();
//                            event.controller = controller;
//                            event.type = com.badlogic.gdx.controllers.android.AndroidControllerEvent.BUTTON_DOWN;
//                            event.code = KeyEvent.KEYCODE_BUTTON_R2;
//                            eventQueue.add(event);
//                        } else if (rTrigger == 0){
//                            com.badlogic.gdx.controllers.android.AndroidControllerEvent event = eventPool.obtain();
//                            event.controller = controller;
//                            event.type = com.badlogic.gdx.controllers.android.AndroidControllerEvent.BUTTON_UP;
//                            event.code = KeyEvent.KEYCODE_BUTTON_R2;
//                            eventQueue.add(event);
//                        }
//                        controller.rTrigger = rTrigger;
//
//                    }
//                }
//
//                int axisIndex = 0;
//                for (int axisId: controller.axesIds) {
//                    float axisValue = motionEvent.getAxisValue(axisId);
//                    if(controller.getAxis(axisIndex) == axisValue) {
//                        axisIndex++;
//                        continue;
//                    }
//                    com.badlogic.gdx.controllers.android.AndroidControllerEvent event = eventPool.obtain();
//                    event.type = com.badlogic.gdx.controllers.android.AndroidControllerEvent.AXIS;
//                    event.controller = controller;
//                    event.code = axisIndex;
//                    event.axisValue = axisValue;
//                    eventQueue.add(event);
//                    axisIndex++;
//                }
//            }
//            return true;
//        }
//        return false;
//    }
//}