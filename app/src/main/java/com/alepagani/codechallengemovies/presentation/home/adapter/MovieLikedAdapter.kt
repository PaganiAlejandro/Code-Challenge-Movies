package com.alepagani.codechallengemovies.presentation.home.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.alepagani.codechallengemovies.data.model.MovieGenre
import com.alepagani.codechallengemovies.databinding.MovieItemLikedBinding
import com.alepagani.codechallengeyape.core.BaseViewHolder
import com.bumptech.glide.Glide

class MovieLikedAdapter(
    private val movielist: List<MovieGenre>,
    private val itemClickListener: onMovieLikedClickListener
) : RecyclerView.Adapter<BaseViewHolder<*>>() {

    private var movieResponses: List<MovieGenre> = movielist

    interface onMovieLikedClickListener {
        fun onMovieLikedClick(movieResponse: MovieGenre)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        val itemBinding = MovieItemLikedBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val holder = MovieViewHolder(itemBinding, parent.context)

        itemBinding.root.setOnClickListener {
            val position = holder.adapterPosition.takeIf { it != DiffUtil.DiffResult.NO_POSITION }
                ?: return@setOnClickListener
            itemClickListener.onMovieLikedClick(movieResponses[position])
        }
        return holder
    }

    override fun getItemCount() = movieResponses.size

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        when (holder) {
            is MovieViewHolder -> holder.bind(movieResponses[position])
        }
    }

    fun updateList(newList: List<MovieGenre>) {
        movieResponses = newList
        notifyDataSetChanged()
    }

    private inner class MovieViewHolder(
        val binding: MovieItemLikedBinding,
        val context: Context
    ) : BaseViewHolder<MovieGenre>(binding.root) {
        override fun bind(item: MovieGenre) {
            Glide.with(context)
                .load("https://image.tmdb.org/t/p/w500/${item.poster_path}")
                .centerCrop()
                .into(binding.imgMovie)
        }
    }
}