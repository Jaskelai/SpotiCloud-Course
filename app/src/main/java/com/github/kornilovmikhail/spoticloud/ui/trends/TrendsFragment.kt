package com.github.kornilovmikhail.spoticloud.ui.trends

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
import com.github.kornilovmikhail.spoticloud.ui.main.CallbackFromFragments
import kotlinx.android.synthetic.main.fragment_trends.*
import javax.inject.Inject

class TrendsFragment : MvpAppCompatFragment(), TrendsView {

    @Inject
    @InjectPresenter
    lateinit var trendsPresenter: TrendsPresenter

    @ProvidePresenter
    fun getPresenter(): TrendsPresenter = trendsPresenter

    private var callback: CallbackFromFragments? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        App.component
            .trendsSubComponentBuilder()
            .build()
            .inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_trends, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        callback = context as CallbackFromFragments
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerViewAdapter()
    }

    override fun onDetach() {
        super.onDetach()
        callback = null
    }

    override fun onDestroy() {
        super.onDestroy()
        trendsPresenter.onCleared()
    }

    override fun showTrendsTracks(tracks: List<Track>) {
        (rv_trends.adapter as TrackTrendsListAdapter).submitList(tracks)
    }

    override fun showProgressBar() {
        progressBar_trends.visibility = View.VISIBLE
    }

    override fun hideProgressBar() {
        progressBar_trends.visibility = View.INVISIBLE
    }

    override fun showErrorMessage() {
        Toast.makeText(context, getString(R.string.error), Toast.LENGTH_SHORT).show()
    }

    override fun showTrackAddedMessage() {
        Toast.makeText(context, getString(R.string.track_added_to_fav), Toast.LENGTH_SHORT).show()
    }

    override fun sendTrackToPlayer(track: Track?) {
        callback?.sendTrackToPlayer(track)
    }

    private fun initRecyclerViewAdapter() {
        if (rv_trends.adapter == null) {
            rv_trends.adapter = TrackTrendsListAdapter(trackClickListener, trackAddToFavLongClickListener)
            rv_trends.layoutManager = LinearLayoutManager(context)
            rv_trends.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        }
    }

    private val trackClickListener: (Track?) -> Unit = {
        trendsPresenter.onTrackClicked(it)
    }

    private val trackAddToFavLongClickListener: (Track?) -> Unit = {
        trendsPresenter.trackAddToFav(it)
    }
}
