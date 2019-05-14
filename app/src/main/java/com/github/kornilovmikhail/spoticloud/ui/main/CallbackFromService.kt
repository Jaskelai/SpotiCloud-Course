package com.github.kornilovmikhail.spoticloud.ui.main

import com.github.kornilovmikhail.spoticloud.core.model.StreamServiceEnum

interface CallbackFromService {

    fun updateFooter(title: String?, imgLink: String?)

    fun updatePlayer(title: String?, author: String?, imgLink: String?, source: StreamServiceEnum, duration: Long)

    fun updateSeekBar(position: Int)
}
