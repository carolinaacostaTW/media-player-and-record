package com.example.mediaplayerandrecord;

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

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.mediaplayerandrecord.databinding.ActivityRecordingBinding;

import java.io.File;

public class Recording extends AppCompatActivity {

    private static int MICROPHONE_PERMISSION_CODE = 200;

    private ActivityRecordingBinding binding;
    private MediaRecorder mediaRecorder;
    private MediaPlayer mediaPlayer;
    private Button startRecording, stopRecording, startPlaying, stopPlaying;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRecordingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        startRecording = binding.startRecord;
        stopRecording = binding.stopRecord;
        startPlaying = binding.startPlaying;
        stopPlaying = binding.stopPlaying;

        if(isMicrophonePresent()) {
            getMicrophonePermission();
        }

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

                    Toast.makeText(Recording.this, "Recording started", Toast.LENGTH_SHORT).show();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        stopRecording.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaRecorder.stop();
                mediaRecorder.release();
                mediaRecorder = null;

                Toast.makeText(Recording.this, "Recording stopped", Toast.LENGTH_SHORT).show();
            }
        });

        startPlaying.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    mediaPlayer = new MediaPlayer();
                    mediaPlayer.setDataSource(getRecordingFilePath());
                    mediaPlayer.prepare();
                    mediaPlayer.start();

                    Toast.makeText(Recording.this, "Recording is playing", Toast.LENGTH_SHORT).show();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }
        private boolean isMicrophonePresent(){
            if(this.getPackageManager().hasSystemFeature(PackageManager.FEATURE_MICROPHONE)){
                return true;
            } else {
                return false;
            }
        }

        private void getMicrophonePermission(){
            if(ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO)
            == PackageManager.PERMISSION_DENIED){
                ActivityCompat.requestPermissions(this, new String[]
                        {Manifest.permission.RECORD_AUDIO}, MICROPHONE_PERMISSION_CODE );
            }
        }

        private String getRecordingFilePath(){
            ContextWrapper contextWrapper = new ContextWrapper(getApplicationContext());
            File musicDirectory = contextWrapper.getExternalFilesDir(Environment.DIRECTORY_MUSIC);
            File file = new File(musicDirectory, "testRecordingFile" + ".mp3");
            return file.getPath();
        }
}


//public class Recording extends AppCompatActivity {
//
//    private ActivityRecordingBinding binding;
//
//    private Button startRecording, stopRecording, startPlaying, stopPlaying;
//    private MediaRecorder mediaRecorder;
//    private MediaPlayer mediaPlayer;
//    private String audioSavePath = null;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        binding = ActivityRecordingBinding.inflate(getLayoutInflater());
//        setContentView(binding.getRoot());
//
//        startRecording = binding.startRecord;
//        stopRecording = binding.stopRecord;
//        startPlaying = binding.startPlaying;
//        stopPlaying = binding.stopPlaying;
//
//        startRecording.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if(checkPermissions()){
////                    audioSavePath = getExternalCacheDir().getAbsolutePath() + "/audiorecordtest.3gp";
////                    audioSavePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + "recordingaudio.mp3";
//                    audioSavePath = getCacheDir().getAbsolutePath() + "/" + "recordingaudio.mp3";
//
//                    mediaRecorder = new MediaRecorder();
//                    mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
//                    mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
//                    mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
//                    mediaRecorder.setOutputFile(audioSavePath);
//
//                    try {
//                        mediaRecorder.prepare();
//                        mediaRecorder.start();
//                        Toast.makeText(Recording.this, "Recording started", Toast.LENGTH_SHORT).show();
//                    } catch (IOException e) {
//                        throw new RuntimeException(e);
//                    }
//
//                } else{
//                    ActivityCompat.requestPermissions(Recording.this, new String[]{
//                            Manifest.permission.RECORD_AUDIO, Manifest.permission.WRITE_EXTERNAL_STORAGE
//                    }, 1);
//                }
//            }
//        });
//
//        stopRecording.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                mediaRecorder.stop();
//                mediaRecorder.release();
//                Toast.makeText(Recording.this, "Recording stopped", Toast.LENGTH_SHORT).show();
//            }
//        });
//
//        startPlaying.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                mediaPlayer = new MediaPlayer();
//                try {
//                    mediaPlayer.setDataSource(audioSavePath);
//                    mediaPlayer.prepare();
//                    mediaPlayer.start();
//                    Toast.makeText(Recording.this, "Start playing", Toast.LENGTH_SHORT).show();
//                } catch (IOException e) {
//                    throw new RuntimeException(e);
//                }
//            }
//        });
//
//        stopPlaying.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if(mediaPlayer != null){
//                    mediaPlayer.stop();
//                    mediaPlayer.release();
//                    Toast.makeText(Recording.this, "Audio stopped", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
//    }
//
//    private boolean checkPermissions(){
//        int recordAudioPermission = ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.RECORD_AUDIO);
//        int externalStoragePermission = ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE);
//
//        return recordAudioPermission == PackageManager.PERMISSION_GRANTED &&
//                externalStoragePermission == PackageManager.PERMISSION_GRANTED;
//    }

//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//
//        if (requestCode == 1) {
//            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                // Permission granted, start recording
//                startRecording.callOnClick();
//            } else {
//                // Permission denied, show a message or take appropriate action
//                Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
//            }
//        }
//    }
