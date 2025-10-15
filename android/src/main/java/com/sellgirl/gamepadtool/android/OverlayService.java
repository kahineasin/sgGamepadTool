package com.sellgirl.gamepadtool.android;

import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.graphics.PixelFormat;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidInput;
import com.badlogic.gdx.controllers.ControllerListener;
import com.sellgirl.gamepadtool.android.simulate.InstrumentationInjector;
import com.sellgirl.gamepadtool.android.simulate.TouchSimulationService;
import com.sellgirl.gamepadtool.phone.ISGTouchSimulate;
import com.sellgirl.sgGameHelper.SGLibGdxHelper;
import com.sellgirl.sgGameHelper.gamepad.ISGPS5Gamepad;

import java.util.HashMap;
import java.util.Map;

public class OverlayService extends Service implements GamepadCallback {

    private String TAG="OverlayService";
    public static final String ACTION_SIMULATE = "SIMULATE";
    public static final String ACTION_SETTING = "SETTING";
    private WindowManager windowManager;
    private ButtonOverlayView buttonView;
    private boolean isOverlayShowing = false;

    private FocusOverlayView simulateView;
//    private WindowManager windowManager;

    // 按钮映射配置
    private Map<Integer, ButtonMapping> buttonMappings = new HashMap<>();


//    private GamepadService gamepadService;
    private boolean isBound = false;
    private ISGPS5Gamepad gamepad;
    @Override
    public void onCreate() {
        super.onCreate();
        setupButtonMappings();

//        bindGamepadService();

        gamepad= SGLibGdxHelper.getSGGamepad();
        activeTouchPoints=new HashMap<>();
        windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
//        createInputOverlay();
        showButtonOverlay();

//        // 等待触摸服务可用
//        waitForTouchService();
    }
//    private void waitForTouchService() {
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                TouchSimulationService touchService = TouchSimulationService.getInstance();
//                if (touchService == null || !touchService.isEnabled()) {
//                    // 继续等待
//                    waitForTouchService();
//                } else {
//                    Log.d("OverlayService", "Touch service ready");
//                    setupJoystickHandling();
//                }
//            }
//        }, 1000);
//    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
//        if (!isOverlayShowing) {
//            showOverlay();
//        }

        if (intent != null && intent.getAction() != null) {
            String action = intent.getAction();

            switch (action) {
                case ACTION_SIMULATE:
//                    windowManager.removeView(buttonView);
                    removeButtonOverlay();
                    showSimulateOverlay();
                    break;
                case ACTION_SETTING:
//                    windowManager.removeView(simulateView);
                    removeSimulateOverlay();
                    showButtonOverlay();
                    break;
                default:
                    break;
            }
        }
        return START_STICKY;
    }

    public void showButtonOverlay() {
        if (buttonView != null) {
            return; // 已经显示
        }

        // 创建悬浮窗布局参数
        WindowManager.LayoutParams params = new WindowManager.LayoutParams(
//                WindowManager.LayoutParams.WRAP_CONTENT,
//                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN,//只有半屏
//                WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                1, 1,
                Build.VERSION.SDK_INT >= Build.VERSION_CODES.O ?
                        WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY :
                        WindowManager.LayoutParams.TYPE_PHONE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL // 不拦截触摸(没有的话，touch无效)
//                        |WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE //|
//                        |WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE // 注意：这里设置为不接收触摸
                ,
                PixelFormat.TRANSLUCENT
        );

        params.gravity = Gravity.TOP | Gravity.START;
        params.x = 0;
        params.y = 100;

        // 创建悬浮窗视图
        buttonView = createButtonOverlayView();
        buttonView.setService(this);
        windowManager.addView(buttonView, params);
        isOverlayShowing = true;
    }
    private ButtonOverlayView createButtonOverlayView() {
        ButtonOverlayView overlayView = new ButtonOverlayView(this);

        overlayView.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
        ));

//        // 设置为可触摸（如果需要）
//        overlayView.setClickable(false); // 根据需求设置

        overlayView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                return false;
            }
        });

        return overlayView;
    }
    public void removeButtonOverlay(){
        windowManager.removeView(buttonView);
        buttonView=null;
    }
