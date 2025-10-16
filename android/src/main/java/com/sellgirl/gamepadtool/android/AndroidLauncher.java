package com.sellgirl.gamepadtool.android;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.PixelFormat;
import android.net.Uri;
import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import android.content.Intent;

import android.os.Build;
import android.provider.Settings;
import android.util.Log;
import android.view.KeyEvent;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import java.io.File;
import com.badlogic.gdx.Gdx;

import com.sellgirl.gamepadtool.AndroidGamepadTool;
import com.sellgirl.gamepadtool.MainMenuScreen;
import com.sellgirl.gamepadtool.android.permission.PermissionManager;
import com.sellgirl.gamepadtool.android.permission.PermissionRequest;
import com.sellgirl.gamepadtool.android.simulate.TouchSimulationService;
import com.sellgirl.gamepadtool.phone.ISGTouchSimulate;

/** Launches the Android application.
 * 此版本可以利用手势类GestureDescription来模拟手柄按键和小摇杆，
 * 但当前遇到个问题，GestureDescription无法表示DOWN MOVE UP的过程，于
 * 是无法模拟虚拟摇杆一直按下的状态（在蛋仔派对中虽然能移动虚拟摇杆，却不能压住，表现为移动无效）
 * 暂时问题无解，可试试改为
 * */
public class AndroidLauncher extends AndroidApplication {
//    private static final int REQUEST_OVERLAY_PERMISSION = 1001;
//    private static final int REQUEST_CAMERA_PERMISSION = 1002;
//    private static final int REQUEST_LOCATION_PERMISSION = 1003;
//    private ISGTouchSimulate touchSimulate;
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//        //这句一定要在前面，否则super.onResume里面input属性null报错
////        initializeLibGDX();
//        initLibGdx(initializeTool());
//        // 检查并请求悬浮窗权限
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            if (!Settings.canDrawOverlays(this)) {
////                requestOverlayPermission();
//                showOverlayPermissionExplanation();
////                return; // 等待权限授予后再初始化
//            }
//        }
//
//        // 检查并请求通知权限
//        checkAndRequestNotificationPermission();
//
//        // 检查并请求通知权限
//        checkAndRequestSimulatePermission();
//
//    }
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == REQUEST_OVERLAY_PERMISSION) {
//            if(PermissionUtils.hasOverlayPermission(this)){
////                    startOverlay();//Overlay服务是onPause时起动的，这里就不启动了
//            } else {
//                // 权限被拒绝，提示用户
//                Toast.makeText(this, "需要悬浮窗权限才能显示按钮", Toast.LENGTH_LONG).show();
//            }
//        }
//    }
    // 在您的 Activity 中
    public void playMusic() {
        Intent serviceIntent = new Intent(this, MusicPlayerService.class);
        serviceIntent.setAction(MusicPlayerService.ACTION_PLAY);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForegroundService(serviceIntent);
        } else {
            startService(serviceIntent);
        }
    }

    public void pauseMusic() {
        Intent serviceIntent = new Intent(this, MusicPlayerService.class);
        serviceIntent.setAction(MusicPlayerService.ACTION_PAUSE);
        startService(serviceIntent);
    }

