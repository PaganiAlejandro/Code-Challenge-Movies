package com.alepagani.codechallengemovies.presentation.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.alepagani.codechallengemovies.R
import com.alepagani.codechallengemovies.data.model.Movie
import com.alepagani.codechallengemovies.databinding.FragmentHomeBinding
import com.alepagani.codechallengemovies.presentation.home.adapter.MovieAdapter
import com.alepagani.codechallengemovies.presentation.home.adapter.MovieLikedAdapter
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
        adapterMovies = MovieAdapter(this@HomeFragment)
        adapterMoviesLiked = MovieLikedAdapter(emptyList(), this@HomeFragment)
        binding.rvMovies.adapter = adapterMovies
        binding.rvMoviesLiked.adapter = adapterMoviesLiked

        binding.search.setOnClickListener {
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToSearchFragment())
        }

        viewModel.popularMovies.observe(viewLifecycleOwner) {
            adapterMovies.submitData(lifecycle, it)
            binding.progresssBar.visibility = View.GONE
        }

        // when (result) {
        //     is ResultResource.Failure -> {
        //         Log.e("Error", "message: ${result.exception.message.toString()}")
        //         binding.progresssBar.visibility = View.GONE
        //     }
        //     is ResultResource.Loading -> binding.progresssBar.visibility = View.VISIBLE
        //     is ResultResource.Success -> {
        //         adapterMovies.updateList(result.data)
        //         binding.progresssBar.visibility = View.GONE
        //     }
        // }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.moviesLiked.observe(viewLifecycleOwner, { result ->
                    if (result.size > 0) binding.movieLikedContainer.visibility =
                        View.VISIBLE else binding.movieLikedContainer.visibility = View.GONE
                    adapterMoviesLiked.updateList(result)
                    binding.movieLikedContainer.visibility = View.VISIBLE

                })
            }
        }
    }

    override fun onMovieClick(movie: Movie) {
        val action = HomeFragmentDirections.actionHomeFragmentToDetailFragment(movie.id)
        findNavController().navigate(action)
    }

    override fun onMovieLikedClick(movie: Movie) {
        val action = HomeFragmentDirections.actionHomeFragmentToDetailFragment(movie.id)
        findNavController().navigate(action)
    }
}