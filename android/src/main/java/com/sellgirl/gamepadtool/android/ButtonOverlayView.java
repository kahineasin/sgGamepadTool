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

import java.util.ArrayList;
import java.util.List;


// ButtonOverlayView.java - 专门用于绘制按钮的自定义View
public class ButtonOverlayView extends View {
    private Paint buttonPaint;
    private Paint textPaint;
    private List<ButtonInfo> buttons;

    public ButtonOverlayView(Context context) {
        super(context);
        init();
    }

    public ButtonOverlayView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
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
    }

    private void loadButtonPositions() {
        SharedPreferences prefs = getContext().getSharedPreferences("button_positions", Context.MODE_PRIVATE);

        // 从SharedPreferences加载按钮位置
        buttons.add(new ButtonInfo(
                "A",
                prefs.getFloat("button1_x", 100),
                prefs.getFloat("button1_y", 100),
                40
        ));

        // 添加更多按钮...
    }

    public void updateButtonPosition(String buttonId, float x, float y) {
        for (ButtonInfo button : buttons) {
            if (button.label.equals(buttonId)) {
                button.x = x;
                button.y = y;
                break;
            }
        }
        invalidate(); // 请求重绘
    }

    // 按钮信息类
    private static class ButtonInfo {
        String label;
        float x, y;
        float radius;

        ButtonInfo(String label, float x, float y, float radius) {
            this.label = label;
            this.x = x;
            this.y = y;
            this.radius = radius;
        }
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        Log.d("FocusOverlay", "Key down: " + keyCode + ", source: " + event.getSource());
        return true;
    }
    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        Log.d("FocusOverlay", "Key up: " + keyCode);
        return true;
    }
}