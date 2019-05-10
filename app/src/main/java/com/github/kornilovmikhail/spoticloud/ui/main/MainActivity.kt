package com.github.kornilovmikhail.spoticloud.ui.main

import android.os.Bundle
import android.os.Message
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.github.kornilovmikhail.spoticloud.app.App
import com.github.kornilovmikhail.spoticloud.core.model.Track
import com.github.kornilovmikhail.spoticloud.musicplayer.MusicServiceConnection
import com.github.kornilovmikhail.spoticloud.musicplayer.MusicServiceHelper
import com.github.kornilovmikhail.spoticloud.navigation.cicerone.MySupportAppNavigator
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject
import android.os.Handler
import android.os.Messenger
import com.github.kornilovmikhail.spoticloud.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.footer_player.*


class MainActivity : MvpAppCompatActivity(), MainView, CallbackFromFragments, CallbackFromService {
    @Inject
    @InjectPresenter
    lateinit var mainPresenter: MainPresenter

    @Inject
    lateinit var musicServiceHelper: MusicServiceHelper

    @Inject
    lateinit var musicServiceConnection: MusicServiceConnection

    private val navigator = MySupportAppNavigator(this, R.id.main_container)

    private lateinit var bottomNav: BottomNavigationView

    private val messenger: Messenger = Messenger(MessageHandler(this))

    @ProvidePresenter
    fun getPresenter(): MainPresenter = mainPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        App.component
            .mainSubComponentBuilder()
            .withActivity(this)
            .build()
            .inject(this)
        super.onCreate(savedInstanceState)
        mainPresenter.onCreate()
        setContentView(R.layout.activity_main)
    }

    override fun onStart() {
        super.onStart()
        musicServiceHelper.doBindService(this, musicServiceConnection)
        if (musicServiceHelper.isServiceStarted()) {
            mainPresenter.showTrack()
        }
    }

    override fun onResume() {
        super.onResume()
        mainPresenter.setNavigator(navigator)
    }

    override fun onPause() {
        super.onPause()
        mainPresenter.detachNavigator()
    }

    override fun onStop() {
        super.onStop()
        musicServiceHelper.doUnbindService(this, musicServiceConnection)
    }

    override fun onDestroy() {
        super.onDestroy()
        mainPresenter.onCleared()
    }

    override fun onBackPressed() {
        mainPresenter.onBackPressed()
    }

    override fun showErrorMessage() {
        Toast.makeText(this, getString(R.string.error), Toast.LENGTH_SHORT).show()
    }

    override fun showBottomBar() {
        enableBottomNavBar()
    }

    override fun enableBottomNavBar() {
        if (vs_bottom_nav != null) {
            bottomNav = vs_bottom_nav.inflate() as BottomNavigationView
            showTrackListChose()
            bottomNav.setOnNavigationItemSelectedListener {
                when (it.itemId) {
                    R.id.bottom_action_search -> mainPresenter.onBottomSearchClicked()
                    R.id.bottom_action_tracks -> mainPresenter.onBottomTracksClicked()
                    R.id.bottom_action_trends -> mainPresenter.onBottomTrendsClicked()
                }
                true
            }
        }
    }

    override fun showToolbar() {
        enableToolbar()
    }

    override fun enableToolbar() {
        if (vs_toolbar != null) {
            val toolbarView = vs_toolbar.inflate()
            setSupportActionBar(toolbarView as Toolbar?)
        }
    }

    override fun showSearchChose() {
        bottomNav.selectedItemId = R.id.bottom_action_search
    }

    override fun showTrackListChose() {
        bottomNav.selectedItemId = R.id.bottom_action_tracks
    }

    override fun showTrendsChose() {
        bottomNav.selectedItemId = R.id.bottom_action_trends
    }

    override fun sendTrackToPlayer(track: Track?) {
        musicServiceHelper.startService(this, track)
        mainPresenter.showTrack()
    }

    override fun showFooter() {
        vs_footer_player?.let {
            vs_footer_player.inflate()
        }
        val message = Message.obtain(null, MusicServiceConnection.MESSAGE_TYPE_FOOTER_INIT)
        message.replyTo = messenger
        musicServiceConnection.messengerService?.send(message)
        addFooterListeners()
    }

    override fun updateFooter(title: String?, imgLink: String?) {
        tv_footer_player_title.text = title
        Picasso.get()
            .load(imgLink)
            .placeholder(R.drawable.placeholder_music_notes)
            .into(iv_footer_player_cover)
    }

    private fun addFooterListeners() {
        btn_footer_player_play_pause.setOnClickListener {
            val message = if (btn_footer_player_play_pause.isChecked) {
                Message.obtain(null, MusicServiceConnection.MESSAGE_TYPE_FOOTER_PAUSE)
            } else {
                Message.obtain(null, MusicServiceConnection.MESSAGE_TYPE_FOOTER_RESUME)
            }
            musicServiceConnection.messengerService?.send(message)
        }
    }

    class MessageHandler(private val callback: CallbackFromService) : Handler() {
        override fun handleMessage(message: Message) {
            when (message.what) {
                MusicServiceConnection.MESSAGE_TYPE_FOOTER_RESPONSE -> {
                    val data = message.data
                    val title = data.getString(MusicServiceConnection.MESSAGE_TITLE_TRACK)
                    val cover = data.getString(MusicServiceConnection.MESSAGE_LINK_COVER_TRACK)
                    callback.updateFooter(title, cover)
                }
            }
        }
    }
}
