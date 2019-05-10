package com.github.kornilovmikhail.spoticloud.musicplayer

import android.content.ComponentName
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.os.Message
import android.os.Messenger

class MusicServiceConnection : ServiceConnection {

    private var musicService: MusicService? = null
    private var isBind: Boolean = false
    var messengerService: Messenger? = null

    override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
        messengerService = Messenger(service)
        isBind = true
    }

    override fun onServiceDisconnected(component: ComponentName?) {
        messengerService = null
        isBind = false
    }

    fun getService(): MusicService? = musicService

    fun isBind(): Boolean = isBind

    fun setIsBind(isBind: Boolean) {
        this.isBind = isBind
    }

    companion object {
        const val MESSAGE_TYPE_FOOTER_INIT = 7865
        const val MESSAGE_TYPE_FOOTER_RESPONSE = 7866
        const val MESSAGE_TYPE_FOOTER_RESUME = 7867
        const val MESSAGE_TYPE_FOOTER_PAUSE = 7868
        const val MESSAGE_LINK_COVER_TRACK = "COVER_IMAGE"
        const val MESSAGE_TITLE_TRACK = "TRACK_TITLE"
    }
}
