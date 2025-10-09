package com.sellgirl.gamepadtool.android;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.PixelFormat;
import android.net.Uri;
import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import android.content.Intent;

import android.os.Build;
import android.provider.Settings;
import android.view.KeyEvent;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.FileProvider;

import java.io.File;
import com.badlogic.gdx.Gdx;

import com.sellgirl.gamepadtool.AndroidGamepadTool;
import com.sellgirl.gamepadtool.phone.ISGTouchSimulate;

/** Launches the Android application. */
public class AndroidLauncher extends AndroidApplication {
//    private ISGTouchSimulate touchSimulate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //这句一定要在前面，否则super.onResume里面input属性null报错
        initializeLibGDX();
        // 检查并请求悬浮窗权限
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!Settings.canDrawOverlays(this)) {
//                requestOverlayPermission();
                showOverlayPermissionExplanation();
                return; // 等待权限授予后再初始化
            }
        }

        // 检查并请求通知权限
        checkAndRequestNotificationPermission();

        // 检查并请求通知权限
        checkAndRequestSimulatePermission();

        // 启动服务
        startGamepadServices();

//        Context context= this.getContext();
//        // 在你的 Activity 中启动服务
//        Intent serviceIntent = new Intent(this, MusicPlayerService.class);
//        serviceIntent.setAction("ACTION_PLAY");
//// 对于 Android 8.0+ 的兼容写法
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            context.startForegroundService(serviceIntent);
//        } else {
//            context.startService(serviceIntent);
//        }

