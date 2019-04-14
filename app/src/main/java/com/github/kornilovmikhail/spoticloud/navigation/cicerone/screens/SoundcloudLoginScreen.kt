package com.github.kornilovmikhail.spoticloud.navigation.cicerone.screens

import androidx.fragment.app.Fragment
import com.github.kornilovmikhail.spoticloud.ui.loginsoundcloud.SoundcloudLoginFragment
import ru.terrakok.cicerone.android.support.SupportAppScreen

class SoundcloudLoginScreen: SupportAppScreen() {

    override fun getFragment(): Fragment = SoundcloudLoginFragment()
}