//    /**
//     * 设置按钮映射 - 这里可以配置哪些按钮需要拦截和映射
//     */
//    private void setupButtonMappings() {
//        // B键映射 - 拦截系统返回行为
//        buttonMappings.put(KeyEvent.KEYCODE_BUTTON_B, new ButtonMapping(
//                "B", "attack",  // 映射为攻击
//                true  // 需要拦截系统行为
//        ));
//
//        // A键映射
//        buttonMappings.put(KeyEvent.KEYCODE_BUTTON_A, new ButtonMapping(
//                "A", "jump",
//                true
//        ));
//
//        // X键映射
//        buttonMappings.put(KeyEvent.KEYCODE_BUTTON_X, new ButtonMapping(
//                "X", "reload",
//                true
//        ));
//
//        // Y键映射
//        buttonMappings.put(KeyEvent.KEYCODE_BUTTON_Y, new ButtonMapping(
//                "Y", "switch_weapon",
//                true
//        ));
//
//        // 肩键映射
//        buttonMappings.put(KeyEvent.KEYCODE_BUTTON_L1, new ButtonMapping(
//                "L1", "aim",
//                true
//        ));
//
//        buttonMappings.put(KeyEvent.KEYCODE_BUTTON_R1, new ButtonMapping(
//                "R1", "shoot",
//                true
//        ));
//    }

//    private void createInputOverlayOld() {
////        windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
//
//        WindowManager.LayoutParams params = new WindowManager.LayoutParams(
//                1, 1, // 最小尺寸，几乎不可见
//                Build.VERSION.SDK_INT >= Build.VERSION_CODES.O ?
//                        WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY :
//                        WindowManager.LayoutParams.TYPE_PHONE,
//                WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL |    // 不拦截触摸
//                        WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH | // 接收外部触摸
//                        WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,       // 可获取焦点
//                PixelFormat.TRANSLUCENT
//        );
//
//        // 放置在屏幕外
//        params.gravity = Gravity.START | Gravity.TOP;
//        params.x = -1000;
//        params.y = -1000;
//
//        simulateView = new FocusOverlayView(this);
//        simulateView.setCallback(this);
////        focusOverlayView.setOnKeyListener(new View.OnKeyListener() {
////            @Override
////            public boolean onKey(View v, int keyCode, KeyEvent event) {
////                return false;
////            }
////        });
//
//        windowManager.addView(simulateView, params);
//    }
    public void showSimulateOverlay() {
        //WindowManager windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
        if(null!= simulateView){
            return;
        }
        WindowManager.LayoutParams params = new WindowManager.LayoutParams(
//                1, 1,
//                150,150,//ok
//                1000,1000,
                80,80,
//                WindowManager.LayoutParams.WRAP_CONTENT,
//                WindowManager.LayoutParams.WRAP_CONTENT,
                Build.VERSION.SDK_INT >= Build.VERSION_CODES.O ?
                        WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY :
                        WindowManager.LayoutParams.TYPE_PHONE,
                // 关键：不要使用FLAG_NOT_FOCUSABLE，允许获取焦点
                WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
//                        |WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH
//                        |WindowManager.LayoutParams.FLAG_SPLIT_TOUCH
//                |WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE//有这句的话，touch一定可以穿透本窗口
                , // 允许触摸事件传递
                PixelFormat.TRANSLUCENT
        );

        // 设置位置
        params.gravity = Gravity.START | Gravity.TOP;
//        params.x = 0;
//        params.y = 0;
        params.x = 100;
        params.y = 100;

        simulateView = new FocusOverlayView(this,windowManager,params,gamepad);
        simulateView.setCallback(
                OverlayService.this
//                new GamepadCallback() {
//                    @Override
//                    public void onButtonPressed(int buttonCode, int deviceId) {
//        //                handleGamepadButton(buttonCode, deviceId, true);
//        //                TouchSimulationService.getInstance().simulateTouch(simulateView.buttons.get(0).x,simulateView.buttons.get(0).y,MotionEvent.ACTION_DOWN,50);
//                        int a=1;
//                    }
//
//                    @Override
//                    public void onButtonReleased(int buttonCode, int deviceId) {
//        //                handleGamepadButton(buttonCode, deviceId, false);
//                        TouchSimulationService.getInstance().simulateTouch(simulateView.buttons.get(0).x,simulateView.buttons.get(0).y,MotionEvent.ACTION_DOWN,50);
//                        int a=1;
//                    }
//
//                    @Override
//                    public void onAxisMoved(int axis, float value, int deviceId) {
//                        int a=1;
//                    }
//
//                    @Override
//                    public void handleJoystickTouch(//String joystickId,
//                                                    float x, float y, boolean isActive, int pointerId) {
//                            OverlayService.this.handleJoystickTouch(//joystickId,
//                                    x, y, isActive, pointerId);
//                    }
//                }
        );

        simulateView.setService(this);
        windowManager.addView(simulateView, params);

//        //参考自AndroidControllers，不过感觉太复杂了
//        AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
//        input=new DefaultAndroidInput(Gdx.app, (Activity)Gdx.app, this, config);//没必要，DefaultAndroidInput和手柄没太大关系，只做了一点事件转发
////        input.addKeyListener(simulateView);
////        input.addGenericMotionListener(simulateView);

//        controllerListener = new ControllerListener() {
//            @Override
//            public void connected(Controller controller) {}
//
//            @Override
//            public void disconnected(Controller controller) {}
//
//            @Override
//            public boolean buttonDown(Controller controller, int buttonCode) {
//                // 这会在后台继续工作
//                if (isAppInBackground()) {
//                    //handleBackgroundControllerInput(buttonCode, true);
//                    return true; // 拦截事件
//                }
//                return false;
//            }
//
//            @Override
//            public boolean buttonUp(Controller controller, int buttonCode) {
//                if (isAppInBackground()) {
//                    //handleBackgroundControllerInput(buttonCode, false);
//                    return true;
//                }
//                return false;
//            }
//
//            @Override
//            public boolean axisMoved(Controller controller, int axisCode, float value) {
//                return false;
//            }
//        };
//
//        // 为所有控制器注册监听器
//        for (Controller controller : Controllers.getControllers()) {
//            controller.addListener(controllerListener);
//            int aa=1;
//        }
    }
    private AndroidInput input=null;
    private ControllerListener controllerListener;

    private boolean isAppInBackground() {
        return Gdx.app.getType() == Application.ApplicationType.Android &&
                ((AndroidApplication)Gdx.app).isFinishing();
    }
    public void removeSimulateOverlay(){
        windowManager.removeView(simulateView);
        simulateView.dispose();
        simulateView=null;
        input=null;

    }
