package com.alepagani.codechallengemovies.presentation.home.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.alepagani.codechallengemovies.data.model.Movie
import com.alepagani.codechallengemovies.databinding.MovieItemBinding
import com.alepagani.codechallengeyape.core.BaseViewHolder
import com.bumptech.glide.Glide

class MovieAdapter(
    private val itemClickListener: onMovieClickListener
) : PagingDataAdapter<Movie, MovieAdapter.MovieViewHolder>(DIFF_UTILS) {

    private var genres: HashMap<Int, String> = HashMap()

    companion object {
        val DIFF_UTILS = object : DiffUtil.ItemCallback<Movie>() {
            override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
                return oldItem == newItem
            }
        }
    }

    interface onMovieClickListener {
        fun onMovieClick(movie: Movie)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val itemBinding = MovieItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val holder = MovieViewHolder(itemBinding, parent.context)

        itemBinding.root.setOnClickListener {
            val position = holder.adapterPosition.takeIf { it != DiffUtil.DiffResult.NO_POSITION }
                ?: return@setOnClickListener

            getItem(position)?.let {
                itemClickListener.onMovieClick(it)
            }

        }
        return holder
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = getItem(position)
        movie?.let {
            holder.bind(it)
        }
    }

    fun updateGenres(genresList: HashMap<Int, String>) {
        Log.d("ALE ADAPTER", "ACTUALIZO LA LISTA")
        genres = genresList
        notifyDataSetChanged()
    }

    inner class MovieViewHolder(
        val binding: MovieItemBinding,
        val context: Context
    ) : BaseViewHolder<Movie>(binding.root) {
        override fun bind(item: Movie) {
            Glide.with(context)
                .load("https://image.tmdb.org/t/p/w500/${item.poster_path}")
                .centerCrop()
                .into(binding.imgMovie)
            binding.apply {
                txtMovieName.setText(item.title)

                Log.d("ALE ADAPTER", "ADAPTER ${genres.toString()}")
                Log.d("ALE ADAPTER", "ADAPTER ${ item.genre_ids.first()}")

                    txtMovieGenre.setText(genres.get(item.genre_ids.first()))

            }
        }
    }
}