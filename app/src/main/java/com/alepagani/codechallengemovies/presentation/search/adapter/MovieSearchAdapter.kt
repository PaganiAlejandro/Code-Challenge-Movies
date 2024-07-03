package com.alepagani.codechallengemovies.presentation.home.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat.getColor
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.alepagani.codechallengemovies.R
import com.alepagani.codechallengemovies.data.model.MovieGenre
import com.alepagani.codechallengemovies.databinding.MovieItemSearchBinding
import com.alepagani.codechallengeyape.core.BaseViewHolder
import com.bumptech.glide.Glide

class MovieSearchAdapter(
    private val movielist: List<MovieGenre>,
    private val itemClickListener: onMovieSearchClickListener
) : RecyclerView.Adapter<BaseViewHolder<*>>() {

    private var movies: List<MovieGenre> = movielist

    interface onMovieSearchClickListener {
        fun onMovieSearchClick(movie: MovieGenre)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        val itemBinding = MovieItemSearchBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val holder = MovieViewHolder(itemBinding, parent.context)

        itemBinding.root.setOnClickListener {
            val position = holder.adapterPosition.takeIf { it != DiffUtil.DiffResult.NO_POSITION }
                ?: return@setOnClickListener
            itemClickListener.onMovieSearchClick(movies[position])
        }
        return holder
    }

    override fun getItemCount() = movies.size

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        when (holder) {
            is MovieViewHolder -> holder.bind(movies[position])
        }
    }

    fun updateList(newList: List<MovieGenre>) {
        movies = newList
        notifyDataSetChanged()
    }

    private inner class MovieViewHolder(
        val binding: MovieItemSearchBinding,
        val context: Context
    ) : BaseViewHolder<MovieGenre>(binding.root) {
        override fun bind(item: MovieGenre) {
            Glide.with(context)
                .load("https://image.tmdb.org/t/p/w500/${item.poster_path}")
                .centerCrop()
                .into(binding.imgMovie)

            binding.apply {
                txtMovieName.setText(item.title)
                txtMovieGenre.setText(item.genre)

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