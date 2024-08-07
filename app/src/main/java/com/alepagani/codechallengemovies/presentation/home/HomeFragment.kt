package com.alepagani.codechallengemovies.presentation.home

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.alepagani.codechallengemovies.R
import com.alepagani.codechallengemovies.data.mapper.toMovie
import com.alepagani.codechallengemovies.data.model.Movie
import com.alepagani.codechallengemovies.data.model.MovieWithGenres
import com.alepagani.codechallengemovies.databinding.FragmentHomeBinding
import com.alepagani.codechallengemovies.presentation.home.adapter.MovieAdapter
import com.alepagani.codechallengemovies.presentation.home.adapter.MovieLikedAdapter
import com.alepagani.codechallengemovies.presentation.home.listener.PaginationScrollListener
import com.alepagani.codechallengeyape.core.ResultResource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home), MovieAdapter.onMovieClickListener, MovieLikedAdapter.onMovieLikedClickListener {

    private lateinit var binding: FragmentHomeBinding
    private val viewModel by activityViewModels<HomeViewModel>()
    private lateinit var adapterMovies: MovieAdapter
    private lateinit var adapterMoviesLiked: MovieLikedAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHomeBinding.bind(view)
        initUI()
    }

    private fun initUI() {
        adapterMovies = MovieAdapter(emptyList(), this@HomeFragment)
        adapterMoviesLiked = MovieLikedAdapter(emptyList(), this@HomeFragment)
        binding.rvMovies.adapter = adapterMovies
        binding.rvMoviesLiked.adapter = adapterMoviesLiked
        addScrollListener()

        binding.search.setOnClickListener {
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToSearchFragment())
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.moviesWithGenreStateFlow.collect { result ->
                    when (result) {
                        is ResultResource.Failure -> {
                            Log.e("Error", "message: ${result.exception.message.toString()}")
                            binding.progresssBar.visibility = View.GONE
                        }
                        is ResultResource.Loading -> binding.progresssBar.visibility = View.VISIBLE
                        is ResultResource.Success -> {
                            adapterMovies.updateList(result.data)
                            binding.progresssBar.visibility = View.GONE
                        }
                    }
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.moviesLikedStateFlow.collect { result ->
                    when (result) {
                        is ResultResource.Failure -> {
                            Log.e("Error", "Movies liked error message: ${result.exception.message}")
                        }
                        is ResultResource.Loading -> {}
                        is ResultResource.Success -> {
                            if (result.data.size > 0) binding.movieLikedContainer.visibility =
                                View.VISIBLE else binding.movieLikedContainer.visibility = View.GONE
                            adapterMoviesLiked.updateList(result.data)
                        }
                    }
                }
            }
        }
    }

    private fun addScrollListener() {
        binding.rvMovies.addOnScrollListener(object :
            PaginationScrollListener(binding.rvMovies.layoutManager as LinearLayoutManager) {
            override fun loadMoreItems() {
                viewModel.getMovieWithGenreList()
            }

            override fun isLastPage() = viewModel.isAllMovieLoaded()

            override fun isLoading() = viewModel.isLoadingMovies
        })
    }

    override fun onMovieClick(movie: MovieWithGenres) {
        val action = HomeFragmentDirections.actionHomeFragmentToDetailFragment(movie.movie.toMovie())
        findNavController().navigate(action)
    }

    override fun onMovieLikedClick(movie: Movie) {
        val action = HomeFragmentDirections.actionHomeFragmentToDetailFragment(movie)
        findNavController().navigate(action)
    }
}