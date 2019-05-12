package com.github.kornilovmikhail.spoticloud.musicplayerservice

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

    fun startMusicService(context: Context?, track: Track?) {
        context?.let {
            val musicServiceIntent = Intent(context, MusicService::class.java)
            musicServiceIntent.putExtra(TRACK_ID, track?.id)
            musicServiceIntent.putExtra(TRACK_URL, track?.streamUrl)
            musicServiceIntent.putExtra(TRACK_COVER_LINK, track?.artworkLowSizeUrl ?: track?.artworkUrl)
            musicServiceIntent.putExtra(TRACK_SOURCE, track?.streamService?.name)
            musicServiceIntent.putExtra(TRACK_TITLE, track?.title)
            musicServiceIntent.putExtra(TRACK_AUTHOR, track?.author?.username)
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
        const val TRACK_COVER_LINK = "TRACK_COVER_LINK"
        const val TRACK_SOURCE = "TRACK_SOURCE"
        const val TRACK_TITLE = "TRACK_TITLE"
        const val TRACK_AUTHOR = "TRACK_AUTHOR"
    }
}
