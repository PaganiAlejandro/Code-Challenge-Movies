package com.alepagani.codechallengemovies.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.alepagani.codechallengemovies.data.local.entity.GenreEntity
import com.alepagani.codechallengemovies.data.local.entity.MovieEntity
import com.alepagani.codechallengemovies.data.local.entity.MovieGenreCrossRef
import com.alepagani.codechallengemovies.data.model.MovieWithGenres
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

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertGenre(genre: GenreEntity)

    @Query("SELECT * FROM genres")
    fun getAllGenres(): List<GenreEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovieGenreCrossRef(crossRef: MovieGenreCrossRef)

    @Transaction
    @Query("SELECT * FROM movies")
    fun getAllMovieWithGenres(): Flow<List<MovieWithGenres>>
 }