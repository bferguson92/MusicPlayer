package com.example.musicplayer.view

import android.content.*
import android.database.Cursor
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.provider.MediaStore
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.musicplayer.R
import com.example.musicplayer.adapter.MusicDisplayAdapter
import com.example.musicplayer.service.MediaPlayerService
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    var mediaPlayerService: MediaPlayerService? = null
    private val musicList = mutableListOf<String>()

    private val serviceConnection = object : ServiceConnection {
        override fun onServiceDisconnected(name: ComponentName?) {

        }

        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            mediaPlayerService =
                (service as MediaPlayerService.MediaPlayerBinder).getMediaPlayerService()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
       // val intent = Intent(this, MediaPlayerService::class.java)
        getMusic()
        for(i  in musicList){
            Log.d("TAG_X", i)
        }

        recycler_view_music_display.adapter = MusicDisplayAdapter(musicList)
        recycler_view_music_display.layoutManager = LinearLayoutManager(this)

//        button_play.setOnClickListener {
//            mediaPlayerService?.onPlay() ?: {
//                bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE)
//            }()
//        }
//
//        button_pause.setOnClickListener {
//            mediaPlayerService?.onPause()
//        }
//
//        button_stop.setOnClickListener {
//            if (mediaPlayerService != null) {
//                unbindService(serviceConnection)
//                mediaPlayerService = null
//            }
//        }
    }

    override fun onDestroy() {
        super.onDestroy()
        //unbindService(serviceConnection)
    }

    private fun getMusic() {
        val uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
        val projections: Array<out String> = arrayOf(MediaStore.Audio.Media.TITLE)

        val cursor = contentResolver.query(uri, projections, null, null, null, null )

        if(cursor != null){
            cursor.moveToFirst()
            do {
                musicList.add(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE)))
            } while (cursor.moveToNext())
        }

        cursor?.close()
    }

}
