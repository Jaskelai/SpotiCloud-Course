package com.github.kornilovmikhail.spoticloud.navigation.cicerone.screens

import androidx.fragment.app.Fragment
import com.github.kornilovmikhail.spoticloud.ui.musicplayer.MusicPlayerFragment
import ru.terrakok.cicerone.android.support.SupportAppScreen

class MusicPlayerScreen: SupportAppScreen() {

    override fun getFragment(): Fragment = MusicPlayerFragment()
}
