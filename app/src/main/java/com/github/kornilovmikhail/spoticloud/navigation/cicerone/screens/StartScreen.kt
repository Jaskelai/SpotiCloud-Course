package com.github.kornilovmikhail.spoticloud.navigation.cicerone.screens

import androidx.fragment.app.Fragment
import com.github.kornilovmikhail.spoticloud.ui.start.StartFragment
import ru.terrakok.cicerone.android.support.SupportAppScreen

class StartScreen: SupportAppScreen() {

    override fun getFragment(): Fragment = StartFragment.getInstance()
}
