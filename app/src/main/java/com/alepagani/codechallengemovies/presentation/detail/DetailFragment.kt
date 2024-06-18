package com.alepagani.codechallengemovies.presentation.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.alepagani.codechallengemovies.R
import com.alepagani.codechallengemovies.databinding.FragmentDetailBinding
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : Fragment(R.layout.fragment_detail) {
    private lateinit var binding: FragmentDetailBinding
    private val args by navArgs<DetailFragmentArgs>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentDetailBinding.bind(view)
        initUI()
    }

    private fun initUI() {
        binding.apply {
            Glide.with(requireContext())
                .load("https://image.tmdb.org/t/p/w500/${args.backgroundUrl}")
                .centerCrop()
                .into(imageBackground)

            Glide.with(requireContext())
                .load("https://image.tmdb.org/t/p/w500/${args.posterUrl}")
                .centerCrop()
                .into(imageCentered)

            textTitle.setText(args.title)
            textYear.setText(args.year)
            textOverview.setText(args.overview)
        }
    }
}