    public void stopMusic() {
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
                .setMessage("手柄映射工具需要在后台运行。")
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

//    // 处理权限请求结果
//    @Override
//    public void onRequestPermissionsResult(
//            int requestCode,
//            @NonNull String[] permissions,
//            @NonNull int[] grantResults
//    ) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//
//        if (NotificationPermissionHelper.handlePermissionResult(
//                requestCode,
//                grantResults,
//                NOTIFICATION_PERMISSION_REQUEST_CODE
//        )) {
//            // 权限已授予，启动服务
//            startMusicServiceIfNeeded();
//        } else {
//            // 权限被拒绝，可以提示用户或提供备选方案
//            Toast.makeText(
//                    this,
//                    "通知权限被拒绝，音乐播放可能无法在后台正常工作",
//                    Toast.LENGTH_LONG
//            ).show();
//
//            // 即使没有权限，也可以尝试启动服务（但通知可能不会显示）
//            startMusicServiceIfNeeded();
//        }
//    }

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
    public TouchSimulationService touchService;

//    // 供LibGDX调用的方法
//    public void simulateTouchFromGdx(final float x, final float y, final int action) {
//        runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                if (touchService != null) {
//                    // 转换坐标：LibGDX坐标 -> Android屏幕坐标
//                    float screenX = x;
//                    float screenY = getResources().getDisplayMetrics().heightPixels - y;
//
//                    touchService.simulateTouch(screenX, screenY, action, 50);
//                } else {
////                    // 提示用户开启无障碍服务
////                    showAccessibilityPrompt();
//
//                }
//            }
//        });
//    }

    public void setTouchService(TouchSimulationService service) {
        this.touchService = service;
    }
//    private void checkAndRequestSimulatePermission() {
//        if (!TouchSimulationService.areNotificationsEnabled(this)) {
////            // 显示解释为什么需要这个权限（可选但推荐）
////            showNotificationPermissionExplanation();
//            startSimulateServiceIfNeeded();
//        } else {
//            // 已经有权限，可以启动服务
////            startSimulateServiceIfNeeded();
//        }
//    }
//    private void startSimulateServiceIfNeeded() {
////        // 你的启动服务代码
////        Intent serviceIntent = new Intent(this, MusicPlayerService.class);
////        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
////            startForegroundService(serviceIntent);
////        } else {
////            startService(serviceIntent);
////        }
//
//        // 提示用户开启无障碍服务
//        Intent intent = new Intent(android.provider.Settings.ACTION_ACCESSIBILITY_SETTINGS);
//        startActivity(intent);
//    }

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


    private AndroidGamepadTool2 initializeTool() {
////        // 初始化您的libGDX应用
////        AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
////        initialize(new YourLibGDXGame(), config);
//
//
//        AndroidApplicationConfiguration configuration = new AndroidApplicationConfiguration();
//        configuration.a = 8; // default is 0
//        configuration.useImmersiveMode = true; // Recommended, but not required.

//        AndroidGamepadTool player= new AndroidGamepadTool() {
//            @Override
//            public void startPlayService() {
//                playMusic();
//            }
//
//            @Override
//            public void pausePlayService() {
//                pauseMusic();
//            }
//
//            @Override
//            public void stopPlayService() {
//                stopMusic();
//            }
//
//            @Override
//            public void updateApk(String url) {
//
//                Intent intent = new Intent(Intent.ACTION_VIEW);
//                File file= Gdx.files.external(url).file();
//                String authority = AndroidLauncher.this.getPackageName() + ".fileprovider";
//                Uri apkUri = FileProvider.getUriForFile(AndroidLauncher.this, authority, file);
//                intent.setDataAndType(apkUri, "application/vnd.android.package-archive");
//                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_ACTIVITY_NEW_TASK);
//                startActivity(intent);
//            }
//
//            @Override
//            public ISGTouchSimulate getTouchSimulate() {
//                if(null!=touchService){}
//                else {
////
////                    // 提示用户开启无障碍服务
////                    Intent intent = new Intent(android.provider.Settings.ACTION_ACCESSIBILITY_SETTINGS);
////                    startActivity(intent);
////                    touchService = new TouchSimulationService();
//                    if(null==TouchSimulationService.getInstance()){
//                        // 提示用户开启无障碍服务
//                        Intent intent = new Intent(android.provider.Settings.ACTION_ACCESSIBILITY_SETTINGS);
//                        startActivity(intent);
//                        if(null!=TouchSimulationService.getInstance()){
//                            touchService = TouchSimulationService.getInstance();
//                        }
//                    }else{
//                        touchService = TouchSimulationService.getInstance();
//                    }
//                }
//                return touchService;
//            }
//
//            @Override
//            public void startOverlayService() {
//               AndroidLauncher.this.startOverlayService();
//            }
//
////            @Override
////            public void checkSimulateService() {
////                if(null==TouchSimulationService.getInstance()){
////                    startSimulateServiceIfNeeded();
////                }
////            }
//
//            @Override
//            public void dispose(){
//                super.dispose();//之前一直漏了这句？
//                Intent serviceIntent = new Intent(AndroidLauncher.this, OverlayService.class);
//                stopService(serviceIntent);
//            }
//        };

//        initialize(player, configuration);
//
//        View view = initializeForView(player, configuration);
//        if (view instanceof SurfaceView) {
//            SurfaceView sv = (SurfaceView) view;
//            sv.setZOrderOnTop(true);
//            sv.getHolder().setFormat(PixelFormat.TRANSLUCENT);
//        }
//        setContentView(view);
        return new AndroidGamepadTool2();
    }
    private void initLibGdx(AndroidGamepadTool game){
        AndroidApplicationConfiguration configuration = new AndroidApplicationConfiguration();
        configuration.a = 8; // default is 0
        configuration.useImmersiveMode = true; // Recommended, but not required.

//        configuration.useAccelerometer = false;
//        configuration.useCompass = false;
        View view = initializeForView(game, configuration);
        if (view instanceof SurfaceView) {
            SurfaceView sv = (SurfaceView) view;
            sv.setZOrderOnTop(true);
            sv.getHolder().setFormat(PixelFormat.TRANSLUCENT);
        }
        setContentView(view);
    }

    //这里不检查权限
//    private void startOverlayService() {
//            Intent serviceIntent = new Intent(this, OverlayService.class);
//            startService(serviceIntent);
//    }

    private void stopOverlayService() {
        Intent serviceIntent = new Intent(this, OverlayService.class);
        stopService(serviceIntent);
    }

    @Override
    protected void onPause() {
        super.onPause();

////////        // 应用进入后台时启动悬浮窗
//        checkAndRequestSimulatePermission();

        // 检查无障碍权限状态（用户可能刚从设置页面返回）
        if (permissionManager != null) {
            permissionManager.checkAccessibilityPermission(this,TouchSimulationService.class,
                    new PermissionManager.PermissionCallback() {
                @Override
                public void onAllPermissionsCompleted(boolean allGranted) {}

                @Override
                public void onSinglePermissionResult(String permission, boolean granted) {
                    if (granted) {
                        Gdx.app.postRunnable(() -> {
                            myGame.onAccessibilityEnabled();
                        });
                    }
                }
            });
        }
        if(OverlayService.isOk()) {
//        if(PermissionUtils.hasOverlayPermission(this))
            startOverlayService();
//        }
        }
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

    //--------------------无障碍权限---------------------
    // 无障碍权限回调
    private void onAccessibilityPermissionGranted() {
        Log.d("AndroidLauncher", "无障碍权限已开启");
        startAccessibilityService();

        Gdx.app.postRunnable(() -> {
            myGame.onAccessibilityEnabled();
        });
        hasAccessibility=true;
    }

    private void onAccessibilityPermissionDenied() {
        Log.w("AndroidLauncher", "无障碍权限被拒绝");
        Gdx.app.postRunnable(() -> {
            myGame.onAccessibilityDisabled();
        });
    }

    private void startAccessibilityService() {
        // 无障碍服务通常由系统管理，不需要手动启动
        // 这里可以发送广播或设置标志位
        Intent intent = new Intent(this, TouchSimulationService.class);
        startService(intent);
    }
    private boolean hasAccessibility=false;
    public boolean isAccessibilityServiceEnabled(){
        //这样判断不了
//        boolean b=ContextCompat.checkSelfPermission(this, "ACCESSIBILITY")
//                == PackageManager.PERMISSION_GRANTED;
        hasAccessibility=TouchSimulationService.areNotificationsEnabled(this);
        return hasAccessibility;
    }
    //--------------------无障碍权限 end---------------------

    //--------------------手柄事件---------------------
//    private void startGamepadServices() {
//
//        // 启动游戏手柄服务
//        Intent gamepadIntent = new Intent(this, GamepadService.class);
//        startService(gamepadIntent);
//    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_VOLUME_DOWN) {
            // 处理音量下键按下事件
            return true; // 如果已经处理了事件，返回 true
        }
        return super.onKeyDown(keyCode, event); // 否则，让系统继续处理其他按键事件
    }
    //--------------------手柄事件 end---------------------

