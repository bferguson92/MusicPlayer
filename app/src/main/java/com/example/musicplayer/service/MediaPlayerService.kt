package com.example.musicplayer.service

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.Binder
import android.os.IBinder
import com.example.musicplayer.R

class MediaPlayerService : Service(){
    val mediaPlayerBinder  = MediaPlayerBinder()
    var paused = true
    var firstTime = true
    lateinit var mediaPlayer: MediaPlayer

    override fun onBind(intent: Intent?): IBinder? {
        onPlay()
        return mediaPlayerBinder
    }

    override fun onCreate() {
        super.onCreate()
        mediaPlayer = MediaPlayer.create(this, R.raw.quake_theme)
        mediaPlayer.isLooping = false
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer.stop()
    }

    fun onPlay(){
        if(!mediaPlayer.isPlaying)
            mediaPlayer.start()
    }

    fun onPause(){
        if(mediaPlayer.isPlaying){
            mediaPlayer.pause()
            paused = true
        }

    }

    inner class MediaPlayerBinder : Binder(){
        fun getMediaPlayerService() : MediaPlayerService{
            return this@MediaPlayerService
        }
    }
}