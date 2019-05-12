package com.github.kornilovmikhail.spoticloud.ui.musicplayer

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.MvpAppCompatFragment
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter

import com.github.kornilovmikhail.spoticloud.R
import com.github.kornilovmikhail.spoticloud.app.App
import com.github.kornilovmikhail.spoticloud.ui.main.CallbackFromFragments
import javax.inject.Inject

class MusicPlayerFragment : MvpAppCompatFragment(), MusicPlayerView {

    @Inject
    @InjectPresenter
    lateinit var musicPlayerPresenter: MusicPlayerPresenter

    @ProvidePresenter
    fun getPresenter(): MusicPlayerPresenter = musicPlayerPresenter

    private var callback: CallbackFromFragments? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        App.component
            .musicPlayerSubComponentBuilder()
            .build()
            .inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_music_player, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        callback = context as CallbackFromFragments
    }

    override fun onDetach() {
        super.onDetach()
        callback = null
    }

    override fun onDestroy() {
        super.onDestroy()
        musicPlayerPresenter.onCleared()
    }

}