    //---------------------------------------------------
    //--------------------统一权限-------------------------
    //---------------------------------------------------
//    public class AndroidLauncher extends AndroidApplication {
        private PermissionManager permissionManager;
        private AndroidGamepadTool2 myGame;
    private boolean permissionsGranted = false;

//        @Override
//        protected void onCreate(Bundle savedInstanceState) {
//            super.onCreate(savedInstanceState);
//
//            // 初始化权限管理器
//            initPermissionManager();
//
////            // 配置 libGDX(这3句根本没起作用啊)
////            AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
////            config.useAccelerometer = false;
////            config.useCompass = false;
//
//            // 使用基础功能启动游戏
//            initializeGameWithLimitedFeatures();
////            // 先不初始化游戏，等权限获取后再初始化
//            //报错: java.lang.RuntimeException: Unable to resume activity {com.sellgirl.gamepadtool/com.sellgirl.gamepadtool.android.AndroidLauncher}: java.lang.NullPointerException: Attempt to
//            // invoke interface method 'void com.badlogic.gdx.backends.android.AndroidInput.onResume()' on a null object reference
//            requestPermissionsBeforeGameStart();
////            initLibGdx(initializeTool());
//        }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 立即初始化游戏，但游戏会显示等待权限的界面
        AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
        myGame =initializeTool();

