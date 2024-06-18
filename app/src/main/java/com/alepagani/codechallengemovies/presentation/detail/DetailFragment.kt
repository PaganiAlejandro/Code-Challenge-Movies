package com.alepagani.codechallengemovies.presentation.detail

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.alepagani.codechallengemovies.R
import com.alepagani.codechallengemovies.core.getYearFromReleaseDate
import com.alepagani.codechallengemovies.data.local.entity.MovieEntity
import com.alepagani.codechallengemovies.data.model.Movie
import com.alepagani.codechallengemovies.databinding.FragmentDetailBinding
import com.alepagani.codechallengemovies.presentation.home.HomeViewModel
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetailFragment : Fragment(R.layout.fragment_detail) {
    private lateinit var binding: FragmentDetailBinding
    private val args by navArgs<DetailFragmentArgs>()
    private val viewmodel by viewModels<DetailViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentDetailBinding.bind(view)
        initUI()
    }

    private fun initUI() {
        val movie = args.movieArgument
        viewmodel.setMovie(movie)

        binding.apply {
            Glide.with(requireContext())
                .load("https://image.tmdb.org/t/p/w500/${movie.backdrop_path}")
                .centerCrop()
                .into(imageBackground)

            Glide.with(requireContext())
                .load("https://image.tmdb.org/t/p/w500/${movie.poster_path}")
                .centerCrop()
                .into(imageCentered)

            textTitle.setText(movie.title)
            textYear.setText(movie.release_date.getYearFromReleaseDate())
            textOverview.setText(movie.overview)
            imageBack.setOnClickListener { findNavController().popBackStack() }

            buttonAction.setOnClickListener {
                viewmodel.saveMovieLiked()
            }
            viewmodel.isLiked.observe( viewLifecycleOwner, {
                buttonAction.setText(if (it) "SUBSCRIPTO" else "SUBSCRIMIRME")
            })
        }
    }
}