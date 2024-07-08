package com.alepagani.codechallengemovies.presentation.home.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat.getColor
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.alepagani.codechallengemovies.R
import com.alepagani.codechallengemovies.data.model.Movie
import com.alepagani.codechallengemovies.databinding.MovieItemSearchBinding
import com.alepagani.codechallengeyape.core.BaseViewHolder
import com.bumptech.glide.Glide

class MovieSearchAdapter(
    private val itemClickListener: onMovieSearchClickListener
) : PagingDataAdapter<Movie, MovieSearchAdapter.MovieViewHolder>(DIFF_UTILS) {

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

    interface onMovieSearchClickListener {
        fun onMovieSearchClick(movie: Movie)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val itemBinding = MovieItemSearchBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val holder = MovieViewHolder(itemBinding, parent.context)

        itemBinding.root.setOnClickListener {
            val position = holder.adapterPosition.takeIf { it != DiffUtil.DiffResult.NO_POSITION }
                ?: return@setOnClickListener
            getItem(position)?.let { it1 -> itemClickListener.onMovieSearchClick(it1) }
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
        genres = genresList
        notifyDataSetChanged()
    }

    inner class MovieViewHolder(
        val binding: MovieItemSearchBinding,
        val context: Context
    ) : BaseViewHolder<Movie>(binding.root) {
        override fun bind(item: Movie) {
            Glide.with(context)
                .load("https://image.tmdb.org/t/p/w500/${item.poster_path}")
                .centerCrop()
                .into(binding.imgMovie)

            binding.apply {
                txtMovieName.setText(item.title)
                if (genres.size >= 1) {
                    if (item.genre_ids.isNotEmpty()) {
                        item.genre_ids.first()?.let { genreId ->
                            genres.get(genreId)?.let {
                                txtMovieGenre.setText(it)
                            }
                        } ?: txtMovieGenre.setText("")
                    } else {
                        txtMovieGenre.setText("")
                    }
                } else {
                    txtMovieGenre.setText("")
                }

                if (item.is_liked == true) {
                    txtLiked.setText(R.string.txt_added)
                    txtLiked.setBackgroundResource(R.drawable.bg_liked_search)
                    txtLiked.setTextColor(getColor(context, R.color.background))
                } else {
                    txtLiked.setText(R.string.txt_add)
                    txtLiked.setBackgroundResource(R.drawable.bg_unliked_search)
                    txtLiked.setTextColor(getColor(context, R.color.background_liked_search))
                }
            }
        }
    }
}