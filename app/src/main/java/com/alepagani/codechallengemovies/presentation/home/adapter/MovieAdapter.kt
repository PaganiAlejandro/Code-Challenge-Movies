package com.alepagani.codechallengemovies.presentation.home.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.alepagani.codechallengemovies.data.model.MovieWithGenres
import com.alepagani.codechallengemovies.databinding.MovieItemBinding
import com.alepagani.codechallengeyape.core.BaseViewHolder
import com.bumptech.glide.Glide

class MovieAdapter(
    private val movielist: List<MovieWithGenres>,
    private val itemClickListener: onMovieClickListener
) : RecyclerView.Adapter<BaseViewHolder<*>>() {

    private var movies: List<MovieWithGenres> = movielist

    interface onMovieClickListener {
        fun onMovieClick(movie: MovieWithGenres)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        val itemBinding = MovieItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val holder = MovieViewHolder(itemBinding, parent.context)

        itemBinding.root.setOnClickListener {
            val position = holder.adapterPosition.takeIf { it != DiffUtil.DiffResult.NO_POSITION }
                ?: return@setOnClickListener
            itemClickListener.onMovieClick(movies[position])
        }
        return holder
    }

    override fun getItemCount() = movies.size

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        when (holder) {
            is MovieViewHolder -> holder.bind(movies[position])
        }
    }

    fun updateList(newList: List<MovieWithGenres>) {
        movies = newList
        notifyDataSetChanged()
    }

    private inner class MovieViewHolder(
        val binding: MovieItemBinding,
        val context: Context
    ) : BaseViewHolder<MovieWithGenres>(binding.root) {
        override fun bind(item: MovieWithGenres) {
            Glide.with(context)
                .load("https://image.tmdb.org/t/p/w500/${item.movie.poster_path}")
                .centerCrop()
                .into(binding.imgMovie)
            binding.apply {
                txtMovieName.setText(item.movie.title)
                if (item.genres.size > 0) {
                    item.genres.first()?.let {
                        txtMovieGenre.setText(it.name)
                    }
                }
            }
        }
    }
}