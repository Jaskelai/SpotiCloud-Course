package com.github.kornilovmikhail.spoticloud.ui.tracklist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.MvpAppCompatFragment
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter

import com.github.kornilovmikhail.spoticloud.R
import javax.inject.Inject

class TrackListFragment : MvpAppCompatFragment(), TrackListView {

    @Inject
    @InjectPresenter
    lateinit var trackListPresenter: TrackListPresenter

    @ProvidePresenter
    fun getPresenter(): TrackListPresenter = TrackListPresenter()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_track_list, container, false)
}
