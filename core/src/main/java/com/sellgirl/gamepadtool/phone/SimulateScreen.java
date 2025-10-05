//package com.sellgirl.gamepadtool.phone;
//
//import com.badlogic.gdx.Gdx;
//import com.badlogic.gdx.InputProcessor;
//import com.badlogic.gdx.Screen;
//import com.badlogic.gdx.graphics.GL20;
//import com.badlogic.gdx.graphics.OrthographicCamera;
//import com.badlogic.gdx.graphics.Texture;
//import com.badlogic.gdx.graphics.g2d.SpriteBatch;
//import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
//import com.badlogic.gdx.math.Rectangle;
//import com.badlogic.gdx.math.Vector2;
//import com.badlogic.gdx.math.Vector3;
//import com.badlogic.gdx.utils.Array;
//import com.badlogic.gdx.utils.ObjectMap;
//
////此方案可能只可以模拟在本安卓线程
//public class SimulateScreen implements Screen, InputProcessor {
//
//    private OrthographicCamera camera;
//    private SpriteBatch batch;
//    private ShapeRenderer shapeRenderer;
//
//    // 悬浮按钮列表
//    private Array<FloatingButton> floatingButtons;
//    // 手柄按钮到悬浮按钮的映射
//    private ObjectMap<Integer, FloatingButton> controllerMapping;
//
//    // 当前被拖动的按钮
//    private FloatingButton draggedButton;
//    private Vector2 dragOffset;
//
//    // 按钮纹理（可以使用简单的形状代替）
//    private Texture buttonTexture;
//
//    private InputManagerInjector inputInjector;
//    private Array<FloatingButton> floatingButtons;
//    private ObjectMap<Integer, FloatingButton> controllerMapping;
//
//    public SimulateScreen() {
//        camera = new OrthographicCamera();
//        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
//
//        batch = new SpriteBatch();
//        shapeRenderer = new ShapeRenderer();
//
//        floatingButtons = new Array<>();
//        controllerMapping = new ObjectMap<>();
//        dragOffset = new Vector2();
//
//        // 创建默认的悬浮按钮
//        createDefaultButtons();
//
//        // 设置输入处理器
//        Gdx.input.setInputProcessor(this);
//    }
//
//    private void createDefaultButtons() {
//        float screenWidth = Gdx.graphics.getWidth();
//        float screenHeight = Gdx.graphics.getHeight();
//
//        // 创建几个默认的悬浮按钮
//        FloatingButton fireButton = new FloatingButton("FIRE",
//                screenWidth * 0.8f, screenHeight * 0.2f, 80, 80);
//        floatingButtons.add(fireButton);
//
//        FloatingButton jumpButton = new FloatingButton("JUMP",
//                screenWidth * 0.7f, screenHeight * 0.3f, 80, 80);
//        floatingButtons.add(jumpButton);
//
//        FloatingButton reloadButton = new FloatingButton("RELOAD",
//                screenWidth * 0.9f, screenHeight * 0.4f, 80, 80);
//        floatingButtons.add(reloadButton);
//
//        // 设置默认的手柄映射（这里使用libGDX的按钮常量）
//        // 实际使用时需要根据您的手柄进行调整
//        controllerMapping.put(0, fireButton);     // A按钮
//        controllerMapping.put(1, jumpButton);     // B按钮
//        controllerMapping.put(2, reloadButton);   // X按钮
//    }
//
//    @Override
//    public void render(float delta) {
//        // 清屏
//        Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 0.5f); // 半透明背景
//        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
//
//        // 启用混合以实现半透明效果
//        Gdx.gl.glEnable(GL20.GL_BLEND);
//        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
//
//        // 绘制悬浮按钮
//        batch.setProjectionMatrix(camera.combined);
//        batch.begin();
//
//        for (FloatingButton button : floatingButtons) {
//            drawFloatingButton(button);
//        }
//
//        batch.end();
//
//        // 绘制连接线（用于显示手柄映射）
//        shapeRenderer.setProjectionMatrix(camera.combined);
//        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
//        shapeRenderer.setColor(1, 1, 0, 0.5f);
//
//        // 这里可以绘制手柄按钮和悬浮按钮之间的连线
//        // 实际实现需要根据您的UI设计来定
//
//        shapeRenderer.end();
//
//        Gdx.gl.glDisable(GL20.GL_BLEND);
//    }
//
//    private void drawFloatingButton(FloatingButton button) {
//        // 简单的圆形按钮绘制
//        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
//
//        if (button.isPressed()) {
//            shapeRenderer.setColor(1, 0, 0, 0.7f); // 按下时红色
//        } else {
//            shapeRenderer.setColor(0, 0.5f, 1, 0.7f); // 正常时蓝色
//        }
//
//        shapeRenderer.circle(button.getX() + button.getWidth() / 2,
//                button.getY() + button.getHeight() / 2,
//                button.getWidth() / 2);
//
//        shapeRenderer.end();
//
//        // 绘制按钮文字
//        batch.begin();
//        // 这里可以使用BitmapFont绘制按钮标签
//        batch.end();
//    }
//
//    /**
//     * 处理手柄按钮按下事件
//     */
//    public void onControllerButtonDown(int buttonCode) {
//        FloatingButton targetButton = controllerMapping.get(buttonCode);
//        if (targetButton != null) {
//            // 模拟触摸按下事件
//            simulateTouchDown(targetButton);
//        }
//    }
//
//    /**
//     * 处理手柄按钮释放事件
//     */
//    public void onControllerButtonUp(int buttonCode) {
//        FloatingButton targetButton = controllerMapping.get(buttonCode);
//        if (targetButton != null) {
//            // 模拟触摸释放事件
//            simulateTouchUp(targetButton);
//        }
//    }
//
//    /**
//     * 模拟触摸按下事件
//     */
//    private void simulateTouchDown(FloatingButton button) {
//        button.setPressed(true);
//
//        // 获取按钮中心坐标
//        float touchX = button.getX() + button.getWidth() / 2;
//        float touchY = button.getY() + button.getHeight() / 2;
//
//        // 这里应该调用Android的触摸事件模拟
//        // 由于libGDX本身不支持跨应用模拟，需要在Android模块中实现
//        simulateAndroidTouch(touchX, touchY, true);
//
//        Gdx.app.log("SimulateScreen", "Simulated touch DOWN at: " + touchX + ", " + touchY);
//    }
//
//    /**
//     * 模拟触摸释放事件
//     */
//    private void simulateTouchUp(FloatingButton button) {
//        button.setPressed(false);
//
//        float touchX = button.getX() + button.getWidth() / 2;
//        float touchY = button.getY() + button.getHeight() / 2;
//
//        simulateAndroidTouch(touchX, touchY, false);
//
//        Gdx.app.log("SimulateScreen", "Simulated touch UP at: " + touchX + ", " + touchY);
//    }
//
//    /**
//     * 调用Android原生触摸模拟
//     * 需要在Android模块中实现具体的触摸事件注入
//     */
//    private native void simulateAndroidTouch(float x, float y, boolean isDown);
//
//    // InputProcessor 方法实现 - 处理触摸拖动
//    @Override
//    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
//        Vector3 worldCoords = camera.unproject(new Vector3(screenX, screenY, 0));
//
//        for (FloatingButton fb : floatingButtons) {
//            if (fb.contains(worldCoords.x, worldCoords.y)) {
//                draggedButton = fb;
//                dragOffset.set(worldCoords.x - fb.getX(), worldCoords.y - fb.getY());
//                return true;
//            }
//        }
//        return false;
//    }
//
//    @Override
//    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
//        draggedButton = null;
//        return false;
//    }
//
//    @Override
//    public boolean touchCancelled(int screenX, int screenY, int pointer, int button) {
//        return false;
//    }
//
//    @Override
//    public boolean touchDragged(int screenX, int screenY, int pointer) {
//        if (draggedButton != null) {
//            Vector3 worldCoords = camera.unproject(new Vector3(screenX, screenY, 0));
//            draggedButton.setPosition(
//                    worldCoords.x - dragOffset.x,
//                    worldCoords.y - dragOffset.y
//            );
//            return true;
//        }
//        return false;
//    }
//
//    // 其他必要的方法
//    @Override
//    public void show() {}
//
//    @Override
//    public void resize(int width, int height) {
//        camera.setToOrtho(false, width, height);
//    }
//
//    @Override
//    public void pause() {}
//
//    @Override
//    public void resume() {}
//
//    @Override
//    public void hide() {}
//
//    @Override
//    public void dispose() {
//        batch.dispose();
//        shapeRenderer.dispose();
//        if (buttonTexture != null) {
//            buttonTexture.dispose();
//        }
//    }
//
//    // 其他InputProcessor方法
//    @Override
//    public boolean keyDown(int keycode) { return false; }
//
//    @Override
//    public boolean keyUp(int keycode) { return false; }
//
//    @Override
//    public boolean keyTyped(char character) { return false; }
//
//    @Override
//    public boolean mouseMoved(int screenX, int screenY) { return false; }
//
//    @Override
//    public boolean scrolled(float amountX, float amountY) { return false; }
//
//    /**
//     * 悬浮按钮类
//     */
//    public static class FloatingButton {
//        private String label;
//        private float x, y;
//        private float width, height;
//        private boolean pressed;
//
//        public FloatingButton(String label, float x, float y, float width, float height) {
//            this.label = label;
//            this.x = x;
//            this.y = y;
//            this.width = width;
//            this.height = height;
//            this.pressed = false;
//        }
//
//        public boolean contains(float pointX, float pointY) {
//            return pointX >= x && pointX <= x + width &&
//                    pointY >= y && pointY <= y + height;
//        }
//
//        // Getter和Setter方法
//        public float getX() { return x; }
//        public float getY() { return y; }
//        public float getWidth() { return width; }
//        public float getHeight() { return height; }
//        public boolean isPressed() { return pressed; }
//        public void setPressed(boolean pressed) { this.pressed = pressed; }
//        public void setPosition(float x, float y) {
//            this.x = x;
//            this.y = y;
//        }
//        public String getLabel() { return label; }
//    }
//}