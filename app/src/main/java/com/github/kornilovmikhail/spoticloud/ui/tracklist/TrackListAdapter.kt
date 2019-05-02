package com.github.kornilovmikhail.spoticloud.ui.tracklist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.github.kornilovmikhail.spoticloud.R
import com.github.kornilovmikhail.spoticloud.core.model.Track
import com.squareup.picasso.Picasso
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.track_list_item.*
import kotlinx.android.synthetic.main.track_list_item.view.*

class TrackListAdapter(
    private val clickListener: (Track?) -> Unit
) : ListAdapter<Track, TrackListAdapter.TrackHolder>(TrackDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, type: Int): TrackHolder {
        return TrackHolder(LayoutInflater.from(parent.context).inflate(R.layout.track_list_item, parent, false))
    }

    override fun onBindViewHolder(holder: TrackHolder, position: Int) {
        holder.bind(getItem(position), clickListener)
    }

    class TrackDiffCallback : DiffUtil.ItemCallback<Track>() {
        override fun areItemsTheSame(oldItem: Track, newItem: Track): Boolean = oldItem.title == newItem.title

        override fun areContentsTheSame(oldItem: Track, newItem: Track): Boolean = oldItem == newItem

    }

    class TrackHolder(
        override val containerView: View
    ) : RecyclerView.ViewHolder(containerView), LayoutContainer {

        fun bind(track: Track?, clickListener: (Track?) -> Unit) {
            with(containerView) {
                tv_list_track_item_title.text = track?.title
                tv_list_track_item_author.text = track?.author?.username
                setOnClickListener { clickListener(track) }
            }
            Picasso.get()
                .load(track?.artworkUrl)
                .placeholder(R.drawable.placeholder_music_notes)
                .into(iv_list_track_item_cover)
        }
    }
}
