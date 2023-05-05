package com.example.mediaplayerandrecord;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.mediaplayerandrecord.databinding.ActivityMainBinding;

public class Audio extends AppCompatActivity {

    ActivityMainBinding binding;
    Button play, pause, record, audioAndRecord;
    MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        play = binding.play;
        pause = binding.pause;
        record = binding.goToRecord;
        audioAndRecord = binding.goToAudioAndRecord;

        mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.helloaudio);

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaPlayer.start();
            }
        });

        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mediaPlayer.isPlaying()){
                    mediaPlayer.pause();
                }
            }
        });

        record.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Audio.this, Recording.class));
            }
        });

        audioAndRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Audio.this, PlayAudioAndRecord.class));
            }
        });

    }

    @Override
    protected void onStop() {
        mediaPlayer.release();
        mediaPlayer = null;
        super.onStop();
    }

}