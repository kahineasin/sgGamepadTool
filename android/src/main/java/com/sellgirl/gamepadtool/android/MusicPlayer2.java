package com.sellgirl.gamepadtool.android;//package com.neris.musicplayer.android;
//
//import android.content.Intent;
//import android.os.Build;
//
//import com.neris.musicplayer.MusicPlayer;
//
//public class MusicPlayer2 extends MusicPlayer {
//    // 在您的 Activity 中
//    private void playMusic() {
//        Intent serviceIntent = new Intent(this, MusicPlayerService.class);
//        serviceIntent.setAction(MusicPlayerService.ACTION_PLAY);
//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            startForegroundService(serviceIntent);
//        } else {
//            startService(serviceIntent);
//        }
//    }
//
//    private void pauseMusic() {
//        Intent serviceIntent = new Intent(this, MusicPlayerService.class);
//        serviceIntent.setAction(MusicPlayerService.ACTION_PAUSE);
//        startService(serviceIntent);
//    }
//
//    private void stopMusic() {
//        Intent serviceIntent = new Intent(this, MusicPlayerService.class);
//        serviceIntent.setAction(MusicPlayerService.ACTION_STOP);
//        startService(serviceIntent);
//    }
//}
