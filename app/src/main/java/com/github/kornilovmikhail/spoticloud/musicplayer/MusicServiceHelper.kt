package com.github.kornilovmikhail.spoticloud.musicplayer

import android.content.Context
import android.content.Intent
import com.github.kornilovmikhail.spoticloud.core.model.Track

class MusicServiceHelper {

    fun doBindService(context: Context?, serviceConnection: MusicServiceConnection) {
        if (!serviceConnection.isBind() && context != null) {
            val intent = Intent(context, MusicService::class.java)
            context.bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE)
            serviceConnection.setIsBind(true)
        }
    }

    fun doUnbindService(context: Context?, serviceConnection: MusicServiceConnection) {
        if (serviceConnection.isBind() && context != null) {
            context.unbindService(serviceConnection)
            serviceConnection.setIsBind(false)
        }
    }

    fun startService(context: Context?, track: Track?) {
        context?.let {
            val musicServiceIntent = Intent(context, MusicService::class.java)
            musicServiceIntent.putExtra(TRACK_ID, track?.id)
            context.startService(musicServiceIntent)
        }
    }

    fun stopService(context: Context?) {
        context?.let {
            context.stopService(Intent(context, MusicService::class.java))
        }
    }

    fun isServiceStarted(): Boolean = MusicService.isServiceStarted

    companion object {
        const val TRACK_ID = "TRACK_ID"
        const val TRACK_URL = "TRACK_URL"
    }
}
