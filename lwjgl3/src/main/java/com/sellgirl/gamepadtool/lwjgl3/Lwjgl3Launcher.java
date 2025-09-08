package com.sellgirl.gamepadtool.lwjgl3;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
//import com.sellgirl.gamepadtool.DesktopLauncher;
import com.sellgirl.gamepadtool.GamepadTool;
import com.sellgirl.gamepadtool.ScreenSetting;
//import com.neris.musicplayer.MusicPlayer;
//import com.neris.musicplayer.ScreenSetting;

/** Launches the desktop (LWJGL3) application. */
public class Lwjgl3Launcher {
    public static void main(String[] args) {
        if (StartupHelper.startNewJvmIfRequired()) return; // This handles macOS support and helps on Windows.
        createApplication();
    }

    private static Lwjgl3Application createApplication() {

		      Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		      config.setTitle("KnightSasha");
		      //config.setWindowedMode(800, 480);
		      config.setWindowedMode((int)ScreenSetting.WORLD_WIDTH, (int)ScreenSetting.WORLD_HEIGHT);
		      config.useVsync(true);
		      config.setForegroundFPS(ScreenSetting.FPS);
		      config.setWindowIcon("favicon.jpg");
//		      new Lwjgl3Application(new KnightSashaGame(), config);
		      Game game = new GamepadTool();
//              game.hasKeyboard=true;
////		      com.mygdx.game.sasha.SashaGame.wechatPurchaseManager = new WechatPurchaseManager();
//              game.wechatPurchaseManager = new DesktopLauncher.WechatPurchaseManager();
//		      new Lwjgl3Application(game, config);
        return new Lwjgl3Application(game, getDefaultConfiguration());
    }

    private static Lwjgl3ApplicationConfiguration getDefaultConfiguration() {
        Lwjgl3ApplicationConfiguration configuration = new Lwjgl3ApplicationConfiguration();


        configuration.setTitle("GamepadTool");
        //// Vsync limits the frames per second to what your hardware can display, and helps eliminate
        //// screen tearing. This setting doesn't always work on Linux, so the line after is a safeguard.
        configuration.useVsync(true);
        //// Limits FPS to the refresh rate of the currently active monitor, plus 1 to try to match fractional
        //// refresh rates. The Vsync setting above should limit the actual FPS to match the monitor.

        int fps=Lwjgl3ApplicationConfiguration.getDisplayMode().refreshRate + 1;
        configuration.setForegroundFPS(fps);//1.13.0gdx生成的版本是这样写的
        ScreenSetting.FPS=fps;

        //// If you remove the above line and set Vsync to false, you can get unlimited FPS, which can be
        //// useful for testing performance, but can also be very stressful to some hardware.
        //// You may also need to configure GPU drivers to fully disable Vsync; this can cause screen tearing.
        //configuration.setWindowedMode(640, 480);
        configuration.setWindowedMode((int) ScreenSetting.WORLD_WIDTH, (int)ScreenSetting.WORLD_HEIGHT);
        //// You can change these files; they are in lwjgl3/src/main/resources/ .
//        configuration.setWindowIcon("libgdx128.png", "libgdx64.png", "libgdx32.png", "libgdx16.png");//用这种写法吧 --benjamin todo
        configuration.setWindowIcon("favicon.jpg");
        return configuration;
    }
}
