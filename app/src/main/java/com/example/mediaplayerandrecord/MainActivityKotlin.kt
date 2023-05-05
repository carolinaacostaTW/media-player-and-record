package com.example.mediaplayerandrecord

import android.media.MediaPlayer
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.mediaplayerandrecord.databinding.ActivityMainBinding

//class MainActivityKotlin : AppCompatActivity() {
//
//    private lateinit var binding: ActivityMainBinding
//    private lateinit var playButton: Button
//    private var mediaPlayer: MediaPlayer? = null
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        binding = ActivityMainBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//
//        playButton = binding.play
//
//        mediaPlayer = MediaPlayer.create(applicationContext, R.raw.helloaudio)
//
//        playButton.setOnClickListener(View.OnClickListener {
//            mediaPlayer!!.start()
//        })
//
//        fun onStop() {
//            mediaPlayer!!.release()
//            mediaPlayer = null
//            super.onStop()
//        }
//
//    }
//}