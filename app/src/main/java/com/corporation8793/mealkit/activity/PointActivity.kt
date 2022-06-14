package com.corporation8793.mealkit.activity

import android.media.MediaPlayer.OnPreparedListener
import android.net.Uri
import android.os.Bundle
import android.widget.MediaController
import android.widget.VideoView
import androidx.appcompat.app.AppCompatActivity
import com.corporation8793.mealkit.R

class PointActivity : AppCompatActivity() {
    lateinit var video: VideoView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_point)

        video = findViewById<VideoView>(R.id.videoview)

        val uri = Uri.parse("http://13.209.29.68/wp-content/uploads/2022/05/slot_test_file.mp4")
        video.setMediaController(MediaController(this))

        video.setVideoURI(uri)

        video.setOnPreparedListener(OnPreparedListener { video.start() })
    }
}