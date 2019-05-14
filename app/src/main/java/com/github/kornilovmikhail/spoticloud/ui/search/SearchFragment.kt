package com.github.kornilovmikhail.spoticloud.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.arellomobile.mvp.MvpAppCompatFragment
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.github.kornilovmikhail.spoticloud.R
import com.github.kornilovmikhail.spoticloud.app.App
import com.github.kornilovmikhail.spoticloud.core.model.Track
import com.github.kornilovmikhail.spoticloud.ui.main.CallbackFromFragments
import kotlinx.android.synthetic.main.fragment_search.*
import javax.inject.Inject

class SearchFragment : MvpAppCompatFragment(), SearchView {

    @Inject
    @InjectPresenter
    lateinit var searchPresenter: SearchPresenter

    @ProvidePresenter
    fun getPresenter(): SearchPresenter = searchPresenter

    private var callback: CallbackFromFragments? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        App.component
            .searchSubComponentBuilder()
            .build()
            .inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_search, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        callback = context as CallbackFromFragments
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerViewAdapter()
        addSearchListener()
    }

    override fun onDetach() {
        super.onDetach()
        callback = null
    }

    override fun onDestroy() {
        super.onDestroy()
        searchPresenter.onCleared()
    }

    override fun showSearchedTracks(tracks: List<Track>) {
        (rv_search.adapter as TrackSearchListAdapter).submitList(tracks)
    }

    override fun showProgressBar() {
        progressBar_search.visibility = View.VISIBLE
    }

    override fun hideProgressBar() {
        progressBar_search.visibility = View.INVISIBLE
    }

    override fun showErrorMessage() {
        Toast.makeText(context, getString(R.string.error), Toast.LENGTH_SHORT).show()
    }

    override fun showNotFoundMessage() {
        Toast.makeText(context, getString(R.string.tracks_not_found), Toast.LENGTH_SHORT).show()
    }

    override fun showTrackAddedMessage() {
        Toast.makeText(context, getString(R.string.track_added_to_fav), Toast.LENGTH_SHORT).show()
    }

    override fun sendTrackToPlayer(track: Track?) {
        callback?.sendTrackToPlayer(track)
    }

    private fun initRecyclerViewAdapter() {
        if (rv_search.adapter == null) {
            rv_search.adapter = TrackSearchListAdapter(trackClickListener, trackAddToFavLongClickListener)
            rv_search.layoutManager = LinearLayoutManager(context)
            rv_search.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        }
    }

    private fun addSearchListener() {
        et_search_track.setOnEditorActionListener { textView, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                searchPresenter.onSearch(textView.text.toString())
                true
            } else {
                false
            }
        }
    }

    private val trackClickListener: (Track?) -> Unit = {
        searchPresenter.onTrackClicked(it)
    }

    private val trackAddToFavLongClickListener: (Track?) -> Unit = {
        searchPresenter.trackAddToFav(it)
    }

}
