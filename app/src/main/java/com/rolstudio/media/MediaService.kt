package com.rolstudio.media

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.IBinder

class MediaService: Service() {
    private lateinit var player: MediaPlayer


    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        player = MediaPlayer.create(this, R.raw.music)
        player.start()
        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        player.stop()
    }


    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

}