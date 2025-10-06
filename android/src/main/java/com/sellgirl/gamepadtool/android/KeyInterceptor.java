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
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
/**
 * 按键拦截管理器 - 提供更精细的拦截控制
 */
public class KeyInterceptor {
    private static KeyInterceptor instance;
    private Map<Integer, InterceptConfig> interceptConfigs = new HashMap<>();

    public static KeyInterceptor getInstance() {
        if (instance == null) {
            instance = new KeyInterceptor();
        }
        return instance;
    }

    private KeyInterceptor() {
        setupDefaultIntercepts();
    }

    private void setupDefaultIntercepts() {
        // 配置需要拦截的按键及其行为
        interceptConfigs.put(KeyEvent.KEYCODE_BUTTON_B, new InterceptConfig(
                "B",
                true,  // 拦截按下
                true,  // 拦截释放
                "back_intercept" // 拦截类型
        ));

        interceptConfigs.put(KeyEvent.KEYCODE_BUTTON_A, new InterceptConfig(
                "A", true, true, "action_intercept"
        ));

        // 可以配置部分拦截，比如只拦截按下不拦截释放
        interceptConfigs.put(KeyEvent.KEYCODE_BUTTON_SELECT, new InterceptConfig(
                "Select", true, false, "menu_intercept"
        ));
    }

    /**
     * 检查是否应该拦截某个按键事件
     */
    public boolean shouldIntercept(int keyCode, int action) {
        InterceptConfig config = interceptConfigs.get(keyCode);
        if (config == null) return false;

        if (action == KeyEvent.ACTION_DOWN) {
            return config.interceptPress;
        } else if (action == KeyEvent.ACTION_UP) {
            return config.interceptRelease;
        }

        return false;
    }

    /**
     * 获取拦截配置
     */
    public InterceptConfig getInterceptConfig(int keyCode) {
        return interceptConfigs.get(keyCode);
    }

    /**
     * 动态添加拦截配置
     */
    public void addInterceptConfig(int keyCode, InterceptConfig config) {
        interceptConfigs.put(keyCode, config);
    }

    /**
     * 拦截配置类
     */
    public static class InterceptConfig {
        public String buttonName;
        public boolean interceptPress;
        public boolean interceptRelease;
        public String interceptType;

        public InterceptConfig(String buttonName, boolean interceptPress,
                               boolean interceptRelease, String interceptType) {
            this.buttonName = buttonName;
            this.interceptPress = interceptPress;
            this.interceptRelease = interceptRelease;
            this.interceptType = interceptType;
        }
    }
}