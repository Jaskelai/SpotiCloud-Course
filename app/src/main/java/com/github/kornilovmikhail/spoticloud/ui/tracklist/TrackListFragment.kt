package com.github.kornilovmikhail.spoticloud.ui.tracklist

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.arellomobile.mvp.MvpAppCompatFragment
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.github.kornilovmikhail.spoticloud.R

import com.github.kornilovmikhail.spoticloud.app.App
import com.github.kornilovmikhail.spoticloud.core.model.Track
import com.github.kornilovmikhail.spoticloud.ui.main.CallbackFromFragments
import kotlinx.android.synthetic.main.fragment_track_list.*
import javax.inject.Inject


class TrackListFragment : MvpAppCompatFragment(), TrackListView {

    @Inject
    @InjectPresenter
    lateinit var trackListPresenter: TrackListPresenter

    @ProvidePresenter
    fun getPresenter(): TrackListPresenter = trackListPresenter

    private var callback: CallbackFromFragments? = null

    private lateinit var trackListAdapter: TrackListAdapter

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

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        callback = context as CallbackFromFragments
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        initRecyclerViewAdapter()
    }

    override fun onDetach() {
        super.onDetach()
        callback = null
    }

    override fun onDestroy() {
        super.onDestroy()
        trackListPresenter.onCleared()
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.toolbar_menu, menu)
        val searchedMenuView = menu?.findItem(R.id.action_search)
        val searchView = searchedMenuView?.actionView as SearchView
        addSearchListener(searchView)
    }

    override fun showErrorMessage() {
        Toast.makeText(context, getString(R.string.error), Toast.LENGTH_SHORT).show()
    }

    override fun showTracks(tracks: List<Track>) {
        trackListAdapter.submitList(tracks as MutableList<Track>)
    }

    override fun showProgressBar() {
        progressBar_list.visibility = View.VISIBLE
    }

    override fun hideProgressBar() {
        progressBar_list.visibility = View.INVISIBLE
    }

    override fun showEmptyTracksMessage() {
        Toast.makeText(context, getString(R.string.empty_list), Toast.LENGTH_SHORT).show()
    }

    private fun initRecyclerViewAdapter() {
        if (rv_list_tracks.adapter == null) {
            trackListAdapter = TrackListAdapter(trackClickListener)
            rv_list_tracks.adapter = trackListAdapter
            rv_list_tracks.layoutManager = LinearLayoutManager(context)
            rv_list_tracks.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        }
    }

    private fun addSearchListener(searchView: SearchView) {
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                trackListAdapter.filter(query)
                rv_list_tracks.adapter = trackListAdapter
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                trackListAdapter.filter(newText)
                rv_list_tracks.adapter = trackListAdapter
                return true
            }
        })
    }


    override fun sendTrackToPlayer(track: Track?) {
        callback?.sendTrackToPlayer(track)
    }

    private val trackClickListener: (Track?) -> Unit = {
        trackListPresenter.onTrackClicked(it)
    }
}