//        playMusic();
    }

    // 在您的 Activity 中
    private void playMusic() {
        Intent serviceIntent = new Intent(this, MusicPlayerService.class);
        serviceIntent.setAction(MusicPlayerService.ACTION_PLAY);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForegroundService(serviceIntent);
        } else {
            startService(serviceIntent);
        }
    }

    private void pauseMusic() {
        Intent serviceIntent = new Intent(this, MusicPlayerService.class);
        serviceIntent.setAction(MusicPlayerService.ACTION_PAUSE);
        startService(serviceIntent);
    }

    private void stopMusic() {
        Intent serviceIntent = new Intent(this, MusicPlayerService.class);
        serviceIntent.setAction(MusicPlayerService.ACTION_STOP);
        startService(serviceIntent);
    }
    //--------------------运行时请求权限------------------------
    private static final int NOTIFICATION_PERMISSION_REQUEST_CODE = 1001;
    private void checkAndRequestNotificationPermission() {
        if (!NotificationPermissionHelper.areNotificationsEnabled(this)) {
            // 显示解释为什么需要这个权限（可选但推荐）
            showNotificationPermissionExplanation();
        } else {
            // 已经有权限，可以启动服务
            startMusicServiceIfNeeded();
        }
    }

    private void showNotificationPermissionExplanation() {
        new AlertDialog.Builder(this)
                .setTitle("需要通知权限")
                .setMessage("音乐播放器需要通知权限来显示播放控制界面，并在后台持续播放音乐。")
                .setPositiveButton("授予权限", (dialog, which) -> {
                    // 请求权限
                    NotificationPermissionHelper.requestNotificationPermission(
                            AndroidLauncher.this,
                            NOTIFICATION_PERMISSION_REQUEST_CODE
                    );
                })
                .setNegativeButton("取消", null)
                .show();
    }

    // 处理权限请求结果
    @Override
    public void onRequestPermissionsResult(
            int requestCode,
            @NonNull String[] permissions,
            @NonNull int[] grantResults
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (NotificationPermissionHelper.handlePermissionResult(
                requestCode,
                grantResults,
                NOTIFICATION_PERMISSION_REQUEST_CODE
        )) {
            // 权限已授予，启动服务
            startMusicServiceIfNeeded();
        } else {
            // 权限被拒绝，可以提示用户或提供备选方案
            Toast.makeText(
                    this,
                    "通知权限被拒绝，音乐播放可能无法在后台正常工作",
                    Toast.LENGTH_LONG
            ).show();

            // 即使没有权限，也可以尝试启动服务（但通知可能不会显示）
            startMusicServiceIfNeeded();
        }
    }

    private void startMusicServiceIfNeeded() {
        // 你的启动服务代码
        Intent serviceIntent = new Intent(this, MusicPlayerService.class);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForegroundService(serviceIntent);
        } else {
            startService(serviceIntent);
        }
    }
    //--------------------模拟触屏---------------------
    private TouchSimulationService touchService;

    // 供LibGDX调用的方法
    public void simulateTouchFromGdx(final float x, final float y, final int action) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (touchService != null) {
                    // 转换坐标：LibGDX坐标 -> Android屏幕坐标
                    float screenX = x;
                    float screenY = getResources().getDisplayMetrics().heightPixels - y;

                    touchService.simulateTouch(screenX, screenY, action, 50);
                } else {
//                    // 提示用户开启无障碍服务
//                    showAccessibilityPrompt();

                }
            }
        });
    }

    public void setTouchService(TouchSimulationService service) {
        this.touchService = service;
    }
    private void checkAndRequestSimulatePermission() {
        if (!TouchSimulationService.areNotificationsEnabled(this)) {
            // 显示解释为什么需要这个权限（可选但推荐）
            showNotificationPermissionExplanation();
        } else {
            // 已经有权限，可以启动服务
            startSimulateServiceIfNeeded();
        }
    }
    private void startSimulateServiceIfNeeded() {
//        // 你的启动服务代码
//        Intent serviceIntent = new Intent(this, MusicPlayerService.class);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            startForegroundService(serviceIntent);
//        } else {
//            startService(serviceIntent);
//        }

        // 提示用户开启无障碍服务
        Intent intent = new Intent(android.provider.Settings.ACTION_ACCESSIBILITY_SETTINGS);
        startActivity(intent);
    }

    private void showSimulatePermissionExplanation() {
        new AlertDialog.Builder(this)
                .setTitle("需要通知权限")
                .setMessage("音乐播放器需要通知权限来显示播放控制界面，并在后台持续播放音乐。")
                .setPositiveButton("授予权限", (dialog, which) -> {
                    // 请求权限
                    NotificationPermissionHelper.requestNotificationPermission(
                            AndroidLauncher.this,
                            NOTIFICATION_PERMISSION_REQUEST_CODE
                    );
                })
                .setNegativeButton("取消", null)
                .show();
    }
    //--------------------模拟触屏 end---------------------

    //--------------------悬浮按钮---------------------
    private static final int REQUEST_OVERLAY_PERMISSION = 1001;
    private void requestOverlayPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                    Uri.parse("package:" + getPackageName()));
            startActivityForResult(intent, REQUEST_OVERLAY_PERMISSION);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_OVERLAY_PERMISSION) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (Settings.canDrawOverlays(this)) {
                    initializeLibGDX();
                } else {
                    // 权限被拒绝，提示用户
                    Toast.makeText(this, "需要悬浮窗权限才能显示按钮", Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    private void initializeLibGDX() {
//        // 初始化您的libGDX应用
//        AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
//        initialize(new YourLibGDXGame(), config);


        AndroidApplicationConfiguration configuration = new AndroidApplicationConfiguration();
        configuration.a = 8; // default is 0
        configuration.useImmersiveMode = true; // Recommended, but not required.

        AndroidGamepadTool player= new AndroidGamepadTool() {
            @Override
            public void startPlayService() {
                playMusic();
            }

            @Override
            public void pausePlayService() {
                pauseMusic();
            }

            @Override
            public void stopPlayService() {
                stopMusic();
            }

            @Override
            public void updateApk(String url) {

                Intent intent = new Intent(Intent.ACTION_VIEW);
                File file= Gdx.files.external(url).file();
                String authority = AndroidLauncher.this.getPackageName() + ".fileprovider";
                Uri apkUri = FileProvider.getUriForFile(AndroidLauncher.this, authority, file);
                intent.setDataAndType(apkUri, "application/vnd.android.package-archive");
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }

            @Override
            public ISGTouchSimulate getTouchSimulate() {
                if(null!=touchService){}
                else {
//
//                    // 提示用户开启无障碍服务
//                    Intent intent = new Intent(android.provider.Settings.ACTION_ACCESSIBILITY_SETTINGS);
//                    startActivity(intent);
//                    touchService = new TouchSimulationService();
                    if(null==TouchSimulationService.getInstance()){
                        // 提示用户开启无障碍服务
                        Intent intent = new Intent(android.provider.Settings.ACTION_ACCESSIBILITY_SETTINGS);
                        startActivity(intent);
                        if(null!=TouchSimulationService.getInstance()){
                            touchService = TouchSimulationService.getInstance();
                        }
                    }else{
                        touchService = TouchSimulationService.getInstance();
                    }
                }
                return touchService;
            }
            @Override
            public void dispose(){
                Intent serviceIntent = new Intent(AndroidLauncher.this, OverlayService.class);
                stopService(serviceIntent);
            }
        };

//        initialize(player, configuration);

        View view = initializeForView(player, configuration);
        if (view instanceof SurfaceView) {
            SurfaceView sv = (SurfaceView) view;
            sv.setZOrderOnTop(true);
            sv.getHolder().setFormat(PixelFormat.TRANSLUCENT);
        }
        setContentView(view);
    }

    private void startOverlayService() {
        Intent serviceIntent = new Intent(this, OverlayService.class);
        startService(serviceIntent);
    }

    private void stopOverlayService() {
        Intent serviceIntent = new Intent(this, OverlayService.class);
        stopService(serviceIntent);
    }

    @Override
    protected void onPause() {
        super.onPause();
////        // 应用进入后台时启动悬浮窗
        startOverlayService();
    }

    @Override
    protected void onResume() {

        super.onResume();
//        if(null==input){
//            return;
//        }
////        if(null!=input){
//            super.onResume();
////        }

//        // 应用回到前台时停止悬浮窗（可选，根据需求）
        stopOverlayService();
//        startOverlayService();
    }
    // 在请求权限前显示说明
    private void showOverlayPermissionExplanation() {
        new AlertDialog.Builder(this)
                .setTitle("需要悬浮窗权限")
                .setMessage("为了在其他应用上方显示游戏按钮，需要您授权\"显示在其他应用上层\"的权限。")
                .setPositiveButton("去设置", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        requestOverlayPermission();
                    }
                })
                .setNegativeButton("取消", null)
                .show();
    }
    //--------------------悬浮按钮 end---------------------


    //--------------------手柄事件---------------------
    private void startGamepadServices() {

        // 启动游戏手柄服务
        Intent gamepadIntent = new Intent(this, GamepadService.class);
        startService(gamepadIntent);
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_VOLUME_DOWN) {
            // 处理音量下键按下事件
            return true; // 如果已经处理了事件，返回 true
        }
        return super.onKeyDown(keyCode, event); // 否则，让系统继续处理其他按键事件
    }
    //--------------------手柄事件 end---------------------
}
