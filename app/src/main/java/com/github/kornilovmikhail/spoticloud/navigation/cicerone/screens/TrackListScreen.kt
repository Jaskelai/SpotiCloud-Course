package com.github.kornilovmikhail.spoticloud.navigation.cicerone.screens

import androidx.fragment.app.Fragment
import com.github.kornilovmikhail.spoticloud.ui.tracklist.TrackListFragment
import ru.terrakok.cicerone.android.support.SupportAppScreen

class TrackListScreen: SupportAppScreen() {

    override fun getFragment(): Fragment = TrackListFragment()
}
