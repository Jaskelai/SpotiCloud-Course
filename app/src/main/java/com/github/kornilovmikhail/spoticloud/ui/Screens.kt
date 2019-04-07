package com.github.kornilovmikhail.spoticloud.ui

import androidx.fragment.app.Fragment
import com.github.kornilovmikhail.spoticloud.ui.start.StartFragment
import ru.terrakok.cicerone.android.support.SupportAppScreen

class Screens {
    class StartScreen : SupportAppScreen() {
        override fun getFragment(): Fragment = StartFragment.getInstance()
    }
}
