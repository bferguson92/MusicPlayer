package com.example.musicplayer.view

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import com.example.musicplayer.R
import com.example.musicplayer.service.MediaPlayerService
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    var mediaPlayerService: MediaPlayerService? = null
    private val serviceConnection = object : ServiceConnection{
        override fun onServiceDisconnected(name: ComponentName?) {

        }

        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            mediaPlayerService = (service as MediaPlayerService.MediaPlayerBinder).getMediaPlayerService()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val intent = Intent(this, MediaPlayerService::class.java)

        button_play.setOnClickListener{
            mediaPlayerService?.onPlay()?:{
                bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE)
            }()
        }

        button_pause.setOnClickListener{
            mediaPlayerService?.onPause()
        }

        button_stop.setOnClickListener{
            if(mediaPlayerService != null){
                unbindService(serviceConnection)
                mediaPlayerService = null
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        unbindService(serviceConnection)
    }
}