//    @Override
//    public void onButtonPressed(int buttonCode, int deviceId) {
//        Log.d("OverlayService", "Button pressed: " + buttonCode);
//
//        ButtonMapping mapping = buttonMappings.get(buttonCode);
//        if (mapping != null && mapping.shouldIntercept) {
//            // 执行映射的动作
//            performMappedAction(mapping.action, deviceId);
//
//            // 记录拦截日志
//            Log.i("InputInterceptor", "Intercepted button: " + mapping.buttonName +
//                    ", mapped to: " + mapping.action);
//        } else {
//            Log.d("OverlayService", "No mapping for button: " + buttonCode);
//        }
//    }

//    @Override
//    public void onButtonReleased(int buttonCode, int deviceId) {
//        Log.d("OverlayService", "Button released: " + buttonCode);
//
//        ButtonMapping mapping = buttonMappings.get(buttonCode);
//        if (mapping != null && mapping.shouldIntercept) {
//            // 处理按钮释放逻辑
//            performMappedActionRelease(mapping.action, deviceId);
//        }
//    }

//    /**
//     * 执行映射的动作
//     */
//    private void performMappedAction(String action, int deviceId) {
//        switch (action) {
//            case "jump":
//                simulateTouchAtMappedPosition("jump_button");
//                break;
//            case "attack":
//                simulateTouchAtMappedPosition("attack_button");
//                break;
//            case "reload":
//                simulateTouchAtMappedPosition("reload_button");
//                break;
//            case "switch_weapon":
//                simulateTouchAtMappedPosition("switch_button");
//                break;
//            case "aim":
//                // 处理持续按下的动作，如瞄准
//                startAiming();
//                break;
//            case "shoot":
//                simulateTouchAtMappedPosition("shoot_button");
//                break;
//        }
//    }

    private void performMappedActionRelease(String action, int deviceId) {
        switch (action) {
            case "aim":
                // 停止瞄准
                stopAiming();
                break;
            // 其他需要处理释放的动作
        }
    }

