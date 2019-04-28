package com.github.kornilovmikhail.spoticloud.navigation.cicerone.screens

import androidx.fragment.app.Fragment
import com.github.kornilovmikhail.spoticloud.ui.search.SearchFragment
import ru.terrakok.cicerone.android.support.SupportAppScreen

class SearchScreen: SupportAppScreen() {

    override fun getFragment(): Fragment = SearchFragment()
}
