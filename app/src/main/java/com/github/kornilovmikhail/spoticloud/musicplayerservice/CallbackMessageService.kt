package com.github.kornilovmikhail.spoticloud.musicplayerservice

import android.os.Messenger

interface CallbackMessageService {

    fun sendFooterUpdate(messenger: Messenger)

    fun pausePlaying()

    fun resumePlaying()

    fun playPrev(messenger: Messenger)

    fun playNext(messenger: Messenger)
}