//    private void simulateTouchAtMappedPosition(String buttonId) {
//        // 从配置中获取按钮位置
//        SharedPreferences prefs = getSharedPreferences("button_positions", MODE_PRIVATE);
//        float x = prefs.getFloat(buttonId + "_x", 0);
//        float y = prefs.getFloat(buttonId + "_y", 0);
//
//        if (x > 0 && y > 0) {
////            // 使用无障碍服务模拟点击
////            if (MyAccessibilityService.getInstance() != null) {
////                MyAccessibilityService.getInstance().simulateTap(x, y);
////            }
//
//            if (TouchSimulationService.getInstance() != null) {
//                TouchSimulationService.getInstance().simulateTouchFromGdx(x, y);
//            }
//        }
//    }

//    private void startAiming() {
//        // 实现瞄准开始逻辑
//    }
//
//    private void stopAiming() {
//        // 实现瞄准结束逻辑
//    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        removeOverlay();
//        if (simulateView != null && windowManager != null) {
//            windowManager.removeView(simulateView);
//        }
        removeSimulateOverlay();
        windowManager=null;
        if (isBound) {
//            unbindService(serviceConnection);
            isBound = false;
        }

        // 停止所有触摸点
        TouchSimulationService touchService = TouchSimulationService.getInstance();
        if (touchService != null) {
            touchService.stopAllTouchPoints();
        }
    }
    private void removeOverlay() {
        if (buttonView != null) {
            windowManager.removeView(buttonView);
            buttonView = null;
            isOverlayShowing = false;
        }
    }

    // 更新按钮位置的方法
    public void updateButtonPosition(String buttonId, float x, float y) {
        SharedPreferences prefs = getSharedPreferences("button_positions", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putFloat(buttonId + "_x", x);
        editor.putFloat(buttonId + "_y", y);
        editor.apply();

        // 强制重绘
        if (buttonView != null) {
            buttonView.invalidate();
        }
    }
//    // 修改 OverlayService 中的参数
//    private void setupTouchableOverlay() {
//        WindowManager.LayoutParams params = new WindowManager.LayoutParams(
//                WindowManager.LayoutParams.WRAP_CONTENT,
//                WindowManager.LayoutParams.WRAP_CONTENT,
//                Build.VERSION.SDK_INT >= Build.VERSION_CODES.O ?
//                        WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY :
//                        WindowManager.LayoutParams.TYPE_PHONE,
//                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE |
//                        WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH,
//                PixelFormat.TRANSLUCENT
//        );
//
//        // 创建可触摸的视图
////        overlayView = new TouchableOverlayView(this);
//        overlayView = createOverlayView();
//        overlayView.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                // 处理触摸事件，判断是否点击了按钮
//                handleTouchEvent(event);
//                return true;
//            }
//        });
//    }

    private void handleTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

//        // 检查是否点击了某个按钮区域
//        if (isPointInButton(x, y, "button1")) {
//            // 通过无障碍服务模拟点击目标游戏
//            simulateTapAtTarget(x, y);
//        }
    }
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    /**
     * 按钮映射配置类
     */
//    private static class ButtonMapping {
//        String buttonName;
//        String action;
//        boolean shouldIntercept;
//
//        ButtonMapping(String buttonName, String action, boolean shouldIntercept) {
//            this.buttonName = buttonName;
//            this.action = action;
//            this.shouldIntercept = shouldIntercept;
//        }
//    }

    /**
     * 按钮映射配置
     */
    private static class ButtonMapping {
        String buttonName;
        String action;

        ButtonMapping(String buttonName, String action) {
            this.buttonName = buttonName;
            this.action = action;
        }
    }
    //-------------------------------手柄事件-----------------------

//    private void bindGamepadService() {
//        Intent intent = new Intent(this, GamepadService.class);
//        intent.setAction("com.yourpackage.GAMEPAD_SERVICE");
//        bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
//    }

