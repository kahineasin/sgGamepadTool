package com.sellgirl.gamepadtool.android;

import android.content.Intent;
import android.net.Uri;

import androidx.core.content.FileProvider;

import com.badlogic.gdx.Gdx;
import com.sellgirl.gamepadtool.AndroidGamepadTool;
import com.sellgirl.gamepadtool.MainMenuScreen;
import com.sellgirl.gamepadtool.android.screen.PermissionScreen;
import com.sellgirl.gamepadtool.android.simulate.TouchSimulationService;
import com.sellgirl.gamepadtool.phone.ISGTouchSimulate;

import java.io.File;

public class AndroidGamepadTool2 extends AndroidGamepadTool {
    @Override
    public void startPlayService() {
        if(limitedMode){return;}
         androidContext.playMusic();
    }

    @Override
    public void pausePlayService() {
        if(limitedMode){return;}
        androidContext.pauseMusic();
    }

    @Override
    public void stopPlayService() {
        if(limitedMode){return;}
        androidContext.stopMusic();
    }

    @Override
    public void updateApk(String url) {
        if(limitedMode){return;}
        Intent intent = new Intent(Intent.ACTION_VIEW);
        File file= Gdx.files.external(url).file();
        String authority = androidContext.getPackageName() + ".fileprovider";
        Uri apkUri = FileProvider.getUriForFile(androidContext, authority, file);
        intent.setDataAndType(apkUri, "application/vnd.android.package-archive");
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_ACTIVITY_NEW_TASK);
        androidContext.startActivity(intent);
    }

    @Override
    public ISGTouchSimulate getTouchSimulate() {
        if(limitedMode){return null;}
        else{
            return TouchSimulationService.getInstance();
        }
//        if(null!=androidContext.touchService){}
//        else {
////
////                    // 提示用户开启无障碍服务
////                    Intent intent = new Intent(android.provider.Settings.ACTION_ACCESSIBILITY_SETTINGS);
////                    startActivity(intent);
////                    touchService = new TouchSimulationService();
//            if(null== TouchSimulationService.getInstance()){
//                // 提示用户开启无障碍服务
//                Intent intent = new Intent(android.provider.Settings.ACTION_ACCESSIBILITY_SETTINGS);
//                startActivity(intent);
//                if(null!=TouchSimulationService.getInstance()){
//                    touchService = TouchSimulationService.getInstance();
//                }
//            }else{
//                touchService = TouchSimulationService.getInstance();
//            }
//        }
//        return touchService;
    }

    @Override
    public void startOverlayService() {
        androidContext.startOverlayService();
    }

//            @Override
//            public void checkSimulateService() {
//                if(null==TouchSimulationService.getInstance()){
//                    startSimulateServiceIfNeeded();
//                }
//            }

    @Override
    public void dispose(){
        super.dispose();
//        Intent serviceIntent = new Intent(AndroidLauncher.this, OverlayService.class);
//        stopService(serviceIntent);
    }


    //--------------------统一权限-------------------------
    //--------------------统一权限 END-------------------------

    private AndroidLauncher androidContext;
    private boolean limitedMode = false;
    private boolean waitingForPermissions = true;
    private PermissionScreen permissionScreen;
    private MainMenuScreen mainMenuScreen;

    public void setAndroidContext(AndroidLauncher context) {
        this.androidContext = context;
    }

    public void setLimitedMode(boolean limited) {
        this.limitedMode = limited;
    }

    @Override
    public void create() {
//        // 根据权限状态初始化游戏
//        super.create();
        doCreate();
//        if (limitedMode) {
//            initializeLimitedGame();
//        } else {
//            initializeFullGame();
//        }
        // 初始显示权限申请屏幕
        permissionScreen = new PermissionScreen(this);
        setScreen(permissionScreen);
    }

    /**
     * 显示权限申请界面
     */
    public void showPermissionRequest() {
        if (permissionScreen != null) {
            permissionScreen.showPermissionRequest();
        }
    }

    /**
     * 用户点击了"申请权限"按钮
     */
    public void onUserRequestedPermissions() {
        if (androidContext != null) {
            androidContext.onUserRequestedPermissions();
        }
    }

    /**
     * 权限检查完成
     */
    public void onPermissionCheckComplete(boolean allGranted) {
        waitingForPermissions = false;

        if (allGranted) {
            // 切换到主菜单
//            if (mainMenuScreen == null) {
//                mainMenuScreen = new MainMenuScreen(this);
//            }
//            setScreen(mainMenuScreen);
            Gdx.app.postRunnable(new Runnable() {
                @Override
                public void run() {
                    setScreen(new MainMenuScreen(AndroidGamepadTool2.this));
                }
            });
        } else {
            // 显示权限不足的提示
            if (permissionScreen != null) {
                permissionScreen.showPermissionDenied();
            }
        }
    }

    /**
     * 所有权限已获取
     */
    public void onAllPermissionsGranted() {
        waitingForPermissions = false;

        if (mainMenuScreen == null) {
            mainMenuScreen = new MainMenuScreen(this);
        }
        setScreen(mainMenuScreen);
    }

    private void initializeFullGame() {
        // 完整功能初始化
        setScreen(new MainMenuScreen(this));
    }

    private void initializeLimitedGame() {
        // 受限功能初始化
//        setScreen(new LimitedMenuScreen(this));
        setScreen(new MainMenuScreen(this));
    }

    /**
     * 检查特定功能是否可用
     */
    public boolean isOverlayFeatureAvailable() {
        return androidContext != null &&
                PermissionUtils.hasOverlayPermission(androidContext)// &&
                //!limitedMode
                ;
    }

    /**
     * 重新检查权限并刷新功能
     */
    public void refreshPermissions() {
        if (androidContext != null) {
            androidContext.checkAndRefreshPermissions();
        }
    }

    //--------------------无障碍权限---------------------

    /**
     * 无障碍服务已启用
     */
    public void onAccessibilityEnabled() {
//        // 启用高级功能
//        if (getScreen() instanceof MainMenuScreen) {
//            ((MainMenuScreen) getScreen()).setAdvancedFeaturesEnabled(true);
//        }
//
//        // 显示提示
//        showMessage("高级辅助功能已启用");
    }

    /**
     * 无障碍服务被禁用
     */
    public void onAccessibilityDisabled() {
//        // 禁用高级功能
//        if (getScreen() instanceof MainMenuScreen) {
//            ((MainMenuScreen) getScreen()).setAdvancedFeaturesEnabled(false);
//        }
    }

    /**
     * 检查无障碍功能是否可用
     */
    public boolean isAccessibilityFeatureAvailable() {
        return androidContext != null &&
                androidContext.isAccessibilityServiceEnabled()
                ;
    }
    //--------------------无障碍权限 END---------------------

//    //ben
//    public boolean getAllPermission(){
//        return androidContext.getAllPermission();
//    }
}