        // 传递 Android 上下文给游戏
        myGame.setAndroidContext(this);

        // 立即初始化，避免 libGDX 生命周期问题
        initialize(myGame, config);

//        //init分开更好 --ben
//        initPermissionManager();
        // 在游戏初始化后检查权限
        checkPermissionsAfterInit();
    }

    private void checkPermissionsAfterInit() {
        // 延迟检查，确保游戏已经初始化完成
        Gdx.app.postRunnable(new Runnable() {
            @Override
            public void run() {
                initPermissionManager();
                if (!allRequiredPermissionsGranted()) {
                    // 通知游戏显示权限申请界面
                    myGame.showPermissionRequest();
                } else {
                    // 所有权限已获取，正常启动游戏
                    permissionsGranted = true;
                    myGame.onAllPermissionsGranted();
                    startOverlayService();
                }
            }
        });
    }
        private void initPermissionManager() {
            permissionManager = new PermissionManager(this);

            // 按优先级添加权限
            permissionManager
                    // 悬浮窗权限（游戏工具必备）
                    .addPermission(new PermissionRequest.Builder()
                            .setName("OVERLAY")
                            .setDesc("悬浮窗权限")
                            .setCode(1001)
                            .setType(PermissionRequest.TYPE_OVERLAY)
                            .setOnGranted(this::onOverlayPermissionGranted)
                            .setOnDenied(this::onOverlayPermissionDenied)
                            .setRequired(true)
                            .build())

                    // 2. 无障碍权限（高级功能）
                    .addPermission(new PermissionRequest.Builder()
                            .setName("ACCESSIBILITY")
                            .setDesc("无障碍权限")
                            .setCode(1002)
                            .setType(PermissionRequest.TYPE_ACCESSIBILITY)
                            .setOnGranted(this::onAccessibilityPermissionGranted)
                            .setOnDenied(this::onAccessibilityPermissionDenied)
                            .setRequired(false) // 设为非必需，因为申请流程复杂
                            .build())

                    // 存储权限（保存游戏数据）
                    .addPermission(new PermissionRequest.Builder()
                            .setName(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                            .setDesc("存储权限")
                            .setCode(1003)
                            .setType(PermissionRequest.TYPE_NORMAL)
                            .setOnGranted(this::onStoragePermissionGranted)
                            .setOnDenied(this::onStoragePermissionDenied)
                            .setRequired(true)
                            .build())

//                    // 网络权限（游戏更新、排行榜等）
//                    .addPermission(new PermissionRequest.Builder()
//                            .setName(Manifest.permission.INTERNET)
//                            .setDesc("网络权限")
//                            .setCode(1004)
//                            .setOnGranted(this::onNetworkPermissionGranted)
//                            .setRequired(true)
//                            .build())
            ;
//            permissionManager.initOK();
        }

//        private void requestPermissionsBeforeGameStart() {
//            // 检查是否所有必需权限都已获取
//            if (allRequiredPermissionsGranted()) {
//                // 直接启动游戏
//                initializeGame();
//            } else {
//                // 显示权限申请界面
//                showPermissionRequestDialog();
//            }
//        }

        private boolean allRequiredPermissionsGranted() {
            return PermissionUtils.hasOverlayPermission(this) &&
                    hasPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) &&
                    hasPermission(Manifest.permission.INTERNET);
        }

        private boolean hasPermission(String permission) {
            return ContextCompat.checkSelfPermission(this, permission) ==
                    PackageManager.PERMISSION_GRANTED;
        }

    // 权限申请回调
    public void onUserRequestedPermissions() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                startPermissionFlow();
            }
        });
    }

    private void showPermissionRequestDialog() {
            runOnUiThread(() -> {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("需要权限")
                        .setMessage("游戏需要以下权限才能正常运行：\n• 悬浮窗权限（显示游戏工具）\n• 存储权限（保存游戏进度）\n• 网络权限（游戏更新）")
                        .setPositiveButton("立即授权", (dialog, which) -> {
                            startPermissionFlow();
                        })
                        .setNegativeButton("稍后再说", (dialog, which) -> {
                            // 使用基础功能启动游戏
                            initializeGameWithLimitedFeatures();
                        })
                        .setCancelable(false)
                        .show();
            });
        }

