package com.androiddevs.musicstreaming;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button btnStartStreaming, btnPauseStreaming, btnStopStreaming;

    private boolean firstStart = true;
    MusicService musicService;

    String url = "https://file-examples.com/wp-content/uploads/2017/11/file_example_MP3_1MG.mp3";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnStartStreaming = findViewById(R.id.btnStartStreaming);
        btnPauseStreaming = findViewById(R.id.btnPauseStreaming);
        btnStopStreaming = findViewById(R.id.btnStopStreaming);

        btnStartStreaming.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(firstStart) {
                    Intent musicServiceIntent = new Intent(getApplicationContext(), MusicService.class);
                    musicServiceIntent.putExtra("url", url);

                    startService(musicServiceIntent);
                    firstStart = false;
                } else {
                    musicService = MusicService.getInstance();
                    musicService.play();
                }
            }
        });

        btnPauseStreaming.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                musicService = MusicService.getInstance();
                musicService.pause();
            }
        });

        btnStopStreaming.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                musicService = MusicService.getInstance();
                musicService.stop();
            }
        });
    }

}