//    private ServiceConnection serviceConnection = new ServiceConnection() {
//        @Override
//        public void onServiceConnected(ComponentName name, IBinder service) {
//            Log.d("OverlayService", "GamepadService connected");
//            GamepadService.GamepadBinder binder = (GamepadService.GamepadBinder) service;
//            gamepadService = binder.getService();
//            gamepadService.setCallback(OverlayService.this);
//            isBound = true;
//        }
//
//        @Override
//        public void onServiceDisconnected(ComponentName name) {
//            Log.d("OverlayService", "GamepadService disconnected");
//            gamepadService = null;
//            isBound = false;
//        }
//    };

    private void setupButtonMappings() {
        // 配置按钮映射
        buttonMappings.put(KeyEvent.KEYCODE_BUTTON_A, new ButtonMapping("A", "jump"));
        buttonMappings.put(KeyEvent.KEYCODE_BUTTON_B, new ButtonMapping("B", "attack"));
        buttonMappings.put(KeyEvent.KEYCODE_BUTTON_X, new ButtonMapping("X", "reload"));
        buttonMappings.put(KeyEvent.KEYCODE_BUTTON_Y, new ButtonMapping("Y", "switch_weapon"));
        buttonMappings.put(KeyEvent.KEYCODE_BUTTON_L1, new ButtonMapping("L1", "aim"));
        buttonMappings.put(KeyEvent.KEYCODE_BUTTON_R1, new ButtonMapping("R1", "shoot"));
    }

//    @Override
//    public void onButtonPressed(int buttonCode, int deviceId) {
//        Log.d("OverlayService", "Button pressed: " + buttonCode + ", device: " + deviceId);
//
//        ButtonMapping mapping = buttonMappings.get(buttonCode);
//        if (mapping != null) {
//            // 执行映射动作
//            performMappedAction(mapping.action, true);
//
//            // 记录日志
//            Log.i("GamepadInput", "Button " + mapping.buttonName + " pressed, action: " + mapping.action);
//        }
//    }

    @Override
//    public void onButtonReleased(int buttonCode, int deviceId) {
    public void onButtonReleased(float x, float y, int deviceId){
//        Log.d("OverlayService", "Button released: " + buttonCode + ", device: " + deviceId);
//
//        ButtonMapping mapping = buttonMappings.get(buttonCode);
//        if (mapping != null) {
//            performMappedAction(mapping.action, false);
//        }
        ISGTouchSimulate touchService = getTouchService();
        if (touchService == null) {
            Log.w("OverlayService", "Touch service not available");
            return;
        }
//        float x=simulateView.buttons.get(0).x;
//        float y=simulateView.buttons.get(0).y;
        touchService.simulateTouchFromGdx(x, y);
    }
//
//    @Override
//    public void onAxisMoved(int axis, float value, int deviceId) {
//        // 处理摇杆移动
//        Log.d("OverlayService", "Axis moved: " + axis + " = " + value + ", device: " + deviceId);
//
//        // 这里可以根据摇杆输入实现更复杂的控制
//        if (Math.abs(value) > 0.5f) {
//            // 处理显著的摇杆移动
//        }
//    }

//    private void performMappedAction(String action, boolean pressed) {
//        switch (action) {
//            case "jump":
//                if (pressed) simulateTouchAtMappedPosition("jump_button");
//                break;
//            case "attack":
//                if (pressed) simulateTouchAtMappedPosition("attack_button");
//                break;
//            case "reload":
//                if (pressed) simulateTouchAtMappedPosition("reload_button");
//                break;
//            case "switch_weapon":
//                if (pressed) simulateTouchAtMappedPosition("switch_button");
//                break;
//            case "aim":
//                if (pressed) startAiming();
//                else stopAiming();
//                break;
//            case "shoot":
//                if (pressed) simulateTouchAtMappedPosition("shoot_button");
//                break;
//        }
//    }