//        private void startPermissionFlow() {
//            permissionManager.startRequest(this, new PermissionManager.PermissionCallback() {
//                @Override
//                public void onAllPermissionsCompleted(boolean allGranted) {
//                    runOnUiThread(() -> {
//                        if (allGranted) {
//                            initializeGame();
//                            Toast.makeText(AndroidLauncher.this, "所有权限已获取", Toast.LENGTH_SHORT).show();
//                        } else {
//                            initializeGameWithLimitedFeatures();
//                            Toast.makeText(AndroidLauncher.this, "部分功能可能受限", Toast.LENGTH_LONG).show();
//                        }
//                    });
//                }
//
//                @Override
//                public void onSinglePermissionResult(String permission, boolean granted) {
//                    // 单个权限结果处理
//                }
//            });
//        }

    private void startPermissionFlow() {
//        // 使用之前讨论的 PermissionManager
//         permissionManager = new PermissionManager(this);
//
//        permissionManager
//                .addPermission(new PermissionRequest.Builder()
//                        .setName("OVERLAY")
//                        .setDesc("悬浮窗权限")
//                        .setCode(1001)
//                        .setOnGranted(this::onOverlayPermissionGranted)
//                        .setOnDenied(this::onOverlayPermissionDenied)
//                        .setRequired(true)
//                        .build())
//                .addPermission(new PermissionRequest.Builder()
//                        .setName(Manifest.permission.WRITE_EXTERNAL_STORAGE)
//                        .setDesc("存储权限")
//                        .setCode(1002)
//                        .setOnGranted(this::onStoragePermissionGranted)
//                        .setRequired(true)
//                        .build())
//                // 网络权限（游戏更新、排行榜等）
//                .addPermission(new PermissionRequest.Builder()
//                        .setName(Manifest.permission.INTERNET)
//                        .setDesc("网络权限")
//                        .setCode(1003)
//                        .setOnGranted(this::onNetworkPermissionGranted)
//                        .setRequired(true)
//                        .build());
        if(null==permissionManager){
            //init分开更好 --ben
            initPermissionManager();
        }

        permissionManager.startRequest(this, new PermissionManager.PermissionCallback() {
            @Override
            public void onAllPermissionsCompleted(boolean allGranted) {
                permissionsGranted = allGranted;
                myGame.onPermissionCheckComplete(allGranted);

                if (allGranted) {
                    startOverlayService();
//                    myGame.setScreen(new MainMenuScreen(myGame)); //todo 我加的待验证
                }
            }

            @Override
            public void onSinglePermissionResult(String permission, boolean granted) {
                // 单个权限结果
            }
        });
    }
        private void initializeGame() {
//            AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
//            myGame = new MyGame();
//
//            // 传递 Android 上下文给游戏
//            myGame.setAndroidContext(this);
//
//            // 初始化游戏
//            initialize(myGame, config);
            myGame=initializeTool();
            // 传递 Android 上下文给游戏
            myGame.setAndroidContext(this);
            initLibGdx(myGame);

            // 启动悬浮窗服务（如果有权限）
            if (PermissionUtils.hasOverlayPermission(this)) {
                startOverlayService();
            }
        }

        private void initializeGameWithLimitedFeatures() {
//            AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
//            myGame = new MyGame();
//
//            // 标记为受限模式
//            myGame.setLimitedMode(true);
//
//            initialize(myGame, config);
            myGame=initializeTool();
            // 标记为受限模式
            myGame.setLimitedMode(true);
            initLibGdx(myGame);
        }

        // 权限回调方法
        private void onOverlayPermissionGranted() {
            startOverlayService();
        }

        private void onOverlayPermissionDenied() {
            runOnUiThread(() ->
                    Toast.makeText(this, "悬浮窗功能不可用", Toast.LENGTH_SHORT).show());
        }

        private void onStoragePermissionGranted() {
            // 初始化文件存储
        }

        private void onStoragePermissionDenied() {
            runOnUiThread(() ->
                    Toast.makeText(this, "游戏存档功能受限", Toast.LENGTH_SHORT).show());
        }

        private void onNetworkPermissionGranted() {
            // 初始化网络功能
        }

        public void startOverlayService() {
            try {
                Intent serviceIntent = new Intent(this, OverlayService.class);
                startService(serviceIntent);
            } catch (SecurityException e) {
                Log.e("AndroidLauncher", "启动悬浮窗服务失败: " + e.getMessage());
            }
        }

        @Override
        public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                               @NonNull int[] grantResults) {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
            if (permissionManager != null) {
                permissionManager.onRequestPermissionsResult(requestCode, permissions, grantResults);
            }
        }

        @Override
        protected void onActivityResult(int requestCode, int resultCode, Intent data) {
            super.onActivityResult(requestCode, resultCode, data);
            if (permissionManager != null) {
                permissionManager.onActivityResult(requestCode);
            }
        }

        public void checkAndRefreshPermissions() {
            runOnUiThread(() -> {
                if (myGame != null) {
                    // 检查悬浮窗权限
                    if (PermissionUtils.hasOverlayPermission(this)) {
                        startOverlayService();
                        myGame.setLimitedMode(false);
                    }

                    // 可以添加其他权限的刷新逻辑
                }
            });
        }
//    }
    //--------------------统一权限 END-------------------------

//    //ben
//    public boolean getAllPermission(){
//        return permissionManager.isAllOK();
//    }
}
