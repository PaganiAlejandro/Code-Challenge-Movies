package com.alepagani.codechallengemovies.presentation.detail

import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat.getColor
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.palette.graphics.Palette
import com.alepagani.codechallengemovies.R
import com.alepagani.codechallengemovies.core.addTransparency
import com.alepagani.codechallengemovies.core.getYearFromReleaseDate
import com.alepagani.codechallengemovies.data.model.Movie
import com.alepagani.codechallengemovies.databinding.FragmentDetailBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : Fragment(R.layout.fragment_detail) {

    private lateinit var binding: FragmentDetailBinding
    private val args by navArgs<DetailFragmentArgs>()
    private val viewmodel by viewModels<DetailViewModel>()
    private var dominantColor: Int = 0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentDetailBinding.bind(view)
        initUI()
    }

    private fun initUI() {
        val movie = args.movieArgument
        viewmodel.setMovie(movie)

        binding.apply {
            Glide.with(requireContext()).load("https://image.tmdb.org/t/p/w500/${movie.backdrop_path}").centerCrop().into(imageBackground)
            loadBackgrondImageAndGetColor(movie)

            textTitle.setText(movie.title)
            textYear.setText(movie.release_date.getYearFromReleaseDate())
            textOverview.setText(movie.overview)
            imageBack.setOnClickListener { findNavController().popBackStack() }
            buttonAction.setOnClickListener {
                viewmodel.saveMovieLiked()
            }
            viewmodel.isLiked.observe(viewLifecycleOwner, {
                buttonAction.setText(if (it) getString(R.string.txt_subscribed) else getString(R.string.txt_subscribe))
                buttonAction.setBackgroundResource(if (it) R.drawable.bg_button_detail else R.drawable.bg_button_subscribe)
                buttonAction.setTextColor(if (it) dominantColor else getColor(root.context, R.color.white))
            })
        }
    }

    private fun loadBackgrondImageAndGetColor(movie: Movie) {
        binding.apply {
            Glide.with(requireContext()).load("https://image.tmdb.org/t/p/w500/${movie.poster_path}").centerCrop()
                .transition(DrawableTransitionOptions.withCrossFade()).listener(object : RequestListener<Drawable> {
                    override fun onResourceReady(
                        resource: Drawable, model: Any, target: Target<Drawable>?, dataSource: DataSource, isFirstResource: Boolean
                    ): Boolean {
                        val drawable = resource as BitmapDrawable
                        val bitmap = drawable.bitmap
                        Palette.Builder(bitmap).generate {
                            it?.let { palette ->
                                dominantColor = palette.getDominantColor(
                                    getColor(
                                        root.context, R.color.white
                                    )
                                )
                                gradientOverlay.setBackgroundColor(addTransparency(dominantColor, 0.7f))
                                buttonAction.setTextColor(
                                    if (viewmodel.isLiked.value == true) dominantColor else getColor(root.context, R.color.white)
                                )
                            }
                        }
                        return false
                    }

                    override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>, isFirstResource: Boolean): Boolean {
                        return false
                    }
                }).into(imageCentered)
        }
    }
}