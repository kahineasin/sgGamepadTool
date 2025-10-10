package com.sellgirl.gamepadtool.android;

import android.content.Context;
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

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.sellgirl.gamepadtool.android.model.ButtonInfo;

import java.util.ArrayList;
import java.util.List;
import com.sellgirl.sgJavaHelper.time.Waiter;

// ButtonOverlayView.java - 专门用于绘制按钮的自定义View

/**
 *按钮位置设定，需要保证gamepad按钮事件和touch本窗的事件
 */
public class ButtonOverlayView extends View {
    private String tag="ButtonOverlayView";
    private Paint buttonPaint;
    private Paint textPaint;
    private List<ButtonInfo> buttons;
    private List<ButtonInfo> toolButtons;
    public List<ButtonInfo> sticks;

    private OverlayService service=null;

    private Waiter waiter=new Waiter(1);
    public ButtonOverlayView(Context context) {
        super(context);
//        setFocusable(true);
//        setFocusableInTouchMode(true);
//        requestFocus();
//        setClickable(true); // 根据需求设置

        setupFocus();
        init();

    }

//    public ButtonOverlayView(Context context, AttributeSet attrs) {
//        super(context, attrs);
//        init();
//    }

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

                //这里需要把手柄对应到view中的button todo
                if(KeyEvent.ACTION_UP==event.getAction()){
                    TouchSimulationService.getInstance().simulateTouchFromGdx(buttons.get(0).x,buttons.get(0).y);
                    return true;
                }
                return false;
            }
        });
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
    private void init() {
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
        for (ButtonInfo button : buttons) {
            // 绘制圆形按钮
            canvas.drawCircle(button.x, button.y, button.radius, buttonPaint);

            // 绘制按钮文字
            float textY = button.y - ((textPaint.descent() + textPaint.ascent()) / 2);
            canvas.drawText(button.label, button.x, textY, textPaint);
        }

        for (ButtonInfo button : toolButtons) {
            // 绘制圆形按钮
            canvas.drawCircle(button.x, button.y, button.radius, buttonPaint);

            // 绘制按钮文字
            float textY = button.y - ((textPaint.descent() + textPaint.ascent()) / 2);
            canvas.drawText(button.label, button.x, textY, textPaint);
        }
        for (ButtonInfo button : sticks) {
            // 绘制圆形按钮
            canvas.drawCircle(button.x, button.y, button.radius, buttonPaint);

            // 绘制按钮文字
            float textY = button.y - ((textPaint.descent() + textPaint.ascent()) / 2);
            canvas.drawText(button.label, button.x, textY, textPaint);
        }
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

        toolButtons.add(new ButtonInfo(
                "simulate",
                100,
                100,
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

    public void updateButtonPosition(String buttonId, float x, float y) {
        boolean found=false;
        for (ButtonInfo button : buttons) {
            if (button.label.equals(buttonId)) {
                button.x = x;
                button.y = y;
                found=true;
                break;
            }
        }
        if(!found){
            for (ButtonInfo button : sticks) {
                if (button.label.equals(buttonId)) {
                    button.x = x;
                    button.y = y;
                    found=true;
                    break;
                }
            }
        }
        invalidate(); // 请求重绘
    }

    private void saveButtonPositions(String buttonId, float x, float y) {
        SharedPreferences prefs = getContext().getSharedPreferences("button_positions", Context.MODE_PRIVATE);
        SharedPreferences.Editor edit=prefs.edit();
        boolean found=false;
        for (ButtonInfo button : buttons) {
            if (button.label.equals(buttonId)) {
                //buttonA_x
                edit.putFloat("button"+buttonId+"_x",x);
                edit.putFloat("button"+buttonId+"_y",y);
                edit.apply();
                found=true;
                break;
            }
        }
        if(!found){
            for (ButtonInfo button : sticks) {
                if (button.label.equals(buttonId)) {
                    //buttonA_x
//                    edit.putFloat("stick"+buttonId+"_x",x);
//                    edit.putFloat("stick"+buttonId+"_y",y);
                    edit.putFloat(buttonId+"_x",x);
                    edit.putFloat(buttonId+"_y",y);
                    edit.apply();
                    found=true;
                    break;
                }
            }
        }
    }
//    // 按钮信息类
//    private static class ButtonInfo {
//        String label;
//        float x, y;
//        float radius;
//
//        ButtonInfo(String label, float x, float y, float radius) {
//            this.label = label;
//            this.x = x;
//            this.y = y;
//            this.radius = radius;
//        }
//        public boolean isPosIn(float x,float y){
//            return this.x-radius<=x&&x<=this.x+radius
//                    &&this.y-radius<=y&&y<=this.y+radius;
//        }
//    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        Log.d("FocusOverlay", "Key down: " + keyCode + ", source: " + event.getSource());
        if(null!=TouchSimulationService.getInstance()) {

            return true;
        }
        return false;
    }
    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        Log.d("FocusOverlay", "Key up: " + keyCode);

        if(null!=TouchSimulationService.getInstance()) {
            //这里需要把手柄对应到view中的button todo
            if (KeyEvent.ACTION_UP == event.getAction()

            ) {
                TouchSimulationService.getInstance().simulateTouchFromGdx(buttons.get(0).x, buttons.get(0).y);

            }
            return true;
        }
        return false;
    }

    private boolean dragging=false;
    private String dragId="";
    public static void printEvent(String tag,MotionEvent event){
        Log.d(tag, "touch a:" + event.getAction()+" x:"+event.getX()+" y:"+event.getY()
        +" rx:"+event.getRawX()+" ry:"+event.getRawY()
        );
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //0 2 2 2 ... 1
        Log.d("ButtonOverlay", "touch: " + event.getAction());

        if(0==event.getAction()){
            for(ButtonInfo i: buttons){
                if(i.isPosIn(event.getX(),event.getY())){
                    dragging=true;
                    dragId=i.label;
                    return true;
                }
            }
            for(ButtonInfo i: sticks){
                if(i.isPosIn(event.getX(),event.getY())){
                    dragging=true;
                    dragId=i.label;
                    return true;
                }
            }
            for(ButtonInfo i: toolButtons){
                //暂时只有一个工具按钮
                if(i.isPosIn(event.getX(),event.getY())){

                    //报错com.sellgirl.gamepadtool.android.AndroidLauncher$2 cannot be cast to com.sellgirl.gamepadtool.android.AndroidLauncher
//                    AndroidLauncher app=((AndroidLauncher) Gdx.app.getApplicationListener());
//                    Intent serviceIntent = new Intent(app, OverlayService.class);
//                    serviceIntent.setAction(OverlayService.ACTION_SIMULATE);
//                    app.startService(serviceIntent);
//                    Gdx.app.postRunnable(new Runnable() {
//                        @Override
//                        public void run () {
//
//                            AndroidLauncher app=((AndroidLauncher) Gdx.app.getApplicationListener());
//                            Intent serviceIntent = new Intent(app, OverlayService.class);
//                            serviceIntent.setAction(OverlayService.ACTION_SIMULATE);
//                            app.startService(serviceIntent);
//                        }
//                    });
                    if(waiter.isOK()) {
                        service.removeButtonOverlay();
                        service.showSimulateOverlay();
                        return true;
                    }
                }
            }
        }else if(2==event.getAction()){
            if(dragging) {
                updateButtonPosition(dragId, event.getX(), event.getY());
                return true;
            }
        }else if(1==event.getAction()){
            if(dragging) {
                saveButtonPositions(dragId,event.getX(),event.getY());
                dragging = false;
                dragId = "";
                return true;
            }
        }

        // 不处理触摸事件，返回 false 让事件传递到下层
        return false;
    }

    public void setService(OverlayService service) {
        this.service = service;
    }
}