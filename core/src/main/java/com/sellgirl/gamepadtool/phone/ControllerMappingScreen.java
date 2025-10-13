package com.sellgirl.gamepadtool.phone;

// ControllerMappingScreen.java

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;
import com.sellgirl.gamepadtool.AndroidGamepadTool;
import com.sellgirl.gamepadtool.GamepadTool;
import com.sellgirl.gamepadtool.MyInputProcessor;
import com.sellgirl.gamepadtool.input.SGXInputControllerListener;
import com.sellgirl.sgGameHelper.SGLibGdxHelper;
import com.sellgirl.sgGameHelper.gamepad.ISGPS5Gamepad;
import com.sellgirl.sgGameHelper.gamepad.XBoxKey;

public class ControllerMappingScreen implements Screen {
    private AndroidGamepadTool game;

    private ShapeRenderer shapeRenderer;
    private SpriteBatch batch;
    private Array<FloatingButton> virtualButtons;
    private ObjectMap<Integer, FloatingButton> keyMapping; // 映射：手柄按键 -> 虚拟按钮
    private ObjectMap<Integer, FloatingButton> controllerMapping;

    // 用于拖动
    private FloatingButton draggedButton;
    private ISGPS5Gamepad gamepad;

    public ControllerMappingScreen(AndroidGamepadTool game) {
        this.game=game;

        shapeRenderer = new ShapeRenderer();
        batch = new SpriteBatch();
        virtualButtons = new Array<>();
        keyMapping = new ObjectMap<>();

        createDefaultButtons();
        setupDefaultKeyMapping();

        gamepad= SGLibGdxHelper.getSGGamepad();

        InputMultiplexer multiplexer = new InputMultiplexer();
        multiplexer.addProcessor(new MyInputProcessor());
        // 可能添加你原有的UI输入处理器
        Gdx.input.setInputProcessor(multiplexer);
        for (Controller controller : Controllers.getControllers()) {
//            if("XInput Controller".equals(controller.getName())) {
//                controller.addListener(new SGXInputControllerListener());
//            }
            controller.addListener(new SGXInputControllerListener());

        }

        game.startOverlayService();
    }

    private void createDefaultButtons() {
        float screenWidth = Gdx.graphics.getWidth();
        float screenHeight = Gdx.graphics.getHeight();

        virtualButtons.add(new FloatingButton("A", screenWidth - 150, 200, 80, 80));
        virtualButtons.add(new FloatingButton("B", screenWidth - 250, 200, 80, 80));
        // 添加更多按钮...

    }

