package com.example.musicplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.SeekBar;

import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    ImageButton playButton;
    MediaPlayer mediaPlayer;
    SeekBar volumeSeekBar;
    SeekBar progressSeekBar;
    AudioManager audioManager;

    @SuppressLint({"WrongViewCast", "MissingInflatedId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.stuff);

        audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
        int maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);

        volumeSeekBar = findViewById(R.id.volumeSeekBar);
        volumeSeekBar.setMax(maxVolume);
        volumeSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                Log.d("Progress changed", "" + progress);
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, progress, 0);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                volumeSeekBar.animate().alpha(1f);
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                volumeSeekBar.animate().alpha(0.2f);
            }
        });

        progressSeekBar = findViewById(R.id.progressSeekBar);
        progressSeekBar.setMax(mediaPlayer.getDuration());
        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                progressSeekBar.setProgress(mediaPlayer.getCurrentPosition());
            }
        }, 0, 1000);

        progressSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser){
                    mediaPlayer.seekTo(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        //colour of string in status bar//
        Objects.requireNonNull(getSupportActionBar()).setTitle(Html.fromHtml("<font color=\"#0E995E\">" + getString(R.string.app_name) + "</font>"));
    }

    public void playClicker(View view) {
        playButton = findViewById(R.id.playButton);
        if (mediaPlayer.isPlaying()) {
            playButton.setImageResource(R.drawable.play_ic);
            mediaPlayer.pause();
        } else {
            playButton.setImageResource(R.drawable.pause_ic);
            mediaPlayer.start();
        }
    }

    public void previousSongClicker(View view) {
    }

    public void nextSongClicker(View view) {
    }
}