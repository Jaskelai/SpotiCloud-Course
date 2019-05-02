package com.github.kornilovmikhail.spoticloud.ui.tracklist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.arellomobile.mvp.MvpAppCompatFragment
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter

import com.github.kornilovmikhail.spoticloud.R
import com.github.kornilovmikhail.spoticloud.app.App
import com.github.kornilovmikhail.spoticloud.core.model.Track
import kotlinx.android.synthetic.main.fragment_track_list.*
import javax.inject.Inject

class TrackListFragment : MvpAppCompatFragment(), TrackListView {

    @Inject
    @InjectPresenter
    lateinit var trackListPresenter: TrackListPresenter

    @ProvidePresenter
    fun getPresenter(): TrackListPresenter = trackListPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        App.component
            .trackListSubComponentBuilder()
            .build()
            .inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_track_list, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerViewAdapter()
    }

    override fun onDestroy() {
        super.onDestroy()
        trackListPresenter.onCleared()
    }

    override fun showErrorMessage() {
        Toast.makeText(context, getString(R.string.error), Toast.LENGTH_SHORT).show()
    }

    override fun showTracks(tracks: List<Track>) {
        (rv_list_tracks.adapter as TrackListAdapter).submitList(tracks)
    }

    private fun initRecyclerViewAdapter() {
        if (rv_list_tracks.adapter == null) {
            rv_list_tracks.adapter = TrackListAdapter(trackClickListener)
            rv_list_tracks.layoutManager = LinearLayoutManager(context)
            rv_list_tracks.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        }
    }

    override fun showProgressBar() {
        progressBar_list.visibility = View.VISIBLE
    }

    override fun hideProgressBar() {
        progressBar_list.visibility = View.INVISIBLE
    }

    private val trackClickListener: (Track?) -> Unit = {
        trackListPresenter.onTrackClicked(it)
    }
}
