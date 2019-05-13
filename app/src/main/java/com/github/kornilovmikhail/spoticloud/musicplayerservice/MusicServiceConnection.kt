package com.github.kornilovmikhail.spoticloud.musicplayerservice

import android.content.ComponentName
import android.content.ServiceConnection
import android.os.IBinder
import android.os.Messenger

class MusicServiceConnection : ServiceConnection {

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

    fun isBind(): Boolean = isBind

    fun setIsBind(isBind: Boolean) {
        this.isBind = isBind
    }

    companion object {
        const val MESSAGE_TYPE_FOOTER_INIT = 7865
        const val MESSAGE_TYPE_FOOTER_RESPONSE = 7866
        const val MESSAGE_TYPE_PLAYER_STARTED = 7871
        const val MESSAGE_TYPE_PLAYER_RESPONSE = 7872
        const val MESSAGE_TYPE_RESUME = 7867
        const val MESSAGE_TYPE_PAUSE = 7868
        const val MESSAGE_TYPE_NEXT = 7869
        const val MESSAGE_TYPE_PREV = 7870
        const val MESSAGE_LINK_COVER_TRACK = "COVER_IMAGE"
        const val MESSAGE_TITLE_TRACK = "TRACK_TITLE"
        const val MESSAGE_AUTHOR_TRACK = "TRACK_AUTHOR"
        const val MESSAGE_SOURCE_TRACK = "TARCK_SOURCE"
    }
}
