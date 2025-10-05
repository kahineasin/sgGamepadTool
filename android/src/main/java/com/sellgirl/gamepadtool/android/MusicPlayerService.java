package com.sellgirl.gamepadtool.android;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.os.PowerManager;

import androidx.core.app.NotificationCompat;

import com.sellgirl.gamepadtool.R;

/**
 * 通过Service前台使音乐可以被唤醒(来自deepseek)
 */
public class MusicPlayerService extends Service {
    public static final String ACTION_PLAY = "PLAY";
    public static final String ACTION_PAUSE = "PAUSE";
    public static final String ACTION_STOP = "STOP";

//    private MediaPlayer mediaPlayer;
    private PowerManager.WakeLock wakeLock;

//    private int ONGOING_NOTIFICATION_ID=2025;
    private int NOTIFICATION_ID=2035;
    private String CHANNEL_ID="sgMusicPlayer";
    @Override
    public void onCreate() {
        super.onCreate();
//        Log.d("MusicService", "onCreate called");

//        // 初始化 MediaPlayer
//        mediaPlayer = new MediaPlayer();

        // 初始化 WakeLock
        PowerManager powerManager = (PowerManager) getSystemService(POWER_SERVICE);
        wakeLock = powerManager.newWakeLock(
            PowerManager.PARTIAL_WAKE_LOCK,
            "MusicPlayer::WakeLock"
        );
    }

    @SuppressLint("ForegroundServiceType")
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
//        Log.d("MusicService", "onStartCommand called");

        if (intent != null && intent.getAction() != null) {
            String action = intent.getAction();

            switch (action) {
                case ACTION_PLAY:
                    startPlayback(intent);
                    break;
                case ACTION_PAUSE:
                    pausePlayback();
                    break;
                case ACTION_STOP:
                    stopPlayback();
                    break;
            }
        }

        // 创建通知并启动前台服务
        startForeground(1, createNotification());

        return START_STICKY;
    }

    private void startPlayback(Intent intent) {
        // 检查通知权限
        if (!NotificationPermissionHelper.areNotificationsEnabled(this)) {
//            // 如果没有权限，尝试创建一个简单的通知（可能会失败）
//            try {
//                //这种方式会闪退，而且catch也无效
//                startForeground(NOTIFICATION_ID, createFallbackNotification());
//            } catch (Throwable e) {
//                // 处理没有权限的情况
//                Gdx.app.error("MusicService", "无法显示通知: " + e.getMessage());
////                // 即使没有通知，也继续播放（但可能会被系统限制）
////                if (mediaPlayer != null) {
////                    mediaPlayer.start();
////                }
//                return;
//            }
        } else {
            // 有权限，正常创建通知
            startForeground(NOTIFICATION_ID, createNotification());
        }


        // 获取 WakeLock
        if (wakeLock != null && !wakeLock.isHeld()) {
            wakeLock.acquire();
        }

//        try {
//            // 这里应该是您的播放逻辑
//            String musicUri = intent.getStringExtra("MUSIC_URI");
//            // 设置 MediaPlayer 数据源并准备播放
//            mediaPlayer.setDataSource(this, Uri.parse(musicUri));
//            mediaPlayer.prepare();
//            mediaPlayer.start();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }
//    private void startPlayback() {
//        // 检查通知权限
//        if (!NotificationPermissionHelper.areNotificationsEnabled(this)) {
//            // 如果没有权限，尝试创建一个简单的通知（可能会失败）
//            try {
//                startForeground(NOTIFICATION_ID, createFallbackNotification());
//            } catch (SecurityException e) {
//                // 处理没有权限的情况
//                Log.e("MusicService", "无法显示通知: " + e.getMessage());
//                // 即使没有通知，也继续播放（但可能会被系统限制）
//                if (mediaPlayer != null) {
//                    mediaPlayer.start();
//                }
//                return;
//            }
//        } else {
//            // 有权限，正常创建通知
//            startForeground(NOTIFICATION_ID, createNotification());
//        }
//
//        // 开始播放
//        if (mediaPlayer != null) {
//            mediaPlayer.start();
//        }
//    }
    private void pausePlayback() {
//        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
//            mediaPlayer.pause();
//        }

        // 释放 WakeLock
        if (wakeLock != null && wakeLock.isHeld()) {
            wakeLock.release();
        }
    }

    private void stopPlayback() {
//        if (mediaPlayer != null) {
//            if (mediaPlayer.isPlaying()) {
//                mediaPlayer.stop();
//            }
//            mediaPlayer.reset();
//            mediaPlayer.release();
//            mediaPlayer = null;
//        }

        // 释放 WakeLock
        if (wakeLock != null && wakeLock.isHeld()) {
            wakeLock.release();
            wakeLock = null;
        }

        // 停止服务
        stopForeground(true);
        stopSelf();
    }

    private Notification createNotification() {
        // 创建通知渠道 (Android 8.0+ 需要)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                 CHANNEL_ID,//"music_channel",
                "Music Player",
                NotificationManager.IMPORTANCE_LOW
            );
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }

        // 创建通知
        return new NotificationCompat.Builder(this,
            CHANNEL_ID//"music_channel"
        )
            .setContentTitle("音乐播放器")
            .setContentText("正在播放音乐")
            .setSmallIcon(getNoticIcon())//R.drawable.ic_music_note)
            .build();
    }
    public static int getNoticIcon(){
        return R.drawable.ic_launcher;
//        return com.badlogic.gdx.backends.android.R.drawable.design_ic_visibility;
    }
//    private void createNotificationChannel() {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            NotificationChannel channel = new NotificationChannel(
//                CHANNEL_ID,
//                "Music Player Channel",
//                NotificationManager.IMPORTANCE_LOW // 重要性级别较低，不会发出声音
//            );
//            channel.setDescription("Channel for music playback");
//            NotificationManager manager = getSystemService(NotificationManager.class);
//            manager.createNotificationChannel(channel);
//        }
//    }
    @Override
    public void onDestroy() {
        super.onDestroy();
//        Log.d("MusicService", "onDestroy called");
//
//        // 确保释放所有资源
//        if (mediaPlayer != null) {
//            mediaPlayer.release();
//            mediaPlayer = null;
//        }

        if (wakeLock != null && wakeLock.isHeld()) {
            wakeLock.release();
            wakeLock = null;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null; // 如果不支持绑定服务，返回 null
    }

    private Notification createFallbackNotification() {
        // 创建一个最简单的通知，即使没有权限也尝试显示
        // 注意：在 Android 13+ 上，这可能仍然会失败
        return new NotificationCompat.Builder(this, "default_channel")
            .setContentTitle("音乐播放器")
            .setContentText("正在播放音乐")
//            .setSmallIcon(R.drawable.ic_music_note)
            .setSmallIcon(getNoticIcon())
            .setPriority(NotificationCompat.PRIORITY_MIN) // 最低优先级
            .build();
    }
}
