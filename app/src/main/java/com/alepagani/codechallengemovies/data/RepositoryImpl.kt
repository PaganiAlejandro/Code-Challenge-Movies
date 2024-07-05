package com.alepagani.codechallengemovies.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.alepagani.codechallengemovies.data.local.LocalMovieDataSource
import com.alepagani.codechallengemovies.data.mapper.toMovieEntity
import com.alepagani.codechallengemovies.data.mapper.toMovieList
import com.alepagani.codechallengemovies.data.model.Movie
import com.alepagani.codechallengemovies.data.remote.RemoteMovieDataSource
import com.alepagani.codechallengemovies.domain.Repository
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val local: LocalMovieDataSource,
    private val remote: RemoteMovieDataSource
): Repository {


    companion object{
        const val STARTING_PAGE_INDEX = 1
    }
    override fun getMoviesFromApi(): PagingSource<Int, Movie> {
        return object : PagingSource<Int, Movie>() {
            override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
                val page = params?.key ?: STARTING_PAGE_INDEX
                return try {
                    Log.d("ALE PASO", "POR LLA,AR A;L SERVICIO")
                    val response = remote.getMovieList(page)
                    Log.d("ALE PASO", "PASOOO SERVICIO")
                    LoadResult.Page(
                        data = response.results,
                        prevKey = if (page == 1) null else page - 1,
                        nextKey = if (response.results.isEmpty()) null else page + 1
                    )
                } catch (e: Exception) {
                    LoadResult.Error(e)
                }
            }

            override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
                return state.anchorPosition?.let {
                    val anchorPage = state?.closestPageToPosition(it)
                    anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
                }
            }
        }
    }

    override fun getMoviesLiked() = local.getMoviesLiked().map { it.toMovieList() }

    override suspend fun saveMovieLiked(movie: Movie) = local.saveMovieLiked(movie.toMovieEntity())

    override suspend fun deleteMovieLiked(movie: Movie) = local.removeMovieLiked(movie.toMovieEntity())

    override suspend fun getGenreFromApi(): HashMap<Int, String> {
        var hashGenres = HashMap<Int, String>()
        val genreList = remote.getGenreList()
        genreList.genres.forEach {
            hashGenres.put(it.id, it.name)
        }
        return hashGenres
    }
}