package com.github.kornilovmikhail.spoticloud.navigation.cicerone.screens

import androidx.fragment.app.Fragment
import com.github.kornilovmikhail.spoticloud.ui.trends.TrendsFragment
import ru.terrakok.cicerone.android.support.SupportAppScreen

class TrendsScreen: SupportAppScreen() {

    override fun getFragment(): Fragment = TrendsFragment()
}