//    private void simulateTouchAtMappedPosition(String buttonId) {
//        // 从配置中获取按钮位置并模拟点击
//        SharedPreferences prefs = getSharedPreferences("button_positions", MODE_PRIVATE);
//        float x = prefs.getFloat(buttonId + "_x", 0);
//        float y = prefs.getFloat(buttonId + "_y", 0);
//
//        if (x > 0 && y > 0) {
//            // 使用无障碍服务模拟点击
//            if (TouchSimulationService.getInstance() != null) {
//                TouchSimulationService.getInstance().simulateTouchFromGdx(x, y);
//            } else {
//                Log.w("OverlayService", "Accessibility service not available");
//            }
//        }
//    }

    private void startAiming() {
        // 实现瞄准开始逻辑
    }

    private void stopAiming() {
        // 实现瞄准结束逻辑
    }

    //-------------------------------处理摇杆-----------------------
    private void setupJoystickHandling() {
        // 设置摇杆处理
        // 这里集成您之前的摇杆管理代码
    }

    private ISGTouchSimulate getTouchSimulate(){
        return TouchSimulationService.getInstance();
    }
    /**
     * 触摸点信息类
     */
    private static class TouchPoint {
        float x, y;
//        GestureDescription.StrokeDescription stroke;

        TouchPoint(float x, float y//, GestureDescription.StrokeDescription stroke
        ) {
            this.x = x;
            this.y = y;
//            this.stroke = stroke;
        }
    }
    // 存储当前活动的触摸点
    private Map<Integer, TouchPoint> activeTouchPoints = new HashMap<>();
    /**
     * 检查指定触摸点是否活跃
     */
    public boolean isTouchPointActive(int pointerId) {
        return activeTouchPoints.containsKey(pointerId);
    }

    private InstrumentationInjector instrumentation=null;
    private ISGTouchSimulate getTouchService(){
        return TouchSimulationService.getInstance();
//        if(null==instrumentation){instrumentation=new InstrumentationInjector();}
//        return instrumentation;
    }
    /**
     * 处理摇杆触摸事件
     * 此方法职能是把摇杆事件格式转为touch:DOWN MOVE UP的格式
     * @param pointerId 计算轴的UP DOWN, 所以用axisX或axisY都可以的
     */
    public void handleJoystickTouch(//String joystickId,
                                    float x0, float y0,
                                    float x, float y, boolean isActive, int pointerId) {
        ISGTouchSimulate touchService = getTouchService();
        if (touchService == null) {
            Log.w("OverlayService", "Touch service not available");
            return;
        }

        if (isActive) {
            if (!isTouchPointActive(pointerId)) {
                // 第一次激活，发送 DOWN 事件
                if(touchService.simulateTouchDown(x0,y0,x, y, pointerId)){
                    activeTouchPoints.put(pointerId, new TouchPoint(x, y));
                }
//                Log.d(TAG, "simulateTouchDown:  at " + x + ", " + y+" id:"+pointerId);
            } else {
                // 持续激活，发送 MOVE 事件
                if(touchService.simulateTouchMove(x, y, pointerId)) {
                    activeTouchPoints.put(pointerId, new TouchPoint(x, y));
                }
//                Log.d(TAG, "simulateTouchMove:  at " + x + ", " + y+" id:"+pointerId);
            }
        } else {
            if (isTouchPointActive(pointerId)) {
                // 失活，发送 UP 事件
                if(touchService.simulateTouchUp(x, y, pointerId)){
                    activeTouchPoints.remove(pointerId);
                }
//                Log.d(TAG, "simulateTouchUp:  at " + x + ", " + y+" id:"+pointerId);
            }
        }
    }

    @Override
    public boolean simulateDrag(float x0, float y0, float x, float y) {
        ISGTouchSimulate touchService = getTouchService();
        if (touchService == null) {
            Log.w("OverlayService", "Touch service not available");
            return false;
        }
        return touchService.simulateDrag(x0,y0,x,y);
    }


//    /**
//     * 处理按钮点击事件
//     */
//    public void handleButtonTap(float x, float y) {
//        TouchSimulationService touchService = TouchSimulationService.getInstance();
//        if (touchService != null) {
//            // 使用简单的点击模拟
//            touchService.simulateTap(x, y, 50);
//        }
//    }

    @Override
    public boolean simulate() {
        ISGTouchSimulate touchService = getTouchService();
        if (touchService == null) {
//            Log.w("OverlayService", "Touch service not available");
            return false;
        }
        return touchService.simulate();

    }
    //-------------------------------处理摇杆 end-----------------------
}