    private void setupDefaultKeyMapping() {
//        // 映射 libGDX 的控制器按键常量到虚拟按钮
//        // 您需要根据实际手柄测试这些键值
//        keyMapping.put(0, virtualButtons.get(0)); // 例如，A按钮
//        keyMapping.put(1, virtualButtons.get(1)); // 例如，B按钮

        keyMapping.put(XBoxKey.CROSS.ordinal(), virtualButtons.get(0)); // 例如，A按钮
        keyMapping.put(XBoxKey.ROUND.ordinal(), virtualButtons.get(1)); // 例如，B按钮
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 0.3f); // 半透明背景
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        handleControllerInput();
        drawButtons();
    }

    @Override
    public void resize(int width, int height) {

    }


    @Override
    public void pause() {
////        paused=true;
        keeyPlaying();
    }

    @Override
    public void resume() {
////        paused=false;
        setKeepPlay(false);
    }

    private Thread keepThread=null;
    private boolean keepPlay=false;
    public void keeyPlaying(){
        if(keepPlay){
            keepPlay=false;
            waitKeepEnd();
        }else if(null!=keepThread){
            waitKeepEnd();
        }
        keepPlay=true;
        if(null==keepThread) {
            keepThread = new Thread() {//线程操作
                public void run() {
                    boolean firstTime=true;
                    while (keepPlay) {
//                        if(null!=playingMusic&&!playingMusic.isPlaying()) {
//                            if(firstTime) {
//                                playingMusic.play();
//                                firstTime=false;
//                            }else if(null!=onComplete){
//                                onComplete.onCompletion(playingMusic);
//                            }else{
//                                playingMusic.play();
//                            }
////                            keepPlay=false;
////                            AudioManager.this.keepThread=null;
////                            break;
//                        }
                        handleControllerInput();
                        try {
                            Thread.sleep(16);//每隔一秒刷新一次
                        } catch (Exception e) {
                        }
                    }
                    keepThread=null;
                }
            };
            keepThread.start();
        }
    }
    /**
     * screen.resume时用
     * @param keepPlay
     */
    public void setKeepPlay(boolean keepPlay) {
        this.keepPlay = keepPlay;
    }
    private int waitCount=0;
    private int waitDelta=1000;
    private void waitKeepEnd(){
        waitCount=0;
        while(null!=keepThread){
            try {
                if(5000<waitCount){
//                    keepThread=null;
                    break;
                }
                Thread.sleep(waitDelta);
                waitCount+=waitDelta;
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }

    private void handleControllerInput() {
//        // 轮询所有已连接的手柄
//        for (int i = 0; i < 4; i++) { // 假设检查前4个手柄位
//            if (Gdx.input.isKeyPressed(com.badlogic.gdx.Input.Keys.BUTTON_A + i)) { // 简化处理，实际应使用Controllers API
//                // 当检测到按键按下时，触发对应虚拟按钮的点击
//                FloatingButton targetButton = keyMapping.get(i);
//                if (targetButton != null) {
////                    // 调用Android层的方法，通过无障碍服务执行点击
////                    ((YourAndroidLauncher) Gdx.app.getApplicationListener()).simulateTap(
////                            targetButton.bounds.x + targetButton.bounds.width / 2,
////                            targetButton.bounds.y + targetButton.bounds.height / 2
////                    );
//
//                    float centerX = targetButton.bounds.x + targetButton.bounds.width / 2;
//                    float centerY = targetButton.bounds.y + targetButton.bounds.height / 2;
//
////                    // 调用Android层进行跨应用触摸模拟
////                    ((AndroidLauncher) Gdx.app.getApplicationListener())
////                            .simulateTouchFromGdx(centerX, centerY, MotionEvent.ACTION_DOWN);
//
////                    // 转换坐标：LibGDX坐标 -> Android屏幕坐标
////                    float screenX = centerX;
////                    float screenY = getResources().getDisplayMetrics().heightPixels - y;
////
////                    touchService.simulateTouch(screenX, screenY, action, 50);
//                    game.getTouchSimulate().simulateTouchFromGdx(centerX, centerY//, MotionEvent.ACTION_DOWN
//                    );
//
//                }
//            }
//        }

        if(gamepad.isCROSS()||gamepad.isTRIANGLE()){
            FloatingButton targetButton = keyMapping.get(XBoxKey.CROSS.ordinal());
            float centerX = targetButton.bounds.x + targetButton.bounds.width / 2;
            float centerY = targetButton.bounds.y + targetButton.bounds.height / 2;

//                    // 调用Android层进行跨应用触摸模拟
//                    ((AndroidLauncher) Gdx.app.getApplicationListener())
//                            .simulateTouchFromGdx(centerX, centerY, MotionEvent.ACTION_DOWN);

//                    // 转换坐标：LibGDX坐标 -> Android屏幕坐标
//                    float screenX = centerX;
//                    float screenY = getResources().getDisplayMetrics().heightPixels - y;
//
//                    touchService.simulateTouch(screenX, screenY, action, 50);
            game.getTouchSimulate().simulateTouchFromGdx(centerX, centerY//, MotionEvent.ACTION_DOWN
            );
        }
    }

    private void drawButtons() {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        for (FloatingButton button : virtualButtons) {
            shapeRenderer.setColor(0.5f, 0.5f, 1, 0.6f);
            shapeRenderer.rect(button.bounds.x, button.bounds.y, button.bounds.width, button.bounds.height);
        }
        shapeRenderer.end();
    }

    // ... 其他Screen方法 (show, resize, pause, resume, hide, dispose) ...
    // ... 触摸事件处理 (touchDown, touchDragged, touchUp) 用于拖动按钮 ...

    static class FloatingButton {
        String label;
        Rectangle bounds;
        public FloatingButton(String label, float x, float y, float width, float height) {
            this.label = label;
            this.bounds = new Rectangle(x, y, width, height);
        }
    }
}