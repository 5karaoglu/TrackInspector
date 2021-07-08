package com.besirkaraoglu.trackinspector.presentation.ui.favorites

import android.content.Context
import android.text.SpannableStringBuilder
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.besirkaraoglu.trackinspector.core.BaseViewHolder
import com.besirkaraoglu.trackinspector.data.entity.Track
import com.besirkaraoglu.trackinspector.databinding.FavoriteRecyclerItemBinding
import com.besirkaraoglu.trackinspector.util.setFav
import com.besirkaraoglu.trackinspector.util.tsToString
import com.squareup.picasso.Picasso


class FavoritesAdapter(
    private val context: Context,
    private val itemClickListener: OnTrackClickListener
): RecyclerView.Adapter<BaseViewHolder<Track>>() {

    private var trackList = listOf<Track>()
    private var favList = listOf<Track>()

    interface OnTrackClickListener {
        fun onTrackClick(track: Track, position: Int)
        fun onFavButtonClicked(track: Track, position: Int)
    }

    fun setTrackList(trackList: List<Track>){
        this.trackList = trackList
        notifyDataSetChanged()
    }

    fun setFavList(favList: List<Track>){
        this.favList = favList
        if(trackList.isNotEmpty()) notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<Track> {
        val itemBinding = FavoriteRecyclerItemBinding.inflate(LayoutInflater.from(context),parent,false)

        val holder = MainViewHolder(itemBinding)

        itemBinding.root.setOnClickListener {
            val position = holder.adapterPosition.takeIf { it != RecyclerView.NO_POSITION } ?: return@setOnClickListener
            itemClickListener.onTrackClick(trackList[position],position)
        }
        itemBinding.ivFav.setOnClickListener {
            val position = holder.adapterPosition.takeIf { it != RecyclerView.NO_POSITION } ?: return@setOnClickListener
            itemClickListener.onFavButtonClicked(trackList[position],position)
        }
        return holder
    }

    override fun onBindViewHolder(holder: BaseViewHolder<Track>, position: Int) {
        when(holder){
            is MainViewHolder -> holder.bind(trackList[position])
        }
    }

    override fun getItemCount() = trackList.size

    private inner class MainViewHolder(
        private val binding: FavoriteRecyclerItemBinding
    ): BaseViewHolder<Track>(binding.root){
        override fun bind(item: Track) = with(binding) {
            Picasso.get()
                .load(item.thumbnail)
                .fit()
                .centerInside().into(binding.iv)

            tvArtist.text = item.artists
            tvDur.text = SpannableStringBuilder()
                .append("Duration: ")
                .append(tsToString(item.durationMS))
            tvPop.text = SpannableStringBuilder()
                .append(item.popularity.toString())
                .append("/100")
            tv.text = item.name

            ivFav.setFav { favList.contains(item) }
        }

    }

}