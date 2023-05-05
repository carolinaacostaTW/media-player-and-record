package com.example.mediaplayerandrecord;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.ContextWrapper;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.mediaplayerandrecord.databinding.ActivityPlayAudioAndRecordBinding;

import java.io.File;

public class PlayAudioAndRecord extends AppCompatActivity {

    private ActivityPlayAudioAndRecordBinding binding;
    private Button playAudio, startRecording, pauseAudio, stopRecording, playRecording;
    private MediaRecorder mediaRecorder;
    private MediaPlayer mediaPlayer;
    private MediaPlayer recordingPlayer;
    private final static int MICROPHONE_PERMISSION_CODE = 200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPlayAudioAndRecordBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        playAudio = binding.playAudio;
        startRecording = binding.startRecording;
        pauseAudio = binding.pauseAudio;
        stopRecording = binding.stopRecording;
        playRecording = binding.playRecording;

        if(isMicrophonePresent()) {
            getMicrophonePermission();
        }

        mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.whoistheartist);

        startRecording.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    mediaRecorder = new MediaRecorder();
                    mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
                    mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
                    mediaRecorder.setOutputFile(getRecordingFilePath());
                    mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
                    mediaRecorder.prepare();
                    mediaRecorder.start();

                    Toast.makeText(PlayAudioAndRecord.this, "Recording started", Toast.LENGTH_SHORT).show();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        playAudio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaPlayer.start();
                Toast.makeText(PlayAudioAndRecord.this, "Audio playing", Toast.LENGTH_SHORT).show();
            }
        });

        pauseAudio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mediaPlayer.isPlaying()){
                    mediaPlayer.pause();
                    Toast.makeText(PlayAudioAndRecord.this, "Audio paused", Toast.LENGTH_SHORT).show();
                }
            }
        });

        stopRecording.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaRecorder.stop();
                mediaRecorder.release();
                mediaRecorder = null;

                Toast.makeText(PlayAudioAndRecord.this, "Recording stopped", Toast.LENGTH_SHORT).show();
            }
        });

        playRecording.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    recordingPlayer = new MediaPlayer();
                    recordingPlayer.setDataSource(getRecordingFilePath());
                    recordingPlayer.prepare();
                    recordingPlayer.start();

                    Toast.makeText(PlayAudioAndRecord.this, "Recording is playing", Toast.LENGTH_SHORT).show();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }


    private boolean isMicrophonePresent() {
        if(this.getPackageManager().hasSystemFeature(PackageManager.FEATURE_MICROPHONE)){
            return true;
        } else {
            return false;
        }
    }

    private void getMicrophonePermission() {
        if(ContextCompat.checkSelfPermission(this, android.Manifest.permission.RECORD_AUDIO)
                == PackageManager.PERMISSION_DENIED){
            ActivityCompat.requestPermissions(this, new String[]
                    {Manifest.permission.RECORD_AUDIO}, MICROPHONE_PERMISSION_CODE);
        }
    }

    private String getRecordingFilePath(){
        ContextWrapper contextWrapper = new ContextWrapper(getApplicationContext());
        File musicDirectory = contextWrapper.getExternalFilesDir(Environment.DIRECTORY_MUSIC);
        File file = new File(musicDirectory, "testRecordingFile" + ".mp3");
        return file.getPath();
    }
}