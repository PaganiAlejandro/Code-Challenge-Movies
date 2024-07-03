package com.alepagani.codechallengemovies.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.alepagani.codechallengemovies.data.local.entity.MovieEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertMovie(movie: MovieEntity)

    @Query("SELECT * FROM movies where is_liked = 1")
    fun getAllMoviesLiked(): Flow<List<MovieEntity>>

    @Update
    suspend fun saveMovieLike(movie: MovieEntity)

    @Delete
    suspend fun deleteMovieLike(movie: MovieEntity)

    @Query("SELECT * FROM movies order by vote_average DESC")
    fun getAllMovieWithGenres(): Flow<List<MovieEntity>>

    @Transaction
    @Query("SELECT * FROM movies WHERE movieId = :id")
    suspend fun getMovieById(id: Int): MovieEntity
}