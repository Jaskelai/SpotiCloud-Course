package com.github.kornilovmikhail.spoticloud.ui.start

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter

import com.github.kornilovmikhail.spoticloud.R
import com.github.kornilovmikhail.spoticloud.app.App
import kotlinx.android.synthetic.main.fragment_start.*
import javax.inject.Inject

class StartFragment : Fragment(), StartView {
    @Inject
    @InjectPresenter
    lateinit var startPresenter: StartPresenter

    @ProvidePresenter
    fun getPresenter(): StartPresenter = startPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.component
            .startSubComponentBuilder()
            .build()
            .inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_start, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btn_start_soundcloud.setOnClickListener { startPresenter.showSouncloudLoginPage() }
        btn_start_spotify.setOnClickListener { startPresenter.showSpotifyLoginPage() }
    }

    companion object {
        fun getInstance(): StartFragment = StartFragment()
    }
}
