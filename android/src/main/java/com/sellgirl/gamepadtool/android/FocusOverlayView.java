package com.sellgirl.gamepadtool.android;
import android.content.Context;
import android.graphics.Rect;
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

import com.sellgirl.gamepadtool.android.model.ButtonInfo;
import com.sellgirl.sgJavaHelper.SGDate;
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
{
    private String tag="FocusOverlayView";
    private Paint buttonPaint;
    private Paint textPaint;
    public List<ButtonInfo> buttons;
    private List<ButtonInfo> toolButtons;
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

    public FocusOverlayView(Context context,
                            //float x, float y,
                            WindowManager windowManager, WindowManager.LayoutParams params) {
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

        setupFocus();

        init();
    }

    private void setupFocus() {
        // 关键步骤1：设置为可获取焦点
        setFocusable(true);
        setFocusableInTouchMode(true); // 在触摸模式下也可聚焦

        // 关键步骤2：请求焦点
        requestFocus();

        // 关键步骤3：设置按键监听器
        setOnKeyListener(new OnKeyListener() {
            @Override
          public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (isGamepadButton(keyCode)) {
                    int action = event.getAction();

                    if (action == KeyEvent.ACTION_DOWN && callback != null) {
                        callback.onButtonPressed(keyCode, event.getDeviceId());
                        return true; // 消费事件
                    } else if (action == KeyEvent.ACTION_UP && callback != null) {
                        callback.onButtonReleased(keyCode, event.getDeviceId());
                        return true; // 消费事件
                    }
                }
                return false;
            }
        });
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
        loadButtonPositions();
    }
    private void loadButtonPositions() {
        SharedPreferences prefs = getContext().getSharedPreferences("button_positions", Context.MODE_PRIVATE);

        // 从SharedPreferences加载按钮位置
        buttons.add(new ButtonInfo(
                "A",
                prefs.getFloat("buttonA_x", 100),
                prefs.getFloat("buttonA_y", 100),
                40
        ));

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
        // 添加更多按钮...
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
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        Log.d("FocusOverlay", "Key down: " + keyCode + ", source: " + event.getSource());

        // 检查是否是游戏手柄按钮
        if (isGamepadButton(keyCode)) {
            // 拦截这个事件，阻止系统默认行为
            if (callback != null) {
                callback.onButtonPressed(keyCode, event.getDeviceId());
            }

            // 返回 true 表示我们已经消费了这个事件，系统不会处理
            return true;
        }

        // 对于非游戏按钮，让系统处理
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        Log.d("FocusOverlay", "Key up: " + keyCode);

        if (isGamepadButton(keyCode)) {
            // 拦截释放事件
            if (callback != null) {
                callback.onButtonReleased(keyCode, event.getDeviceId());
            }
            return true;
        }

        return super.onKeyUp(keyCode, event);
    }

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
        ButtonOverlayView.printEvent(tag,event);
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
}