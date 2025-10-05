//package com.sellgirl.gamepadtool.android;
//
//// ControllerMappingScreen.java
//
//import com.badlogic.gdx.Gdx;
//import com.badlogic.gdx.Screen;
//import com.badlogic.gdx.graphics.GL20;
//import com.badlogic.gdx.graphics.g2d.SpriteBatch;
//import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
//import com.badlogic.gdx.math.Rectangle;
//import com.badlogic.gdx.utils.Array;
//import com.badlogic.gdx.utils.ObjectMap;
//
//public class ControllerMappingScreen implements Screen {
//
//    private ShapeRenderer shapeRenderer;
//    private SpriteBatch batch;
//    private Array<FloatingButton> virtualButtons;
//    private ObjectMap<Integer, FloatingButton> keyMapping; // 映射：手柄按键 -> 虚拟按钮
//
//    // 用于拖动
//    private FloatingButton draggedButton;
//
//    public ControllerMappingScreen() {
//        shapeRenderer = new ShapeRenderer();
//        batch = new SpriteBatch();
//        virtualButtons = new Array<>();
//        keyMapping = new ObjectMap<>();
//
//        createDefaultButtons();
//        setupDefaultKeyMapping();
//    }
//
//    private void createDefaultButtons() {
//        float screenWidth = Gdx.graphics.getWidth();
//        float screenHeight = Gdx.graphics.getHeight();
//
//        virtualButtons.add(new FloatingButton("A", screenWidth - 150, 200, 80, 80));
//        virtualButtons.add(new FloatingButton("B", screenWidth - 250, 200, 80, 80));
//        // 添加更多按钮...
//    }
//
//    private void setupDefaultKeyMapping() {
//        // 映射 libGDX 的控制器按键常量到虚拟按钮
//        // 您需要根据实际手柄测试这些键值
//        keyMapping.put(0, virtualButtons.get(0)); // 例如，A按钮
//        keyMapping.put(1, virtualButtons.get(1)); // 例如，B按钮
//    }
//
//    @Override
//    public void render(float delta) {
//        Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 0.3f); // 半透明背景
//        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
//
//        handleControllerInput();
//        drawButtons();
//    }
//
//    private void handleControllerInput() {
//        // 轮询所有已连接的手柄
//        for (int i = 0; i < 4; i++) { // 假设检查前4个手柄位
//            if (Gdx.input.isKeyPressed(com.badlogic.gdx.Input.Keys.BUTTON_A + i)) { // 简化处理，实际应使用Controllers API
//                // 当检测到按键按下时，触发对应虚拟按钮的点击
//                FloatingButton targetButton = keyMapping.get(i);
//                if (targetButton != null) {
//                    // 调用Android层的方法，通过无障碍服务执行点击
//                    ((YourAndroidLauncher) Gdx.app.getApplicationListener()).simulateTap(
//                            targetButton.bounds.x + targetButton.bounds.width / 2,
//                            targetButton.bounds.y + targetButton.bounds.height / 2
//                    );
//                }
//            }
//        }
//    }
//
//    private void drawButtons() {
//        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
//        for (FloatingButton button : virtualButtons) {
//            shapeRenderer.setColor(0.5f, 0.5f, 1, 0.6f);
//            shapeRenderer.rect(button.bounds.x, button.bounds.y, button.bounds.width, button.bounds.height);
//        }
//        shapeRenderer.end();
//    }
//
//    // ... 其他Screen方法 (show, resize, pause, resume, hide, dispose) ...
//    // ... 触摸事件处理 (touchDown, touchDragged, touchUp) 用于拖动按钮 ...
//
//    static class FloatingButton {
//        String label;
//        Rectangle bounds;
//        public FloatingButton(String label, float x, float y, float width, float height) {
//            this.label = label;
//            this.bounds = new Rectangle(x, y, width, height);
//        }
//    }
//}