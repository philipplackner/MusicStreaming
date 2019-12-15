package com.androiddevs.musicstreaming;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

import static android.content.ContentValues.TAG;

public class MusicService extends Service {

    private static MusicService instance;

    MediaPlayer mp = new MediaPlayer();
    String url;

    boolean firstStart = true;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) { return null; }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        instance = this;
        this.url = intent.getStringExtra("url");
        play();
        return super.onStartCommand(intent, flags, startId);
    }

    public static MusicService getInstance() {
        return instance;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if (mp != null) {
            mp.reset();
            mp.release();
            mp = null;
        }
    }

    public void pause() {
        if(mp.isPlaying()) {
            mp.pause();
        }
    }

    public void stop() {
        if(mp.isPlaying()) {
            mp.stop();
            mp.reset();
            firstStart = true;
        }
    }

    public void play() {
        if(firstStart) {
            try {
                mp.setDataSource(url);
                mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mediaPlayer) {
                        mediaPlayer.stop();
                        mediaPlayer.reset();
                    }
                });

                mp.prepare();
                mp.start();
                firstStart = false;
            } catch (Exception e) {
                Log.e(TAG, e.toString());
            }
        } else {
            if(!mp.isPlaying()) {
                mp.start();
            }
        }

    }

}
