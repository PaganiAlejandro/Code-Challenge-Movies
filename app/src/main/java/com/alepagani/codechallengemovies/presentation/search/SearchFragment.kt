package com.alepagani.codechallengemovies.presentation.search

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.alepagani.codechallengemovies.R
import com.alepagani.codechallengemovies.data.model.MovieGenre
import com.alepagani.codechallengemovies.databinding.FragmentSearchBinding
import com.alepagani.codechallengemovies.presentation.home.HomeViewModel
import com.alepagani.codechallengemovies.presentation.home.adapter.MovieSearchAdapter
import com.alepagani.codechallengeyape.core.ResultResource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchFragment : Fragment(R.layout.fragment_search), MovieSearchAdapter.onMovieSearchClickListener {
    private lateinit var binding: FragmentSearchBinding
    private val viewModel by activityViewModels<HomeViewModel>()
    private lateinit var adapterMovies: MovieSearchAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSearchBinding.bind(view)
        initUI()
    }

    private fun initUI() {
        adapterMovies = MovieSearchAdapter(emptyList(), this@SearchFragment)
        binding.rvMovies.adapter = adapterMovies
        binding.textCancel.setOnClickListener {
            findNavController().popBackStack()
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.movieSearchStateFlow.collect { result ->
                    when (result) {
                        is ResultResource.Failure -> Log.e("Error", "message: ${result.exception.message.toString()}")
                        is ResultResource.Loading -> {}
                        is ResultResource.Success -> adapterMovies.updateList(result.data)
                    }
                }
            }
        }
        viewModel.filterRecipes("")

        lifecycleScope.launch {
            binding.searchEdit.textChanges.collect { query ->
                viewModel.filterRecipes(query)
            }
        }
    }

    override fun onMovieSearchClick(movie: MovieGenre) {
        val action = SearchFragmentDirections.actionSearchFragmentToDetailFragment(movie.id)
        findNavController().navigate(action)
    }
}