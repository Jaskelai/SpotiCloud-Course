package com.github.kornilovmikhail.spoticloud.musicplayer

import android.os.Messenger

interface CallbackMessageService {

    fun sendFooterInit(messenger: Messenger)

    fun pausePlaying()

    fun resumePlaying()
}
